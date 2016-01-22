package com.newtglobal.eFmFmFleet.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import com.newtglobal.eFmFmFleet.business.bo.IRouteDetailBO;
import com.newtglobal.eFmFmFleet.model.EFmFmAreaMasterPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientRouteMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmRouteAreaMappingPO;
import com.newtglobal.eFmFmFleet.model.EFmFmZoneMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/xlUtilityAreaUpload")
public class AreaMasterImportExcel {	
	/*
	 * @Reading vendor details from vendor_master xl utility.
	 * @Stored all the values on Arraylist.
	 */
	private static Log log = LogFactory.getLog(AreaMasterImportExcel.class);	
	@POST
	@Path("/areaRecord")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response upload(
			@FormDataParam("filename") InputStream uploadedInputStream,
			@FormDataParam("filename") FormDataContentDisposition fileDetail,
			@QueryParam("branchId") int branchId,			
			@Context HttpServletRequest request) throws ParseException, IOException {		
		try {
			log.info("inside..............................."+branchId);
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
					areaRecord(columnValue,branchId);
				}
					
			}

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return Response.ok("success", MediaType.APPLICATION_JSON).build();
	}	
	/*
	 * @xl utility row values are inserted into Area master table  & route master table. 
	 * validation also be handle here.
	 * 
	 */
	private void areaRecord(ArrayList<Object> columnValue,int branchId) throws ParseException {				
		IRouteDetailBO iRouteDetailBO = (IRouteDetailBO) ContextLoader.getContext().getBean("IRouteDetailBO");				
		 try
         {
			    EFmFmRouteAreaMappingPO eFmFmRouteAreaMappingPO=new EFmFmRouteAreaMappingPO();
			 	EFmFmAreaMasterPO areaMasterPO = new EFmFmAreaMasterPO();
			 	areaMasterPO.setAreaName(columnValue.get(0).toString().toUpperCase());			 
			 	areaMasterPO.setAreaDescription(columnValue.get(1).toString());		 	
			 	if(!areaMasterPO.getAreaName().isEmpty() && areaMasterPO.getAreaName() !="" && !columnValue.get(2).toString().trim().isEmpty() && columnValue.get(2).toString().trim()!=""){
			 	//validating: Area already existing on table.
/*			 	List<EFmFmAreaMasterPO> areaExist=iRouteDetailBO.getAllAreaName(areaMasterPO.getAreaName().trim().toUpperCase().replaceAll("\\s", ""));				
				if(areaExist.size() ==0){				
					iRouteDetailBO.saveAreaRecord(areaMasterPO);					
				}
			 	
				List<EFmFmAreaMasterPO> eFmFmAreaMasterPO=iRouteDetailBO.getAllAreaName(areaMasterPO.getAreaName().trim().toUpperCase().replaceAll("\\s", ""));
				if(eFmFmAreaMasterPO.size()>0){
					for(EFmFmAreaMasterPO areaDetails:eFmFmAreaMasterPO){
						areaMasterPO.setAreaId(areaDetails.getAreaId());
					}
					
				}*/
			 		List<EFmFmAreaMasterPO> areaExist=iRouteDetailBO.getAllAreaName(areaMasterPO.getAreaName().trim().toUpperCase().replaceAll("\\s",""));    
			 	    if(areaExist.size() ==0){    
			 	     iRouteDetailBO.saveAreaRecord(areaMasterPO);     
			 	    }
			 	     
			 	    List<EFmFmAreaMasterPO> eFmFmAreaMasterPO=iRouteDetailBO.getAllAreaName(areaMasterPO.getAreaName().trim().toUpperCase().replaceAll("\\s",""));
			 	    if(eFmFmAreaMasterPO.size()>0){
			 	     for(EFmFmAreaMasterPO areaDetails:eFmFmAreaMasterPO){
			 	      areaMasterPO.setAreaId(areaDetails.getAreaId());
			 	     }
			 	     
			 	    }
				EFmFmZoneMasterPO routeMasterPO = new EFmFmZoneMasterPO();
				routeMasterPO.setZoneName(columnValue.get(2).toString());
				routeMasterPO.setStatus("Y");
				//validating: route already existing on table.
				List<EFmFmZoneMasterPO> routeExist=iRouteDetailBO.getAllRouteName(routeMasterPO.getZoneName().trim().toUpperCase());				
				if(routeExist.size()==0){					
					iRouteDetailBO.saveRouteNameRecord(routeMasterPO);					
				}
				List<EFmFmZoneMasterPO> eFmFmRouteMasterPO=iRouteDetailBO.getAllRouteName(routeMasterPO.getZoneName().trim().toUpperCase());
				if(eFmFmRouteMasterPO.size() >0){
					for(EFmFmZoneMasterPO routeDetails:eFmFmRouteMasterPO){
						routeMasterPO.setZoneId(routeDetails.getZoneId());
					}					
				}				
				eFmFmRouteAreaMappingPO.setEfmFmAreaMaster(areaMasterPO);
				eFmFmRouteAreaMappingPO.seteFmFmZoneMaster(routeMasterPO);				
				EFmFmClientRouteMappingPO eFmFmClientRouteMappingPO=new EFmFmClientRouteMappingPO();
				eFmFmClientRouteMappingPO.seteFmFmZoneMaster(routeMasterPO);
				EFmFmClientBranchPO eFmFmbranchMaster=new EFmFmClientBranchPO();
				eFmFmbranchMaster.setBranchId(branchId);				
				eFmFmClientRouteMappingPO.seteFmFmClientBranchPO(eFmFmbranchMaster);
				
				List<EFmFmClientRouteMappingPO> clientRouteMappingPO=iRouteDetailBO.clientRouteMappaingAlreadyExist(eFmFmClientRouteMappingPO);
				if(clientRouteMappingPO.size()==0){				
					iRouteDetailBO.saveClientRouteMapping(eFmFmClientRouteMappingPO);
				}							
//				List<EFmFmRouteAreaMappingPO> routeAreaMappingPO=iRouteDetailBO.routeMappaingAlreadyExist(eFmFmRouteAreaMappingPO);
//				if(routeAreaMappingPO.size()==0){				
					iRouteDetailBO.saveRouteMappingDetails(eFmFmRouteAreaMappingPO);
//				}
			 	}
			 	
         }
         catch(IndexOutOfBoundsException e){
        	 e.printStackTrace();
         }		
		
	}

}
