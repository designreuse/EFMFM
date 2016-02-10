package com.newtglobal.eFmFmRouter.routing;

public class Cost {
    private long distance_metre;
    private long duration_second;
    
    public Cost(long distance_metre, long duration_second) {
        this.distance_metre = distance_metre;
        this.duration_second = duration_second;
    }
    
    public double getTime()
    {
        return duration_second;
    }
    
    public double getDistance()
    {
        return distance_metre;
    }
}
