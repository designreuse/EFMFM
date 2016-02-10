package com.newtglobal.eFmFmFleet.business.bo;

import java.util.List;

import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;

public interface IApprovalBO {	
	/*
	 * start EFmFmDriverMasterPO  master details 
	 */
	public void save(EFmFmDriverMasterPO  eFmFmDriverMasterPO);
	public void update(EFmFmDriverMasterPO  eFmFmDriverMasterPO);
	public void delete(EFmFmDriverMasterPO  eFmFmDriverMasterPO);
	public List<EFmFmDriverMasterPO> getAllUnapprovedDrivers(int clientId);
	public List<EFmFmDriverMasterPO> getAllApprovedDrivers(int clientId);
	public List<EFmFmDriverMasterPO> getAllInActiveDrivers(int clientId);
	public EFmFmDriverMasterPO getParticularDriverDetail(int driverId);
	public void deleteParticularDriver(int driverId);
	
	/**
	* Register Driver device and update the driver data
	* or with particular client
	*
	* @author  SARFRAZ Khan
	* 
	* @since   2015-05-27 
	*/	
	public List<EFmFmDriverMasterPO> getParticularDriverDeviceDetails(
			String mobileNo, int clientId);
	/*
	 * end EFmFmDriverMasterPO  master details 
	 */
	
	/*
	 * start Vehicle  approval details 
	 */
	public List<EFmFmVehicleMasterPO> getAllUnapprovedVehicles(int clientId);
	public List<EFmFmVehicleMasterPO> getAllApprovedVehicles(int clientId);
	public List<EFmFmVehicleMasterPO> getAllInActiveVehicles(int clientId);
	public void deleteParticularVehicle(int vehicleId);
	/*
	 * end Vehicle  approval details 
	 */
	/*
	 * start vendor  approval details 
	 */
	public List<EFmFmVendorMasterPO> getAllUnapprovedVendors(int clientId);
	public List<EFmFmVendorMasterPO> getAllApprovedVendors(int clientId);
	public List<EFmFmVendorMasterPO> getAllInActiveVendors(int clientId);
	public void deleteParticularVendor(int vendorId);
	/*
	 * end vendor  master details 
	 */
	public void saveRouteTripDetails(
			EFmFmEmployeeTripDetailPO eFmFmEmployeeTripDetailPO);
	public List<EFmFmDriverMasterPO> getParticularDriverDetailsFromDeviceToken(
			String deviceId, int clientId);
	public List<EFmFmDriverMasterPO> getParticularDriverDetailFromDeriverId(int driverId);

}
