package com.newtglobal.eFmFmFleet.business.bo.boImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtglobal.eFmFmFleet.business.bo.IUserMasterBO;
import com.newtglobal.eFmFmFleet.business.dao.IUserMasterDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientUserRolePO;
import com.newtglobal.eFmFmFleet.model.EFmFmRoleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.PersistentLoginPO;
@Service("IUserMasterBO")
public class UserMasterBOImpl implements IUserMasterBO{

	@Autowired
	IUserMasterDAO iUserMasterDAO;
	public void setIUserMasterDAO(IUserMasterDAO iUserMasterDAO) {
		this.iUserMasterDAO = iUserMasterDAO;
	}
	
	@Override
	public void save(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.save(eFmFmUserMasterPO);
	
	}

	@Override
	public void update(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.update(eFmFmUserMasterPO);
	}

	@Override
	public void delete(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.delete(eFmFmUserMasterPO);
	}
	
	@Override
	public List<EFmFmRoleMasterPO>  getUserRoleByRoleId(int roleId) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getUserRoleByRoleId(roleId);
	}
	@Override
	public EFmFmUserMasterPO getUserDetailByUserName(String userName) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getUserDetailByUserName(userName);
	}	

	@Override
	public void save(PersistentLoginPO persistentLoginPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.save(persistentLoginPO);
	}

	@Override
	public void update(PersistentLoginPO persistentLoginPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.update(persistentLoginPO);
		
	}
	@Override
	public void delete(PersistentLoginPO persistentLoginPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.delete(persistentLoginPO);
	}


	@Override
	public String getUserNamebySeries(String series) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getUserNamebySeries(series);
	}

	@Override
	public void updaetLastrequestTimebyuserName(String userName) {
		// TODO Auto-generated method stub
		iUserMasterDAO.updaetLastrequestTimebyuserName(userName);
	}

	@Override
	public int isAleradyLoggedin(String userName) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.isAleradyLoggedin(userName);
	}
	@Override
	public void updatePersistentPO(PersistentLoginPO persistentLoginPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.updatePersistentPO(persistentLoginPO);
	}

	@Override
	public void delteRecord(String ipAddress) {
		// TODO Auto-generated method stub
		iUserMasterDAO.delteRecord(ipAddress);
	}

	@Override
	public List<PersistentLoginPO> getUserLoggedInDetail(String UserName) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getUserLoggedInDetail(UserName);
	}

	@Override
	public PersistentLoginPO getAllLoggedUser(
			PersistentLoginPO persistentLoginPO) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getAllLoggedUser(persistentLoginPO);
	}
	
	

	
	@Override
	public void save(EFmFmRoleMasterPO eFmFmRoleMasterPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.save(eFmFmRoleMasterPO);
	}

	@Override
	public void update(EFmFmRoleMasterPO eFmFmRoleMasterPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.update(eFmFmRoleMasterPO);

	}

	@Override
	public void delete(EFmFmRoleMasterPO eFmFmRoleMasterPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.delete(eFmFmRoleMasterPO);
	}

	@Override
	public List<EFmFmUserMasterPO> getLoggedInUserDetailFromClientIdAndUserId(
			EFmFmUserMasterPO eFmFmUserMaster) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getLoggedInUserDetailFromClientIdAndUserId(eFmFmUserMaster);
	}

	@Override
	public List<EFmFmUserMasterPO> getUsersFromClientId(
			EFmFmUserMasterPO userMasterPO) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getUsersFromClientId(userMasterPO);
	}

	@Override
	public List<EFmFmClientBranchPO> getClientDetails(int clientId) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getClientDetails(clientId);
	}

	@Override
	public void save(EFmFmClientBranchPO eFmFmClientMasterPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.save(eFmFmClientMasterPO);
	}

	@Override
	public void update(EFmFmClientBranchPO eFmFmClientMasterPO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.update(eFmFmClientMasterPO);
	}

	

	@Override
	public List<EFmFmClientUserRolePO> getUserRoleByClientId(int clientId) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getUserRoleByClientId(clientId);
	}

	@Override
	public List<EFmFmRoleMasterPO> getRoleId(String roleName) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getRoleId(roleName);
	}

	@Override
	public void save(EFmFmClientUserRolePO eFmFmClientUserRolePO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.save(eFmFmClientUserRolePO);
	}

	@Override
	public void update(EFmFmClientUserRolePO eFmFmClientUserRolePO) {
		// TODO Auto-generated method stub
		iUserMasterDAO.update(eFmFmClientUserRolePO);
	}

	@Override
	public List<EFmFmClientUserRolePO> getUserRolesFromUserIdAndBranchId(
			int userId, int branchId) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getUserRolesFromUserIdAndBranchId(userId, branchId);
	}

	
	@Override
	public PersistentLoginPO PersistentLoginPODettail(String series) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.PersistentLoginPODettail(series);
	}

	@Override
	public List<EFmFmUserMasterPO> getAllUsersBelogsProject(int branchId,
			int projectId) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getAllUsersBelogsProject(branchId, projectId);
	}

	@Override
	public List<EFmFmUserMasterPO> getUsersRoleExist(int branchId,
			String clientProjectId, String role) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getUsersRoleExist(branchId, clientProjectId, role);
	}

	@Override
	public void deleteAnEmployeeFromData(int userId) {
		// TODO Auto-generated method stub
		iUserMasterDAO.deleteAnEmployeeFromData(userId);
	}

	@Override
	public List<EFmFmUserMasterPO> getEmployeeUserDetailFromEmployeeId(
			int branchId, String employeeId) {
		// TODO Auto-generated method stub
		return iUserMasterDAO.getEmployeeUserDetailFromEmployeeId(branchId, employeeId);
	}	
	
	}
