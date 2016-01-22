package com.newtglobal.eFmFmFleet.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/request")
public class CabAllocationService {

	private static Log log = LogFactory.getLog(CabAllocationService.class);	

	
		@POST
		@Path("/caballocation")
		@Consumes("application/json")
		@Produces("application/json")
		public void getAllRequestOfParticularShiftAndRouteForAllocation(EFmFmEmployeeTravelRequestPO travelRequestPO) throws ParseException{
			ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
			IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");			
			IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		    DateFormat shiftFormate = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
			String shiftTime=travelRequestPO.getTime();
			Date shift  = (Date) shiftFormate.parse(shiftTime);				 
			java.sql.Time dateShiftTime = new java.sql.Time(shift.getTime());
			List<EFmFmEmployeeTravelRequestPO> travelDetails=null;
			if(travelRequestPO.getZoneId()==0){
				travelDetails=iCabRequestBO.assignCabRequestToParticularShiftEmployees(travelRequestPO.getBranchId(), travelRequestPO.getTripType(),dateShiftTime);		
			}
			else{
				travelDetails=iCabRequestBO.assignCabRequestToParticularShiftOrRouteEmployees(travelRequestPO.getBranchId(),travelRequestPO.getTripType(),dateShiftTime, travelRequestPO.getZoneId());		
			}
outer:		for(EFmFmEmployeeTravelRequestPO cabRequests:travelDetails){
				  if(cabRequests.getShiftTime().getHours()==0){
					  continue outer;
				  }
             if(dateformate.format(new Date()).equalsIgnoreCase(dateformate.format(cabRequests.getRequestDate()))){
		        List<EFmFmEmployeeRequestMasterPO>  employeeRequestMasterPO = iCabRequestBO.getParticularEmployeeMasterRequestDetails(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), cabRequests.geteFmFmEmployeeRequestMaster().getTripId());
		        List<EFmFmAssignRoutePO> assignRoutePO;
		        List<EFmFmEmployeeTravelRequestPO> particularRouteEmplyees  = iCabRequestBO.getAllParticularRouteRequest(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), cabRequests.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId(),cabRequests.getShiftTime());		        
		        List<EFmFmVehicleCheckInPO> allCheckInVehicles;
		       log.info("Route Emp Size:-"+particularRouteEmplyees.size());
		        if(particularRouteEmplyees.size() <= 5){
		            allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLessCapacity(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),10);
		        }
		        else{
		            allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLargeCapacity(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),10);
		        }          
	            if(!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0){
		        EFmFmVehicleMasterPO updateVehicleStatus = iCabRequestBO.getVehicleDetail(allCheckInVehicles.get(0).getEfmFmVehicleMaster().getVehicleId());
		        updateVehicleStatus.setStatus("A");
		        updateVehicleStatus.setAvailableSeat(updateVehicleStatus.getCapacity()-1);
		        iVehicleCheckInBO.update(updateVehicleStatus);		        
		        EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(allCheckInVehicles.get(0).getEfmFmDriverMaster().getDriverId());
                particularDriverDetails.setStatus("A");
                approvalBO.update(particularDriverDetails);
                List<EFmFmDeviceMasterPO> deviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(allCheckInVehicles.get(0).geteFmFmDeviceMaster().getDeviceId(),cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
                deviceDetails.get(0).setStatus("Y");
                iVehicleCheckInBO.update(deviceDetails.get(0));
                 }
		        assignRoutePO = iCabRequestBO.getHalfCompletedAssignRoute(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), cabRequests.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId(), cabRequests.geteFmFmEmployeeRequestMaster().getTripType(), cabRequests.getShiftTime());
		        EFmFmEmployeeTripDetailPO employeeTripDetailPO = new EFmFmEmployeeTripDetailPO();
		        EFmFmAssignRoutePO eAssignRoutePO = new EFmFmAssignRoutePO();
		        
		        if(assignRoutePO.isEmpty() || assignRoutePO.size() == 0 || !((EFmFmAssignRoutePO)assignRoutePO.get(0)).geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName().equalsIgnoreCase(cabRequests.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName()))
		        {
		            if(!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0)
		            {
		                EFmFmAssignRoutePO assignRoute = new EFmFmAssignRoutePO();
		                EFmFmVehicleCheckInPO vehicleCheckInPO = new EFmFmVehicleCheckInPO();
		                vehicleCheckInPO.setCheckInId(((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1)).getCheckInId());
		                assignRoute.setEfmFmVehicleCheckIn(vehicleCheckInPO);
		                EFmFmRouteAreaMappingPO routeAreaMapping = new EFmFmRouteAreaMappingPO();
		                routeAreaMapping.setRouteAreaId(cabRequests.geteFmFmRouteAreaMapping().getRouteAreaId());
		                assignRoute.seteFmFmRouteAreaMapping(routeAreaMapping);
		                assignRoute.setEscortRequredFlag("N");
		                assignRoute.setAllocationMsg("N");
		                assignRoute.setShiftTime(cabRequests.getShiftTime());
		                assignRoute.setTripStatus("allocated");
		                assignRoute.setTripAssignDate(new Date());
		                assignRoute.setVehicleStatus("A");
		                assignRoute.setBucketStatus("N");
		                EFmFmClientBranchPO eFmFmClientBranchPO = new EFmFmClientBranchPO();
		                eFmFmClientBranchPO.setBranchId(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		                assignRoute.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		                assignRoute.setTripType(((EFmFmEmployeeRequestMasterPO)employeeRequestMasterPO.get(0)).getTripType());
		                ((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1)).setStatus("N");
		                if(cabRequests.getEfmFmUserMaster().geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("Always") || cabRequests.getEfmFmUserMaster().geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("femalepresent") && cabRequests.getEfmFmUserMaster().getGender().equalsIgnoreCase("Female"))
		                {
		                	List <EFmFmEscortCheckInPO> escortList = iCabRequestBO.getAllCheckedInEscort(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		                    if(!escortList.isEmpty() || escortList.size() != 0)
		                    {
		                        EFmFmEscortCheckInPO checkInEscort = new EFmFmEscortCheckInPO();
		                        checkInEscort.setEscortCheckInId(((EFmFmEscortCheckInPO)escortList.get(0)).getEscortCheckInId());
		                        assignRoute.seteFmFmEscortCheckIn(checkInEscort);
		                        ((EFmFmEscortCheckInPO)escortList.get(0)).setStatus("N");		                        
		                        iVehicleCheckInBO.update((EFmFmEscortCheckInPO)escortList.get(0));
		                    }
		                    assignRoute.setEscortRequredFlag("Y");
		                }
		                iVehicleCheckInBO.update((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1));
		                iCabRequestBO.update(assignRoute);
		                
		                cabRequests.setReadFlg("R");
		                iCabRequestBO.update(cabRequests);
		                assignRoutePO = iCabRequestBO.getHalfCompletedAssignRouteFromCheckInId(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), cabRequests.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId(), ((EFmFmEmployeeRequestMasterPO)employeeRequestMasterPO.get(0)).getTripType(), cabRequests.getShiftTime(), ((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1)).getCheckInId());
		                EFmFmVehicleMasterPO vehicleMaster = iCabRequestBO.getVehicleDetail(((EFmFmAssignRoutePO)assignRoutePO.get(0)).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
		                if(((EFmFmAssignRoutePO)assignRoutePO.get(0)).geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("Always") || ((EFmFmAssignRoutePO)assignRoutePO.get(0)).geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("femalepresent") && cabRequests.getEfmFmUserMaster().getGender().equalsIgnoreCase("Female"))
		                    vehicleMaster.setAvailableSeat(vehicleMaster.getAvailableSeat() - 2);
		                else
		                    vehicleMaster.setAvailableSeat(vehicleMaster.getAvailableSeat() - 1);
		                vehicleMaster.setStatus("allocated");
		                iVehicleCheckInBO.update(vehicleMaster);
		                EFmFmDriverMasterPO particularDriverDetails=approvalBO.getParticularDriverDetail(assignRoutePO.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
		                particularDriverDetails.setStatus("allocated");
		                approvalBO.update(particularDriverDetails);

		                List<EFmFmDeviceMasterPO>deviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(assignRoutePO.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), travelRequestPO.getBranchId());
		                deviceDetails.get(0).setStatus("allocated");
		                iVehicleCheckInBO.update(deviceDetails.get(0));
		                eAssignRoutePO.setAssignRouteId(((EFmFmAssignRoutePO)assignRoutePO.get(0)).getAssignRouteId());
		                if(((EFmFmAssignRoutePO)assignRoutePO.get(0)).getTripType().equalsIgnoreCase("DROP")){
		                    employeeTripDetailPO.setTenMinuteMessageStatus("Y");
		                    employeeTripDetailPO.setTwoMinuteMessageStatus("Y");
		                    employeeTripDetailPO.setReachedFlg("Y");
		                    employeeTripDetailPO.setCabDelayMsgStatus("Y");
		                    
		                }
		                else{
		                    employeeTripDetailPO.setTenMinuteMessageStatus("N");
		                    employeeTripDetailPO.setTwoMinuteMessageStatus("N");
		                    employeeTripDetailPO.setReachedFlg("N");
		                    employeeTripDetailPO.setCabDelayMsgStatus("N");

		                }
		                employeeTripDetailPO.setActualTime(new Date());
		                employeeTripDetailPO.setGoogleEta(0);
		                employeeTripDetailPO.setBoardedFlg("N");
		                employeeTripDetailPO.seteFmFmEmployeeTravelRequest(cabRequests);
		                employeeTripDetailPO.setEfmFmAssignRoute(eAssignRoutePO);
		                employeeTripDetailPO.setCurrenETA("0");
		                employeeTripDetailPO.setEmployeeStatus("allocated");
		                iCabRequestBO.update(employeeTripDetailPO);
		            }
		        } else
		        {
		            EFmFmVehicleMasterPO vehicleMaster = iCabRequestBO.getVehicleDetail(((EFmFmAssignRoutePO)assignRoutePO.get(0)).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
		            int availableCapacity = 0;
		            availableCapacity = vehicleMaster.getAvailableSeat() - 1;
		            if(vehicleMaster.getAvailableSeat() > 1 && ((EFmFmAssignRoutePO)assignRoutePO.get(0)).geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("femalepresent") && cabRequests.getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") && ((EFmFmAssignRoutePO)assignRoutePO.get(0)).getEscortRequredFlag().equalsIgnoreCase("N"))
		            {
		            	List <EFmFmEscortCheckInPO> escortList = iCabRequestBO.getAllCheckedInEscort(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		                if(!escortList.isEmpty() || escortList.size() != 0)
		                {
		                    EFmFmEscortCheckInPO checkInEscort = new EFmFmEscortCheckInPO();
		                    checkInEscort.setEscortCheckInId(((EFmFmEscortCheckInPO)escortList.get(0)).getEscortCheckInId());
		                    ((EFmFmAssignRoutePO)assignRoutePO.get(0)).seteFmFmEscortCheckIn(checkInEscort);
		                    ((EFmFmEscortCheckInPO)escortList.get(0)).setStatus("N");
		                    iVehicleCheckInBO.update((EFmFmEscortCheckInPO)escortList.get(0));
		                }
		                ((EFmFmAssignRoutePO)assignRoutePO.get(0)).setEscortRequredFlag("Y");
		                ((EFmFmAssignRoutePO)assignRoutePO.get(0)).setVehicleStatus("F");
		                iCabRequestBO.update((EFmFmAssignRoutePO)assignRoutePO.get(0));
		                availableCapacity = vehicleMaster.getAvailableSeat() - 2;
		            }
		            if(vehicleMaster.getAvailableSeat() == 1 && ((EFmFmAssignRoutePO)assignRoutePO.get(0)).geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("femalepresent") && cabRequests.getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") && ((EFmFmAssignRoutePO)assignRoutePO.get(0)).getEscortRequredFlag().equalsIgnoreCase("N"))
		            	continue outer;
		            if(cabRequests.getEfmFmUserMaster().geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("firstlastfemale") && cabRequests.geteFmFmEmployeeRequestMaster().getTripType().equalsIgnoreCase("PICKUP") && vehicleMaster.getAvailableSeat() == 1)
		            {
		                boolean requestAddingFlg = true;
						for(EFmFmAssignRoutePO assignRouteDetails:assignRoutePO){
		                    EFmFmEmployeeTripDetailPO employeeTripDetail = new EFmFmEmployeeTripDetailPO();
		                    eAssignRoutePO.setAssignRouteId(assignRouteDetails.getAssignRouteId());
		                    if(assignRouteDetails.getTripType().equalsIgnoreCase("DROP")){
		                    	employeeTripDetail.setTenMinuteMessageStatus("Y");
		                    	employeeTripDetail.setTwoMinuteMessageStatus("Y");
		                    	employeeTripDetail.setReachedFlg("Y");
		                    	employeeTripDetail.setCabDelayMsgStatus("Y");

		                        
		                    }
		                    else{
		                    	employeeTripDetail.setTenMinuteMessageStatus("N");
		                        employeeTripDetail.setTwoMinuteMessageStatus("N");
		                        employeeTripDetail.setReachedFlg("N");
		                        employeeTripDetail.setCabDelayMsgStatus("N");

		                    }
		                    
		                    employeeTripDetail.setActualTime(new Date());
		                    employeeTripDetail.setGoogleEta(0);
		                    employeeTripDetail.setBoardedFlg("N");
		                    employeeTripDetail.setCurrenETA("0");
		                    employeeTripDetail.seteFmFmEmployeeTravelRequest(cabRequests);
		                    employeeTripDetail.setEfmFmAssignRoute(eAssignRoutePO);
		                    employeeTripDetail.setEmployeeStatus("allocated");
		                    iCabRequestBO.save(employeeTripDetail);
		                    List<EFmFmEmployeeTripDetailPO>  allEmployees = iCabRequestBO.getParticularTripAllEmployees(((EFmFmAssignRoutePO)assignRoutePO.get(0)).getAssignRouteId());
		                    if(((EFmFmEmployeeTripDetailPO)allEmployees.get(0)).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") && (((EFmFmEmployeeTripDetailPO)allEmployees.get(0)).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() >= 20 || ((EFmFmEmployeeTripDetailPO)allEmployees.get(0)).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() == 0 || ((EFmFmEmployeeTripDetailPO)allEmployees.get(0)).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() <= 7))
		                    {
		                        List<EFmFmEmployeeTripDetailPO> tripId = iCabRequestBO.getRequestStatusFromBranchIdAndRequestId(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), cabRequests.getRequestId());
		                        iCabRequestBO.deleteParticularRequestFromEmployeeTripDetail(((EFmFmEmployeeTripDetailPO)tripId.get(0)).getEmpTripId());
		                    } else
		                    {
		                        ((EFmFmAssignRoutePO)assignRoutePO.get(0)).setVehicleStatus("F");
		                        iCabRequestBO.update((EFmFmAssignRoutePO)assignRoutePO.get(0));
		                        availableCapacity = vehicleMaster.getAvailableSeat() - 1;
		                        vehicleMaster.setAvailableSeat(availableCapacity);
		                        iVehicleCheckInBO.update(vehicleMaster);
		                        cabRequests.setReadFlg("R");
		                        iCabRequestBO.update(cabRequests);
		                        requestAddingFlg = false;
		                        continue outer;
		                    }
		                }
	
		                if(requestAddingFlg)
		                {
		                    if(particularRouteEmplyees.size() <= 6)
		                        allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLessCapacity(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), 10);
		                    else
		                        allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLessCapacity(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), 20);
		                    if(!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0)
		                    {
		                        EFmFmAssignRoutePO assignRoute1 = new EFmFmAssignRoutePO();
		                        EFmFmVehicleCheckInPO vehicleCheckInPO = new EFmFmVehicleCheckInPO();
		                        vehicleCheckInPO.setCheckInId(((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1)).getCheckInId());
		                        assignRoute1.setEfmFmVehicleCheckIn(vehicleCheckInPO);
		                        EFmFmRouteAreaMappingPO routeAreaMapping = new EFmFmRouteAreaMappingPO();
		                        routeAreaMapping.setRouteAreaId(cabRequests.geteFmFmRouteAreaMapping().getRouteAreaId());
		                        assignRoute1.seteFmFmRouteAreaMapping(routeAreaMapping);
		                        assignRoute1.setEscortRequredFlag("N");
		                        assignRoute1.setAllocationMsg("N");
		                        assignRoute1.setTripStatus("allocated");
		                        assignRoute1.setTripAssignDate(new Date());
		                        assignRoute1.setShiftTime(cabRequests.getShiftTime());
		                        assignRoute1.setVehicleStatus("A");
		                        assignRoute1.setBucketStatus("N");
	
		                        EFmFmClientBranchPO eFmFmClientBranchPO = new EFmFmClientBranchPO();
		                        eFmFmClientBranchPO.setBranchId(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		                        assignRoute1.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		                        assignRoute1.setTripType(((EFmFmEmployeeRequestMasterPO)employeeRequestMasterPO.get(0)).getTripType());
		                        ((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1)).setStatus("N");
		                        if(cabRequests.getEfmFmUserMaster().geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("Always") || cabRequests.getEfmFmUserMaster().geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("femalepresent") && cabRequests.getEfmFmUserMaster().getGender().equalsIgnoreCase("Female"))
		                        {
		                            List <EFmFmEscortCheckInPO> escortList = iCabRequestBO.getAllCheckedInEscort(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		                            if(!escortList.isEmpty() || escortList.size() != 0)
		                            {
		                                EFmFmEscortCheckInPO checkInEscort = new EFmFmEscortCheckInPO();
		                                checkInEscort.setEscortCheckInId(((EFmFmEscortCheckInPO)escortList.get(0)).getEscortCheckInId());
		                                assignRoute1.seteFmFmEscortCheckIn(checkInEscort);
		                                ((EFmFmEscortCheckInPO)escortList.get(0)).setStatus("N");
		                                iVehicleCheckInBO.update((EFmFmEscortCheckInPO)escortList.get(0));
		                            }
		                            assignRoute1.setEscortRequredFlag("Y");
		                        }
		                        
		                        iCabRequestBO.update(assignRoute1);
		                        iVehicleCheckInBO.update((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1));
		                        cabRequests.setReadFlg("R");
		                        iCabRequestBO.update(cabRequests);
		                        List<EFmFmAssignRoutePO> assignRoutePO1 = iCabRequestBO.getHalfCompletedAssignRouteFromCheckInId(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), cabRequests.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId(), ((EFmFmEmployeeRequestMasterPO)employeeRequestMasterPO.get(0)).getTripType(), cabRequests.getShiftTime(), ((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1)).getCheckInId());
		                        EFmFmVehicleMasterPO vehicleMaster1 = iCabRequestBO.getVehicleDetail(((EFmFmAssignRoutePO)assignRoutePO1.get(0)).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
		                        vehicleMaster1.setAvailableSeat(vehicleMaster1.getAvailableSeat() - 1);
		                        vehicleMaster1.setStatus("allocated");
		                        iVehicleCheckInBO.update(vehicleMaster1);
				                EFmFmDriverMasterPO particularDriverDetails1=approvalBO.getParticularDriverDetail(assignRoutePO1.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				                particularDriverDetails1.setStatus("allocated");
				                approvalBO.update(particularDriverDetails1);
				                List<EFmFmDeviceMasterPO>deviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(assignRoutePO1.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), travelRequestPO.getBranchId());
				                deviceDetails.get(0).setStatus("allocated");
				                iVehicleCheckInBO.update(deviceDetails.get(0));
		                        EFmFmEmployeeTripDetailPO employeeTripDetailPO1 = new EFmFmEmployeeTripDetailPO();
		                        eAssignRoutePO.setAssignRouteId(((EFmFmAssignRoutePO)assignRoutePO1.get(0)).getAssignRouteId());
		                        if(((EFmFmAssignRoutePO)assignRoutePO1.get(0)).getTripType().equalsIgnoreCase("DROP")){
		                        	employeeTripDetailPO1.setTenMinuteMessageStatus("Y");
		                            employeeTripDetailPO1.setTwoMinuteMessageStatus("Y");
		                            employeeTripDetailPO1.setReachedFlg("Y");
		                            employeeTripDetailPO1.setCabDelayMsgStatus("Y");
		                        }
		                        else{
		                        	employeeTripDetailPO1.setTenMinuteMessageStatus("N");
		                            employeeTripDetailPO1.setTwoMinuteMessageStatus("N");
		                            employeeTripDetailPO1.setReachedFlg("N");
		                            employeeTripDetailPO1.setCabDelayMsgStatus("N");

		                        }
		                        employeeTripDetailPO1.setActualTime(new Date());
		                        employeeTripDetailPO1.setGoogleEta(0);
		                        employeeTripDetailPO1.setBoardedFlg("N");
		                        employeeTripDetailPO1.seteFmFmEmployeeTravelRequest(cabRequests);
		                        employeeTripDetailPO1.setEfmFmAssignRoute(eAssignRoutePO);
		                        employeeTripDetailPO1.setEmployeeStatus("allocated");
		                        employeeTripDetailPO1.setCurrenETA("0");
		                        iCabRequestBO.update(employeeTripDetailPO1);
		                        continue outer;
		                    } else
		                    {
		                        continue outer;
		                    }
		                }
		            }
		            if(cabRequests.getEfmFmUserMaster().geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("firstlastfemale") && cabRequests.geteFmFmEmployeeRequestMaster().getTripType().equalsIgnoreCase("DROP") && vehicleMaster.getAvailableSeat() == 1)
		            {
		                boolean requestAddingFlg = true;
						for(EFmFmAssignRoutePO assignRouteDetails:assignRoutePO){
		                    EFmFmEmployeeTripDetailPO employeeTripDetail = new EFmFmEmployeeTripDetailPO();
		                    eAssignRoutePO.setAssignRouteId(assignRouteDetails.getAssignRouteId());
		                    if(assignRouteDetails.getTripType().equalsIgnoreCase("DROP")){
		                    	employeeTripDetail.setTenMinuteMessageStatus("Y");
		                        employeeTripDetail.setTwoMinuteMessageStatus("Y");
		                        employeeTripDetail.setReachedFlg("Y");
		                        employeeTripDetail.setCabDelayMsgStatus("Y");

		                    }
		                    else{
		                    	employeeTripDetail.setTenMinuteMessageStatus("N");
		                        employeeTripDetail.setTwoMinuteMessageStatus("N");
		                        employeeTripDetail.setReachedFlg("N");
		                        employeeTripDetail.setCabDelayMsgStatus("N");
		                    }
		                    employeeTripDetail.setActualTime(new Date());
		                    employeeTripDetail.setGoogleEta(0);
		                    employeeTripDetail.setBoardedFlg("N");
		                    employeeTripDetail.setCurrenETA("0");
		                    employeeTripDetail.seteFmFmEmployeeTravelRequest(cabRequests);
		                    employeeTripDetail.setEfmFmAssignRoute(eAssignRoutePO);
		                    employeeTripDetail.setEmployeeStatus("allocated");
		                    iCabRequestBO.save(employeeTripDetail);
		                    List<EFmFmEmployeeTripDetailPO> allSortedEmployees = iCabRequestBO.getDropTripAllSortedEmployees(assignRouteDetails.getAssignRouteId());
		                    if(((EFmFmEmployeeTripDetailPO)allSortedEmployees.get(allSortedEmployees.size() - 1)).geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("Female") && (((EFmFmEmployeeTripDetailPO)allSortedEmployees.get(allSortedEmployees.size() - 1)).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() >= 19 || ((EFmFmEmployeeTripDetailPO)allSortedEmployees.get(allSortedEmployees.size() - 1)).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() == 0 || ((EFmFmEmployeeTripDetailPO)allSortedEmployees.get(allSortedEmployees.size() - 1)).geteFmFmEmployeeTravelRequest().getShiftTime().getHours() <7))
		                    {
		                    	List<EFmFmEmployeeTripDetailPO> tripId = iCabRequestBO.getRequestStatusFromBranchIdAndRequestId(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), cabRequests.getRequestId());
		                    	iCabRequestBO.deleteParticularRequestFromEmployeeTripDetail(((EFmFmEmployeeTripDetailPO)tripId.get(0)).getEmpTripId());
		                    } else
		                    {
		                        ((EFmFmAssignRoutePO)assignRoutePO.get(0)).setVehicleStatus("F");
		                        iCabRequestBO.update((EFmFmAssignRoutePO)assignRoutePO.get(0));
		                        availableCapacity = vehicleMaster.getAvailableSeat() - 1;
		                        vehicleMaster.setAvailableSeat(availableCapacity);
		                        iVehicleCheckInBO.update(vehicleMaster);
		                        cabRequests.setReadFlg("R");
		                        iCabRequestBO.update(cabRequests);
		                        requestAddingFlg = false;
		                        continue outer;
		                    }
		                }
		                if(requestAddingFlg)
		                {
		                    if(particularRouteEmplyees.size() <= 6)
		                        allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLessCapacity(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),10);
		                    else
		                        allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLessCapacity(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(),20);
		                    if(!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0)
		                    {
		                        EFmFmAssignRoutePO assignRoute1 = new EFmFmAssignRoutePO();
		                        EFmFmVehicleCheckInPO vehicleCheckInPO = new EFmFmVehicleCheckInPO();
		                        vehicleCheckInPO.setCheckInId(((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1)).getCheckInId());
		                        assignRoute1.setEfmFmVehicleCheckIn(vehicleCheckInPO);
		                        EFmFmRouteAreaMappingPO routeAreaMapping = new EFmFmRouteAreaMappingPO();
		                        routeAreaMapping.setRouteAreaId(cabRequests.geteFmFmRouteAreaMapping().getRouteAreaId());
		                        assignRoute1.seteFmFmRouteAreaMapping(routeAreaMapping);
		                        assignRoute1.setEscortRequredFlag("N");
		                        assignRoute1.setAllocationMsg("N");
		                        assignRoute1.setTripStatus("allocated");
		                        assignRoute1.setTripAssignDate(new Date());
		                        assignRoute1.setShiftTime(cabRequests.getShiftTime());
		                        assignRoute1.setVehicleStatus("A");
		                        assignRoute1.setBucketStatus("N");
	
		                        EFmFmClientBranchPO eFmFmClientBranchPO = new EFmFmClientBranchPO();
		                        eFmFmClientBranchPO.setBranchId(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		                        assignRoute1.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		                        assignRoute1.setTripType(((EFmFmEmployeeRequestMasterPO)employeeRequestMasterPO.get(0)).getTripType());
		                        ((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1)).setStatus("N");
		                        if(cabRequests.getEfmFmUserMaster().geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("Always") || cabRequests.getEfmFmUserMaster().geteFmFmClientBranchPO().getEscortRequired().equalsIgnoreCase("femalepresent") && cabRequests.getEfmFmUserMaster().getGender().equalsIgnoreCase("Female"))
		                        {
		                            List <EFmFmEscortCheckInPO> escortList = iCabRequestBO.getAllCheckedInEscort(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
		                            if(!escortList.isEmpty() || escortList.size() != 0)
		                            {
		                                EFmFmEscortCheckInPO checkInEscort = new EFmFmEscortCheckInPO();
		                                checkInEscort.setEscortCheckInId(((EFmFmEscortCheckInPO)escortList.get(0)).getEscortCheckInId());
		                                assignRoute1.seteFmFmEscortCheckIn(checkInEscort);
		                                ((EFmFmEscortCheckInPO)escortList.get(0)).setStatus("N");
		                                iVehicleCheckInBO.update((EFmFmEscortCheckInPO)escortList.get(0));
		                            }
		                            assignRoute1.setEscortRequredFlag("Y");
		                        }
		                         iCabRequestBO.update(assignRoute1);
		                        iVehicleCheckInBO.update((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1));
		                        cabRequests.setReadFlg("R");
		                        iCabRequestBO.update(cabRequests);
		                        List<EFmFmAssignRoutePO> assignRoutePO1 = iCabRequestBO.getHalfCompletedAssignRouteFromCheckInId(cabRequests.geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId(), cabRequests.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneId(), ((EFmFmEmployeeRequestMasterPO)employeeRequestMasterPO.get(0)).getTripType(), cabRequests.getShiftTime(), ((EFmFmVehicleCheckInPO)allCheckInVehicles.get(allCheckInVehicles.size() - 1)).getCheckInId());
		                        EFmFmVehicleMasterPO vehicleMaster1 = iCabRequestBO.getVehicleDetail(((EFmFmAssignRoutePO)assignRoutePO1.get(0)).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
		                        vehicleMaster1.setAvailableSeat(vehicleMaster1.getAvailableSeat() - 1);
		                        vehicleMaster1.setStatus("allocated");
		                        iVehicleCheckInBO.update(vehicleMaster1);
				               EFmFmDriverMasterPO particularDriverDetails1=approvalBO.getParticularDriverDetail(assignRoutePO1.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				                particularDriverDetails1.setStatus("allocated");
				                approvalBO.update(particularDriverDetails1);
				                List<EFmFmDeviceMasterPO>deviceDetails= iVehicleCheckInBO.getDeviceDetailsFromDeviceId(assignRoutePO1.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), travelRequestPO.getBranchId());
				                deviceDetails.get(0).setStatus("allocated");
				                iVehicleCheckInBO.update(deviceDetails.get(0));

		                        EFmFmEmployeeTripDetailPO employeeTripDetailPO1 = new EFmFmEmployeeTripDetailPO();
		                        eAssignRoutePO.setAssignRouteId(((EFmFmAssignRoutePO)assignRoutePO1.get(0)).getAssignRouteId());
		                        if(((EFmFmAssignRoutePO)assignRoutePO1.get(0)).getTripType().equalsIgnoreCase("DROP")){
		                        	employeeTripDetailPO1.setTenMinuteMessageStatus("Y");
		                            employeeTripDetailPO1.setTwoMinuteMessageStatus("Y");
		                            employeeTripDetailPO1.setReachedFlg("Y");
		                            employeeTripDetailPO1.setCabDelayMsgStatus("Y");
		                        }
		                        else{
		                        	employeeTripDetailPO1.setTenMinuteMessageStatus("N");
		                            employeeTripDetailPO1.setTwoMinuteMessageStatus("N");
		                            employeeTripDetailPO1.setReachedFlg("N");
		                            employeeTripDetailPO1.setCabDelayMsgStatus("N");
		                        }
		                        employeeTripDetailPO1.setActualTime(new Date());
		                        employeeTripDetailPO1.setGoogleEta(0);
		                        employeeTripDetailPO1.setBoardedFlg("N");
		                        employeeTripDetailPO1.seteFmFmEmployeeTravelRequest(cabRequests);
		                        employeeTripDetailPO1.setEfmFmAssignRoute(eAssignRoutePO);
		                        employeeTripDetailPO1.setEmployeeStatus("allocated");
		                        employeeTripDetailPO1.setCurrenETA("0");
		                        iCabRequestBO.update(employeeTripDetailPO1);
		                        continue outer;
		                    } 
		                        continue outer;
		                }
		            }
		            if(vehicleMaster.getAvailableSeat() == 1)
		            {
		                ((EFmFmAssignRoutePO)assignRoutePO.get(0)).setVehicleStatus("F");
		                iCabRequestBO.update((EFmFmAssignRoutePO)assignRoutePO.get(0));
		            }
		            vehicleMaster.setAvailableSeat(availableCapacity);
		            iVehicleCheckInBO.update(vehicleMaster);
		            cabRequests.setReadFlg("R");
		            iCabRequestBO.update(cabRequests);
		            eAssignRoutePO.setAssignRouteId(((EFmFmAssignRoutePO)assignRoutePO.get(0)).getAssignRouteId());
		            if(((EFmFmAssignRoutePO)assignRoutePO.get(0)).getTripType().equalsIgnoreCase("DROP")){
		                employeeTripDetailPO.setTenMinuteMessageStatus("Y");
		                employeeTripDetailPO.setTwoMinuteMessageStatus("Y");
		                employeeTripDetailPO.setReachedFlg("Y");
	                    employeeTripDetailPO.setCabDelayMsgStatus("Y");

		            }
		            else{
		                employeeTripDetailPO.setTenMinuteMessageStatus("N");
		                employeeTripDetailPO.setTwoMinuteMessageStatus("N");
		                employeeTripDetailPO.setReachedFlg("N");
	                    employeeTripDetailPO.setCabDelayMsgStatus("N");
		            }
		            employeeTripDetailPO.setActualTime(new Date());
                    employeeTripDetailPO.setGoogleEta(0);
		            employeeTripDetailPO.setBoardedFlg("N");
		            employeeTripDetailPO.setCurrenETA("0");
		            employeeTripDetailPO.seteFmFmEmployeeTravelRequest(cabRequests);
		            employeeTripDetailPO.setEfmFmAssignRoute(eAssignRoutePO);
		            employeeTripDetailPO.setEmployeeStatus("allocated");
		            iCabRequestBO.save(employeeTripDetailPO);
		        }
			}	
		}
		}
}
