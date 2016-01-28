package com.newtglobal.eFmFmFleet.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/approval")
@Consumes("application/json")
@Produces("application/json")
public class ApprovalService {

/*
 *  starting driver approval  services for pending,register and unregister drivers
 */
	@POST
	@Path("/unapproveddriver")
	public Response allUnapprovedDrivers(EFmFmDriverMasterPO driverMasterPO){					
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
		List<EFmFmDriverMasterPO> unapprovedDrivers=approvalBO.getAllUnapprovedDrivers(driverMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());	
		List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
		 if(unapprovedDrivers!=null){	
				for (EFmFmDriverMasterPO pending : unapprovedDrivers) {
					Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
					allPendingRequests.put("mobileNumber", pending.getMobileNumber());
					allPendingRequests.put("vendorName", pending.getEfmFmVendorMaster().getVendorName());
					allPendingRequests.put("name", pending.getFirstName());
					allPendingRequests.put("driverId", pending.getDriverId());
					requests.add(allPendingRequests);			
				}
	        }
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/approveddriver")
	public Response allApprovedDrivers(EFmFmDriverMasterPO driverMasterPO){					
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
		List<EFmFmDriverMasterPO> approvedDrivers=approvalBO.getAllApprovedDrivers(driverMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());	
		List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
		 if(approvedDrivers!=null){	
				for (EFmFmDriverMasterPO pending : approvedDrivers) {
					Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
					allPendingRequests.put("mobileNumber", pending.getMobileNumber());
					allPendingRequests.put("vendorName", pending.getEfmFmVendorMaster().getVendorName());
					allPendingRequests.put("name", pending.getFirstName());
					allPendingRequests.put("driverId", pending.getDriverId());
					requests.add(allPendingRequests);			
				}
	        }
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	@POST
	@Path("/inactivedriver")
	public Response allInActiveDrivers(EFmFmDriverMasterPO driverMasterPO){					
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
		List<EFmFmDriverMasterPO> inactiveDrivers=approvalBO.getAllInActiveDrivers(driverMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());
		List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
		 if(inactiveDrivers!=null){	
				for (EFmFmDriverMasterPO pending : inactiveDrivers) {
					Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
					allPendingRequests.put("mobileNumber", pending.getMobileNumber());
					allPendingRequests.put("vendorName", pending.getEfmFmVendorMaster().getVendorName());
					allPendingRequests.put("name", pending.getFirstName());
					allPendingRequests.put("driverId", pending.getDriverId());
					requests.add(allPendingRequests);			
				}
	        }
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/approvedriver")
	public Response approveParticularDriver(EFmFmDriverMasterPO driverMasterPO){					
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
	    EFmFmDriverMasterPO approveDriver=approvalBO.getParticularDriverDetail(driverMasterPO.getDriverId());
	    if(!(approveDriver.getEfmFmVendorMaster().getStatus().equalsIgnoreCase("A"))){
			return Response.ok("notapproved", MediaType.APPLICATION_JSON).build();	
	    }
	    approveDriver.setStatus("A");
	    approvalBO.update(approveDriver);	
		return Response.ok("success", MediaType.APPLICATION_JSON).build();
	}
	
	
	@POST
	@Path("/removedriver")
	public Response rejectParticularDriver(EFmFmDriverMasterPO driverMasterPO){					
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
	    approvalBO.deleteParticularDriver(driverMasterPO.getDriverId());
		return Response.ok("success", MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/rejectdriver")
	public Response removeParticularDriver(EFmFmDriverMasterPO driverMasterPO){					
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
	    EFmFmDriverMasterPO approveDriver=approvalBO.getParticularDriverDetail(driverMasterPO.getDriverId());
	    approveDriver.setStatus("R");
	    approvalBO.update(approveDriver);	
		return Response.ok("success", MediaType.APPLICATION_JSON).build();
	}
	@POST
	@Path("/addagaindriver")
	public Response addAgainParticularDriver(EFmFmDriverMasterPO driverMasterPO){					
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
	    EFmFmDriverMasterPO approveDriver=approvalBO.getParticularDriverDetail(driverMasterPO.getDriverId());
	    approveDriver.setStatus("P");
	    approvalBO.update(approveDriver);	
		return Response.ok("success", MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 *  starting Particular Deriver Detail service for 
	 */
	
	@POST
	@Path("/driverDetail")
	public Response getParticularDriverDetail(EFmFmDriverMasterPO driverMasterPO){					
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");	
		EFmFmDriverMasterPO driverDetails=approvalBO.getParticularDriverDetail(driverMasterPO.getDriverId());	
		List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		List<Map<String, Object>> docs = new ArrayList<Map<String, Object>>();
		Map<String, Object>  docName = new HashMap<String, Object>();	
		Map<String, Object>  alldoc = new HashMap<String, Object>();	
		 if(driverDetails!=null){	
					Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
					allPendingRequests.put("mobileNumber", driverDetails.getMobileNumber());
					allPendingRequests.put("name", driverDetails.getFirstName());
					allPendingRequests.put("vendorName", driverDetails.getEfmFmVendorMaster().getVendorName());
					allPendingRequests.put("dob", formatter.format(driverDetails.getDob()));
					allPendingRequests.put("driverId", driverDetails.getDriverId());
					allPendingRequests.put("address", driverDetails.getAddress());
					alldoc.put("type", "Licence");
					alldoc.put("location", driverDetails.getLicenceDocPath());
					docs.add(alldoc);
					docName.put("type", "MedicalCertficate");
					docName.put("location", driverDetails.getMedicalDocPath());
					docs.add(docName);
					allPendingRequests.put("licenceNum", driverDetails.getLicenceNumber());
					allPendingRequests.put("licenceValid",formatter.format(driverDetails.getLicenceValid()));
					allPendingRequests.put("medicalValid",formatter.format(driverDetails.getLicenceValid()));
					allPendingRequests.put("policeVerification", driverDetails.getPoliceVerification());
					allPendingRequests.put("vendorName", driverDetails.getEfmFmVendorMaster().getVendorName());
					allPendingRequests.put("fatherName", driverDetails.getFatherName());
					allPendingRequests.put("documents", docs);

					requests.add(allPendingRequests);			
	        }
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	/*
	 *  ending driver approval  services for pending,register and unregister drivers
	 */
	
	/*
	 *  starting vehicle approval service for pending,register and unregister vehicle
	 */
		@POST
		@Path("/unapprovedvehicle")
		public Response allUnapprovedVehicles(EFmFmVehicleMasterPO vehicleMasterPO){					
			IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
			List<EFmFmVehicleMasterPO> unapprovedVehicles=approvalBO.getAllUnapprovedVehicles(vehicleMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());	
			List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
			 if(unapprovedVehicles!=null){	
					for (EFmFmVehicleMasterPO pending : unapprovedVehicles) {
						Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
						allPendingRequests.put("vehicleNumber", pending.getVehicleNumber());
						allPendingRequests.put("name", pending.getEfmFmVendorMaster().getVendorName());
						allPendingRequests.put("vehicleId", pending.getVehicleId());
//						log.info("APProval calllled.......................................................");
						requests.add(allPendingRequests);			
					}
		        }
			return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		
		@POST
		@Path("/approvedvehicle")
		public Response allApprovedVehicle(EFmFmVehicleMasterPO vehicleMasterPO){					
			IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
			List<EFmFmVehicleMasterPO> approvedVehicles=approvalBO.getAllApprovedVehicles(vehicleMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());	
			List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
			 if(approvedVehicles!=null){	
					for (EFmFmVehicleMasterPO pending : approvedVehicles) {
						Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
						allPendingRequests.put("vehicleNumber", pending.getVehicleNumber());
						allPendingRequests.put("name", pending.getEfmFmVendorMaster().getVendorName());
						allPendingRequests.put("vehicleId", pending.getVehicleId());
						requests.add(allPendingRequests);			
					}
		        }
			return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		@POST
		@Path("/inactivevehicle")
		public Response allInActiveVehicle(EFmFmVehicleMasterPO vehicleMasterPO){					
			IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
			List<EFmFmVehicleMasterPO> inactiveVehicles=approvalBO.getAllInActiveVehicles(vehicleMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());
			List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
			 if(inactiveVehicles!=null){	
					for (EFmFmVehicleMasterPO pending : inactiveVehicles) {
						Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
						allPendingRequests.put("vehicleNumber", pending.getVehicleNumber());
						allPendingRequests.put("name", pending.getEfmFmVendorMaster().getVendorName());
						allPendingRequests.put("vehicleId", pending.getVehicleId());
						requests.add(allPendingRequests);			
					}
		        }
			return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		
		@POST
		@Path("/approvevehicle")
		public Response approveParticularVehicle(EFmFmVehicleMasterPO vehicleMasterPO){					
			IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
			EFmFmVehicleMasterPO approvevehicle=vehicleCheckInBO.getParticularVehicleDetail(vehicleMasterPO.getVehicleId());
		    if(!(approvevehicle.getEfmFmVendorMaster().getStatus().equalsIgnoreCase("A"))){
				return Response.ok("notapproved", MediaType.APPLICATION_JSON).build();	
		    }
			approvevehicle.setStatus("A");
			vehicleCheckInBO.update(approvevehicle);	
			return Response.ok("success", MediaType.APPLICATION_JSON).build();
		}
		
		
		@POST
		@Path("/rejectvehicle")
		public Response rejectParticularVehicle(EFmFmVehicleMasterPO vehicleMasterPO){					
			IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
			EFmFmVehicleMasterPO rejectvehicle=vehicleCheckInBO.getParticularVehicleDetail(vehicleMasterPO.getVehicleId());
			rejectvehicle.setStatus("R");
			vehicleCheckInBO.update(rejectvehicle);	
			return Response.ok("success", MediaType.APPLICATION_JSON).build();
		}
		@POST
		@Path("/addagainvehicle")
		public Response addAgainParticularVehicle(EFmFmVehicleMasterPO vehicleMasterPO){					
			IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
			EFmFmVehicleMasterPO addAgainvehicle=vehicleCheckInBO.getParticularVehicleDetail(vehicleMasterPO.getVehicleId());
			addAgainvehicle.setStatus("P");
			vehicleCheckInBO.update(addAgainvehicle);	
			return Response.ok("success", MediaType.APPLICATION_JSON).build();
		}
		
		@POST
		@Path("/removevehicle")
		public Response removeParticularVehicle(EFmFmVehicleMasterPO vehicleMasterPO){					
			IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
			approvalBO.deleteParticularVehicle(vehicleMasterPO.getVehicleId());
			return Response.ok("success", MediaType.APPLICATION_JSON).build();
		}
		@POST
		@Path("/vehicleDetail")
		public Response getParticularVehicleDetail(EFmFmVehicleMasterPO vehicleMasterPO){					
			IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
			EFmFmVehicleMasterPO vehicleDetail=vehicleCheckInBO.getParticularVehicleDetail(vehicleMasterPO.getVehicleId());	
			DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
			List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
			Map<String, Object>  alldoc = new HashMap<String, Object>();	
			Map<String, Object>  alldoc1 = new HashMap<String, Object>();	
			Map<String, Object>  alldoc2 = new HashMap<String, Object>();	
			Map<String, Object>  alldoc3 = new HashMap<String, Object>();	
			List<Map<String, Object>> docs = new ArrayList<Map<String, Object>>();	
			 if(vehicleDetail!=null){	
						Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
						allPendingRequests.put("vehicleNumber", vehicleDetail.getVehicleNumber());
						allPendingRequests.put("vehicleOwnerName", vehicleDetail.getEfmFmVendorMaster().getVendorName());
						allPendingRequests.put("vehicleId", vehicleDetail.getVehicleId());
						allPendingRequests.put("vehicleCapacity", vehicleDetail.getCapacity());
						allPendingRequests.put("insuranceValid", formatter.format(vehicleDetail.getInsuranceValidDate()));
						allPendingRequests.put("permitValid", formatter.format(vehicleDetail.getPermitValidDate()));
						alldoc.put("type", "Insurance");
						alldoc.put("location", vehicleDetail.getInsurancePath());
						docs.add(alldoc);
						alldoc1.put("type", "PolutionCertificate");
						alldoc1.put("location", vehicleDetail.getPoluctionCertificatePath());
						docs.add(alldoc1);
						alldoc2.put("type", "RegistrationCertificate");
						alldoc2.put("location", vehicleDetail.getRegistartionCertificatePath());
						docs.add(alldoc2);
						alldoc3.put("type", "TaxCertificate");
						alldoc3.put("location", vehicleDetail.getTaxCertificatePath());
						docs.add(alldoc3);
						allPendingRequests.put("polutionValid", formatter.format(vehicleDetail.getPolutionValid()));
						allPendingRequests.put("taxCertificate", formatter.format(vehicleDetail.getTaxCertificateValid()));
						allPendingRequests.put("vehicleEngineNum", vehicleDetail.getVehicleEngineNumber());
						allPendingRequests.put("vehicleModel", vehicleDetail.getVehicleModel());
						allPendingRequests.put("vendorName", vehicleDetail.getEfmFmVendorMaster().getVendorName());
						allPendingRequests.put("vehicleMake", vehicleDetail.getVehicleMake());
						allPendingRequests.put("vehicleRegistrationNum", vehicleDetail.getRegistartionCertificateNumber());
						allPendingRequests.put("documents", docs);
//						log.info("APProval calllled.......................................................");
						requests.add(allPendingRequests);			
		        }
			return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		/*
		 *  ending vehicle approval  services for pending,register and unregister vendors
		 */
		
		/*
		 *  starting vendor approval  services for pending,register and unregister vendors
		 */
			@POST
			@Path("/unapprovedvendor")
			public Response allUnapprovedVendor(EFmFmVendorMasterPO vendorMasterPO){					
				IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
				List<EFmFmVendorMasterPO> unapprovedDrivers=approvalBO.getAllUnapprovedVendors(vendorMasterPO.geteFmFmClientBranchPO().getBranchId());	
				List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
				 if(unapprovedDrivers!=null){	
						for (EFmFmVendorMasterPO pending : unapprovedDrivers) {
							Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
							allPendingRequests.put("mobileNumber", pending.getVendorMobileNo());
							allPendingRequests.put("name", pending.getVendorName());
							allPendingRequests.put("vendorId", pending.getVendorId());
//							log.info("APProval calllled.......................................................");
							requests.add(allPendingRequests);			
						}
			        }
				return Response.ok(requests, MediaType.APPLICATION_JSON).build();
			}
			
			@POST
			@Path("/approvedvendor")
			public Response allApprovedVendor(EFmFmVendorMasterPO vendorMasterPO){					
				IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
				List<EFmFmVendorMasterPO> approvedVendors=approvalBO.getAllApprovedVendors(vendorMasterPO.geteFmFmClientBranchPO().getBranchId());	
				List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
				 if(approvedVendors!=null){	
						for (EFmFmVendorMasterPO pending : approvedVendors) {
							Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
							allPendingRequests.put("mobileNumber", pending.getVendorMobileNo());
							allPendingRequests.put("name", pending.getVendorName());
							allPendingRequests.put("vendorId", pending.getVendorId());
							requests.add(allPendingRequests);			
						}
			        }
				return Response.ok(requests, MediaType.APPLICATION_JSON).build();
			}
			@POST
			@Path("/inactivevendor")
			public Response allInActiveVendor(EFmFmVendorMasterPO vendorMasterPO){					
				IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
				List<EFmFmVendorMasterPO> inactiveVendors=approvalBO.getAllInActiveVendors(vendorMasterPO.geteFmFmClientBranchPO().getBranchId());
				List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
				 if(inactiveVendors!=null){	
						for (EFmFmVendorMasterPO pending : inactiveVendors) {
							Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
							allPendingRequests.put("mobileNumber", pending.getVendorMobileNo());
							allPendingRequests.put("name", pending.getVendorName());
							allPendingRequests.put("vendorId", pending.getVendorId());
							requests.add(allPendingRequests);			
						}
			        }
				return Response.ok(requests, MediaType.APPLICATION_JSON).build();
			}
			
			
			@POST
			@Path("/vendorDetail")
			public Response getParticularVendorDetail(EFmFmVendorMasterPO vendorMasterPO){					
				IVendorDetailsBO vendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");			
				EFmFmVendorMasterPO vendorDetails=vendorDetailsBO.getParticularVendorDetail(vendorMasterPO.getVendorId());
				List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
				 if(vendorDetails!=null){	
							Map<String, Object>  allPendingRequests = new HashMap<String, Object>();	
							allPendingRequests.put("mobileNumber", vendorDetails.getVendorMobileNo());
							allPendingRequests.put("name", vendorDetails.getVendorName());
							allPendingRequests.put("vendorId", vendorDetails.getVendorId());
							allPendingRequests.put("address", vendorDetails.getAddress());
							allPendingRequests.put("emailId", vendorDetails.getEmailId());
							allPendingRequests.put("tinNum", vendorDetails.getTinNumber());
							requests.add(allPendingRequests);			
						}
				return Response.ok(requests, MediaType.APPLICATION_JSON).build();
			}
			
			@POST
			@Path("/approvevendor")
			public Response approveParticularVendor(EFmFmVendorMasterPO vendorMasterPO){					
				IVendorDetailsBO vendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");			
				EFmFmVendorMasterPO approvevendor=vendorDetailsBO.getParticularVendorDetail(vendorMasterPO.getVendorId());
				approvevendor.setStatus("A");
				vendorDetailsBO.update(approvevendor);	
				return Response.ok("success", MediaType.APPLICATION_JSON).build();
			}
			
			
			@POST
			@Path("/rejectvendor")
			public Response rejectParticularVendor(EFmFmVendorMasterPO vendorMasterPO){					
				IVendorDetailsBO vendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");			
				EFmFmVendorMasterPO rejectvendor=vendorDetailsBO.getParticularVendorDetail(vendorMasterPO.getVendorId());
				rejectvendor.setStatus("R");
				vendorDetailsBO.update(rejectvendor);	
				return Response.ok("success", MediaType.APPLICATION_JSON).build();
			}
			@POST
			@Path("/removevendor")
			public Response removeParticularVendor(EFmFmVendorMasterPO vendorMasterPO){	
				IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
				approvalBO.deleteParticularVendor(vendorMasterPO.getVendorId());
				return Response.ok("success", MediaType.APPLICATION_JSON).build();
			}
			
			@POST
			@Path("/addagainvendor")
			public Response addAgainParticularVendor(EFmFmVendorMasterPO vendorMasterPO){					
				IVendorDetailsBO vendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");			
				EFmFmVendorMasterPO addAgainVendor=vendorDetailsBO.getParticularVendorDetail(vendorMasterPO.getVendorId());
				addAgainVendor.setStatus("P");
				vendorDetailsBO.update(addAgainVendor);	
				return Response.ok("success", MediaType.APPLICATION_JSON).build();
			}
			
}
