package com.newtglobal.eFmFmFleet.business.dao.daoImpl;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newtglobal.eFmFmFleet.model.EFmFmActualRoutTravelledPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;

@SuppressWarnings("unchecked")
@Repository("IVehicleCheckInDAO")
public class SchedulerVehicleCheckInDAOImpl  {

	private EntityManager entityManager;
	
	public SchedulerVehicleCheckInDAOImpl(EntityManager entityManager){
		setEntityManager(entityManager);
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		entityManager.persist(eFmFmVehicleMasterPO);		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmEmployeeTripDetailPO eFmFmEmployeeTripDetailPO) {
		entityManager.persist(eFmFmEmployeeTripDetailPO);
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		entityManager.merge(eFmFmVehicleMasterPO);
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetails(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		List <EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd");		
		Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b where DATE(b.checkInTime)=TRIM('"+formatter.format(eFmFmVehicleCheckInPO.getCheckInTime())+"')) ");		
		vehicleCheckIn=query.getResultList();		
		return vehicleCheckIn;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleLessCapacity(int branchId,int capacity) {
	  List <EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where d.capacity<='"+capacity+"' and d.vehicleNumber like '%DUMMY%' and b.status='Y' and g.branchId='"+branchId+"' ");  
	  vehicleCheckIn=query.getResultList();  
	  return vehicleCheckIn;
	 
	 }
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleLargeCapacity(int branchId,int capacity) {
	  List <EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where d.capacity>='"+capacity+"'  and d.vehicleNumber like '%DUMMY%' and g.branchId='"+branchId+"' and b.status='Y'");  
	  vehicleCheckIn=query.getResultList();  
	  return vehicleCheckIn;
	 }
	
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleDetails(int branchId,Date todayDate) {
	  List <EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where g.branchId='"+branchId+"'  ");  
	  vehicleCheckIn=query.getResultList();  
	  return vehicleCheckIn;
	 }
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getVehicleDetailsFromClientIdForSmallerRoute(int branchId,String vehicleId) {		
			List <EFmFmVehicleMasterPO> vehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();		
			Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.status='A' AND b.vehicleId in ("+vehicleId+") ORDER BY capacity ASC");
			vehicleMasterPO=query.getResultList();		
			return vehicleMasterPO;
		}
		
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getVehicleDetailsFromClientIdForLargerRoute(int branchId,String vehicleId) {		
			List <EFmFmVehicleMasterPO> vehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();		
			Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.status='A' AND b.vehicleId in ("+vehicleId+") ORDER BY capacity DESC ");
			vehicleMasterPO=query.getResultList();		
			return vehicleMasterPO;
		}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getLessTravelledVehicle(int branchId,String vehicleId) {		
			List <EFmFmVehicleMasterPO> vehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();		
			Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.status='A' AND b.vehicleId in ("+vehicleId+") ORDER BY b.pendingKM DESC ");
			vehicleMasterPO=query.getResultList();		
			return vehicleMasterPO;
		}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeRequestMasterPO> getParticularEmployeeMasterRequestDetails(int branchId,int tripId) {
		List <EFmFmEmployeeRequestMasterPO> employeeRequest = new ArrayList<EFmFmEmployeeRequestMasterPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeRequestMasterPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO  c where b.tripId='"+tripId+"' AND  c.branchId='"+branchId+"' ");
		employeeRequest=query.getResultList();		
		return employeeRequest;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmAssignRoutePO> getParticularAssignRouteDetail(int branchId,int checkInId) {
	  List <EFmFmAssignRoutePO> assignVehicleDetail = new ArrayList<EFmFmAssignRoutePO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.efmFmVehicleCheckIn v  JOIN v.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vd JOIN vd.eFmFmClientBranchPO c where c.branchId ='"+branchId+"' and v.checkInId='"+checkInId+"' ");  
	  assignVehicleDetail=query.getResultList();  
	  return assignVehicleDetail;
	 }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmAssignRoutePO> getHalfCompletedAssignRouteFromCheckInId(int branchId,int zoneId,String reqType,Time shiftTime, int checkInId) {
	  List <EFmFmAssignRoutePO> assignVehicleDetail = new ArrayList<EFmFmAssignRoutePO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmRouteAreaMapping v  JOIN v.eFmFmZoneMaster vm JOIN b.eFmFmClientBranchPO c JOIN b.efmFmVehicleCheckIn vc where b.vehicleStatus='A' AND b.tripStatus='allocated' AND c.branchId ='"+branchId+"' AND vm.zoneId='"+zoneId+"' AND b.tripType='"+reqType+"' AND b.shiftTime='"+shiftTime+"'  AND vc.checkInId='"+checkInId+"'");  
	  assignVehicleDetail=query.getResultList();  
	  return assignVehicleDetail;
	 }	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmAssignRoutePO> getHalfCompletedAssignRoute(int branchId,int zoneId,String reqType,Time shiftTime) {
	  List <EFmFmAssignRoutePO> assignVehicleDetail = new ArrayList<EFmFmAssignRoutePO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmRouteAreaMapping v  JOIN v.eFmFmZoneMaster vm JOIN b.eFmFmClientBranchPO c JOIN b.efmFmVehicleCheckIn vc  JOIN vc.efmFmVehicleMaster vem where b.vehicleStatus='A' AND b.tripStatus='allocated' AND c.branchId ='"+branchId+"' AND vm.zoneId='"+zoneId+"' AND b.tripType='"+reqType+"' AND b.shiftTime='"+shiftTime+"'  ORDER BY vem.availableSeat DESC ");  
	  assignVehicleDetail=query.getResultList();  
	  return assignVehicleDetail;
	 }
	
	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmAssignRoutePO> getAssignRouteForBucketClose(int branchId,int zoneId,String reqType,Time shiftTime) {
	  List <EFmFmAssignRoutePO> assignVehicleDetail = new ArrayList<EFmFmAssignRoutePO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmRouteAreaMapping v  JOIN v.eFmFmZoneMaster vm JOIN b.eFmFmClientBranchPO c  where b.vehicleStatus='A' AND b.tripStatus='allocated' AND c.branchId ='"+branchId+"' AND vm.zoneId='"+zoneId+"' AND b.tripType='"+reqType+"' AND b.shiftTime='"+shiftTime+"'");  
	  assignVehicleDetail=query.getResultList();  
	  return assignVehicleDetail;
	 }	
	*/
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveRouteTripDetails(EFmFmEmployeeTripDetailPO eFmFmEmployeeTripDetailPO) {
		entityManager.merge(eFmFmEmployeeTripDetailPO);	
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public EFmFmVehicleMasterPO getVehicleDetail(int vehicleId) {
		List<EFmFmVehicleMasterPO> vehicleDetail  =new ArrayList<EFmFmVehicleMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO as b  where b.vehicleId='"+vehicleId+"'");
    	vehicleDetail=query.getResultList();
		return vehicleDetail.get(0);
	}

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getParticularTripAllEmployees(int assignRouteId) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.eFmFmEmployeeRequestMaster r JOIN b.efmFmAssignRoute d where d.assignRouteId='"+assignRouteId+"' ORDER BY r.pickUpTime ASC");		
		tripemployees=query.getResultList();				
		return tripemployees;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getDropTripAllSortedEmployees(int assignRouteId) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster u JOIN b.efmFmAssignRoute d where d.assignRouteId='"+assignRouteId+"' ORDER BY t.dropSequence ASC");		
		tripemployees=query.getResultList();				
		return tripemployees;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularRequest(int empTripId) {
		Query query = entityManager.createQuery("DELETE EFmFmEmployeeTripDetailPO where empTripId = '"+ empTripId+ "' ");					
		query.executeUpdate();
	}
	
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getRequestStatusFromBranchIdAndRequestId(int branchId,int requestId) {
		List <EFmFmEmployeeTripDetailPO> travelRequestDetails = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.eFmFmEmployeeRequestMaster r JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND t.requestId='"+requestId+"'");		
		travelRequestDetails=query.getResultList();				
		return travelRequestDetails;		
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getAllParticularRouteRequest(int branchId,int zoneId,Time shiftTime) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c JOIN u.eFmFmRouteAreaMapping v  JOIN v.eFmFmZoneMaster vm where c.branchId='"+branchId+"' AND  vm.zoneId="+zoneId+" AND b.isActive='Y' AND b.approveStatus='Y' AND b.readFlg='Y' AND b.shiftTime='"+shiftTime+"' AND (b.requestStatus!='C' OR b.requestStatus!='R')  ");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getAllAreaFromParticularZone(int branchId,int zoneId) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c JOIN u.eFmFmRouteAreaMapping v  JOIN v.eFmFmZoneMaster vm where c.branchId='"+branchId+"' AND  vm.zoneId="+zoneId+" AND b.isActive='Y' AND b.approveStatus='Y' AND b.readFlg='Y'");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	*/
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEscortCheckInPO> getAllCheckedInEscort(int branchId) {
		List <EFmFmEscortCheckInPO> escortMasterPO = new ArrayList<EFmFmEscortCheckInPO>();	
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEscortCheckInPO b JOIN b.eFmFmEscortMaster e JOIN e.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.status='Y' AND b.escortCheckOutTime is null AND c.branchId='"+branchId+"'");
		escortMasterPO=query.getResultList();		
		return escortMasterPO;
	}
	 
	  /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	  public List<EFmFmVehicleCheckInPO> getCheckedInVehicleAndDriver(int driverId,int deviceId,int vehicleId,int branchId) {
	     List <EFmFmVehicleCheckInPO> driverMasterPO = new ArrayList<EFmFmVehicleCheckInPO>();        
	        Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster v JOIN v.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmDriverMaster dr JOIN b.eFmFmDeviceMaster dm where v.vehicleId='"+vehicleId+"' AND dm.deviceId='"+deviceId+"' AND c.branchId='"+branchId+"'  AND c.driverId!='"+driverId+"'  AND b.checkOutTime is not null order by b.checkOutTime asc ");
	        driverMasterPO=query.getResultList();  
	        return driverMasterPO;
	  }*/
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	  public List<EFmFmVehicleCheckInPO> getCheckedInVehicleAndDriver(int driverId,int vehicleId,int branchId) {
	     List <EFmFmVehicleCheckInPO> driverMasterPO = new ArrayList<EFmFmVehicleCheckInPO>();        
	        Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster v JOIN v.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmDriverMaster dr where  dr.driverId!='"+driverId+"' AND v.vehicleId='"+vehicleId+"'  AND c.branchId='"+branchId+"' AND b.checkOutTime is not null order by b.checkOutTime desc ");
	        driverMasterPO=query.getResultList();  
	        return driverMasterPO;
	  }
	
	//Sending msg once device stop
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmActualRoutTravelledPO> getLastUpdatedValueFromDevice(int assignRouteId,int branchId) {
		List <EFmFmActualRoutTravelledPO> routedetails = new ArrayList<EFmFmActualRoutTravelledPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmActualRoutTravelledPO b JOIN b.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a  where c.branchId='"+branchId+"' AND a.assignRouteId='"+assignRouteId+"' ");
		routedetails=query.getResultList();				
		return routedetails;
	}
	
	
	
	
	
}
