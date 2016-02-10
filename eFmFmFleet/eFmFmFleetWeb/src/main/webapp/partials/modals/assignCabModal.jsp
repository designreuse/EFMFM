<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div>
<div class = "createNewRouteModal container-fluid">
    <div class = "row">
        <div class="modal-header modal-header1 col-md-12"> 
            <div class = "row">
                <div class = "icon-pencil edsModal-icon col-md-1 floatLeft"></div>
                <div class = "modalHeading col-md-8 floatLeft">Assign Cab</div>
                <div class = "col-md-2 floatRight pointer">
                    <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
                </div>    
            </div>
        </div>        
    </div>      
<div class="modal-body modalMainContent">     
  <form name = "assignCabForm" class = 'createNewZone'>  
	   <div class = "col-md-12 col-xs-12"> 
           <div>
           <label>Trip Type</label>
               <select ng-model="assignCab.tripType"
                       class="form-control" 
                       ng-options="tripType.text for tripType in tripTypes track by tripType.value"
                       ng-change = "setTripType(assignCab.tripType)"
                       required>
              </select>
           </div>
       </div>  
	   <div class = "col-md-6 col-xs-12"> 
           <div>
           <label>Select Shift Time</label><br>
               <input type="radio" 
                       class = "floatLeft select_radio_assignCb radio_assignCab"
                       ng-model="shiftTime"
                       ng-disabled = "isRadioDisable()"
                       value="preDefineShiftTime" 
                       ng-change = "selectShiftTimeRadio(shiftTime)"
                       >
               <select ng-model="assignCab.shiftTime" 
                       class="form-control select_assignCab floatLeft" 
                       ng-options="shiftTime.shiftTime for shiftTime in shiftsTime | orderBy:'shiftTime' "
                       ng-disabled = "shiftTime != 'preDefineShiftTime'"
                       ng-required = "shiftTime == 'preDefineShiftTime'"
                       ng-change = 'setAlgo()'>
<!--                 <option value="">-- Select Shift Time --</option>-->
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
                <timepicker ng-model="assignCab.createNewAdHocTime" 
                          ng-disabled = "typeOfShiftTimeSelected != 'ADHOCTime'"
                          hour-step="hstep" 
                          minute-step="mstep" 
                          show-meridian="ismeridian" 
                          readonly-input = 'true'
                          class = "timepicker2_empReq floatLeft"
                            ng-change = 'setAlgo()'>
               </timepicker>
        </div>
    </div> 
    <div class = 'col-md-12 marginTopNeg15'>
        <fieldset class = 'fieldSetTravelDesk' ng-disabled = "!assignCab.applyAlgorithm">
            <legend class = 'legendTravelDesk'><input type="checkbox" 
              ng-model="assignCab.applyAlgorithm" ng-change = 'applyAlgorithm(assignCab.tripType, shiftTime)'> Apply algorithm</legend>
            <div class = 'row marginNeg10'>
                <div class = 'col-md-6 marginBottom15'>
                    <label>{{assignCab.tripType.text}} Date</label>                    
                    <div class = "input-group calendarInput">
                        <!--  tooltip="Date of Birth"
                         tooltip-placement="top"
                         tooltip-trigger="mouseenter"> -->
                        <span class="input-group-btn">
                            <button class="btn btn-default" ng-click="openAssignDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
                      <input type="text" 
                             ng-model="assignCab.assignDate" 
                             class="form-control" 
                             placeholder = "Select Date" 
                             datepicker-popup = '{{format}}'
                             min-date = "setMinDate"
                             is-open="datePicker.openedassignDate" 
                             show-button-bar = false
                             show-weeks=false
                             datepicker-options = 'dateOptions'
                             ng-change = "applyAlgorithm(assignCab.tripType, shiftTime)"
                             readonly
                             ng-required = "assignCab.applyAlgorithm"
                             name = 'assignDate'>

           </div>
                </div>
                <div class = 'col-md-6 marginBottom15'>
                    <label>Vehicle Tempo</label>
                    <select ng-model="assignCab.vehicleTempo" 
                       class="form-control select_assignCab floatLeft" 
                       ng-options="assignCabTempo.valueList for assignCabTempo in assignCabsTempo track by assignCabTempo.valueList"
                       ng-disabled = "!assignCab.assignDate">
<!--                 <option value="">-- Select Shift Time --</option>-->
                    </select>
                </div>
                <div class = 'col-md-6 marginBottom15'>
                    <label>Vehicle Inova</label>
                    <select ng-model="assignCab.vehicleInova" 
                       class="form-control select_assignCab floatLeft" 
                       ng-options="assignCabInova.valueList for assignCabInova in assignCabsInova track by assignCabInova.valueList"
                       ng-disabled = "!assignCab.assignDate">
<!--                 <option value="">-- Select Shift Time --</option>-->
                    </select>
                </div>
                <div class = 'col-md-6 marginBottom15'>
                    <label>Number of Employee</label>
                    <input type = 'text' class = 'form-control' placeholder = 'Number of Employees' ng-model = 'assignCab.noOfEmp' readonly>
                </div>
            </div>
        </fieldset>
    </div>
    <div class = "col-md-12 col-xs-12">           
       <input type="checkbox" 
              ng-model="assignCab.needRoutewise">
       <span>Assign a Cab by Route</span>
    </div>
      
    <div class = "col-md-12 col-xs-12" ng-show = "assignCab.needRoutewise"> 
           <div>
           <label>Route Name</label>
               <select ng-model="assignCab.zone"
                       class="form-control" 
                       ng-options="allZoneData.routeName for allZoneData in allZonesData track by allZoneData.routeName"
                       ng-required = 'assignCab.needRoutewise'>
                 <option value="">-- Select Zone --</option>
              </select>
           </div>
       </div>
  </form>   
</div>
     <div class="modal-footer modalFooter createNewZone_modalFooter">  
           <button type="button" class="btn btn-close floatRight noMoreClick" ng-click = "cancel()">Cancel</button>
           <button type="button" 
                   class="btn btn-success floatRight noMoreClick" 
                   ng-click = "assign(assignCab)"
                   ng-disabled="assignCabForm.$invalid">Assign Cab</button>
           
     </div>
</div>