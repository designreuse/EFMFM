package com.newtglobal.eFmFmFleet.business.bo.boImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.dao.IApprovalDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;

@Service("IApprovalBO")
public class IApprovalBOImpl implements IApprovalBO {

	@Autowired
	IApprovalDAO iApprovalDAO;
	public void setIUserMasterDAO(IApprovalDAO iApprovalDAO) {
		this.iApprovalDAO = iApprovalDAO;
	}
	
	@Override
	public void save(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		// TODO Auto-generated method stub
		iApprovalDAO.save(eFmFmDriverMasterPO);
	}

	@Override
	public void update(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		// TODO Auto-generated method stub
		iApprovalDAO.update(eFmFmDriverMasterPO);
	}

	@Override
	public void delete(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		// TODO Auto-generated method stub
		iApprovalDAO.delete(eFmFmDriverMasterPO);
	}

	@Override
	public List<EFmFmDriverMasterPO> getAllUnapprovedDrivers(int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getAllUnapprovedDrivers(clientId);
	}

	@Override
	public List<EFmFmDriverMasterPO> getAllApprovedDrivers(int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getAllApprovedDrivers(clientId);
	}

	@Override
	public List<EFmFmDriverMasterPO> getAllInActiveDrivers(int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getAllInActiveDrivers(clientId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getAllUnapprovedVehicles(int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getAllUnapprovedVehicles(clientId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getAllApprovedVehicles(int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getAllApprovedVehicles(clientId);
	}

	@Override
	public List<EFmFmVehicleMasterPO> getAllInActiveVehicles(int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getAllInActiveVehicles(clientId);
	}

	@Override
	public List<EFmFmVendorMasterPO> getAllUnapprovedVendors(int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getAllUnapprovedVendors(clientId);
	}

	@Override
	public List<EFmFmVendorMasterPO> getAllApprovedVendors(int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getAllApprovedVendors(clientId);
	}

	@Override
	public List<EFmFmVendorMasterPO> getAllInActiveVendors(int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getAllInActiveVendors(clientId);
	}

	@Override
	public EFmFmDriverMasterPO getParticularDriverDetail(int driverId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getParticularDriverDetail(driverId);
	}

	@Override
	public void deleteParticularDriver(int driverId) {
		// TODO Auto-generated method stub
		iApprovalDAO.deleteParticularDriver(driverId);
	}

	@Override
	public void deleteParticularVehicle(int vehicleId) {
		// TODO Auto-generated method stub
		iApprovalDAO.deleteParticularVehicle(vehicleId);
	}

	@Override
	public void deleteParticularVendor(int vendorId) {
		// TODO Auto-generated method stub
		iApprovalDAO.deleteParticularVendor(vendorId);
	}

	@Override
	public void saveRouteTripDetails(
			EFmFmEmployeeTripDetailPO eFmFmEmployeeTripDetailPO) {
		// TODO Auto-generated method stub
		iApprovalDAO.saveRouteTripDetails(eFmFmEmployeeTripDetailPO);
	}
	@Override
	public List<EFmFmDriverMasterPO> getParticularDriverDeviceDetails(
			String mobileNo, int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getParticularDriverDeviceDetails(mobileNo, clientId);
	}

	@Override
	public List<EFmFmDriverMasterPO> getParticularDriverDetailsFromDeviceToken(
			String deviceId, int clientId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getParticularDriverDetailsFromDeviceToken(deviceId, clientId);
	}

	@Override
	public List<EFmFmDriverMasterPO> getParticularDriverDetailFromDeriverId(
			int driverId) {
		// TODO Auto-generated method stub
		return iApprovalDAO.getParticularDriverDetailFromDeriverId(driverId);
	}

	
}
