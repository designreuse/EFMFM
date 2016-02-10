package com.newtglobal.eFmFmFleet.business.dao;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripTimingMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;

public interface ICabRequestDAO {
	/**
	* Below method's are implemented for Adding ,Modify and Delete functionality on EmployeeTrevelRequest table.
	*
	* @author  Rajan R
	* 
	* @since   2015-05-12 
	*/
	public void save(EFmFmEmployeeTravelRequestPO  eFmFmEmployeeTravelRequestPO);
	public void save(EFmFmEmployeeRequestMasterPO employeeRequestMasterPO);
	public void save(EFmFmAssignRoutePO eFmFmAssignRoutePO);
	public void save(EFmFmTripTimingMasterPO eFmFmTripTimingMasterPO);

	public void update(EFmFmAssignRoutePO eFmFmAssignRoutePO);
	public void update(EFmFmEmployeeTripDetailPO employeeTripDetailPO);


	
	public void update(EFmFmEmployeeTravelRequestPO  eFmFmEmployeeTravelRequestPO);
	public void delete(EFmFmEmployeeTravelRequestPO  eFmFmEmployeeTravelRequestPO);
	/**
	* The method implemented for handling the duplication while reading travel request from xl sheet. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-12 
	*/
	public List<EFmFmEmployeeRequestMasterPO> travelRequestExist(String employeeId,
			String tripType, int branchId, String requestType);
	
	/**
	* The method implemented for travel list for particular day.  
	*
	* @author  Rajan R
	* 
	* @since   2015-05-13 
	*/
	public List<EFmFmEmployeeTravelRequestPO>  listOfTravelRequest(EFmFmEmployeeTravelRequestPO  eFmFmEmployeeTravelRequestPO);
	/**
	* The method implemented for getting list of shift time.  
	*
	* @author  Rajan R
	* 
	* @since   2015-05-13 
	*/	
	public List<EFmFmTripTimingMasterPO> listOfShiftTime(int clientId);
	public List<EFmFmEmployeeTravelRequestPO> getTodayRequestForParticularEmployee(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequest);
	public List<EFmFmEmployeeTravelRequestPO> getparticularRequestDetail(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO);
	public List<EFmFmEmployeeTravelRequestPO> getParticularApproveRequestDetail(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO);
	public List<EFmFmEmployeeTravelRequestPO> getParticularRequestDetail(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO);
	public List<EFmFmAssignRoutePO> gettripForParticularDriver(
			EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmEmployeeTripDetailPO> getParticularTripAllEmployees(
			int assignRouteId);
	public List<EFmFmAssignRoutePO> getParticularDriverAssignTripDetail(
			EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmEmployeeTripDetailPO> getParticularTripParticularEmployees(
			int employeeId, int assignRouteId,int clientId);
	public List<EFmFmEmployeeTripDetailPO> getTodayTripEmployeesDetail(int employeeId,
			int clientId, Date todayDate);
	public List<EFmFmEmployeeTripDetailPO> getAllTodayTripDetails(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequest);
	public void update(EFmFmEmployeeRequestMasterPO eFmFmEmployeeRequestMasterPO);
	public List<EFmFmEmployeeTravelRequestPO> getAllTodaysActiveRequests(int clientId);
	public List<EFmFmEmployeeTripDetailPO> getParticularTriprEmployeeBoardedStatus(
			int requestId, int assignRouteId);
	public List<EFmFmEmployeeTravelRequestPO> getAllResheduleRequests(int projectId,
			int branchId);
	public List<EFmFmEmployeeTripDetailPO> getParticularTripNonDropEmployeesDetails(
			int assignRouteId);
	public void deleteParticularTripDetail(int empTripId);
	public List<EFmFmEmployeeTripDetailPO> getrequestStatusFromBranchIdAndRequestId(
			int branchId, int requestId);
	public List<EFmFmEmployeeTripDetailPO> getDropTripAllSortedEmployees(
			int assignRouteId);
	public List<EFmFmEmployeeTravelRequestPO> getAllTodaysRequestForParticularEmployee(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequest);
	public List<EFmFmEmployeeRequestMasterPO> getEmplyeeRequestsForSameDateAndShiftTime(
			Date date, Time siftTime, int branchId, int userId, String tripType);
	public List<EFmFmEmployeeTravelRequestPO> getEmplyeeRequestsForSameDateAndShiftTimeFromTravelReq(
			Date date, Time siftTime, int branchId, int userId, String tripType);
	public void save(EFmFmEmployeeTripDetailPO employeeTripDetailPO);
	public List<EFmFmEmployeeRequestMasterPO> getRequestFromRequestMaster(int tripId,
			int branchId);
	public void deleteParticularRequest(int requestId);
	public void deleteParticularRequestFromRequestMaster(int tripId);
	public List<EFmFmEmployeeRequestMasterPO> getAllRequestDetailsFromRequestMasterFromBranchId(
			int branchId);
	public List<EFmFmAssignRoutePO> closeVehicleCapacity(int checkInId, int branchId);
	public List<EFmFmEmployeeRequestMasterPO> getAllRequestFromRequestMasterFprParticularEmployee(
			int userId, int branchId);
	public List<EFmFmAssignRoutePO> getLastRouteDetails(int checkInId, int branchId,
			String tripType);
	public List<EFmFmEmployeeTravelRequestPO> getParticularRequestDetailOnTripComplete(
			int branchId, int requestId);
	public List<EFmFmEmployeeTripDetailPO> getAllocatedEmployeeDetail(int userId,
			int branchId, Date todayDate);
	public List<EFmFmEmployeeTravelRequestPO> assignCabRequestToParticularShiftOrRouteEmployees(
			int branchId, String tripType, Time siftTime, int zoneId);
	//Cab allocation method
	public List<EFmFmEmployeeRequestMasterPO> getParticularEmployeeMasterRequestDetails(
			int branchId, int tripId);
	public List<EFmFmEmployeeTravelRequestPO> getAllParticularRouteRequest(
			int branchId, int zoneId, Time shiftTime);
	public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleLessCapacity(
			int branchId, int capacity);
	public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleLargeCapacity(
			int branchId, int capacity);
	public List<EFmFmAssignRoutePO> getHalfCompletedAssignRouteFromCheckInId(
			int branchId, int zoneId, String reqType, Time shiftTime,
			int checkInId);
	public List<EFmFmAssignRoutePO> getHalfCompletedAssignRoute(int branchId,
			int zoneId, String reqType, Time shiftTime);
	public List<EFmFmEscortCheckInPO> getAllCheckedInEscort(int branchId);
	public List<EFmFmVehicleMasterPO> getVehicleDetailsFromClientIdForSmallerRoute(
			int branchId, String vehicleId);
	public List<EFmFmEmployeeTripDetailPO> getRequestStatusFromBranchIdAndRequestId(
			int branchId, int requestId);
	public void deleteParticularRequestFromEmployeeTripDetail(int empTripId);
	public EFmFmVehicleMasterPO getVehicleDetail(int vehicleId);
	public List<EFmFmEmployeeTravelRequestPO> assignCabRequestToParticularShiftEmployees(
			int branchId, String tripType, Time siftTime);
	public  List<EFmFmEmployeeTravelRequestPO> getAllActiveRequests(int branchId);
	public long getAllActivePickUpRequestCounter(int branchId);
	public long getAllActivePickupInProgressEmployeeRequestCounter(int branchId);
	public long getAllActiveNoShowEmployeeRequestCounter(int branchId);
	public long getAllActiveBoardedEmployeeRequestCounter(int branchId);
	public long getAllActiveDropRequestCounter(int branchId);
	public long getAllActiveDropedEmployeeRequestCounter(int branchId);
	public long getAllActiveDropNoShowEmployeeRequestCounter(int branchId);
	public long getAllActiveDropInProgressEmployeeRequestCounter(int branchId);
	public long getAllPickupScheduleActiveRequests(int branchId);
	public long getAllDropScheduleActiveRequests(int branchId);
	public List<EFmFmEmployeeTravelRequestPO> getAnotherActiveRequestDetail(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO);
	public List<EFmFmEmployeeTravelRequestPO> getAnotherActiveRequestForNextDate(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO);
	public List<EFmFmEmployeeTravelRequestPO> particularEmployeeRequestFromEmpId(
			int branchId, String employeeId);
	public List<EFmFmEmployeeTravelRequestPO> listOfTravelRequestForAdminShiftWise(
			EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO);
	public long getAllActiveDropOrPickupRequestCounterForGuest(int branchId);
	public long getAllActiveNoShowGuestRequestCounter(int branchId);
	public long getAllActiveBoardedGuestRequestCounter(int branchId);
	public long getAllScheduleActiveRequestsForGuest(int branchId);
	public List<EFmFmEmployeeTravelRequestPO> listOfTravelRequestByShiftWice(
			int branchId, Time shiftTime);
	public List<EFmFmEmployeeTravelRequestPO> listOfAdhocAndGuestTravelRequests(
			int branchId);
	public List<EFmFmEmployeeTravelRequestPO> getAllDropScheduleActiveRequestsDetails(
			int branchId);
	public List<EFmFmEmployeeTripDetailPO> getAllActiveDropedEmployeeRequestsDetails(
			int branchId);
	public List<EFmFmEmployeeTripDetailPO> getAllActiveDropNoShowEmployeeRequestsDetails(
			int branchId);
	public List<EFmFmEmployeeTravelRequestPO> getAllPickupScheduleActiveRequestsDetails(
			int branchId);
	public List<EFmFmEmployeeTripDetailPO> getAllActivePickupBoardedEmployeeRequestsDetails(
			int branchId);
	public List<EFmFmEmployeeTripDetailPO> getAllActivePickupNoShowEmployeeRequestsDetails(
			int branchId);
	public List<EFmFmEmployeeTravelRequestPO> getAllActiveDropRequestDetails(
			int branchId);
	public List<EFmFmEmployeeTravelRequestPO> getAllActivePickUpRequestDetails(
			int branchId);
	public List<EFmFmEmployeeTripDetailPO> getNonDropTripAllSortedEmployees(
			int assignRouteId);
	public List<EFmFmEmployeeTripDetailPO> getEmployeeLiveTripDetailFromUserId(
			int userId, int branchId);
	public List<EFmFmTripTimingMasterPO> getParticularShiftTimeDetail(int branchId,
			Time shiftTime);
	public List<EFmFmTripTimingMasterPO> listOfShiftTimeByTripType(int branchId,
			String tripType);
	public List<EFmFmEmployeeRequestMasterPO> getAllRequestDetailsFromRequestMasterFromBranchIdByTripType(
			int branchId, String tripType);
	public List<EFmFmEmployeeTravelRequestPO> getAllScheduleActiveGuestRequestsDetails(
			int branchId);
	public List<EFmFmEmployeeTripDetailPO> getAllActiveBoardedOrDroppedEmployeeRequestsDetailsForGuest(
			int branchId);
	public List<EFmFmEmployeeTravelRequestPO> getAllActiveGuestRequestsDetails(
			int branchId);
	public List<EFmFmEmployeeTripDetailPO> getAllActiveGuestNoShowRequestsDetails(
			int branchId);
	public List<EFmFmEmployeeTravelRequestPO> particularEmployeePickupRequestFromUserId(
			int branchId, int userId, String tripType);
	public List<EFmFmEmployeeTravelRequestPO> deleteCurentRequestfromTraveldesk(
			int branchId, int tripId);
	public List<EFmFmTripTimingMasterPO> getShiftTimeDetailFromShiftTimeAndTripType(
			int branchId, Time shiftTime, String tripType);
	public List<EFmFmTripTimingMasterPO> getParticularShiftTimeDetailByTripType(
			int branchId, Time shiftTime, String tripType);
			public List<EFmFmEmployeeTravelRequestPO> listOfTravelRequestByShiftTripType(int branchId, Time shiftTime, String tripType,	String todaysDate);
	public List<EFmFmClientBranchPO> getBranchDetails(int branchId);
	public List<EFmFmEmployeeTravelRequestPO> getparticularRequestwithShiftTime(EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO);
	public List<EFmFmAssignRoutePO> getCreatedAssignRoute(int branchId, String reqType, Time shiftTime);
	public List<EFmFmEmployeeTripDetailPO> getDropTripByAlgoRouteSortedEmployees(int assignRouteId);
	public void deleteParticularTripDetailByRouteId(int routeId);
	public List<EFmFmEmployeeTravelRequestPO> getparticularEmployeeRequest(String todaysDate, String employeeId,String tripType, int branchId, String shiftTime);
	public EFmFmEmployeeTripDetailPO ParticularTripDetail(int empTripId);
	public List<EFmFmEmployeeTripDetailPO> getparticularEmployeeTripDetails(String todaysDate, String employeeId,String tripType, int branchId, String shiftTime);
	public List<EFmFmEmployeeRequestMasterPO> getParticularRequestDetailFromUserIdAndTripType(
			int userId, int branchId, String tripType);
	
}
