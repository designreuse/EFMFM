package com.newtglobal.eFmFmFleet.business.dao;

import java.util.Date;
import java.util.List;

import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmFixedDistanceContractDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripBasedContractDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorContractInvoicePO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorContractTypeMasterPO;


public interface IVehicleCheckInDAO {
	
	/*
	 * Start EFmFmDeviceMasterPO master details 
	 */
	public void save(EFmFmDeviceMasterPO eFmFmDeviceMasterPO);
	public void update(EFmFmDeviceMasterPO  eFmFmDeviceMasterPO);
	public void delete(EFmFmDeviceMasterPO  eFmFmDeviceMasterPO);

	/*
	 * Start EFmFmVehicleMasterPO master details 
	 */
	public void save(EFmFmVehicleMasterPO eFmFmVehicleMasterPO);
	public void update(EFmFmVehicleMasterPO  eFmFmVehicleMasterPO);
	public void delete(EFmFmVehicleMasterPO  eFmFmVehicleMasterPO);
	/**
	* The getParticularVehicleDetails implements for
	* Getting particular vehicle details based on vehicle Number. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	public EFmFmVehicleMasterPO getParticularVehicleDetails(String vehicleNumber,int clientId);
	public EFmFmVehicleMasterPO getParticularVehicleDetail(int vehicleId);
	/**
	* The getAllVehicleDetails implements for
	* Getting alla Vehicle details based on vendor & client Id. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-18 
	*/
	public List<EFmFmVehicleMasterPO> getAllVehicleDetails(EFmFmVehicleMasterPO eFmFmVehicleMasterPO);
	/**
	* The getAllDriverDetails implements for
	* Getting all driver details based on vendor & client Id. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-19 
	*/
	public List<EFmFmDriverMasterPO> getAllDriverDetails(EFmFmDriverMasterPO eFmFmDriverMasterPO);
	/**
	* The randomDriveDetails implements for
	* Getting all random driver details based on vendor. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-20 
	*/
	public List<EFmFmDriverMasterPO> randomDriverDetails(int vendorId,Date todayDate);
	/**
	* The getCheckInDriverDetails implements for
	* Getting previously allocated deriver details. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-20 
	*/
	public EFmFmVehicleCheckInPO getCheckInDriverDetails(int driverId);
	/**
	* The vehicleDriverCheckIn implements for
	* Check In  the vehicle Id along with driver Id. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-20 
	*/
	
	public void vehicleDriverCheckIn(EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO);
	/**
	* The getAvailableVehicleDetails implements for
	* Getting all available Vehicle details based on vendorId. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-20 
	*/
	public List<EFmFmVehicleMasterPO> getAvailableVehicleDetails(int vendorId,Date todayDate);
	
	/**
	* The getCheckedInVehicleDetails implements for
	* Getting all checkedIn Vehicle details based on vendorId. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-21 
	*/
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetails(EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO);
	
	/**
	 * The getCheckedInVehicleDetails implements for
	 * Getting all checkedIn Vehicle details based on vendorId. 
	 *
	 * @author  Rajan R
	 * 
	 * @since   2015-05-21 
	 */ 
	 public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetails(int clientId,Date date);
	
	/**
	* The getAllEscortDetails implements for
	* Getting all Escort details based on client Id. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-25 
	*/
	public List<EFmFmEscortMasterPO> getAllEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO);
	/**
	* The getParticularEscortDetails implements for
	* Getting Escort details based on escort Id. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-25 
	*/
	
	public List<EFmFmEscortMasterPO> getParticularEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO);
	
	/**
	* The saveEscortDetails implements for
	* storing escort details. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-28 
	*/
	public void saveEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO);
	public List<EFmFmVehicleCheckInPO> getParticulaCheckedInVehicleDetails(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO);
	public int getNumberOfVehiclesFromClientId(int clientId);
	public List<EFmFmVehicleCheckInPO> getAssignedVehicleDetails(int clientId,
			Date todayDate);
	public void update(EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO);
	public List<EFmFmEscortCheckInPO> getAllCheckedInEscort(int clientId,
			Date todayDate);
	public List<EFmFmEscortMasterPO> getAllCheckInEscort(int clientId, Date todayDate);
	public void saveEscortCheckIn(EFmFmEscortCheckInPO eFmFmEscortCheckInPO);
	public List<EFmFmVehicleCheckInPO> checkedOutParticularDriver(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO);
	public void updateEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO);
	public List<EFmFmDeviceMasterPO> deviceNumberExistsCheck(
			EFmFmDeviceMasterPO eFmFmDeviceMasterPO);
	public List<EFmFmDeviceMasterPO> deviceImeiNumberExistsCheck(
			EFmFmDeviceMasterPO eFmFmDeviceMasterPO);
	public List<EFmFmDriverMasterPO> getParticularDriverDetailsFromMobileNum(
			String mobileNumber, int clientId);
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInDriver(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO);
	public List<EFmFmTripAlertsPO> getGroupByDriver(int clientId, Date fromDate,
			Date toDate);
	public List<EFmFmTripAlertsPO> getScoreCardDriver(int clientId, int driverId,
			Date fromDate, Date toDate);
	public List<EFmFmAssignRoutePO> getDriverAssignedTrip(int clientId, int driverId,
			Date fromDate, Date toDate);
	public List<EFmFmTripAlertsPO> getGroupByVehicle(int clientId, Date fromDate,
			Date toDate);
	public List<EFmFmTripAlertsPO> getScoreCardVehicle(int clientId, int driverId,
			Date fromDate, Date toDate);
	public List<EFmFmAssignRoutePO> getVehicleAssignedVehicleTrip(int clientId,
			int driverId, Date fromDate, Date toDate);
	public List<EFmFmVehicleMasterPO> getRcNumberDetails(String rcNumber, int branchId);
	public List<EFmFmVehicleMasterPO> getEngineNoDetails(String engineNo, int branchId);
	public List<EFmFmEscortMasterPO> getMobileNoDetails(
			EFmFmEscortMasterPO eFmFmEscortMasterPO);
	public List<EFmFmDeviceMasterPO> getListOfAllActiveDevices(
			EFmFmDeviceMasterPO eFmFmDeviceMasterPO);
	public List<EFmFmVehicleCheckInPO> getCheckInDetails(int branchId);
	public List<EFmFmDriverMasterPO> getAllActiveDriverDetails(int vendorId);
	public List<EFmFmVehicleMasterPO> getAllActiveVehicleDetails(int vendorId);
	public List<EFmFmVehicleCheckInPO> alreadyCheckInExist(int vehicleId);	
	//public List<EFmFmVendorContractInvoicePO> getInvoiceforVehicle(Date fromDate, Date toDate, int branchId, int vehicleId);
	
	public List<EFmFmDriverMasterPO> getAllDriverDetails(int branchId);
	public List<EFmFmEscortCheckInPO> getParticulaEscortDetailFromEscortId(
			int branchId, int escortId);
	public void update(EFmFmEscortCheckInPO eFmFmEscortCheckInPO);	
	//public List<EFmFmVendorContractInvoicePO> getInvoiceDetailsVendor(Date fromDate, Date toDate, int branchId, int vendorId);
	//public List<EFmFmVendorContractInvoicePO> getvendorInvoiceFixedDistanceSummary(Date fromDate, Date toDate, int branchId,int vendorId);
	
	public List<EFmFmDeviceMasterPO> getDeviceDetailsFromDeviceId(int deviceId,
			int branchId);
	public List<EFmFmVehicleCheckInPO> getAllCheckedInVehiclesByVendor(int branchId,
			int vendorId);
	public List<EFmFmVehicleCheckInPO> getAllCheckedInDriversByVendor(int branchId,
			int vendorId);
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInDriverDetails(
			int branchId, int driverId);
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInDeviceDetails(
			int branchId, int deviceId);
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInVehicles(int branchId,
			int vehicleId);	
	/*Invoice*/
	public List<EFmFmVehicleMasterPO> getVendorBasedTripSheet(Date fromDate, Date toDate, int branchId, int vendorId);
	public List<EFmFmVehicleMasterPO> getNoOfWorkingDays(Date fromDate, Date toDate, int branchId, int vehicleId,String contractType, int contractDetailId);
	public List<EFmFmTripBasedContractDetailPO> getTripDistanceDetails(EFmFmTripBasedContractDetailPO eFmFmTripBasedContractDetailPO);
	public List<EFmFmFixedDistanceContractDetailPO> getFixedDistanceDetails(EFmFmFixedDistanceContractDetailPO eFmFmFixedDistanceContractDetailPO);
	public List<EFmFmAssignRoutePO> getVendorBasedVehicleDetails(Date fromDate, Date toDate, int branchId, int vendorId,String contractType,int contractDetailsId);
	public List<EFmFmAssignRoutePO> getVehicleBasedTripSheet(Date fromDate, Date toDate, int branchId,int vehicleId);
	public List<EFmFmVehicleMasterPO> getTripBasedNoOfWorkingDays(Date fromDate, Date toDate, int branchId, int vehicleId,String contractType, int contractDetailId);
	public void save(EFmFmVendorContractInvoicePO eFmFmVendorContractInvoicePO);
	public void update(EFmFmVendorContractInvoicePO  eFmFmVendorContractInvoicePO);
	public void delete(EFmFmVendorContractInvoicePO  eFmFmVendorContractInvoicePO);
	public List<EFmFmVendorContractInvoicePO> getInvoiceforVendor(Date fromDate, Date toDate, int branchId, int vendorId, String invoiceType);	
	public List<EFmFmVehicleMasterPO> getAllVehicleDetails(int branchId);	
	public List<EFmFmVendorContractInvoicePO> getListOfInvoiceNumbers(int branchId);
	public List<EFmFmVendorContractInvoicePO> getInvoiceDetails(int branchId,int InvoiceNumber);	
	public List<EFmFmVendorContractInvoicePO> getInvoiceforVendorByGroup(Date fromDate, Date toDate, int branchId,int vendorId);
	public List<EFmFmVehicleMasterPO> getSumOfTotalKmByVehicle(Date fromDate, Date toDate, int branchId, int vehicleId,String contractType, int contractDetailId);
	public List<EFmFmVendorContractInvoicePO> getInvoiceTripBasedVehicle(Date fromDate, Date toDate, int branchId,int vehicleId);
	public List<EFmFmAssignRoutePO> getTripBasedVehicleDetails(Date fromDate, Date toDate, int branchId, int vendorId,String contractType, int contractDetailsId);
	public List<EFmFmVendorContractInvoicePO> getInvoiceByVehicleFixedDistance(Date fromDate, Date toDate, int branchId,int vendorId, int vehicleId);
	public List<EFmFmVendorContractTypeMasterPO> getContractTypeDetails(String contractType,int branchId);
	public List<EFmFmVendorContractTypeMasterPO> getAllContractType(int branchId);
	public void deleteParticularCheckInEntryFromDeviceVehicleDriver(int checkInId);
	public List<EFmFmVehicleCheckInPO> alreadyCheckInDriverExistence(int driverId);
	public List<EFmFmVehicleCheckInPO> getDriverCheckInDetails(int branchId);
	public List<EFmFmDriverMasterPO> getAllApprovedDriversByVendorId(int vendorId,
			int branchId);
	public List<EFmFmVehicleMasterPO> getAllApprovedVehiclesByVendorId(int vendorId,
			int branchId);
	public List<EFmFmDeviceMasterPO> getAllApprovedDevices(int branchId);
	public List<EFmFmVehicleCheckInPO> alreadyCheckInDeviceExistence(int deviceId,
			int branchId);
	public List<EFmFmDeviceMasterPO> getAllActiveDeviceDetails(int branchId);
	public void deleteExtraCheckInEntry(int checkInId);
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetailsForEditBucket(
			int branchId);
	public List<EFmFmVehicleCheckInPO> getLastCheckInEntitiesDetails(int branchId);
	public long getAllCheckedInVehicleCount(int branchId);
	public List<EFmFmVehicleCheckInPO> getLastCheckedOutInDeviceDetails(int branchId,
			int deviceId);
	public List<EFmFmVehicleCheckInPO> getCheckedInVehiclesOnRoad(int branchId);
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetailsFromChecInId(
			int checkInId);
	public List<EFmFmVehicleMasterPO> getVehicleDetailsFromVehicleNumber(
			String vehicleNumber, int branchId);
	public List<EFmFmVehicleMasterPO> getAllActualVehicleDetailsFromBranchId(
			int branchId);
}
