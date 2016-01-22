<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "addNewShiftModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Add New Shift</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body addNewShiftTimeModal modalMainContent">
       <form class = "addNewShift" name = "addNewShift">
         <div class = "userInfo_adminForm row">
                <div class ='col-md-5'>
                    <label class = 'floatLeft'>Shift Time</label>
                    <timepicker ng-model="newShift.createNewAdHocTime" 
                              hour-step="hstep" 
                              minute-step="mstep" 
                              show-meridian="ismeridian" 
                              readonly-input = 'true'
                              class = "timepicker2_empReq floatLeft">
                    </timepicker>
                </div>
                <div class ='col-md-7'>
                    <label>Trip Type</label><br>
                    <select ng-model="newShift.tripType"
                            class = 'form-control'
                            ng-options="tripType.text for tripType in tripTypes track by tripType.value"
                            ng-change = "setTripType(newShift.tripType)"
                            required>
                        <option value="">-- Select Trip Type --</option>
                    </select>
                </div>
<!--
                <div class ='col-md-4'>
                    <input type = 'button' class = 'btn btn-success' value = 'Save' ng-click = 'addNewShift(newShift)'>
                </div>
-->
         </div>  
       </form>
    </div>      
<div class="modal-footer modalFooter"> 
	<button type="button" class="btn btn-success buttonRadius0 noMoreClick" 
            ng-click = "saveNewShift(newShift)" 
            ng-disabled="addNewShift.$invalid">Save</button> 
    <button type="button" class="btn btn-default buttonRadius0 noMoreClick" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>
<!--ng-disabled="addUserForm.$invalid"-->