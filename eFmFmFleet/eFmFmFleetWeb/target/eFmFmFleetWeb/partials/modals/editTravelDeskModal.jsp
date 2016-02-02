<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "editModalTemplate" cg-busy="{promise:promise,templateUrl:templateUrl,message:message,backdrop:backdrop,delay:delay,minDuration:minDuration}">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Edit Travel Desk</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name = "editTravelDesk" class = "editEscortForm">           
           <div class = "col-md-6 col-xs-12 form-group">

               <label for = "escortName">Shift Time</label>
                <select class = 'form-control'
                        ng-model="update.shiftTime" 
                        ng-if = 'triptype != "DROP"'
                        ng-options="shiftsTime.shiftTime for shiftsTime in shiftsTimes track by shiftsTime.shiftTime"
                        ng-change = "setshiftTime(update.shiftTime)"
                        required>
                    <option value="">-- All Shift Times --</option>
               </select>
                <select class = 'form-control'
                        ng-model="update.shiftTime" 
                        ng-if = 'triptype == "DROP"'
                        ng-options="shiftsTime.shiftTime for shiftsTime in shiftsTimes track by shiftsTime.shiftTime"
                        ng-change = "setshiftTime(update.shiftTime)"
                        required>
                    <option value="">-- All Shift Times --</option>
               </select>
           </div>
             
           <div class = "col-md-6 col-xs-12 form-group">
               <label for = "escortMobileNumber">Days Off</label>
               <div class = 'multiSelect_travelDesk'
                    ng-dropdown-multiselect="" 
                    options="daysData" 
                    selected-model="update.daysModel" 
                    extra-settings="daysSettings"
                    translation-texts="daysButtonLabel"></div>
           </div>
           
           <div class = "col-md-6 col-xs-12 form-group">
               <label for = "escortAddress">Routes</label>
                <select class = 'form-control'
                        ng-model="update.zone" 
                        ng-options="zoneData.routeName for zoneData in zonesData track by zoneData.routeId"
                        ng-change = "setZone(update.zone)"
                        required>
                    <option value="">-- All Routes --</option>
               </select>
           </div>
           
           <div class = "col-md-6 col-xs-12 form-group">
               <label for = "dob">Area</label>
               <input type = 'text' class = 'form-control' ng-model = 'update.area' readonly>
           </div>
           
           
           <div class = "col-md-6 col-xs-12 form-group" ng-if = 'triptype == "DROP"'>
               <label for = "dob">Drop Sequence</label>
               <input type = 'text' class = 'form-control' ng-model = 'update.sequenceNumber'>
           </div>
       
<!--
           <div class = "col-md-6 col-xs-12 form-group">
             <label for = "dob">Area</label>  
                <select class = 'form-control'
                        ng-model="update.area" 
                        ng-options="areaData.areaName for areaData in areasData track by areaData.areaId"
                        ng-change = "setArea(update.area)"
                        name = 'area'
                        required>
                    <option value="">-- All Areas --</option>
               </select>
           </div>
-->
       
           <div class = "col-md-2 col-xs-12 form-group"
                ng-hide = 'triptype == "DROP"'>
               <label class ='areaTimePicker' for = "dob">{{triptype}}<span class = 'tripTypeLabel'>Time</span></label></div>           
       
           <div class = "col-md-4 col-xs-12 form-group"
                ng-if = 'triptype != "DROP"'>
               <timepicker ng-model="update.selectedDate" 
                           hour-step="hstep" 
                           minute-step="mstep" 
                           show-meridian="ismeridian" 
                           readonly-input = 'true'
                           class = "timepicker2_empReq floatLeft">
               </timepicker>
           </div>       
       
           <div class = "col-md-6 col-xs-12 form-group checkBox_travelRequest">           
               <input type="checkbox" 
                      ng-model="update.changeMasterData"
                      ng-true-value="'Y'" 
                      ng-false-value="'N'">
               <span>Change Reqular Shift</span>
           </div>
           
           <input ng-model = 'update.timePickerSelectedTime' class = 'hidden'>
           <input ng-model = 'update.selectedDays' class = 'hidden'>
       </form>   
      </div>        
    </div>      
<div class="modal-footer modalFooter"> 
	<button type="button" class="btn btn-warning buttonRadius0" ng-click = "save(update)" ng-disabled="editTravelDesk.$invalid">Save
<!--        <img ng-src = 'images/orangeBGSpinner.gif' ng-show = 'isProcessingModal'>-->
    </button> 
    <button type="button" class="btn btn-default buttonRadius0" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>