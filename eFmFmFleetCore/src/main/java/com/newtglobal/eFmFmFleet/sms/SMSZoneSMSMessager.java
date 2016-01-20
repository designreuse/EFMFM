package com.newtglobal.eFmFmFleet.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class SMSZoneSMSMessager implements SMSMessager {

	private static Log log=LogFactory.getLog(SMSZoneSMSMessager.class);	

	@Override
	public boolean sendMessage(String smsNumber, String text,String requestType) throws Exception {
		final String USER_AGENT = "Mozilla/5.0";		 
		text = URLEncoder.encode(text, "UTF-8"); 
		final String GET_URL = "http://www.smszone.in/sendsms.asp?page=SendSmsBulk&username=919841292063&password=A0D1&number="+smsNumber+"&message="+text;
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer(); 
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            log.info("Message Response From GateWay: "+response.toString()+" for Mobile: "+smsNumber+" : "+responseCode+" Message Text"+text);
        } else {
            log.warn("Message Responce From Gateway GET request not worked for Mobile: "+smsNumber+" : " +responseCode+" Message Text"+text);
            return false;
        }
		return true;
	}

}
