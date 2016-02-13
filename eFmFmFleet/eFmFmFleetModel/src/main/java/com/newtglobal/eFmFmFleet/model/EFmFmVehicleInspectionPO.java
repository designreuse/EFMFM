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

@Entity
@Table(name = "eFmFmVehicleInspection")
public class EFmFmVehicleInspectionPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "InspectionId", length = 10)
	private int inspectionId;

	@Column(name = "DocumentRC")
	boolean documentRc;

	@Column(name = "DocumentInsurance")
	boolean documentInsurance;

	@Column(name = "DocumentDriverLicence")
	boolean documentDriverLicence;

	@Column(name = "DocumentUpdateJmp")
	boolean documentUpdateJmp;

	@Column(name = "FirstAidKit")
	boolean firstAidKit;

	@Column(name = "FireExtingusher")
	boolean fireExtingusher;

	@Column(name = "AllSeatBeltsWorking")
	boolean allSeatBeltsWorking;

	@Column(name = "Placard")
	boolean placard;

	@Column(name = "MosquitoBat")
	boolean mosquitoBat;

	@Column(name = "PanicButton")
	boolean panicButton;

	@Column(name = "WalkyTalky")
	boolean walkyTalky;

	@Column(name = "GPS")
	boolean GPS;

	@Column(name = "DriverUniform")
	boolean driverUniform;

	@Column(name = "Stephney")
	boolean stephney;

	@Column(name = "Umbrella")
	boolean umbrella;

	@Column(name = "Torch")
	boolean torch;

	@Column(name = "Toolkit")
	boolean toolkit;

	@Column(name = "SeatUpholtseryCleanNotTorn")
	boolean seatUpholtseryCleanNotTorn;

	@Column(name = "VehcileRoofUpholtseryCleanNotTorn")
	boolean vehcileRoofUpholtseryCleanNotTorn;

	@Column(name = "VehcileFloorUpholtseryCleanNotTorn")
	boolean vehcileFloorUpholtseryCleanNotTorn;

	@Column(name = "VehcileDashboardClean")
	boolean vehcileDashboardClean;

	@Column(name = "VehicleGlassCleanStainFree")
	boolean vehicleGlassCleanStainFree;

	@Column(name = "VehicleInteriorLightBrightWorking")
	boolean vehicleInteriorLightBrightWorking;

	@Column(name = "BolsterSeperatingLastTwoSeats")
	boolean bolsterSeperatingLastTwoSeats;

	@Column(name = "ExteriorScratches")
	boolean exteriorScratches;

	@Column(name = "NoPatchWork")
	boolean noPatchWork;

	@Column(name = "PedalBrakeWorking")
	boolean PedalBrakeWorking;

	@Column(name = "HandBrakeWorking")
	boolean handBrakeWorking;

	@Column(name = "TyresNoDentsTrimWheel")
	boolean tyresNoDentsTrimWheel;

	@Column(name = "TyresGoodCondition")
	boolean tyresGoodCondition;

	@Column(name = "AllTyresAndStephneyInflate")
	boolean allTyresAndStephneyInflate;

	@Column(name = "ackAndTool")
	boolean JackAndTool;

	@Column(name = "NumberofPunctruresdone")
	int numberofPunctruresdone;

	@Column(name = "WiperWorking")
	boolean wiperWorking;

	@Column(name = "Ac_CoolingIn5mins")
	boolean acCoolingIn5mins;

	@Column(name = "NoSmellInAC")
	boolean NoSmellInAC;

	@Column(name = "AcVentsClean")
	boolean acVentsClean;

	@Column(name = "EnginOilChangeIndicatorOn")
	boolean enginOilChangeIndicatorOn;

	@Column(name = "CoolantIndicatorOn")
	boolean coolantIndicatorOn;

	@Column(name = "BrakeClutchIndicatorOn")
	boolean brakeClutchIndicatorOn;

	@Column(name = "HighBeamWorking")
	boolean highBeamWorking;

	@Column(name = "LowBeamWorking")
	boolean lowBeamWorking;

	@Column(name = "RightFromtIndicatorWorking")
	boolean rightFromtIndicatorWorking;

	@Column(name = "LeftFrontIndicatorWorking")
	boolean leftFrontIndicatorWorking;

	@Column(name = "ParkingLightsWorking")
	boolean parkingLightsWorking;

	@Column(name = "LedDayTimeRunningLightWorking")
	boolean ledDayTimeRunningLightWorking;

	@Column(name = "RightRearIndicatorWorking")
	boolean rightRearIndicatorWorking;

	@Column(name = "LeftRearIndicatorWorking")
	boolean leftRearIndicatorWorking;

	@Column(name = "BrakeLightsOn")
	boolean brakeLightsOn;

	@Column(name = "ReverseLightsOn")
	boolean ReverseLightsOn;

	@Column(name = "Diesel", length = 50)
	String diesel;

	@Column(name = "HornWorking")
	boolean hornWorking;

	@Column(name = "ReflectionSignBoard")
	boolean reflectionSignBoard;

	@Column(name = "DriverName")
	String driverName;

	@Column(name = "remarks")
	String remarks;
	
	//bi-directional many-to-one association to EFmFmVehicleMaster
	@ManyToOne
	@JoinColumn(name="VehicleId")
	private EFmFmVehicleMasterPO efmFmVehicleMaster;
	
	//bi-directional many-to-one association to EFmFmUserMaster
	@ManyToOne
	@JoinColumn(name="UserId")
	private EFmFmUserMasterPO efmFmUserMaster;


	public EFmFmVehicleMasterPO getEfmFmVehicleMaster() {
		return efmFmVehicleMaster;
	}

	public void setEfmFmVehicleMaster(EFmFmVehicleMasterPO efmFmVehicleMaster) {
		this.efmFmVehicleMaster = efmFmVehicleMaster;
	}

		public int getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(int inspectionId) {
		this.inspectionId = inspectionId;
	}

	public boolean isDocumentRc() {
		return documentRc;
	}

	public void setDocumentRc(boolean documentRc) {
		this.documentRc = documentRc;
	}

	public boolean isDocumentInsurance() {
		return documentInsurance;
	}

	public void setDocumentInsurance(boolean documentInsurance) {
		this.documentInsurance = documentInsurance;
	}

	public boolean isDocumentDriverLicence() {
		return documentDriverLicence;
	}

	public void setDocumentDriverLicence(boolean documentDriverLicence) {
		this.documentDriverLicence = documentDriverLicence;
	}

	public boolean isDocumentUpdateJmp() {
		return documentUpdateJmp;
	}

	public void setDocumentUpdateJmp(boolean documentUpdateJmp) {
		this.documentUpdateJmp = documentUpdateJmp;
	}

	public boolean isFirstAidKit() {
		return firstAidKit;
	}

	public void setFirstAidKit(boolean firstAidKit) {
		this.firstAidKit = firstAidKit;
	}

	public boolean isFireExtingusher() {
		return fireExtingusher;
	}

	public void setFireExtingusher(boolean fireExtingusher) {
		this.fireExtingusher = fireExtingusher;
	}

	public boolean isAllSeatBeltsWorking() {
		return allSeatBeltsWorking;
	}

	public void setAllSeatBeltsWorking(boolean allSeatBeltsWorking) {
		this.allSeatBeltsWorking = allSeatBeltsWorking;
	}

	public boolean isPlacard() {
		return placard;
	}

	public void setPlacard(boolean placard) {
		this.placard = placard;
	}

	public boolean isMosquitoBat() {
		return mosquitoBat;
	}

	public void setMosquitoBat(boolean mosquitoBat) {
		this.mosquitoBat = mosquitoBat;
	}

	public boolean isPanicButton() {
		return panicButton;
	}

	public void setPanicButton(boolean panicButton) {
		this.panicButton = panicButton;
	}

	public boolean isWalkyTalky() {
		return walkyTalky;
	}

	public void setWalkyTalky(boolean walkyTalky) {
		this.walkyTalky = walkyTalky;
	}

	public boolean isGPS() {
		return GPS;
	}

	public void setGPS(boolean gPS) {
		GPS = gPS;
	}

	public boolean isDriverUniform() {
		return driverUniform;
	}

	public void setDriverUniform(boolean driverUniform) {
		this.driverUniform = driverUniform;
	}

	public boolean isStephney() {
		return stephney;
	}

	public void setStephney(boolean stephney) {
		this.stephney = stephney;
	}

	public boolean isUmbrella() {
		return umbrella;
	}

	public void setUmbrella(boolean umbrella) {
		this.umbrella = umbrella;
	}

	public boolean isTorch() {
		return torch;
	}

	public void setTorch(boolean torch) {
		this.torch = torch;
	}

	public boolean isToolkit() {
		return toolkit;
	}

	public void setToolkit(boolean toolkit) {
		this.toolkit = toolkit;
	}

	public boolean isSeatUpholtseryCleanNotTorn() {
		return seatUpholtseryCleanNotTorn;
	}

	public void setSeatUpholtseryCleanNotTorn(boolean seatUpholtseryCleanNotTorn) {
		this.seatUpholtseryCleanNotTorn = seatUpholtseryCleanNotTorn;
	}

	public boolean isVehcileRoofUpholtseryCleanNotTorn() {
		return vehcileRoofUpholtseryCleanNotTorn;
	}

	public void setVehcileRoofUpholtseryCleanNotTorn(
			boolean vehcileRoofUpholtseryCleanNotTorn) {
		this.vehcileRoofUpholtseryCleanNotTorn = vehcileRoofUpholtseryCleanNotTorn;
	}

	public boolean isVehcileFloorUpholtseryCleanNotTorn() {
		return vehcileFloorUpholtseryCleanNotTorn;
	}

	public void setVehcileFloorUpholtseryCleanNotTorn(
			boolean vehcileFloorUpholtseryCleanNotTorn) {
		this.vehcileFloorUpholtseryCleanNotTorn = vehcileFloorUpholtseryCleanNotTorn;
	}

	public boolean isVehcileDashboardClean() {
		return vehcileDashboardClean;
	}

	public void setVehcileDashboardClean(boolean vehcileDashboardClean) {
		this.vehcileDashboardClean = vehcileDashboardClean;
	}

	public boolean isVehicleGlassCleanStainFree() {
		return vehicleGlassCleanStainFree;
	}

	public void setVehicleGlassCleanStainFree(boolean vehicleGlassCleanStainFree) {
		this.vehicleGlassCleanStainFree = vehicleGlassCleanStainFree;
	}

	public boolean isVehicleInteriorLightBrightWorking() {
		return vehicleInteriorLightBrightWorking;
	}

	public void setVehicleInteriorLightBrightWorking(
			boolean vehicleInteriorLightBrightWorking) {
		this.vehicleInteriorLightBrightWorking = vehicleInteriorLightBrightWorking;
	}

	public boolean isBolsterSeperatingLastTwoSeats() {
		return bolsterSeperatingLastTwoSeats;
	}

	public void setBolsterSeperatingLastTwoSeats(
			boolean bolsterSeperatingLastTwoSeats) {
		this.bolsterSeperatingLastTwoSeats = bolsterSeperatingLastTwoSeats;
	}

	public boolean isExteriorScratches() {
		return exteriorScratches;
	}

	public void setExteriorScratches(boolean exteriorScratches) {
		this.exteriorScratches = exteriorScratches;
	}

	public boolean isNoPatchWork() {
		return noPatchWork;
	}

	public void setNoPatchWork(boolean noPatchWork) {
		this.noPatchWork = noPatchWork;
	}

	public boolean isPedalBrakeWorking() {
		return PedalBrakeWorking;
	}

	public void setPedalBrakeWorking(boolean pedalBrakeWorking) {
		PedalBrakeWorking = pedalBrakeWorking;
	}

	public boolean isHandBrakeWorking() {
		return handBrakeWorking;
	}

	public void setHandBrakeWorking(boolean handBrakeWorking) {
		this.handBrakeWorking = handBrakeWorking;
	}

	public boolean isTyresNoDentsTrimWheel() {
		return tyresNoDentsTrimWheel;
	}

	public void setTyresNoDentsTrimWheel(boolean tyresNoDentsTrimWheel) {
		this.tyresNoDentsTrimWheel = tyresNoDentsTrimWheel;
	}

	public boolean isTyresGoodCondition() {
		return tyresGoodCondition;
	}

	public void setTyresGoodCondition(boolean tyresGoodCondition) {
		this.tyresGoodCondition = tyresGoodCondition;
	}

	public boolean isAllTyresAndStephneyInflate() {
		return allTyresAndStephneyInflate;
	}

	public void setAllTyresAndStephneyInflate(boolean allTyresAndStephneyInflate) {
		this.allTyresAndStephneyInflate = allTyresAndStephneyInflate;
	}

	public boolean isJackAndTool() {
		return JackAndTool;
	}

	public void setJackAndTool(boolean jackAndTool) {
		JackAndTool = jackAndTool;
	}

	public int getNumberofPunctruresdone() {
		return numberofPunctruresdone;
	}

	public void setNumberofPunctruresdone(int numberofPunctruresdone) {
		this.numberofPunctruresdone = numberofPunctruresdone;
	}

	public boolean isWiperWorking() {
		return wiperWorking;
	}

	public void setWiperWorking(boolean wiperWorking) {
		this.wiperWorking = wiperWorking;
	}

	public boolean isAcCoolingIn5mins() {
		return acCoolingIn5mins;
	}

	public void setAcCoolingIn5mins(boolean acCoolingIn5mins) {
		this.acCoolingIn5mins = acCoolingIn5mins;
	}

	public boolean isNoSmellInAC() {
		return NoSmellInAC;
	}

	public void setNoSmellInAC(boolean noSmellInAC) {
		NoSmellInAC = noSmellInAC;
	}

	public boolean isAcVentsClean() {
		return acVentsClean;
	}

	public void setAcVentsClean(boolean acVentsClean) {
		this.acVentsClean = acVentsClean;
	}

	public boolean isEnginOilChangeIndicatorOn() {
		return enginOilChangeIndicatorOn;
	}

	public void setEnginOilChangeIndicatorOn(boolean enginOilChangeIndicatorOn) {
		this.enginOilChangeIndicatorOn = enginOilChangeIndicatorOn;
	}

	public boolean isCoolantIndicatorOn() {
		return coolantIndicatorOn;
	}

	public void setCoolantIndicatorOn(boolean coolantIndicatorOn) {
		this.coolantIndicatorOn = coolantIndicatorOn;
	}

	public boolean isBrakeClutchIndicatorOn() {
		return brakeClutchIndicatorOn;
	}

	public void setBrakeClutchIndicatorOn(boolean brakeClutchIndicatorOn) {
		this.brakeClutchIndicatorOn = brakeClutchIndicatorOn;
	}

	public boolean isHighBeamWorking() {
		return highBeamWorking;
	}

	public void setHighBeamWorking(boolean highBeamWorking) {
		this.highBeamWorking = highBeamWorking;
	}

	public boolean isLowBeamWorking() {
		return lowBeamWorking;
	}

	public void setLowBeamWorking(boolean lowBeamWorking) {
		this.lowBeamWorking = lowBeamWorking;
	}

	public boolean isRightFromtIndicatorWorking() {
		return rightFromtIndicatorWorking;
	}

	public void setRightFromtIndicatorWorking(boolean rightFromtIndicatorWorking) {
		this.rightFromtIndicatorWorking = rightFromtIndicatorWorking;
	}

	public boolean isLeftFrontIndicatorWorking() {
		return leftFrontIndicatorWorking;
	}

	public void setLeftFrontIndicatorWorking(boolean leftFrontIndicatorWorking) {
		this.leftFrontIndicatorWorking = leftFrontIndicatorWorking;
	}

	public boolean isParkingLightsWorking() {
		return parkingLightsWorking;
	}

	public void setParkingLightsWorking(boolean parkingLightsWorking) {
		this.parkingLightsWorking = parkingLightsWorking;
	}

	public boolean isLedDayTimeRunningLightWorking() {
		return ledDayTimeRunningLightWorking;
	}

	public void setLedDayTimeRunningLightWorking(
			boolean ledDayTimeRunningLightWorking) {
		this.ledDayTimeRunningLightWorking = ledDayTimeRunningLightWorking;
	}

	public boolean isRightRearIndicatorWorking() {
		return rightRearIndicatorWorking;
	}

	public void setRightRearIndicatorWorking(boolean rightRearIndicatorWorking) {
		this.rightRearIndicatorWorking = rightRearIndicatorWorking;
	}

	public boolean isLeftRearIndicatorWorking() {
		return leftRearIndicatorWorking;
	}

	public void setLeftRearIndicatorWorking(boolean leftRearIndicatorWorking) {
		this.leftRearIndicatorWorking = leftRearIndicatorWorking;
	}

	public boolean isBrakeLightsOn() {
		return brakeLightsOn;
	}

	public void setBrakeLightsOn(boolean brakeLightsOn) {
		this.brakeLightsOn = brakeLightsOn;
	}

	public boolean isReverseLightsOn() {
		return ReverseLightsOn;
	}

	public void setReverseLightsOn(boolean reverseLightsOn) {
		ReverseLightsOn = reverseLightsOn;
	}

	public String getDiesel() {
		return diesel;
	}

	public void setDiesel(String diesel) {
		this.diesel = diesel;
	}

	public boolean isHornWorking() {
		return hornWorking;
	}

	public void setHornWorking(boolean hornWorking) {
		this.hornWorking = hornWorking;
	}

	public boolean isReflectionSignBoard() {
		return reflectionSignBoard;
	}

	public void setReflectionSignBoard(boolean reflectionSignBoard) {
		this.reflectionSignBoard = reflectionSignBoard;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
