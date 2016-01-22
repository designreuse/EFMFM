package com.newtglobal.eFmFmFleetRouter.routing;

import com.newtglobal.eFmFmFleetRouter.clustering.Geocode;

public interface CostInterface {
	public Cost getCost(Geocode G1, Geocode G2);
}
