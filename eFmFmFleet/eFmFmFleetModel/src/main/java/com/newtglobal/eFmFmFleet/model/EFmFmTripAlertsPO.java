package com.newtglobal.eFmFmFleet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the eFmFmTripAlerts database table.
 * 
 */

@Entity
@Table(name="eFmFmTripAlerts")
public class EFmFmTripAlertsPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TripAlertId", length=15)
	private int tripAlertsId;

	@Column(name="Created_By", length=50)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CreationTime", length=30)
	private Date creationTime;

	
	@Column(name="Status", length=10)
	private String status;
	
	@Column(name="AlertOpenStatus", length=50)
	private String alertOpenStatus;
	
	@Column(name="AlertClosingDescription", length=255)
	private String alertClosingDescription;

	@Column(name="UpdatedBy", length=50)
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UpdatedTime", length=30)
	private Date updatedTime;

	@Column(name="UserType", length=50)
	private String userType;
	
	@Column(name="Speed", length=50)
	private String currentSpeed;

	@Transient
	private int branchId;
	
	@Transient
	private int assignRouteId;
	
	//bi-directional many-to-one association to EFmFm_Alert_Type_Master
    @ManyToOne
	@JoinColumn(name="AlertId")
	private EFmFmAlertTypeMasterPO efmFmAlertTypeMaster;
	
	//bi-directional many-to-one association to EFmFm_Alert_Type_Master
	@ManyToOne
	@JoinColumn(name="AssignRouteId")
	private EFmFmAssignRoutePO efmFmAssignRoute;

	public EFmFmTripAlertsPO() {
	}

	public int getTripAlertsId() {
		return tripAlertsId;
	}

	public void setTripAlertsId(int tripAlertsId) {
		this.tripAlertsId = tripAlertsId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	

	public EFmFmAssignRoutePO getEfmFmAssignRoute() {
		return efmFmAssignRoute;
	}

	public void setEfmFmAssignRoute(EFmFmAssignRoutePO efmFmAssignRoute) {
		this.efmFmAssignRoute = efmFmAssignRoute;
	}

	public EFmFmAlertTypeMasterPO getEfmFmAlertTypeMaster() {
		return efmFmAlertTypeMaster;
	}

	public void setEfmFmAlertTypeMaster(EFmFmAlertTypeMasterPO efmFmAlertTypeMaster) {
		this.efmFmAlertTypeMaster = efmFmAlertTypeMaster;
	}

	public String getAlertOpenStatus() {
		return alertOpenStatus;
	}

	public void setAlertOpenStatus(String alertOpenStatus) {
		this.alertOpenStatus = alertOpenStatus;
	}

	public String getAlertClosingDescription() {
		return alertClosingDescription;
	}

	public void setAlertClosingDescription(String alertClosingDescription) {
		this.alertClosingDescription = alertClosingDescription;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getAssignRouteId() {
		return assignRouteId;
	}

	public void setAssignRouteId(int assignRouteId) {
		this.assignRouteId = assignRouteId;
	}

	public String getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(String currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	
}