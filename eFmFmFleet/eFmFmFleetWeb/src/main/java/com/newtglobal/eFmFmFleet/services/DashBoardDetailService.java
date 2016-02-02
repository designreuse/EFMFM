package com.newtglobal.eFmFmFleet.services;

import java.io.IOException;
import java.text.DateFormat;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.newtglobal.eFmFmFleet.business.bo.IAlertBO;
import com.newtglobal.eFmFmFleet.business.bo.IAssignRouteBO;
import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/dashboard")
@Consumes("application/json")
@Produces("application/json")
public class DashBoardDetailService {
	
	private static Log log = LogFactory.getLog(DashBoardDetailService.class);	


	@POST
	@Path("/detail")
	public Response getAllDashBoardData(EFmFmAssignRoutePO assignRoutePO){		
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		Map<String, Object>  requests = new HashMap<String, Object>();		
		assignRoutePO.setTripAssignDate(new Date());
		EFmFmClientBranchPO eFmFmClientBranch=new EFmFmClientBranchPO();				
		eFmFmClientBranch.setBranchId(assignRoutePO.geteFmFmClientBranchPO().getBranchId());
		assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranch);
		int polutionExpired=0,insuranceExpired=0,licenseExpire=0,medicalFitnessCertificateValid=0,policeVarificationValid = 0,ddTrainingValid = 0,taxCertificateValid=0,permitValid=0,vehicleMaintenance=0;
		int speedAlert=0,breakDownalert=0,accidentAlerts=0;
		EFmFmTripAlertsPO eFmFmTripAlertsPO=new EFmFmTripAlertsPO();
		eFmFmTripAlertsPO.setEfmFmAssignRoute(assignRoutePO);
		eFmFmTripAlertsPO.setCreationTime(new Date());	
		EFmFmVendorMasterPO efmFmVendorMaster=new EFmFmVendorMasterPO();
		efmFmVendorMaster.seteFmFmClientBranchPO(eFmFmClientBranch);		
		List<EFmFmVendorMasterPO> listOfVendorByVehicle=iVendorDetailsBO.getAllVendorsDetails(efmFmVendorMaster);		
		if((!(listOfVendorByVehicle.isEmpty())) || listOfVendorByVehicle.size() !=0){													
			for(EFmFmVendorMasterPO vendorList:listOfVendorByVehicle){					
				EFmFmVehicleMasterPO eFmFmVehicleMasterPO=new EFmFmVehicleMasterPO();
				efmFmVendorMaster.setVendorId(vendorList.getVendorId());
				eFmFmVehicleMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
				List<EFmFmVehicleMasterPO> listOfDriver=iVehicleCheckInBO.getAllVehicleDetails(eFmFmVehicleMasterPO);
				for(EFmFmVehicleMasterPO vehicleDetails:listOfDriver){
					Date TodayDate=new Date();	
					if(vehicleDetails.getStatus().equalsIgnoreCase("A")|| vehicleDetails.getStatus().equalsIgnoreCase("allocated")){						
						if(vehicleDetails.getPolutionValid().before(TodayDate)){												
							polutionExpired++;
						}else{
							int diffInDays = (int) ((vehicleDetails.getPolutionValid().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
							if(diffInDays<30){													
								polutionExpired++;
							}												
						}
						if(vehicleDetails.getInsuranceValidDate().before(TodayDate)){												
							insuranceExpired++;
						}else{
							int diffInDays = (int) ((vehicleDetails.getInsuranceValidDate().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
							if(diffInDays<30){													
								insuranceExpired++;
							}												
						}
						//Tax valid Date
						if(vehicleDetails.getTaxCertificateValid().before(TodayDate)){												
							taxCertificateValid++;
						}else{
							int diffInDays = (int) ((vehicleDetails.getTaxCertificateValid().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
							if(diffInDays<30){													
								taxCertificateValid++;
							}												
						}
						//Permit valid Date
						if(vehicleDetails.getPermitValidDate().before(TodayDate)){												
							permitValid++;
						}else{
							int diffInDays = (int) ((vehicleDetails.getPermitValidDate().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
							if(diffInDays<30){													
								permitValid++;
							}												
						}
						//VehicleMaitenance valid Date
						if(vehicleDetails.getVehicleFitnessDate().before(TodayDate)){												
							vehicleMaintenance++;
						}else{
							int diffInDays = (int) ((vehicleDetails.getVehicleFitnessDate().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
							if(diffInDays<30){													
								vehicleMaintenance++;
							}												
						}
						
					}
				}										
			}
		}

		if((!(listOfVendorByVehicle.isEmpty())) || listOfVendorByVehicle.size() !=0){			
			for(EFmFmVendorMasterPO vendorList:listOfVendorByVehicle){				
				EFmFmDriverMasterPO eFmFmDriverMasterPO=new EFmFmDriverMasterPO();
				efmFmVendorMaster.setVendorId(vendorList.getVendorId());
				eFmFmDriverMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
				List<EFmFmDriverMasterPO> listOfDriver=iVehicleCheckInBO.getAllDriverDetails(eFmFmDriverMasterPO);	
				for(EFmFmDriverMasterPO driverDetails:listOfDriver){				
					Date TodayDate=new Date();
					if(driverDetails.getStatus().equalsIgnoreCase("A") || driverDetails.getStatus().equalsIgnoreCase("allocated")){
						if(driverDetails.getLicenceValid().before(TodayDate)){
							licenseExpire++;
						}else{
							int diffInDays = (int) ((driverDetails.getLicenceValid().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
							if(diffInDays<30){													
								licenseExpire++;
							}												
						}
						if(driverDetails.getMedicalFitnessCertificateValid().before(TodayDate)){
							medicalFitnessCertificateValid++;
						}else{
							int diffInDays = (int) ((driverDetails.getMedicalFitnessCertificateValid().getTime()- TodayDate.getTime()) / (1000 * 60 * 60 * 24));
							if(diffInDays<30){													
								medicalFitnessCertificateValid++;
							}												
						}
						//Police Verification Valid
						if(driverDetails.getPoliceVerificationValid().before(TodayDate)){
							policeVarificationValid++;
						}else{
							int diffInDays = (int) ((driverDetails.getPoliceVerificationValid().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
							if(diffInDays<30){													
								policeVarificationValid++;
							}												
						}
						//DD Training Valid
						if(driverDetails.getDdtValidDate().before(TodayDate)){
							ddTrainingValid++;
						}else{
							int diffInDays = (int) ((driverDetails.getDdtValidDate().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
							if(diffInDays<30){													
								ddTrainingValid++;
							}												
						}

					}
				}	


			}
		}	
		/*
		 * Governance Alerts Details 
		 */		
		eFmFmTripAlertsPO.setEfmFmAssignRoute(assignRoutePO);								
		/*List<EFmFmTripAlertsPO> governanceAlerts=iAlertBO.getAllTripAlerts(eFmFmTripAlertsPO);
		Date TodayDate=new Date();
		if(governanceAlerts.size()!=0 || (!(governanceAlerts.isEmpty()))){
			for(EFmFmTripAlertsPO alerts:governanceAlerts){			
				int diffInDays = (int) ((alerts.getCreationTime().getTime()-TodayDate.getTime()) / (1000 * 60 * 60 * 24));
				if(diffInDays<30){	
					if(alerts.getEfmFmAlertTypeMaster().getAlertId()==8){
						accidentAlerts++;
					}
					if(alerts.getEfmFmAlertTypeMaster().getAlertId()==9){
						breakDownalert++;
					}
					if(alerts.getEfmFmAlertTypeMaster().getAlertId()==10){
						speedAlert++;
					}
				}
			}
		}	*/
		requests.put("noShowGuests", iCabRequestBO.getAllActiveNoShowGuestRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("numberOfGuestRequest", iCabRequestBO.getAllActiveDropOrPickupRequestCounterForGuest(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("boardedGuest", iCabRequestBO.getAllActiveBoardedGuestRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId())); 
		requests.put("tripScheduledForGuest",iCabRequestBO.getAllScheduleActiveRequestsForGuest(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));		


		requests.put("vehiclesInTrip",assignRouteBO.getAllVehiclesOnRoadCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));

		requests.put("numberOfPickUpRequest", iCabRequestBO.getAllActivePickUpRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("boardedEmployee", iCabRequestBO.getAllActiveBoardedEmployeeRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId())); //employee picked in pickup tab
		requests.put("pickUpScheduled",iCabRequestBO.getAllPickupScheduleActiveRequests(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));		
		requests.put("noShowPickUp", iCabRequestBO.getAllActiveNoShowEmployeeRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("pickUpInProgress", iCabRequestBO.getAllActivePickupInProgressEmployeeRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));

		requests.put("numberOfDropRequest", iCabRequestBO.getAllActiveDropRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("dropedEmployee", iCabRequestBO.getAllActiveDropedEmployeeRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("noShowDrop", iCabRequestBO.getAllActiveDropNoShowEmployeeRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("dropScheduled",iCabRequestBO.getAllDropScheduleActiveRequests(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));		
		requests.put("dropInProgress", iCabRequestBO.getAllActiveDropInProgressEmployeeRequestCounter(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		
		requests.put("roadAlerts",iAlertBO.getAllTodaysTripRoadAlertsCount(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("sosAlerts", iAlertBO.getAllTodaysTripSOSAlertsCount(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("openAlerts", iAlertBO.getAllTodaysTripOpenAlertsCount(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));
		requests.put("closeAlerts", iAlertBO.getAllTodaysTripCloseAlertsCount(assignRoutePO.geteFmFmClientBranchPO().getBranchId()));

		requests.put("insuranceExpired", insuranceExpired);		
		requests.put("polutionExpired", polutionExpired);
		requests.put("breakDownalert", breakDownalert);		
		requests.put("licenseExpire", licenseExpire);		
		requests.put("medicalFitnessCertificateValid", medicalFitnessCertificateValid);			
		requests.put("policeVarificationValid", policeVarificationValid);		
		requests.put("ddTrainingValid", ddTrainingValid);		
		requests.put("taxCertificateValid", taxCertificateValid);		
		requests.put("permitValid", permitValid);	
		requests.put("vehicleMaintenance", vehicleMaintenance);		

		
		
		requests.put("speedAlert", speedAlert);		
		requests.put("accidentAlerts", accidentAlerts);	
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}




	@POST
	@Path("/getDashBoardDetailList")
	public Response getDashBoardDetailList(EFmFmEmployeeTravelRequestPO travelRequestPO){	    	
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");
		String tripType=travelRequestPO.getTripType();
		String activityType=travelRequestPO.getRequestStatus();	    	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");	
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");
		List<Map<String,Object>> dashBoardActivity = new ArrayList<Map<String,Object>>();
		EFmFmClientBranchPO eFmFmClientBranch=new EFmFmClientBranchPO();			
		eFmFmClientBranch.setBranchId(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());			
		travelRequestPO.setRequestDate(new Date());
		EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
		assignRoutePO.setTripAssignDate(new Date());
		assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranch);
		EFmFmUserMasterPO eFmFmUserMasterPO=new EFmFmUserMasterPO();
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		EFmFmVendorMasterPO efmFmVendorMaster=new EFmFmVendorMasterPO();
		efmFmVendorMaster.seteFmFmClientBranchPO(eFmFmClientBranch);
		eFmFmUserMasterPO.seteFmFmClientBranchPO(eFmFmClientBranch);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
		DateFormat  dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");


		if(tripType.toUpperCase().equalsIgnoreCase("EMPLOYEESTATUS")){
		}					
		switch (tripType.toUpperCase()) {			
		case "EMPLOYEESTATUS":						
			switch (activityType.toLowerCase()) {						
			case "pickuprequest":	
				List<EFmFmEmployeeTravelRequestPO> employeesPickupRequestDetails=iCabRequestBO.getAllActivePickUpRequestDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if((!(employeesPickupRequestDetails.isEmpty())) || employeesPickupRequestDetails.size() !=0){			
					for(EFmFmEmployeeTravelRequestPO allTravelRequest:employeesPickupRequestDetails){									
						Map<String, Object>  requestList= new HashMap<String, Object>();				
						requestList.put("employeeId", allTravelRequest.getEfmFmUserMaster().getEmployeeId());
						requestList.put("requestId", allTravelRequest.getRequestId());
						requestList.put("tripType", allTravelRequest.getTripType());
						requestList.put("tripTime", timeFormat.format(allTravelRequest.getShiftTime()));
						requestList.put("employeeName", allTravelRequest.getEfmFmUserMaster().getFirstName());
						requestList.put("employeeAddress", allTravelRequest.getEfmFmUserMaster().getAddress());
						if(allTravelRequest.getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							requestList.put("gender", 1);	
						}
						else{
							requestList.put("gender", 2);	
						}
						requestList.put("gender", 1);
						dashBoardActivity.add(requestList);
					}									
				}						
				break;
			case "droprequest":	
				List<EFmFmEmployeeTravelRequestPO> employeesDropRequestDetails=iCabRequestBO.getAllActiveDropRequestDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if((!(employeesDropRequestDetails.isEmpty())) || employeesDropRequestDetails.size() !=0){			
					for(EFmFmEmployeeTravelRequestPO allTravelRequest:employeesDropRequestDetails){
					  //if(allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType().equalsIgnoreCase("DROP") && allTravelRequest.getIsActive().equalsIgnoreCase("Y")){										
						Map<String, Object>  requestList= new HashMap<String, Object>();		
						requestList.put("employeeId", allTravelRequest.getEfmFmUserMaster().getEmployeeId());
						requestList.put("requestId", allTravelRequest.getRequestId());
						requestList.put("tripType", allTravelRequest.getTripType());
						requestList.put("tripTime", timeFormat.format(allTravelRequest.getShiftTime()));
						requestList.put("employeeName", allTravelRequest.getEfmFmUserMaster().getFirstName());
						requestList.put("employeeAddress", allTravelRequest.getEfmFmUserMaster().getAddress());
						if(allTravelRequest.getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							requestList.put("gender", 1);	
						}
						else{
							requestList.put("gender", 2);	
						}
						requestList.put("gender", 1);
						dashBoardActivity.add(requestList);
					}									
				}
				break;
			case "pickupschedule":
				List<EFmFmEmployeeTravelRequestPO> pickupScheduleEmployees=iCabRequestBO.getAllPickupScheduleActiveRequestsDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(pickupScheduleEmployees.size()!=0 || (!(pickupScheduleEmployees.isEmpty()))){
					for(EFmFmEmployeeTravelRequestPO travelRequest:pickupScheduleEmployees){
						Map<String, Object>  requestList= new HashMap<String, Object>();											
						requestList.put("employeeId", travelRequest.getEfmFmUserMaster().getEmployeeId());
						requestList.put("requestId", travelRequest.getRequestId());
						requestList.put("tripType", travelRequest.getTripType());
						requestList.put("tripTime", timeFormat.format(travelRequest.getShiftTime()));
						requestList.put("employeeName", travelRequest.getEfmFmUserMaster().getFirstName());
						requestList.put("employeeAddress",travelRequest.getEfmFmUserMaster().getAddress());
						if(travelRequest.getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							requestList.put("gender", 1);	
						}else{
							requestList.put("gender", 2);	
						}
						dashBoardActivity.add(requestList);
					}	
				}
				break;
			case "dropschedule":									
				List<EFmFmEmployeeTravelRequestPO> dropScheduleEmployees=iCabRequestBO.getAllDropScheduleActiveRequestsDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(dropScheduleEmployees.size()!=0 || (!(dropScheduleEmployees.isEmpty()))){
					for(EFmFmEmployeeTravelRequestPO travelRequest:dropScheduleEmployees){
						Map<String, Object>  requestList= new HashMap<String, Object>();											
						requestList.put("employeeId", travelRequest.getEfmFmUserMaster().getEmployeeId());
						requestList.put("requestId", travelRequest.getRequestId());
						requestList.put("tripType", travelRequest.getTripType());
						requestList.put("tripTime", timeFormat.format(travelRequest.getShiftTime()));
						requestList.put("employeeName",travelRequest.getEfmFmUserMaster().getFirstName());
						requestList.put("employeeAddress",travelRequest.getEfmFmUserMaster().getAddress());
						if(travelRequest.getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							requestList.put("gender", 1);	
						}else{
							requestList.put("gender", 2);	
						}
						dashBoardActivity.add(requestList);
					}	
				}
				break;
			case "guestrequests":	
				List<EFmFmEmployeeTravelRequestPO> guestRequestDetails=iCabRequestBO.getAllActiveGuestRequestsDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if((!(guestRequestDetails.isEmpty())) || guestRequestDetails.size() !=0){			
					for(EFmFmEmployeeTravelRequestPO allTravelRequest:guestRequestDetails){
					  //if(allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType().equalsIgnoreCase("DROP") && allTravelRequest.getIsActive().equalsIgnoreCase("Y")){										
						Map<String, Object>  requestList= new HashMap<String, Object>();		
						requestList.put("employeeId", allTravelRequest.getEfmFmUserMaster().getEmployeeId());
						requestList.put("tripType", allTravelRequest.getTripType());
						requestList.put("requestId", allTravelRequest.getRequestId());
						requestList.put("tripType", allTravelRequest.getTripType());
						requestList.put("tripTime", timeFormat.format(allTravelRequest.getShiftTime()));
						requestList.put("employeeName", allTravelRequest.getEfmFmUserMaster().getFirstName());
						requestList.put("employeeAddress", allTravelRequest.getEfmFmUserMaster().getAddress());
						if(allTravelRequest.getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							requestList.put("gender", 1);	
						}
						else{
							requestList.put("gender", 2);	
						}
						dashBoardActivity.add(requestList);
					}									
				}
				break;
				
			case "guestschedulerequests":									
				List<EFmFmEmployeeTravelRequestPO> guestRequests=iCabRequestBO.getAllScheduleActiveGuestRequestsDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(guestRequests.size()!=0 || (!(guestRequests.isEmpty()))){
					for(EFmFmEmployeeTravelRequestPO travelRequest:guestRequests){
						Map<String, Object>  requestList= new HashMap<String, Object>();											
						requestList.put("employeeId", travelRequest.getEfmFmUserMaster().getEmployeeId());
						requestList.put("tripType", travelRequest.getTripType());
						requestList.put("requestId", travelRequest.getRequestId());
						requestList.put("tripType", travelRequest.getTripType());
						requestList.put("tripTime", timeFormat.format(travelRequest.getShiftTime()));
						requestList.put("employeeName",travelRequest.getEfmFmUserMaster().getFirstName());
						requestList.put("employeeAddress",travelRequest.getEfmFmUserMaster().getAddress());
						if(travelRequest.getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							requestList.put("gender", 1);	
						}else{
							requestList.put("gender", 2);	
						}
						dashBoardActivity.add(requestList);
					}	
				}
				break;
			case "guestboardedordropped":
				List<EFmFmEmployeeTripDetailPO> boardedOrDroppedRequestDetails=iCabRequestBO.getAllActiveBoardedOrDroppedEmployeeRequestsDetailsForGuest(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(boardedOrDroppedRequestDetails.size()!=0 || (!(boardedOrDroppedRequestDetails.isEmpty()))){
					for(EFmFmEmployeeTripDetailPO employeeDetails:boardedOrDroppedRequestDetails){
						Map<String,Object> activity=new HashMap<>();
						activity.put("requestId", employeeDetails.geteFmFmEmployeeTravelRequest().getRequestId());
						activity.put("tripType", employeeDetails.geteFmFmEmployeeTravelRequest().getTripType());
						activity.put("tripTime", employeeDetails.geteFmFmEmployeeTravelRequest().getShiftTime());
						activity.put("employeeId", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
						activity.put("employeeName", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());
						activity.put("employeeAddress", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getAddress());
						if(employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							activity.put("gender", 1); 
						}
						else{
							activity.put("gender", 2); 
						}
						dashBoardActivity.add(activity);
					}
				}
				break;
				
			case "guestnoshow":
				List<EFmFmEmployeeTripDetailPO> guestNoShowRequestDetails=iCabRequestBO.getAllActiveGuestNoShowRequestsDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(guestNoShowRequestDetails.size()!=0 || (!(guestNoShowRequestDetails.isEmpty()))){
					for(EFmFmEmployeeTripDetailPO employeeDetails:guestNoShowRequestDetails){
						Map<String,Object> activity=new HashMap<>();
						activity.put("requestId", employeeDetails.geteFmFmEmployeeTravelRequest().getRequestId());
						activity.put("tripType", employeeDetails.geteFmFmEmployeeTravelRequest().getTripType());
						activity.put("tripTime", employeeDetails.geteFmFmEmployeeTravelRequest().getShiftTime());
						activity.put("employeeId", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
						activity.put("employeeName", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());
						activity.put("employeeAddress", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getAddress());
						if(employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							activity.put("gender", 1); 
						}
						else{
							activity.put("gender", 2); 
						}
						dashBoardActivity.add(activity);
					}
				}
				break;	
				
			case "pickupboarded":
				List<EFmFmEmployeeTripDetailPO> pickupBoardedRequestDetails=iCabRequestBO.getAllActivePickupBoardedEmployeeRequestsDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(pickupBoardedRequestDetails.size()!=0 || (!(pickupBoardedRequestDetails.isEmpty()))){
					for(EFmFmEmployeeTripDetailPO employeeDetails:pickupBoardedRequestDetails){
						Map<String,Object> activity=new HashMap<>();
						activity.put("requestId", employeeDetails.geteFmFmEmployeeTravelRequest().getRequestId());
						activity.put("tripTime", employeeDetails.geteFmFmEmployeeTravelRequest().getShiftTime());
						activity.put("employeeId", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
						activity.put("employeeName", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());
						activity.put("employeeAddress", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getAddress());
						if(employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							activity.put("gender", 1); 
						}
						else{
							activity.put("gender", 2); 
						}
						dashBoardActivity.add(activity);
					}
				}
				break;
			case "dropboarded":										
				List<EFmFmEmployeeTripDetailPO> dropBoardedRequestDetails=iCabRequestBO.getAllActiveDropedEmployeeRequestsDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(dropBoardedRequestDetails.size()!=0 || (!(dropBoardedRequestDetails.isEmpty()))){
					for(EFmFmEmployeeTripDetailPO employeeDetails:dropBoardedRequestDetails){
						Map<String,Object> activity=new HashMap<>();
						activity.put("requestId", employeeDetails.geteFmFmEmployeeTravelRequest().getRequestId());
						activity.put("tripTime", employeeDetails.geteFmFmEmployeeTravelRequest().getShiftTime());
						activity.put("employeeId", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
						activity.put("employeeName", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());
						activity.put("employeeAddress", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getAddress());
						if(employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							activity.put("gender", 1); 
						}
						else{
							activity.put("gender", 2); 
						}
						dashBoardActivity.add(activity);
					}
				}
				break;
			case "noshowpickup":
				List<EFmFmEmployeeTripDetailPO> noShowPickupRequestDetails=iCabRequestBO.getAllActivePickupNoShowEmployeeRequestsDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(noShowPickupRequestDetails.size()!=0 || (!(noShowPickupRequestDetails.isEmpty()))){
					for(EFmFmEmployeeTripDetailPO employeeDetails:noShowPickupRequestDetails){
						Map<String,Object> activity=new HashMap<>();
						activity.put("requestId", employeeDetails.geteFmFmEmployeeTravelRequest().getRequestId());
						activity.put("tripTime", employeeDetails.geteFmFmEmployeeTravelRequest().getShiftTime());
						activity.put("employeeId", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
						activity.put("employeeName", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());
						activity.put("employeeAddress", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getAddress());
						if(employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							activity.put("gender", 1); 
						}
						else{
							activity.put("gender", 2); 
						}
						dashBoardActivity.add(activity);
					}
				}
				break;							   
			case "noshowdrop":	
				List<EFmFmEmployeeTripDetailPO> noShowDropRequestDetails=iCabRequestBO.getAllActiveDropNoShowEmployeeRequestsDetails(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(noShowDropRequestDetails.size()!=0 || (!(noShowDropRequestDetails.isEmpty()))){
					for(EFmFmEmployeeTripDetailPO employeeDetails:noShowDropRequestDetails){
						Map<String,Object> activity=new HashMap<>();
						activity.put("requestId", employeeDetails.geteFmFmEmployeeTravelRequest().getRequestId());
						activity.put("tripTime", employeeDetails.geteFmFmEmployeeTravelRequest().getShiftTime());
						activity.put("employeeId", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
						activity.put("employeeName", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());
						activity.put("employeeAddress", employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getAddress());
						if(employeeDetails.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
							activity.put("gender", 1); 
						}
						else{
							activity.put("gender", 2); 
						}
						dashBoardActivity.add(activity);
					}
				}
				break;		
			}
			break;
		case "VEHICLESTATUS":
			break;
		case "GOVERNANCEDRIVERS":				
			List<EFmFmVendorMasterPO> listOfVendorByDriver=iVendorDetailsBO.getAllVendorsDetails(efmFmVendorMaster);
			switch (activityType.toLowerCase()) {	
			case "policevarificationexp":																			
				if((!(listOfVendorByDriver.isEmpty())) || listOfVendorByDriver.size() !=0){													
					for(EFmFmVendorMasterPO vendorList:listOfVendorByDriver){										
						EFmFmDriverMasterPO eFmFmDriverMasterPO=new EFmFmDriverMasterPO();
						efmFmVendorMaster.setVendorId(vendorList.getVendorId());
						eFmFmDriverMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
						List<EFmFmDriverMasterPO> listOfDriver=iVehicleCheckInBO.getAllDriverDetails(eFmFmDriverMasterPO);		
						for(EFmFmDriverMasterPO driverDetails:listOfDriver){											
							Map<String,Object> driverMaster=new HashMap<>();
							Date TodayDate=new Date();
							if(driverDetails.getStatus().equalsIgnoreCase("A") || driverDetails.getStatus().equalsIgnoreCase("allocated")){
								if(driverDetails.getPoliceVerificationValid().before(TodayDate)){
									driverMaster.put("driverId",driverDetails.getDriverId());
									driverMaster.put("driverName",driverDetails.getFirstName());
									driverMaster.put("mobileNumber",driverDetails.getMobileNumber());
									driverMaster.put("licenceNumber",driverDetails.getLicenceNumber());	
									driverMaster.put("vendorId",vendorList.getVendorId());
									driverMaster.put("vendorName",vendorList.getVendorName());
									driverMaster.put("vendorContactName",vendorList.getVendorContactName());
									driverMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
									driverMaster.put("policeExpDate",dateFormat.format(driverDetails.getPoliceVerificationValid()));
									dashBoardActivity.add(driverMaster);
								}else{
									int diffInDays = (int) ((driverDetails.getPoliceVerificationValid().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
									if(diffInDays<30){													
										driverMaster.put("driverId",driverDetails.getDriverId());
										driverMaster.put("driverName",driverDetails.getFirstName());													
										driverMaster.put("licenceNumber",driverDetails.getLicenceNumber());
										driverMaster.put("mobileNumber",driverDetails.getMobileNumber());
										driverMaster.put("vendorId",vendorList.getVendorId());
										driverMaster.put("vendorName",vendorList.getVendorName());
										driverMaster.put("vendorContactName",vendorList.getVendorContactName());
										driverMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
										driverMaster.put("policeExpDate",dateFormat.format(driverDetails.getPoliceVerificationValid()));
										dashBoardActivity.add(driverMaster);
									}												
								}											

							}
						}							
					}
				}	
				break;
			case "licenseexpire":																			
				if((!(listOfVendorByDriver.isEmpty())) || listOfVendorByDriver.size() !=0){													
					for(EFmFmVendorMasterPO vendorList:listOfVendorByDriver){										
						EFmFmDriverMasterPO eFmFmDriverMasterPO=new EFmFmDriverMasterPO();
						efmFmVendorMaster.setVendorId(vendorList.getVendorId());
						eFmFmDriverMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
						List<EFmFmDriverMasterPO> listOfDriver=iVehicleCheckInBO.getAllDriverDetails(eFmFmDriverMasterPO);		
						for(EFmFmDriverMasterPO driverDetails:listOfDriver){											
							Map<String,Object> driverMaster=new HashMap<>();
							Date TodayDate=new Date();
							if(driverDetails.getStatus().equalsIgnoreCase("A") || driverDetails.getStatus().equalsIgnoreCase("allocated")){
								if(driverDetails.getLicenceValid().before(TodayDate)){
									driverMaster.put("driverId",driverDetails.getDriverId());
									driverMaster.put("driverName",driverDetails.getFirstName());
									driverMaster.put("mobileNumber",driverDetails.getMobileNumber());
									driverMaster.put("licenceNumber",driverDetails.getLicenceNumber());	
									driverMaster.put("vendorId",vendorList.getVendorId());
									driverMaster.put("vendorName",vendorList.getVendorName());
									driverMaster.put("vendorContactName",vendorList.getVendorContactName());
									driverMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
									driverMaster.put("licenceExpDate",dateFormat.format(driverDetails.getLicenceValid()));
									dashBoardActivity.add(driverMaster);
								}else{
									int diffInDays = (int) ((driverDetails.getLicenceValid().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
									if(diffInDays<30){													
										driverMaster.put("driverId",driverDetails.getDriverId());
										driverMaster.put("driverName",driverDetails.getFirstName());													
										driverMaster.put("licenceNumber",driverDetails.getLicenceNumber());
										driverMaster.put("mobileNumber",driverDetails.getMobileNumber());
										driverMaster.put("vendorId",vendorList.getVendorId());
										driverMaster.put("vendorName",vendorList.getVendorName());
										driverMaster.put("vendorContactName",vendorList.getVendorContactName());
										driverMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
										driverMaster.put("licenceExpDate",dateFormat.format(driverDetails.getLicenceValid()));
										dashBoardActivity.add(driverMaster);
									}												
								}											

							}
						}							
					}
				}	
				break;
			case "ddtrainingexp":																			
				if((!(listOfVendorByDriver.isEmpty())) || listOfVendorByDriver.size() !=0){	
					for(EFmFmVendorMasterPO vendorList:listOfVendorByDriver){										
						EFmFmDriverMasterPO eFmFmDriverMasterPO=new EFmFmDriverMasterPO();
						efmFmVendorMaster.setVendorId(vendorList.getVendorId());
						eFmFmDriverMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
						List<EFmFmDriverMasterPO> listOfDriver=iVehicleCheckInBO.getAllDriverDetails(eFmFmDriverMasterPO);	
						for(EFmFmDriverMasterPO driverDetails:listOfDriver){											
							Map<String,Object> driverMaster=new HashMap<>();
							Date TodayDate=new Date();
							if(driverDetails.getStatus().equalsIgnoreCase("A") || driverDetails.getStatus().equalsIgnoreCase("allocated")){
								if(driverDetails.getDdtValidDate().before(TodayDate)){
									driverMaster.put("driverId",driverDetails.getDriverId());
									driverMaster.put("driverName",driverDetails.getFirstName());
									driverMaster.put("mobileNumber",driverDetails.getMobileNumber());
									driverMaster.put("licenceNumber",driverDetails.getLicenceNumber());	
									driverMaster.put("vendorId",vendorList.getVendorId());
									driverMaster.put("vendorName",vendorList.getVendorName());
									driverMaster.put("vendorContactName",vendorList.getVendorContactName());
									driverMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
									driverMaster.put("ddExpDate",dateFormat.format(driverDetails.getDdtValidDate()));
									dashBoardActivity.add(driverMaster);
								}else{
									int diffInDays = (int) ((driverDetails.getDdtValidDate().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
									if(diffInDays<30){													
										driverMaster.put("driverId",driverDetails.getDriverId());
										driverMaster.put("driverName",driverDetails.getFirstName());													
										driverMaster.put("licenceNumber",driverDetails.getLicenceNumber());
										driverMaster.put("mobileNumber",driverDetails.getMobileNumber());
										driverMaster.put("vendorId",vendorList.getVendorId());
										driverMaster.put("vendorName",vendorList.getVendorName());
										driverMaster.put("vendorContactName",vendorList.getVendorContactName());
										driverMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
										driverMaster.put("ddExpDate",dateFormat.format(driverDetails.getDdtValidDate()));
										dashBoardActivity.add(driverMaster);
									}												
								}											

							}
						}							
					}
				}	
				break;
			case "medicalexpire":																			
				if((!(listOfVendorByDriver.isEmpty())) || listOfVendorByDriver.size() !=0){													
					for(EFmFmVendorMasterPO vendorList:listOfVendorByDriver){	
						EFmFmDriverMasterPO eFmFmDriverMasterPO=new EFmFmDriverMasterPO();
						efmFmVendorMaster.setVendorId(vendorList.getVendorId());
						eFmFmDriverMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
						List<EFmFmDriverMasterPO> listOfVehicle=iVehicleCheckInBO.getAllDriverDetails(eFmFmDriverMasterPO);	
						for(EFmFmDriverMasterPO driverDetails:listOfVehicle){											
							Map<String,Object> driverMaster=new HashMap<>();
							Date TodayDate=new Date();
							if(driverDetails.getStatus().equalsIgnoreCase("A") || driverDetails.getStatus().equalsIgnoreCase("allocated")){
								if(driverDetails.getMedicalFitnessCertificateValid().before(TodayDate)){
									driverMaster.put("driverId",driverDetails.getDriverId());
									driverMaster.put("driverName",driverDetails.getFirstName());
									driverMaster.put("mobileNumber",driverDetails.getMobileNumber());
									driverMaster.put("licenceNumber",driverDetails.getLicenceNumber());	
									driverMaster.put("vendorId",vendorList.getVendorId());
									driverMaster.put("vendorName",vendorList.getVendorName());
									driverMaster.put("vendorContactName",vendorList.getVendorContactName());
									driverMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
									driverMaster.put("medicalExpDate",dateFormat.format(driverDetails.getMedicalFitnessCertificateValid()));

									dashBoardActivity.add(driverMaster);
								}else{
									int diffInDays = (int) ((driverDetails.getMedicalFitnessCertificateValid().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
									if(diffInDays<30){													
										driverMaster.put("driverId",driverDetails.getDriverId());
										driverMaster.put("driverName",driverDetails.getFirstName());
										driverMaster.put("mobileNumber",driverDetails.getMobileNumber());
										driverMaster.put("licenceNumber",driverDetails.getLicenceNumber());	
										driverMaster.put("vendorId",vendorList.getVendorId());
										driverMaster.put("vendorName",vendorList.getVendorName());
										driverMaster.put("vendorContactName",vendorList.getVendorContactName());
										driverMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
										driverMaster.put("medicalExpDate",dateFormat.format(driverDetails.getMedicalFitnessCertificateValid()));
										dashBoardActivity.add(driverMaster);
									}												
								}											

							}
						}
					}
				}	
				break;
			case "accidentalerts":
				EFmFmTripAlertsPO eFmFmTripAlertsPO=new EFmFmTripAlertsPO();
				eFmFmTripAlertsPO.setEfmFmAssignRoute(assignRoutePO);
				Date TodayDate=new Date();
				List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getAllTripAlerts(eFmFmTripAlertsPO);
				if(allAlerts.size()!=0 || (!(allAlerts.isEmpty()))){
					for(EFmFmTripAlertsPO alerts:allAlerts){
						int diffInDays = (int) ((alerts.getCreationTime().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
						if(diffInDays<30){
							if(alerts.getEfmFmAlertTypeMaster().getAlertId()==8){											
								Map<String, Object>  alertsDetails= new HashMap<String, Object>();											
								alertsDetails.put("tittle", alerts.getEfmFmAlertTypeMaster().getAlertTitle());
								alertsDetails.put("userType", alerts.getUserType());
								alertsDetails.put("driverName", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
								alertsDetails.put("driverNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());											
								alertsDetails.put("vehicleNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
								alertsDetails.put("alertType", alerts.getEfmFmAlertTypeMaster().getAlertType());
								alertsDetails.put("description", alerts.getEfmFmAlertTypeMaster().getAlertDescription());
								alertsDetails.put("zoneName", alerts.getEfmFmAssignRoute().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
								dashBoardActivity.add(alertsDetails);
							}
						}
					}
				}			
				break;
			case "speedalerts":
				EFmFmTripAlertsPO eFmFmTripAlerts=new EFmFmTripAlertsPO();
				eFmFmTripAlerts.setEfmFmAssignRoute(assignRoutePO);	
				Date todayDate=new Date();
				List<EFmFmTripAlertsPO> allSpeedAlerts=iAlertBO.getAllTripAlerts(eFmFmTripAlerts);
				if(allSpeedAlerts.size()!=0 || (!(allSpeedAlerts.isEmpty()))){
					for(EFmFmTripAlertsPO alerts:allSpeedAlerts){
						int diffInDays = (int) ((alerts.getCreationTime().getTime() -todayDate.getTime()) / (1000 * 60 * 60 * 24));
						if(diffInDays<30){	
							if(alerts.getEfmFmAlertTypeMaster().getAlertId()==10){
								Map<String, Object>  alertsDetails= new HashMap<String, Object>();
								alertsDetails.put("tittle", alerts.getEfmFmAlertTypeMaster().getAlertTitle());
								alertsDetails.put("userType", alerts.getUserType());
								alertsDetails.put("driverName", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
								alertsDetails.put("driverNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
								alertsDetails.put("vehicleNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
								alertsDetails.put("alertType", alerts.getEfmFmAlertTypeMaster().getAlertType());
								alertsDetails.put("currentSpeed", alerts.getCurrentSpeed());
								alertsDetails.put("description", alerts.getEfmFmAlertTypeMaster().getAlertDescription());
								alertsDetails.put("zoneName", alerts.getEfmFmAssignRoute().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
								dashBoardActivity.add(alertsDetails);
							}
						}
					}
				}			
				break;	
			}
			break;
		case "GOVERNCEVEHICLES":						
			List<EFmFmVendorMasterPO> listOfVendorByVehicle=iVendorDetailsBO.getAllVendorsDetails(efmFmVendorMaster);
			switch (activityType.toLowerCase()) {							
			case "polutionexpire":																			
				if((!(listOfVendorByVehicle.isEmpty())) || listOfVendorByVehicle.size() !=0){													
					for(EFmFmVendorMasterPO vendorList:listOfVendorByVehicle){				
						EFmFmVehicleMasterPO eFmFmVehicleMasterPO=new EFmFmVehicleMasterPO();
						efmFmVendorMaster.setVendorId(vendorList.getVendorId());
						eFmFmVehicleMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
						List<EFmFmVehicleMasterPO> listOfDriver=iVehicleCheckInBO.getAllVehicleDetails(eFmFmVehicleMasterPO);					
						for(EFmFmVehicleMasterPO vehicleDetails:listOfDriver){											
							Map<String,Object> vehicleMaster=new HashMap<>();
							Date TodayDate=new Date();	
							if(vehicleDetails.getStatus().equalsIgnoreCase("A") || vehicleDetails.getStatus().equalsIgnoreCase("allocated")){												
								if(vehicleDetails.getPolutionValid().before(TodayDate)){												
									vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());			
									vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
									vehicleMaster.put("vendorId",vendorList.getVendorId());
									vehicleMaster.put("vendorName",vendorList.getVendorName());
									vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
									vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
									vehicleMaster.put("polutionExpDate",dateFormat.format(vehicleDetails.getPolutionValid()));

									dashBoardActivity.add(vehicleMaster);
								}else{
									int diffInDays = (int) ((vehicleDetails.getPolutionValid().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
									if(diffInDays<30){													
										vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());
										vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
										vehicleMaster.put("vendorId",vendorList.getVendorId());
										vehicleMaster.put("vendorName",vendorList.getVendorName());
										vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
										vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
										vehicleMaster.put("polutionExpDate",dateFormat.format(vehicleDetails.getPolutionValid()));
										dashBoardActivity.add(vehicleMaster);
									}												
								}									

							}
						}
					}
				}	
				break;
			case "insurancevalid":																			
				if((!(listOfVendorByVehicle.isEmpty())) || listOfVendorByVehicle.size() !=0){													
					for(EFmFmVendorMasterPO vendorList:listOfVendorByVehicle){				
						EFmFmVehicleMasterPO eFmFmVehicleMasterPO=new EFmFmVehicleMasterPO();
						efmFmVendorMaster.setVendorId(vendorList.getVendorId());
						eFmFmVehicleMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
						List<EFmFmVehicleMasterPO> listOfDriver=iVehicleCheckInBO.getAllVehicleDetails(eFmFmVehicleMasterPO);						
						for(EFmFmVehicleMasterPO vehicleDetails:listOfDriver){											
							Map<String,Object> vehicleMaster=new HashMap<>();
							Date TodayDate=new Date();	
							if(vehicleDetails.getStatus().equalsIgnoreCase("A") || vehicleDetails.getStatus().equalsIgnoreCase("allocated")){												
								if(vehicleDetails.getInsuranceValidDate().before(TodayDate)){												
									vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());			
									vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
									vehicleMaster.put("vendorId",vendorList.getVendorId());
									vehicleMaster.put("vendorName",vendorList.getVendorName());
									vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
									vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
									vehicleMaster.put("insuranceExpDate",dateFormat.format(vehicleDetails.getInsuranceValidDate()));

									dashBoardActivity.add(vehicleMaster);
								}else{
									int diffInDays = (int) ((vehicleDetails.getInsuranceValidDate().getTime()-TodayDate.getTime()) / (1000 * 60 * 60 * 24));
									if(diffInDays<30){													
										vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());
										vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
										vehicleMaster.put("vendorId",vendorList.getVendorId());
										vehicleMaster.put("vendorName",vendorList.getVendorName());
										vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
										vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
										vehicleMaster.put("insuranceExpDate",dateFormat.format(vehicleDetails.getInsuranceValidDate()));

										dashBoardActivity.add(vehicleMaster);
									}												
								}									

							}
						}	

					}
				}	
				break;
			case "taxvalid":																			
				if((!(listOfVendorByVehicle.isEmpty())) || listOfVendorByVehicle.size() !=0){													
					for(EFmFmVendorMasterPO vendorList:listOfVendorByVehicle){				
						EFmFmVehicleMasterPO eFmFmVehicleMasterPO=new EFmFmVehicleMasterPO();
						efmFmVendorMaster.setVendorId(vendorList.getVendorId());
						eFmFmVehicleMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
						List<EFmFmVehicleMasterPO> listOfDriver=iVehicleCheckInBO.getAllVehicleDetails(eFmFmVehicleMasterPO);						
						for(EFmFmVehicleMasterPO vehicleDetails:listOfDriver){											
							Map<String,Object> vehicleMaster=new HashMap<>();
							Date TodayDate=new Date();	
							if(vehicleDetails.getStatus().equalsIgnoreCase("A") || vehicleDetails.getStatus().equalsIgnoreCase("allocated")){												
								if(vehicleDetails.getTaxCertificateValid().before(TodayDate)){												
									vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());			
									vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
									vehicleMaster.put("vendorId",vendorList.getVendorId());
									vehicleMaster.put("vendorName",vendorList.getVendorName());
									vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
									vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
									vehicleMaster.put("taxExpDate",dateFormat.format(vehicleDetails.getTaxCertificateValid()));

									dashBoardActivity.add(vehicleMaster);
								}else{
									int diffInDays = (int) ((vehicleDetails.getTaxCertificateValid().getTime()-TodayDate.getTime()) / (1000 * 60 * 60 * 24));
									if(diffInDays<30){													
										vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());
										vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
										vehicleMaster.put("vendorId",vendorList.getVendorId());
										vehicleMaster.put("vendorName",vendorList.getVendorName());
										vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
										vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
										vehicleMaster.put("taxExpDate",dateFormat.format(vehicleDetails.getTaxCertificateValid()));

										dashBoardActivity.add(vehicleMaster);
									}												
								}									

							}
						}	

					}
				}	
				break;
			case "permitvalid":																			
				if((!(listOfVendorByVehicle.isEmpty())) || listOfVendorByVehicle.size() !=0){													
					for(EFmFmVendorMasterPO vendorList:listOfVendorByVehicle){				
						EFmFmVehicleMasterPO eFmFmVehicleMasterPO=new EFmFmVehicleMasterPO();
						efmFmVendorMaster.setVendorId(vendorList.getVendorId());
						eFmFmVehicleMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
						List<EFmFmVehicleMasterPO> listOfDriver=iVehicleCheckInBO.getAllVehicleDetails(eFmFmVehicleMasterPO);						
						for(EFmFmVehicleMasterPO vehicleDetails:listOfDriver){											
							Map<String,Object> vehicleMaster=new HashMap<>();
							Date TodayDate=new Date();	
							if(vehicleDetails.getStatus().equalsIgnoreCase("A") || vehicleDetails.getStatus().equalsIgnoreCase("allocated")){												
								if(vehicleDetails.getPermitValidDate().before(TodayDate)){												
									vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());			
									vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
									vehicleMaster.put("vendorId",vendorList.getVendorId());
									vehicleMaster.put("vendorName",vendorList.getVendorName());
									vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
									vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
									vehicleMaster.put("permitExpDate",dateFormat.format(vehicleDetails.getPermitValidDate()));

									dashBoardActivity.add(vehicleMaster);
								}else{
									int diffInDays = (int) ((vehicleDetails.getPermitValidDate().getTime()-TodayDate.getTime()) / (1000 * 60 * 60 * 24));
									if(diffInDays<30){													
										vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());
										vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
										vehicleMaster.put("vendorId",vendorList.getVendorId());
										vehicleMaster.put("vendorName",vendorList.getVendorName());
										vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
										vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
										vehicleMaster.put("permitExpDate",dateFormat.format(vehicleDetails.getPermitValidDate()));

										dashBoardActivity.add(vehicleMaster);
									}												
								}									

							}
						}	

					}
				}	
				break;
			case "vehiclemaintenancevalid":																			
				if((!(listOfVendorByVehicle.isEmpty())) || listOfVendorByVehicle.size() !=0){													
					for(EFmFmVendorMasterPO vendorList:listOfVendorByVehicle){				
						EFmFmVehicleMasterPO eFmFmVehicleMasterPO=new EFmFmVehicleMasterPO();
						efmFmVendorMaster.setVendorId(vendorList.getVendorId());
						eFmFmVehicleMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
						List<EFmFmVehicleMasterPO> listOfDriver=iVehicleCheckInBO.getAllVehicleDetails(eFmFmVehicleMasterPO);						
						for(EFmFmVehicleMasterPO vehicleDetails:listOfDriver){											
							Map<String,Object> vehicleMaster=new HashMap<>();
							Date TodayDate=new Date();	
							if(vehicleDetails.getStatus().equalsIgnoreCase("A") || vehicleDetails.getStatus().equalsIgnoreCase("allocated")){												
								if(vehicleDetails.getVehicleFitnessDate().before(TodayDate)){												
									vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());			
									vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
									vehicleMaster.put("vendorId",vendorList.getVendorId());
									vehicleMaster.put("vendorName",vendorList.getVendorName());
									vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
									vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
									vehicleMaster.put("vehicleManintenanceExpDate",dateFormat.format(vehicleDetails.getVehicleFitnessDate()));

									dashBoardActivity.add(vehicleMaster);
								}else{
									int diffInDays = (int) ((vehicleDetails.getVehicleFitnessDate().getTime()-TodayDate.getTime()) / (1000 * 60 * 60 * 24));
									if(diffInDays<30){													
										vehicleMaster.put("vehicleId",vehicleDetails.getVehicleId());
										vehicleMaster.put("vehicleNumber",vehicleDetails.getVehicleNumber());
										vehicleMaster.put("vendorId",vendorList.getVendorId());
										vehicleMaster.put("vendorName",vendorList.getVendorName());
										vehicleMaster.put("vendorContactName",vendorList.getVendorContactName());
										vehicleMaster.put("vendorMobileNumber",vendorList.getVendorMobileNo());
										vehicleMaster.put("vehicleManintenanceExpDate",dateFormat.format(vehicleDetails.getVehicleFitnessDate()));

										dashBoardActivity.add(vehicleMaster);
									}												
								}									

							}
						}	

					}
				}	
				break;
			case "breakdownsalerts":
				EFmFmTripAlertsPO eFmFmTripAlertsPO=new EFmFmTripAlertsPO();
				eFmFmTripAlertsPO.setEfmFmAssignRoute(assignRoutePO);
				Date TodayDate=new Date();
				List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getAllTripAlerts(eFmFmTripAlertsPO);
				if(allAlerts.size()!=0 || (!(allAlerts.isEmpty()))){
					for(EFmFmTripAlertsPO alerts:allAlerts){
						int diffInDays = (int) ((alerts.getCreationTime().getTime() -TodayDate.getTime()) / (1000 * 60 * 60 * 24));
						if(diffInDays<30){	
							if(alerts.getEfmFmAlertTypeMaster().getAlertId()==9){											
								Map<String, Object>  alertsDetails= new HashMap<String, Object>();											
								alertsDetails.put("tittle", alerts.getEfmFmAlertTypeMaster().getAlertTitle());
								alertsDetails.put("userType", alerts.getUserType());
								alertsDetails.put("driverName", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
								alertsDetails.put("driverNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
								alertsDetails.put("vehicleNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
								alertsDetails.put("alertType", alerts.getEfmFmAlertTypeMaster().getAlertType());
								alertsDetails.put("description", alerts.getEfmFmAlertTypeMaster().getAlertDescription());
								alertsDetails.put("zoneName", alerts.getEfmFmAssignRoute().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
								dashBoardActivity.add(alertsDetails);

							}
						}
					}
				}			
				break;
			}
			break;
		case "ALERTS":
			switch (activityType.toLowerCase()) {
			case "sos":	
				List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getAllTodaysTripSOSAlerts(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(allAlerts.size()!=0 || (!(allAlerts.isEmpty()))){
					for(EFmFmTripAlertsPO alerts:allAlerts){
							Map<String, Object>  alertsDetails= new HashMap<String, Object>();
							alertsDetails.put("tripAlertId", alerts.getTripAlertsId());
							alertsDetails.put("tittle", alerts.getEfmFmAlertTypeMaster().getAlertTitle());
							alertsDetails.put("userType", alerts.getUserType());
							alertsDetails.put("driverName", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
							alertsDetails.put("driverNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
							alertsDetails.put("vehicleNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
							alertsDetails.put("alertType", alerts.getEfmFmAlertTypeMaster().getAlertType());
							alertsDetails.put("assignRouteId", alerts.getEfmFmAssignRoute().getAssignRouteId());
							alertsDetails.put("alertDate", dateTimeFormat.format(alerts.getCreationTime()));
							alertsDetails.put("description", alerts.getAlertClosingDescription());
							alertsDetails.put("zoneName", alerts.getEfmFmAssignRoute().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
							dashBoardActivity.add(alertsDetails);
					}
				}	
				break;
			case "roadalerts":	
				List<EFmFmTripAlertsPO> allAlert=iAlertBO.getAllTodaysTripRoadAlerts(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(allAlert.size()!=0 || (!(allAlert.isEmpty()))){
					for(EFmFmTripAlertsPO alerts:allAlert){
						Map<String, Object>  alertsDetails= new HashMap<String, Object>();
						alertsDetails.put("tripAlertId", alerts.getTripAlertsId());
						alertsDetails.put("tittle", alerts.getEfmFmAlertTypeMaster().getAlertTitle());
						alertsDetails.put("userType", alerts.getUserType());
						alertsDetails.put("driverName", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
						alertsDetails.put("driverNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
						alertsDetails.put("vehicleNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
						alertsDetails.put("alertType", alerts.getEfmFmAlertTypeMaster().getAlertType());
						alertsDetails.put("assignRouteId", alerts.getEfmFmAssignRoute().getAssignRouteId());
						alertsDetails.put("alertDate", dateTimeFormat.format(alerts.getCreationTime()));
						alertsDetails.put("description", alerts.getAlertClosingDescription());
						alertsDetails.put("zoneName", alerts.getEfmFmAssignRoute().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
						dashBoardActivity.add(alertsDetails);
				}
				}		    
				break;
			case "openalerts":	
				List<EFmFmTripAlertsPO> allOpenAlert=iAlertBO.getAllTodaysTripOpenAlerts(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(allOpenAlert.size()!=0 || (!(allOpenAlert.isEmpty()))){
					for(EFmFmTripAlertsPO alerts:allOpenAlert){
						Map<String, Object>  alertsDetails= new HashMap<String, Object>();
						alertsDetails.put("tripAlertId", alerts.getTripAlertsId());
						alertsDetails.put("tittle", alerts.getEfmFmAlertTypeMaster().getAlertTitle());
						alertsDetails.put("userType", alerts.getUserType());
						alertsDetails.put("driverName", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
						alertsDetails.put("driverNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
						alertsDetails.put("vehicleNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
						alertsDetails.put("alertType", alerts.getEfmFmAlertTypeMaster().getAlertType());
						alertsDetails.put("assignRouteId", alerts.getEfmFmAssignRoute().getAssignRouteId());
						alertsDetails.put("alertDate", dateTimeFormat.format(alerts.getCreationTime()));
						alertsDetails.put("description", alerts.getAlertClosingDescription());
						alertsDetails.put("zoneName", alerts.getEfmFmAssignRoute().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
						dashBoardActivity.add(alertsDetails);
				}
				}		    
				break;	
				case "closealerts":	
					List<EFmFmTripAlertsPO> allCloseAlerts=iAlertBO.getAllTodaysTripCloseAlerts(travelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
					log.info("all Alerts"+allCloseAlerts.size());
					if(allCloseAlerts.size()!=0 || (!(allCloseAlerts.isEmpty()))){
						for(EFmFmTripAlertsPO alerts:allCloseAlerts){
							Map<String, Object>  alertsDetails= new HashMap<String, Object>();
							alertsDetails.put("tripAlertId", alerts.getTripAlertsId());
							alertsDetails.put("tittle", alerts.getEfmFmAlertTypeMaster().getAlertTitle());
							alertsDetails.put("userType", alerts.getUserType());
							alertsDetails.put("driverName", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
							alertsDetails.put("driverNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
							alertsDetails.put("vehicleNumber", alerts.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
							alertsDetails.put("alertType", alerts.getEfmFmAlertTypeMaster().getAlertType());
							alertsDetails.put("alertDate", dateTimeFormat.format(alerts.getCreationTime()));
							alertsDetails.put("description", alerts.getAlertClosingDescription());
							alertsDetails.put("zoneName", alerts.getEfmFmAssignRoute().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
							dashBoardActivity.add(alertsDetails);
					}
					}		    
					break;
			}
			break;

		}					
		return Response.ok(dashBoardActivity, MediaType.APPLICATION_JSON).build();		
	}
	/**
	* update the alerts description.
	* .   
	*
	* @author  Sarfraz Khan
	* 
	* @since   2016-01-11
	* 
	* @return EFmFmTripAlertsPO details.
	 * @throws ParseException 
	*/	
	
	@POST
	@Path("/updateAlertDesc")
	public Response updateAlertDesc(EFmFmTripAlertsPO eFmFmTripAlertsPO) throws IOException{
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		Map<String, Object>  requests = new HashMap<String, Object>();	
		List<EFmFmTripAlertsPO> particularaAlertDetail=iAlertBO.getParticuarAlertDetailFromAlertId(eFmFmTripAlertsPO.getBranchId(), eFmFmTripAlertsPO.getTripAlertsId(), eFmFmTripAlertsPO.getAssignRouteId());
		particularaAlertDetail.get(0).setAlertClosingDescription(eFmFmTripAlertsPO.getAlertClosingDescription());
		iAlertBO.update(particularaAlertDetail.get(0));
		requests.put("status", "success");		
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	* Close the trip alerts.
	* .   
	*
	* @author  Sarfraz Khan
	* 
	* @since   2016-01-11
	* 
	* @return EFmFmTripAlertsPO details.
	 * @throws ParseException 
	*/	
	
	@POST
	@Path("/closeOpenAlert")
	public Response closeOPenAlerts(EFmFmTripAlertsPO eFmFmTripAlertsPO) throws IOException{
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		Map<String, Object>  requests = new HashMap<String, Object>();	
		List<EFmFmTripAlertsPO> particularaAlertDetail=iAlertBO.getParticuarAlertDetailFromAlertId(eFmFmTripAlertsPO.getBranchId(), eFmFmTripAlertsPO.getTripAlertsId(), eFmFmTripAlertsPO.getAssignRouteId());
		particularaAlertDetail.get(0).setAlertClosingDescription(eFmFmTripAlertsPO.getAlertClosingDescription());
		particularaAlertDetail.get(0).setAlertOpenStatus("close");
		iAlertBO.update(particularaAlertDetail.get(0));
		requests.put("status", "success");		
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	
	
}