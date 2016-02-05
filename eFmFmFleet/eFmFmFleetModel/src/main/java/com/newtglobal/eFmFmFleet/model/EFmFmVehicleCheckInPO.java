package com.newtglobal.eFmFmFleet.model;

import java.io.Serializable;
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


/**
 * The persistent class for the eFmFmVehicleCheckIn database table.
 * 
 */
@Entity
@Table(name="eFmFmVehicleCheckIn")
public class EFmFmVehicleCheckInPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CheckInId", length=10)
	private int checkInId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CheckInTime", length=30)
	private Date checkInTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CheckOutTime", length=30)
	private Date checkOutTime;
	
	@Column(name="Status", length=10)
	private String status;
	
    @Transient
	private int branchId;
    
    @Transient
	private int driverId;
    
    @Transient
   	private int vendorId;
    
    @Transient
	private String deviceImei;
	
	
	//bi-directional many-to-one association to EFmFmAssignRoute
	@OneToMany(mappedBy="efmFmVehicleCheckIn")
	private List<EFmFmAssignRoutePO> efmFmAssignRoutes;


	//bi-directional many-to-one association to EFmFmDriverMaster
	@ManyToOne
	@JoinColumn(name="DriverId")
	private EFmFmDriverMasterPO efmFmDriverMaster;
	
	//bi-directional many-to-one association to EFmFmDeviceMasterPO
	@ManyToOne
	@JoinColumn(name="DeviceId")
	private EFmFmDeviceMasterPO eFmFmDeviceMaster;


	//bi-directional many-to-one association to EFmFmVehicleMaster
	@ManyToOne
	@JoinColumn(name="VehicleId")
	private EFmFmVehicleMasterPO efmFmVehicleMaster;

	public EFmFmVehicleCheckInPO() {
	}

	public int getCheckInId() {
		return this.checkInId;
	}

	public void setCheckInId(int checkInId) {
		this.checkInId = checkInId;
	}

	public Date getCheckInTime() {
		return this.checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOutTime() {
		return this.checkOutTime;
	}

	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public List<EFmFmAssignRoutePO> getEfmFmAssignRoutes() {
		return this.efmFmAssignRoutes;
	}

	public void setEfmFmAssignRoutes(List<EFmFmAssignRoutePO> efmFmAssignRoutes) {
		this.efmFmAssignRoutes = efmFmAssignRoutes;
	}

	
	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public EFmFmAssignRoutePO addEfmFmAssignRoute(EFmFmAssignRoutePO efmFmAssignRoute) {
		getEfmFmAssignRoutes().add(efmFmAssignRoute);
		efmFmAssignRoute.setEfmFmVehicleCheckIn(this);

		return efmFmAssignRoute;
	}

	public EFmFmAssignRoutePO removeEfmFmAssignRoute(EFmFmAssignRoutePO efmFmAssignRoute) {
		getEfmFmAssignRoutes().remove(efmFmAssignRoute);
		efmFmAssignRoute.setEfmFmVehicleCheckIn(null);

		return efmFmAssignRoute;
	}


	public EFmFmDeviceMasterPO geteFmFmDeviceMaster() {
		return eFmFmDeviceMaster;
	}

	public void seteFmFmDeviceMaster(EFmFmDeviceMasterPO eFmFmDeviceMaster) {
		this.eFmFmDeviceMaster = eFmFmDeviceMaster;
	}

	public EFmFmDriverMasterPO getEfmFmDriverMaster() {
		return this.efmFmDriverMaster;
	}

	public void setEfmFmDriverMaster(EFmFmDriverMasterPO efmFmDriverMaster) {
		this.efmFmDriverMaster = efmFmDriverMaster;
	}

	public EFmFmVehicleMasterPO getEfmFmVehicleMaster() {
		return this.efmFmVehicleMaster;
	}

	public void setEfmFmVehicleMaster(EFmFmVehicleMasterPO efmFmVehicleMaster) {
		this.efmFmVehicleMaster = efmFmVehicleMaster;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	
}