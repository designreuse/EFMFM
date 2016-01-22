package com.newtglobal.eFmFmFleet.business.bo;

import java.util.Date;
import java.util.List;

import com.newtglobal.eFmFmFleet.model.EFmFmAreaMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientRouteMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmZoneMasterPO;




public interface IRouteDetailBO {
	/**
	* The getGeoCode implements for
	* Generating Latitude & longitude for given Address.
	*
	* @author  Rajan R
	* 
	* @since   2015-05-05 
	*/
	public String getGeoCode(String address);
	/**
	* The getRouteId implements for
	* Getting RouteId based on the Area Name. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-05 
	*/
	public EFmFmAreaMasterPO getAreaId(String areaName);
	
	/**
	* The getValidLicenseNumber implements for
	* validating driver license number. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	public List<EFmFmDriverMasterPO> getValidLicenseNumber(String licenseNumber);
	/**
	* The saveDriverRecord implements for
	* storing drvier details into driver master table from xl utility. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	public void saveDriverRecord(EFmFmDriverMasterPO  eFmFmDriverMasterPO);
	
	/**
	* The saveAreaRecord implements for
	* storing Area details into Area master table from xl utility. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	public void saveAreaRecord(EFmFmAreaMasterPO  eFmFmAreaMasterPO);
	/**
	* The getAllAreaName implements for
	* for validating and getting all area names from area master table. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	public List<EFmFmAreaMasterPO> getAllAreaName(String areaName);
	/**
	* The saveRouteNameRecord implements for
	* storing Area details into Route master table from xl utility. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	public void saveRouteNameRecord(EFmFmZoneMasterPO  eFmFmZoneMasterPO);
	/**
	* The getAllAreaName implements for
	* for validating and getting all area names from area master table. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	public List<EFmFmZoneMasterPO> getAllRouteName(String routeName);

	/**
	* The getAllOnRoadVehicleDetails implements for
	* Getting all checkedIn on Road Vehicle details using Current Date. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-21 
	*/
	public List<EFmFmAssignRoutePO> getAllOnRoadVehicleDetails(int clientId,
			Date todayDate);
		/**
	* The saveRouteMappingDetails implements for
	* saving routeMapping with Routeid and areaId. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-22 
	*/
	public void saveRouteMappingDetails(EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO);
	/**
	* The routeMappaingAlreadyExist implements for
	* validating RouteID already Exist or not. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-22 
	*/
	public List<EFmFmRouteAreaMappingPO> routeMappaingAlreadyExist(EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO);
	
	/**
	* The getAllZoneNames implements for
	* getting all zoneNames. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-26 
	*/
	public List<EFmFmZoneMasterPO> getAllZoneNames(int clientId);
	
	
	/**
	* The modifyDriverRecord implements for
	* modifying drvier details on driver master table. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-29 
	*/
	public void modifyDriverRecord(EFmFmDriverMasterPO  eFmFmDriverMasterPO);
	/**
	* The saveClientRouteMappaing implements for
	* saving saveClientRouteMapping  details. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-29 
	*/
	public void saveClientRouteMapping(EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO);
	/**
	* The clientrouteMappaingAlreadyExist implements for
	* validating ClientRoute already Exist or not. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-29 
	*/
	public List<EFmFmClientRouteMappingPO> clientRouteMappaingAlreadyExist(EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO);
	public EFmFmRouteAreaMappingPO getRouteAreaId(String areaName, int branchId,
			String zoneName);
	public List<EFmFmClientRouteMappingPO> getAllRoutesOfParticularClient(int branchId);
	public List<EFmFmRouteAreaMappingPO> getAllAreasFromZoneId(int zoneId);
	public List<EFmFmAssignRoutePO> getVehicleDetailFromVehicleId(int branchId,
			int checInId);
	public List<EFmFmClientRouteMappingPO> getParticularRouteDetailByClient(
			int branchId, String routeName);
	
}
