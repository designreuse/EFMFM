package com.newtglobal.eFmFmFleet.TransferObjects;

import java.io.Serializable;
import java.util.Date;

public class TravelActivity implements Serializable,Comparable<TravelActivity> {
	
	private static final long serialVersionUID = 1L;
	
	private int trackingSessionDetailId;
	
	private Date trackingTime ;
	
	private boolean isTrackedDeviceMoving;
	
	private double deviceLatitude;
	
	private double deviceLongitude;
	
	private String trackingLocationDetail;
	
	private String mobileNo;

	private boolean  isTrackingActive;	

	private String  trackerImeiNumber;

	private String trackedImeiNumber;
	
	private String eta;
	
	private int distance;
	
	private String destinationAddresses;

	private String originAddresses;

	private long trackingDate;
	
	private int groupId;


	
	public int getGroupId() {
		return groupId;
	}


	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}


	public int getTrackingSessionDetailId() {
		return trackingSessionDetailId;
	}


	public void setTrackingSessionDetailId(int trackingSessionDetailId) {
		this.trackingSessionDetailId = trackingSessionDetailId;
	}



	public Date getTrackingTime() {
		return trackingTime;
	}



	public void setTrackingTime(Date trackingTime) {
		this.trackingTime = trackingTime;
	}



	public boolean isTrackedDeviceMoving() {
		return isTrackedDeviceMoving;
	}



	public void setTrackedDeviceMoving(boolean isTrackedDeviceMoving) {
		this.isTrackedDeviceMoving = isTrackedDeviceMoving;
	}



	public double getDeviceLatitude() {
		return deviceLatitude;
	}



	public void setDeviceLatitude(double deviceLatitude) {
		this.deviceLatitude = deviceLatitude;
	}



	public double getDeviceLongitude() {
		return deviceLongitude;
	}



	public void setDeviceLongitude(double deviceLongitude) {
		this.deviceLongitude = deviceLongitude;
	}



	public String getTrackingLocationDetail() {
		return trackingLocationDetail;
	}



	public void setTrackingLocationDetail(String trackingLocationDetail) {
		this.trackingLocationDetail = trackingLocationDetail;
	}



	public String getMobileNo() {
		return mobileNo;
	}



	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}



	public boolean isTrackingActive() {
		return isTrackingActive;
	}



	public void setTrackingActive(boolean isTrackingActive) {
		this.isTrackingActive = isTrackingActive;
	}



	public String getTrackerImeiNumber() {
		return trackerImeiNumber;
	}



	public void setTrackerImeiNumber(String trackerImeiNumber) {
		this.trackerImeiNumber = trackerImeiNumber;
	}



	public String getTrackedImeiNumber() {
		return trackedImeiNumber;
	}



	public void setTrackedImeiNumber(String trackedImeiNumber) {
		this.trackedImeiNumber = trackedImeiNumber;
	}



	public String getEta() {
		return eta;
	}



	public void setEta(String eta) {
		this.eta = eta;
	}



	public int getDistance() {
		return distance;
	}



	public void setDistance(int distance) {
		this.distance = distance;
	}



	public String getDestinationAddresses() {
		return destinationAddresses;
	}



	public void setDestinationAddresses(String destinationAddresses) {
		this.destinationAddresses = destinationAddresses;
	}



	public String getOriginAddresses() {
		return originAddresses;
	}

	public void setOriginAddresses(String originAddresses) {
		this.originAddresses = originAddresses;
	}
     	
	public long getTrackingDate() {
		return trackingDate;
	}


	public void setTrackingDate(long trackingDate) {
		this.trackingDate = trackingDate;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public int compareTo(TravelActivity arg0) {
		// TODO Auto-generated method stub
		if(this.trackingDate > arg0.trackingDate){
			return -1;
		}else if(this.trackingDate < arg0.trackingDate){
			return 1;
		}else{
			return 0;
	/*			if(arg0.getContentId() == this.contentId){
				return 0;
			}else{
				return -1;
			}
	*/		}
	}

}
