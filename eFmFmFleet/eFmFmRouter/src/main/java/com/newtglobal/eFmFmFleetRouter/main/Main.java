package com.newtglobal.eFmFmFleetRouter.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.newtglobal.eFmFmFleetRouter.data.*;
import com.thetransactioncompany.jsonrpc2.*;
import com.thetransactioncompany.jsonrpc2.server.*;
import net.minidev.json.*;

import java.util.*;
import java.io.*;

public class Main
{
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter responseBuffer;
	private BufferedReader requestBuffer;
	
	static class RoutingHandler implements RequestHandler {
		
		public String[] handledRequests() {
		    return new String[]{"createRoute"};
		}
		
		public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {
			if (req.getMethod().equals("createRoute")) {
				try {
					Map<String, Object> params = (Map<String, Object>) req.getNamedParams();
					EfmfmVRP routes = new EfmfmVRP(new Settings((JSONObject) params.get("settings")), 
							JsonEmployee.convertArray((ArrayList<JSONObject>) params.get("employees")), 
							JsonDepot.convertArray((ArrayList<JSONObject>) params.get("depots")), 
							JsonVehicleType.convertArray((ArrayList<JSONObject>) params.get("vehicle_types")),
							JsonVehicle.convertArray((ArrayList<JSONObject>) params.get("vehicles")));
					routes.solve();
					return new JSONRPC2Response(routes.getSolution(), req.getID());
				} catch (Exception E) {
					E.printStackTrace();
					return new JSONRPC2Response(JSONRPC2Error.INTERNAL_ERROR, req.getID());
				}
		    }
		    else {					
		        return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, req.getID());
		    }
        }
	}
	
	public Main(int portNumber)
	{
		try { 
			serverSocket = new ServerSocket(portNumber);
			System.out.println("started server on port " + portNumber);
		    clientSocket = serverSocket.accept();
		    System.out.println("accepted client connection on port " + portNumber);
		    responseBuffer = new PrintWriter(clientSocket.getOutputStream(), true);
			requestBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	
		} catch (Exception E) {}
	}
	
	public void reconnect()
	{
		responseBuffer.close();
		try {
			requestBuffer.close();
			clientSocket.close();
			clientSocket = serverSocket.accept();
			responseBuffer = new PrintWriter(clientSocket.getOutputStream(), true);
			requestBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (Exception E) {}
	}
		
	
	public static void main(String [] args) throws IOException, ClassNotFoundException
	{	
		int portNumber = 4444;
		if (args.length > 0) {
			try {
				portNumber = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e) {
				System.err.println("Malformed port number provided as input. "
						+ "Using default port number of 4444");
			}
		}
		else {
			System.out.println("Using default port number of 4444");
		}
		
		//creating dispatcher and registering the sole routing handler
		Dispatcher dispatcher =  new Dispatcher();
		dispatcher.register(new RoutingHandler());
		
		Main A = new Main(portNumber);
		String inputLine;
		while (true) {
			while (true) {
				inputLine = A.requestBuffer.readLine();
				if (inputLine != null) {
					JSONRPC2Request req = null;
					try {
						System.out.println("Received request from client");
						System.out.println(inputLine);
						req = JSONRPC2Request.parse(inputLine);
						JSONRPC2Response response = dispatcher.process(req, null);
						A.responseBuffer.println(response.toJSONString());
						System.out.println(response.toJSONString());
						System.gc();
					} catch (JSONRPC2ParseException E) {
						System.err.println("Unable to process request");
						A.responseBuffer.println("Unable to process request");
					}
					break;
				}
			}
			A.reconnect();
		}
	}
}