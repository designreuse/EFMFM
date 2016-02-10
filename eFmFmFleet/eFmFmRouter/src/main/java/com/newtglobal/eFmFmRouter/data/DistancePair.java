package com.newtglobal.eFmFmRouter.data;

import java.io.Serializable;
import java.util.Date;

import com.newtglobal.eFmFmRouter.clustering.Geocode;

public class DistancePair implements Serializable{
	private static final long serialVersionUID = -8188389085171189838L;
	private Geocode from;
	private Geocode to;
	private long distanceMetres;
	private long durationSeconds;
	
	private Date timestamp;
	
	public Geocode getFrom() {
		return from;
	}

	public Geocode getTo() {
		return to;
	}

	public long getDistanceMetres() {
		return distanceMetres;
	}

	public long getDurationSeconds() {
		return durationSeconds;
	}

	public Date getTime() {
		return timestamp;
	}

	public DistancePair(Geocode from, Geocode to, long distance_metres, long duration_seconds,
			Date time) {
		this.from = from;
		this.to = to;
		this.distanceMetres = distance_metres;
		this.durationSeconds = duration_seconds;
		this.timestamp = time;
	}
	
	public DistancePair(Geocode from, Geocode to) {
		this.from = from;
		this.to = to;
		this.distanceMetres = 0;
		this.durationSeconds = 0;
		this.timestamp = null;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof DistancePair)) return false;
		DistancePair distancePair = (DistancePair) object;
		return (((long)Math.floor(from.getLat()*1000000)) == ((long)Math.floor(distancePair.from.getLat()*1000000)) &&
				((long)Math.floor(from.getLong()*1000000)) == ((long)Math.floor(distancePair.from.getLong()*1000000))&&
				((long)Math.floor(to.getLat()*1000000)) == ((long)Math.floor(distancePair.to.getLat()*1000000)) &&
				((long)Math.floor(to.getLong()*1000000)) == ((long)Math.floor(distancePair.to.getLong()*1000000)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (int) Math.floor(from.getLat()*1000000);
		hash += 10*((int) Math.floor(to.getLat()*1000000));
		hash += 20*((int) Math.floor(from.getLong()*1000000));
		hash += 50*((int) Math.floor(to.getLong()*1000000));
		return hash;
	}
}