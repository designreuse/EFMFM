package com.newtglobal.eFmFmFleet.business.bo;

import java.util.Date;
import java.util.List;

import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverFeedbackPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;

public interface IVendorDetailsBO {
	/*
	 * Start EFmFmVendorMasterPO master details 
	 */
	public void save(EFmFmVendorMasterPO eFmFmVendorMasterPO);
	public void update(EFmFmVendorMasterPO  eFmFmVendorMasterPO);
	public void delete(EFmFmVendorMasterPO  eFmFmVendorMasterPO);	
	
	public void save(EFmFmDriverFeedbackPO driverFeedbackPO);

	/**
	* The getAllVendorName implements
	* for validating and getting vendor names from vendor master table. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	public List<EFmFmVendorMasterPO> getAllVendorName(String vendorName,int clientId);
	public EFmFmVendorMasterPO getParticularVendorDetail(int vendorId);
	/**
	* The getAllVendorsDetails implements 
	* for validating and getting vendor names from vendor master table. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-18 
	*/	
	public List<EFmFmVendorMasterPO> getAllVendorsDetails(EFmFmVendorMasterPO eFmFmVendorMasterPO);
	
	/**
	* check Driver device Exist or not 
	* or with particular client
	*
	* @author  SARFRAZ Khan
	* 
	* @since   2015-05-27 
	*/	
	
	public boolean doesDriverDeviceExist(String deviceId,int clientId);
	
	public List<EFmFmDeviceMasterPO> getAllDeviceDetails(int clientId, Date todayDate);
	public List<EFmFmVendorMasterPO> getVendorTinNumber(String tinNumber, int branchId);
	public List<EFmFmVendorMasterPO> getVendorEmailId(String emailID, int branchId);
	public List<EFmFmVendorMasterPO> getVendorMobileNo(String vendorMobileNo,
			int branchId);
	public List<EFmFmDeviceMasterPO> getAllActiveDeviceDetails(int branchId);
	public List<EFmFmVendorMasterPO> getAllApprovedVendorsDetails(int branchId);
	public List<EFmFmDeviceMasterPO> getAllDeviceDetailsFromBranchId(int branchId);

}
