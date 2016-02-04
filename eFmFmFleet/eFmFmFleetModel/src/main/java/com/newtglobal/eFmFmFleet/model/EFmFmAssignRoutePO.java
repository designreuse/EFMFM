package com.newtglobal.eFmFmFleet.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * The persistent class for the eFmFmAssignRoute database table.
 * 
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="eFmFmAssignRoute")
public class EFmFmAssignRoutePO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="AssignRouteId", length=15)
	private int assignRouteId;

	@Column(name="EscortRequredFlag", length=10)
	private String escortRequredFlag;

	@Column(name="OdometerEndKm", length=15)
	private String odometerEndKm;

	@Column(name="OdometerStartKm", length=15)
	private String odometerStartKm;

	@Column(name="PlannedDistance", length=15)
	private double plannedDistance;

	@Column(name="RouteGenerationType", length=10)
	private String routeGenerationType;

	@Column(name="TravelledDistance", length=15)
	private double travelledDistance;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TripCompleteTime", length=30)
	private Date tripCompleteTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TripAssignDate", length=30)
	private Date tripAssignDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="AllocationMsgDeliveryDate", length=30)
	private Date allocationMsgDeliveryDate;
	
	
	@Column(name="VehicleStatus", length=15)
	private String vehicleStatus;
	
	@Column(name="CabAllocationMessage", length=15)
	private String allocationMsg;


	@Transient
	private String toDate;
	
	@Transient
	private String searchType;
	
	@Transient
	private int vehicleId;
	
	@Transient
	private int vendorId;
	
	@Transient
	private int driverId;
	
	@Transient
	private String employeeId;
	
	@Transient
	private int requestId;
	
	@Transient
	private int deviceId;
	
	@Transient
	private int escortCheckInId;
	
	@Transient
	private int newCheckInId;
	
	@Transient
	private int selectedAssignRouteId;
	

	@Transient
	private String time;
	
	@Transient
	private String fromDate;

	@Column(name="TripStatus", length=10)
	private String tripStatus;
	
	@Column(name="BucketStatus", length=10)
	private String bucketStatus;

	@Column(name="TripType", length=10)
	private String tripType;
	
	@Column(name="ShiftTime", length=30)
	private Time shiftTime;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TripStartTime", length=30)
	private Date tripStartTime;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TripUpdateTime", length=30)
	private Date tripUpdateTime;

	public Date getTripUpdateTime() {
		return tripUpdateTime;
	}



	public void setTripUpdateTime(Date tripUpdateTime) {
		this.tripUpdateTime = tripUpdateTime;
	}



	//bi-directional many-to-one association to eFmFmRouteAreaMapping
	@ManyToOne
	@JoinColumn(name="RouteAreaId")
	private EFmFmRouteAreaMappingPO eFmFmRouteAreaMapping;
	
	//bi-directional many-to-one association to EFmFmEmployeeTripDetail
	@OneToMany(mappedBy="efmFmAssignRoute")
	private List<EFmFmEmployeeTripDetailPO> efmFmEmployeeTripDetails;
	
	//bi-directional many-to-one association to EFmFmEmployeeTripDetail
	@OneToMany(mappedBy="efmFmAssignRoute")
	private List<EFmFmActualRoutTravelledPO> eFmFmActualRouteTravelled;
	
	//bi-directional many-to-one association to EFmFmEmployeeTripDetail
	@OneToMany(mappedBy="efmFmAssignRoute")
	private List<EFmFmTripAlertsPO> eFmFmTripAlerts;

		
	
	//bi-directional many-to-one association to EFmFmVehicleCheckIn
	@ManyToOne
	@JoinColumn(name="CheckInId")
	private EFmFmVehicleCheckInPO efmFmVehicleCheckIn;
	
	
	//bi-directional many-to-one association to EFmFmVehicleCheckIn
	@ManyToOne
	@JoinColumn(name="EscortCheckInId")
	private EFmFmEscortCheckInPO eFmFmEscortCheckIn;

	
	//bi-directional many-to-one association to EFmFmClientMaster
	@ManyToOne
	@JoinColumn(name="BranchId")
	private EFmFmClientBranchPO eFmFmClientBranchPO;

	public EFmFmAssignRoutePO() {
	}

	

	public int getAssignRouteId() {
		return assignRouteId;
	}



	public void setAssignRouteId(int assignRouteId) {
		this.assignRouteId = assignRouteId;
	}



	public String getVehicleStatus() {
		return vehicleStatus;
	}



	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public String getToDate() {
		return toDate;
	}


	public void setToDate(String toDate) {
		this.toDate = toDate;
	}


	

	public int getRequestId() {
		return requestId;
	}



	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}



	public String getFromDate() {
		return fromDate;
	}



	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}


	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getAllocationMsg() {
		return allocationMsg;
	}



	public void setAllocationMsg(String allocationMsg) {
		this.allocationMsg = allocationMsg;
	}



	public String getEscortRequredFlag() {
		return this.escortRequredFlag;
	}

	public void setEscortRequredFlag(String escortRequredFlag) {
		this.escortRequredFlag = escortRequredFlag;
	}

	public String getOdometerEndKm() {
		return this.odometerEndKm;
	}

	public void setOdometerEndKm(String odometerEndKm) {
		this.odometerEndKm = odometerEndKm;
	}

	public String getOdometerStartKm() {
		return this.odometerStartKm;
	}

	public void setOdometerStartKm(String odometerStartKm) {
		this.odometerStartKm = odometerStartKm;
	}

	public double getPlannedDistance() {
		return this.plannedDistance;
	}

	public void setPlannedDistance(double plannedDistance) {
		this.plannedDistance = plannedDistance;
	}

	public String getRouteGenerationType() {
		return this.routeGenerationType;
	}

	public void setRouteGenerationType(String routeGenerationType) {
		this.routeGenerationType = routeGenerationType;
	}

	public double getTravelledDistance() {
		return this.travelledDistance;
	}

	public void setTravelledDistance(double travelledDistance) {
		this.travelledDistance = travelledDistance;
	}

	public Date getTripCompleteTime() {
		return this.tripCompleteTime;
	}

	public void setTripCompleteTime(Date tripCompleteTime) {
		this.tripCompleteTime = tripCompleteTime;
	}
	

	public Date getTripAssignDate() {
		return tripAssignDate;
	}



	public int getNewCheckInId() {
		return newCheckInId;
	}



	public void setNewCheckInId(int newCheckInId) {
		this.newCheckInId = newCheckInId;
	}



	public Date getTripStartTime() {
		return tripStartTime;
	}



	public int getVehicleId() {
		return vehicleId;
	}



	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}



	public int getDriverId() {
		return driverId;
	}



	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}



	public String getSearchType() {
		return searchType;
	}



	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}



	public String getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}



	public int getDeviceId() {
		return deviceId;
	}



	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}


	public int getEscortCheckInId() {
		return escortCheckInId;
	}



	public void setEscortCheckInId(int escortCheckInId) {
		this.escortCheckInId = escortCheckInId;
	}


	public int getSelectedAssignRouteId() {
		return selectedAssignRouteId;
	}



	public void setSelectedAssignRouteId(int selectedAssignRouteId) {
		this.selectedAssignRouteId = selectedAssignRouteId;
	}



	public void setTripStartTime(Date tripStartTime) {
		this.tripStartTime = tripStartTime;
	}



	public void setTripAssignDate(Date tripAssignDate) {
		this.tripAssignDate = tripAssignDate;
	}


	public String getTripStatus() {
		return this.tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public String getTripType() {
		return this.tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}


	public EFmFmRouteAreaMappingPO geteFmFmRouteAreaMapping() {
		return eFmFmRouteAreaMapping;
	}

	public void seteFmFmRouteAreaMapping(
			EFmFmRouteAreaMappingPO eFmFmRouteAreaMapping) {
		this.eFmFmRouteAreaMapping = eFmFmRouteAreaMapping;
	}

	public EFmFmVehicleCheckInPO getEfmFmVehicleCheckIn() {
		return this.efmFmVehicleCheckIn;
	}

	public void setEfmFmVehicleCheckIn(EFmFmVehicleCheckInPO efmFmVehicleCheckIn) {
		this.efmFmVehicleCheckIn = efmFmVehicleCheckIn;
	}

	public List<EFmFmEmployeeTripDetailPO> getEfmFmEmployeeTripDetails() {
		return efmFmEmployeeTripDetails;
	}

	public void setEfmFmEmployeeTripDetails(
			List<EFmFmEmployeeTripDetailPO> efmFmEmployeeTripDetails) {
		this.efmFmEmployeeTripDetails = efmFmEmployeeTripDetails;
	}

	

	public EFmFmClientBranchPO geteFmFmClientBranchPO() {
		return eFmFmClientBranchPO;
	}



	public void seteFmFmClientBranchPO(EFmFmClientBranchPO eFmFmClientBranchPO) {
		this.eFmFmClientBranchPO = eFmFmClientBranchPO;
	}



	public List<EFmFmActualRoutTravelledPO> geteFmFmActualRouteTravelled() {
		return eFmFmActualRouteTravelled;
	}

	public void seteFmFmActualRouteTravelled(
			List<EFmFmActualRoutTravelledPO> eFmFmActualRouteTravelled) {
		this.eFmFmActualRouteTravelled = eFmFmActualRouteTravelled;
	}

	public List<EFmFmTripAlertsPO> geteFmFmTripAlerts() {
		return eFmFmTripAlerts;
	}

	public void seteFmFmTripAlerts(List<EFmFmTripAlertsPO> eFmFmTripAlerts) {
		this.eFmFmTripAlerts = eFmFmTripAlerts;
	}

	public Time getShiftTime() {
		return shiftTime;
	}



	public void setShiftTime(Time shiftTime) {
		this.shiftTime = shiftTime;
	}



	public EFmFmEscortCheckInPO geteFmFmEscortCheckIn() {
		return eFmFmEscortCheckIn;
	}

	public String getBucketStatus() {
		return bucketStatus;
	}



	public void setBucketStatus(String bucketStatus) {
		this.bucketStatus = bucketStatus;
	}



	public void seteFmFmEscortCheckIn(EFmFmEscortCheckInPO eFmFmEscortCheckIn) {
		this.eFmFmEscortCheckIn = eFmFmEscortCheckIn;
	}



	public Date getAllocationMsgDeliveryDate() {
		return allocationMsgDeliveryDate;
	}



	public void setAllocationMsgDeliveryDate(Date allocationMsgDeliveryDate) {
		this.allocationMsgDeliveryDate = allocationMsgDeliveryDate;
	}



	public int getVendorId() {
		return vendorId;
	}



	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	
	
}