package com.newtglobal.eFmFmFleetRouter.routing;

import java.util.*;

import com.newtglobal.eFmFmFleetRouter.clustering.Geocode;
import com.newtglobal.eFmFmFleetRouter.data.JsonDepot;
import com.newtglobal.eFmFmFleetRouter.data.JsonVehicle;

public final class Depot {
	private ArrayList<Vehicle> vehicleList;
	private int content;
	private Geocode depotLocation;
	private String name;
	private static int numberOfDepots = 0;
	private String depotId;

	public ArrayList<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public Geocode getDepotLocation() {
		return depotLocation;
	}

	public String getName() {
		return name;
	}

	public static int getNumberOfDepots() {
		return numberOfDepots;
	}

	public String getDepotId() {
		return depotId;
	}

	public Depot(Geocode depot_location, String name)
	{
		content = 0;
		vehicleList = new ArrayList<Vehicle>();
		this.depotLocation = depot_location;
		this.name = name;
		numberOfDepots += 1;
		depotId = numberOfDepots + "";
	}
	
	public Depot(JsonDepot D) {
		numberOfDepots += 1;
		vehicleList = new ArrayList<Vehicle>();
		this.depotLocation = new Geocode(D.location);
		this.name = D.name;
		this.depotId = D.depot_id;
		this.content = 0;
		
		for (JsonDepot.JsonVehicleShed S : D.vehicle_type) {
			this.addVehicle(new VehiclePrototype(S.vehicle), S.quantity);
		}
	}
	
	public Depot(JsonVehicle V) {
		numberOfDepots += 1;
		vehicleList = new ArrayList<Vehicle>();
		this.depotLocation = new Geocode(V.location);
		this.name = V.vehicle_type.vehicle_type_name;
		this.depotId = V.vehicle_id;
		this.content = 0;
		this.vehicleList.add(new Vehicle(new VehiclePrototype(V), depotLocation, V.vehicle_id, false));
	}
	
	public boolean isEqual(Depot D) {
		return (depotId == D.depotId);
	}
	
	public void addVehicle(VehiclePrototype V, int quantity)
	{
		for(int i = 1; i <= quantity; i++) {
			content += 1;
			Vehicle V2;
			V2 = new Vehicle(V, depotLocation, V.getName() + "_" + content + "_" + name, true);
			vehicleList.add(V2);
		}
	}
	
	//returns true if vehicle to remove is found
	public boolean removeVehicle(jsprit.core.problem.vehicle.Vehicle vehicle) {
		Vehicle VR = null;
		for(Vehicle V2 : vehicleList) {
			if(vehicle.equals(V2.getVehicleInstance())) {
				VR = V2;
				break;
			}
		}
		if (VR!= null) {
			vehicleList.remove(VR);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean containsVehicle(jsprit.core.problem.vehicle.Vehicle vehicle) {
		for (Vehicle V : vehicleList) {
			if (V.getVehicleInstance().equals(vehicle)) {
				return true;
			}
		}
		return false;
	}
}