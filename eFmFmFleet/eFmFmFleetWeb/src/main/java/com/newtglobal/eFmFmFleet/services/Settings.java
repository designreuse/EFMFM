package com.newtglobal.eFmFmFleet.services;

import java.util.Date;

import net.minidev.json.JSONObject;

public class Settings {
    public long max_travel_time;
    public long max_radial_distance;
    public long max_route_length;
    public long max_effective_route_length;
    public long max_idle_time;
    public long employee_service_time;
    public boolean escorts;
    public boolean rigid_escort_constraint;
    public boolean global_employee_time_constraint;
    public String trip_type;
    public boolean finite_vehicles;
    public boolean auto_clustering;
    public boolean use_api;
    public Date shift_time;
    public long shift_time_instant;
    public long shift_buffer_time;
    
    public Settings() {
    	max_travel_time = 7200;
    	max_radial_distance = -1;
    	max_route_length = 250000;
    	max_effective_route_length = -1;
    	employee_service_time = 180;
    	max_idle_time = 7200;
    	escorts = false;
    	rigid_escort_constraint = false;
    	trip_type = "login";
    	finite_vehicles = true;
    	auto_clustering = true;
    	shift_time = new Date();
    	use_api = true;
    	shift_buffer_time = 0;
      global_employee_time_constraint = true;
    }
    
    public Settings(Settings S) {
		this.max_travel_time = S.max_travel_time;
		this.max_radial_distance = S.max_radial_distance;
		this.max_route_length = S.max_route_length;
		this.max_effective_route_length = S.max_effective_route_length;
		this.max_idle_time = S.max_idle_time;
		this.employee_service_time = S.employee_service_time;
		this.escorts = S.escorts;
		this.rigid_escort_constraint = S.rigid_escort_constraint;
		this.trip_type = S.trip_type;
		this.finite_vehicles = S.finite_vehicles;
		this.auto_clustering = S.auto_clustering;
		this.use_api = S.use_api;
		this.shift_time = S.shift_time;
		this.shift_time_instant = S.shift_time_instant;
		this.shift_buffer_time = S.shift_buffer_time;
    this.global_employee_time_constraint = S.global_employee_time_constraint;
	}

	public void finalise() {
    	shift_time_instant = shift_time.toInstant().toEpochMilli();
    }
    
	public Settings(JSONObject settings) {
    	max_travel_time = (Long) settings.get("max_travel_time");
    	max_radial_distance = (Long) settings.get("max_radial_distance");
    	max_route_length = (Long) settings.get("max_route_length");
    	max_effective_route_length = (Long) settings.get("max_effective_route_length");
    	employee_service_time = (Long) settings.get("employee_service_time");
    	max_idle_time = (Long) settings.get("max_idle_time");
    	escorts = (Boolean) settings.get("escorts");
    	rigid_escort_constraint = (Boolean) settings.get("rigid_escort_constraint");
    	trip_type = (String) settings.get("trip_type");
    	finite_vehicles = (Boolean) settings.get("finite_vehicles");
    	auto_clustering = (Boolean) settings.get("auto_clustering");
    	shift_time_instant = (Long) settings.get("shift_time_instant");
    	shift_time = new Date((Long) shift_time_instant);
    	use_api = (Boolean) settings.get("use_api");
    	shift_buffer_time = (Long) settings.get("shift_buffer_time");
      global_employee_time_constraint = (Boolean) settings.get("global_employee_time_constraint");
    }
}
