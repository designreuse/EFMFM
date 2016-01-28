package com.newtglobal.eFmFmRouter.routing;

import com.newtglobal.eFmFmRouter.data.JsonVehicle;
import com.newtglobal.eFmFmRouter.data.JsonVehicleType;

import jsprit.core.problem.vehicle.VehicleType;
import jsprit.core.problem.vehicle.VehicleTypeImpl;

public final class VehiclePrototype {
	private int capacity;
	private String name;
	private String vehicleTypeId;
	private VehicleTypeImpl.Builder vehicleTypeBuilder;
	private VehicleType vehicleType;
	
	public int getCapacity() {
		return capacity;
	}

	public String getName() {
		return name;
	}

	public String getVehicleTypeId() {
		return vehicleTypeId;
	}

	public VehicleTypeImpl.Builder getVehicleTypeBuilder() {
		return vehicleTypeBuilder;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}
	
	public VehiclePrototype(String name, int capacity) {
		this.capacity = capacity;
		this.name = name;
		vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance(name)
				.addCapacityDimension(0, capacity).setCostPerDistance(1);
		vehicleType = vehicleTypeBuilder.build();
	}
	
	public VehiclePrototype(JsonVehicleType V) {
		this.capacity = V.capacity;
		this.name = V.vehicle_type_name;
		this.vehicleTypeId = V.vehicle_type_id;
		vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance(name)
				.addCapacityDimension(0, capacity);
		vehicleType = vehicleTypeBuilder.build();
	}
	
	public VehiclePrototype(JsonVehicle V) {
		this.capacity = V.vehicle_type.capacity;
		this.name = V.vehicle_id;
		this.vehicleTypeId = V.vehicle_id;
		vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance(name)
				.addCapacityDimension(0, capacity);
		vehicleType = vehicleTypeBuilder.build();
	}
}