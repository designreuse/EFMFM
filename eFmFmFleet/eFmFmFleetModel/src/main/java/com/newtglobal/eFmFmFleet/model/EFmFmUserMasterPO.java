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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the eFmFmUserMaster database table.
 * 
 */
@Entity
@Table(name="eFmFmUserMaster")
public class EFmFmUserMasterPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserId", length=10)
	private int userId;

	@Column(name="CreatedBy", length=50)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CreationTime", length=50)
	private Date creationTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DateOfBirth", length=50)
	private Date DateOfBirth;

	@Lob
	@Column(name="Address", length=1000)
	private String address;	

	@Column(name="LoggedIn", length=10)
	private boolean loggedIn;


	@Column(name="DeviceId", length=100)
	private String deviceId;

	@Column(name="DeviceToken", length=250)
	private String deviceToken;
	
	@Column(name="EmployeeDesignation", length=100)
	private String employeeDesignation;

	@Transient
	private String newPassword;
	
	@Transient
	private String birthDate;


	@Column(name="DeviceType", length=50)
	private String deviceType;
	
	@Column(name="LocationStatus", length=50)
	private String locationStatus;

	@Column(name="EmployeeId", length=100)
	private String employeeId;
	
	@Column(name="UserType", length=100)
	private String userType;
	

	@Column(name="Gender", length=10)
	private String gender;	

	@Column(name="DeviceStatus", length=30)
	private String deviceStatus;
	
	@Column(name="StateName", length=50)
	private String stateName;

	@Column(name="CityName", length=50)
	private String cityName;
	
	@Column(name="Pincode", length=10)
	private int pinCode;

	@Column(name="EmailId", length=100)
	private String emailId;

	@Column(name="FirstName", length=100)
	private String firstName;
	
	@Column(name="MiddleName", length=50)
	private String middleName;

	@Column(name="LastName", length=50)
	private String lastName;

	@Column(name="LatitudeLongitude", length=200)
	private String latitudeLongitude;

	@Column(name="DeviceLatitudeLongitude", length=200)
	private String deviceLatitudeLongitude;
	
	@Column(name="MobileNumber", length=30)
	private String mobileNumber;
	
	@Column(name="PanicNumber", length=30)
	private String panicNumber;

	@Column(name="PhysicallyChallenged", length=10)
	private String physicallyChallenged;

	@Column(name="password", length=100)
	private String password;
	
	
	@Column(name="HostMobileNumber", length=100)
	private String hostMobileNumber;
	
	@Column(name="GuestMiddleLoc", length=100)
	private String guestMiddleLoc;
	
	
	
	@Column(name="Status", length=10)
	private String status;
	
	@Column(name="WeekOffDays", length=50)
	private String weekOffDays;
	

	@Column(name="UpdatedBy", length=50)
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UpdatedTime", length=30)
	private Date updatedTime;

	@Column(name="userName", length=200)
	private String userName;
	
	
	@Column(name="EmployeeBusinessUnit", length=100)
	private String employeeBusinessUnit;
	
	
	@Column(name="EmployeeDepartment", length=100)
	private String employeeDepartment;
	
	@Column(name="EmployeeDomain", length=100)
	private String employeeDomain;
	
	@Column(name="Distance", length=30)
	private double distance;
	
	
	//bi-directional many-to-one association to EFmFmAreaMaster
	@ManyToOne
	@JoinColumn(name="RouteAreaId")
	private EFmFmRouteAreaMappingPO eFmFmRouteAreaMapping;


	//bi-directional many-to-one association to EFmFmClientBranchPO
	@ManyToOne
	@JoinColumn(name="BranchId")
	private EFmFmClientBranchPO eFmFmClientBranchPO;
	
	//bi-directional many-to-one association to EFmFmClientMaster
	@ManyToOne
	@JoinColumn(name="ProjectId")
	private EFmFmClientProjectDetailsPO eFmFmClientProjectDetails;

	
	//bi-directional many-to-one association to EFmFmAlertTypeMaster
	@OneToMany(mappedBy="efmFmUserMaster")
	private List<EFmFmAlertTypeMasterPO> efmFmAlertTypeMasters;
	
	//bi-directional many-to-one association to eFmFmAlertTxn
	@OneToMany(mappedBy="efmFmUserMaster")
	private List<EFmFmAlertTxnPO> eFmFmAlertTxn;

	//bi-directional many-to-one association to EFmFmClientUserRole
	@OneToMany(mappedBy="efmFmUserMaster")
	private List<EFmFmClientUserRolePO> efmFmClientUserRoles;
	
	//bi-directional many-to-one association to EFmFmDriverFeedback
	@OneToMany(mappedBy="efmFmUserMaster")
	private List<EFmFmDriverFeedbackPO> efmFmDriverFeedbacks;
	
	//bi-directional many-to-one association to eFmFmEmployeeRequestMaster
	@OneToMany(mappedBy="efmFmUserMaster")
	private List<EFmFmEmployeeRequestMasterPO> eFmFmEmployeeRequestMaster;
	
	//bi-directional many-to-one association to EFmFmEmployeeTravelRequestPO
	@OneToMany(mappedBy="efmFmUserMaster")
	private List<EFmFmEmployeeTravelRequestPO> eFmFmEmployeeTravelRequestPO;


	
	
	public EFmFmUserMasterPO() {
	}

	
	
	public String getEmployeeBusinessUnit() {
		return employeeBusinessUnit;
	}



	public void setEmployeeBusinessUnit(String employeeBusinessUnit) {
		this.employeeBusinessUnit = employeeBusinessUnit;
	}



	public String getEmployeeDepartment() {
		return employeeDepartment;
	}



	public void setEmployeeDepartment(String employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}


	public String getEmployeeDomain() {
		return employeeDomain;
	}

	public void setEmployeeDomain(String employeeDomain) {
		this.employeeDomain = employeeDomain;
	}



	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public List<EFmFmEmployeeRequestMasterPO> geteFmFmEmployeeRequestMaster() {
		return eFmFmEmployeeRequestMaster;
	}

	public void seteFmFmEmployeeRequestMaster(
			List<EFmFmEmployeeRequestMasterPO> eFmFmEmployeeRequestMaster) {
		this.eFmFmEmployeeRequestMaster = eFmFmEmployeeRequestMaster;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public String getWeekOffDays() {
		return weekOffDays;
	}



	public void setWeekOffDays(String weekOffDays) {
		this.weekOffDays = weekOffDays;
	}



	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLatitudeLongitude() {
		return latitudeLongitude;
	}

	public void setLatitudeLongitude(String latitudeLongitude) {
		this.latitudeLongitude = latitudeLongitude;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public double getDistance() {
		return distance;
	}



	public void setDistance(double distance) {
		this.distance = distance;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<EFmFmAlertTypeMasterPO> getEfmFmAlertTypeMasters() {
		return this.efmFmAlertTypeMasters;
	}

	public void setEfmFmAlertTypeMasters(List<EFmFmAlertTypeMasterPO> efmFmAlertTypeMasters) {
		this.efmFmAlertTypeMasters = efmFmAlertTypeMasters;
	}
	
	

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDeviceLatitudeLongitude() {
		return deviceLatitudeLongitude;
	}



	public void setDeviceLatitudeLongitude(String deviceLatitudeLongitude) {
		this.deviceLatitudeLongitude = deviceLatitudeLongitude;
	}



	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getEmployeeDesignation() {
		return employeeDesignation;
	}

	public void setEmployeeDesignation(String employeeDesignation) {
		this.employeeDesignation = employeeDesignation;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getPanicNumber() {
		return panicNumber;
	}

	public void setPanicNumber(String panicNumber) {
		this.panicNumber = panicNumber;
	}

	public String getPhysicallyChallenged() {
		return physicallyChallenged;
	}

	public void setPhysicallyChallenged(String physicallyChallenged) {
		this.physicallyChallenged = physicallyChallenged;
	}

	public EFmFmRouteAreaMappingPO geteFmFmRouteAreaMapping() {
		return eFmFmRouteAreaMapping;
	}

	public void seteFmFmRouteAreaMapping(
			EFmFmRouteAreaMappingPO eFmFmRouteAreaMapping) {
		this.eFmFmRouteAreaMapping = eFmFmRouteAreaMapping;
	}

	public List<EFmFmDriverFeedbackPO> getEfmFmDriverFeedbacks() {
		return efmFmDriverFeedbacks;
	}

	public void setEfmFmDriverFeedbacks(
			List<EFmFmDriverFeedbackPO> efmFmDriverFeedbacks) {
		this.efmFmDriverFeedbacks = efmFmDriverFeedbacks;
	}

	public EFmFmAlertTypeMasterPO addEfmFmAlertTypeMaster(EFmFmAlertTypeMasterPO efmFmAlertTypeMaster) {
		getEfmFmAlertTypeMasters().add(efmFmAlertTypeMaster);
		efmFmAlertTypeMaster.setEfmFmUserMaster(this);

		return efmFmAlertTypeMaster;
	}

	public EFmFmAlertTypeMasterPO removeEfmFmAlertTypeMaster(EFmFmAlertTypeMasterPO efmFmAlertTypeMaster) {
		getEfmFmAlertTypeMasters().remove(efmFmAlertTypeMaster);
		efmFmAlertTypeMaster.setEfmFmUserMaster(null);

		return efmFmAlertTypeMaster;
	}

	public List<EFmFmClientUserRolePO> getEfmFmClientUserRoles() {
		return this.efmFmClientUserRoles;
	}

	public void setEfmFmClientUserRoles(List<EFmFmClientUserRolePO> efmFmClientUserRoles) {
		this.efmFmClientUserRoles = efmFmClientUserRoles;
	}

	
	public List<EFmFmAlertTxnPO> geteFmFmAlertTxn() {
		return eFmFmAlertTxn;
	}

	public void seteFmFmAlertTxn(List<EFmFmAlertTxnPO> eFmFmAlertTxn) {
		this.eFmFmAlertTxn = eFmFmAlertTxn;
	}

	public EFmFmClientUserRolePO addEfmFmClientUserRole(EFmFmClientUserRolePO efmFmClientUserRole) {
		getEfmFmClientUserRoles().add(efmFmClientUserRole);
		efmFmClientUserRole.setEfmFmUserMaster(this);

		return efmFmClientUserRole;
	}

	public EFmFmClientUserRolePO removeEfmFmClientUserRole(EFmFmClientUserRolePO efmFmClientUserRole) {
		getEfmFmClientUserRoles().remove(efmFmClientUserRole);
		efmFmClientUserRole.setEfmFmUserMaster(null);

		return efmFmClientUserRole;
	}



	public EFmFmClientBranchPO geteFmFmClientBranchPO() {
		return eFmFmClientBranchPO;
	}



	public void seteFmFmClientBranchPO(EFmFmClientBranchPO eFmFmClientBranchPO) {
		this.eFmFmClientBranchPO = eFmFmClientBranchPO;
	}



	public List<EFmFmEmployeeTravelRequestPO> geteFmFmEmployeeTravelRequestPO() {
		return eFmFmEmployeeTravelRequestPO;
	}

	public void seteFmFmEmployeeTravelRequestPO(
			List<EFmFmEmployeeTravelRequestPO> eFmFmEmployeeTravelRequestPO) {
		this.eFmFmEmployeeTravelRequestPO = eFmFmEmployeeTravelRequestPO;
	}



	public EFmFmClientProjectDetailsPO geteFmFmClientProjectDetails() {
		return eFmFmClientProjectDetails;
	}



	public void seteFmFmClientProjectDetails(
			EFmFmClientProjectDetailsPO eFmFmClientProjectDetails) {
		this.eFmFmClientProjectDetails = eFmFmClientProjectDetails;
	}



	public String getLocationStatus() {
		return locationStatus;
	}



	public String getHostMobileNumber() {
		return hostMobileNumber;
	}



	public void setHostMobileNumber(String hostMobileNumber) {
		this.hostMobileNumber = hostMobileNumber;
	}



	public String getGuestMiddleLoc() {
		return guestMiddleLoc;
	}


	public void setGuestMiddleLoc(String guestMiddleLoc) {
		this.guestMiddleLoc = guestMiddleLoc;
	}

	public void setLocationStatus(String locationStatus) {
		this.locationStatus = locationStatus;
	}



	public String getUserType() {
		return userType;
	}



	public void setUserType(String userType) {
		this.userType = userType;
	}

	
}