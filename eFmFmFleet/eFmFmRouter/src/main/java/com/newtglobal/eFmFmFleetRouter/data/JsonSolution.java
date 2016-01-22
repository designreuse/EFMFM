package com.newtglobal.eFmFmFleetRouter.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.newtglobal.eFmFmFleetRouter.main.EfmfmVRP;
import com.newtglobal.eFmFmFleetRouter.routing.Depot;
import com.newtglobal.eFmFmFleetRouter.routing.Employee;
import com.newtglobal.eFmFmFleetRouter.routing.RoutingProblem;

import jsprit.core.problem.job.Job;
import jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import jsprit.core.problem.solution.route.VehicleRoute;
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
	
	private Map<String, JsonEmployee> employees_map;
	private Map<String, JsonVehicleType> vehicle_type_map;
	private Map<String, JsonVehicle> vehicle_map;
	
	public ArrayList<JsonRoute> routes;
	public ArrayList<JsonEmployee> unassigned_employees;
	
	public JsonSolution(ArrayList<JsonEmployee> employees, ArrayList<JsonVehicleType> vehicle_types,
			ArrayList<JsonVehicle> vehicles, Settings settings) {
		number_of_routes = 0;
		number_of_employees = 0;
    number_of_unassigned_employees = 0;
		total_route_length = 0;
		total_route_time = 0;
		total_effective_route_length = 0;
		total_effective_route_time = 0;
		this.settings = settings;
		
		routes = new ArrayList<JsonRoute>();
		unassigned_employees = new ArrayList<JsonEmployee>();
		
		employees_map = new HashMap<String, JsonEmployee>();
		vehicle_type_map = new HashMap<String, JsonVehicleType>();
		vehicle_map = new HashMap<String, JsonVehicle>();
		
		for(JsonEmployee E : employees) {
			employees_map.put(E.emp_id, E);
		}
		
		for(JsonVehicleType V : vehicle_types) {
			vehicle_type_map.put(V.vehicle_type_name, V);
		}
		
		for(JsonVehicle V : vehicles) {
			vehicle_map.put(V.vehicle_id, V);
		}
	}
	
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
	
	public void addUnassignedEmployees(ArrayList<Employee> employees) {
		for(Employee E: employees){
			unassigned_employees.add(employees_map.get(E.getEmployeeId()));
		}
    number_of_unassigned_employees = unassigned_employees.size();
	}
	
	public void add(RoutingProblem R, ArrayList<Depot> depots, EfmfmVRP vrp, boolean reschedule) {
		VehicleRoutingProblemSolution solution = R.getSolution();
		
		for(VehicleRoute route : solution.getRoutes()) {
			JsonRoute json_route = new JsonRoute();
			json_route.createRoute(route, R, employees_map, vehicle_type_map, vehicle_map, depots);
			total_route_time += json_route.total_time;
			total_route_length += json_route.total_length;
			total_effective_route_time += json_route.effective_time;
			total_effective_route_length += json_route.effective_length;
			int used_capacity = json_route.number_of_employees;
			if (json_route.escort_required) used_capacity+=1;
			
			if (used_capacity/((double) json_route.vehicle_type.capacity) < 0.5 
					&& json_route.is_assigned_dummy_vehicle && reschedule) {
				vrp.reschedule(json_route.getEmployees(this.settings), json_route.vehicle_type, json_route.depot_name);
				JsonRoute.number_of_routes -= 1;
			}
			else {
			number_of_routes += 1;
			number_of_employees += json_route.number_of_employees;
			routes.add(json_route);
			}
		}
		
		Collection<Job> unassigned_jobs = solution.getUnassignedJobs();
		for(Job J : unassigned_jobs) {
			unassigned_employees.add(employees_map.get(J.getId()));
		}
    number_of_unassigned_employees = unassigned_employees.size();
	}
}
