package com.newtglobal.eFmFmFleetRouter.routing;

import java.io.IOException;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.newtglobal.eFmFmFleetRouter.clustering.Geocode;
import com.newtglobal.eFmFmFleetRouter.data.DistanceCache;
import com.newtglobal.eFmFmFleetRouter.data.DistancePair;
import com.newtglobal.eFmFmFleetRouter.data.Settings;

public class GoogleCosts implements CostInterface{
	private GeoApiContext context;
	private Settings settings;
	private static int apiCalls = 0;
	private static int cacheHits = 0;
	private DistanceCache cache;
	
	public static int getApiCalls() {
		return apiCalls;
	}

	public static int getCacheHits() {
		return cacheHits;
	}
	
	//public LinkedList<DistancePair> distancePairList;

	public GoogleCosts(String client_id, String cryptoKey, DistanceCache cache, Settings settings) {
		context = new GeoApiContext().setEnterpriseCredentials(client_id, cryptoKey).setQueryRateLimit(80);
		this.cache = cache;
		this.settings = settings;
	}

    public GoogleCosts(String apiKey, DistanceCache cache, Settings settings) {
		context = new GeoApiContext().setApiKey(apiKey);
		this.cache = cache;
		this.settings = settings;
	}

	private DistanceMatrixElement callApi(Geocode G1, Geocode G2) throws IOException {
		DistanceMatrix distance_matrix = null;
		
		
		distance_matrix = DistanceMatrixApi.newRequest(context).mode(TravelMode.DRIVING)
				.origins(new LatLng(G1.getLat(), G1.getLong()))
				.destinations(new LatLng(G2.getLat(), G2.getLong())).awaitIgnoreError();
		
		
		DistanceMatrixElement E = null;
		if(distance_matrix != null) {
			DistanceMatrixElement distancematrixelement = distance_matrix.rows[0].elements[0];
			if(distancematrixelement.distance != null && distancematrixelement.duration != null) {
				E = distancematrixelement;
			}
		}
		else {
			System.err.println("Check if connected to the internet");
			cache.saveToDisk(); //If this block of code gets executed, it means that the internet connection is not working
			throw new NullPointerException();
		}
		
		if (E != null) {
			cache.addToCache(new DistancePair(G1, G2, E.distance.inMeters, E.duration.inSeconds, 
					settings.shift_time));
		}
		
		else {
			System.err.println("Malformed geocode input in pair " + G1.getLat()+ "," + G1.getLong() 
			+ ";" + G2.getLat() + "," + G2.getLong());
			cache.saveToDisk(); //Something wrong with geocodes
			throw new NullPointerException();
		}
		apiCalls += 1;
		return E;
	}

	@Override
	public Cost getCost(Geocode G1, Geocode G2){
		DistancePair D = cache.search(G1, G2);
		DistanceMatrixElement E = null;
		if(D != null) {
			cacheHits += 1;
			return new Cost(D.getDistanceMetres(), D.getDurationSeconds());
		}
		else {
			try {
				E = callApi(G1, G2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new Cost(E.distance.inMeters, E.duration.inSeconds);
		}
	}
}
