package com.newtglobal.eFmFmFleet.model;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the eFmFm_triptime database table.
 * 
 */
@Entity
@Table(name="eFmFmShiftTiming")
public class EFmFmTripTimingMasterPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ShiftId", length=15)
	private int shiftId;

	@Column(name="Shift_Time", length=30)
	private Time shiftTime;
	
	@Column(name="IsActive", length=10)
	private String isActive;
	
	@Transient
	private String time;
	
	@Column(name="TripType", length=50)
	private String tripType;
	
	//bi-directional many-to-one association to EFmFmClientBranchPO
	@ManyToOne
	@JoinColumn(name="BranchId")
	private EFmFmClientBranchPO eFmFmClientBranchPO;

	
	public int getShiftId() {
		return shiftId;
	}

	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public Time getShiftTime() {
		return shiftTime;
	}

	public void setShiftTime(Time shiftTime) {
		this.shiftTime = shiftTime;
	}

	public EFmFmClientBranchPO geteFmFmClientBranchPO() {
		return eFmFmClientBranchPO;
	}

	public void seteFmFmClientBranchPO(EFmFmClientBranchPO eFmFmClientBranchPO) {
		this.eFmFmClientBranchPO = eFmFmClientBranchPO;
	}


}
