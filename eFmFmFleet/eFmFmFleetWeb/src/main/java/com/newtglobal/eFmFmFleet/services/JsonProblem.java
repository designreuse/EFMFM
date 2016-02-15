package com.newtglobal.eFmFmFleet.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.minidev.json.JSONObject;

import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

public class JsonProblem {
	Settings settings;
	ArrayList<JsonEmployee> employees;
	ArrayList<JsonDepot> depots;
	ArrayList<JsonVehicleType> vehicle_types;
	ArrayList<JsonVehicle> vehicles;

	String response;
	JSONRPC2Response jsonresponse;
	JsonSolution solution;

	private static Socket clientSocket;
	private static PrintWriter requestBuffer;
	private static BufferedReader responseBuffer;

	public JsonProblem(ArrayList<JsonEmployee> employees, ArrayList<JsonDepot> depots,
			ArrayList<JsonVehicleType> vehicle_types, ArrayList<JsonVehicle> vehicles) {
		this.settings = new Settings();
		this.employees = employees;
		this.depots = depots;
		this.vehicle_types = vehicle_types;
		this.vehicles = vehicles;
	}

	public String get_json_string() {
		String method_name = "createRoute";
		String request_id = "request1"; // Client.request_id_prefix +
										// Client.request_id_suffix++;
		Map<String, Object> params = new HashMap<String, Object>();
		settings.finalise();
		params.put("settings", settings);
		params.put("employees", employees);
		params.put("depots", depots);
		params.put("vehicle_types", vehicle_types);
		params.put("vehicles", vehicles);
		JSONRPC2Request request = new JSONRPC2Request(method_name, params, request_id);
		return request.toJSONString();
	}

	public void startSolve() {
		StartRouting S = new StartRouting(this);
		S.start();
	}

	public static void init_client(int portNumber) throws UnknownHostException, IOException {
		String hostName = "127.0.0.1";
		clientSocket = new Socket(hostName, portNumber);
		requestBuffer = new PrintWriter(clientSocket.getOutputStream(), true);
		responseBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public static void disconnect_client() throws IOException {
		responseBuffer.close();
		requestBuffer.close();
		clientSocket.close();
	}

	public class StartRouting implements Runnable {
		JsonProblem problem;
		static final int portNumber = 4444;
		Thread t;
		String response;

		public StartRouting(JsonProblem problem) {
			this.problem = problem;
		}

		public void run() {
			try {
				JsonProblem.init_client(portNumber);
				requestBuffer.println(problem.get_json_string());

				while (true) {
					response = responseBuffer.readLine();
					if (response != null) {
						break;
					}
				}

				System.out.println(response);

				problem.response = response;

				problem.jsonresponse = JSONRPC2Response.parse(response);

				if (jsonresponse.getError() != null && jsonresponse.getError().equals(JSONRPC2Error.INTERNAL_ERROR)) {
					System.err.println("Error in creating routes");
					problem.solution = null;
				} else {
					problem.solution = new JsonSolution((JSONObject) problem.jsonresponse.getResult());
					// insert code
					/// =solution.routes.
					for (JsonRoute route : solution.routes) {
						boolean vehicleAllocated = false;
						int routeId = 0;
						//route.start_time;
						//route.end_time;
						int seq_Num=0;
						Set<Integer> keyJob = route.jobs.keySet();
						for (int key : keyJob) {							
							JsonRoute.Job J = route.jobs.get(key);
							//J.time
							if (solution.settings.trip_type.equalsIgnoreCase("login")
									&& J.type.equalsIgnoreCase("pickup")) {
								// insert the job into the route
								System.out.println("JsonProb_EmpId" + Integer.parseInt(J.employee.emp_id));
								System.out.println("JsonProb_Type" + route.vehicle_type.vehicle_type_name);
								if (!vehicleAllocated) {
									routeId = VehicleAllocate(route.vehicle_type.capacity,
											Integer.parseInt(J.employee.emp_id), Integer.parseInt(J.employee.branch_id),
											route.escort_required,route.number_of_employees,J.time,seq_Num++);
									if (routeId != 0) {
										vehicleAllocated = true;
									}
								} else {
									employeeAllocate(routeId, Integer.parseInt(J.employee.emp_id), J.type,Integer.parseInt(J.employee.branch_id),J.time,seq_Num++);
								}

							} else if ((solution.settings.trip_type.equalsIgnoreCase("logout")
									&& J.type.equalsIgnoreCase("drop"))) {
								// insert the job into the route
								System.out.println("JsonProb_EmpId" + Integer.parseInt(J.employee.emp_id));
								System.out.println("JsonProb_Type" + route.vehicle_type.vehicle_type_name);
								if (!vehicleAllocated) {
									routeId = VehicleAllocate(route.vehicle_type.capacity,
											Integer.parseInt(J.employee.emp_id), Integer.parseInt(J.employee.branch_id),
											route.escort_required,route.number_of_employees,J.time,seq_Num++);
									if (routeId != 0) {
										vehicleAllocated = true;
									}
								} else {
									employeeAllocate(routeId, Integer.parseInt(J.employee.emp_id), J.type,Integer.parseInt(J.employee.branch_id),J.time,seq_Num++);
								}
							}
						}
					}
					System.out.println("Routes successfully solved");
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONRPC2ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					disconnect_client();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		public void start() {
			if (t == null) {
				t = new Thread(this, "Default routing client thread");
				t.start();
			}
		}
	}

	public int VehicleAllocate(int vehicleCapacity, int employeeReqId, int BranchId, boolean escortRequired,int requiredCapcity,Date pickupTime,int seq_Num) {

		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext()
				.getBean("IVehicleCheckInBO");
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm:ss");		
		SimpleDateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
		EFmFmEmployeeTripDetailPO employeeTripDetailPO = new EFmFmEmployeeTripDetailPO();
		int routeId = 0;
		List<EFmFmAssignRoutePO> assignRoutePO;
		EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO = new EFmFmEmployeeTravelRequestPO();
		eFmFmEmployeeTravelRequestPO.setBranchId(BranchId);
		eFmFmEmployeeTravelRequestPO.setRequestId(employeeReqId);
		List<EFmFmEmployeeTravelRequestPO> cabRequests = null;
		cabRequests = iCabRequestBO.getparticularRequestwithShiftTime(eFmFmEmployeeTravelRequestPO);
		List<EFmFmVehicleCheckInPO> allCheckInVehicles;
		if (vehicleCapacity <= 5)
			allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLessCapacity(BranchId, 10);
		else
			allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLargeCapacity(BranchId, 10);
		if (!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0) {
			EFmFmVehicleMasterPO updateVehicleStatus = iCabRequestBO
					.getVehicleDetail(allCheckInVehicles.get(0).getEfmFmVehicleMaster().getVehicleId());
			updateVehicleStatus.setStatus("A");
			updateVehicleStatus.setAvailableSeat(updateVehicleStatus.getCapacity() -1);
			iVehicleCheckInBO.update(updateVehicleStatus);			
			EFmFmDriverMasterPO particularDriverDetails = approvalBO
					.getParticularDriverDetail(allCheckInVehicles.get(0).getEfmFmDriverMaster().getDriverId());
			particularDriverDetails.setStatus("A");
			approvalBO.update(particularDriverDetails);
			List<EFmFmDeviceMasterPO> deviceDetails = iVehicleCheckInBO.getDeviceDetailsFromDeviceId(
					allCheckInVehicles.get(0).geteFmFmDeviceMaster().getDeviceId(), BranchId);
			deviceDetails.get(0).setStatus("Y");
			iVehicleCheckInBO.update(deviceDetails.get(0));
			EFmFmAssignRoutePO eAssignRoutePO = new EFmFmAssignRoutePO();
			if (!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0) {
				EFmFmAssignRoutePO assignRoute = new EFmFmAssignRoutePO();
				EFmFmVehicleCheckInPO vehicleCheckInPO = new EFmFmVehicleCheckInPO();
				vehicleCheckInPO.setCheckInId(((EFmFmVehicleCheckInPO) allCheckInVehicles.get(allCheckInVehicles.size() - 1)).getCheckInId());				
				assignRoute.setEfmFmVehicleCheckIn(vehicleCheckInPO);
				EFmFmRouteAreaMappingPO routeAreaMapping = new EFmFmRouteAreaMappingPO();
				routeAreaMapping.setRouteAreaId(cabRequests.get(0).geteFmFmRouteAreaMapping().getRouteAreaId());
				assignRoute.seteFmFmRouteAreaMapping(routeAreaMapping);
				if (escortRequired == true){
					requiredCapcity+=1;
					assignRoute.setEscortRequredFlag("Y");					
					List<EFmFmEscortCheckInPO> escortList = iCabRequestBO.getAllCheckedInEscort(BranchId);
						if (!escortList.isEmpty() || escortList.size() != 0) {
							EFmFmEscortCheckInPO checkInEscort = new EFmFmEscortCheckInPO();
							checkInEscort.setEscortCheckInId(((EFmFmEscortCheckInPO) escortList.get(0)).getEscortCheckInId());
							assignRoute.seteFmFmEscortCheckIn(checkInEscort);
							((EFmFmEscortCheckInPO) escortList.get(0)).setStatus("N");
							iVehicleCheckInBO.update((EFmFmEscortCheckInPO) escortList.get(0));
						}
				}
				else{
					assignRoute.setEscortRequredFlag("N");
				}
				assignRoute.setAllocationMsg("N");
				assignRoute.setShiftTime(cabRequests.get(0).getShiftTime());
				assignRoute.setTripStatus("allocated");
				assignRoute.setTripAssignDate(new Date());
				assignRoute.setVehicleStatus("A");
				assignRoute.setBucketStatus("N");
				EFmFmClientBranchPO eFmFmClientBranchPO = new EFmFmClientBranchPO();
				eFmFmClientBranchPO.setBranchId(cabRequests.get(0).geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				assignRoute.seteFmFmClientBranchPO(eFmFmClientBranchPO);
				assignRoute.setTripType(cabRequests.get(0).getTripType());
				((EFmFmVehicleCheckInPO) allCheckInVehicles.get(allCheckInVehicles.size() - 1)).setStatus("N");
				iVehicleCheckInBO.update((EFmFmVehicleCheckInPO) allCheckInVehicles.get(allCheckInVehicles.size() - 1));
				iCabRequestBO.update(assignRoute);
				cabRequests.get(0).setReadFlg("R");	
				cabRequests.get(0).setDropSequence(seq_Num);
				System.out.println("pickupTime"+shiftFormate.format(pickupTime));
				java.sql.Time myTime = java.sql.Time.valueOf(shiftFormate.format(pickupTime));				
				cabRequests.get(0).setPickUpTime(myTime);
				
				iCabRequestBO.update(cabRequests.get(0));
			}
		}
		assignRoutePO = iCabRequestBO.getCreatedAssignRoute(
				cabRequests.get(0).geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO()
						.getBranchId(),
				cabRequests.get(0).geteFmFmEmployeeRequestMaster().getTripType(), cabRequests.get(0).getShiftTime());
		if (assignRoutePO.size() > 0) {
			EFmFmVehicleMasterPO vehicleMaster = iCabRequestBO.getVehicleDetail(((EFmFmAssignRoutePO)assignRoutePO.get(0)).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());			
			vehicleMaster.setAvailableSeat((vehicleMaster.getCapacity()-1)-requiredCapcity);
			vehicleMaster.setStatus("allocated");
			iVehicleCheckInBO.update(vehicleMaster);
			EFmFmDriverMasterPO particularDriverDetails = approvalBO.getParticularDriverDetail(
					assignRoutePO.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
			particularDriverDetails.setStatus("allocated");
			approvalBO.update(particularDriverDetails);
			List<EFmFmDeviceMasterPO> deviceDetails = iVehicleCheckInBO.getDeviceDetailsFromDeviceId(
					assignRoutePO.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(),BranchId);
			deviceDetails.get(0).setStatus("allocated");
			iVehicleCheckInBO.update(deviceDetails.get(0));
			
			System.out.println(assignRoutePO.get(0).getAssignRouteId());
			routeId = assignRoutePO.get(0).getAssignRouteId();
			if (((EFmFmAssignRoutePO) assignRoutePO.get(0)).getTripType().equalsIgnoreCase("DROP")) {
				employeeTripDetailPO.setTenMinuteMessageStatus("Y");
				employeeTripDetailPO.setTwoMinuteMessageStatus("Y");
				employeeTripDetailPO.setReachedFlg("Y");
				employeeTripDetailPO.setCabDelayMsgStatus("Y");

			} else {
				employeeTripDetailPO.setTenMinuteMessageStatus("N");
				employeeTripDetailPO.setTwoMinuteMessageStatus("N");
				employeeTripDetailPO.setReachedFlg("N");
				employeeTripDetailPO.setCabDelayMsgStatus("N");

			}
			employeeTripDetailPO.setActualTime(new Date());
			employeeTripDetailPO.setGoogleEta(0);
			employeeTripDetailPO.setBoardedFlg("N");
			employeeTripDetailPO.seteFmFmEmployeeTravelRequest(cabRequests.get(0));
			employeeTripDetailPO.setEfmFmAssignRoute(assignRoutePO.get(0));
			employeeTripDetailPO.setCurrenETA("0");
			employeeTripDetailPO.setEmployeeStatus("allocated");
			iCabRequestBO.update(employeeTripDetailPO);

		}
		return routeId;
	}

	public void employeeAllocate(int routeId, int requestId, String tripType,int branchId,Date pickupTime,int seq_Num) {
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");		
		EFmFmEmployeeTripDetailPO employeeTripDetailPO = new EFmFmEmployeeTripDetailPO();	
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm:ss");
		EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO = new EFmFmEmployeeTravelRequestPO();
		eFmFmEmployeeTravelRequestPO.setBranchId(branchId);
		eFmFmEmployeeTravelRequestPO.setRequestId(requestId);
		List<EFmFmEmployeeTravelRequestPO> cabRequests = null;
		cabRequests = iCabRequestBO.getparticularRequestwithShiftTime(eFmFmEmployeeTravelRequestPO);		
		EFmFmAssignRoutePO assignRoutePO = new EFmFmAssignRoutePO();
		assignRoutePO.setAssignRouteId(routeId);		
		if (tripType.equalsIgnoreCase("DROP")) {
			employeeTripDetailPO.setTenMinuteMessageStatus("Y");
			employeeTripDetailPO.setTwoMinuteMessageStatus("Y");
			employeeTripDetailPO.setReachedFlg("Y");
			employeeTripDetailPO.setCabDelayMsgStatus("Y");
		} else {
			employeeTripDetailPO.setTenMinuteMessageStatus("N");
			employeeTripDetailPO.setTwoMinuteMessageStatus("N");
			employeeTripDetailPO.setReachedFlg("N");
			employeeTripDetailPO.setCabDelayMsgStatus("N");
		}
		employeeTripDetailPO.setActualTime(new Date());
		employeeTripDetailPO.setGoogleEta(0);
		employeeTripDetailPO.setBoardedFlg("N");
		employeeTripDetailPO.seteFmFmEmployeeTravelRequest(cabRequests.get(0));
		employeeTripDetailPO.setEfmFmAssignRoute(assignRoutePO);
		employeeTripDetailPO.setCurrenETA("0");		
		employeeTripDetailPO.setEmployeeStatus("allocated");
		iCabRequestBO.update(employeeTripDetailPO);
		cabRequests.get(0).setReadFlg("R");
		cabRequests.get(0).setDropSequence(seq_Num);
		System.out.println("pickupTime"+shiftFormate.format(pickupTime));
		java.sql.Time myTime = java.sql.Time.valueOf(shiftFormate.format(pickupTime));				
		cabRequests.get(0).setPickUpTime(myTime);
		iCabRequestBO.update(cabRequests.get(0));
	}
}