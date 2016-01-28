package com.newtglobal.eFmFmFleet.business.bo.boImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtglobal.eFmFmFleet.business.bo.IAlertBO;
import com.newtglobal.eFmFmFleet.business.dao.IAlertDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmAlertTxnPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAlertTypeMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;

@Service("IAlertBO")
public class IAlertBOImpl implements IAlertBO {
	
	@Autowired
	IAlertDAO iAlertDAO;	
	public void setiAlertDAO(IAlertDAO iAlertDAO) {
		this.iAlertDAO = iAlertDAO;
	}
	@Override
	public void save(EFmFmAlertTxnPO eFmFmAlertTxnPO) {
		iAlertDAO.save(eFmFmAlertTxnPO);
		
	}
	@Override
	public void update(EFmFmAlertTxnPO eFmFmAlertTxnPO) {
		iAlertDAO.update(eFmFmAlertTxnPO);
		
		
	}
	@Override
	public void delete(EFmFmAlertTxnPO eFmFmAlertTxnPO) {		
		iAlertDAO.delete(eFmFmAlertTxnPO);
	}
	@Override
	public List<EFmFmAlertTxnPO> getAllAlertDetails(
			EFmFmAlertTxnPO eFmFmAlertTxnPO) {		
		return iAlertDAO.getAllAlertDetails(eFmFmAlertTxnPO);
	}
	@Override
	public EFmFmAlertTxnPO getParticularAlert(EFmFmAlertTxnPO eFmFmAlertTxnPO) {
		return iAlertDAO.getParticularAlert(eFmFmAlertTxnPO);
	}
	@Override
	public EFmFmAlertTypeMasterPO getAlertTypeIdDetails(
			EFmFmAlertTypeMasterPO EFmFmAlertTypeMasterPO) {
		return iAlertDAO.getAlertTypeIdDetails(EFmFmAlertTypeMasterPO);
	}
	@Override
	public List<EFmFmAlertTypeMasterPO> getAllAlertTypeDetails(
			EFmFmAlertTypeMasterPO EFmFmAlertTypeMasterPO) {
		return iAlertDAO.getAllAlertTypeDetails(EFmFmAlertTypeMasterPO);
	}
	@Override
	public void save(EFmFmAlertTypeMasterPO eFmFmAlertTypeMasterPO) {
		iAlertDAO.save(eFmFmAlertTypeMasterPO);
	}
	@Override
	public List<EFmFmAlertTxnPO> getCreatedAlertsByDate(Date fromDate,
			Date toDate, int clientId) {
		return iAlertDAO.getCreatedAlertsByDate(fromDate, toDate, clientId);
	}
	@Override
	public void save(EFmFmTripAlertsPO eFmFmTripAlertsPO) {
		iAlertDAO.save(eFmFmTripAlertsPO);
	}
	@Override
	public List<EFmFmTripAlertsPO> getAllTodaysTripAlerts(
			EFmFmTripAlertsPO eFmFmTripAlertsPO) {
		return iAlertDAO.getAllTodaysTripAlerts(eFmFmTripAlertsPO);
	}
	@Override
	public List<EFmFmTripAlertsPO> getAllTripAlerts(
			EFmFmTripAlertsPO eFmFmTripAlertsPO) {
		// TODO Auto-generated method stub
		return iAlertDAO.getAllTripAlerts(eFmFmTripAlertsPO);
	}
	
	@Override
	public List<EFmFmTripAlertsPO> getParticularTripAlerts(int branchId,
			int assignRouteId) {
		return iAlertDAO.getParticularTripAlerts(branchId, assignRouteId);
	}
	@Override
	public void update(EFmFmTripAlertsPO tripAlertsPO) {
		iAlertDAO.update(tripAlertsPO);
	}
	@Override
	public void deleteAllAlerts(int tripAlertsId) {
		iAlertDAO.deleteAllAlerts(tripAlertsId);
	}
	@Override
	public long getNumberOfSosAlertCount(int branchId) {
		return iAlertDAO.getNumberOfSosAlertCount(branchId);
	}
	@Override
	public long getNumberOfRoadAlertCount(int branchId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getNumberOfRoadAlertCount(branchId);
	}
	@Override
	public List<EFmFmTripAlertsPO> getAllTodaysTripSOSAlerts(int branchId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getAllTodaysTripSOSAlerts(branchId);
	}
	@Override
	public List<EFmFmTripAlertsPO> getAllTodaysTripRoadAlerts(int branchId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getAllTodaysTripRoadAlerts(branchId);
	}
	@Override
	public List<EFmFmTripAlertsPO> getAllTodaysTripOpenAlerts(int branchId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getAllTodaysTripOpenAlerts(branchId);
	}
	@Override
	public List<EFmFmTripAlertsPO> getAllTodaysTripCloseAlerts(int branchId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getAllTodaysTripCloseAlerts(branchId);
	}
	@Override
	public long getAllTodaysTripSOSAlertsCount(int branchId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getAllTodaysTripSOSAlertsCount(branchId);
	}
	@Override
	public long getAllTodaysTripRoadAlertsCount(int branchId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getAllTodaysTripRoadAlertsCount(branchId);
	}
	@Override
	public long getAllTodaysTripOpenAlertsCount(int branchId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getAllTodaysTripOpenAlertsCount(branchId);
	}
	@Override
	public long getAllTodaysTripCloseAlertsCount(int branchId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getAllTodaysTripCloseAlertsCount(branchId);
	}
	@Override
	public List<EFmFmTripAlertsPO> getParticuarAlertDetailFromAlertId(
			int branchId, int alertId, int assignRouteId) {
		// TODO Auto-generated method stub
		return iAlertDAO.getParticuarAlertDetailFromAlertId(branchId, alertId, assignRouteId);
	}
	
	
}
