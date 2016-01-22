package com.newtglobal.eFmFmFleet.business.dao.daoImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newtglobal.eFmFmFleet.business.dao.IRouteDetailDAO;
import com.newtglobal.eFmFmFleet.model.EFmFmAreaMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientRouteMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmZoneMasterPO;

@Repository("IRouteDetailDAO")
public class RouteDetailDAOImpl implements IRouteDetailDAO {
	
	private EntityManager entityManager;	

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	* The saveDriverRecord implements for
	* storing drvier details into driver master table from xl utility. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveDriverRecord(EFmFmDriverMasterPO eFmFmDriverMasterPO) {		
		entityManager.persist(eFmFmDriverMasterPO);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveRouteMappingDetails(EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO) {
		entityManager.merge(eFmFmRouteAreaMappingPO);
		
	}
	/**
	* The saveAreaRecord implements for
	* storing Area details into Area master table from xl utility. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveAreaRecord(EFmFmAreaMasterPO eFmFmAreaMasterPO) {
		entityManager.persist(eFmFmAreaMasterPO);		
	}
	
	/**
	* The getGeoCode implements for
	* Generating Latitude & longitude for given Address.
	*
	* @author  Rajan R
	* 
	* @since   2015-05-05 
	*/
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String getGeoCode(String address) {
		String geoCode="unknown";
		try{					
				String line = "";
				String urlLocation = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";			
				URL geocodeURL = new URL(urlLocation);
				URLConnection connection = geocodeURL.openConnection();
				connection.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer data = new StringBuffer();
				line = "";
				while((line = reader.readLine()) != null){
					data.append(line.trim());
				}				
				JSONArray results = new JSONObject(data.toString()).getJSONArray("results");
				if(results.length() >0){
					JSONObject geo = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
					geoCode=geo.getDouble("lat")+","+geo.getDouble("lng");			
				}			
								
		}catch(Exception ex){
			ex.printStackTrace();
			
		}				
		return geoCode;
	}
	
	/**
	* The getAreaId implements for
	* Getting AreaId based on the Area Name. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-05 
	*/
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EFmFmAreaMasterPO  getAreaId(String areaName){
    	List <EFmFmAreaMasterPO> areaMasterPO = new ArrayList<EFmFmAreaMasterPO>();
    	Query query=entityManager.createQuery("SELECT b FROM EFmFmAreaMasterPO b where b.areaName='"+areaName+"'");
    	try {
    		areaMasterPO=query.getResultList();			
		} catch (Exception e) {
		}
    	if(areaMasterPO.isEmpty()){
    		return null;
    	}else{
    		return areaMasterPO.get(0);	
    	}
    	
       
    }
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmDriverMasterPO> getValidLicenseNumber(String licenseNumber) {
		List<EFmFmDriverMasterPO> allDriverDetail = new ArrayList<EFmFmDriverMasterPO>() ;
		Query query = entityManager.createQuery("SELECT b FROM EFmFmDriverMasterPO as b where b.licenceNumber ='"+licenseNumber+"'");
		allDriverDetail = query.getResultList();
		return allDriverDetail;
		
	}
	/**
	* The getAllAreaName implements for
	* for validating and getting all area names from area master table. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/	
	/*@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAreaMasterPO> getAllAreaName(String areaName) {
		List<EFmFmAreaMasterPO> areaMasterPO=new ArrayList<EFmFmAreaMasterPO>();
		Query query =entityManager.createQuery("SELECT b FROM EFmFmAreaMasterPO as b where TRIM(UPPER(b.areaName))=TRIM(UPPER('"+areaName+"'))");
		areaMasterPO=query.getResultList();
		return areaMasterPO;
	}*/
	
	@Override
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmAreaMasterPO> getAllAreaName(String areaName) {
	  List<EFmFmAreaMasterPO> areaMasterPO=new ArrayList<EFmFmAreaMasterPO>();
	  Query query =entityManager.createQuery("SELECT b FROM EFmFmAreaMasterPO as b where TRIM(UPPER(REPLACE(b.areaName,' ','')))=TRIM(UPPER('"+areaName+"'))");
	  areaMasterPO=query.getResultList();
	  return areaMasterPO;
	 }
	
	
	/**
	* The saveRouteNameRecord implements for
	* storing Area details into Route master table from xl utility. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveRouteNameRecord(EFmFmZoneMasterPO eFmFmZoneMasterPO) {
		entityManager.persist(eFmFmZoneMasterPO);
		
	}
	/**
	* The getAllRouteName implements for
	* for validating and getting all area names from area master table. 
	*
	* @author  Rajan R
	* 
	* @since   2015-05-06 
	*/
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmZoneMasterPO> getAllRouteName(String routeName) {
		List <EFmFmZoneMasterPO> routeMasterPO = new ArrayList<EFmFmZoneMasterPO>();
	     Query query=entityManager.createQuery(
	          "SELECT b FROM EFmFmZoneMasterPO as b where b.zoneName='"+routeName+"'");
	     routeMasterPO=query.getResultList();
		return routeMasterPO;
	}
	
	 @Override
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmAssignRoutePO> getAllOnRoadVehicleDetails(int branchId,Date todayDate) {
	  List <EFmFmAssignRoutePO> onRoadVehicle = new ArrayList<EFmFmAssignRoutePO>();
	  Format formatter;  
	  formatter = new SimpleDateFormat("yyyy-MM-dd");  
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.efmFmVehicleCheckIn c  JOIN c.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where g.branchId ='"+branchId+"' and DATE(b.tripShiftTime)=TRIM('"+formatter.format(todayDate)+"')) ");  
	  onRoadVehicle=query.getResultList();  
	  return onRoadVehicle;
	 }

	/*@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmAssignRoutePO> getAllOnRoadVehicleDetails(EFmFmAssignRoutePO eFmFmAssignRoutePO) {
		List <EFmFmAssignRoutePO> onRoadVehicle = new ArrayList<EFmFmAssignRoutePO>();
		Format formatter;		
		formatter = new SimpleDateFormat("yyyy-MM-dd");		
		Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b where DATE(b.tripShiftTime)=TRIM('"+formatter.format(eFmFmAssignRoutePO.getTripShiftTime())+"')) ");		
		onRoadVehicle=query.getResultList();		
		return onRoadVehicle;
	}*/

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmRouteAreaMappingPO> routeMappaingAlreadyExist(
			EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO) {
		List<EFmFmRouteAreaMappingPO> routeAreaMappingPO=new ArrayList<EFmFmRouteAreaMappingPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmRouteAreaMappingPO b JOIN b.eFmFmZoneMaster c JOIN b.efmFmAreaMaster d where c.zoneId='"+eFmFmRouteAreaMappingPO.geteFmFmZoneMaster().getZoneId()+"' and d.areaId='"+eFmFmRouteAreaMappingPO.getEfmFmAreaMaster().getAreaId()+"'");
		routeAreaMappingPO=query.getResultList();
		return routeAreaMappingPO;
	}	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmRouteAreaMappingPO> getAllAreasFromZoneId(int zoneId) {
		List<EFmFmRouteAreaMappingPO> routeAreaMappingPO=new ArrayList<EFmFmRouteAreaMappingPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmRouteAreaMappingPO b JOIN b.eFmFmZoneMaster c JOIN b.efmFmAreaMaster d where c.zoneId='"+zoneId+"' ");
		routeAreaMappingPO=query.getResultList();
		return routeAreaMappingPO;
	}	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmZoneMasterPO> getAllZoneNames(int branchId) {
		List<EFmFmZoneMasterPO> zoneDetails=new ArrayList<EFmFmZoneMasterPO>();
		Query query=entityManager.createQuery("SELECT r FROM EFmFmZoneMasterPO r JOIN r.efmFmClientRouteMappings v JOIN v.eFmFmClientBranchPO c   where r.status='Y' AND c.branchId="+branchId+" ");
		zoneDetails=query.getResultList();
		return zoneDetails;
	}
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void modifyDriverRecord(EFmFmDriverMasterPO eFmFmDriverMasterPO) {
		entityManager.merge(eFmFmDriverMasterPO);		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveClientRouteMapping(
			EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO) {
		entityManager.persist(eFmFmClientRouteMappingPO);
 	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmClientRouteMappingPO> clientRouteMappaingAlreadyExist(
			EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO) {
		List<EFmFmClientRouteMappingPO> eFmFmClientRouteMapping=new ArrayList<EFmFmClientRouteMappingPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmClientRouteMappingPO b JOIN b.eFmFmZoneMaster c JOIN b.eFmFmClientBranchPO d where c.zoneId='"+eFmFmClientRouteMappingPO.geteFmFmZoneMaster().getZoneId()+"' and d.branchId='"+eFmFmClientRouteMappingPO.geteFmFmClientBranchPO().getBranchId()+"'");
		eFmFmClientRouteMapping=query.getResultList();
		return eFmFmClientRouteMapping;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmClientRouteMappingPO> getParticularRouteDetailByClient(int branchId,String routeName) {
		List<EFmFmClientRouteMappingPO> eFmFmClientRouteMapping=new ArrayList<EFmFmClientRouteMappingPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmClientRouteMappingPO b JOIN b.eFmFmZoneMaster c JOIN b.eFmFmClientBranchPO d where  d.branchId='"+branchId+"' AND c.zoneName='"+routeName+"'");
		eFmFmClientRouteMapping=query.getResultList();
		return eFmFmClientRouteMapping;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<EFmFmClientRouteMappingPO> getAllRoutesOfParticularClient(int branchId) {
		List<EFmFmClientRouteMappingPO> eFmFmClientRouteMapping=new ArrayList<EFmFmClientRouteMappingPO>();
		Query query=entityManager.createQuery("SELECT b FROM EFmFmClientRouteMappingPO b JOIN b.eFmFmZoneMaster c JOIN b.eFmFmClientBranchPO d where  d.branchId='"+branchId+"'");
		eFmFmClientRouteMapping=query.getResultList();
		return eFmFmClientRouteMapping;
	}

	 @Override
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public EFmFmRouteAreaMappingPO getRouteAreaId(String areaName,int branchId,String zoneName) {
	  List <EFmFmRouteAreaMappingPO> routeAreaMapping = new ArrayList<EFmFmRouteAreaMappingPO>();
	     Query query=entityManager.createQuery("SELECT r FROM EFmFmRouteAreaMappingPO r JOIN r.efmFmAreaMaster a JOIN r.eFmFmZoneMaster m JOIN m.efmFmClientRouteMappings c JOIN c.eFmFmClientBranchPO f where TRIM(UPPER(REPLACE(a.areaName,' ','')))='"+areaName+"' and TRIM(UPPER(REPLACE(m.zoneName,' ','')))='"+zoneName+"' and f.branchId='"+branchId+"'");
	     try {
	      routeAreaMapping=query.getResultList();   
	  } catch (Exception e) {
	  }
	     if(routeAreaMapping.isEmpty()){
	      return null;
	     }else{
	      return routeAreaMapping.get(0); 
	     }
	 }
	 @Override
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	 public List<EFmFmAssignRoutePO> getVehicleDetailFromVehicleId(int branchId,int checInId) {
	  List <EFmFmAssignRoutePO> vehicleDetail = new ArrayList<EFmFmAssignRoutePO>();
	  Query query=entityManager.createQuery("SELECT b FROM EFmFmAssignRoutePO b JOIN b.efmFmVehicleCheckIn c  JOIN c.efmFmVehicleMaster d JOIN d.efmFmVendorMaster f JOIN f.eFmFmClientBranchPO g where b.tripStatus !='completed' AND g.branchId ='"+branchId+"' AND c.checkInId='"+checInId+"' ");  
	  vehicleDetail=query.getResultList();  
	  return vehicleDetail;
	 }
	 
	 
}