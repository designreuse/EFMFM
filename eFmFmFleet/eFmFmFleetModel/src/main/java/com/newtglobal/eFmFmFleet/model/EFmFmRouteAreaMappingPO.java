package com.newtglobal.eFmFmFleet.model;

import java.io.Serializable;
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


/**
 * The persistent class for the eFmFmClientRouteAreaMapping database table.
 * 
 */
@Entity
@Table(name="eFmFmRouteAreaMapping")
public class EFmFmRouteAreaMappingPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RouteAreaId", length=15)
	private int routeAreaId;

	//bi-directional many-to-one association to EFmFmRouteMaster
	@ManyToOne
	@JoinColumn(name="ZoneId")
	private EFmFmZoneMasterPO eFmFmZoneMaster;

	//bi-directional many-to-one association to EFmFmAreaMaster
	@ManyToOne
	@JoinColumn(name="AreaId")
	private EFmFmAreaMasterPO efmFmAreaMaster;
	
	//bi-directional many-to-one association to EFmFmAssignRoute
	@OneToMany(mappedBy="eFmFmRouteAreaMapping")
	private List<EFmFmAssignRoutePO> efmFmAssignRoutes;			
	
	//bi-directional many-to-one association to eFmFmUserMaster
	@OneToMany(mappedBy="eFmFmRouteAreaMapping")
	private List<EFmFmUserMasterPO> eFmFmUserMaster;
	
	
	//bi-directional many-to-one association to eFmFmEmployeeTravelRequest
	@OneToMany(mappedBy="eFmFmRouteAreaMapping")
	private List<EFmFmEmployeeTravelRequestPO> eFmFmEmployeeTravelRequest;
		
		

	public EFmFmRouteAreaMappingPO() {
	}
	
	public int getRouteAreaId() {
		return routeAreaId;
	}


	public void setRouteAreaId(int routeAreaId) {
		this.routeAreaId = routeAreaId;
	}

	public List<EFmFmAssignRoutePO> getEfmFmAssignRoutes() {
		return efmFmAssignRoutes;
	}

	public void setEfmFmAssignRoutes(List<EFmFmAssignRoutePO> efmFmAssignRoutes) {
		this.efmFmAssignRoutes = efmFmAssignRoutes;
	}


	public EFmFmZoneMasterPO geteFmFmZoneMaster() {
		return eFmFmZoneMaster;
	}

	public void seteFmFmZoneMaster(EFmFmZoneMasterPO eFmFmZoneMaster) {
		this.eFmFmZoneMaster = eFmFmZoneMaster;
	}

	public EFmFmAreaMasterPO getEfmFmAreaMaster() {
		return this.efmFmAreaMaster;
	}
	public void setEfmFmAreaMaster(EFmFmAreaMasterPO efmFmAreaMaster) {
		this.efmFmAreaMaster = efmFmAreaMaster;
	}

	public List<EFmFmUserMasterPO> geteFmFmUserMaster() {
		return eFmFmUserMaster;
	}

	public void seteFmFmUserMaster(List<EFmFmUserMasterPO> eFmFmUserMaster) {
		this.eFmFmUserMaster = eFmFmUserMaster;
	}

	public List<EFmFmEmployeeTravelRequestPO> geteFmFmEmployeeTravelRequest() {
		return eFmFmEmployeeTravelRequest;
	}

	public void seteFmFmEmployeeTravelRequest(
			List<EFmFmEmployeeTravelRequestPO> eFmFmEmployeeTravelRequest) {
		this.eFmFmEmployeeTravelRequest = eFmFmEmployeeTravelRequest;
	}

	

}