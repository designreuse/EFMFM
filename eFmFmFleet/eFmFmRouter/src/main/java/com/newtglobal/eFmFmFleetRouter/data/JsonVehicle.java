package com.newtglobal.eFmFmFleetRouter.data;

import java.util.ArrayList;

import net.minidev.json.JSONObject;

public class JsonVehicle {
	public String vehicle_id;
	public JsonVehicleType vehicle_type;
	public JsonGeocode location;
	
	public JsonVehicle(String vehicle_id, JsonVehicleType vehicle_type, JsonGeocode location) {
		this.vehicle_id = vehicle_id;
		this.vehicle_type = vehicle_type;
		this.location = location;
	}
	
	public JsonVehicle(JSONObject vehicle) {
		vehicle_id = (String) vehicle.get("vehicle_id");
		vehicle_type = new JsonVehicleType((JSONObject) vehicle.get("vehicle_type"));
		location = new JsonGeocode((JSONObject) vehicle.get("location"));
	}
	
	public static ArrayList<JsonVehicle> convertArray(ArrayList<JSONObject> vehicles) {
		ArrayList<JsonVehicle> jsonvehicles = new ArrayList<JsonVehicle>();
		for (JSONObject vehicle : vehicles) {
			jsonvehicles.add(new JsonVehicle(vehicle));
		}
		return jsonvehicles;
	}
}