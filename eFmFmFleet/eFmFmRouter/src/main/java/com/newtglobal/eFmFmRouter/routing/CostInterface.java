package com.newtglobal.eFmFmRouter.routing;

import com.newtglobal.eFmFmRouter.clustering.Geocode;

public interface CostInterface {
    public Cost getCost(Geocode G1, Geocode G2);
}
