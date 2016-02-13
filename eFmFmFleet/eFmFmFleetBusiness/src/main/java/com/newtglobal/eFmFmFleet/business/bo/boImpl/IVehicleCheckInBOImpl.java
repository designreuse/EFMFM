package com.newtglobal.eFmFmFleet.business.bo.boImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.business.dao.IVehicleCheckInDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmFixedDistanceContractDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripBasedContractDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleInspectionPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorContractInvoicePO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorContractTypeMasterPO;

@Service("IVehicleCheckInBO")
public class IVehicleCheckInBOImpl implements IVehicleCheckInBO  {

	
	@Autowired
	private IVehicleCheckInDAO iVehicleCheckInDAO;	
	public void setiVehicleCheckInDAO(IVehicleCheckInDAO iVehicleCheckInDAO) {
		this.iVehicleCheckInDAO = iVehicleCheckInDAO;
	}
	@Override
	public void save(EFmFmVehicleInspectionPO eFmFmVehicleInspectionPO) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.save(eFmFmVehicleInspectionPO);
	}
	
	@Override
	public void save(EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.save(eFmFmDeviceMasterPO);
	}
	@Override
	public void update(EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.update(eFmFmDeviceMasterPO);
	}
	@Override
	public void delete(EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.delete(eFmFmDeviceMasterPO);
	}

	
	
	@Override
	public void save(EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		iVehicleCheckInDAO.save(eFmFmVehicleMasterPO);
		
	}
	@Override
	public void update(EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		iVehicleCheckInDAO.update(eFmFmVehicleMasterPO);
		
	}
	@Override
	public void delete(EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {
		iVehicleCheckInDAO.delete(eFmFmVehicleMasterPO);
		
	}
	@Override
	public EFmFmVehicleMasterPO getParticularVehicleDetails(String vehicleNumber,int branchId) {		
		return iVehicleCheckInDAO.getParticularVehicleDetails(vehicleNumber,branchId);
	}
	@Override
	public EFmFmVehicleMasterPO getParticularVehicleDetail(int vehicleId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getParticularVehicleDetail(vehicleId);
	}
	@Override
	public List<EFmFmVehicleMasterPO> getAllVehicleDetails(EFmFmVehicleMasterPO eFmFmVehicleMasterPO) {		
		return iVehicleCheckInDAO.getAllVehicleDetails(eFmFmVehicleMasterPO);
	}
	@Override
	public List<EFmFmDriverMasterPO> getAllDriverDetails(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		return iVehicleCheckInDAO.getAllDriverDetails(eFmFmDriverMasterPO);
	}
	@Override
	public List<EFmFmDriverMasterPO> randomDriverDetails(int vendorId,Date todayDate) {		
		return iVehicleCheckInDAO.randomDriverDetails(vendorId,todayDate);
	}
	
	@Override
	public void vehicleDriverCheckIn(EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		iVehicleCheckInDAO.vehicleDriverCheckIn(eFmFmVehicleCheckInPO);
		
	}
	@Override
	public List<EFmFmVehicleMasterPO> getAvailableVehicleDetails(int vendorId,Date todayDate) {
		return iVehicleCheckInDAO.getAvailableVehicleDetails(vendorId,todayDate);
	}
	@Override
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetails(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {		
		return iVehicleCheckInDAO.getCheckedInVehicleDetails(eFmFmVehicleCheckInPO);
	}
	
	@Override
	public List<EFmFmEscortMasterPO> getAllEscortDetails(
			EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllEscortDetails(eFmFmEscortMasterPO);
	}
	@Override
	public List<EFmFmEscortMasterPO> getParticularEscortDetails(
			EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getParticularEscortDetails(eFmFmEscortMasterPO);
	}
	@Override
	public void saveEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.saveEscortDetails(eFmFmEscortMasterPO);
	}
	@Override
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetails(int branchId,
			Date date) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getCheckedInVehicleDetails(branchId, date);
	}
	@Override
	public List<EFmFmVehicleCheckInPO> getParticulaCheckedInVehicleDetails(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);
	}
	@Override
	public int getNumberOfVehiclesFromClientId(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getNumberOfVehiclesFromClientId(branchId);
	}
	@Override
	public List<EFmFmVehicleCheckInPO> getAssignedVehicleDetails(int branchId,
			Date todayDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAssignedVehicleDetails(branchId, todayDate);
	}
	@Override
	public void update(EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.update(eFmFmVehicleCheckInPO);
	}
	@Override
	public List<EFmFmEscortCheckInPO> getAllCheckedInEscort(int branchId,
			Date todayDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllCheckedInEscort(branchId, todayDate);
	}
	@Override
	public List<EFmFmEscortMasterPO> getAllCheckInEscort(int branchId,
			Date todayDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllCheckInEscort(branchId, todayDate);
	}
	@Override
	public void saveEscortCheckIn(EFmFmEscortCheckInPO eFmFmEscortCheckInPO) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.saveEscortCheckIn(eFmFmEscortCheckInPO);
	}
	
	@Override
	public List<EFmFmVehicleCheckInPO> checkedOutParticularDriver(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.checkedOutParticularDriver(eFmFmVehicleCheckInPO);
	}
	
	@Override
	public void updateEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.updateEscortDetails(eFmFmEscortMasterPO);
	}
	@Override
	public EFmFmVehicleCheckInPO getCheckInDriverDetails(int driverId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getCheckInDriverDetails(driverId);
	}

	@Override
	public List<EFmFmDeviceMasterPO> deviceNumberExistsCheck(
			EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.deviceNumberExistsCheck(eFmFmDeviceMasterPO);
	}

	@Override
	public List<EFmFmDeviceMasterPO> deviceImeiNumberExistsCheck(
			EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.deviceImeiNumberExistsCheck(eFmFmDeviceMasterPO);
	}

	

	@Override
	public List<EFmFmDriverMasterPO> getParticularDriverDetailsFromMobileNum(
			String mobileNumber, int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getParticularDriverDetailsFromMobileNum(mobileNumber, branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInDriver(
			EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getParticularCheckedInDriver(eFmFmVehicleCheckInPO);
	}

	@Override
	public List<EFmFmTripAlertsPO> getGroupByDriver(int branchId,
			Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getGroupByDriver(branchId, fromDate, toDate);
	}

	@Override
	public List<EFmFmTripAlertsPO> getScoreCardDriver(int branchId,
			int driverId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getScoreCardDriver(branchId, driverId, fromDate, toDate);
	}
	@Override
	public List<EFmFmAssignRoutePO> getDriverAssignedTrip(int branchId,
			int driverId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getDriverAssignedTrip(branchId, driverId, fromDate, toDate);
	}

	@Override
	public List<EFmFmTripAlertsPO> getGroupByVehicle(int branchId,
			Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getGroupByVehicle(branchId, fromDate, toDate);
	}

	@Override
	public List<EFmFmTripAlertsPO> getScoreCardVehicle(int branchId,
			int driverId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getScoreCardVehicle(branchId, driverId, fromDate, toDate);
	}

	@Override
	public List<EFmFmAssignRoutePO> getVehicleAssignedVehicleTrip(int branchId,
			int driverId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getVehicleAssignedVehicleTrip(branchId, driverId, fromDate, toDate);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getRcNumberDetails(String rcNumber,
			int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getRcNumberDetails(rcNumber, branchId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getEngineNoDetails(String engineNo,
			int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getEngineNoDetails(engineNo, branchId);
	}

	@Override
	public List<EFmFmEscortMasterPO> getMobileNoDetails(
			EFmFmEscortMasterPO eFmFmEscortMasterPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getMobileNoDetails(eFmFmEscortMasterPO);
	}

	@Override
	public List<EFmFmDeviceMasterPO> getListOfAllActiveDevices(
			EFmFmDeviceMasterPO eFmFmDeviceMasterPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getListOfAllActiveDevices(eFmFmDeviceMasterPO);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getCheckInDetails(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getCheckInDetails(branchId);
	}

	@Override
	public List<EFmFmDriverMasterPO> getAllActiveDriverDetails(int vendorId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllActiveDriverDetails(vendorId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getAllActiveVehicleDetails(int vendorId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllActiveVehicleDetails(vendorId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> alreadyCheckInExist(int vehicleId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.alreadyCheckInExist(vehicleId);
	}
	
	@Override
	public List<EFmFmVehicleMasterPO> getVendorBasedTripSheet(Date fromDate, Date toDate, int branchId, int vendorId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getVendorBasedTripSheet(fromDate, toDate, branchId, vendorId);
	}

	@Override
	public List<EFmFmTripBasedContractDetailPO> getTripDistanceDetails(
			EFmFmTripBasedContractDetailPO eFmFmTripBasedContractDetailPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getTripDistanceDetails(eFmFmTripBasedContractDetailPO);
	}

	@Override
	public List<EFmFmFixedDistanceContractDetailPO> getFixedDistanceDetails(
			EFmFmFixedDistanceContractDetailPO eFmFmFixedDistanceContractDetailPO) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getFixedDistanceDetails(eFmFmFixedDistanceContractDetailPO);
	}

	

	@Override
	public List<EFmFmVehicleMasterPO> getNoOfWorkingDays(Date fromDate, Date toDate, int branchId, int vehicleId,
			String contractType, int contractDetailId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getNoOfWorkingDays(fromDate, toDate, branchId, vehicleId, contractType, contractDetailId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getVendorBasedVehicleDetails(Date fromDate, Date toDate, int branchId, int vendorId,
			String contractType, int contractDetailsId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getVendorBasedVehicleDetails(fromDate, toDate, branchId, vendorId, contractType, contractDetailsId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getVehicleBasedTripSheet(Date fromDate, Date toDate, int branchId,
			int vehicleId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getVehicleBasedTripSheet(fromDate, toDate, branchId, vehicleId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getTripBasedNoOfWorkingDays(Date fromDate, Date toDate, int branchId,
			int vehicleId, String contractType, int contractDetailId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getTripBasedNoOfWorkingDays(fromDate, toDate, branchId, vehicleId, contractType, contractDetailId);
	}

	@Override
	public void save(EFmFmVendorContractInvoicePO eFmFmVendorContractInvoicePO) {
		iVehicleCheckInDAO.save(eFmFmVendorContractInvoicePO);
		
	}

	@Override
	public void update(EFmFmVendorContractInvoicePO eFmFmVendorContractInvoicePO) {
		iVehicleCheckInDAO.update(eFmFmVendorContractInvoicePO);
		
	}

	@Override
	public void delete(EFmFmVendorContractInvoicePO eFmFmVendorContractInvoicePO) {
		iVehicleCheckInDAO.delete(eFmFmVendorContractInvoicePO);
		
	}

	@Override
	public List<EFmFmVendorContractInvoicePO> getInvoiceforVendor(Date fromDate, Date toDate, int branchId, int vendorId,String invoiceType) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getInvoiceforVendor(fromDate, toDate, branchId, vendorId,invoiceType);
	}
/*
	@Override
	public List<EFmFmVendorContractInvoicePO> getInvoiceforVehicle(Date fromDate, Date toDate, int branchId, int vehicleId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getInvoiceforVehicle(fromDate, toDate, branchId, vehicleId);
	}*/

	@Override
	public List<EFmFmVehicleMasterPO> getAllVehicleDetails(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllVehicleDetails(branchId);
	}

	@Override
	public List<EFmFmDriverMasterPO> getAllDriverDetails(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllDriverDetails(branchId);
	}

	@Override
	public List<EFmFmEscortCheckInPO> getParticulaEscortDetailFromEscortId(int branchId, int escortId) {
	// TODO Auto-generated method stub
	return iVehicleCheckInDAO.getParticulaEscortDetailFromEscortId(branchId, escortId);
	}
	@Override
	public void update(EFmFmEscortCheckInPO eFmFmEscortCheckInPO) {
			// TODO Auto-generated method stub
		iVehicleCheckInDAO.update(eFmFmEscortCheckInPO);
	}
	@Override
	public List<EFmFmVendorContractInvoicePO> getListOfInvoiceNumbers(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getListOfInvoiceNumbers(branchId);
	}

	@Override
	public List<EFmFmVendorContractInvoicePO> getInvoiceDetails(int branchId, int InvoiceNumber) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getInvoiceDetails(branchId, InvoiceNumber);
	}

/*	@Override
	public List<EFmFmVendorContractInvoicePO> getInvoiceDetailsVendor(Date fromDate, Date toDate, int branchId,
			int vendorId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getInvoiceDetailsVendor(fromDate, toDate, branchId, vendorId);
	}

	@Override
	public List<EFmFmVendorContractInvoicePO> getvendorInvoiceFixedDistanceSummary(Date fromDate, Date toDate, int branchId,
			int vendorId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getvendorInvoiceFixedDistanceSummary(fromDate, toDate, branchId, vendorId);
	}*/

	@Override
	public List<EFmFmVendorContractInvoicePO> getInvoiceforVendorByGroup(Date fromDate, Date toDate, int branchId,
			int vendorId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getInvoiceforVendorByGroup(fromDate, toDate, branchId, vendorId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getSumOfTotalKmByVehicle(Date fromDate, Date toDate, int branchId, int vehicleId,
			String contractType, int contractDetailId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getSumOfTotalKmByVehicle(fromDate, toDate, branchId, vehicleId, contractType, contractDetailId);
	}

	@Override
	public List<EFmFmVendorContractInvoicePO> getInvoiceTripBasedVehicle(Date fromDate, Date toDate, int branchId,
			int vehicleId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getInvoiceTripBasedVehicle(fromDate, toDate, branchId, vehicleId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getTripBasedVehicleDetails(Date fromDate, Date toDate, int branchId, int vendorId,
			String contractType, int contractDetailsId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getTripBasedVehicleDetails(fromDate, toDate, branchId, vendorId, contractType, contractDetailsId);
	}

	@Override
	public List<EFmFmVendorContractInvoicePO> getInvoiceByVehicleFixedDistance(Date fromDate, Date toDate, int branchId,
			int vendorId, int vehicleId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getInvoiceByVehicleFixedDistance(fromDate, toDate, branchId, vendorId, vehicleId);
	}

	@Override
	public List<EFmFmDeviceMasterPO> getDeviceDetailsFromDeviceId(int deviceId,
			int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getDeviceDetailsFromDeviceId(deviceId, branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getAllCheckedInVehiclesByVendor(
			int branchId, int vendorId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllCheckedInVehiclesByVendor(branchId, vendorId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getAllCheckedInDriversByVendor(
			int branchId, int vendorId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllCheckedInDriversByVendor(branchId, vendorId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInDriverDetails(
			int branchId, int driverId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getParticularCheckedInDriverDetails(branchId, driverId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInDeviceDetails(
			int branchId, int deviceId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getParticularCheckedInDeviceDetails(branchId, deviceId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getParticularCheckedInVehicles(
			int branchId, int vehicleId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getParticularCheckedInVehicles(branchId, vehicleId);
	}
	
	@Override
	public List<EFmFmVendorContractTypeMasterPO> getContractTypeDetails(String contractType, int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getContractTypeDetails(contractType, branchId);
	}

	@Override
	public List<EFmFmVendorContractTypeMasterPO> getAllContractType(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllContractType(branchId);
	}

	@Override
	public void deleteParticularCheckInEntryFromDeviceVehicleDriver(
			int checkInId) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.deleteParticularCheckInEntryFromDeviceVehicleDriver(checkInId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> alreadyCheckInDriverExistence(
			int driverId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.alreadyCheckInDriverExistence(driverId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getDriverCheckInDetails(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getDriverCheckInDetails(branchId);
	}

	@Override
	public List<EFmFmDriverMasterPO> getAllApprovedDriversByVendorId(
			int vendorId, int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllApprovedDriversByVendorId(vendorId, branchId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getAllApprovedVehiclesByVendorId(
			int vendorId, int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllApprovedVehiclesByVendorId(vendorId, branchId);
	}

	@Override
	public List<EFmFmDeviceMasterPO> getAllApprovedDevices(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllApprovedDevices(branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> alreadyCheckInDeviceExistence(
			int deviceId, int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.alreadyCheckInDeviceExistence(deviceId, branchId);
	}

	@Override
	public List<EFmFmDeviceMasterPO> getAllActiveDeviceDetails(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllActiveDeviceDetails(branchId);
	}

	@Override
	public void deleteExtraCheckInEntry(int checkInId) {
		// TODO Auto-generated method stub
		iVehicleCheckInDAO.deleteExtraCheckInEntry(checkInId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetailsForEditBucket(
			int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getCheckedInVehicleDetailsForEditBucket(branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getLastCheckInEntitiesDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getLastCheckInEntitiesDetails(branchId);
	}

	@Override
	public long getAllCheckedInVehicleCount(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllCheckedInVehicleCount(branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getLastCheckedOutInDeviceDetails(
			int branchId, int deviceId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getLastCheckedOutInDeviceDetails(branchId, deviceId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getCheckedInVehiclesOnRoad(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getCheckedInVehiclesOnRoad(branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetailsFromChecInId(
			int checkInId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getCheckedInVehicleDetailsFromChecInId(checkInId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getVehicleDetailsFromVehicleNumber(
			String vehicleNumber, int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getVehicleDetailsFromVehicleNumber(vehicleNumber, branchId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getAllActualVehicleDetailsFromBranchId(
			int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllActualVehicleDetailsFromBranchId(branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getAllCheckedInVehicleForCreatingNewBucket(
			int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllCheckedInVehicleForCreatingNewBucket(branchId);
	}
		@Override
	public List<EFmFmVehicleMasterPO> getCheckInVehicle(int branchId, Date requestedDate) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getCheckInVehicle(branchId, requestedDate);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getAllVehicleModel(int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getAllVehicleModel(branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getCheckedInVehicleDetailsFromChecInAndBranchId(
			int checkInId, int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getCheckedInVehicleDetailsFromChecInAndBranchId(checkInId, branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getVehicleAndDriverAttendence(
			Date fromDate, Date toDate, int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getVehicleAndDriverAttendence(fromDate, toDate, branchId);
	}

	@Override
	public List<EFmFmVehicleCheckInPO> getVehicleAndDriverAttendenceByVehicleId(
			Date fromDate, Date toDate, int branchId, int vehicleId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getVehicleAndDriverAttendenceByVehicleId(fromDate, toDate, branchId, vehicleId);
	}

	@Override
	public List<EFmFmFixedDistanceContractDetailPO> getFixedDistanceActiveContractDetails(
			int branchId) {
		// TODO Auto-generated method stub
		return iVehicleCheckInDAO.getFixedDistanceActiveContractDetails(branchId);
	}

	



}
