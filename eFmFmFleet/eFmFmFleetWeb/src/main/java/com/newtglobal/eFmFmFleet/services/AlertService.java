package com.newtglobal.eFmFmFleet.services;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IAlertBO;
import com.newtglobal.eFmFmFleet.business.bo.IRouteDetailBO;
import com.newtglobal.eFmFmFleet.model.EFmFmAlertTxnPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAlertTypeMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripAlertsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmZoneMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;


@Component
@Path("/alert")
@Consumes("application/json")
@Produces("application/json")
public class AlertService {	
	
	private static Log log = LogFactory.getLog(AlertService.class);	
	
	/**
	* The updateAlert method implemented.
	* for modifying Alert record on alert Transaction table.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-26 
	* 
	* @return success/failure details.
	*/	
	
	@POST
	@Path("/updateAlert")
	public Response updateAlert(EFmFmAlertTxnPO eFmFmAlertTxnPO){		
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");
		EFmFmAlertTxnPO alertTxnPO=iAlertBO.getParticularAlert(eFmFmAlertTxnPO);		
		iAlertBO.update(alertTxnPO);		
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	

	/**
	* The deleteAlert method implemented.
	* for deleting unwanted Alert record on alert Transaction table.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-26 
	* 
	* @return success/failure details.
	*/	
	
	@POST
	@Path("/deleteAlert")
	public Response deleteAlert(EFmFmAlertTxnPO eFmFmAlertTxnPO){		
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");
		EFmFmAlertTxnPO alertTxnPO=iAlertBO.getParticularAlert(eFmFmAlertTxnPO);
		alertTxnPO.setStatus("D");
		iAlertBO.update(alertTxnPO);		
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	

	/**
	* The postAlert method implemented.
	* for adding new Request to Alert Transaction table.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-26 
	* 
	*  @return success/failure details.
	*/	
	
	@POST
	@Path("/postAlertFromMobile")
	public Response postAlertFromMobile(EFmFmAlertTxnPO eFmFmAlertTxnPO){		
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");
		EFmFmAlertTypeMasterPO  eFmFmAlertTypeMasterPO=new EFmFmAlertTypeMasterPO();
		EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();		
		eFmFmAlertTypeMasterPO.setAlertId(eFmFmAlertTxnPO.getEfmFmAlertTypeMaster().getAlertId());
		eFmFmAlertTypeMasterPO.seteFmFmClientBranchPO(eFmFmClientBranchPO);		
//		EFmFmAlertTypeMasterPO  alertTypeMasterPO=iAlertBO.getAlertTypeIdDetails(eFmFmAlertTypeMasterPO);
				
		/*
		 * set the values for the alertMaster
		 */
		
		iAlertBO.save(eFmFmAlertTxnPO);		
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	
	/**
	* The listOfAlertType Details method implemented.
	* for getting the AlertType Details.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-26 
	* 
	* @return AlertType details.
	*/	
	@POST
	@Path("/listOfAlertType")
	public Response listOfAlertType(EFmFmAlertTypeMasterPO eFmFmAlertTypeMasterPO){		
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");			
		List<Map<String, Object>> listOfAlertTypeDetails= new ArrayList<Map<String, Object>>();
		List<EFmFmAlertTypeMasterPO> listOfAlertType=iAlertBO.getAllAlertTypeDetails(eFmFmAlertTypeMasterPO);		
		if((!(listOfAlertType.isEmpty())) || listOfAlertType.size() !=0){			
			for(EFmFmAlertTypeMasterPO alertDetails:listOfAlertType){				
					Map<String, Object>  alertList= new HashMap<String, Object>();					
					alertList.put("alertId",alertDetails.getAlertId());
					listOfAlertTypeDetails.add(alertList);					
				}			
			}			
		return Response.ok(listOfAlertTypeDetails, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	* Get all posted alerts and get all zones,get all created alerts.
	* .   
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-06-26 
	* 
	* @return showing all posted alerts details.
	 * @throws ParseException 
	*/	
	
	@POST
	@Path("/postedalerts")
	public Response showAllPostedAlertZoneAndAlertTitlesDetails(EFmFmAlertTxnPO eFmFmAlertTxnPO) throws ParseException{		
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");		
		List<Map<String, Object>> allzoneDetails= new ArrayList<Map<String, Object>>();	
		List<Map<String, Object>> masteralerts= new ArrayList<Map<String, Object>>();	
		log.info("postedalerts Service..");
		Map<String, Object>  request= new HashMap<String, Object>();
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate  = (Date) formatter.parse(eFmFmAlertTxnPO.getFromDate());
		Date toDate  = (Date) formatter.parse(eFmFmAlertTxnPO.getToDate());
		List<Map<String, Object>> allAlertsDetail= new ArrayList<Map<String, Object>>();	
		List<EFmFmZoneMasterPO> allZones=iRouteDetailBO.getAllZoneNames(eFmFmAlertTxnPO.getEfmFmAlertTypeMaster().geteFmFmClientBranchPO().getBranchId());		
		if((!(allZones.isEmpty())) || allZones.size() !=0){				
			for(EFmFmZoneMasterPO zoneDetails:allZones){				
					Map<String, Object>  zoneDetail= new HashMap<String, Object>();					
					zoneDetail.put("routeId",zoneDetails.getZoneId());
					zoneDetail.put("routeName",zoneDetails.getZoneName());										
					allzoneDetails.add(zoneDetail);			
				}			
			}	
		EFmFmAlertTypeMasterPO eFmFmAlertTypeMasterPO=new EFmFmAlertTypeMasterPO();
		EFmFmClientBranchPO branchId=new EFmFmClientBranchPO();
		branchId.setBranchId(eFmFmAlertTxnPO.getEfmFmAlertTypeMaster().geteFmFmClientBranchPO().getBranchId());
		eFmFmAlertTypeMasterPO.seteFmFmClientBranchPO(branchId);
		List<EFmFmAlertTypeMasterPO> allActiveAlerts=iAlertBO.getAllAlertTypeDetails(eFmFmAlertTypeMasterPO);
		if((!(allActiveAlerts.isEmpty())) || allActiveAlerts.size() !=0){				
			for(EFmFmAlertTypeMasterPO alertDetail:allActiveAlerts){				
					Map<String, Object>  alerts= new HashMap<String, Object>();					
					alerts.put("alertId",alertDetail.getAlertId());
					alerts.put("alertTitle",alertDetail.getAlertTitle());	
					alerts.put("alertType",alertDetail.getAlertType());										
					masteralerts.add(alerts);			
				}			
			}	
		List<EFmFmAlertTxnPO> allAlerts=iAlertBO.getCreatedAlertsByDate(fromDate, toDate, eFmFmAlertTxnPO.getEfmFmAlertTypeMaster().geteFmFmClientBranchPO().getBranchId());		
		for(EFmFmAlertTxnPO alerts:allAlerts){
			Map<String, Object>  alertsDetails= new HashMap<String, Object>();
			alertsDetails.put("startDate", formatter.format(alerts.getStartDate()));
			alertsDetails.put("endDate", formatter.format(alerts.getEndDate()));
			alertsDetails.put("tittle", alerts.getEfmFmAlertTypeMaster().getAlertTitle());
			alertsDetails.put("alertsType", alerts.getUserType());
			alertsDetails.put("postAlertId", alerts.getId());
			alertsDetails.put("description", alerts.getDescription());
			alertsDetails.put("zoneName", alerts.geteFmFmZoneMaster().getZoneName());
			allAlertsDetail.add(alertsDetails);
		}
		request.put("zones", allzoneDetails);
		request.put("alerts", masteralerts);
		request.put("postalerts", allAlertsDetail);		
		return Response.ok(request, MediaType.APPLICATION_JSON).build();
	}
	
	
	/**
	* Creation an master alerts before posting.
	* .   
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-06-26 
	* 
	* @return AlertType details.
	*/	
	
	@POST
	@Path("/create")
	public Response createAnAlert(EFmFmAlertTypeMasterPO eFmFmAlertTypeMasterPO){		
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");
		eFmFmAlertTypeMasterPO.setStatus("Y");
		Map<String, Object>  request= new HashMap<String, Object>();	
		request.put("status", "success");
		iAlertBO.save(eFmFmAlertTypeMasterPO);		
		return Response.ok(request, MediaType.APPLICATION_JSON).build();
	}
	

	/**
	* post an allready created alerts for specific time period for particular date.
	* .   
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-06-26 
	* 
	* @return EFmFmAlertTxnPO details.
	 * @throws ParseException 
	*/	
	
	@POST
	@Path("/postalert")
	public Response postAnAlert(EFmFmAlertTxnPO eFmFmAlertTxnPO) throws ParseException{		
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		Map<String, Object>  request= new HashMap<String, Object>();
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate  = (Date) formatter.parse(eFmFmAlertTxnPO.getFromDate());
		Date toDate  = (Date) formatter.parse(eFmFmAlertTxnPO.getToDate());
		eFmFmAlertTxnPO.setCreationTime(new Date());
		eFmFmAlertTxnPO.setStartDate(fromDate);
		eFmFmAlertTxnPO.setStatus("Y");
		eFmFmAlertTxnPO.setEndDate(toDate);
		eFmFmAlertTxnPO.setDescription(eFmFmAlertTxnPO.getDescription());
		eFmFmAlertTxnPO.setUserType(eFmFmAlertTxnPO.getUserType());
		request.put("status", "success");
		iAlertBO.save(eFmFmAlertTxnPO);		
		return Response.ok(request, MediaType.APPLICATION_JSON).build();
	}
	
	
	/**
	* Edit the  posted alerts.
	* .   
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-07-01 
	* 
	* @return EFmFmAlertTxnPO details.
	 * @throws ParseException 
	*/	
	
	@POST
	@Path("/editalert")
	public Response editAnpostAlert(EFmFmAlertTxnPO eFmFmAlertTxnPO) throws ParseException{		
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		Map<String, Object>  request= new HashMap<String, Object>();
		EFmFmAlertTxnPO eFmFmAlertTxn=iAlertBO.getParticularAlert(eFmFmAlertTxnPO);
		eFmFmAlertTxn.setDescription(eFmFmAlertTxnPO.getDescription());
		eFmFmAlertTxn.setUpdatedTime(new Date());
		iAlertBO.update(eFmFmAlertTxn);	
		request.put("status", "success");
		return Response.ok(request, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	* Cancel the  posted alerts.
	* .   
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-07-01 
	* 
	* @return EFmFmAlertTxnPO details.
	 * @throws ParseException 
	*/	
	
	@POST
	@Path("/cancelalert")
	public Response cancelAnpostAlert(EFmFmAlertTxnPO eFmFmAlertTxnPO) throws ParseException{		
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		Map<String, Object>  request= new HashMap<String, Object>();
		EFmFmAlertTxnPO eFmFmAlertTxn=iAlertBO.getParticularAlert(eFmFmAlertTxnPO);
		eFmFmAlertTxn.setStatus("N");
		iAlertBO.update(eFmFmAlertTxn);	
		request.put("status", "success");
		return Response.ok(request, MediaType.APPLICATION_JSON).build();
	}
	

	/**
	* get all the trip alerts from driver device.
	* .   
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-06-30
	* 
	* @return EFmFmTripAlertsPO details.
	 * @throws ParseException 
	*/	
	
	@POST
	@Path("/driveralert")
	public Response getDriverAlerts(EFmFmTripAlertsPO eFmFmTripAlertsPO) throws IOException{
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		Map<String, Object>  requests = new HashMap<String, Object>();	
		EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
		assignRoutePO.setAssignRouteId(eFmFmTripAlertsPO.getEfmFmAssignRoute().getAssignRouteId());
		EFmFmAlertTypeMasterPO alertTypeMasterPO=new EFmFmAlertTypeMasterPO();
		alertTypeMasterPO.setAlertId(eFmFmTripAlertsPO.getEfmFmAlertTypeMaster().getAlertId());
		eFmFmTripAlertsPO.setEfmFmAlertTypeMaster(alertTypeMasterPO);
		eFmFmTripAlertsPO.setEfmFmAssignRoute(assignRoutePO);
		eFmFmTripAlertsPO.setCreationTime(new Date());
		eFmFmTripAlertsPO.setUpdatedTime(new Date());
		eFmFmTripAlertsPO.setUserType("driver");
		eFmFmTripAlertsPO.setAlertClosingDescription("No Action");
		eFmFmTripAlertsPO.setAlertOpenStatus("open");
		eFmFmTripAlertsPO.setStatus("Y");		
		iAlertBO.save(eFmFmTripAlertsPO);		
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	* get all the trip alerts from employee device.
	* .   
	*
	* @author  Sarfraz Khan
	* 
	* @since   2015-06-30
	* 
	* @return EFmFmTripAlertsPO details.
	 * @throws ParseException 
	*/	
	
	
	@POST
	@Path("/employeealert")
	public Response getEmployeeAlerts(EFmFmTripAlertsPO eFmFmTripAlertsPO) throws IOException{
		IAlertBO iAlertBO = (IAlertBO) ContextLoader.getContext().getBean("IAlertBO");	
		Map<String, Object>  requests = new HashMap<String, Object>();	
		EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
		assignRoutePO.setAssignRouteId(eFmFmTripAlertsPO.getEfmFmAssignRoute().getAssignRouteId());
		EFmFmAlertTypeMasterPO alertTypeMasterPO=new EFmFmAlertTypeMasterPO();
		alertTypeMasterPO.setAlertId(eFmFmTripAlertsPO.getEfmFmAlertTypeMaster().getAlertId());
		eFmFmTripAlertsPO.setEfmFmAlertTypeMaster(alertTypeMasterPO);
		eFmFmTripAlertsPO.setCreationTime(new Date());
		eFmFmTripAlertsPO.setUpdatedTime(new Date());
		eFmFmTripAlertsPO.setAlertClosingDescription("No Action");
		eFmFmTripAlertsPO.setAlertOpenStatus("open");
		eFmFmTripAlertsPO.setEfmFmAssignRoute(assignRoutePO);
		eFmFmTripAlertsPO.setUserType("employee");
		eFmFmTripAlertsPO.setStatus("Y");
		iAlertBO.save(eFmFmTripAlertsPO);
		requests.put("status", "success");		
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	
	
	}
