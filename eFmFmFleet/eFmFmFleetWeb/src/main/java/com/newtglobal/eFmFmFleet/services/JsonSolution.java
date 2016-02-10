package com.newtglobal.eFmFmFleet.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;

public class JsonSolution
{
	public Settings settings;
	
	public int number_of_routes;
	public int number_of_employees;
  public int number_of_unassigned_employees;
	public double total_route_length;
	public double total_route_time;
	public double total_effective_route_length;
	public double total_effective_route_time;

	private Map<String, JsonEmployee> employees_map = new HashMap<String, JsonEmployee>();
	private Map<String, JsonVehicleType> vehicle_type_map = new HashMap<String, JsonVehicleType>();
	
	public ArrayList<JsonRoute> routes;
	public ArrayList<JsonEmployee> unassigned_employees;
	
	public JsonSolution(ArrayList<JsonEmployee> employees, ArrayList<JsonVehicleType> vehicle_types) {
		number_of_routes = 0;
		number_of_employees = 0;
    number_of_unassigned_employees = 0;
		total_route_length = 0;
		total_route_time = 0;
		total_effective_route_length = 0;
		total_effective_route_time = 0;
		
		routes = new ArrayList<JsonRoute>();
		unassigned_employees = new ArrayList<JsonEmployee>();
		
		employees_map = new HashMap<String, JsonEmployee>();
		vehicle_type_map = new HashMap<String, JsonVehicleType>();
		
		for(JsonEmployee E : employees) {
			employees_map.put(E.emp_id, E);
		}
		
		for(JsonVehicleType V : vehicle_types) {
			vehicle_type_map.put(V.vehicle_type_name, V);
		}
	}
	
	@SuppressWarnings("unchecked")
	public JsonSolution(JSONObject solution) {
		employees_map = null;
		vehicle_type_map = null;
		settings = new Settings((JSONObject) solution.get("settings"));
		long temp;
		temp = (Long) solution.get("number_of_routes");
		number_of_routes = (int) temp;
		temp = (Long) solution.get("number_of_employees");
		number_of_employees = (int) temp;
		temp = (Long) solution.get("number_of_unassigned_employees");
		number_of_unassigned_employees = (int) temp;
		total_route_length = (Double) solution.get("total_route_length");
		total_route_time = (Double) solution.get("total_route_time");
		total_effective_route_length = (Double) solution.get("total_effective_route_length");
		total_effective_route_time = (Double) solution.get("total_effective_route_time");
		routes = JsonRoute.convertArray((ArrayList<JSONObject>) solution.get("routes"));
		unassigned_employees = JsonEmployee.convertArray((ArrayList<JSONObject>) solution.get("unassigned_employees"));	
	}
}
