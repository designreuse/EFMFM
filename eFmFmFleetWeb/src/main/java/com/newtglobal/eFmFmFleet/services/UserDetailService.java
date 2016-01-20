package com.newtglobal.eFmFmFleet.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IUserMasterBO;
import com.newtglobal.eFmFmFleet.eFmFmFleet.CalculateDistance;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientUserRolePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRoleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/user")
@Consumes("application/json")
@Produces("application/json")
public class UserDetailService {
	/**
	 * 
	 * @param Particular login user details
	 * @return it will returns all the logged in user details
	 * 
	 */
	
		@POST
		@Path("/loginuser")
		public Response loggedInUserDetails(EFmFmUserMasterPO eFmFmUserMaster){					
			IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");	
			Map<String, Object> userDetail = new HashMap<String, Object>();	
			List<EFmFmUserMasterPO> loggedInUserDetail=userMasterBO.getLoggedInUserDetailFromClientIdAndUserId(eFmFmUserMaster);
			List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
			if(!(loggedInUserDetail.isEmpty()) ||  loggedInUserDetail.size()!=0){
			userDetail.put("address", loggedInUserDetail.get(0).getAddress());
			userDetail.put("userName", loggedInUserDetail.get(0).getUserName());
			userDetail.put("firstName", loggedInUserDetail.get(0).getFirstName());
			userDetail.put("lastName", loggedInUserDetail.get(0).getLastName());
			userDetail.put("designation", loggedInUserDetail.get(0).getEmployeeDesignation());
			userDetail.put("number", loggedInUserDetail.get(0).getMobileNumber());
			userDetail.put("interest", loggedInUserDetail.get(0).getEmployeeDomain());
			userDetail.put("address", loggedInUserDetail.get(0).getAddress());
			userDetail.put("country", "India");
			userDetail.put("mobileNumber", loggedInUserDetail.get(0).getMobileNumber());
			userDetail.put("emailId", loggedInUserDetail.get(0).getEmailId());
			userDetail.put("clientName", loggedInUserDetail.get(0).geteFmFmClientBranchPO().getBranchName());
			requests.add(userDetail);
			}
 			return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
	
		
		/**
		 * 
		 * @param Update Particular user pickup and drop address 
		 *  with latitude longitude and distance
		 * 
		 */
		
			@POST
			@Path("/updateuseraddress")
			public Response updateAddressAndLatiLongi(EFmFmUserMasterPO eFmFmUserMaster){					
				IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");	
				Map<String, Object> userDetail = new HashMap<String, Object>();	
				List<EFmFmUserMasterPO> loggedInUserDetail=userMasterBO.getLoggedInUserDetailFromClientIdAndUserId(eFmFmUserMaster);
				userDetail.put("status", "success");
				if(!(loggedInUserDetail.isEmpty()) ||  loggedInUserDetail.size()!=0){
					List<EFmFmClientBranchPO> clientBranchDetails=userMasterBO.getClientDetails(eFmFmUserMaster.geteFmFmClientBranchPO().getBranchId());
					CalculateDistance empDistance=new CalculateDistance();
					try {
						loggedInUserDetail.get(0).setLatitudeLongitude(eFmFmUserMaster.getLatitudeLongitude());
//						loggedInUserDetail.get(0).setAddress(eFmFmUserMaster.getAddress());
						loggedInUserDetail.get(0).setDistance(empDistance.employeeDistanceCalculation(clientBranchDetails.get(0).getLatitudeLongitude(), eFmFmUserMaster.getLatitudeLongitude()));
						userMasterBO.update(loggedInUserDetail.get(0));
					} catch (InvalidKeyException | NoSuchAlgorithmException
							| IOException | URISyntaxException e) {
						userDetail.put("status", "fail");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
	 			return Response.ok(userDetail, MediaType.APPLICATION_JSON).build();
			}
		
		
		/*
		 * Uodate the login User profile details
		 * 
		 * 
		 */
		@POST
		@Path("/updateprofile")
		public Response updateUserProfile(EFmFmUserMasterPO eFmFmUserMaster) throws ParseException{					
			IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");	
			Map<String, Object> request = new HashMap<String, Object>();	
			List<EFmFmUserMasterPO> loggedInUserDetail=userMasterBO.getLoggedInUserDetailFromClientIdAndUserId(eFmFmUserMaster);
			if(!(loggedInUserDetail.isEmpty()) ||  loggedInUserDetail.size()!=0){
			loggedInUserDetail.get(0).setUserName(eFmFmUserMaster.getUserName());
			loggedInUserDetail.get(0).setFirstName(eFmFmUserMaster.getFirstName());
			loggedInUserDetail.get(0).setLastName(eFmFmUserMaster.getLastName());
			loggedInUserDetail.get(0).setMobileNumber(eFmFmUserMaster.getMobileNumber());
			loggedInUserDetail.get(0).setEmailId(eFmFmUserMaster.getEmailId());
			loggedInUserDetail.get(0).setEmployeeDesignation(eFmFmUserMaster.getEmployeeDesignation());
			loggedInUserDetail.get(0).setEmployeeDomain(eFmFmUserMaster.getEmployeeDomain());
			userMasterBO.update(loggedInUserDetail.get(0));
			}
			request.put("status", "success");
 			return Response.ok(request, MediaType.APPLICATION_JSON).build();
		}		
		
		/*
		 * change login user password
		 * 
		 * 
		 */
		@POST
		@Path("/changeuserpassword")
		public Response changeLoggedInUserPassword(EFmFmUserMasterPO eFmFmUserMaster){					
			IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");	
			Map<String, Object> requests = new HashMap<String, Object>();	
			List<EFmFmUserMasterPO> loggedInUserDetail=userMasterBO.getLoggedInUserDetailFromClientIdAndUserId(eFmFmUserMaster);
			if(!(loggedInUserDetail.get(0).getPassword().equalsIgnoreCase(eFmFmUserMaster.getPassword()))){
				requests.put("status", "wrong");
				return Response.ok(requests, MediaType.APPLICATION_JSON).build();
			}			
			loggedInUserDetail.get(0).setPassword(eFmFmUserMaster.getNewPassword());
			userMasterBO.update(loggedInUserDetail.get(0));
			requests.put("status", "success");
 			return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		
		/**
		 * 
		 * @param eFmFmUserMaster get all the users of the particular client
		 * @return all the users and the client type
		 */
		@POST
		@Path("/allusers")
		public Response getAllUsers(EFmFmUserMasterPO eFmFmUserMaster){					
		IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
		List<EFmFmUserMasterPO> clientUserDetails=userMasterBO.getUsersFromClientId(eFmFmUserMaster);
		List<Map<String, Object>> allusers = new ArrayList<Map<String, Object>>();
		Map<String, Object> allUserDetail = new HashMap<String, Object>();  
		if(!(clientUserDetails.isEmpty()) ||  clientUserDetails.size()!=0){			
			for(EFmFmUserMasterPO users:clientUserDetails){
				Map<String, Object> requests = new HashMap<String, Object>();
				List<EFmFmClientUserRolePO> roleMaster = userMasterBO.getUserRolesFromUserIdAndBranchId(users.getUserId(),eFmFmUserMaster.geteFmFmClientBranchPO().getBranchId());
				if(!(roleMaster.isEmpty()) ||  roleMaster.size()!=0){
					requests.put("userRole", roleMaster.get(0).getEfmFmRoleMaster().getRole());
			     }
				requests.put("userId", users.getUserId());
				requests.put("emailId", users.getEmailId());
				requests.put("userName", users.getUserName());
				requests.put("fullName", users.getFirstName());
				requests.put("MobileNum", users.getMobileNumber());
				allusers.add(requests);
			}		
		}
		allUserDetail.put("users", allusers);
		return Response.ok(allUserDetail, MediaType.APPLICATION_JSON).build();
		}	
				
		/**
		 * 
		 * @param eFmFmUserMaster save application setting of the client
		 * @return application setting saved by client
		 */
		
		@POST
		@Path("/updatebranch")
		public Response saveApplicationSetting(EFmFmClientBranchPO clientDetails){					
		IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
		List<EFmFmClientBranchPO> particularBranchDetails=userMasterBO.getClientDetails(clientDetails.getBranchId());
		particularBranchDetails.get(0).setEscortRequired(clientDetails.getEscortRequired());
		particularBranchDetails.get(0).setMangerApprovalRequired(clientDetails.getMangerApprovalRequired());
		Map<String, Object> requests = new HashMap<String, Object>();	
		requests.put("status", "success");
		userMasterBO.update(particularBranchDetails.get(0));
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		
		
		/**
		 * 
		 * @param eFmFmUserMaster delete employee from transport data
		 * @return delete employee from employee details
		 */
		
		@POST
		@Path("/deleteemployee")
		public Response deleteEmployeeFromEmployeeDetails(EFmFmUserMasterPO eFmFmUserMaster){					
		IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		Map<String, Object> requests = new HashMap<String, Object>();
		List<EFmFmUserMasterPO> loggedInUserDetail=userMasterBO.getLoggedInUserDetailFromClientIdAndUserId(eFmFmUserMaster);
		loggedInUserDetail.get(0).setStatus("N");
		userMasterBO.update(loggedInUserDetail.get(0));
		List<EFmFmEmployeeRequestMasterPO> requestDetails=iCabRequestBO.getAllRequestFromRequestMasterFprParticularEmployee(eFmFmUserMaster.getUserId(),eFmFmUserMaster.geteFmFmClientBranchPO().getBranchId());		
		if(!(requestDetails.isEmpty()) ||  requestDetails.size()!=0){
		  for(EFmFmEmployeeRequestMasterPO requestMasterPO:requestDetails){
				requestMasterPO.setStatus("N");
				requestMasterPO.setReadFlg("N");
			    iCabRequestBO.update(requestMasterPO);
			}
		}
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		
		
		
		/**
		 * 
		 * @param eFmFmUserMaster save application setting of the client
		 * @return application setting saved bye client
		 */
		
		@POST
		@Path("/changeuserrole")
		public Response changeUserRole(EFmFmClientUserRolePO eFmFmClientUserRolePO){					
		IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");	
		Map<String, Object> requests = new HashMap<String, Object>();	
		if(eFmFmClientUserRolePO.getEfmFmRoleMaster().getRole().equalsIgnoreCase("manager")){
			EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
			eFmFmClientBranchPO.setBranchId(eFmFmClientUserRolePO.geteFmFmClientBranchPO().getBranchId());
			EFmFmUserMasterPO eFmFmUserMaster=new EFmFmUserMasterPO();
			eFmFmUserMaster.setUserId(eFmFmClientUserRolePO.getEfmFmUserMaster().getUserId());
			eFmFmUserMaster.seteFmFmClientBranchPO(eFmFmClientBranchPO);
			 List<EFmFmUserMasterPO> userDetail=userMasterBO.getLoggedInUserDetailFromClientIdAndUserId(eFmFmUserMaster);
			 List<EFmFmUserMasterPO> allProjectEmployees=userMasterBO.getAllUsersBelogsProject(eFmFmClientUserRolePO.geteFmFmClientBranchPO().getBranchId(), userDetail.get(0).geteFmFmClientProjectDetails().getProjectId());
			 for(EFmFmUserMasterPO userMasterPO:allProjectEmployees){
				 List<EFmFmClientUserRolePO> userClientRoleMaster = userMasterBO.getUserRolesFromUserIdAndBranchId(userMasterPO.getUserId(),eFmFmClientUserRolePO.geteFmFmClientBranchPO().getBranchId());
				 if(userClientRoleMaster.get(0).getEfmFmRoleMaster().getRole().equalsIgnoreCase("manager")){
						requests.put("name",userClientRoleMaster.get(0).getEfmFmUserMaster().getFirstName() );
						requests.put("status","exist");
				 }
				 
             }
     		return Response.ok(requests, MediaType.APPLICATION_JSON).build();		 			 
		}
		List<EFmFmRoleMasterPO> eFmFmRoleMasterPO=userMasterBO.getRoleId(eFmFmClientUserRolePO.getEfmFmRoleMaster().getRole());		
		List<EFmFmClientUserRolePO> userClientRoleMaster = userMasterBO.getUserRolesFromUserIdAndBranchId(eFmFmClientUserRolePO.getEfmFmUserMaster().getUserId(),eFmFmClientUserRolePO.geteFmFmClientBranchPO().getBranchId());
		userClientRoleMaster.get(0).setEfmFmRoleMaster(eFmFmRoleMasterPO.get(0));		
		requests.put("status", "success");
		userMasterBO.update(userClientRoleMaster.get(0));
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}	
		
		
		@POST
		@Path("/changenormaluserrole")
		public Response changeNormalUserRole(EFmFmClientUserRolePO eFmFmClientUserRolePO){					
			IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");	
			Map<String, Object> requests = new HashMap<String, Object>();	
			if(eFmFmClientUserRolePO.getEfmFmRoleMaster().getRole().equalsIgnoreCase("manager")){
				EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
				eFmFmClientBranchPO.setBranchId(eFmFmClientUserRolePO.geteFmFmClientBranchPO().getBranchId());
				EFmFmUserMasterPO eFmFmUserMaster=new EFmFmUserMasterPO();
				eFmFmUserMaster.setUserId(eFmFmClientUserRolePO.getEfmFmUserMaster().getUserId());
				eFmFmUserMaster.seteFmFmClientBranchPO(eFmFmClientBranchPO);
				 List<EFmFmUserMasterPO> userDetail=userMasterBO.getLoggedInUserDetailFromClientIdAndUserId(eFmFmUserMaster);
				 List<EFmFmUserMasterPO> allProjectEmployees=userMasterBO.getAllUsersBelogsProject(eFmFmClientUserRolePO.geteFmFmClientBranchPO().getBranchId(), userDetail.get(0).geteFmFmClientProjectDetails().getProjectId());
				 for(EFmFmUserMasterPO userMasterPO:allProjectEmployees){
					 List<EFmFmClientUserRolePO> userClientRoleMaster = userMasterBO.getUserRolesFromUserIdAndBranchId(userMasterPO.getUserId(),eFmFmClientUserRolePO.geteFmFmClientBranchPO().getBranchId());
					 if(userClientRoleMaster.get(0).getEfmFmRoleMaster().getRole().equalsIgnoreCase("manager")){
							List<EFmFmRoleMasterPO> eFmFmRoleMasterPO=userMasterBO.getRoleId(eFmFmClientUserRolePO.getEfmFmRoleMaster().getRole());		
							eFmFmRoleMasterPO.get(0).setRoleId(4);
							userClientRoleMaster.get(0).setEfmFmRoleMaster(eFmFmRoleMasterPO.get(0));		
							userMasterBO.update(userClientRoleMaster.get(0));							
							
					 }
					 
	             }
			}
			List<EFmFmRoleMasterPO> eFmFmRoleMasterPO=userMasterBO.getRoleId(eFmFmClientUserRolePO.getEfmFmRoleMaster().getRole());		
			List<EFmFmClientUserRolePO> userClientRoleMaster = userMasterBO.getUserRolesFromUserIdAndBranchId(eFmFmClientUserRolePO.getEfmFmUserMaster().getUserId(),eFmFmClientUserRolePO.geteFmFmClientBranchPO().getBranchId());
			userClientRoleMaster.get(0).setEfmFmRoleMaster(eFmFmRoleMasterPO.get(0));		
			requests.put("status", "success");
			userMasterBO.update(userClientRoleMaster.get(0));
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}	
		
		
		/**
		 * 
		 * @param  users branch Details details
		 * @return it will returns branch  details
		 * 
		 */
		
		@POST
		@Path("/branchdetail")
		public Response getBranchDetails(EFmFmClientBranchPO clientDetails){					
		IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
		List<EFmFmClientBranchPO> particularBranchDetails=userMasterBO.getClientDetails(clientDetails.getBranchId());
		Map<String, Object> requests = new HashMap<String, Object>();
		requests.put("escortRequired", particularBranchDetails.get(0).getEscortRequired());
		requests.put("managerApproval",particularBranchDetails.get(0).getMangerApprovalRequired());	
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		
		/**
		 * 
		 * @param  get all Request Master Details On the client basis
		 * @return it will returns all the request details
		 * 
		 */
		
		@POST
		@Path("/allrequestdetail")
		public Response getAllRequestDetails(EFmFmEmployeeRequestMasterPO travelRequest) throws ParseException{	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		List<EFmFmEmployeeRequestMasterPO> allRequestDetails=iCabRequestBO.getAllRequestDetailsFromRequestMasterFromBranchId(travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());		
		List<Map<String, Object>> allRequestsDetails = new ArrayList<Map<String, Object>>();
		for(EFmFmEmployeeRequestMasterPO requestMasterPO:allRequestDetails){
			Map<String, Object> requests = new HashMap<String, Object>();
			requests.put("userId", requestMasterPO.getEfmFmUserMaster().getUserId());
			requests.put("tripId", requestMasterPO.getTripId());
			requests.put("employeeId", requestMasterPO.getEfmFmUserMaster().getEmployeeId());
			if(requestMasterPO.getTripType().equalsIgnoreCase("PICKUP"))
			requests.put("pickupTime", requestMasterPO.getPickUpTime());
			else requests.put("pickupTime", "Not Required");
			requests.put("requestType", requestMasterPO.getRequestType());
			requests.put("status", requestMasterPO.getStatus());
			requests.put("address", requestMasterPO.getEfmFmUserMaster().getAddress());
			requests.put("routeName", requestMasterPO.getEfmFmUserMaster().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
			requests.put("areaName", requestMasterPO.getEfmFmUserMaster().geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaName());
			requests.put("shiftTime", requestMasterPO.getShiftTime());
			allRequestsDetails.add(requests);
		}
		return Response.ok(allRequestsDetails, MediaType.APPLICATION_JSON).build();
		}
		
		
		
		
		
		/**
		 * 
		 * @param  get all Request Master Details On the client basis
		 * @return it will returns all the request details According to the trip type
		 * 
		 */
		
		@POST
		@Path("/allrequestdetailtype")
		public Response getAllRequestDetailsByTripType(EFmFmEmployeeRequestMasterPO travelRequest) throws ParseException{	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		List<EFmFmEmployeeRequestMasterPO> allRequestDetails=iCabRequestBO.getAllRequestDetailsFromRequestMasterFromBranchIdByTripType(travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),travelRequest.getTripType());		
		List<Map<String, Object>> allRequestsDetails = new ArrayList<Map<String, Object>>();
		for(EFmFmEmployeeRequestMasterPO requestMasterPO:allRequestDetails){
			Map<String, Object> requests = new HashMap<String, Object>();
			requests.put("userId", requestMasterPO.getEfmFmUserMaster().getUserId());
			requests.put("tripId", requestMasterPO.getTripId());
			requests.put("employeeId", requestMasterPO.getEfmFmUserMaster().getEmployeeId());
			if(requestMasterPO.getTripType().equalsIgnoreCase("PICKUP"))
			requests.put("pickupTime", requestMasterPO.getPickUpTime());
			else requests.put("pickupTime", "Not Required");
			requests.put("requestType", requestMasterPO.getRequestType());
			requests.put("status", requestMasterPO.getStatus());
			requests.put("address", requestMasterPO.getEfmFmUserMaster().getAddress());
			requests.put("routeName", requestMasterPO.getEfmFmUserMaster().geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
			requests.put("areaName", requestMasterPO.getEfmFmUserMaster().geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaName());
			requests.put("shiftTime", requestMasterPO.getShiftTime());
			allRequestsDetails.add(requests);
		}
		return Response.ok(allRequestsDetails, MediaType.APPLICATION_JSON).build();
		}
		
		
		/**
		 * 
		 * @param  get all Request Master Details On the client basis
		 * @return it will returns all the request details
		 * 
		 */
		
		@POST
		@Path("/updaterequestdetail")
		public Response updateRequestMasterDetail(EFmFmEmployeeRequestMasterPO travelRequest) throws ParseException{	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		Map<String, Object> requests = new HashMap<String, Object>();
		List<EFmFmEmployeeRequestMasterPO> requestDetails=iCabRequestBO.getRequestFromRequestMaster(travelRequest.getTripId(),travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		requestDetails.get(0).setStatus(travelRequest.getStatus());
		requestDetails.get(0).setReadFlg(travelRequest.getReadFlg());
		iCabRequestBO.update(requestDetails.get(0));
		if(travelRequest.getStatus().equalsIgnoreCase("N")){
		  List<EFmFmEmployeeTravelRequestPO> currentRequestDetails=iCabRequestBO.deleteCurentRequestfromTraveldesk(travelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), travelRequest.getTripId());
			if(!(currentRequestDetails.isEmpty()) ||  currentRequestDetails.size()!=0){	
				for(EFmFmEmployeeTravelRequestPO employeeRequest:currentRequestDetails){
				iCabRequestBO.deleteParticularRequest(employeeRequest.getRequestId());
				}
			}
		}
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		
		
		
		
		/**
		 * 
		 * @param update eFmFmUserMaster  employee details
		 * @return update employee from employee details
		 */
		
		@POST
		@Path("/updateempdetails")
		public Response updateEmployeeDetails(EFmFmUserMasterPO eFmFmUserMaster){					
		IUserMasterBO userMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
		Map<String, Object> requests = new HashMap<String, Object>();
		List<EFmFmUserMasterPO> loggedInUserDetail=userMasterBO.getEmployeeUserDetailFromEmployeeId(eFmFmUserMaster.geteFmFmClientBranchPO().getBranchId(),eFmFmUserMaster.getEmployeeId());
		if(!(loggedInUserDetail.isEmpty()) ||  loggedInUserDetail.size()!=0){			
		loggedInUserDetail.get(0).setMobileNumber(eFmFmUserMaster.getMobileNumber());
		loggedInUserDetail.get(0).setFirstName(eFmFmUserMaster.getFirstName());
		loggedInUserDetail.get(0).setAddress(eFmFmUserMaster.getAddress());
		loggedInUserDetail.get(0).setEmployeeDesignation(eFmFmUserMaster.getEmployeeDesignation());
		loggedInUserDetail.get(0).setEmailId(eFmFmUserMaster.getEmailId());
		userMasterBO.update(loggedInUserDetail.get(0));
		}
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		
		
}
