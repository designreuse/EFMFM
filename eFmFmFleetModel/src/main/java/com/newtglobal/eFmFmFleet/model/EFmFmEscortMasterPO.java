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
 * The persistent class for the eFmFmEscortMaster database table.
 * 
 */
@Entity
@Table(name="eFmFmEscortMaster")
public class EFmFmEscortMasterPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="EscortId", length=10)
	private int escortId;

	@Column(name="Address", length=250)
	private String address;

	@Column(name="AddressProofDocsPath", length=150)
	private String addressProofDocsPath;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DateOfBirth", length=30)
	private Date dateOfBirth;

	@Column(name="EmailId", length=50)
	private String emailId;

	@Column(name="FatherName", length=50)
	private String fatherName;

	@Column(name="Feedback", length=250)
	private String feedback;

	@Column(name="Gender", length=10)
	private String gender;

	@Column(name="IsActive", length=10)
	private String isActive;

	@Column(name="MobileNumber", length=20)
	private String mobileNumber;

	@Column(name="PhysicallyChallenged", length=10)
	private String physicallyChallenged;

	@Column(name="Pincode", length=15)
	private int pincode;
	
	@Column(name="BackgroundCheckDoc", length=15)
	private int backgroundCheckDoc;


	@Column(name="PoliceVerification", length=250)
	private String policeVerification;

	@Column(name="Remarks", length=250)
	private String remarks;
	
	
	@Column(name="FirstName", length=50)
	private String firstName;
	
	@Column(name="MiddleName", length=50)
	private String middleName;
	
	@Column(name="LastName", length=50)
	private String lastName;
	
	@Column(name="CityName", length=50)
	private String cityName;	
	
	@Column(name="StateName", length=50)
	private String stateName;
	
	@Column(name="Designation", length=50)
	private String designation;
	
    @Transient
	private String vendorName;
	
	
	
	@Column(name="EscortEmployeeId", length=10)
	private String escortEmployeeId;

	//bi-directional many-to-one association to EFmFmVendorMaster
	@ManyToOne
	@JoinColumn(name="VendorId")
	private EFmFmVendorMasterPO efmFmVendorMaster;

	
	
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEscortEmployeeId() {
		return escortEmployeeId;
	}

	public void setEscortEmployeeId(String escortEmployeeId) {
		this.escortEmployeeId = escortEmployeeId;
	}

	//bi-directional many-to-one association to eFmFmEscortMaster
	@OneToMany(mappedBy="eFmFmEscortMaster")
	private List<EFmFmEscortCheckInPO> eFmFmEscortCheckIn;
		
		
	public List<EFmFmEscortCheckInPO> geteFmFmEscortCheckIn() {
		return eFmFmEscortCheckIn;
	}

	public void seteFmFmEscortCheckIn(List<EFmFmEscortCheckInPO> eFmFmEscortCheckIn) {
		this.eFmFmEscortCheckIn = eFmFmEscortCheckIn;
	}

	public EFmFmEscortMasterPO() {
	}

	
	public int getBackgroundCheckDoc() {
		return backgroundCheckDoc;
	}

	public void setBackgroundCheckDoc(int backgroundCheckDoc) {
		this.backgroundCheckDoc = backgroundCheckDoc;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}
	
	

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getEscortId() {
		return this.escortId;
	}

	public void setEscortId(int escortId) {
		this.escortId = escortId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressProofDocsPath() {
		return this.addressProofDocsPath;
	}

	public void setAddressProofDocsPath(String addressProofDocsPath) {
		this.addressProofDocsPath = addressProofDocsPath;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	

	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFeedback() {
		return this.feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhysicallyChallenged() {
		return this.physicallyChallenged;
	}

	public void setPhysicallyChallenged(String physicallyChallenged) {
		this.physicallyChallenged = physicallyChallenged;
	}

	public int getPincode() {
		return this.pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getPoliceVerification() {
		return this.policeVerification;
	}

	public void setPoliceVerification(String policeVerification) {
		this.policeVerification = policeVerification;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public EFmFmVendorMasterPO getEfmFmVendorMaster() {
		return efmFmVendorMaster;
	}

	public void setEfmFmVendorMaster(EFmFmVendorMasterPO efmFmVendorMaster) {
		this.efmFmVendorMaster = efmFmVendorMaster;
	}

	
	

	

}