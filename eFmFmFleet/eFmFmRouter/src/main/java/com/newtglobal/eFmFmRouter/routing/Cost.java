package com.newtglobal.eFmFmRouter.routing;

public class Cost {
	private Long distance_metre;
	private Long duration_second;
	
	public Cost(Long distance_metre, Long duration_second) {
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
