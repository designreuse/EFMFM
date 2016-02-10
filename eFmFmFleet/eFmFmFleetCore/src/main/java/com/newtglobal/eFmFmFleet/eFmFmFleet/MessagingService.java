package com.newtglobal.eFmFmFleet.eFmFmFleet;
import com.newtglobal.eFmFmFleet.sms.SMSMessagerFactory;

public class MessagingService {
	
	public void etaMessage(String empName,String vehicleNum,String eta,String mobileNum,String requestType)throws Exception {			
		String text="Dear employee your cab " +vehicleNum+",will be at your pickup point in next " +eta;
		SMSMessagerFactory.getMessager().sendMessage(mobileNum, text,requestType);
    }	
	
	public void cabReachedMessage(String mobileNumber, String text,String requestType) throws Exception {
		SMSMessagerFactory.getMessager().sendMessage(mobileNumber, text,requestType);
	}
	
	public void sendTripAsMessage(String mobileNumber, String text,String requestType) throws Exception{
		SMSMessagerFactory.getMessager().sendMessage(mobileNumber, text,requestType);
	}
	
	public void etaMessageWhenCabDelay(String empName,String vehicleNum,String eta,String mobileNumber,String requestType)throws Exception{	
		String text="Dear employee your cab " +vehicleNum+", delayed will update you";
		SMSMessagerFactory.getMessager().sendMessage(mobileNumber, text,requestType);
    }
	
	public void cabHasLeftMessageForSch(String mobileNumber, String text,String requestType) throws Exception {
		SMSMessagerFactory.getMessager().sendMessage(mobileNumber, text,requestType);
	}

	public void cabHasLeftMessageForGuestFromSch(String mobileNumber, String text,String requestType) throws Exception {
		SMSMessagerFactory.getMessager().sendMessage(mobileNumber, text,requestType);
	}

	public void etaMessagesForGuest(String empName,String vehicleNum,String eta,String mobileNumber,String requestType)throws Exception{			
		String text="Dear guest your cab " +vehicleNum+",will be at your pickup point in next " +eta;
		SMSMessagerFactory.getGuestMessager().sendMessage(mobileNumber, text,requestType);
	}
	
	public void cabReachedMessageForGuest(String mobileNumber, String text,String requestType) throws Exception {
		SMSMessagerFactory.getGuestMessager().sendMessage(mobileNumber, text,requestType);
	}
	
	
	public void sendMessageToGuest(String mobileNumber, String text,String requestType) throws Exception{
		SMSMessagerFactory.getGuestMessager().sendMessage(mobileNumber, text,requestType);
	}
	
	public void etaMessagesForGuestWhenCabDelay(String empName,String vehicleNum,String eta,String mobileNumber,String requestType) throws Exception {
		String text="Dear guest your cab " +vehicleNum+",delayed will update you";
		SMSMessagerFactory.getGuestMessager().sendMessage(mobileNumber, text,requestType);
	}
}
