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


/**
 * The persistent class for the eFmFmClientProjectDetailsPO database table.
 * 
 */
@Entity
@Table(name="eFmFmClientProjectDetails")
public class EFmFmClientProjectDetailsPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ProjectId", length=10)
	private int projectId;
	
	
	@Column(name="ClientProjectId", length=50)
	private String clientProjectId;
	
	@Column(name="EmployeeProjectName", length=50)
	private String employeeProjectName;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ProjectAllocationStarDate", length=30)
	private Date projectAllocationStarDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ProjectAllocationEndDate", length=30)
	private Date projectAllocationEndDate;
	
	//bi-directional many-to-one association to EFmFmClientMaster
	@ManyToOne
	@JoinColumn(name="BranchId")
	private EFmFmClientBranchPO eFmFmClientBranchPO;
	
	//bidirectional manytoone association to eFmFmClientProjectDetails
	@OneToMany(mappedBy="eFmFmClientProjectDetails")
	private List<EFmFmUserMasterPO> eFmFmUserMaster;



	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getClientProjectId() {
		return clientProjectId;
	}

	public void setClientProjectId(String clientProjectId) {
		this.clientProjectId = clientProjectId;
	}

	public String getEmployeeProjectName() {
		return employeeProjectName;
	}

	public void setEmployeeProjectName(String employeeProjectName) {
		this.employeeProjectName = employeeProjectName;
	}

	public Date getProjectAllocationStarDate() {
		return projectAllocationStarDate;
	}

	public void setProjectAllocationStarDate(Date projectAllocationStarDate) {
		this.projectAllocationStarDate = projectAllocationStarDate;
	}

	public Date getProjectAllocationEndDate() {
		return projectAllocationEndDate;
	}

	public void setProjectAllocationEndDate(Date projectAllocationEndDate) {
		this.projectAllocationEndDate = projectAllocationEndDate;
	}

	public EFmFmClientBranchPO geteFmFmClientBranchPO() {
		return eFmFmClientBranchPO;
	}

	public void seteFmFmClientBranchPO(EFmFmClientBranchPO eFmFmClientBranchPO) {
		this.eFmFmClientBranchPO = eFmFmClientBranchPO;
	}

	public List<EFmFmUserMasterPO> geteFmFmUserMaster() {
		return eFmFmUserMaster;
	}

	public void seteFmFmUserMaster(List<EFmFmUserMasterPO> eFmFmUserMaster) {
		this.eFmFmUserMaster = eFmFmUserMaster;
	}
	
	
}
