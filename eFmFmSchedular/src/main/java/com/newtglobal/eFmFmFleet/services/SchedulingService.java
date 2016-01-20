package com.newtglobal.eFmFmFleet.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newtglobal.eFmFmFleet.business.dao.daoImpl.SchedulerCabRequestDAOImpl;
import com.newtglobal.eFmFmFleet.business.dao.daoImpl.SchedulerVehicleCheckInDAOImpl;
import com.newtglobal.eFmFmFleet.model.EFmFmActualRoutTravelledPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;

// Referenced classes of package com.newtglobal.eFmFmFleet.services:
//            MessagingService

public class SchedulingService {

	private static final int DELAY_THROTTLE = 0;
	private static Log log = LogFactory.getLog(SchedulingService.class);
	private final EntityManagerFactory entityMangerFactory;
	final SchedularMessagingService messaging = new SchedularMessagingService();

	public SchedulingService(EntityManagerFactory _entityMangerFactory) {
		entityMangerFactory = _entityMangerFactory;
	}

	public void updateTravelRequest(EFmFmEmployeeRequestMasterPO travelRequest)
			throws ParseException, IOException, Exception {
		if(DELAY_THROTTLE > 0) Thread.sleep(DELAY_THROTTLE);
		EntityManager em = entityMangerFactory.createEntityManager();
		try {
			em.getTransaction().begin();			
		} catch (Exception e) {
			em.close();
			return;
		}
		
		try{
		long l1 = System.currentTimeMillis();
		SchedulerCabRequestDAOImpl requestDAOImpl = new SchedulerCabRequestDAOImpl(
				em);
		EFmFmEmployeeTravelRequestPO travelRequestPO = new EFmFmEmployeeTravelRequestPO();
		EFmFmUserMasterPO userMaster = new EFmFmUserMasterPO();
		EFmFmRouteAreaMappingPO eFmFmRouteAreaMapping = new EFmFmRouteAreaMappingPO();
		eFmFmRouteAreaMapping.setRouteAreaId(travelRequest.getEfmFmUserMaster()
				.geteFmFmRouteAreaMapping().getRouteAreaId());
		userMaster.setUserId(travelRequest.getEfmFmUserMaster().getUserId());
		travelRequestPO.setEfmFmUserMaster(userMaster);
		travelRequestPO.seteFmFmRouteAreaMapping(eFmFmRouteAreaMapping);
		travelRequestPO.setCompletionStatus("N");
		if(travelRequest.getTripType().equalsIgnoreCase("DROP")){
		travelRequestPO.setDropSequence(travelRequest.getDropSequence());
		}
		travelRequestPO.seteFmFmEmployeeRequestMaster(travelRequest);
		DateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		SimpleDateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");

		Date todayDate = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(todayDate);
		cal.add(Calendar.DATE, 1);
		Date currentDate = cal.getTime();

		Calendar weekEnd = new GregorianCalendar();
		weekEnd.setTime(todayDate);
		weekEnd.add(Calendar.DATE, 2);
		Date weekDate = weekEnd.getTime();

		Format formatter = new SimpleDateFormat("dd-MM-yyyy");
		String todaDate = formatter.format(currentDate);
		String weekEndDate = formatter.format(weekDate);
        log.info("Trip Id:"+travelRequest.getTripId());
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		if (travelRequest.getTripRequestEndDate().getTime() > (new Date())
				.getTime()
				&& travelRequest.getTripRequestStartDate().getTime() == dateFormat
						.parse(todaDate).getTime())
			travelRequestPO.setRequestDate(dateFormat.parse(todaDate));
		else
			travelRequestPO.setRequestDate(dateFormat.parse(dateFormat
					.format(new Date())));
		EFmFmClientMasterPO eFmFmClientMasterPO = new EFmFmClientMasterPO();
		eFmFmClientMasterPO.setClientId(travelRequest.getEfmFmUserMaster()
				.geteFmFmClientBranchPO().getBranchId());
		travelRequestPO.setRequestType(travelRequest.getRequestType());
		travelRequestPO.setShiftTime(travelRequest.getShiftTime());

		if (travelRequest.getTripId()==12842) {
			System.out.println("kk");
//			log.debug("Debug Request: " + travelRequest.getTripId());
		}

		List<EFmFmEmployeeTravelRequestPO> employeeTravelRequest = requestDAOImpl
				.getParticularRequestDetail(travelRequestPO);
		if (!employeeTravelRequest.isEmpty()
				|| employeeTravelRequest.size() != 0) {
			if(employeeTravelRequest.size()>1){
				for(EFmFmEmployeeTravelRequestPO employeeDisableRequests:employeeTravelRequest){				
				  String requestDateTime = (new StringBuilder(
						String.valueOf(dateFormat
								.format(((EFmFmEmployeeTravelRequestPO) employeeDisableRequests
										)
										.getRequestDate()).toString())))
						.append(" ")
						.append(((EFmFmEmployeeTravelRequestPO) employeeDisableRequests
								)
								.getShiftTime()).toString();
			
				Date todayDateTime = dateTimeFormate.parse(requestDateTime);
				if (todayDateTime.getTime() < (new Date()).getTime()) {
					if (!(dateformate.format(new Date())
							.equalsIgnoreCase(dateformate
									.format(employeeDisableRequests
											.getRequestDate())))) {
						((EFmFmEmployeeTravelRequestPO) employeeDisableRequests
								)
								.setReadFlg("N");
						((EFmFmEmployeeTravelRequestPO) employeeDisableRequests
								)
								.setIsActive("Y");
						((EFmFmEmployeeTravelRequestPO) employeeDisableRequests
								)
								.setRequestStatus("C");
						em.merge((EFmFmEmployeeTravelRequestPO) employeeDisableRequests);
					}
				}
				
			}
		}
//			else {
			String weekOffs = ((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
					.get(employeeTravelRequest.size() - 1))
					.getEfmFmUserMaster().getWeekOffDays();
			String currentDay = new SimpleDateFormat("EEEE")
					.format(currentDate);
			String requestDateTime = (new StringBuilder(
					String.valueOf(dateFormat
							.format(((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
									.get(employeeTravelRequest.size() - 1))
									.getRequestDate()).toString())))
					.append(" ")
					.append(((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
							.get(employeeTravelRequest.size() - 1))
							.getShiftTime()).toString();
			String requestDateOnly = dateFormat
					.format(((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
							.get(employeeTravelRequest.size() - 1))
							.getRequestDate());
			Date todayDateTimeOnly = dateFormat.parse(requestDateOnly);
			String currentDateOnly = dateFormat.format(new Date());
			Date currentDateForTest = dateFormat.parse(currentDateOnly);
			Date todayDateTime = dateTimeFormate.parse(requestDateTime);
			if (todayDateTime.getTime() < (new Date()).getTime()) {
				if (!(dateformate.format(new Date())
						.equalsIgnoreCase(dateformate
								.format(employeeTravelRequest.get(
										employeeTravelRequest.size() - 1)
										.getRequestDate())))) {
					((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
							.get(employeeTravelRequest.size() - 1))
							.setReadFlg("N");
					((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
							.get(employeeTravelRequest.size() - 1))
							.setIsActive("Y");
					((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
							.get(employeeTravelRequest.size() - 1))
							.setRequestStatus("C");
					em.merge((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
							.get(employeeTravelRequest.size() - 1));
//					em.getTransaction().commit();
//					em.close();
				}
				String weekOffDays[] = ((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
						.get(employeeTravelRequest.size() - 1))
						.getEfmFmUserMaster().getWeekOffDays().split(",");
				if (weekOffDays[weekOffDays.length - 1]
						.equalsIgnoreCase((new SimpleDateFormat("EEEE"))
								.format(new Date()))) {
					travelRequestPO.setRequestDate(dateFormat.parse(todaDate));
					if (weekOffDays[weekOffDays.length - 1]
							.equalsIgnoreCase((new SimpleDateFormat("EEEE"))
									.format(new Date()))
							&& (((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
									.get(employeeTravelRequest.size() - 1))
									.getShiftTime().getHours() == 1 || ((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
									.get(employeeTravelRequest.size() - 1))
									.getShiftTime().getHours() == 3)) {
						travelRequestPO.setRequestDate(dateFormat
								.parse(weekEndDate));
					}
					EFmFmEmployeeRequestMasterPO employeeRequestMaster = new EFmFmEmployeeRequestMasterPO();
					employeeRequestMaster.setTripId(travelRequest.getTripId());
					travelRequestPO.setShiftTime(travelRequest.getShiftTime());
					travelRequestPO
							.setPickUpTime(travelRequest.getPickUpTime());
					travelRequestPO.setApproveStatus("Y");
					travelRequestPO.setRequestType(travelRequest
							.getRequestType());
					travelRequestPO.setTripType(travelRequest.getTripType());
					travelRequestPO.setRequestStatus(travelRequest
							.getRequestFrom());
					travelRequestPO.setReadFlg("Y");
					travelRequestPO.setIsActive("Y");
					travelRequestPO
							.seteFmFmEmployeeRequestMaster(employeeRequestMaster);
					if (!(weekOffs.contains(currentDay)))
						em.merge(travelRequestPO);
					em.getTransaction().commit();
					em.close();
					return;
				}
				if (todayDateTimeOnly.getTime() < currentDateForTest.getTime()
						&& !(dateFormat
								.format(((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
										.get(employeeTravelRequest.size() - 1))
										.getRequestDate()).toString()
								.equalsIgnoreCase(dateFormat.format(new Date())
										.toString()))) {
					if (todayDateTime.getTime() < (new Date()).getTime()) {
						travelRequestPO.setRequestDate(dateFormat
								.parse(dateFormat.format(new Date())));
						EFmFmEmployeeRequestMasterPO employeeRequestMaster = new EFmFmEmployeeRequestMasterPO();
						if (weekOffDays[weekOffDays.length - 1]
								.equalsIgnoreCase((new SimpleDateFormat("EEEE"))
										.format(currentDate))
								&& ((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
										.get(employeeTravelRequest.size() - 1))
										.getRequestType().equalsIgnoreCase(
												"normal")
								&& (((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
										.get(employeeTravelRequest.size() - 1))
										.getShiftTime().getHours() == 1 || ((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
										.get(employeeTravelRequest.size() - 1))
										.getShiftTime().getHours() == 3)) {
							travelRequestPO.setRequestDate(dateFormat
									.parse(todaDate));
						}
						employeeRequestMaster.setTripId(travelRequest
								.getTripId());
						travelRequestPO.setShiftTime(travelRequest
								.getShiftTime());
						travelRequestPO.setPickUpTime(travelRequest
								.getPickUpTime());
						travelRequestPO.setApproveStatus("Y");
						travelRequestPO.setRequestType(travelRequest
								.getRequestType());
						travelRequestPO
								.setTripType(travelRequest.getTripType());
						travelRequestPO.setRequestStatus(travelRequest
								.getRequestFrom());
						travelRequestPO.setReadFlg("Y");
						travelRequestPO.setIsActive("Y");
						travelRequestPO
								.seteFmFmEmployeeRequestMaster(employeeRequestMaster);
						em.merge(travelRequestPO);
						em.getTransaction().commit();
						em.close();
						return;
					}
				}
			}
//		}
		}

		List<EFmFmEmployeeTravelRequestPO> newemployeeTravelRequest = requestDAOImpl
				.getParticularRequestDetail(travelRequestPO);
		if (newemployeeTravelRequest.isEmpty()
				|| newemployeeTravelRequest.size() == 0) {
			List<EFmFmEmployeeTravelRequestPO> readRequestFromTravelRequest = requestDAOImpl
					.getParticularReadRequestDetail(travelRequestPO);
			if (!readRequestFromTravelRequest.isEmpty()
					|| readRequestFromTravelRequest.size() != 0) {
				Date requestDate = dateFormat.parse(dateFormat
						.format(readRequestFromTravelRequest.get(
								readRequestFromTravelRequest.size() - 1)
								.getRequestDate()));
				Date requestEndDate = dateFormat.parse(dateFormat
						.format(travelRequest.getTripRequestEndDate()));
				if (requestEndDate.getTime() > requestDate.getTime()) {
					travelRequestPO.setRequestDate(dateFormat.parse(todaDate));
				} else {
					em.getTransaction().commit();
					em.close();
					return;
				}
			}
			EFmFmEmployeeRequestMasterPO employeeRequestMaster = new EFmFmEmployeeRequestMasterPO();
			employeeRequestMaster.setTripId(travelRequest.getTripId());
			travelRequestPO.setShiftTime(travelRequest.getShiftTime());
			travelRequestPO.setApproveStatus("Y");
			travelRequestPO.setPickUpTime(travelRequest.getPickUpTime());
			travelRequestPO.setRequestType(travelRequest.getRequestType());
			travelRequestPO.setTripType(travelRequest.getTripType());
			travelRequestPO.setRequestStatus(travelRequest.getRequestFrom());
			travelRequestPO.setReadFlg("Y");
			travelRequestPO.setIsActive("Y");
			travelRequestPO
					.seteFmFmEmployeeRequestMaster(employeeRequestMaster);
			em.merge(travelRequestPO);
		} else if (!((EFmFmEmployeeTravelRequestPO) newemployeeTravelRequest
				.get(newemployeeTravelRequest.size() - 1)).getReadFlg()
				.equalsIgnoreCase("Y")
				&& dateFormat
						.format(((EFmFmEmployeeTravelRequestPO) newemployeeTravelRequest
								.get(newemployeeTravelRequest.size() - 1))
								.getRequestDate())
						.toString()
						.equalsIgnoreCase(
								dateFormat.format(new Date()).toString())) {
			String weekOffs = ((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
					.get(employeeTravelRequest.size() - 1))
					.getEfmFmUserMaster().getWeekOffDays();
			String currentDay = new SimpleDateFormat("EEEE")
					.format(currentDate);
			String requestDateTime = (new StringBuilder(
					String.valueOf(dateFormat
							.format(((EFmFmEmployeeTravelRequestPO) newemployeeTravelRequest
									.get(newemployeeTravelRequest.size() - 1))
									.getRequestDate()).toString())))
					.append(" ")
					.append(((EFmFmEmployeeTravelRequestPO) newemployeeTravelRequest
							.get(newemployeeTravelRequest.size() - 1))
							.getShiftTime()).toString();
			Date todayDateTime = dateTimeFormate.parse(requestDateTime);
			if (travelRequest.getTripRequestEndDate().getTime() < dateFormat
					.parse(todaDate).getTime()) {
				em.getTransaction().commit();
				em.close();
				return;
			}
			if (todayDateTime.getTime() > (new Date()).getTime()) {
				travelRequestPO.setRequestDate(dateFormat.parse(todaDate));
				travelRequestPO.setShiftTime(travelRequest.getShiftTime());
				travelRequestPO.setPickUpTime(travelRequest.getPickUpTime());
				travelRequestPO.setApproveStatus("Y");
				travelRequestPO.setRequestType(travelRequest.getRequestType());
				travelRequestPO.setTripType(travelRequest.getTripType());
				travelRequestPO
						.setRequestStatus(travelRequest.getRequestFrom());
				travelRequestPO.setReadFlg("Y");
				travelRequestPO.setIsActive("Y");
				if (!(weekOffs.contains(currentDay)))
					em.merge(travelRequestPO);
			} else {
				String weekOffDays[] = ((EFmFmEmployeeTravelRequestPO) newemployeeTravelRequest
						.get(newemployeeTravelRequest.size() - 1))
						.getEfmFmUserMaster().getWeekOffDays().split(",");
				if (!((EFmFmEmployeeTravelRequestPO) newemployeeTravelRequest
						.get(newemployeeTravelRequest.size() - 1))
						.getEfmFmUserMaster()
						.getWeekOffDays()
						.contains(
								(new SimpleDateFormat("EEEE"))
										.format(currentDate))) {
					if (travelRequest.getTripRequestEndDate().getTime() < dateFormat
							.parse(todaDate).getTime()) {
						em.getTransaction().commit();
						em.close();
						return;
					}
					travelRequestPO.setRequestDate(dateFormat.parse(todaDate));
					EFmFmEmployeeRequestMasterPO employeeRequestMaster = new EFmFmEmployeeRequestMasterPO();
					employeeRequestMaster.setTripId(travelRequest.getTripId());
					travelRequestPO.setShiftTime(travelRequest.getShiftTime());
					travelRequestPO
							.setPickUpTime(travelRequest.getPickUpTime());
					travelRequestPO.setApproveStatus("Y");
					travelRequestPO.setRequestType(travelRequest
							.getRequestType());
					travelRequestPO.setTripType(travelRequest.getTripType());
					travelRequestPO.setRequestStatus(travelRequest
							.getRequestFrom());
					travelRequestPO.setReadFlg("Y");
					travelRequestPO.setIsActive("Y");
					travelRequestPO
							.seteFmFmEmployeeRequestMaster(employeeRequestMaster);
					if (!(weekOffs.contains(currentDay)))
						em.merge(travelRequestPO);
				}
				// Check For First Off day for employee will check the shift
				// drop shiftTime only.Right Now we Have 2 Night Drops only
				else if (weekOffDays[0].equalsIgnoreCase((new SimpleDateFormat(
						"EEEE")).format(currentDate))
						&& (((EFmFmEmployeeTravelRequestPO) newemployeeTravelRequest
								.get(newemployeeTravelRequest.size() - 1))
								.getShiftTime().getHours() == 1 || ((EFmFmEmployeeTravelRequestPO) newemployeeTravelRequest
								.get(newemployeeTravelRequest.size() - 1))
								.getShiftTime().getHours() == 3)) {
					if (travelRequest.getTripRequestEndDate().getTime() < dateFormat
							.parse(todaDate).getTime()) {
						em.getTransaction().commit();
						em.close();
						return;
					}
					travelRequestPO.setRequestDate(dateFormat.parse(todaDate));
					EFmFmEmployeeRequestMasterPO employeeRequestMaster = new EFmFmEmployeeRequestMasterPO();
					employeeRequestMaster.setTripId(travelRequest.getTripId());
					travelRequestPO.setShiftTime(travelRequest.getShiftTime());
					travelRequestPO
							.setPickUpTime(travelRequest.getPickUpTime());
					travelRequestPO.setApproveStatus("Y");
					travelRequestPO.setRequestType(travelRequest
							.getRequestType());
					travelRequestPO.setTripType(travelRequest.getTripType());
					travelRequestPO.setRequestStatus(travelRequest
							.getRequestFrom());
					travelRequestPO.setReadFlg("Y");
					travelRequestPO.setIsActive("Y");
					travelRequestPO
							.seteFmFmEmployeeRequestMaster(employeeRequestMaster);
					if (!(weekOffs.contains(currentDay)))
						em.merge(travelRequestPO);
				}
			}
		}
		em.getTransaction().commit();
		em.close();
		long l2 = System.currentTimeMillis();
		log.debug("Creating the request from request master table : "
				+ (l2 - l1) + "ms for Employee: "
				+ travelRequest.getEfmFmUserMaster().getEmployeeId());
	}catch(Exception e){};
	
	}

	public void disableActiveRequests(EFmFmEmployeeRequestMasterPO travelRequest)
			throws ParseException, IOException, Exception {
		if(DELAY_THROTTLE > 0) Thread.sleep(DELAY_THROTTLE);
		EntityManager em = entityMangerFactory.createEntityManager();
		try {
			em.getTransaction().begin();			
		} catch (Exception e) {
			em.close();
			return;
		}
		long l1 = System.currentTimeMillis();
		SchedulerCabRequestDAOImpl requestDAOImpl = new SchedulerCabRequestDAOImpl(
				em);
		EFmFmEmployeeTravelRequestPO travelRequestPO = new EFmFmEmployeeTravelRequestPO();
		EFmFmUserMasterPO userMaster = new EFmFmUserMasterPO();
		EFmFmRouteAreaMappingPO eFmFmRouteAreaMapping = new EFmFmRouteAreaMappingPO();
		eFmFmRouteAreaMapping.setRouteAreaId(travelRequest.getEfmFmUserMaster()
				.geteFmFmRouteAreaMapping().getRouteAreaId());
		userMaster.setUserId(travelRequest.getEfmFmUserMaster().getUserId());
		travelRequestPO.setEfmFmUserMaster(userMaster);
		travelRequestPO.seteFmFmRouteAreaMapping(eFmFmRouteAreaMapping);
		travelRequestPO.seteFmFmEmployeeRequestMaster(travelRequest);
		EFmFmClientMasterPO eFmFmClientMasterPO = new EFmFmClientMasterPO();
		eFmFmClientMasterPO.setClientId(travelRequest.getEfmFmUserMaster()
				.geteFmFmClientBranchPO().getBranchId());
		travelRequestPO.setRequestType(travelRequest.getRequestType());
		travelRequestPO.setShiftTime(travelRequest.getShiftTime());
		List<EFmFmEmployeeTravelRequestPO> employeeTravelRequest = requestDAOImpl
				.getParticularRequestDetail(travelRequestPO);
		if (!employeeTravelRequest.isEmpty()
				|| employeeTravelRequest.size() != 0) {
			((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
					.get(employeeTravelRequest.size() - 1)).setReadFlg("N");
			((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
					.get(employeeTravelRequest.size() - 1)).setIsActive("Y");
			((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
					.get(employeeTravelRequest.size() - 1))
					.setRequestStatus("C");
			em.merge((EFmFmEmployeeTravelRequestPO) employeeTravelRequest
					.get(employeeTravelRequest.size() - 1));
		}
		long l2 = System.currentTimeMillis();
		em.getTransaction().commit();
		em.close();
		log.debug("Inside Disable Active Request Fucntion Execution Time: "
				+ (l2 - l1) + "ms");
	}

	public void sendingAutoMsgOnDeviceStop(
			final EFmFmEmployeeTripDetailPO eFmFmEmployeeTripDetailPO)
			throws ParseException, IOException, Exception {
		if(DELAY_THROTTLE > 0) Thread.sleep(DELAY_THROTTLE);
		EntityManager em = entityMangerFactory.createEntityManager();
		try {
			em.getTransaction().begin();			
		} catch (Exception e) {
			em.close();
			return;
		}
		long l1 = System.currentTimeMillis();
		SchedulerVehicleCheckInDAOImpl vehicleCheckIn = new SchedulerVehicleCheckInDAOImpl(
				em);
		List<EFmFmActualRoutTravelledPO> actualRoutTravelledPO = vehicleCheckIn
				.getLastUpdatedValueFromDevice(eFmFmEmployeeTripDetailPO
						.getEfmFmAssignRoute().getAssignRouteId(),
						eFmFmEmployeeTripDetailPO.getEfmFmAssignRoute()
								.geteFmFmClientBranchPO().getBranchId());
		if (!actualRoutTravelledPO.isEmpty()
				|| actualRoutTravelledPO.size() != 0) {
			DateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat dateTimeformate = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm:ss");
			Date currentDate = new Date();
			String currentDateStr = dateformate.format(currentDate)
					+ " "
					+ eFmFmEmployeeTripDetailPO.geteFmFmEmployeeTravelRequest()
							.getPickUpTime();
			long pickupTime = dateTimeformate.parse(currentDateStr).getTime();
			log.info("currentDate: " + currentDateStr + "currenttime: "
					+ pickupTime);
			int lastEta = eFmFmEmployeeTripDetailPO.getGoogleEta();
			// 15 minute ETA meesage
			int lastEtaInMs = lastEta * 1000;
			long currentTime = System.currentTimeMillis();
			long gracePeriodForMessage = 120000;
			// 15 min - 2 minute in msec, in case we get it exactly at 15min.
			long timeCalculationFor15Min = ((actualRoutTravelledPO.get(
					actualRoutTravelledPO.size() - 1).getTravelledTime()
					.getTime()) + (lastEtaInMs)) - (900000-gracePeriodForMessage);
			// Cab Arrived Message + 2 min more - in case Device reaches and
			// triggers before automatic Arrived message
			long timeCalculationForArrived = ((actualRoutTravelledPO.get(
					actualRoutTravelledPO.size() - 1).getTravelledTime()
					.getTime()) + (lastEtaInMs)) + gracePeriodForMessage;
			if (eFmFmEmployeeTripDetailPO.getReachedFlg().equalsIgnoreCase("N")
					&& eFmFmEmployeeTripDetailPO.getTenMinuteMessageStatus()
							.equalsIgnoreCase("N")
					&& (currentTime >= timeCalculationFor15Min)) {
				log.info("Current Time: " + currentTime
						+ " vs. Time Calculated for 15 Minutes: "
						+ timeCalculationFor15Min);
				log.info("Sending ETA for Trip: "
						+ eFmFmEmployeeTripDetailPO.getEmpTripId());
				Thread thread1 = new Thread(new Runnable() {
					synchronized public void run() {
						try {
							long l1 = System.currentTimeMillis();
							if (eFmFmEmployeeTripDetailPO
									.geteFmFmEmployeeTravelRequest()
									.getRequestType().equalsIgnoreCase("guest")) {
								messaging
										.etaMessagesForGuestFromSchedular(
												eFmFmEmployeeTripDetailPO
														.getEfmFmAssignRoute()
														.getEfmFmVehicleCheckIn()
														.getEfmFmVehicleMaster()
														.getVehicleNumber(),
												"15 min",
												eFmFmEmployeeTripDetailPO
														.geteFmFmEmployeeTravelRequest()
														.geteFmFmEmployeeRequestMaster()
														.getEfmFmUserMaster()
														.getMobileNumber(),eFmFmEmployeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());
							} else {
								messaging
										.etaMessageFromSchedular(
												eFmFmEmployeeTripDetailPO
														.getEfmFmAssignRoute()
														.getEfmFmVehicleCheckIn()
														.getEfmFmVehicleMaster()
														.getVehicleNumber(),
												"13 min",
												eFmFmEmployeeTripDetailPO
														.geteFmFmEmployeeTravelRequest()
														.getEfmFmUserMaster()
														.getMobileNumber(),eFmFmEmployeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());
							}
							Thread.currentThread().stop();
							long l2 = System.currentTimeMillis();
							log.debug("Time Taken by 15 minute message from gate way: "
									+ (l2 - l1)
									+ "ms for mobile number "
									+ eFmFmEmployeeTripDetailPO
											.geteFmFmEmployeeTravelRequest()
											.getEfmFmUserMaster()
											.getMobileNumber()
									+ "For Trip Id "
									+ eFmFmEmployeeTripDetailPO.getEmpTripId());
						} catch (Exception e) {
							log.error("Error Sending 15 minute ETA for Trip: "
									+ eFmFmEmployeeTripDetailPO.getEmpTripId(),
									e);
							Thread.currentThread().stop();
						}
					}
				});
				thread1.start();
				eFmFmEmployeeTripDetailPO.setTenMinuteMessageStatus("Y");
				eFmFmEmployeeTripDetailPO
						.setTenMinuteMessageDeliveryDate(new Date());
				em.merge(eFmFmEmployeeTripDetailPO);
			}
			if (eFmFmEmployeeTripDetailPO.getReachedFlg().equalsIgnoreCase("N")
					&& (currentTime >= timeCalculationForArrived)) {
				log.info("Sending Reached ETA Message for Trip: "
						+ eFmFmEmployeeTripDetailPO.getEmpTripId());
				Thread thread1 = new Thread(new Runnable() {
					synchronized public void run() {
						try {
							long l1 = System.currentTimeMillis();
							String text = "";
							if (eFmFmEmployeeTripDetailPO
									.geteFmFmEmployeeTravelRequest()
									.getRequestType().equalsIgnoreCase("guest")) {
								text = "Dear guest your cab\n"
										+ eFmFmEmployeeTripDetailPO
												.getEfmFmAssignRoute()
												.getEfmFmVehicleCheckIn()
												.getEfmFmVehicleMaster()
												.getVehicleNumber()
										+ "\n should arrived at your pickup point";

								messaging
										.cabReachedMessageForGuestFromSch(
												eFmFmEmployeeTripDetailPO
														.getEfmFmAssignRoute()
														.getEfmFmVehicleCheckIn()
														.getEfmFmVehicleMaster()
														.getVehicleNumber(),
												eFmFmEmployeeTripDetailPO
														.geteFmFmEmployeeTravelRequest()
														.geteFmFmEmployeeRequestMaster()
														.getEfmFmUserMaster()
														.getMobileNumber(),eFmFmEmployeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());
							} else {
								text = "Dear employee your cab\n"
										+ eFmFmEmployeeTripDetailPO
												.getEfmFmAssignRoute()
												.getEfmFmVehicleCheckIn()
												.getEfmFmVehicleMaster()
												.getVehicleNumber()
										+ "\n should arrived at your pickup point";
								messaging
										.cabReachedMessageForSch(
												eFmFmEmployeeTripDetailPO
														.geteFmFmEmployeeTravelRequest()
														.getEfmFmUserMaster()
														.getMobileNumber(),
												text,eFmFmEmployeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());
							}

							long l2 = System.currentTimeMillis();
							log.debug("Time Taken by reached message from gate way: "
									+ (l2 - l1)
									+ "ms for mobile number "
									+ eFmFmEmployeeTripDetailPO
											.geteFmFmEmployeeTravelRequest()
											.getEfmFmUserMaster()
											.getMobileNumber());

							Thread.currentThread().stop();
						} catch (Exception e) {
							log.error(
									"Error Sending Reached ETA Message for Trip: "
											+ eFmFmEmployeeTripDetailPO
													.getEmpTripId(), e);
							Thread.currentThread().stop();

						}
					}
				});
				thread1.start();
				eFmFmEmployeeTripDetailPO.setReachedFlg("Y");
				eFmFmEmployeeTripDetailPO
						.setCabRecheddestinationTime(currentTime);
				eFmFmEmployeeTripDetailPO
						.setReachedMessageDeliveryDate(new Date());
				em.merge(eFmFmEmployeeTripDetailPO);
			}
			// Cab Has Left
			long timeMax = Math.max(
					eFmFmEmployeeTripDetailPO.getCabRecheddestinationTime(),
					pickupTime);
			timeMax = Math.max(timeCalculationForArrived, timeMax);
			if (eFmFmEmployeeTripDetailPO.getReachedFlg().equalsIgnoreCase("Y")
					&& currentTime >= (timeMax + 180000)) {
				log.info("Sending Cab Left  Message for Trip: "
						+ eFmFmEmployeeTripDetailPO.getEmpTripId());
				Thread thread1 = new Thread(new Runnable() {
					synchronized public void run() {
						try {
							long l1 = System.currentTimeMillis();
							String text = "";
							if (eFmFmEmployeeTripDetailPO
									.geteFmFmEmployeeTravelRequest()
									.getRequestType().equalsIgnoreCase("guest")) {
								text = "Dear guest your cab\n"
										+ eFmFmEmployeeTripDetailPO
												.getEfmFmAssignRoute()
												.getEfmFmVehicleCheckIn()
												.getEfmFmVehicleMaster()
												.getVehicleNumber()
										+ "\n has left at your pickup point";

								messaging
										.cabHasLeftMessageForGuestFromSch(
												eFmFmEmployeeTripDetailPO
														.geteFmFmEmployeeTravelRequest()
														.geteFmFmEmployeeRequestMaster()
														.getEfmFmUserMaster()
														.getMobileNumber(),
												text,eFmFmEmployeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());
							} else {
								text = "Dear employee your cab\n"
										+ eFmFmEmployeeTripDetailPO
												.getEfmFmAssignRoute()
												.getEfmFmVehicleCheckIn()
												.getEfmFmVehicleMaster()
												.getVehicleNumber()
										+ "\n has left at your pickup point";
								messaging
										.cabHasLeftMessageForSch(
												eFmFmEmployeeTripDetailPO
														.geteFmFmEmployeeTravelRequest()
														.getEfmFmUserMaster()
														.getMobileNumber(),
												text,eFmFmEmployeeTripDetailPO.geteFmFmEmployeeTravelRequest().getRequestType());
							}
							long l2 = System.currentTimeMillis();
							log.debug("Time Taken by Cab Left  Message from gate way: "
									+ (l2 - l1)
									+ "ms for mobile number "
									+ eFmFmEmployeeTripDetailPO
											.geteFmFmEmployeeTravelRequest()
											.getEfmFmUserMaster()
											.getMobileNumber());
							Thread.currentThread().stop();
						} catch (Exception e) {
							log.error(
									"Error Sending Cas Left  Message for Trip: "
											+ eFmFmEmployeeTripDetailPO
													.getEmpTripId(), e);
							Thread.currentThread().stop();
						}
					}
				});
				thread1.start();
				eFmFmEmployeeTripDetailPO.setBoardedFlg("NO");
				eFmFmEmployeeTripDetailPO
						.setCabstartFromDestination(currentTime);
				em.merge(eFmFmEmployeeTripDetailPO);
			}
		}
		long l2 = System.currentTimeMillis();
		em.getTransaction().commit();
		em.close();
		log.debug("Inside the Message sending code of schedular: " + (l2 - l1)
				+ "ms EmpTripId" + eFmFmEmployeeTripDetailPO.getEmpTripId());

	}

}