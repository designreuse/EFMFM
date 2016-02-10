package com.newtglobal.eFmFmFleet.business.bo.boImpl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtglobal.eFmFmFleet.business.bo.IRouteDetailBO;
import com.newtglobal.eFmFmFleet.business.dao.IRouteDetailDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmAreaMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientRouteMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmZoneMasterPO;

@Service("IRouteDetailBO")
public class IRouteDetailBOImpl implements IRouteDetailBO {
	
	
	@Autowired
	IRouteDetailDAO iRouteDetailDAO;
	public void setiRouteDetailDAO(IRouteDetailDAO iRouteDetailDAO) {
		this.iRouteDetailDAO = iRouteDetailDAO;
	}	
	
	@Override
	public String getGeoCode(String address) {		
		return iRouteDetailDAO.getGeoCode(address);
	}

	@Override
	public EFmFmAreaMasterPO getAreaId(String areaName) {		
		return iRouteDetailDAO.getAreaId(areaName);
	}

	
	@Override
	public void saveDriverRecord(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		iRouteDetailDAO.saveDriverRecord(eFmFmDriverMasterPO);
		
	}

	@Override
	public void saveAreaRecord(EFmFmAreaMasterPO eFmFmAreaMasterPO) {
		iRouteDetailDAO.saveAreaRecord(eFmFmAreaMasterPO);
		
	}

	@Override
	public List<EFmFmAreaMasterPO> getAllAreaName(String areaName) {		
		return iRouteDetailDAO.getAllAreaName(areaName);
	}

	@Override
	public void saveRouteNameRecord(EFmFmZoneMasterPO eFmFmZoneMasterPO) {
		iRouteDetailDAO.saveRouteNameRecord(eFmFmZoneMasterPO);
		
	}

	@Override
	public List<EFmFmZoneMasterPO> getAllRouteName(String routeName) {		
		return iRouteDetailDAO.getAllRouteName(routeName);
	}

	

	@Override
	public void saveRouteMappingDetails(
			EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO) {
		// TODO Auto-generated method stub
		iRouteDetailDAO.saveRouteMappingDetails(eFmFmRouteAreaMappingPO);
	}

	@Override
	public List<EFmFmRouteAreaMappingPO> routeMappaingAlreadyExist(
			EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.routeMappaingAlreadyExist(eFmFmRouteAreaMappingPO);
	}

	@Override
	public List<EFmFmAssignRoutePO> getAllOnRoadVehicleDetails(int clientId,
			Date todayDate) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getAllOnRoadVehicleDetails(clientId, todayDate);
	}

	@Override
	public List<EFmFmZoneMasterPO> getAllZoneNames(int clientId) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getAllZoneNames(clientId);
	}

	@Override
	public void modifyDriverRecord(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		// TODO Auto-generated method stub
		iRouteDetailDAO.modifyDriverRecord(eFmFmDriverMasterPO);
	}

	@Override
	public void saveClientRouteMapping(
			EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO) {
		// TODO Auto-generated method stub
		iRouteDetailDAO.saveClientRouteMapping(eFmFmClientRouteMappingPO);
	}

	@Override
	public List<EFmFmClientRouteMappingPO> clientRouteMappaingAlreadyExist(
			EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.clientRouteMappaingAlreadyExist(eFmFmClientRouteMappingPO);
	}

	

	@Override
	public List<EFmFmDriverMasterPO> getValidLicenseNumber(String licenseNumber) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getValidLicenseNumber(licenseNumber);
	}

	@Override
	public EFmFmRouteAreaMappingPO getRouteAreaId(String areaName,
			int branchId, String zoneName) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getRouteAreaId(areaName, branchId, zoneName);
	}

	@Override
	public List<EFmFmClientRouteMappingPO> getAllRoutesOfParticularClient(
			int branchId) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getAllRoutesOfParticularClient(branchId);
	}

	@Override
	public List<EFmFmRouteAreaMappingPO> getAllAreasFromZoneId(int zoneId) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getAllAreasFromZoneId(zoneId);
	}

	@Override
	public List<EFmFmAssignRoutePO> getVehicleDetailFromVehicleId(int branchId,
			int checInId) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getVehicleDetailFromVehicleId(branchId, checInId);
	}

	@Override
	public List<EFmFmClientRouteMappingPO> getParticularRouteDetailByClient(
			int branchId, String routeName) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getParticularRouteDetailByClient(branchId, routeName);
	}

	@Override
	public List<EFmFmRouteAreaMappingPO> getRouteAreaIdFromAreaNameAndZoneNameForExcelUpload(
			String areaName, int branchId, String zoneName) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getRouteAreaIdFromAreaNameAndZoneNameForExcelUpload(areaName, branchId, zoneName);
	}

	@Override
	public List<EFmFmAreaMasterPO> getParticularAreaNameDetails(String areaName) {
		// TODO Auto-generated method stub
		return iRouteDetailDAO.getParticularAreaNameDetails(areaName);
	}

	
}
