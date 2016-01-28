package com.newtglobal.eFmFmFleet.business.bo.boImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtglobal.eFmFmFleet.business.bo.IEmployeeDetailBO;
import com.newtglobal.eFmFmFleet.business.dao.IEmployeeDetailDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientProjectDetailsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;

@Service("IEmployeeDetailBO")
public class IEmployeeDetailBOImpl implements IEmployeeDetailBO{
	
	@Autowired
	IEmployeeDetailDAO employeeDetailDAO;
	public void setIUserMasterDAO(IEmployeeDetailDAO employeeDetailDAO) {
		this.employeeDetailDAO = employeeDetailDAO;
	}

	@Override
	public void save(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		employeeDetailDAO.save(eFmFmUserMasterPO);
	}

	@Override
	public void update(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		employeeDetailDAO.update(eFmFmUserMasterPO);
	}

	@Override
	public void delete(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		employeeDetailDAO.delete(eFmFmUserMasterPO);
	}

	@Override
	public List<EFmFmUserMasterPO> getAllEmployeeDetailsFromClientId(
			int clientId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getAllEmployeeDetailsFromClientId(clientId);
	}

	@Override
	public boolean doesDeviceExist(String deviceId,int clientId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.doesDeviceExist(deviceId, clientId);
	}

	@Override
	public EFmFmUserMasterPO getParticularDeviceDetails(String deviceId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getParticularDeviceDetails(deviceId);
	}

	@Override
	public List<EFmFmUserMasterPO> loginEmployeeDetails(String employeeId,String password) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.loginEmployeeDetails(employeeId,password);
	}

	@Override
	public List<EFmFmClientBranchPO> doesClientCodeExist(String branchCode) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.doesClientCodeExist(branchCode);
	}

	@Override
	public boolean doesEmailIdExist(String emailId, int clientId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.doesEmailIdExist(emailId, clientId);
	}

	@Override
	public List<EFmFmUserMasterPO> getParticularEmployeeDetailsFromEmailId(
			String emailId, int clientId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getParticularEmployeeDetailsFromEmailId(emailId, clientId);
	}

	@Override
	public List<EFmFmUserMasterPO> getParticularEmpDetailsFromEmployeeId(
			String employeeId, int clientId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getParticularEmpDetailsFromEmployeeId(employeeId, clientId);
	}

	@Override
	public List<EFmFmUserMasterPO> getParticularEmpDetailsFromUserId(
			int userId, int branchId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getParticularEmpDetailsFromUserId(userId, branchId);
	}

	@Override
	public void save(EFmFmClientProjectDetailsPO eFmFmClientProjectDetailsPO) {
		employeeDetailDAO.save(eFmFmClientProjectDetailsPO);
		
	}

	@Override
	public List<EFmFmClientProjectDetailsPO> getProjectDetails(String projectId, int branchId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getProjectDetails(projectId, branchId);
	}

	@Override
	public List<EFmFmUserMasterPO> getEmpMobileNoDetails(String mobileNo,
			int branchId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getEmpMobileNoDetails(mobileNo, branchId);
	}

	@Override
	public List<EFmFmUserMasterPO> getEmpMobileNumberCheck(String mobileNumber,
			int branchId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getEmpMobileNumberCheck(mobileNumber, branchId);
	}

	@Override
	public List<EFmFmUserMasterPO> getParticularEmpDetailsFromEmployeeIdForGuest(
			String employeeId, int branchId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getParticularEmpDetailsFromEmployeeIdForGuest(employeeId, branchId);
	}

	@Override
	public List<EFmFmUserMasterPO> getParticularGuestDetailsFromEmployeeId(
			String employeeId, int branchId) {
		// TODO Auto-generated method stub
		return employeeDetailDAO.getParticularGuestDetailsFromEmployeeId(employeeId, branchId);
	}

}
