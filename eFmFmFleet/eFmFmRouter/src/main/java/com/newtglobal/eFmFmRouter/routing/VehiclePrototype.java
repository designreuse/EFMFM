package com.newtglobal.eFmFmRouter.routing;

import java.util.ArrayList;
import java.util.List;

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
	private static List<VehiclePrototype> vehicleTypes = new ArrayList<VehiclePrototype>();
	
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
	
	public static List<VehiclePrototype> getVehicleTypes(){
		return vehicleTypes;
	}
	
	public static VehiclePrototype getVehicleType(JsonVehicleType V) {
		for (VehiclePrototype vehicleProto : vehicleTypes) {
			if (vehicleProto.name.equalsIgnoreCase(V.vehicle_type_name) && 
					vehicleProto.vehicleTypeId.equalsIgnoreCase(V.vehicle_type_id)
					&& vehicleProto.capacity == V.capacity) {
				return vehicleProto;
			}
		}
		return null;
	}
	
	public VehiclePrototype(String name, int capacity) {
		this.capacity = capacity;
		this.name = name;
		vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance(name)
				.addCapacityDimension(0, capacity).setCostPerDistance(1);
		vehicleType = vehicleTypeBuilder.build();
		vehicleTypes.add(this);
	}
	
	public VehiclePrototype(JsonVehicleType V) {
		this.capacity = V.capacity;
		this.name = V.vehicle_type_name;
		this.vehicleTypeId = V.vehicle_type_id;
		vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance(name)
				.addCapacityDimension(0, capacity);
		vehicleType = vehicleTypeBuilder.build();
		vehicleTypes.add(this);
	}
}