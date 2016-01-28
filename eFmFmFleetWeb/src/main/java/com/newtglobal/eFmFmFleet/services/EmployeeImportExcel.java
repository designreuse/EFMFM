package com.newtglobal.eFmFmFleet.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.ICabRequestBO;
import com.newtglobal.eFmFmFleet.business.bo.IEmployeeDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IRouteDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IUserMasterBO;
import com.newtglobal.eFmFmFleet.eFmFmFleet.CalculateDistance;
import com.newtglobal.eFmFmFleet.model.EFmFmAreaMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientProjectDetailsPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientRouteMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientUserRolePO;
import com.newtglobal.eFmFmFleet.model.EFmFmEmployeeRequestMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRoleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmUserMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmZoneMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/xlUtilityEmployeeUpload")
public class EmployeeImportExcel {	
	/*
	 * @Reading employee details from employee_master xl utility.
	 * @Stored all the values on Arraylist.
	 * @author  Rajan R
	 * @since   2015-05-12 
	 */
	@POST
	@Path("/employeeRecord")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response upload(
			@FormDataParam("filename") InputStream uploadedInputStream,
			@FormDataParam("filename") FormDataContentDisposition fileDetail,
			@QueryParam("branchId") int branchId,			
			@Context HttpServletRequest request) throws ParseException, IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(uploadedInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();			
			while (rowIterator.hasNext()) {
				ArrayList<Object> columnValue = new ArrayList<Object>();
				Row row = rowIterator.next();
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
							SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm z");
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
					employeeRecord(columnValue,branchId);
				}
					
			}

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
	}
	/*
	 * @Reading employee Trip Request details from employee_request xl utility.
	 * @Stored all the values on Arraylist.
	 * @author  Rajan R
	 * @since   2015-05-12 
	 */
	@POST
	@Path("/employeeTravelTripRequest")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response employeeTravelRequest(
			@FormDataParam("filename") InputStream uploadedInputStream,
			@FormDataParam("filename") FormDataContentDisposition fileDetail,
			@QueryParam("branchId") int branchId,
			@QueryParam("profileId") int profileId,
			@QueryParam("userRole") String userRole,
			@Context HttpServletRequest request) throws ParseException, IOException {		
		try {					
			XSSFWorkbook workbook = new XSSFWorkbook(uploadedInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();			
			while (rowIterator.hasNext()) {
				ArrayList<Object> columnValue = new ArrayList<Object>();
				Row row = rowIterator.next();
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
							SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm z");
							columnValue.add(df.format(date));
						}else {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							columnValue.add(cell.getStringCellValue());	
						}
						break;
					case Cell.CELL_TYPE_STRING:
						columnValue.add(cell.getStringCellValue().toString());
						break;
					case Cell.CELL_TYPE_FORMULA:
						columnValue.add("");
						break;
					default:
						columnValue.add("");
						break;
					}
				}
				columnValue.removeAll(Arrays.asList(null,""));
				if(columnValue.size()>1){					
					employeeTravelTripDetails(columnValue,branchId,userRole,profileId);
				}
					
			}

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
	}
	
	
	
	/*
	 * ERP DATABASE Integration
	 * 
	 */
	@POST
	@Path("/sqlServerConnection")
	@Consumes("application/json")
	@Produces("application/json")
	public Response SqlServerConnection(EFmFmClientBranchPO  eFmFmClientBranchPO) throws ParseException, ClassNotFoundException, SQLException, InvalidKeyException, NoSuchAlgorithmException, IOException, URISyntaxException{
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm z");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//Connection conn = DriverManager.getConnection(url, userName, password);		
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://172.16.25.31:1433;databaseName=MicrosoftDynamicsAX_LIVE;integratedSecurity=true");
		//Data Source=172.16.25.31,1433;Network Library=DBMSSOCN;Initial Catalog=MicrosoftDynamicsAX_LIVE;User ID=traveluser;Password=$ervion!23;		
		Statement sta = conn.createStatement();
		String Sql = "select * "
				+ "from SGSL_HCMEMPLOYEEDETAILS e, SGSL_PROJECTMASTERDETAILS p, SGSL_PROJRESOURCEALLOCATIONDETAILS a "
				+ "where e.EmployeeID=a.EmployeeID "
				+ "and a.PROJID=p.PROJID "
				+ "AND cast(a.ALLOCATIONTODATE as date) >= cast(GETDATE() as date)";
				/*+ "AND cast(a.ALLOCATIONFROMDATE as date) >= cast(GETDATE() as date) "*/				
		ResultSet rs = sta.executeQuery(Sql);
		while (rs.next()) {
			ArrayList<Object> columnValue = new ArrayList<Object>();			
			columnValue.add(rs.getString("EmployeeID"));
			columnValue.add(rs.getString("EmployeeName"));		
			columnValue.add(rs.getString("EmployeeMiddleName"));			
			columnValue.add(rs.getString("EmployeeLastName"));	
			columnValue.add(rs.getString("Gender"));
			columnValue.add(rs.getString("EmployeeLocation"));
			columnValue.add(rs.getString("EmployeeDEPT"));
			columnValue.add(rs.getString("EmployeeDomain"));
			columnValue.add(rs.getString("Phone"));
			columnValue.add(rs.getString("EmailID"));
			//destination area	
			columnValue.add("Adyar");
			//
			columnValue.add(rs.getString("STATE"));
			columnValue.add(rs.getString("CITY"));
			columnValue.add(rs.getString("ZIPCODE"));			
			columnValue.add(rs.getString("ADDRESS"));
			columnValue.add(rs.getString("EmployeeDesignation"));
			columnValue.add(rs.getString("PROJID"));
			columnValue.add(rs.getString("PROJNAME"));
			//df.format(""ALLOCATIONFROMDATE);			
			columnValue.add(format.format(df.parse((rs.getString("ALLOCATIONFROMDATE")))));
			columnValue.add(format.format(df.parse((rs.getString("ALLOCATIONTODATE")))));
			//Physically challenged
			columnValue.add("N");
			//Role
			columnValue.add("webuser");
			employeeRecord(columnValue, eFmFmClientBranchPO.getBranchId());			
		}
		conn.close();
		
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
		
	}
	
	/*
	 * @xl utility row values are inserted into employeeTravelrequest table table. 
	 * validation also be handle here.
	 * @author  Rajan R
	 * @since   2015-05-12 to 13-05-2015
	 */
	private void employeeTravelTripDetails(ArrayList<Object> columnValue,int branchId,String userRole,int profileId) throws ParseException {
		IEmployeeDetailBO iEmployeeDetailBO = (IEmployeeDetailBO) ContextLoader	.getContext().getBean("IEmployeeDetailBO");
		ICabRequestBO iCabRequestBO=(ICabRequestBO) ContextLoader.getContext().getBean("ICabRequestBO");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat  timeformate = new SimpleDateFormat("MM/dd/yyyy HH:mm");	
		DateFormat  shifTimeFormate = new SimpleDateFormat("HH:mm");		 
		String executionStatus="N";
		 try
         {			 
			if(columnValue.get(0).toString() !="" && columnValue.get(1).toString() !="" && columnValue.get(2).toString() !="" && columnValue.get(3).toString() !="" && columnValue.get(4).toString() !=""){
				if(columnValue.get(1).toString().trim().equalsIgnoreCase("PICKUP")){
					if(columnValue.get(5).toString().trim() !=""){
						executionStatus="Y";
					}
				}else{
					executionStatus="Y";
				}				
			if(executionStatus.equalsIgnoreCase("Y")){					
			 EFmFmEmployeeRequestMasterPO employeeRequestPO = new EFmFmEmployeeRequestMasterPO();
			 List<EFmFmUserMasterPO> employeeDetails=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(columnValue.get(0).toString(),branchId);				
			 List<EFmFmUserMasterPO> loggedInUserDetail=iEmployeeDetailBO.getParticularEmpDetailsFromUserId(profileId,branchId);							 
			 String shiftTimeInDateTimeFormate=columnValue.get(2).toString();
			 String  shifTimeSplit[]=shiftTimeInDateTimeFormate.split("\\s+"); 
			 java.sql.Time theTime = new java.sql.Time(shifTimeFormate.parse(shifTimeSplit[1]).getTime());
			 if(userRole.equalsIgnoreCase("manager")){
				 if(employeeDetails.get(0).geteFmFmClientProjectDetails().getProjectId()==loggedInUserDetail.get(0).geteFmFmClientProjectDetails().getProjectId()) {					
				 employeeRequestPO.setEfmFmUserMaster(employeeDetails.get(0));
				 employeeRequestPO.setEmployeeId(columnValue.get(0).toString());
				 employeeRequestPO.setTripType(columnValue.get(1).toString());
				 employeeRequestPO.setRequestDate(new Date());	
					Date startDate  = (Date) dateFormat.parse(columnValue.get(3).toString());					
					Date endDate  = (Date) timeformate.parse(columnValue.get(4).toString());	
					if(employeeRequestPO.getTripType().equalsIgnoreCase("PICKUP")){
						 String shiftPickUpTime=columnValue.get(5).toString();
						 String  shifPickUpTimeSplit[]=shiftPickUpTime.split("\\s+"); 						
						 java.sql.Time pickUpTime = new java.sql.Time(shifTimeFormate.parse(shifPickUpTimeSplit[1]).getTime());
						 employeeRequestPO.setPickUpTime(pickUpTime);	
					}	
					if(employeeRequestPO.getTripType().equalsIgnoreCase("DROP")){
						employeeRequestPO.setDropSequence(1);
					}
				 	employeeRequestPO.setShiftTime(theTime);				 	
				 	employeeRequestPO.setTripRequestStartDate(startDate);				 	
				 	employeeRequestPO.setTripRequestEndDate(endDate);			 	
				 	employeeRequestPO.setStatus("Y");	
				 	employeeRequestPO.setReadFlg("Y");
				 	employeeRequestPO.setRequestFrom("E");
				 	employeeRequestPO.setRequestType("normal");
				 	

				    /*
					 * getting latitute and Longitute based on the employee address.
					 */
					if(employeeDetails.size() >0){	
						for(EFmFmUserMasterPO empLatLng:employeeDetails){
							/*
							 * Data Duplication Validation handled here
							 */
							List<EFmFmEmployeeRequestMasterPO>  empTravelRequest=iCabRequestBO.travelRequestExist(columnValue.get(0).toString(), columnValue.get(1).toString(), branchId, "normal");
							if(empTravelRequest.size()==0){
								iCabRequestBO.save(employeeRequestPO);
							}else{
									empTravelRequest.get(0).setStatus("N");
									empTravelRequest.get(0).setReadFlg("N");
									empTravelRequest.get(0).setActulaTripEndDate(employeeRequestPO.getTripRequestStartDate());															
									iCabRequestBO.update(empTravelRequest.get(0));
									iCabRequestBO.save(employeeRequestPO);									
							}
						}
					}
				 }	
	         }
			 else{
				 if(employeeDetails.size() >0) {
				 employeeRequestPO.setEfmFmUserMaster(employeeDetails.get(0));
				 employeeRequestPO.setTripType(columnValue.get(1).toString());
				 employeeRequestPO.setEmployeeId(columnValue.get(0).toString());
				 employeeRequestPO.setRequestDate(new Date());	
				 Date startDate  = (Date) dateFormat.parse(columnValue.get(3).toString());					
				 Date endDate  = (Date) timeformate.parse(columnValue.get(4).toString());	
					if(employeeRequestPO.getTripType().equalsIgnoreCase("PICKUP")){
						 String shiftPickUpTime=columnValue.get(5).toString();
						 String  shifPickUpTimeSplit[]=shiftPickUpTime.split("\\s+"); 						
						 java.sql.Time pickUpTime = new java.sql.Time(shifTimeFormate.parse(shifPickUpTimeSplit[1]).getTime());
						 employeeRequestPO.setPickUpTime(pickUpTime);	
					}				 		
				 	employeeRequestPO.setShiftTime(theTime);
				 	employeeRequestPO.setTripRequestStartDate(startDate);				 	
				 	employeeRequestPO.setTripRequestEndDate(endDate);
				 	employeeRequestPO.setStatus("Y");	
				 	employeeRequestPO.setReadFlg("Y");
				 	employeeRequestPO.setRequestFrom("E");	
				 	employeeRequestPO.setRequestType("normal");

				    /*
					 * getting latitute and Longitute based on the employee address.
					 */
					if(employeeDetails.size() >0){
						for(EFmFmUserMasterPO empLatLng:employeeDetails){
							/*
							 * Data Duplication Validation handled here
							 */
							List<EFmFmEmployeeRequestMasterPO>  empTravelRequest=iCabRequestBO.travelRequestExist(columnValue.get(0).toString(), columnValue.get(1).toString(), branchId, "normal");
							if(empTravelRequest.size()==0){	
								iCabRequestBO.save(employeeRequestPO);
							}else{								
								if(employeeRequestPO.getTripRequestStartDate().before(empTravelRequest.get(0).getTripRequestEndDate())){									
									empTravelRequest.get(0).setStatus("N");
									empTravelRequest.get(0).setReadFlg("N");
									empTravelRequest.get(0).setActulaTripEndDate(employeeRequestPO.getTripRequestStartDate());															
									iCabRequestBO.update(empTravelRequest.get(0));
									iCabRequestBO.save(employeeRequestPO);
									
								}
								
							}
						}
					}
				 }
			 }
				}
			 }
         }
         catch(IndexOutOfBoundsException e){
        	 e.printStackTrace();
         }		
		
	}
	/*
	 * @xl utility row values are inserted into employee master table table. 
	 * validation also be handle here.
	 * Fetching the AreaId from based on the employee areaName from efmfmareamaster Table.
	 * @author  Rajan R
	 * @since   2015-05-12 
	 */
	private  void employeeRecord(ArrayList<Object> columnValue,int branchId) throws ParseException, InvalidKeyException, NoSuchAlgorithmException, IOException, URISyntaxException {				
		IEmployeeDetailBO iEmployeeDetailBO = (IEmployeeDetailBO) ContextLoader.getContext().getBean("IEmployeeDetailBO");
		IUserMasterBO iUserMasterBO = (IUserMasterBO) ContextLoader.getContext().getBean("IUserMasterBO");
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		 try
         {
			    EFmFmUserMasterPO employeeDetailsPO = new EFmFmUserMasterPO();
			    EFmFmClientProjectDetailsPO eFmFmClientProjectDetailsPO = new EFmFmClientProjectDetailsPO();			    
			 	employeeDetailsPO.setEmployeeId(columnValue.get(0).toString().trim());
			 	employeeDetailsPO.setUserName(columnValue.get(22).toString().trim());			 	
			 	employeeDetailsPO.setPassword(columnValue.get(0).toString().trim());			 	
			 	employeeDetailsPO.setFirstName(columnValue.get(1).toString());
			 	employeeDetailsPO.setMiddleName(columnValue.get(2).toString());
			 	employeeDetailsPO.setLastName(columnValue.get(3).toString());			 				 	
			 	employeeDetailsPO.setGender(columnValue.get(4).toString());			 	
			 	employeeDetailsPO.setEmployeeBusinessUnit(columnValue.get(5).toString());
			 	employeeDetailsPO.setEmployeeDepartment(columnValue.get(6).toString());			 	
			 	employeeDetailsPO.setEmployeeDomain(columnValue.get(7).toString());
			 	employeeDetailsPO.setMobileNumber(columnValue.get(8).toString());
			 	employeeDetailsPO.setEmailId(columnValue.get(9).toString());			 	
			 	employeeDetailsPO.setStateName(columnValue.get(11).toString());
			 	employeeDetailsPO.setCityName(columnValue.get(12).toString());
			 	employeeDetailsPO.setPinCode(Integer.parseInt(columnValue.get(13).toString()));
			 	employeeDetailsPO.setAddress(columnValue.get(14).toString());
			 	employeeDetailsPO.setEmployeeDesignation(columnValue.get(15).toString());
			 	employeeDetailsPO.setCreationTime(new Date());
			 	employeeDetailsPO.setUpdatedTime(new Date());			 	
			 	eFmFmClientProjectDetailsPO.setClientProjectId(columnValue.get(16).toString());
			 	eFmFmClientProjectDetailsPO.setEmployeeProjectName(columnValue.get(17).toString());			 	
			 	eFmFmClientProjectDetailsPO.setProjectAllocationStarDate((Date)dateFormat.parse(columnValue.get(18).toString()));
			 	eFmFmClientProjectDetailsPO.setProjectAllocationEndDate((Date)dateFormat.parse(columnValue.get(19).toString()));			 	
			 	employeeDetailsPO.setPhysicallyChallenged(columnValue.get(20).toString()); 	
			 	employeeDetailsPO.setWeekOffDays(columnValue.get(24).toString());
			 	EFmFmClientBranchPO eFmFmClientBranch=new EFmFmClientBranchPO();
			 	eFmFmClientBranch.setBranchId(branchId);
			    employeeDetailsPO.seteFmFmClientBranchPO(eFmFmClientBranch);
			 	employeeDetailsPO.setStatus("Y");		 	
			 	employeeDetailsPO.setLocationStatus("N");
			 	employeeDetailsPO.setUserType("employee");
			 	/* 
			 	 * Generating latitute & longitute based on the Address
			 	 */
			 	eFmFmClientProjectDetailsPO.seteFmFmClientBranchPO(eFmFmClientBranch);
			 	
			 	/*
			 	 * Project Id Already Exit
			 	 * 
			 	 */
			 	List<EFmFmClientProjectDetailsPO> projectIdExist=iEmployeeDetailBO.getProjectDetails(eFmFmClientProjectDetailsPO.getClientProjectId().toUpperCase(),branchId);
			 	if(projectIdExist.size()==0){
			 		iEmployeeDetailBO.save(eFmFmClientProjectDetailsPO);
			 		List<EFmFmClientProjectDetailsPO> projectDetails=iEmployeeDetailBO.getProjectDetails(eFmFmClientProjectDetailsPO.getClientProjectId().toUpperCase(),branchId);
			 		if(projectDetails.size()>0){			 			
			 			//eFmFmClientProjectDetailsPO.setProjectId(projectDetails.get(0).getProjectId());
			 			employeeDetailsPO.seteFmFmClientBranchPO(eFmFmClientBranch);
			 			employeeDetailsPO.seteFmFmClientProjectDetails(projectDetails.get(0));
				 		}
			 	}else{
			 		//eFmFmClientProjectDetailsPO.setProjectId(projectIdExist.get(0).getProjectId());
			 		employeeDetailsPO.seteFmFmClientBranchPO(eFmFmClientBranch);
			 		employeeDetailsPO.seteFmFmClientProjectDetails(projectIdExist.get(0));			 		
			 	}
			 	String latlng = iRouteDetailBO.getGeoCode(employeeDetailsPO.getAddress());
			 	
			 	if (!latlng.trim().equalsIgnoreCase("unknown")){					
					employeeDetailsPO.setLatitudeLongitude(latlng);
					CalculateDistance empDistance=new CalculateDistance();
					List<EFmFmClientBranchPO> clientBranchDetails=iUserMasterBO.getClientDetails(branchId);
					try{
					employeeDetailsPO.setDistance(empDistance.employeeDistanceCalculation(clientBranchDetails.get(0).getLatitudeLongitude(), latlng));
					}catch(Exception e){}
					}
			 	
			 	/*
			 	 * Checking Area is exist or not if not adding in to the system
			 	 */
			 	List<EFmFmAreaMasterPO> areaNameCheck=iRouteDetailBO.getAllAreaName(columnValue.get(10).toString().trim().toUpperCase().replaceAll("\\s", ""));
				if(areaNameCheck.isEmpty() || areaNameCheck.size() ==0){
					EFmFmAreaMasterPO areaMasterPO=new EFmFmAreaMasterPO();
					areaMasterPO.setAreaDescription(columnValue.get(10).toString().trim().toUpperCase().replaceAll("\\s", ""));
					areaMasterPO.setAreaName(columnValue.get(10).toString().trim().toUpperCase().replaceAll("\\s", ""));
					iRouteDetailBO.saveAreaRecord(areaMasterPO);	
					areaNameCheck=iRouteDetailBO.getAllAreaName(columnValue.get(10).toString().trim().toUpperCase().replaceAll("\\s", ""));
				}
			 	/*
			 	 * Checking Zone is exist or not if not adding in to the system
			 	 */
			 	List<EFmFmZoneMasterPO> routeNameCheck=iRouteDetailBO.getAllRouteName(columnValue.get(23).toString().trim().toUpperCase().replaceAll("\\s", ""));
				if(routeNameCheck.isEmpty() || routeNameCheck.size() ==0){
					EFmFmZoneMasterPO zoneDetails =new EFmFmZoneMasterPO();
					zoneDetails.setZoneName(columnValue.get(23).toString().trim().toUpperCase().replaceAll("\\s", ""));
					zoneDetails.setStatus("Y");
					zoneDetails.setCreationTime(new Date());
					zoneDetails.setUpdatedTime(new Date());
					iRouteDetailBO.saveRouteNameRecord(zoneDetails);
					EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO=new EFmFmClientRouteMappingPO();
				 	List<EFmFmZoneMasterPO> routeName=iRouteDetailBO.getAllRouteName(columnValue.get(23).toString().trim().toUpperCase().replaceAll("\\s", ""));
					eFmFmClientRouteMappingPO.seteFmFmClientBranchPO(eFmFmClientBranch);
					eFmFmClientRouteMappingPO.seteFmFmZoneMaster(routeName.get(0));
					iRouteDetailBO.saveClientRouteMapping(eFmFmClientRouteMappingPO);
					routeNameCheck=iRouteDetailBO.getAllRouteName(columnValue.get(23).toString().trim().toUpperCase().replaceAll("\\s", ""));
				}
				/* 
				 * Fetching AreaId from efmfmareamaster table.
				 */
				EFmFmRouteAreaMappingPO routeAreaDetails = iRouteDetailBO.getRouteAreaId(columnValue.get(10).toString().trim().toUpperCase().replaceAll("\\s", ""),branchId,columnValue.get(23).toString().trim().toUpperCase().replaceAll("\\s", ""));				
				if (routeAreaDetails ==null) {	
					EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO=new EFmFmRouteAreaMappingPO();
					eFmFmRouteAreaMappingPO.setEfmFmAreaMaster(areaNameCheck.get(0));
					eFmFmRouteAreaMappingPO.seteFmFmZoneMaster(routeNameCheck.get(0));
					iRouteDetailBO.saveRouteMappingDetails(eFmFmRouteAreaMappingPO);
					routeAreaDetails = iRouteDetailBO.getRouteAreaId(columnValue.get(10).toString().trim().toUpperCase().replaceAll("\\s", ""),branchId,columnValue.get(23).toString().trim().toUpperCase().replaceAll("\\s", ""));
					employeeDetailsPO.seteFmFmRouteAreaMapping(routeAreaDetails);
				}
				else {					
					employeeDetailsPO.seteFmFmRouteAreaMapping(routeAreaDetails);
				}
				/*
				 * employee Record already existing on table.
				 */
				String userRole=columnValue.get(21).toString().toUpperCase();
				if(!employeeDetailsPO.getEmployeeId().isEmpty() && employeeDetailsPO.getEmployeeId() !=null && employeeDetailsPO.getEmployeeId() !="" 
						&& employeeDetailsPO.getEmailId() !="" && !employeeDetailsPO.getEmailId().isEmpty() && employeeDetailsPO.getMobileNumber() !="" 
						&& !employeeDetailsPO.getMobileNumber().isEmpty() && routeAreaDetails !=null /*&& !latlng.trim().equalsIgnoreCase("unknown")*/){					
				List<EFmFmUserMasterPO> employeeIdExist=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(employeeDetailsPO.getEmployeeId(),branchId);				
				if(employeeIdExist.size() ==0){
					List<EFmFmUserMasterPO> employeeMobileNo=iEmployeeDetailBO.getEmpMobileNoDetails(employeeDetailsPO.getMobileNumber().trim(), branchId);
					if(employeeMobileNo.size() ==0){
						List<EFmFmUserMasterPO> employeeEmailId=iEmployeeDetailBO.getParticularEmployeeDetailsFromEmailId(employeeDetailsPO.getEmailId(), branchId);
						if(employeeEmailId.size() ==0){
							if(userRole.equalsIgnoreCase("manager")){
							    List<EFmFmUserMasterPO> roleExist=iUserMasterBO.getUsersRoleExist(branchId, employeeDetailsPO.geteFmFmClientProjectDetails().getClientProjectId(), userRole.trim());
								if(roleExist.size()>0){
									userRole="webuser";
									iEmployeeDetailBO.save(employeeDetailsPO);
								}else{
									iEmployeeDetailBO.save(employeeDetailsPO);
								}
							}else{
								iEmployeeDetailBO.save(employeeDetailsPO);
							}	
							List<EFmFmUserMasterPO> employeeIdDetails=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(employeeDetailsPO.getEmployeeId(),branchId);
							if(employeeIdDetails.size() >0){					
										List<EFmFmRoleMasterPO> getRoleDetails=iUserMasterBO.getRoleId(userRole);
										if(getRoleDetails.size() >0){						
											EFmFmClientUserRolePO eFmFmClientUserRolePO=new EFmFmClientUserRolePO();
											eFmFmClientUserRolePO.setEfmFmUserMaster(employeeIdDetails.get(0));
											eFmFmClientUserRolePO.setEfmFmRoleMaster(getRoleDetails.get(0));
											eFmFmClientUserRolePO.seteFmFmClientBranchPO(eFmFmClientBranch);
											iUserMasterBO.save(eFmFmClientUserRolePO);
										}
									}
						}
					}
				 }else{
					 List<EFmFmUserMasterPO> employeeIdalreadyExist=iEmployeeDetailBO.getParticularEmpDetailsFromEmployeeId(employeeDetailsPO.getEmployeeId(),branchId);
					 employeeIdalreadyExist.get(0).setAddress(employeeDetailsPO.getAddress());
					 employeeIdalreadyExist.get(0).setCityName(employeeDetailsPO.getCityName());
					 employeeIdalreadyExist.get(0).setLatitudeLongitude(employeeDetailsPO.getLatitudeLongitude());
					 employeeIdalreadyExist.get(0).setEmailId(employeeDetailsPO.getEmailId());
					 employeeIdalreadyExist.get(0).setMobileNumber(employeeDetailsPO.getMobileNumber());
					 employeeIdalreadyExist.get(0).setFirstName(employeeDetailsPO.getFirstName());
					 employeeIdalreadyExist.get(0).setLastName(employeeDetailsPO.getLastName());
					 employeeIdalreadyExist.get(0).setMiddleName(employeeDetailsPO.getMiddleName());
					 iEmployeeDetailBO.update(employeeIdalreadyExist.get(0));					 
				 }
				
				}
         }
         catch(IndexOutOfBoundsException e){
        	 e.printStackTrace();
         }		
		
	}
	/*
	 * Calculating working day's without Saturday & Sunday.
	 * @return the end Date.
	 * @author  Rajan R
	 * @since   2015-05-12 
	 */	
	public static Date getWorkingDays(Date startDate, int dayCount) {
	    Calendar startCal;	    
	    startCal = Calendar.getInstance();
	    startCal.setTime(startDate);	    
	    int noOfDays=2;	     	
	    	do {	
	    		int dayOfWeek = startCal.get(Calendar.DAY_OF_WEEK);
		         if (dayOfWeek == Calendar.FRIDAY){	
		        	 startCal.add(Calendar.DATE, 2);
		         }else{
		            	startCal.add(Calendar.DATE, 1);		            	
		            	noOfDays=noOfDays+1;		            	
		            }
		        } while (dayCount >= noOfDays);
	   
	       return startCal.getTime(); 
	}

}