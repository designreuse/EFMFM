package com.newtglobal.eFmFmFleetRouter.routing;

import com.newtglobal.eFmFmFleetRouter.clustering.Geocode;
public class CrowCosts implements CostInterface{
	@Override
	public Cost getCost(Geocode G1, Geocode G2){
		double R = 6378.137; // Radius of earth in KM
	    double dLat = (G1.getLat() - G2.getLat()) * Math.PI / 180;
	    double dLon = (G1.getLong() - G2.getLong()) * Math.PI / 180;
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	    Math.cos(G1.getLat() * Math.PI / 180) * Math.cos(G2.getLat() * Math.PI / 180) *
	    Math.sin(dLon/2) * Math.sin(dLon/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double d = R * c;
	    return new Cost((long) d*1000, (long) (d*1000/10.0));  //return in metres
	}
}