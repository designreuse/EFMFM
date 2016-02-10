package com.newtglobal.eFmFmFleet.services;

import com.newtglobal.eFmFmFleet.eFmFmFleet.Geocode;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String Vertex=""
		Geocode geocode1=new Geocode("12.973802,80.244119");
		Geocode geocode2=new Geocode("12.845873, 80.226510");
		

		System.out.println("Distance"+Geocode.distance(geocode1,geocode2));
		if(Geocode.distance(geocode1,geocode2)<1000){
			
			
		}
		
	}

}
