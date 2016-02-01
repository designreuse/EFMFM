<div class = "vendorDashboardTemplate container-fluid">
    <div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-12 col-xs-12">Vendor Dashboard </span>            
            <div class = "col-md-12 col-xs-12 mainTabDiv_vendorDashBoard">
            <!-- /*START OF WRAPPER1 = DRIVER*/ -->
               <div class = "wrapper1" id="driverContent">             
                
                <tabset class="tabset vendorTab_vendorDashboard">
                  <tab ng-click = "getContractManag()">
                    <tab-heading>Vendor Management</tab-heading>
                        <div class = "contractMangTabContent row">
                            <div class = "col-md-3">
	                            <button class = "addVendor button btn btn-success" 
                                        ng-model = "addVendors" 
                                        ng-click = "addVendors('lg')">
	                        		<i class = ""><span>Add Vendors</span></i>
	                        	</button>
                            </div>
                            <div class = "col-md-3">
                            	<select ng-model="showRecords" 
                						ng-options="pagination.text for pagination in paginations track by pagination.value"
                						ng-change = "setLimit(showRecords)">
      						  		<option value="">-- All Records --</option>
    							</select>
    						</div>
                            <div class = "col-md-3"></div>
                        	<div class = "col-md-3">
                        		<input class = "contractManagSearchBox" ng-model = "searchVendors" placeholder = "Search Vendors">
                        	</div>
                        	
                            <div class = "col-md-12 col-xs-12 tableWrapper">
                            <table class = "contractMangTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Vendor Name</th>
                                      <th>Vendor Number</th>
                                      <th>Total Drivers</th> 
                                      <th>Total Cabs</th>
                                      <th></th>
                                    </tr> 
                                </thead>
<!--
                                <tbody ng-show = "countofFilteredVendors.length==0">
                                    <tr>
                                        <td colspan = '4'>
                                            <div class = "noData">Result: No Vendors found. Please search again!</div>
                                        </td>
                                    </tr>
                                </tbody>
-->
                                <tbody>
		                           <tr class = "visibleRow" 
                                       ng-class = "{expandRow:post.vehicle_isClicked || post.driver_isClicked}"
                                       ng-repeat-start = "post in vendorContractManagData |limitTo: numberofRecords | filter:searchVendors">
		                                 <td>{{post.vendorName}}</td>
		                                 <td>{{post.vendorMobileNumber}}</td>
		                                 <td>{{post.noOfDriver}}</td>
		                                 <td>{{post.noOfVehicle}}</td> 
		                                 <td>
			                                 <input id= "viewDriverButton_vendor{{post.vehicleId}}" 
			                                 		class = "viewDriverButton_vendor btn btn-primary btn-xs" 
			                                 		type = "button" 
			                                 		ng-disabled = "post.vehicle_isClicked" 
			                                 		ng-click = "viewDrivers(post, $index)" 
			                                 		value = "View Drivers">	                                        
			                                 <input id = "viewVehicleButton_vendor{{post.vehicleId}}" 
			                                 		class = "viewVehicleButton_vendor btn btn-info btn-xs" 
			                                 		type = "button" 
			                                 		ng-disabled = "post.driver_isClicked"
			                                 		ng-click = "viewVehicles(post, $index)" 
			                                 		value = "View Vehicles">
                                             <input id = "editVendorButton_vendor{{post.vehicleId}}" 
			                                 		class = "editVendorButton_vendor btn btn-warning btn-xs" 
			                                 		type = "button"
			                                 		ng-click = "editVendor(post, $index, 'lg')" 
			                                 		value = "View/Edit">
		                                 </td>                                    
		                            </tr>   
		                            <tr ng-repeat-end="" id = "secondRow{{post.vehicleId}}" 
		                            	class = "secondRow_VendorContractManag"
		                            	ng-show = "post.driver_isClicked || post.vehicle_isClicked">
		                            	<td colspan="5">
		                            	<div id = "driver{{post.vehicleId}}" 
		                            		 ng-show = "post.driver_isClicked" 
		                            		 class = "viewDriver" 
		                            		 ng-include = "'partials/viewDriver_Vendor.jsp'"></div>
		                            	<div id = "vehicle{{post.vehicleId}}" 
		                            	     ng-show = "post.vehicle_isClicked" 
		                            	     class = "viewVehicle" 
		                            	     ng-include = "'partials/viewVehicle_Vendor.jsp'"></div>
		                            	<div>		                            	
									</div>
									</td>		                            	
		                            </tr>                                
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
                    
                    <tab ng-click = "getEscortDetails()">
                    <tab-heading>Escort Management</tab-heading>
                    	<div class = "escortDetailsTabContent row">
                            <div class = "col-md-3">
	                            <button class = "addEscort button btn btn-success" ng-model = "addEscorts" ng-click = "addEscort()">
	                        		<i class = ""><span>Add New Escort</span></i>
	                        	</button>
                            </div>
                            <div class = "col-md-3">
                            	<select ng-model="showRecords" 
                						ng-options="pagination.text for pagination in paginations track by pagination.value"
                						ng-change = "setLimit(showRecords)">
      						  		<option value="">-- All Records --</option>
    							</select>
    						</div>
                            <div class = "col-md-3"></div>
                        	<div class = "col-md-3">
                        		<input class = "searchEscortDetail" 
                                       type = "text" 
                                       ng-model = "searchEscortDetails" 
                                       placeholder = "Search Check In Escort">
                        	</div>                      

                            <table class = "escortDetailTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Escort Id</th>
                                      <th>Escort Name</th>
                                      <th>Escort Number</th>
                                      <th>Address</th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody>
		                           <tr class = "visibleRow" 
                                       ng-repeat-start = "post in escortDetailData |limitTo: numberofRecords | filter:searchEscortDetails">
		                                 <td class = "col-md-1">{{post.escortId}}</td>
		                                 <td class = "col-md-2">{{post.escortName}}</td>   
                                         <td class = "col-md-2">{{post.escortMobileNumber}}</td>
		                                 <td class = "col-md-3">{{post.escortAddress}}</td>
                                         <td class = "col-md-4">
                                             <input id= "viewEscortDetailButton_vendor" 
			                                 		class = "viewEscortDetail_vendor btn btn-primary btn-xs" 
			                                 		type = "button"  
			                                 		ng-click = "viewEscortDetail(post, $index)" 
			                                 		value = "View Detail">	
                                             <input id= "editEscortButton_vendor{{post.regId}}" 
			                                 		class = "viewEscortDetail_vendor btn btn-warning btn-xs" 
			                                 		type = "button"  
			                                 		ng-click = "editEscortDetail(post, $index)" 
			                                 		value = "Edit">
                                             <input id = "uploadEscortButton_vendor" 
                                                    class = "uploadEscort_vendor btn btn-success btn-xs" 
                                                    type = "button"
                                                    ng-click = "uploadEscort(post, $index)" 			   	  
                                                    value = "Upload">
			                             </td>  
		                            </tr> 
                                    <tr ng-repeat-end=""
                                        ng-show = "post.isClicked"
                                        ng-class = "escort{{post.vehicleId}}" 
                                        class = "unVisisbleRow">
                                        <td colspan="5" class = "unVisisbleRowtd">
                                            <div class = "row">
                                                <div class = "col-md-12 escortLabel">
                                                    <img src="images/portlet-remove-icon.png" class="floatRight pointer" ng-click="closeEscortDetail(post)"></div>
                                                <div class = "col-md-2 escortLabel">
                                                        <span>Escort Id</span><br><span>{{escortDetails[0].escortId}}</span></div>
                                                <div class = "col-md-2 escortLabel">
                                                        <span>Full Name</span><br><span>{{escortDetails[0].escortName}}</span></div>
                                                <div class = "col-md-2 escortLabel">
                                                        <span>Father's Name</span><br><span>{{escortDetails[0].escortFatherName}}</span></div>
                                                <div class = "col-md-2 escortLabel">
                                                        <span>Gender</span><br><span>{{escortDetails[0].escortGender}}</span></div>
                                                <div class = "col-md-2 escortLabel">
                                                        <span>Date of Birth</span><br><span>{{escortDetails[0].escortdob}}</span></div>
                                                <div class = "col-md-2 escortLabel">
                                                        <span>Mobile Number</span><br><span>{{escortDetails[0].escortMobileNumber}}</span></div>
                                                <div class = "col-md-2 escortLabel">
                                                        <span>Pincode</span><br><span>{{escortDetails[0].escortPincode}}</span></div>   
                                                <div class = "col-md-4 escortLabel">
                                                        <span>Address</span><br><span>{{escortDetails[0].escortAddress}}</span></div>                                                                                      <div class = "col-md-4 escortLabel">
                                                        <span>Remarks</span><br><span>{{escortDetails[0].remarks}}</span></div> 
                                            </div>
                                        </td>
                                    </tr>
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    
                    
                   <tab ng-click = "getVehicleCheckedIn()"> 
                       <tab-heading>Check In Drivers</tab-heading>
<!--                    <tab-heading>Vehicle Check In</tab-heading>-->
                        <div class = "vehickeCheckInTabContent row">
                            <div class = "row firstRowActionDiv">
                                <div class = "floatLeft">
                                    <select ng-model="showRecords" 
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                            <option value="">-- All Records --</option>
                                   </select>
                                </div>
                                <div class = "floatRight">
                                    <input type = "text" ng-model = "searchCheckInVehicle" placeholder = "Search Check In Vehicle">
                                </div>
                            </div>
                            <table class = "vehickeCheckInTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                    <th>Vendor Name</th> 
                                      <th>Vehicle Number</th> 
                                      <th>Vehicle Capacity</th> 
                                      <th>Vehicle Make</th>                                    
                                      <th>Device Id</th>
                                      <th>Driver Name</th>
                                       <th>Driver Mobile Number</th>
<!--                                       <th>Check In</th>
 -->                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "vehicleCheckInData.length==0">
                                    <tr>
                                        <td colspan = '6'>
                                            <div class = "noData">Found No Vehicle for Check-In</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "vehicleCheckInData.length>0">
		                           <tr class = "vehicle{{post.vehicleId}}" 
                                       ng-repeat = "post in vehicleCheckInData |limitTo: numberofRecords | filter : searchCheckInVehicle">
		                                 <td>{{post.vendorName}}</td>
		                                 <td>{{post.vehicleNumber}}</td>
		                                 <td>{{post.capacity}}</td>
		                                 <td>{{post.vehicleMake}}</td>		                                 
		                                  <td>{{post.deviceId}}</td>               
		                                 <td>{{post.DriverName}}</td> 
		                                 <td>{{post.MobileNumber}}</td>                             
		                                 <!-- <td ng-click = "select_vehicleCheckIn(post)">
                                             <i ng-hide = "post.checkBoxFlag" class = "icon-check-empty"></i>
                                             <i ng-show = "post.checkBoxFlag" class = "icon-check icon-check-custom"></i>
		                           		 </td> -->
		                           		 <td>
		                           		 
			                            <input id= "changeEntity_button{{post.vehicleId}}" 
			                                 		class = "editEntity_vendor btn btn-warning btn-xs" 
			                                 		type = "button"  
			                                 		ng-click = "editEntity(post, $index)" 
			                                 		value = "Edit Entity">   	
			                         
			                                 		
			                                 		
			                              </td> 
		                            </tr>                                   
		                         </tbody>
                            </table>
                      <!--       <div class = "row lastRowActionDiv" >
                                <div class = "col-md-12 col-xs-12">
                                    <span></span>
                                </div>                                    
                                <div class = "col-md-12 col-xs-12" ng-if="0!=vehicleCheckInData">
                                    <input type = "button" 
                                           class = "submitVehicleCheckIn btn btn-default" 
                                           value="Cancel"
                                           ng-click = "cancelVehicleCheckIn()"> 
                                    <input type = "button" 
                                           class = "submitVehicleCheckIn btn btn-success" 
                                           value="Check In" 
                                           ng-click = "submitVehicleCheckIn()">
                                </div>
                            </div> -->
                        </div>
                    </tab>
                
                 
                    <tab ng-click = "getAvailableVehicle()">
                    <tab-heading>Available Driver</tab-heading>
                    	<div class = "availableVehicleTabContent row">
                            <div class = "row firstRowActionDiv">
                                <div class = "floatLeft">
                                    <select ng-model="showRecords" 
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                            <option value="">-- All Records --</option>
                                   </select>
                                </div>
                                <div class = "floatRight">
                                    <input type = "text" ng-model = "searchAvailableVehicle" placeholder = "Search Available Vehicle">
                                </div>
                            </div>
                            <table class = "availableVehicleTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                       <th>CheckInId</th>
                                      <th>Driver Number</th>
                                      <th>Device Id</th>
                                      <th>Driver Name</th>
                                      <th>Vehicle Number</th>
                                      <th>Capacity</th> 
                                       <th>Vehicle Make</th>           
                                      <th>Status</th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "availableVehicleData.length==0">
                                    <tr>
                                        <td colspan = '8'>
                                            <div class = "noData">Found No Available Vehicle</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "availableVehicleData.length>0">
		                           <tr ng-repeat = "post in availableVehicleData  |limitTo: numberofRecords | filter:searchAvailableVehicle"
                                       class = 'vehicle{{post.checkInId}}'>
		                                  <td>{{post.checkInId}}</td>
		                                 <td>{{post.driverNumber}}</td>
		                                 <td>{{post.deviceId}}</td>		                                 
                                         <td>{{post.driverName}}</td>
		                                 <td>{{post.vehicleNumber}}</td>
		                                 <td>{{post.capacity}}</td>
		                                  <td>{{post.vehicleMake}}</td>	   
                                         <td>{{post.status}}</td>
                                         <td>
                                             <input type = 'button' 
                                                    class = 'btn btn-success checkout_vendorDashboard' 
                                                    value = 'Checkout'
                                                    ng-click = 'checkoutVehicle(post)'>
                                         </td>
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                    </tab>                 
                
                   <tab ng-click = "getEscortCheckIn()">
                      <tab-heading>Escort Check-In</tab-heading>
                    	<div class = "escortCheckInTabContent row">
                            <div class = "row firstRowActionDiv">
                                <div class = "floatLeft">
                                    <select ng-model="showRecords" 
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                            <option value="">-- All Records --</option>
                                   </select>
                                </div>
                                <div class = "floatRight">
                                    <input type = "text" ng-model = "escortDetail" placeholder = "Search Escort">
                                </div>
                            </div>
		                                 
                            <table class = "escortCheckInTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Escort Name</th>
                                      <th>Escort Number</th>
                                      <th>Address</th>
                                      <th>Check In</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "escortCheckInData.length==0">
                                    <tr>
                                        <td colspan = '4'>
                                            <div class = "noData">Found No Escort for Check-In</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "escortCheckInData.length>0">
		                           <tr class = "escort{{post.escortId}}" ng-repeat = "post in escortCheckInData |limitTo: numberofRecords | filter:searchEscortcheckIn">
		                                 <td>{{post.escortId}}</td>
		                                 <td>{{post.escortName}}</td>                                        
		                                 <td>{{post.escortAddress}}</td>
                                         <td ng-click = "select_EscortCheckIn(post)">
                                             <i ng-hide = "post.checkBoxFlag" class = "icon-check-empty"></i>
                                             <i ng-show = "post.checkBoxFlag" class = "icon-check icon-check-custom"></i>
		                           		 </td>
		                            </tr>                                   
		                         </tbody>
                            </table>
                            
                            <div class = "row lastRowActionDiv">
                                <div class = "col-md-12 col-xs-12">
                                    <span></span>
                                </div>
                                    
                                <div class = "col-md-12 col-xs-12" ng-if="0!=escortCheckInData">
                                    <input type = "button" 
                                           class = "submitEscortCheckIn btn btn-default" 
                                           value="Cancel"
                                           ng-click = "cancelEscortCheckIn()"> 
                                    <input type = "button" 
                                           class = "submitEscortCheckIn btn btn-success" 
                                           value="Check In" 
                                           ng-click = "submitEscortCheckIn($index)">
                                </div>
                            </div>
                        </div>
                    </tab>
                    
                    <tab ng-click = "getEscortAvailable()">
                    <tab-heading>Escort Available</tab-heading>
                    	<div class = "escortAvailableTabContent row">
                            <div class = "row firstRowActionDiv">
                                <div class = "floatLeft">
                                    <select ng-model="showRecords" 
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                            <option value="">-- All Records --</option>
                                   </select>
                                </div>
                                <div class = "floatRight">
                                    <input type = "text" ng-model = "escortAvailable" placeholder = "Search Escort Available">
                                </div>
                            </div>
                            <table class = "escortAvailableTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Escort Name</th>
                                      <th>Escort Number</th>
                                      <th>Address</th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "escortAvailableData.length==0">
                                    <tr>
                                        <td colspan = '4'>
                                            <div class = "noData">Found No Available Escort</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "escortAvailableData.length>0">
		                           <tr  class = "escort{{post.escortId}}"  ng-repeat = "post in escortAvailableData |limitTo: numberofRecords | filter:escortAvailable">
		                                <td>{{post.escortId}}</td>
		                                 <td>{{post.escortName}}</td>                                        
		                                 <td>{{post.escortAddress}}</td>
                                         <td>
                                             <input type = 'button' 
                                                    class = 'btn btn-success checkout_vendorDashboard' 
                                                    value = 'Checkout'
                                                    ng-click = 'checkoutEscort(post)'>
                                         </td>
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    
                    <!-- DEVICE DETAIL TAB -->
                    <tab ng-click = "getDeviceDetail()">
                    <tab-heading>Device Detail</tab-heading>
                    	<div class = "escortAvailableTabContent row">
                            <div class = "row firstRowActionDiv">
                                <div class = "floatLeft">
                                    <select ng-model="showRecords" 
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                            <option value="">-- All Records --</option>
                                   </select>
                                </div>
                                <div class = "floatRight">
                                    <input type = "text" ng-model = "deviceSearch" placeholder = "Search Escort Available">
                                </div>
                            </div>
                            <table class = "escortAvailableTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                    <th>Device Id</th>
                                      <th>Device Type</th>
                                      <th>Device Model</th>
                                      <th>Device Version</th>
                                      <th>Device Number</th>
                                      <th>Device Status</th>
                                      <th></th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "deviceDetailData.length==0">
                                    <tr>
                                        <td colspan = '8'>
                                            <div class = "noData">Found No Device</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "deviceDetailData.length>0">
		                           <tr ng-repeat = "device in deviceDetailData |limitTo: numberofRecords | filter:deviceSearch">
		                                <td>{{device.deviceId}}</td>
		                                <td>{{device.driverType}}</td>
		                                 <td>{{device.deviceModel}}</td>                                        
		                                 <td>{{device.deviceOs}}</td>
		                                 <td>{{device.mobileNumber}}</td>                                        
		                                 <td>{{device.deviceStatus}}</td>
		                                 <td>
                                             <input type = "button"
                                                    class = "btn btn-success btn-xs enable_vendorDeviceDetail"
                                                    value = "Enable"
                                                    ng-click = "enableDevice(device)"
                                                    ng-class = "{disabled: device.deviceStatus=='Y'}">
                                                  
                                         </td>                                        
		                                 <td>
                                             <input type = "button"
                                                    class = "btn btn-danger btn-xs disable_vendorDeviceDetail"
                                                    value = "Disable"
                                                    ng-click = "disableDevice(device)"
                                                    ng-class = "{disabled: device.deviceStatus=='N'}"></td>
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    
                    <!-- DRIVERS ON ROAD TAB -->
                    <tab ng-click = "getDriversOnRoad()">
                    <tab-heading>Drivers On Road</tab-heading>
                    	<div class = "escortAvailableTabContent row">
                            <div class = "row firstRowActionDiv">
                                <div class = "floatLeft">
                                    <select ng-model="showRecords" 
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                            <option value="">-- All Records --</option>
                                   </select>
                                </div>
                                <div class = "floatRight">
                                    <input type = "text" ng-model = "driversOnRoad" placeholder = "Search Escort Available">
                                </div>
                            </div>
                            <table class = "escortAvailableTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                     <th>Vendor Name</th> 
                                      <th>Vehicle Number</th> 
                                      <th>Vehicle Capacity</th> 
                                      <th>Vehicle Make</th>                                    
                                      <th>Device Id</th>
                                      <th>Driver Name</th>
                                       <th>Driver Mobile Number</th>
<!--                                       <th>Check In</th>
 -->                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "deviceDetailData.length==0">
                                    <tr>
                                        <td colspan = '8'>
                                            <div class = "noData">Found No Device</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "driversOnRoadData.length>0">
		                           <tr ng-repeat = "driver in driversOnRoadData |limitTo: numberofRecords | filter:driversOnRoad">
		                                <td>{{driver.vendorName}}</td>
		                                 <td>{{driver.vehicleNumber}}</td>
		                                 <td>{{driver.capacity}}</td>
		                                 <td>{{driver.vehicleMake}}</td>		                                 
		                                  <td>{{driver.deviceId}}</td>               
		                                 <td>{{driver.DriverName}}</td> 
		                                 <td>{{driver.MobileNumber}}</td> 
		                                 <td>
                                                  
                                         </td>
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    
                    <!-- -------------------------------------------------------------------------------------->
                    
                    <!-- Vehicle Inspection TAB -->
                    <tab ng-click = "getVehicleInspectionForm()">
                    <tab-heading>Vehicle Inspection</tab-heading>
                    	<div class = "escortAvailableTabContent row">
                            <div class = "row firstRowActionDiv">
                                <div class = "floatLeft col-md-2">
                                    <select ng-model="inspection.vendor"
                                           class="form-control" 
                                           ng-options="allInspectionVendor.name for allInspectionVendor in allInspectionVendors track by allInspectionVendor.Id"
                                           ng-change = "setVendor(inspection.vendor)"
                                           >
<!--                                     <option value="">-- Select Vendors --</option>-->
                                  </select>
                                </div>
                                <div class = "floatRight col-md-8"> 
<!--                                        <button class = 'btn btn-primary floatRight buttonRadius0' ng-click = "nextToPage2()">Next Page(2)</button>                                    -->
                                </div>
                            </div>
                            <form name="inspectionForm">
                                <div class = 'page1 col-md-12'> 
                                <table class = "escortAvailableTable table table-responsive container-fluid">
                                    <thead class ="tableHeading">
                                        <tr>
                                         <th>Vendor Name</th> 
                                          <th>Vehicle Number</th> 
                                          <th>Vehicle Type</th> 
                                            
                                          <th>Vehicle Documents</th>                                    
                                          <th>List of items any one missing/expired</th>
                                          <th>Interior</th>
                                        </tr> 
                                    </thead>
                                    <tbody class = 'bottomBorder1px'>
                                       <tr>
                                            <td class = 'col-md-1'>Uber</td>
                                            <td class = 'col-md-2'>TN902123232</td>
                                            <td class = 'col-md-1'>Inova</td>
                                            <td class = 'col-md-2'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.document_rc" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>RC Document</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.document_insurance" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">
                                                        <span>Insurance Document</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.document_driverLicense" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Driver License</span></br>
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.document_updateJmp" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Update JMP</span></br>                                                
                                            </td> 
                                            <td class = 'col-md-2'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.firstAidKit" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>First Aid Kit</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.fireExtingusher" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">
                                                        <span>Fire Extingusher</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="nspection.seatBeltsWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>All Seat Belt Working</span></br>
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.placard" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Placard</span></br>     
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.mosquitoBat" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Mosquito Bat</span></br>    
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.panicButton" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Panic Button</span></br>
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.walkyTalky" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Walky Talky</span></br>    
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.GPS" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>GPS</span></br>    
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.driverUniform" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Driver Uniform</span></br>
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.stephney" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Stephney</span></br>   
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.umbrella" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Umbrella</span></br>
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.torch" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Torch</span></br>    
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.toolkit" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Toolkit</span></br>    
                                            </td> 
                                            <td class = 'col-md-4'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.seatUpholtseryCleanNotTorn" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Seat Upholtsery is Clean / Not Torn</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.vehcileRoofUpholtseryCleanNotTorn" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">
                                                        <span>Vehcile Roof Upholtsery is clean and not torn</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.vehcileFloorUpholtseryCleanNotTorn" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Vehcile Floor Upholtsery is clean and not torn</span></br>
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.vehcileDashboardClean" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Vehcile Dashboard is Clean </span></br>     
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.vehicleGlassCleanStainFree" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Vehciles glasses is Clean / Stain Free / No Films etc</span></br>    
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.vehicleInteriorLightBrightWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Vehcile Interior Lighting is Bright and working</span></br>
                                                <input type="checkbox"
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.bolsterSeperatingLastTeoSeats" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">                                                          
                                                        <span>Bolster Seperating the Last two seats (Only in Innova)</span></br>  
                                                 
                                              </td>  
                                        </tr>                                   
                                     </tbody>
                                </table>
                                <div class = 'row'>
                                    <div class = 'col-md-12'>  
                                        <button class = 'btn btn-info buttonRadius0 floatRight' ng-click = "nextToPage2()"><span><i class ='icon-chevron-right'></i></span></button>
                                    </div>
                                </div>
                                </div>

                                <div class = 'page2 col-md-12'>
                                <table class = "escortAvailableTable table table-responsive container-fluid">
                                    <thead class ="tableHeading">
                                        <tr>
                                         <th>Vendor Name</th> 
                                          <th>Vehicle Number</th> 
                                          <th>Vehicle Type</th> 
                                            
                                          <th>Exterior</th>                                    
                                          <th>Brake Working</th>
                                          <th>Tyre & Wheel Condition</th>
                                          <th>AC</th>
                                        </tr> 
                                    </thead>
                                    <tbody class = 'bottomBorder1px'>
                                       <tr>
                                            <td class = 'col-md-1'>Uber</td>
                                            <td class = 'col-md-1'>TN902123232</td>
                                            <td class = 'col-md-1'>Inova</td>
                                           
                                           
                                            <td class = 'col-md-2'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.exterior_scratches" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Scratches on the body </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.exterior_noPatchWork" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">
                                                        <span>No Patch Work</span></br>
                                            </td>

                                            <td class = 'col-md-2'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.brake_pedalBrakeWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Pedal Brake Working</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.brake_handBrakeWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">
                                                        <span>Hand Brake Working</span></br>
                                            </td>

                                            <td class = 'col-md-3'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.tyres_noDentsTrimWheel" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>No Dents on the Trim of the Wheel</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.tyres_tyresGoodCondition" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">
                                                        <span>Tyres in good condition</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.tyres_allTyresAndStephneyInflate" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>All Tyres including Stephney Inflate</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.tyres_jackAndTool" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'">
                                                        <span>Jack & Tools</span></br>
                                                <span>Number of Punctrures done</span></br>
                                                <select ng-model="inspection.tyres_numberofPunctruresdone" 
                                                        ng-options="puncture for puncture in punctures track by puncture"
                                                        ng-change = "setLimit(inspection.tyres_numberofPunctruresdone)">                                              
                                            </td>

                                            <td class = 'col-md-2'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.wiperWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Wiper Working</span></br>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class = 'row'>
                                    <div class = 'col-md-12'> 
                                        <button class = 'btn btn-info buttonRadius0 floatRight' 
                                                ng-click = "nextToPage3()"><span><i class ='icon-chevron-right'></i></span></button> 
                                        <button class = 'btn btn-primary marginRight10 buttonRadius0 floatRight' 
                                                ng-click = "backToPage1()"><span><i class ='icon-chevron-left'></i></span></button>
                                        
                                    </div>
                                </div>
                                </div>

                                <div class = 'page3 col-md-12'>
                                <table class = "escortAvailableTable table table-responsive container-fluid">
                                    <thead class ="tableHeading">
                                        <tr>
                                         <th>Vendor Name</th> 
                                          <th>Vehicle Number</th> 
                                          <th>Vehicle Type</th> 
                                            
                                          <th>AC</th>                                    
                                          <th>Indicators</th>
                                          <th>Lights / Inidcator</th>
                                        </tr> 
                                    </thead>
                                    <tbody class = 'bottomBorder1px'>
                                       <tr>
                                            <td class = 'col-md-1'>Uber</td>
                                            <td class = 'col-md-1'>TN902123232</td>
                                            <td class = 'col-md-1'>Inova</td>                                           

                                            <td class = 'col-md-3'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.ac_coolingIn5mins" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Cooling achieved end to end in 5 mns </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.ac_noSmellInAC" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>No Smell in AC  </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.ac_acVentsClean" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>AC vents are clean  </span></br>
                                            </td>                                        

                                            <td class = 'col-md-3'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.enginOilChangeIndicatorOn" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Engine oil Chaange Indicator ON </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.coolantIndicatorOn" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Coolant Indicator ON</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.brakeClutchIndicatorOn" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Brake & Clutch oil Indicator On </span></br>
                                            </td>                                       

                                            <td class = 'col-md-3'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.light_highBeamWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>High Beam Working </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.light_lowBeamWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Low Beam Working</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.light_rightFromtIndicatorWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Right Fromt Indicator Working  </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.light_leftFrontIndicatorWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Left Front Indicator Working  </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.light_parkingLightsWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Parking Lights Working</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.light_ledDayTimeRunningLightWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>LED Day Time Running Lights Working  </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.rightRearIndicatorWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Right Rear Indicator Working  </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.light_leftRearIndicatorWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Left Rear Indicator Working   </span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.light_brakeLightsOn" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Brake Lights On</span></br>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.light_reverseLightsOn" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Reverse Lights On  </span></br>
                                            </td>
                                        </tr>                                   
                                     </tbody>
                                </table>
                                <div class = 'row'>
                                    <div class = 'col-md-12'>  
                                        <button class = 'btn btn-info buttonRadius0 floatRight' 
                                                ng-click = "nextToPage4()"><span><i class ='icon-chevron-right'></i></span></button>
                                        <button class = 'btn btn-primary marginRight10 buttonRadius0 floatRight' 
                                                ng-click = "backToPage2()"><span><i class ='icon-chevron-left'></i></span></button>
                                    </div>
                                </div>
                                </div>

                                <div class = 'page4 col-md-12'>
                                <table class = "escortAvailableTable table table-responsive container-fluid">
                                    <thead class ="tableHeading">
                                        <tr>
                                         <th>Vendor Name</th> 
                                          <th>Vehicle Number</th> 
                                          <th>Vehicle Type</th> 
                                            
                                          <th>Diesel</th>
                                          <th>Horn Working</th>
                                          <th>Reflective sign board</th>
                                          <th>Driver Name</th>
                                          <th>Remarks</th>
                                        </tr> 
                                    </thead>
                                    <tbody class = 'bottomBorder1px'>
                                       <tr>
                                            <td class = 'col-md-1'>Uber</td>
                                            <td class = 'col-md-1'>TN902123232</td>
                                            <td class = 'col-md-1'>Inova</td>  
            
                                            <td class = 'col-md-2'>
                                                <span>Deisel</span></br>
                                                <select ng-model="inspection.deisel" 
                                                        ng-options="deisel for deisel in deisels"
                                                        ng-change = "setLimit(inspection.deise)">
                                            </td>
            
                                            <td class = 'col-md-2'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.hornWorking" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Horn Working  </span></br>
                                            </td>
            
                                            <td class = 'col-md-2'>
                                                <input type="checkbox" 
                                                       class = 'radiobuttonRight' 
                                                       ng-model="inspection.reflectionSignBoard" 
                                                       ng-true-value="'YES'" 
                                                       ng-false-value="'NO'"> 
                                                        <span>Reflective sign board  </span></br>
                                            </td> 
            
                                            <td lass = 'col-md-2'>
                                                <span>Driver Name</span>
                                                <input type="text" 
                                                       class = 'form-control' 
                                                       ng-model="inspection.driverName"> 
                                            </td> 
            
                                            <td class = 'col-md-2'>
                                                <span>Remarkse</span>
                                                <textarea type="text" 
                                                       class = 'form-control' 
                                                          ng-model="inspection.remarks"> </textarea>
                                            </td> 
        
                                        </tr>                                   
                                     </tbody>
                                </table>
                                <div class = 'row'>
                                    <div class = 'col-md-12'>  
                                        <button class = 'btn btn-success buttonRadius0 floatRight' ng-click = "submitInspection(inspection)">Submit</button>
                                        <button class = 'btn btn-primary buttonRadius0 marginRight10 floatRight marginRight20' 
                                                ng-click = "backToPage3()"><span><i class ='icon-chevron-left'></i></span></button>
                                    </div>
                                </div>
                                <div class = 'col-md-3'>  
                                    
                                </div>
                                </div> <!--END of Page4 -->
                                </div>
                            </form>
                        </div>
                    </tab>
                   </tabset>
                   </tabset>
                </div>
                <div class="clearfix"></div>
                <br>
            </div>
            
            
        </div>
        
    </div>
</div>