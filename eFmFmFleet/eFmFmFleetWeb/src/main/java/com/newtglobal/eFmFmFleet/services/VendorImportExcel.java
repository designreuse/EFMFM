package com.newtglobal.eFmFmFleet.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import com.newtglobal.eFmFmFleet.business.bo.IVendorDetailsBO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmVendorMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;
@Component
@Path("/xlUtilityVendorUpload")
public class VendorImportExcel {	
	/*
	 * @Reading vendor details from vendor_master xl utility.
	 * @Stored all the values on Arraylist.
	 */
	private static Log log = LogFactory.getLog(VendorImportExcel.class);

	@POST
	@Path("/vendorRecord")
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
			log.info("called");
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
					vendorRecord(columnValue,branchId);
				}
					
			}

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
	}	
	
	
	/**
	 * 
	 * @param add new vendor records
	 * @return success after adding the vendor
	 */
	
	@POST
	@Path("/addnewvendor")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addNewVendor(EFmFmVendorMasterPO vendorMasterPO){					
		IVendorDetailsBO iVendorMasterBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");
		Map<String, Object> requests = new HashMap<String, Object>();
		vendorMasterPO.setStatus("P");
		List<EFmFmVendorMasterPO> venderExist=iVendorMasterBO.getAllVendorName(vendorMasterPO.getVendorName(),vendorMasterPO.geteFmFmClientBranchPO().getBranchId());				
		if(venderExist.size()==0){				
			iVendorMasterBO.save(vendorMasterPO);
			requests.put("status", "success");
			return Response.ok(requests, MediaType.APPLICATION_JSON).build();
		}
		requests.put("status", "exist");
		return Response.ok(requests, MediaType.APPLICATION_JSON).build();

	}
	
	/*
	 * @xl utility row values are inserted into vendor master table table. 
	 * validation also be handle here.
	 * 
	 */
	private void vendorRecord(ArrayList<Object> columnValue,int branchId) throws ParseException {		
		IVendorDetailsBO iVendorMasterBO = (IVendorDetailsBO) ContextLoader.getContext().getBean("IVendorDetailsBO");		
		 try
         {
			 	EFmFmVendorMasterPO vendorMasterPO = new EFmFmVendorMasterPO();			 	
			 	vendorMasterPO.setTinNumber(columnValue.get(0).toString());
			 	vendorMasterPO.setVendorName(columnValue.get(1).toString().trim());	 	
			 	vendorMasterPO.setVendorContactName(columnValue.get(2).toString());			 	
			 	vendorMasterPO.setVendorMobileNo(columnValue.get(3).toString());
			 	vendorMasterPO.setVendorOfficeNo(columnValue.get(4).toString());			 	
			 	vendorMasterPO.setEmailId(columnValue.get(5).toString());			 	
			 	vendorMasterPO.setStateName(columnValue.get(6).toString());
			 	vendorMasterPO.setCityName(columnValue.get(7).toString());
			 	vendorMasterPO.setPinCode(Integer.parseInt(columnValue.get(8).toString())); 	
			 	vendorMasterPO.setAddress(columnValue.get(9).toString());			 	
			 	EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();		 	
			 	eFmFmClientBranchPO.setBranchId(branchId);
			 	vendorMasterPO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
			 	vendorMasterPO.setStatus("P");	
			 	//vendor Record already existing on table.
			 if(!vendorMasterPO.getVendorName().isEmpty() && vendorMasterPO.getVendorName() !=null &&  vendorMasterPO.getVendorName() !=""){			 		
			 	List<EFmFmVendorMasterPO> venderNameExist=iVendorMasterBO.getAllVendorName(vendorMasterPO.getVendorName(),branchId);				
				if(venderNameExist.size()==0){				
					List<EFmFmVendorMasterPO> venderMobileNoExist=iVendorMasterBO.getVendorMobileNo(vendorMasterPO.getVendorMobileNo(),branchId);				
					if(venderMobileNoExist.size()==0){
						List<EFmFmVendorMasterPO> venderTinNumberExist=iVendorMasterBO.getVendorTinNumber(vendorMasterPO.getTinNumber(),branchId);				
						if(venderTinNumberExist.size()==0){
						List<EFmFmVendorMasterPO> venderEmailIdExist=iVendorMasterBO.getVendorEmailId(vendorMasterPO.getEmailId(),branchId);				
							if(venderEmailIdExist.size()==0){	
								iVendorMasterBO.save(vendorMasterPO);
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

}
