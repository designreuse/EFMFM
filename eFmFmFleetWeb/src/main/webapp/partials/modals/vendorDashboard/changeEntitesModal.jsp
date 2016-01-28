<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "editVehicleEntityFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-8 floatLeft">Edit Entities</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper_editEntity">
       <form name = "editVehicleEntityForm" class = "editVehicleEntityForm">    
           <div class = "col-md-12 col-xs-12 form-group"> 
              <label for = "driverName">Driver Number</label>
               <select class = 'form-control'
                        ng-model="edit.driverName" 
                        ng-options="driverData.driverName for driverData in driversData track by driverData.driverName"
                        ng-change = "updateDriverManual(edit.driverName)"
                        required>
                    <option value="">-- Select Driver --</option>
               </select>
               
                   <input type="text" class="noPointer hidden form-control" 
                       aria-label="..." 
                       ng-model = "edit.driverNumber"
                       name = 'driverName'>
<!--
               <div class="input-group">
                <div class="input-group-btn">
                    <button type="button" 
                            class="btn btn-info3 dropdown-toggle" 
                            data-toggle="dropdown" 
                            aria-haspopup="true" 
                            aria-expanded="false"><span class="caret"></span></button>
                            <ul class="dropdown-menu customDropDown">
                              <li ng-repeat = "driver in driversData" 
                                  ng-click = "updateDriverManual(driver)" 
                                  class = "pointer">{{driver.driverName}}</li>
                            </ul>
                </div>
                <input type="text" class="noPointer form-control" 
                       aria-label="..." 
                       ng-model = "edit.driverName"
                       name = 'driverName'                                              
                       ng-class = "{error: manualStartForm.driverName.$invalid && !manualStartForm.driverName.$pristine}">
              </div>
-->
            </div>
           
           <div class = "col-md-12 col-xs-12 form-group"> 
              <label for = "driverName">Vehicle Number</label>
               <select class = 'form-control'
                        ng-model="edit.vehicleNumber" 
                        ng-options="vehicleData.vehicleNumber for vehicleData in vehiclesData track by vehicleData.vehicleNumber"
                        ng-change = "updateVehicleNumberManual(edit.vehicleNumber)"
                        required>
                    <option value="">-- Select Vehicle --</option>
               </select>
<!--
               <div class="input-group">
                <div class="input-group-btn">
                    <button type="button" 
                            class="btn btn-info3 dropdown-toggle" 
                            data-toggle="dropdown" 
                            aria-haspopup="true" 
                            aria-expanded="false"><span class="caret"></span></button>
                            <ul class="dropdown-menu customDropDown">
                              <li ng-repeat = "vehicle in vehiclesData" 
                                  ng-click = "updateVehicleNumberManual(vehicle)" 
                                  class = "pointer">{{vehicle.vehicleNumber}}</li>
                            </ul>
                </div>
                <input type="text" class="noPointer form-control" 
                       aria-label="..." 
                       ng-model = "edit.vehicleNumber" 
                       name = 'vehicleNumber'                       
                       ng-class = "{error: manualStartForm.vehicleNumber.$invalid && !manualStartForm.vehicleNumber.$pristine}">
              </div>
-->
            </div>
           
           <div class = "col-md-12 col-xs-12 form-group"> 
              <label for = "driverName">Device Number</label>               
               <select class = 'form-control'
                        ng-model="edit.deviceId" 
                        ng-options="deviceData.deviceId for deviceData in devicesData track by deviceData.deviceId"
                        ng-change = "updateDeviceIdManual(edit.deviceId)"
                        required>
                    <option value="">-- Select Device --</option>
               </select>
<!--
               <div class="input-group">
                <div class="input-group-btn">
                    <button type="button" 
                            class="btn btn-info3 dropdown-toggle" 
                            data-toggle="dropdown" 
                            aria-haspopup="true" 
                            aria-expanded="false"><span class="caret"></span></button>
                            <ul class="dropdown-menu customDropDown">
                              <li ng-repeat = "device in devicesData" 
                                  ng-click = "updateDeviceIdManual(device)" 
                                  class = "pointer">{{device.mobileNumber}}</li>
                            </ul>
                </div>
                <input type="text" 
                       class="noPointer form-control" 
                       aria-label="..." 
                       ng-model = "edit.deviceNumber"
                       >
              </div>
-->
            </div>
       </form>   
      </div>        
    </div>      
<div class="modal-footer modalFooter">    
	<button type="button" class="btn btn-warning buttonRadius0" ng-click = "updateVehicleEntity(edit)" 
            ng-disabled="editVehicleEntityForm.$invalid">Update Check Details</button> 
    <button type="button" class="btn btn-default buttonRadius0" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>