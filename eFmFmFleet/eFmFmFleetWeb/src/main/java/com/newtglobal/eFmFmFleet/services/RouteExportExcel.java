package com.newtglobal.eFmFmFleet.services;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.IAssignRouteBO;
import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IEmployeeDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IRouteDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IUserMasterBO;
import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.eFmFmFleet.CalculateDistance;
import com.newtglobal.eFmFmFleet.model.EFmFmAreaMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmAssignRoutePO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientProjectDetailsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientRouteMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientUserRolePO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTravelRequestPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeTripDetailPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRoleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmZoneMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/xlEmployeeExport")
public class RouteExportExcel {
	private static Log RouteExportExcel_log = LogFactory.getLog(RouteExportExcel.class);
	private static final String SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER = "C:/ExcelExport/";
	private static final String SERVER_UPLOAD_LINUX_LOCATION_FOLDER = "/home/tripDownloadDocs/";
	/*
	 * @Reading employee details from employee_master xl utility.
	 * @Stored all the values on Arraylist.
	 * @author  Rajan R
	 * @since   2015-05-12 
	 */	
	@POST
	@Path("/exportRoute")
	@Consumes("application/json")
	@Produces("application/json")
	public Response exporCreatedRoute(EFmFmEmployeeTravelRequestPO travelRequestPO) throws ParseException{	
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		 List<EFmFmEmployeeTripDetailPO> employeeTripList=null;
		String name="os.name",filePath="";		 	
		boolean  OsName =System.getProperty(name).startsWith("Windows");		
		if(OsName){			
			filePath =SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER;			
		}else{
			filePath =SERVER_UPLOAD_LINUX_LOCATION_FOLDER;			
		}
		DateFormat shiftFormate = new SimpleDateFormat("HH-mm-ss");
		DateFormat shiftTimeFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");	
		DateFormat shiftTimeFormate = new SimpleDateFormat("HH:mm");	
		String shiftTime = travelRequestPO.getTime();
		Date shift = (Date) shiftTimeFormate.parse(shiftTime);
		java.sql.Time dateShiftTime = new java.sql.Time(shift.getTime());	

		Date today = new Date();		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Route Details");		
		
		
		XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderTop((short) 6); // double lines border
        style.setBorderBottom((short) 1); // single line border
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);           
        font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL); 
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(177,162,186)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(font);  
       	Date excutionDate = dateformate.parse(travelRequestPO.getExecutionDate());			
		/*String tripType="PICKUP";
		String ShifTime="05:30:00";*/
		int rownum = 0,noOfRoute=0;		
		List<EFmFmAssignRoutePO>  activeRoutes=iAssignRouteBO.getExportTodayTrips(excutionDate,excutionDate, travelRequestPO.getTripType(),shiftTimeFormat.format(dateShiftTime), travelRequestPO.getBranchId());
		if(activeRoutes.size() >0){			
			for (EFmFmAssignRoutePO activeRouteList:activeRoutes){
				Row OutSiderow = sheet.createRow(rownum++);
				for(int columnIndex = 0; columnIndex < 8; columnIndex++) {
					sheet.autoSizeColumn(columnIndex);
				}
				 noOfRoute++;
				 Cell cell0 = OutSiderow.createCell(0);	
				 cell0.setCellValue(noOfRoute);
				 cell0.setCellStyle(style);
				
				
				 Cell routeIdHeading = OutSiderow.createCell(1);
				 routeIdHeading.setCellValue("RouteId");
				 routeIdHeading.setCellStyle(style);				 
				 
			     Cell routeId = OutSiderow.createCell(2);			      
			     routeId.setCellValue(activeRouteList.getAssignRouteId());
			     routeId.setCellStyle(style);
			     
			     Cell routeNameHeading = OutSiderow.createCell(3);
			     routeNameHeading.setCellValue("RouteName");
			     routeNameHeading.setCellStyle(style);
			     Cell routeName = OutSiderow.createCell(4);			      
			     routeName.setCellValue(activeRouteList.geteFmFmRouteAreaMapping().geteFmFmZoneMaster().getZoneName());
			     routeName.setCellStyle(style);
			     Cell shftTime = OutSiderow.createCell(5);	
			     shftTime.setCellValue("ShiftTime");
			     shftTime.setCellStyle(style);
			     Cell shftTimeVal = OutSiderow.createCell(6);	
			     shftTimeVal.setCellValue((String) travelRequestPO.getExecutionDate()+" "+shiftTimeFormat.format(activeRouteList.getShiftTime()));
			     shftTimeVal.setCellStyle(style);
			     
			     /*outSide Heading Start*/
			     Row outSideHeading = sheet.createRow(rownum++);
			     Cell sNo = outSideHeading.createCell(0);
			     sNo.setCellValue("S.No");
			     sNo.setCellStyle(style);
			     Cell empId = outSideHeading.createCell(1);
			     empId.setCellValue("EmployeeId");
			     empId.setCellStyle(style);
			     Cell empName = outSideHeading.createCell(2);
			     empName.setCellValue("EmployeeName");
			     empName.setCellStyle(style);
			     Cell empGender = outSideHeading.createCell(3);
			     empGender.setCellValue("Gender");	
			     empGender.setCellStyle(style);
			     Cell picKupTime = outSideHeading.createCell(4);
			     if(travelRequestPO.getTripType().equalsIgnoreCase("PICKUP")){
			    	 picKupTime.setCellValue("PickupTime");
			     }else{
			    	 picKupTime.setCellValue("DropTime");
			     }
			     picKupTime.setCellStyle(style);
			     Cell areaName = outSideHeading.createCell(5);
			     areaName.setCellValue("AreaName");
			     areaName.setCellStyle(style);
			     Cell empAddress = outSideHeading.createCell(6);
			     empAddress.setCellValue("EmployeeAddress");
			     empAddress.setCellStyle(style);
			     /*outSide Heading End*/
			     if(travelRequestPO.getTripType().equalsIgnoreCase("DROP")){			    	 
			    	 employeeTripList=iCabRequestBO.getNonDropTripAllSortedEmployees(activeRouteList.getAssignRouteId());
			     }else{
			    	 employeeTripList=iCabRequestBO.getParticularTripAllEmployees(activeRouteList.getAssignRouteId());
			     }				
				if(employeeTripList.size()>0){
					int rowNum=1;
					for(EFmFmEmployeeTripDetailPO employeeList:employeeTripList){
						Row insideRow = sheet.createRow(rownum++);
						Cell rowNo = insideRow.createCell(0);
						rowNo.setCellValue(rowNum++);
						Cell employeeId = insideRow.createCell(1);
						if(employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId() instanceof String) 
						employeeId.setCellValue((String) employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getEmployeeId());						 
						Cell employeeName = insideRow.createCell(2);
						if(employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName() instanceof String) 
							employeeName.setCellValue((String) employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getFirstName());								 
						Cell employeeGender = insideRow.createCell(3);
						if(employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender() instanceof String) 
						employeeGender.setCellValue((String) employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender());						
						Cell pickupTime = insideRow.createCell(4);
						if(employeeList.geteFmFmEmployeeTravelRequest().getPickUpTime() instanceof Date) {
							pickupTime.setCellValue((String) shiftTimeFormate.format(employeeList.geteFmFmEmployeeTravelRequest().getPickUpTime()));
						}else{
							pickupTime.setCellValue((String)"00:00");
						}
						Cell employeeAreaName = insideRow.createCell(5);
						if(employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaName() instanceof String) 
							employeeAreaName.setCellValue((String) employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().geteFmFmRouteAreaMapping().getEfmFmAreaMaster().getAreaName());						
						Cell employeeAddress = insideRow.createCell(6);
						if(employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getAddress() instanceof String) 
							employeeAddress.setCellValue((String) employeeList.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getAddress());
					       
					}
				}
				
			}
		}
		
		try {
			String FileName=filePath+travelRequestPO.getTripType()+"_("+shiftFormate.format(dateShiftTime)+")_"+dateformate.format(today)+"_"+shiftFormate.format(today)+".xlsx";
		    FileOutputStream out =new FileOutputStream(new File(FileName));
		    workbook.write(out);
		    out.close();
		    System.out.println("Route Excel written successfully..");
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	
		
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
	}
	
	/*
	 * Assign Cab pop up Inputs
	 */
	@POST
	@Path("/algoPopUpInputs")	
	@Consumes("application/json")
	@Produces("application/json")
	public Response algoPopUpInputs(EFmFmEmployeeTravelRequestPO travelRequestPO) throws ParseException{				
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		List<Map<String, Object>> availableVehicleDetails= new ArrayList<Map<String, Object>>();
		DateFormat shiftTimeFormate = new SimpleDateFormat("HH:mm");				 
		DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");		 
		Date date = inputFormat.parse(travelRequestPO.getExecutionDate());
		String convertedCurrentDate = outputFormat.format(date);		 
		String shiftTime = travelRequestPO.getTime();
		Date shift = (Date) shiftTimeFormate.parse(shiftTime);
		java.sql.Time dateShiftTime = new java.sql.Time(shift.getTime());		
		List<EFmFmEmployeeTravelRequestPO>  noOfEmployee=iCabRequestBO.listOfTravelRequestByShiftTripType(travelRequestPO.getBranchId(), dateShiftTime, travelRequestPO.getTripType(),convertedCurrentDate);		
		List<EFmFmVehicleMasterPO>  listOfAvailableVehicles=iVehicleCheckInBO.getCheckInVehicle(travelRequestPO.getBranchId(), new Date());
		if((!(listOfAvailableVehicles.isEmpty())) || listOfAvailableVehicles.size() !=0){			
			for(EFmFmVehicleMasterPO VehicleList:listOfAvailableVehicles){
				Map<String, Object>  vehicleDetails= new HashMap<String, Object>();					
				if(VehicleList.getCapacity() ==6 && VehicleList.getNoOfVehicles() !=0){
					List<Map<String, Object>> capacityVehicleDetails= new ArrayList<Map<String, Object>>();
					for(int noofVehicle=0; noofVehicle<=VehicleList.getNoOfVehicles(); noofVehicle++){
						Map<String, Object>  capacityList= new HashMap<String, Object>();
						capacityList.put("valueList", noofVehicle);
						capacityVehicleDetails.add(capacityList);					
					}
					vehicleDetails.put("inova",capacityVehicleDetails);
				}else if(VehicleList.getCapacity() ==13 && VehicleList.getNoOfVehicles() !=0){				
					List<Map<String, Object>> capacityVehicleDetails= new ArrayList<Map<String, Object>>();
					for(int noofVehicle=0; noofVehicle<=VehicleList.getNoOfVehicles(); noofVehicle++){
						Map<String, Object>  capacityTempo= new HashMap<String, Object>();
						capacityTempo.put("valueList",noofVehicle);
						capacityVehicleDetails.add(capacityTempo);					
					}
					vehicleDetails.put("tempo",capacityVehicleDetails);
				}						
				vehicleDetails.put("employeeCount",noOfEmployee.size());
				availableVehicleDetails.add(vehicleDetails);
			}				
		}			
		return Response.ok(availableVehicleDetails, MediaType.APPLICATION_JSON).build();
	}
	
	
	
	@POST
	@Path("/listOfFileNames")	
	@Consumes("application/json")
	@Produces("application/json")
	public Response listOfFileNames(EFmFmEmployeeTravelRequestPO travelRequestPO) throws ParseException{	
		List<Map<String, Object>> availableFileDetails= new ArrayList<Map<String, Object>>();
		String name="os.name",filePath="";
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
		Date TodayDate=new Date();
		
		boolean  OsName =System.getProperty(name).startsWith("Windows");		
		if(OsName){			
			filePath =SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER;			
		}else{
			filePath =SERVER_UPLOAD_LINUX_LOCATION_FOLDER;			
		}
		
		try {
			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();
			Arrays.asList(listOfFiles);
			List listFile = Arrays.asList(listOfFiles);
			Collections.sort(listFile, Collections.reverseOrder());
			if (listOfFiles.length > 0) {
				for (int fileCount = 0; fileCount < listOfFiles.length; fileCount++) {
					Map<String, Object> fileDetails = new HashMap<String, Object>();
					if (listOfFiles[fileCount].isFile()) {
						if (listOfFiles[fileCount].getName().contains(dateFormat.format(TodayDate))) {
							fileDetails.put("reportName", listOfFiles[fileCount].getName());
							availableFileDetails.add(fileDetails);
						} else {
							(new File(listOfFiles[fileCount].getName())).delete();					
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(availableFileDetails, MediaType.APPLICATION_JSON).build();
	}	
	@POST
	@Path("/readCreatedRoute")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response upload(
			@FormDataParam("filename") InputStream uploadedInputStream,
			@FormDataParam("filename") FormDataContentDisposition fileDetail,
			@QueryParam("branchId") int branchId,			
			@Context HttpServletRequest request) throws ParseException, IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {	
		if (fileDetail.getFileName().endsWith("xlsx")) {	
		try {
			System.out.println("fileName"+fileDetail.getFileName());
			Map<Integer, Object>  vehicleDetails= new HashMap<Integer, Object>();
			int rowId=0;
			XSSFWorkbook workbook = new XSSFWorkbook(uploadedInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			//rowIterator.next();			
			while (rowIterator.hasNext()) {				
				ArrayList<Object> columnValue = new ArrayList<Object>();
				Row row = rowIterator.next();
				rowId++;
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {					
					Cell cell = cellIterator.next();					
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						columnValue.add(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_BLANK:
						columnValue.add(cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							Date date = DateUtil.getJavaDate((double) cell.getNumericCellValue());
							SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							columnValue.add(df.format(date));
						}else {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							columnValue.add(cell.getStringCellValue().trim());	
						}
						break;
					case Cell.CELL_TYPE_STRING:
						columnValue.add(cell.getStringCellValue().toString().trim());
						break;
					case Cell.CELL_TYPE_FORMULA:
						columnValue.add("");
						break;
					default:
						columnValue.add("");
						break;
					}
				}
				
				if(columnValue.size()>1){					
					vehicleDetails.put(rowId, columnValue);
				}
					
			}			
			System.out.println("Size"+vehicleDetails.size());
			if(vehicleDetails.size() >1){
				extractRecord(vehicleDetails,branchId);	
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
	}	
	
	public void extractRecord(Map<Integer, Object> vehicleDetails, int branchId) throws ParseException {
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext()
				.getBean("IVehicleCheckInBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm:ss");
		EFmFmClientBranchPO eFmFmClientBranchPO = new EFmFmClientBranchPO();
		eFmFmClientBranchPO.setBranchId(branchId);
		int routeId = 0, empCount = 0, avaiable_seat = 0, seq_count = 0, loop_Count = vehicleDetails.size();
		boolean route_executed = false,escort_req = false,update_route=false;
		String shiftTime = "", tripType = "";
		try {
			for (Entry<Integer, Object> entry : vehicleDetails.entrySet()) {
				ArrayList xlvalues = (ArrayList) entry.getValue();
				loop_Count--;
				if ("RouteId".equalsIgnoreCase(xlvalues.get(1).toString().trim())) {
					seq_count = 0;
					if (route_executed) {
						vehicleAllocation(routeId, escort_req, empCount, tripType, avaiable_seat, eFmFmClientBranchPO,
								shiftTime);
						routeId=0;
					}
					if (!xlvalues.get(2).toString().replace(".0", " ").trim().equalsIgnoreCase("")) {
						int assignId = Integer.parseInt(xlvalues.get(2).toString().replace(".0", " ").trim());
						System.out.println("routeId" + assignId);
						List<EFmFmEmployeeTripDetailPO> noOfEmp = iCabRequestBO.getParticularTripAllEmployees(assignId);
						if (noOfEmp.size() > 0) {
							for (EFmFmEmployeeTripDetailPO tripDetails : noOfEmp) {
								EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO = new EFmFmEmployeeTravelRequestPO();
								eFmFmEmployeeTravelRequestPO.setBranchId(branchId);
								eFmFmEmployeeTravelRequestPO
										.setRequestId(tripDetails.geteFmFmEmployeeTravelRequest().getRequestId());
								List<EFmFmEmployeeTravelRequestPO> cabRequests = null;
								cabRequests = iCabRequestBO
										.getparticularRequestwithShiftTime(eFmFmEmployeeTravelRequestPO);
								cabRequests.get(0).setReadFlg("Y");
								iCabRequestBO.update(cabRequests.get(0));
							}
							avaiable_seat = noOfEmp.get(0).getEfmFmAssignRoute().getEfmFmVehicleCheckIn()
									.getEfmFmVehicleMaster().getCapacity();
							tripType = noOfEmp.get(0).geteFmFmEmployeeTravelRequest().getTripType();
							routeId = assignId;
							shiftTime = xlvalues.get(6).toString().trim();
							iCabRequestBO.deleteParticularTripDetailByRouteId(routeId);
							route_executed = true;
							escort_req = false;
							empCount = 0;

						} else {
							EFmFmAssignRoutePO assignRoutePO = new EFmFmAssignRoutePO();
							assignRoutePO.setAssignRouteId(assignId);
							assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
							List<EFmFmAssignRoutePO> assignCount = iCabRequestBO
									.getParticularDriverAssignTripDetail(assignRoutePO);
							if (assignCount.size() > 0) {
								routeId = assignId;
								tripType = assignCount.get(0).getTripType();
								shiftTime = xlvalues.get(6).toString().trim();
								avaiable_seat = assignCount.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster()
										.getCapacity();
							}
						}
					}
				} else {
					if (!xlvalues.get(1).toString().equalsIgnoreCase("EmployeeId") && routeId != 0) {
						String employeeId = xlvalues.get(1).toString().replace(".0", "").trim();
						String gender = xlvalues.get(3).toString().trim();
						String pickupTime = "";
						if (xlvalues.get(4).toString().trim().length() == 5) {
							pickupTime = xlvalues.get(4).toString().trim().concat(":00");
						} else {
							System.out.println("value" + xlvalues.get(4).toString().trim());
							pickupTime = shiftFormate.format(date_format.parse(xlvalues.get(4).toString()));
						}
						System.out.println("value -" + employeeId + " -- " + gender + " -- " + pickupTime);
						employeeAllocate(routeId, employeeId, tripType, branchId, pickupTime, shiftTime, seq_count++);
						empCount++;
						if (tripType.equalsIgnoreCase("PICKUP") && empCount == 1 && gender.equalsIgnoreCase("FEMALE")) {
							escort_req = true;
						} else if (tripType.equalsIgnoreCase("DROP") && gender.equalsIgnoreCase("FEMALE")) {
							escort_req = true;
						} else {
							escort_req = false;
						}

					}
					if (loop_Count == 0 && routeId != 0) {
						vehicleAllocation(routeId, escort_req, empCount, tripType, avaiable_seat, eFmFmClientBranchPO,
								shiftTime);
						routeId=0;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void employeeAllocate(int routeId, String employeeId, String tripType,int branchId,String pickupTime,String shiftTime,int seq_count) throws ParseException {
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");		
		EFmFmEmployeeTripDetailPO employeeTripDetailPO = new EFmFmEmployeeTripDetailPO();	
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm:ss");		
		DateFormat shiftDateFormate = new SimpleDateFormat("dd-MM-yyyy");		
		EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO = new EFmFmEmployeeTravelRequestPO();
		eFmFmEmployeeTravelRequestPO.setBranchId(branchId);		
		List<EFmFmEmployeeTravelRequestPO> cabRequests = null;				
		DateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
        Date  shiftReq_Date= dateTime.parse(shiftTime);     		 
		DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = shiftDateFormate.parse(shiftDateFormate.format(shiftReq_Date));		
		String convertedCurrentDate = outputFormat.format(date);		
		cabRequests = iCabRequestBO.getparticularEmployeeRequest(convertedCurrentDate, employeeId, tripType, branchId,shiftFormate.format(shiftReq_Date));
		if (cabRequests.size()>0){
			EFmFmAssignRoutePO assignRoutePO = new EFmFmAssignRoutePO();
			assignRoutePO.setAssignRouteId(routeId);		
			if (tripType.equalsIgnoreCase("DROP")) {
				employeeTripDetailPO.setTenMinuteMessageStatus("Y");
				employeeTripDetailPO.setTwoMinuteMessageStatus("Y");
				employeeTripDetailPO.setReachedFlg("Y");
				employeeTripDetailPO.setCabDelayMsgStatus("Y");
			} else {
				employeeTripDetailPO.setTenMinuteMessageStatus("N");
				employeeTripDetailPO.setTwoMinuteMessageStatus("N");
				employeeTripDetailPO.setReachedFlg("N");
				employeeTripDetailPO.setCabDelayMsgStatus("N");
			}
			employeeTripDetailPO.setActualTime(new Date());
			employeeTripDetailPO.setGoogleEta(0);
			employeeTripDetailPO.setBoardedFlg("N");
			employeeTripDetailPO.seteFmFmEmployeeTravelRequest(cabRequests.get(0));
			employeeTripDetailPO.setEfmFmAssignRoute(assignRoutePO);
			employeeTripDetailPO.setCurrenETA("0");		
			employeeTripDetailPO.setEmployeeStatus("allocated");
			List<EFmFmEmployeeTripDetailPO> allocationCount = iCabRequestBO.getparticularEmployeeTripDetails(
					convertedCurrentDate, employeeId, tripType, branchId, shiftFormate.format(shiftReq_Date));
			if (allocationCount.size()>0) {
				allocationCount.get(0).getEmpTripId();
				iCabRequestBO.deleteParticularTripDetail(allocationCount.get(0).getEmpTripId());				
			}
				iCabRequestBO.update(employeeTripDetailPO);
				cabRequests.get(0).setReadFlg("R");
				cabRequests.get(0).setDropSequence(seq_count);
				//System.out.println("pickupTime"+shiftFormate.format(pickupTime));				
				java.sql.Time myTime = java.sql.Time.valueOf(pickupTime);
				cabRequests.get(0).setPickUpTime(myTime);				
				iCabRequestBO.update(cabRequests.get(0));
					}
				}catch(Exception e){
					System.out.println("exception-Pcikup time xl sheet column Need to format the text"+e);
				}
			
		
	}	
	
	public void vehicleAllocation(int routeId, boolean escort_req, int empCount, String tripType, int avaiable_seat,EFmFmClientBranchPO eFmFmClientBranchPO, String shiftTime) throws ParseException{		
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		IAssignRouteBO iAssignRouteBO = (IAssignRouteBO) ContextLoader.getContext().getBean("IAssignRouteBO");
		boolean escort_required_time=false;
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm:ss");		
		DateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date  shiftReq_Date= dateTime.parse(shiftTime);        
		java.sql.Time currentShiftTime = java.sql.Time.valueOf(shiftFormate.format(shiftReq_Date));
		java.sql.Time startTime = java.sql.Time.valueOf("07:00:00");
		java.sql.Time Endtime = java.sql.Time.valueOf("19:00:00");	
		EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
		assignRoutePO.setAssignRouteId(routeId);
		assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		if(startTime.getTime()>=currentShiftTime.getTime() || Endtime.getTime() <=currentShiftTime.getTime()){
			
			escort_required_time=true;
		}		
		if(empCount==0){
				travelStatusReset(routeId, eFmFmClientBranchPO);	
				iAssignRouteBO.deleteParticularAssignRoute(routeId);		
		}else{			
			if(empCount >(avaiable_seat-1) || (empCount ==(avaiable_seat-1) && escort_required_time==true)){
				if(escort_required_time){
					empCount+=empCount;
				}				
				changeVehicleByCapacity(empCount,eFmFmClientBranchPO, escort_required_time, routeId,(avaiable_seat-1));				
				travelStatusReset(routeId, eFmFmClientBranchPO);
				
			}else if(empCount ==(avaiable_seat-1) && escort_required_time==false){						    			
				List<EFmFmAssignRoutePO>  assignCount =iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);
				assignCount.get(0).setVehicleStatus("F");
				iCabRequestBO.update(assignRoutePO);					
			}else if(empCount <(avaiable_seat-1)){				
				List<EFmFmEmployeeTripDetailPO>  empCountCount =iCabRequestBO.getParticularTripNonDropEmployeesDetails(routeId);
				int employeeCounts=0;
				if(empCountCount.size()>0){
						employeeCounts=empCountCount.size();
					if(empCountCount.get(0).getEfmFmAssignRoute().getEscortRequredFlag().equalsIgnoreCase("Y")){
						employeeCounts=empCountCount.size()+1;
					}
					
				}
				List<EFmFmAssignRoutePO>  capcity_Update =iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);				
				if(capcity_Update.size() >0){	
					EFmFmVehicleMasterPO vehicleDetails=iCabRequestBO.getVehicleDetail(capcity_Update.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
					vehicleDetails.setAvailableSeat((vehicleDetails.getCapacity()-1)-employeeCounts);
					vehicleDetails.setStatus("A");
					iVehicleCheckInBO.update(vehicleDetails);
				}		
			}
		}
		
	}	
	public void travelStatusReset(int routeId,EFmFmClientBranchPO eFmFmClientBranchPO){
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");	
		IApprovalBO iApprovalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");					
			EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
			assignRoutePO.setAssignRouteId(routeId);
			assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);		    			
			List<EFmFmAssignRoutePO>  assignCount =iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);
			if(assignCount.size() >0){				
				EFmFmVehicleCheckInPO eFmFmVehicleCheckInPO=new EFmFmVehicleCheckInPO();
				eFmFmVehicleCheckInPO.setCheckInId(assignCount.get(0).getEfmFmVehicleCheckIn().getCheckInId());				
				List<EFmFmVehicleCheckInPO> vehicleCheckin=iVehicleCheckInBO.getParticulaCheckedInVehicleDetails(eFmFmVehicleCheckInPO);
				vehicleCheckin.get(0).setStatus("Y");
				iVehicleCheckInBO.update(vehicleCheckin.get(0));
				
				List<EFmFmDeviceMasterPO> deviceDetails=iVehicleCheckInBO.getDeviceDetailsFromDeviceId(assignCount.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(), eFmFmClientBranchPO.getBranchId());
				deviceDetails.get(0).setStatus("Y");
				iVehicleCheckInBO.update(deviceDetails.get(0));				
				
				EFmFmVehicleMasterPO vehicleDetails=iCabRequestBO.getVehicleDetail(assignCount.get(0).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());
				vehicleDetails.setAvailableSeat(vehicleDetails.getCapacity()-1);
				vehicleDetails.setStatus("A");
				iVehicleCheckInBO.update(vehicleDetails);
				
				EFmFmDriverMasterPO driverDetails=iApprovalBO.getParticularDriverDetail(assignCount.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
				driverDetails.setStatus("A");
				iApprovalBO.update(driverDetails);
				
				if(assignCount.get(0).getEscortRequredFlag().equalsIgnoreCase("Y")){					
					EFmFmEscortMasterPO eFmFmEscortMasterPO=new EFmFmEscortMasterPO();					
					/*int escortCheckInId=assignCount.get(0).geteFmFmEscortCheckIn().getEscortCheckInId();					
					iVehicleCheckInBO.update(eFmFmEscortCheckInPO);					
					int escortId=assignCount.get(0).geteFmFmEscortCheckIn().geteFmFmEscortMaster().getEscortId();					
					List<EFmFmEscortMasterPO> escortDetails=iVehicleCheckInBO.getParticularEscortDetails(eFmFmEscortMasterPO);
					escortDetails.get(0).setIsActive("Y");
					iVehicleCheckInBO.updateEscortDetails(escortDetails.get(0));*/
				}	
			
				
    		}
		
		
	}
	
	
	
	public void changeVehicleByCapacity(int employeeSeatCapacity,EFmFmClientBranchPO eFmFmClientBranchPO, boolean escortRequired,int routeId, int vehicleSeatCapacity) {
		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");	
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm:ss");		
		SimpleDateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");		
		EFmFmEmployeeTripDetailPO employeeTripDetailPO = new EFmFmEmployeeTripDetailPO();
		EFmFmAssignRoutePO assignRoutePO=new EFmFmAssignRoutePO();
		assignRoutePO.setAssignRouteId(routeId);
		assignRoutePO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		int emp_no = 0;
		int employee_with_out_escort=0;
		boolean escort_Required=false;	
		List<EFmFmVehicleCheckInPO> allCheckInVehicles;		
			allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLargeCapacity(eFmFmClientBranchPO.getBranchId(),employeeSeatCapacity);			
			if(allCheckInVehicles.size()==0){
				//Splitting two vehicles
				List<EFmFmEmployeeTripDetailPO> listOfEmployees=iCabRequestBO.getParticularTripAllEmployees(routeId);
				int emp_count=listOfEmployees.size();
				for(EFmFmEmployeeTripDetailPO emp_list:listOfEmployees){
					boolean new_route_update=false;					
					vehicleSeatCapacity--;				
					emp_no++;
					emp_count--;
					if(escortRequired){	
						employeeSeatCapacity=employeeSeatCapacity-1;						
						if (emp_list.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("PICKUP") && emp_count == 1
								&& emp_list.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender()
										.equalsIgnoreCase("FEMALE")) {
							escort_Required = true;
							vehicleSeatCapacity--;
							if(vehicleSeatCapacity<0){
								new_route_update=true;
							}
							
						} else if (emp_list.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("DROP") && emp_list.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender()
										.equalsIgnoreCase("FEMALE") && vehicleSeatCapacity <=1 || emp_count==0) {
							escort_Required = true;							
							if(vehicleSeatCapacity<0){
								new_route_update=true;
							}								
						} else {
							escort_Required = false;
						}
						
					}
					
					if(new_route_update==true || vehicleSeatCapacity<0){						
						if(emp_list.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("PICKUP") && (employeeSeatCapacity-emp_no)>0){							
							allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLessCapacity(eFmFmClientBranchPO.getBranchId(),(employeeSeatCapacity-emp_no));
							vehicleSeatCapacity=allCheckInVehicles.get(0).getEfmFmVehicleMaster().getCapacity();
							int seat_Capacity=1;
							if(escortRequired==true && emp_list.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("FEMALE")){										
								seat_Capacity=2;
							}
							employeeSeatCapacity=employeeSeatCapacity-emp_no;
							emp_no=0;
							new_route_update=false;							
							routeId=VehicleAllocate(emp_list.geteFmFmEmployeeTravelRequest().getRequestId(), eFmFmClientBranchPO.getBranchId(),allCheckInVehicles,seat_Capacity);
							
						}else if(emp_list.getEfmFmAssignRoute().getTripType().equalsIgnoreCase("DROP") && (employeeSeatCapacity-emp_no)>0){
							allCheckInVehicles = iCabRequestBO.getAllCheckedInVehicleLessCapacity(eFmFmClientBranchPO.getBranchId(),(employeeSeatCapacity-emp_no));
							vehicleSeatCapacity=allCheckInVehicles.get(0).getEfmFmVehicleMaster().getCapacity();
							int seat_Capacity=1;
							if(escortRequired==true && emp_list.geteFmFmEmployeeTravelRequest().getEfmFmUserMaster().getGender().equalsIgnoreCase("FEMALE")){										
								seat_Capacity=2;
							}
							employeeSeatCapacity=employeeSeatCapacity-emp_no;
							emp_no=0;
							new_route_update=false;							
							routeId=VehicleAllocate(emp_list.geteFmFmEmployeeTravelRequest().getRequestId(), eFmFmClientBranchPO.getBranchId(),allCheckInVehicles,seat_Capacity);
							
						}
					}					
					EFmFmEmployeeTripDetailPO list=iCabRequestBO.ParticularTripDetail(emp_list.getEmpTripId());
					EFmFmAssignRoutePO efmFmAssignRoute=new EFmFmAssignRoutePO();
					efmFmAssignRoute.setAssignRouteId(routeId);
					list.setEfmFmAssignRoute(efmFmAssignRoute);
					iCabRequestBO.update(list);		
				}				
				
			}	
			
		if (!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0) {
			
			EFmFmVehicleMasterPO updateVehicleStatus = iCabRequestBO.getVehicleDetail(allCheckInVehicles.get(0).getEfmFmVehicleMaster().getVehicleId());
			updateVehicleStatus.setStatus("A");
			updateVehicleStatus.setAvailableSeat(updateVehicleStatus.getCapacity() -1);
			iVehicleCheckInBO.update(updateVehicleStatus);			
			EFmFmDriverMasterPO particularDriverDetails = approvalBO.getParticularDriverDetail(allCheckInVehicles.get(0).getEfmFmDriverMaster().getDriverId());
			particularDriverDetails.setStatus("A");
			approvalBO.update(particularDriverDetails);
			List<EFmFmDeviceMasterPO> deviceDetails = iVehicleCheckInBO.getDeviceDetailsFromDeviceId(allCheckInVehicles.get(0).geteFmFmDeviceMaster().getDeviceId(), eFmFmClientBranchPO.getBranchId());
			deviceDetails.get(0).setStatus("Y");
			
			iVehicleCheckInBO.update(deviceDetails.get(0));			
			if (!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0) {			
				List<EFmFmAssignRoutePO>  assignRoute =iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);			
				EFmFmVehicleCheckInPO vehicleCheckInPO = new EFmFmVehicleCheckInPO();
				vehicleCheckInPO.setCheckInId(((EFmFmVehicleCheckInPO) allCheckInVehicles.get(allCheckInVehicles.size() - 1)).getCheckInId());				
				assignRoute.get(0).setEfmFmVehicleCheckIn(vehicleCheckInPO);
				EFmFmRouteAreaMappingPO routeAreaMapping = new EFmFmRouteAreaMappingPO();			
				assignRoute.get(0).seteFmFmRouteAreaMapping(routeAreaMapping);
				if (escortRequired == true){
					assignRoute.get(0).setEscortRequredFlag("Y");					
					List<EFmFmEscortCheckInPO> escortList = iCabRequestBO.getAllCheckedInEscort(eFmFmClientBranchPO.getBranchId());
						if (!escortList.isEmpty() || escortList.size() != 0) {
							EFmFmEscortCheckInPO checkInEscort = new EFmFmEscortCheckInPO();
							checkInEscort.setEscortCheckInId(((EFmFmEscortCheckInPO) escortList.get(0)).getEscortCheckInId());
							assignRoute.get(0).seteFmFmEscortCheckIn(checkInEscort);
							((EFmFmEscortCheckInPO) escortList.get(0)).setStatus("N");
							iVehicleCheckInBO.update((EFmFmEscortCheckInPO) escortList.get(0));
						}
				}
				else{
					assignRoute.get(0).setEscortRequredFlag("N");
				}
				assignRoute.get(0).setAllocationMsg("N");				
				assignRoute.get(0).setTripStatus("allocated");
				assignRoute.get(0).setTripAssignDate(new Date());
				assignRoute.get(0).setVehicleStatus("A");
				assignRoute.get(0).setBucketStatus("N");				
				((EFmFmVehicleCheckInPO) allCheckInVehicles.get(allCheckInVehicles.size() - 1)).setStatus("N");
				iVehicleCheckInBO.update((EFmFmVehicleCheckInPO) allCheckInVehicles.get(allCheckInVehicles.size() - 1));
				iCabRequestBO.update(assignRoute.get(0));			
			}
		}		
		List<EFmFmAssignRoutePO>  assignRoute =iCabRequestBO.getParticularDriverAssignTripDetail(assignRoutePO);
		if (assignRoute.size() > 0) {			
			EFmFmVehicleMasterPO vehicleMaster = iCabRequestBO.getVehicleDetail(((EFmFmAssignRoutePO)assignRoute.get(0)).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());			
			vehicleMaster.setAvailableSeat((vehicleMaster.getCapacity()-1)-employeeSeatCapacity);
			vehicleMaster.setStatus("allocated");
			iVehicleCheckInBO.update(vehicleMaster);
			EFmFmDriverMasterPO particularDriverDetails = approvalBO.getParticularDriverDetail(
					assignRoute.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
			particularDriverDetails.setStatus("allocated");
			approvalBO.update(particularDriverDetails);
			List<EFmFmDeviceMasterPO> deviceDetails = iVehicleCheckInBO.getDeviceDetailsFromDeviceId(
					assignRoute.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(),eFmFmClientBranchPO.getBranchId());
			deviceDetails.get(0).setStatus("allocated");
			iVehicleCheckInBO.update(deviceDetails.get(0));
		}	
		
	}
	
	public int VehicleAllocate(int employeeReqId, int BranchId, List<EFmFmVehicleCheckInPO> allCheckInVehicles, int seat_Capacity) {

		ICabRequestBO iCabRequestBO = (ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");	
		DateFormat shiftFormate = new SimpleDateFormat("HH:mm:ss");		
		SimpleDateFormat dateformate = new SimpleDateFormat("dd-MM-yyyy");		
		EFmFmEmployeeTripDetailPO employeeTripDetailPO = new EFmFmEmployeeTripDetailPO();
		int routeId = 0;
		List<EFmFmAssignRoutePO> assignRoutePO;
		EFmFmEmployeeTravelRequestPO eFmFmEmployeeTravelRequestPO = new EFmFmEmployeeTravelRequestPO();
		eFmFmEmployeeTravelRequestPO.setBranchId(BranchId);
		eFmFmEmployeeTravelRequestPO.setRequestId(employeeReqId);
		List<EFmFmEmployeeTravelRequestPO> cabRequests = null;
		cabRequests = iCabRequestBO.getparticularRequestwithShiftTime(eFmFmEmployeeTravelRequestPO);		
		if (!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0) {
			EFmFmVehicleMasterPO updateVehicleStatus = iCabRequestBO
					.getVehicleDetail(allCheckInVehicles.get(0).getEfmFmVehicleMaster().getVehicleId());
			updateVehicleStatus.setStatus("A");
			updateVehicleStatus.setAvailableSeat(updateVehicleStatus.getCapacity() -1);
			iVehicleCheckInBO.update(updateVehicleStatus);			
			EFmFmDriverMasterPO particularDriverDetails = approvalBO.getParticularDriverDetail(allCheckInVehicles.get(0).getEfmFmDriverMaster().getDriverId());
			particularDriverDetails.setStatus("A");
			approvalBO.update(particularDriverDetails);
			List<EFmFmDeviceMasterPO> deviceDetails = iVehicleCheckInBO.getDeviceDetailsFromDeviceId(
					allCheckInVehicles.get(0).geteFmFmDeviceMaster().getDeviceId(), BranchId);
			deviceDetails.get(0).setStatus("Y");
			iVehicleCheckInBO.update(deviceDetails.get(0));
			EFmFmAssignRoutePO eAssignRoutePO = new EFmFmAssignRoutePO();
			if (!allCheckInVehicles.isEmpty() && allCheckInVehicles.size() != 0) {
				EFmFmAssignRoutePO assignRoute = new EFmFmAssignRoutePO();
				EFmFmVehicleCheckInPO vehicleCheckInPO = new EFmFmVehicleCheckInPO();
				vehicleCheckInPO.setCheckInId(((EFmFmVehicleCheckInPO) allCheckInVehicles.get(allCheckInVehicles.size() - 1)).getCheckInId());				
				assignRoute.setEfmFmVehicleCheckIn(vehicleCheckInPO);
				EFmFmRouteAreaMappingPO routeAreaMapping = new EFmFmRouteAreaMappingPO();
				routeAreaMapping.setRouteAreaId(cabRequests.get(0).geteFmFmRouteAreaMapping().getRouteAreaId());
				assignRoute.seteFmFmRouteAreaMapping(routeAreaMapping);			
				assignRoute.setAllocationMsg("N");
				assignRoute.setShiftTime(cabRequests.get(0).getShiftTime());
				assignRoute.setTripStatus("allocated");
				assignRoute.setTripAssignDate(new Date());
				assignRoute.setVehicleStatus("A");
				assignRoute.setBucketStatus("N");
				EFmFmClientBranchPO eFmFmClientBranchPO = new EFmFmClientBranchPO();
				eFmFmClientBranchPO.setBranchId(cabRequests.get(0).geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO().getBranchId());
				assignRoute.seteFmFmClientBranchPO(eFmFmClientBranchPO);
				assignRoute.setTripType(cabRequests.get(0).getTripType());
					((EFmFmVehicleCheckInPO) allCheckInVehicles.get(allCheckInVehicles.size() - 1)).setStatus("N");
					iVehicleCheckInBO.update((EFmFmVehicleCheckInPO) allCheckInVehicles.get(allCheckInVehicles.size() - 1));
				iCabRequestBO.update(assignRoute);
				cabRequests.get(0).setReadFlg("R");				
				iCabRequestBO.update(cabRequests.get(0));
			}
		}
		assignRoutePO = iCabRequestBO.getCreatedAssignRoute(
				cabRequests.get(0).geteFmFmEmployeeRequestMaster().getEfmFmUserMaster().geteFmFmClientBranchPO()
						.getBranchId(),
				cabRequests.get(0).geteFmFmEmployeeRequestMaster().getTripType(), cabRequests.get(0).getShiftTime());
		if (assignRoutePO.size() > 0) {
			EFmFmVehicleMasterPO vehicleMaster = iCabRequestBO.getVehicleDetail(((EFmFmAssignRoutePO)assignRoutePO.get(0)).getEfmFmVehicleCheckIn().getEfmFmVehicleMaster().getVehicleId());			
			vehicleMaster.setAvailableSeat((vehicleMaster.getCapacity()-1)-seat_Capacity);
			vehicleMaster.setStatus("allocated");
			iVehicleCheckInBO.update(vehicleMaster);
			EFmFmDriverMasterPO particularDriverDetails = approvalBO.getParticularDriverDetail(
					assignRoutePO.get(0).getEfmFmVehicleCheckIn().getEfmFmDriverMaster().getDriverId());
			particularDriverDetails.setStatus("allocated");
			approvalBO.update(particularDriverDetails);
			List<EFmFmDeviceMasterPO> deviceDetails = iVehicleCheckInBO.getDeviceDetailsFromDeviceId(
					assignRoutePO.get(0).getEfmFmVehicleCheckIn().geteFmFmDeviceMaster().getDeviceId(),BranchId);
			deviceDetails.get(0).setStatus("allocated");
			iVehicleCheckInBO.update(deviceDetails.get(0));
			
			System.out.println(assignRoutePO.get(0).getAssignRouteId());
			routeId = assignRoutePO.get(0).getAssignRouteId();
		}
		return routeId;
	}
	
	
	
}