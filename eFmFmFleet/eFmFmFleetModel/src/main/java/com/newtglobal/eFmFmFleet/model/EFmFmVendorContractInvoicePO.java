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
 * The persistent class for the eFmFmVendorContractInvoice database table.
 * 
 */
@Entity
@Table(name="eFmFmVendorContractInvoice")
public class EFmFmVendorContractInvoicePO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ContractInvoiceId", length=15)
	private int invoiceId;
	
	@Column(name="InvoiceStatus", length=10)
	private String invoiceStatus;
	
	@Column(name="InvoiceType", length=200)
	private String invoiceType;

	@Column(name="InvoiceNumber", length=10)
	private int invoiceNumber;
	
	@Column(name="TotalExtraDistance", length=50)
	private double totalExtraDistance;
	
	@Column(name="TotalExtraHour", length=50)
	private double totalExtraHour;
	
	@Column(name="TravelledDistance", length=50)
	private double travelledDistance;
		
	@Column(name="TotalAbsenceHours", length=50)
	private double totalAbsenceHours;

	@Column(name="TotalAmountPayable", length=50)
	private double totalAmountPayable;
	
	@Column(name="BaseTotal", length=50)
	private double baseTotal;
	
	@Column(name="TotalDeductibles", length=50)
	private double totalDeductibles;
	
	@Column(name="BaseDistance", length=50)
	private double baseDistance;	
	
	@Column(name="TripTotalAmount", length=50)
	private double tripTotalAmount;
	
	@Column(name="ExtraDistanceCharge", length=50)
	private double extraDistanceCharge;
	
	@Column(name="TotalDistance", length=50)
	private double totalDistance;
	
	@Column(name="WorkingDays", length=50)
	private int workingDays;
	
	@Column(name="PresentDays", length=50)
	private int presentDays;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="InvoiceEndDate", length=30)
	private Date invoiceEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="InvoiveStartDate", length=30)
	private Date invoiveStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="InvoiceGenerationDate", length=30)
	private Date invoiceGenerationDate;
	
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
		
		
	/*//bi-directional many-to-one association to EFmFmVendorMaster
	@ManyToOne
	@JoinColumn(name="VehicleId")
	private EFmFmVehicleMasterPO eFmFmVehicleMaster;*/
	
	@ManyToOne
	@JoinColumn(name="AssignRouteId")
	private EFmFmAssignRoutePO efmFmAssignRoute;

	public EFmFmVendorContractInvoicePO() {
	}

	
	public double getBaseDistance() {
		return baseDistance;
	}


	public double getTravelledDistance() {
		return travelledDistance;
	}


	public void setTravelledDistance(double travelledDistance) {
		this.travelledDistance = travelledDistance;
	}


	public void setBaseDistance(double baseDistance) {
		this.baseDistance = baseDistance;
	}


	public double getTripTotalAmount() {
		return tripTotalAmount;
	}


	public void setTripTotalAmount(double tripTotalAmount) {
		this.tripTotalAmount = tripTotalAmount;
	}


	public double getTotalDistance() {
		return totalDistance;
	}


	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}


	public int getWorkingDays() {
		return workingDays;
	}


	public void setWorkingDays(int workingDays) {
		this.workingDays = workingDays;
	}


	public int getPresentDays() {
		return presentDays;
	}


	public void setPresentDays(int presentDays) {
		this.presentDays = presentDays;
	}


	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public double getTotalExtraDistance() {
		return totalExtraDistance;
	}

	public void setTotalExtraDistance(double totalExtraDistance) {
		this.totalExtraDistance = totalExtraDistance;
	}

	public double getTotalExtraHour() {
		return totalExtraHour;
	}

	public void setTotalExtraHour(double totalExtraHour) {
		this.totalExtraHour = totalExtraHour;
	}

	public double getTotalAbsenceHours() {
		return totalAbsenceHours;
	}

	public void setTotalAbsenceHours(double totalAbsenceHours) {
		this.totalAbsenceHours = totalAbsenceHours;
	}

	public double getTotalAmountPayable() {
		return totalAmountPayable;
	}

	public void setTotalAmountPayable(double totalAmountPayable) {
		this.totalAmountPayable = totalAmountPayable;
	}

	public double getBaseTotal() {
		return baseTotal;
	}

	public void setBaseTotal(double baseTotal) {
		this.baseTotal = baseTotal;
	}

	public double getTotalDeductibles() {
		return totalDeductibles;
	}

	public void setTotalDeductibles(double totalDeductibles) {
		this.totalDeductibles = totalDeductibles;
	}

	public Date getInvoiceEndDate() {
		return invoiceEndDate;
	}

	public void setInvoiceEndDate(Date invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}

	public Date getInvoiveStartDate() {
		return invoiveStartDate;
	}

	public void setInvoiveStartDate(Date invoiveStartDate) {
		this.invoiveStartDate = invoiveStartDate;
	}

	public Date getInvoiceGenerationDate() {
		return invoiceGenerationDate;
	}

	public void setInvoiceGenerationDate(Date invoiceGenerationDate) {
		this.invoiceGenerationDate = invoiceGenerationDate;
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


	public EFmFmAssignRoutePO getEfmFmAssignRoute() {
		return efmFmAssignRoute;
	}


	public void setEfmFmAssignRoute(EFmFmAssignRoutePO efmFmAssignRoute) {
		this.efmFmAssignRoute = efmFmAssignRoute;
	}


	public double getExtraDistanceCharge() {
		return extraDistanceCharge;
	}


	public void setExtraDistanceCharge(double extraDistanceCharge) {
		this.extraDistanceCharge = extraDistanceCharge;
	}

	/*public EFmFmVehicleMasterPO geteFmFmVehicleMaster() {
		return eFmFmVehicleMaster;
	}

	public void seteFmFmVehicleMaster(EFmFmVehicleMasterPO eFmFmVehicleMaster) {
		this.eFmFmVehicleMaster = eFmFmVehicleMaster;
	}*/

	
	
	
}