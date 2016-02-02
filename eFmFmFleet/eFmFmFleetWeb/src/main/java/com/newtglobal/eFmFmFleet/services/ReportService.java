package com.newtglobal.eFmFmFleet.services;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IAlertBO;
import com.newtglobal.eFmFmFleet.business.bo.IAssignRouteBO;
import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripTimingMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/report")
@Consumes("application/json")
@Produces("application/json")
public class ReportService {

	private static Log log = LogFactory.getLog(ReportService.class);

	/**
	 * The tripSheet Details method implemented. for getting the list of trip
	 * sheet Report
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-06
	 * 
	 * @return tripsheet details.
	 * @throws ParseException
	 */

	/**
	 * @param assignRoutePO
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/tripSheet")
	public Response tripSheet(EFmFmAssignRoutePO assignRoutePO)
			throws ParseException {
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader
				.getContext().getBean("ICabRequestBO");
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
				.getAllTripByDate(fromDate, toDate, assignRoutePO
						.geteFmFmClientBranchPO().getBranchId());
		List<Map<String, Object>> trip = new ArrayList<Map<String, Object>>();
		List<String> dateList = new ArrayList<String>();
		String date = "";
		if ((!(allTripDetails.isEmpty())) || allTripDetails.size() != 0) {
			outer: for (EFmFmAssignRoutePO trips : allTripDetails) {
				if (!(dateList.contains(formatter.format(trips
						.getTripAssignDate())))) {
					date = formatter.format(trips.getTripAssignDate());
					dateList.add(formatter.format(trips.getTripAssignDate()));
				} else {
					continue outer;
				}
				Map<String, Object> detailTrip = new HashMap<String, Object>();
				List<Map<String, Object>> tripList = new ArrayList<Map<String, Object>>();
				for (EFmFmAssignRoutePO tripDetails : allTripDetails) {
					if (date.equalsIgnoreCase(formatter.format(tripDetails
							.getTripAssignDate()))) {
						Map<String, Object> detailTripList = new HashMap<String, Object>();
						detailTripList.put("routeId",
								tripDetails.getAssignRouteId());
						detailTripList.put("routeName", tripDetails
								.geteFmFmRouteAreaMapping()
								.geteFmFmZoneMaster().getZoneName());
						detailTripList.put("endKm",
								tripDetails.getOdometerEndKm());
						detailTripList.put("startKm",
								tripDetails.getOdometerStartKm());
						detailTripList.put("plannedDistance",
								tripDetails.getPlannedDistance());
						try {
							if (tripDetails.getTravelledDistance() == 0) {
								detailTripList.put("travelledDistance", "NA");
							} else {
								String extensionRemoved = Double.toString(
										tripDetails.getTravelledDistance())
										.split("\\.")[1];
								if (!(extensionRemoved.equalsIgnoreCase("0"))) {
									detailTripList.put("travelledDistance",
											Math.round((double) tripDetails
													.getTravelledDistance()));
								} else {
									detailTripList.put("travelledDistance",
											tripDetails.getTravelledDistance());
								}
							}
						} catch (Exception e) {
						}
						detailTripList.put("vehicleNumber", tripDetails
								.getEfmFmVehicleCheckIn()
								.getEfmFmVehicleMaster().getVehicleNumber());
						detailTripList.put("driverName", tripDetails
								.getEfmFmVehicleCheckIn()
								.getEfmFmDriverMaster().getFirstName());
						detailTripList.put("deviceNumber", tripDetails
								.getEfmFmVehicleCheckIn()
								.getEfmFmDriverMaster().getMobileNumber());
						detailTripList.put("escortRequired", "NotRequired");
						if (tripDetails.getEscortRequredFlag()
								.equalsIgnoreCase("Y")) {
							try {
								int a = tripDetails.geteFmFmEscortCheckIn()
										.getEscortCheckInId();
								detailTripList.put("escortRequired",
										tripDetails.geteFmFmEscortCheckIn()
												.geteFmFmEscortMaster()
												.getFirstName());
								detailTripList.put("escortId", tripDetails
										.geteFmFmEscortCheckIn()
										.getEscortCheckInId());
							} catch (Exception e) {
								detailTripList.put("escortRequired",
										"Escort Required But Not Available");
							}
						} else {
							detailTripList
									.put("escortRequired", "Not Required");
						}
						detailTripList.put("tripStartDate", dateFormatter
								.format(tripDetails.getTripStartTime()));
						detailTripList.put("tripAssignDate", dateFormatter
								.format(tripDetails.getTripAssignDate()));
						detailTripList.put("tripCompleteDate", dateFormatter
								.format(tripDetails.getTripCompleteTime()));
						detailTripList.put("tripType",
								tripDetails.getTripType());
						List<Map<String, Object>> pickupDetails = new ArrayList<Map<String, Object>>();
						List<EFmFmEmployeeTripDetailPO> employeeDetails = iCabRequestBO
								.getParticularTripAllEmployees(tripDetails
										.getAssignRouteId());
						if ((!(employeeDetails.isEmpty()))
								|| employeeDetails.size() != 0) {
							detailTripList.put(
									"shitTime",timeFormat
									.format(tripDetails.getShiftTime()));
									
							for (EFmFmEmployeeTripDetailPO employeeList : employeeDetails) {
								Map<String, Object> empList = new HashMap<String, Object>();
								empList.put("empId", employeeList
										.geteFmFmEmployeeTravelRequest()
										.geteFmFmEmployeeRequestMaster()
										.getEfmFmUserMaster().getEmployeeId());
								empList.put("empName", employeeList
										.geteFmFmEmployeeTravelRequest()
										.geteFmFmEmployeeRequestMaster()
										.getEfmFmUserMaster().getFirstName());

								if (employeeList
										.geteFmFmEmployeeTravelRequest()
										.getEfmFmUserMaster().getGender()
										.equalsIgnoreCase("Male")) {
									empList.put("empGender", 1);
								} else {
									empList.put("empGender", 2);
								}
								empList.put("empAddress", employeeList
										.geteFmFmEmployeeTravelRequest()
										.getEfmFmUserMaster().getAddress());
								empList.put(
										"shiftTime",
										shiftFormate
										.format(employeeDetails.get(0)
												.getEfmFmAssignRoute()
												.getShiftTime()));
								empList.put("scheduleTime", timeFormat
										.format(employeeList.getActualTime()));

								try {
									if (!(employeeList
											.getCabstartFromDestination() == 0)) {
										empList.put(
												"travelTime",
												timeFormat.format(employeeList
														.getCabstartFromDestination()));
									} else {
										empList.put("travelTime", "NA");
									}
								} catch (Exception e) {

								}
								if (tripDetails.getTripType().equalsIgnoreCase(
										"PICKUP")) {
									empList.put(
											"scheduleTime",
											timeFormat
													.format(employeeList
															.geteFmFmEmployeeTravelRequest()
															.getPickUpTime()));
									if (employeeList.getBoardedFlg()
											.equalsIgnoreCase("B")) {
										empList.put("travelStatus", "Boarded");
									} else if (employeeList.getBoardedFlg()
											.equalsIgnoreCase("NO")) {
										empList.put("travelStatus", "No Show");
									} else if (employeeList.getBoardedFlg()
											.equalsIgnoreCase("N")) {
										empList.put("travelStatus",
												"Yet to PickUp");
									}
								}
								if (tripDetails.getTripType().equalsIgnoreCase(
										"DROP")) {
									if (employeeList.getBoardedFlg()
											.equalsIgnoreCase("D")) {
										empList.put("travelStatus", "Dropped");
									} else if (employeeList.getBoardedFlg()
											.equalsIgnoreCase("NO")) {
										empList.put("travelStatus", "No Show");
									} else if (employeeList.getBoardedFlg()
											.equalsIgnoreCase("N")) {
										empList.put("travelStatus",
												"Yet to Drop");
									}
								}
								pickupDetails.add(empList);
							}

						}
						detailTripList.put("empDetails", pickupDetails);
						tripList.add(detailTripList);
					}
					detailTrip.put("tripDetail", tripList);
					detailTrip.put("tripAssignDate", date);

				}
				trip.add(detailTrip);

			}
			allTrips.put("data", trip);
		}
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * @param Get
	 *            all trips Kilometer by selected date range...
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/kmreports")
	public Response kiloMeterReportsByVehicleAndByVendor(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		log.info("Service called");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		List<Map<String, Object>> allVehicleDetails = new ArrayList<Map<String, Object>>();
		if (assignRoutePO.getSearchType().equalsIgnoreCase("vehicle")) {
			
			
			/*List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
					.getAllTripsTravelledAndPlannedDistanceByDateAndVehicle(
							fromDate, toDate, assignRoutePO
									.geteFmFmClientBranchPO().getBranchId(),
							assignRoutePO.getVehicleId());*/
			List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
					.getAllTripsTravelledAndPlannedDistanceByAllVendor(
							fromDate, toDate, assignRoutePO
									.geteFmFmClientBranchPO().getBranchId());
			log.info(allTripDetails.size()+"Inside Vehicle Fun"+assignRoutePO.getSearchType());
			if ((!(allTripDetails.isEmpty())) || allTripDetails.size() != 0) {
				for (EFmFmAssignRoutePO assignRouteDetail : allTripDetails) {
					Map<String, Object> vehicleList = new HashMap<String, Object>();
					vehicleList.put("date", formatter.format(assignRouteDetail.getTripAssignDate()));
					vehicleList.put("totalApportunity", "154");
					vehicleList.put("plannedDistance", assignRouteDetail.getPlannedDistance());
					vehicleList.put("travelledDistance", assignRouteDetail.getTravelledDistance());
					vehicleList.put("vehicleType", assignRouteDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel());
					vehicleList.put("vehicleNumber", assignRouteDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
					vehicleList.put("vendorName", assignRouteDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
					allVehicleDetails.add(vehicleList);
				}
			}
		}
		//all Vendor Wice KM reports....First Template
		else if (assignRoutePO.getVendorId()==0 && assignRoutePO.getTime().equalsIgnoreCase("1") ) {
			List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
					.getAllTripsTravelledAndPlannedDistanceByAllVendor(
							fromDate, toDate, assignRoutePO
									.geteFmFmClientBranchPO().getBranchId());
			log.info("trips:"+allTripDetails.size());
			if ((!(allTripDetails.isEmpty())) || allTripDetails.size() != 0) {
				List<String> vehicleNumber=new ArrayList<String>();
				for (EFmFmAssignRoutePO routeDetail : allTripDetails) {				
					Map<String, Object> vehicleList = new HashMap<String, Object>();					
					String tripDate=formatter.format(routeDetail.getTripAssignDate());
//					if(!(tripDates.contains(tripDate))){
					if(!(vehicleNumber.contains(routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+tripDate))){
					vehicleNumber.add(routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+tripDate);
					Date tripDateMonths = (Date) formatter.parse(tripDate);
					List<EFmFmAssignRoutePO> vehicleDetails = iAssignRouteBO.getAllTripsVehicleDetailsByVehicleNumber(tripDateMonths,tripDateMonths, assignRoutePO
							.geteFmFmClientBranchPO().getBranchId(), routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
					log.info("vehicleDetails..firsttemplete:"+vehicleDetails.size());
					int plannedDistance = 0;
					int travelledDistance = 0;					
					for (EFmFmAssignRoutePO vehicle : vehicleDetails) {
						plannedDistance+=vehicle.getPlannedDistance();
						travelledDistance+=vehicle.getTravelledDistance();								
					}
					vehicleList.put("date", formatter.format(routeDetail.getTripAssignDate()));
					vehicleList.put("totalApportunity", "154");
					vehicleList.put("plannedDistance", plannedDistance);
					vehicleList.put("travelledDistance", travelledDistance);
					vehicleList.put("vehicleType", routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel());
					vehicleList.put("vehicleNumber", routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
					vehicleList.put("vendorName", routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
					allVehicleDetails.add(vehicleList);
					}
//				}
				}
			}
		} 
		//Shfit Wice Second template		
		else if (assignRoutePO.getTime().equalsIgnoreCase("0") && assignRoutePO.getVendorId()==0) {			
			List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
					.getAllTripsTravelledAndPlannedDistanceByAllVendor(
							fromDate, toDate, assignRoutePO
									.geteFmFmClientBranchPO().getBranchId());
			log.info("trips:"+allTripDetails.size());
			if ((!(allTripDetails.isEmpty())) || allTripDetails.size() != 0) {
				List<String> tripDates=new ArrayList<String>();
				for (EFmFmAssignRoutePO routeDetail : allTripDetails) {				
					Map<String, Object> vehicleList = new HashMap<String, Object>();					
					String tripDate=formatter.format(routeDetail.getTripAssignDate());
//					if(!(tripDates.contains(tripDate))){
					if(!(tripDates.contains(tripDate+routeDetail.getShiftTime()))){
					tripDates.add(tripDate+routeDetail.getShiftTime());
					Date tripDateMonths = (Date) formatter.parse(tripDate);
					List<EFmFmAssignRoutePO> vehicleDetails = iAssignRouteBO.getAllTripsVehicleKMDetailsByShiftTime(tripDateMonths,tripDateMonths, assignRoutePO
							.geteFmFmClientBranchPO().getBranchId(), routeDetail.getShiftTime());
					log.info("Shfit Wice Second:"+vehicleDetails.size());
					int plannedDistance = 0;
					int travelledDistance = 0;					
					for (EFmFmAssignRoutePO vehicle : vehicleDetails) {
						plannedDistance+=vehicle.getPlannedDistance();
						travelledDistance+=vehicle.getTravelledDistance();								
					}
					vehicleList.put("date", formatter.format(routeDetail.getTripAssignDate()));
					vehicleList.put("shiftTime", timeFormat.format(routeDetail.getShiftTime()));
					vehicleList.put("totalApportunity", "154");
					vehicleList.put("plannedDistance", plannedDistance);
					vehicleList.put("travelledDistance", travelledDistance);
					vehicleList.put("vehicleType", routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel());
					vehicleList.put("vehicleNumber", routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
					vehicleList.put("vendorName", routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
					allVehicleDetails.add(vehicleList);
					}
//				}
				}
			}
		
			
		}
		else {
			//Vendor wice KM reports last template
			List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
					.getAllTripsTravelledAndPlannedDistanceByDate(fromDate,
							toDate, assignRoutePO.geteFmFmClientBranchPO()
									.getBranchId(), assignRoutePO.getVendorId());
			if ((!(allTripDetails.isEmpty())) || allTripDetails.size() != 0) {
				List<String> tripDates=new ArrayList<String>();
				for (EFmFmAssignRoutePO routeDetail : allTripDetails) {				
					Map<String, Object> vehicleList = new HashMap<String, Object>();					
					String tripDate=formatter.format(routeDetail.getTripAssignDate());
//					if(!(tripDates.contains(tripDate))){
					if(!(tripDates.contains(tripDate))){
					tripDates.add(tripDate);
					Date tripDateMonths = (Date) formatter.parse(tripDate);
					List<EFmFmAssignRoutePO> vehicleDetails = iAssignRouteBO
							.getAllTripsTravelledAndPlannedDistanceByDate(tripDateMonths,
									tripDateMonths, assignRoutePO.geteFmFmClientBranchPO()
											.getBranchId(), assignRoutePO.getVendorId());
					log.info("Vendor wice KM:"+vehicleDetails.size());
					int plannedDistance = 0;
					int travelledDistance = 0;					
					for (EFmFmAssignRoutePO vehicle : vehicleDetails) {
						plannedDistance+=vehicle.getPlannedDistance();
						travelledDistance+=vehicle.getTravelledDistance();								
					}
					vehicleList.put("date", formatter.format(routeDetail.getTripAssignDate()));
					vehicleList.put("totalApportunity", "2729");
					vehicleList.put("plannedDistance", plannedDistance);
					vehicleList.put("travelledDistance", travelledDistance);
					vehicleList.put("vehicleType", routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel());
					vehicleList.put("vehicleNumber", routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
					vehicleList.put("vendorName", routeDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
					allVehicleDetails.add(vehicleList);
					}
//				}
				}
			}
		}
		return Response.ok(allVehicleDetails, MediaType.APPLICATION_JSON)
				.build();
	}

	/**
	 * @param Get
	 *            all trips particular vendors vehicles kilometer by selected
	 *            date range...
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/vendorvehiclekm")
	public Response getParticularVendorsAllVehiclesDetailAtGivenDateRange(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		List<Map<String, Object>> allVehicleDetails = new ArrayList<Map<String, Object>>();
		List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
				.getAllTripsTravelledAndPlannedDistanceByDate(fromDate, toDate,
						assignRoutePO.geteFmFmClientBranchPO().getBranchId(),
						assignRoutePO.getVendorId());
		if ((!(allTripDetails.isEmpty())) || allTripDetails.size() != 0) {
			for (EFmFmAssignRoutePO assignRouteDetail : allTripDetails) {
				Map<String, Object> vehicleList = new HashMap<String, Object>();
				vehicleList.put("vehicleNum", assignRouteDetail
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getVehicleNumber());
				vehicleList.put("vehicleId", assignRouteDetail
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getVehicleId());
				vehicleList.put("vendorName", assignRouteDetail
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getEfmFmVendorMaster().getVendorName());
				vehicleList.put("plannedDistance",
						assignRouteDetail.getPlannedDistance());
				try {
					if (assignRouteDetail.getTravelledDistance() == 0) {
						vehicleList.put("travelledDistance", "NA");
					} else {
						String extensionRemoved = Double.toString(
								assignRouteDetail.getTravelledDistance())
								.split("\\.")[1];
						if (!(extensionRemoved.equalsIgnoreCase("0"))) {
							vehicleList.put("travelledDistance", Math
									.round((double) assignRouteDetail
											.getTravelledDistance()));
						} else {
							vehicleList.put("travelledDistance",
									assignRouteDetail.getTravelledDistance());
						}
					}
				} catch (Exception e) {
				}
				vehicleList.put("travelledDate", formatter
						.format(assignRouteDetail.getTripAssignDate()));
				vehicleList.put("shiftTime",
						timeFormat.format(assignRouteDetail.getShiftTime()));
				vehicleList.put("tripType", assignRouteDetail.getTripType());
				allVehicleDetails.add(vehicleList);
			}
		}
		return Response.ok(allVehicleDetails, MediaType.APPLICATION_JSON)
				.build();
	}

	/**
	 * @param Get
	 *            all No show employees by selected date range...
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/noshowemp")
	public Response getAllNoShowEmployeesDetailAtGivenDateRange(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		List<Map<String, Object>> allVehicleDetails = new ArrayList<Map<String, Object>>();
		List<EFmFmEmployeeTripDetailPO> allTripDetails = iAssignRouteBO
				.getAllNoShowEmployeesByDate(fromDate, toDate, assignRoutePO
						.geteFmFmClientBranchPO().getBranchId());
		
		Map<String, Object> noShowList = new HashMap<String, Object>();
		if ((!(allTripDetails.isEmpty())) || allTripDetails.size() != 0) {
			for (EFmFmEmployeeTripDetailPO employeeDetail : allTripDetails) {
				Map<String, Object> vehicleList = new HashMap<String, Object>();
				vehicleList.put("employeeId", employeeDetail
						.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster()
						.getEmployeeId());
				vehicleList.put("employeeAddress", employeeDetail
						.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster()
						.getAddress());
				vehicleList.put("routeName", employeeDetail
						.getEfmFmAssignRoute().geteFmFmRouteAreaMapping()
						.geteFmFmZoneMaster().getZoneName());
				vehicleList.put("travelledDate",
						formatter.format(employeeDetail.getActualTime()));
				vehicleList.put("shiftTime", timeFormat.format(employeeDetail
						.getEfmFmAssignRoute().getShiftTime()));
				vehicleList.put("tripType", employeeDetail
						.getEfmFmAssignRoute().getTripType());
				allVehicleDetails.add(vehicleList);
			}
			noShowList.put("noShowCount", allTripDetails.size());
			noShowList.put("noShowEmployees", allVehicleDetails);
		}
		return Response.ok(noShowList, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * @param Get
	 *            all employees SMS report by selected date range...
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/smsreport")
	public Response getAllEmployeesSMSDetailAtGivenDateRange(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");

		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		List<Map<String, Object>> allVehicleDetails = new ArrayList<Map<String, Object>>();
		List<EFmFmEmployeeTripDetailPO> allTripDetails = iAssignRouteBO
				.getAllMessageEmployeesMessageDetailsByDate(fromDate, toDate, assignRoutePO
						.geteFmFmClientBranchPO().getBranchId());
		if ((!(allTripDetails.isEmpty())) || allTripDetails.size() != 0) {
			for (EFmFmEmployeeTripDetailPO employeeDetail : allTripDetails) {
				Map<String, Object> vehicleList = new HashMap<String, Object>();
				vehicleList.put("employeeId", employeeDetail
						.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster()
						.getEmployeeId());
				vehicleList.put("employeeAddress", employeeDetail
						.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster()
						.getAddress());
				vehicleList.put("routeName", employeeDetail
						.getEfmFmAssignRoute().geteFmFmRouteAreaMapping()
						.geteFmFmZoneMaster().getZoneName());
				vehicleList.put("employeeNumber", employeeDetail
						.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster()
						.getMobileNumber());
				vehicleList.put("travelledDate",
						formatter.format(employeeDetail.getActualTime()));
				vehicleList.put("shiftTime", timeFormat.format(employeeDetail
						.getEfmFmAssignRoute().getShiftTime()));
				vehicleList.put("tripType", employeeDetail
						.getEfmFmAssignRoute().getTripType());
				if (employeeDetail.getEfmFmAssignRoute().getAllocationMsg()
						.equalsIgnoreCase("Y")) {
					vehicleList.put("allocationMsgDeliveryDate", dateFormatter
							.format(employeeDetail.getEfmFmAssignRoute()
									.getAllocationMsgDeliveryDate()));
				} else {
					vehicleList.put("allocationMsgDeliveryDate", "NO");
				}
				if (employeeDetail.getTenMinuteMessageStatus()
						.equalsIgnoreCase("Y") && employeeDetail.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("PICKUP")) {
					vehicleList.put("eat15MinuteMsgDeliveryDate", dateFormatter
							.format(employeeDetail
									.getTenMinuteMessageDeliveryDate()));
				} else {
					vehicleList.put("eat15MinuteMsgDeliveryDate", "NO");
				}
				if (employeeDetail.getReachedFlg().equalsIgnoreCase("Y") && employeeDetail.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("PICKUP")) {
					vehicleList.put("reachedMsgDeliveryDate", dateFormatter
							.format(employeeDetail
									.getReachedMessageDeliveryDate()));
				} else {
					vehicleList.put("reachedMsgDeliveryDate", "NO");
				}
				if (employeeDetail.getCabDelayMsgStatus().equalsIgnoreCase("Y") && employeeDetail.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("PICKUP")) {
					vehicleList.put("cabDelayMsgDeliveryDate",
							dateFormatter.format(employeeDetail
									.getCabDelayMsgDeliveryDate()));
				} else {
					vehicleList.put("cabDelayMsgDeliveryDate", "NO");
				}
				allVehicleDetails.add(vehicleList);
			}
		}
		return Response.ok(allVehicleDetails, MediaType.APPLICATION_JSON)
				.build();
	}

	@POST
	@Path("/noshow")
	public Response getNoShowTripData(EFmFmAssignRoutePO assignRoutePO)
			throws ParseException {
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader
				.getContext().getBean("ICabRequestBO");
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
				.getAllTripByDate(fromDate, toDate, assignRoutePO
						.geteFmFmClientBranchPO().getBranchId());
		List<Map<String, Object>> trip = new ArrayList<Map<String, Object>>();

		List<String> dateList = new ArrayList<String>();
		String date = "";
		if ((!(allTripDetails.isEmpty())) || allTripDetails.size() != 0) {
			outer: for (EFmFmAssignRoutePO trips : allTripDetails) {
				if (!(dateList.contains(formatter.format(trips
						.getTripAssignDate())))) {
					date = formatter.format(trips.getTripAssignDate());
					dateList.add(formatter.format(trips.getTripAssignDate()));
				} else {
					continue outer;
				}
				Map<String, Object> detailTrip = new HashMap<String, Object>();
				List<Map<String, Object>> tripList = new ArrayList<Map<String, Object>>();
				for (EFmFmAssignRoutePO tripDetails : allTripDetails) {
					if (date.equalsIgnoreCase(formatter.format(tripDetails
							.getTripAssignDate()))) {
						Map<String, Object> detailTripList = new HashMap<String, Object>();
						detailTripList.put("routeId",
								tripDetails.getAssignRouteId());
						detailTripList.put("routeName", tripDetails
								.geteFmFmRouteAreaMapping()
								.geteFmFmZoneMaster().getZoneName());
						detailTripList.put("vehicleNumber", tripDetails
								.getEfmFmVehicleCheckIn()
								.getEfmFmVehicleMaster().getVehicleNumber());
						detailTripList.put("driverName", tripDetails
								.getEfmFmVehicleCheckIn()
								.getEfmFmDriverMaster().getFirstName());
						detailTripList.put("tripAssignDate", formatter
								.format(tripDetails.getTripAssignDate()));
						detailTripList.put("tripCompleteDate", dateFormatter
								.format(tripDetails.getTripAssignDate()));
						detailTripList.put("tripType",
								tripDetails.getTripType());
						detailTripList.put("ShitTime", "test");
						List<Map<String, Object>> pickupDetails = new ArrayList<Map<String, Object>>();
						int noShowCount = 0;
						// List<EFmFmEmployeeTripDetailPO>
						// employeeDetails=iCabRequestBO.getParticularTripAllEmployees(tripDetails.getAssignRouteId());
						List<EFmFmEmployeeTripDetailPO> employeeDetails = iCabRequestBO
								.getParticularTripAllEmployees(tripDetails
										.getAssignRouteId());

						if ((!(employeeDetails.isEmpty()))
								|| employeeDetails.size() != 0) {
							for (EFmFmEmployeeTripDetailPO employeeList : employeeDetails) {
								// List<EFmFmUserMasterPO>
								// employeeDetail=employeeDetailBO.getParticularEmpDetails(String.valueOf(employeeList.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId()),
								// assignRoutePO.geteFmFmClientBranchPO().getBranchId());
								Map<String, Object> empList = new HashMap<String, Object>();
								if (employeeList.getBoardedFlg().equals("NO")) {
									noShowCount++;
									empList.put("empId", employeeList
											.geteFmFmEmployeeTravelRequest()
											.getEfmFmUserMaster()
											.getEmployeeId());
									empList.put("empName", employeeList
											.geteFmFmEmployeeTravelRequest()
											.getEfmFmUserMaster()
											.getFirstName());
									if (employeeList
											.geteFmFmEmployeeTravelRequest()
											.getEfmFmUserMaster().getGender()
											.equalsIgnoreCase("Male")) {
										empList.put("empGender", 1);
									} else {
										empList.put("empGender", 2);
									}

									// empList.put("empGender",
									// employeeList.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getGender());
									empList.put("empAddress", employeeList
											.geteFmFmEmployeeTravelRequest()
											.getEfmFmUserMaster().getAddress());
									empList.put(
											"shiftTime",
											dateFormatter
													.format(employeeList
															.geteFmFmEmployeeTravelRequest()
															.getShiftTime()));
									empList.put("scheduleTime", timeFormat
											.format(employeeList
													.getActualTime()));
									empList.put("travelStatus", "noShow");
									pickupDetails.add(empList);
								}
							}

						}
						detailTripList.put("noshowCounter", noShowCount);
						detailTripList.put("empDetails", pickupDetails);
						tripList.add(detailTripList);

					}
					detailTrip.put("tripDetail", tripList);
					detailTrip.put("tripAssignDate", date);

				}
				trip.add(detailTrip);

			}
			allTrips.put("data", trip);

		}
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * driverscorecard
	 */

	@POST
	@Path("/driverscorecard")
	public Response getDriverScoreCardReports(EFmFmAssignRoutePO assignRoutePO)
			throws ParseException {
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader
				.getContext().getBean("ICabRequestBO");
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
				.getAllTripByDate(fromDate, toDate, assignRoutePO
						.geteFmFmClientBranchPO().getBranchId());
		List<Map<String, Object>> tripList = new ArrayList<Map<String, Object>>();
		if (allTripDetails.size() > 0) {
			for (EFmFmAssignRoutePO tripDetails : allTripDetails) {
				Map<String, Object> detailTripList = new HashMap<String, Object>();
				detailTripList.put("routeId", tripDetails.getAssignRouteId());
				detailTripList.put("routeName", tripDetails
						.geteFmFmRouteAreaMapping().geteFmFmZoneMaster()
						.getZoneName());
				detailTripList.put("vehicleNumber", tripDetails
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getVehicleNumber());
				detailTripList.put("driverName", tripDetails
						.getEfmFmVehicleCheckIn().getEfmFmDriverMaster()
						.getFirstName());
				detailTripList.put("tripAssignDate",
						dateFormatter.format(tripDetails.getTripAssignDate()));
				detailTripList
						.put("tripCompleteDate", dateFormatter
								.format(tripDetails.getTripCompleteTime()));
				detailTripList.put("tripType", tripDetails.getTripType());
				detailTripList.put("ShitTime", "test");
				List<Map<String, Object>> pickupDetails = new ArrayList<Map<String, Object>>();
				int noShowCount = 0;
				List<EFmFmEmployeeTripDetailPO> employeeDetails = iCabRequestBO
						.getParticularTripAllEmployees(tripDetails
								.getAssignRouteId());
				if ((!(employeeDetails.isEmpty()))
						|| employeeDetails.size() != 0) {
					for (EFmFmEmployeeTripDetailPO employeeList : employeeDetails) {
						// List<EFmFmUserMasterPO>
						// employeeDetail=employeeDetailBO.getParticularEmpDetails(employeeList.getEmployeeId(),
						// assignRoutePO.geteFmFmClientBranchPO().getBranchId());
						Map<String, Object> empList = new HashMap<String, Object>();
						if (employeeList.getBoardedFlg().equals("NO")) {
							noShowCount++;
							empList.put("empId", employeeList
									.geteFmFmEmployeeTravelRequest()
									.getEfmFmUserMaster().getEmployeeId());
							empList.put("empName", employeeList
									.geteFmFmEmployeeTravelRequest()
									.getEfmFmUserMaster().getFirstName());
							if (employeeList.geteFmFmEmployeeTravelRequest()
									.getEfmFmUserMaster().getGender()
									.equalsIgnoreCase("Male")) {
								empList.put("empGender", 1);
							} else {
								empList.put("empGender", 2);
							}

							// empList.put("empGender",
							// employeeList.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getGender());
							empList.put("empAddress", employeeList
									.geteFmFmEmployeeTravelRequest()
									.getEfmFmUserMaster().getAddress());
							empList.put("shiftTime", dateFormatter
									.format(employeeList
											.geteFmFmEmployeeTravelRequest()
											.getShiftTime()));
							empList.put("scheduleTime", timeFormat
									.format(employeeList.getActualTime()));
							empList.put("travelStatus", "noShow");
							pickupDetails.add(empList);
						}
					}

				}
				detailTripList.put("noshowCounter", noShowCount);
				detailTripList.put("empDetails", pickupDetails);
				tripList.add(detailTripList);
			}
			allTrips.put("tripDetail", tripList);

		}
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}

	// Shell new reports 18 Dec 2015

	/*
	 * On Time Arrival Reports
	 */

	@POST
	@Path("/ontimearrival")
	public Response getOnTimeArrivalReports(EFmFmAssignRoutePO assignRoutePO)
			throws ParseException {
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		log.info("Time......"+assignRoutePO.getTime());
		log.info("vendorId......"+assignRoutePO.getVendorId());
		List<Date> selectedDates = new ArrayList<Date>();
		List<Time> selectedShiftTimes = new ArrayList<Time>();
		if(assignRoutePO.getTime().equalsIgnoreCase("0") && assignRoutePO.getVendorId()==0){	
			
			
			
			selectedDates = iAssignRouteBO.getAllTripsDistinctDates(
					fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
							.getBranchId(), assignRoutePO.getTripType());
			log.info("all zero"+selectedDates.size());
			List<Map<String, Object>> onTimeReport = new ArrayList<Map<String, Object>>();
			log.info("Dates" + selectedDates.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			// if (allTripDetails.size() > 0) {
			for (Date tripDetails : selectedDates) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
				String date = formatter.format(tripDetails);
				Date tripDates = (Date) formatter.parse(date);
				onTimeReportDetail.put("tripDates", formatter.format(tripDetails));
				onTimeReportDetail.put("totalTrips", iAssignRouteBO
						.getAllTripsCountByDate(tripDates, tripDates, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType()));
				onTimeReportDetail.put("totalUsedVehicles", iAssignRouteBO
						.getAllTripsCountByDate(tripDates, tripDates, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType()));
				List<EFmFmAssignRoutePO> trips = iAssignRouteBO.getAllTripsByDate(tripDates,
						tripDates, assignRoutePO.geteFmFmClientBranchPO()
								.getBranchId(), assignRoutePO.getTripType());
				onTimeReportDetail
						.put("totalAllocatedEmployeesCount", iAssignRouteBO
								.getAllEmployeesCountByDate(tripDates, tripDates,
										assignRoutePO.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType()));

				onTimeReportDetail
						.put("totalEmployeesNoShowCount", iAssignRouteBO
								.getNoShowEmployeesCountByDate(tripDates,
										tripDates, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType()));
				onTimeReportDetail
						.put("totalEmployeesPickedDropCount", iAssignRouteBO
								.getPickedUpEmployeesCountByDate(tripDates,
										tripDates, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType()));
				
				int delayTripCount = 0,delayBeyondTimeCount = 0,onTimeCount = 0;
				DateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
				DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				
				for (EFmFmAssignRoutePO delayTrips : trips) {
					//15 minutes 	
					List<EFmFmTripTimingMasterPO> shiftDetails=iCabRequestBO.getShiftTimeDetailFromShiftTimeAndTripType(assignRoutePO
							.geteFmFmClientBranchPO()
							.getBranchId(), delayTrips.getShiftTime(), delayTrips.getTripType());	
					log.info("buffertime...."+shiftDetails.get(0).getShiftBufferTime());					
					log.info("onTime...."+shiftDetails.get(0).getShiftBufferTime());					
					long onTime = TimeUnit.SECONDS.toMillis(shiftDetails.get(0).getShiftBufferTime() * 60L);
					if (assignRoutePO.getTripType().equalsIgnoreCase("DROP")) {
						String requestDate = dateformate.format(delayTrips
								.getTripStartTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()+onTime)>=delayTrips.getTripStartTime().getTime()){
							onTimeCount++;
						}
						
						else{
							delayBeyondTimeCount++;
						}
						
					} else {
						String requestDate = dateformate.format(delayTrips
								.getTripCompleteTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()-onTime)>=delayTrips.getTripCompleteTime().getTime()){
							onTimeCount++;
						}
						else if(shiftDateAndTime.getTime()>=delayTrips.getTripCompleteTime().getTime()){
							delayBeyondTimeCount++;
						}
						else{
							delayTripCount++;
						}
						
					}

				}
				log.info("onTimeCount"+onTimeCount);
				log.info("trips.size()"+trips.size()+(onTimeCount*100)/trips.size());
				onTimeReportDetail
				.put("delayTripsPercentage",(onTimeCount*100)/trips.size());
				onTimeReportDetail
						.put("totalDelayTrips",delayTripCount);
				onTimeReportDetail.put("totalDelayTripsBeyondLogin",delayBeyondTimeCount);
				onTimeReportDetail.put("onTimeTrips",onTimeCount);
				onTimeReportDetail
				.put("vendorName", "All Vendors");
				onTimeReport.add(onTimeReportDetail);
			}
			allTrips.put("tripDetail", onTimeReport);		
		}
		//shift wise and vendor wise
		else if(!(assignRoutePO.getTime().equalsIgnoreCase("0")) && !(assignRoutePO.getVendorId()==0)){			
			if(assignRoutePO.getTime().equalsIgnoreCase("1")){
				selectedShiftTimes = iAssignRouteBO.getAllTripsByAllShiftsForVendor(
						fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
								.getBranchId(), assignRoutePO.getTripType(),assignRoutePO.getVendorId());
				log.info("Both not null"+selectedShiftTimes.size());
				List<Map<String, Object>> onTimeReport = new ArrayList<Map<String, Object>>();
				log.info("Dates" + selectedShiftTimes.size());
				log.info("From Date" + assignRoutePO.getFromDate());
				log.info("From Date" + assignRoutePO.getToDate());
				// if (allTripDetails.size() > 0) {
				for (Time shiftTimeDetails : selectedShiftTimes) {
					Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
					onTimeReportDetail.put("tripDates", shiftFormate
							.format(shiftTimeDetails));
				
					long totalUsedVehiclesVendor=iAssignRouteBO
							.getUsedFleetByByVendorIdByShift(fromDate, toDate, assignRoutePO
									.geteFmFmClientBranchPO().getBranchId(),
									assignRoutePO.getTripType(),assignRoutePO.getVendorId(),shiftTimeDetails);
					onTimeReportDetail.put("totalTrips",totalUsedVehiclesVendor);
					onTimeReportDetail.put("totalUsedVehicles",totalUsedVehiclesVendor);
					List<EFmFmAssignRoutePO> trips = iAssignRouteBO.getAllTripsDetailsByShiftByVendor(fromDate,
							toDate, assignRoutePO.geteFmFmClientBranchPO()
									.getBranchId(), assignRoutePO.getTripType(),shiftTimeDetails,assignRoutePO.getVendorId());
					onTimeReportDetail
							.put("totalAllocatedEmployeesCount", iAssignRouteBO
									.getAllEmployeesCountByShiftByVendorId(fromDate, toDate,
											assignRoutePO.geteFmFmClientBranchPO()
													.getBranchId(), assignRoutePO
													.getTripType(),shiftTimeDetails,assignRoutePO.getVendorId()));

					onTimeReportDetail
							.put("totalEmployeesNoShowCount", iAssignRouteBO
									.getNoShowEmployeesCountByShiftByVendor(fromDate,
											toDate, assignRoutePO
													.geteFmFmClientBranchPO()
													.getBranchId(), assignRoutePO
													.getTripType(),shiftTimeDetails,assignRoutePO.getVendorId()));
					onTimeReportDetail
							.put("totalEmployeesPickedDropCount", iAssignRouteBO
									.getPickedUpEmployeesCountByShiftByVendor(fromDate,
											toDate, assignRoutePO
													.geteFmFmClientBranchPO()
													.getBranchId(), assignRoutePO
													.getTripType(),shiftTimeDetails,assignRoutePO.getVendorId()));
					onTimeReportDetail
					.put("vendorName", trips.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());				
					int delayTripCount = 0,delayBeyondTimeCount = 0,onTimeCount = 0;
					DateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");				
					for (EFmFmAssignRoutePO delayTrips : trips) {
						List<EFmFmTripTimingMasterPO> shiftDetails=iCabRequestBO.getShiftTimeDetailFromShiftTimeAndTripType(assignRoutePO
								.geteFmFmClientBranchPO()
								.getBranchId(), delayTrips.getShiftTime(), delayTrips.getTripType());					
						log.info("onTime...."+shiftDetails.get(0).getShiftBufferTime());					
						long onTime = TimeUnit.SECONDS.toMillis(shiftDetails.get(0).getShiftBufferTime() * 60L);
						if (assignRoutePO.getTripType().equalsIgnoreCase("DROP")) {
							String requestDate = dateformate.format(delayTrips
									.getTripStartTime());
							String requestDateShiftTime = requestDate + " "
									+ delayTrips.getShiftTime();
							Date shiftDateAndTime = dateTimeFormate
									.parse(requestDateShiftTime);
							if((shiftDateAndTime.getTime()+onTime)>=delayTrips.getTripStartTime().getTime()){
								onTimeCount++;
							}
						
							else{
								delayBeyondTimeCount++;
							}
							
						} else {
							String requestDate = dateformate.format(delayTrips
									.getTripCompleteTime());
							String requestDateShiftTime = requestDate + " "
									+ delayTrips.getShiftTime();
							Date shiftDateAndTime = dateTimeFormate
									.parse(requestDateShiftTime);
							if((shiftDateAndTime.getTime()-onTime)>=delayTrips.getTripCompleteTime().getTime()){
								onTimeCount++;
							}
							else if(shiftDateAndTime.getTime()>=delayTrips.getTripCompleteTime().getTime()){
								delayBeyondTimeCount++;
							}
							else{
								delayTripCount++;
							}
							
						}
					}
					onTimeReportDetail
					.put("delayTripsPercentage",(onTimeCount*100)/trips.size());
					onTimeReportDetail.put("totalDelayTrips",delayTripCount);
					onTimeReportDetail.put("totalDelayTripsBeyondLogin",delayBeyondTimeCount);
					onTimeReportDetail.put("onTimeTrips",onTimeCount);
					onTimeReport.add(onTimeReportDetail);
				}
				allTrips.put("tripDetail", onTimeReport);
			
			}
			else{
			String shiftDate=assignRoutePO.getTime();
			Date shift  = (Date) shiftFormate.parse(shiftDate);				 
			java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
			selectedShiftTimes = iAssignRouteBO.getAllTripsByShiftByVendorId(
					fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
							.getBranchId(), assignRoutePO.getTripType(),assignRoutePO.getVendorId(),shiftTime);
			log.info("Both not null"+selectedShiftTimes.size());
			List<Map<String, Object>> onTimeReport = new ArrayList<Map<String, Object>>();
			log.info("Dates" + selectedShiftTimes.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			// if (allTripDetails.size() > 0) {
			for (Time shiftTimeDetails : selectedShiftTimes) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
				onTimeReportDetail.put("tripDates", shiftFormate
						.format(shiftTimeDetails));
				long totalUsedVehiclesVendor=iAssignRouteBO
						.getUsedFleetByByVendorIdByShift(fromDate, toDate, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType(),assignRoutePO.getVendorId(),shiftTimeDetails);
				onTimeReportDetail.put("totalTrips",totalUsedVehiclesVendor);
				onTimeReportDetail.put("totalUsedVehicles",totalUsedVehiclesVendor);
				
				List<EFmFmAssignRoutePO> trips = iAssignRouteBO.getAllTripsDetailsByShiftByVendor(fromDate,
						toDate, assignRoutePO.geteFmFmClientBranchPO()
								.getBranchId(), assignRoutePO.getTripType(),shiftTimeDetails,assignRoutePO.getVendorId());
				onTimeReportDetail
						.put("totalAllocatedEmployeesCount", iAssignRouteBO
								.getAllEmployeesCountByShiftByVendorId(fromDate, toDate,
										assignRoutePO.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),shiftTimeDetails,assignRoutePO.getVendorId()));

				onTimeReportDetail
						.put("totalEmployeesNoShowCount", iAssignRouteBO
								.getNoShowEmployeesCountByShiftByVendor(fromDate,
										toDate, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),shiftTimeDetails,assignRoutePO.getVendorId()));
				onTimeReportDetail
						.put("totalEmployeesPickedDropCount", iAssignRouteBO
								.getPickedUpEmployeesCountByShiftByVendor(fromDate,
										toDate, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),shiftTimeDetails,assignRoutePO.getVendorId()));
				onTimeReportDetail
				.put("vendorName", trips.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());				
				int delayTripCount = 0,delayBeyondTimeCount = 0,onTimeCount = 0;
				DateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
				DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");				
				for (EFmFmAssignRoutePO delayTrips : trips) {
					List<EFmFmTripTimingMasterPO> shiftDetails=iCabRequestBO.getShiftTimeDetailFromShiftTimeAndTripType(assignRoutePO
							.geteFmFmClientBranchPO()
							.getBranchId(), delayTrips.getShiftTime(), delayTrips.getTripType());	
					long onTime = TimeUnit.SECONDS.toMillis(shiftDetails.get(0).getShiftBufferTime() * 60L);
					if (assignRoutePO.getTripType().equalsIgnoreCase("DROP")) {
						String requestDate = dateformate.format(delayTrips
								.getTripStartTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()+onTime)>=delayTrips.getTripStartTime().getTime()){
							onTimeCount++;
						}
						else{
							delayBeyondTimeCount++;
						}
						
					} else {
						String requestDate = dateformate.format(delayTrips
								.getTripCompleteTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()-onTime)>=delayTrips.getTripCompleteTime().getTime()){
							onTimeCount++;
						}
						else if(shiftDateAndTime.getTime()>=delayTrips.getTripCompleteTime().getTime()){
							delayBeyondTimeCount++;
						}
						else{
							delayTripCount++;
						}
						
					}
				}
				onTimeReportDetail
				.put("delayTripsPercentage",(onTimeCount*100)/trips.size());
				onTimeReportDetail.put("totalDelayTrips",delayTripCount);
				onTimeReportDetail.put("totalDelayTripsBeyondLogin",delayBeyondTimeCount);
				onTimeReportDetail.put("onTimeTrips",onTimeCount);
				onTimeReport.add(onTimeReportDetail);
			}
			allTrips.put("tripDetail", onTimeReport);
		}
		}
		//shift wise
		else if(!(assignRoutePO.getTime().equalsIgnoreCase("0")) && assignRoutePO.getVendorId()==0){
			if(assignRoutePO.getTime().equalsIgnoreCase("1")){				
			selectedShiftTimes = iAssignRouteBO.getAllTripsByAllShifts(
					fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
							.getBranchId(), assignRoutePO.getTripType());
			log.info("time not 0 and vendor 0"+selectedShiftTimes.size());
			List<Map<String, Object>> onTimeReport = new ArrayList<Map<String, Object>>();
			log.info("Dates" + selectedShiftTimes.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			// if (allTripDetails.size() > 0) {
			for (Time shiftTimeDetails : selectedShiftTimes) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
				onTimeReportDetail.put("tripDates", shiftFormate
						.format(shiftTimeDetails));
				onTimeReportDetail.put("totalTrips", iAssignRouteBO
						.getAllTripsCountByShift(fromDate, toDate, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType(),shiftTimeDetails));
				onTimeReportDetail.put("totalUsedVehicles", iAssignRouteBO
						.getAllTripsCountByShift(fromDate, toDate, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType(),shiftTimeDetails));
				
				List<EFmFmAssignRoutePO> trips = iAssignRouteBO.getAllTripsDetailsByShift(fromDate,
						toDate, assignRoutePO.geteFmFmClientBranchPO()
								.getBranchId(), assignRoutePO.getTripType(),shiftTimeDetails);
				onTimeReportDetail
						.put("totalAllocatedEmployeesCount", iAssignRouteBO
								.getAllEmployeesCountByShift(fromDate, toDate,
										assignRoutePO.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),shiftTimeDetails));

				onTimeReportDetail
						.put("totalEmployeesNoShowCount", iAssignRouteBO
								.getNoShowEmployeesCountByShift(fromDate,
										toDate, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),shiftTimeDetails));
				onTimeReportDetail
						.put("totalEmployeesPickedDropCount", iAssignRouteBO
								.getPickedUpEmployeesCountByShift(fromDate,
										toDate, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),shiftTimeDetails));
				
				int delayTripCount = 0,delayBeyondTimeCount = 0,onTimeCount = 0;
				DateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
				DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");				
				for (EFmFmAssignRoutePO delayTrips : trips) {
					List<EFmFmTripTimingMasterPO> shiftDetails=iCabRequestBO.getShiftTimeDetailFromShiftTimeAndTripType(assignRoutePO
							.geteFmFmClientBranchPO()
							.getBranchId(), delayTrips.getShiftTime(), delayTrips.getTripType());	
					long onTime = TimeUnit.SECONDS.toMillis(shiftDetails.get(0).getShiftBufferTime() * 60L);
					if (assignRoutePO.getTripType().equalsIgnoreCase("DROP")) {
						String requestDate = dateformate.format(delayTrips
								.getTripStartTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()+onTime)>delayTrips.getTripStartTime().getTime()){
							onTimeCount++;
						}
						else{
							delayBeyondTimeCount++;
						}
						
					} else {
						String requestDate = dateformate.format(delayTrips
								.getTripCompleteTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()-onTime)>=delayTrips.getTripCompleteTime().getTime()){
							onTimeCount++;
						}
						else if(shiftDateAndTime.getTime()>=delayTrips.getTripCompleteTime().getTime()){
							delayBeyondTimeCount++;
						}
						else{
							delayTripCount++;
						}
						
					}

				}
				onTimeReportDetail
				.put("delayTripsPercentage",(onTimeCount*100)/trips.size());
				onTimeReportDetail.put("totalDelayTrips",delayTripCount);
				onTimeReportDetail.put("totalDelayTripsBeyondLogin",delayBeyondTimeCount);
				onTimeReportDetail.put("onTimeTrips",onTimeCount);
				onTimeReportDetail
				.put("vendorName", "All Vendors");
				onTimeReport.add(onTimeReportDetail);
			}
			allTrips.put("tripDetail", onTimeReport);
			}
			else{				
			String shiftDate=assignRoutePO.getTime();
			Date shift  = (Date) shiftFormate.parse(shiftDate);				 
			java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
			selectedShiftTimes = iAssignRouteBO.getAllTripsByShift(
					fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
							.getBranchId(), assignRoutePO.getTripType(),shiftTime);
			log.info("time not 0 and vendor 0"+selectedShiftTimes.size());
			List<Map<String, Object>> onTimeReport = new ArrayList<Map<String, Object>>();
			log.info("Dates" + selectedShiftTimes.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			// if (allTripDetails.size() > 0) {
			for (Time shiftTimeDetails : selectedShiftTimes) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
				onTimeReportDetail.put("tripDates", shiftFormate
						.format(shiftTimeDetails));
				onTimeReportDetail.put("totalTrips", iAssignRouteBO
						.getAllTripsCountByShift(fromDate, toDate, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType(),shiftTimeDetails));
				onTimeReportDetail.put("totalUsedVehicles", iAssignRouteBO
						.getAllTripsCountByShift(fromDate, toDate, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType(),shiftTimeDetails));
				
				List<EFmFmAssignRoutePO> trips = iAssignRouteBO.getAllTripsDetailsByShift(fromDate,
						toDate, assignRoutePO.geteFmFmClientBranchPO()
								.getBranchId(), assignRoutePO.getTripType(),shiftTimeDetails);
				onTimeReportDetail
						.put("totalAllocatedEmployeesCount", iAssignRouteBO
								.getAllEmployeesCountByShift(fromDate, toDate,
										assignRoutePO.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),shiftTimeDetails));

				onTimeReportDetail
						.put("totalEmployeesNoShowCount", iAssignRouteBO
								.getNoShowEmployeesCountByShift(fromDate,
										toDate, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),shiftTimeDetails));
				onTimeReportDetail
						.put("totalEmployeesPickedDropCount", iAssignRouteBO
								.getPickedUpEmployeesCountByShift(fromDate,
										toDate, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),shiftTimeDetails));
				
				int delayTripCount = 0,delayBeyondTimeCount = 0,onTimeCount = 0;
				DateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
				DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");				
				for (EFmFmAssignRoutePO delayTrips : trips) {
					List<EFmFmTripTimingMasterPO> shiftDetails=iCabRequestBO.getShiftTimeDetailFromShiftTimeAndTripType(assignRoutePO
							.geteFmFmClientBranchPO()
							.getBranchId(), delayTrips.getShiftTime(), delayTrips.getTripType());
					long onTime = TimeUnit.SECONDS.toMillis(shiftDetails.get(0).getShiftBufferTime() * 60L);
					if (assignRoutePO.getTripType().equalsIgnoreCase("DROP")) {
						String requestDate = dateformate.format(delayTrips
								.getTripStartTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()+onTime)>=delayTrips.getTripStartTime().getTime()){
							onTimeCount++;
						}
						
						else{
							delayBeyondTimeCount++;
						}
						
					} else {
						String requestDate = dateformate.format(delayTrips
								.getTripCompleteTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()-onTime)>=delayTrips.getTripCompleteTime().getTime()){
							onTimeCount++;
						}
						else if(shiftDateAndTime.getTime()>=delayTrips.getTripCompleteTime().getTime()){
							delayBeyondTimeCount++;
						}
						else{
							delayTripCount++;
						}
						
					}

				}
				onTimeReportDetail
				.put("delayTripsPercentage",(onTimeCount*100)/trips.size());
				onTimeReportDetail.put("totalDelayTrips",delayTripCount);
				onTimeReportDetail.put("totalDelayTripsBeyondLogin",delayBeyondTimeCount);
				onTimeReportDetail.put("onTimeTrips",onTimeCount);
				onTimeReportDetail
				.put("vendorName", "All Vendors");
				onTimeReport.add(onTimeReportDetail);
			}
			allTrips.put("tripDetail", onTimeReport);
		}
			
		}
		else if(assignRoutePO.getTime().equalsIgnoreCase("0") && !(assignRoutePO.getVendorId()==0)){
			selectedDates = iAssignRouteBO.getAllTripsByByVendorId(
					fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
							.getBranchId(), assignRoutePO.getTripType(),assignRoutePO.getVendorId());
			log.info("time 0 and vendor not 0"+selectedDates.size());
			List<Map<String, Object>> onTimeReport = new ArrayList<Map<String, Object>>();
			log.info("Dates" + selectedDates.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			// if (allTripDetails.size() > 0) {
			for (Date tripDetails : selectedDates) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
				String date = formatter.format(tripDetails);
				Date tripDates = (Date) formatter.parse(date);
				onTimeReportDetail.put("tripDates", formatter.format(tripDetails));
				long totalUsedVehiclesVendor=iAssignRouteBO
						.getUsedFleetByByVendorId(tripDates, tripDates, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType(),assignRoutePO.getVendorId());
				onTimeReportDetail.put("totalTrips",totalUsedVehiclesVendor);
				onTimeReportDetail.put("totalUsedVehicles",totalUsedVehiclesVendor);
				List<EFmFmAssignRoutePO> trips = iAssignRouteBO.getAllTripsDetailsByVendorWiseOnly(tripDates,
						tripDates, assignRoutePO.geteFmFmClientBranchPO()
								.getBranchId(), assignRoutePO.getTripType(),assignRoutePO.getVendorId());
				onTimeReportDetail
						.put("totalAllocatedEmployeesCount", iAssignRouteBO
								.getAllEmployeesCountByDateByVendorId(tripDates, tripDates,
										assignRoutePO.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),assignRoutePO.getVendorId()));

				onTimeReportDetail
						.put("totalEmployeesNoShowCount", iAssignRouteBO
								.getNoShowEmployeesCountByDateByVendor(tripDates,
										tripDates, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),assignRoutePO.getVendorId()));
				onTimeReportDetail
						.put("totalEmployeesPickedDropCount", iAssignRouteBO
								.getPickedUpEmployeesCountByDateByVendor(tripDates,
										tripDates, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType(),assignRoutePO.getVendorId()));			
				onTimeReportDetail
				.put("vendorName", trips.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
				int delayTripCount = 0,delayBeyondTimeCount = 0,onTimeCount = 0;
				DateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
				DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");				
				for (EFmFmAssignRoutePO delayTrips : trips) {
					List<EFmFmTripTimingMasterPO> shiftDetails=iCabRequestBO.getShiftTimeDetailFromShiftTimeAndTripType(assignRoutePO
							.geteFmFmClientBranchPO()
							.getBranchId(), delayTrips.getShiftTime(), delayTrips.getTripType());
					long onTime = TimeUnit.SECONDS.toMillis(shiftDetails.get(0).getShiftBufferTime() * 60L);
					if (assignRoutePO.getTripType().equalsIgnoreCase("DROP")) {
						String requestDate = dateformate.format(delayTrips
								.getTripStartTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()+onTime)>=delayTrips.getTripStartTime().getTime()){
							onTimeCount++;
						}
						
						else{
							delayBeyondTimeCount++;
						}
						
					} else {
						String requestDate = dateformate.format(delayTrips
								.getTripCompleteTime());
						String requestDateShiftTime = requestDate + " "
								+ delayTrips.getShiftTime();
						Date shiftDateAndTime = dateTimeFormate
								.parse(requestDateShiftTime);
						if((shiftDateAndTime.getTime()-onTime)>=delayTrips.getTripCompleteTime().getTime()){
							onTimeCount++;
						}
						else if(shiftDateAndTime.getTime()>=delayTrips.getTripCompleteTime().getTime()){
							delayBeyondTimeCount++;
						}
						else{
							delayTripCount++;
						}						
					}

				}				
				onTimeReportDetail
				.put("delayTripsPercentage",(onTimeCount*100)/trips.size());				
				onTimeReportDetail
						.put("totalDelayTrips",delayTripCount);
				onTimeReportDetail.put("totalDelayTripsBeyondLogin",delayBeyondTimeCount);
				onTimeReportDetail.put("onTimeTrips",onTimeCount);
				onTimeReport.add(onTimeReportDetail);
			}
			allTrips.put("tripDetail", onTimeReport);
		}
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Seat Utilization Reports 
	 */

	@POST
	@Path("/seatutilization")
	public Response getOnTimeArrivalReportsShiftWice(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {		
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		List<Map<String, Object>> onTimeReport = new ArrayList<Map<String, Object>>();
		List<Time> selectedShiftTimes = new ArrayList<Time>();
		log.info("time"+assignRoutePO.getTime());
		if(assignRoutePO.getTime().equalsIgnoreCase("0")){
			List<Date> selectedDates = iAssignRouteBO.getAllTripsDistinctDates(
					fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
							.getBranchId(),assignRoutePO.getTripType());
			log.info("Dates" + selectedDates.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			for (Date tripDetails : selectedDates) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
				log.info("type"+assignRoutePO.getTripType());
				onTimeReportDetail.put("tripDates", formatter.format(tripDetails));
				onTimeReportDetail.put("totalUsedVehicles", iAssignRouteBO
						.getAllTripsCountByDate(tripDetails, tripDetails, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),assignRoutePO.getTripType()));		
				long totalPickupPax=iAssignRouteBO.getPickedUpEmployeesCountByDate(tripDetails,
						tripDetails, assignRoutePO.geteFmFmClientBranchPO()
						.getBranchId(),assignRoutePO.getTripType());
				log.info("pickupCount"+totalPickupPax);
				onTimeReportDetail.put("totalEmployeesPickedDropCount",totalPickupPax);
				List<EFmFmAssignRoutePO> trips = iAssignRouteBO.getAllTripsByDate(tripDetails,
						tripDetails, assignRoutePO.geteFmFmClientBranchPO()
								.getBranchId(), assignRoutePO.getTripType());
				int count=0;
				for (EFmFmAssignRoutePO tripDetail : trips) {
					count+=tripDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getCapacity();				
				}				
				onTimeReportDetail.put("totalVehicleCapacity",count);
				
				onTimeReportDetail
				.put("utilizedSeatPercentage",(totalPickupPax*100)/count);	
				onTimeReport.add(onTimeReportDetail);
			}
		}
		else if(assignRoutePO.getTime().equalsIgnoreCase("1")){
			selectedShiftTimes = iAssignRouteBO.getAllTripsByAllShifts(
					fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
							.getBranchId(), assignRoutePO.getTripType());
			log.info("time not 0 and vendor 0"+selectedShiftTimes.size());
			log.info("Dates" + selectedShiftTimes.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			for (Time shiftTimeDetails : selectedShiftTimes) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
				onTimeReportDetail.put("tripDates", shiftFormate
						.format(shiftTimeDetails));				
				onTimeReportDetail.put("totalUsedVehicles", iAssignRouteBO
						.getAllTripsCountByShift(fromDate, toDate, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType(),shiftTimeDetails));				
				List<EFmFmAssignRoutePO> trips = iAssignRouteBO.getAllTripsDetailsByShift(fromDate,
						toDate, assignRoutePO.geteFmFmClientBranchPO()
								.getBranchId(), assignRoutePO.getTripType(),shiftTimeDetails);
				long totalPickupPax=iAssignRouteBO
						.getPickedUpEmployeesCountByShift(fromDate,
								toDate, assignRoutePO
										.geteFmFmClientBranchPO()
										.getBranchId(), assignRoutePO
										.getTripType(),shiftTimeDetails);
				onTimeReportDetail
						.put("totalEmployeesPickedDropCount",totalPickupPax);				
				int count=0;
				for (EFmFmAssignRoutePO tripDetail : trips) {
					count+=tripDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getCapacity();				
				}
				onTimeReportDetail.put("totalVehicleCapacity",count);
				onTimeReportDetail
				.put("utilizedSeatPercentage",(totalPickupPax*100)/count);	
				onTimeReport.add(onTimeReportDetail);
			}
		}
		else{	
			String shiftDate=assignRoutePO.getTime();
			Date shift  = (Date) shiftFormate.parse(shiftDate);				 
			java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
			selectedShiftTimes = iAssignRouteBO.getAllTripsByShift(
					fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
							.getBranchId(), assignRoutePO.getTripType(),shiftTime);
			log.info("time not 0 and vendor 0"+selectedShiftTimes.size());
			log.info("Dates" + selectedShiftTimes.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			for (Time shiftTimeDetails : selectedShiftTimes) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
				onTimeReportDetail.put("tripDates", shiftFormate
						.format(shiftTimeDetails));				
				onTimeReportDetail.put("totalUsedVehicles", iAssignRouteBO
						.getAllTripsCountByShift(fromDate, toDate, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType(),shiftTimeDetails));				
				List<EFmFmAssignRoutePO> trips = iAssignRouteBO.getAllTripsDetailsByShift(fromDate,
						toDate, assignRoutePO.geteFmFmClientBranchPO()
								.getBranchId(), assignRoutePO.getTripType(),shiftTimeDetails);
				long totalPickupPax=iAssignRouteBO
						.getPickedUpEmployeesCountByShift(fromDate,
								toDate, assignRoutePO
										.geteFmFmClientBranchPO()
										.getBranchId(), assignRoutePO
										.getTripType(),shiftTimeDetails);
				onTimeReportDetail
						.put("totalEmployeesPickedDropCount",totalPickupPax);				
				int count=0;
				for (EFmFmAssignRoutePO tripDetail : trips) {
					count+=tripDetail.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getCapacity();				
				}
				onTimeReportDetail.put("totalVehicleCapacity",count);
				onTimeReportDetail
				.put("utilizedSeatPercentage",(totalPickupPax*100)/count);	
				onTimeReport.add(onTimeReportDetail);
			}
		}
		allTrips.put("tripDetail", onTimeReport);
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * No Show Report as given by shell 
	 */

	@POST
	@Path("/noshowreport")
	public Response getNoShowReportsByDate(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {		
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		List<Map<String, Object>> onTimeReport = new ArrayList<Map<String, Object>>();
		List<Time> selectedShiftTimes = new ArrayList<Time>();
		log.info("time"+assignRoutePO.getTime());
		if(assignRoutePO.getTime().equalsIgnoreCase("0") && assignRoutePO.getEmployeeId().equalsIgnoreCase("0")){
			List<Date> selectedDates = iAssignRouteBO.getAllTripsDistinctDates(
					fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
							.getBranchId(),assignRoutePO.getTripType());
			log.info("Dates" + selectedDates.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			for (Date tripDates : selectedDates) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();
				onTimeReportDetail.put("tripDates", formatter.format(tripDates));
				onTimeReportDetail.put("totalUsedVehicles", iAssignRouteBO
						.getAllTripsCountByDate(tripDates, tripDates, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),
								assignRoutePO.getTripType()));
				
				long noShowCount=iAssignRouteBO
						.getNoShowEmployeesCountByDate(tripDates,
								tripDates, assignRoutePO
										.geteFmFmClientBranchPO()
										.getBranchId(), assignRoutePO
										.getTripType());
				onTimeReportDetail
						.put("totalEmployeesNoShowCount",noShowCount);
				onTimeReportDetail
						.put("totalEmployeesPickedDropCount", iAssignRouteBO
								.getPickedUpEmployeesCountByDate(tripDates,
										tripDates, assignRoutePO
												.geteFmFmClientBranchPO()
												.getBranchId(), assignRoutePO
												.getTripType()));
				if(noShowCount>0){
				onTimeReport.add(onTimeReportDetail);
			}
			}
	}
		else if(!(assignRoutePO.getTime().equalsIgnoreCase("0")) && assignRoutePO.getEmployeeId().equalsIgnoreCase("0")){
			if(assignRoutePO.getTime().equalsIgnoreCase("1")){				
				List<EFmFmEmployeeTripDetailPO> allTripDetails = iAssignRouteBO
						.getAllNoShowEmployeesByDateWise(fromDate, toDate, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),assignRoutePO.getTripType());
				log.info("time not 0 and emp 0"+selectedShiftTimes.size());
				log.info("Dates" + selectedShiftTimes.size());
				log.info("From Date" + assignRoutePO.getFromDate());
				log.info("From Date" + assignRoutePO.getToDate());
//				for (Time shiftTimeDetails : selectedShiftTimes) {
				for (EFmFmEmployeeTripDetailPO employeeDetail : allTripDetails) {
					Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();			
					onTimeReportDetail.put("tripDates", shiftFormate
							.format(employeeDetail.getEfmFmAssignRoute().getShiftTime()));	
					//totalUsedVehicles...means EmployeeId
					onTimeReportDetail.put("totalUsedVehicles", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
					//totalEmployeesNoShowCount......Means employee name
					onTimeReportDetail.put("totalEmployeesPickedDropCount", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());	
					//totalEmployeesPickedDropCount No show value
					onTimeReportDetail.put("totalEmployeesNoShowCount", employeeDetail.getBoardedFlg());	
					
					onTimeReport.add(onTimeReportDetail);
				}
			
			}
			else{			
			String shiftDate=assignRoutePO.getTime();
			Date shift  = (Date) shiftFormate.parse(shiftDate);				 
			java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
			List<EFmFmEmployeeTripDetailPO> allTripDetails = iAssignRouteBO
					.getAllNoShowEmployeesByIdAndName(fromDate, toDate, assignRoutePO
							.geteFmFmClientBranchPO().getBranchId(),assignRoutePO.getTripType(),shiftTime);
			log.info("time not 0 and emp 0"+selectedShiftTimes.size());
			log.info("Dates" + selectedShiftTimes.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
//			for (Time shiftTimeDetails : selectedShiftTimes) {
			for (EFmFmEmployeeTripDetailPO employeeDetail : allTripDetails) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();			
				onTimeReportDetail.put("tripDates", shiftFormate
						.format(employeeDetail.getEfmFmAssignRoute().getShiftTime()));	
				//totalUsedVehicles...means EmployeeId
				onTimeReportDetail.put("totalUsedVehicles", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
				//totalEmployeesNoShowCount......Means employee name
				onTimeReportDetail.put("totalEmployeesPickedDropCount", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());	
				//totalEmployeesPickedDropCount No show value
				onTimeReportDetail.put("totalEmployeesNoShowCount", employeeDetail.getBoardedFlg());	
				
				onTimeReport.add(onTimeReportDetail);
			}
		}
		}
		else if(!(assignRoutePO.getTime().equalsIgnoreCase("0")) && !(assignRoutePO.getEmployeeId().equalsIgnoreCase("0"))){			
			if(assignRoutePO.getTime().equalsIgnoreCase("1")){
				List<EFmFmEmployeeTripDetailPO> allTripDetails = iAssignRouteBO
						.getAllNoShowEmployeesByDateWise(fromDate, toDate, assignRoutePO
								.geteFmFmClientBranchPO().getBranchId(),assignRoutePO.getTripType());
				log.info("time not 0 and emp not 0"+selectedShiftTimes.size());
				log.info("Dates" + selectedShiftTimes.size());
				log.info("From Date" + assignRoutePO.getFromDate());
				log.info("From Date" + assignRoutePO.getToDate());
//				for (Time shiftTimeDetails : selectedShiftTimes) {
				for (EFmFmEmployeeTripDetailPO employeeDetail : allTripDetails) {
					Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();			
					onTimeReportDetail.put("tripDates", shiftFormate
							.format(employeeDetail.getEfmFmAssignRoute().getShiftTime()));	
					//totalUsedVehicles...means EmployeeId
					onTimeReportDetail.put("totalUsedVehicles", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
					//totalEmployeesNoShowCount......Means employee name
					onTimeReportDetail.put("totalEmployeesPickedDropCount", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());	
					//totalEmployeesPickedDropCount No show value
					onTimeReportDetail.put("totalEmployeesNoShowCount", employeeDetail.getBoardedFlg());	
					
					onTimeReport.add(onTimeReportDetail);
				}
			
			}
			else{
			String shiftDate=assignRoutePO.getTime();
			Date shift  = (Date) shiftFormate.parse(shiftDate);				 
			java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
			List<EFmFmEmployeeTripDetailPO> allTripDetails = iAssignRouteBO
					.getAllNoShowEmployeesByEmployeeId(fromDate, toDate, assignRoutePO
							.geteFmFmClientBranchPO().getBranchId(),assignRoutePO.getTripType(),shiftTime,assignRoutePO.getEmployeeId());
			log.info("time not 0 and emp not 0"+selectedShiftTimes.size());
			log.info("Dates" + selectedShiftTimes.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
//			for (Time shiftTimeDetails : selectedShiftTimes) {
			for (EFmFmEmployeeTripDetailPO employeeDetail : allTripDetails) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();			
				onTimeReportDetail.put("tripDates", shiftFormate
						.format(employeeDetail.getEfmFmAssignRoute().getShiftTime()));	
				//totalUsedVehicles...means EmployeeId
				onTimeReportDetail.put("totalUsedVehicles", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
				//totalEmployeesNoShowCount......Means employee name
				onTimeReportDetail.put("totalEmployeesPickedDropCount", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());	
				//totalEmployeesPickedDropCount No show value
				onTimeReportDetail.put("totalEmployeesNoShowCount", employeeDetail.getBoardedFlg());	
				
				onTimeReport.add(onTimeReportDetail);
			}
		}	
		}		
		else if(assignRoutePO.getTime().equalsIgnoreCase("0") && !(assignRoutePO.getEmployeeId().equalsIgnoreCase("0"))){			
			List<EFmFmEmployeeTripDetailPO> allTripDetails = iAssignRouteBO
					.getAllNoShowEmployeesByEmployeeIdDateWise(fromDate, toDate, assignRoutePO
							.geteFmFmClientBranchPO().getBranchId(),assignRoutePO.getTripType(),assignRoutePO.getEmployeeId());
			log.info("time  0 and emp not 0"+selectedShiftTimes.size());
			log.info("Dates" + selectedShiftTimes.size());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
//			for (Time shiftTimeDetails : selectedShiftTimes) {
			for (EFmFmEmployeeTripDetailPO employeeDetail : allTripDetails) {
				Map<String, Object> onTimeReportDetail = new HashMap<String, Object>();	
				
				onTimeReportDetail.put("tripDates", formatter.format(employeeDetail.getActualTime()));	
				//totalUsedVehicles...means EmployeeId
				onTimeReportDetail.put("totalUsedVehicles", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
				//totalEmployeesNoShowCount......Means employee name
				onTimeReportDetail.put("totalEmployeesPickedDropCount", employeeDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());	
				//totalEmployeesPickedDropCount No show value
				onTimeReportDetail.put("totalEmployeesNoShowCount", employeeDetail.getBoardedFlg());	
				
				onTimeReport.add(onTimeReportDetail);
			}
		
		
		}
		
				
		allTrips.put("tripDetail", onTimeReport);
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * @param Get
	 * escort Reports get all the reports which is required Escort...
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/escortReport")
	public Response getEscortRequiredReport(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	

		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");

		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		List<Map<String, Object>> escortReportList = new ArrayList<Map<String, Object>>();
		List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
					.getAllEscortRequiredTripsByDate(fromDate, toDate, assignRoutePO
							.geteFmFmClientBranchPO().getBranchId());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			if ((!(allTripDetails.isEmpty()))
					|| allTripDetails.size() != 0) {
			for (EFmFmAssignRoutePO trips : allTripDetails) {
				Map<String, Object> escortReport = new HashMap<String, Object>();
				escortReport.put("tripStartDate", dateFormatter
						.format(trips.getTripStartTime()));
				escortReport.put("tripAssignDate", dateFormatter
						.format(trips.getTripAssignDate()));
				escortReport.put("tripCompleteDate", dateFormatter
						.format(trips.getTripCompleteTime()));
				escortReport.put("tripType",trips.getTripType());
				escortReport.put("vehicleNumber",trips.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
				escortReport.put("vendorName",trips.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
				escortReport.put("driverName",trips.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
				escortReport.put("driverId",trips.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());				

				try {
					int a = trips.geteFmFmEscortCheckIn()
							.getEscortCheckInId();
					escortReport.put("escortName",trips.geteFmFmEscortCheckIn().geteFmFmEscortMaster().getFirstName());
					escortReport.put("escortId",trips.geteFmFmEscortCheckIn().geteFmFmEscortMaster().getEscortId());
				}
				catch(Exception e){
					escortReport.put("escortName","Escort Required But Not Available");
					escortReport.put("escortId",0);
				}
				escortReport.put("routeName",trips.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
				List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=null;
				if(trips.getTripType().equalsIgnoreCase("DROP")){
				    employeeTripDetailPO=iCabRequestBO.getDropTripAllSortedEmployees(trips.getAssignRouteId());
					escortReport.put("pickOrDropTime",timeFormat.format(employeeTripDetailPO.get(employeeTripDetailPO.size()-1).getCabstartFromDestination()));
				}
				else{
				    employeeTripDetailPO=iCabRequestBO.getParticularTripAllEmployees(trips.getAssignRouteId());
					escortReport.put("pickOrDropTime",timeFormat.format(employeeTripDetailPO.get(0).getCabstartFromDestination()));
				}				
				escortReport.put("employeeName",employeeTripDetailPO.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());
				escortReport.put("employeeId",employeeTripDetailPO.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
				escortReportList.add(escortReport);	
              }
			}
		allTrips.put("tripDetail", escortReportList);
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}	
	
	
	/**
	 * @param Get
	 * Get all route details start time and end time details...
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/routeWiceReport")
	public Response getRouteWiseTravelTime(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {	
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		List<Map<String, Object>> routeWiseReportList = new ArrayList<Map<String, Object>>();
		List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
				.getAllTripByDate(fromDate, toDate, assignRoutePO
						.geteFmFmClientBranchPO().getBranchId());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			if ((!(allTripDetails.isEmpty()))
					|| allTripDetails.size() != 0) {
			for (EFmFmAssignRoutePO trips : allTripDetails) {
				Map<String, Object> routeReport = new HashMap<String, Object>();
				routeReport.put("tripStartDate", dateFormatter
						.format(trips.getTripStartTime()));
				routeReport.put("tripAssignDate", dateFormatter
						.format(trips.getTripAssignDate()));
				routeReport.put("tripCompleteDate", dateFormatter
						.format(trips.getTripCompleteTime()));
				long millis=trips.getTripCompleteTime().getTime()-trips.getTripStartTime().getTime();				
				String travellHours = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
			            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
			            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
				routeReport.put("totalRouteTravelledTime",travellHours);
				routeReport.put("vehicleNumber",trips.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
				routeReport.put("vendorName",trips.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
				routeReport.put("driverName",trips.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
				routeReport.put("driverId",trips.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
//				routeReport.put("tripType",trips.getTripType());
				routeReport.put("routeName",trips.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
				routeWiseReportList.add(routeReport);	
              }
			}
		allTrips.put("tripDetail", routeWiseReportList);
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * @param Get
	 * Get all getVehicleAndDriverAttendenceReport details method will return all the checkin and Checkout details...
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/attendenceReport")
	public Response getVehicleAndDriverAttendenceReport(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {	
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		List<Map<String, Object>> attendenceReportList = new ArrayList<Map<String, Object>>();
		List<EFmFmVehicleCheckInPO> allTripDetails = iVehicleCheckInBO
				.getVehicleAndDriverAttendence(fromDate, toDate, assignRoutePO
						.geteFmFmClientBranchPO().getBranchId());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			if ((!(allTripDetails.isEmpty()))
					|| allTripDetails.size() != 0) {
			for (EFmFmVehicleCheckInPO trips : allTripDetails) {
				Map<String, Object> attendenceReport = new HashMap<String, Object>();
				attendenceReport.put("attendanceDate", dateFormatter
						.format(trips.getCheckInTime()));
				attendenceReport.put("status", "Yes");				
				attendenceReport.put("vehicleNumber",trips.getEfmFmVehicleMaster().getVehicleNumber());
				attendenceReport.put("vendorName",trips.getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
				attendenceReport.put("driverName",trips.getEfmFmDriverMaster().getFirstName());
				attendenceReport.put("driverId",trips.getEfmFmDriverMaster().getDriverId());				
				attendenceReportList.add(attendenceReport);	
              }
			}
		allTrips.put("tripDetail", attendenceReportList);
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}	
	
	
	/**
	 * @param Get
	 * Get all getVehicleAndDriverAttendenceReport details method will return all the checkin and Checkout details...
	 * Basically deriver working ours details
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/driverWorkinHoursReport")
	public Response getDriverWorkingHours(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {	
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		List<Map<String, Object>> driverWorkingReportList = new ArrayList<Map<String, Object>>();
		List<EFmFmVehicleCheckInPO> allTripDetails = iVehicleCheckInBO
				.getVehicleAndDriverAttendence(fromDate, toDate, assignRoutePO
						.geteFmFmClientBranchPO().getBranchId());
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			if ((!(allTripDetails.isEmpty()))
					|| allTripDetails.size() != 0) {
			for (EFmFmVehicleCheckInPO trips : allTripDetails) {
				Map<String, Object> driverWorkingReport = new HashMap<String, Object>();
				driverWorkingReport.put("date", dateFormatter
						.format(trips.getCheckInTime()));				
				driverWorkingReport.put("loginTime", dateFormatter
						.format(trips.getCheckInTime()));
				driverWorkingReport.put("logOutTime", dateFormatter
						.format(trips.getCheckOutTime()));
				long millis=trips.getCheckOutTime().getTime()-trips.getCheckInTime().getTime();				
				String workingHours = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
			            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
			            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
				driverWorkingReport.put("totalWorkingHours",workingHours);
				driverWorkingReport.put("vehicleNumber",trips.getEfmFmVehicleMaster().getVehicleNumber());
				driverWorkingReport.put("vendorName",trips.getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
				driverWorkingReport.put("driverName",trips.getEfmFmDriverMaster().getFirstName());
				driverWorkingReport.put("driverId",trips.getEfmFmDriverMaster().getDriverId());				              			
				driverWorkingReportList.add(driverWorkingReport);	
              }
			}
		allTrips.put("tripDetail", driverWorkingReportList);
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}	
	
	/**
	 * @param Get
	 * Get all getDriverDrivingHours details method will return all the driving hours details...
	 * Basically deriver driving hours details
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/driverDrivingHoursReport")
	public Response getDriverDrivingHours(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {	
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		List<Date> selectedDates = new ArrayList<Date>();
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());		
		selectedDates = iAssignRouteBO.getAllTripsByDistinctDates(
				fromDate, toDate, assignRoutePO.geteFmFmClientBranchPO()
						.getBranchId());
		log.info("all zero"+selectedDates.size());
		log.info("Dates" + selectedDates.size());
		log.info("From Date" + assignRoutePO.getFromDate());
		log.info("From Date" + assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		List<Map<String, Object>> allTripsDetailsData = new ArrayList<Map<String, Object>>();

		if ((!(selectedDates.isEmpty()))
				|| selectedDates.size() != 0) {		
			for (Date tripdates : selectedDates) {	
				List<Map<String, Object>> driverDrivingReportList = new ArrayList<Map<String, Object>>();
				Map<String, Object> driverReport = new HashMap<String, Object>();
			List<EFmFmAssignRoutePO> allTripDetails = iAssignRouteBO
					.getAllTripByDate(tripdates, tripdates, assignRoutePO
							.geteFmFmClientBranchPO().getBranchId());				
			log.info("From Date" + assignRoutePO.getFromDate());
			log.info("From Date" + assignRoutePO.getToDate());
			if ((!(allTripDetails.isEmpty()))
					|| allTripDetails.size() != 0) {
				long millis=0;
			for (EFmFmAssignRoutePO trips : allTripDetails) {
				Map<String, Object> driverDrivingReport = new HashMap<String, Object>();
				driverDrivingReport.put("date", dateFormatter
						.format(trips.getTripAssignDate()));
				driverDrivingReport.put("tripStartDate", dateFormatter
						.format(trips.getTripStartTime()));
				driverDrivingReport.put("tripCompleteDate", dateFormatter
						.format(trips.getTripCompleteTime()));				
				millis+=trips.getTripCompleteTime().getTime()-trips.getTripStartTime().getTime();
				driverDrivingReport.put("vehicleNumber",trips.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
				driverDrivingReport.put("vendorName",trips.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
				driverDrivingReport.put("driverName",trips.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
				driverDrivingReport.put("driverId",trips.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				driverDrivingReport.put("tripType",trips.getTripType());
				driverDrivingReport.put("routeName",trips.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());				
				driverDrivingReportList.add(driverDrivingReport);	
              }
			String travellHours = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
		            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
		            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));			
			driverReport.put("tripCount", allTripDetails.size());
			driverReport.put("totalDrivingHours",travellHours);
			driverReport.put("tripsDetails",driverDrivingReportList);
			}
			allTripsDetailsData.add(driverReport);
			}
			allTrips.put("tripDetail", allTripsDetailsData);

	}
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}	
	
	/**
	 * @param Get
	 * Get all getSpeedAlertVehicleAndVendorWise details method will return speed alert of the selected vehicle...
	 * 
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/speedReport")
	public Response getSpeedAlertVehicleAndVendorWise(
			EFmFmAssignRoutePO assignRoutePO) throws ParseException {	
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		Date fromDate = (Date) formatter.parse(assignRoutePO.getFromDate());
		Date toDate = (Date) formatter.parse(assignRoutePO.getToDate());
		Map<String, Object> allTrips = new HashMap<String, Object>();
		List<Map<String, Object>> vehicleSpeedAlertsList = new ArrayList<Map<String, Object>>();
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");			
		List<EFmFmTripAlertsPO> allSpeedAlerts=iAlertBO.getAllTripAlertsForSelectedDates(fromDate, toDate,assignRoutePO.geteFmFmClientBranchPO().getBranchId());
		log.info("From Date" + assignRoutePO.getFromDate());
		log.info("From Date" + assignRoutePO.getToDate());
		if ((!(allSpeedAlerts.isEmpty()))
					|| allSpeedAlerts.size() != 0) {
			for (EFmFmTripAlertsPO tripsAlerts : allSpeedAlerts) {
				Map<String, Object> vehicleSpeedAlerts = new HashMap<String, Object>();
				vehicleSpeedAlerts.put("dateTime", dateFormatter
						.format(tripsAlerts.getCreationTime()));							
				vehicleSpeedAlerts.put("vehicleNumber",tripsAlerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
				vehicleSpeedAlerts.put("vendorName",tripsAlerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
				vehicleSpeedAlerts.put("driverName",tripsAlerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
				vehicleSpeedAlerts.put("driverId",tripsAlerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				vehicleSpeedAlerts.put("area", tripsAlerts.getEfmFmAssignRoute().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
				vehicleSpeedAlerts.put("speed",tripsAlerts.getCurrentSpeed());
				vehicleSpeedAlertsList.add(vehicleSpeedAlerts);	
              }
			}
		allTrips.put("tripDetail", vehicleSpeedAlertsList);
		return Response.ok(allTrips, MediaType.APPLICATION_JSON).build();
	}	

}