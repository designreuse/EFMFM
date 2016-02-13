package com.newtglobal.eFmFmFleet.services;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String token = "c0c699c44ef79460bba2c86ace24d0f1fc1666234ee097636c3d9d49b3b594ce";
		String msg = "testdata";

		ApnsService service = APNS
				.newService()
				.withCert("/home/newtglobal/Dev/eFmFmPushNotification.p12",
						"newt@123").withSandboxDestination().build();
		String payload = APNS.newPayload().alertBody(msg).build();
		service.push(token, payload);
		System.out.println("done");

		/*
		 * // TODO Auto-generated method stub String deviceToken=
		 * "ez1fpAzfAME:APA91bGywAac-BYTnJd80xKR1rierXM-BkC9iUAuikN77TN19bREhsG8m7XA1eQcCzeQasnU53S-EO2ASaBXrwNsvw5zEX95-Yf3Oab4SBs3_MWCSAi9J8v_sjhJtCvIr1yH8cBxQE5O"
		 * ; final String GOOGLE_SERVER_KEY =
		 * "AIzaSyAapRgZnLGWytYGVbS29WUPYm0wXggb53c"; final String MESSAGE_KEY =
		 * "message"; Result result = null; // GCM RedgId of Android device to
		 * send push notification try { Sender sender = new
		 * Sender(GOOGLE_SERVER_KEY);
		 * 
		 * Message message = new Message.Builder().timeToLive(30)
		 * .delayWhileIdle(true).addData(MESSAGE_KEY, deviceToken).build();
		 * result = sender.send(message, deviceToken, 1);
		 * System.out.println("Push Send to "+new Date()+"result"+result); }
		 * catch (Exception ioe) {
		 * System.out.println("Push Exception Block"+ioe); }
		 */}

}
