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
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortCheckInPO;
import com.newtglobal.eFmFmFleet.model.EFmFmEscortMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVehicleMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;


@Component
@Path("/escort")
@Consumes("application/json")
@Produces("application/json")
public class EscortService {	
	
	private static final String SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER = "E:/uploadDocs/";
	private static final String SERVER_UPLOAD_LINUX_LOCATION_FOLDER = "/home/ubuntushell/upload/";
	
	/*
	 * @Reading Escort details from escort Master xl utility.
	 * @Stored all the values on Arraylist.
	 * @author  Rajan R
	 * @since   2015-05-28 
	 */
	@POST
	@Path("/escortDetails")
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
					addEscortRecord(columnValue,branchId);
				}
					
			}

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
	}
	
	
	/*
	 * @xl utility row values are inserted into employee master table table. 
	 * validation also be handle here.	 * 
	 * @author  Rajan R
	 * @since   2015-05-12 
	 */
	private void addEscortRecord(ArrayList<Object> columnValue,int branchId) throws ParseException {	
		IVendorDetailsBO iVendorDetailsBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");	
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		 try
         {
			 if(columnValue.get(16).toString().toUpperCase()!="" && !columnValue.get(16).toString().toUpperCase().isEmpty()){
			 	EFmFmEscortMasterPO escortMasterPO = new EFmFmEscortMasterPO();	
			 	escortMasterPO.setEscortEmployeeId(columnValue.get(0).toString());
			 	escortMasterPO.setFirstName(columnValue.get(1).toString());
			 	escortMasterPO.setMiddleName(columnValue.get(2).toString());
			 	escortMasterPO.setLastName(columnValue.get(3).toString());
			 	escortMasterPO.setFatherName(columnValue.get(4).toString());
			 	escortMasterPO.setMobileNumber(columnValue.get(5).toString());
			 	escortMasterPO.setGender(columnValue.get(6).toString());
			 	escortMasterPO.setStateName(columnValue.get(7).toString());			 	
			 	escortMasterPO.setCityName(columnValue.get(8).toString());
			 	escortMasterPO.setAddress(columnValue.get(9).toString());			 	
			 	escortMasterPO.setPincode(Integer.parseInt(columnValue.get(10).toString()));			 	
			 	escortMasterPO.setPoliceVerification(columnValue.get(11).toString());
			 	escortMasterPO.setDesignation(columnValue.get(12).toString());
			 	escortMasterPO.setDateOfBirth((Date)dateFormat.parse(columnValue.get(13).toString()));			 	
			 	escortMasterPO.setEmailId(columnValue.get(14).toString());			 	
			 	escortMasterPO.setPhysicallyChallenged(columnValue.get(15).toString());
			 	escortMasterPO.setIsActive("Y");
			 	EFmFmClientMasterPO efmFmClientMaster=new EFmFmClientMasterPO();
			 	efmFmClientMaster.setClientId(branchId);
			 	List<EFmFmVendorMasterPO> vendorDetails=iVendorDetailsBO.getAllVendorName(columnValue.get(16).toString().toUpperCase(),branchId);
			 	if(vendorDetails.size() >0){			 		
			 		for(EFmFmVendorMasterPO vendorId:vendorDetails){
			 			EFmFmVendorMasterPO eFmFmVendorMasterPO=new EFmFmVendorMasterPO();
			 			eFmFmVendorMasterPO.setVendorId(vendorId.getVendorId());
			 			escortMasterPO.setEfmFmVendorMaster(eFmFmVendorMasterPO);
			 		}
			 	}
			 	
				/* 
				 * Fetching AreaId from efmfmareamaster table.
				 
				EFmFmAreaMasterPO areaDetails = iRouteDetailBO.getAreaId(columnValue.get(5).toString().trim());
				if (areaDetails !=null) {					
					  escortMasterPO.setEfmFmAreaMaster(areaDetails);
				}*/
			 	
				/*
				 * escort Record already existing on table.
				 */
			 	if((!escortMasterPO.getEscortEmployeeId().isEmpty() && escortMasterPO.getEscortEmployeeId() !="" && escortMasterPO.getEscortEmployeeId() !=null) &&
			 			(escortMasterPO.getMobileNumber() !="" && !escortMasterPO.getMobileNumber().isEmpty() && escortMasterPO.getMobileNumber()!="") && vendorDetails.size() >0){
			 	List<EFmFmEscortMasterPO> escortIdExist=iVehicleCheckInBO.getParticularEscortDetails(escortMasterPO);			 	
					if(escortIdExist.size() ==0){
						List<EFmFmEscortMasterPO> escortMobileNoExist=iVehicleCheckInBO.getMobileNoDetails(escortMasterPO);										 	
						if(escortMobileNoExist.size() ==0){
							iVehicleCheckInBO.saveEscortDetails(escortMasterPO);
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
	* The listOfEscort method implemented.
	* for getting the list of Escort details based on the escort.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-25 
	* 
	* @return escort details.
	*/
	
	
	@POST
	@Path("/listOfEscort")
	public Response listOfEscort(EFmFmVendorMasterPO eFmFmVendorMasterPO){		
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
		List<Map<String, Object>> listOfEscortDetails= new ArrayList<Map<String, Object>>();
		EFmFmEscortMasterPO eFmFmEscortMasterPO=new EFmFmEscortMasterPO();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		List<EFmFmEscortMasterPO> listOfEscort=iVehicleCheckInBO.getAllEscortDetails(eFmFmEscortMasterPO);		 
		if((!(listOfEscort.isEmpty())) || listOfEscort.size() !=0){				
			for(EFmFmEscortMasterPO escortDetails:listOfEscort){				
					Map<String, Object>  escortList= new HashMap<String, Object>();					
					escortList.put("escortId",escortDetails.getEscortId());
					escortList.put("escortName",escortDetails.getFirstName());
					escortList.put("escortAddress",escortDetails.getAddress());
					escortList.put("escortMobileNumber",escortDetails.getMobileNumber());						
					escortList.put("escortFatherName",escortDetails.getFatherName());
					escortList.put("escortGender",escortDetails.getGender());
					escortList.put("escortPincode",escortDetails.getPincode());
					escortList.put("remarks",escortDetails.getRemarks());
					escortList.put("escortdob",dateFormat.format(listOfEscort.get(0).getDateOfBirth()));
					listOfEscortDetails.add(escortList);			
				}			
			}			
		return Response.ok(listOfEscortDetails, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	* The escortDetails method implemented.
	* for getting the escort details based on the escortId.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-25 
	* 
	* @return escort details.
	*/	
	@POST
	@Path("/escortallDetails")
	public Response escortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO){		
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");				
		List<Map<String, Object>> listOfEscortDetails= new ArrayList<Map<String, Object>>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		List<EFmFmEscortMasterPO> listOfEscort=iVehicleCheckInBO.getParticularEscortDetails(eFmFmEscortMasterPO);		 
			if(listOfEscort.size()>0){
					Map<String, Object>  escortList= new HashMap<String, Object>();					
					escortList.put("escortId",listOfEscort.get(0).getEscortId());
					escortList.put("escortName",listOfEscort.get(0).getFirstName());
					escortList.put("escortAddress",listOfEscort.get(0).getAddress());
					escortList.put("escortMobileNumber",listOfEscort.get(0).getMobileNumber());
					escortList.put("escortFatherName",listOfEscort.get(0).getFatherName());
					escortList.put("escortGender",listOfEscort.get(0).getGender());
					escortList.put("escortPincode",listOfEscort.get(0).getPincode());
					escortList.put("remarks",listOfEscort.get(0).getRemarks());
					escortList.put("escortdob",dateFormat.format(listOfEscort.get(0).getDateOfBirth()));
					escortList.put("escortBatchNum",listOfEscort.get(0).getEscortEmployeeId());
					escortList.put("vendorName",listOfEscort.get(0).getEfmFmVendorMaster().getVendorName());

					listOfEscortDetails.add(escortList);			
			 }			
		return Response.ok(listOfEscortDetails, MediaType.APPLICATION_JSON).build();
	}
	/**
	* The escortCheckInDetails method implemented.
	* for getting the escort details based on the escortId.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-25 
	* 
	* @return escort details.
	*/	
	
	@POST
	@Path("/escortCheckInDetails")
	@Consumes("application/json")
	@Produces("application/json")
	public Response escortCheckInDetails(EFmFmClientBranchPO  eFmFmClientBranchPO){			
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");				
		List<Map<String, Object>> listOfEscortDetails= new ArrayList<Map<String, Object>>();		
		List<EFmFmEscortMasterPO> listOfEscort=iVehicleCheckInBO.getAllCheckInEscort(eFmFmClientBranchPO.getBranchId(), new Date());		 
		if((!(listOfEscort.isEmpty())) || listOfEscort.size() !=0){				
			for(EFmFmEscortMasterPO escortDetails:listOfEscort){				
					Map<String, Object>  escortList= new HashMap<String, Object>();					
					escortList.put("escortId",escortDetails.getEscortId());
					escortList.put("escortName",escortDetails.getFirstName());
					escortList.put("escortAddress",escortDetails.getAddress());
					escortList.put("escortMobileNumber",escortDetails.getMobileNumber());	
					escortList.put("escortFatherName",escortDetails.getFatherName());
					escortList.put("escortGender",escortDetails.getGender());
					escortList.put("escortPincode",escortDetails.getPincode());
					escortList.put("remarks",escortDetails.getRemarks());
					listOfEscortDetails.add(escortList);			
				}			
			}			
		return Response.ok(listOfEscortDetails, MediaType.APPLICATION_JSON).build();
	}
	
	
	/*
	 * Below Method used to check in the escort. 
	 */
	@POST
	@Path("/checkInEscort")
	@Consumes("application/json")
	@Produces("application/json")	
	public Response escortCheckIn(EFmFmEscortMasterPO eFmFmEscortMasterPO){
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");		
		EFmFmEscortCheckInPO eFmFmEscortCheckInPO=new EFmFmEscortCheckInPO();		
		eFmFmEscortCheckInPO.setEscortCheckInTime(new Date());
		eFmFmEscortCheckInPO.setStatus("Y");
		eFmFmEscortCheckInPO.seteFmFmEscortMaster(eFmFmEscortMasterPO);		
		iVehicleCheckInBO.saveEscortCheckIn(eFmFmEscortCheckInPO);			
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	
	
	
	
	/**
	 * 
	 * @param eFmFmEscortMasterPO
	 * 
	 * @return Success after checkout the Escort
	 */
	@POST
	@Path("/checkOutEscort")
	@Consumes("application/json")
	@Produces("application/json")	
	public Response escortCheckOutFunction(EFmFmEscortMasterPO eFmFmEscortMasterPO){
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");	
		List<EFmFmEscortCheckInPO> checkInDetails=iVehicleCheckInBO.getParticulaEscortDetailFromEscortId(eFmFmEscortMasterPO.getBranchId(), eFmFmEscortMasterPO.getEscortCheckInId());
		checkInDetails.get(0).setStatus("N");
		checkInDetails.get(0).setEscortCheckOutTime(new Date());
		iVehicleCheckInBO.update(checkInDetails.get(0));
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/availableEscortDetails")
	@Consumes("application/json")
	@Produces("application/json")
	public Response availableEscortDetails(EFmFmClientBranchPO  eFmFmClientBranchPO){		
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");				
		List<Map<String, Object>> listOfEscortDetails= new ArrayList<Map<String, Object>>();	
		List<EFmFmEscortCheckInPO> listOfEscort=iVehicleCheckInBO.getAllCheckedInEscort(eFmFmClientBranchPO.getBranchId(), new Date());		 
		if((!(listOfEscort.isEmpty())) || listOfEscort.size() !=0){				
			for(EFmFmEscortCheckInPO escortDetails:listOfEscort){				
					Map<String, Object>  escortList= new HashMap<String, Object>();					
					escortList.put("escortId",escortDetails.geteFmFmEscortMaster().getEscortId());
					escortList.put("escortCheckId",escortDetails.getEscortCheckInId());
					escortList.put("escortName",escortDetails.geteFmFmEscortMaster().getFirstName());					
					escortList.put("escortMobileNumber",escortDetails.geteFmFmEscortMaster().getMobileNumber());
					escortList.put("escortAddress",escortDetails.geteFmFmEscortMaster().getAddress());
					listOfEscortDetails.add(escortList);			
				}			
			}			
		return Response.ok(listOfEscortDetails, MediaType.APPLICATION_JSON).build();
	}
	
	
	
	/**
	* The modifyEscortDetails method implemented.
	* for Modifying Escort details.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-29 
	* 
	* @return modified Status.
	*/	
	@POST
	@Path("/modifyEscortDetails")
	@Consumes("application/json")
	@Produces("application/json")	
	public Response modifyEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO){
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");			
	    List<EFmFmEscortMasterPO> escortMasterPO=	iVehicleCheckInBO.getParticularEscortDetails(eFmFmEscortMasterPO); 
	    escortMasterPO.get(0).setFirstName(eFmFmEscortMasterPO.getFirstName());
	    escortMasterPO.get(0).setAddress(eFmFmEscortMasterPO.getAddress());	    
	    escortMasterPO.get(0).setMobileNumber(eFmFmEscortMasterPO.getMobileNumber());		    
	    iVehicleCheckInBO.updateEscortDetails(escortMasterPO.get(0));	    
		return Response.ok("Success", MediaType.APPLICATION_JSON).build();
	}
	
	
	
	/**
	* The addEscortDetails method implemented.
	* for add Escort details.   
	*
	* @author  Rajan R
	* 
	* @since   2015-05-29 
	* 
	* @return add Status.
	*/	
	@POST
	@Path("/addEscortDetails")
	@Consumes("application/json")
	@Produces("application/json")	
	public Response addEscortDetails(EFmFmEscortMasterPO eFmFmEscortMasterPO){
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		IVendorDetailsBO iVendorMasterBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");
		List<EFmFmVendorMasterPO> venderExist=iVendorMasterBO.getAllVendorName(eFmFmEscortMasterPO.getVendorName(),eFmFmEscortMasterPO.getEfmFmVendorMaster().geteFmFmClientBranchPO().getBranchId());				
	    Map<String, Object>  request = new HashMap<String, Object>();				    

		if(venderExist.isEmpty() || venderExist.size()==0){
			request.put("status", "fail");
    		return Response.ok(request, MediaType.APPLICATION_JSON).build();	
        }		
		eFmFmEscortMasterPO.setIsActive("Y");
		eFmFmEscortMasterPO.setMobileNumber("91"+eFmFmEscortMasterPO.getMobileNumber());
		eFmFmEscortMasterPO.setPoliceVerification("DONE");
		eFmFmEscortMasterPO.setGender("Male");
		eFmFmEscortMasterPO.setEfmFmVendorMaster(venderExist.get(0));
	    iVehicleCheckInBO.saveEscortDetails(eFmFmEscortMasterPO);	
	    request.put("status", "success");
		return Response.ok(request, MediaType.APPLICATION_JSON).build();
	}
	
	
	
	
	@POST
	@Path("/escortDocument")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")		
   public Response uploadFile(
				@FormDataParam("filename") InputStream uploadedInputStream,
				@FormDataParam("filename") FormDataContentDisposition fileDetail,
				@QueryParam("branchId") int branchId,
				@QueryParam("vehicleId") int vehicleId,	
				@QueryParam("typeOfUploadDoc") String typeOfUploadDoc,	

				@Context HttpServletRequest request) throws ParseException, IOException {
		
		Map<String, Object> requests = new HashMap<String, Object>();
		
		boolean  OsName =System.getProperty("os.name").startsWith("Windows");
		String filePath;
		if(OsName){			
			filePath =SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER+ fileDetail.getFileName();			
			File pathExist = new File(SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER);			
			if(!pathExist.exists()){
				new File(SERVER_UPLOAD_WINDOWS_LOCATION_FOLDER).mkdir();
			}			
		}else{
			filePath  =SERVER_UPLOAD_LINUX_LOCATION_FOLDER+ fileDetail.getFileName();
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