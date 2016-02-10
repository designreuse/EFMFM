package com.newtglobal.eFmFmFleet.services;

import net.minidev.json.JSONObject;

public class JsonGeocode {
	public double latitude;
	public double longitude;
	
	public JsonGeocode(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public JsonGeocode(JsonGeocode G) {
		this.latitude = G.latitude;
		this.longitude = G.longitude;
	}
	
	public JsonGeocode(String latlong)
	{
		String [] geocode = latlong.split(",");
		this.latitude = Double.parseDouble(geocode[0].trim());
		this.longitude = Double.parseDouble(geocode[1].trim());
	}
	
	public JsonGeocode(JSONObject geocode) {
		this.latitude = (Double) geocode.get("latitude");
		this.longitude = (Double) geocode.get("longitude");
	}
}
