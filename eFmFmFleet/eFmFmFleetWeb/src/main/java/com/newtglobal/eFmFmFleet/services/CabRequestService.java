package com.newtglobal.eFmFmFleet.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IAlertBO;
import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.IAssignRouteBO;
import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IEmployeeDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IRouteDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IUserMasterBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.eFmFmFleet.CalculateDistance;
import com.newtglobal.eFmFmFleet.eFmFmFleet.MessagingService;
import com.newtglobal.eFmFmFleet.model.EFmFmActualRoutTravelledPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAlertTypeMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAreaMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientProjectDetailsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientRouteMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientUserRolePO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRoleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripTimingMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmZoneMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import java.util.Base64;

@Component
@Path("/trip")
@Consumes("application/json")
@Produces("application/json")
public class CabRequestService {

	private static Log log = LogFactory.getLog(CabRequestService.class);	

	/**
	 * The employeeTravelRequstDetails method implemented.
	 * for getting the list of travel request from employeeTravelREquestTable.   
	 *
	 * @author  Rajan R
	 * 
	 * @since   2015-05-13 
	 * 
	 * @return Employee Travel Desk Module.
	 */	
	private static  byte[] key;
	private static String keyString ="ZkcB7U7OSeKArCiA0rBuEdQSIGM=";  
	@POST
	@Path("/employeeTravelRequest")
	public Response employeeTravelRequestDetails(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO) throws ParseException{
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		List<Map<String, Object>> shitTimings = new ArrayList<Map<String, Object>>();
		Map<String, Object>  responce= new HashMap<String, Object>();
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat  shiftDateFormater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		DateFormat  shiftTimeFormaters = new SimpleDateFormat("HH:mm:ss");
		employeeTravelRequestPO.setRequestDate(new Date());			
		List<EFmFmTripTimingMasterPO> shiftTimeDetails=iCabRequestBO.listOfShiftTime(employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
		if((!(shiftTimeDetails.isEmpty())) || shiftTimeDetails.size() !=0){			
			for(EFmFmTripTimingMasterPO shiftiming:shiftTimeDetails){				
				Map<String, Object>  requestList= new HashMap<String, Object>();
				requestList.put("shiftTime", shiftTimeFormater.format(shiftiming.getShiftTime()));				
				shitTimings.add(requestList);
			}		
		}
		List<EFmFmEmployeeTravelRequestPO> travelDetails=iCabRequestBO.listOfAdhocAndGuestTravelRequests(employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
		List<Map<String, Object>> travelRequestList = new ArrayList<Map<String, Object>>();
		if((!(travelDetails.isEmpty())) || travelDetails.size() !=0){			
			for(EFmFmEmployeeTravelRequestPO allTravelRequest:travelDetails){				
				Map<String, Object>  requestList= new HashMap<String, Object>();				
				requestList.put("employeeId", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getEmployeeId());
				requestList.put("requestId", allTravelRequest.getRequestId());
				String cabAvailableTime=formatter.format(allTravelRequest.getRequestDate())+" "+allTravelRequest.getShiftTime();
				if(allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType().equalsIgnoreCase("DROP")){
					requestList.put("pickUpTime",allTravelRequest.getDropSequence());

					if(shiftDateFormater.parse(cabAvailableTime).getTime()<System.currentTimeMillis()+900000){
						requestList.put("cabAvailable", "Cab not available");															
					}
					else{
						requestList.put("cabAvailable", "Waiting");															
					}
				}
				else{
					try{
						requestList.put("pickUpTime",shiftTimeFormater.format(allTravelRequest.getPickUpTime()));
					}catch(Exception e){
						requestList.put("pickUpTime","0");
					}
					if(shiftDateFormater.parse(cabAvailableTime).getTime()<System.currentTimeMillis()+16200000){
						requestList.put("cabAvailable", "Cab not available");															
					}
					else{
						requestList.put("cabAvailable", "Waiting");															
					}
				}
				requestList.put("tripDate", formatter.format(allTravelRequest.getRequestDate()));
				requestList.put("weekOffs",allTravelRequest.getEfmFmUserMaster().getWeekOffDays() );
				requestList.put("tripType", allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType());
				requestList.put("requestType", allTravelRequest.getRequestType());
				requestList.put("tripTime", shiftTimeFormaters.format(allTravelRequest.getShiftTime()));
				requestList.put("employeeName", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName());					
				requestList.put("employeeRouteName", allTravelRequest.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
				requestList.put("employeeRouteId", allTravelRequest.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
				requestList.put("employeeAreaName", allTravelRequest.geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaName());
				requestList.put("employeeAreaId", allTravelRequest.geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaId());
				requestList.put("employeePickUpTime", allTravelRequest.geteFmFmEmployeeRequestMaster().getPickUpTime());					
				requestList.put("employeeAddress", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getAddress());
				requestList.put("employeeWaypoints", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getLatitudeLongitude());							
				travelRequestList.add(requestList);
			}									

		}
		responce.put("shifts", shitTimings);
		responce.put("requests", travelRequestList);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	//Get shift wise employee requests
	@POST
	@Path("/employeeshiftwiserequest")
	public Response employeeShiftWiseequestDetails(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO) throws ParseException{
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		Map<String, Object>  responce= new HashMap<String, Object>();
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat  shiftDateFormater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		DateFormat  shiftTimeFormaters = new SimpleDateFormat("HH:mm:ss");
		employeeTravelRequestPO.setRequestDate(new Date());		
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		String shiftDate=employeeTravelRequestPO.getTime();
		Date shift  = (Date) shiftFormate.parse(shiftDate);				 
		java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
		List<EFmFmEmployeeTravelRequestPO> travelDetails=iCabRequestBO.listOfTravelRequestByShiftWice(employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),shiftTime);		
		List<Map<String, Object>> travelRequestList = new ArrayList<Map<String, Object>>();
		if((!(travelDetails.isEmpty())) || travelDetails.size() !=0){			
			for(EFmFmEmployeeTravelRequestPO allTravelRequest:travelDetails){				
				Map<String, Object>  requestList= new HashMap<String, Object>();
				requestList.put("employeeId", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getEmployeeId());
				requestList.put("requestId", allTravelRequest.getRequestId());
				String cabAvailableTime=formatter.format(allTravelRequest.getRequestDate())+" "+allTravelRequest.getShiftTime();
				if(allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType().equalsIgnoreCase("DROP")){
					requestList.put("pickUpTime",allTravelRequest.getDropSequence());

					if(shiftDateFormater.parse(cabAvailableTime).getTime()<System.currentTimeMillis()+900000){
						requestList.put("cabAvailable", "Cab not available");															
					}
					else{
						requestList.put("cabAvailable", "Waiting");															
					}
				}
				else{
					try{
						requestList.put("pickUpTime",shiftTimeFormater.format(allTravelRequest.getPickUpTime()));
					}catch(Exception e){
						requestList.put("pickUpTime","0");
					}
					if(shiftDateFormater.parse(cabAvailableTime).getTime()<System.currentTimeMillis()+16200000){
						requestList.put("cabAvailable", "Cab not available");															
					}
					else{
						requestList.put("cabAvailable", "Waiting");															
					}
				}
				requestList.put("tripDate", formatter.format(allTravelRequest.getRequestDate()));
				requestList.put("weekOffs",allTravelRequest.getEfmFmUserMaster().getWeekOffDays() );
				requestList.put("tripType", allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType());
				requestList.put("requestType", allTravelRequest.getRequestType());
				requestList.put("tripTime", shiftTimeFormaters.format(allTravelRequest.getShiftTime()));
				requestList.put("employeeName", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName());					
				requestList.put("employeeRouteName", allTravelRequest.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
				requestList.put("employeeRouteId", allTravelRequest.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
				requestList.put("employeeAreaName", allTravelRequest.geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaName());
				requestList.put("employeeAreaId", allTravelRequest.geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaId());
				requestList.put("employeePickUpTime", allTravelRequest.geteFmFmEmployeeRequestMaster().getPickUpTime());					
				requestList.put("employeeAddress", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getAddress());
				requestList.put("employeeWaypoints", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getLatitudeLongitude());							
				travelRequestList.add(requestList);
			}									

		}
		//		responce.put("shifts", shitTimings);
		responce.put("requests", travelRequestList);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Search particular employee request from employeeId
	 */

	@POST
	@Path("/emplyeerequestsearch")
	public Response searchEmployeeRequestFromEmpId(EFmFmEmployeeTravelRequestPO travelRequest) throws ParseException{	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		travelRequest.setRequestDate(new Date());		
		List<EFmFmEmployeeTravelRequestPO> employeeTravelRequest=iCabRequestBO.particularEmployeeRequestFromEmpId(travelRequest.getBranchId(),travelRequest.getEmployeeId());
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<Map<String, Object>> travelRequestList = new ArrayList<Map<String, Object>>();
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat  shiftDateFormater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		DateFormat  shiftTimeFormaters = new SimpleDateFormat("HH:mm:ss");
		if((!(employeeTravelRequest.isEmpty())) || employeeTravelRequest.size() !=0){	
			for(EFmFmEmployeeTravelRequestPO allTravelRequest:employeeTravelRequest){				
				Map<String, Object>  requestList= new HashMap<String, Object>();				
				requestList.put("employeeId", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getEmployeeId());
				requestList.put("requestId", allTravelRequest.getRequestId());
				String cabAvailableTime=formatter.format(allTravelRequest.getRequestDate())+" "+allTravelRequest.getShiftTime();
				if(allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType().equalsIgnoreCase("DROP")){
					requestList.put("pickUpTime", allTravelRequest.getDropSequence());
					if(shiftDateFormater.parse(cabAvailableTime).getTime()<System.currentTimeMillis()+900000){
						requestList.put("cabAvailable", "Cab not available");															
					}
					else{
						requestList.put("cabAvailable", "Waiting");															
					}
				}
				else{
					try{
						requestList.put("pickUpTime",shiftTimeFormater.format(allTravelRequest.getPickUpTime()));
					}catch(Exception e){
						requestList.put("pickUpTime","0");
					}
					if(shiftDateFormater.parse(cabAvailableTime).getTime()<System.currentTimeMillis()+16200000){
						requestList.put("cabAvailable", "Cab not available");															
					}
					else{
						requestList.put("cabAvailable", "Waiting");															
					}
				}
				requestList.put("tripDate", formatter.format(allTravelRequest.getRequestDate()));
				requestList.put("weekOffs",allTravelRequest.getEfmFmUserMaster().getWeekOffDays() );
				requestList.put("tripType", allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType());
				requestList.put("requestType", allTravelRequest.getRequestType());
				requestList.put("tripTime", shiftTimeFormaters.format(allTravelRequest.getShiftTime()));
				requestList.put("employeeName", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName());					
				requestList.put("employeeRouteName", allTravelRequest.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
				requestList.put("employeeRouteId", allTravelRequest.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId());
				requestList.put("employeeAreaName", allTravelRequest.geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaName());
				requestList.put("employeeAreaId", allTravelRequest.geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaId());
				requestList.put("employeePickUpTime", allTravelRequest.geteFmFmEmployeeRequestMaster().getPickUpTime());					
				requestList.put("employeeAddress", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getAddress());
				//requestList.put("employeeWaypoints", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getLatitudeLongitude());							
				travelRequestList.add(requestList);			
			}
		}
		responce.put("requests", travelRequestList);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}



	/**
	 * The shifTime method implemented.
	 * for getting the list of shif timing from shift time table.   
	 *
	 * @author  Sarfraz Khan
	 * 	
	 */	
	@POST
	@Path("/shiftime")
	public Response shifTime(EFmFmEmployeeTravelRequestPO employeeRequest){	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		Map<String, Object>  responce = new HashMap<String,Object>();
		employeeRequest.setRequestDate(new Date());
		List<Map<String, Object>> shitTimings = new ArrayList<Map<String, Object>>();
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		List<EFmFmTripTimingMasterPO> shiftTimeDetails=iCabRequestBO.listOfShiftTime(employeeRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
		if((!(shiftTimeDetails.isEmpty())) || shiftTimeDetails.size() !=0){			
			for(EFmFmTripTimingMasterPO shiftiming:shiftTimeDetails){				
				Map<String, Object>  shifList= new HashMap<String, Object>();
				shifList.put("shiftTime", shiftTimeFormater.format(shiftiming.getShiftTime()));
				shifList.put("tripType", shiftiming.getTripType());	
				shifList.put("bufferTime", shiftiming.getShiftBufferTime());				

				shitTimings.add(shifList);
			}		
		}	
		responce.put("status", "success");
		responce.put("shift", shitTimings);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	
	
	/**
	 * The shifTime method implemented.
	 * for getting the list of shift timing from shift time table according to the Trip Type DropDown  
	 *
	 * @author  Sarfraz Khan
	 * 	
	 */	
	@POST
	@Path("/tripshiftime")
	public Response shifTimeByTripType(EFmFmEmployeeTravelRequestPO employeeRequest){	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		Map<String, Object>  responce = new HashMap<String,Object>();
		employeeRequest.setRequestDate(new Date());
		List<Map<String, Object>> shitTimings = new ArrayList<Map<String, Object>>();
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		List<EFmFmTripTimingMasterPO> shiftTimeDetails=iCabRequestBO.listOfShiftTimeByTripType(employeeRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),employeeRequest.getTripType());		
		System.out.println("shiftTimeDetails"+shiftTimeDetails.size());
		if((!(shiftTimeDetails.isEmpty())) || shiftTimeDetails.size() !=0){			
			for(EFmFmTripTimingMasterPO shiftiming:shiftTimeDetails){				
				Map<String, Object>  shifList= new HashMap<String, Object>();
				shifList.put("shiftTime", shiftTimeFormater.format(shiftiming.getShiftTime()));
				shifList.put("tripType", shiftiming.getTripType());	
				shifList.put("bufferTime", shiftiming.getShiftBufferTime());				
				shitTimings.add(shifList);
			}		
		}	
		responce.put("status", "success");
		responce.put("shift", shitTimings);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	
	/**
	 * The shifTime method implemented.
	 * adding shift timings time table.   
	 *
	 * @author  Sarfraz Khan
	 * @throws ParseException 
	 * 	
	 */	
	@POST
	@Path("addshiftime")
	public Response addShifTime(EFmFmTripTimingMasterPO shiftTimingDetail) throws ParseException{	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		Map<String, Object>  responce = new HashMap<String,Object>();
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		String shiftDate=shiftTimingDetail.getTime();
		Date shift  = (Date) shiftFormate.parse(shiftDate);				 
		java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
		List<EFmFmTripTimingMasterPO> shiftTimeDetail=iCabRequestBO.getParticularShiftTimeDetailByTripType(shiftTimingDetail.geteFmFmClientBranchPO().getBranchId(), shiftTime,shiftTimingDetail.getTripType());
		if((!(shiftTimeDetail.isEmpty())) || shiftTimeDetail.size() !=0){
			responce.put("status", "fail");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		shiftTimingDetail.setTripType(shiftTimingDetail.getTripType());
		shiftTimingDetail.setShiftBufferTime(shiftTimingDetail.getShiftBufferTime());
		shiftTimingDetail.setShiftTime(shiftTime);
		shiftTimingDetail.setIsActive("Y");
		iCabRequestBO.save(shiftTimingDetail);		
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Guest request for cab from  Web console 
	 * 
	 * 
	 */

	@POST
	@Path("/requestforguest")
	public Response cabRequestForGuest(EFmFmEmployeeRequestMasterPO travelRequest) throws ParseException{	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
		IEmployeeDetailBO iEmployeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
		Map<String, Object>  responce = new HashMap<String,Object>();
//		List<EFmFmUserMasterPO> requestEmployeeIdExitCheck=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeIdForGuest(travelRequest.getEmployeeId(), travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		/*if((!(requestEmployeeIdExitCheck.isEmpty())) || requestEmployeeIdExitCheck.size() !=0){
			responce.put("status", "empExist");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		List<EFmFmUserMasterPO> mobileNumCheck=iEmployeeDetailBO.getEmpMobileNumberCheck(travelRequest.getMobileNumber(), travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		if((!(mobileNumCheck.isEmpty())) || mobileNumCheck.size() !=0){
			responce.put("status", "mobileExist");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}*/
		List<EFmFmUserMasterPO> guestIdExistsCheck=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(travelRequest.getEmployeeId(), travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		EFmFmRouteAreaMappingPO routeAreaDetails =new EFmFmRouteAreaMappingPO();			
		try{
			DateFormat timeformate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
			String shiftDate=travelRequest.getTime();
			Date shift  = (Date) shiftFormate.parse(shiftDate);				 
			java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
			Date startDate  = (Date) timeformate.parse(travelRequest.getStartDate()+" "+"00:00");
			Date endDate  = (Date) timeformate.parse(travelRequest.getEndDate()+" "+"23:59");			
			if((!(guestIdExistsCheck.isEmpty())) || guestIdExistsCheck.size() !=0){
				List<EFmFmEmployeeRequestMasterPO> existRequestsInReqMaster=iCabRequestBO.getEmplyeeRequestsForSameDateAndShiftTime(startDate, shiftTime, travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), guestIdExistsCheck.get(0).getUserId(), travelRequest.getTripType());
				if(existRequestsInReqMaster.size()!=0 || (!(existRequestsInReqMaster.isEmpty())) ){			
					responce.put("status", "alreadyRaised");
					return Response.ok(responce, MediaType.APPLICATION_JSON).build();	
				}

				//Emp detail From UserId
				EFmFmClientProjectDetailsPO eFmFmClientProjectDetailsPO = new EFmFmClientProjectDetailsPO();	    
				guestIdExistsCheck.get(0).setEmployeeId(travelRequest.getEmployeeId());
				guestIdExistsCheck.get(0).setUserName(travelRequest.getEmployeeId());			 	
				guestIdExistsCheck.get(0).setPassword(travelRequest.getEmployeeId());	
				guestIdExistsCheck.get(0).setHostMobileNumber(travelRequest.getHostMobileNumber());
				guestIdExistsCheck.get(0).setGuestMiddleLoc(travelRequest.getGuestMiddleLoc());	
				guestIdExistsCheck.get(0).setFirstName(travelRequest.getFirstName());
				guestIdExistsCheck.get(0).setLastName(travelRequest.getLastName());
				guestIdExistsCheck.get(0).setGender(travelRequest.getGender());			 	
				guestIdExistsCheck.get(0).setMobileNumber(travelRequest.getMobileNumber());
				guestIdExistsCheck.get(0).setEmailId(travelRequest.getEmailId());			 	
				guestIdExistsCheck.get(0).setAddress(travelRequest.getAddress());
				guestIdExistsCheck.get(0).setUpdatedTime(new Date());
				EFmFmClientBranchPO eFmFmClientBranch=new EFmFmClientBranchPO();
				eFmFmClientBranch.setBranchId(travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				guestIdExistsCheck.get(0).seteFmFmClientBranchPO(eFmFmClientBranch);
				eFmFmClientProjectDetailsPO.setProjectId(1);	 	
				guestIdExistsCheck.get(0).seteFmFmClientProjectDetails(eFmFmClientProjectDetailsPO);
				guestIdExistsCheck.get(0).setLatitudeLongitude(travelRequest.getLatitudeLongitude());
				List<EFmFmClientBranchPO> clientBranchDetails=userMasterBO.getClientDetails(travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				CalculateDistance empDistance=new CalculateDistance();
				try{
					guestIdExistsCheck.get(0).setDistance(empDistance.employeeDistanceCalculation(clientBranchDetails.get(0).getLatitudeLongitude(), travelRequest.getLatitudeLongitude()));
				}catch(Exception e){}
				routeAreaDetails.setRouteAreaId(39618);
				guestIdExistsCheck.get(0).seteFmFmRouteAreaMapping(routeAreaDetails);
				userMasterBO.update(guestIdExistsCheck.get(0));
				
				//Emp detail From EmployeeId
				List<EFmFmUserMasterPO> employeeDetailFromEmpId=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(travelRequest.getEmployeeId(), travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());

				travelRequest.setEmployeeId(employeeDetailFromEmpId.get(employeeDetailFromEmpId.size()-1).getEmployeeId());
				EFmFmClientUserRolePO eFmFmClientUserRolePO=new EFmFmClientUserRolePO();
				EFmFmRoleMasterPO efmFmRoleMaster=new EFmFmRoleMasterPO();
				efmFmRoleMaster.setRoleId(4);
				eFmFmClientUserRolePO.setEfmFmUserMaster(employeeDetailFromEmpId.get(0));
				eFmFmClientUserRolePO.setEfmFmRoleMaster(efmFmRoleMaster);
				eFmFmClientUserRolePO.seteFmFmClientBranchPO(eFmFmClientBranch);
				userMasterBO.save(eFmFmClientUserRolePO);
				EFmFmUserMasterPO efmFmUserMaster=new EFmFmUserMasterPO();	
				EFmFmEmployeeRequestMasterPO eFmFmEmployeeReqMasterPO= new EFmFmEmployeeRequestMasterPO();
				efmFmUserMaster.setUserId(employeeDetailFromEmpId.get(employeeDetailFromEmpId.size()-1).getUserId());
				EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO=new EFmFmRouteAreaMappingPO();
				eFmFmRouteAreaMappingPO.setRouteAreaId(employeeDetailFromEmpId.get(employeeDetailFromEmpId.size()-1).geteFmFmRouteAreaMapping().getRouteAreaId());
				eFmFmEmployeeReqMasterPO.setShiftTime(shiftTime);
				eFmFmEmployeeReqMasterPO.setEfmFmUserMaster(efmFmUserMaster);
				eFmFmEmployeeReqMasterPO.setStatus("Y");
				eFmFmEmployeeReqMasterPO.setRequestType("guest");
				eFmFmEmployeeReqMasterPO.setReadFlg("Y");
				eFmFmEmployeeReqMasterPO.setPickUpTime(shiftTime);
				eFmFmEmployeeReqMasterPO.setDropSequence(1);
				eFmFmEmployeeReqMasterPO.setRequestFrom(travelRequest.getRequestFrom());
				eFmFmEmployeeReqMasterPO.setRequestDate(new Date());
				eFmFmEmployeeReqMasterPO.setTripType(travelRequest.getTripType());
				eFmFmEmployeeReqMasterPO.setTripRequestStartDate(startDate);
				eFmFmEmployeeReqMasterPO.setTripRequestEndDate(endDate);
				iCabRequestBO.save(eFmFmEmployeeReqMasterPO);
			}
			else{
			//Emp detail From UserId
			EFmFmUserMasterPO employeeDetailsPO = new EFmFmUserMasterPO();
			EFmFmClientProjectDetailsPO eFmFmClientProjectDetailsPO = new EFmFmClientProjectDetailsPO();	    
			employeeDetailsPO.setEmployeeId(travelRequest.getEmployeeId());
			employeeDetailsPO.setUserName(travelRequest.getEmployeeId());			 	
			employeeDetailsPO.setPassword(travelRequest.getEmployeeId());	
			employeeDetailsPO.setHostMobileNumber(travelRequest.getHostMobileNumber());
			employeeDetailsPO.setGuestMiddleLoc(travelRequest.getGuestMiddleLoc());	
			employeeDetailsPO.setFirstName(travelRequest.getFirstName());
			employeeDetailsPO.setFirstName(travelRequest.getLastName());
			employeeDetailsPO.setGender(travelRequest.getGender());			 	
			employeeDetailsPO.setMobileNumber(travelRequest.getMobileNumber());
			employeeDetailsPO.setEmailId(travelRequest.getEmailId());			 	
			employeeDetailsPO.setAddress(travelRequest.getAddress());
			employeeDetailsPO.setCreationTime(new Date());
			employeeDetailsPO.setUpdatedTime(new Date());
			employeeDetailsPO.setPhysicallyChallenged("N"); 
			employeeDetailsPO.setEmployeeDesignation("Shell");
			EFmFmClientBranchPO eFmFmClientBranch=new EFmFmClientBranchPO();
			eFmFmClientBranch.setBranchId(travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
			employeeDetailsPO.seteFmFmClientBranchPO(eFmFmClientBranch);
			employeeDetailsPO.setStatus("Y");		 	
			employeeDetailsPO.setLocationStatus("N");
			eFmFmClientProjectDetailsPO.setProjectId(1);	 	
			employeeDetailsPO.seteFmFmClientProjectDetails(eFmFmClientProjectDetailsPO);
			employeeDetailsPO.setLatitudeLongitude(travelRequest.getLatitudeLongitude());
			employeeDetailsPO.setWeekOffDays("Sunday");
		 	employeeDetailsPO.setUserType("guest");

			List<EFmFmClientBranchPO> clientBranchDetails=userMasterBO.getClientDetails(travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
			CalculateDistance empDistance=new CalculateDistance();
			try{
				employeeDetailsPO.setDistance(empDistance.employeeDistanceCalculation(clientBranchDetails.get(0).getLatitudeLongitude(), travelRequest.getLatitudeLongitude()));
			}catch(Exception e){}
			routeAreaDetails.setRouteAreaId(39618);
			employeeDetailsPO.seteFmFmRouteAreaMapping(routeAreaDetails);
			userMasterBO.save(employeeDetailsPO);			
			
			//Emp detail From EmployeeId
			List<EFmFmUserMasterPO> employeeDetailFromEmpId=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(travelRequest.getEmployeeId(), travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());

			travelRequest.setEmployeeId(employeeDetailFromEmpId.get(employeeDetailFromEmpId.size()-1).getEmployeeId());
			EFmFmClientUserRolePO eFmFmClientUserRolePO=new EFmFmClientUserRolePO();
			EFmFmRoleMasterPO efmFmRoleMaster=new EFmFmRoleMasterPO();
			efmFmRoleMaster.setRoleId(4);
			eFmFmClientUserRolePO.setEfmFmUserMaster(employeeDetailFromEmpId.get(0));
			eFmFmClientUserRolePO.setEfmFmRoleMaster(efmFmRoleMaster);
			eFmFmClientUserRolePO.seteFmFmClientBranchPO(eFmFmClientBranch);
			userMasterBO.save(eFmFmClientUserRolePO);
			EFmFmUserMasterPO efmFmUserMaster=new EFmFmUserMasterPO();	
			EFmFmEmployeeRequestMasterPO eFmFmEmployeeReqMasterPO= new EFmFmEmployeeRequestMasterPO();
			efmFmUserMaster.setUserId(employeeDetailFromEmpId.get(employeeDetailFromEmpId.size()-1).getUserId());
			EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO=new EFmFmRouteAreaMappingPO();
			eFmFmRouteAreaMappingPO.setRouteAreaId(employeeDetailFromEmpId.get(employeeDetailFromEmpId.size()-1).geteFmFmRouteAreaMapping().getRouteAreaId());
			eFmFmEmployeeReqMasterPO.setShiftTime(shiftTime);
			eFmFmEmployeeReqMasterPO.setEfmFmUserMaster(efmFmUserMaster);
			eFmFmEmployeeReqMasterPO.setStatus("Y");
			eFmFmEmployeeReqMasterPO.setRequestType("guest");
			eFmFmEmployeeReqMasterPO.setReadFlg("Y");
			eFmFmEmployeeReqMasterPO.setPickUpTime(shiftTime);
			eFmFmEmployeeReqMasterPO.setDropSequence(1);
			eFmFmEmployeeReqMasterPO.setRequestFrom(travelRequest.getRequestFrom());
			eFmFmEmployeeReqMasterPO.setRequestDate(new Date());
			eFmFmEmployeeReqMasterPO.setTripType(travelRequest.getTripType());
			eFmFmEmployeeReqMasterPO.setTripRequestStartDate(startDate);
			eFmFmEmployeeReqMasterPO.setTripRequestEndDate(endDate);
			iCabRequestBO.save(eFmFmEmployeeReqMasterPO);
			}
			responce.put("status", "success");
		}
		catch(Exception e){
			//			e.printStackTrace();
		}
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();	
	}	

	/*
	 * Cab request from Employee device as well as employee web console 
	 * 
	 * 
	 */

	@POST
	@Path("/devicerequest")
	public Response cabRequestFromDevice(final EFmFmEmployeeRequestMasterPO travelRequest) throws ParseException{	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
		IEmployeeDetailBO iEmployeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		final IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");	
		Map<String, Object>  responce = new HashMap<String,Object>();
		try{
			DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
			String shiftDate=travelRequest.getTime();
			Date shift  = (Date) shiftFormate.parse(shiftDate);				 
			java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
			Date startDate  = (Date) dateTimeFormate.parse(travelRequest.getStartDate()+" "+"00:00");
			Date endDate  = (Date) dateTimeFormate.parse(travelRequest.getEndDate()+" "+"23:59");
			//Emp detail From UserId
			List<EFmFmUserMasterPO> employeeDetailsFromUserId=iEmployeeDetailBO.getParticularEmpDetailsFromUserId(travelRequest.getUserId(),travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());			
			//Emp detail From EmployeeId
			String employeeId="";
			if(travelRequest.getEmployeeId().equalsIgnoreCase("NO")){
				employeeId=employeeDetailsFromUserId.get(0).getEmployeeId();
			}
			else{
				employeeId=travelRequest.getEmployeeId();
			}

			List<EFmFmUserMasterPO> requestEmployeeIdExitCheck=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(employeeId, travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());

			if((requestEmployeeIdExitCheck.isEmpty()) || requestEmployeeIdExitCheck.size() ==0){
				responce.put("status", "empNotExist");
				return Response.ok(responce, MediaType.APPLICATION_JSON).build();
			}

			final List<EFmFmUserMasterPO> employeeDetailFromEmpId=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(employeeId, travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());	
			try{
				Thread thread1 = new Thread(new Runnable() {		
					public synchronized void run() {
						// TODO Auto-generated method stub
						try {
							List<EFmFmUserMasterPO> allProjectEmployees=userMasterBO.getAllUsersBelogsProject(travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), employeeDetailFromEmpId.get(0).geteFmFmClientProjectDetails().getProjectId());
							for(EFmFmUserMasterPO userMasterPO:allProjectEmployees){
								List<EFmFmClientUserRolePO> userClientRoleMaster = userMasterBO.getUserRolesFromUserIdAndBranchId(userMasterPO.getUserId(),travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
								if(userClientRoleMaster.get(0).getEfmFmRoleMaster().getRole().equalsIgnoreCase("manager")){
									SendMailBySite mailSender=new SendMailBySite();
									mailSender.mail(userClientRoleMaster.get(0).getEfmFmUserMaster().getEmailId(), "test");
								}

							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}); thread1.start();
			}catch(Exception e){}
			List<EFmFmEmployeeRequestMasterPO> existRequestsInReqMaster=iCabRequestBO.getEmplyeeRequestsForSameDateAndShiftTime(startDate, shiftTime, travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), employeeDetailFromEmpId.get(0).getUserId(), travelRequest.getTripType());
			if(existRequestsInReqMaster.size()!=0 || (!(existRequestsInReqMaster.isEmpty())) ){			
				responce.put("status", "alreadyRaised");
				return Response.ok(responce, MediaType.APPLICATION_JSON).build();	
			}
			EFmFmUserMasterPO efmFmUserMaster=new EFmFmUserMasterPO();	
			EFmFmEmployeeRequestMasterPO eFmFmEmployeeReqMasterPO= new EFmFmEmployeeRequestMasterPO();		
			efmFmUserMaster.setUserId(employeeDetailFromEmpId.get(employeeDetailFromEmpId.size()-1).getUserId());
			EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO=new EFmFmRouteAreaMappingPO();
			eFmFmRouteAreaMappingPO.setRouteAreaId(employeeDetailFromEmpId.get(employeeDetailFromEmpId.size()-1).geteFmFmRouteAreaMapping().getRouteAreaId());
			eFmFmEmployeeReqMasterPO.setShiftTime(shiftTime);
			eFmFmEmployeeReqMasterPO.setEfmFmUserMaster(efmFmUserMaster);
			eFmFmEmployeeReqMasterPO.setStatus("Y");
			eFmFmEmployeeReqMasterPO.setRequestType("adhoc");
			eFmFmEmployeeReqMasterPO.setDropSequence(1);
			eFmFmEmployeeReqMasterPO.setReadFlg("Y");
			eFmFmEmployeeReqMasterPO.setPickUpTime(shiftTime);
			eFmFmEmployeeReqMasterPO.setRequestFrom(travelRequest.getRequestFrom());
			eFmFmEmployeeReqMasterPO.setRequestDate(new Date());
			eFmFmEmployeeReqMasterPO.setTripType(travelRequest.getTripType());
			eFmFmEmployeeReqMasterPO.setTripRequestStartDate(startDate);
			eFmFmEmployeeReqMasterPO.setTripRequestEndDate(endDate);
			iCabRequestBO.save(eFmFmEmployeeReqMasterPO);
			responce.put("status", "success");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();	
	}

	/*
	 * Reshedule Request From Device
	 */

	@POST
	@Path("/reshedulerequestfromdevice")
	public Response resheduleRequestFromDevice(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO) throws ParseException{
		Map<String, Object>  responce = new HashMap<String,Object>();
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		DateFormat  dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date rescheduleDate=(Date) dateFormatter.parse(employeeTravelRequestPO.getResheduleDate());
		DateFormat timeformate = new SimpleDateFormat("HH:mm");
		String shiftDate=employeeTravelRequestPO.getTime();
		Date shift  = (Date) timeformate.parse(shiftDate);				 
		java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
		String requestDateShiftTime=employeeTravelRequestPO.getResheduleDate()+" "+employeeTravelRequestPO.getTime();					
		Date resheduleDateAndTime=(Date) dateTimeFormate.parse(requestDateShiftTime);
		List<EFmFmEmployeeTravelRequestPO> employeeTravelRequest=iCabRequestBO.getparticularRequestDetail(employeeTravelRequestPO);
		employeeTravelRequest.get(0).setRequestStatus("RM");
		employeeTravelRequest.get(0).setRequestDate(rescheduleDate);	
		if(employeeTravelRequest.get(0).getEfmFmUserMaster().geteFmFmClientBranchPO().getMangerApprovalRequired().equalsIgnoreCase("Yes")){
			employeeTravelRequest.get(0).setIsActive("N");
		}
		else{
			employeeTravelRequest.get(0).setIsActive("Y");
		}
		List<EFmFmEmployeeRequestMasterPO> employeeRequestMaster=iCabRequestBO.getRequestFromRequestMaster(employeeTravelRequest.get(0).geteFmFmEmployeeRequestMaster().getTripId(),employeeTravelRequest.get(0).getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());				
		if((!(employeeRequestMaster.isEmpty())) || employeeRequestMaster.size() !=0){
			employeeRequestMaster.get(0).setShiftTime(shiftTime);	
			employeeRequestMaster.get(0).setTripRequestStartDate(rescheduleDate);	
			employeeRequestMaster.get(0).setTripRequestEndDate(rescheduleDate);	
			if(employeeRequestMaster.get(0).getRequestType().equalsIgnoreCase("guest") || employeeRequestMaster.get(0).getRequestType().equalsIgnoreCase("adhoc"))
				iCabRequestBO.update(employeeRequestMaster.get(0));
		}
		if(resheduleDateAndTime.getTime()>System.currentTimeMillis()+86400000){
			if(employeeRequestMaster.get(0).getRequestType().equalsIgnoreCase("guest") || employeeRequestMaster.get(0).getRequestType().equalsIgnoreCase("adhoc"))
				iCabRequestBO.deleteParticularRequest(employeeTravelRequest.get(0).getRequestId());
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		employeeTravelRequest.get(0).setShiftTime(shiftTime);		
		iCabRequestBO.update(employeeTravelRequest.get(0));
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Reschedule Request From Employee web console
	 */

	@POST
	@Path("/reshedulerequestfromweb")
	public Response resheduleRequestFromEmolyeeConsole(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO) throws ParseException{
		Map<String, Object>  responce = new HashMap<String,Object>();
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		DateFormat  dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date rescheduleDate=(Date) dateFormatter.parse(employeeTravelRequestPO.getResheduleDate());
		DateFormat timeformate = new SimpleDateFormat("HH:mm");
		String shiftDate=employeeTravelRequestPO.getTime();
		Date shift  = (Date) timeformate.parse(shiftDate);				 
		java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
		String requestDateShiftTime=employeeTravelRequestPO.getResheduleDate()+" "+employeeTravelRequestPO.getTime();
		Date resheduleDateAndTime=(Date) dateTimeFormate.parse(requestDateShiftTime);

		List<EFmFmEmployeeTravelRequestPO> employeeTravelRequest=iCabRequestBO.getparticularRequestDetail(employeeTravelRequestPO);
		employeeTravelRequest.get(0).setRequestStatus("RW");
		employeeTravelRequest.get(0).setRequestDate(rescheduleDate);	
		if(employeeTravelRequest.get(0).getEfmFmUserMaster().geteFmFmClientBranchPO().getMangerApprovalRequired().equalsIgnoreCase("Yes")){
			employeeTravelRequest.get(0).setIsActive("N");
		}
		else{
			employeeTravelRequest.get(0).setIsActive("Y");
		}
		List<EFmFmEmployeeRequestMasterPO> employeeRequestMaster=iCabRequestBO.getRequestFromRequestMaster(employeeTravelRequest.get(0).geteFmFmEmployeeRequestMaster().getTripId(),employeeTravelRequest.get(0).getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
		if((!(employeeRequestMaster.isEmpty())) || employeeRequestMaster.size() !=0){
			employeeRequestMaster.get(0).setShiftTime(shiftTime);	
			employeeRequestMaster.get(0).setTripRequestStartDate(rescheduleDate);	
			employeeRequestMaster.get(0).setTripRequestEndDate(rescheduleDate);	
			if(employeeRequestMaster.get(0).getRequestType().equalsIgnoreCase("guest") || employeeRequestMaster.get(0).getRequestType().equalsIgnoreCase("adhoc"))
				iCabRequestBO.update(employeeRequestMaster.get(0));
		}
		if(resheduleDateAndTime.getTime()>System.currentTimeMillis()+86400000){
			if(employeeRequestMaster.get(0).getRequestType().equalsIgnoreCase("guest") || employeeRequestMaster.get(0).getRequestType().equalsIgnoreCase("adhoc"))
				iCabRequestBO.deleteParticularRequest(employeeTravelRequest.get(0).getRequestId());
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		employeeTravelRequest.get(0).setShiftTime(shiftTime);		
		iCabRequestBO.update(employeeTravelRequest.get(0));
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * employee cancel  Request from employee web console
	 */

	@POST
	@Path("/cancelrequestfrmweb")
	public Response cancelRequestFromEmployeeWebConsole(EFmFmEmployeeTravelRequestPO travelRequest){	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		travelRequest.setRequestDate(new Date());		
		List<EFmFmEmployeeTravelRequestPO> employeeTravelRequest=iCabRequestBO.getparticularRequestDetail(travelRequest);
		Map<String, Object>  responce = new HashMap<String,Object>();
		if(employeeTravelRequest.get(0).getRequestType().equalsIgnoreCase("adhoc")){			
			iCabRequestBO.deleteParticularRequest(employeeTravelRequest.get(0).getRequestId());	
			iCabRequestBO.deleteParticularRequestFromRequestMaster(employeeTravelRequest.get(0).geteFmFmEmployeeRequestMaster().getTripId());
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();

		}
		employeeTravelRequest.get(0).setRequestStatus("CW");
		employeeTravelRequest.get(0).setIsActive("N");
		employeeTravelRequest.get(0).setApproveStatus("Y");
		employeeTravelRequest.get(0).setReadFlg("N");
		iCabRequestBO.update(employeeTravelRequest.get(0));	
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * cancel  button inside request from device
	 */

	@POST
	@Path("/cancelrequest")
	public Response cancelRequestFromDevice(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO){	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		employeeTravelRequestPO.setRequestDate(new Date());		
		List<EFmFmEmployeeTravelRequestPO> employeeTravelRequest=iCabRequestBO.getparticularRequestDetail(employeeTravelRequestPO);
		Map<String, Object>  responce = new HashMap<String,Object>();
		if(employeeTravelRequest.get(0).getRequestType().equalsIgnoreCase("adhoc")){			
			iCabRequestBO.deleteParticularRequest(employeeTravelRequest.get(0).getRequestId());	
			iCabRequestBO.deleteParticularRequestFromRequestMaster(employeeTravelRequest.get(0).geteFmFmEmployeeRequestMaster().getTripId());
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();

		}
		employeeTravelRequest.get(employeeTravelRequest.size()-1).setRequestStatus("CM");
		employeeTravelRequest.get(employeeTravelRequest.size()-1).setIsActive("N");
		employeeTravelRequest.get(employeeTravelRequest.size()-1).setApproveStatus("Y");
		employeeTravelRequest.get(employeeTravelRequest.size()-1).setReadFlg("N");
		iCabRequestBO.update(employeeTravelRequest.get(employeeTravelRequest.size()-1));	
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Request approve from web console From manager page 
	 */

	@POST
	@Path("/approvereshedulerequest")
	public Response approveResheduleRequest(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO){
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		employeeTravelRequestPO.setRequestDate(new Date());		
		List<EFmFmEmployeeTravelRequestPO> employeeTravelRequest=iCabRequestBO.getparticularRequestDetail(employeeTravelRequestPO);
		//		if(employeeTravelRequest.get(0).getRequestStatus().equalsIgnoreCase("CM") || employeeTravelRequest.get(0).getRequestStatus().equalsIgnoreCase("CW")){
		//			employeeTravelRequest.get(0).setApproveStatus("Y");
		/*}
		else{*/
		employeeTravelRequest.get(employeeTravelRequest.size()-1).setApproveStatus("Y");
		employeeTravelRequest.get(employeeTravelRequest.size()-1).setIsActive("Y");
		employeeTravelRequest.get(employeeTravelRequest.size()-1).setReadFlg("Y");

		//		}
		iCabRequestBO.update(employeeTravelRequest.get(0));
		return Response.ok("success", MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Request Reject from web console by particular manager
	 */

	@POST
	@Path("/rejectreshedulerequest")
	public Response rejectResheduleRequest(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO){
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		employeeTravelRequestPO.setRequestDate(new Date());		
		List<EFmFmEmployeeTravelRequestPO> employeeTravelRequest=iCabRequestBO.getparticularRequestDetail(employeeTravelRequestPO);
		employeeTravelRequest.get(0).setApproveStatus("R");
		employeeTravelRequest.get(0).setReadFlg("N");
		employeeTravelRequest.get(0).setIsActive("N");
		iCabRequestBO.update(employeeTravelRequest.get(0));			
		return Response.ok("success", MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Get All Todays Request call from device 
	 *  
	 */

	@POST
	@Path("/employeetodayrequest")
	public Response employeeTodaysRequestDetail(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO){
		Map<String, Object>  responce = new HashMap<String,Object>();
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IEmployeeDetailBO iEmployeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		List<Map<String, Object>> travelRequestList = new ArrayList<Map<String, Object>>();
		DateFormat  dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		employeeTravelRequestPO.setRequestDate(new Date());			
		List<EFmFmEmployeeTravelRequestPO> todayrequests=iCabRequestBO.getAllTodaysRequestForParticularEmployee(employeeTravelRequestPO);		
		if((!(todayrequests.isEmpty())) || todayrequests.size() !=0){			
			for(EFmFmEmployeeTravelRequestPO allTravelRequest:todayrequests){				
				Map<String, Object>  requestList= new HashMap<String, Object>();				
				requestList.put("userId", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId());
				requestList.put("requestId", allTravelRequest.getRequestId());
				requestList.put("tripType", allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType());
				requestList.put("requestStatus", allTravelRequest.getRequestStatus());
				requestList.put("requestType", allTravelRequest.getRequestType());
				requestList.put("approveStatus", allTravelRequest.getApproveStatus());
				requestList.put("activeStatus", allTravelRequest.getIsActive());
				requestList.put("requestDate",dateFormatter.format(allTravelRequest.getRequestDate()));
				requestList.put("tripTime", shiftTimeFormater.format(allTravelRequest.getShiftTime()));
				List<EFmFmUserMasterPO> employeeDetails=iEmployeeDetailBO.getParticularEmpDetailsFromUserId(employeeTravelRequestPO.getEfmFmUserMaster().getUserId(),employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				if(employeeDetails.size() >0){
					for(EFmFmUserMasterPO userMasterPO:employeeDetails){
						requestList.put("employeeId", userMasterPO.getEmployeeId());
						requestList.put("employeeName", userMasterPO.getFirstName());
						requestList.put("employeeAddress", userMasterPO.getAddress());
						requestList.put("employeeWaypoints", userMasterPO.getLatitudeLongitude());							
					}
				}
				travelRequestList.add(requestList);
			}									

		}	
		responce.put("requests", travelRequestList);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Reshedule  Request call from device as well as Web 
	 *  
	 */

	@POST
	@Path("/requestsforreshedule")
	public Response getEmployeeRequestsForReshedule(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO){
		Map<String, Object>  responce = new HashMap<String,Object>();
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		List<Map<String, Object>> travelRequestList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> shitTimings = new ArrayList<Map<String, Object>>();
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		employeeTravelRequestPO.setRequestDate(new Date());	
		List<EFmFmEmployeeTravelRequestPO> todayrequests=iCabRequestBO.getTodayRequestForParticularEmployee(employeeTravelRequestPO);		
		List<EFmFmTripTimingMasterPO> shiftTimeDetails=iCabRequestBO.listOfShiftTime(employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
		if((!(todayrequests.isEmpty())) || todayrequests.size() !=0){				
			if((!(shiftTimeDetails.isEmpty())) || shiftTimeDetails.size() !=0){			
				for(EFmFmTripTimingMasterPO shiftiming:shiftTimeDetails){				
					Map<String, Object>  shifList= new HashMap<String, Object>();
					shifList.put("shiftTime", shiftTimeFormater.format(shiftiming.getShiftTime()));				
					shitTimings.add(shifList);
				}		
			}
		}
		if((!(todayrequests.isEmpty())) || todayrequests.size() !=0){			
			for(EFmFmEmployeeTravelRequestPO allTravelRequest:todayrequests){
				if(allTravelRequest.getReadFlg().equalsIgnoreCase("Y") || allTravelRequest.getReadFlg().equalsIgnoreCase("N") && (!(allTravelRequest.getReadFlg().equalsIgnoreCase("CM"))) && (!(allTravelRequest.getReadFlg().equalsIgnoreCase("CW")))){
					Map<String, Object>  requestList= new HashMap<String, Object>();				
					requestList.put("employeeId", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getEmployeeId());
					requestList.put("userId", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId());
					requestList.put("requestId", allTravelRequest.getRequestId());
					requestList.put("tripType", allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType());
					requestList.put("address", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getAddress());
					requestList.put("requestStatus", allTravelRequest.getRequestStatus());				
					requestList.put("requestDate", formatter.format(allTravelRequest.getRequestDate()));
					requestList.put("requestType", allTravelRequest.getRequestType());
					requestList.put("approveStatus", allTravelRequest.getApproveStatus());
					requestList.put("activeStatus", allTravelRequest.getIsActive());
					requestList.put("tripTime", shiftTimeFormater.format(allTravelRequest.getShiftTime()));
					travelRequestList.add(requestList);
				}
			}	
		}
		responce.put("requests", travelRequestList);
		responce.put("shifts", shitTimings);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}


	/*
	 * Cancel  Request call from device as well as Web
	 *  
	 */

	@POST
	@Path("/requestsforcancel")
	public Response getEmployeeRequestsForCancellation(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO){
		Map<String, Object>  responce = new HashMap<String,Object>();
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		List<Map<String, Object>> travelRequestList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> shitTimings = new ArrayList<Map<String, Object>>();
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		employeeTravelRequestPO.setRequestDate(new Date());	
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		List<EFmFmEmployeeTravelRequestPO> todayrequests=iCabRequestBO.getTodayRequestForParticularEmployee(employeeTravelRequestPO);		
		if((!(todayrequests.isEmpty())) || todayrequests.size() !=0){			
			for(EFmFmEmployeeTravelRequestPO allTravelRequest:todayrequests){
				if(allTravelRequest.getReadFlg().equalsIgnoreCase("Y") || allTravelRequest.getReadFlg().equalsIgnoreCase("N") && (!(allTravelRequest.getReadFlg().equalsIgnoreCase("CM"))) && (!(allTravelRequest.getReadFlg().equalsIgnoreCase("CW")))){
					Map<String, Object>  requestList= new HashMap<String, Object>();				
					requestList.put("employeeId", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getEmployeeId());
					requestList.put("userId", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId());
					requestList.put("requestId", allTravelRequest.getRequestId());
					requestList.put("tripType", allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType());
					requestList.put("address", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getAddress());
					requestList.put("requestStatus", allTravelRequest.getRequestStatus());	
					requestList.put("requestType", allTravelRequest.getRequestType());

					requestList.put("requestDate", formatter.format(allTravelRequest.getRequestDate()));
					requestList.put("approveStatus", allTravelRequest.getApproveStatus());
					requestList.put("activeStatus", allTravelRequest.getIsActive());
					requestList.put("tripTime", timeFormat.format(allTravelRequest.getShiftTime()));
					travelRequestList.add(requestList);
				}
			}	
		}
		responce.put("requests", travelRequestList);
		responce.put("shifts", shitTimings);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * list of Reschedule requests for browser
	 * 
	 */	

	@POST
	@Path("/reshedulerequest")
	public Response getAllResheduleRequestsDetails(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO){
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IEmployeeDetailBO iEmployeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		List<Map<String, Object>> resheduleRequestList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		employeeTravelRequestPO.setRequestDate(new Date());	
		List<EFmFmUserMasterPO> loggedInUserDetail=iEmployeeDetailBO.getParticularEmpDetailsFromUserId(employeeTravelRequestPO.getEfmFmUserMaster().getUserId(),employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		 
		List<EFmFmEmployeeTravelRequestPO> travelDetails=iCabRequestBO.getAllResheduleRequests(loggedInUserDetail.get(0).geteFmFmClientProjectDetails().getProjectId(),employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
		if((!(travelDetails.isEmpty())) || travelDetails.size() !=0){			
			for(EFmFmEmployeeTravelRequestPO allTravelRequest:travelDetails){				
				Map<String, Object>  requestList= new HashMap<String, Object>();				
				requestList.put("employeeId", allTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getEmployeeId());
				requestList.put("requestId", allTravelRequest.getRequestId());
				requestList.put("tripType", allTravelRequest.geteFmFmEmployeeRequestMaster().getTripType());
				requestList.put("requestStatus", allTravelRequest.getRequestStatus());
				requestList.put("tripTime", timeFormat.format(allTravelRequest.getShiftTime()));
				List<EFmFmUserMasterPO> userDetails=iEmployeeDetailBO.getParticularEmpDetailsFromUserId(allTravelRequest.getEfmFmUserMaster().getUserId(),employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		 
				if((!(userDetails.isEmpty())) || userDetails.size() !=0){			
					for(EFmFmUserMasterPO employeeMasterPO:userDetails){							
						requestList.put("employeeName", employeeMasterPO.getFirstName());
						requestList.put("employeeNumber", employeeMasterPO.getMobileNumber());
						requestList.put("emailId", employeeMasterPO.getEmailId());
						requestList.put("employeeAddress", employeeMasterPO.getAddress());
						requestList.put("employeeWaypoints", employeeMasterPO.getLatitudeLongitude());							
					}
				}
				resheduleRequestList.add(requestList);
			}									

		}			
		return Response.ok(resheduleRequestList, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Employee cancel request from web from employee login page
	 * 
	 */	
	@POST
	@Path("/employeerequestdelete")
	public Response employeeCancelRequestFromWeb(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO){		
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");			
		eFmFmEmployeeTravelRequestPO.setRequestDate(new Date());
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmEmployeeTravelRequestPO> cabRequest=iCabRequestBO.getparticularRequestDetail(eFmFmEmployeeTravelRequestPO);
		cabRequest.get(cabRequest.size()-1).setIsActive("N");	
		cabRequest.get(cabRequest.size()-1).setRequestStatus("CW");
		cabRequest.get(cabRequest.size()-1).setApproveStatus("Y");
		cabRequest.get(cabRequest.size()-1).setReadFlg("N");
		iCabRequestBO.update(cabRequest.get(cabRequest.size()-1));				
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/deleteRequestTravelDesk")
	public Response requestRemovedFromTravelDesk(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) throws ParseException{		
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");			
		eFmFmEmployeeTravelRequestPO.setRequestDate(new Date());
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmEmployeeTravelRequestPO> cabRequest=iCabRequestBO.getparticularRequestDetail(eFmFmEmployeeTravelRequestPO);
		if(cabRequest.get(0).getRequestType().equalsIgnoreCase("normal")){
		iCabRequestBO.deleteParticularRequest(eFmFmEmployeeTravelRequestPO.getRequestId());						
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	     }
		eFmFmEmployeeTravelRequestPO.setRequestDate(new Date());
		EFmFmUserMasterPO efmFmUserMaster=new EFmFmUserMasterPO();
		efmFmUserMaster.setUserId(cabRequest.get(0).getEfmFmUserMaster().getUserId());
		EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
		eFmFmClientBranchPO.setBranchId(eFmFmEmployeeTravelRequestPO.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		efmFmUserMaster.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		eFmFmEmployeeTravelRequestPO.setEfmFmUserMaster(efmFmUserMaster);
		if(cabRequest.get(0).getTripType().equalsIgnoreCase("PICKUP")){
			eFmFmEmployeeTravelRequestPO.setTripType("DROP");
		}
		else{
			eFmFmEmployeeTravelRequestPO.setTripType("PICKUP");
		}
		eFmFmEmployeeTravelRequestPO.setRequestType(cabRequest.get(0).getRequestType());
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		String shiftTime="23:50:00";
		Date shift  = (Date) shiftFormate.parse(shiftTime);				 
		java.sql.Time dateShiftTime = new java.sql.Time(shift.getTime());

		List<EFmFmEmployeeTravelRequestPO> anotherCabRequest=iCabRequestBO.getAnotherActiveRequestDetail(eFmFmEmployeeTravelRequestPO);
		if(anotherCabRequest.size()!=0 || (!(anotherCabRequest.isEmpty()))){
			anotherCabRequest.get(0).setShiftTime(dateShiftTime);
			iCabRequestBO.update(anotherCabRequest.get(0));
		}
		cabRequest.get(0).setShiftTime(dateShiftTime);
		iCabRequestBO.update(cabRequest.get(0));				
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	
		
		
		
		
	}
	
	
	
	@POST
	@Path("/requestdelete")
	public Response requestRemoved(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) throws ParseException{		
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");			
		eFmFmEmployeeTravelRequestPO.setRequestDate(new Date());
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmEmployeeTravelRequestPO> cabRequest=iCabRequestBO.getparticularRequestDetail(eFmFmEmployeeTravelRequestPO);
		EFmFmUserMasterPO efmFmUserMaster=new EFmFmUserMasterPO();
		efmFmUserMaster.setUserId(cabRequest.get(0).getEfmFmUserMaster().getUserId());
		EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
		eFmFmClientBranchPO.setBranchId(eFmFmEmployeeTravelRequestPO.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		efmFmUserMaster.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		eFmFmEmployeeTravelRequestPO.setEfmFmUserMaster(efmFmUserMaster);
		if(cabRequest.get(0).getTripType().equalsIgnoreCase("PICKUP")){
			eFmFmEmployeeTravelRequestPO.setTripType("DROP");
		}
		else{
			eFmFmEmployeeTravelRequestPO.setTripType("PICKUP");
		}
		eFmFmEmployeeTravelRequestPO.setRequestType(cabRequest.get(0).getRequestType());
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		String shiftTime="23:50:00";
		Date shift  = (Date) shiftFormate.parse(shiftTime);				 
		java.sql.Time dateShiftTime = new java.sql.Time(shift.getTime());

		List<EFmFmEmployeeTravelRequestPO> anotherCabRequest=iCabRequestBO.getAnotherActiveRequestDetail(eFmFmEmployeeTravelRequestPO);
		if(anotherCabRequest.size()!=0 || (!(anotherCabRequest.isEmpty()))){
			anotherCabRequest.get(0).setShiftTime(dateShiftTime);
			iCabRequestBO.update(anotherCabRequest.get(0));
		}
		cabRequest.get(0).setShiftTime(dateShiftTime);
		iCabRequestBO.update(cabRequest.get(0));				
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/tripRequestVerified")
	public Response tripRequestVerified(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO){		
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");		
		eFmFmEmployeeTravelRequestPO.setRequestStatus("AL");		
		iCabRequestBO.update(eFmFmEmployeeTravelRequestPO);		
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}

	//Test the parameters plz

	@POST
	@Path("/drivertrip")
	public Response triptoDriverDevice(EFmFmDeviceMasterPO eFmFmDeviceMasterPO){		
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
		Map<String, Object>  requests = new HashMap<String, Object>();	
		Map<String, Object>  tripSheet = new HashMap<String, Object>();	
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		List<Map<String, Object>> tripAllDetails = new ArrayList<Map<String, Object>>();
		List<EFmFmDeviceMasterPO> deviceDetail=iVehicleCheckInBO.deviceImeiNumberExistsCheck(eFmFmDeviceMasterPO);
		EFmFmVehicleCheckInPO vehicleCheckInPO=new EFmFmVehicleCheckInPO();
		EFmFmDeviceMasterPO deviceMasterPO=new EFmFmDeviceMasterPO();
		deviceMasterPO.setDeviceId(deviceDetail.get(0).getDeviceId());	
		vehicleCheckInPO.seteFmFmDeviceMaster(deviceMasterPO);
		vehicleCheckInPO.setCheckInTime(new Date());
		List<EFmFmVehicleCheckInPO> checkInDetail=iVehicleCheckInBO.getParticularCheckedInDeviceDetails(eFmFmDeviceMasterPO.geteFmFmClientBranchPO().getBranchId(), deviceDetail.get(0).getDeviceId());
		if(checkInDetail.size()!=0 || (!(checkInDetail.isEmpty()))){
			EFmFmVehicleCheckInPO checkIn=new EFmFmVehicleCheckInPO();
			checkIn.setCheckInId(checkInDetail.get(0).getCheckInId());   	
			EFmFmAssignRoutePO assignRoutePO = new EFmFmAssignRoutePO();
			EFmFmClientBranchPO eFmFmClientBranch=new EFmFmClientBranchPO();		   		
			eFmFmClientBranch.setBranchId(eFmFmDeviceMasterPO.geteFmFmClientBranchPO().getBranchId());	
			assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranch);
			assignRoutePO.setEfmFmVehicleCheckIn(checkIn);
			assignRoutePO.setTripAssignDate(new Date());
			//Method for geeting the trip after bucket close only
			List<EFmFmAssignRoutePO> assignRoute=iCabRequestBO.closeVehicleCapacity(checkInDetail.get(0).getCheckInId(), eFmFmDeviceMasterPO.geteFmFmClientBranchPO().getBranchId());			
			StringBuffer waypoints=new StringBuffer();
			if(assignRoute.size()!=0 || (!(assignRoute.isEmpty()))){
				List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=null;
				if(assignRoute.get(0).getTripType().equalsIgnoreCase("PICKUP")){
					employeeTripDetailPO=iCabRequestBO.getParticularTripAllEmployees(assignRoute.get(0).getAssignRouteId());
				}
				else{
					employeeTripDetailPO=iCabRequestBO.getDropTripAllSortedEmployees(assignRoute.get(0).getAssignRouteId());
				}    

				if(employeeTripDetailPO.size()!=0 || (!(employeeTripDetailPO.isEmpty()))){
					for(EFmFmEmployeeTripDetailPO employeeTripDetail:employeeTripDetailPO){
						Map<String, Object>  employeeDetails = new HashMap<String, Object>();						
						List<EFmFmUserMasterPO> employeeDetail=employeeDetailBO.getParticularEmpDetailsFromUserId(employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId(), assignRoute.get(0).geteFmFmClientBranchPO().getBranchId());
						employeeDetails.put("name", employeeDetail.get(0).getFirstName());
						employeeDetails.put("locationStatus", employeeDetail.get(0).getLocationStatus());
						employeeDetails.put("requestId",employeeTripDetail.geteFmFmEmployeeTravelRequest().getRequestId());
						employeeDetails.put("employeeId", employeeTripDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());
						employeeDetails.put("tripTime",shiftTimeFormater.format(assignRoute.get(0).getShiftTime()));
						employeeDetails.put("address", employeeDetail.get(0).getAddress());
						employeeDetails.put("boardedFlg", employeeTripDetail.getBoardedFlg());
						employeeDetails.put("reachedFlg", employeeTripDetail.getReachedFlg());
						employeeDetails.put("latLongi", employeeTripDetail.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getLatitudeLongitude());
						if(assignRoute.get(0).getTripType().equalsIgnoreCase("PICKUP")){
							employeeDetails.put("pickUpTime", employeeTripDetail.geteFmFmEmployeeTravelRequest().getPickUpTime());
						} 
						tripAllDetails.add(employeeDetails);
					}					
					if(tripAllDetails.size()!=0 || (!(tripAllDetails.isEmpty()))){
						for(int i=0;i<=tripAllDetails.size()-1;i++){
							waypoints.append(tripAllDetails.get(i).get("latLongi")+"|");
						}
					}

				}
				if(assignRoute.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
					try{
						int a=assignRoute.get(0).geteFmFmEscortCheckIn().getEscortCheckInId();
						requests.put("escortName", assignRoute.get(0).geteFmFmEscortCheckIn().geteFmFmEscortMaster().getFirstName());	
					}catch(Exception e){
						requests.put("escortId", "N");							
						requests.put("escortName", "Escort Required But Not Available");	

					}
				}
				else{
					requests.put("escortName", "Not Required");							
				}
				requests.put("tripType", assignRoute.get(0).getTripType());
				requests.put("tripUpdateTime", assignRoute.get(0).getTripUpdateTime().getTime());
			  //requests.put("cabStartFlg", assignRoute.get(0).getAllocationMsg());
				requests.put("startKm", assignRoute.get(0).getOdometerStartKm());
				requests.put("baseLatLong",assignRoute.get(0).geteFmFmClientBranchPO().getLatitudeLongitude());
				requests.put("waypoints", waypoints);
				requests.put("routeName", assignRoute.get(0).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
				requests.put("assignRouteId", assignRoute.get(0).getAssignRouteId());
				requests.put("empDetails", tripAllDetails);
				requests.put("status", "success");
				tripSheet.put("data",requests);

			}		
		}
		return Response.ok(tripSheet, MediaType.APPLICATION_JSON).build();
	}


	@POST
	@Path("/tripstartkm")
	public Response tripStartKm(final EFmFmAssignRoutePO assignRoutePO){		
		Map<String, Object>  requests = new HashMap<String, Object>();
		Thread thread1 = new Thread(new Runnable() {		
			public synchronized void run() {
				try {
					Thread.sleep(3000);
					String allpoints="";
					ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
					IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
					DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
					String startTime=assignRoutePO.getTime();
					Date rescheduleDate=(Date) dateTimeFormate.parse(startTime);
					assignRoutePO.setTripAssignDate(new Date());
					StringBuffer allwayPoints = new StringBuffer();
					List<EFmFmAssignRoutePO> assignRoute=iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);
					List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=iCabRequestBO.getParticularTripAllEmployees(assignRoutePO.getAssignRouteId());
					EFmFmActualRoutTravelledPO actualRoutTravelled=new EFmFmActualRoutTravelledPO();
					actualRoutTravelled.setTravelledTime(new Date());
					EFmFmClientBranchPO clientBranchPO=new EFmFmClientBranchPO();															
					clientBranchPO.setBranchId(assignRoutePO.geteFmFmClientBranchPO().getBranchId());
					actualRoutTravelled.seteFmFmClientBranchPO(clientBranchPO);
					actualRoutTravelled.setEfmFmAssignRoute(assignRoutePO);
					List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
					if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
						allwayPoints.append(actualRouteTravelled.get(actualRouteTravelled.size()-1).getLatitudeLongitude()+"|");
					}
					else{
						allwayPoints.append(employeeTripDetailPO.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmClientBranchPO().getLatitudeLongitude()+"|");
					}	
					if(employeeTripDetailPO.size()!=0 || (!(employeeTripDetailPO.isEmpty()))){
						for(EFmFmEmployeeTripDetailPO employeeTripDetail:employeeTripDetailPO){
							allwayPoints.append(employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getLatitudeLongitude()+"|");
						}

					}			        
					allwayPoints.append(employeeTripDetailPO.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmClientBranchPO().getLatitudeLongitude());
					allpoints=allwayPoints.toString();
					String destination=allpoints.substring(allpoints.indexOf('|')).substring(1);
					CalculateDistance calculateDistance=new CalculateDistance();
					try{
						String plannedDistance=calculateDistance.googlePlannedDistanceCalculation(allwayPoints.toString(), destination);
						assignRoute.get(0).setPlannedDistance(Double.parseDouble(plannedDistance));
					}catch(Exception e){}
					assignRoute.get(0).setTripStartTime(rescheduleDate);
					assignRoute.get(0).setOdometerStartKm(assignRoutePO.getOdometerStartKm());
					assignRoute.get(0).setTripStatus("Started");				
					iCabRequestBO.update(assignRoute.get(0));										
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread1.start();

		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Employee status is basically..Employee trip status it a drop,pickup or no show
	 * 
	 */

	@POST
	@Path("/employeestatus")
	public Response employeeStatusFromDevice(EFmFmEmployeeTripDetailPO employeeTripDetailPO) throws ParseException{		
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		final List<EFmFmEmployeeTripDetailPO> empDetails=iCabRequestBO.getParticularTriprEmployeeBoardedStatus(employeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestId(),employeeTripDetailPO.getEfmFmAssignRoute().getAssignRouteId());		
		Map<String, Object>  requests = new HashMap<String, Object>();	
		List<EFmFmUserMasterPO> loggedInUserDetail=employeeDetailBO.getParticularEmpDetailsFromUserId(empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getUserId(),empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		if(loggedInUserDetail.get(0).getLocationStatus().equalsIgnoreCase("N")){
			loggedInUserDetail.get(0).setLatitudeLongitude(employeeTripDetailPO.getGeoCodes());
			loggedInUserDetail.get(0).setLocationStatus("Y");
		}
		///Cab Has Left Message........
		if(!(empDetails.get(0).getBoardedFlg().equalsIgnoreCase("NO") && !(empDetails.get(0).geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")) && employeeTripDetailPO.getBoardedFlg().equalsIgnoreCase("NO"))){
					Thread thread1 = new Thread(new Runnable() {
						public synchronized void run() {
							try {
								String text="";
								MessagingService messaging=new MessagingService();
								text="Dear employee your cab\n"+empDetails.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\n has left at your "+empDetails.get(0).geteFmFmEmployeeTravelRequest().getTripType()+" point.";		
								messaging.cabHasLeftMessageForSch(empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(),text,empDetails.get(0).geteFmFmEmployeeTravelRequest().getRequestType());
								Thread.currentThread().stop();
								log.debug("Time taken by cab left message from gate way and button click for trip Id: "+empDetails.get(0).getEmpTripId());
							} catch (Exception e) {
								log.info("Error Cab has left Message Triggered  for First employee from button click "+empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
								Thread.currentThread().stop();
							}						
						}
					}); thread1.start();
					empDetails.get(0).setCabstartFromDestination(new Date().getTime());
		}
		DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String startTime=employeeTripDetailPO.getTime();
		Date pickupOrNoShowTime=(Date) dateTimeFormate.parse(startTime);
		empDetails.get(0).setBoardedFlg(employeeTripDetailPO.getBoardedFlg());
		empDetails.get(0).setCabstartFromDestination(pickupOrNoShowTime.getTime());	
		empDetails.get(0).setEmployeeStatus("completed");
		employeeDetailBO.update(loggedInUserDetail.get(0));
		iCabRequestBO.update(empDetails.get(0));
		if(empDetails.get(0).geteFmFmEmployeeTravelRequest().getTripType().equalsIgnoreCase("PICKUP") && employeeTripDetailPO.getBoardedFlg().equalsIgnoreCase("NO")){		
		List<EFmFmEmployeeTravelRequestPO> employeeRequestUpdate=iCabRequestBO.particularEmployeePickupRequestFromUserId(empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getUserId(),"DROP");
		log.info("Size"+employeeRequestUpdate.size()+"Id"+empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getUserId());
		if(employeeRequestUpdate.size()!=0 || !(employeeRequestUpdate.isEmpty()) ){
		String shiftDate="23:50:00";
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
		Date shift  = (Date) shiftFormate.parse(shiftDate);				 
		java.sql.Time shiftTime = new java.sql.Time(shift.getTime());
		employeeRequestUpdate.get(0).setShiftTime(shiftTime);
		iCabRequestBO.update(employeeRequestUpdate.get(0));
		}
		}
		
		if(empDetails.get(0).geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest") && (employeeTripDetailPO.getBoardedFlg().equalsIgnoreCase("B") || employeeTripDetailPO.getBoardedFlg().equalsIgnoreCase("D"))){
			Thread thread1 = new Thread(new Runnable() {
				public synchronized void run() {
					// TODO Auto-generated method stub
					try {
						String hostText="";	    
						MessagingService messagingService=new MessagingService();
						if(empDetails.get(0).getEfmFmAssignRoute().getTripType().equalsIgnoreCase("DROP")){
							hostText="Dear Host,Your guest "+empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName()+" is Dropped.";
						}
						else{
							hostText="Dear Host,Your guest "+empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName()+" is picked up.";
						}
						messagingService.sendTripAsMessage(empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getHostMobileNumber(), hostText,empDetails.get(0).geteFmFmEmployeeTravelRequest().getRequestType());
						Thread.currentThread().stop();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}); thread1.start();
		}

		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/liveemployeetrip")
	public Response tripStatusForEmployee(EFmFmEmployeeTripDetailPO tripdetail){		
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		List<Map<String, Object>> emloyeeLiveTrips = new ArrayList<Map<String, Object>>();		
		Map<String, Object>  responce = new HashMap<String, Object>();		
		List<EFmFmEmployeeTripDetailPO> empDetails=iCabRequestBO.getEmployeeLiveTripDetailFromUserId(tripdetail.getUserId(),tripdetail.getEfmFmAssignRoute().geteFmFmClientBranchPO().getBranchId());			
		if(empDetails.size()!=0 || !(empDetails.isEmpty()) ){
			for(EFmFmEmployeeTripDetailPO tripData:empDetails){
//				if(tripData.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId()==tripdetail.getUserId() && tripData.getEmployeeStatus().equalsIgnoreCase("allocated")){
					Map<String, Object>  requests = new HashMap<String, Object>();	
					EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();					
					EFmFmClientBranchPO eFmFmClientBranch=new EFmFmClientBranchPO();							
					eFmFmClientBranch.setBranchId(tripData.getEfmFmAssignRoute().geteFmFmClientBranchPO().getBranchId());
					assignRoutePO.setAssignRouteId(tripData.getEfmFmAssignRoute().getAssignRouteId());
					assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranch);
					List<EFmFmAssignRoutePO> routeDetail=assignRouteBO.closeParticularTrips(assignRoutePO);			
					EFmFmEmployeeRequestMasterPO requestMasterPO= new EFmFmEmployeeRequestMasterPO();
					requestMasterPO.setTripType(routeDetail.get(0).getTripType());
					requestMasterPO.setRequestDate(new Date());
//					SimpleDateFormat formatter = new SimpleDateFormat("hh:MM:ss"); 
					   DateFormat timeFormat = new SimpleDateFormat("HH:mm");

					requests.put("tripStatus", routeDetail.get(0).getTripStatus());
					requests.put("tripType", routeDetail.get(0).getTripType());
					requests.put("driverName", tripData.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getFirstName());
					requests.put("driverNumber", tripData.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getMobileNumber());
					requests.put("VehicleNumber", tripData.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
					requests.put("shiftTime", tripData.geteFmFmEmployeeTravelRequest().getShiftTime());
					requests.put("assignRouteId", routeDetail.get(0).getAssignRouteId());
					requests.put("tripDriverPic", routeDetail.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getProfilePicPath());
//					requests.put("tripDriverPic", "http://192.168.1.102:8080/upload/driver.jpeg");
					requests.put("empLatLong", tripData.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getLatitudeLongitude());
					requests.put("Eta",tripData.getCurrenETA());
					if(routeDetail.get(0).getTripType().equalsIgnoreCase("PICKUP")){
						requests.put("pickupTime", timeFormat.format(tripData.geteFmFmEmployeeTravelRequest().getPickUpTime()));
					}
					if(routeDetail.get(0).getTripStatus().equalsIgnoreCase("Started")){
						EFmFmAssignRoutePO assignRoute=new EFmFmAssignRoutePO();
						assignRoute.setAssignRouteId(tripData.getEfmFmAssignRoute().getAssignRouteId());
						EFmFmActualRoutTravelledPO actualRoutTravelled=new EFmFmActualRoutTravelledPO();
						actualRoutTravelled.setTravelledTime(new Date());			    		
						EFmFmClientBranchPO clientBranch=new EFmFmClientBranchPO();								
						clientBranch.setBranchId(tripdetail.getEfmFmAssignRoute().geteFmFmClientBranchPO().getBranchId());
						actualRoutTravelled.seteFmFmClientBranchPO(clientBranch);
						actualRoutTravelled.setEfmFmAssignRoute(assignRoute);
						List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
						if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
							requests.put("driverLatLong", actualRouteTravelled.get(actualRouteTravelled.size()-1).getLatitudeLongitude());
							requests.put("cabLocation", actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentCabLocation());	
						}
						else{
							requests.put("driverLatLong", tripData.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmClientBranchPO().getLatitudeLongitude());
							requests.put("cabLocation", tripData.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmClientBranchPO().getAddress());	
						}
					}
					emloyeeLiveTrips.add(requests);
//				}
			}
		}
		responce.put("data", emloyeeLiveTrips);
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	//Cab reached msg on reached click inside driver app

	@POST
	@Path("/cabreached")
	public Response cabReachedAtEmployeeDestination(final EFmFmEmployeeTripDetailPO employeeTripDetailPO){	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		final List<EFmFmEmployeeTripDetailPO> empDetails=iCabRequestBO.getParticularTriprEmployeeBoardedStatus(employeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestId(),employeeTripDetailPO.getEfmFmAssignRoute().getAssignRouteId());		
		Map<String, Object>  responce = new HashMap<String,Object>();	
		try{
			List<EFmFmUserMasterPO> loggedInUserDetail=employeeDetailBO.getParticularEmpDetailsFromUserId(empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getUserId(),empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
			if(loggedInUserDetail.get(0).getLocationStatus().equalsIgnoreCase("N")){
				loggedInUserDetail.get(0).setDeviceLatitudeLongitude(employeeTripDetailPO.getGeoCodes());
				loggedInUserDetail.get(0).setLocationStatus("Y");
				employeeDetailBO.update(loggedInUserDetail.get(0));
			}
			if(empDetails.get(0).getReachedFlg().equalsIgnoreCase("N")){
				DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				String startTime=employeeTripDetailPO.getTime();
				Date reachedDate=(Date) dateTimeFormate.parse(startTime);
				Thread thread1 = new Thread(new Runnable() {		
					public synchronized void run() {
						MessagingService messagingService=new MessagingService();	
						String text="";
						try {
							long l1 = System.currentTimeMillis();			
						if(empDetails.get(0).geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")){
							text="Dear guest your cab\n"+empDetails.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\n has arrived at your pickup point";		

						}
						else{
							text="Dear employee your cab\n"+empDetails.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\n has arrived at your pickup point";		

						}
							messagingService.cabReachedMessage(String.valueOf(empDetails.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber()),text,empDetails.get(0).geteFmFmEmployeeTravelRequest().getRequestType());
							long l2 = System.currentTimeMillis();			
							log.debug("Time taken by cab reached message from gate way from button click for trip Id: "+empDetails.get(0).getEmpTripId()+" Time "+(l2-l1)+"ms");	

						} 
						catch (Exception  e) {
							
						}
					}
				}); thread1.start();
				empDetails.get(0).setReachedFlg("Y");
				empDetails.get(0).setReachedMessageDeliveryDate(reachedDate);
				empDetails.get(0).setCabRecheddestinationTime(reachedDate.getTime());
				iCabRequestBO.update(empDetails.get(0));
			}
		}catch(Exception e){}
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/tripcomplete")
	public Response completeTrip(final EFmFmAssignRoutePO assignRoutePO){		
		final ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		Map<String, Object>  requests = new HashMap<String, Object>();			
		assignRoutePO.setTripAssignDate(new Date());
		final List<EFmFmAssignRoutePO> assignRoute=iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);
		if(Double.parseDouble(assignRoute.get(0).getOdometerStartKm()) > Double.parseDouble(assignRoutePO.getOdometerEndKm())){
			requests.put("status", "fail");
			return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		Thread thread1 = new Thread(new Runnable() {		
			public synchronized void run() {
				// TODO Auto-generated method stub
				try {
					IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");
					DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
					String startTime=assignRoutePO.getTime();
					Date tripCompleteTime=(Date) dateTimeFormate.parse(startTime);
					IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");	
					IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
					EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
					eFmFmVehicleCheckInPO.setCheckInTime(new Date());
					eFmFmVehicleCheckInPO.setCheckInId(assignRoute.get(0).getEfmFmVehicleCheckIn().getCheckInId());
					List<EFmFmVehicleCheckInPO> vehicleCheckIn=iVehicleCheckInBO.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);	
					EFmFmVehicleMasterPO vehicleMasterDetail=iVehicleCheckInBO.getParticularVehicleDetail(vehicleCheckIn.get(0).getEfmFmVehicleMaster().getVehicleId());
					vehicleCheckIn.get(0).setStatus("Y");
					vehicleMasterDetail.setStatus("A");
					vehicleMasterDetail.setAvailableSeat(vehicleMasterDetail.getCapacity()-1);
					assignRoute.get(0).setTripCompleteTime(tripCompleteTime);
					assignRoute.get(0).setTripStatus("completed");
					assignRoute.get(0).setOdometerEndKm(assignRoutePO.getOdometerEndKm());
					iCabRequestBO.update(assignRoute.get(0));
					EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(vehicleCheckIn.get(0).getEfmFmDriverMaster().getDriverId());
					particularDriverDetails.setStatus("A");
					approvalBO.update(particularDriverDetails);

					List<EFmFmDeviceMasterPO>deviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(vehicleCheckIn.get(0).geteFmFmDeviceMaster().getDeviceId(),assignRoutePO.geteFmFmClientBranchPO().getBranchId());
					deviceDetails.get(0).setStatus("Y");
					iVehicleCheckInBO.update(deviceDetails.get(0));

					iVehicleCheckInBO.update(vehicleCheckIn.get(0));
					iVehicleCheckInBO.update(vehicleMasterDetail);
					if(assignRoute.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){
						try{
							int a=assignRoute.get(0).geteFmFmEscortCheckIn().getEscortCheckInId();
							List<EFmFmEscortCheckInPO> escortDetails=iVehicleCheckInBO.getParticulaEscortDetailFromEscortId(assignRoutePO.geteFmFmClientBranchPO().getBranchId(), assignRoute.get(0).geteFmFmEscortCheckIn().getEscortCheckInId());						
							escortDetails.get(0).setEscortCheckOutTime(new Date());
							iVehicleCheckInBO.update(escortDetails.get(0));
						}catch(Exception e){

						}
					}
					/*List<EFmFmTripAlertsPO> allAlerts=iAlertBO.getParticularTripAlerts(assignRoutePO.geteFmFmClientBranchPO().getBranchId(), assignRoutePO.getAssignRouteId());
					for(EFmFmTripAlertsPO tripAlertsPO:allAlerts){
						tripAlertsPO.setStatus("N");
						iAlertBO.update(tripAlertsPO);
					}*/
					List<EFmFmEmployeeTripDetailPO> allRequests=iCabRequestBO.getDropTripAllSortedEmployees(assignRoutePO.getAssignRouteId());
					for(EFmFmEmployeeTripDetailPO requestDetail:allRequests){						
						List<EFmFmEmployeeTravelRequestPO> activerequest=iCabRequestBO.getParticularRequestDetailOnTripComplete(assignRoutePO.geteFmFmClientBranchPO().getBranchId(),requestDetail.geteFmFmEmployeeTravelRequest().getRequestId());
						activerequest.get(0).setCompletionStatus("Y");
						iCabRequestBO.update(activerequest.get(0));	
						if(!(requestDetail.getEmployeeStatus().equalsIgnoreCase("completed"))){
							requestDetail.setEmployeeStatus("completed");
							iCabRequestBO.update(requestDetail);
						}

					}
				} catch (Exception e) {
					System.out.println("eee"+e);
				}
			}
		}); thread1.start();

		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}

	//Optimized drop route 

	@POST
	@Path("/optimizeroute")
	public Response OptimozedDropTrip(EFmFmAssignRoutePO assignRoutePO) throws IOException{		
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		Map<String, Object>  requests = new HashMap<String, Object>();			
		assignRoutePO.setTripAssignDate(new Date());
		String baseLatLng="";
		List<EFmFmAssignRoutePO> assignRoute=iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);
		List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=iCabRequestBO.getParticularTripAllEmployees(assignRoute.get(0).getAssignRouteId());
		String wayPoints="";
		StringBuffer allwayPoints = new StringBuffer();
		StringBuffer ids = new StringBuffer();
		baseLatLng=assignRoute.get(0).geteFmFmClientBranchPO().getLatitudeLongitude();
		allwayPoints.append(baseLatLng+"|");		
		if(employeeTripDetailPO.size()!=0 || (!(employeeTripDetailPO.isEmpty()))){
			for(EFmFmEmployeeTripDetailPO employeeTripDetail:employeeTripDetailPO){
				if(employeeTripDetail.getBoardedFlg().equalsIgnoreCase("N")){					
					ids.append(employeeTripDetail.getEmpTripId()+",");
					allwayPoints.append(employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getLatitudeLongitude()+"|");
				}
			}
		}
		wayPoints=allwayPoints.toString();
		String urlLocation="";
		urlLocation = "http://maps.googleapis.com/maps/api/directions/json?origin="+baseLatLng+"&destination="+baseLatLng+"&waypoints=optimize:true|"+wayPoints+"&sensor=true";	  	
		URL geocodeURL;
		geocodeURL = new URL(urlLocation);
		URLConnection connection = geocodeURL.openConnection();
		connection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String data = "";
		String line = ""; 
		while((line = reader.readLine()) != null){
			data += line.trim();
		}
		JSONObject status = new JSONObject(data);
		String objstatus=status.getString("status");
		if(objstatus.equals("OK")){
			JSONArray routes = new JSONObject(data).getJSONArray("routes");		    	
			JSONArray elements = routes.getJSONObject(0).getJSONArray("waypoint_order");
			int indexOfOpenBracket = elements.toString().toString().indexOf(",");
			int indexOfLastBracket = elements.toString().toString().lastIndexOf("]");
			StringTokenizer stringTokenizer = new StringTokenizer(elements.toString().substring(indexOfOpenBracket+1, indexOfLastBracket),",");
			String empId="";
			int i=0;
			while (stringTokenizer.hasMoreElements()) {
				empId=(String) stringTokenizer.nextElement();
				employeeTripDetailPO.get(i).setOrderId(Integer.parseInt(empId));
				iCabRequestBO.update(employeeTripDetailPO.get(i));
				i++;

			}
		}		
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	//Previously i am using employee id need to change with request Id	
	@POST
	@Path("/travelrequest")
	public Response employeeTravelRequestFromWeb(EFmFmEmployeeTravelRequestPO employeeTravelRequestPO){
		Map<String, Object>  requests = new HashMap<String, Object>();	
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");						
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IEmployeeDetailBO iEmployeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		int indexOfOpenBracket = employeeTravelRequestPO.getEmployeeId().toString().indexOf("[");
		int indexOfLastBracket = employeeTravelRequestPO.getEmployeeId().toString().lastIndexOf("]");
		StringTokenizer stringTokenizer = new StringTokenizer(employeeTravelRequestPO.getEmployeeId().toString().substring(indexOfOpenBracket+1, indexOfLastBracket),",");
		String requestId="";
		while (stringTokenizer.hasMoreElements()) {
			requestId=(String) stringTokenizer.nextElement();
			List<EFmFmVehicleCheckInPO> listOfCheckedInVehicle=iVehicleCheckInBO.getCheckedInVehicleDetails(employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),new Date());				
			employeeTravelRequestPO.setRequestDate(new Date());
			employeeTravelRequestPO.setRequestId(Integer.valueOf(requestId));
			EFmFmUserMasterPO efmFmUserMaster=new EFmFmUserMasterPO();
			EFmFmClientBranchPO clientBranch=new EFmFmClientBranchPO();								
			clientBranch.setBranchId(employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
			efmFmUserMaster.seteFmFmClientBranchPO(clientBranch);
			employeeTravelRequestPO.setEfmFmUserMaster(efmFmUserMaster);		
			List<EFmFmEmployeeTravelRequestPO> requestDetails=iCabRequestBO.getparticularRequestDetail(employeeTravelRequestPO);
			if(listOfCheckedInVehicle.size()!=0 || !(listOfCheckedInVehicle.isEmpty())){
				requestDetails.get(0).setReadFlg("Y");
				iCabRequestBO.update(requestDetails.get(0));
				requests.put("status", "success");
				return Response.ok(requests, MediaType.APPLICATION_JSON).build();
			}
			if(listOfCheckedInVehicle.size()==0 || listOfCheckedInVehicle.isEmpty() ){
				List<EFmFmVehicleCheckInPO> listAssignedVehicles=iVehicleCheckInBO.getAssignedVehicleDetails(employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),new Date());
				if(listAssignedVehicles.size()==0 || listAssignedVehicles.isEmpty()){
					requests.put("status", "fail");
					return Response.ok(requests, MediaType.APPLICATION_JSON).build();
				}
				else{
					IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");		
					List<EFmFmUserMasterPO> empDetails=iEmployeeDetailBO.getParticularEmpDetailsFromUserId(requestDetails.get(0).geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId(), employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());									
					EFmFmAssignRoutePO assignRoute=new EFmFmAssignRoutePO();
					assignRoute.setTripAssignDate(new Date());
					EFmFmClientBranchPO clientBranchId=new EFmFmClientBranchPO();													
					clientBranchId.setBranchId(employeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
					EFmFmRouteAreaMappingPO routeAreaMappingPO=new EFmFmRouteAreaMappingPO();
					routeAreaMappingPO.setRouteAreaId(empDetails.get(0).geteFmFmRouteAreaMapping().getRouteAreaId());
					assignRoute.seteFmFmClientBranchPO(clientBranchId);
					assignRoute.seteFmFmRouteAreaMapping(routeAreaMappingPO);	
					assignRoute.setTripType(requestDetails.get(0).geteFmFmEmployeeRequestMaster().getTripType());
					List<EFmFmAssignRoutePO> routeDetail=assignRouteBO.getAllOnlyAssignedTrips(assignRoute);
					if(routeDetail.size()>0){
						requestDetails.get(0).setReadFlg("Y");
						iCabRequestBO.update(requestDetails.get(0));
						requests.put("status", "success");
						return Response.ok(requests, MediaType.APPLICATION_JSON).build();
					}
					requests.put("status", "fail");
					return Response.ok(requests, MediaType.APPLICATION_JSON).build();

				}

			}
		}	
		requests.put("status", "fail");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}


	@POST
	@Path("/updatedriverloc")
	public Response driverUpdateLoc(final EFmFmActualRoutTravelledPO actualRouteTravelledPO) throws IOException, ParseException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException{
		final Map<String, Object>  requests = new HashMap<String, Object>();	
		final CabRequestService signer =new CabRequestService();
		String eta="calculating...";
		try{
			EFmFmClientBranchPO clientBranchPO=new EFmFmClientBranchPO();															
			clientBranchPO.setBranchId(actualRouteTravelledPO.geteFmFmClientBranchPO().getBranchId());
			actualRouteTravelledPO.seteFmFmClientBranchPO(clientBranchPO);
			eta=signer.locationUpdater(actualRouteTravelledPO.getLatitudeLongitude(), actualRouteTravelledPO);
		}catch(Exception e){
		}
		requests.put("eta", eta);
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	public String locationUpdater(String currentLatLong,EFmFmActualRoutTravelledPO actualRouteTravelledPO) throws IOException, ParseException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException{
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		
		
		EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
		EFmFmClientBranchPO clientBranch=new EFmFmClientBranchPO();									
		clientBranch.setBranchId(actualRouteTravelledPO.geteFmFmClientBranchPO().getBranchId());		
		assignRoutePO.setAssignRouteId(actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId());
		assignRoutePO.seteFmFmClientBranchPO(clientBranch);
		List<EFmFmAssignRoutePO> liveRoute=assignRouteBO.closeParticularTrips(assignRoutePO);	
		if(liveRoute.get(0).getTripStatus().equalsIgnoreCase("completed")){
			//Trip Completed....
			return "N";
		}
		else if(actualRouteTravelledPO.getTripUpdateTime()!=liveRoute.get(0).getTripUpdateTime().getTime()){
			//Trip Changed....
			return "C";
		}
		else if(liveRoute.get(0).getBucketStatus().equalsIgnoreCase("N")){
			//Processing....
			return "P";
		}

		final List<EFmFmEmployeeTripDetailPO> employeeTripDetailPO=iCabRequestBO.getParticularTripNonDropEmployeesDetails(actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId());
		List<EFmFmEmployeeTripDetailPO> dropEmployeeTripDetail=iCabRequestBO.getNonDropTripAllSortedEmployees(actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId());
		List<EFmFmEmployeeTripDetailPO> baseLatiLongi=iCabRequestBO.getParticularTripAllEmployees(actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId());		
		EFmFmAssignRoutePO assignRoute=new EFmFmAssignRoutePO();
		final MessagingService messaging=new MessagingService();
		EFmFmActualRoutTravelledPO actualRoutTravelled=new EFmFmActualRoutTravelledPO();
		actualRoutTravelled.setTravelledTime(new Date());
		EFmFmClientBranchPO clientBranchPO=new EFmFmClientBranchPO();															
		clientBranchPO.setBranchId(actualRouteTravelledPO.geteFmFmClientBranchPO().getBranchId());
		actualRoutTravelled.seteFmFmClientBranchPO(clientBranchPO);
		assignRoute.setAssignRouteId(actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId());
		actualRoutTravelled.setEfmFmAssignRoute(assignRoute);
		List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
		StringBuffer allwayPoints = new StringBuffer();
		if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
			if(actualRouteTravelled.get(actualRouteTravelled.size()-1).getLatitudeLongitude().equalsIgnoreCase(actualRouteTravelledPO.getLatitudeLongitude())){   	        
				actualRouteTravelled.get(actualRouteTravelled.size()-1).setTravelledTime(new Date());
				assignRouteBO.update(actualRouteTravelled.get(actualRouteTravelled.size()-1));
				return actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentEta();
			}
		}
		if(employeeTripDetailPO.size()!=0 || (!(employeeTripDetailPO.isEmpty()))){
			for(EFmFmEmployeeTripDetailPO employeeTripDetail:employeeTripDetailPO){
				if(employeeTripDetail.getBoardedFlg().equalsIgnoreCase("N")){					
					allwayPoints.append(employeeTripDetail.geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getLatitudeLongitude()+"|");
				}
			}
		}
		String urlLocation="",etaInSec="",driverEta="";					
		int eta=0;
		int etaInSeconds=0;
		String allpoints=allwayPoints.toString();
		String cabCurrentLocation="";		
		if(baseLatiLongi.get(0).getEfmFmAssignRoute().getTripType().equalsIgnoreCase("DROP")){
			if(dropEmployeeTripDetail.size()!=0 || !(dropEmployeeTripDetail.isEmpty())){
				allpoints=dropEmployeeTripDetail.get(0).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getLatitudeLongitude();
			}
			else{
				allpoints=baseLatiLongi.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmClientBranchPO().getLatitudeLongitude();
			}       		
			urlLocation = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+actualRouteTravelledPO.getLatitudeLongitude()+"&destinations="+allpoints+"&mode=driving&units=metric&sensor=true&client=gme-skymapglobalindia";			
			URL geocodeURL;
			URL url = new URL(urlLocation);
			CabRequestService signer = new CabRequestService();
			signer.passingKey(keyString);
			String request = signer.signRequest(url.getPath(),url.getQuery());
			urlLocation=url.getProtocol() + "://" + url.getHost() + request;			
			geocodeURL = new URL(urlLocation);
			URLConnection connection = geocodeURL.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));				
			String data = "";
			String line = "";
			while((line = reader.readLine()) != null){
				data += line.trim();
			}
			JSONObject status = new JSONObject(data);
			String objstatus=status.getString("status");
			if(objstatus.equals("OK")){
				cabCurrentLocation = status.getJSONArray("origin_addresses").toString();
				JSONArray rows = status.getJSONArray("rows");
				JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");	
				if(elements.getJSONObject(0).getString("status").equalsIgnoreCase("OK")){
					for(int i=0; i<elements.length();){
						eta= elements.getJSONObject(i).getJSONObject("duration").getInt("value");
						int hours = eta / 3600;
						int minutes = (eta % 3600) / 60;
						int seconds = eta % 60;
						if(hours!=0){
							etaInSec =hours +" h "+minutes+" min"+seconds+" sec";
						}
						else if(minutes!=0){
							etaInSec =minutes+" min "+seconds+" sec";
						}
						else{
							etaInSec =seconds+" sec";
						}	
						driverEta=etaInSec;
						etaInSeconds=eta;
						if(driverEta.length()==0 && driverEta.isEmpty()){
							if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
								driverEta=actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentEta();
							}
							else{
								driverEta="Calculating...";
							}
						}	  								 
						break;
					}
				}
			}
		}	
		if(baseLatiLongi.get(0).getEfmFmAssignRoute().getTripType().equalsIgnoreCase("PICKUP")){
			urlLocation = "http://maps.googleapis.com/maps/api/directions/json?origin="+actualRouteTravelledPO.getLatitudeLongitude()+"&destination="+baseLatiLongi.get(0).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmClientBranchPO().getLatitudeLongitude()+"&waypoints=optimize:false|"+allwayPoints.toString()+"&mode=driving&language=en-EN&sensor=false&client=gme-skymapglobalindia";				
			URL geocodeURL;
			URL url = new URL(urlLocation);
			CabRequestService signer = new CabRequestService();
			signer.passingKey(keyString);
			String request = signer.signRequest(url.getPath(),url.getQuery());
			urlLocation=url.getProtocol() + "://" + url.getHost() + request;			
			geocodeURL = new URL(urlLocation);
			URLConnection connection = geocodeURL.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String data = "";
			String line = "";
			JSONArray cabLocation =null;
			while((line = reader.readLine()) != null){
				data += line.trim();
			}
			JSONObject status = new JSONObject(data);
			String objstatus=status.getString("status");
			if(objstatus.equals("OK")){
				cabLocation = new JSONObject(data).getJSONArray("routes");	
				DateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
	    		DateFormat dateTimeformate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				String currentDate = dateformate.format(new Date());
				boolean etaFlag=true;
				long currentTime=System.currentTimeMillis();
				JSONArray elements = cabLocation.getJSONObject(0).getJSONArray("legs");    	
				cabCurrentLocation=elements.getJSONObject(0).get("start_address").toString();
				for (int i=0; i<=elements.length()-1; i++){							
					try{	
						eta+= elements.getJSONObject(i).getJSONObject("duration").getInt("value");	
						int hours = eta / 3600;
						int minutes = (eta % 3600) / 60;
						int seconds = eta % 60;
						hours = eta / 3600;
						minutes = (eta % 3600) / 60;
						seconds = eta % 60;									 
						if(hours!=0){
							etaInSec =hours +" h "+minutes+" min"+seconds+" sec";
						}
						else if(minutes!=0){
							etaInSec =minutes+" min "+seconds+" sec";
						}
						else{
							etaInSec =seconds+" sec";
						}
						if(etaFlag){
							etaFlag=false;
							etaInSeconds=eta;
							driverEta=etaInSec;
							List<EFmFmEmployeeTripDetailPO> employeeTrips=iCabRequestBO.getParticularTripNonDropEmployeesDetails(actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId()); 
							if(employeeTrips.size()==0 || employeeTrips.isEmpty() || employeeTrips.get(0).getEfmFmAssignRoute().getTripType().equalsIgnoreCase("DROP") || employeeTrips.size()==i){
								break;
							}
				            String currentDateStr = currentDate+ " " +employeeTripDetailPO.get(i).geteFmFmEmployeeTravelRequest().getPickUpTime();
				            long pickupTime = dateTimeformate.parse(currentDateStr).getTime();
							long timeMax = Math.max(employeeTripDetailPO.get(i).getCabRecheddestinationTime(), pickupTime);     
							if(employeeTripDetailPO.get(i).getReachedFlg().equalsIgnoreCase("Y") && currentTime>=(timeMax+180000)) {
								log.info("Cab has left Message Triggered  for First employee"+employeeTripDetailPO.get(i).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
								final int k=i;
								Thread thread1 = new Thread(new Runnable() {		
									 public synchronized void run() {
										try {
											long l1 = System.currentTimeMillis();			
											String text="";
											if(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")){	
												text="Dear guest your cab\n"+employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\n has left at your pickup point";		
										//		messaging.cabHasLeftMessageForGuestFromSch(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber(),text,employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
											}
											else{
												text="Dear employee your cab\n"+employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\n has left at your pickup point.";		
												messaging.cabHasLeftMessageForSch(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(),text,employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());
											}
											Thread.currentThread().stop();
											long l2 = System.currentTimeMillis();			
											log.debug("Time taken by cab left message from gate way for trip Id: "+employeeTripDetailPO.get(k).getEmpTripId()+" Time "+(l2-l1)+"ms");
										} catch (Exception e) {
											log.info("Error Cab has left Message Triggered  for First employee"+employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
											Thread.currentThread().stop();
										}
									}
								}); thread1.start();	
								employeeTripDetailPO.get(i).setBoardedFlg("NO");
								employeeTripDetailPO.get(i).setCabstartFromDestination(new Date().getTime());
							}						
							final int k=i;
							final String employeeMsgEta=etaInSec;
							if(employeeTripDetailPO.get(k).getReachedFlg().equalsIgnoreCase("N") && (new CalculateDistance().distance(Double.parseDouble(actualRouteTravelledPO.getLatitudeLongitude().split(",")[0]),Double.parseDouble(actualRouteTravelledPO.getLatitudeLongitude().split(",")[1]),Double.parseDouble(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getLatitudeLongitude().split(",")[0]),(Double.parseDouble(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getLatitudeLongitude().split(",")[1])), 'm'))<100){								
			                 	log.info("Sending Reached ETA Message for Trip: "+employeeTripDetailPO.get(k).getEmpTripId());
			                 	 Thread thread1 = new Thread(new Runnable() {		
			     						public synchronized void run() {
			     							try {
			     								String text="";
												long l1 = System.currentTimeMillis();			
			     								if(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")){	
			     									text="Dear guest your cab\n"+employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\n should arrived at your pickup point";		
			     								}
			     								else{
			     									text="Dear employee your cab\n"+employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber()+"\n should arrived at your pickup point";		
			     								}
		     									messaging.cabReachedMessage(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber(),text,employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());
												long l2 = System.currentTimeMillis();			
												log.debug("Time taken by Sending Reached ETA Message from gate way for trip Id: "+employeeTripDetailPO.get(k).getEmpTripId()+" Time "+(l2-l1)+"ms");

		     									Thread.currentThread().stop();
			     							} catch (Exception e) {
			     			                 	log.error("Error Sending Reached ETA Message for Trip: "+employeeTripDetailPO.get(k).getEmpTripId(),e);
			     							Thread.currentThread().stop();

			     						}
			     						}
			     					}); thread1.start();	
			     					employeeTripDetailPO.get(i).setReachedFlg("Y");
			     					employeeTripDetailPO.get(i).setCabRecheddestinationTime(currentTime);
			     					employeeTripDetailPO.get(i).setReachedMessageDeliveryDate(new Date());	
								employeeTripDetailPO.get(i).setTwoMinuteMessageStatus("Y");
								employeeTripDetailPO.get(i).setTwoMinuteMessageDeliveryDate(new Date());						 
							}
							if(employeeTripDetailPO.get(k).getTenMinuteMessageStatus().equalsIgnoreCase("N") && employeeTripDetailPO.get(k).getReachedFlg().equalsIgnoreCase("N") && eta<900){	
								log.info("15 minute Message Triggered from Server for First employee"+employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
								Thread thread1 = new Thread(new Runnable() {		
									public synchronized  void run() {
										try {
											long l1 = System.currentTimeMillis();			
											if(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")){	
												messaging.etaMessagesForGuest(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName(), employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), employeeMsgEta, employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getHostMobileNumber(),employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
												messaging.etaMessagesForGuest(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName(), employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), employeeMsgEta, employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber(),employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
											}
											else{
												messaging.etaMessage(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName(), employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), employeeMsgEta, employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber(),employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
											}
											long l2 = System.currentTimeMillis();			
											log.debug("Time taken by Sending 15 minute Message from gate way for first employee trip Id: "+employeeTripDetailPO.get(k).getEmpTripId()+" Time "+(l2-l1)+"ms");
											Thread.currentThread().stop();
										} catch (Exception e) {
											log.error("Error 15 Minute Message Triggering from Server for First Employee"+employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
											Thread.currentThread().stop();

										}
									}
								}); thread1.start();
								employeeTripDetailPO.get(i).setTenMinuteMessageStatus("Y");
								employeeTripDetailPO.get(i).setTenMinuteMessageDeliveryDate(new Date());					   
							}
							
							if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
							long delayCalculationTime=(new Date().getTime())+(eta*1000)-((actualRouteTravelled.get(actualRouteTravelled.size()-1).getTravelledTime().getTime())+(employeeTripDetailPO.get(k).getGoogleEta()*1000));	  
							if(employeeTripDetailPO.get(k).getTenMinuteMessageStatus().equalsIgnoreCase("Y") && employeeTripDetailPO.get(k).getCabDelayMsgStatus().equalsIgnoreCase("N")&& employeeTripDetailPO.get(k).getReachedFlg().equalsIgnoreCase("N") && (delayCalculationTime>300000)){								  
								log.info("Cab Delay Message Triggered for First employee from web for second employee"+employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());	
								Thread thread1 = new Thread(new Runnable() {		
									public synchronized  void run() {
										try {
											long l1 = System.currentTimeMillis();			
											if(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")){														
												messaging.etaMessagesForGuestWhenCabDelay(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName(), employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), employeeMsgEta, employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber(),employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
											}
											else{
												messaging.etaMessageWhenCabDelay(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName(), employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), employeeMsgEta, employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber(),employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
											}
											long l2 = System.currentTimeMillis();			
											log.debug("Time taken by Sending Cab Delay Message from gate way for first employee trip Id: "+employeeTripDetailPO.get(k).getEmpTripId()+" Time "+(l2-l1)+"ms");
											Thread.currentThread().stop();
										} catch (Exception e) {
											log.error("Error Cab Delay Message Triggered for First employee from web for second employee"+employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
											Thread.currentThread().stop();

										}
									}
								}); thread1.start();
								employeeTripDetailPO.get(i).setCabDelayMsgStatus("Y");
								employeeTripDetailPO.get(i).setCabDelayMsgDeliveryDate(new Date());				   
							}
						}
						}
						else{
							if(employeeTripDetailPO.get(i).getReachedFlg().equalsIgnoreCase("N") && (new CalculateDistance().distance(Double.parseDouble(actualRouteTravelledPO.getLatitudeLongitude().split(",")[0]),Double.parseDouble(actualRouteTravelledPO.getLatitudeLongitude().split(",")[1]),Double.parseDouble(employeeTripDetailPO.get(i).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getLatitudeLongitude().split(",")[0]),(Double.parseDouble(employeeTripDetailPO.get(i).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getLatitudeLongitude().split(",")[1])), 'm'))<100){
								final int k=i;								
			                 	log.info("Sending Reached ETA Message for Trip: "+employeeTripDetailPO.get(k).getEmpTripId());
								    employeeTripDetailPO.get(i).setTwoMinuteMessageStatus("Y");
								    employeeTripDetailPO.get(i).setTwoMinuteMessageDeliveryDate(new Date());						 							
							}
							final String employeeMsgEta=etaInSec;
							final int k=i;
							if(employeeTripDetailPO.get(i).getTenMinuteMessageStatus().equalsIgnoreCase("N") && employeeTripDetailPO.get(i).getReachedFlg().equalsIgnoreCase("N") && eta<900){
								log.info("15 minute Message Triggered from Server for second employee"+employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
								Thread thread1 = new Thread(new Runnable() {		
									public synchronized void run() {
										// TODO Auto-generated method stub
										try {
											long l1 = System.currentTimeMillis();			
											MessagingService messaging=new MessagingService();
											if(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")){														
												messaging.etaMessagesForGuest(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName(), employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), employeeMsgEta, employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber(),employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
											}
											else{
												messaging.etaMessage(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName(), employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), employeeMsgEta, employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber(),employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
											}
											Thread.currentThread().stop();
											long l2 = System.currentTimeMillis();			
											log.debug("Time taken by Sending 15 minute Message from gate way for next employee trip Id: "+employeeTripDetailPO.get(k).getEmpTripId()+" Time "+(l2-l1)+"ms");

										} catch (Exception e) {
											log.error("Error 15 Minute Message Triggering from Server for second employee"+employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
											Thread.currentThread().stop();
										}
									}
								}); thread1.start();
								employeeTripDetailPO.get(i).setTenMinuteMessageStatus("Y");
								employeeTripDetailPO.get(i).setTenMinuteMessageDeliveryDate(new Date());					    
							}
							if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
							long delayCalculationTime=(new Date().getTime())+(eta*1000)-((actualRouteTravelled.get(actualRouteTravelled.size()-1).getTravelledTime().getTime())+(employeeTripDetailPO.get(k).getGoogleEta()*1000));	  
							if(employeeTripDetailPO.get(k).getTenMinuteMessageStatus().equalsIgnoreCase("Y") && employeeTripDetailPO.get(k).getCabDelayMsgStatus().equalsIgnoreCase("N") &&employeeTripDetailPO.get(k).getReachedFlg().equalsIgnoreCase("N") && (delayCalculationTime>300000)){								  
								log.info("Cab Delay Message Triggered for second employee from web for second employee"+employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());
								Thread thread1 = new Thread(new Runnable() {		
									public synchronized void run() {
										// TODO Auto-generated method stub
										try {
											long l1 = System.currentTimeMillis();			
											MessagingService messaging=new MessagingService();
											if(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType().equalsIgnoreCase("guest")){														
												messaging.etaMessagesForGuestWhenCabDelay(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName(), employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), employeeMsgEta, employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber(),employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
											}
											else{
												messaging.etaMessageWhenCabDelay(employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getFirstName(), employeeTripDetailPO.get(k).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber(), employeeMsgEta, employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getMobileNumber(),employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getRequestType());						
											}
											long l2 = System.currentTimeMillis();			
											log.debug("Time taken by Sending Cab Delay Message from gate way for next employee trip Id: "+employeeTripDetailPO.get(k).getEmpTripId()+" Time "+(l2-l1)+"ms");
											Thread.currentThread().stop();
										} catch (Exception e) {
											log.info("Error Cab Delay Message Triggered for second employee from web for second employee"+employeeTripDetailPO.get(k).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getMobileNumber());

											Thread.currentThread().stop();

										}
									}
								}); thread1.start();
								employeeTripDetailPO.get(i).setCabDelayMsgStatus("Y");
								employeeTripDetailPO.get(i).setCabDelayMsgDeliveryDate(new Date());				   
							}		   
						}

						}

						employeeTripDetailPO.get(i).setCurrenETA(etaInSec);
						employeeTripDetailPO.get(i).setGoogleEta(eta);
						iCabRequestBO.update(employeeTripDetailPO.get(i));
					}catch(Exception e){}							
				}
			}


			if(driverEta.length()==0 && driverEta.isEmpty()){
				if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
					driverEta=actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentEta();
				}
				else{
					driverEta="calculating...";
				}
			}
		}
		if(actualRouteTravelled.size()>0){
//			String extensionRemoved = actualRouteTravelledPO.getTravelledDistance().split("\\.")[1];
//			if(extensionRemoved.length()>1){
				liveRoute.get(0).setTravelledDistance(liveRoute.get(0).getTravelledDistance()+Double.valueOf(actualRouteTravelledPO.getTravelledDistance()));
				assignRouteBO.update(liveRoute.get(0));
//			}
		}
		if(Double.parseDouble(actualRouteTravelledPO.getSpeed())>57){
		EFmFmAlertTypeMasterPO alertTypeMasterPO=new EFmFmAlertTypeMasterPO();
		EFmFmTripAlertsPO eFmFmTripAlertsPO=new EFmFmTripAlertsPO();
		alertTypeMasterPO.setAlertId(10);
		eFmFmTripAlertsPO.setEfmFmAlertTypeMaster(alertTypeMasterPO);
		eFmFmTripAlertsPO.setCreationTime(new Date());
		eFmFmTripAlertsPO.setUpdatedTime(new Date());
		eFmFmTripAlertsPO.setEfmFmAssignRoute(assignRoutePO);
		eFmFmTripAlertsPO.setUserType("Driver");
		eFmFmTripAlertsPO.setAlertOpenStatus("open");
		eFmFmTripAlertsPO.setAlertClosingDescription("No Action");
		eFmFmTripAlertsPO.setCurrentSpeed(actualRouteTravelledPO.getSpeed());
		eFmFmTripAlertsPO.setStatus("Y");
		iAlertBO.save(eFmFmTripAlertsPO);
		}
		
		//saving speed alerts...		
		actualRouteTravelledPO.setTravelledTime(new Date());	
		actualRouteTravelledPO.setEfmFmAssignRoute(assignRoutePO);
		actualRouteTravelledPO.seteFmFmClientBranchPO(clientBranch);
		actualRouteTravelledPO.setEtaInSeconds(etaInSeconds);
		actualRouteTravelledPO.setCurrentEta(driverEta);
		if(cabCurrentLocation.length()!=0 && (!(cabCurrentLocation.isEmpty())))
			actualRouteTravelledPO.setCurrentCabLocation(cabCurrentLocation);
		assignRouteBO.save(actualRouteTravelledPO);		
		return driverEta;
	}


	/**
	 * The shifTime method implemented.
	 * for getting the list of shif timing from shift time table.   
	 *
	 * @author  Sarfraz Khan
	 * 	
	 */	
	@POST
	@Path("/editemployeetravelrequest")
	public Response editEmployeeDetailsFromTravelDesk(EFmFmEmployeeTravelRequestPO employeeRequest){	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");		
		Map<String, Object>  responce = new HashMap<String,Object>();
		employeeRequest.setRequestDate(new Date());
		//		List<EFmFmClientUserRolePO> userDetail=userMasterBO.getUserRolesFromUserIdAndBranchId(employeeRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId(), employeeRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		List<Map<String, Object>> shitTimings = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> allRoutesData = new ArrayList<Map<String, Object>>();

		List<EFmFmClientRouteMappingPO> allRoutes=iRouteDetailBO.getAllRoutesOfParticularClient(employeeRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		DateFormat  shiftTimeFormater = new SimpleDateFormat("HH:mm");
		List<EFmFmTripTimingMasterPO> shiftTimeDetails=iCabRequestBO.listOfShiftTime(employeeRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
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
		responce.put("status", "success");
		responce.put("shiftTimings", shitTimings);
		responce.put("routesData", allRoutesData);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	
	@POST
	@Path("/allzone")
	public Response getAllZonesUsingClientId(EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO){	
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");		
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<Map<String, Object>> allRoutesData = new ArrayList<Map<String, Object>>();
		List<EFmFmClientRouteMappingPO> allRoutes=iRouteDetailBO.getAllRoutesOfParticularClient(eFmFmClientRouteMappingPO.geteFmFmClientBranchPO().getBranchId());
		if((!(allRoutes.isEmpty())) || allRoutes.size() !=0){
			for(EFmFmClientRouteMappingPO routeDetails:allRoutes){				
				Map<String, Object>  routeName= new HashMap<String, Object>();
				routeName.put("routeName", routeDetails.geteFmFmZoneMaster().getZoneName());
				routeName.put("routeId", routeDetails.geteFmFmZoneMaster().getZoneId());				
				allRoutesData.add(routeName);									
			}

		}	
		responce.put("status", "success");
		responce.put("zones", allRoutesData);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/addzone")
	public Response addNewZone(EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO){	
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");		
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmClientRouteMappingPO> allRoutes=iRouteDetailBO.getParticularRouteDetailByClient(eFmFmClientRouteMappingPO.geteFmFmClientBranchPO().getBranchId(),eFmFmClientRouteMappingPO.getRouteName());
		if((!(allRoutes.isEmpty())) || allRoutes.size() !=0){
			responce.put("status", "fail");	
		}	
		EFmFmZoneMasterPO eFmFmZoneMaster=new EFmFmZoneMasterPO();
		eFmFmZoneMaster.setZoneName(eFmFmClientRouteMappingPO.getRouteName());
		eFmFmZoneMaster.setStatus("Y");
		eFmFmZoneMaster.setCreationTime(new Date());
		iRouteDetailBO.saveRouteNameRecord(eFmFmZoneMaster);
		List<EFmFmZoneMasterPO> newZoneDetail=iRouteDetailBO.getAllRouteName(eFmFmClientRouteMappingPO.getRouteName());
		eFmFmClientRouteMappingPO.seteFmFmZoneMaster(newZoneDetail.get(0));
		iRouteDetailBO.saveClientRouteMapping(eFmFmClientRouteMappingPO);
		responce.put("routeId", newZoneDetail.get(0).getZoneId());

		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	

	@POST
	@Path("/zonevicearea")
	public Response getAllAreasOfAParticularZone(EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO){	
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");		
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<Map<String, Object>> allAreasData = new ArrayList<Map<String, Object>>();
		List<EFmFmRouteAreaMappingPO> allZoneAreas=iRouteDetailBO.getAllAreasFromZoneId(eFmFmClientRouteMappingPO.geteFmFmZoneMaster().getZoneId());
		for(EFmFmRouteAreaMappingPO routeAreaMappingPO:allZoneAreas){
			Map<String, Object>  areaName= new HashMap<String, Object>();
			areaName.put("areaName", routeAreaMappingPO.getEfmFmAreaMaster().getAreaName());
			areaName.put("areaId", routeAreaMappingPO.getEfmFmAreaMaster().getAreaId());
			allAreasData.add(areaName);
		}
		responce.put("status", "success");
		responce.put("areas", allAreasData);
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/updatetravelrequestdata")
	public Response updateTravelRequestData(EFmFmEmployeeTravelRequestPO employeeRequest) throws ParseException{	
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");	
		EFmFmAreaMasterPO efmFmAreaMaster=new EFmFmAreaMasterPO();
		efmFmAreaMaster.setAreaId(employeeRequest.getAreaId());
		EFmFmZoneMasterPO eFmFmZoneMaster=new EFmFmZoneMasterPO();
		eFmFmZoneMaster.setZoneId(employeeRequest.getZoneId());
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmEmployeeTravelRequestPO> employeeRequestDetails=iCabRequestBO.getparticularRequestDetail(employeeRequest);
		EFmFmRouteAreaMappingPO eFmFmRouteAreaMapping=new EFmFmRouteAreaMappingPO();
		eFmFmRouteAreaMapping.seteFmFmZoneMaster(eFmFmZoneMaster);
		eFmFmRouteAreaMapping.setEfmFmAreaMaster(efmFmAreaMaster);
		DateFormat timeformate = new SimpleDateFormat("HH:mm");
		String shiftDate=employeeRequest.getTime();
		Date shift  = (Date) timeformate.parse(shiftDate);				 
		java.sql.Time shiftTime = new java.sql.Time(shift.getTime());	
		EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO=new EFmFmEmployeeTravelRequestPO();
		EFmFmUserMasterPO efmFmUserMaster=new EFmFmUserMasterPO();
		efmFmUserMaster.setUserId(employeeRequestDetails.get(0).getEfmFmUserMaster().getUserId());
		EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
		eFmFmClientBranchPO.setBranchId(employeeRequestDetails.get(0).getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		efmFmUserMaster.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		eFmFmEmployeeTravelRequestPO.setEfmFmUserMaster(efmFmUserMaster);
		eFmFmEmployeeTravelRequestPO.setTripType("DROP");
		eFmFmEmployeeTravelRequestPO.setRequestType(employeeRequestDetails.get(0).getRequestType());
		List<EFmFmEmployeeTravelRequestPO> dropRequestDetails=iCabRequestBO.getAnotherActiveRequestDetail(eFmFmEmployeeTravelRequestPO);
		List<EFmFmRouteAreaMappingPO> routeAreaId=null;
		routeAreaId=iRouteDetailBO.routeMappaingAlreadyExist(eFmFmRouteAreaMapping);
		if((routeAreaId.isEmpty()) || routeAreaId.size() ==0){
		iRouteDetailBO.saveRouteMappingDetails(eFmFmRouteAreaMapping);
		routeAreaId=iRouteDetailBO.routeMappaingAlreadyExist(eFmFmRouteAreaMapping);
		}
		if(employeeRequest.getUpdateRegularRequest().equalsIgnoreCase("N")){
			employeeRequestDetails.get(0).setShiftTime(shiftTime);
			if(employeeRequestDetails.get(0).getTripType().equalsIgnoreCase("PICKUP")){
				String pickUpTime=employeeRequest.getPickTime();
				Date changePickUpTime  = (Date) timeformate.parse(pickUpTime);				 
				java.sql.Time pickTime = new java.sql.Time(changePickUpTime.getTime());
				employeeRequestDetails.get(0).setPickUpTime(pickTime);
				//employeeRequestDetails.get(0).setTripType("DROP");
				if((!(dropRequestDetails.isEmpty())) || dropRequestDetails.size() !=0){	
					java.sql.Time changeShiftTime = new java.sql.Time(employeeRequestDetails.get(0).getShiftTime().getTime()+32400000);
					dropRequestDetails.get(0).setShiftTime(changeShiftTime);
					dropRequestDetails.get(0).seteFmFmRouteAreaMapping(routeAreaId.get(0));	
					iCabRequestBO.update(dropRequestDetails.get(0));
				}
			}
			else{
				String pickUpTime=employeeRequest.getPickTime();				
				employeeRequestDetails.get(0).setDropSequence(Integer.parseInt(pickUpTime));
				if((!(dropRequestDetails.isEmpty())) || dropRequestDetails.size() !=0){	
					java.sql.Time changeShiftTime = new java.sql.Time(employeeRequestDetails.get(0).getShiftTime().getTime()+32400000);
					dropRequestDetails.get(0).setShiftTime(changeShiftTime);
					dropRequestDetails.get(0).seteFmFmRouteAreaMapping(routeAreaId.get(0));	
					iCabRequestBO.update(dropRequestDetails.get(0));
				}
			}
			employeeRequestDetails.get(0).seteFmFmRouteAreaMapping(routeAreaId.get(0));	
			iCabRequestBO.update(employeeRequestDetails.get(0));
		}
		
		
		
		else{
			employeeRequestDetails.get(0).setShiftTime(shiftTime);
			if(employeeRequestDetails.get(0).getTripType().equalsIgnoreCase("PICKUP")){
				String pickUpTime=employeeRequest.getPickTime();
				Date changePickUpTime  = (Date) timeformate.parse(pickUpTime);				 
				java.sql.Time pickTime = new java.sql.Time(changePickUpTime.getTime());
				employeeRequestDetails.get(0).setPickUpTime(pickTime);
				if((!(dropRequestDetails.isEmpty())) || dropRequestDetails.size() !=0){	
					java.sql.Time changeShiftTime = new java.sql.Time(employeeRequestDetails.get(0).getShiftTime().getTime()+32400000);
					dropRequestDetails.get(0).setShiftTime(changeShiftTime);
					dropRequestDetails.get(0).seteFmFmRouteAreaMapping(routeAreaId.get(0));	
					iCabRequestBO.update(dropRequestDetails.get(0));
				}
			}
			else{
				String pickUpTime=employeeRequest.getPickTime();
				employeeRequestDetails.get(0).setDropSequence(Integer.parseInt(pickUpTime));
				if((!(dropRequestDetails.isEmpty())) || dropRequestDetails.size() !=0){	
					java.sql.Time changeShiftTime = new java.sql.Time(employeeRequestDetails.get(0).getShiftTime().getTime()+32400000);
					dropRequestDetails.get(0).setShiftTime(changeShiftTime);
					dropRequestDetails.get(0).seteFmFmRouteAreaMapping(routeAreaId.get(0));	
					iCabRequestBO.update(dropRequestDetails.get(0));
				}
			}
			employeeRequestDetails.get(0).seteFmFmRouteAreaMapping(routeAreaId.get(0));	
			iCabRequestBO.update(employeeRequestDetails.get(0));
			List<EFmFmEmployeeRequestMasterPO> requestDetails=iCabRequestBO.getRequestFromRequestMaster(employeeRequestDetails.get(0).geteFmFmEmployeeRequestMaster().getTripId(),employeeRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
			requestDetails.get(0).setShiftTime(shiftTime);		
			if(employeeRequestDetails.get(0).geteFmFmEmployeeRequestMaster().getTripType().equalsIgnoreCase("PICKUP")){
				String pickUpTime=employeeRequest.getPickTime();
				Date changePickUpTime  = (Date) timeformate.parse(pickUpTime);				 
				java.sql.Time pickTime = new java.sql.Time(changePickUpTime.getTime());
				requestDetails.get(0).setPickUpTime(pickTime);
				if((!(dropRequestDetails.isEmpty())) || dropRequestDetails.size() !=0){
					List<EFmFmEmployeeRequestMasterPO> anotherRequestDetails=iCabRequestBO.getRequestFromRequestMaster(dropRequestDetails.get(0).geteFmFmEmployeeRequestMaster().getTripId(),employeeRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
					if((!(anotherRequestDetails.isEmpty())) || anotherRequestDetails.size() !=0){
						java.sql.Time changeShiftTime = new java.sql.Time(employeeRequestDetails.get(0).getShiftTime().getTime()+32400000);
						anotherRequestDetails.get(0).setShiftTime(changeShiftTime);
						iCabRequestBO.update(anotherRequestDetails.get(0));
					}
				}
			}
			else{
				String pickUpTime=employeeRequest.getPickTime();
				requestDetails.get(0).setDropSequence(Integer.parseInt(pickUpTime));
				if((!(dropRequestDetails.isEmpty())) || dropRequestDetails.size() !=0){
					List<EFmFmEmployeeRequestMasterPO> anotherRequestDetails=iCabRequestBO.getRequestFromRequestMaster(dropRequestDetails.get(0).geteFmFmEmployeeRequestMaster().getTripId(),employeeRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
					if((!(anotherRequestDetails.isEmpty())) || anotherRequestDetails.size() !=0){
						java.sql.Time changeShiftTime = new java.sql.Time(employeeRequestDetails.get(0).getShiftTime().getTime()+32400000);
						anotherRequestDetails.get(0).setShiftTime(changeShiftTime);
						iCabRequestBO.update(anotherRequestDetails.get(0));
					}
				}

			
			}
			iCabRequestBO.update(requestDetails.get(0));
			EFmFmUserMasterPO eFmFmUserMaster=new EFmFmUserMasterPO();
			EFmFmClientBranchPO eFmFmClientBranchPO1=new EFmFmClientBranchPO();
			eFmFmClientBranchPO1.setBranchId(employeeRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
			eFmFmUserMaster.seteFmFmClientBranchPO(eFmFmClientBranchPO1);
			eFmFmUserMaster.setUserId(requestDetails.get(0).getEfmFmUserMaster().getUserId());
			List<EFmFmUserMasterPO> userDetail=userMasterBO.getLoggedInUserDetailFromClientIdAndUserId(eFmFmUserMaster);
			userDetail.get(0).seteFmFmRouteAreaMapping(routeAreaId.get(0));
			userDetail.get(0).setWeekOffDays(employeeRequest.getWeekOffs());
			userMasterBO.update(userDetail.get(0));
		}
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/devicedbcall")
	public Response latiLongiFromDeviceDataBase(EFmFmActualRoutTravelledPO actualRouteTravelledPO) throws IOException, ParseException{
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String startTime=actualRouteTravelledPO.getTime();
		Date travelledDateTime=(Date) dateTimeFormate.parse(startTime);
		Map<String, Object>  requests = new HashMap<String, Object>();	

		EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
		EFmFmClientBranchPO clientBranch=new EFmFmClientBranchPO();										
		clientBranch.setBranchId(actualRouteTravelledPO.geteFmFmClientBranchPO().getBranchId());		
		assignRoutePO.setAssignRouteId(actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId());
		assignRoutePO.seteFmFmClientBranchPO(clientBranch);
    	List<EFmFmAssignRoutePO> assignRoute=assignRouteBO.closeParticularTrips(assignRoutePO);
		if((!(assignRoute.isEmpty())) || assignRoute.size() !=0){
	    	assignRoute.get(0).setTravelledDistance(assignRoute.get(0).getTravelledDistance()+Double.valueOf(actualRouteTravelledPO.getTravelledDistance()));
			assignRouteBO.update(assignRoute.get(0));
		}
		actualRouteTravelledPO.seteFmFmClientBranchPO(clientBranch);
		actualRouteTravelledPO.setEfmFmAssignRoute(assignRoutePO);
		actualRouteTravelledPO.setTravelledTime(travelledDateTime);
		assignRouteBO.save(actualRouteTravelledPO);
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}



	@POST
	@Path("/drivereta")
	public Response etaForDriver(EFmFmActualRoutTravelledPO actualRouteTravelledPO) throws IOException{
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		Map<String, Object>  requests = new HashMap<String, Object>();	
		EFmFmActualRoutTravelledPO actualRoutTravelled=new EFmFmActualRoutTravelledPO();
		actualRoutTravelled.setTravelledTime(new Date());
		EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
		EFmFmClientBranchPO clientBranch=new EFmFmClientBranchPO();										
		clientBranch.setBranchId(actualRouteTravelledPO.geteFmFmClientBranchPO().getBranchId());		
		assignRoutePO.setAssignRouteId(actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId());
		actualRoutTravelled.seteFmFmClientBranchPO(clientBranch);
		actualRoutTravelled.setEfmFmAssignRoute(assignRoutePO);
		List<EFmFmActualRoutTravelledPO> actualRouteTravelled=assignRouteBO.getEtaAndDistanceFromAssignRouteId(actualRoutTravelled);
		if(actualRouteTravelled.size()!=0 || !(actualRouteTravelled.isEmpty())){
			requests.put("eta", actualRouteTravelled.get(actualRouteTravelled.size()-1).getCurrentEta());
		}
		else{
			requests.put("eta", "0");
		}
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	
	public void  passingKey(String keyString) throws IOException {
	    // Convert the key from 'web safe' base 64 to binary
	    keyString = keyString.replace('-', '+');
	    keyString = keyString.replace('_', '/');
	    System.out.println("Key: " + keyString);
	    this.key = Base64.decode(keyString);
	  }

	  public String signRequest(String path, String query) throws NoSuchAlgorithmException,
	    InvalidKeyException, UnsupportedEncodingException, URISyntaxException {   
	    // Retrieve the proper URL components to sign
	    String resource = path + '?' + query;  
	    // Get an HMAC-SHA1 signing key from the raw key bytes
	    SecretKeySpec sha1Key = new SecretKeySpec(key, "HmacSHA1");
	    // Get an HMAC-SHA1 Mac instance and initialize it with the HMAC-SHA1 key
	    Mac mac = Mac.getInstance("HmacSHA1");
	    mac.init(sha1Key);
	    // compute the binary signature for the request
	    byte[] sigBytes = mac.doFinal(resource.getBytes());
	    // base 64 encode the binary signature
	    String signature = Base64.encode(sigBytes);	    
	    // convert the signature to 'web safe' base 64
	    signature = signature.replace('+', '-');
	    signature = signature.replace('/', '_');	    
	    return resource + "&signature=" + signature;
	  }	
}