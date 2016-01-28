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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import com.newtglobal.eFmFmFleet.business.bo.IVehicleCheckInBO;
import com.newtglobal.eFmFmFleet.model.EFmFmClientBranchPO;
import com.newtglobal.eFmFmFleet.model.EFmFmDeviceMasterPO;
import com.newtglobal.eFmFmFleet.web.ContextLoader;

@Component
@Path("/device")
@Consumes("application/json")
@Produces("application/json")
public class DeviceDetailService {
	
	
	
	
	@POST
	@Path("/deviceRecord")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response deviceUpload(
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
					deviceRecord(columnValue,branchId);
				}
			
					
			}

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return Response.ok("SUCCEES", MediaType.APPLICATION_JSON).build();
	}

	
	private void deviceRecord(ArrayList<Object> columnValue, int branchId) {		
		IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
		EFmFmDeviceMasterPO eFmFmDeviceMasterPO=new EFmFmDeviceMasterPO();
		eFmFmDeviceMasterPO.setDeviceModel(columnValue.get(0).toString());
		eFmFmDeviceMasterPO.setDeviceOs(columnValue.get(1).toString());
		eFmFmDeviceMasterPO.setImeiNumber(columnValue.get(2).toString().trim());
		eFmFmDeviceMasterPO.setMobileNumber(columnValue.get(3).toString().trim());
		EFmFmClientBranchPO eFmFmClientBranchPO=new EFmFmClientBranchPO();
		eFmFmClientBranchPO.setBranchId(branchId);		
		eFmFmDeviceMasterPO.seteFmFmClientBranchPO(eFmFmClientBranchPO);
		eFmFmDeviceMasterPO.setStatus("Y");
		eFmFmDeviceMasterPO.setDeviceType("Android");
		eFmFmDeviceMasterPO.setIsActive("Y");		
		if(eFmFmDeviceMasterPO.getImeiNumber() !=null && !eFmFmDeviceMasterPO.getImeiNumber().isEmpty() && eFmFmDeviceMasterPO.getImeiNumber() !=""
				&& eFmFmDeviceMasterPO.getMobileNumber() !=null && !eFmFmDeviceMasterPO.getMobileNumber().isEmpty() && eFmFmDeviceMasterPO.getMobileNumber() !=""){
			List<EFmFmDeviceMasterPO> mobileExist=iVehicleCheckInBO.deviceNumberExistsCheck(eFmFmDeviceMasterPO);
			if(mobileExist.size()==0){
				List<EFmFmDeviceMasterPO> deviceExist=iVehicleCheckInBO.deviceImeiNumberExistsCheck(eFmFmDeviceMasterPO);
				if(deviceExist.size()==0){
					iVehicleCheckInBO.save(eFmFmDeviceMasterPO);
				}
			}
		}
	}


		/*
	 *  get all the active devices
	 */
		@POST
		@Path("/alldevices")
		public Response getAllActiveDevices(EFmFmDeviceMasterPO eFmFmDeviceMasterPO){					
			IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
			List<EFmFmDeviceMasterPO> allActiveDeviceDetails=iVehicleCheckInBO.getListOfAllActiveDevices(eFmFmDeviceMasterPO);
			List<Map<String, Object>> allDevices = new ArrayList<Map<String, Object>>();	

			for(EFmFmDeviceMasterPO deviceDetail:allActiveDeviceDetails){
				Map<String, Object>  device = new HashMap<String, Object>();	
				device.put("mobileNumber", deviceDetail.getMobileNumber());
				device.put("imeiNumber", deviceDetail.getImeiNumber());
				device.put("driverType", deviceDetail.getDeviceType());
				device.put("deviceModel", deviceDetail.getDeviceModel());
				device.put("deviceStatus", deviceDetail.getIsActive());
				device.put("deviceId", deviceDetail.getDeviceId());
				device.put("deviceStatus", deviceDetail.getIsActive());
				device.put("simOperator", deviceDetail.getSimOperator());
				device.put("deviceOs", deviceDetail.getDeviceOs());
//				log.info("APProval calllled.......................................................");
				allDevices.add(device);			
			}			
			return Response.ok(allDevices, MediaType.APPLICATION_JSON).build();
		}
		/*
		 *  enabling and disabling the devices
		 */
			@POST
			@Path("/devicestatus")
			public Response makeADeviceEnableOrDisable(EFmFmDeviceMasterPO eFmFmDeviceMasterPO){					
				IVehicleCheckInBO iVehicleCheckInBO = (IVehicleCheckInBO) ContextLoader.getContext().getBean("IVehicleCheckInBO");
				Map<String, Object>  request = new HashMap<String, Object>();	
				List<EFmFmDeviceMasterPO> updateDeviceStatus=iVehicleCheckInBO.deviceImeiNumberExistsCheck(eFmFmDeviceMasterPO);
				updateDeviceStatus.get(0).setIsActive(eFmFmDeviceMasterPO.getIsActive());
				iVehicleCheckInBO.update(updateDeviceStatus.get(0));
				request.put("status", "success");
				return Response.ok(request, MediaType.APPLICATION_JSON).build();
			}
		
}
