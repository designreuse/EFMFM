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

/**
 * The persistent class for the eFmFmTripBasedContractDetail database table.
 * 
 */
@Entity
@Table(name="eFmFmTripBasedContractDetail")
public class EFmFmTripBasedContractDetailPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TripBasedContractId", length=15)
	private int tripBasedContractId;
	
	@Column(name="FixedDistance", length=10)
	private double fixedDistance;	
	
	
	@Column(name="ExtraDistanceChargeRate", length=50)
	private double extraDistanceChargeRate;
		
	
	@Column(name="FixedDistanceChargeRate", length=50)
	private double fixedDistanceChargeRate;
	
		
	@Column(name="Created_By", length=50)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CreationTime", length=30)
	private Date creationTime;


	@Column(name="UpdatedBy", length=50)
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UpdatedTime", length=30)
	private Date updatedTime;
	
	
	//bi-directional many-to-one association to EFmFmClientBranchPO
	@ManyToOne
	@JoinColumn(name="BranchId")
	private EFmFmClientBranchPO eFmFmClientBranchPO;


	public int getTripBasedContractId() {
		return tripBasedContractId;
	}


	public void setTripBasedContractId(int tripBasedContractId) {
		this.tripBasedContractId = tripBasedContractId;
	}


	public double getFixedDistance() {
		return fixedDistance;
	}


	public void setFixedDistance(double fixedDistance) {
		this.fixedDistance = fixedDistance;
	}


	public double getExtraDistanceChargeRate() {
		return extraDistanceChargeRate;
	}


	public void setExtraDistanceChargeRate(double extraDistanceChargeRate) {
		this.extraDistanceChargeRate = extraDistanceChargeRate;
	}


	public double getFixedDistanceChargeRate() {
		return fixedDistanceChargeRate;
	}


	public void setFixedDistanceChargeRate(double fixedDistanceChargeRate) {
		this.fixedDistanceChargeRate = fixedDistanceChargeRate;
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


	public EFmFmClientBranchPO geteFmFmClientBranchPO() {
		return eFmFmClientBranchPO;
	}


	public void seteFmFmClientBranchPO(EFmFmClientBranchPO eFmFmClientBranchPO) {
		this.eFmFmClientBranchPO = eFmFmClientBranchPO;
	}
	
	
	
	}