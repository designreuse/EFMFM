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
                 <option value="">-- Select Trip Type --</option>
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
                       required>
               <select ng-model="assignCab.shiftTime" 
                       class="form-control select_assignCab floatLeft" 
                       ng-options="shiftTime.shiftTime for shiftTime in shiftsTime | orderBy:'shiftTime' "
                       ng-disabled = "typeOfShiftTimeSelected != 'preDefineShiftTime'"
                       required>
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
                   required>     
                <timepicker ng-model="assignCab.createNewAdHocTime" 
                          ng-disabled = "typeOfShiftTimeSelected != 'ADHOCTime'"
                          hour-step="hstep" 
                          minute-step="mstep" 
                          show-meridian="ismeridian" 
                          readonly-input = 'true'
                          class = "timepicker2_empReq floatLeft">
               </timepicker>
        </div>
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