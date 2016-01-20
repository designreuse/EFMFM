package com.newtglobal.eFmFmFleet.services;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

public class PushNotificationService {

public void notification(String deviceToken,String userMessage ){
	final String GOOGLE_SERVER_KEY = "AIzaSyDgLJVUP8DjUQMYgV0L-iS8bomWy4b-bNQ";	
	 final String MESSAGE_KEY = "message";
	try {
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			Message message = new Message.Builder().timeToLive(30)
					.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
			sender.send(message, deviceToken, 1);
		} catch (Exception ioe) {
		}
}
}