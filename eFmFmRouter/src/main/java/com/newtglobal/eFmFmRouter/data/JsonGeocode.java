package com.newtglobal.eFmFmRouter.data;

import com.newtglobal.eFmFmRouter.clustering.Geocode;

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
	
	public JsonGeocode(JSONObject geocode) {
		this.latitude = (Double) geocode.get("latitude");
		this.longitude = (Double) geocode.get("longitude");
	}
	
	public JsonGeocode(Geocode G) {
		this.latitude = G.getLat();
		this.longitude = G.getLong();
	}
}
