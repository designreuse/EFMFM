package com.newtglobal.eFmFmFleet.services;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IAssignRouteBO;
import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IEmployeeDetailBO;
import com.newtglobal.eFmFmFleet.model.EFmFmActualRoutTravelledPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/view")
@Consumes("application/json")
@Produces("application/json")
public class ViewMapService {
	
	
	@POST
	@Path("/livetrips")
	public Response allLiveTrips(EFmFmAssignRoutePO assignRoutePO) throws ParseException{					
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
		assignRoutePO.setTripAssignDate(new Date());
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		List<EFmFmAssignRoutePO> assignRoutes=assignRouteBO.getAllLiveTrips(assignRoutePO);
		List<Map<String, Object>> allRoutes= new ArrayList<Map<String, Object>>();
        if(assignRoutes.size()!=0 || (!(assignRoutes.isEmpty()))){
			 for(EFmFmAssignRoutePO assignRoute:assignRoutes){
					StringBuffer waypoints=new StringBuffer();
					List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=iCabRequestBO.getParticularTripAllEmployees(assignRoute.getAssignRouteId());
					Map<String, Object>  requests = new HashMap<String, Object>();	
					if(employeeTripDetailPO.size()!=0 || (!(employeeTripDetailPO.isEmpty()))){
						for(EFmFmEmployeeTripDetailPO employeeTripDetail:employeeTripDetailPO){
							List<EFmFmUserMasterPO> employeeDetail=employeeDetailBO.getParticularEmpDetailsFromUserId(employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId(), assignRoute.geteFmFmClientBranchPO().getBranchId());				
							waypoints.append(employeeDetail.get(0).getLatitudeLongitude()+"|");						
					     }
					}
					requests.put("baseLatLong",assignRoute.geteFmFmClientBranchPO().getLatitudeLongitude());
					requests.put("routeId", assignRoute.getAssignRouteId());
					requests.put("shiftTime",formatter.format(assignRoute.getShiftTime()));
					requests.put("tripType", assignRoute.getTripType());
					requests.put("driverName", assignRoute.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
					requests.put("driverNumber", assignRoute.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
					requests.put("vehicleNumber", assignRoute.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
					requests.put("zoneName", assignRoute.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
					requests.put("numberOfEmployees", employeeTripDetailPO.size());
					requests.put("status", assignRoute.getTripStatus());					
					EFmFmActualRoutTravelledPO actualRoutTravelled=new EFmFmActualRoutTravelledPO();
					actualRoutTravelled.setTravelledTime(new Date());
					EFmFmClientBranchPO clientBranchPO=new EFmFmClientBranchPO();															
					clientBranchPO.setBranchId(assignRoute.geteFmFmClientBranchPO().getBranchId());
					actualRoutTravelled.seteFmFmClientBranchPO(clientBranchPO);
					actualRoutTravelled.setEfmFmAssignRoute(assignRoute);
					requests.put("waypoints", waypoints);
					String speed="";
					DecimalFormat decimalFormat = new DecimalFormat("0.#");

			    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
					if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
						if(actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentCabLocation()==null){
						List<EFmFmActualRoutTravelledPO> travelledEta=assignRouteBO.getLastEtaFromAssignRouteId(actualRoutTravelled);
					    if(travelledEta.size()!=0 || !(travelledEta.isEmpty())){
				    	requests.put("ExpectedTime",travelledEta.get(travelledEta.size()-1).getCurrentEta());
						requests.put("currentCabLatiLongi",actualRouteTravelled.get(actualRouteTravelled.size()-1).getLatitudeLongitude());
						try{
							float speedInput = Float.parseFloat(actualRouteTravelled.get(actualRouteTravelled.size()-1).getSpeed());
							speed=decimalFormat.format(speedInput);
					    	}catch(Exception e){}
				    	requests.put("speed",speed);
				    	}else{
				    		requests.put("ExpectedTime","calculating...");
							requests.put("currentCabLatiLongi",actualRouteTravelled.get(actualRouteTravelled.size()-1).getLatitudeLongitude());
							try{
								float speedInput = Float.parseFloat(actualRouteTravelled.get(actualRouteTravelled.size()-1).getSpeed());
								speed=decimalFormat.format(speedInput);
						    	}catch(Exception e){}
					    	requests.put("speed",speed);	
					    	}
						}
						else{
					    	requests.put("ExpectedTime",actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentEta());
							requests.put("currentCabLatiLongi",actualRouteTravelled.get(actualRouteTravelled.size()-1).getLatitudeLongitude());
							try{
								float speedInput = Float.parseFloat(actualRouteTravelled.get(actualRouteTravelled.size()-1).getSpeed());
								speed=decimalFormat.format(speedInput);
						    	}catch(Exception e){}
							requests.put("speed",speed);
						}
					}
					else{
//				    	requests.put("ExpectedTime",actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentEta());
						requests.put("ExpectedTime","calculating...");
						requests.put("speed",0);
						requests.put("currentCabLatiLongi",assignRoute.geteFmFmClientBranchPO().getLatitudeLongitude());
					}
					allRoutes.add(requests);
			 }
		 }
		return Response.ok(allRoutes, MediaType.APPLICATION_JSON).build();
	}
	
	
	@POST
	@Path("/individualtrip")
	public Response indevidualTripDetail(EFmFmAssignRoutePO assignRoutePO) throws ParseException{					
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		assignRoutePO.setAssignRouteId(assignRoutePO.getAssignRouteId());
		List<EFmFmAssignRoutePO> assignRoutes=assignRouteBO.closeParticularTrips(assignRoutePO);
		List<Map<String, Object>> allRoutes= new ArrayList<Map<String, Object>>();
        if(assignRoutes.size()!=0 || (!(assignRoutes.isEmpty()))){
			 for(EFmFmAssignRoutePO assignRoute:assignRoutes){
					List<Map<String, Object>> tripAllDetails = new ArrayList<Map<String, Object>>();
					Map<String, Object>  requests = new HashMap<String, Object>();	
					StringBuffer waypoints=new StringBuffer();
					List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=null;
					if(assignRoute.getTripType().equalsIgnoreCase("PICKUP")){
					    employeeTripDetailPO=iCabRequestBO.getParticularTripAllEmployees(assignRoute.getAssignRouteId());
					}
					else{
					    employeeTripDetailPO=iCabRequestBO.getDropTripAllSortedEmployees(assignRoute.getAssignRouteId());
					}					
					if(employeeTripDetailPO.size()!=0 || (!(employeeTripDetailPO.isEmpty()))){
						for(EFmFmEmployeeTripDetailPO employeeTripDetail:employeeTripDetailPO){
							Map<String, Object>  employeeDetails = new HashMap<String, Object>();	
							List<EFmFmUserMasterPO> employeeDetail=employeeDetailBO.getParticularEmpDetailsFromUserId(employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId(), assignRoute.geteFmFmClientBranchPO().getBranchId());
							employeeDetails.put("name",employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName());
							employeeDetails.put("employeeId", employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getEmployeeId());
							employeeDetails.put("employeeNum",employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber());							
							String dateFormatted="0";
							DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

							if(employeeTripDetail.getCabRecheddestinationTime()!=0){
								Date date = new Date(employeeTripDetail.getCabRecheddestinationTime());
								dateFormatted = formatter.format(date);								
							}
							employeeDetails.put("pickedUpTime",dateFormatted);
							/*if(employeeTripDetail.getBoardedFlg().equalsIgnoreCase("NO")){
								employeeDetails.put("status","No Show");
							}*/
							if(employeeTripDetail.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("DROP")){
								if(employeeTripDetail.getBoardedFlg().equalsIgnoreCase("D")){
									employeeDetails.put("status","Dropped");
								}
                                else if(employeeTripDetail.getBoardedFlg().equalsIgnoreCase("NO")){
                                	employeeDetails.put("status","No Show");
								}
								else{
									employeeDetails.put("status","Yet to dropped");
								}
								
							}
							else if(employeeTripDetail.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("PICKUP")){
								if(employeeTripDetail.getBoardedFlg().equalsIgnoreCase("B")){
									employeeDetails.put("status","PickedUp");
								}
								else if(employeeTripDetail.getBoardedFlg().equalsIgnoreCase("NO")){
									employeeDetails.put("status","No Show");
								}
								else{
									employeeDetails.put("status","Yet to picked up");
								}
							}							
							employeeDetails.put("tripTime", employeeTripDetail.geteFmFmEmployeeTravelRequest().getShiftTime());
							employeeDetails.put("address", employeeDetail.get(0).getAddress());
							waypoints.append(employeeDetail.get(0).getLatitudeLongitude()+"|");
							employeeDetails.put("latLongi", employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getLatitudeLongitude());
							if(assignRoute.getTripType().equalsIgnoreCase("PICKUP")){
								try{
								employeeDetails.put("pickUpTime", employeeTripDetail.geteFmFmEmployeeTravelRequest().getPickUpTime());
								if(!(employeeTripDetail.getCabstartFromDestination()==0)){
								employeeDetails.put("actualUpTime", formatter.format(new Date(employeeTripDetail.getCabstartFromDestination())));
								}else{
									employeeDetails.put("actualUpTime", "0");

								}
								}catch(Exception e){
									employeeDetails.put("pickUpTime","0");
									employeeDetails.put("actualUpTime", "0");
								}
							} 
							if(assignRoute.getTripType().equalsIgnoreCase("DROP")){
								try{
								if(!(employeeTripDetail.getCabstartFromDestination()==0)){
								employeeDetails.put("actualUpTime", formatter.format(new Date(employeeTripDetail.getCabstartFromDestination())));
								}else{
									employeeDetails.put("actualUpTime", "0");
								}
								}catch(Exception e){
									employeeDetails.put("actualUpTime", "0");
								}
							} 
							tripAllDetails.add(employeeDetails);
					     }
			        }
					requests.put("waypoints", waypoints);
					requests.put("tripType", assignRoute.getTripType());
					requests.put("tripStatus", assignRoute.getTripStatus());
					requests.put("routeId", assignRoute.getAssignRouteId());
					requests.put("driverName", assignRoute.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
					requests.put("driverNumber", assignRoute.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
					requests.put("vehicleNumber", assignRoute.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
					requests.put("zoneName", assignRoute.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
					requests.put("baseLatLong",assignRoute.geteFmFmClientBranchPO().getLatitudeLongitude());
					EFmFmActualRoutTravelledPO actualRoutTravelled=new EFmFmActualRoutTravelledPO();
					actualRoutTravelled.setTravelledTime(new Date());
					EFmFmClientBranchPO clientBranchPO=new EFmFmClientBranchPO();										
					clientBranchPO.setBranchId(assignRoute.geteFmFmClientBranchPO().getBranchId());					
					actualRoutTravelled.seteFmFmClientBranchPO(clientBranchPO);
					actualRoutTravelled.setEfmFmAssignRoute(assignRoute);
			    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
					if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
						requests.put("currentCabLatiLongi",actualRouteTravelled.get(actualRouteTravelled.size()-1).getLatitudeLongitude());
						requests.put("expectedTime",actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentEta());
				    	requests.put("plannedDistance",assignRoute.getPlannedDistance());
				    	System.out.println(assignRoute.getTravelledDistance());
				    	String availDistance="";
				    	String speed="";
				    	try{
						float distanceInput = (float) (assignRoute.getTravelledDistance());
						float speedInput = Float.parseFloat(actualRouteTravelled.get(actualRouteTravelled.size()-1).getSpeed());
						DecimalFormat decimalFormat = new DecimalFormat("0.#");
						availDistance=decimalFormat.format(distanceInput);
						speed=decimalFormat.format(speedInput);
				    	}catch(Exception e){}
						requests.put("travelledDistance",availDistance);					
						requests.put("speed",speed);
						
						if(actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentCabLocation()==null){
					    List<EFmFmActualRoutTravelledPO> cabLocation=assignRouteBO.getCabLocationFromAssignRouteId(actualRoutTravelled);
					    if(cabLocation.size()!=0 || !(cabLocation.isEmpty())){
					    requests.put("currentLocation",cabLocation.get(cabLocation.size()-1).getCurrentCabLocation());
					    requests.put("ExpectedTime",cabLocation.get(cabLocation.size()-1).getCurrentEta());
					    }
					    else{
						requests.put("currentLocation","waiting");	
					    }
						}
						else{
						requests.put("ExpectedTime",actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentEta());
						requests.put("currentLocation",actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentCabLocation());}
						}
						else{
							requests.put("ExpectedTime","calculating...");
							requests.put("currentCabLatiLongi",assignRoute.geteFmFmClientBranchPO().getLatitudeLongitude());
						}
						requests.put("empDetails",tripAllDetails);
						allRoutes.add(requests);
				 }
			 }
		return Response.ok(allRoutes, MediaType.APPLICATION_JSON).build();
	}

}
