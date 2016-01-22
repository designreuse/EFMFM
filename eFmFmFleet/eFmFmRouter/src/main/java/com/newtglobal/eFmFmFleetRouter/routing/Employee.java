package com.newtglobal.eFmFmFleetRouter.routing;

import com.newtglobal.eFmFmFleetRouter.clustering.Geocode;
import com.newtglobal.eFmFmFleetRouter.data.JsonEmployee;;

public final class Employee {
	private Geocode pickupLocation;
	private Geocode dropLocation;
	private long maxTripDuration;
	private long pickupServiceTime;
	private static int numberOfEmployees = 0;
	private String employeeId;
	private String sex;
	
	public Geocode getPickupLocation() {
		return pickupLocation;
	}

	public Geocode getDropLocation() {
		return dropLocation;
	}

	public long getMaxTripDuration() {
		return maxTripDuration;
	}

	public long getPickupServiceTime() {
		return pickupServiceTime;
	}

	public static int getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getSex() {
		return sex;
	}

	public Employee(Geocode pickup_location, Geocode drop_location, long max_trip_duration, 
			String employee_id) {
		this.pickupLocation = pickup_location;
		this.dropLocation = drop_location;
		this.maxTripDuration = max_trip_duration;
		numberOfEmployees += 1;
		this.employeeId = employee_id;
		this.sex = "NA";
		this.pickupServiceTime = 0;
	}
	
	public Employee(Geocode pickup_location, Geocode drop_location, long max_trip_duration, 
			String employee_id, String sex) {
		this.pickupLocation = pickup_location;
		this.dropLocation = drop_location;
		this.maxTripDuration = max_trip_duration;
		numberOfEmployees += 1;
		this.employeeId = employee_id;
		this.sex = sex;
		this.pickupServiceTime = 0;
	}
	
	public Employee(JsonEmployee E, long max_trip_duration, long pickup_service_time) {
		this.pickupLocation = new Geocode(E.pickup);
		this.dropLocation = new Geocode(E.drop);
		this.maxTripDuration = max_trip_duration;
		this.employeeId = E.emp_id;
		this.sex = E.sex;
		this.pickupServiceTime = pickup_service_time;
		numberOfEmployees += 1;
	}
	
	public Geocode getPickup() {
		return pickupLocation;
	}
	
	public Geocode getDrop() {
		return dropLocation;
	}
	
	public boolean isEqual(Employee E){
		return (employeeId.equals(E.employeeId));
	}
}