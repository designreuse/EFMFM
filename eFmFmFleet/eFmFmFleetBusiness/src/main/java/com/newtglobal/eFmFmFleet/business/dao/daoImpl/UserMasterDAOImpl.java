package com.newtglobal.eFmFmFleet.business.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newtglobal.eFmFmFleet.business.dao.IUserMasterDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientUserRolePO;
import com.newtglobal.eFmFmFleet.model.EFmFmRoleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.PersistentLoginPO;
@Repository("IUserMasterDAO")
public class UserMasterDAOImpl implements  IUserMasterDAO {	
   private EntityManager entityManager;
   
	@PersistenceContext
	public void setEntityManager(EntityManager _entityManager) {
	this.entityManager = _entityManager;
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmClientBranchPO eFmFmClientMasterPO) {
		// TODO Auto-generated method stub		
		entityManager.persist(eFmFmClientMasterPO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		
		entityManager.persist(eFmFmUserMasterPO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmClientBranchPO eFmFmClientMasterPO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmClientMasterPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmUserMasterPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		entityManager.remove(eFmFmUserMasterPO);
	}
	
	/*
	 * Client User Role
	 * 
	 */
	
	@Override
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public void save(EFmFmClientUserRolePO eFmFmClientUserRolePO) {  
	  entityManager.merge(eFmFmClientUserRolePO);
	 }
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmClientUserRolePO eFmFmClientUserRolePO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmClientUserRolePO);
	}
	
	
	@Override
	public void save(PersistentLoginPO persistentLoginPO) {
		// TODO Auto-generated method stub
		entityManager.persist(persistentLoginPO);
		
	}
	
	@Override
	public void update(PersistentLoginPO persistentLoginPO) {
		// TODO Auto-generated method stub
		entityManager.merge(persistentLoginPO);
	}
	@Override
	public void delete(PersistentLoginPO persistentLoginPO) {
		// TODO Auto-generated method stub
		entityManager.remove(persistentLoginPO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteAnEmployeeFromData(int userId) {
		Query query = entityManager.createQuery("DELETE EFmFmUserMasterPO where userId = '"+ userId+ "' ");					
		query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EFmFmUserMasterPO getUserDetailByUserName(String userName) {
		List <EFmFmUserMasterPO> userDetail = new ArrayList<EFmFmUserMasterPO>();
    	Query query=entityManager.createQuery(
		        "SELECT b FROM EFmFmUserMasterPO as b where b.userName='"+userName+"'");
    	userDetail=query.getResultList();
    	if(userDetail.isEmpty() || userDetail.size()==0)
    		return null;
       return userDetail.get(0);
		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String getUserNamebySeries(String series) {
		 //List<PersistentLoginPO>  userNameList = new ArrayList<PersistentLoginPO>();
		 
		 List<String> userNameList = new ArrayList<String>();
		Query query = entityManager.createNativeQuery("SELECT userName FROM persistent_logins WHERE series = ?1");
		query.setParameter(1, series);
			try {
				userNameList = query.getResultList();
				} catch (Exception e) {

			}
			if (userNameList.isEmpty()) {
				return null;
			}
		return userNameList.get(0);
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public PersistentLoginPO PersistentLoginPODettail(String series) {
		List <PersistentLoginPO> userDetail = new ArrayList<PersistentLoginPO>();
    	Query query=entityManager.createQuery(
		        "SELECT b FROM PersistentLoginPO as b where b.series='"+series+"'  ORDER BY b.lastUsed DESC");
    	userDetail=query.getResultList();
    	if(userDetail.isEmpty()|| userDetail.size()==0){
    		return null;
    	}
		return userDetail.get(0);
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updaetLastrequestTimebyuserName(String userName) {
		Query query = entityManager
				.createQuery("UPDATE PersistentLoginPO set lastUsed=NOW() WHERE userName = '" + userName	+ "'");
		query.executeUpdate();
		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int isAleradyLoggedin(String userName) {
		String s = entityManager
				.createQuery(
						"SELECT COUNT(p) FROM PersistentLoginPO as p WHERE p.userName = '"
								+ userName + "' ")
				.getSingleResult().toString();
		return Integer.valueOf(s);
		
		
	}
		
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updatePersistentPO(PersistentLoginPO persistentLoginPO) {
		entityManager.merge(persistentLoginPO);
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delteRecord(String ipAddress) {
		Query query = entityManager.createQuery("DELETE FROM PersistentLoginPO c WHERE c.ipAddress='"+ipAddress+"'");
		query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<PersistentLoginPO> getUserLoggedInDetail(String UserName) {
		List <PersistentLoginPO> userDetail = new ArrayList<PersistentLoginPO>();
    	Query query=entityManager.createQuery(
		        "SELECT b FROM PersistentLoginPO as b where b.userName='"+UserName+"'  ORDER BY b.lastUsed DESC");
    	userDetail=query.getResultList();
		return userDetail;
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public PersistentLoginPO getAllLoggedUser(
			PersistentLoginPO persistentLoginPO) {		
		List <PersistentLoginPO> userDetails = new ArrayList<PersistentLoginPO>();
    	Query query=entityManager.createQuery("SELECT b FROM PersistentLoginPO as b ORDER BY b.lastUsed DESC");
    	userDetails=query.getResultList();
		return userDetails.get(0);
	}
	
	@Override
	public void save(EFmFmRoleMasterPO eFmFmRoleMasterPO) {
		// TODO Auto-generated method stub
		entityManager.persist(eFmFmRoleMasterPO);
	}
	@Override
	public void update(EFmFmRoleMasterPO eFmFmRoleMasterPO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmRoleMasterPO);
	}
	@Override
	public void delete(EFmFmRoleMasterPO eFmFmRoleMasterPO) {
		// TODO Auto-generated method stub
		entityManager.remove(eFmFmRoleMasterPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmRoleMasterPO> getUserRoleByRoleId(int roleId) {
		 List <EFmFmRoleMasterPO> roleDetail = new ArrayList<EFmFmRoleMasterPO>();
	     Query query=entityManager.createQuery(
	          "SELECT r FROM EFmFmClientUserRolePO as r JOIN r.eFmFmUserMaster u where u.userId='"+roleId+"'");
	     roleDetail=query.getResultList();
		return roleDetail;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmClientUserRolePO> getUserRoleByClientId(int branchId) {
		 List <EFmFmClientUserRolePO> roleDetail = new ArrayList<EFmFmClientUserRolePO>();
	     Query query=entityManager.createQuery(
	          "SELECT r FROM EFmFmClientUserRolePO as r JOIN r.eFmFmClientBranchPO c where c.branchId='"+branchId+"'");
	     roleDetail=query.getResultList();
		return roleDetail;
	}
	
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmClientUserRolePO> getUserRolesFromUserIdAndBranchId(int userId,int branchId) {
		 List <EFmFmClientUserRolePO> roleDetail = new ArrayList<EFmFmClientUserRolePO>();
	     Query query=entityManager.createQuery(
	          "SELECT r FROM EFmFmClientUserRolePO as r JOIN r.efmFmUserMaster u  JOIN r.eFmFmClientBranchPO c where u.userId='"+userId+"' AND c.branchId='"+branchId+"'");
	     roleDetail=query.getResultList();
			return roleDetail;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getLoggedInUserDetailFromClientIdAndUserId(EFmFmUserMasterPO eFmFmUserMaster) {
		 List <EFmFmUserMasterPO> loginUserDetail = new ArrayList<EFmFmUserMasterPO>();
	     Query query=entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+eFmFmUserMaster.geteFmFmClientBranchPO().getBranchId()+"' AND b.status='Y' AND b.userId='"+eFmFmUserMaster.getUserId()+"'");	
	     loginUserDetail=query.getResultList();
	     return loginUserDetail;
    	
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getEmployeeUserDetailFromEmployeeId(int branchId,String employeeId) {
		 List <EFmFmUserMasterPO> loginUserDetail = new ArrayList<EFmFmUserMasterPO>();
	     Query query=entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.status='Y' AND b.employeeId='"+employeeId+"'");	
	     loginUserDetail=query.getResultList();
	     return loginUserDetail;
    	
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getUsersFromClientId(EFmFmUserMasterPO userMasterPO) {
		// TODO Auto-generated method stub
		List <EFmFmUserMasterPO> allUsersDetail = new ArrayList<EFmFmUserMasterPO>();
	     Query query=entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+userMasterPO.geteFmFmClientBranchPO().getBranchId()+"' AND b.status='Y' ");	
	     allUsersDetail=query.getResultList();
	     return allUsersDetail;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmClientBranchPO> getClientDetails(int branchId) {
		 List <EFmFmClientBranchPO> clientDetails = new ArrayList<EFmFmClientBranchPO>();
	     Query query=entityManager.createQuery("SELECT b FROM EFmFmClientBranchPO b   where b.branchId='"+branchId+"' AND b.status='Y' ");	
	     clientDetails=query.getResultList();
	     return clientDetails;   	
	}
	
	@Override
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmRoleMasterPO> getRoleId(String roleName) {
	  List <EFmFmRoleMasterPO> eFmFmRoleMasterPO = new ArrayList<EFmFmRoleMasterPO>();
	      Query query=entityManager.createQuery("SELECT b FROM EFmFmRoleMasterPO b  where UPPER(b.role)='"+roleName+"'"); 
	      eFmFmRoleMasterPO=query.getResultList();
	      return eFmFmRoleMasterPO;     
	 }
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getAllUsersBelogsProject(int branchId,int projectId) {
		 List <EFmFmUserMasterPO> loginUserDetail = new ArrayList<EFmFmUserMasterPO>();
	     Query query=entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c JOIN b.eFmFmClientProjectDetails p  where c.branchId='"+branchId+"' AND b.status='Y' AND p.projectId='"+projectId+"'");	
	     loginUserDetail=query.getResultList();
	     return loginUserDetail;   	
	}
	@Override
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmUserMasterPO> getUsersRoleExist(int branchId, String clientProjectId, String role) {
	   List <EFmFmUserMasterPO> loginUserDetail = new ArrayList<EFmFmUserMasterPO>();
	      Query query=entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c JOIN b.eFmFmClientProjectDetails p JOIN b.efmFmClientUserRoles r JOIN r.efmFmRoleMaster m where TRIM(UPPER(m.role))='"+role+"' AND c.branchId='"+branchId+"' AND b.status='Y' AND TRIM(UPPER(p.clientProjectId))='"+clientProjectId+"'"); 
	      loginUserDetail=query.getResultList();
	      return loginUserDetail;
	 }		
}
