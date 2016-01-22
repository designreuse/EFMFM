package com.newtglobal.eFmFmFleet.business.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;

@Repository("ICabRequestDAO")
public class SchedulerCabRequestDAOImpl {
	private EntityManager entityManager;
	
	public SchedulerCabRequestDAOImpl(EntityManager entityManager){
		setEntityManager(entityManager);
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {		
		entityManager.persist(eFmFmEmployeeTravelRequestPO);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmEmployeeRequestMasterPO employeeRequestMasterPO) {		
		entityManager.persist(employeeRequestMasterPO);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getParticularRequestDetail(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
        Query query = this.entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO  c where  r.tripId='" + eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getTripId() + "' AND c.branchId='" + eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId() + "' AND b.requestType='" + eFmFmEmployeeTravelRequestPO.getRequestType() + "' AND b.isActive='Y' AND b.readFlg='Y' ");
        employeeTravelRequestPO = query.getResultList();
        return employeeTravelRequestPO;
    }
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getParticularReadRequestDetail(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
        Query query = this.entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO  c where  r.tripId='" + eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getTripId() + "' AND c.branchId='" + eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId() + "' AND b.requestType='" + eFmFmEmployeeTravelRequestPO.getRequestType() + "' AND (b.readFlg='R' OR b.readFlg='N') ");
        employeeTravelRequestPO = query.getResultList();
        return employeeTravelRequestPO;
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getAllRequestDetailsFromRequestMaster(int branchId) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
        Query query = this.entityManager.createQuery("SELECT b FROM EFmFmEmployeeRequestMasterPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO  c where   c.branchId='" +branchId+ "' AND b.readFlg='Y' AND b.status='Y'  ");
        employeeTravelRequestPO = query.getResultList();
        return employeeTravelRequestPO;
    }
	
}
