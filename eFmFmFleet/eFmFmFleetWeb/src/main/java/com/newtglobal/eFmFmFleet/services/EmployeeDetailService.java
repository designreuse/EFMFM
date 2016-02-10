package com.newtglobal.eFmFmFleet.services;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IEmployeeDetailBO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/employee")
@Consumes("application/json")
@Produces("application/json")
public class EmployeeDetailService {
	
	private static Log log = LogFactory.getLog(EmployeeDetailService.class);	

	@POST
	@Path("/details")
	public Response allEmployeeDetails(EFmFmUserMasterPO eFmFmUserMasterPO){					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");	
		long l1=System.currentTimeMillis();
		log.info("time"+System.currentTimeMillis());
		List<EFmFmUserMasterPO> employeeMasterData=employeeDetailBO.getAllEmployeeDetailsFromClientId(eFmFmUserMasterPO.geteFmFmClientBranchPO().getBranchId());		
		log.info("qwery done");
		List< Map<String,Object>> employeeProfile = new ArrayList< Map<String,Object>>();
		if(employeeMasterData.size()>0){
			for( EFmFmUserMasterPO empProfileDetails:employeeMasterData){
				Map<String,Object> profileValues = new HashMap<String,Object>();
				profileValues.put("employeeId", empProfileDetails.getEmployeeId());
				profileValues.put("userId", empProfileDetails.getUserId());
				profileValues.put("employeeName", empProfileDetails.getFirstName());
				profileValues.put("employeeAddress", empProfileDetails.getAddress());
				profileValues.put("employeeLatiLongi", empProfileDetails.getLatitudeLongitude());		
				profileValues.put("employeeDesignation", empProfileDetails.getEmployeeDesignation());				

				profileValues.put("emailId", empProfileDetails.getEmailId());
				if(empProfileDetails.getGender().equalsIgnoreCase("Male")){
					profileValues.put("employeeGender", 1);	
				}else{
					profileValues.put("employeeGender", 2);	
				}
				
				profileValues.put("mobileNumber", empProfileDetails.getMobileNumber());
				employeeProfile.add(profileValues);
			}
		}
		long l2=(System.currentTimeMillis()-l1);
        log.info("total Excution time"+l2);
		return Response.ok(employeeProfile, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * 
	 * @param Check EmployeeId in eFmFmUserMaster  Exist Or not
	 * @return   employee detail
	 */
	
	@POST
	@Path("/empiddetails")
	public Response employeeDetailsFromEmployeeId(EFmFmUserMasterPO eFmFmUserMasterPO){					
		IEmployeeDetailBO employeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");			
		List<EFmFmUserMasterPO> employeeMasterData=employeeDetailBO.getParticularEmpDetailsFromEmployeeId(eFmFmUserMasterPO.getEmployeeId(),eFmFmUserMasterPO.geteFmFmClientBranchPO().getBranchId());		
		List< Map<String,Object>> employeeProfile = new ArrayList< Map<String,Object>>();
		if(employeeMasterData.size()>0){
			for( EFmFmUserMasterPO empProfileDetails:employeeMasterData){
				Map<String,Object> profileValues = new HashMap<String,Object>();
				profileValues.put("employeeId", empProfileDetails.getEmployeeId());
				profileValues.put("userId", empProfileDetails.getUserId());
				profileValues.put("employeeName", empProfileDetails.getFirstName());
				profileValues.put("employeeAddress", empProfileDetails.getAddress());
				profileValues.put("employeeLatiLongi", empProfileDetails.getLatitudeLongitude());		
				profileValues.put("employeeDesignation", empProfileDetails.getEmployeeDesignation());				

				profileValues.put("emailId", empProfileDetails.getEmailId());
				if(empProfileDetails.getGender().equalsIgnoreCase("Male")){
					profileValues.put("employeeGender", 1);	
				}else{
					profileValues.put("employeeGender", 2);	
				}
				
				profileValues.put("mobileNumber", empProfileDetails.getMobileNumber());
				employeeProfile.add(profileValues);
			}
		}
		return Response.ok(employeeProfile, MediaType.APPLICATION_JSON).build();
	}
	
}
