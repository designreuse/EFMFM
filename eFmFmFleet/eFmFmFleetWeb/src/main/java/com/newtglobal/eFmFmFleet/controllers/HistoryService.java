/*package com.newtglobal.eFmFmFleet.controllers;

import java.text.DateFormat;
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

import com.newtglobal.efmfmVdsi.business.bo.IAarsaActualRouteTraveledBO;
import com.newtglobal.efmfmVdsi.business.bo.IAssignRouteBO;
import com.newtglobal.efmfmVdsi.business.bo.IRouteMasterBO;
import com.newtglobal.efmfmVdsi.model.AarsaActualRouteTraveledPO;
import com.newtglobal.efmfmVdsi.model.AssignRouteTxnPO;
import com.newtglobal.efmfmVdsi.model.RouteMasterPO;
import com.newtglobal.efmfmVdsi.web.ContextLoader;

@Component
@Path("/history")
public class HistoryService {
	private static Log log = LogFactory.getLog(HistoryService.class);


	@POST
	@Path("/gettickethistory")
	@Consumes("application/json")
	@Produces("application/json")
	public Response gettickethistory(CommonJSONObject commanjson)
			throws java.text.ParseException {
		DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate  = (Date) formatter.parse(commanjson.getFromdate());
		Date toDate  = (Date) formatter.parse(commanjson.getTodate());
		System.out.println(toDate);
		System.out.println("Date from dd-MM-yyyy String in Java : " + fromDate);		
		IRouteMasterBO iRouteMasterBO = (IRouteMasterBO) ContextLoader
				.getContext().getBean("IRouteMasterBO");
		log.info("history");
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader
				.getContext().getBean("IAssignRouteBO");	
		List<RouteMasterPO> instancerouteMasterPO=iRouteMasterBO.getAllRout(fromDate);		
		List<Map<String, Object>>jsonForHistory = new ArrayList<Map<String,Object>>();
		if (instancerouteMasterPO.size() > 0) {
		try{	
			for (RouteMasterPO  allRoute : instancerouteMasterPO) {					
				AssignRouteTxnPO  assignRouteTxnPO= iAssignRouteBO.getAllRouteByDriverNumber(allRoute.getDeviceNum()).get(0);			
				Map<String, Object> json  = new HashMap<String,Object>();	
				json.put("RouteId", allRoute.getRouteId());
				json.put("driverName", allRoute.getDriverName().trim());
				json.put("driverNumber", allRoute.getDeviceNum());
				String temp = allRoute.getRoutegroupId().replaceAll("'", "");				  
				  json.put("employeeList", temp);
				  json.put("zone", allRoute.getZoneId());
				   StringBuffer  asplong=new StringBuffer();
				   asplong.append(assignRouteTxnPO.getLongitude());
					StringBuffer asp= new StringBuffer();
					asp.append(assignRouteTxnPO.getLatitude());
					StringBuffer vehical= new StringBuffer();
					vehical.append(assignRouteTxnPO.getCustLat());
					StringBuffer vehicallong= new StringBuffer();
					vehicallong.append(assignRouteTxnPO.getCustLong());
					
					StringBuffer two= new StringBuffer();
					two.append(assignRouteTxnPO.getTowLat());
					StringBuffer serviccenter=new StringBuffer();
					serviccenter.append(assignRouteTxnPO.getServiceLat());
					StringBuffer twolong= new StringBuffer();
					twolong.append(assignRouteTxnPO.getTowLong());
					StringBuffer serviccenterlong=new StringBuffer();
					serviccenterlong.append(assignRouteTxnPO.getServiceLong());
					
					json.put("asplat", asp.toString());
					json.put("asplang", asplong.toString());
					json.put("vehicallat", vehical.toString());
					json.put("vehicallang",vehicallong.toString());
					json.put("traveledDistance", assignRouteTxnPO.getDistanceTravelled());
					json.put("waypoints", allRoute.getRoutewaypoints());
					json.put("cabStartlati",assignRouteTxnPO.getStartLat());
					json.put("cabStartlongi",assignRouteTxnPO.getStartLong());
					json.put("requestId", assignRouteTxnPO.getRegistrationId());
					
					
					jsonForHistory.add(json);
				  asp.replace(0, asp.length(),"");
				  vehical.replace(0, vehical.length(),"");
				  asplong.replace(0, asplong.length(),"");
				  vehicallong.replace(0, vehicallong.length(),"");
				
				  
			}
			
	 
			return Response.ok(jsonForHistory, MediaType.APPLICATION_JSON).build();
		}
		catch(NullPointerException npe)
		{
			return Response.ok("SOME_THING_IS_WRONG", MediaType.APPLICATION_JSON).build();
			
			
		}
		
		} else {
			
			return Response.ok("NO_CONTENT", MediaType.APPLICATION_JSON).build();
		}
		
	}
	
	
	@POST
	@Path("/actualtravelrout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getActualTravelledRoute(CommonJSONObject commanjson){
		try{
			IAarsaActualRouteTraveledBO iAarsaActualRouteTraveledBO = (IAarsaActualRouteTraveledBO) ContextLoader.getContext().getBean("IAarsaActualRouteTraveledBO");
			List<AarsaActualRouteTraveledPO> aarsaActualRouteTraveledPO = iAarsaActualRouteTraveledBO.getactualTraveldroute(commanjson.getRequestId());
			int counter=0;
			StringBuffer sb =new StringBuffer();
			List<Map<String, Object>>jsonforActualtraveled = new ArrayList<Map<String,Object>>();			
			System.out.println("total size is "+aarsaActualRouteTraveledPO.size());
			for(int i =0;i<aarsaActualRouteTraveledPO.size();i++){
					Map<String, Object> json =new HashMap<String,Object>();
					sb.append(aarsaActualRouteTraveledPO.get(i).getLatitude());
						sb.append(",");
						sb.append(aarsaActualRouteTraveledPO.get(i).getLongitude());
						sb.append("|");
						if(counter==18)
						{
							sb.append(aarsaActualRouteTraveledPO.get(i+1).getLatitude());
							sb.append(",");
							sb.append(aarsaActualRouteTraveledPO.get(i+1).getLongitude());
							
							json.put("route", sb.toString());
							jsonforActualtraveled.add(json);
							  sb.replace(0, sb.length(),"");
							  counter=0;
						}
						counter++;
				
				
				
			
				
			}
			
			System.out.println("final size "+jsonforActualtraveled.size());
			return Response.ok(jsonforActualtraveled, MediaType.APPLICATION_JSON)
					.build();
		}
		catch (Exception e)
		{
			return Response.ok("SOME_THING_IS_WRONG", MediaType.APPLICATION_JSON).build();
		}
			
	}
	

}*/