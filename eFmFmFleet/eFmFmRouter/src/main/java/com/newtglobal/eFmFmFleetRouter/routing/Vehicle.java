package com.newtglobal.eFmFmFleetRouter.routing;

import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.problem.vehicle.VehicleImpl.Builder;

import com.newtglobal.eFmFmFleetRouter.clustering.Geocode;

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

	public Vehicle(VehiclePrototype prototype, Geocode depot_location, String name, boolean return_to_depot) {
		this.depotLocation = depot_location;
		vehicleBuilder = VehicleImpl.Builder.newInstance(name);
		vehicleBuilder.setStartLocation(Location.newInstance(depot_location.getLat(),
				depot_location.getLong()));
		vehicleBuilder.setType(prototype.getVehicleType()).setReturnToDepot(return_to_depot);
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