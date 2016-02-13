package com.newtglobal.eFmFmFleet.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.IEmployeeDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverFeedbackPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/user")
@Consumes("application/json")
@Produces("application/json")
public class RegistrationService {
	String result = "";
	private static Log log = LogFactory.getLog(RegistrationService.class);

	
	/**
	 * 
	 * @param Device Registration with a specific Number
	 * 
	 * @return driver details exist check
	 */
	@GET
	@Path("/dlrrequest")
	public Response dlrRequest(
			@QueryParam("myid") String myid,
			@QueryParam("status") String status,
			@QueryParam("reciever") String reciever,
			@QueryParam("updated_on") String updated_on,
			@QueryParam("res") String res){

		log.info("myid"+myid);
		log.info("status"+status);
		log.info("reciever"+reciever);
		log.info("updated_on"+updated_on);
		log.info("res"+res);
		
		
		Map<String, Object>  responce = new HashMap<String,Object>();

		/*	
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmDeviceMasterPO> deviceDetail=iVehicleCheckInBO.deviceNumberExistsCheck(eFmFmDeviceMasterPO);
		if(!(deviceDetail.isEmpty()) || (deviceDetail.size()!=0)){
			responce.put("status", "exist");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		eFmFmDeviceMasterPO.setStatus("Y");
		eFmFmDeviceMasterPO.setDeviceType("Android");
		eFmFmDeviceMasterPO.setIsActive("Y");
		iVehicleCheckInBO.save(eFmFmDeviceMasterPO);
	*/	responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	
	/**
	 * 
	 * @param Device Registration with a specific Number
	 * 
	 * @return driver details exist check
	 */
	
	@POST
	@Path("/deviceregisteration")
	public Response deviceRegistration(EFmFmDeviceMasterPO eFmFmDeviceMasterPO){	
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmDeviceMasterPO> deviceDetail=iVehicleCheckInBO.deviceNumberExistsCheck(eFmFmDeviceMasterPO);
		if(!(deviceDetail.isEmpty()) || (deviceDetail.size()!=0)){
			responce.put("status", "exist");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		eFmFmDeviceMasterPO.setStatus("Y");
		eFmFmDeviceMasterPO.setDeviceType("Android");
		eFmFmDeviceMasterPO.setIsActive("Y");
		iVehicleCheckInBO.save(eFmFmDeviceMasterPO);
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * 
	 * @param Device Splash Activity
	 * 
	 * @return device exist or check in check
	 */
	
	@POST
	@Path("/devicecheck")
	public Response deviceSplashCheck(EFmFmDeviceMasterPO eFmFmDeviceMasterPO){	
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmDeviceMasterPO> deviceDetail=iVehicleCheckInBO.deviceImeiNumberExistsCheck(eFmFmDeviceMasterPO);
		if(deviceDetail.isEmpty() || deviceDetail.size()==0){
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		else{			
			List<EFmFmVehicleCheckInPO> checkInDetail=iVehicleCheckInBO.getParticularCheckedInDeviceDetails(eFmFmDeviceMasterPO.geteFmFmClientBranchPO().getBranchId(), deviceDetail.get(0).getDeviceId());
	        if(checkInDetail.size()==0 || checkInDetail.isEmpty()){
	          List<EFmFmVehicleCheckInPO> lastCheckInDetail=iVehicleCheckInBO.getLastCheckedOutInDeviceDetails(eFmFmDeviceMasterPO.geteFmFmClientBranchPO().getBranchId(), deviceDetail.get(0).getDeviceId());
	          if(!(lastCheckInDetail.isEmpty()) || (lastCheckInDetail.size()!=0))
	        	responce.put("vehicleNum",lastCheckInDetail.get(lastCheckInDetail.size()-1).getEfmFmVehicleMaster().getVehicleNumber());  
				responce.put("status", "checkin");
				return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	        }
			if(!(checkInDetail.isEmpty()) || (checkInDetail.size()!=0)){
				DateFormat  dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
				responce.put("driverName",checkInDetail.get(0).getEfmFmDriverMaster().getFirstName());		
				responce.put("driverNumber", checkInDetail.get(0).getEfmFmDriverMaster().getMobileNumber());
				responce.put("licenceValid",dateFormatter.format(checkInDetail.get(0).getEfmFmDriverMaster().getLicenceValid()));		
				responce.put("vehicleNumber",checkInDetail.get(0).getEfmFmVehicleMaster().getVehicleNumber());		
				responce.put("capacity",checkInDetail.get(0).getEfmFmVehicleMaster().getCapacity());				
				responce.put("profilePic",checkInDetail.get(0).getEfmFmDriverMaster().getProfilePicPath());	
				responce.put("status", "home");
				return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	        }			
		}
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * 
	 * 
	 * @param driverMaster checkin the particular driver with a particular vehicle from device
	 * @return if enter vehicle number is exist
	 */
	
	@POST
	@Path("/deviceCheckIn")
	public Response driverCheckInFromDevice(EFmFmDeviceMasterPO eFmFmDeviceMasterPO){	
		IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");

		Map<String, Object>  responce = new HashMap<String,Object>();
		EFmFmVehicleMasterPO vehicleDetails=vehicleCheckInBO.getParticularVehicleDetails(eFmFmDeviceMasterPO.getVehicleNum(), eFmFmDeviceMasterPO.geteFmFmClientBranchPO().getBranchId());
        List<EFmFmDriverMasterPO> driverDetails=approvalBO.getParticularDriverDetailFromDeriverId(eFmFmDeviceMasterPO.getDriverId());

		if(vehicleDetails!=null){
			if(vehicleDetails.getStatus().equalsIgnoreCase("P") || vehicleDetails.getStatus().equalsIgnoreCase("R")){
				responce.put("status", "VNA");		
			    return Response.ok(responce, MediaType.APPLICATION_JSON).build();
			}
		}
		else if(vehicleDetails==null){
			responce.put("status", "Vwrong");		
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		if(!(driverDetails.isEmpty()) || (driverDetails.size()!=0)){
				if(driverDetails.get(0).getStatus().equalsIgnoreCase("P") || driverDetails.get(0).getStatus().equalsIgnoreCase("R")){
					responce.put("status", "DNA");		
				    return Response.ok(responce, MediaType.APPLICATION_JSON).build();
				}			
		}
		else if(driverDetails.isEmpty() || driverDetails.size()==0){
			responce.put("status", "Dwrong");		
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}				
		EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
		EFmFmVehicleMasterPO vehicleMaster=new EFmFmVehicleMasterPO();
		vehicleMaster.setVehicleId(vehicleDetails.getVehicleId());
		EFmFmDriverMasterPO driverMaster= new EFmFmDriverMasterPO();
		driverMaster.setDriverId(driverDetails.get(0).getDriverId());
		eFmFmVehicleCheckInPO.setCheckInTime(new Date());
		eFmFmVehicleCheckInPO.setEfmFmVehicleMaster(vehicleMaster);
		List<EFmFmVehicleCheckInPO> checkInVehicle=vehicleCheckInBO.getParticularCheckedInVehicles(eFmFmDeviceMasterPO.geteFmFmClientBranchPO().getBranchId(), vehicleDetails.getVehicleId());
		if((!(checkInVehicle.isEmpty())) || checkInVehicle.size() !=0){	
			responce.put("status", "VcheckedIn");		
		    return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		eFmFmVehicleCheckInPO.setEfmFmDriverMaster(driverMaster);
		List<EFmFmVehicleCheckInPO> checkInDriver=vehicleCheckInBO.getParticularCheckedInDriver(eFmFmVehicleCheckInPO);						
		if((!(checkInDriver.isEmpty())) || checkInDriver.size() !=0){	
			responce.put("status", "DcheckedIn");		
		   return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		List<EFmFmDeviceMasterPO> deviceDetail=vehicleCheckInBO.deviceImeiNumberExistsCheck(eFmFmDeviceMasterPO);			
		if(!(driverDetails.get(0).getEfmFmVendorMaster().getVendorId()==vehicleDetails.getEfmFmVendorMaster().getVendorId())){
			responce.put("status", "mismatch");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}		
		EFmFmDeviceMasterPO deviceMaster=new EFmFmDeviceMasterPO();
		deviceMaster.setDeviceId(deviceDetail.get(0).getDeviceId());
		eFmFmVehicleCheckInPO.seteFmFmDeviceMaster(deviceMaster);
		eFmFmVehicleCheckInPO.setStatus("Y");
		vehicleCheckInBO.vehicleDriverCheckIn(eFmFmVehicleCheckInPO);
		DateFormat  dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		responce.put("driverName",driverDetails.get(0).getFirstName());		
		responce.put("driverNumber", driverDetails.get(0).getMobileNumber());
		responce.put("licenceValid",dateFormatter.format(driverDetails.get(0).getLicenceValid()));		
		responce.put("vehicleNumber",vehicleDetails.getVehicleNumber());
		responce.put("profilePic",driverDetails.get(0).getProfilePicPath());	

		responce.put("capacity",vehicleDetails.getCapacity());		
		responce.put("status", "success");		
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	
	
	/**
	 * 
	 * 
	 * @param driverMaster checkout the particular driver with a particular vehicle from device
	 * @return return Success
	 */
	
	@POST
	@Path("/driverCheckOut")
	public Response driverCheckOutFromDevice(EFmFmDeviceMasterPO eFmFmDeviceMasterPO){	
		IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmDeviceMasterPO> deviceDetail=vehicleCheckInBO.deviceImeiNumberExistsCheck(eFmFmDeviceMasterPO);	
		EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
		eFmFmVehicleCheckInPO.setCheckInTime(new Date());
		eFmFmVehicleCheckInPO.seteFmFmDeviceMaster(deviceDetail.get(0));		
		List<EFmFmVehicleCheckInPO> vehicleCheckInPO=vehicleCheckInBO.checkedOutParticularDriver(eFmFmVehicleCheckInPO);
		if(vehicleCheckInPO.size()!=0 || !(vehicleCheckInPO.isEmpty())){
		if(vehicleCheckInPO.get(0).getStatus().equalsIgnoreCase("N")){
			responce.put("status", "fail");		
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		vehicleCheckInPO.get(vehicleCheckInPO.size()-1).setStatus("N");
		vehicleCheckInPO.get(vehicleCheckInPO.size()-1).setCheckOutTime(new Date());
		vehicleCheckInBO.update(vehicleCheckInPO.get(vehicleCheckInPO.size()-1));	
		}
		responce.put("status", "success");		
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	

	/**
	 * 
	 * @param driver profile
	 * 
	 * @return driver details
	 */
	
	@POST
	@Path("/driverprofile")
	public Response getDriverProfile(EFmFmDeviceMasterPO deviceMasterPO){					
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");						
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmDeviceMasterPO>  deviceDetail=iVehicleCheckInBO.deviceImeiNumberExistsCheck(deviceMasterPO);		
        List<EFmFmVehicleCheckInPO>  checkInDriverDetail=iVehicleCheckInBO.getParticularCheckedInDeviceDetails(deviceMasterPO.geteFmFmClientBranchPO().getBranchId(),deviceDetail.get(0).getDeviceId());
		if(checkInDriverDetail.size()!=0 || !(checkInDriverDetail.isEmpty())){
			DateFormat  dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
			responce.put("driverName",checkInDriverDetail.get(0).getEfmFmDriverMaster().getFirstName());		
			responce.put("driverNumber", checkInDriverDetail.get(0).getEfmFmDriverMaster().getMobileNumber());
			responce.put("licenceValid",dateFormatter.format(checkInDriverDetail.get(0).getEfmFmDriverMaster().getLicenceValid()));		
			responce.put("vehicleNumber",checkInDriverDetail.get(0).getEfmFmVehicleMaster().getVehicleNumber());	
			responce.put("profilePic",checkInDriverDetail.get(0).getEfmFmDriverMaster().getProfilePicPath());	

			responce.put("capacity",checkInDriverDetail.get(0).getEfmFmVehicleMaster().getCapacity());		
		}
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 * 
	 * Employee spalce activity  Check
	 */
	
	@POST
	@Path("/registercheck")
	public Response registerCheck(EFmFmUserMasterPO eFmFmUserMasterPO){					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");			
		Map<String, Object>  responce = new HashMap<String,Object>();
			EFmFmUserMasterPO userMasterDetail=employeeDetailBO.getParticularDeviceDetails(eFmFmUserMasterPO.getDeviceId());
			if(userMasterDetail!=null){
			if(userMasterDetail.isLoggedIn()){
				responce.put("employeeName", userMasterDetail.getFirstName());
				responce.put("userId", userMasterDetail.getUserId());
				responce.put("employeeId", userMasterDetail.getEmployeeId());
				responce.put("panicNumber", userMasterDetail.getPanicNumber());
				responce.put("branchId", userMasterDetail.geteFmFmClientBranchPO().getBranchId());
//				responce.put("dateOfBirth", employeeMasterPO.getDateOfBirth());				
				responce.put("emailId", userMasterDetail.getEmailId());
				responce.put("designation", userMasterDetail.getEmployeeDesignation());
				responce.put("emergencyNum", userMasterDetail.getPanicNumber());

				responce.put("mobileNumber", userMasterDetail.getMobileNumber());
				responce.put("status", "loggedIn");
				return Response.ok(responce, MediaType.APPLICATION_JSON).build();
			}
			}
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 * Employee device registration....Service
	 */
		
	@POST
	@Path("/employeeregister")
	public Response employeeRegistration(EFmFmUserMasterPO eFmFmUserMasterPO){					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");	
		Map<String, Object>  responce = new HashMap<String,Object>();
		log.info("device token"+eFmFmUserMasterPO.getDeviceToken());
		log.info("device id"+eFmFmUserMasterPO.getDeviceId());
		List<EFmFmClientBranchPO> clientMasterDetail=employeeDetailBO.doesClientCodeExist(eFmFmUserMasterPO.geteFmFmClientBranchPO().getBranchCode());
		if(clientMasterDetail.size()>0){
			List<EFmFmUserMasterPO> userMasterDetail=employeeDetailBO.getParticularEmpDetailsFromEmployeeId(eFmFmUserMasterPO.getEmployeeId(),clientMasterDetail.get(0).getBranchId());
	         if(userMasterDetail.size()!=0){
	        	 try{
	        	 if(userMasterDetail.get(0).getDeviceId().equalsIgnoreCase(eFmFmUserMasterPO.getDeviceId())){
	 	     		responce.put("status", "already");	
		    		return Response.ok(responce, MediaType.APPLICATION_JSON).build(); 
	        	 }
	        	 }catch(Exception e){}	        	 
	        	 userMasterDetail.get(0).setLoggedIn(true);
//	        	 userMasterDetail.get(0).setPassword(eFmFmUserMasterPO.getPassword());
	        	 userMasterDetail.get(0).setDeviceToken(eFmFmUserMasterPO.getDeviceToken());
	        	 userMasterDetail.get(0).setDeviceType(eFmFmUserMasterPO.getDeviceType());
	        	 userMasterDetail.get(0).setDeviceId(eFmFmUserMasterPO.getDeviceId());
	        	 userMasterDetail.get(0).setLoggedIn(true);
	        	 employeeDetailBO.update(userMasterDetail.get(0));
	 			responce.put("employeeName", userMasterDetail.get(0).getFirstName());
				responce.put("userId", userMasterDetail.get(0).getUserId());
				responce.put("branchId", userMasterDetail.get(0).geteFmFmClientBranchPO().getBranchId());
				responce.put("employeeId", userMasterDetail.get(0).getEmployeeId());
				responce.put("panicNumber", userMasterDetail.get(0).getPanicNumber());
				responce.put("emailId", userMasterDetail.get(0).getEmailId());
				responce.put("emergencyNum", userMasterDetail.get(0).getPanicNumber());
				responce.put("designation", userMasterDetail.get(0).getEmployeeDesignation());
				responce.put("mobileNumber", userMasterDetail.get(0).getMobileNumber());
	        	responce.put("status", "success");		     		
	    		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	         }
	         else{
	       		responce.put("status", "failed");
	     		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	 
	         }
		}
   		responce.put("status", "codeerror");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 * Employee Login Service
	 * 
	 */
	
	@POST
	@Path("/login")
	public Response employeeLogin(EFmFmUserMasterPO eFmFmUserMasterPO){					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");			
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmUserMasterPO> userMasterDetail=employeeDetailBO.loginEmployeeDetails(eFmFmUserMasterPO.getEmployeeId(),eFmFmUserMasterPO.getPassword());
		if(userMasterDetail.size()!=0){
			userMasterDetail.get(0).setLoggedIn(true);
			responce.put("employeeName", userMasterDetail.get(0).getFirstName());
			responce.put("userId", userMasterDetail.get(0).getUserId());
			responce.put("branchId", userMasterDetail.get(0).geteFmFmClientBranchPO().getBranchId());
			responce.put("employeeId", userMasterDetail.get(0).getEmployeeId());
			responce.put("panicNumber", userMasterDetail.get(0).getPanicNumber());
			responce.put("emailId", userMasterDetail.get(0).getEmailId());
			responce.put("emergencyNum", userMasterDetail.get(0).getPanicNumber());
			responce.put("designation", userMasterDetail.get(0).getEmployeeDesignation());
			responce.put("mobileNumber", userMasterDetail.get(0).getMobileNumber());
	 		responce.put("status", "success");
			employeeDetailBO.update(userMasterDetail.get(0));
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
  		responce.put("status", "failed");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 * Employee Logout Service
	 * 
	 */
	
	
	@POST
	@Path("/logout")
	public Response employeeLogout(EFmFmUserMasterPO eFmFmUserMasterPO){					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");			
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmUserMasterPO> userMasterDetail=employeeDetailBO.getParticularEmpDetailsFromUserId(eFmFmUserMasterPO.getUserId(),eFmFmUserMasterPO.geteFmFmClientBranchPO().getBranchId());
		userMasterDetail.get(0).setLoggedIn(false);
		employeeDetailBO.update(userMasterDetail.get(0));
	 	responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 * Employee feed back for driver
	 * 
	 */
	
	@POST
	@Path("/feedback")
	public Response employeeFeedbackToDriver(EFmFmDriverFeedbackPO driverFeedbackPO) throws ParseException{					
		IVendorDetailsBO vendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");			
		EFmFmEmployeeTravelRequestPO employeeTravelRequestPO =new EFmFmEmployeeTravelRequestPO();
		employeeTravelRequestPO.setRequestDate(new Date());
		EFmFmUserMasterPO efmFmUserMaster =new EFmFmUserMasterPO();
		EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();		
		eFmFmClientBranchPO.setBranchId(driverFeedbackPO.getBranchId());		
		efmFmUserMaster.seteFmFmClientBranchPO(eFmFmClientBranchPO);		
		employeeTravelRequestPO.setEfmFmUserMaster(efmFmUserMaster);
		efmFmUserMaster.setUserId(driverFeedbackPO.getEfmFmUserMaster().getUserId());
		Map<String, Object>  responce = new HashMap<String,Object>();
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date tripDate = formatter.parse(driverFeedbackPO.getTime());
		driverFeedbackPO.setTripDate(tripDate);
		driverFeedbackPO.setEfmFmUserMaster(efmFmUserMaster);
		vendorDetailsBO.save(driverFeedbackPO);
	 	responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/forgotpassword")
	public Response emailCheck(EFmFmClientBranchPO clientDetails){					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");			
		Map<String, Object>  responce = new HashMap<String,Object>();
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i = 0; i < 10; i++){
	        numbers.add(i);
	    }
	    Collections.shuffle(numbers);
	    for(int i = 0; i < 4; i++){
	        result += numbers.get(i).toString();
	    }	
		List<EFmFmClientBranchPO> employeeMasterPO=employeeDetailBO.doesClientCodeExist(clientDetails.getBranchCode()); 	
         if(employeeMasterPO.size()>0){
		if(employeeDetailBO.doesEmailIdExist(clientDetails.getEmailId(),employeeMasterPO.get(0).getBranchId())){
			final List<EFmFmUserMasterPO> employeeMaster=employeeDetailBO.getParticularEmployeeDetailsFromEmailId(clientDetails.getEmailId(),employeeMasterPO.get(0).getBranchId());
			Thread thread1 = new Thread(new Runnable() {		
				public void run() {
					// TODO Auto-generated method stub
					SendMailBySite mailSender=new SendMailBySite();
				    mailSender.mail(employeeMaster.get(0).getEmailId(), "Temporary Password "+result);
				}
			});
		    thread1.start();
		    employeeMaster.get(0).setPassword(result);
		    employeeMaster.get(0).setLoggedIn(false);
		    employeeDetailBO.update(employeeMaster.get(0));
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		else{
			responce.put("status", "fail");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
	}
 		responce.put("status", "invalid");
 		return Response.ok(responce, MediaType.APPLICATION_JSON).build();

	}
	@POST
	@Path("/changepassword")
	public Response changePassword(EFmFmUserMasterPO eFmFmUserMasterPO) throws ParseException{					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");			
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmUserMasterPO> employeeDetails=employeeDetailBO.getParticularEmpDetailsFromUserId(eFmFmUserMasterPO.getUserId(),eFmFmUserMasterPO.geteFmFmClientBranchPO().getBranchId());
		if(employeeDetails.get(0).getPassword().equalsIgnoreCase(eFmFmUserMasterPO.getPassword())){
			employeeDetails.get(0).setPassword(eFmFmUserMasterPO.getNewPassword());
			employeeDetailBO.update(employeeDetails.get(0));
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}		
	 		responce.put("status", "fail");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 * Check panic number exist or not.If yes make a call.If not please ask user to update it.
	 */
	
	
	@POST
	@Path("/panic")
	public Response panicNumbers(EFmFmUserMasterPO eFmFmUserMasterPO) throws ParseException{					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");			
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmUserMasterPO> employeeDetails=employeeDetailBO.getParticularEmpDetailsFromUserId(eFmFmUserMasterPO.getUserId(),eFmFmUserMasterPO.geteFmFmClientBranchPO().getBranchId());
		if(employeeDetails.get(0).getPanicNumber()!=null){
			responce.put("panicNumber",employeeDetails.get(0).getPanicNumber());
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}		
	 		responce.put("status", "fail");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 * Here you can update the panic number
	 */
	
	
	@POST
	@Path("/panicnumberupdate")
	public Response panicNumbersUpdate(EFmFmUserMasterPO eFmFmUserMasterPO) throws ParseException{					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");			
		Map<String, Object>  responce = new HashMap<String,Object>();
		List<EFmFmUserMasterPO> employeeDetails=employeeDetailBO.getParticularEmpDetailsFromUserId(eFmFmUserMasterPO.getUserId(),eFmFmUserMasterPO.geteFmFmClientBranchPO().getBranchId());
		if(employeeDetails.get(0).getMobileNumber().equalsIgnoreCase(eFmFmUserMasterPO.getPanicNumber())){
			responce.put("status", "same");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
			employeeDetails.get(0).setPanicNumber(eFmFmUserMasterPO.getPanicNumber());
			employeeDetailBO.update(employeeDetails.get(0));
			responce.put("status", "success");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}
	
}
