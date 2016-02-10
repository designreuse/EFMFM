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
import java.util.TreeMap;

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
import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.IAssignRouteBO;
import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IEmployeeDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IRouteDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.eFmFmFleet.MessagingService;
import com.newtglobal.eFmFmFleet.model.EFmFmActualRoutTravelledPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientRouteMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripTimingMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmZoneMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/zones")
@Consumes("application/json")
@Produces("application/json")
public class ServiceMappingService  {
	
	private static Log log = LogFactory.getLog(ServiceMappingService.class);	

	@POST
	@Path("/allzones")
	public Response getAllZonesAndRoutesDetails(EFmFmAssignRoutePO assignRoutePO){		
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		assignRoutePO.setTripAssignDate(new Date());
		List<String> zones=new ArrayList<String>();
		List<EFmFmAssignRoutePO> assignRoute=assignRouteBO.getAllTodaysTrips(assignRoutePO);
		Map<String, Object>  allRoutesSingleObj = new HashMap<String, Object>();	
		List<TreeMap<String, Object>> allRoutes= new ArrayList<TreeMap<String, Object>>();
        if(assignRoute.size()!=0 || (!(assignRoute.isEmpty()))){
		 for(EFmFmAssignRoutePO assignRoutes:assignRoute){
			if(!(zones.contains(assignRoutes.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName()))){
				TreeMap<String, Object>  requests = new TreeMap<String, Object>();					
				zones.add(assignRoutes.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
				EFmFmRouteAreaMappingPO routeId=new EFmFmRouteAreaMappingPO();
				routeId.setRouteAreaId(assignRoutes.geteFmFmRouteAreaMapping().getRouteAreaId());
				EFmFmZoneMasterPO eFmFmZoneMaster=new EFmFmZoneMasterPO();
				eFmFmZoneMaster.setZoneId(assignRoutes.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
				routeId.seteFmFmZoneMaster(eFmFmZoneMaster);
				assignRoutePO.seteFmFmRouteAreaMapping(routeId);
				List<EFmFmAssignRoutePO> routesInZone=assignRouteBO.getAllRoutesOfParticularZone(assignRoutePO);
				requests.put("routeId", assignRoutes.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
			    requests.put("routeName", assignRoutes.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());		    
			    requests.put("NumberOfRoutes", routesInZone.size());
				 allRoutes.add(requests);
			}
			 }
        }
		 allRoutesSingleObj.put("routeDetails", allRoutes);
		return Response.ok(allRoutesSingleObj, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/allroutes")
	public Response getAllRoutesOfParticulaZone(EFmFmAssignRoutePO assignRoutePO){		
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
		assignRoutePO.setTripAssignDate(new Date());
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		List<EFmFmAssignRoutePO> assignRoute=assignRouteBO.getAllRoutesOfParticularZone(assignRoutePO);
		Map<String, Object>  allRoutesSingleObj = new HashMap<String, Object>();	
		List<Map<String, Object>> allRoutes= new ArrayList<Map<String, Object>>();
        if(assignRoute.size()!=0 || (!(assignRoute.isEmpty()))){
		 for(EFmFmAssignRoutePO assignRoutes:assignRoute){
				List<Map<String, Object>> tripAllDetails = new ArrayList<Map<String, Object>>();
				Map<String, Object>  requests = new HashMap<String, Object>();	
				StringBuffer waypoints=new StringBuffer();
				List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=null;
				if(assignRoutes.getTripType().equalsIgnoreCase("PICKUP")){
				    employeeTripDetailPO=iCabRequestBO.getParticularTripAllEmployees(assignRoutes.getAssignRouteId());
				}
				else{
				    employeeTripDetailPO=iCabRequestBO.getDropTripAllSortedEmployees(assignRoutes.getAssignRouteId());
				}
	        if(employeeTripDetailPO.size()!=0 || (!(employeeTripDetailPO.isEmpty()))){
				for(EFmFmEmployeeTripDetailPO employeeTripDetail:employeeTripDetailPO){
					Map<String, Object>  employeeDetails = new HashMap<String, Object>();
					employeeDetails.put("name",employeeTripDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());
					employeeDetails.put("employeeId", employeeTripDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
					employeeDetails.put("requestId", employeeTripDetail.geteFmFmEmployeeTravelRequest().getRequestId());
					employeeDetails.put("employeeNumber",employeeTripDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
					employeeDetails.put("tripType", assignRoutes.getTripType());
					employeeDetails.put("empArea", employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaName());					
					if(employeeTripDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Male")){
						employeeDetails.put("gender", 1);	
					}else{
						employeeDetails.put("gender", 2);	
					}
					employeeDetails.put("tripTime", employeeTripDetail.geteFmFmEmployeeTravelRequest().getShiftTime());
					employeeDetails.put("address", employeeTripDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getAddress());
					employeeDetails.put("latLongi", employeeTripDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getLatitudeLongitude());
					if(assignRoutes.getTripType().equalsIgnoreCase("PICKUP")){
						employeeDetails.put("pickUpTime", employeeTripDetail.geteFmFmEmployeeTravelRequest().getPickUpTime());
					} 
					else{
						employeeDetails.put("pickUpTime", employeeTripDetail.geteFmFmEmployeeTravelRequest().getDropSequence()	);
					}
                    tripAllDetails.add(employeeDetails);
			     }	
				
			   if(tripAllDetails.size()!=0 || (!(tripAllDetails.isEmpty()))){
	                	for(int i=0;i<=tripAllDetails.size()-1;i++){
						waypoints.append(tripAllDetails.get(i).get("latLongi")+"|");
	                	}
	                }					   			
	        }
			requests.put("assignRouteId", assignRoutes.getAssignRouteId());
			requests.put("tripType", assignRoutes.getTripType());
			if(assignRoutes.getEscortRequredFlag().equalsIgnoreCase("Y")){
				try{
				int a=assignRoutes.geteFmFmEscortCheckIn().getEscortCheckInId();
				requests.put("escortName", assignRoutes.geteFmFmEscortCheckIn().geteFmFmEscortMaster().getFirstName());	
				requests.put("escortId", assignRoutes.geteFmFmEscortCheckIn().getEscortCheckInId());							
				}catch(Exception e){
					requests.put("escortId", "N");							
				 requests.put("escortName", "Escort Required But Not Available");					 
				}
			}
			else{
				requests.put("escortName", "Not Required");							
			}
			requests.put("escortRequired", assignRoutes.getEscortRequredFlag());
			requests.put("shiftTime",shiftTimeFormater.format(assignRoutes.getShiftTime()));
			requests.put("vehicleStatus", assignRoutes.getVehicleStatus());
			requests.put("bucketStatus",assignRoutes.getBucketStatus());
			requests.put("tripStatus", assignRoutes.getTripStatus());
			requests.put("waypoints", waypoints);
			requests.put("baseLatLong",assignRoutes.geteFmFmClientBranchPO().getLatitudeLongitude());
			requests.put("routeId", assignRoutes.getAssignRouteId());
			requests.put("driverName", assignRoutes.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
			requests.put("driverNumber", assignRoutes.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
			requests.put("vehicleNumber", assignRoutes.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
			requests.put("vendorId", assignRoutes.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorId());
			requests.put("driverId", assignRoutes.getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
			requests.put("checkInId", assignRoutes.getEfmFmVehicleCheckIn().getCheckInId());
			requests.put("vehicleId", assignRoutes.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
			requests.put("vehicleAvailableCapacity", assignRoutes.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getAvailableSeat());
			requests.put("capacity", assignRoutes.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getCapacity());
			requests.put("deviceNumber", assignRoutes.getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId());
			requests.put("empDetails", tripAllDetails);
		    requests.put("routeName", assignRoutes.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
			allRoutes.add(requests);
			 }
		 allRoutesSingleObj.put("routeDetails", allRoutes);
		 allRoutesSingleObj.put("totalRoutes", assignRoute.size());
        }	
		return Response.ok(allRoutesSingleObj, MediaType.APPLICATION_JSON).build();
	}
	
	
	/*
	 * update route drop Sequence from service mapping
	 */
	
	@POST
	@Path("/updateDropSequnce")
	public Response updateDropTripRouteSquencing(EFmFmAssignRoutePO assignRoutePO) throws ParseException{
		Map<String, Object>  responce = new HashMap<String,Object>();
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");			
		List<EFmFmEmployeeTravelRequestPO> cabRequestDetail=iCabRequestBO.getParticularRequestDetailOnTripComplete(assignRoutePO.geteFmFmClientBranchPO().getBranchId(), assignRoutePO.getRequestId());		
		if((!(cabRequestDetail.isEmpty())) || cabRequestDetail.size() !=0){	
			//Time is int varible for updating drop sequence
			cabRequestDetail.get(0).setDropSequence(Integer.parseInt(assignRoutePO.getTime()));
			iCabRequestBO.update(cabRequestDetail.get(0));
		}
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	
	}
	
	
	/*
	 * update route pickuptime for Sequence from service mapping
	 */
	
	@POST
	@Path("/updatePickUpTime")
	public Response updateRouteSquencing(EFmFmAssignRoutePO assignRoutePO) throws ParseException{
		Map<String, Object>  responce = new HashMap<String,Object>();
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");			
		List<EFmFmEmployeeTravelRequestPO> cabRequestDetail=iCabRequestBO.getParticularRequestDetailOnTripComplete(assignRoutePO.geteFmFmClientBranchPO().getBranchId(), assignRoutePO.getRequestId());
		DateFormat timeformate = new SimpleDateFormat("HH:mm");
		String pickUpTime=assignRoutePO.getTime();
		Date changePickUpTime  = (Date) timeformate.parse(pickUpTime);				 
		java.sql.Time pickTime = new java.sql.Time(changePickUpTime.getTime());
		if((!(cabRequestDetail.isEmpty())) || cabRequestDetail.size() !=0){	
			cabRequestDetail.get(0).setPickUpTime(pickTime);
			iCabRequestBO.update(cabRequestDetail.get(0));
		}
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	
	}
	
	/*
	 * Deleting an empty Route or bucket 
	 * 
	*/
	
	@POST
	@Path("/deleteroute")
	public Response deleteAnemptyRoute(EFmFmAssignRoutePO assignRoutePO){
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
		Map<String, Object>  responce = new HashMap<String,Object>();
    	List<EFmFmAssignRoutePO> assignRoute=assignRouteBO.closeParticularTrips(assignRoutePO);
    	if(!(assignRoute.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getCapacity()==assignRoute.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getAvailableSeat()+1)){
    		responce.put("status", "failed");
    		return Response.ok(responce, MediaType.APPLICATION_JSON).build();

    	}
    	EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
    	eFmFmVehicleCheckInPO.setCheckInId(assignRoute.get(0).getEfmFmVehicleCheckIn().getCheckInId());
		List<EFmFmVehicleCheckInPO> vehicleCheckIn=iVehicleCheckInBO.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
		vehicleCheckIn.get(0).setStatus("Y");
		iVehicleCheckInBO.update(vehicleCheckIn.get(0));
    	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(assignRoute.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
        particularDriverDetails.setStatus("A");
        approvalBO.update(particularDriverDetails);
        List<EFmFmDeviceMasterPO> deviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(assignRoute.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), assignRoute.get(0).geteFmFmClientBranchPO().getBranchId());
        deviceDetails.get(0).setStatus("Y");
        iVehicleCheckInBO.update(deviceDetails.get(0));

    	assignRouteBO.deleteParticularAssignRoute(assignRoute.get(0).getAssignRouteId());
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	
	}
	
	
	
	/*
	 * Deleting a request from Bucket 
	 * 
	*/
	
	@POST
	@Path("/cancelbucketrequest")
	public Response cancelRequestFromBucket(EFmFmEmployeeTripDetailPO eFmFmEmployeeTripDetailPO) throws ParseException{					
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");	
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	


		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object>  responce = new HashMap<String,Object>();		
		//For making In active the request from travel desk.
		EFmFmAssignRoutePO eFmFmAssignRoutePO=new EFmFmAssignRoutePO();
		eFmFmAssignRoutePO.setAssignRouteId(eFmFmEmployeeTripDetailPO.getEfmFmAssignRoute().getAssignRouteId());
		
		EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
		eFmFmClientBranchPO.setBranchId(eFmFmEmployeeTripDetailPO.getBranchId());
		eFmFmAssignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		
		EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO=new EFmFmEmployeeTravelRequestPO();
		eFmFmEmployeeTravelRequestPO.setRequestId(eFmFmEmployeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestId());
		
		EFmFmUserMasterPO eFmFmUserMasterPO=new EFmFmUserMasterPO();
		eFmFmUserMasterPO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		eFmFmEmployeeTravelRequestPO.setEfmFmUserMaster(eFmFmUserMasterPO);		
		List<EFmFmEmployeeTravelRequestPO> cabRequest=iCabRequestBO.getparticularRequestDetail(eFmFmEmployeeTravelRequestPO);
		eFmFmUserMasterPO.setUserId(cabRequest.get(0).getEfmFmUserMaster().getUserId());
		eFmFmEmployeeTravelRequestPO.setEfmFmUserMaster(eFmFmUserMasterPO);	
		eFmFmEmployeeTravelRequestPO.setRequestType(cabRequest.get(0).getRequestType());
		eFmFmEmployeeTravelRequestPO.setTripType(cabRequest.get(0).getTripType());

	    DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		String shiftTime="23:50:00";
		Date shift  = (Date) shiftFormate.parse(shiftTime);				 
		java.sql.Time dateShiftTime = new java.sql.Time(shift.getTime());
		cabRequest.get(0).setShiftTime(dateShiftTime);
		cabRequest.get(0).setReadFlg("Y");
		iCabRequestBO.update(cabRequest.get(0));
		List<EFmFmEmployeeTravelRequestPO> nextDayCabRequest=iCabRequestBO.getAnotherActiveRequestForNextDate(eFmFmEmployeeTravelRequestPO);
		if((!(nextDayCabRequest.isEmpty())) || nextDayCabRequest.size() !=0){			
		iCabRequestBO.deleteParticularRequest(nextDayCabRequest.get(0).getRequestId());
		}
		List<EFmFmAssignRoutePO> assignRoute=assignRouteBO.closeParticularTrips(eFmFmAssignRoutePO);
    	assignRoute.get(0).setBucketStatus("N");
    	assignRouteBO.update(assignRoute.get(0));
		List<EFmFmEmployeeTripDetailPO> tripRequest=iCabRequestBO.getParticularTriprEmployeeBoardedStatus(eFmFmEmployeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestId(), eFmFmEmployeeTripDetailPO.getEfmFmAssignRoute().getAssignRouteId());
		//update the vehicle capacity after deletion
		EFmFmVehicleMasterPO eFmFmVehicleMasterPO=iVehicleCheckInBO.getParticularVehicleDetails(tripRequest.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), eFmFmEmployeeTripDetailPO.getBranchId());
		if(((eFmFmVehicleMasterPO.getAvailableSeat()+1==eFmFmVehicleMasterPO.getCapacity()-1) && assignRoute.get(0).getEscortRequredFlag().equalsIgnoreCase("N")) || ((eFmFmVehicleMasterPO.getAvailableSeat()+2==eFmFmVehicleMasterPO.getCapacity()-1) && assignRoute.get(0).getEscortRequredFlag().equalsIgnoreCase("Y"))){		
			//Delete travel desk when vehicle started...
			eFmFmVehicleMasterPO.setStatus("A");
			EFmFmActualRoutTravelledPO actualRoutTravelled=new EFmFmActualRoutTravelledPO();
			actualRoutTravelled.setTravelledTime(new Date());			    		
			actualRoutTravelled.seteFmFmClientBranchPO(eFmFmClientBranchPO);
			actualRoutTravelled.setEfmFmAssignRoute(eFmFmAssignRoutePO);			
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
			eFmFmVehicleCheckInPO.setCheckInTime(new Date());
			eFmFmVehicleCheckInPO.setCheckInId(tripRequest.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getCheckInId());
			List<EFmFmVehicleCheckInPO> vehicleCheckIn=iVehicleCheckInBO.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
			vehicleCheckIn.get(0).setStatus("Y");
			iVehicleCheckInBO.update(vehicleCheckIn.get(0));
			if(assignRoute.get(0).getEscortRequredFlag().equalsIgnoreCase("N")){
				eFmFmVehicleMasterPO.setAvailableSeat(eFmFmVehicleMasterPO.getAvailableSeat()+1);
			}
			else{				
				List<EFmFmEscortCheckInPO> checkInEscortDetails=iVehicleCheckInBO.getParticulaEscortDetailFromEscortId(assignRoute.get(0).geteFmFmClientBranchPO().getBranchId(),assignRoute.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
				
				checkInEscortDetails.get(0).setStatus("Y");
				iVehicleCheckInBO.update(checkInEscortDetails.get(0));
				eFmFmVehicleMasterPO.setAvailableSeat(eFmFmVehicleMasterPO.getAvailableSeat()+2);
			}			
			iVehicleCheckInBO.update(eFmFmVehicleMasterPO);           
        	iCabRequestBO.deleteParticularTripDetail(tripRequest.get(0).getEmpTripId());
        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(assignRoute.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
            particularDriverDetails.setStatus("A");
            approvalBO.update(particularDriverDetails);
            List<EFmFmDeviceMasterPO> deviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(assignRoute.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), assignRoute.get(0).geteFmFmClientBranchPO().getBranchId());
            deviceDetails.get(0).setStatus("Y");
            iVehicleCheckInBO.update(deviceDetails.get(0)); 
            
            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(assignRoute.get(0).geteFmFmClientBranchPO().getBranchId(),assignRoute.get(0).getAssignRouteId());
            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
	            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
					iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
				}
            }
	    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
			if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
				for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
					assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
				}
			}
           	assignRouteBO.deleteParticularAssignRoute(assignRoute.get(0).getAssignRouteId());
		}
		else{
			int availableCapacity;
			List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=null;
			if(assignRoute.get(0).getTripType().equalsIgnoreCase("PICKUP")){
				availableCapacity=eFmFmVehicleMasterPO.getAvailableSeat()+1;
			    employeeTripDetailPO=iCabRequestBO.getParticularTripAllEmployees(assignRoute.get(0).getAssignRouteId());
			    if(assignRoute.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
			    	availableCapacity=eFmFmVehicleMasterPO.getAvailableSeat()+2;
			    	assignRoute.get(0).setEscortRequredFlag("N");
			    	assignRouteBO.update(assignRoute.get(0));
			    }			    
			}
			else{
			    employeeTripDetailPO=iCabRequestBO.getDropTripAllSortedEmployees(assignRoute.get(0).getAssignRouteId());
				availableCapacity=eFmFmVehicleMasterPO.getAvailableSeat()+1;
				 if(assignRoute.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
				    	availableCapacity=eFmFmVehicleMasterPO.getAvailableSeat()+2;
				    	assignRoute.get(0).setEscortRequredFlag("N");
				    	assignRouteBO.update(assignRoute.get(0));
				    }
			}			
			eFmFmVehicleMasterPO.setAvailableSeat(availableCapacity);
			iVehicleCheckInBO.update(eFmFmVehicleMasterPO);
    		iCabRequestBO.deleteParticularTripDetail(tripRequest.get(0).getEmpTripId());			

		}
		responce.put("escortName", "Not Required");
		responce.put("availableCapacity", eFmFmVehicleMasterPO.getAvailableSeat());
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/bucketclose")
	public Response manualBucketClose(EFmFmAssignRoutePO assignRoutePO) throws ParseException{					
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");
		Map<String, Object>  responce = new HashMap<String,Object>();
		final List<EFmFmAssignRoutePO> assignRoutes=assignRouteBO.closeParticularTrips(assignRoutePO);
		IVehicleCheckInBO vehicleCheckIn = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		assignRoutes.get(0).setTripUpdateTime(new Date());
		 if(assignRoutes.get(0).getTripType().equalsIgnoreCase("PICKUP")){
			List<EFmFmEmployeeTripDetailPO> tripEmployees=iCabRequestBO.getParticularTripAllEmployees(assignRoutes.get(0).getAssignRouteId());
			if(assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getAvailableSeat()==0 && assignRoutes.get(0).getEscortRequredFlag().equalsIgnoreCase("N") && tripEmployees.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") &&(tripEmployees.get(0).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() >=20 || tripEmployees.get(0).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() ==0 || tripEmployees.get(0).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() <=7)){
				responce.put("type", "PICKUP");
				responce.put("status", "notClose");
				return Response.ok(responce, MediaType.APPLICATION_JSON).build(); 
			 }
			EFmFmVehicleMasterPO vehicleMaster=vehicleCheckIn.getParticularVehicleDetail(assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());				
		   if(assignRoutes.get(0).getAllocationMsg().equalsIgnoreCase("N")){
				Thread thread1 = new Thread(new Runnable() {		
					public  void run() {
						try {
							ICabRequestBO iCabRequestBO1 = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
							List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=iCabRequestBO1.getParticularTripAllEmployees(assignRoutes.get(0).getAssignRouteId());
							for(EFmFmEmployeeTripDetailPO tripDetailPO:employeeTripDetailPO){								
								String text="Dear employee cab is allocated for your pickup\n"+assignRoutes.get(0).getShiftTime()+" shift\nAllocated Cab number is\n"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+" \nyour pickup time is "+tripDetailPO.geteFmFmEmployeeTravelRequest().getPickUpTime()+"\nVehicle Type "+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel();
								MessagingService messagingService=new MessagingService();
								if(tripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")){
									String hostText="Dear Host,Your guest pickup details,Cab No-"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\nDriver-"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName()+" Mobile- "+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber()+"\nGuest reporting time is-"+tripDetailPO.geteFmFmEmployeeTravelRequest().getPickUpTime()+"\nRegards SBOT 919962598888";
									text="Dear Guest,Your pickup details,Cab No-"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\nDriver-"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName()+" Mobile- "+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber()+"\nyour reporting time is-"+tripDetailPO.geteFmFmEmployeeTravelRequest().getPickUpTime()+"\nRegards SBOT 919962598888";
									messagingService.sendMessageToGuest(tripDetailPO.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,tripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());			
									messagingService.sendTripAsMessage(tripDetailPO.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getHostMobileNumber(), hostText,tripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());
								}else{
									messagingService.sendTripAsMessage(tripDetailPO.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,tripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());			
								}
							}
					} catch (Exception e) {
						e.printStackTrace();
					}
					}
				}); thread1.start();
				assignRoutes.get(0).setAllocationMsg("Y");
				assignRoutes.get(0).setAllocationMsgDeliveryDate(new Date());
			 }
				if(assignRoutes.get(0).getEscortRequredFlag().equalsIgnoreCase("N")){
                   if(tripEmployees.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") && (tripEmployees.get(0).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() >=20 || tripEmployees.get(0).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() ==0 || tripEmployees.get(0).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() <=7)){
	 					List <EFmFmEscortCheckInPO> escortList=vehicleCheckIn.getAllCheckedInEscort(assignRoutes.get(0).geteFmFmClientBranchPO().getBranchId(),new Date());	         					
	 					if(!(escortList.isEmpty()) || escortList.size()!=0){
	 					EFmFmEscortCheckInPO checkInEscort=new EFmFmEscortCheckInPO();
	 					checkInEscort.setEscortCheckInId(escortList.get(0).getEscortCheckInId());
	 					assignRoutes.get(0).seteFmFmEscortCheckIn(checkInEscort);
	 					escortList.get(0).setStatus("N");
	 					vehicleCheckIn.update(escortList.get(0));
	 					}
	 					assignRoutes.get(0).setEscortRequredFlag("Y");
	 					assignRoutes.get(0).setVehicleStatus("F");
	 					assignRoutes.get(0).setBucketStatus("Y");
	 					assignRouteBO.update(assignRoutes.get(0));
	 				    vehicleMaster.setAvailableSeat(vehicleMaster.getAvailableSeat()-1);
		 				vehicleCheckIn.update(vehicleMaster);
                   }
                   else{
                	   assignRoutes.get(0).setBucketStatus("Y");
                	   assignRoutes.get(0).setVehicleStatus("F");
                	   assignRouteBO.update(assignRoutes.get(0));
                   }
				}
				else{
					assignRoutes.get(0).setBucketStatus("Y");
					assignRoutes.get(0).setVehicleStatus("F");
					assignRouteBO.update(assignRoutes.get(0));
				}
		}
		else if(assignRoutes.get(0).getTripType().equalsIgnoreCase("DROP")){
			log.info("drop call");
			   List<EFmFmEmployeeTripDetailPO> finalTripEmployees=iCabRequestBO.getDropTripAllSortedEmployees(assignRoutes.get(0).getAssignRouteId());
			   if(assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getAvailableSeat()==0 && assignRoutes.get(0).getEscortRequredFlag().equalsIgnoreCase("N") && finalTripEmployees.get(finalTripEmployees.size()-1).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") && (finalTripEmployees.get(finalTripEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() >=19 || finalTripEmployees.get(finalTripEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() ==0 || finalTripEmployees.get(finalTripEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() <7)){
					responce.put("type", "DROP");
				   responce.put("status", "notClose");
					return Response.ok(responce, MediaType.APPLICATION_JSON).build();
				 }
			   EFmFmVehicleMasterPO vehicleMaster=vehicleCheckIn.getParticularVehicleDetail(assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());				
				if(assignRoutes.get(0).getAllocationMsg().equalsIgnoreCase("N")){
					Thread thread1 = new Thread(new Runnable() {		
						public  void run() {
							try {
								ICabRequestBO iCabRequestBO1 = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
								List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=iCabRequestBO1.getDropTripAllSortedEmployees(assignRoutes.get(0).getAssignRouteId());
								for(EFmFmEmployeeTripDetailPO tripDetailPO:employeeTripDetailPO){
									String text="Dear employee  cab is allocated for your drop\n"+assignRoutes.get(0).getShiftTime()+" shift\nAllocated Cab number is\n"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\nVehicle Type "+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel();
									MessagingService messagingService=new MessagingService();
									if(tripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")){
										String hostText="Dear Host,Your guest drop details,Cab No-"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\nDriver-"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName()+" Mobile- "+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber()+"\nGuest reporting time is-"+tripDetailPO.geteFmFmEmployeeTravelRequest().getShiftTime()+"\nRegards SBOT 919962598888";
										text="Dear Guest,Your drop details,Cab No-"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\nDriver-"+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName()+" Mobile- "+assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber()+"\nyour reporting time is-"+tripDetailPO.geteFmFmEmployeeTravelRequest().getShiftTime()+"\nRegards SBOT 919962598888";
										messagingService.sendMessageToGuest(tripDetailPO.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,tripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());			
										messagingService.sendTripAsMessage(tripDetailPO.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getHostMobileNumber(), hostText,tripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());
									}
									else{
										messagingService.sendTripAsMessage(tripDetailPO.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,tripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());			
									}
								}
						} catch (Exception e) {
							e.printStackTrace();
						}
						}
					}); thread1.start();
					assignRoutes.get(0).setAllocationMsg("Y");	
					assignRoutes.get(0).setAllocationMsgDeliveryDate(new Date());
				 }
				if(assignRoutes.get(0).getEscortRequredFlag().equalsIgnoreCase("N")){
                      if(finalTripEmployees.get(finalTripEmployees.size()-1).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") && (finalTripEmployees.get(finalTripEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() >=19 || finalTripEmployees.get(finalTripEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() ==0 || finalTripEmployees.get(finalTripEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() <7)){
                	   List <EFmFmEscortCheckInPO> escortList=vehicleCheckIn.getAllCheckedInEscort(assignRoutes.get(0).geteFmFmClientBranchPO().getBranchId(),new Date());	         					
	 					if(!(escortList.isEmpty()) || escortList.size()!=0){
	 					EFmFmEscortCheckInPO checkInEscort=new EFmFmEscortCheckInPO();
	 					checkInEscort.setEscortCheckInId(escortList.get(0).getEscortCheckInId());
//	 					escortName=escortList.get(0).geteFmFmEscortMaster().getFirstName();
	 					assignRoutes.get(0).seteFmFmEscortCheckIn(checkInEscort);
	 					escortList.get(0).setStatus("N");
	 					vehicleCheckIn.update(escortList.get(0));
	 					}
	 					assignRoutes.get(0).setEscortRequredFlag("Y");
	 					assignRoutes.get(0).setVehicleStatus("F");
	 					assignRoutes.get(0).setBucketStatus("Y");
	 					assignRouteBO.update(assignRoutes.get(0));
	 				    vehicleMaster.setAvailableSeat(vehicleMaster.getAvailableSeat()-1);
	 				   vehicleCheckIn.update(vehicleMaster);
                   }
                   else{
                	   assignRoutes.get(0).setBucketStatus("Y");
                	   assignRoutes.get(0).setVehicleStatus("F");
                	   assignRouteBO.update(assignRoutes.get(0)); 
                   }
				}
				else{
					assignRoutes.get(0).setBucketStatus("Y");
					assignRoutes.get(0).setVehicleStatus("F");
					assignRouteBO.update(assignRoutes.get(0));
	 					
				}
		}
		List<EFmFmAssignRoutePO> assignRoutesDetail=assignRouteBO.closeParticularTrips(assignRoutePO);	
        EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(assignRoutesDetail.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
        particularDriverDetails.setStatus("allocated");
        approvalBO.update(particularDriverDetails);
        List<EFmFmDeviceMasterPO>deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(assignRoutesDetail.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(),assignRoutesDetail.get(0).geteFmFmClientBranchPO().getBranchId());
        deviceDetails.get(0).setStatus("allocated");
        vehicleCheckIn.update(deviceDetails.get(0));
		 if(assignRoutesDetail.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
				try{
				int a=assignRoutes.get(0).geteFmFmEscortCheckIn().getEscortCheckInId();
				responce.put("escortName", assignRoutes.get(0).geteFmFmEscortCheckIn().geteFmFmEscortMaster().getFirstName());	
				responce.put("escortId", assignRoutes.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());							
				}catch(Exception e){
					responce.put("escortId", "N");							
					responce.put("escortName", "Escort Required But Not Available");				 
				}
			}
			else{
				responce.put("escortId", "N");
				responce.put("escortName", "Not Required");							

			} 
		 
		    responce.put("availableCapacity", assignRoutesDetail.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getAvailableSeat());
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	
	@POST
	@Path("/saveeditbucket")
	public Response editBucketSaveClick(EFmFmAssignRoutePO assignRoutePO) throws ParseException, IOException{					
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
	    IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO"); 
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
	    Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmAssignRoutePO> assignRouteDetails=assignRouteBO.closeParticularTrips(assignRoutePO);
		if(assignRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId()!=assignRoutePO.getNewCheckInId()){        	
			//Start Update Old CheckIn Entry Details
			EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(assignRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
            particularDriverDetails.setStatus("A");
            approvalBO.update(particularDriverDetails);
            List<EFmFmDeviceMasterPO> deviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(assignRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), assignRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
            deviceDetails.get(0).setStatus("Y");           
            iVehicleCheckInBO.update(deviceDetails.get(0));
            //old vehicle CheckIn Entries
			List<EFmFmVehicleCheckInPO> oldCheckInEntryDetail=iVehicleCheckInBO.getCheckedInVehicleDetailsFromChecInId(assignRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
        	List<EFmFmVehicleCheckInPO>  newCheckInEntryDetail=iVehicleCheckInBO.getParticularCheckedInVehicles(assignRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(), assignRoutePO.getVehicleId());			
			EFmFmVehicleMasterPO newVehicleDetail=iVehicleCheckInBO.getParticularVehicleDetail(newCheckInEntryDetail.get(0).getEfmFmVehicleMaster().getVehicleId()); 
			int usedCapacity=assignRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getCapacity()-assignRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getAvailableSeat();
			if((usedCapacity-1)>newVehicleDetail.getAvailableSeat()){
				responce.put("status", "lessCapacity");
				responce.put("capacity", usedCapacity-1);
				return Response.ok(responce, MediaType.APPLICATION_JSON).build();	
			}
			
			if(assignRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y") && !(newVehicleDetail.getVehicleNumber().contains("DUMMY"))){
				final int assignRouteId=assignRouteDetails.get(0).getAssignRouteId();
				final String vehicleNumber=newVehicleDetail.getVehicleNumber();
				Thread thread1 = new Thread(new Runnable() {		
					public  void run() {
						// TODO Auto-generated method stub
						try {
							ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
							List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=iCabRequestBO.getDropTripAllSortedEmployees(assignRouteId);
							for(EFmFmEmployeeTripDetailPO tripDetailPO:employeeTripDetailPO){
								String text="";
								if(tripDetailPO.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("PICKUP")){
									
								   text="Dear employee your cab is changed,\nthe new cab is\n"+vehicleNumber+"\nYour schedule pickup time is\n"+tripDetailPO.geteFmFmEmployeeTravelRequest().getPickUpTime();
								}else{
									 text="Dear employee your cab is changed,\nthe new cab is\n"+vehicleNumber;
								}
								MessagingService messagingService=new MessagingService();
								messagingService.sendTripAsMessage(tripDetailPO.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,tripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());			
								}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}); thread1.start();
			}
			EFmFmVehicleMasterPO updatePreviousVehicleDetail=iVehicleCheckInBO.getParticularVehicleDetail(assignRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
			updatePreviousVehicleDetail.setStatus("A");
			updatePreviousVehicleDetail.setAvailableSeat(updatePreviousVehicleDetail.getCapacity()-1);
			iVehicleCheckInBO.update(updatePreviousVehicleDetail);
			//End Update Old CheckIn Entry Details

			//Start Update New CheckIn Entry Details
			EFmFmDriverMasterPO newDriverDetails=approvalBO.getParticularDriverDetail(newCheckInEntryDetail.get(0).getEfmFmDriverMaster().getDriverId());
			newDriverDetails.setStatus("allocated");
            approvalBO.update(newDriverDetails);
            
            List<EFmFmDeviceMasterPO> newDeviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(newCheckInEntryDetail.get(0).geteFmFmDeviceMaster().getDeviceId(), assignRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
            newDeviceDetails.get(0).setStatus("allocated");           
            iVehicleCheckInBO.update(deviceDetails.get(0));
        
            
            newVehicleDetail.setStatus("allocated");
            newVehicleDetail.setAvailableSeat(newVehicleDetail.getAvailableSeat()-(usedCapacity-1));
			iVehicleCheckInBO.update(newVehicleDetail);
			//End Update New CheckIn Entry Details
			newCheckInEntryDetail.get(0).setStatus("N");
			iVehicleCheckInBO.update(newCheckInEntryDetail.get(0));
			oldCheckInEntryDetail.get(0).setStatus("Y");
			iVehicleCheckInBO.update(oldCheckInEntryDetail.get(0));
			assignRouteDetails.get(0).setEfmFmVehicleCheckIn(newCheckInEntryDetail.get(0));			
		}
		
		if(assignRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y") && assignRoutePO.getEscortCheckInId()!=0){
			// Updating the change escort details			.
			List<EFmFmEscortCheckInPO> changeCheckInEscortDetails=iVehicleCheckInBO.getParticulaEscortDetailFromEscortId(assignRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(), assignRoutePO.getEscortCheckInId());
			changeCheckInEscortDetails.get(0).setStatus("N");
			iVehicleCheckInBO.update(changeCheckInEscortDetails.get(0));			
			// Updating the previous allocated escort details
			try{
			List<EFmFmEscortCheckInPO> allocatedCheckInEscortDetails=iVehicleCheckInBO.getParticulaEscortDetailFromEscortId(assignRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(), assignRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
			allocatedCheckInEscortDetails.get(0).setStatus("Y");
			iVehicleCheckInBO.update(allocatedCheckInEscortDetails.get(0));
			}catch(Exception e){}
			assignRouteDetails.get(0).seteFmFmEscortCheckIn(changeCheckInEscortDetails.get(0));			
		}
		    assignRouteBO.update(assignRouteDetails.get(0));
			List<EFmFmAssignRoutePO> routeDetails=assignRouteBO.closeParticularTrips(assignRoutePO);
			responce.put("status", "success");
			responce.put("availableCapacity", routeDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getAvailableSeat());
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
			
	}

	@POST
	@Path("/swapemployee")
	public Response swapEmployeeFromOneRouteToAnother(EFmFmAssignRoutePO assignRoutePO) throws Exception{					
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
	    IVehicleCheckInBO vehicleCheckIn = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");  
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		Map<String, Object>  responce = new HashMap<String,Object>();
		//Courrent Route Details....
		final List<EFmFmAssignRoutePO> currentRouteDetails=assignRouteBO.closeParticularTrips(assignRoutePO);	
		List<EFmFmEmployeeTripDetailPO> sortedDropEmployeeList=iCabRequestBO.getDropTripAllSortedEmployees(currentRouteDetails.get(0).getAssignRouteId());

		//Selected route Details		
		EFmFmAssignRoutePO eFmFmAssignRoutePO=new EFmFmAssignRoutePO();
		eFmFmAssignRoutePO.setAssignRouteId(assignRoutePO.getSelectedAssignRouteId());	
		EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
		eFmFmClientBranchPO.setBranchId(assignRoutePO.geteFmFmClientBranchPO().getBranchId());
		eFmFmAssignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		final List<EFmFmAssignRoutePO> selectedRouteDetails=assignRouteBO.closeParticularTrips(eFmFmAssignRoutePO);
		
		//Current route
		EFmFmAssignRoutePO currentAssignRoute=new EFmFmAssignRoutePO();
		currentAssignRoute.setAssignRouteId(currentRouteDetails.get(0).getAssignRouteId());
		EFmFmActualRoutTravelledPO actualRoutTravelled=new EFmFmActualRoutTravelledPO();
		actualRoutTravelled.setTravelledTime(new Date());			    		
		actualRoutTravelled.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		actualRoutTravelled.setEfmFmAssignRoute(currentAssignRoute);			

	if(selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getAvailableSeat()==1){		
		final List<EFmFmEmployeeTripDetailPO> requestTripDetail =iCabRequestBO.getParticularTriprEmployeeBoardedStatus(assignRoutePO.getRequestId(), currentRouteDetails.get(0).getAssignRouteId());	
		requestTripDetail.get(0).setEfmFmAssignRoute(selectedRouteDetails.get(0));
		iCabRequestBO.update(requestTripDetail.get(0));	
		List<EFmFmEmployeeTripDetailPO> tripEmployees=iCabRequestBO.getParticularTripAllEmployees(selectedRouteDetails.get(0).getAssignRouteId());
		List<EFmFmEmployeeTripDetailPO> allSortedEmployees=iCabRequestBO.getDropTripAllSortedEmployees(selectedRouteDetails.get(0).getAssignRouteId());
	if(allSortedEmployees.get(allSortedEmployees.size()-1).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") && selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("DROP") && selectedRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("N") && (allSortedEmployees.get(allSortedEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() >=19 ||allSortedEmployees.get(allSortedEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() ==0 || allSortedEmployees.get(allSortedEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() <7)){		
		requestTripDetail.get(0).setEfmFmAssignRoute(currentRouteDetails.get(0));
		iCabRequestBO.update(requestTripDetail.get(0));			
		responce.put("status", "failed");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
	else if(tripEmployees.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") && selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("PICKUP") && selectedRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("N") &&(allSortedEmployees.get(allSortedEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() >=20 ||allSortedEmployees.get(allSortedEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() ==0 || allSortedEmployees.get(allSortedEmployees.size()-1).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() <=7)){		
		requestTripDetail.get(0).setEfmFmAssignRoute(currentRouteDetails.get(0));
		iCabRequestBO.update(requestTripDetail.get(0));			
		responce.put("status", "failed");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	   }
		else{
			selectedRouteDetails.get(0).setVehicleStatus("F");
			iCabRequestBO.update(selectedRouteDetails.get(0));
			EFmFmVehicleMasterPO updateSelectedRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());				
			if(selectedRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y") && requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("MALE") ){				
				updateSelectedRouteVehicleCapacity.setAvailableSeat(updateSelectedRouteVehicleCapacity.getAvailableSeat());
				vehicleCheckIn.update(updateSelectedRouteVehicleCapacity);	
				try{
					List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(selectedRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),selectedRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
					checkInEscortDetails.get(0).setStatus("Y");
					vehicleCheckIn.update(checkInEscortDetails.get(0));
					selectedRouteDetails.get(0).setVehicleStatus("A");
					selectedRouteDetails.get(0).setEscortRequredFlag("N");
					iCabRequestBO.update(selectedRouteDetails.get(0));

                	}catch(Exception e){
                		selectedRouteDetails.get(0).setVehicleStatus("A");
                		selectedRouteDetails.get(0).setEscortRequredFlag("N");
						iCabRequestBO.update(selectedRouteDetails.get(0));
                	}
			}
			else{
				updateSelectedRouteVehicleCapacity.setAvailableSeat(updateSelectedRouteVehicleCapacity.getAvailableSeat()-1);
				vehicleCheckIn.update(updateSelectedRouteVehicleCapacity);

			}			
			//Update Current Route Details
			EFmFmVehicleMasterPO currentRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());							
			//Updating current  route Details
			if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y") && currentRouteDetails.get(0).getTripType().equalsIgnoreCase("DROP")){
//				   List<EFmFmEmployeeTripDetailPO> dropEmployeeSortedList=iCabRequestBO.getDropTripAllSortedEmployees(currentRouteDetails.get(0).getAssignRouteId());
				   if(sortedDropEmployeeList.get(sortedDropEmployeeList.size()-1).getEmpTripId()==requestTripDetail.get(0).getEmpTripId()){
					   currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+2);
						vehicleCheckIn.update(currentRouteVehicleCapacity);
						currentRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());							
                        if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
                        	try{
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
							currentRouteDetails.get(0).setVehicleStatus("A");
							currentRouteDetails.get(0).setEscortRequredFlag("N");
							iCabRequestBO.update(currentRouteDetails.get(0));

                        	}catch(Exception e){
                        		currentRouteDetails.get(0).setVehicleStatus("A");
                        		currentRouteDetails.get(0).setEscortRequredFlag("N");
    							iCabRequestBO.update(currentRouteDetails.get(0));
                        	}

                        }

						if(currentRouteVehicleCapacity.getAvailableSeat()+1==currentRouteVehicleCapacity.getCapacity()){				
							currentRouteVehicleCapacity.setStatus("A");
							EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
							eFmFmVehicleCheckInPO.setCheckInTime(new Date());
							eFmFmVehicleCheckInPO.setCheckInId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
							List<EFmFmVehicleCheckInPO> vehicleCheck=vehicleCheckIn.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
							vehicleCheck.get(0).setStatus("Y");
							vehicleCheckIn.update(vehicleCheck.get(0));
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
							
				        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				            particularDriverDetails.setStatus("A");
				            approvalBO.update(particularDriverDetails);
				            List<EFmFmDeviceMasterPO> deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
				            deviceDetails.get(0).setStatus("Y");
				            vehicleCheckIn.update(deviceDetails.get(0));
				          //when trip will start then this code will work start	            
				            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).getAssignRouteId());
				            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
					            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
									iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
								}
				            }
					    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
							if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
								for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
									assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
								}
							}
							//End
				           	assignRouteBO.deleteParticularAssignRoute(currentRouteDetails.get(0).getAssignRouteId());
							vehicleCheckIn.update(currentRouteVehicleCapacity);	
						}					   
					   
				   }
				   else{
					   currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+2);
						vehicleCheckIn.update(currentRouteVehicleCapacity);
						currentRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());							
                        if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
                        	try{
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
							currentRouteDetails.get(0).setVehicleStatus("A");
							currentRouteDetails.get(0).setEscortRequredFlag("N");
							iCabRequestBO.update(currentRouteDetails.get(0));

                        	}catch(Exception e){
                        		currentRouteDetails.get(0).setVehicleStatus("A");
                        		currentRouteDetails.get(0).setEscortRequredFlag("N");
    							iCabRequestBO.update(currentRouteDetails.get(0));
                        	}

                        }

						if(currentRouteVehicleCapacity.getAvailableSeat()+1==currentRouteVehicleCapacity.getCapacity()){				
							currentRouteVehicleCapacity.setStatus("A");
							EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
							eFmFmVehicleCheckInPO.setCheckInTime(new Date());
							eFmFmVehicleCheckInPO.setCheckInId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
							List<EFmFmVehicleCheckInPO> vehicleCheck=vehicleCheckIn.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
							vehicleCheck.get(0).setStatus("Y");
							vehicleCheckIn.update(vehicleCheck.get(0));
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
														
				        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				            particularDriverDetails.setStatus("A");
				            approvalBO.update(particularDriverDetails);
				            List<EFmFmDeviceMasterPO> deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
				            deviceDetails.get(0).setStatus("Y");
				            vehicleCheckIn.update(deviceDetails.get(0));
				          //when trip will start then this code will work start	            
				            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).getAssignRouteId());
				            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
					            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
									iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
								}
				            }
					    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
							if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
								for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
									assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
								}
							}
							//End
				           	assignRouteBO.deleteParticularAssignRoute(currentRouteDetails.get(0).getAssignRouteId());
							vehicleCheckIn.update(currentRouteVehicleCapacity);	
						}					   
				   }
			}
			 else if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y") && currentRouteDetails.get(0).getTripType().equalsIgnoreCase("PICKUP")){
				List<EFmFmEmployeeTripDetailPO> tripEmployeesForCurrentRoute=iCabRequestBO.getParticularTripAllEmployees(currentRouteDetails.get(0).getAssignRouteId());  
				if(tripEmployeesForCurrentRoute.get(0).getEmpTripId()==requestTripDetail.get(0).getEmpTripId()){
					   currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+2);
						vehicleCheckIn.update(currentRouteVehicleCapacity);
						currentRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());							
                     if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
                     	try{
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
							currentRouteDetails.get(0).setVehicleStatus("A");
							currentRouteDetails.get(0).setEscortRequredFlag("N");
							iCabRequestBO.update(currentRouteDetails.get(0));

                     	}catch(Exception e){
                     		currentRouteDetails.get(0).setVehicleStatus("A");
                     		currentRouteDetails.get(0).setEscortRequredFlag("N");
 							iCabRequestBO.update(currentRouteDetails.get(0));
                     	}

                     }						
					   if(currentRouteVehicleCapacity.getAvailableSeat()+1==currentRouteVehicleCapacity.getCapacity()){				
							currentRouteVehicleCapacity.setStatus("A");
							EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
							eFmFmVehicleCheckInPO.setCheckInTime(new Date());
							eFmFmVehicleCheckInPO.setCheckInId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
							List<EFmFmVehicleCheckInPO> vehicleCheck=vehicleCheckIn.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
							vehicleCheck.get(0).setStatus("Y");
							vehicleCheckIn.update(vehicleCheck.get(0));
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
							
				        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				            particularDriverDetails.setStatus("A");
				            approvalBO.update(particularDriverDetails);
				            List<EFmFmDeviceMasterPO> deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
				            deviceDetails.get(0).setStatus("Y");
				            vehicleCheckIn.update(deviceDetails.get(0));
				          //when trip will start then this code will work start	            
				            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).getAssignRouteId());
				            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
					            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
									iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
								}
				            }
					    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
							if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
								for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
									assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
								}
							}
							//End
				           	assignRouteBO.deleteParticularAssignRoute(currentRouteDetails.get(0).getAssignRouteId());
							vehicleCheckIn.update(currentRouteVehicleCapacity);	
						}					   
					   
				   }	
			}
			else{
				currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+1);				
				if(currentRouteVehicleCapacity.getAvailableSeat()+1==currentRouteVehicleCapacity.getCapacity()){				
					currentRouteVehicleCapacity.setStatus("A");
					EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
					eFmFmVehicleCheckInPO.setCheckInTime(new Date());
					eFmFmVehicleCheckInPO.setCheckInId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
					List<EFmFmVehicleCheckInPO> vehicleCheck=vehicleCheckIn.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
					vehicleCheck.get(0).setStatus("Y");
					vehicleCheckIn.update(vehicleCheck.get(0));
					
		        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
		            particularDriverDetails.setStatus("A");
		            approvalBO.update(particularDriverDetails);
		            List<EFmFmDeviceMasterPO> deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
		            deviceDetails.get(0).setStatus("Y");
		            vehicleCheckIn.update(deviceDetails.get(0));
		            //when trip will start then this code will work start	            
		            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).getAssignRouteId());
		            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
			            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
							iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
						}
		            }
			    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
					if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
						for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
							assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
						}
					}
					//End
					
		           	assignRouteBO.deleteParticularAssignRoute(currentRouteDetails.get(0).getAssignRouteId());	
					vehicleCheckIn.update(currentRouteVehicleCapacity);	
				}
				else
				vehicleCheckIn.update(currentRouteVehicleCapacity);	
			}
			log.info("currentRouteDetails.get(0)"+currentRouteDetails.get(0).getAssignRouteId());
			log.info("selectedRouteDetails.get(0)"+selectedRouteDetails.get(0).getAssignRouteId());
			Thread thread1 = new Thread(new Runnable() {		
				public void run() {
					// TODO Auto-generated method stub
					try{
					String text="";
					
					MessagingService messagingService=new MessagingService();
					if(selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("PICKUP") && currentRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y") && selectedRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")){				
					   text="Dear employee your cab is changed,\nthe new cab is\n"+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\nYour schedule pickup time is\n"+requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getPickUpTime();
						messagingService.sendTripAsMessage(requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getRequestType());						
					}
					if(selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("PICKUP") && currentRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("N") && selectedRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")){				
						text="Dear employee cab is allocated for your pickup\n"+selectedRouteDetails.get(0).getShiftTime()+" shift\nAllocated Cab number is\n"+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+" \nyour pickup time is "+requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getPickUpTime()+"\nVehicle Type "+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel();
						messagingService.sendTripAsMessage(requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getRequestType());						

					}
					if(selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("DROP") && currentRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")  && selectedRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")){
						 text="Dear employee your cab is changed,\nthe new cab is\n"+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber();
							messagingService.sendTripAsMessage(requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getRequestType());						
					}
					if(selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("DROP") && currentRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("N")  && selectedRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")){
					    text="Dear employee cab is allocated for your pickup\n"+selectedRouteDetails.get(0).getShiftTime()+" shift\nAllocated Cab number is\n"+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+" \nyour pickup time is "+requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getPickUpTime()+"\nVehicle Type "+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel();
						messagingService.sendTripAsMessage(requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getRequestType());						

					}
				}catch(Exception e){}
				}	

				
			});
		    thread1.start();
			
			responce.put("currentRouteAvailableCapacity", currentRouteVehicleCapacity.getAvailableSeat());	
			responce.put("selectedRouteAvailableCapacity", updateSelectedRouteVehicleCapacity.getAvailableSeat());	

		}
		}
		else{
			final List<EFmFmEmployeeTripDetailPO> requestTripDetail =iCabRequestBO.getParticularTriprEmployeeBoardedStatus(assignRoutePO.getRequestId(), currentRouteDetails.get(0).getAssignRouteId());
			requestTripDetail.get(0).setEfmFmAssignRoute(selectedRouteDetails.get(0));
			//Updating selected route details
			EFmFmVehicleMasterPO selectedRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());				
			selectedRouteVehicleCapacity.setAvailableSeat(selectedRouteVehicleCapacity.getAvailableSeat()-1);
			vehicleCheckIn.update(selectedRouteVehicleCapacity);
			iCabRequestBO.update(requestTripDetail.get(0));				
			EFmFmVehicleMasterPO currentRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());					
			if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y") && requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("FEMALE") ){				
				currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat());
				vehicleCheckIn.update(currentRouteVehicleCapacity);	
				try{
					List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
					checkInEscortDetails.get(0).setStatus("Y");
					vehicleCheckIn.update(checkInEscortDetails.get(0));
					selectedRouteDetails.get(0).setVehicleStatus("A");
					selectedRouteDetails.get(0).setEscortRequredFlag("N");
					iCabRequestBO.update(selectedRouteDetails.get(0));

                	}catch(Exception e){
                		selectedRouteDetails.get(0).setVehicleStatus("A");
                		selectedRouteDetails.get(0).setEscortRequredFlag("N");
						iCabRequestBO.update(selectedRouteDetails.get(0));
                	}
			}
/*			else{
				currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+1);
				vehicleCheckIn.update(currentRouteVehicleCapacity);

			}*/
			//Updating current  route Details
			if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y") && currentRouteDetails.get(0).getTripType().equalsIgnoreCase("DROP")){
//				   List<EFmFmEmployeeTripDetailPO> dropEmployeeSortedList=iCabRequestBO.getDropTripAllSortedEmployees(currentRouteDetails.get(0).getAssignRouteId());
				   if(sortedDropEmployeeList.get(sortedDropEmployeeList.size()-1).getEmpTripId()==requestTripDetail.get(0).getEmpTripId()){
					   currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+2);
						vehicleCheckIn.update(currentRouteVehicleCapacity);
						currentRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());							
                     if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
                     	try{
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
							currentRouteDetails.get(0).setVehicleStatus("A");
							currentRouteDetails.get(0).setEscortRequredFlag("N");
							iCabRequestBO.update(currentRouteDetails.get(0));

                     	}catch(Exception e){
                     		currentRouteDetails.get(0).setVehicleStatus("A");
                     		currentRouteDetails.get(0).setEscortRequredFlag("N");
 							iCabRequestBO.update(currentRouteDetails.get(0));
                     	}

                     }

						if(currentRouteVehicleCapacity.getAvailableSeat()+1==currentRouteVehicleCapacity.getCapacity()){				
							currentRouteVehicleCapacity.setStatus("A");
							EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
							eFmFmVehicleCheckInPO.setCheckInTime(new Date());
							eFmFmVehicleCheckInPO.setCheckInId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
							List<EFmFmVehicleCheckInPO> vehicleCheck=vehicleCheckIn.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
							vehicleCheck.get(0).setStatus("Y");
							vehicleCheckIn.update(vehicleCheck.get(0));
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
							
				        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				            particularDriverDetails.setStatus("A");
				            approvalBO.update(particularDriverDetails);
				            List<EFmFmDeviceMasterPO> deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
				            deviceDetails.get(0).setStatus("Y");
				            vehicleCheckIn.update(deviceDetails.get(0));
				          //when trip will start then this code will work start	            
				            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).getAssignRouteId());
				            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
					            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
									iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
								}
				            }
					    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
							if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
								for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
									assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
								}
							}
							//End							
				           	assignRouteBO.deleteParticularAssignRoute(currentRouteDetails.get(0).getAssignRouteId());
							vehicleCheckIn.update(currentRouteVehicleCapacity);	
						}					   
					   
				   }
				   else{
					   currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+2);
						vehicleCheckIn.update(currentRouteVehicleCapacity);
						currentRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());							
                     if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
                     	try{
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
							currentRouteDetails.get(0).setVehicleStatus("A");
							currentRouteDetails.get(0).setEscortRequredFlag("N");
							iCabRequestBO.update(currentRouteDetails.get(0));

                     	}catch(Exception e){
                     		currentRouteDetails.get(0).setVehicleStatus("A");
                     		currentRouteDetails.get(0).setEscortRequredFlag("N");
 							iCabRequestBO.update(currentRouteDetails.get(0));
                     	}

                     }

						if(currentRouteVehicleCapacity.getAvailableSeat()+1==currentRouteVehicleCapacity.getCapacity()){				
							currentRouteVehicleCapacity.setStatus("A");
							EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
							eFmFmVehicleCheckInPO.setCheckInTime(new Date());
							eFmFmVehicleCheckInPO.setCheckInId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
							List<EFmFmVehicleCheckInPO> vehicleCheck=vehicleCheckIn.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
							vehicleCheck.get(0).setStatus("Y");
							vehicleCheckIn.update(vehicleCheck.get(0));
							List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
							checkInEscortDetails.get(0).setStatus("Y");
							vehicleCheckIn.update(checkInEscortDetails.get(0));
							
				        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				            particularDriverDetails.setStatus("A");
				            approvalBO.update(particularDriverDetails);
				            List<EFmFmDeviceMasterPO> deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
				            deviceDetails.get(0).setStatus("Y");
				            vehicleCheckIn.update(deviceDetails.get(0));

				          //when trip will start then this code will work start	            
				            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).getAssignRouteId());
				            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
					            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
									iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
								}
				            }
					    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
							if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
								for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
									assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
								}
							}
							//End							
				           	assignRouteBO.deleteParticularAssignRoute(currentRouteDetails.get(0).getAssignRouteId());
							vehicleCheckIn.update(currentRouteVehicleCapacity);	
						}					   
				   }
			}
			 else if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y") && currentRouteDetails.get(0).getTripType().equalsIgnoreCase("PICKUP")){
					List<EFmFmEmployeeTripDetailPO> tripEmployeesForCurrentRoute=iCabRequestBO.getParticularTripAllEmployees(currentRouteDetails.get(0).getAssignRouteId());  
					if(tripEmployeesForCurrentRoute.get(0).getEmpTripId()==requestTripDetail.get(0).getEmpTripId()){
						   currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+2);
							vehicleCheckIn.update(currentRouteVehicleCapacity);
							currentRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());							
	                     if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
	                     	try{
								List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
								checkInEscortDetails.get(0).setStatus("Y");
								vehicleCheckIn.update(checkInEscortDetails.get(0));
								currentRouteDetails.get(0).setVehicleStatus("A");
								currentRouteDetails.get(0).setEscortRequredFlag("N");
								iCabRequestBO.update(currentRouteDetails.get(0));

	                     	}catch(Exception e){
	                     		currentRouteDetails.get(0).setVehicleStatus("A");
	                     		currentRouteDetails.get(0).setEscortRequredFlag("N");
	 							iCabRequestBO.update(currentRouteDetails.get(0));
	                     	}

	                     }						
						   if(currentRouteVehicleCapacity.getAvailableSeat()+1==currentRouteVehicleCapacity.getCapacity()){				
								currentRouteVehicleCapacity.setStatus("A");
								EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
								eFmFmVehicleCheckInPO.setCheckInTime(new Date());
								eFmFmVehicleCheckInPO.setCheckInId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
								List<EFmFmVehicleCheckInPO> vehicleCheck=vehicleCheckIn.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
								vehicleCheck.get(0).setStatus("Y");
								vehicleCheckIn.update(vehicleCheck.get(0));
								List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
								checkInEscortDetails.get(0).setStatus("Y");
								vehicleCheckIn.update(checkInEscortDetails.get(0));
					        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
					            particularDriverDetails.setStatus("A");
					            approvalBO.update(particularDriverDetails);
					            List<EFmFmDeviceMasterPO> deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
					            deviceDetails.get(0).setStatus("Y");
					            vehicleCheckIn.update(deviceDetails.get(0));
					            
					          //when trip will start then this code will work start	            
					            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).getAssignRouteId());
					            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
						            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
										iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
									}
					            }
						    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
								if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
									for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
										assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
									}
								}
								//End
					            
					            
					           	assignRouteBO.deleteParticularAssignRoute(currentRouteDetails.get(0).getAssignRouteId());
								vehicleCheckIn.update(currentRouteVehicleCapacity);	
							}					   
						   
					   }	
					else{

						   currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+2);
							vehicleCheckIn.update(currentRouteVehicleCapacity);
							currentRouteVehicleCapacity=vehicleCheckIn.getParticularVehicleDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());							
	                     if(currentRouteDetails.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
	                     	try{
								List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
								checkInEscortDetails.get(0).setStatus("Y");
								vehicleCheckIn.update(checkInEscortDetails.get(0));
								currentRouteDetails.get(0).setVehicleStatus("A");
								currentRouteDetails.get(0).setEscortRequredFlag("N");
								iCabRequestBO.update(currentRouteDetails.get(0));

	                     	}catch(Exception e){
	                     		currentRouteDetails.get(0).setVehicleStatus("A");
	                     		currentRouteDetails.get(0).setEscortRequredFlag("N");
	 							iCabRequestBO.update(currentRouteDetails.get(0));
	                     	}

	                     }

							if(currentRouteVehicleCapacity.getAvailableSeat()+1==currentRouteVehicleCapacity.getCapacity()){				
								currentRouteVehicleCapacity.setStatus("A");
								EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
								eFmFmVehicleCheckInPO.setCheckInTime(new Date());
								eFmFmVehicleCheckInPO.setCheckInId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
								List<EFmFmVehicleCheckInPO> vehicleCheck=vehicleCheckIn.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
								vehicleCheck.get(0).setStatus("Y");
								vehicleCheckIn.update(vehicleCheck.get(0));
								List<EFmFmEscortCheckInPO> checkInEscortDetails=vehicleCheckIn.getParticulaEscortDetailFromEscortId(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
								checkInEscortDetails.get(0).setStatus("Y");
								vehicleCheckIn.update(checkInEscortDetails.get(0));
								
					        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
					            particularDriverDetails.setStatus("A");
					            approvalBO.update(particularDriverDetails);
					            List<EFmFmDeviceMasterPO> deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
					            deviceDetails.get(0).setStatus("Y");
					            vehicleCheckIn.update(deviceDetails.get(0));
					            					            
					          //when trip will start then this code will work start	            
					            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).getAssignRouteId());
					            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
						            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
										iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
									}
					            }
						    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
								if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
									for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
										assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
									}
								}
								//End
					           	assignRouteBO.deleteParticularAssignRoute(currentRouteDetails.get(0).getAssignRouteId());
								vehicleCheckIn.update(currentRouteVehicleCapacity);	
							}					   
					   
					}
					
				}
			else{
				currentRouteVehicleCapacity.setAvailableSeat(currentRouteVehicleCapacity.getAvailableSeat()+1);				
				if(currentRouteVehicleCapacity.getAvailableSeat()+1==currentRouteVehicleCapacity.getCapacity()){				
					currentRouteVehicleCapacity.setStatus("A");
					EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
					eFmFmVehicleCheckInPO.setCheckInTime(new Date());
					eFmFmVehicleCheckInPO.setCheckInId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getCheckInId());
					List<EFmFmVehicleCheckInPO> vehicleCheck=vehicleCheckIn.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
					vehicleCheck.get(0).setStatus("Y");
					vehicleCheckIn.update(vehicleCheck.get(0));					
		        	EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
		            particularDriverDetails.setStatus("A");
		            approvalBO.update(particularDriverDetails);
		            List<EFmFmDeviceMasterPO> deviceDetails= vehicleCheckIn.getDeviceDetailsFromDeviceId(currentRouteDetails.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId());
		            deviceDetails.get(0).setStatus("Y");
		            vehicleCheckIn.update(deviceDetails.get(0));
		            
		            
		          //when trip will start then this code will work start	            
		            List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(currentRouteDetails.get(0).geteFmFmClientBranchPO().getBranchId(),currentRouteDetails.get(0).getAssignRouteId());
		            if(!(allAlerts.isEmpty()) || allAlerts.size()!=0){
			            for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
							iAlertBO.deleteAllAlerts(tripAlertsPO.getTripAlertsId());
						}
		            }
			    	List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
					if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
						for(EFmFmActualRoutTravelledPO actulaTraveled:actualRouteTravelled){
							assignRouteBO.deleteParticularActualTravelled(actulaTraveled.getTravelId());
						}
					}
					//End
		           	assignRouteBO.deleteParticularAssignRoute(currentRouteDetails.get(0).getAssignRouteId());	
					vehicleCheckIn.update(currentRouteVehicleCapacity);	
				}
				else{
					vehicleCheckIn.update(currentRouteVehicleCapacity);	
				}

			}
			
			log.info("currentRouteDetails.get(0)DOWN"+currentRouteDetails.get(0).getAssignRouteId()+"Id"+sortedDropEmployeeList.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getUserId());
			log.info("selectedRouteDetails.get(0)down"+selectedRouteDetails.get(0).getAssignRouteId()+"Id"+sortedDropEmployeeList.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getUserId());
			log.info("requestTripDetail"+requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
			Thread thread1 = new Thread(new Runnable() {		
				public void run() {
					// TODO Auto-generated method stub
					try{
					String text="";
					
					MessagingService messagingService=new MessagingService();
					if(selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("PICKUP") && currentRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y") && selectedRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")){				
					   text="Dear employee your cab is changed,\nthe new cab is\n"+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\nYour schedule pickup time is\n"+requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getPickUpTime();
						messagingService.sendTripAsMessage(requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getRequestType());						
					}
					if(selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("PICKUP") && currentRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("N") && selectedRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")){				
						text="Dear employee cab is allocated for your pickup\n"+selectedRouteDetails.get(0).getShiftTime()+" shift\nAllocated Cab number is\n"+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+" \nyour pickup time is "+requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getPickUpTime()+"\nVehicle Type "+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel();
						messagingService.sendTripAsMessage(requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getRequestType());						

					}
					if(selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("DROP") && currentRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")  && selectedRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")){
						 text="Dear employee your cab is changed,\nthe new cab is\n"+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber();
							messagingService.sendTripAsMessage(requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getRequestType());						
					}
					if(selectedRouteDetails.get(0).getTripType().equalsIgnoreCase("DROP") && currentRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("N")  && selectedRouteDetails.get(0).getAllocationMsg().equalsIgnoreCase("Y")){
					    text="Dear employee cab is allocated for your pickup\n"+selectedRouteDetails.get(0).getShiftTime()+" shift\nAllocated Cab number is\n"+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+" \nyour pickup time is "+requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getPickUpTime()+"\nVehicle Type "+selectedRouteDetails.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleModel();
						messagingService.sendTripAsMessage(requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(), text,requestTripDetail.get(0).geteFmFmEmployeeTravelRequest().getRequestType());						

					}
				}catch(Exception e){}
				}	

				
			});
		    thread1.start();
			responce.put("currentRouteAvailableCapacity", currentRouteVehicleCapacity.getAvailableSeat());	
			responce.put("selectedRouteAvailableCapacity", selectedRouteVehicleCapacity.getAvailableSeat());	

		}		
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	
	 //Get all Checked In vehicles,drivers,devices and Escorts	On click on edit bucket 
	 @POST
	 @Path("/checkedinentity")
	 public Response checkedInVehicleDriverDeviceAndEscort(EFmFmVendorMasterPO eFmFmVendorMasterPO){
	   IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
//		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");	

	   Map<String, Object>  vehicleCheckInList= new HashMap<String, Object>();  	   
	   EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
	   eFmFmClientBranchPO.setBranchId(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId());
	   EFmFmAssignRoutePO eFmFmAssignRoutePO=new EFmFmAssignRoutePO();
	   eFmFmAssignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
	   eFmFmAssignRoutePO.setAssignRouteId(eFmFmVendorMasterPO.getAssignRouteId());	   
		List<EFmFmVehicleCheckInPO> listOfCheckedInVehicle=iVehicleCheckInBO.getCheckedInVehicleDetailsForEditBucket(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId());	
		List<EFmFmAssignRoutePO> assignRoutes=assignRouteBO.closeParticularTrips(eFmFmAssignRoutePO);
		assignRoutes.get(0).setVehicleStatus("A");
		assignRoutes.get(0).setBucketStatus("N");
		assignRouteBO.update(assignRoutes.get(0));			
       /* EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
        particularDriverDetails.setStatus("A");
        approvalBO.update(particularDriverDetails);
        List<EFmFmDeviceMasterPO> deviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(assignRoutes.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(),assignRoutes.get(0).geteFmFmClientBranchPO().getBranchId());
        deviceDetails.get(0).setStatus("Y");
        iVehicleCheckInBO.update(deviceDetails.get(0));*/
		List<Map<String, Object>> checkInList= new ArrayList<Map<String, Object>>();  
	    List<Map<String, Object>> escortCheckInList= new ArrayList<Map<String, Object>>();
	if((!(listOfCheckedInVehicle.isEmpty())) || listOfCheckedInVehicle.size() !=0){			
	    for(EFmFmVehicleCheckInPO vehicleDetails:listOfCheckedInVehicle){
	     Map<String, Object>  checkCombinationDetails= new HashMap<String, Object>();
	     checkCombinationDetails.put("vehicleId", vehicleDetails.getEfmFmVehicleMaster().getVehicleId());
	     checkCombinationDetails.put("checkInId", vehicleDetails.getCheckInId());
	     checkCombinationDetails.put("vehicleNumber", vehicleDetails.getEfmFmVehicleMaster().getVehicleNumber());
	     checkCombinationDetails.put("vendorId", vehicleDetails.getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorId());	     
	     checkCombinationDetails.put("driverId", vehicleDetails.getEfmFmDriverMaster().getDriverId());
	     checkCombinationDetails.put("driverName", vehicleDetails.getEfmFmDriverMaster().getFirstName());
	     checkCombinationDetails.put("mobileNumber", vehicleDetails.getEfmFmDriverMaster().getMobileNumber()); 
	     checkCombinationDetails.put("deviceId", vehicleDetails.geteFmFmDeviceMaster().getDeviceId());     
	     checkCombinationDetails.put("deviceNumber", vehicleDetails.geteFmFmDeviceMaster().getMobileNumber());          
	     checkInList.add(checkCombinationDetails);
	    }
	   } 
    Map<String, Object>  checkCombinationDetails= new HashMap<String, Object>();
    checkCombinationDetails.put("vehicleId", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
    checkCombinationDetails.put("checkInId", assignRoutes.get(0).getEfmFmVehicleCheckIn().getCheckInId());
    checkCombinationDetails.put("vehicleNumber", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
    checkCombinationDetails.put("vendorId", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorId());	     
    checkCombinationDetails.put("driverId", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
    checkCombinationDetails.put("driverName", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
    checkCombinationDetails.put("mobileNumber", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber()); 
    checkCombinationDetails.put("deviceId", assignRoutes.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId());     
    checkCombinationDetails.put("deviceNumber", assignRoutes.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getMobileNumber());          
    checkInList.add(checkCombinationDetails);
	List <EFmFmEscortCheckInPO> escortList=iVehicleCheckInBO.getAllCheckedInEscort(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId(),new Date());	         					
	if(!(escortList.isEmpty()) || escortList.size()!=0){
	    for(EFmFmEscortCheckInPO escorts:escortList){     
		     Map<String, Object>  escortsDetails= new HashMap<String, Object>();     
		     escortsDetails.put("escortCheckInId", escorts.getEscortCheckInId());     
		     escortsDetails.put("escortName", escorts.geteFmFmEscortMaster().getFirstName());     
		     escortCheckInList.add(escortsDetails);
		    } 
		   }
	if(assignRoutes.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
	     Map<String, Object>  escortsDetails= new HashMap<String, Object>();     
	 try{
		int a=assignRoutes.get(0).geteFmFmEscortCheckIn().getEscortCheckInId();
		escortsDetails.put("escortName", assignRoutes.get(0).geteFmFmEscortCheckIn().geteFmFmEscortMaster().getFirstName());	
		escortsDetails.put("escortCheckInId", assignRoutes.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());
		escortCheckInList.add(escortsDetails);
		}catch(Exception e){}
	   }
	    vehicleCheckInList.put("escortDetails", escortCheckInList);
	    vehicleCheckInList.put("checkInList", checkInList);

	    vehicleCheckInList.put("vehicleStatus", "A");
	   return Response.ok(vehicleCheckInList, MediaType.APPLICATION_JSON).build();
	 }	
	 
	 
  
	 // get all available vehicles,driver and Devices for Changing the entites in	
	 @POST
	 @Path("/checkedindriver")
	 public Response getAllCheckedInDrivers(EFmFmVendorMasterPO eFmFmVendorMasterPO){   
	   IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
//		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
	   Map<String, Object>  vehicleCheckInList= new HashMap<String, Object>();
	   EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
	   assignRoutePO.setAssignRouteId(eFmFmVendorMasterPO.getAssignRouteId());
	   EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
	   eFmFmClientBranchPO.setBranchId(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId());
	   assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
//	   List<EFmFmAssignRoutePO> assignRoutes=assignRouteBO.closeParticularTrips(assignRoutePO);
	   List<EFmFmVehicleCheckInPO> allDriverByVendor =iVehicleCheckInBO.getAllCheckedInDriversByVendor(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId(), eFmFmVendorMasterPO.getVendorId());
	   List<Map<String, Object>> checkInList= new ArrayList<Map<String, Object>>();
	   if(!(allDriverByVendor.isEmpty()) || allDriverByVendor.size()!=0){
	    for(EFmFmVehicleCheckInPO driverDetails:allDriverByVendor){     
	     Map<String, Object>  driverList= new HashMap<String, Object>();     
	     driverList.put("driverId", driverDetails.getEfmFmDriverMaster().getDriverId());
	     driverList.put("driverName", driverDetails.getEfmFmDriverMaster().getFirstName());
	     driverList.put("mobileNumber", driverDetails.getEfmFmDriverMaster().getMobileNumber());     
	     driverList.put("vendorId", driverDetails.getEfmFmDriverMaster().getEfmFmVendorMaster().getVendorId());
	     checkInList.add(driverList);
	    } 
	   } 
	    /*Map<String, Object>  driverList= new HashMap<String, Object>();     
	     driverList.put("driverId", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
	     driverList.put("driverName", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
	     driverList.put("mobileNumber", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber()); 
	     driverList.put("vendorName", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getEfmFmVendorMaster().getVendorName());
	     driverList.put("vendorId", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getEfmFmVendorMaster().getVendorId());
	     checkInList.add(driverList);*/ 

	    vehicleCheckInList.put("driverDetails", checkInList);
	   return Response.ok(vehicleCheckInList, MediaType.APPLICATION_JSON).build();
	 }
	 
	 @POST
	 @Path("/checkedinvehicle")
	 public Response getAllCheckedInVehicles(EFmFmVendorMasterPO eFmFmVendorMasterPO){   
	   IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
	   Map<String, Object>  vehicleCheckInList= new HashMap<String, Object>();  
	   EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
	   assignRoutePO.setAssignRouteId(eFmFmVendorMasterPO.getAssignRouteId());
	   EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
	   eFmFmClientBranchPO.setBranchId(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId());
	   assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
	   List<EFmFmVehicleCheckInPO> allVehicleByVendor=iVehicleCheckInBO.getAllCheckedInVehiclesByVendor(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId(), eFmFmVendorMasterPO.getVendorId());
	   List<Map<String, Object>> checkInList= new ArrayList<Map<String, Object>>();
	   if(!(allVehicleByVendor.isEmpty()) || allVehicleByVendor.size()!=0){
	    for(EFmFmVehicleCheckInPO vehicleDetails:allVehicleByVendor){
	     Map<String, Object>  vehicleList= new HashMap<String, Object>();
	     vehicleList.put("vehicleId", vehicleDetails.getEfmFmVehicleMaster().getVehicleId());
	     vehicleList.put("vehicleNumber", vehicleDetails.getEfmFmVehicleMaster().getVehicleNumber());
	     vehicleList.put("vendorName", vehicleDetails.getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
	     vehicleList.put("vendorId", vehicleDetails.getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorId());
	     checkInList.add(vehicleList);
	    }
	   }
	   /* Map<String, Object>  vehicleList= new HashMap<String, Object>();
	     vehicleList.put("vehicleId", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
	     vehicleList.put("vehicleNumber", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
	     vehicleList.put("vendorName", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
	     vehicleList.put("vendorId", assignRoutes.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorId());
	     checkInList.add(vehicleList);*/

	    vehicleCheckInList.put("vehicleDetails", checkInList); 
	   return Response.ok(vehicleCheckInList, MediaType.APPLICATION_JSON).build();
	 }
	
	 //Get all Checked In vehicles,drivers,devices and zones. 
	 @POST
	 @Path("/createbucket")
	 public Response createDummyBucket(EFmFmVendorMasterPO eFmFmVendorMasterPO){
//	   IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");		
 		List<EFmFmClientRouteMappingPO> allRoutes=iRouteDetailBO.getAllRoutesOfParticularClient(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId());
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		List<EFmFmTripTimingMasterPO> shiftTimeDetails=iCabRequestBO.listOfShiftTime(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId());		
	   Map<String, Object>  vehicleCheckInList= new HashMap<String, Object>();  	   
	   EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
	   eFmFmClientBranchPO.setBranchId(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId());
	   EFmFmAssignRoutePO eFmFmAssignRoutePO=new EFmFmAssignRoutePO();
	   eFmFmAssignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
	   eFmFmAssignRoutePO.setAssignRouteId(eFmFmVendorMasterPO.getAssignRouteId());	   
//		List<EFmFmVehicleCheckInPO> listOfCheckedInVehicle=iVehicleCheckInBO.getCheckedInVehicleDetails(eFmFmVendorMasterPO.geteFmFmClientBranchPO().getBranchId(),new Date());	
	   	List<Map<String, Object>> shitTimings = new ArrayList<Map<String, Object>>();
	   	List<Map<String, Object>> allRoutesData = new ArrayList<Map<String, Object>>();
	  if((!(shiftTimeDetails.isEmpty())) || shiftTimeDetails.size() !=0){			
			for(EFmFmTripTimingMasterPO shiftiming:shiftTimeDetails){				
				Map<String, Object>  shifList= new HashMap<String, Object>();
					shifList.put("shiftTime", shiftTimeFormater.format(shiftiming.getShiftTime()));				
					shitTimings.add(shifList);
			}
		
		}
	if((!(allRoutes.isEmpty())) || allRoutes.size() !=0){
			for(EFmFmClientRouteMappingPO routeDetails:allRoutes){				
				Map<String, Object>  routeName= new HashMap<String, Object>();
				routeName.put("routeName", routeDetails.geteFmFmZoneMaster().getZoneName());
				routeName.put("routeId", routeDetails.geteFmFmZoneMaster().getZoneId());				
				allRoutesData.add(routeName);									
			}
		}
	/*if((!(listOfCheckedInVehicle.isEmpty())) || listOfCheckedInVehicle.size() !=0){			
	    for(EFmFmVehicleCheckInPO vehicleDetails:listOfCheckedInVehicle){
	     Map<String, Object>  vehicleList= new HashMap<String, Object>();
	     vehicleList.put("vehicleId", vehicleDetails.getEfmFmVehicleMaster().getVehicleId());
	     vehicleList.put("vehicleNumber", vehicleDetails.getEfmFmVehicleMaster().getVehicleNumber());
	     vehicleList.put("vendorName", vehicleDetails.getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
	     vehicleList.put("vendorId", vehicleDetails.getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorId());
	     checkInList.add(vehicleList);
	    } 
	   }
	if((!(listOfCheckedInVehicle.isEmpty())) || listOfCheckedInVehicle.size() !=0){			
	    for(EFmFmVehicleCheckInPO driverDetails:listOfCheckedInVehicle){     
	     Map<String, Object>  driverList= new HashMap<String, Object>();     
	     driverList.put("driverId", driverDetails.getEfmFmDriverMaster().getDriverId());
	     driverList.put("driverName", driverDetails.getEfmFmDriverMaster().getFirstName());
	     driverList.put("mobileNumber", driverDetails.getEfmFmDriverMaster().getMobileNumber()); 
	     driverList.put("vendorName", driverDetails.getEfmFmDriverMaster().getEfmFmVendorMaster().getVendorName());
	     driverList.put("vendorId", driverDetails.getEfmFmDriverMaster().getEfmFmVendorMaster().getVendorId());
	     driverCheckInList.add(driverList);
	    } 
	   }
	if((!(listOfCheckedInVehicle.isEmpty())) || listOfCheckedInVehicle.size() !=0){			
	    for(EFmFmVehicleCheckInPO deviceDetails:listOfCheckedInVehicle){     
	     Map<String, Object>  deviceList= new HashMap<String, Object>();     
	     deviceList.put("deviceId", deviceDetails.geteFmFmDeviceMaster().getDeviceId());     
	     deviceList.put("mobileNumber", deviceDetails.geteFmFmDeviceMaster().getMobileNumber());     
	     deviceCheckInList.add(deviceList);
	    } 
	   }*/
		vehicleCheckInList.put("shiftTimings", shitTimings);
		vehicleCheckInList.put("routesData", allRoutesData);
	//	vehicleCheckInList.put("vehicleDetails", checkInList);
//		vehicleCheckInList.put("driverDetails", driverCheckInList);
	//	vehicleCheckInList.put("deviceDetails", deviceCheckInList);
	   return Response.ok(vehicleCheckInList, MediaType.APPLICATION_JSON).build();
	 }	
	 
	 //Create new bucket save click.	 	 
	    @POST
		@Path("/savecreatebucket")
		public Response createNewBucketSaveClick(EFmFmAssignRoutePO assignRoutePO) throws ParseException, IOException{					
			IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		    IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO"); 
			IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");	
			Map<String, Object>  requests = new HashMap<String,Object>();	
			Map<String, Object>  responce = new HashMap<String,Object>();	
			ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
			//Query for fetching the Available checked in dummy entity
			List<EFmFmVehicleCheckInPO> checkedInEntity=iVehicleCheckInBO.getAllCheckedInVehicleForCreatingNewBucket(assignRoutePO.geteFmFmClientBranchPO().getBranchId());
			if((checkedInEntity.isEmpty()) || checkedInEntity.size() ==0){			
				  responce.put("status", "fail");
			    return Response.ok(responce, MediaType.APPLICATION_JSON).build();				
			}
			  //Creating new dummy route with out employees
			  DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
			  String shiftDate=assignRoutePO.getTime();
			  Date shift  = (Date) shiftFormate.parse(shiftDate);				 
			  java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
              EFmFmAssignRoutePO assignRoute = new EFmFmAssignRoutePO();
              EFmFmVehicleCheckInPO vehicleCheckInPO = new EFmFmVehicleCheckInPO();
              vehicleCheckInPO.setCheckInId(checkedInEntity.get(0).getCheckInId());
              checkedInEntity.get(0).setStatus("N");            
              assignRoute.setEfmFmVehicleCheckIn(vehicleCheckInPO);
              EFmFmRouteAreaMappingPO routeAreaMapping = new EFmFmRouteAreaMappingPO();
			  List<EFmFmRouteAreaMappingPO> allZoneAreas=iRouteDetailBO.getAllAreasFromZoneId(assignRoutePO.getSelectedAssignRouteId());          
              routeAreaMapping.setRouteAreaId(allZoneAreas.get(0).getRouteAreaId());           
              assignRoute.seteFmFmRouteAreaMapping(routeAreaMapping);
              assignRoute.setEscortRequredFlag("N");
              assignRoute.setAllocationMsg("N");
              assignRoute.setShiftTime(shiftTime);
              assignRoute.setBucketStatus("N");
              assignRoute.setTripStatus("allocated");
              assignRoute.setTripAssignDate(new Date());
              assignRoute.setVehicleStatus("A");
              EFmFmClientBranchPO eFmFmClientBranchPO = new EFmFmClientBranchPO();
              eFmFmClientBranchPO.setBranchId(assignRoutePO.geteFmFmClientBranchPO().getBranchId());
              assignRoute.seteFmFmClientBranchPO(eFmFmClientBranchPO);
              assignRoute.setTripType(assignRoutePO.getTripType());
              iVehicleCheckInBO.update(checkedInEntity.get(0));
              assignRouteBO.save(assignRoute);             
              List<EFmFmAssignRoutePO> assignRouteDetail=iCabRequestBO.getLastRouteDetails(checkedInEntity.get(0).getCheckInId(), assignRoutePO.geteFmFmClientBranchPO().getBranchId(), assignRoutePO.getTripType());
  			requests.put("escortRequired", "N");
  			requests.put("shiftTime",shiftDate);
  			requests.put("vehicleStatus","A");
  			requests.put("bucketStatus","N");
  			requests.put("driverName", checkedInEntity.get(0).getEfmFmDriverMaster().getFirstName());
  			requests.put("driverNumber", checkedInEntity.get(0).getEfmFmDriverMaster().getMobileNumber());
  			requests.put("vehicleNumber", checkedInEntity.get(0).getEfmFmVehicleMaster().getVehicleNumber());
  			requests.put("vendorId", checkedInEntity.get(0).getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorId());
  			requests.put("driverId", checkedInEntity.get(0).getEfmFmDriverMaster().getDriverId());
  			requests.put("vehicleId", checkedInEntity.get(0).getEfmFmVehicleMaster().getVehicleId());
  			requests.put("vehicleAvailableCapacity",checkedInEntity.get(0).getEfmFmVehicleMaster().getAvailableSeat());
  			requests.put("capacity", checkedInEntity.get(0).getEfmFmVehicleMaster().getCapacity());
  			requests.put("deviceNumber",checkedInEntity.get(0).geteFmFmDeviceMaster().getDeviceId());
  		    requests.put("routeName", allZoneAreas.get(0).geteFmFmZoneMaster().getZoneName());
			requests.put("escortName", "Not Required");							
			requests.put("tripStatus", "allocated");
			requests.put("tripType",assignRoutePO.getTripType());
			requests.put("routeId", assignRouteDetail.get(assignRouteDetail.size()-1).getAssignRouteId());
			return Response.ok(requests, MediaType.APPLICATION_JSON).build();				
		}	 
	    
	    //Search employees inside the all routes employeeId based.	 	 
	    @POST
		@Path("/employeesearchinroute")
		public Response employeeSearchInsideBucket(EFmFmAssignRoutePO assignRoutePO) throws ParseException, IOException{					
			IEmployeeDetailBO iEmployeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		    IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO"); 
			IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");	
			Map<String, Object>  requests = new HashMap<String,Object>();
			Map<String, Object>  allRoutesSingleObj = new HashMap<String, Object>();	
			List<Map<String, Object>> allRoutes= new ArrayList<Map<String, Object>>();
			ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
			IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
			try{
			if(assignRoutePO.getSearchType().equalsIgnoreCase("vehicle")){
				List<EFmFmVehicleMasterPO> eFmFmVehicleMaster=iVehicleCheckInBO.getVehicleDetailsFromVehicleNumber(assignRoutePO.getEmployeeId(), assignRoutePO.geteFmFmClientBranchPO().getBranchId());				
			    if((!(eFmFmVehicleMaster.isEmpty())) || eFmFmVehicleMaster.size() !=0){
				List<EFmFmVehicleCheckInPO> vehicleCheckIn=iVehicleCheckInBO.getParticularCheckedInVehicles(assignRoutePO.geteFmFmClientBranchPO().getBranchId(), eFmFmVehicleMaster.get(0).getVehicleId());
				if((!(vehicleCheckIn.isEmpty())) || vehicleCheckIn.size() !=0){
				List<EFmFmAssignRoutePO> activeRouteDetail=iRouteDetailBO.getVehicleDetailFromVehicleId(assignRoutePO.geteFmFmClientBranchPO().getBranchId(), vehicleCheckIn.get(0).getCheckInId());
				if((!(activeRouteDetail.isEmpty())) || activeRouteDetail.size() !=0){
					List<EFmFmAssignRoutePO> routesInZone=assignRouteBO.getAllRoutesInsideZone(assignRoutePO.geteFmFmClientBranchPO().getBranchId(), activeRouteDetail.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
				    requests.put("routeName", activeRouteDetail.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());		    
				    requests.put("NumberOfRoutes", routesInZone.size());
		            requests.put("routeId",activeRouteDetail.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
		            allRoutes.add(requests);
				}
				}
				}
			}
		  else{
			List<EFmFmUserMasterPO> requestEmployeeIdExitCheck=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(assignRoutePO.getEmployeeId(), assignRoutePO.geteFmFmClientBranchPO().getBranchId());			
			List<EFmFmEmployeeTripDetailPO> allocatedEmployeeDetail=iCabRequestBO.getAllocatedEmployeeDetail(requestEmployeeIdExitCheck.get(0).getUserId(), assignRoutePO.geteFmFmClientBranchPO().getBranchId(), new Date());
			if((!(allocatedEmployeeDetail.isEmpty())) || allocatedEmployeeDetail.size() !=0){
				assignRoutePO.setAssignRouteId(allocatedEmployeeDetail.get(0).getEfmFmAssignRoute().getAssignRouteId());
	            List<EFmFmAssignRoutePO> assignRouteDetail=iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);				
				List<EFmFmAssignRoutePO> routesInZone=assignRouteBO.getAllRoutesInsideZone(assignRoutePO.geteFmFmClientBranchPO().getBranchId(), assignRouteDetail.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
			    requests.put("routeName", assignRouteDetail.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());		    
			    requests.put("NumberOfRoutes", routesInZone.size());
	            requests.put("routeId",assignRouteDetail.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
	            allRoutes.add(requests);
	      }
			}
			}catch(Exception e){
				log.error(e);
			}
			 allRoutesSingleObj.put("routeDetails", allRoutes);			 
			return Response.ok(allRoutesSingleObj, MediaType.APPLICATION_JSON).build();				
		}  
	    
	    
	    //Search vehicle inside the all routes .
	    
	    @POST
		@Path("/vehiclesearchinroute")
		public Response vehicleSearchInsideBucket(EFmFmAssignRoutePO assignRoutePO) throws ParseException, IOException{					
			IEmployeeDetailBO iEmployeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
			Map<String, Object>  requests = new HashMap<String,Object>();
			Map<String, Object>  allRoutesSingleObj = new HashMap<String, Object>();	
			List<Map<String, Object>> allRoutes= new ArrayList<Map<String, Object>>();
			ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
			IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
			try{
			List<EFmFmUserMasterPO> requestEmployeeIdExitCheck=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(assignRoutePO.getEmployeeId(), assignRoutePO.geteFmFmClientBranchPO().getBranchId());			
			List<EFmFmEmployeeTripDetailPO> allocatedEmployeeDetail=iCabRequestBO.getAllocatedEmployeeDetail(requestEmployeeIdExitCheck.get(0).getUserId(), assignRoutePO.geteFmFmClientBranchPO().getBranchId(), new Date());
			if((!(allocatedEmployeeDetail.isEmpty())) || allocatedEmployeeDetail.size() !=0){
				assignRoutePO.setAssignRouteId(allocatedEmployeeDetail.get(0).getEfmFmAssignRoute().getAssignRouteId());
	            List<EFmFmAssignRoutePO> assignRouteDetail=iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);				
				List<EFmFmAssignRoutePO> routesInZone=assignRouteBO.getAllRoutesInsideZone(assignRoutePO.geteFmFmClientBranchPO().getBranchId(), assignRouteDetail.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
			    requests.put("routeName", assignRouteDetail.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());		    
			    requests.put("NumberOfRoutes", routesInZone.size());
	            requests.put("routeId",assignRouteDetail.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
	            allRoutes.add(requests);
	      }
			}catch(Exception e){}
			 allRoutesSingleObj.put("routeDetails", allRoutes);			 
			return Response.ok(allRoutesSingleObj, MediaType.APPLICATION_JSON).build();				
		}  
	    
	    //Trip type and Shift Time search inside the all routes	 	 
	    @POST
		@Path("/triptypesearchinroute")
		public Response tripTypeAndShiftTimeSearch(EFmFmAssignRoutePO assignRoutePO) throws ParseException, IOException{
			IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
			Map<String, Object>  allRoutesSingleObj = new HashMap<String, Object>();
			List<EFmFmAssignRoutePO> assignRouteDetail=new ArrayList<EFmFmAssignRoutePO>();
			List<String> zones=new ArrayList<String>();
			List<Map<String, Object>> allRoutes= new ArrayList<Map<String, Object>>();
			DateFormat timeformate = new SimpleDateFormat("HH:mm:ss");
			String shiftDate=assignRoutePO.getTime();
			Date shift  = (Date) timeformate.parse(shiftDate);				 
			java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
			assignRoutePO.setShiftTime(shiftTime);
			if(assignRoutePO.getSearchType().equalsIgnoreCase("All")){
	            assignRouteDetail=assignRouteBO.getAllRoutesBasedOnTripTypeAndShiftTime(assignRoutePO);
			}
			else if(assignRoutePO.getSearchType().equalsIgnoreCase("Close")){
	            assignRouteDetail=assignRouteBO.getAllBucketClosedRoutes(assignRoutePO);
			}
			else if(assignRoutePO.getSearchType().equalsIgnoreCase("Started")){
	            assignRouteDetail=assignRouteBO.getAllStartedRoutes(assignRoutePO);
			}
			else if(assignRoutePO.getSearchType().equalsIgnoreCase("open")){
	            assignRouteDetail=assignRouteBO.getAllOpenBucketRoutes(assignRoutePO);
			}
			
			if((!(assignRouteDetail.isEmpty())) || assignRouteDetail.size() !=0){
	  		  for(EFmFmAssignRoutePO routeDetail:assignRouteDetail){
	  			if(!(zones.contains(routeDetail.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName()))){
	  			Map<String, Object>  requests = new HashMap<String,Object>();
				zones.add(routeDetail.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
				List<EFmFmAssignRoutePO> routesInZone=assignRouteBO.getAllRoutesInsideZone(assignRoutePO.geteFmFmClientBranchPO().getBranchId(),routeDetail.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
			    requests.put("routeName", routeDetail.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());		    
			    requests.put("NumberOfRoutes", routesInZone.size());
	            requests.put("routeId",routeDetail.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
	            allRoutes.add(requests);
	  			}
	  			
	  		  }
	    }
			 allRoutesSingleObj.put("routeDetails", allRoutes);
			return Response.ok(allRoutesSingleObj, MediaType.APPLICATION_JSON).build();				
		}  
	
}