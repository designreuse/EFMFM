package com.newtglobal.eFmFmFleet.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class ValueFirstSMSMessager implements SMSMessager {

	private static Log log=LogFactory.getLog(ValueFirstSMSMessager.class);	

	@Override
	public boolean sendMessage(String smsNumber, String text,String requestType) throws Exception {
		final String USER_AGENT = "Mozilla/5.0";
		String GET_URL="";
		text = URLEncoder.encode(text, "UTF-8"); 
		if(smsNumber.length()==12 && smsNumber.subSequence(0, 2).equals("91")){
			log.info("Msg from National API");
		    GET_URL = "http://www.myvaluefirst.com/smpp/sendsms?username=newtglobalhttp&password=newtglobal123&to="+smsNumber+"&from=SBOTVI&text="+text+"&dlr-mask=19&dlr-url";
		}
		else{
			log.info("Msg from Guest API");
			GET_URL ="http://myvaluefirst.com/smpp/sendsms?username=ngfvint&password=ngfvint123&to="+smsNumber+"&udh=&from=SBOTVI&text="+text+"&dlr-url";
		}
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
           /* if(response.toString().contains("Service not available")){
            		log.warn("Message Responce From Gateway GET request not worked for Mobile: "+smsNumber+" : Service not available Message Text"+text);
        		return false;
            }*/
            log.info("Message ValueFirstSMSMessager Response From GateWay: "+response.toString()+" for Mobile: "+smsNumber+" : "+responseCode+" Message Text"+text);
        } else {
            log.warn("Message ValueFirstSMSMessager Responce From Gateway GET request not worked for Mobile: "+smsNumber+" : " +responseCode+" Message Text"+text);
            return false;
        }
		return true;
	}	

}
