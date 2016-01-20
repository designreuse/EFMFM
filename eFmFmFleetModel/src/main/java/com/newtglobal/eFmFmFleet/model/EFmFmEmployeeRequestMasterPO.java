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


/**
 * The persistent class for the eFmFmEmployeeRequestMaster database table.
 * 
 */
@Entity
@Table(name="eFmFmEmployeeRequestMaster")
public class EFmFmEmployeeRequestMasterPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TripId", length=50)
	private int tripId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RequestDate", length=30)
	private Date requestDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TripRequestEndDate", length=30)
	private Date tripRequestEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TripRequestStartDate", length=30)
	private Date tripRequestStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ActualTripEndDate", length=30)
	private Date actulaTripEndDate;

	@Column(name="DropSequence", length=50)
	private int dropSequence;

	@Column(name="ShiftTime", length=30)
	private Time shiftTime;
	
	@Column(name="PICKUP_TIME")
	private Time pickUpTime;
	
    @Column(name="waitingTime")
	private long  waitingTime;
	
	@Transient
	private String time;
	
	@Transient
	private String firstName;
	
	@Transient
	private String emailId;

	@Transient
	private String mobileNumber;
	
	@Transient
	private String latitudeLongitude;


	@Transient
	private String address;
	
	@Transient
	private String gender;
	
		
	
	@Transient
	private int userId;
	
	@Transient
	private String employeeId;

	
	@Transient
	private String startDate;
	
	@Transient
	private String role;

	@Transient
	private String endDate;
	@Transient
	private String hostMobileNumber;
	@Transient
	private String guestMiddleLoc;

	
	@Column(name="TripType", length=10)
	private String tripType;
	
	@Column(name="Status", length=10)
	private String status;
	
	@Column(name="ReadFlg", length=10)
	private String readFlg;
	
	@Column(name="RequestFrom", length=10)
	private String requestFrom;
	
	@Column(name="RequestType", length=10)
	private String requestType;

		
	//bidirectional manytoone association to eFmFmEmployeeTravelRequest
	@OneToMany(mappedBy="eFmFmEmployeeRequestMaster")
	private List<EFmFmEmployeeTravelRequestPO> eFmFmEmployeeTravelRequest;
		
	
	//bi-directional many-to-one association to EFmFmUserMaster
	@ManyToOne
	@JoinColumn(name="UserId")
	private EFmFmUserMasterPO efmFmUserMaster;

	
	
	public EFmFmEmployeeRequestMasterPO() {
	}

	

	public int getTripId() {
		return tripId;
	}



	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
 
	public String getReadFlg() {
		return readFlg;
	}

	
	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public Time getPickUpTime() {
		return pickUpTime;
	}



	public void setPickUpTime(Time pickUpTime) {
		this.pickUpTime = pickUpTime;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public void setReadFlg(String readFlg) {
		this.readFlg = readFlg;
	}


	
	public String getRequestType() {
		return requestType;
	}



	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}



	public String getRequestFrom() {
		return requestFrom;
	}


	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}
	
	public Time getShiftTime() {
		return shiftTime;
	}



	public void setShiftTime(Time shiftTime) {
		this.shiftTime = shiftTime;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public Date getActulaTripEndDate() {
		return actulaTripEndDate;
	}

	public void setActulaTripEndDate(Date actulaTripEndDate) {
		this.actulaTripEndDate = actulaTripEndDate;
	}

	
	public long getWaitingTime() {
		return waitingTime;
	}



	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}



	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
	
	public String getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}



	public Date getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getTripRequestEndDate() {
		return this.tripRequestEndDate;
	}

	public void setTripRequestEndDate(Date tripRequestEndDate) {
		this.tripRequestEndDate = tripRequestEndDate;
	}

	public Date getTripRequestStartDate() {
		return this.tripRequestStartDate;
	}

	public void setTripRequestStartDate(Date tripRequestStartDate) {
		this.tripRequestStartDate = tripRequestStartDate;
	}

	
	
	public String getTripType() {
		return this.tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	public List<EFmFmEmployeeTravelRequestPO> geteFmFmEmployeeTravelRequest() {
		return eFmFmEmployeeTravelRequest;
	}



	public void seteFmFmEmployeeTravelRequest(
			List<EFmFmEmployeeTravelRequestPO> eFmFmEmployeeTravelRequest) {
		this.eFmFmEmployeeTravelRequest = eFmFmEmployeeTravelRequest;
	}



	public EFmFmUserMasterPO getEfmFmUserMaster() {
		return efmFmUserMaster;
	}



	public void setEfmFmUserMaster(EFmFmUserMasterPO efmFmUserMaster) {
		this.efmFmUserMaster = efmFmUserMaster;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getEmailId() {
		return emailId;
	}



	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}



	public String getMobileNumber() {
		return mobileNumber;
	}



	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}



	public String getLatitudeLongitude() {
		return latitudeLongitude;
	}



	public void setLatitudeLongitude(String latitudeLongitude) {
		this.latitudeLongitude = latitudeLongitude;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
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



	public int getDropSequence() {
		return dropSequence;
	}



	public void setDropSequence(int dropSequence) {
		this.dropSequence = dropSequence;
	}



	

}