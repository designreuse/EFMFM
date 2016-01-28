package com.newtglobal.eFmFmFleet.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.newtglobal.eFmFmFleet.business.bo.IApprovalBO;
import com.newtglobal.eFmFmFleet.business.bo.IRouteDetailBO;
import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.model.EFmFmDriverMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/xlUtilityDriverUpload")
public class DriverImportExcel {	
	private static final String SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER = "E:/uploadDocs/";
	private static final String SERVER_UPLOAD_LINUX_LOCATION_FOLDER = "/home/newt-power/upload/";
	/*
	 * @Reading Driver details from driver_master xl utility.
	 * @Stored all the values on Arraylist.
	 */	
	@POST
	@Path("/driverRecord")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response upload(
			@FormDataParam("filename") InputStream uploadedInputStream,
			@FormDataParam("filename") FormDataContentDisposition fileDetail,
			@QueryParam("branchId") int branchId,			
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
				if(columnValue.size()>1){
					driverRecord(columnValue,branchId);
				}
			
					
			}

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
	}	
	/**
	 * 
	 * @param add new driver records
	 * @return success after adding the driver
	 * @throws ParseException 
	 */
	
	@POST
	@Path("/addnewdriver")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addNewDriver(EFmFmDriverMasterPO driverMasterPO) throws ParseException{
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");
		Map<String, Object> requests = new HashMap<String, Object>();
		List<EFmFmVendorMasterPO> vendorDetails=iVendorDetailsBO.getAllVendorName(driverMasterPO.getEfmFmVendorMaster().getVendorName(),driverMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());
	 	if(vendorDetails.size() >0){			 		
	 		for(EFmFmVendorMasterPO vendorId:vendorDetails){
	 			EFmFmVendorMasterPO eFmFmVendorMasterPO=new EFmFmVendorMasterPO();
	 			eFmFmVendorMasterPO.setVendorId(vendorId.getVendorId());
	 			driverMasterPO.setEfmFmVendorMaster(eFmFmVendorMasterPO);
	 		}
	 	}
        //Mobile number check need to add
	 	//Licence check is dere
	 	List<EFmFmDriverMasterPO> licenseNoExist=iRouteDetailBO.getValidLicenseNumber(driverMasterPO.getLicenceNumber());				
		if(licenseNoExist.size()==0){
			driverMasterPO.setStatus("P");
			driverMasterPO.setStateName("TamilNadu");
			driverMasterPO.setGender("Male");
			driverMasterPO.setCityName("Chennai");
			driverMasterPO.setMobileNumber("91"+driverMasterPO.getMobileNumber());
		 	driverMasterPO.setMedicalFitnessCertificateIssued(new Date());
		 	driverMasterPO.setPoliceVerification("Done");			 	
			iRouteDetailBO.saveDriverRecord(driverMasterPO);
			requests.put("status", "success");
			return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		
		requests.put("status", "exist");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();

	}	
	/*
	 * @xl utility row values are inserted into efmfm_driver_master table table. 
	 * validation also be handle here.
	 * 
	 */
	private void driverRecord(ArrayList<Object> columnValue,int branchId) throws ParseException {				
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		 try
         {
			 	EFmFmDriverMasterPO driverMasterPO = new EFmFmDriverMasterPO();
			 if(columnValue.get(0).toString().toUpperCase()!=null && !columnValue.get(0).toString().toUpperCase().isEmpty() && columnValue.get(0).toString().toUpperCase()!=""){
			 	driverMasterPO.setFirstName(columnValue.get(1).toString());	
			 	driverMasterPO.setMiddleName(columnValue.get(2).toString());			 	
			 	driverMasterPO.setLastName(columnValue.get(3).toString());
			 	driverMasterPO.setFatherName(columnValue.get(4).toString());
			 	driverMasterPO.setGender(columnValue.get(5).toString());			 	
			 	driverMasterPO.setMobileNumber(columnValue.get(6).toString());			 	
			 	driverMasterPO.setLicenceNumber(columnValue.get(7).toString());			 	
			 	driverMasterPO.setLicenceIssued((Date)dateFormat.parse(columnValue.get(8).toString()));
			 	driverMasterPO.setLicenceValid((Date)dateFormat.parse(columnValue.get(9).toString()));
			 	driverMasterPO.setMedicalFitnessCertificateIssued((Date)dateFormat.parse(columnValue.get(10).toString()));
			 	driverMasterPO.setMedicalFitnessCertificateValid((Date)dateFormat.parse(columnValue.get(11).toString()));
			 	driverMasterPO.setPoliceVerification(columnValue.get(12).toString());
			 	driverMasterPO.setStateName(columnValue.get(13).toString());
			 	driverMasterPO.setCityName(columnValue.get(14).toString());
			 	driverMasterPO.setPinCode(Integer.parseInt(columnValue.get(15).toString()));				 	
			 	driverMasterPO.setAddress(columnValue.get(16).toString());			 	
			 	driverMasterPO.setStatus("P");
			 	driverMasterPO.setDateOfJoining(new Date());
			 	driverMasterPO.setBatchDate(new Date());
			 	driverMasterPO.setDdtValidDate(new Date());
			 	driverMasterPO.setPoliceVerificationValid(new Date());
//			 	driverMasterPO.set
			 	
			 	driverMasterPO.setDob(new Date());
				List<EFmFmVendorMasterPO> vendorDetails=iVendorDetailsBO.getAllVendorName(columnValue.get(0).toString().toUpperCase(),branchId);
			 	if(vendorDetails.size() >0){			 		
			 		for(EFmFmVendorMasterPO vendorId:vendorDetails){
			 			EFmFmVendorMasterPO eFmFmVendorMasterPO=new EFmFmVendorMasterPO();
			 			eFmFmVendorMasterPO.setVendorId(vendorId.getVendorId());
			 			driverMasterPO.setEfmFmVendorMaster(eFmFmVendorMasterPO);
			 		}
			 		//Driver Record already existing on table.
			 		if(!driverMasterPO.getLicenceNumber().isEmpty() && driverMasterPO.getLicenceNumber() !="" && !driverMasterPO.getMobileNumber().isEmpty() && driverMasterPO.getMobileNumber() !="" ){
					 	List<EFmFmDriverMasterPO> licenseNoExist=iRouteDetailBO.getValidLicenseNumber(driverMasterPO.getLicenceNumber().trim().toUpperCase());				
						if(licenseNoExist.size()==0){
							List<EFmFmDriverMasterPO> mobileNoExist=iRouteDetailBO.getValidLicenseNumber(driverMasterPO.getMobileNumber());				
							if(mobileNoExist.size()==0){
								iRouteDetailBO.saveDriverRecord(driverMasterPO);
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
	
	/**
	* upload documents for driver.
	*    
	*
	* @author  Sarfraz Khan 
	* 
	* @since   2015-06-20
	* 
	* @return uploadFile
	*/	

	@POST
	@Path("/documentupload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	//@Produces("application/json")		
   public Response uploadFile(
				@FormDataParam("filename") InputStream uploadedInputStream,
				@FormDataParam("filename") FormDataContentDisposition fileDetail,
				@QueryParam("branchId") int branchId,
				@QueryParam("driverId") int driverId,
				@QueryParam("typeOfUploadDoc") String typeOfUploadDoc,
				@Context HttpServletRequest request) throws ParseException, IOException {
		VehicleImportExcel vehicleService=new VehicleImportExcel();
		Map<String, Object> requests = new HashMap<String, Object>();
		IApprovalBO approvalBO = (IApprovalBO) ContextLoader.getContext().getBean("IApprovalBO");	
		EFmFmDriverMasterPO driverDetails=approvalBO.getParticularDriverDetail(driverId);	
		
		String name="os.name",filePath="";		 	
		boolean  OsName =System.getProperty(name).startsWith("Windows");
		
		if(OsName){			
			filePath =SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER+ fileDetail.getFileName();			
			File pathExist = new File(SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER);			
			if(!pathExist.exists()){
				new File(SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER).mkdir();
			}			
		}else{
			filePath =SERVER_UPLOAD_LINUX_LOCATION_FOLDER+ fileDetail.getFileName();
			File pathExist = new File(SERVER_UPLOAD_LINUX_LOCATION_FOLDER);			
			if(!pathExist.exists()){
				new File(SERVER_UPLOAD_LINUX_LOCATION_FOLDER).mkdir();
			}
		}		
		File fileExist = new File(filePath);		
		if(!fileExist.exists() && !fileExist.isDirectory()){			
			requests.put("status", "success");
			vehicleService.saveFile(uploadedInputStream, filePath);
		}else{
			requests.put("status", "exist");
		}
		if(typeOfUploadDoc.equalsIgnoreCase("address")){
		driverDetails.setLicenceDocPath(filePath);
		}
		else{
			driverDetails.setMedicalDocPath(filePath);
		}
		approvalBO.update(driverDetails);		
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}

}
