package com.newtglobal.eFmFmFleet.services;


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

import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;


@Component
@Path("/vendor")
@Consumes("application/json")
@Produces("application/json")
public class VendorService {	
	
	/**
	* The vendorByVehicleDetails method implemented.
	* for getting the list of vendor details based on the vehicles.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-18 
	* 
	* @return vendor details.
	*/	
	@POST
	@Path("/vendorByVehicleDetails")
	public Response vendorByVehicleDetails(EFmFmVendorMasterPO efmFmVendorMaster){	
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");		
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");						
		List<Map<String, Object>> vendorByVehicle= new ArrayList<Map<String, Object>>();
		List<EFmFmVendorMasterPO> listOfvendor=iVendorDetailsBO.getAllVendorsDetails(efmFmVendorMaster);
		if((!(listOfvendor.isEmpty())) || listOfvendor.size() !=0){			
			for(EFmFmVendorMasterPO vendorList:listOfvendor){				
				Map<String, Object>  vendorDetails= new HashMap<String, Object>();
				vendorDetails.put("vendorId",vendorList.getVendorId());
				vendorDetails.put("vendorName",vendorList.getVendorName());
				vendorDetails.put("vendorContactNumber",vendorList.getVendorOfficeNo());
				vendorDetails.put("vendorContactName",vendorList.getVendorContactName());
				vendorDetails.put("vendorMobileNumber",vendorList.getVendorMobileNo());
				vendorDetails.put("vendorAddress",vendorList.getAddress());
				vendorDetails.put("emailId",vendorList.getEmailId());
				vendorDetails.put("vendorContactName1",vendorList.getVendorContactName1());
				vendorDetails.put("vendorContactName2",vendorList.getVendorContactName2());
				vendorDetails.put("vendorContactName3",vendorList.getVendorContactName3());
				vendorDetails.put("vendorContactName4",vendorList.getVendorContactName4());

				vendorDetails.put("vendorContactNumber1",vendorList.getVendorContactNumber1());
				vendorDetails.put("vendorContactNumber2",vendorList.getVendorContactNumber2());
				vendorDetails.put("vendorContactNumber3",vendorList.getVendorContactNumber3());
				vendorDetails.put("vendorContactNumber4",vendorList.getVendorContactNumber4());


				EFmFmVehicleMasterPO eFmFmVehicleMasterPO=new EFmFmVehicleMasterPO();
				efmFmVendorMaster.setVendorId(vendorList.getVendorId());
				eFmFmVehicleMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
				List<EFmFmVehicleMasterPO> listOfVehicle=iVehicleCheckInBO.getAllVehicleDetails(eFmFmVehicleMasterPO);
				EFmFmDriverMasterPO eFmFmDriverMasterPO=new EFmFmDriverMasterPO();
				eFmFmDriverMasterPO.setEfmFmVendorMaster(efmFmVendorMaster);
				List<EFmFmDriverMasterPO> listOfDriver=iVehicleCheckInBO.getAllDriverDetails(eFmFmDriverMasterPO);
				vendorDetails.put("noOfVehicle",listOfVehicle.size());	
				vendorDetails.put("noOfDriver",listOfDriver.size());				

				vendorByVehicle.add(vendorDetails);
				}
			}			
		return Response.ok(vendorByVehicle, MediaType.APPLICATION_JSON).build();
	}	
	/**
	* The listOfVehiclebyVendor method implemented.
	* for getting the list of vehicle details based on the vendor.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-19 
	* 
	* @return vehicle details.
	*/	
	@POST
	@Path("/listOfVehiclebyVendor")
	public Response listOfVehiclebyVendor(EFmFmVehicleMasterPO eFmFmVehicleMasterPO){				
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");						
		List<Map<String, Object>> vendorByVehicle= new ArrayList<Map<String, Object>>();    
		List<EFmFmVehicleMasterPO> listOfVehicle=iVehicleCheckInBO.getAllVehicleDetails(eFmFmVehicleMasterPO);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
		if((!(listOfVehicle.isEmpty())) || listOfVehicle.size() !=0){			
			for(EFmFmVehicleMasterPO vehicleDetails:listOfVehicle){					
				Map<String, Object>  vehicleList= new HashMap<String, Object>();				
				vehicleList.put("vehicleId",vehicleDetails.getVehicleId());				
				vehicleList.put("vehicleNumber",vehicleDetails.getVehicleNumber());				
				vehicleList.put("vehicleOwnerName",vehicleDetails.getEfmFmVendorMaster().getVendorName());
				vehicleList.put("vehicleEngineNumber",vehicleDetails.getVehicleEngineNumber());				
				vehicleList.put("capacity",vehicleDetails.getCapacity());			
				vehicleList.put("rcNumber",vehicleDetails.getRegistartionCertificateNumber());				
				vehicleList.put("vehicleMake",vehicleDetails.getVehicleMake());
				vehicleList.put("vehicleModel",vehicleDetails.getVehicleModel());
				vehicleList.put("vehicleModelYear",vehicleDetails.getVehicleModelYear());				
				vehicleList.put("contractType",vehicleDetails.geteFmFmVendorContractTypeMaster().getContractType());
				vehicleList.put("conTariffId",vehicleDetails.geteFmFmVendorContractTypeMaster().getContractTypeId());
				vehicleList.put("contractTariffId",vehicleDetails.getContractDetailId());
				vehicleList.put("taxCertificateValid",dateFormat.format(vehicleDetails.getTaxCertificateValid()));
				vehicleList.put("polutionValid",dateFormat.format(vehicleDetails.getPolutionValid()));			
				vehicleList.put("InsuranceDate",dateFormat.format(vehicleDetails.getInsuranceValidDate()));
				vehicleList.put("registrationDate",dateFormat.format(vehicleDetails.getRegistrationDate()));
				vehicleList.put("vehicleFitnessDate",dateFormat.format(vehicleDetails.getVehicleFitnessDate()));
				vehicleList.put("PermitValid",dateFormat.format(vehicleDetails.getPermitValidDate()));
				vendorByVehicle.add(vehicleList);
				}
			}			
		return Response.ok(vendorByVehicle, MediaType.APPLICATION_JSON).build();
	}
	/**
	* The listOfdriverbyVendor method implemented.
	* for getting the list of Driver details based on the vendor.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-19 
	* 
	* @return driver details.
	*/	
	@POST
	@Path("/listOfDriverByVendor")
	public Response listOfdriverbyVendor(EFmFmDriverMasterPO eFmFmDriverMasterPO){				
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");						
		List<Map<String, Object>> vendorByDrivers= new ArrayList<Map<String, Object>>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		List<EFmFmDriverMasterPO> listOfDrivers=iVehicleCheckInBO.getAllDriverDetails(eFmFmDriverMasterPO);	
		if((!(listOfDrivers.isEmpty())) || listOfDrivers.size() !=0){			
			for(EFmFmDriverMasterPO driverDetails:listOfDrivers){					
				Map<String, Object>  driverList= new HashMap<String, Object>();				
				driverList.put("driverId",driverDetails.getDriverId());
				driverList.put("driverName",driverDetails.getFirstName());
				driverList.put("driverAddress",driverDetails.getAddress());				
				driverList.put("mobileNumber",driverDetails.getMobileNumber());
				driverList.put("dateOfBirth",dateFormat.format(driverDetails.getDob()));
				driverList.put("licenceNumber",driverDetails.getLicenceNumber());
				driverList.put("licenceValid",dateFormat.format(driverDetails.getLicenceValid()));
				driverList.put("medicalCertificateValid",dateFormat.format(driverDetails.getMedicalFitnessCertificateValid()));			
				driverList.put("batchDate",dateFormat.format(driverDetails.getBatchDate()));
				driverList.put("driverBatchNum",driverDetails.getBatchNumber());
				driverList.put("ddtExpiryDate",dateFormat.format(driverDetails.getDdtValidDate()));
				driverList.put("policeExpiryDate",dateFormat.format(driverDetails.getPoliceVerificationValid()));
				driverList.put("driverJoining",dateFormat.format(driverDetails.getDateOfJoining()));
				vendorByDrivers.add(driverList);
				}
			}			
		return Response.ok(vendorByDrivers, MediaType.APPLICATION_JSON).build();
	}
	/**
	* The addingVendorDetails method implemented.
	* for Adding vendor details.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-19 
	* 
	* @return added status.
	*/	
	@POST
	@Path("/addingVendorDetails")
	public Response addingVendorDetails(EFmFmVendorMasterPO eFmFmVendorMasterPO){
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");
		//EFmFmVendorMasterPO eFmFmVendorMasterPO=new EFmFmVendorMasterPO();		
	/*	EFmFmClientMasterPO eFmFmClientMasterPO=new EFmFmClientMasterPO();
		eFmFmClientMasterPO.setClientId(1);
		eFmFmVendorMasterPO.setEfmFmClientMaster(eFmFmClientMasterPO);
		eFmFmVendorMasterPO.setAddress("Navallur");
		eFmFmVendorMasterPO.setContractStartDate(new Date());
		eFmFmVendorMasterPO.setContractEndDate(new Date());		
		eFmFmVendorMasterPO.setVendorContactName("Suresh");
		eFmFmVendorMasterPO.setVendorContactNo("98657565776657");
		eFmFmVendorMasterPO.setVendorMobileNo("9349348594385");
		eFmFmVendorMasterPO.setVendorName("NTL");
		eFmFmVendorMasterPO.setNoOfDays(9);
		eFmFmVendorMasterPO.setEmailId("gmail.com");
		eFmFmVendorMasterPO.setStatus("Y");
		eFmFmVendorMasterPO.setTinNumber("23423412323");*/		
		iVendorDetailsBO.save(eFmFmVendorMasterPO);				
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	/**
	* The modifyVendorDetails method implemented.
	* for Modifying vendor details.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-19 
	* 
	* @return Modified Status.
	*/	
	@POST
	@Path("/modifyVendorDetails")
	public Response modifyVendorDetails(EFmFmVendorMasterPO eFmFmVendorMasterPO){
		
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");	
		EFmFmVendorMasterPO vendorDetails=iVendorDetailsBO.getParticularVendorDetail(eFmFmVendorMasterPO.getVendorId());		
		vendorDetails.setAddress(eFmFmVendorMasterPO.getAddress());
		vendorDetails.setVendorContactName(eFmFmVendorMasterPO.getVendorContactName());
		vendorDetails.setVendorOfficeNo(eFmFmVendorMasterPO.getVendorOfficeNo());
		vendorDetails.setVendorMobileNo(eFmFmVendorMasterPO.getVendorMobileNo());
		vendorDetails.setVendorName(eFmFmVendorMasterPO.getVendorName());
		//vendorDetails.setNoOfDays(9);
		vendorDetails.setEmailId(eFmFmVendorMasterPO.getEmailId());
		//vendorDetails.setStatus("Y");
		//vendorDetails.setTinNumber("23423412323");	
		iVendorDetailsBO.update(vendorDetails);				
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	
	/**
	* The removeVendorDetails method implemented.
	* for Modifying vendor details.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-19 
	* 
	* @return removed Status.
	*/	
	@POST
	@Path("/removeVendorDetails")
	public Response removeVendorDetails(EFmFmVendorMasterPO eFmFmVendorMasterPO){
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");	
		EFmFmVendorMasterPO vendorDetails=iVendorDetailsBO.getParticularVendorDetail(eFmFmVendorMasterPO.getVendorId());
		vendorDetails.setStatus("D");
		iVendorDetailsBO.update(vendorDetails);				
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	
	/**
	* The removeDriverDetails method implemented.
	* for Modifying driver details.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-29 
	* 
	* @return removed Status.
	*/	
	@POST
	@Path("/removeDriverDetails")
	public Response removeDriverDetails(EFmFmDriverMasterPO eFmFmDriverMasterPO){
		IApprovalBO iApprovalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");	
	    EFmFmDriverMasterPO driverMasterPO=	iApprovalBO.getParticularDriverDetail(eFmFmDriverMasterPO.getDriverId());
	    driverMasterPO.setStatus("D");		
	    iApprovalBO.update(driverMasterPO);					
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	
	/**
	* The modifyDriverDetails method implemented.
	* for Modifying driver details.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-29 
	* 
	* @return modified Status.
	 * @throws ParseException 
	*/	
	@POST
	@Path("/modifyDriverDetails")
	public Response modifyDriverDetails(EFmFmDriverMasterPO eFmFmDriverMasterPO) throws ParseException{
		IApprovalBO iApprovalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");	
	    EFmFmDriverMasterPO driverMasterPO=	iApprovalBO.getParticularDriverDetail(eFmFmDriverMasterPO.getDriverId());
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		Date licenceValidDate = (Date) formatter.parse(eFmFmDriverMasterPO.getLicenceValidDate());		
		Date ddtValidDate = (Date) formatter.parse(eFmFmDriverMasterPO.getDdtExpiryDate());
		Date dobValidDate = (Date) formatter.parse(eFmFmDriverMasterPO.getDobDate());
		Date batchValidDate = (Date) formatter.parse(eFmFmDriverMasterPO.getDriverBatchDate());
		Date joiningValidDate = (Date) formatter.parse(eFmFmDriverMasterPO.getDriverJoiningDate());
		Date medicalValidDate = (Date) formatter.parse(eFmFmDriverMasterPO.getDriverMedicalExpiryDate());
		Date policeValidDate = (Date) formatter.parse(eFmFmDriverMasterPO.getDriverPoliceVerificationDate());
		driverMasterPO.setDob(dobValidDate);
		
		driverMasterPO.setBatchNumber(eFmFmDriverMasterPO.getBatchNumber());
		driverMasterPO.setMedicalFitnessCertificateValid(medicalValidDate);		
	    driverMasterPO.setLicenceValid(licenceValidDate);
	    driverMasterPO.setDdtValidDate(ddtValidDate);	    
	    driverMasterPO.setBatchDate(batchValidDate);
	    driverMasterPO.setPoliceVerificationValid(policeValidDate);
	    driverMasterPO.setDateOfJoining(joiningValidDate);
	    driverMasterPO.setFirstName(eFmFmDriverMasterPO.getFirstName());
	    driverMasterPO.setMobileNumber(eFmFmDriverMasterPO.getMobileNumber());
	    driverMasterPO.setLicenceNumber(eFmFmDriverMasterPO.getLicenceNumber());
	    driverMasterPO.setAddress(eFmFmDriverMasterPO.getAddress());
	    iApprovalBO.update(driverMasterPO);					
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
}