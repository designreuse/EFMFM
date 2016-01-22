package com.newtglobal.eFmFmFleet.business.bo;

import java.util.List;

import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientUserRolePO;
import com.newtglobal.eFmFmFleet.model.EFmFmRoleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.PersistentLoginPO;

public interface IUserMasterBO {
	/*
	 * start PersistentLoginPO  master details 
	 */
	public void save(PersistentLoginPO  persistentLoginPO);
	public void update(PersistentLoginPO  persistentLoginPO);
	public void delete(PersistentLoginPO  persistentLoginPO);
	public String getUserNamebySeries(String series);
	public PersistentLoginPO PersistentLoginPODettail(String series);
	public void updaetLastrequestTimebyuserName(String userName);
	public int isAleradyLoggedin(String userName);
	public void updatePersistentPO(PersistentLoginPO persistentLoginPO);
	public void delteRecord(String ipAddress);
	public List<PersistentLoginPO> getUserLoggedInDetail(String UserName);
	public PersistentLoginPO getAllLoggedUser(PersistentLoginPO persistentLoginPO);

	/*
	 * end Role master details 
	 */
	/*
	 * start Role master details 
	 */
	public void save(EFmFmRoleMasterPO eFmFmRoleMasterPO);
	public void update(EFmFmRoleMasterPO eFmFmRoleMasterPO);
	public void delete(EFmFmRoleMasterPO eFmFmRoleMasterPO);
	/*
	 * end Role master details 
	 */
	/*
	 * start use master details 
	 */
    public void save(EFmFmUserMasterPO eFmFmUserMasterPO);
	public void update(EFmFmUserMasterPO eFmFmUserMasterPO);
	public void delete(EFmFmUserMasterPO eFmFmUserMasterPO);
	public EFmFmUserMasterPO getUserDetailByUserName(String userName);
	public List<EFmFmRoleMasterPO> getUserRoleByRoleId(int roleId);
	
	/**
	* Get the current Logged in user details
	* 
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-05-28 
	*/
	public List<EFmFmUserMasterPO> getLoggedInUserDetailFromClientIdAndUserId(
			EFmFmUserMasterPO eFmFmUserMaster);
	/*
	 * end Role master details 
	 */
	
	/**
	* Get all the user details of a particular client
	* 
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-05-28 
	*/
	public List<EFmFmUserMasterPO> getUsersFromClientId(EFmFmUserMasterPO userMasterPO);
	/*
	 * end Role master details 
	 */
	public List<EFmFmClientBranchPO> getClientDetails(int clientId);
	public void save(EFmFmClientBranchPO eFmFmClientMasterPO);
	public void update(EFmFmClientBranchPO eFmFmClientMasterPO);
	public List<EFmFmClientUserRolePO> getUserRoleByClientId(int clientId);
	public List<EFmFmRoleMasterPO> getRoleId(String roleName);
	public void save(EFmFmClientUserRolePO eFmFmClientUserRolePO);
	public void update(EFmFmClientUserRolePO eFmFmClientUserRolePO);
	public List<EFmFmClientUserRolePO> getUserRolesFromUserIdAndBranchId(int userId,
			int branchId);
	public List<EFmFmUserMasterPO> getAllUsersBelogsProject(int branchId, int projectId);
	public List<EFmFmUserMasterPO> getUsersRoleExist(int branchId,
			String clientProjectId, String role);
	public void deleteAnEmployeeFromData(int userId);
	public List<EFmFmUserMasterPO> getEmployeeUserDetailFromEmployeeId(int branchId,
			String employeeId);

}
