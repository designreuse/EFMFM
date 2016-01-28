<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div>
<div class = "createNewRouteModal container-fluid">
    <div class = "row">
        <div class="modal-header modal-header1 col-md-12"> 
            <div class = "row">
                <div class = "icon-pencil edsModal-icon col-md-1 floatLeft"></div>
                <div class = "modalHeading col-md-8 floatLeft">Export Route</div>
                <div class = "col-md-2 floatRight pointer">
                    <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
                </div>    
            </div>
        </div>        
    </div>      
<div class="modal-body modalMainContent modalMainContentDownload">     
  <form name = "assignCabForm" class = 'createNewZone'>  
	   <div class = "col-md-6 col-xs-12"> 
           <div>
           <label>Trip Type</label>
               <select ng-model="download.tripType"
                       class="form-control" 
                       ng-options="tripType.text for tripType in tripTypes track by tripType.value"
                       ng-change = "setTripType(download.tripType)"
                       required>
              </select>
           </div>
       </div> 
        <div class = 'col-md-6'>
            <label>Date</label>                    
            <div class = "input-group calendarInput">
                <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="openDownloadDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
              <input type="text" 
                     ng-model="download.date" 
                     class="form-control" 
                     placeholder = "Select Date" 
                     datepicker-popup = '{{format}}'
                     is-open="datePicker.openeddownloadDate" 
                     show-button-bar = false
                     show-weeks=false
                     datepicker-options = 'dateOptions'
                     readonly
                     name = 'date'>
               </div>
        </div> 
	   <div class = "col-md-6 col-xs-12"> 
           <div>
<!--           <b>CustomJar.jar</b>&nbsp;<a href="downloadRoutes.do?fileName=PICKUP_(05-30-00)_26-01-2016_22-42-52.xlsx">Click and download file here</a>-->
           <label>Select Shift Time</label><br>
               <input type="radio" 
                       class = "floatLeft select_radio_assignCb radio_assignCab"
                       ng-model="shiftTime"
                       ng-disabled = "isRadioDisable()"
                       value="preDefineShiftTime" 
                       ng-change = "selectShiftTimeRadio(shiftTime)"
                       >
               <select ng-model="download.shiftTime" 
                       class="form-control select_assignCab floatLeft" 
                       ng-options="shiftTime.shiftTime for shiftTime in shiftsTime | orderBy:'shiftTime' "
                       ng-disabled = "shiftTime != 'preDefineShiftTime'"
                       ng-required = "shiftTime == 'preDefineShiftTime'">
              </select>
           </div>
       </div>  
    <div class = "col-md-6 col-xs-12"> 
        <div class = "timerDiv">
            <input type="radio" 
                   ng-model="shiftTime" 
                   class = "timepickerRadioButton radio_assignCab floatLeft"
                   value="ADHOCTime" 
                   ng-disabled = "isRadioDisable()"
                   ng-change = "selectShiftTimeRadio2(shiftTime)"
                   >     
                <timepicker ng-model="download.createNewAdHocTime" 
                          ng-disabled = "typeOfShiftTimeSelected != 'ADHOCTime'"
                          hour-step="hstep" 
                          minute-step="mstep" 
                          show-meridian="ismeridian" 
                          readonly-input = 'true'
                          class = "timepicker2_empReq floatLeft">
               </timepicker>
        </div>
    </div>
<!--
      <div class = 'col-md-6 marginTopNeg20'>
          <label>File List</label><br>
            <select ng-model="download.fileList" 
                   class="form-control select_assignCab floatLeft" 
                   ng-options="reportList.reportName for reportList in reportList track by reportList.reportName"
                   ng-change=fileSelect(download.fileList) >
                 <option value="">-- Select File Name --</option>
          </select>
      </div>
-->

      <div class = 'col-md-6 marginTopNeg20'>
          <a class = "hrefFile" href = "temp/test2.xlsx">{{download.fileList.reportName}}</a>
      </div>

  </form>   
</div>
     <div class="modal-footer modalFooter createNewZone_modalFooter">  
           <button type="button" class="btn btn-close floatRight noMoreClick" ng-click = "cancel()">Cancel</button>
           <button type="button" 
                   class="btn btn-success floatRight noMoreClick" 
                   ng-click = "ExportRoute(download)"
                   ng-disabled="assignCabForm.$invalid">Export Route</button>

<!--
           <button type="button" 
                   class="btn btn-success floatRight noMoreClick" 
                   ng-click = "downloadFile(download)"
                   ng-disabled="assignCabForm.$invalid">Download File</button>
-->

<!--
         <div class = "hrefFile btn btn-success floatRight buttonRadius0 noMoreClick" style = "color:white;"><a style = "color: white;" href = "{{fileSelected}}">Download File {{download.fileList.reportName}}</a>
         
         <a target="_self" href="example.com/uploads/asd4a4d5a.pdf" download="foo.pdf"></a>
             
         </div>           
-->
     </div>
</div>


<!--
<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring 3 MVC</title>
</head>
<body>
<h2>Spring MVC download files: ${message}</h2>
<b>CustomJar.jar</b>&nbsp;<a href="downloadFiles.do">Click and download file here</a> 
</body>
</html> --%>
-->


