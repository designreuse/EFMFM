package com.newtglobal.eFmFmFleet.business.bo.boImpl;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.dao.ICabRequestDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripTimingMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;

@Service("ICabRequestBO")
public class ICabRequestBOImpl implements ICabRequestBO{
	
	@Autowired
	ICabRequestDAO iCabRequestDAO;

	public void setiCabRequestDAO(ICabRequestDAO iCabRequestDAO) {
		this.iCabRequestDAO = iCabRequestDAO;
	}

	@Override
	public void save(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		
		iCabRequestDAO.save(eFmFmEmployeeTravelRequestPO);
	}
	
	@Override
	public void save(EFmFmTripTimingMasterPO eFmFmTripTimingMasterPO) {
		
		iCabRequestDAO.save(eFmFmTripTimingMasterPO);
	}
	
	@Override
	public void save(EFmFmAssignRoutePO eFmFmAssignRoutePO) {
		// TODO Auto-generated method stub
		iCabRequestDAO.save(eFmFmAssignRoutePO);
	}

	@Override
	public void update(EFmFmAssignRoutePO eFmFmAssignRoutePO) {
		// TODO Auto-generated method stub
		iCabRequestDAO.update(eFmFmAssignRoutePO);

	}
	@Override
	public void update(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {		
		iCabRequestDAO.update(eFmFmEmployeeTravelRequestPO);
	}

	@Override
	public void delete(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		iCabRequestDAO.delete(eFmFmEmployeeTravelRequestPO);
		
	}


	@Override
	public List<EFmFmEmployeeTravelRequestPO> listOfTravelRequest(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {		
		return iCabRequestDAO.listOfTravelRequest(eFmFmEmployeeTravelRequestPO);
	}

	@Override
	public List<EFmFmTripTimingMasterPO> listOfShiftTime(int clientId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.listOfShiftTime(clientId);
	}


	@Override
	public List<EFmFmEmployeeTravelRequestPO> getTodayRequestForParticularEmployee(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequest) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getTodayRequestForParticularEmployee(eFmFmEmployeeTravelRequest);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getparticularRequestDetail(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getparticularRequestDetail(eFmFmEmployeeTravelRequestPO);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getParticularApproveRequestDetail(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getParticularApproveRequestDetail(eFmFmEmployeeTravelRequestPO);
	}

	@Override
	public void save(EFmFmEmployeeRequestMasterPO employeeRequestMasterPO) {
		// TODO Auto-generated method stub
		iCabRequestDAO.save(employeeRequestMasterPO);
	}

	

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getParticularRequestDetail(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getparticularRequestDetail(eFmFmEmployeeTravelRequestPO);
	}

	@Override
	public List<EFmFmAssignRoutePO> gettripForParticularDriver(EFmFmAssignRoutePO assignRoutePO) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.gettripForParticularDriver(assignRoutePO);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getParticularTripAllEmployees(int assignRouteId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getParticularTripAllEmployees(assignRouteId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getParticularDriverAssignTripDetail(
			EFmFmAssignRoutePO assignRoutePO) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getParticularDriverAssignTripDetail(assignRoutePO);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getParticularTripParticularEmployees(
			int employeeId, int assignRouteId,int clientId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getParticularTripParticularEmployees(employeeId, assignRouteId,clientId);
	}

	@Override
	public void update(EFmFmEmployeeTripDetailPO employeeTripDetailPO) {
		// TODO Auto-generated method stub
		iCabRequestDAO.update(employeeTripDetailPO);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getTodayTripEmployeesDetail(
			int employeeId, int clientId, Date todayDate) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getTodayTripEmployeesDetail(employeeId, clientId, todayDate);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllTodayTripDetails(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequest) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllTodayTripDetails(eFmFmEmployeeTravelRequest);
	}

	@Override
	public void update(EFmFmEmployeeRequestMasterPO eFmFmEmployeeRequestMasterPO) {
		// TODO Auto-generated method stub
		iCabRequestDAO.update(eFmFmEmployeeRequestMasterPO);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllTodaysActiveRequests(int clientId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllTodaysActiveRequests(clientId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getParticularTriprEmployeeBoardedStatus(
			int requestId, int assignRouteId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getParticularTriprEmployeeBoardedStatus(requestId, assignRouteId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllResheduleRequests(
			int projectId, int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllResheduleRequests(projectId, branchId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getParticularTripNonDropEmployeesDetails(
			int assignRouteId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getParticularTripNonDropEmployeesDetails(assignRouteId);
	}

	@Override
	public void deleteParticularTripDetail(int empTripId){
		// TODO Auto-generated method stub
		iCabRequestDAO.deleteParticularTripDetail(empTripId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getrequestStatusFromBranchIdAndRequestId(
			int branchId, int requestId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getrequestStatusFromBranchIdAndRequestId(branchId, requestId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getDropTripAllSortedEmployees(
			int assignRouteId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getDropTripAllSortedEmployees(assignRouteId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllTodaysRequestForParticularEmployee(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequest) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllTodaysRequestForParticularEmployee(eFmFmEmployeeTravelRequest);
	}

	@Override
	public List<EFmFmEmployeeRequestMasterPO> getEmplyeeRequestsForSameDateAndShiftTime(
			Date date, Time siftTime, int branchId, int userId, String tripType) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getEmplyeeRequestsForSameDateAndShiftTime(date, siftTime, branchId, userId, tripType);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getEmplyeeRequestsForSameDateAndShiftTimeFromTravelReq(
			Date date, Time siftTime, int branchId, int userId, String tripType) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getEmplyeeRequestsForSameDateAndShiftTimeFromTravelReq(date, siftTime, branchId, userId, tripType);
	}

	@Override
	public void save(EFmFmEmployeeTripDetailPO employeeTripDetailPO) {
		// TODO Auto-generated method stub
		iCabRequestDAO.save(employeeTripDetailPO);
	}

	@Override
	public List<EFmFmEmployeeRequestMasterPO> travelRequestExist(
			String employeeId, String tripType, int branchId, String requestType) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.travelRequestExist(employeeId, tripType, branchId, requestType);
	}

	@Override
	public List<EFmFmEmployeeRequestMasterPO> getRequestFromRequestMaster(
			int tripId, int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getRequestFromRequestMaster(tripId, branchId);
	}

	@Override
	public void deleteParticularRequest(int requestId) {
		// TODO Auto-generated method stub
		iCabRequestDAO.deleteParticularRequest(requestId);
	}

	@Override
	public void deleteParticularRequestFromRequestMaster(int tripId) {
		// TODO Auto-generated method stub
		iCabRequestDAO.deleteParticularRequestFromRequestMaster(tripId);
	}

	@Override
	public List<EFmFmEmployeeRequestMasterPO> getAllRequestDetailsFromRequestMasterFromBranchId(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllRequestDetailsFromRequestMasterFromBranchId(branchId);
	}

	@Override
	public List<EFmFmAssignRoutePO> closeVehicleCapacity(int checkInId,
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.closeVehicleCapacity(checkInId, branchId);
	}

	@Override
	public List<EFmFmEmployeeRequestMasterPO> getAllRequestFromRequestMasterFprParticularEmployee(
			int userId, int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllRequestFromRequestMasterFprParticularEmployee(userId, branchId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getLastRouteDetails(int checkInId,
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getLastRouteDetails(checkInId, branchId, tripType);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getParticularRequestDetailOnTripComplete(
			int branchId, int requestId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getParticularRequestDetailOnTripComplete(branchId, requestId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllocatedEmployeeDetail(
			int userId, int branchId, Date todayDate) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllocatedEmployeeDetail(userId, branchId, todayDate);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> assignCabRequestToParticularShiftOrRouteEmployees(
			int branchId, String tripType, Time siftTime, int zoneId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.assignCabRequestToParticularShiftOrRouteEmployees(branchId, tripType, siftTime, zoneId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllParticularRouteRequest(
			int branchId, int zoneId, Time shiftTime) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllParticularRouteRequest(branchId, zoneId, shiftTime);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleLessCapacity(
			int branchId, int capacity) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllCheckedInVehicleLessCapacity(branchId, capacity);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleLargeCapacity(
			int branchId, int capacity) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllCheckedInVehicleLargeCapacity(branchId, capacity);
	}

	@Override
	public List<EFmFmAssignRoutePO> getHalfCompletedAssignRouteFromCheckInId(
			int branchId, int zoneId, String reqType, Time shiftTime,
			int checkInId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getHalfCompletedAssignRouteFromCheckInId(branchId, zoneId, reqType, shiftTime, checkInId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getHalfCompletedAssignRoute(int branchId,
			int zoneId, String reqType, Time shiftTime) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getHalfCompletedAssignRoute(branchId, zoneId, reqType, shiftTime);
	}

	@Override
	public List<EFmFmEscortCheckInPO> getAllCheckedInEscort(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllCheckedInEscort(branchId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getVehicleDetailsFromClientIdForSmallerRoute(
			int branchId, String vehicleId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getVehicleDetailsFromClientIdForSmallerRoute(branchId, vehicleId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getRequestStatusFromBranchIdAndRequestId(
			int branchId, int requestId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getRequestStatusFromBranchIdAndRequestId(branchId, requestId);
	}

	@Override
	public void deleteParticularRequestFromEmployeeTripDetail(int empTripId) {
		// TODO Auto-generated method stub
		iCabRequestDAO.deleteParticularRequestFromEmployeeTripDetail(empTripId);
	}

	@Override
	public List<EFmFmEmployeeRequestMasterPO> getParticularEmployeeMasterRequestDetails(
			int branchId, int tripId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getParticularEmployeeMasterRequestDetails(branchId, tripId);
	}

	@Override
	public EFmFmVehicleMasterPO getVehicleDetail(int vehicleId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getVehicleDetail(vehicleId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> assignCabRequestToParticularShiftEmployees(
			int branchId, String tripType, Time siftTime) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.assignCabRequestToParticularShiftEmployees(branchId, tripType, siftTime);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllActiveRequests(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveRequests(branchId);
	}

	@Override
	public long getAllActivePickUpRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActivePickUpRequestCounter(branchId);
	}

	@Override
	public long getAllActivePickupInProgressEmployeeRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActivePickupInProgressEmployeeRequestCounter(branchId);
	}

	@Override
	public long getAllActiveNoShowEmployeeRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveNoShowEmployeeRequestCounter(branchId);
	}

	@Override
	public long getAllActiveBoardedEmployeeRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveBoardedEmployeeRequestCounter(branchId);
	}

	@Override
	public long getAllActiveDropRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveDropRequestCounter(branchId);
	}

	@Override
	public long getAllActiveDropedEmployeeRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveDropedEmployeeRequestCounter(branchId);
	}

	@Override
	public long getAllActiveDropNoShowEmployeeRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveDropNoShowEmployeeRequestCounter(branchId);
	}

	@Override
	public long getAllActiveDropInProgressEmployeeRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveDropInProgressEmployeeRequestCounter(branchId);
	}

	@Override
	public long getAllPickupScheduleActiveRequests(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllPickupScheduleActiveRequests(branchId);
	}

	@Override
	public long getAllDropScheduleActiveRequests(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllDropScheduleActiveRequests(branchId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAnotherActiveRequestDetail(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAnotherActiveRequestDetail(eFmFmEmployeeTravelRequestPO);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAnotherActiveRequestForNextDate(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAnotherActiveRequestForNextDate(eFmFmEmployeeTravelRequestPO);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> particularEmployeeRequestFromEmpId(
			int branchId, String employeeId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.particularEmployeeRequestFromEmpId(branchId, employeeId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> listOfTravelRequestForAdminShiftWise(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.listOfTravelRequestForAdminShiftWise(eFmFmEmployeeTravelRequestPO);
	}

	@Override
	public long getAllActiveDropOrPickupRequestCounterForGuest(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveDropOrPickupRequestCounterForGuest(branchId);
	}

	@Override
	public long getAllActiveNoShowGuestRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveNoShowGuestRequestCounter(branchId);
	}

	@Override
	public long getAllActiveBoardedGuestRequestCounter(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveBoardedGuestRequestCounter(branchId);
	}

	@Override
	public long getAllScheduleActiveRequestsForGuest(int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllScheduleActiveRequestsForGuest(branchId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> listOfTravelRequestByShiftWice(
			int branchId, Time shiftTime) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.listOfTravelRequestByShiftWice(branchId, shiftTime);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> listOfAdhocAndGuestTravelRequests(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.listOfAdhocAndGuestTravelRequests(branchId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllDropScheduleActiveRequestsDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllDropScheduleActiveRequestsDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllActiveDropedEmployeeRequestsDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveDropedEmployeeRequestsDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllActiveDropNoShowEmployeeRequestsDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveDropNoShowEmployeeRequestsDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllPickupScheduleActiveRequestsDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllPickupScheduleActiveRequestsDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllActivePickupBoardedEmployeeRequestsDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActivePickupBoardedEmployeeRequestsDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllActivePickupNoShowEmployeeRequestsDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActivePickupNoShowEmployeeRequestsDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllActiveDropRequestDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveDropRequestDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllActivePickUpRequestDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActivePickUpRequestDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getNonDropTripAllSortedEmployees(
			int assignRouteId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getNonDropTripAllSortedEmployees(assignRouteId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getEmployeeLiveTripDetailFromUserId(
			int userId, int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getEmployeeLiveTripDetailFromUserId(userId, branchId);
	}

	@Override
	public List<EFmFmTripTimingMasterPO> getParticularShiftTimeDetail(
			int branchId, Time shiftTime) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getParticularShiftTimeDetail(branchId, shiftTime);
	}

	@Override
	public List<EFmFmTripTimingMasterPO> listOfShiftTimeByTripType(
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.listOfShiftTimeByTripType(branchId, tripType);
	}

	@Override
	public List<EFmFmEmployeeRequestMasterPO> getAllRequestDetailsFromRequestMasterFromBranchIdByTripType(
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllRequestDetailsFromRequestMasterFromBranchIdByTripType(branchId, tripType);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllScheduleActiveGuestRequestsDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllScheduleActiveGuestRequestsDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllActiveBoardedOrDroppedEmployeeRequestsDetailsForGuest(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveBoardedOrDroppedEmployeeRequestsDetailsForGuest(branchId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> getAllActiveGuestRequestsDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveGuestRequestsDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllActiveGuestNoShowRequestsDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.getAllActiveGuestNoShowRequestsDetails(branchId);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> particularEmployeePickupRequestFromUserId(
			int branchId, int userId, String tripType) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.particularEmployeePickupRequestFromUserId(branchId, userId, tripType);
	}

	@Override
	public List<EFmFmEmployeeTravelRequestPO> deleteCurentRequestfromTraveldesk(
			int branchId, int tripId) {
		// TODO Auto-generated method stub
		return iCabRequestDAO.deleteCurentRequestfromTraveldesk(branchId, tripId);
	}

	
	
}
