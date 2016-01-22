package com.newtglobal.eFmFmFleet.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the eFmFmClientUserRole database table.
 * 
 */
@Entity
@Table(name="eFmFmClientUserRole")
public class EFmFmClientUserRolePO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserRoleId", length=10)
	private int userRoleId;

	//bi-directional many-to-one association to EFmFmClientBranchPO
	@ManyToOne
	@JoinColumn(name="BranchId")
	private EFmFmClientBranchPO eFmFmClientBranchPO;

	//bi-directional many-to-one association to EFmFmUserMaster
	@ManyToOne
	@JoinColumn(name="UserId")
	private EFmFmUserMasterPO efmFmUserMaster;

	//bi-directional many-to-one association to EFmFmRoleMaster
	@ManyToOne
	@JoinColumn(name="RoleId")
	private EFmFmRoleMasterPO efmFmRoleMaster;

	public EFmFmClientUserRolePO() {
	}

	

	public int getUserRoleId() {
		return userRoleId;
	}



	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	

	public EFmFmClientBranchPO geteFmFmClientBranchPO() {
		return eFmFmClientBranchPO;
	}



	public void seteFmFmClientBranchPO(EFmFmClientBranchPO eFmFmClientBranchPO) {
		this.eFmFmClientBranchPO = eFmFmClientBranchPO;
	}



	public EFmFmUserMasterPO getEfmFmUserMaster() {
		return this.efmFmUserMaster;
	}

	public void setEfmFmUserMaster(EFmFmUserMasterPO efmFmUserMaster) {
		this.efmFmUserMaster = efmFmUserMaster;
	}

	public EFmFmRoleMasterPO getEfmFmRoleMaster() {
		return this.efmFmRoleMaster;
	}

	public void setEfmFmRoleMaster(EFmFmRoleMasterPO efmFmRoleMaster) {
		this.efmFmRoleMaster = efmFmRoleMaster;
	}

}