package com.newtglobal.eFmFmFleet.business.bo;

import java.util.List;

import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientProjectDetailsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;

public interface IEmployeeDetailBO {
	/*
	 * Start IEmployeeDetailBO  master details 
	 */
	public void save(EFmFmUserMasterPO eFmFmUserMasterPO);
	public void update(EFmFmUserMasterPO eFmFmUserMasterPO);
	public void delete(EFmFmUserMasterPO eFmFmUserMasterPO);
	public List<EFmFmUserMasterPO> getAllEmployeeDetailsFromClientId(int clientId);
	public boolean doesDeviceExist(String deviceId, int clientId);
	public EFmFmUserMasterPO getParticularDeviceDetails(String deviceId);
	public List<EFmFmUserMasterPO> loginEmployeeDetails(String employeeId,String password);
	public List<EFmFmClientBranchPO> doesClientCodeExist(String branchCode);
	public boolean doesEmailIdExist(String emailId, int clientId);
	public List<EFmFmUserMasterPO> getParticularEmployeeDetailsFromEmailId(
			String emailId, int clientId);
	public List<EFmFmUserMasterPO> getParticularEmpDetailsFromEmployeeId(
			String employeeId, int clientId);
	public List<EFmFmUserMasterPO> getParticularEmpDetailsFromUserId(int userId,int branchId);
	/*
	 * End IEmployeeDetailBO  master details 
	 */
	public void save (EFmFmClientProjectDetailsPO eFmFmClientProjectDetailsPO);	
	public List<EFmFmClientProjectDetailsPO> getProjectDetails(String string,int branchId);
	public List<EFmFmUserMasterPO> getEmpMobileNoDetails(String mobileNo, int branchId);
	public List<EFmFmUserMasterPO> getEmpMobileNumberCheck(String mobileNumber,
			int branchId);
	public List<EFmFmUserMasterPO> getParticularEmpDetailsFromEmployeeIdForGuest(
			String employeeId, int branchId);
	public List<EFmFmUserMasterPO> getParticularGuestDetailsFromEmployeeId(
			String employeeId, int branchId);
	 
	 
}
