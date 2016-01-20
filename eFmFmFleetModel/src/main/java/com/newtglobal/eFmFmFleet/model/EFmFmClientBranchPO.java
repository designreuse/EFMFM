package com.newtglobal.eFmFmFleet.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * The persistent class for the eFmFmClientBranchPO database table.
 * 
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="eFmFmClientBranchPO")
public class EFmFmClientBranchPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BranchId", length=10)
	private int branchId;

	@Column(name="StateName", length=50)
	private String stateName;

	@Column(name="CityName", length=50)
	private String cityName;
	
	@Column(name="Pincode", length=10)
	private int pinCode;

	@Column(name="Address", length=10)
	private String address;

	
	@Column(name="BranchName", length=50)
	private String branchName;

	@Column(name="CountryCode", length=10)
	private String countryCode;

	@Column(name="CreatedBy", length=50)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CreationTime", length=30)
	private Date creationTime;

	@Column(name="EmailId", length=30)
	private String emailId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EndDate", length=30)
	private Date endDate;

	@Column(name="LatitudeLongitude", length=100)
	private String latitudeLongitude;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="StartDate", length=30)
	private Date startDate;

	
	@Column(name="Status", length=10)
	private String status;

	@Column(name="ManagerApprovalRequired", length=10)
	private String mangerApprovalRequired;
	
	@Column(name="EscortRequired", length=50)
	private String escortRequired;

	
	@Column(name="UpdatedBy", length=50)
	private String updatedBy;
	
	@Column(name="BranchCode", length=10)
	private String branchCode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UpdatedTime", length=30)
	private Date updatedTime;

	//bi-directional many-to-one association to EFmFmClientMaster
	@ManyToOne
	@JoinColumn(name="ClientId")
	private EFmFmClientMasterPO eFmFmClientMaster;

	//bidirectional manytoone association to EFmFmAlertTypeMaster
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmAlertTypeMasterPO> efmFmAlertTypeMasters;

	//bidirectional manytoone association to EFmFmClientRouteMapping
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmClientRouteMappingPO> efmFmClientRouteMappings;

	//bidirectional manytoone association to EFmFmClientUserRole
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmClientUserRolePO> efmFmClientUserRoles;

	
	//bidirectional manytoone association to eFmFmShiftTiming
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmTripTimingMasterPO> eFmFmShiftTiming;


	//bidirectional manytoone association to EFmFmUserMaster
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmUserMasterPO> efmFmUserMasters;

	//bidirectional manytoone association to EFmFmVendorMaster
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmVendorMasterPO> efmFmVendorMasters;
	
	//bidirectional manytoone association to EFmFmVendorMaster
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmAssignRoutePO> eFmFmAssignRoute;
	
	//bidirectional manytoone association to eFmFmClientProjectDetails
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmClientProjectDetailsPO> eFmFmClientProjectDetails;


	//bidirectional manytoone association to EFmFmVendorMaster
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmActualRoutTravelledPO> eFmFmActualRouteTravelled;
	
	//bidirectional manytoone association to eFmFmVendorContractMaster
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmVendorContractTypeMasterPO> eFmFmVendorContractTypeMaster;
	
	//bidirectional manytoone association to eFmFmDeviceMaster
	@OneToMany(mappedBy="eFmFmClientBranchPO")
	private List<EFmFmDeviceMasterPO> eFmFmDeviceMaster;
	
	public EFmFmClientBranchPO() {
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLatitudeLongitude() {
		return latitudeLongitude;
	}

	public void setLatitudeLongitude(String latitudeLongitude) {
		this.latitudeLongitude = latitudeLongitude;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getMangerApprovalRequired() {
		return mangerApprovalRequired;
	}

	public void setMangerApprovalRequired(String mangerApprovalRequired) {
		this.mangerApprovalRequired = mangerApprovalRequired;
	}

	public String getEscortRequired() {
		return escortRequired;
	}

	public void setEscortRequired(String escortRequired) {
		this.escortRequired = escortRequired;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public List<EFmFmAlertTypeMasterPO> getEfmFmAlertTypeMasters() {
		return efmFmAlertTypeMasters;
	}

	public void setEfmFmAlertTypeMasters(
			List<EFmFmAlertTypeMasterPO> efmFmAlertTypeMasters) {
		this.efmFmAlertTypeMasters = efmFmAlertTypeMasters;
	}

	public List<EFmFmClientRouteMappingPO> getEfmFmClientRouteMappings() {
		return efmFmClientRouteMappings;
	}

	public void setEfmFmClientRouteMappings(
			List<EFmFmClientRouteMappingPO> efmFmClientRouteMappings) {
		this.efmFmClientRouteMappings = efmFmClientRouteMappings;
	}

	public List<EFmFmClientUserRolePO> getEfmFmClientUserRoles() {
		return efmFmClientUserRoles;
	}

	public void setEfmFmClientUserRoles(
			List<EFmFmClientUserRolePO> efmFmClientUserRoles) {
		this.efmFmClientUserRoles = efmFmClientUserRoles;
	}

	public List<EFmFmTripTimingMasterPO> geteFmFmShiftTiming() {
		return eFmFmShiftTiming;
	}

	public void seteFmFmShiftTiming(List<EFmFmTripTimingMasterPO> eFmFmShiftTiming) {
		this.eFmFmShiftTiming = eFmFmShiftTiming;
	}

	public List<EFmFmUserMasterPO> getEfmFmUserMasters() {
		return efmFmUserMasters;
	}

	public void setEfmFmUserMasters(List<EFmFmUserMasterPO> efmFmUserMasters) {
		this.efmFmUserMasters = efmFmUserMasters;
	}

	public List<EFmFmVendorMasterPO> getEfmFmVendorMasters() {
		return efmFmVendorMasters;
	}

	public void setEfmFmVendorMasters(List<EFmFmVendorMasterPO> efmFmVendorMasters) {
		this.efmFmVendorMasters = efmFmVendorMasters;
	}

	public List<EFmFmAssignRoutePO> geteFmFmAssignRoute() {
		return eFmFmAssignRoute;
	}

	public void seteFmFmAssignRoute(List<EFmFmAssignRoutePO> eFmFmAssignRoute) {
		this.eFmFmAssignRoute = eFmFmAssignRoute;
	}

	public List<EFmFmClientProjectDetailsPO> geteFmFmClientProjectDetails() {
		return eFmFmClientProjectDetails;
	}

	public void seteFmFmClientProjectDetails(
			List<EFmFmClientProjectDetailsPO> eFmFmClientProjectDetails) {
		this.eFmFmClientProjectDetails = eFmFmClientProjectDetails;
	}

	public List<EFmFmActualRoutTravelledPO> geteFmFmActualRouteTravelled() {
		return eFmFmActualRouteTravelled;
	}

	public void seteFmFmActualRouteTravelled(
			List<EFmFmActualRoutTravelledPO> eFmFmActualRouteTravelled) {
		this.eFmFmActualRouteTravelled = eFmFmActualRouteTravelled;
	}

	public List<EFmFmVendorContractTypeMasterPO> geteFmFmVendorContractTypeMaster() {
		return eFmFmVendorContractTypeMaster;
	}

	public void seteFmFmVendorContractTypeMaster(
			List<EFmFmVendorContractTypeMasterPO> eFmFmVendorContractTypeMaster) {
		this.eFmFmVendorContractTypeMaster = eFmFmVendorContractTypeMaster;
	}

	public List<EFmFmDeviceMasterPO> geteFmFmDeviceMaster() {
		return eFmFmDeviceMaster;
	}

	public void seteFmFmDeviceMaster(List<EFmFmDeviceMasterPO> eFmFmDeviceMaster) {
		this.eFmFmDeviceMaster = eFmFmDeviceMaster;
	}

	public EFmFmClientMasterPO geteFmFmClientMaster() {
		return eFmFmClientMaster;
	}

	public void seteFmFmClientMaster(EFmFmClientMasterPO eFmFmClientMaster) {
		this.eFmFmClientMaster = eFmFmClientMaster;
	}

}