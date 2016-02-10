package com.newtglobal.eFmFmRouter.clustering;

import java.io.Serializable;

import com.newtglobal.eFmFmRouter.data.JsonGeocode;

public class Geocode implements Serializable{
	private static final long serialVersionUID = 6495532690546696883L;
	private double latitude;
    private double longitude;
    
    public Geocode(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Geocode(Geocode G)
    {
    	this.latitude = G.latitude;
    	this.longitude = G.longitude;
    }
    
    public Geocode(JsonGeocode G)
    {
    	this.latitude = G.latitude;
    	this.longitude = G.longitude;
    }

    public String toString() {
         String str = "Latitude : " + latitude + " Longitude : " + longitude;
         return str;
    }
    
    public double getLat() {
    	return latitude;
    }
    
    public double getLong() {
    	return longitude;
    }
    
    public static double distance(Geocode G1, Geocode G2)
    {
    	double R = 6378.137; // Radius of earth in KM
	    double dLat = (G1.getLat() - G2.getLat()) * Math.PI / 180;
	    double dLon = (G1.getLong() - G2.getLong()) * Math.PI / 180;
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	    Math.cos(G1.getLat() * Math.PI / 180) * Math.cos(G2.getLat() * Math.PI / 180) *
	    Math.sin(dLon/2) * Math.sin(dLon/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double d = R * c;
	    return d * 1000;  //return in metres
    }
}