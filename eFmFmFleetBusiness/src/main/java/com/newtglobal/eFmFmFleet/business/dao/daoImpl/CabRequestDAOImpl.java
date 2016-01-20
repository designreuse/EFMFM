package com.newtglobal.eFmFmFleet.business.dao.daoImpl;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newtglobal.eFmFmFleet.business.dao.ICabRequestDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripTimingMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
@Repository("ICabRequestDAO")
public class CabRequestDAOImpl implements ICabRequestDAO{
	
	private EntityManager entityManager;
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {		
		entityManager.persist(eFmFmEmployeeTravelRequestPO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmTripTimingMasterPO eFmFmTripTimingMasterPO) {		
		entityManager.persist(eFmFmTripTimingMasterPO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmAssignRoutePO eFmFmAssignRoutePO) {		
		entityManager.persist(eFmFmAssignRoutePO);
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmEmployeeRequestMasterPO employeeRequestMasterPO) {
		// TODO Auto-generated method stub
		entityManager.persist(employeeRequestMasterPO);

	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmAssignRoutePO eFmFmAssignRoutePO) {
		// TODO Auto-generated method stub
		entityManager.merge(eFmFmAssignRoutePO);		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmEmployeeTripDetailPO employeeTripDetailPO) {
		// TODO Auto-generated method stub
		entityManager.merge(employeeTripDetailPO);		
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(EFmFmEmployeeTripDetailPO employeeTripDetailPO) {
		// TODO Auto-generated method stub
		entityManager.persist(employeeTripDetailPO);		
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularTripDetail(int empTripId) {	
		Query query = entityManager.createQuery("DELETE EFmFmEmployeeTripDetailPO where empTripId = '"+ empTripId+ "' ");					
		query.executeUpdate();
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularRequest(int requestId) {	
		Query query = entityManager.createQuery("DELETE EFmFmEmployeeTravelRequestPO where requestId = '"+ requestId+ "' ");					
		query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularRequestFromRequestMaster(int tripId) {	
		Query query = entityManager.createQuery("DELETE EFmFmEmployeeRequestMasterPO where tripId = '"+ tripId+ "' ");					
		query.executeUpdate();
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {	
		entityManager.merge(eFmFmEmployeeTravelRequestPO);		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void update(EFmFmEmployeeRequestMasterPO eFmFmEmployeeRequestMasterPO) {	
		entityManager.merge(eFmFmEmployeeRequestMasterPO);		
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {		
		entityManager.remove(eFmFmEmployeeTravelRequestPO);
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeRequestMasterPO> travelRequestExist(String employeeId,String tripType,int branchId,String requestType) {
		List <EFmFmEmployeeRequestMasterPO> employeeRequestPO = new ArrayList<EFmFmEmployeeRequestMasterPO>(); 
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeRequestMasterPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c  where u.employeeId='"+employeeId+"' and b.tripType='"+tripType+"' AND c.branchId='"+branchId+"' AND b.requestType='"+requestType+"' AND b.status='Y' ");
    	employeeRequestPO=query.getResultList();
       return employeeRequestPO;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeRequestMasterPO> getRequestFromRequestMaster(int tripId,int branchId) {
		List <EFmFmEmployeeRequestMasterPO> employeeRequestPO = new ArrayList<EFmFmEmployeeRequestMasterPO>(); 
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeRequestMasterPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c  where b.tripId='"+tripId+"'  AND c.branchId='"+branchId+"'  ");
    	employeeRequestPO=query.getResultList();
       return employeeRequestPO;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeRequestMasterPO> getAllRequestFromRequestMasterFprParticularEmployee(int userId,int branchId) {
		List <EFmFmEmployeeRequestMasterPO> employeeRequestPO = new ArrayList<EFmFmEmployeeRequestMasterPO>(); 
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeRequestMasterPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c  where u.userId='"+userId+"'  AND c.branchId='"+branchId+"' AND b.readFlg='Y' ");
    	employeeRequestPO=query.getResultList();
       return employeeRequestPO;
	}
	
	
	@Override
	public List<EFmFmEmployeeRequestMasterPO> getEmplyeeRequestsForSameDateAndShiftTime(Date date,Time siftTime,int branchId,int userId,String tripType) {
		List <EFmFmEmployeeRequestMasterPO> employeeRequestPO = new ArrayList<EFmFmEmployeeRequestMasterPO>(); 
		String todayDate;
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		todayDate = formatter.format(date);
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeRequestMasterPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c  where u.userId='"+userId+"' and b.tripRequestStartDate='"+todayDate+"' and b.tripType='"+tripType+"' and b.shiftTime='"+siftTime+"' and c.branchId='"+branchId+"' and b.status='Y' ");
    	employeeRequestPO=query.getResultList();
       return employeeRequestPO;
	}
	
	
	@Override
	public List<EFmFmEmployeeTravelRequestPO> getEmplyeeRequestsForSameDateAndShiftTimeFromTravelReq(Date date,Time siftTime,int branchId,int userId,String tripType) {
		List <EFmFmEmployeeTravelRequestPO> employeeRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>(); 
		String todayDate;
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		todayDate = formatter.format(date);
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c JOIN b.eFmFmEmployeeRequestMaster r where u.userId='"+userId+"' and b.requestDate='"+todayDate+"' and  r.tripType='"+tripType+"' and b.shiftTime='"+siftTime+"' and c.branchId='"+branchId+"' and ( b.requestStatus='RM' OR b.requestStatus='RW' OR b.requestStatus='M' OR b.requestStatus='W'OR b.requestStatus='Y') ");
    	employeeRequestPO=query.getResultList();
       return employeeRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getAllTodaysActiveRequests(int branchId) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		/*String todayDate;
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		todayDate = formatter.format(todaysDate);*/
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y'");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getAllActiveRequests(int branchId) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.completionStatus='N' ");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> listOfTravelRequest(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"' AND b.approveStatus='Y' AND b.readFlg='Y'  ORDER BY pickUpTime ASC");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> listOfAdhocAndGuestTravelRequests(int branchId) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z  where c.branchId='"+branchId+"' AND b.approveStatus='Y' AND b.readFlg='Y' AND b.requestType!='normal' ORDER BY requestDate,z.zoneName,pickUpTime ASC");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> deleteCurentRequestfromTraveldesk(int branchId,int tripId) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND r.tripId='"+tripId+"' AND b.approveStatus='Y' AND b.readFlg='Y' ");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> listOfTravelRequestByShiftWice(int branchId,Time shiftTime) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z where c.branchId='"+branchId+"' AND b.approveStatus='Y' AND b.readFlg='Y' AND b.shiftTime='"+shiftTime+"' ORDER BY requestDate,z.zoneName,pickUpTime ASC");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> listOfTravelRequestForAdminShiftWise(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"' AND b.shiftTime='"+eFmFmEmployeeTravelRequestPO.getShiftTime()+"' AND b.approveStatus='Y' AND b.readFlg='Y'  ORDER BY pickUpTime ASC");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> assignCabRequestToParticularShiftOrRouteEmployees(int branchId, String tripType,Time siftTime,int zoneId) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z  where c.branchId='"+branchId+"' and b.tripType='"+tripType+"' and b.shiftTime='"+siftTime+"' and z.zoneId='"+zoneId+"'and b.approveStatus='Y' and b.readFlg='Y'");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> assignCabRequestToParticularShiftEmployees(int branchId, String tripType,Time siftTime) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping a JOIN a.eFmFmZoneMaster z  where c.branchId='"+branchId+"' and b.tripType='"+tripType+"' and b.shiftTime='"+siftTime+"' and  b.approveStatus='Y' and b.readFlg='Y'");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmTripTimingMasterPO> listOfShiftTime(int branchId) {
		List <EFmFmTripTimingMasterPO> shiftTime = new ArrayList<EFmFmTripTimingMasterPO>();		
		Query query=entityManager.createQuery("SELECT b FROM EFmFmTripTimingMasterPO b JOIN b.eFmFmClientBranchPO  c where c.branchId='"+branchId+"' AND b.isActive='Y' ");
		shiftTime=query.getResultList();		
		return shiftTime;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmTripTimingMasterPO> listOfShiftTimeByTripType(int branchId,String tripType) {
		List <EFmFmTripTimingMasterPO> shiftTime = new ArrayList<EFmFmTripTimingMasterPO>();		
		Query query=entityManager.createQuery("SELECT b FROM EFmFmTripTimingMasterPO b JOIN b.eFmFmClientBranchPO  c where c.branchId='"+branchId+"' AND b.tripType='"+tripType+"' AND b.isActive='Y' ");
		shiftTime=query.getResultList();		
		return shiftTime;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmTripTimingMasterPO> getParticularShiftTimeDetail(int branchId,Time shiftTime) {
		List <EFmFmTripTimingMasterPO> shiftTimeDetail = new ArrayList<EFmFmTripTimingMasterPO>();		
		Query query=entityManager.createQuery("SELECT b FROM EFmFmTripTimingMasterPO b JOIN b.eFmFmClientBranchPO  c where c.branchId='"+branchId+"' AND b.shiftTime='"+shiftTime+"' AND b.isActive='Y' ");
		shiftTimeDetail=query.getResultList();		
		return shiftTimeDetail;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getAllResheduleRequests(int projectId,int branchId) {
		List <EFmFmEmployeeTravelRequestPO> resheduleRequests = new ArrayList<EFmFmEmployeeTravelRequestPO>();		
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO  c JOIN u.eFmFmClientProjectDetails d where  b.isActive='N' AND b.approveStatus='N' AND  b.readFlg='N' AND d.projectId='"+projectId+"' AND c.branchId='"+branchId+"' AND  (b.requestStatus='CW' OR b.requestStatus='CM' OR b.requestStatus='RM' OR b.requestStatus='RW' OR b.requestStatus='M' OR b.requestStatus='W') ");
		resheduleRequests=query.getResultList();		
		return resheduleRequests;
	}	
	
	@Override
	public List<EFmFmEmployeeTravelRequestPO> getTodayRequestForParticularEmployee(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequest) {
		// TODO Auto-generated method stub
		List <EFmFmEmployeeTravelRequestPO>  todayrequests = new ArrayList<EFmFmEmployeeTravelRequestPO>();		
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+eFmFmEmployeeTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"' AND u.userId='"+eFmFmEmployeeTravelRequest.getEfmFmUserMaster().getUserId()+"' AND ( b.requestStatus='RM' OR b.requestStatus='RW' OR b.requestStatus='M' OR b.requestStatus='W'OR b.requestStatus='Y' OR b.requestStatus='E') ");
		todayrequests=query.getResultList();		
		return todayrequests;
	}
	
	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllTodaysRequestForParticularEmployee(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequest) {
		// TODO Auto-generated method stub
		List <EFmFmEmployeeTravelRequestPO>  todayrequests = new ArrayList<EFmFmEmployeeTravelRequestPO>();		
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+eFmFmEmployeeTravelRequest.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"' AND u.userId='"+eFmFmEmployeeTravelRequest.getEfmFmUserMaster().getUserId()+"' AND b.readFlg='Y' ");
		todayrequests=query.getResultList();		
		return todayrequests;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getparticularRequestDetail(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where b.requestId='"+eFmFmEmployeeTravelRequestPO.getRequestId()+"' AND c.branchId='"+eFmFmEmployeeTravelRequestPO.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"' ");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getParticularRequestDetailOnTripComplete(int branchId,int requestId) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where b.requestId='"+requestId+"' AND c.branchId='"+branchId+"' ");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getParticularApproveRequestDetail(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
	/*	String todayDate;
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		todayDate = formatter.format(eFmFmEmployeeTravelRequestPO.getRequestDate());*/
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where b.approveStatus='Y' and u.userId='"+eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId()+"' AND c.branchId='"+eFmFmEmployeeTravelRequestPO.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"'");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getAnotherActiveRequestDetail(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where b.approveStatus='Y' AND b.readFlg='Y' AND b.isActive='Y' AND b.requestType='"+eFmFmEmployeeTravelRequestPO.getRequestType()+"' AND b.tripType='"+eFmFmEmployeeTravelRequestPO.getTripType()+"' AND u.userId='"+eFmFmEmployeeTravelRequestPO.getEfmFmUserMaster().getUserId()+"' AND c.branchId='"+eFmFmEmployeeTravelRequestPO.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"'");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getAnotherActiveRequestForNextDate(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Date todayDate = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(todayDate);
		cal.add(Calendar.DATE, 1);
        Date currentDate = cal.getTime();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String nextDate = formatter.format(currentDate);
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where DATE(b.requestDate)=TRIM('"+nextDate+"') AND b.approveStatus='Y' AND b.readFlg='Y' AND b.isActive='Y' AND b.requestType='"+eFmFmEmployeeTravelRequestPO.getRequestType()+"' AND b.tripType='"+eFmFmEmployeeTravelRequestPO.getTripType()+"' AND u.userId='"+eFmFmEmployeeTravelRequestPO.getEfmFmUserMaster().getUserId()+"' AND c.branchId='"+eFmFmEmployeeTravelRequestPO.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"'");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getParticularRequestDetail(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.eFmFmEmployeeRequestMaster r JOIN r.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where b.employeeId='"+eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().getUserId()+"' AND  b.tripType='"+eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getTripType()+"' AND c.branchId='"+eFmFmEmployeeTravelRequestPO.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"' ");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> gettripForParticularDriver(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> tripDtails = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.efmFmVehicleCheckIn ch where ch.checkInId='"+assignRoutePO.getEfmFmVehicleCheckIn().getCheckInId()+"' AND b.tripStatus!='completed' AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"'");
		tripDtails=query.getResultList();				
		return tripDtails;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getLastRouteDetails(int checkInId,int branchId,String tripType) {
		List <EFmFmAssignRoutePO> tripDtails = new ArrayList<EFmFmAssignRoutePO>();
		/*String todayDate;
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		todayDate = formatter.format(assignRoutePO.getTripAssignDate());*/
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.efmFmVehicleCheckIn ch where ch.checkInId='"+checkInId+"' AND tripType='"+tripType+"' AND b.tripStatus='allocated' AND c.branchId='"+branchId+"'");
		tripDtails=query.getResultList();				
		return tripDtails;
	}
	
	//Trip on driver device after closing the bucket only
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> closeVehicleCapacity(int checkInId,int branchId) {
		List <EFmFmAssignRoutePO> tripDtails = new ArrayList<EFmFmAssignRoutePO>();
		/*String todayDate;
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		todayDate = formatter.format(assignRoutePO.getTripAssignDate());*/
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c JOIN b.efmFmVehicleCheckIn ch where ch.checkInId='"+checkInId+"' AND b.tripStatus!='completed' AND b.bucketStatus='Y' AND c.branchId='"+branchId+"'");
		tripDtails=query.getResultList();				
		return tripDtails;
	}
	
	
	
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getDropTripAllSortedEmployees(int assignRouteId) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster u JOIN b.efmFmAssignRoute d where d.assignRouteId='"+assignRouteId+"' ORDER BY t.dropSequence ASC");		
		tripemployees=query.getResultList();				
		return tripemployees;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getNonDropTripAllSortedEmployees(int assignRouteId) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster u JOIN b.efmFmAssignRoute d where b.boardedFlg='N' AND d.assignRouteId='"+assignRouteId+"' ORDER BY t.dropSequence ASC");		
		tripemployees=query.getResultList();				
		return tripemployees;
	}
	
	/*@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getParticularTripAllEmployees(int assignRouteId) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.eFmFmEmployeeRequestMaster r JOIN b.efmFmAssignRoute d where d.assignRouteId='"+assignRouteId+"' ORDER BY r.pickUpTime ASC");		

//		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.efmFmAssignRoute d where d.assignRouteId='"+assignRouteId+"'");
		tripemployees=query.getResultList();				
		return tripemployees;
	}*/
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getParticularTripNonDropEmployeesDetails(int assignRouteId) {
		List <EFmFmEmployeeTripDetailPO> tripEmployees = new ArrayList<EFmFmEmployeeTripDetailPO>();			
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN b.efmFmAssignRoute d where b.boardedFlg='N'  AND d.assignRouteId='"+assignRouteId+"' ORDER BY t.pickUpTime ASC");		
		tripEmployees=query.getResultList();				
		return tripEmployees;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getParticularDriverAssignTripDetail(EFmFmAssignRoutePO assignRoutePO) {
		List <EFmFmAssignRoutePO> tripDtails = new ArrayList<EFmFmAssignRoutePO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmClientBranchPO c where  b.assignRouteId='"+assignRoutePO.getAssignRouteId()+"' AND c.branchId='"+assignRoutePO.geteFmFmClientBranchPO().getBranchId()+"'");
		tripDtails=query.getResultList();				
		return tripDtails;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getParticularTripParticularEmployees(int employeeId,int assignRouteId,int branchId) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.efmFmAssignRoute d JOIN d.eFmFmClientBranchPO c where d.assignRouteId='"+assignRouteId+"' AND b.employeeId='"+employeeId+"' AND c.branchId='"+branchId+"'");
		tripemployees=query.getResultList();				
		return tripemployees;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getTodayTripEmployeesDetail(int userId,int branchId,Date todayDate) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();	
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.efmFmAssignRoute d JOIN d.eFmFmClientBranchPO c JOIN b.eFmFmEmployeeTravelRequest r JOIN r.efmFmUserMaster u where  u.userId='"+userId+"'  AND c.branchId='"+branchId+"'");
		tripemployees=query.getResultList();				
		return tripemployees;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getEmployeeLiveTripDetailFromUserId(int userId,int branchId) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();	
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.efmFmAssignRoute d JOIN d.eFmFmClientBranchPO c JOIN b.eFmFmEmployeeTravelRequest r JOIN r.efmFmUserMaster u where  u.userId='"+userId+"'  AND c.branchId='"+branchId+"' AND d.tripStatus='Started' AND b.boardedFlg='N' ");
		tripemployees=query.getResultList();				
		return tripemployees;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getAllocatedEmployeeDetail(int userId,int branchId,Date todayDate) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();	
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.efmFmAssignRoute d JOIN d.eFmFmClientBranchPO c JOIN b.eFmFmEmployeeTravelRequest r JOIN r.efmFmUserMaster u where  u.userId='"+userId+"'  AND c.branchId='"+branchId+"' AND d.tripStatus!='completed' ");
		tripemployees=query.getResultList();				
		return tripemployees;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getAllTodayTripDetails(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequest) {
		List <EFmFmEmployeeTripDetailPO> travelRequestDetails = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Format formatter;	
		String todaysDate="";
		formatter = new SimpleDateFormat("yyyy-MM-dd");	
		todaysDate=formatter.format(new Date());
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.eFmFmEmployeeRequestMaster r JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c where DATE(b.actualTime)='"+todaysDate+"' AND c.branchId='"+eFmFmEmployeeTravelRequest.getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId()+"'");		
		travelRequestDetails=query.getResultList();				
		return travelRequestDetails;		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getParticularTriprEmployeeBoardedStatus(int requestId,int assignRouteId) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.efmFmAssignRoute a JOIN b.eFmFmEmployeeTravelRequest r where a.assignRouteId='"+assignRouteId+"' AND r.requestId='"+requestId+"'");
		tripemployees=query.getResultList();				
		return tripemployees;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getrequestStatusFromBranchIdAndRequestId(int branchId,int requestId) {
		List <EFmFmEmployeeTripDetailPO> travelRequestDetails = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.eFmFmEmployeeRequestMaster r JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND t.requestId='"+requestId+"'");		
		travelRequestDetails=query.getResultList();				
		return travelRequestDetails;		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeRequestMasterPO> getAllRequestDetailsFromRequestMasterFromBranchId(int branchId) {
		List <EFmFmEmployeeRequestMasterPO> employeeRequestPO = new ArrayList<EFmFmEmployeeRequestMasterPO>(); 
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeRequestMasterPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' ");
    	employeeRequestPO=query.getResultList();
       return employeeRequestPO;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeRequestMasterPO> getAllRequestDetailsFromRequestMasterFromBranchIdByTripType(int branchId,String tripType) {
		List <EFmFmEmployeeRequestMasterPO> employeeRequestPO = new ArrayList<EFmFmEmployeeRequestMasterPO>(); 
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeRequestMasterPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c  where c.branchId='"+branchId+"' AND b.tripType='"+tripType+"' ");
    	employeeRequestPO=query.getResultList();
       return employeeRequestPO;
	}
	
	
	//All allocation methods
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeRequestMasterPO> getParticularEmployeeMasterRequestDetails(int branchId,int tripId) {
		List <EFmFmEmployeeRequestMasterPO> employeeRequest = new ArrayList<EFmFmEmployeeRequestMasterPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeRequestMasterPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO  c where b.tripId='"+tripId+"' AND  c.branchId='"+branchId+"' ");
		employeeRequest=query.getResultList();		
		return employeeRequest;
	}
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTravelRequestPO> getAllParticularRouteRequest(int branchId,int zoneId,Time shiftTime) {
		List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
		Format formatter;  
	     formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c JOIN b.eFmFmRouteAreaMapping v  JOIN v.eFmFmZoneMaster vm where c.branchId='"+branchId+"' AND  vm.zoneId='"+zoneId+"' AND b.requestDate='"+formatter.format(new Date())+"' AND b.isActive='Y' AND b.approveStatus='Y' AND b.readFlg='Y' AND b.shiftTime='"+shiftTime+"' AND (b.requestStatus!='C' OR b.requestStatus!='R')");
		employeeTravelRequestPO=query.getResultList();		
		return employeeTravelRequestPO;
	}

	@Override
     @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleLessCapacity(int branchId,int capacity) {
	  List <EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where d.capacity<='"+capacity+"' and d.vehicleNumber like '%DUMMY%' and b.status='Y' and g.branchId='"+branchId+"' ");  
	  vehicleCheckIn=query.getResultList();  
	  return vehicleCheckIn;
	 
	 }
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleLargeCapacity(int branchId,int capacity) {
	  List <EFmFmVehicleCheckInPO> vehicleCheckIn = new ArrayList<EFmFmVehicleCheckInPO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleCheckInPO b JOIN b.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where d.capacity>='"+capacity+"'  and d.vehicleNumber like '%DUMMY%' and g.branchId='"+branchId+"' and b.status='Y'");  
	  vehicleCheckIn=query.getResultList();  
	  return vehicleCheckIn;
	 
	 }

	 @Override
     @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmAssignRoutePO> getHalfCompletedAssignRouteFromCheckInId(int branchId,int zoneId,String reqType,Time shiftTime, int checkInId) {
	  List <EFmFmAssignRoutePO> assignVehicleDetail = new ArrayList<EFmFmAssignRoutePO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmRouteAreaMapping v  JOIN v.eFmFmZoneMaster vm JOIN b.eFmFmClientBranchPO c JOIN b.efmFmVehicleCheckIn vc where b.vehicleStatus='A' AND b.tripStatus='allocated' AND c.branchId ='"+branchId+"' AND vm.zoneId='"+zoneId+"' AND b.tripType='"+reqType+"' AND b.shiftTime='"+shiftTime+"'  AND vc.checkInId='"+checkInId+"'");  
	  assignVehicleDetail=query.getResultList();  
	  return assignVehicleDetail;
	 }		
	
	 @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmAssignRoutePO> getHalfCompletedAssignRoute(int branchId,int zoneId,String reqType,Time shiftTime) {
	  List <EFmFmAssignRoutePO> assignVehicleDetail = new ArrayList<EFmFmAssignRoutePO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.eFmFmRouteAreaMapping v  JOIN v.eFmFmZoneMaster vm JOIN b.eFmFmClientBranchPO c JOIN b.efmFmVehicleCheckIn vc  JOIN vc.efmFmVehicleMaster vem where b.vehicleStatus='A' AND b.tripStatus='allocated' AND c.branchId ='"+branchId+"' AND vm.zoneId='"+zoneId+"' AND b.tripType='"+reqType+"' AND b.shiftTime='"+shiftTime+"'  ORDER BY vem.availableSeat DESC ");  
	  assignVehicleDetail=query.getResultList();  
	  return assignVehicleDetail;
	 }


	 @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEscortCheckInPO> getAllCheckedInEscort(int branchId) {
		List <EFmFmEscortCheckInPO> escortMasterPO = new ArrayList<EFmFmEscortCheckInPO>();	
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEscortCheckInPO b JOIN b.eFmFmEscortMaster e JOIN e.efmFmVendorMaster v JOIN v.eFmFmClientBranchPO c where b.status='Y' AND b.escortCheckOutTime is null AND c.branchId='"+branchId+"'");
		escortMasterPO=query.getResultList();		
		return escortMasterPO;
	}
	 
	 @Override  
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmVehicleMasterPO> getVehicleDetailsFromClientIdForSmallerRoute(int branchId,String vehicleId) {		
			List <EFmFmVehicleMasterPO> vehicleMasterPO = new ArrayList<EFmFmVehicleMasterPO>();		
			Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO b JOIN b.efmFmVendorMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.status='A' AND b.vehicleId in ("+vehicleId+") ORDER BY capacity ASC");
			vehicleMasterPO=query.getResultList();		
			return vehicleMasterPO;
		}
	 @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getParticularTripAllEmployees(int assignRouteId) {
		List <EFmFmEmployeeTripDetailPO> tripemployees = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN b.efmFmAssignRoute d where d.assignRouteId='"+assignRouteId+"' ORDER BY t.pickUpTime ASC");		
		tripemployees=query.getResultList();				
		return tripemployees;
	}

	 @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmEmployeeTripDetailPO> getRequestStatusFromBranchIdAndRequestId(int branchId,int requestId) {
		List <EFmFmEmployeeTripDetailPO> travelRequestDetails = new ArrayList<EFmFmEmployeeTripDetailPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.eFmFmEmployeeRequestMaster r JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND t.requestId='"+requestId+"'");		
		travelRequestDetails=query.getResultList();				
		return travelRequestDetails;		
	}
	 @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteParticularRequestFromEmployeeTripDetail(int empTripId) {
		Query query = entityManager.createQuery("DELETE EFmFmEmployeeTripDetailPO where empTripId = '"+ empTripId+ "' ");					
		query.executeUpdate();
	}
	 @Override
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public EFmFmVehicleMasterPO getVehicleDetail(int vehicleId) {
			List<EFmFmVehicleMasterPO> vehicleDetail  =new ArrayList<EFmFmVehicleMasterPO>();
	    	Query query=entityManager.createQuery("SELECT b FROM EFmFmVehicleMasterPO as b  where b.vehicleId='"+vehicleId+"'");
	    	vehicleDetail=query.getResultList();
			return vehicleDetail.get(0);
		}
	 
	    @Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllActivePickUpRequestCounter(int branchId) {
			Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.tripType='PICKUP' AND b.completionStatus='N' AND b.requestType!='guest' AND (b.readFlg='Y' OR b.readFlg='R') ");
			long numberOfPickUpRequest=(long) query.getSingleResult();
			return numberOfPickUpRequest;
			
		}
	    
	    @Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllActiveDropRequestCounter(int branchId) {
			Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.tripType='DROP' AND b.completionStatus='N' AND b.requestType!='guest' AND (b.readFlg='Y' OR b.readFlg='R')");
			long numberOfDropRequest=(long) query.getSingleResult();
			return numberOfDropRequest;
			
		}
	    
	    @Override
		@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		public long getAllActiveDropOrPickupRequestCounterForGuest(int branchId) {
			Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y'  AND b.completionStatus='N' AND b.requestType='guest' AND (b.readFlg='Y' OR b.readFlg='R')");
			long numberOfDropRequest=(long) query.getSingleResult();
			return numberOfDropRequest;
			
		}
		
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllActiveBoardedEmployeeRequestCounter(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='B' AND a.tripStatus!='completed' AND a.tripType='PICKUP' AND t.requestType!='guest'");		
				long boardedEmployee=(long) query.getSingleResult();
				return boardedEmployee;
				
			}
		  
		  
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllActiveBoardedGuestRequestCounter(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg!='N' AND a.tripStatus!='completed' AND t.requestType='guest' ");		
				long boardedEmployee=(long) query.getSingleResult();
				return boardedEmployee;
				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllActiveNoShowEmployeeRequestCounter(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='NO' AND a.tripStatus!='completed' AND a.tripType='PICKUP' AND t.requestType!='guest'");		
				long noShowEmployee=(long) query.getSingleResult();
				return noShowEmployee;
				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllActiveNoShowGuestRequestCounter(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='NO' AND a.tripStatus!='completed' AND t.requestType='guest'");		
				long noShowEmployee=(long) query.getSingleResult();
				return noShowEmployee;
				
			}
		  
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllActivePickupInProgressEmployeeRequestCounter(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='N' AND a.tripStatus!='completed' AND a.tripType='PICKUP' AND t.requestType!='guest'");		
				long pickUpInProgress=(long) query.getSingleResult();
				return pickUpInProgress;
				
			}
		  
		  //Drop employee list
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllActiveDropedEmployeeRequestCounter(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='D' AND a.tripStatus!='completed' AND a.tripType='DROP' AND t.requestType!='guest'");		
				long droppedEmployee=(long) query.getSingleResult();
				return droppedEmployee;
				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllActiveDropNoShowEmployeeRequestCounter(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='NO' AND a.tripStatus!='completed' AND a.tripType='DROP' AND t.requestType!='guest'");		
				long noShowDrop=(long) query.getSingleResult();
				return noShowDrop;
				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllActiveDropInProgressEmployeeRequestCounter(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='N' AND a.tripStatus!='completed' AND a.tripType='DROP' AND t.requestType!='guest'");		
				long dropUpInProgress=(long) query.getSingleResult();
				return dropUpInProgress;
				
			}
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllDropScheduleActiveRequests(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.tripType='DROP' AND b.readFlg='R' AND b.completionStatus='N' AND b.requestType!='guest'");
				long dropScheduled=(long) query.getSingleResult();
				return dropScheduled;				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllPickupScheduleActiveRequests(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.tripType='PICKUP' AND b.readFlg='R' AND b.completionStatus='N' AND b.requestType!='guest' ");
				long dropScheduled=(long) query.getSingleResult();
				return dropScheduled;				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public long getAllScheduleActiveRequestsForGuest(int branchId) {
				Query query=entityManager.createQuery("SELECT count(b) FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.readFlg='R' AND b.completionStatus='N' AND b.requestType='guest' ");
				long dropScheduled=(long) query.getSingleResult();
				return dropScheduled;				
			}
		  
		  
		  
		  @Override
			@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
			public List<EFmFmEmployeeTravelRequestPO> particularEmployeeRequestFromEmpId(int branchId,String employeeId) {
				List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND u.employeeId='"+employeeId+"' AND b.approveStatus='Y' AND b.readFlg='Y'  ORDER BY pickUpTime ASC");
				employeeTravelRequestPO=query.getResultList();		
				return employeeTravelRequestPO;
			}
		  
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTravelRequestPO> particularEmployeePickupRequestFromUserId(int branchId,int userId,String tripType) {
				List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND u.userId='"+userId+"' AND b.approveStatus='Y' AND b.readFlg='Y' AND b.tripType='"+tripType+"' ORDER BY pickUpTime ASC");
				employeeTravelRequestPO=query.getResultList();		
				return employeeTravelRequestPO;
			}
		  
		  
		 
		  //Drop template detail quiries
		  
		  @Override
			@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
			public List<EFmFmEmployeeTravelRequestPO> getAllActiveDropRequestDetails(int branchId) {
				List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.tripType='DROP' AND b.completionStatus='N' AND b.requestType!='guest' AND (b.readFlg='Y' OR b.readFlg='R') ");
				employeeTravelRequestPO=query.getResultList();	
				return employeeTravelRequestPO;	
				
			}
		  
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTravelRequestPO> getAllActiveGuestRequestsDetails(int branchId) {
				List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y'  AND b.completionStatus='N' AND b.requestType='guest' AND (b.readFlg='Y' OR b.readFlg='R') ");
				employeeTravelRequestPO=query.getResultList();	
				return employeeTravelRequestPO;	
				
			}
		  
		  
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTravelRequestPO> getAllDropScheduleActiveRequestsDetails(int branchId) {
				List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.tripType='DROP' AND b.readFlg='R' AND b.completionStatus='N' AND b.requestType!='guest'");
				employeeTravelRequestPO=query.getResultList();		
				return employeeTravelRequestPO;			
			}
		  
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTravelRequestPO> getAllScheduleActiveGuestRequestsDetails(int branchId) {
				List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y'  AND b.readFlg='R' AND b.completionStatus='N' AND b.requestType='guest'");
				employeeTravelRequestPO=query.getResultList();		
				return employeeTravelRequestPO;			
			}
		  
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTripDetailPO> getAllActiveDropedEmployeeRequestsDetails(int branchId) {
				List <EFmFmEmployeeTripDetailPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTripDetailPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='D' AND a.tripStatus!='completed' AND a.tripType='DROP' AND t.requestType!='guest'");		
				employeeTravelRequestPO=query.getResultList();	
				return employeeTravelRequestPO;				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTripDetailPO> getAllActiveDropNoShowEmployeeRequestsDetails(int branchId) {
				List <EFmFmEmployeeTripDetailPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTripDetailPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='NO' AND a.tripStatus!='completed' AND a.tripType='DROP' AND t.requestType!='guest'");		
				employeeTravelRequestPO=query.getResultList();	
				return employeeTravelRequestPO;				
			}
		  
		  //Pickup template detail quiries
		  
		  @Override
			@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
			public List<EFmFmEmployeeTravelRequestPO> getAllActivePickUpRequestDetails(int branchId) {
				List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.tripType='PICKUP' AND b.completionStatus='N' AND b.requestType!='guest' AND (b.readFlg='Y' OR b.readFlg='R') ");
				employeeTravelRequestPO=query.getResultList();	
				return employeeTravelRequestPO;	
				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTravelRequestPO> getAllPickupScheduleActiveRequestsDetails(int branchId) {
				List <EFmFmEmployeeTravelRequestPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTravelRequestPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTravelRequestPO b JOIN b.efmFmUserMaster u JOIN u.eFmFmClientBranchPO c where c.branchId='"+branchId+"' AND b.isActive='Y' AND b.tripType='PICKUP' AND b.readFlg='R' AND b.completionStatus='N' AND b.requestType!='guest'");
				employeeTravelRequestPO=query.getResultList();		
				return employeeTravelRequestPO;			
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTripDetailPO> getAllActivePickupBoardedEmployeeRequestsDetails(int branchId) {
				List <EFmFmEmployeeTripDetailPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTripDetailPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='B' AND a.tripStatus!='completed' AND a.tripType='PICKUP' AND t.requestType!='guest'");		
				employeeTravelRequestPO=query.getResultList();	
				return employeeTravelRequestPO;				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTripDetailPO> getAllActiveBoardedOrDroppedEmployeeRequestsDetailsForGuest(int branchId) {
				List <EFmFmEmployeeTripDetailPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTripDetailPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND (b.boardedFlg='B' OR b.boardedFlg='D') AND a.tripStatus!='completed'  AND t.requestType='guest'");		
				employeeTravelRequestPO=query.getResultList();	
				return employeeTravelRequestPO;				
			}
		  
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTripDetailPO> getAllActivePickupNoShowEmployeeRequestsDetails(int branchId) {
				List <EFmFmEmployeeTripDetailPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTripDetailPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='NO' AND a.tripStatus!='completed' AND a.tripType='PICKUP' AND t.requestType!='guest'");		
				employeeTravelRequestPO=query.getResultList();	
				return employeeTravelRequestPO;				
			}
		  
		  @Override
		  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
		  public List<EFmFmEmployeeTripDetailPO> getAllActiveGuestNoShowRequestsDetails(int branchId) {
				List <EFmFmEmployeeTripDetailPO> employeeTravelRequestPO = new ArrayList<EFmFmEmployeeTripDetailPO>();
				Query query=entityManager.createQuery("SELECT b FROM EFmFmEmployeeTripDetailPO b JOIN b.eFmFmEmployeeTravelRequest t JOIN t.efmFmUserMaster d JOIN d.eFmFmClientBranchPO c JOIN b.efmFmAssignRoute a where c.branchId='"+branchId+"' AND b.boardedFlg='NO' AND a.tripStatus!='completed' AND t.requestType='guest'");		
				employeeTravelRequestPO=query.getResultList();	
				return employeeTravelRequestPO;				
			}
		  
}
