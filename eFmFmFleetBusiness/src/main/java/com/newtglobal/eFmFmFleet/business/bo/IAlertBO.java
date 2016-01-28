package com.newtglobal.eFmFmFleet.business.bo;

import java.util.Date;
import java.util.List;

import com.newtglobal.eFmFmFleet.model.EFmFmAlertTxnPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAlertTypeMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;



public interface IAlertBO {
	
	public void save(EFmFmAlertTxnPO eFmFmAlertTxnPO);
	public void update(EFmFmAlertTxnPO eFmFmAlertTxnPO);
	public void delete(EFmFmAlertTxnPO eFmFmAlertTxnPO);
	public List<EFmFmAlertTxnPO> getAllAlertDetails(EFmFmAlertTxnPO eFmFmAlertTxnPO);		
	public void update(EFmFmTripAlertsPO tripAlertsPO);

	/**
	* The getParticularAlert method implemented.
	* for getting particular row details.  
	*
	* @author  Rajan R
	* 
	* @since   2015-05-28 
	* 
	* @return transaction details.
	*/
	public EFmFmAlertTxnPO getParticularAlert(EFmFmAlertTxnPO eFmFmAlertTxnPO);
	
	/**
	* The getAlertTypeIdDetails method implemented.
	* for getting particular alertId details from alert type master 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-28 
	* 
	* @return alert details.
	*/
	
	public EFmFmAlertTypeMasterPO getAlertTypeIdDetails(EFmFmAlertTypeMasterPO EFmFmAlertTypeMasterPO);
	
	/**
	* The getAllAlertTypeDetails method implemented.
	* for getting list of Alert Details. 
	*
	* @author  Rajan R
	* 
	* @since   2015-06-02 
	* 
	* @return alertType details.
	*/
	
	public List<EFmFmAlertTypeMasterPO> getAllAlertTypeDetails(EFmFmAlertTypeMasterPO EFmFmAlertTypeMasterPO);
	public void save(EFmFmAlertTypeMasterPO eFmFmAlertTypeMasterPO);
	public List<EFmFmAlertTxnPO> getCreatedAlertsByDate(Date fromDate,
			Date toDate, int clientId);
	
	/**
	* storing all the trip alerts.
	*  
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-06-30 
	* 
	* @return trip alert .
	*/
	public void save(EFmFmTripAlertsPO eFmFmTripAlertsPO);
	public List<EFmFmTripAlertsPO> getAllTodaysTripAlerts(EFmFmTripAlertsPO eFmFmTripAlertsPO);
	public List<EFmFmTripAlertsPO> getAllTripAlerts(EFmFmTripAlertsPO eFmFmTripAlertsPO);
	public List<EFmFmTripAlertsPO> getParticularTripAlerts(int branchId,
			int assignRouteId);
	public void deleteAllAlerts(int tripAlertsId);
	public long getNumberOfSosAlertCount(int branchId);
	public long getNumberOfRoadAlertCount(int branchId);
	public List<EFmFmTripAlertsPO> getAllTodaysTripSOSAlerts(int branchId);
	public List<EFmFmTripAlertsPO> getAllTodaysTripRoadAlerts(int branchId);
	public List<EFmFmTripAlertsPO> getAllTodaysTripOpenAlerts(int branchId);
	public List<EFmFmTripAlertsPO> getAllTodaysTripCloseAlerts(int branchId);
	public long getAllTodaysTripSOSAlertsCount(int branchId);
	public long getAllTodaysTripRoadAlertsCount(int branchId);
	public long getAllTodaysTripOpenAlertsCount(int branchId);
	public long getAllTodaysTripCloseAlertsCount(int branchId);
	public List<EFmFmTripAlertsPO> getParticuarAlertDetailFromAlertId(int branchId,
			int alertId, int assignRouteId);


}
