package com.newtglobal.eFmFmRouter.data;

import java.util.ArrayList;

import net.minidev.json.JSONObject;

public class JsonVehicleType {
	public String vehicle_type_id;
	public String vehicle_type_name;
	public int capacity;
	
	public JsonVehicleType(String vehicle_type_id, String vehicle_type_name, int capacity) {
		this.vehicle_type_id = vehicle_type_id;
		this.vehicle_type_name = vehicle_type_name;
		this.capacity = capacity;
	}
	
	public JsonVehicleType(JsonVehicleType T) {
		this.vehicle_type_id = T.vehicle_type_id;
		this.vehicle_type_name = T.vehicle_type_name;
		this.capacity = T.capacity;
	}
	public JsonVehicleType(JSONObject vehicle_type) {
		vehicle_type_id = (String) vehicle_type.get("vehicle_type_id");
		vehicle_type_name = (String) vehicle_type.get("vehicle_type_name");
		long capacity = (Long) vehicle_type.get("capacity");
		this.capacity = (int) capacity;
	}
	
	public static ArrayList<JsonVehicleType> convertArray(ArrayList<JSONObject> vehicletypes) {
		ArrayList<JsonVehicleType> jsonvehicletypes = new ArrayList<JsonVehicleType>();
		for (JSONObject vehicletype : vehicletypes) {
			jsonvehicletypes.add(new JsonVehicleType(vehicletype));
		}
		return jsonvehicletypes;
	}
}