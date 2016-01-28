package com.newtglobal.eFmFmFleet.services;

import java.util.ArrayList;

import net.minidev.json.JSONObject;

public class JsonDepot {
	public String depot_id;
	public String name;
	public JsonGeocode location;
	public ArrayList<JsonVehicleShed> vehicle_type;
	
	public JsonDepot(String depot_id, String name, JsonGeocode location)
	{
		this.depot_id = depot_id;
		this.name = name;
		this.location = location;
		vehicle_type = new ArrayList<JsonVehicleShed>();
	}
	@SuppressWarnings("unchecked")
	public JsonDepot(JSONObject depot) {
		depot_id = (String) depot.get("depot_id");
		name = (String) depot.get("name");
		location = new JsonGeocode((JSONObject) depot.get("location"));
		vehicle_type = new ArrayList<JsonVehicleShed>();
		for(JSONObject vehicle_shed : (ArrayList<JSONObject>) depot.get("vehicle_type")) {
			long quantity = (Long) vehicle_shed.get("quantity");
			vehicle_type.add(new JsonVehicleShed(new JsonVehicleType((JSONObject) vehicle_shed.get("vehicle")), 
					(int) quantity));
		}
	}
	
	public void add_vehicle(JsonVehicleType V, int quantity) {
		vehicle_type.add(new JsonVehicleShed(V, quantity));
	}
	
	public class JsonVehicleShed {
		public JsonVehicleType vehicle;
		public int quantity;
		
		public JsonVehicleShed(JsonVehicleType vehicle, int quantity) {
			this.vehicle = vehicle;
			this.quantity = quantity;
		}
	}
	
	public JsonVehicleShed containsVehicle(String type_name)
	{
		for(JsonVehicleShed S : vehicle_type) {
			if (S.vehicle.vehicle_type_name.equals(type_name)) return S;
		}
		return null;
	}
	
	public static ArrayList<JsonDepot> convertArray(ArrayList<JSONObject> depots) {
		ArrayList<JsonDepot> jsondepot = new ArrayList<JsonDepot>();
		for (JSONObject depot : depots) {
			jsondepot.add(new JsonDepot(depot));
		}
		return jsondepot;
	}
}
