package com.newtglobal.eFmFmRouter.routing;

import com.newtglobal.eFmFmRouter.clustering.Geocode;

public class CrowCosts implements CostInterface{
    @Override
    public Cost getCost(Geocode G1, Geocode G2){
        double dLat = toRadians(Math.abs(G1.getLat() - G2.getLat()));
        double dLong = toRadians(Math.abs(G1.getLong() - G2.getLong()));
        double a = Math.pow(Math.sin(dLat/2),2) + Math.cos(toRadians(G1.getLat()))
            *Math.cos(toRadians(G2.getLat()))*Math.pow(Math.sin(dLong/2),2);
        double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double radius = 6371000;
        return new Cost((long) (radius*c), (long)(radius*c/10.0));
    }
    
    public static double toRadians(double degrees)
    {
        return degrees*Math.PI/180;
    }
}