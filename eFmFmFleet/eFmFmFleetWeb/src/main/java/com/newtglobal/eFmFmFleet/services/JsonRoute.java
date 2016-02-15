package com.newtglobal.eFmFmFleet.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minidev.json.JSONObject;

public class JsonRoute {
	public static int number_of_routes = 0;
	public int route_id;
	public double total_time;
	public double total_length;
	public double effective_length;
	public double effective_time;
	public int number_of_employees;
	public boolean escort_required;
	public JsonVehicleType vehicle_type;
	public boolean is_assigned_dummy_vehicle;
	public JsonVehicle vehicle;  //assigned iff it is not a dummy vehicle
	public String depot_name; //assigned iff it is a dummy vehicle
	public Map<Integer,Job> jobs;
	public Date start_time;
	public Date end_time;
	public long start_time_instant;
	public long end_time_instant;
	
	public void setStartTime(Date start_time) {
		this.start_time = start_time;
		this.start_time_instant = start_time.toInstant().toEpochMilli();
	}
	
	public void setEndTime(Date end_time) {
		this.end_time = end_time;
		this.end_time_instant = end_time.toInstant().toEpochMilli();
	}
	
	
	public class Job {
		public JsonEmployee employee;
		public String type;  //Is either pickup or deliver
		public Date time;
		public long time_instant;
		
		public Job(JsonEmployee employee, String type) {
			this.employee = employee;
			this.type = type;
		}
		
		public void setTime(Date time) {
			this.time = time;
			this.time_instant = time.toInstant().toEpochMilli();
		}
		
		public Job(JSONObject job){
			this.employee = new JsonEmployee((JSONObject) job.get("employee"));
			this.type = (String) job.get("type");
			this.time_instant = (Long) job.get("time_instant");
			this.time = new Date((Long) this.time_instant);
		}
	}
	
	public Map<Integer, Job> jobstoMap(Map<String, JSONObject> jsonjobs) {
		Map<Integer, Job> jobs = new HashMap<Integer, Job>();
		Set<String> keySet = jsonjobs.keySet();
		for(String  i : keySet) {
			jobs.put(Integer.parseInt(i), new Job(jsonjobs.get(i)));
		}
		return jobs;
	}
	
	public JsonRoute() {
		//empty constructor just for intialising with default values
	}
	
	public JsonRoute(JSONObject route) {
		long temp = (Long) route.get("route_id");
		route_id = (int) temp;
		total_time = (Double) route.get("total_time");
		total_length = (Double) route.get("total_length");
		effective_length = (Double) route.get("effective_length");
		effective_time = (Double) route.get("effective_time");
		temp = (Long) route.get("number_of_employees");
		number_of_employees = (int) temp;
		escort_required = (Boolean) route.get("escort_required");
		vehicle_type = new JsonVehicleType((JSONObject) route.get("vehicle_type"));
		is_assigned_dummy_vehicle = (Boolean) route.get("is_assigned_dummy_vehicle");
		start_time_instant = (Long) route.get("start_time_instant");
		end_time_instant = (Long) route.get("end_time_instant");
		start_time = new Date(start_time_instant);
		end_time = new Date(end_time_instant);
		try {
			vehicle = new JsonVehicle((JSONObject) route.get("vehicle"));
		} catch (Exception E) {
			vehicle = null;
		}
		try {
			depot_name = (String) route.get("depot_name");
		} catch (Exception E) {
			depot_name = null;
		}
		jobs = jobstoMap((Map<String, JSONObject>) route.get("jobs"));
	}
	
	public static ArrayList<JsonRoute> convertArray(ArrayList<JSONObject> jsonroutes) {
		ArrayList<JsonRoute> routes = new ArrayList<JsonRoute>();
		for (JSONObject route : jsonroutes) {
			routes.add(new JsonRoute(route));
		}
		return routes;
	}
}