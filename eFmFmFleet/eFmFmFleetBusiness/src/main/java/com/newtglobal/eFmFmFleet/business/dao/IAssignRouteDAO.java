package com.newtglobal.eFmFmFleet.business.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newtglobal.eFmFmFleet.model.EFmFmActualRoutTravelledPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;

public interface IAssignRouteDAO {

	
	/*
	 * employee Travelled route entry data
	 */
	public void save(EFmFmActualRoutTravelledPO  actualRoutTravelledPO);
	public void update(EFmFmActualRoutTravelledPO  actualRoutTravelledPO);	
	
	public List<EFmFmAssignRoutePO> getAllTodaysTrips(EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmAssignRoutePO> closeParticularTrips(
			EFmFmAssignRoutePO assignRoutePO);
	public void update(EFmFmAssignRoutePO eFmFmAssignRoutePO);
	public List<EFmFmAssignRoutePO> getAllLiveTrips(EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmAssignRoutePO> getAllRoutesOfParticularZone(
			EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmAssignRoutePO> getAllOnlyAssignedTrips(
			EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmActualRoutTravelledPO> getEtaAndDistanceFromAssignRouteId(
			EFmFmActualRoutTravelledPO actualRouteTravelledPO);
	public List<EFmFmAssignRoutePO> getAllTodaysCompletedTrips(
			EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmAssignRoutePO> getTripCountByDate(Date fromDate, Date toDate,
			String tripType, int clientId);
	public List<EFmFmAssignRoutePO> getAllTripDetails(EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmAssignRoutePO> getAllTripByDate(Date fromDate, Date toDate,
			int clientId);
	public List<EFmFmAssignRoutePO> getAllActiveTrips(EFmFmAssignRoutePO assignRoutePO);
	public void deleteParticularAssignRoute(int assignRouteId);
	public void deleteParticularActualTravelled(int travelId);
	public List<EFmFmAssignRoutePO> getAllRoutesOfParticularBranch(
			EFmFmAssignRoutePO assignRoutePO);
	public void save(EFmFmAssignRoutePO eFmFmAssignRoutePO);
	public List<EFmFmAssignRoutePO> getAllRoutesBasedOnTripTypeAndShiftTime(
			EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmAssignRoutePO> getAllRoutesInsideZone(int branchId, int zoneId);
	public long getPickupVehiclesOnRoadCounter(int branchId);
	public long getDropVehiclesOnRoadCounter(int branchId);
	public long getAllVehiclesOnRoadCounter(int branchId);
	public List<EFmFmActualRoutTravelledPO> getCabLocationFromAssignRouteId(
			EFmFmActualRoutTravelledPO actualRouteTravelledPO);
	public List<EFmFmActualRoutTravelledPO> getLastEtaFromAssignRouteId(
			EFmFmActualRoutTravelledPO actualRouteTravelledPO);
	public List<EFmFmAssignRoutePO> getAllBucketClosedRoutes(
			EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmAssignRoutePO> getAllStartedRoutes(
			EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmAssignRoutePO> getAllClosedRoutes(String tripType,
			Time shiftTime, int branchId);
	public List<EFmFmAssignRoutePO> getAllOpenBucketRoutes(
			EFmFmAssignRoutePO assignRoutePO);
	public List<EFmFmAssignRoutePO> getAllTripsTravelledAndPlannedDistanceByDate(
			Date fromDate, Date toDate, int branchId, int vendorId);
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByDate(Date fromDate,
			Date toDate, int branchId);
	public List<EFmFmAssignRoutePO> getAllTripsTravelledAndPlannedDistanceByDateAndVehicle(
			Date fromDate, Date toDate, int branchId, int vehicleId);
	public List<EFmFmAssignRoutePO> getAllTripsTravelledAndPlannedDistanceByAllVendor(
			Date fromDate, Date toDate, int branchId);
	public String getVendorNameTravelledAndPlannedDistanceByAllVendor(Date fromDate,
			Date toDate, int branchId);
	public List<Date> getAllTripsDistinctDates(Date fromDate, Date toDate, int branchId,
			String tripType);
//All function Counts for For Date Wice
	public long getAllTripsCountByDate(Date fromDate, Date toDate, int branchId,
			String tripType);
	public List<EFmFmAssignRoutePO> getAllTripsByDate(Date fromDate, Date toDate, int branchId,
			String tripType);
	public long getAllEmployeesCountByDate(Date fromDate, Date toDate, int branchId,
			String tripType);
	public long getNoShowEmployeesCountByDate(Date fromDate, Date toDate,
			int branchId, String tripType);
	public long getPickedUpEmployeesCountByDate(Date fromDate, Date toDate,
			int branchId, String tripType);
	
	public long getAllDelayTripsCountByDate(Date fromDate, Date toDate, int branchId,
			String tripType);
	public long getAllDelayTripsBeyondLoginTimeCountByDate(Date fromDate, Date toDate,
			int branchId, String tripType);
	public List<Time> getAllTripsByShift(Date fromDate, Date toDate, int branchId,
			String tripType, Time shiftTime);
	public List<Date> getAllTripsByShiftByVendor(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftTime, int vendorId);
	public List<Date> getAllTripsByByVendorId(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId);
	
	//All function Counts Shift Wice
	public long getAllTripsCountByShift(Date fromDate, Date toDate, int branchId,
			String tripType,Time shiftWice);
	public List<EFmFmAssignRoutePO> getAllTripsDetailsByShift(Date fromDate, Date toDate, int branchId,
			String tripType,Time shiftWice);
	public long getAllEmployeesCountByShift(Date fromDate, Date toDate, int branchId,
			String tripType,Time shiftWice);
	public long getNoShowEmployeesCountByShift(Date fromDate, Date toDate,
			int branchId, String tripType,Time shiftWice);
	public long getPickedUpEmployeesCountByShift(Date fromDate, Date toDate,
			int branchId, String tripType,Time shiftWice);
	public List<Time> getAllTripsByShiftByVendorId(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId,Time shiftTime);
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByIdAndName(
			Date fromDate, Date toDate, int branchId, String tripType,Time shiftTime);
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByEmployeeId(
			Date fromDate, Date toDate, int branchId, String tripType,
			Time shiftTime, String employeeId);
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByEmployeeIdDateWise(
			Date fromDate, Date toDate, int branchId, String tripType,
			String employeeId);
	public List<EFmFmAssignRoutePO> getAllTripsDetailsByShiftByVendor(Date fromDate,
			Date toDate, int branchId, String tripType, Time shiftTime,
			int vendorId);
	public List<EFmFmAssignRoutePO> getAllTripsDetailsByVendorWiseOnly(Date fromDate,
			Date toDate, int branchId, String tripType, int vendorId);
	public List<Time> getAllTripsByAllShifts(Date fromDate, Date toDate, int branchId,
			String tripType);
	public List<Time> getAllTripsByAllShiftsForVendor(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId);
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByDateWise(
			Date fromDate, Date toDate, int branchId, String tripType);
	public List<EFmFmAssignRoutePO> getAllTripsVehicleDetailsByVehicleNumber(
			Date fromDate, Date toDate, int branchId, String vehicleNumber);
	public List<EFmFmAssignRoutePO> getAllTripsVehicleKMDetailsByShiftTime(
			Date fromDate, Date toDate, int branchId, Time shiftTime);
	public List<EFmFmEmployeeTripDetailPO> getAllMessageEmployeesMessageDetailsByDate(
			Date fromDate, Date toDate, int branchId);
	public long getUsedFleetByByVendorId(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId);
	public long getNoShowEmployeesCountByDateByVendor(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId);
	public long getPickedUpEmployeesCountByDateByVendor(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId);
	public long getPickedUpEmployeesCountByShiftByVendor(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftTime, int vendorId);
	public long getNoShowEmployeesCountByShiftByVendor(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftTime, int vendorId);
	public long getUsedFleetByByVendorIdByShift(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId, Time shiftTime);
	public long getAllEmployeesCountByDateByVendorId(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId);
	public long getAllEmployeesCountByShiftByVendorId(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftTime, int vendorId);
	public List<EFmFmAssignRoutePO> getTodayTripByShift(Date fromDate, Date toDate, String tripType, String ShifTime,
			int branchId);
	public List<EFmFmAssignRoutePO> getExportTodayTrips(Date fromDate, Date toDate, String tripType, String ShifTime,int branchId);
	public List<EFmFmAssignRoutePO> getAllEscortRequiredTripsByDate(Date fromDate,
			Date toDate, int branchId);
	public List<Date> getAllTripsByDistinctDates(Date fromDate, Date toDate,
			int branchId);
	public long getPickedUpOrDroppedEmployeesCountByDate(Date fromDate, Date toDate,
			int branchId, String tripType);
	public List<ArrayList> getAllTripsByDistinctDatesAndDeriverId(Date fromDate,
			Date toDate, int branchId);
	public List<EFmFmAssignRoutePO> getAllTripByDateByDeriverId(Date fromDate,
			Date toDate, int branchId, int driverId);
	public List<EFmFmAssignRoutePO> getAllTripsByDatesAndDriverId(Date fromDate,
			Date toDate, int branchId, int driverId);
}
