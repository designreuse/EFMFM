package com.newtglobal.eFmFmRouter.routing;

import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.problem.vehicle.VehicleImpl.Builder;

import com.newtglobal.eFmFmRouter.clustering.Geocode;
import com.newtglobal.eFmFmRouter.data.JsonVehicle;
import com.newtglobal.eFmFmRouter.data.Settings;

import jsprit.core.problem.Location;

public final class Vehicle {
  private VehiclePrototype prototype;
  private Builder vehicleBuilder;
  private VehicleImpl vehicleInstance;
  private Geocode depotLocation;
  private Location location;
  private String name;
  
  public VehiclePrototype getPrototype() {
    return prototype;
  }

  public Builder getVehicleBuilder() {
    return vehicleBuilder;
  }

  public VehicleImpl getVehicleInstance() {
    return vehicleInstance;
  }

  public Geocode getDepotLocation() {
    return depotLocation;
  }

  public Location getLocation() {
    return location;
  }

  public String getName() {
    return name;
  }

  public Vehicle(VehiclePrototype prototype, Geocode depotLocation, String name) {
    this.depotLocation = depotLocation;
    vehicleBuilder = VehicleImpl.Builder.newInstance(name);
    vehicleBuilder.setStartLocation(Location.newInstance(depotLocation.getLat(),
        depotLocation.getLong()));
    vehicleBuilder.setType(prototype.getVehicleType()).setReturnToDepot(true);
    vehicleInstance = vehicleBuilder.build();
  }

  public Vehicle(VehiclePrototype prototype,
      JsonVehicle V, Settings settings) {
    this.depotLocation = new Geocode(V.startLocation);
    vehicleBuilder = VehicleImpl.Builder.newInstance(V.vehicle_id);
    vehicleBuilder.setStartLocation(Location.newInstance(depotLocation.getLat(),
        depotLocation.getLong()));
    if (V.maxTravelTime > 0) {
    	if (settings.trip_type.equalsIgnoreCase("login")) {
    		vehicleBuilder.setType(prototype.getVehicleType()).setReturnToDepot(true)
    		.setEndLocation(Location.newInstance(V.endLocation.latitude, V.endLocation.longitude))
    		.setEarliestStart(settings.max_travel_time + settings.max_idle_time -
    				V.maxTravelTime).setLatestArrival(settings.max_travel_time + settings.max_idle_time);
    	}
    	else {
    		vehicleBuilder.setType(prototype.getVehicleType()).setReturnToDepot(true)
    		.setEndLocation(Location.newInstance(V.endLocation.latitude, V.endLocation.longitude))
    		.setEarliestStart(0).setLatestArrival(V.maxTravelTime);
    	}
    }
    else {
	    vehicleBuilder.setType(prototype.getVehicleType()).setReturnToDepot(true)
	    .setEndLocation(Location.newInstance(V.endLocation.latitude, V.endLocation.longitude));
    }
	vehicleInstance = vehicleBuilder.build();
  }

  public Vehicle(VehiclePrototype prototype, Location location, String name, boolean return_to_depot) {
    this.location = location;
    vehicleBuilder = VehicleImpl.Builder.newInstance(name);
    vehicleBuilder.setStartLocation(location);
    vehicleBuilder.setType(prototype.getVehicleType()).setReturnToDepot(return_to_depot);
    vehicleInstance = vehicleBuilder.build();
  }
}