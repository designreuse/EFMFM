package com.newtglobal.eFmFmRouter.data;

import java.util.ArrayList;

import net.minidev.json.JSONObject;

public class JsonVehicle {
	public String vehicle_id;
	public JsonVehicleType vehicle_type;
	public JsonGeocode startLocation;
	public JsonGeocode endLocation;
	public long maxTravelTime;

	
	public JsonVehicle(String vehicle_id, JsonVehicleType vehicle_type, JsonGeocode startLocation,
      JsonGeocode endLocation) {
		this.vehicle_id = vehicle_id;
		this.vehicle_type = vehicle_type;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.maxTravelTime = -1;
	}
	
	public JsonVehicle(String vehicle_id, JsonVehicleType vehicle_type, JsonGeocode startLocation,
		      JsonGeocode endLocation, long maxTravelTime) {
				this.vehicle_id = vehicle_id;
				this.vehicle_type = vehicle_type;
				this.startLocation = startLocation;
				this.endLocation = endLocation;
				this.maxTravelTime = maxTravelTime;
			}
	
	public JsonVehicle(JSONObject vehicle) {
		vehicle_id = (String) vehicle.get("vehicle_id");
		vehicle_type = new JsonVehicleType((JSONObject) vehicle.get("vehicle_type"));
		startLocation = new JsonGeocode((JSONObject) vehicle.get("startLocation"));
		endLocation = new JsonGeocode((JSONObject) vehicle.get("endLocation"));
		maxTravelTime = (Long) vehicle.get("maxTravelTime");
	}
	
	public static ArrayList<JsonVehicle> convertArray(ArrayList<JSONObject> vehicles) {
		ArrayList<JsonVehicle> jsonvehicles = new ArrayList<JsonVehicle>();
		for (JSONObject vehicle : vehicles) {
			jsonvehicles.add(new JsonVehicle(vehicle));
		}
		return jsonvehicles;
	}
}
