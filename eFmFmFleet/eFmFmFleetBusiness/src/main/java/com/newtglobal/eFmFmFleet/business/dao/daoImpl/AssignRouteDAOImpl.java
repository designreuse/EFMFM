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
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newtglobal.eFmFmFleet.business.dao.IAssignRouteDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmActualRoutTravelledPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;

@Repository("IAssignRouteDAO")
public class AssignRouteDAOImpl implements IAssignRouteDAO {
	
	private EntityManager entityManager;
	@PersistenceContext
	public void setEntityManager(EntityManager _entityManager) {
	this.entityManager = _entityManager;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)	
	public void update(EFmFmAssignRoutePO eFmFmAssignRoutePO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmAssignRoutePO);	
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)	
	public void save(EFmFmAssignRoutePO eFmFmAssignRoutePO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmAssignRoutePO);	
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)	
	public void save(EFmFmActualRoutTravelledPO actualRoutTravelledPO) {
		// TODO Auto-generated method stub
		entityManager.persist(actualRoutTravelledPO);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)	
	public void update(EFmFmActualRoutTravelledPO actualRoutTravelledPO) {
		// TODO Auto-generated method stub
		entityManager.merge(actualRoutTravelledPO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularAssignRoute(int assignRouteId) {
		Query query = entityManager.createQuery("DELETE EFmFmAssignRoutePO where assignRouteId = '"+ assignRouteId+ "' ");					
		query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularActualTravelled(int travelId) {
		Query query = entityManager.createQuery("DELETE EFmFmActualRoutTravelledPO where travelId = '"+ travelId+ "' ");					
		query.executeUpdate();
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTodaysTrips(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
	/*	String todayDate;
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		todayDate = formatter.format(assignRoutePO.getTripAssignDate());*/
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c  JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z where c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"'AND b.tripStatus !='completed' ORDER BY z.zoneName ASC");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> closeParticularTrips(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where  c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"' AND b.assignRouteId='"+assignRoutePO.getAssignRouteId()+"'");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllLiveTrips(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.tripStatus='Started' AND b.tripStartTime!=NULL AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"'");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllRoutesOfParticularZone(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z where b.tripStatus !='completed'  AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"' AND z.zoneId='"+assignRoutePO.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId()+"' ");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllRoutesInsideZone(int branchId,int zoneId) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z where b.tripStatus !='completed'  AND c.branchId='"+branchId+"' AND z.zoneId='"+zoneId+"' ORDER BY z.zoneName ASC");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllOnlyAssignedTrips(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a  where b.tripStatus !='completed' AND b.vehicleStatus!='F'  AND b.tripType='"+assignRoutePO.getTripType()+"' AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"' AND a.routeAreaId='"+assignRoutePO.geteFmFmRouteAreaMapping().getRouteAreaId()+"' ");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmActualRoutTravelledPO> getEtaAndDistanceFromAssignRouteId(EFmFmActualRoutTravelledPO actualRouteTravelledPO) {
		List <EFmFmActualRoutTravelledPO> routedetails = new ArrayList<EFmFmActualRoutTravelledPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmActualRoutTravelledPO b JOIN b.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a  where c.branchId='"+actualRouteTravelledPO.geteFmFmClientBranchPO().getBranchId()+"' AND a.assignRouteId='"+actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId()+"' ");
		routedetails=query.getResultList();				
		return routedetails;
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getTripCountByDate(Date fromDate,Date toDate,String tripType,int branchId) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();		
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		formatter.format(fromDate);
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.tripType='"+tripType+"' AND DATE(b.tripAssignDate)>=TRIM('"+formatter.format(fromDate)+"') AND DATE(b.tripAssignDate)<=TRIM('"+formatter.format(toDate)+"') AND b.tripStatus ='completed' AND c.branchId='"+branchId+"' group by b.tripAssignDate");
		allTrips=query.getResultList();				
		return allTrips;
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTodaysCompletedTrips(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.tripType='"+assignRoutePO.getTripType()+"'  AND b.tripStatus ='completed' AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"'");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllRoutesBasedOnTripTypeAndShiftTime(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z where b.tripType='"+assignRoutePO.getTripType()+"'  AND b.shiftTime='"+assignRoutePO.getShiftTime()+"' AND b.tripStatus !='completed' AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"' ORDER BY z.zoneName ASC");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllStartedRoutes(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z where b.tripStatus='Started' AND b.tripStartTime!=NULL AND b.tripType='"+assignRoutePO.getTripType()+"' AND b.shiftTime='"+assignRoutePO.getShiftTime()+"' AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"' ORDER BY z.zoneName ASC");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllBucketClosedRoutes(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z where b.bucketStatus='Y' AND b.tripType='"+assignRoutePO.getTripType()+"' AND b.shiftTime='"+assignRoutePO.getShiftTime()+"'AND b.tripStatus!='completed' AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"' ORDER BY z.zoneName ASC ");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllOpenBucketRoutes(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z where b.bucketStatus='N' AND b.tripType='"+assignRoutePO.getTripType()+"' AND b.shiftTime='"+assignRoutePO.getShiftTime()+"'AND b.tripStatus!='completed' AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"' ORDER BY z.zoneName ASC");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllClosedRoutes(String tripType,Time shiftTime,int branchId) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.bucketStatus='Y' AND b.tripType='"+tripType+"' AND b.shiftTime='"+shiftTime+"'AND b.tripStatus!='completed' AND c.branchId='"+branchId+"' ");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTripDetails(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
	/*	String todayDate;
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		todayDate = formatter.format(assignRoutePO.getTripAssignDate());*/
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.tripStatus ='completed' AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"'");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTripByDate(Date fromDate, Date toDate,int branchId) {
		String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"'  ORDER BY tripAssignDate ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTripByDateByDeriverId(Date fromDate, Date toDate,int branchId,int driverId) {
		String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmDriverMaster dm WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND dm.driverId='"+driverId+"' ORDER BY tripAssignDate ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByDate(Date fromDate, Date toDate,int branchId) {
		String query = "SELECT t FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE t.boardedFlg ='NO' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"'  ORDER BY actualTime ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getAllMessageEmployeesMessageDetailsByDate(Date fromDate, Date toDate,int branchId) {
		String query = "SELECT t FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE date(t.actualTime) >= ?1  AND  date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' ORDER BY actualTime ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTripsTravelledAndPlannedDistanceByDateAndVehicle(Date fromDate, Date toDate,int branchId,int vehicleId) {
		String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster d JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND d.vehicleId='"+vehicleId+"' ORDER BY tripAssignDate ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTripsTravelledAndPlannedDistanceByDate(Date fromDate, Date toDate,int branchId,int vendorId) {
		String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND f.vendorId="+vendorId+"  ORDER BY tripAssignDate ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTripsTravelledAndPlannedDistanceByAllVendor(Date fromDate, Date toDate,int branchId) {
		
		String query = "SELECT t  FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c  JOIN t.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f  WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"'  ORDER BY tripAssignDate,f.vendorName ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTripsVehicleDetailsByVehicleNumber(Date fromDate, Date toDate,int branchId,String vehicleNumber) {
		String query = "SELECT t  FROM EFmFmAssignRoutePO t JOIN t.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster d JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND d.vehicleNumber='"+vehicleNumber+"' ORDER BY tripAssignDate ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}
	//Getting KM shift wice report
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllTripsVehicleKMDetailsByShiftTime(Date fromDate, Date toDate,int branchId,Time shiftTime) {
		String query = "SELECT t  FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.shiftTime='"+shiftTime+"' ORDER BY tripAssignDate ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String getVendorNameTravelledAndPlannedDistanceByAllVendor(Date fromDate, Date toDate,int branchId) {
		String query = "SELECT t.distinct(vendorId,vendorName) FROM EFmFmAssignRoutePO t JOIN t.efmFmVehicleCheckIn v JOIN v.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"'  ORDER BY tripAssignDate ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList().toString();
	}

	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllActiveTrips(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.tripStatus !='completed'  AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"'");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllRoutesOfParticularBranch(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where  c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"'");
		allTrips=query.getResultList();				
		return allTrips;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmActualRoutTravelledPO> getCabLocationFromAssignRouteId(EFmFmActualRoutTravelledPO actualRouteTravelledPO) {
		List <EFmFmActualRoutTravelledPO> routedetails = new ArrayList<EFmFmActualRoutTravelledPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmActualRoutTravelledPO b JOIN b.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a  where c.branchId='"+actualRouteTravelledPO.geteFmFmClientBranchPO().getBranchId()+"' AND a.assignRouteId='"+actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId()+"' AND b.currentCabLocation is not null");
		routedetails=query.getResultList();				
		return routedetails;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmActualRoutTravelledPO> getLastEtaFromAssignRouteId(EFmFmActualRoutTravelledPO actualRouteTravelledPO) {
		List <EFmFmActualRoutTravelledPO> routedetails = new ArrayList<EFmFmActualRoutTravelledPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmActualRoutTravelledPO b JOIN b.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a  where c.branchId='"+actualRouteTravelledPO.geteFmFmClientBranchPO().getBranchId()+"' AND a.assignRouteId='"+actualRouteTravelledPO.getEfmFmAssignRoute().getAssignRouteId()+"' AND b.currentEta is not null");
		routedetails=query.getResultList();				
		return routedetails;
	}
	
	
	@Override
	  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	  public long getPickupVehiclesOnRoadCounter(int branchId) {
		Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.tripStatus='Started' AND b.tripStartTime!=NULL AND b.tripType='PICKUP' AND c.branchId='"+branchId+"'");
		    long pickupVehicleOnRoad=(long) query.getSingleResult();
			return pickupVehicleOnRoad;				
		}
	
	@Override
	  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	  public long getDropVehiclesOnRoadCounter(int branchId) {
		Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.tripStatus='Started' AND b.tripStartTime!=NULL AND b.tripType='DROP' AND c.branchId='"+branchId+"'");	
		    long pickupVehicleOnRoad=(long) query.getSingleResult();
			return pickupVehicleOnRoad;				
		}
	
	@Override
	  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	  public long getAllVehiclesOnRoadCounter(int branchId) {
		Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.tripStatus='Started' AND b.tripStartTime!=NULL AND c.branchId='"+branchId+"'");	
		    long pickupVehicleOnRoad=(long) query.getSingleResult();
			return pickupVehicleOnRoad;				
		}
	
	//total trip distinct date counts
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<Date> getAllTripsDistinctDates(Date fromDate, Date toDate,int branchId,String tripType) {
			String query = "SELECT Distinct DATE(t.tripAssignDate) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' ORDER BY tripAssignDate ASC";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			List<Date> tripDates=(List<Date>) q.getResultList();
			return tripDates;
		}
		
		//total trip counts shift wise date counts
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<Time> getAllTripsByShift(Date fromDate, Date toDate,int branchId,String tripType,Time shiftTime) {
			String query = "SELECT Distinct Time(t.shiftTime) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND t.shiftTime='"+shiftTime+"' ORDER BY tripAssignDate ASC";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			List<Time> tripDates=(List<Time>) q.getResultList();
			return tripDates;
		
		}
		
		
		//total trip counts by shift wise date counts
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<Time> getAllTripsByAllShifts(Date fromDate, Date toDate,int branchId,String tripType) {
		       String query = "SELECT Distinct Time(t.shiftTime) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"'  ORDER BY tripAssignDate ASC";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				List<Time> tripDates=(List<Time>) q.getResultList();
				return tripDates;
				
				}
		
		
		//total trip counts by shift wise on the bais of vendorid
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<Time> getAllTripsByAllShiftsForVendor(Date fromDate, Date toDate,int branchId,String tripType,int vendorId) {
		       String query = "SELECT Distinct Time(t.shiftTime) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND vem.vendorId='"+vendorId+"' ORDER BY tripAssignDate ASC";
		    	Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				List<Time> tripDates=(List<Time>) q.getResultList();
				return tripDates;						
		}
				
		//total trip counts shift wise plus vendorId date counts
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<Date> getAllTripsByShiftByVendor(Date fromDate, Date toDate,int branchId,String tripType,Time shiftTime,int vendorId) {
		       String query = "SELECT Distinct DATE(t.tripAssignDate) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND t.shiftTime='"+shiftTime+"' AND vem.vendorId='"+vendorId+"' ORDER BY tripAssignDate ASC";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				List<Date> tripDates=(List<Date>) q.getResultList();
				return tripDates;				
		}
		
		//total trip counts by vendorId date counts
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<Date> getAllTripsByByVendorId(Date fromDate, Date toDate,int branchId,String tripType,int vendorId) {
		       String query = "SELECT Distinct DATE(t.tripAssignDate) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND vem.vendorId='"+vendorId+"' ORDER BY tripAssignDate ASC";
		    	Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				List<Date> tripDates=(List<Date>) q.getResultList();
				return tripDates;							
		}
		
		//total trip counts by vendorId date counts
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getUsedFleetByByVendorId(Date fromDate, Date toDate,int branchId,String tripType,int vendorId) {
		      String query = "SELECT count(t) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND vem.vendorId='"+vendorId+"' ORDER BY tripAssignDate ASC";
		    	Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				long tripDates=(long) q.getSingleResult();
				return tripDates;									
		}
		
		
		//total trip counts by vendorId date counts and shift wise
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getUsedFleetByByVendorIdByShift(Date fromDate, Date toDate,int branchId,String tripType,int vendorId,Time shiftTime) {
				      String query = "SELECT count(t) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND vem.vendorId='"+vendorId+"' AND t.shiftTime='"+shiftTime+"' ORDER BY tripAssignDate ASC";
				    	Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
						long tripDates=(long) q.getSingleResult();
						return tripDates;									
				}
		
		
		
		
		//total trip count by date
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllTripsCountByDate(Date fromDate, Date toDate,int branchId,String tripType) {
			String query = "SELECT count(t) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' ORDER BY tripAssignDate ASC";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long tripDates=(long) q.getSingleResult();
			return tripDates;
		}
		
		
		//total Delaytrips count by date	
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllDelayTripsCountByDate(Date fromDate, Date toDate,int branchId,String tripType) {
			String query = "SELECT count(t) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' ORDER BY tripAssignDate ASC";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long tripDates=(long) q.getSingleResult();
			return tripDates;
			}
		
		//total Delaytrips Beyond Login time count by date	
			@Override
			@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
			public long getAllDelayTripsBeyondLoginTimeCountByDate(Date fromDate, Date toDate,int branchId,String tripType) {
				String query = "SELECT count(t) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' ORDER BY tripAssignDate ASC";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				long tripDates=(long) q.getSingleResult();
				return tripDates;
				}
		
		//total Employees traveled count by date		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllEmployeesCountByDate(Date fromDate, Date toDate,int branchId,String tripType) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE a.tripStatus ='completed' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"'";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		//total Employees traveled count by date By vendorId		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllEmployeesCountByDateByVendorId(Date fromDate, Date toDate,int branchId,String tripType,int vendorId) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c JOIN a.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE a.tripStatus ='completed'  AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND vem.vendorId='"+vendorId+"'";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		
		//total No Show Employees count by date		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getNoShowEmployeesCountByDate(Date fromDate, Date toDate,int branchId,String tripType) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE a.tripStatus ='completed' AND t.boardedFlg ='NO' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"'";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		
		
		//total No Show Employees count by date by vendor		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getNoShowEmployeesCountByDateByVendor(Date fromDate, Date toDate,int branchId,String tripType,int vendorId) {			
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c JOIN a.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE a.tripStatus ='completed'  AND t.boardedFlg ='NO' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND vem.vendorId='"+vendorId+"'";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		
		
		//total PickedUp Employees traveled count by date		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getPickedUpEmployeesCountByDate(Date fromDate, Date toDate,int branchId,String tripType) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE a.tripStatus ='completed' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND (t.boardedFlg ='B' OR  t.boardedFlg ='D')";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		
		//total PickedUp Employees traveled count by date		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getPickedUpOrDroppedEmployeesCountByDate(Date fromDate, Date toDate,int branchId,String tripType) {
				String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE  date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripStatus ='completed' AND a.tripType='"+tripType+"' AND (t.boardedFlg ='B' OR  t.boardedFlg ='D')";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
	     		long empCountByTripDate=(long) q.getSingleResult();
				return empCountByTripDate;		
		}
		
		
		//total PickedUp Employees traveled count by date by vendor		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getPickedUpEmployeesCountByDateByVendor(Date fromDate, Date toDate,int branchId,String tripType,int vendorId) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c JOIN a.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE  a.tripStatus ='completed'  AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND vem.vendorId='"+vendorId+"' AND (t.boardedFlg ='B' OR  t.boardedFlg ='D')";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		
		}
		
		

		//total Delaytrips count by date	
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmAssignRoutePO> getAllTripsByDate(Date fromDate, Date toDate,int branchId,String tripType) {
			String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' ORDER BY tripAssignDate ASC";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			return q.getResultList();
		}

		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllTripsCountByShift(Date fromDate, Date toDate,
				int branchId, String tripType, Time shiftTime) {
			String query = "SELECT count(t) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND t.shiftTime='"+shiftTime+"' ORDER BY tripAssignDate ASC";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long tripDates=(long) q.getSingleResult();
			return tripDates;
		}

		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmAssignRoutePO> getAllTripsDetailsByShift(
				Date fromDate, Date toDate, int branchId, String tripType,
				Time shiftTime) {
			String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND t.shiftTime='"+shiftTime+"' ORDER BY tripAssignDate ASC";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			return q.getResultList();
		}
		
		//Vendorwise trip details
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmAssignRoutePO> getAllTripsDetailsByShiftByVendor(
				Date fromDate, Date toDate, int branchId, String tripType,
				Time shiftTime,int vendorId) {
			String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND t.shiftTime='"+shiftTime+"' AND vem.vendorId='"+vendorId+"' ORDER BY tripAssignDate ASC";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			return q.getResultList();
		}
		
		
		//Vendorwise details for date wice.
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmAssignRoutePO> getAllTripsDetailsByVendorWiseOnly(
		    	Date fromDate, Date toDate, int branchId, String tripType,
				int vendorId) {
				String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND vem.vendorId='"+vendorId+"' ORDER BY tripAssignDate ASC";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				return q.getResultList();
				}
		

		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllEmployeesCountByShift(Date fromDate, Date toDate,
				int branchId, String tripType, Time shiftTime) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE a.tripStatus ='completed'  AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND a.shiftTime='"+shiftTime+"'";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllEmployeesCountByShiftByVendorId(Date fromDate, Date toDate,
				int branchId, String tripType, Time shiftTime,int vendorId) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c JOIN a.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE a.tripStatus ='completed' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND a.shiftTime='"+shiftTime+"' AND vem.vendorId='"+vendorId+"'";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		

		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getNoShowEmployeesCountByShift(Date fromDate, Date toDate,
				int branchId, String tripType, Time shiftTime) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE a.tripStatus ='completed'  AND t.boardedFlg ='NO' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND a.shiftTime='"+shiftTime+"'";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		//No show EmployeeCount By Shift wise and Vendor wise		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getNoShowEmployeesCountByShiftByVendor(Date fromDate, Date toDate,
				int branchId, String tripType, Time shiftTime,int vendorId) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c JOIN a.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE a.tripStatus ='completed' AND t.boardedFlg ='NO' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND a.shiftTime='"+shiftTime+"' AND vem.vendorId='"+vendorId+"'";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		

		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getPickedUpEmployeesCountByShift(Date fromDate,
				Date toDate, int branchId, String tripType, Time shiftTime) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE a.tripStatus ='completed'  AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND a.shiftTime='"+shiftTime+"' AND (t.boardedFlg ='B' OR  t.boardedFlg ='D')";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		//Total dropped and picked up employees by shift and by vendor
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getPickedUpEmployeesCountByShiftByVendor(Date fromDate,
				Date toDate, int branchId, String tripType, Time shiftTime,int vendorId) {
			String query = "SELECT count(t) FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c JOIN a.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE  a.tripStatus ='completed' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' AND a.tripType='"+tripType+"' AND a.shiftTime='"+shiftTime+"' AND vem.vendorId='"+vendorId+"' AND (t.boardedFlg ='B' OR  t.boardedFlg ='D')";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			long empCountByTripDate=(long) q.getSingleResult();
			return empCountByTripDate;
		}
		
		
		
		
		
		//total trip counts shift wise plus vendorId date counts
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<Time> getAllTripsByShiftByVendorId(Date fromDate, Date toDate,int branchId,String tripType,int vendorId,Time shiftTime) {
		       String query = "SELECT Distinct Time(t.shiftTime) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmVehicleMaster vm JOIN vm.efmFmVendorMaster vem WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' AND t.tripType='"+tripType+"' AND t.shiftTime='"+shiftTime+"' AND vem.vendorId='"+vendorId+"' ORDER BY tripAssignDate ASC";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				List<Time> tripDates=(List<Time>) q.getResultList();
				return tripDates;						
		}
		
		//No show Reports employeeId and employee name wise
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByIdAndName(Date fromDate, Date toDate,int branchId,String tripType,Time shiftTime) {
			String query = "SELECT t FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE a.tripStatus ='completed'  AND t.boardedFlg ='NO' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' and a.tripType='"+tripType+"' AND a.shiftTime='"+shiftTime+"' ORDER BY actualTime ASC";
			Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
			return q.getResultList();
		}
		
		//No show Reports by Date wice and employee name wise
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByDateWise(Date fromDate, Date toDate,int branchId,String tripType) {
				String query = "SELECT t FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c WHERE a.tripStatus ='completed'  AND t.boardedFlg ='NO' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' and a.tripType='"+tripType+"' ORDER BY actualTime ASC";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				return q.getResultList();
				}
		
		
		//No show Reports employeeId and employee name wise from EmployeeId
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByEmployeeId(Date fromDate, Date toDate,int branchId,String tripType,Time shiftTime,String employeeId) {
		       String query = "SELECT t FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c JOIN t.eFmFmEmployeeTravelRequest tr JOIN tr.efmFmUserMaster u WHERE a.tripStatus ='completed'  AND t.boardedFlg ='NO' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' and a.tripType='"+tripType+"' AND a.shiftTime='"+shiftTime+"' AND u.employeeId='"+employeeId+"' ORDER BY actualTime ASC";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				return q.getResultList();				
		}
		
		//No show Reports employeeId and employee name wise from EmployeeId
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByEmployeeIdDateWise(Date fromDate, Date toDate,int branchId,String tripType,String employeeId) {
		       String query = "SELECT t FROM EFmFmEmployeeTripDetailPO t JOIN t.efmFmAssignRoute a JOIN a.eFmFmClientBranchPO c JOIN t.eFmFmEmployeeTravelRequest tr JOIN tr.efmFmUserMaster u WHERE a.tripStatus ='completed'  AND t.boardedFlg ='NO' AND date(t.actualTime) >= ?1  AND   date(t.actualTime) <=?2  AND c.branchId='"+branchId+"' and a.tripType='"+tripType+"' AND u.employeeId='"+employeeId+"' ORDER BY actualTime ASC";
		    	Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				return q.getResultList();								
		}
		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmAssignRoutePO> getTodayTripByShift(Date fromDate,Date toDate,String tripType,String ShifTime,int branchId) {
			List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();		
			Format formatter;		
			formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			formatter.format(fromDate);
			Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.efmFmEmployeeTripDetails t JOIN t.eFmFmEmployeeTravelRequest r JOIN b.eFmFmClientBranchPO c where b.shiftTime='"+ShifTime+"' AND b.tripType='"+tripType+"' AND DATE(r.requestDate)>=TRIM('"+formatter.format(fromDate)+"') AND DATE(r.requestDate)<=TRIM('"+formatter.format(toDate)+"') AND b.tripStatus ='allocated' AND c.branchId='"+branchId+"'");			
			allTrips=query.getResultList();				
			return allTrips;
		}
		
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<EFmFmAssignRoutePO> getExportTodayTrips(Date fromDate,Date toDate,String tripType,String ShifTime,int branchId) {
			List <EFmFmAssignRoutePO> allTrips = new ArrayList<EFmFmAssignRoutePO>();		
			Format formatter;		
			formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			formatter.format(fromDate);
			Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where b.shiftTime='"+ShifTime+"' AND b.tripType='"+tripType+"' AND DATE(b.tripAssignDate)>=TRIM('"+formatter.format(fromDate)+"') AND DATE(b.tripAssignDate)<=TRIM('"+formatter.format(toDate)+"') AND b.tripStatus ='allocated' AND c.branchId='"+branchId+"'");			
			allTrips=query.getResultList();				
			return allTrips;
		}
		
//total trip distinct date counts
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<Date> getAllTripsByDistinctDates(Date fromDate, Date toDate,int branchId) {
		    	String query = "SELECT Distinct DATE(t.tripAssignDate) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"'  ORDER BY tripAssignDate ASC";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				List<Date> tripDates=(List<Date>) q.getResultList();
				return tripDates;	
		}		
		
		//total trip distinct date counts
		@Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public List<ArrayList> getAllTripsByDistinctDatesAndDeriverId(Date fromDate, Date toDate,int branchId) {
		    	String query = "SELECT Distinct (t.tripAssignDate,dm.driverId) FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmDriverMaster dm WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"'  ORDER BY tripAssignDate ASC";
				Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
				List<ArrayList> tripDates=(List<ArrayList>) q.getResultList();
				return tripDates;			
		}
		
		//total trip distinct date counts
				@Override
				@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
				public List<EFmFmAssignRoutePO> getAllTripsByDatesAndDriverId(Date fromDate, Date toDate,int branchId,int driverId) {
				    	String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c JOIN t.efmFmVehicleCheckIn ch JOIN ch.efmFmDriverMaster dm WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND c.branchId='"+branchId+"' And dm.driverId='"+driverId+"' ORDER BY tripAssignDate ASC";
						Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);						
						return q.getResultList();			
				}
		
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllEscortRequiredTripsByDate(Date fromDate, Date toDate,int branchId) {
		String query = "SELECT t FROM EFmFmAssignRoutePO t JOIN t.eFmFmClientBranchPO c WHERE t.tripStatus ='completed' AND date(t.tripAssignDate) >= ?1  AND   date(t.tripAssignDate) <=?2  AND t.escortRequredFlag='Y' AND c.branchId='"+branchId+"'  ORDER BY tripAssignDate ASC";
		Query q = entityManager.createQuery(query).setParameter(1, fromDate, TemporalType.TIMESTAMP).setParameter(2, toDate, TemporalType.TIMESTAMP);
		return q.getResultList();
	}		
		
}