package com.newtglobal.eFmFmFleetRouter.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;

public class JsonEmployee {
	public String emp_id;
	public String branch_id;
	public String sex; //Male or Female
	public JsonGeocode pickup;
	public JsonGeocode drop;
  public long time;
	
	public JsonEmployee(String emp_id, JsonGeocode pickup, JsonGeocode drop) {
		this.emp_id = emp_id;
		this.pickup = pickup;
		this.drop = drop;
		this.sex = "NA";
		this.branch_id = null;
    this.time = -1;
	}
	
  public JsonEmployee(String emp_id, long time,JsonGeocode pickup, JsonGeocode drop) {
		this.emp_id = emp_id;
		this.pickup = pickup;
		this.drop = drop;
		this.sex = "NA";
		this.branch_id = null;
    this.time = time;
	}

	public JsonEmployee(String emp_id, JsonGeocode pickup, JsonGeocode drop, String sex) {
		this.emp_id = emp_id;
		this.pickup = pickup;
		this.drop = drop;
		this.sex = sex;
		this.branch_id = null;
    this.time = -1;
	}
  
  public JsonEmployee(String emp_id, long time, JsonGeocode pickup, JsonGeocode drop, String sex) {
		this.emp_id = emp_id;
		this.pickup = pickup;
		this.drop = drop;
		this.sex = sex;
		this.branch_id = null;
    this.time = time;
	}
	
	public JsonEmployee(String emp_id, String branch_id, JsonGeocode pickup, JsonGeocode drop, String sex) {
		this.emp_id = emp_id;
		this.pickup = pickup;
		this.drop = drop;
		this.sex = sex;
		this.branch_id = branch_id;
    this.time = -1;
	}

  public JsonEmployee(String emp_id, long time ,String branch_id, JsonGeocode pickup, JsonGeocode drop, String sex) {
		this.emp_id = emp_id;
		this.pickup = pickup;
		this.drop = drop;
		this.sex = sex;
		this.branch_id = branch_id;
    this.time = time;
	}
	
	public JsonEmployee(JSONObject employee) {
		this.emp_id = (String) employee.get("emp_id");
		this.sex = (String) employee.get("sex");
		this.pickup = new JsonGeocode((JSONObject) employee.get("pickup"));
		this.drop = new JsonGeocode((JSONObject) employee.get("drop"));
		this.branch_id = (String) employee.get("branch_id");
    this.time = (Long) employee.get("time");
	}
	
	public JsonEmployee(JsonEmployee E) {
		this.emp_id = E.emp_id;
		this.sex = E.sex;
		this.pickup = new JsonGeocode(E.pickup);
		this.drop = new JsonGeocode(E.drop);
		this.branch_id = E.branch_id;
    this.time = E.time;
	}
	
	public static ArrayList<JsonEmployee> convertArray(ArrayList<JSONObject> employees) {
		ArrayList<JsonEmployee> jsonEmployees = new ArrayList<JsonEmployee>();
		for (JSONObject employee : employees) {
			jsonEmployees.add(new JsonEmployee(employee));
		}
		return jsonEmployees;
	}
	
	public static Map<Integer, ArrayList<JsonEmployee>> convertMap(Map<String, ArrayList<JSONObject>> employeesMap) {
		Map<Integer, ArrayList<JsonEmployee>> jsonEmployeesMap = new HashMap<Integer, ArrayList<JsonEmployee>>();
		for (String key : employeesMap.keySet()) {
			int keyInt = Integer.parseInt(key);
			jsonEmployeesMap.put(keyInt, convertArray(employeesMap.get(key)));
		}
		return jsonEmployeesMap;
	}
}
