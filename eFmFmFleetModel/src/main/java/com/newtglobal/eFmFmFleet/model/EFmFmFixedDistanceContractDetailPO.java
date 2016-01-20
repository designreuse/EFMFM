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
 * The persistent class for the eFmFmFixedDistanceContractDetail database table.
 * 
 */
@Entity
@Table(name="eFmFmFixedDistanceContractDetail")
public class EFmFmFixedDistanceContractDetailPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DistanceContractId", length=15)
	private int distanceContractId;
	
	@Column(name="FixedDistanceMonthly", length=10)
	private double fixedDistanceMonthly;
	
	
	@Column(name="FixedDistancePrDay", length=10)
	private double fixedDistancePrDay;
	
	
	@Column(name="MinimumDays", length=10)
	private int minimumDays;


	
	@Column(name="ExtraDistanceChargeRate", length=50)
	private double extraDistanceChargeRate;
		
	
	@Column(name="FixedDistanceChargeRate", length=50)
	private double fixedDistanceChargeRate;
	
	@Column(name="Penalty", length=10)
	private String penalty;

	
	
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



	public EFmFmFixedDistanceContractDetailPO() {
	}



	public int getDistanceContractId() {
		return distanceContractId;
	}



	public void setDistanceContractId(int distanceContractId) {
		this.distanceContractId = distanceContractId;
	}

	public double getFixedDistanceMonthly() {
		return fixedDistanceMonthly;
	}



	public void setFixedDistanceMonthly(double fixedDistanceMonthly) {
		this.fixedDistanceMonthly = fixedDistanceMonthly;
	}



	public double getFixedDistancePrDay() {
		return fixedDistancePrDay;
	}



	public void setFixedDistancePrDay(double fixedDistancePrDay) {
		this.fixedDistancePrDay = fixedDistancePrDay;
	}



	public int getMinimumDays() {
		return minimumDays;
	}



	public void setMinimumDays(int minimumDays) {
		this.minimumDays = minimumDays;
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



	public String getPenalty() {
		return penalty;
	}



	public void setPenalty(String penalty) {
		this.penalty = penalty;
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