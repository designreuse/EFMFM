package com.newtglobal.eFmFmFleet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchInitialization {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("messageSendingJob.xml");
		new ClassPathXmlApplicationContext("itemOrientedJob.xml");
	}
		
}
