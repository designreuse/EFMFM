package com.newtglobal.eFmFmFleet.business.bo.boImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.business.dao.IVendorDetailsDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverFeedbackPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;

@Service("IVendorDetailsBO")
public class IVendorDetailsBOImpl implements IVendorDetailsBO {

	
	@Autowired
	private IVendorDetailsDAO iVendorDetailsDAO;	
	public void setiVendorDetailsDAO(IVendorDetailsDAO iVendorDetailsDAO) {
		this.iVendorDetailsDAO = iVendorDetailsDAO;
	}
	@Override
	public void save(EFmFmVendorMasterPO eFmFmVendorMasterPO) {
		iVendorDetailsDAO.save(eFmFmVendorMasterPO);
		
	}
	@Override
	public void update(EFmFmVendorMasterPO eFmFmVendorMasterPO) {
		iVendorDetailsDAO.update(eFmFmVendorMasterPO);
		
	}
	@Override
	public void delete(EFmFmVendorMasterPO eFmFmVendorMasterPO) {
		iVendorDetailsDAO.delete(eFmFmVendorMasterPO);		
	}
	@Override
	public List<EFmFmVendorMasterPO> getAllVendorName(String vendorName,int clientId) {
		return iVendorDetailsDAO.getAllVendorName(vendorName,clientId);
	}
	@Override
	public EFmFmVendorMasterPO getParticularVendorDetail(int vendorId) {
		// TODO Auto-generated method stub
		return iVendorDetailsDAO.getParticularVendorDetail(vendorId);
	}
	@Override
	public List<EFmFmVendorMasterPO> getAllVendorsDetails(EFmFmVendorMasterPO eFmFmVendorMasterPO) {
		return iVendorDetailsDAO.getAllVendorsDetails(eFmFmVendorMasterPO);
	}
	@Override
	public void save(EFmFmDriverFeedbackPO driverFeedbackPO) {
		// TODO Auto-generated method stub
		iVendorDetailsDAO.save(driverFeedbackPO);
	}
	@Override
	public boolean doesDriverDeviceExist(String deviceId, int clientId) {
		// TODO Auto-generated method stub
		return iVendorDetailsDAO.doesDriverDeviceExist(deviceId, clientId);
	}
	@Override
	public List<EFmFmDeviceMasterPO> getAllDeviceDetails(int clientId,
			Date todayDate) {
		// TODO Auto-generated method stub
		return iVendorDetailsDAO.getAllDeviceDetails(clientId, todayDate);
	}
	@Override
	public List<EFmFmVendorMasterPO> getVendorTinNumber(String tinNumber,
			int branchId) {
		// TODO Auto-generated method stub
		return iVendorDetailsDAO.getVendorTinNumber(tinNumber, branchId);
	}
	@Override
	public List<EFmFmVendorMasterPO> getVendorEmailId(String emailID,
			int branchId) {
		// TODO Auto-generated method stub
		return iVendorDetailsDAO.getVendorEmailId(emailID, branchId);
	}
	@Override
	public List<EFmFmVendorMasterPO> getVendorMobileNo(String vendorMobileNo,
			int branchId) {
		// TODO Auto-generated method stub
		return iVendorDetailsDAO.getVendorMobileNo(vendorMobileNo, branchId);
	}
	@Override
	public List<EFmFmDeviceMasterPO> getAllActiveDeviceDetails(int branchId) {
		// TODO Auto-generated method stub
		return iVendorDetailsDAO.getAllActiveDeviceDetails(branchId);
	}
	@Override
	public List<EFmFmVendorMasterPO> getAllApprovedVendorsDetails(int branchId) {
		// TODO Auto-generated method stub
		return iVendorDetailsDAO.getAllApprovedVendorsDetails(branchId);
	}
	@Override
	public List<EFmFmDeviceMasterPO> getAllDeviceDetailsFromBranchId(
			int branchId) {
		// TODO Auto-generated method stub
		return iVendorDetailsDAO.getAllDeviceDetailsFromBranchId(branchId);
	}
	
}
