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
import com.newtglobal.eFmFmFleet.business.bo.IAssignRouteBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorContractTypeMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/vehicle")
@Consumes("application/json")
@Produces("application/json")
public class VehicleService {

	/**
	 * The vehicleDetails method implemented for getting the list of vehicle
	 * details and driver will allocate dynamically.
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-18
	 * 
	 * @return vehicle details.
	 */

	@POST
	@Path("/AllVehicleDetails")
	public Response vehicleDetails(EFmFmVendorMasterPO eFmFmVendorMasterPO) {
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader
				.getContext().getBean("IVendorDetailsBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		List<Map<String, Object>> vehicleCheckIn = new ArrayList<Map<String, Object>>();
		int deviceCheckIn = 0;
		List<EFmFmVehicleCheckInPO> checkInDriver = iVehicleCheckInBO
				.getLastCheckInEntitiesDetails(eFmFmVendorMasterPO
						.geteFmFmClientBranchPO().getBranchId());
		for (EFmFmVehicleCheckInPO listOfCheckIn : checkInDriver) {
			Map<String, Object> checkInDetails = new HashMap<String, Object>();
			if (listOfCheckIn.geteFmFmDeviceMaster().getIsActive()
					.equalsIgnoreCase("Y")
					&& listOfCheckIn.getEfmFmVehicleMaster().getStatus()
							.equalsIgnoreCase("A")
					&& listOfCheckIn.getEfmFmDriverMaster().getStatus()
							.equalsIgnoreCase("A")
					&& listOfCheckIn.getStatus().equalsIgnoreCase("N")) {
				checkInDetails.put("vehicleId", listOfCheckIn
						.getEfmFmVehicleMaster().getVehicleId());
				checkInDetails.put("vendorId", listOfCheckIn
						.getEfmFmVehicleMaster().getEfmFmVendorMaster()
						.getVendorId());
				checkInDetails.put("vehicleMake", listOfCheckIn
						.getEfmFmVehicleMaster().getVehicleMake());
				checkInDetails.put("capacity", listOfCheckIn
						.getEfmFmVehicleMaster().getCapacity());
				checkInDetails.put("vehicleNumber", listOfCheckIn
						.getEfmFmVehicleMaster().getVehicleNumber());
				List<EFmFmVehicleCheckInPO> checkInVehicleDetail = iVehicleCheckInBO
						.getParticularCheckedInVehicles(eFmFmVendorMasterPO
								.geteFmFmClientBranchPO().getBranchId(),
								listOfCheckIn.getEfmFmVehicleMaster()
										.getVehicleId());
				List<EFmFmVehicleCheckInPO> checkInDriverDetail = iVehicleCheckInBO
						.getParticularCheckedInDriverDetails(
								eFmFmVendorMasterPO.geteFmFmClientBranchPO()
										.getBranchId(), listOfCheckIn
										.getEfmFmDriverMaster().getDriverId());
				List<EFmFmVehicleCheckInPO> checkInDeviceDetail = iVehicleCheckInBO
						.getParticularCheckedInDeviceDetails(
								eFmFmVendorMasterPO.geteFmFmClientBranchPO()
										.getBranchId(), listOfCheckIn
										.geteFmFmDeviceMaster().getDeviceId());
				if (((checkInDeviceDetail.isEmpty() || checkInDeviceDetail
						.size() == 0))
						&& ((checkInVehicleDetail.isEmpty() || checkInVehicleDetail
								.size() == 0))
						&& ((checkInDriverDetail.isEmpty() || checkInDriverDetail
								.size() == 0))) {
					checkInDetails.put("DriverName", listOfCheckIn
							.getEfmFmDriverMaster().getFirstName());
					checkInDetails.put("MobileNumber", listOfCheckIn
							.getEfmFmDriverMaster().getMobileNumber());
					checkInDetails.put("driverId", listOfCheckIn
							.getEfmFmDriverMaster().getDriverId());
					checkInDetails.put("deviceId", listOfCheckIn
							.geteFmFmDeviceMaster().getDeviceId());
					checkInDetails.put("deviceNumber", listOfCheckIn
							.geteFmFmDeviceMaster().getMobileNumber());
					vehicleCheckIn.add(checkInDetails);
				}
			}
		}
		List<EFmFmDeviceMasterPO> allDevice = iVendorDetailsBO
				.getAllActiveDeviceDetails(eFmFmVendorMasterPO
						.geteFmFmClientBranchPO().getBranchId());
		List<EFmFmVendorMasterPO> allVendor = iVendorDetailsBO
				.getAllVendorsDetails(eFmFmVendorMasterPO);
		if ((!(allVendor.isEmpty())) || allVendor.size() != 0) {
			for (EFmFmVendorMasterPO vendorList : allVendor) {
				List<EFmFmVehicleMasterPO> allVehicleByVendor = iVehicleCheckInBO
						.getAllActiveVehicleDetails(vendorList.getVendorId());
				List<EFmFmDriverMasterPO> allDriverByVendor = iVehicleCheckInBO
						.getAllActiveDriverDetails(vendorList.getVendorId());
				int driverCount = 0;
				if ((!(allVehicleByVendor.isEmpty()))
						|| allVehicleByVendor.size() != 0) {
					for (EFmFmVehicleMasterPO allVehicleListList : allVehicleByVendor) {
						Map<String, Object> checkInDetails = new HashMap<String, Object>();
						checkInDetails.put("vehicleId",
								allVehicleListList.getVehicleId());
						checkInDetails.put("vehicleNumber",
								allVehicleListList.getVehicleNumber());
						checkInDetails.put("vehicleMake",
								allVehicleListList.getVehicleMake());
						checkInDetails.put("capacity",
								allVehicleListList.getCapacity());
						if ((allDriverByVendor.size() - 1) >= driverCount) {
							checkInDetails.put("DriverName", allDriverByVendor
									.get(driverCount).getFirstName());
							checkInDetails.put("driverId", allDriverByVendor
									.get(driverCount).getDriverId());
							checkInDetails.put("vendorId", allDriverByVendor
									.get(driverCount).getEfmFmVendorMaster()
									.getVendorId());
							checkInDetails.put("MobileNumber",
									allDriverByVendor.get(driverCount)
											.getMobileNumber());
							if ((allDevice.size() - 1) >= deviceCheckIn) {
								checkInDetails.put("deviceId",
										allDevice.get(deviceCheckIn)
												.getDeviceId());
								checkInDetails.put("deviceNumber", allDevice
										.get(deviceCheckIn).getMobileNumber());
								deviceCheckIn++;
								List<EFmFmVehicleCheckInPO> checkInVehicleDetail = iVehicleCheckInBO
										.getParticularCheckedInVehicles(
												eFmFmVendorMasterPO
														.geteFmFmClientBranchPO()
														.getBranchId(),
												allVehicleListList
														.getVehicleId());
								List<EFmFmVehicleCheckInPO> checkInDriverDetail = iVehicleCheckInBO
										.getParticularCheckedInDriverDetails(
												eFmFmVendorMasterPO
														.geteFmFmClientBranchPO()
														.getBranchId(),
												allDriverByVendor.get(
														driverCount)
														.getDriverId());
								List<EFmFmVehicleCheckInPO> checkInDeviceDetail = iVehicleCheckInBO
										.getParticularCheckedInDeviceDetails(
												eFmFmVendorMasterPO
														.geteFmFmClientBranchPO()
														.getBranchId(),
												allDevice.get(deviceCheckIn)
														.getDeviceId());
								if (((checkInDeviceDetail.isEmpty() || checkInDeviceDetail
										.size() == 0))
										&& ((checkInVehicleDetail.isEmpty() || checkInVehicleDetail
												.size() == 0))
										&& ((checkInDriverDetail.isEmpty() || checkInDriverDetail
												.size() == 0))) {
									vehicleCheckIn.add(checkInDetails);
								}
							}
							driverCount++;
						}

					}

				}
			}
		}

		return Response.ok(vehicleCheckIn, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * The driverCheckIn method implemented. for creating check in details for
	 * vehicle and drivers.
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-20
	 * 
	 * @return driver checkIn details.
	 */
	@POST
	@Path("/driverCheckIn")
	public Response driverCheckIn(EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		String response = "alreadyExist";
		List<EFmFmVehicleCheckInPO> alreadyCheckInVehicleDetail = iVehicleCheckInBO
				.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);
		EFmFmDriverMasterPO efmFmDriverMaster = new EFmFmDriverMasterPO();
		EFmFmVehicleMasterPO eFmFmVehicleMaster = new EFmFmVehicleMasterPO();
		EFmFmDeviceMasterPO eFmFmDeviceMasterPO = new EFmFmDeviceMasterPO();
		efmFmDriverMaster.setDriverId(eFmFmVehicleCheckInPO
				.getEfmFmDriverMaster().getDriverId());
		eFmFmVehicleMaster.setVehicleId(eFmFmVehicleCheckInPO
				.getEfmFmVehicleMaster().getVehicleId());
		eFmFmDeviceMasterPO.setDeviceId(eFmFmVehicleCheckInPO
				.geteFmFmDeviceMaster().getDeviceId());

		List<EFmFmVehicleCheckInPO> checkInVehicleDetail = iVehicleCheckInBO
				.getParticularCheckedInVehicles(eFmFmVehicleCheckInPO
						.getBranchId(), eFmFmVehicleCheckInPO
						.getEfmFmVehicleMaster().getVehicleId());
		if ((!(checkInVehicleDetail.isEmpty()))
				|| checkInVehicleDetail.size() != 0) {
			EFmFmVehicleMasterPO alreadyCheckInVehicle = new EFmFmVehicleMasterPO();
			alreadyCheckInVehicle.setVehicleId(alreadyCheckInVehicleDetail
					.get(0).getEfmFmVehicleMaster().getVehicleId());
			checkInVehicleDetail.get(0).setEfmFmVehicleMaster(
					alreadyCheckInVehicle);
			iVehicleCheckInBO.update(checkInVehicleDetail.get(0));
		}

		List<EFmFmVehicleCheckInPO> checkInDriverDetail = iVehicleCheckInBO
				.getParticularCheckedInDriverDetails(eFmFmVehicleCheckInPO
						.getBranchId(), eFmFmVehicleCheckInPO
						.getEfmFmDriverMaster().getDriverId());
		if ((!(checkInDriverDetail.isEmpty()))
				|| checkInDriverDetail.size() != 0) {
			EFmFmDriverMasterPO alreadyCheckInDriver = new EFmFmDriverMasterPO();
			alreadyCheckInDriver.setDriverId(alreadyCheckInVehicleDetail.get(0)
					.getEfmFmDriverMaster().getDriverId());
			checkInDriverDetail.get(0).setEfmFmDriverMaster(
					alreadyCheckInDriver);
			iVehicleCheckInBO.update(checkInDriverDetail.get(0));
		}

		List<EFmFmVehicleCheckInPO> checkInDeviceDetail = iVehicleCheckInBO
				.getParticularCheckedInDeviceDetails(eFmFmVehicleCheckInPO
						.getBranchId(), eFmFmVehicleCheckInPO
						.geteFmFmDeviceMaster().getDeviceId());
		if ((!(checkInDeviceDetail.isEmpty()))
				|| checkInDeviceDetail.size() != 0) {
			EFmFmDeviceMasterPO alreadyCheckInDevice = new EFmFmDeviceMasterPO();
			alreadyCheckInDevice.setDeviceId(alreadyCheckInVehicleDetail.get(0)
					.geteFmFmDeviceMaster().getDeviceId());
			checkInDeviceDetail.get(0).seteFmFmDeviceMaster(
					alreadyCheckInDevice);
			iVehicleCheckInBO.update(checkInDeviceDetail.get(0));

		}
		alreadyCheckInVehicleDetail.get(0).setEfmFmDriverMaster(
				efmFmDriverMaster);
		alreadyCheckInVehicleDetail.get(0).setEfmFmVehicleMaster(
				eFmFmVehicleMaster);
		alreadyCheckInVehicleDetail.get(0).seteFmFmDeviceMaster(
				eFmFmDeviceMasterPO);
		iVehicleCheckInBO.update(alreadyCheckInVehicleDetail.get(0));

		response = "success";
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	// driver,vehicle or device changing

	@POST
	@Path("/changeCheckInEntity")
	public Response changingCheckInEntities(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		String response = "alreadyExist";
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * The listOfCheckedInVehicles method implemented. for getting the list of
	 * checkedIn vehicle details.
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-21
	 * 
	 * @return checkedIn vehicle details.
	 */

	@POST
	@Path("/listOfCheckedInVehicles")
	public Response listOfCheckedInVehicles(
			EFmFmVendorMasterPO eFmFmVendorMasterPO) {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		List<Map<String, Object>> listOfVehicle = new ArrayList<Map<String, Object>>();
		List<EFmFmVehicleCheckInPO> listOfCheckedInVehicle = iVehicleCheckInBO
				.getCheckedInVehicleDetails(eFmFmVendorMasterPO
						.geteFmFmClientBranchPO().getBranchId(), new Date());
		if ((!(listOfCheckedInVehicle.isEmpty()))
				|| listOfCheckedInVehicle.size() != 0) {
			for (EFmFmVehicleCheckInPO vehicleDetails : listOfCheckedInVehicle) {
				Map<String, Object> vehicleList = new HashMap<String, Object>();
				vehicleList.put("checkInId", vehicleDetails.getCheckInId());
				vehicleList.put("driverName", vehicleDetails
						.getEfmFmDriverMaster().getFirstName());
				vehicleList.put("driverNumber", vehicleDetails
						.getEfmFmDriverMaster().getMobileNumber());
				vehicleList.put("driverId", vehicleDetails
						.getEfmFmDriverMaster().getDriverId());
				vehicleList.put("DriverName", vehicleDetails
						.getEfmFmDriverMaster().getFirstName());
				vehicleList.put("MobileNumber", vehicleDetails
						.getEfmFmDriverMaster().getMobileNumber());
				vehicleList.put("deviceNumber", vehicleDetails
						.geteFmFmDeviceMaster().getMobileNumber());
				vehicleList.put("deviceId", vehicleDetails
						.geteFmFmDeviceMaster().getDeviceId());
				vehicleList.put("vehicleMake", vehicleDetails
						.getEfmFmVehicleMaster().getVehicleMake());
				vehicleList.put("capacity", vehicleDetails
						.getEfmFmVehicleMaster().getCapacity());
				vehicleList.put("vehicleNumber", vehicleDetails
						.getEfmFmVehicleMaster().getVehicleNumber());
				vehicleList.put("vehicleId", vehicleDetails
						.getEfmFmVehicleMaster().getVehicleId());
				vehicleList.put("vendorId", vehicleDetails
						.getEfmFmVehicleMaster().getEfmFmVendorMaster()
						.getVendorId());
				vehicleList.put("vendorName", vehicleDetails
						.getEfmFmVehicleMaster().getEfmFmVendorMaster()
						.getVendorName());
				vehicleList.put("capacity", vehicleDetails
						.getEfmFmVehicleMaster().getCapacity());
				vehicleList.put("status", vehicleDetails.getStatus());
				listOfVehicle.add(vehicleList);

			}
		}
		return Response.ok(listOfVehicle, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * The listOfOnRoadVehicles method implemented. for getting the list of
	 * checkedIn vehicle details.
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-21
	 * 
	 * @return OnRoad vehicle details.
	 */
	@POST
	@Path("/vehicleonroad")
	public Response vehiclesOnRoad(EFmFmAssignRoutePO assignRoutePO) {
		IAssignRouteBO assignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");
		List<Map<String, Object>> listOfVehicle = new ArrayList<Map<String, Object>>();
		List<EFmFmAssignRoutePO> vehiclesOnRoad = assignRouteBO
				.getAllLiveTrips(assignRoutePO);
		if ((!(vehiclesOnRoad.isEmpty())) || vehiclesOnRoad.size() != 0) {
			for (EFmFmAssignRoutePO vehicleDetails : vehiclesOnRoad) {
				Map<String, Object> vehicleList = new HashMap<String, Object>();
				vehicleList.put("checkInId", vehicleDetails
						.getEfmFmVehicleCheckIn().getCheckInId());
				vehicleList.put("driverName", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmDriverMaster()
						.getFirstName());
				vehicleList.put("driverNumber", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmDriverMaster()
						.getMobileNumber());
				vehicleList.put("driverId", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmDriverMaster()
						.getDriverId());
				vehicleList.put("DriverName", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmDriverMaster()
						.getFirstName());
				vehicleList.put("MobileNumber", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmDriverMaster()
						.getMobileNumber());
				vehicleList.put("deviceNumber", vehicleDetails
						.getEfmFmVehicleCheckIn().geteFmFmDeviceMaster()
						.getMobileNumber());
				vehicleList.put("deviceId", vehicleDetails
						.getEfmFmVehicleCheckIn().geteFmFmDeviceMaster()
						.getDeviceId());
				vehicleList.put("vehicleMake", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getVehicleMake());
				vehicleList.put("capacity", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getCapacity());
				vehicleList.put("vehicleNumber", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getVehicleNumber());
				vehicleList.put("vehicleId", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getVehicleId());
				vehicleList.put("vendorId", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getEfmFmVendorMaster().getVendorId());
				vehicleList.put("vendorName", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getEfmFmVendorMaster().getVendorName());
				vehicleList.put("capacity", vehicleDetails
						.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
						.getCapacity());
				vehicleList.put("status", vehicleDetails
						.getEfmFmVehicleCheckIn().getStatus());
				listOfVehicle.add(vehicleList);
			}
		}
		return Response.ok(listOfVehicle, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * The addingVendorDetails method implemented. for Adding vendor details.
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-19
	 * 
	 * @return added status.
	 */
	@POST
	@Path("/addingVendorDetails")
	public Response addingVendorDetails(
			EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		iVehicleCheckInBO.save(eFmFmVehicleMasterPO);
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}

	/**
	 * The modifyVendorDetails method implemented. for Modifying vehicle
	 * details.
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-19
	 * 
	 * @return Modified Status.
	 * @throws ParseException
	 */
	@POST
	@Path("/modifyVehicleDetails")
	public Response modifyVehicleDetails(
			EFmFmVehicleMasterPO eFmFmVehicleMasterPO) throws ParseException {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		EFmFmVehicleMasterPO vehicleDetails = iVehicleCheckInBO
				.getParticularVehicleDetail(eFmFmVehicleMasterPO.getVehicleId());
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date permitValidDate = (Date) formatter.parse(eFmFmVehicleMasterPO
				.getPermitValid());
		Date insuranceValidDate = (Date) formatter.parse(eFmFmVehicleMasterPO
				.getInsuranceValid());
		Date registrationValidDate = (Date) formatter
				.parse(eFmFmVehicleMasterPO.getRegistrationValid());
		Date maintenanceValidDate = (Date) formatter.parse(eFmFmVehicleMasterPO
				.getMaintenanceValid());
		Date taxValidDate = (Date) formatter.parse(eFmFmVehicleMasterPO
				.getTaxValidDate());
		Date polutionValidDate = (Date) formatter.parse(eFmFmVehicleMasterPO
				.getPolutionDate());
		vehicleDetails.setPermitValidDate(permitValidDate);
		vehicleDetails.setInsuranceValidDate(insuranceValidDate);
		vehicleDetails.setRegistrationDate(registrationValidDate);
		vehicleDetails.setVehicleFitnessDate(maintenanceValidDate);
		vehicleDetails.setTaxCertificateValid(taxValidDate);
		vehicleDetails.setPolutionValid(polutionValidDate);
		vehicleDetails.setCapacity(eFmFmVehicleMasterPO.getCapacity());
		vehicleDetails.setVehicleOwnerName(eFmFmVehicleMasterPO
				.getVehicleOwnerName());
		vehicleDetails
				.setVehicleNumber(eFmFmVehicleMasterPO.getVehicleNumber());
		vehicleDetails.setVehicleModel(eFmFmVehicleMasterPO.getVehicleModel());
		vehicleDetails.setVehicleMake(eFmFmVehicleMasterPO.getVehicleMake());
		vehicleDetails.setVehicleEngineNumber(eFmFmVehicleMasterPO
				.getVehicleEngineNumber());
		vehicleDetails.setRegistartionCertificateNumber(eFmFmVehicleMasterPO
				.getRegistartionCertificateNumber());
		vehicleDetails.setCapacity(eFmFmVehicleMasterPO.getCapacity());
		vehicleDetails.setContractDetailId(eFmFmVehicleMasterPO
				.getContractDetailId());
		EFmFmVendorContractTypeMasterPO eFmFmVendorContractTypeMaster = new EFmFmVendorContractTypeMasterPO();
		eFmFmVendorContractTypeMaster.setContractTypeId(eFmFmVehicleMasterPO
				.geteFmFmVendorContractTypeMaster().getContractTypeId());
		vehicleDetails
				.seteFmFmVendorContractTypeMaster(eFmFmVendorContractTypeMaster);
		iVehicleCheckInBO.update(vehicleDetails);
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}

	/**
	 * The removeVehicleDetails method implemented. for Modifying vehicle
	 * details.
	 * 
	 * @author Rajan R
	 * 
	 * @since 2015-05-19
	 * 
	 * @return deleted Status.
	 */
	@POST
	@Path("/removeVehicleDetails")
	public Response removeVehicleDetails(
			EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		EFmFmVehicleMasterPO vehicleDetails = iVehicleCheckInBO
				.getParticularVehicleDetail(eFmFmVehicleMasterPO.getVehicleId());
		vehicleDetails.setStatus("D");
		iVehicleCheckInBO.update(vehicleDetails);
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}

	// edit entity button click......all driver,vehicle and device according to
	// the vendor.

	@POST
	@Path("/listOfActiveEntity")
	public Response checkInVehicleAndDevice(
			EFmFmVendorMasterPO eFmFmVendorMasterPO) {
		// IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO)
		// ContextLoader.getContext().getBean("IVendorDetailsBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object> vehicleCheckInList = new HashMap<String, Object>();
		List<EFmFmDeviceMasterPO> allDevice = iVehicleCheckInBO
				.getAllActiveDeviceDetails(eFmFmVendorMasterPO
						.geteFmFmClientBranchPO().getBranchId());
		List<EFmFmVehicleMasterPO> allVehicleByVendor = iVehicleCheckInBO
				.getAllActiveVehicleDetails(eFmFmVendorMasterPO.getVendorId());
		List<EFmFmDriverMasterPO> allDriverByVendor = iVehicleCheckInBO
				.getAllActiveDriverDetails(eFmFmVendorMasterPO.getVendorId());
		List<Map<String, Object>> checkInList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> driverCheckInList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> deviceCheckInList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> escortCheckInList = new ArrayList<Map<String, Object>>();
		if (allVehicleByVendor.size() > 0) {
			for (EFmFmVehicleMasterPO vehicleDetails : allVehicleByVendor) {
				Map<String, Object> vehicleList = new HashMap<String, Object>();
				vehicleList.put("vehicleId", vehicleDetails.getVehicleId());
				vehicleList.put("vehicleNumber",
						vehicleDetails.getVehicleNumber());
				vehicleList.put("vendorName", vehicleDetails
						.getEfmFmVendorMaster().getVendorName());
				vehicleList.put("vendorId", vehicleDetails
						.getEfmFmVendorMaster().getVendorId());
				checkInList.add(vehicleList);
			}
		}
		vehicleCheckInList.put("vehicleDetails", checkInList);
		if (allDriverByVendor.size() > 0) {
			for (EFmFmDriverMasterPO driverDetails : allDriverByVendor) {
				Map<String, Object> driverList = new HashMap<String, Object>();
				driverList.put("driverId", driverDetails.getDriverId());
				driverList.put("driverName", driverDetails.getFirstName());
				driverList.put("mobileNumber", driverDetails.getMobileNumber());
				driverList.put("DriverName", driverDetails.getFirstName());
				driverList.put("MobileNumber", driverDetails.getMobileNumber());

				driverList.put("vendorName", driverDetails
						.getEfmFmVendorMaster().getVendorName());
				driverList.put("vendorId", driverDetails.getEfmFmVendorMaster()
						.getVendorId());
				driverCheckInList.add(driverList);
			}
		}
		vehicleCheckInList.put("driverDetails", driverCheckInList);
		if (allDevice.size() > 0) {
			for (EFmFmDeviceMasterPO deviceDetails : allDevice) {
				Map<String, Object> deviceList = new HashMap<String, Object>();
				deviceList.put("deviceId", deviceDetails.getDeviceId());
				deviceList.put("mobileNumber", deviceDetails.getMobileNumber());
				deviceCheckInList.add(deviceList);
			}
		}
		vehicleCheckInList.put("deviceDetails", deviceCheckInList);
		List<EFmFmEscortCheckInPO> escortList = iVehicleCheckInBO
				.getAllCheckedInEscort(eFmFmVendorMasterPO
						.geteFmFmClientBranchPO().getBranchId(), new Date());
		if (!(escortList.isEmpty()) || escortList.size() != 0) {
			for (EFmFmEscortCheckInPO escorts : escortList) {
				Map<String, Object> escortsDetails = new HashMap<String, Object>();
				escortsDetails.put("escortCheckInId",
						escorts.getEscortCheckInId());
				escortsDetails.put("escortName", escorts.geteFmFmEscortMaster()
						.getFirstName());
				escortCheckInList.add(escortsDetails);
			}
		}
		vehicleCheckInList.put("escortDetails", escortCheckInList);

		return Response.ok(vehicleCheckInList, MediaType.APPLICATION_JSON)
				.build();
	}

	// get all available vehicles,driver and Devices for Changing the entites in
	@POST
	@Path("/listOfActiveDriver")
	public Response listOfActiveDriver(EFmFmVendorMasterPO eFmFmVendorMasterPO) {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object> vehicleCheckInList = new HashMap<String, Object>();
		// List<EFmFmDriverMasterPO> allDriverByVendor
		// =iVehicleCheckInBO.randomDriverDetails(eFmFmVendorMasterPO.getVendorId(),new
		// Date());
		List<EFmFmDriverMasterPO> allDriverByVendor = iVehicleCheckInBO
				.getAllActiveDriverDetails(eFmFmVendorMasterPO.getVendorId());

		List<Map<String, Object>> checkInList = new ArrayList<Map<String, Object>>();
		if (allDriverByVendor.size() > 0) {
			for (EFmFmDriverMasterPO driverDetails : allDriverByVendor) {
				Map<String, Object> driverList = new HashMap<String, Object>();
				driverList.put("driverId", driverDetails.getDriverId());
				driverList.put("driverName", driverDetails.getFirstName());
				driverList.put("mobileNumber", driverDetails.getMobileNumber());
				driverList.put("vendorId", driverDetails.getEfmFmVendorMaster()
						.getVendorId());
				checkInList.add(driverList);
			}
		}
		vehicleCheckInList.put("driverDetails", checkInList);
		return Response.ok(vehicleCheckInList, MediaType.APPLICATION_JSON)
				.build();
	}

	@POST
	@Path("/listOfActiveVehicle")
	public Response listOfActiveVehicle(EFmFmVendorMasterPO eFmFmVendorMasterPO) {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object> vehicleCheckInList = new HashMap<String, Object>();
		// List<EFmFmVehicleMasterPO>
		// allVehicleByVendor=iVehicleCheckInBO.getAvailableVehicleDetails(eFmFmVendorMasterPO.getVendorId(),new
		// Date());
		List<EFmFmVehicleMasterPO> allVehicleByVendor = iVehicleCheckInBO
				.getAllActiveVehicleDetails(eFmFmVendorMasterPO.getVendorId());

		List<Map<String, Object>> checkInList = new ArrayList<Map<String, Object>>();
		if (allVehicleByVendor.size() > 0) {
			for (EFmFmVehicleMasterPO vehicleDetails : allVehicleByVendor) {
				Map<String, Object> vehicleList = new HashMap<String, Object>();
				vehicleList.put("vehicleId", vehicleDetails.getVehicleId());
				vehicleList.put("vehicleNumber",
						vehicleDetails.getVehicleNumber());
				vehicleList.put("vendorName", vehicleDetails
						.getEfmFmVendorMaster().getVendorName());
				vehicleList.put("vendorId", vehicleDetails
						.getEfmFmVendorMaster().getVendorId());
				checkInList.add(vehicleList);
			}
		}
		vehicleCheckInList.put("vehicleDetails", checkInList);
		return Response.ok(vehicleCheckInList, MediaType.APPLICATION_JSON)
				.build();
	}

	@POST
	@Path("/listOfContractType")
	public Response listOfContractType(EFmFmClientBranchPO eFmFmClientBranchPO) {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object> listOfContractType = new HashMap<String, Object>();
		List<EFmFmVendorContractTypeMasterPO> allContractType = iVehicleCheckInBO
				.getAllContractType(eFmFmClientBranchPO.getBranchId());
		List<Map<String, Object>> checkInList = new ArrayList<Map<String, Object>>();
		if (allContractType.size() > 0) {
			for (EFmFmVendorContractTypeMasterPO contractTypeList : allContractType) {
				Map<String, Object> contractList = new HashMap<String, Object>();
				contractList.put("contractTypeID",
						contractTypeList.getContractTypeId());
				contractList.put("contractType",
						contractTypeList.getContractType());
				checkInList.add(contractList);
			}
			listOfContractType.put("vehicleDetails", checkInList);
		}
		return Response.ok(listOfContractType, MediaType.APPLICATION_JSON)
				.build();
	}

	/**
	 * 
	 * 
	 * @param with
	 *            a particular vehicle and device from Web
	 * @return return Success
	 */

	@POST
	@Path("/devicecheckin")
	public Response driverCheckInFromDevice(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext()
				.getBean("IApprovalBO");
		Map<String, Object> responce = new HashMap<String, Object>();
		List<EFmFmDriverMasterPO> particularDriverDetails = approvalBO
				.getParticularDriverDetailFromDeriverId(eFmFmVehicleCheckInPO
						.getDriverId());
		if (!(particularDriverDetails.isEmpty())
				|| particularDriverDetails.size() != 0) {
			if (particularDriverDetails.get(0).getStatus()
					.equalsIgnoreCase("P")) {
				responce.put("status", "pending");
				return Response.ok(responce, MediaType.APPLICATION_JSON)
						.build();
			}
		} else {
			responce.put("status", "fail");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}

		eFmFmVehicleCheckInPO.setCheckInTime(new Date());
		List<EFmFmVehicleCheckInPO> vehicleCheckInPO = vehicleCheckInBO
				.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);
		if (vehicleCheckInPO.get(0).getStatus().equalsIgnoreCase("N")) {
			responce.put("status", "fail");
			return Response.ok(responce, MediaType.APPLICATION_JSON).build();
		}
		vehicleCheckInPO.get(vehicleCheckInPO.size() - 1).setStatus("N");
		vehicleCheckInPO.get(vehicleCheckInPO.size() - 1).setCheckOutTime(
				new Date());
		vehicleCheckInBO
				.update(vehicleCheckInPO.get(vehicleCheckInPO.size() - 1));
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * 
	 * 
	 * @param driverMaster
	 *            checkout the particular driver with a particular vehicle and
	 *            device from Web
	 * @return return Success
	 */

	@POST
	@Path("/driverCheckOut")
	public Response driverCheckOutFromDevice(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		Map<String, Object> responce = new HashMap<String, Object>();
		eFmFmVehicleCheckInPO.setCheckInTime(new Date());
		List<EFmFmVehicleCheckInPO> vehicleCheckInPO = vehicleCheckInBO
				.getCheckedInVehicleDetailsFromChecInAndBranchId(eFmFmVehicleCheckInPO.getCheckInId(), eFmFmVehicleCheckInPO.getBranchId());
		vehicleCheckInPO.get(vehicleCheckInPO.size() - 1).setStatus("N");
		vehicleCheckInPO.get(vehicleCheckInPO.size() - 1).setCheckOutTime(
				new Date());
		vehicleCheckInBO
				.update(vehicleCheckInPO.get(vehicleCheckInPO.size() - 1));
		responce.put("status", "success");
		return Response.ok(responce, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * 
	 * 
	 * @param driverMaster
	 *            checkout the particular driver with a particular vehicle and
	 *            device from Web
	 * @return return Success
	 */

	@POST
	@Path("/actualvehiclelist")
	public Response getAllActualVehicles(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader
				.getContext().getBean("IVehicleCheckInBO");
		List<EFmFmVehicleMasterPO> allVehicleDetail = vehicleCheckInBO
				.getAllActualVehicleDetailsFromBranchId(eFmFmVehicleCheckInPO
						.getBranchId());
		List<Map<String, Object>> vehicleList = new ArrayList<Map<String, Object>>();
		System.out.println("....." + allVehicleDetail.size());
		if (!(allVehicleDetail.isEmpty()) || allVehicleDetail.size() != 0) {
			for (EFmFmVehicleMasterPO vehicleDetail : allVehicleDetail) {
				if (!(vehicleDetail.getVehicleNumber().contains("DUMMY"))) {
					Map<String, Object> vehicle = new HashMap<String, Object>();
					vehicle.put("vehicleNum", vehicleDetail.getVehicleNumber());
					vehicle.put("vehicleId", vehicleDetail.getVehicleId());
					vehicleList.add(vehicle);
				}
			}
		}
		return Response.ok(vehicleList, MediaType.APPLICATION_JSON).build();
	}

}