package com.newtglobal.eFmFmFleet.business.bo.boImpl;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtglobal.eFmFmFleet.business.bo.IAssignRouteBO;
import com.newtglobal.eFmFmFleet.business.dao.IAssignRouteDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmActualRoutTravelledPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;

@Service("IAssignRouteBO")
public class IAssignRouteBOImpl implements IAssignRouteBO {
	
	@Autowired
	IAssignRouteDAO iAssignRouteDAO;
	public void setIUserMasterDAO(IAssignRouteDAO iAssignRouteDAO) {
		this.iAssignRouteDAO = iAssignRouteDAO;
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTodaysTrips(
			EFmFmAssignRoutePO assignRoutePO) {
		return iAssignRouteDAO.getAllTodaysTrips(assignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> closeParticularTrips(
			EFmFmAssignRoutePO assignRoutePO) {
		return iAssignRouteDAO.closeParticularTrips(assignRoutePO);
	}

	@Override
	public void update(EFmFmAssignRoutePO eFmFmAssignRoutePO) {
		iAssignRouteDAO.update(eFmFmAssignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllLiveTrips(
			EFmFmAssignRoutePO assignRoutePO) {
		return iAssignRouteDAO.getAllLiveTrips(assignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllRoutesOfParticularZone(
			EFmFmAssignRoutePO assignRoutePO) {
		return iAssignRouteDAO.getAllRoutesOfParticularZone(assignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllOnlyAssignedTrips(
			EFmFmAssignRoutePO assignRoutePO) {
		return iAssignRouteDAO.getAllOnlyAssignedTrips(assignRoutePO);
	}

	@Override
	public void save(EFmFmActualRoutTravelledPO actualRoutTravelledPO) {
		iAssignRouteDAO.save(actualRoutTravelledPO);
	}

	@Override
	public void update(EFmFmActualRoutTravelledPO actualRoutTravelledPO) {
		iAssignRouteDAO.update(actualRoutTravelledPO);
	}

	@Override
	public List<EFmFmActualRoutTravelledPO> getEtaAndDistanceFromAssignRouteId(
			EFmFmActualRoutTravelledPO actualRouteTravelledPO) {
		return iAssignRouteDAO.getEtaAndDistanceFromAssignRouteId(actualRouteTravelledPO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTodaysCompletedTrips(
			EFmFmAssignRoutePO assignRoutePO) {
		return iAssignRouteDAO.getAllTodaysCompletedTrips(assignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getTripCountByDate(Date fromDate,
			Date toDate, String tripType, int clientId) {
		return iAssignRouteDAO.getTripCountByDate(fromDate, toDate, tripType, clientId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripDetails(
			EFmFmAssignRoutePO assignRoutePO) {
		return iAssignRouteDAO.getAllTripDetails(assignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripByDate(Date fromDate,
			Date toDate, int clientId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripByDate(fromDate, toDate, clientId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllActiveTrips(
			EFmFmAssignRoutePO assignRoutePO) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllActiveTrips(assignRoutePO);
	}

	@Override
	public void deleteParticularAssignRoute(int assignRouteId) {
		// TODO Auto-generated method stub
		iAssignRouteDAO.deleteParticularAssignRoute(assignRouteId);
	}

	@Override
	public void deleteParticularActualTravelled(int travelId) {
		// TODO Auto-generated method stub
		iAssignRouteDAO.deleteParticularActualTravelled(travelId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllRoutesOfParticularBranch(
			EFmFmAssignRoutePO assignRoutePO) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllRoutesOfParticularBranch(assignRoutePO);
	}

	@Override
	public void save(EFmFmAssignRoutePO eFmFmAssignRoutePO) {
		// TODO Auto-generated method stub
		iAssignRouteDAO.save(eFmFmAssignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllRoutesBasedOnTripTypeAndShiftTime(
			EFmFmAssignRoutePO assignRoutePO) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllRoutesBasedOnTripTypeAndShiftTime(assignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllRoutesInsideZone(int branchId,
			int zoneId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllRoutesInsideZone(branchId, zoneId);
	}

	@Override
	public long getPickupVehiclesOnRoadCounter(int branchId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getPickupVehiclesOnRoadCounter(branchId);
	}

	@Override
	public long getDropVehiclesOnRoadCounter(int branchId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getDropVehiclesOnRoadCounter(branchId);
	}

	@Override
	public long getAllVehiclesOnRoadCounter(int branchId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllVehiclesOnRoadCounter(branchId);
	}

	@Override
	public List<EFmFmActualRoutTravelledPO> getCabLocationFromAssignRouteId(
			EFmFmActualRoutTravelledPO actualRouteTravelledPO) {
		return iAssignRouteDAO.getCabLocationFromAssignRouteId(actualRouteTravelledPO);
	}

	@Override
	public List<EFmFmActualRoutTravelledPO> getLastEtaFromAssignRouteId(
			EFmFmActualRoutTravelledPO actualRouteTravelledPO) {
		return iAssignRouteDAO.getLastEtaFromAssignRouteId(actualRouteTravelledPO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllBucketClosedRoutes(
			EFmFmAssignRoutePO assignRoutePO) {
		return iAssignRouteDAO.getAllBucketClosedRoutes(assignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllStartedRoutes(
			EFmFmAssignRoutePO assignRoutePO) {
		return iAssignRouteDAO.getAllStartedRoutes(assignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllClosedRoutes(String tripType,
			Time shiftTime, int branchId) {
		return iAssignRouteDAO.getAllClosedRoutes(tripType, shiftTime, branchId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllOpenBucketRoutes(
			EFmFmAssignRoutePO assignRoutePO) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllOpenBucketRoutes(assignRoutePO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripsTravelledAndPlannedDistanceByDate(
			Date fromDate, Date toDate, int branchId, int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsTravelledAndPlannedDistanceByDate(fromDate, toDate, branchId, vendorId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByDate(Date fromDate,
			Date toDate, int branchId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllNoShowEmployeesByDate(fromDate, toDate, branchId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripsTravelledAndPlannedDistanceByDateAndVehicle(
			Date fromDate, Date toDate, int branchId, int vehicleId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsTravelledAndPlannedDistanceByDateAndVehicle(fromDate, toDate, branchId, vehicleId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripsTravelledAndPlannedDistanceByAllVendor(
			Date fromDate, Date toDate, int branchId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsTravelledAndPlannedDistanceByAllVendor(fromDate, toDate, branchId);
	}

	@Override
	public String getVendorNameTravelledAndPlannedDistanceByAllVendor(
			Date fromDate, Date toDate, int branchId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getVendorNameTravelledAndPlannedDistanceByAllVendor(fromDate, toDate, branchId);
	}

	@Override
	public List<Date> getAllTripsDistinctDates(Date fromDate, Date toDate,
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsDistinctDates(fromDate, toDate, branchId, tripType);
	}

	@Override
	public long getAllTripsCountByDate(Date fromDate, Date toDate,
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsCountByDate(fromDate, toDate, branchId, tripType);
	}

	@Override
	public long getAllDelayTripsCountByDate(Date fromDate, Date toDate,
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllDelayTripsCountByDate(fromDate, toDate, branchId, tripType);
	}

	@Override
	public long getAllDelayTripsBeyondLoginTimeCountByDate(Date fromDate,
			Date toDate, int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllDelayTripsBeyondLoginTimeCountByDate(fromDate, toDate, branchId, tripType);
	}

	@Override
	public long getAllEmployeesCountByDate(Date fromDate, Date toDate,
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllEmployeesCountByDate(fromDate, toDate, branchId, tripType);
	}

	@Override
	public long getNoShowEmployeesCountByDate(Date fromDate, Date toDate,
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getNoShowEmployeesCountByDate(fromDate, toDate, branchId, tripType);
	}

	@Override
	public long getPickedUpEmployeesCountByDate(Date fromDate, Date toDate,
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getPickedUpEmployeesCountByDate(fromDate, toDate, branchId, tripType);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripsByDate(Date fromDate, Date toDate,
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsByDate(fromDate, toDate, branchId, tripType);
	}

	@Override
	public List<Time> getAllTripsByShift(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftTime) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsByShift(fromDate, toDate, branchId, tripType, shiftTime);
	}

	@Override
	public List<Date> getAllTripsByShiftByVendor(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftTime, int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsByShiftByVendor(fromDate, toDate, branchId, tripType, shiftTime, vendorId);
	}

	@Override
	public List<Date> getAllTripsByByVendorId(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsByByVendorId(fromDate, toDate, branchId, tripType, vendorId);
	}

	@Override
	public long getAllTripsCountByShift(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftWice) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsCountByShift(fromDate, toDate, branchId, tripType, shiftWice);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripsDetailsByShift(Date fromDate,
			Date toDate, int branchId, String tripType, Time shiftWice) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsDetailsByShift(fromDate, toDate, branchId, tripType, shiftWice);
	}

	@Override
	public long getAllEmployeesCountByShift(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftWice) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllEmployeesCountByShift(fromDate, toDate, branchId, tripType, shiftWice);
	}

	@Override
	public long getNoShowEmployeesCountByShift(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftWice) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getNoShowEmployeesCountByShift(fromDate, toDate, branchId, tripType, shiftWice);
	}

	@Override
	public long getPickedUpEmployeesCountByShift(Date fromDate, Date toDate,
			int branchId, String tripType, Time shiftWice) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getPickedUpEmployeesCountByShift(fromDate, toDate, branchId, tripType, shiftWice);
	}

	@Override
	public List<Time> getAllTripsByShiftByVendorId(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId, Time shiftTime) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsByShiftByVendorId(fromDate, toDate, branchId, tripType, vendorId, shiftTime);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByIdAndName(
			Date fromDate, Date toDate, int branchId, String tripType,Time shiftTime) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllNoShowEmployeesByIdAndName(fromDate, toDate, branchId, tripType,shiftTime);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByEmployeeId(
			Date fromDate, Date toDate, int branchId, String tripType,
			Time shiftTime, String employeeId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllNoShowEmployeesByEmployeeId(fromDate, toDate, branchId, tripType, shiftTime, employeeId);		
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByEmployeeIdDateWise(
			Date fromDate, Date toDate, int branchId, String tripType,
			String employeeId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllNoShowEmployeesByEmployeeIdDateWise(fromDate, toDate, branchId, tripType, employeeId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripsDetailsByShiftByVendor(
			Date fromDate, Date toDate, int branchId, String tripType,
			Time shiftWice, int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsDetailsByShiftByVendor(fromDate, toDate, branchId, tripType, shiftWice, vendorId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripsDetailsByVendorWiseOnly(
			Date fromDate, Date toDate, int branchId, String tripType,
			int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsDetailsByVendorWiseOnly(fromDate, toDate, branchId, tripType, vendorId);
	}

	@Override
	public List<Time> getAllTripsByAllShifts(Date fromDate, Date toDate,
			int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsByAllShifts(fromDate, toDate, branchId, tripType);
	}

	@Override
	public List<Time> getAllTripsByAllShiftsForVendor(Date fromDate,
			Date toDate, int branchId, String tripType, int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsByAllShiftsForVendor(fromDate, toDate, branchId, tripType, vendorId);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllNoShowEmployeesByDateWise(
			Date fromDate, Date toDate, int branchId, String tripType) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllNoShowEmployeesByDateWise(fromDate, toDate, branchId, tripType);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripsVehicleDetailsByVehicleNumber(
			Date fromDate, Date toDate, int branchId, String vehicleNumber) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsVehicleDetailsByVehicleNumber(fromDate, toDate, branchId, vehicleNumber);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllTripsVehicleKMDetailsByShiftTime(
			Date fromDate, Date toDate, int branchId, Time shiftTime) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllTripsVehicleKMDetailsByShiftTime(fromDate, toDate, branchId, shiftTime);
	}

	@Override
	public List<EFmFmEmployeeTripDetailPO> getAllMessageEmployeesMessageDetailsByDate(
			Date fromDate, Date toDate, int branchId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getAllMessageEmployeesMessageDetailsByDate(fromDate, toDate, branchId);
	}

	@Override
	public long getUsedFleetByByVendorId(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getUsedFleetByByVendorId(fromDate, toDate, branchId, tripType, vendorId);
	}

	@Override
	public long getNoShowEmployeesCountByDateByVendor(Date fromDate,
			Date toDate, int branchId, String tripType, int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getNoShowEmployeesCountByDateByVendor(fromDate, toDate, branchId, tripType, vendorId);
	}

	@Override
	public long getPickedUpEmployeesCountByDateByVendor(Date fromDate,
			Date toDate, int branchId, String tripType, int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getPickedUpEmployeesCountByDateByVendor(fromDate, toDate, branchId, tripType, vendorId);
	}

	@Override
	public long getPickedUpEmployeesCountByShiftByVendor(Date fromDate,
			Date toDate, int branchId, String tripType, Time shiftTime,
			int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getPickedUpEmployeesCountByShiftByVendor(fromDate, toDate, branchId, tripType, shiftTime, vendorId);
	}

	@Override
	public long getNoShowEmployeesCountByShiftByVendor(Date fromDate,
			Date toDate, int branchId, String tripType, Time shiftTime,
			int vendorId) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getNoShowEmployeesCountByShiftByVendor(fromDate, toDate, branchId, tripType, shiftTime, vendorId);
	}

	@Override
	public long getUsedFleetByByVendorIdByShift(Date fromDate, Date toDate,
			int branchId, String tripType, int vendorId, Time shiftTime) {
		// TODO Auto-generated method stub
		return iAssignRouteDAO.getUsedFleetByByVendorIdByShift(fromDate, toDate, branchId, tripType, vendorId, shiftTime);
	}

}
