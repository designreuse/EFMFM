package com.newtglobal.eFmFmFleet.business.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newtglobal.eFmFmFleet.business.dao.IEmployeeDetailDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientProjectDetailsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
@Repository("IEmployeeDetailDAO")
public class EmployeeDetailDAOImpl implements IEmployeeDetailDAO{
	
	private static Log log = LogFactory.getLog(EmployeeDetailDAOImpl.class);	
	private EntityManager entityManager;
	
		@PersistenceContext
		public void setEntityManager(EntityManager _entityManager) {
		this.entityManager = _entityManager;
		}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmUserMasterPO eFmFmUserMasterPO) {
		// TODO Auto-generated method stub
		entityManager.persist(eFmFmUserMasterPO);
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
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<EFmFmUserMasterPO>  getAllEmployeeDetailsFromClientId(int branchId){
    	List <EFmFmUserMasterPO> employeeDetails = new ArrayList<EFmFmUserMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.status='Y'");
    	log.info("Hibernate Cache This object");  
    	employeeDetails=query.getResultList();
       return employeeDetails;
    } 
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> loginEmployeeDetails(String employeeId,String password) {
		List<EFmFmUserMasterPO> eFmFmEmployeeMasterPO =new ArrayList<EFmFmUserMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b   where b.userName = '" + employeeId + "' AND b.password='"+password+"' AND b.status='Y' ");	
		eFmFmEmployeeMasterPO=query.getResultList();		
		return eFmFmEmployeeMasterPO;
	} 
	
	/**
	* The getParticularEmployeeDetails implements for
	* getting particular employees details.
	*
	* @author  Rajan R
	* 
	* @since   2015-05-05 
	*/
	
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getParticularEmpDetailsFromUserId(int userId,int branchId) {
		List<EFmFmUserMasterPO> eFmFmEmployeeMasterPO =new ArrayList<EFmFmUserMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.userId = '" + userId + "' AND b.status='Y' ");	
		eFmFmEmployeeMasterPO=query.getResultList();		
		return eFmFmEmployeeMasterPO;
	} 
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getParticularEmpDetailsFromEmployeeId(String employeeId,int branchId) {
		List<EFmFmUserMasterPO> eFmFmEmployeeMasterPO =new ArrayList<EFmFmUserMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.employeeId = '" + employeeId + "' AND b.status='Y' ");	
		eFmFmEmployeeMasterPO=query.getResultList();		
		return eFmFmEmployeeMasterPO;
	} 
	
	
	//For Guest EmployeeId Check.
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getParticularEmpDetailsFromEmployeeIdForGuest(String employeeId,int branchId) {
		List<EFmFmUserMasterPO> eFmFmEmployeeMasterPO =new ArrayList<EFmFmUserMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.employeeId = '" + employeeId + "' AND b.userType='employee' AND b.status='Y' ");	
		eFmFmEmployeeMasterPO=query.getResultList();		
		return eFmFmEmployeeMasterPO;
	} 
	
	//For Guest MobileNumber Check.
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getEmpMobileNumberCheck(String mobileNumber, int branchId) {
		List<EFmFmUserMasterPO> eFmFmEmployeeMasterPO =new ArrayList<EFmFmUserMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.mobileNumber = '" + mobileNumber + "' AND b.userType='employee' AND b.status='Y' ");	
		eFmFmEmployeeMasterPO=query.getResultList();		
		return eFmFmEmployeeMasterPO;
	}
	
	//For Guest EmployeeId Check Exist Check.
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getParticularGuestDetailsFromEmployeeId(String employeeId,int branchId) {
	       List<EFmFmUserMasterPO> eFmFmEmployeeMasterPO =new ArrayList<EFmFmUserMasterPO>();
		   Query query = entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.employeeId = '" + employeeId + "' AND b.userType='guest' AND b.status='Y' ");	
		 eFmFmEmployeeMasterPO=query.getResultList();		
		 return eFmFmEmployeeMasterPO;
		} 


		
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getParticularEmployeeDetailsFromEmailId(String emailId,int branchId) {
		List<EFmFmUserMasterPO> eFmFmEmployeeMasterPO =new ArrayList<EFmFmUserMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.emailId = '" + emailId + "' AND b.status='Y' ");	
		eFmFmEmployeeMasterPO=query.getResultList();		
		return eFmFmEmployeeMasterPO;
	} 
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EFmFmUserMasterPO getParticularDeviceDetails(String deviceId) {
		List<EFmFmUserMasterPO> eFmFmEmployeeMasterPO =null;
		Query query = entityManager.createQuery("SELECT s FROM EFmFmUserMasterPO as s WHERE s.deviceId = '" + deviceId + "' ");		
		try{
			eFmFmEmployeeMasterPO=query.getResultList();
		} catch(Exception e){
		}
		if(eFmFmEmployeeMasterPO == null || eFmFmEmployeeMasterPO.isEmpty()){
			return null;
		}
		return eFmFmEmployeeMasterPO.get(0);
	} 
	
	
	@Override
	public boolean doesDeviceExist(String deviceId,int branchId) {
		List<EFmFmUserMasterPO> deviceCheck = new ArrayList<EFmFmUserMasterPO>();
    	Query query=entityManager.createQuery("SELECT s.deviceId FROM EFmFmUserMasterPO  s JOIN s.eFmFmClientBranchPO c WHERE s.deviceId = '" + deviceId + "' AND c.branchId='"+branchId+"'");
    	deviceCheck=query.getResultList();
    	if(deviceCheck.size()>0){
    		return true;
    	}
    		return false ;
	}
	
	@Override
	public boolean doesEmailIdExist(String emailId,int branchId) {
		List<EFmFmUserMasterPO> emailCheck = new ArrayList<EFmFmUserMasterPO>();
    	Query query=entityManager.createQuery("SELECT s.emailId FROM EFmFmUserMasterPO  s JOIN s.eFmFmClientBranchPO c WHERE s.emailId = '" + emailId + "' AND c.branchId='"+branchId+"'");
    	emailCheck=query.getResultList();
    	if(emailCheck.size()>0){
    		return true;
    	}
    		return false ;
	}
	
		
	
	@Override
	public List<EFmFmClientBranchPO> doesClientCodeExist(String branchCode) {
		List<EFmFmClientBranchPO> branchCodeCheck = new ArrayList<EFmFmClientBranchPO>();
    	Query query=entityManager.createQuery("SELECT s FROM EFmFmClientBranchPO  s WHERE s.branchCode='"+branchCode+"'");
    	branchCodeCheck=query.getResultList();
    	return branchCodeCheck ;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmClientProjectDetailsPO eFmFmClientProjectDetailsPO) {		
		entityManager.persist(eFmFmClientProjectDetailsPO);
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmClientProjectDetailsPO> getProjectDetails(String projectId, int branchId) {
		List<EFmFmClientProjectDetailsPO> eFmFmClientProjectDetailsPO =new ArrayList<EFmFmClientProjectDetailsPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmClientProjectDetailsPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND UPPER(b.clientProjectId) = '" + projectId + "'");	
		eFmFmClientProjectDetailsPO=query.getResultList();		
		return eFmFmClientProjectDetailsPO;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmUserMasterPO> getEmpMobileNoDetails(String mobileNo, int branchId) {
		List<EFmFmUserMasterPO> eFmFmEmployeeMasterPO =new ArrayList<EFmFmUserMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmUserMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.mobileNumber = '" + mobileNo + "' AND b.status='Y' ");	
		eFmFmEmployeeMasterPO=query.getResultList();		
		return eFmFmEmployeeMasterPO;
	}
	

}
