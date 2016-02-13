package com.newtglobal.eFmFmFleet.eFmFmFleet;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class PushServiceTryout{
	
	
public  void pushMSG(String token,String msg){
      ApnsService service =
   	         APNS.newService()
   	        .withCert("/home/newtglobal/Dev/eFmFmPushCertificate.p12", "newt@123")                            
   	        .withSandboxDestination()
   	        .build();
	String payload = APNS.newPayload().alertBody(msg).build();
	service.push(token, payload);
	System.out.println("donneee");
	}
}