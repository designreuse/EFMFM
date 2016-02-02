package com.newtglobal.eFmFmFleet.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		long currentDate=new Date().getTime();
		Thread.sleep(5000);
		long currentDate2=new Date().getTime();
		long millis=currentDate2-currentDate;
		System.out.println(millis);
		// New date object from millis
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
	            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
	            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	    System.out.println(hms);
     System.out.println("kk");
	}

}
