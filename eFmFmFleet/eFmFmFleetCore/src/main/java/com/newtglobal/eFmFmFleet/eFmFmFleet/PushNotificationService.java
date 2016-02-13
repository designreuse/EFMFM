package com.newtglobal.eFmFmFleet.eFmFmFleet;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class PushNotificationService {

	private static Log log = LogFactory.getLog(PushNotificationService.class);

	public void notification(String deviceToken,String userMessage ){
	final String GOOGLE_SERVER_KEY = "AIzaSyAapRgZnLGWytYGVbS29WUPYm0wXggb53c";	
	 final String MESSAGE_KEY = "message";
		Result result = null;
	// GCM RedgId of Android device to send push notification
	try {
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			log.info("Push Send to "+new Date());
			Message message = new Message.Builder().timeToLive(30)
					.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
			result = sender.send(message, deviceToken, 1);
		} catch (Exception ioe) {
		log.info("Push Exception Block"+ioe);
		}
	
		
	
}
}