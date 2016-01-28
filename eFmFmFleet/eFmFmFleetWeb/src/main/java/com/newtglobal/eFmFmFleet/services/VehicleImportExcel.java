package com.newtglobal.eFmFmFleet.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorContractTypeMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;


@Component
@Path("/xlUtilityVehicleUpload")
public class VehicleImportExcel {	
	
	private static final String SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER = "E:/upload/";
	private static final String SERVER_UPLOAD_LINUX_LOCATION_FOLDER = "/home/teamsm/upload/";
	
	
	/*
	 * @Reading Driver details from vehicle_master xl utility.
	 * @Stored all the values on Arraylist.
	 */
	private static Log log = LogFactory.getLog(VehicleImportExcel.class);	
	@POST
	@Path("/vehicleRecord")
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
					vehicleRecord(columnValue,branchId);
				}
					
			}

		}catch (FileNotFoundException e) {
			e.printStackTrace();
			log.info("catch exception.");
		} 
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
	}	
	
	/*
	 * Add new vehicle 
	 * here we can add new vehicle one bye one
	 */
	
	@POST
	@Path("/addnewvehicle")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addNewVehicle(EFmFmVehicleMasterPO vehicleMasterPO){					
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");
	Map<String, Object> requests = new HashMap<String, Object>();
	int branchId=vehicleMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId();
	List<EFmFmVendorMasterPO> vendorDetails=iVendorDetailsBO.getAllVendorName(vehicleMasterPO.getEfmFmVendorMaster().getVendorName(),vehicleMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());
 	if(vendorDetails.size() >0){			 		
 		for(EFmFmVendorMasterPO vendorId:vendorDetails){
 			EFmFmVendorMasterPO eFmFmVendorMasterPO=new EFmFmVendorMasterPO();
 			eFmFmVendorMasterPO.setVendorId(vendorId.getVendorId());
 			vehicleMasterPO.setEfmFmVendorMaster(eFmFmVendorMasterPO);
 			
 		}
 	}
	//Vehicle Record already existing on table.
 	EFmFmVehicleMasterPO vehicleNoExist=iVehicleCheckInBO.getParticularVehicleDetails(vehicleMasterPO.getVehicleNumber(),branchId);				
	if(vehicleNoExist ==null){
		vehicleMasterPO.setAvailableSeat(vehicleMasterPO.getCapacity()-1);
		vehicleMasterPO.setrFIDFitted("N");
		vehicleMasterPO.setVehicleGPSFitted("N");
		vehicleMasterPO.setVehicleACFitted("Y");
		iVehicleCheckInBO.save(vehicleMasterPO);
		requests.put("status", "success");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	
	requests.put("status", "exist");
	return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}	
	/*
	 * @xl utility row values are inserted into efmfm_vehicle_master table table. 
	 * validation also be handle here.
	 * 
	 */
	private void vehicleRecord(ArrayList<Object> columnValue,int branchId) throws ParseException {		
		
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		 try
         {
			 	EFmFmVehicleMasterPO vehicleMasterPO = new EFmFmVehicleMasterPO();
			 	if(columnValue.get(6).toString().toUpperCase() !=null && !columnValue.get(6).toString().toUpperCase().isEmpty() && columnValue.get(6).toString().toUpperCase() !=""){
			 	//vehicleMasterPO.setVehicleOwnerName(columnValue.get(0).toString());
			 	vehicleMasterPO.setVehicleNumber(columnValue.get(0).toString());			 	
			 	vehicleMasterPO.setRegistartionCertificateNumber(columnValue.get(1).toString());			 	
			 	vehicleMasterPO.setCapacity(Integer.parseInt(columnValue.get(2).toString()));	
			 	vehicleMasterPO.setVehicleEngineNumber(columnValue.get(3).toString());			 	
			 	vehicleMasterPO.setVehicleMake((columnValue.get(4).toString()));			 	
			 	vehicleMasterPO.setVehicleModel((columnValue.get(5).toString()));
			 	
			 	vehicleMasterPO.setTaxCertificateValid((Date)dateFormat.parse(columnValue.get(7).toString()));
			 	vehicleMasterPO.setPermitValidDate((Date)dateFormat.parse(columnValue.get(8).toString()));
			 	vehicleMasterPO.setPolutionValid((Date)dateFormat.parse(columnValue.get(9).toString()));
			 	vehicleMasterPO.setInsuranceValidDate((Date)dateFormat.parse(columnValue.get(10).toString()));			 		 	
			 	vehicleMasterPO.setVehicleModelYear(columnValue.get(11).toString());			 	
			 	vehicleMasterPO.setRemarks(columnValue.get(12).toString());
			 	vehicleMasterPO.setVehicleACFitted(columnValue.get(13).toString());
			 	vehicleMasterPO.setVehicleGPSFitted(columnValue.get(14).toString());
			 	vehicleMasterPO.setrFIDFitted(columnValue.get(15).toString());
			 	vehicleMasterPO.setUpdatedTime(new Date());
			 	vehicleMasterPO.setRegistrationDate(new Date());
			 	vehicleMasterPO.setVehicleFitnessDate(new Date());
			 	vehicleMasterPO.setStatus("P");
			 	//vehicleMasterPO.setTotalContactKM(200);
			 	vehicleMasterPO.setAvailableSeat(Integer.parseInt(columnValue.get(2).toString())-1);
			 	//vendor details
			 	
			 	List<EFmFmVendorMasterPO> vendorDetails=iVendorDetailsBO.getAllVendorName(columnValue.get(6).toString().toUpperCase(),branchId);
			 	if(vendorDetails.size() >0){			 		
			 		for(EFmFmVendorMasterPO vendorId:vendorDetails){
			 			EFmFmVendorMasterPO eFmFmVendorMasterPO=new EFmFmVendorMasterPO();
			 			eFmFmVendorMasterPO.setVendorId(vendorId.getVendorId());
			 			vehicleMasterPO.setEfmFmVendorMaster(eFmFmVendorMasterPO);
			 		}
			 		if(vehicleMasterPO.getVehicleNumber()!="" && !vehicleMasterPO.getVehicleNumber().isEmpty()){
			 		EFmFmVehicleMasterPO vehicleNoExist=iVehicleCheckInBO.getParticularVehicleDetails(vehicleMasterPO.getVehicleNumber(),branchId);				
					if(vehicleNoExist ==null){
						List<EFmFmVehicleMasterPO> vehicleRcExist=iVehicleCheckInBO.getRcNumberDetails(vehicleMasterPO.getRegistartionCertificateNumber(),branchId);				
						if(vehicleRcExist.size()==0){
							List<EFmFmVehicleMasterPO> vehicleEngineNoExist=iVehicleCheckInBO.getEngineNoDetails(vehicleMasterPO.getVehicleEngineNumber().toUpperCase(),branchId);				
							if(vehicleEngineNoExist.size()==0){
								List<EFmFmVendorContractTypeMasterPO> contractType=iVehicleCheckInBO.getContractTypeDetails(columnValue.get(16).toString().toLowerCase().trim(), branchId);
								if(contractType.size()>0){
									vehicleMasterPO.seteFmFmVendorContractTypeMaster(contractType.get(0));
									vehicleMasterPO.setContractDetailId(Integer.parseInt(columnValue.get(17).toString().trim()));
									iVehicleCheckInBO.save(vehicleMasterPO);
								}
								
							}
						}
					 }
					}
			 	}
			 	}
				//vehicle Record already existing on table.			 	
         }
         catch(IndexOutOfBoundsException e){
        	 e.printStackTrace();
         }		
		
	}
	
	/**
	 * Upload a File
	 */

	@POST
	@Path("/vehicledocument")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	//@Produces("application/json")		
   public Response uploadFile(
				@FormDataParam("filename") InputStream uploadedInputStream,
				@FormDataParam("filename") FormDataContentDisposition fileDetail,
				@QueryParam("branchId") int branchId,
				@QueryParam("vehicleId") int vehicleId,	
				@QueryParam("typeOfUploadDoc") String typeOfUploadDoc,	

				@Context HttpServletRequest request) throws ParseException, IOException {
		
		Map<String, Object> requests = new HashMap<String, Object>();
		
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
		IVehicleCheckInBO vehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
		EFmFmVehicleMasterPO vehicleDetails=vehicleCheckInBO.getParticularVehicleDetail(vehicleId);		
		File fileExist = new File(filePath);		
		if(!fileExist.exists() && !fileExist.isDirectory()){			
			requests.put("status", "success");
			// save the file to the server	
			saveFile(uploadedInputStream, filePath);
		}else{
			requests.put("status", "exist");
		}
		if(typeOfUploadDoc.equalsIgnoreCase("Insurance")){
			vehicleDetails.setInsurancePath(filePath);
		}
		else if(typeOfUploadDoc.equalsIgnoreCase("Registration")){
			vehicleDetails.setRegistartionCertificatePath(filePath);
		}
       else if(typeOfUploadDoc.equalsIgnoreCase("pollution")){
    	   vehicleDetails.setPoluctionCertificatePath(filePath);

		}
		else{
			vehicleDetails.setTaxCertificatePath(filePath);
		}
		vehicleCheckInBO.update(vehicleDetails);		
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();
	}
	// save uploaded file to a defined location on the server
	 void saveFile(InputStream uploadedInputStream, String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(
					serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	

}
