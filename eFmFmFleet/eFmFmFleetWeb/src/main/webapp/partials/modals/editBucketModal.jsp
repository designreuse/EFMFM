<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "manualStartModal container-fluid" cg-busy="{promise:promise,templateUrl:templateUrl,message:message,backdrop:backdrop,delay:delay,minDuration:minDuration}">
    <div class = "row">
        <div class="modal-header modal-header1 col-md-12">
          <div class = "row"> 
            <div class = "icon-map-marker edsModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Edit Bucket</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
          </div>
        </div>        
    </div> 
    
<div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name="manualStartForm" class = "row driverEditForm">
           <div class = "col-md-6 col-xs-12 form-group"> 
              <label for = "driverName">Driver Name</label>
<!--               <input type = 'text' ng-model = 'edit.x'>-->
               <select class = 'form-control'
                        ng-model="edit.driverName" 
                        ng-options="checkInEntitieData.driverName for checkInEntitieData in checkInEntitiesData track by checkInEntitieData.driverName"
                        ng-change = "updateDriverManual(edit.driverName)"
                        required>
                    <option value="">-- Select Driver --</option>
               </select>
               <input type = 'text' ng-model = 'edit.driverNumber' class = 'hidden'>
            </div><!-- /.col-lg-6 -->
           <div class = "col-md-6 col-xs-12 form-group"> 
              <label for = "driverName">Vehicle Number</label>               
               <select class = 'form-control'
                        ng-model="edit.vehicleNumber" 
                        ng-options="checkInEntity.vehicleNumber for checkInEntity in checkInEntitiesData track by checkInEntity.vehicleNumber"
                        ng-change = "updateVehicleNumberManual(edit.vehicleNumber)"
                        required>
                    <option value="">-- Select Vehicle --</option>
               </select>
               
            </div>
           <div class = "col-md-6 col-xs-12 form-group"> 
              <label for = "driverName">Device ID</label>       
               <select class = 'form-control'
                        ng-model="edit.deviceId" 
                        ng-options="checkInEntity.deviceId for checkInEntity in checkInEntitiesData track by checkInEntity.deviceId"
                        ng-change = "updateDeviceIdManual(edit.deviceId)"
                        required>
                    <option value="">-- Select Device --</option>
               </select>
               
            </div>
           
           <div class = "col-md-6 col-xs-12 form-group"
                ng-if = "escortRequired !='N'"> 
              <label for = "driverName">Escort Name</label>
               <select class = 'form-control'
                        ng-model="edit.escortName" 
                        ng-options="escortData.escortName for escortData in escortsData track by escortData.escortName"
                        ng-change = "updateEscortManual(edit.escortName)"
                        ng-required = "escortNameFlag !='Escort Required But Not Available'">
                    <option value="">-- Select Escort --</option>
               </select>
               
            </div><!-- /.col-lg-6 --> 
           
<!--           <input type="text" class="hidden form-control" ng-model = "edit.escortName">-->
           
    </form>
    </div>
<!--
    <div class = "EditBucketTable">
      <table class = "serviceMappingTable2 table table-responsive container-fluid">
        <thead class ="tableHeading">
           <tr>
              <th>ID</th>
              <th>Employee Name</th>
              <th>Employee Number</th>
              <th>Employee Address</th>
              <th>Pick/Drop</th>
              <th>Cancel Request</th>
              <th>Manual Swap</th>
           </tr> 
       </thead>
       <tbody ng-show = "routes.empDetails.length==0">
             <tr><td colspan = "7"><div class = "noData_serviceMapping">There is No Employee in this Route</div></td></tr>
       </tbody>
       <tbody>                                   
		     <tr ng-repeat = "employee in routes.empDetails" 
                 ng-show = "routes.empDetails.length>0" 
                 id = "row{{employee.employeeId}}">                                       
		         <td class = "col-md-1 id"><span>{{employee.employeeId}}</span></td>
                 <td class = "col-md-2 name"><span>{{employee.name}}</span> </td>
                 <td class = "col-md-2 number"><span>{{employee.employeeNumber}}</span> </td>
                 <td class = "col-md-3 address"><span>{{employee.address}}</span></td>		                                        
		         <td class = "col-md-1 triptype"><span>{{employee.tripType}}</span></td>
                 <td class = "col-md-1 cancel">
                          <div class = "pointer" 
                               tooltip="Delete Employee"
                               tooltip-placement="top"
                               tooltip-trigger="mouseenter"
                               ng-click = "deleteEmployee(employee)">
                                  <i class = "delete-employee_TravelDesk icon-remove-sign"></i>
                                         </div> 
                  </td>
                  <td class = "col-md-1 manualSwap">
                            <button class = "btn upDownDiv_serviceMapping pointer floatLeft"
                                    id = "down{{employee.employeeId}}"
                                    tooltip="Move Down"
                                    tooltip-placement="top"
                                    tooltip-trigger="mouseenter"
                                    ng-click = "moveDown(employee)"><i class = "icon-arrow-down"></i></button>
                             <button class = "btn upDownDiv_serviceMapping pointer floatLeft"
                                     id = "up{{employee.employeeId}}"
                                     tooltip="Move Up"
                                     tooltip-placement="top"
                                     tooltip-trigger="mouseenter"
                                     ng-click = "moveUp(employee)"><i class = "icon-arrow-up"></i></button>
                  </td>
		        </tr>                                       
		  </tbody>
        </table>
    </div>
-->
</div>
    
<div class="modal-footer modalFooter">     
    <button type="button" 
            class="btn btn-info editBucketbutton buttonRadius0" 
            ng-click = "save(edit)" 
            ng-disabled="manualStartForm.$invalid">Save</button>
    <button type="button" class="btn btn-default editBucketbutton buttonRadius0" ng-click = "cancel()">Cancel</button> 
</div>
</div>