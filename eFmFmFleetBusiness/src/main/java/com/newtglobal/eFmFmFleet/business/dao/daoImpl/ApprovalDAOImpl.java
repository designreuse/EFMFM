package com.newtglobal.eFmFmFleet.business.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newtglobal.eFmFmFleet.business.dao.IApprovalDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;

@Repository("IApprovalDAO")
public class ApprovalDAOImpl implements IApprovalDAO {

	private EntityManager entityManager;
	@PersistenceContext
	public void setEntityManager(EntityManager _entityManager) {
	this.entityManager = _entityManager;
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)	
	public void save(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		// TODO Auto-generated method stub
		entityManager.persist(eFmFmDriverMasterPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)	
	public void update(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmDriverMasterPO);	
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)	
	public void delete(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		// TODO Auto-generated method stub
		entityManager.remove(eFmFmDriverMasterPO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularDriver(int driverId) {
		Query query = entityManager.createQuery("DELETE EFmFmDriverMasterPO where driverId = '"+ driverId+ "' ");					
		query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularVehicle(int vehicleId) {
		Query query = entityManager.createQuery("DELETE EFmFmVehicleMasterPO where vehicleId = '"+ vehicleId+ "' ");					
		query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularVendor(int vendorId) {
		Query query = entityManager.createQuery("DELETE EFmFmVendorMasterPO where vendorId = '"+ vendorId+ "' ");					
		query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getAllUnapprovedDrivers(int branchId) {
		List<EFmFmDriverMasterPO> allUnApprovedDrivers  =new ArrayList<EFmFmDriverMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO  b JOIN b.efmFmVendorMaster  v JOIN v.eFmFmClientBranchPO c where  c.branchId='"+branchId+"' AND b.status='P' ");
		allUnApprovedDrivers=query.getResultList();
		return allUnApprovedDrivers;
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getAllApprovedDrivers(int branchId) {
		List<EFmFmDriverMasterPO> allApprovedDrivers  =new ArrayList<EFmFmDriverMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO  b JOIN b.efmFmVendorMaster  v JOIN v.eFmFmClientBranchPO c where  c.branchId='"+branchId+"' AND b.status='A' ");
    	allApprovedDrivers=query.getResultList();
		return allApprovedDrivers;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getParticularDriverDetailFromDeriverId(int driverId) {
		List<EFmFmDriverMasterPO> driverDetails  =new ArrayList<EFmFmDriverMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO  b  where  b.driverId='"+driverId+"'");
    	driverDetails=query.getResultList();
		return driverDetails;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getAllInActiveDrivers(int branchId) {
		List<EFmFmDriverMasterPO> allInactiveDrivers  =new ArrayList<EFmFmDriverMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO  b JOIN b.efmFmVendorMaster  v JOIN v.eFmFmClientBranchPO c where  c.branchId='"+branchId+"' AND b.status='R' ");
    	allInactiveDrivers=query.getResultList();
		return allInactiveDrivers;
	}
	@Override
	public List<EFmFmVehicleMasterPO> getAllUnapprovedVehicles(int branchId) {
		// TODO Auto-generated method stub
		List<EFmFmVehicleMasterPO> allUnApprovedVehicles  =new ArrayList<EFmFmVehicleMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO  b JOIN b.efmFmVendorMaster  v JOIN v.eFmFmClientBranchPO c where  c.branchId='"+branchId+"' AND b.status='P'");
		allUnApprovedVehicles=query.getResultList();
		return allUnApprovedVehicles;

	}
	@Override
	public List<EFmFmVehicleMasterPO> getAllApprovedVehicles(int branchId) {
		// TODO Auto-generated method stub
		List<EFmFmVehicleMasterPO> allApprovedVehicles  =new ArrayList<EFmFmVehicleMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO  b JOIN b.efmFmVendorMaster  v JOIN v.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.status!='P' ");
    	allApprovedVehicles=query.getResultList();
		return allApprovedVehicles;
		}
	@Override
	public List<EFmFmVehicleMasterPO> getAllInActiveVehicles(int branchId) {
		// TODO Auto-generated method stub
		
		List<EFmFmVehicleMasterPO> allUnApprovedDrivers  =new ArrayList<EFmFmVehicleMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO  b JOIN b.efmFmVendorMaster  v JOIN v.eFmFmClientBranchPO c where  c.branchId='"+branchId+"' AND b.status='R' ");
		allUnApprovedDrivers=query.getResultList();
		return allUnApprovedDrivers;
	}
	@Override
	public List<EFmFmVendorMasterPO> getAllUnapprovedVendors(int branchId) {
		// TODO Auto-generated method stub
		List<EFmFmVendorMasterPO> allUnApprovedVendors  =new ArrayList<EFmFmVendorMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmVendorMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.status='P'");
    	allUnApprovedVendors=query.getResultList();
		return allUnApprovedVendors;
	}
	@Override
	public List<EFmFmVendorMasterPO> getAllApprovedVendors(int branchId) {
		// TODO Auto-generated method stub
		List<EFmFmVendorMasterPO> allApprovedVendors  =new ArrayList<EFmFmVendorMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmVendorMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.status='A' ");
    	allApprovedVendors=query.getResultList();
		return allApprovedVendors;
		}
	@Override
	public List<EFmFmVendorMasterPO> getAllInActiveVendors(int branchId) {
		// TODO Auto-generated method stub
		List<EFmFmVendorMasterPO> allUnApprovedVendors  =new ArrayList<EFmFmVendorMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmVendorMasterPO b JOIN b.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.status='R' ");
    	allUnApprovedVendors=query.getResultList();
		return allUnApprovedVendors;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EFmFmDriverMasterPO getParticularDriverDetail(int driverId) {
		List<EFmFmDriverMasterPO> driverDetail  =new ArrayList<EFmFmDriverMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO as b  where b.driverId='"+driverId+"'");
    	driverDetail=query.getResultList();
		return driverDetail.get(0);
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveRouteTripDetails(EFmFmEmployeeTripDetailPO eFmFmEmployeeTripDetailPO) {
		entityManager.merge(eFmFmEmployeeTripDetailPO);
		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getParticularDriverDeviceDetails(String mobileNo,int branchId) {
		List<EFmFmDriverMasterPO> driverMasterPO =new ArrayList<EFmFmDriverMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.mobileNo = '" + mobileNo + "'");	
		driverMasterPO=query.getResultList();		
		return driverMasterPO;
	} 
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getParticularDriverDetailsFromDeviceToken(String deviceId,int branchId) {
		List<EFmFmDriverMasterPO> driverMasterPO =new ArrayList<EFmFmDriverMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.deviceId = '" + deviceId + "' ");	
		driverMasterPO=query.getResultList();		
		return driverMasterPO;
	} 
	
	/*@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getParticularDeviceDetailFromDeviceImeiNum(String deviceId,int branchId) {
		List<EFmFmDriverMasterPO> driverMasterPO =new ArrayList<EFmFmDriverMasterPO>();
		Query query = entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO b JOIN b.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.deviceId = '" + deviceId + "' ");	
		driverMasterPO=query.getResultList();		
		return driverMasterPO;
	} */
	
}
