package com.newtglobal.eFmFmFleet.eFmFmFleet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class CalculateDistance {
	private static  byte[] key;
	private static String keyString = "ZkcB7U7OSeKArCiA0rBuEdQSIGM=";  
	
	public String googlePlannedDistanceCalculation(String origin,String destination) throws IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException{
		String urlLocation = "";
		int distance=0;
		URL geocodeURL;
		urlLocation = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin+"&destinations="+destination+"&mode=driving&units=metric&sensor=true&client=gme-skymapglobalindia";
	    URL url = new URL(urlLocation);
	    CalculateDistance signer = new CalculateDistance();
	    signer.passingKey(keyString);
	    String request = signer.signRequest(url.getPath(),url.getQuery());
	    urlLocation=url.getProtocol() + "://" + url.getHost() + request;
			geocodeURL = new URL(urlLocation);
			URLConnection connection = geocodeURL.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer data = new StringBuffer();
			String line = "";
			String availDistance="";
			while((line = reader.readLine()) != null){
				data.append(line.trim());
			}		
			JSONObject status = new JSONObject(data.toString());
			String objstatus=status.getString("status");		
			    if(objstatus.equals("OK")){
					JSONArray rows = status.getJSONArray("rows");
					for(int j=0;j<rows.length()-1;j++){
						JSONArray elements = rows.getJSONObject(j).getJSONArray("elements");	
			    	for(int i=0; i<elements.length();){			    		
			    		distance+=elements.getJSONObject(j).getJSONObject("distance").getInt("value");
						break;
						}
					}
					float input = (float) (distance * .001);
					DecimalFormat decimalFormat = new DecimalFormat("0.#");
					availDistance=decimalFormat.format(input);
	     }	
		
		return availDistance;
	}
	
	
	public double employeeDistanceCalculation(String origin,String destination) throws IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException{
		String urlLocation = "";
		int distance=0;
		URL geocodeURL;
		urlLocation = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin+"&destinations="+destination+"&mode=driving&units=metric&sensor=true&client=gme-skymapglobalindia";
	    URL url = new URL(urlLocation);
	    CalculateDistance signer = new CalculateDistance();
	    signer.passingKey(keyString);
	    String request = signer.signRequest(url.getPath(),url.getQuery());
	    urlLocation=url.getProtocol() + "://" + url.getHost() + request;
			geocodeURL = new URL(urlLocation);
			URLConnection connection = geocodeURL.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer data = new StringBuffer();
			String line = "";
			double empDistance = 0;
			while((line = reader.readLine()) != null){
				data.append(line.trim());
			}		
			JSONObject status = new JSONObject(data.toString());
			String objstatus=status.getString("status");		
			    if(objstatus.equals("OK")){
					JSONArray rows = status.getJSONArray("rows");
					JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
					distance= elements.getJSONObject(0).getJSONObject("distance").getInt("value");
					empDistance = (double) (distance * .001);
	     }	
		
		return empDistance;
	}
	
	
	
	public int googleTravelledDistanceCalculation(String origin,String destination) throws IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException{
		String urlLocation = "";
		int distance=0;
//		urlLocation = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin+"&destinations="+destination+"&mode=driving&units=metric&sensor=true";			
		URL geocodeURL;
		urlLocation = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin+"&destinations="+destination+"&mode=driving&units=metric&sensor=true&client=gme-skymapglobalindia";
	    URL url = new URL(urlLocation);
	    CalculateDistance signer = new CalculateDistance();
	    signer.passingKey(keyString);
	    String request = signer.signRequest(url.getPath(),url.getQuery());
	    urlLocation=url.getProtocol() + "://" + url.getHost() + request;
			geocodeURL = new URL(urlLocation);
			URLConnection connection = geocodeURL.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer data = new StringBuffer();
			String line = "";
			while((line = reader.readLine()) != null){
				data.append(line.trim());
			}		
			JSONObject status = new JSONObject(data.toString());
			String objstatus=status.getString("status");
			    if(objstatus.equals("OK")){
					JSONArray rows = status.getJSONArray("rows");
					for(int j=0;j<rows.length();j++){
						JSONArray elements = rows.getJSONObject(j).getJSONArray("elements");	
			    	for(int i=0; i<elements.length();){			    		
			    		distance+=elements.getJSONObject(j).getJSONObject("distance").getInt("value");
						break;
						}
					}


	     }	
		
		return distance;
	}
	

	public  double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
		try{
	      double theta = lon1 - lon2;
	      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	      dist = Math.acos(dist);
	      dist = rad2deg(dist);
	      dist = dist * 60 * 1.1515;
	      if (unit == 'K'){
	        dist = dist * 1.609344;
	      }
	      else if (unit == 'm'){
		        dist = dist * 1609.3;
		      }
	      else if (unit == 'N'){
	        dist = dist * 0.8684;  
	      }
	      StringBuffer sb  =new StringBuffer();
	      DecimalFormat decimalFormat = new DecimalFormat("0.#");
	      sb.append(decimalFormat.format(dist));
	      return (Double.parseDouble(sb.toString()));
		}
		catch(Exception e ){
				return 0;
		}
	    }	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  /*::  This function converts decimal degrees to radians             :*/
  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  public double deg2rad(double deg) {
    return (deg * Math.PI / 180.0);
  }

  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  /*::  This function converts radians to decimal degrees             :*/
  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  public double rad2deg(double rad) {
    return (rad * 180.0 / Math.PI);
  }
	
  public void  passingKey(String keyString) throws IOException {
	    // Convert the key from 'web safe' base 64 to binary
	    keyString = keyString.replace('-', '+');
	    keyString = keyString.replace('_', '/');
	    System.out.println("Key: " + keyString);
	    this.key = Base64.decode(keyString);
	  }

	  public String signRequest(String path, String query) throws NoSuchAlgorithmException,
	    InvalidKeyException, UnsupportedEncodingException, URISyntaxException {   
	    // Retrieve the proper URL components to sign
	    String resource = path + '?' + query;  
	    // Get an HMAC-SHA1 signing key from the raw key bytes
	    SecretKeySpec sha1Key = new SecretKeySpec(key, "HmacSHA1");
	    // Get an HMAC-SHA1 Mac instance and initialize it with the HMAC-SHA1 key
	    Mac mac = Mac.getInstance("HmacSHA1");
	    mac.init(sha1Key);
	    // compute the binary signature for the request
	    byte[] sigBytes = mac.doFinal(resource.getBytes());
	    // base 64 encode the binary signature
	    String signature = Base64.encode(sigBytes);	    
	    // convert the signature to 'web safe' base 64
	    signature = signature.replace('+', '-');
	    signature = signature.replace('/', '_');	    
	    return resource + "&signature=" + signature;
	  }	
}