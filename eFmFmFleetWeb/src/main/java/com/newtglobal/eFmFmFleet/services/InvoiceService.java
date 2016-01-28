package com.newtglobal.eFmFmFleet.services;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmFixedDistanceContractDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmTripBasedContractDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorContractInvoicePO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;



@Component
@Path("/contract")
@Consumes("application/json")
@Produces("application/json")
public class InvoiceService {	
	@POST
	@Path("/invoiceTripDetails")
	public Response invoiceTripDetails(EFmFmVehicleMasterPO  eFmFmVehicleMasterPO) throws ParseException{		  
		  IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");		  
			DateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat  monthDate = new SimpleDateFormat("MM-yyyy");	
			List<Map<String, Object>> invoiceVendorDetails= new ArrayList<Map<String, Object>>();
			Map<String, Object>  fixedDistanceVehicleDetails= new HashMap<String, Object>();
			String activityType=eFmFmVehicleMasterPO.getActionType();			
			EFmFmClientBranchPO clientBranchPO=new EFmFmClientBranchPO();
			clientBranchPO.setBranchId(eFmFmVehicleMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());
			Random rand = new Random();
			int randomNumber = rand.nextInt((999999999 - 0) + 1) + 0;	
			switch (activityType.toUpperCase().trim()) {
			case "VENDORBASED":	
				Date fromDate =(Date) monthDate.parse(eFmFmVehicleMasterPO.getInvoiceDate());			
				Date toDate = (Date) monthDate.parse(eFmFmVehicleMasterPO.getInvoiceDate());
				List<EFmFmVendorContractInvoicePO> listInvoiceDetails=iVehicleCheckInBO.getInvoiceforVendorByGroup(fromDate, toDate, clientBranchPO.getBranchId(),eFmFmVehicleMasterPO.getEfmFmVendorMaster().getVendorId());			
				if(listInvoiceDetails.size()==0){
					List<EFmFmVehicleMasterPO> fixedDistanceTrips=iVehicleCheckInBO.getVendorBasedTripSheet(fromDate, toDate,clientBranchPO.getBranchId(),eFmFmVehicleMasterPO.getEfmFmVendorMaster().getVendorId());								
					if(fixedDistanceTrips.size() >0){
					for(EFmFmVehicleMasterPO tripDetails:fixedDistanceTrips){							
						if(tripDetails.getContractType().equalsIgnoreCase("FDC")){
						// travelledDistance =travelledDistance+tripDetails.getSumTravelledDistance();						
					
						
						//List<EFmFmFixedDistanceContractDetailPO> fixedDistanceDetails=iVehicleCheckInBO.getFixedDistanceDetails(fixedDistanceContractDetailPO);											
						  					  
												  
						 // perKmCharges=fixedDistanceDetails.get(0).getFixedDistanceChargeRate()/fixedDistanceDetails.get(0).getFixedDistanceMonthly();				  
						  List<EFmFmAssignRoutePO> allFixedDistanceVehicleDetails=iVehicleCheckInBO.getVendorBasedVehicleDetails(fromDate, toDate,clientBranchPO.getBranchId(),eFmFmVehicleMasterPO.getEfmFmVendorMaster().getVendorId(), tripDetails.getContractType(), tripDetails.getContractDetailId());
						  if (allFixedDistanceVehicleDetails.size()>0){
							  for (EFmFmAssignRoutePO allVehicleDetails:allFixedDistanceVehicleDetails){ 
								  double totalAmt=0.0,penaltyAmt=0.0,penaltyTotalAmt=0.0,tripBasedAmount=0.0,extraKmCharges=0.0,extraKm=0.0;
								  //tripBasedAmount=allVehicleDetails.getTravelledDistance()*perKmCharges;
									EFmFmFixedDistanceContractDetailPO  fixedDistanceContractDetailPO=new EFmFmFixedDistanceContractDetailPO();
									fixedDistanceContractDetailPO.setDistanceContractId(allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getContractDetailId());
									fixedDistanceContractDetailPO.seteFmFmClientBranchPO(clientBranchPO);								  
								 List<EFmFmVehicleMasterPO> noOfDays=iVehicleCheckInBO.getNoOfWorkingDays(fromDate, toDate,clientBranchPO.getBranchId(),allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId(), allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getContractType().trim(), allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getContractDetailId());
								 List<EFmFmVehicleMasterPO> SumOftotalKm=iVehicleCheckInBO.getSumOfTotalKmByVehicle(fromDate, toDate,clientBranchPO.getBranchId(),allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId(), allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getContractType().trim(), allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getContractDetailId());
								 List<EFmFmFixedDistanceContractDetailPO> fixedDistanceDetails=iVehicleCheckInBO.getFixedDistanceDetails(fixedDistanceContractDetailPO);
								  if(SumOftotalKm.get(0).getSumTravelledDistance() >= fixedDistanceDetails.get(0).getFixedDistanceMonthly()){
									  extraKm=SumOftotalKm.get(0).getSumTravelledDistance()-fixedDistanceDetails.get(0).getFixedDistanceMonthly();
									  extraKmCharges=extraKm*fixedDistanceDetails.get(0).getExtraDistanceChargeRate();
									  totalAmt=fixedDistanceDetails.get(0).getFixedDistanceChargeRate()+extraKmCharges;							  
								  }else if(SumOftotalKm.get(0).getSumTravelledDistance()<fixedDistanceDetails.get(0).getFixedDistanceMonthly() && noOfDays.size()>=fixedDistanceDetails.get(0).getMinimumDays()){
									  totalAmt=fixedDistanceDetails.get(0).getFixedDistanceChargeRate();					  
								  }else if(SumOftotalKm.get(0).getSumTravelledDistance() <fixedDistanceDetails.get(0).getFixedDistanceMonthly() && noOfDays.size()< fixedDistanceDetails.get(0).getMinimumDays()){
									  if(fixedDistanceDetails.get(0).getPenalty().equalsIgnoreCase("Y")){
										  penaltyAmt=fixedDistanceDetails.get(0).getFixedDistanceChargeRate()/fixedDistanceDetails.get(0).getMinimumDays();
										  penaltyTotalAmt=penaltyAmt*(fixedDistanceDetails.get(0).getMinimumDays()-noOfDays.size());
										  totalAmt=fixedDistanceDetails.get(0).getFixedDistanceChargeRate()-penaltyTotalAmt;							  
									  }
								  }
								  List<EFmFmAssignRoutePO> tripBasedFixedDistance=iVehicleCheckInBO.getVehicleBasedTripSheet(fromDate, toDate,clientBranchPO.getBranchId(),allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
								  if(tripBasedFixedDistance.size()>0){
									  for (EFmFmAssignRoutePO  fixedDistanceLst:tripBasedFixedDistance){
										  addInvoiceRecord(tripBasedAmount,fixedDistanceLst,clientBranchPO,fromDate,totalAmt,penaltyTotalAmt,noOfDays.size(),SumOftotalKm.get(0).getSumTravelledDistance(),fixedDistanceDetails.get(0).getFixedDistanceMonthly(),fixedDistanceDetails.get(0).getMinimumDays(),fixedDistanceDetails.get(0).getFixedDistanceChargeRate(),randomNumber,fixedDistanceLst.getTravelledDistance(),extraKm,extraKmCharges);
									  }
								  }								  								   
							  }
							  
						      }
					    	}else if(tripDetails.getContractType().equalsIgnoreCase("TDC")){					    							    		
					    		
							    int noOfDays=0;
					    		List<EFmFmAssignRoutePO> tripBasedDistanceVehicleDetails=iVehicleCheckInBO.getTripBasedVehicleDetails(fromDate, toDate,clientBranchPO.getBranchId(),eFmFmVehicleMasterPO.getEfmFmVendorMaster().getVendorId(), tripDetails.getContractType(), tripDetails.getContractDetailId());
					    		if(tripBasedDistanceVehicleDetails.size()>0){
					    			double tripTotalAmt=0.0,tripBasedAmount=0.0,penaltyTotalAmt=0.0;
					    			EFmFmTripBasedContractDetailPO eFmFmTripBasedContractDetailPO=new EFmFmTripBasedContractDetailPO();
					    			eFmFmTripBasedContractDetailPO.setTripBasedContractId(tripDetails.getContractDetailId());
					    			eFmFmTripBasedContractDetailPO.seteFmFmClientBranchPO(clientBranchPO);
					    		    List<EFmFmTripBasedContractDetailPO> tripDistanceDetails=iVehicleCheckInBO.getTripDistanceDetails(eFmFmTripBasedContractDetailPO);					    			
					    		for (EFmFmAssignRoutePO tripVehicleDetails:tripBasedDistanceVehicleDetails){
					    			   double extraKm=0.0,extraKmCharges=0.0;
					    			   int travelledOdoMeterKm=0;
					    			   travelledOdoMeterKm=Integer.valueOf(tripVehicleDetails.getOdometerEndKm())-Integer.valueOf(tripVehicleDetails.getOdometerStartKm());					    			   
									   if(travelledOdoMeterKm<=tripDistanceDetails.get(0).getFixedDistance()){										   
										   tripBasedAmount=tripDistanceDetails.get(0).getFixedDistanceChargeRate();
									   }else if(travelledOdoMeterKm>tripDistanceDetails.get(0).getFixedDistance()){
										   extraKm=travelledOdoMeterKm-tripDistanceDetails.get(0).getFixedDistance();
										   extraKmCharges=extraKm*tripDistanceDetails.get(0).getExtraDistanceChargeRate();
										   tripBasedAmount=tripDistanceDetails.get(0).getFixedDistanceChargeRate()+extraKmCharges;
									   }									  					   
									   //tripTotalAmt=tripTotalAmt+tripBasedAmount;									   					   
									   addInvoiceRecord(tripBasedAmount,tripVehicleDetails,clientBranchPO,fromDate,tripTotalAmt,penaltyTotalAmt,noOfDays,travelledOdoMeterKm,tripDistanceDetails.get(0).getFixedDistance(),0,tripDistanceDetails.get(0).getFixedDistanceChargeRate(),randomNumber,travelledOdoMeterKm,extraKm,extraKmCharges);
									   
					    		 }					    		      
					    		}					    		
					    		
					    	}
						}
					}
			}
			            List<Map<String, Object>> tripBasedVehicleDetails= new ArrayList<Map<String, Object>>();
						List<EFmFmVendorContractInvoicePO> vendorInvoiceDetails=iVehicleCheckInBO.getInvoiceforVendor(fromDate, toDate, clientBranchPO.getBranchId(),eFmFmVehicleMasterPO.getEfmFmVendorMaster().getVendorId(),"");
						if(vendorInvoiceDetails.size()>0){
							double totalAmount=0.0,penalty=0.0,tripAmount=0.0,totalBaseAmount=0.0,serviceTax=0.0,totalServiceTaxAmount=0.0;
							int noOfvehicle=0;
							for(EFmFmVendorContractInvoicePO vendorInvoiceDetailsList:vendorInvoiceDetails){														
								fixedDistanceVehicleDetails.put("vendorName", vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
								fixedDistanceVehicleDetails.put("invoiceNumber", vendorInvoiceDetailsList.getInvoiceNumber());
								fixedDistanceVehicleDetails.put("invoiceMonthDate", monthDate.format(vendorInvoiceDetailsList.getInvoiveStartDate()));
								fixedDistanceVehicleDetails.put("invoiceCreationDate", formatter.format(vendorInvoiceDetailsList.getCreationTime()));
								penalty=penalty+vendorInvoiceDetailsList.getTotalDeductibles();																			
							if(vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getContractType().equalsIgnoreCase("FDC")){
								Map<String, Object>  fixedDistanceTrips= new HashMap<String, Object>();
								totalAmount=totalAmount+vendorInvoiceDetailsList.getTotalAmountPayable();
								totalBaseAmount=totalBaseAmount+vendorInvoiceDetailsList.getBaseTotal();
								fixedDistanceTrips.put("invoiceType", vendorInvoiceDetailsList.getInvoiceType());
								fixedDistanceTrips.put("vehicleNumber", vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
								fixedDistanceTrips.put("totalKm", vendorInvoiceDetailsList.getTotalDistance());
								fixedDistanceTrips.put("extraKm", vendorInvoiceDetailsList.getTotalExtraDistance());
								fixedDistanceTrips.put("totalWorkingDays", vendorInvoiceDetailsList.getPresentDays());
								fixedDistanceTrips.put("fixedcharges", vendorInvoiceDetailsList.getBaseTotal());
								fixedDistanceTrips.put("penalty", vendorInvoiceDetailsList.getTotalDeductibles());
								fixedDistanceTrips.put("extraKmcharges", vendorInvoiceDetailsList.getExtraDistanceCharge());
								fixedDistanceTrips.put("totalAmount", vendorInvoiceDetailsList.getTotalAmountPayable());								
								invoiceVendorDetails.add(fixedDistanceTrips);
								noOfvehicle++;
							}else if(vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getContractType().equalsIgnoreCase("TDC")){
								noOfvehicle++;
								List<EFmFmVendorContractInvoicePO> vendorFixedInvoiceDetails=iVehicleCheckInBO.getInvoiceTripBasedVehicle(fromDate, toDate, clientBranchPO.getBranchId(),vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
								if(vendorFixedInvoiceDetails.size()>0){
								for(EFmFmVendorContractInvoicePO fixedTripList:vendorFixedInvoiceDetails){
									Map<String, Object>  tripFixedDistanceTrips= new HashMap<String, Object>();
									totalBaseAmount=totalBaseAmount+fixedTripList.getBaseTotal();
									tripAmount=tripAmount+fixedTripList.getTripTotalAmount();
									tripFixedDistanceTrips.put("invoiceType", fixedTripList.getInvoiceType());
									tripFixedDistanceTrips.put("vehicleNumber", fixedTripList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
									tripFixedDistanceTrips.put("tripId", fixedTripList.getEfmFmAssignRoute().getAssignRouteId());
									tripFixedDistanceTrips.put("totalKm", fixedTripList.getTotalDistance());
									tripFixedDistanceTrips.put("extraKm", fixedTripList.getTotalExtraDistance());								
									tripFixedDistanceTrips.put("fixedcharges", fixedTripList.getBaseTotal());
									tripFixedDistanceTrips.put("extraKmcharges", fixedTripList.getExtraDistanceCharge());
									tripFixedDistanceTrips.put("totalAmount", fixedTripList.getTripTotalAmount());								
									tripBasedVehicleDetails.add(tripFixedDistanceTrips);
								 }
								 
								}
								
							}	    
							}					
							serviceTax=(totalAmount+tripAmount)*vendorInvoiceDetails.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getServiceTax()/100;
							totalServiceTaxAmount=(totalAmount+tripAmount)+serviceTax;							
							fixedDistanceVehicleDetails.put("totalAmount",(double) Math.round(totalBaseAmount * 100) / 100);
							fixedDistanceVehicleDetails.put("penalty", (double) Math.round(penalty * 100) / 100);
							fixedDistanceVehicleDetails.put("totalPayableAmount",(double) Math.round((totalAmount+tripAmount) * 100) / 100);
							fixedDistanceVehicleDetails.put("serviceTax",vendorInvoiceDetails.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getServiceTax());
							fixedDistanceVehicleDetails.put("serviceTaxAmount", (double) Math.round(serviceTax * 100) / 100);							
							fixedDistanceVehicleDetails.put("total", (double) Math.round(totalServiceTaxAmount * 100) / 100);							
							fixedDistanceVehicleDetails.put("noOfvehicle", noOfvehicle);
							fixedDistanceVehicleDetails.put("fixedDistanceBased", invoiceVendorDetails);
							fixedDistanceVehicleDetails.put("tripBasedFixedDetails", tripBasedVehicleDetails);
							
						}
										
					/*vendorDetailsList.put("vehicleDetails", invoiceVendorDetails);
					invoiceDetails.put("VendorDetails", vendorDetailsList);*/
			      				
				break;
			case "VEHICLEBASED":
				Date vehicleFromDate =(Date) monthDate.parse(eFmFmVehicleMasterPO.getInvoiceDate());			
				Date vehicleToDate = (Date) monthDate.parse(eFmFmVehicleMasterPO.getInvoiceDate());
				List<Map<String, Object>> tripByVehicle= new ArrayList<Map<String, Object>>();
				List<EFmFmVendorContractInvoicePO> invoiceByVehicleFixedDistance=iVehicleCheckInBO.getInvoiceByVehicleFixedDistance(vehicleFromDate, vehicleToDate, clientBranchPO.getBranchId(),eFmFmVehicleMasterPO.getEfmFmVendorMaster().getVendorId(),eFmFmVehicleMasterPO.getVehicleId());				
				if(invoiceByVehicleFixedDistance.size()>0){
					for(EFmFmVendorContractInvoicePO vendorInvoiceDetailsList:invoiceByVehicleFixedDistance){																								
					if(vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getContractType().equalsIgnoreCase("FDC")){
						Map<String, Object>  fixedDistanceTrips= new HashMap<String, Object>();						
						fixedDistanceTrips.put("invoiceType", vendorInvoiceDetailsList.getInvoiceType());
						fixedDistanceTrips.put("vehicleNumber", vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
						fixedDistanceTrips.put("totalKm", vendorInvoiceDetailsList.getTotalDistance());
						fixedDistanceTrips.put("extraKm", vendorInvoiceDetailsList.getTotalExtraDistance());
						fixedDistanceTrips.put("totalWorkingDays", vendorInvoiceDetailsList.getPresentDays());
						fixedDistanceTrips.put("fixedcharges", vendorInvoiceDetailsList.getBaseTotal());
						fixedDistanceTrips.put("penalty", vendorInvoiceDetailsList.getTotalDeductibles());
						fixedDistanceTrips.put("extraKmcharges", vendorInvoiceDetailsList.getExtraDistanceCharge());
						fixedDistanceTrips.put("totalAmount", vendorInvoiceDetailsList.getTotalAmountPayable());								
						invoiceVendorDetails.add(fixedDistanceTrips);						
					}else if(vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getContractType().equalsIgnoreCase("TDC")){						
						List<EFmFmVendorContractInvoicePO> vendorFixedInvoiceDetails=iVehicleCheckInBO.getInvoiceTripBasedVehicle(vehicleFromDate, vehicleToDate, clientBranchPO.getBranchId(),vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
						if(vendorFixedInvoiceDetails.size()>0){
						for(EFmFmVendorContractInvoicePO fixedTripList:vendorFixedInvoiceDetails){
							Map<String, Object>  tripFixedDistanceTrips= new HashMap<String, Object>();						
							tripFixedDistanceTrips.put("invoiceType", fixedTripList.getInvoiceType());
							tripFixedDistanceTrips.put("vehicleNumber", fixedTripList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
							tripFixedDistanceTrips.put("tripId", fixedTripList.getEfmFmAssignRoute().getAssignRouteId());
							tripFixedDistanceTrips.put("totalKm", fixedTripList.getTotalDistance());
							tripFixedDistanceTrips.put("extraKm", fixedTripList.getTotalExtraDistance());								
							tripFixedDistanceTrips.put("fixedcharges", fixedTripList.getBaseTotal());
							tripFixedDistanceTrips.put("extraKmcharges", fixedTripList.getExtraDistanceCharge());
							tripFixedDistanceTrips.put("totalAmount", fixedTripList.getTripTotalAmount());								
							tripByVehicle.add(tripFixedDistanceTrips);
						 }							 
						}							
					  }	    
					}					
					fixedDistanceVehicleDetails.put("fixedDistanceBased", invoiceVendorDetails);
					fixedDistanceVehicleDetails.put("tripBasedFixedDetails", tripByVehicle);
					
				}
			
				
				break;
				case "INVOICEDETAILS":	
					List<Map<String, Object>> tripInvoiceBasedVehicleDetails= new ArrayList<Map<String, Object>>();
					List<EFmFmVendorContractInvoicePO> vendorSummaryInvoiceDetails=iVehicleCheckInBO.getInvoiceDetails(clientBranchPO.getBranchId(),eFmFmVehicleMasterPO.getInvoiceNumber());
					if(vendorSummaryInvoiceDetails.size()>0){
						double totalAmount=0.0,penalty=0.0,tripAmount=0.0,totalBaseAmount=0.0,serviceTax=0.0,totalServiceTaxAmount=0.0;
						int noOfvehicle=0;
						for(EFmFmVendorContractInvoicePO vendorInvoiceDetailsList:vendorSummaryInvoiceDetails){														
							fixedDistanceVehicleDetails.put("vendorName", vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorName());
							fixedDistanceVehicleDetails.put("invoiceNumber", vendorInvoiceDetailsList.getInvoiceNumber());
							fixedDistanceVehicleDetails.put("invoiceMonthDate", monthDate.format(vendorInvoiceDetailsList.getInvoiveStartDate()));
							fixedDistanceVehicleDetails.put("invoiceCreationDate", formatter.format(vendorInvoiceDetailsList.getCreationTime()));
							penalty=penalty+vendorInvoiceDetailsList.getTotalDeductibles();																			
						if(vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getContractType().equalsIgnoreCase("FDC")){
							Map<String, Object>  fixedDistanceTrips= new HashMap<String, Object>();
							totalAmount=totalAmount+vendorInvoiceDetailsList.getTotalAmountPayable();
							totalBaseAmount=totalBaseAmount+vendorInvoiceDetailsList.getBaseTotal();
							fixedDistanceTrips.put("invoiceType", vendorInvoiceDetailsList.getInvoiceType());
							fixedDistanceTrips.put("vehicleNumber", vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
							fixedDistanceTrips.put("totalKm", vendorInvoiceDetailsList.getTotalDistance());
							fixedDistanceTrips.put("extraKm", vendorInvoiceDetailsList.getTotalExtraDistance());
							fixedDistanceTrips.put("totalWorkingDays", vendorInvoiceDetailsList.getPresentDays());
							fixedDistanceTrips.put("fixedcharges", vendorInvoiceDetailsList.getBaseTotal());
							fixedDistanceTrips.put("penalty", vendorInvoiceDetailsList.getTotalDeductibles());
							fixedDistanceTrips.put("extraKmcharges", vendorInvoiceDetailsList.getExtraDistanceCharge());
							fixedDistanceTrips.put("totalAmount", vendorInvoiceDetailsList.getTotalAmountPayable());								
							invoiceVendorDetails.add(fixedDistanceTrips);
							noOfvehicle++;
						}else if(vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getContractType().equalsIgnoreCase("TDC")){
							noOfvehicle++;
							List<EFmFmVendorContractInvoicePO> vendorFixedInvoiceDetails=iVehicleCheckInBO.getInvoiceTripBasedVehicle(vendorSummaryInvoiceDetails.get(0).getInvoiveStartDate(), vendorSummaryInvoiceDetails.get(0).getInvoiveStartDate(), clientBranchPO.getBranchId(),vendorInvoiceDetailsList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
							if(vendorFixedInvoiceDetails.size()>0){
							for(EFmFmVendorContractInvoicePO fixedTripList:vendorFixedInvoiceDetails){
								Map<String, Object>  tripFixedDistanceTrips= new HashMap<String, Object>();
								totalBaseAmount=totalBaseAmount+fixedTripList.getBaseTotal();
								tripAmount=tripAmount+fixedTripList.getTripTotalAmount();
								tripFixedDistanceTrips.put("invoiceType", fixedTripList.getInvoiceType());
								tripFixedDistanceTrips.put("vehicleNumber", fixedTripList.getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleNumber());
								tripFixedDistanceTrips.put("tripId", fixedTripList.getEfmFmAssignRoute().getAssignRouteId());
								tripFixedDistanceTrips.put("totalKm", fixedTripList.getTotalDistance());
								tripFixedDistanceTrips.put("extraKm", fixedTripList.getTotalExtraDistance());								
								tripFixedDistanceTrips.put("fixedcharges", fixedTripList.getBaseTotal());
								tripFixedDistanceTrips.put("extraKmcharges", fixedTripList.getExtraDistanceCharge());
								tripFixedDistanceTrips.put("totalAmount", fixedTripList.getTripTotalAmount());								
								tripInvoiceBasedVehicleDetails.add(tripFixedDistanceTrips);
							 }							 
							}							
						  }	    
						}					
						serviceTax=(totalAmount+tripAmount)*vendorSummaryInvoiceDetails.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getServiceTax()/100;
						totalServiceTaxAmount=(totalAmount+tripAmount)+serviceTax;							
						fixedDistanceVehicleDetails.put("totalAmount",(double) Math.round(totalBaseAmount * 100) / 100);
						fixedDistanceVehicleDetails.put("penalty", (double) Math.round(penalty * 100) / 100);
						fixedDistanceVehicleDetails.put("totalPayableAmount",(double) Math.round((totalAmount+tripAmount) * 100) / 100);
						fixedDistanceVehicleDetails.put("serviceTax",vendorSummaryInvoiceDetails.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getServiceTax());
						fixedDistanceVehicleDetails.put("serviceTaxAmount", (double) Math.round(serviceTax * 100) / 100);							
						fixedDistanceVehicleDetails.put("total", (double) Math.round(totalServiceTaxAmount * 100) / 100);							
						fixedDistanceVehicleDetails.put("noOfvehicle", noOfvehicle);
						fixedDistanceVehicleDetails.put("fixedDistanceBased", invoiceVendorDetails);
						fixedDistanceVehicleDetails.put("tripBasedFixedDetails", tripInvoiceBasedVehicleDetails);
						
					}
				break;
			}
		return Response.ok(fixedDistanceVehicleDetails, MediaType.APPLICATION_JSON).build();
	}
	

	
	@POST
	@Path("/listOfInvoiceDetails")
	public Response listOfInvoiceDetails(EFmFmClientBranchPO  eFmFmClientBranchPO) throws ParseException{		  
		  IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		  List<EFmFmVendorContractInvoicePO> listInvoiceDetails=iVehicleCheckInBO.getListOfInvoiceNumbers(eFmFmClientBranchPO.getBranchId());
			List<Map<String, Object>> invoiceList = new ArrayList<Map<String, Object>>();	
			 if(listInvoiceDetails.size()>0){	
					for (EFmFmVendorContractInvoicePO invoiceDetails : listInvoiceDetails) {						
							Map<String, Object>  vehicleList = new HashMap<String, Object>();					
							vehicleList.put("invoiceNumber", invoiceDetails.getInvoiceNumber());							
							invoiceList.add(vehicleList);						
					}
		        }
			return Response.ok(invoiceList, MediaType.APPLICATION_JSON).build();
	}
	@POST
	@Path("/allActiveVehicle")
	public Response allActiveVehicle(EFmFmVendorMasterPO  eFmFmVendorMasterPO){					
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		EFmFmVehicleMasterPO eFmFmVehicleMasterPO=new EFmFmVehicleMasterPO();
		eFmFmVehicleMasterPO.setEfmFmVendorMaster(eFmFmVendorMasterPO);
		List<EFmFmVehicleMasterPO> allActiveVehicle=iVehicleCheckInBO.getAllVehicleDetails(eFmFmVehicleMasterPO);
		List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
		 if(allActiveVehicle.size()>0){	
				for (EFmFmVehicleMasterPO vehicleDetails : allActiveVehicle) {
					//if(vehicleDetails.getStatus().equalsIgnoreCase("A")){
						Map<String, Object>  vehicleList = new HashMap<String, Object>();					
						vehicleList.put("vehicleNumber", vehicleDetails.getVehicleNumber());					
						vehicleList.put("vehicleId", vehicleDetails.getVehicleId());
						requests.add(vehicleList);
					//}
				}
	        }
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}	
	
	@POST
	@Path("/allActiveVendor")
	public Response allActiveVendor(EFmFmClientBranchPO  eFmFmClientBranchPO){					
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");
		List<EFmFmVendorMasterPO> allActiveVendor=approvalBO.getAllApprovedVendors(eFmFmClientBranchPO.getBranchId());		
		List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();	
		 if(allActiveVendor.size()>0){	
				for (EFmFmVendorMasterPO vendorDetails : allActiveVendor) {
					Map<String, Object>  vendorList = new HashMap<String, Object>();					
					vendorList.put("name", vendorDetails.getVendorName());
					vendorList.put("vendorId", vendorDetails.getVendorId());
					requests.add(vendorList);			
				}
	        }
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 *Adding Record into Invoice Table 
	 */
	private void addInvoiceRecord(double tripBasedAmount,EFmFmAssignRoutePO allVehicleDetails, EFmFmClientBranchPO clientBranchPO, Date fromDate, double totalAmt, double penaltyTotalAmt, int noOfDays, double travelledDistance, double fixedDistance, int workingDays, double fixedDistanceChargeRate,int invoiceNumber,double tripTravelledDistance, double extraKm, double extraKmCharges) {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		   EFmFmVendorContractInvoicePO  eFmFmVendorContractInvoicePO=new EFmFmVendorContractInvoicePO();
		   EFmFmVehicleMasterPO  eFmFmVehicleMasterPO=new EFmFmVehicleMasterPO();
		   EFmFmVendorMasterPO  eFmFmVendorMasterPO=new EFmFmVendorMasterPO();
		   eFmFmVendorContractInvoicePO.seteFmFmClientBranchPO(clientBranchPO);
		   eFmFmVehicleMasterPO.setVehicleId(allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
		   eFmFmVendorMasterPO.setVendorId(allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getEfmFmVendorMaster().getVendorId());
		   eFmFmVendorContractInvoicePO.setEfmFmAssignRoute(allVehicleDetails);
		   eFmFmVendorContractInvoicePO.setTotalAmountPayable((double) Math.round(totalAmt * 100) / 100);		   
		   eFmFmVendorContractInvoicePO.setCreationTime(new Date());
		   eFmFmVendorContractInvoicePO.setInvoiceGenerationDate(new Date());
		   eFmFmVendorContractInvoicePO.setInvoiveStartDate(fromDate);
		   eFmFmVendorContractInvoicePO.setInvoiceEndDate(fromDate);
		   eFmFmVendorContractInvoicePO.setTripTotalAmount((double) Math.round(tripBasedAmount * 100) / 100);
		   eFmFmVendorContractInvoicePO.setInvoiceType(allVehicleDetails.getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().geteFmFmVendorContractTypeMaster().getContractDescription());
		   eFmFmVendorContractInvoicePO.setInvoiceStatus("A");
		   eFmFmVendorContractInvoicePO.setTotalDeductibles((double) Math.round(penaltyTotalAmt * 100) / 100);		   
		   eFmFmVendorContractInvoicePO.setPresentDays(noOfDays);
		   eFmFmVendorContractInvoicePO.setTotalDistance(travelledDistance);
		   eFmFmVendorContractInvoicePO.setBaseDistance(fixedDistance);
		   eFmFmVendorContractInvoicePO.setWorkingDays(workingDays);
		   eFmFmVendorContractInvoicePO.setBaseTotal(fixedDistanceChargeRate);
		   eFmFmVendorContractInvoicePO.setUpdatedTime(new Date());
		   eFmFmVendorContractInvoicePO.setInvoiceNumber(invoiceNumber);
		   eFmFmVendorContractInvoicePO.setTravelledDistance(tripTravelledDistance);
		   eFmFmVendorContractInvoicePO.setExtraDistanceCharge(extraKmCharges);
		   eFmFmVendorContractInvoicePO.setTotalExtraDistance(extraKm);
		   iVehicleCheckInBO.save(eFmFmVendorContractInvoicePO);
		
	}	 
	}