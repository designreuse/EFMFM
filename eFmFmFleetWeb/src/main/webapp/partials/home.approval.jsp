<div class = "approvalTemplate container-fluid">
    <div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-6 col-xs-12">Approval</span>  
            <div class = "tags_Approval col-md-6 col-xs-12">
            	<div class = " floatRight" ng-click="scrollTo('vendor')"><span>Vendor</span></div>
            	<div class = " floatRight" ng-click="scrollTo('vehicle')"><span>Vehicle</span></div>
		        <div class = "floatRight" ng-click="scrollTo('driver')"><span>Driver</span></div>	        
		    </div>           
            <div class = "col-md-12 col-xs-12 mainTabDiv_Approval">
          <!--   /*START OF WRAPPER1 = DRIVER*/ -->
               <div class = "wrapper1" id="driverContent">
                <div class = "heading2 row">
                    <span class = "col-md-7 floatLeft" id = "driver">Driver</span>
                    <div class= "col-md-5 floatRight">
                    	<efmfm-button img-class = "efmfm_approvalButtons_collapse"
                                      src-url = "images/portlet-collapse-icon"
                                      selected-url = "images/portlet-expand-icon"
                                      hover-url = "images/portlet-collapse-icon"
                                      alt-text = "Collapse Window"
                                      main-div = "driverContent"
                                      target-div = "driverTab_Approval">
                        </efmfm-button> 
                        <efmfm-button img-class = "efmfm_approvalButtons_reload"
                                      src-url = "images/portlet-reload-icon"
                                      selected-url = "images/portlet-reload-icon"
                                      hover-url = "images/portlet-reload-icon"
                                      alt-text = "Reload Window"
                                      main-div = "driverContent"
                                      target-div = "driverTab_Approval"
                                      ng-click='refreshDriverApproval()'>
                        </efmfm-button>
                        <efmfm-button img-class = "efmfm_dashboarButtons_remove"
                                      src-url = "images/portlet-remove-icon"
                                      selected-url = "images/portlet-remove-icon"
                                      hover-url = "images/portlet-remove-icon"
                                      alt-text = "Remove Window"
                                      main-div = "driverContent"
                                      target-div = "driverTab_Approval">
                        </efmfm-button>
                    </div>
                </div>
                <tabset class="tabset driverTab_Approval">
                	<tab ng-click = "getPendingDriver()">
                      <tab-heading>Pending Driver</tab-heading>
                        <div class = "pendingTabContent row">
                            <table class = "pendingTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Driver Id</th>
                                      <th>Driver Name</th>
                                      <th>Driver Number</th>
                                      <th>Vendor Name</th>
                                      <th>Action</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "driversPendingData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Drivers Pending</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "driversPendingData.length>0">
		                           <tr class = "rowSeen" 
                                       ng-repeat-start = "post in driversPendingData | filter:searchtextDriver"
                                       ng-class = "{driverRowExpanded:post.isClicked}">
		                                 <td>{{post.driverId}}</td>
		                                 <td>{{post.name}}</td>
		                                
		                                 <td>{{post.mobileNumber}}</td>
		                                  <td>{{post.vendorName}}</td>
		                                 <td>
		                                 	<input ng-show = "!post.isClicked" 
                                                   class = "viewButton btn btn-primary btn-xs buttonRadius0" 
                                                   type = "button" 
                                                   ng-class = "{{post.driverId}}" 
                                                   ng-click = "viewDriver(post, $index)" 
                                                   value = "View Detail">
		                                 	<input ng-show = "post.isClicked" 
                                                   class = "approvalButton btn btn-success btn-xs buttonRadius0" 
                                                   type = "button" ng-class = "{{post.driverId}}" 
                                                   ng-click = "approveDriver(post, $index)" 
                                                   value = "Approve"
                                                   ng-disabled = "supervisorRole">
		                                 	<input ng-show = "post.isClicked" 
                                                   class = "rejectButton btn btn-danger btn-xs buttonRadius0" 
                                                   type = "button" ng-click = "rejectDriver(post, $index)" 
                                                   value = "Reject"
                                                   ng-disabled = "supervisorRole">
		                                 </td>                                    
		                            </tr>  
		                            <tr ng-show = "post.isClicked" 
                                        ng-repeat-end=""  
                                        ng-class = "{{post.driverId}}" 
                                        class = "driverPendingDetail">
		                            	<td colspan="5">
		                            	<div class="row marginRow">
                                            <div class = "col-md-9 marginRow padding0">
                                                <div class = "row marginRow driverInfo_approval">
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Driver Name</span><br><span>{{driverDetail[0].name}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Date of Birth</span><br><span>{{driverDetail[0].dob}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Father Name</span><br><span>{{driverDetail[0].fatherName}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Driver Address</span><br><span>{{driverDetail[0].address}}</span></div>
                                                    
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Driver Mobile Number</span><br><span>{{driverDetail[0].mobileNumber}}</span></div>
                                                    
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vendor Name</span><br><span>{{driverDetail[0].vendorName}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Driver License Number</span><br><span>{{driverDetail[0].licenceNum}}</span></div>
                                                    
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>License Valid Date</span><br><span>{{driverDetail[0].licenceValid}}</span></div>
                                                    
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Medical Valid Date</span><br><span>{{driverDetail[0].medicalValid}}</span></div>
                                                    
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Police Verification</span><br><span>{{driverDetail[0].policeVerification}}</span></div>
                                                    
                                                </div>
                                            </div>
                                            <div class = "col-md-3">
                                                <img src="images/portlet-remove-icon.png" 
                                                     class = "floatRight pointer" 
                                                     ng-click = "closeDriver(post)">
                                                <div class = "col-md-12">
                                                    <div ng-repeat = "doc in driverDetail[0].documents" class = "floatLeft imageDiv_approval" id = "div{{$index}}">
                                                       <i class = "icon-remove-sign pointer" ng-click="deleteDocument($index)"></i>
                                                       <a href="{{doc.location}}" class = "noLinkLine">
                                                            <img class="img-responsive img-rounded fileImg" 
                                                                  alt="Responsive image" 
                                                                  ng-src = "{{doc.imgSrc}}">
                                                                <div class = "docName"><span>{{doc.type}}</span></div>
                                                        </a>
                                                    </div>
		                            		    </div>
                                            </div>
		                            	</div>
		                            	 </td>                            	
		                            </tr>                                 
		                         </tbody>
                                
                            </table>
                        </div>
                    </tab>
                    <tab ng-click = "getRegisteredDriver()">
                     <tab-heading>Registered Driver</tab-heading>
                        <div class = "registeredTabContent row">
                            <table class = "registeredTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                     <th>Driver Id</th>
                                      <th>Driver Name</th>
                                      <th>Driver Number</th>
                                      <th>Vendor Name</th>
                                      <th>Action</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "driversRegisterData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Drivers for Registration</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "driversRegisterData.length>0">
		                           <tr ng-repeat = "post in driversRegisterData | filter:searchtextDriver">
		                                 <td>{{post.driverId}}</td>
		                                 <td>{{post.name}}</td>
		                                 <td>{{post.mobileNumber}}</td>
		                                 <td>{{post.vendorName}}</td>
		                                 <td><input class = "removeButton btn btn-warning btn-xs buttonRadius0" 
                                                    type = "button" 
                                                    ng-click = "removeDriver(post, $index)" 
                                                    value = "Remove"></td>                                    
		                            </tr>  
		                                                                 
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    <tab ng-click = "getUnRegisteredDriver()">
                      <tab-heading>Unregistered Driver</tab-heading>
                    	<div class = "unregisteredTabContent row">
                            <table class = "unregisteredTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                     <th>Driver Id</th>
                                      <th>Driver Name</th>
                                      <th>Driver Number</th>
                                      <th>Vendor Name</th>
                                      <th>Action</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "driversUnregisterData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Drivers for Registration</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "driversUnregisterData.length>0">
		                           <tr ng-repeat = "post in driversUnregisterData | filter:searchtextDriver">
		                                 <td>{{post.driverId}}</td>
		                                 <td>{{post.name}}</td>
		                                 <td>{{post.mobileNumber}}</td>
		                                 <td>{{post.vendorName}}</td>
		                                 <td>
		                                 	<input class = "addagainButton btn btn-info btn-xs buttonRadius0" 
                                                   type = "button" 
                                                   ng-click = "addagainDriver(post, $index)" 
                                                   value = "Add Again">
										</td>                                    
		                            </tr> 		                                                
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    <input type = 'text' class = "approvalSearchBox" ng-model = "searchtextDriver">
                   </tabset>
                </div>
                <div class="clearfix"></div>
                <br>
              <!--    /*END OF WRAPPER1 = DRIVER*/
                 /*START OF WRAPPER2 = VEHICLE*/ -->
                <div class = "wrapper1" id = "vehicleContent">
                <div class = "heading2 row">
                    <span class = "col-md-7 floatLeft" id = "vehicle">Vehicle</span>
                    <div class= "col-md-5 floatRight">
                    	<efmfm-button img-class = "efmfm_approvalButtons_collapse"
                                      src-url = "images/portlet-collapse-icon"
                                      selected-url = "images/portlet-expand-icon"
                                      hover-url = "images/portlet-collapse-icon"
                                      alt-text = "Collapse Window"
                                      main-div = "vehicleContent"
                                      target-div = "vehicleTab_Approval">
                        </efmfm-button> 
                        <efmfm-button img-class = "efmfm_approvalButtons_reload"
                                      src-url = "images/portlet-reload-icon"
                                      selected-url = "images/portlet-reload-icon"
                                      hover-url = "images/portlet-reload-icon"
                                      alt-text = "Reload Window"
                                      main-div = "vehicleContent"
                                      target-div = "vehicleTab_Approval"
                                      ng-click='refreshVehicleApproval()'>
                        </efmfm-button>
                        <efmfm-button img-class = "efmfm_dashboarButtons_remove"
                                      src-url = "images/portlet-remove-icon"
                                      selected-url = "images/portlet-remove-icon"
                                      hover-url = "images/portlet-remove-icon"
                                      alt-text = "Remove Window"
                                      main-div = "vehicleContent"
                                      target-div = "vehicleTab_Approval">
                        </efmfm-button>
                    </div>
                </div>
                <tabset class="tabset vehicleTab_Approval">
                <tab ng-click = "getPendingVehicle()">
                    <tab-heading>Pending Vehicle</tab-heading>
                        <div class = "pendingTabContent row">
                            <table class = "pendingTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Vehicle Id</th>
                                      <th>Vehicle Vendor Name</th>
                                      <th>Vehicle Number</th>
                                      <th>Action</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "vehiclesPendingData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Vehicles Pending</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "vehiclesPendingData.length>0">
		                           <tr class = "rowSeen" 
                                       ng-repeat-start = "post in vehiclesPendingData | filter:searchtextVehicle"
                                       ng-class = "{vehicleRowExpanded:post.isClicked}">
		                                 <td>{{post.vehicleId}}</td>
		                                 <td>{{post.name}}</td>
		                                 <td>{{post.vehicleNumber}}</td>
		                                 <td><input ng-show = "!post.isClicked" 
                                                   class = "viewButton btn btn-primary btn-xs buttonRadius0" 
                                                   type = "button" 
                                                   ng-click = "viewVehicle(post, $index)" 
                                                   value = "View Detail">
		                                 	<input ng-show = "post.isClicked" 
                                                   class = "approvalButton btn btn-success btn-xs buttonRadius0" 
                                                   type = "button" ng-class = "{{post.vehicleId}}" 
                                                   ng-click = "approveVehicle(post, $index)" 
                                                   value = "Approve"
                                                   ng-disabled = "supervisorRole">
		                                 	<input ng-show = "post.isClicked" 
                                                   class = "rejectButton btn btn-danger btn-xs buttonRadius0" 
                                                   type = "button" ng-click = "rejectVehicle(post, $index)" 
                                                   value = "Reject"
                                                   ng-disabled = "supervisorRole"></td>                                    
		                            </tr>  
		                            <tr ng-show = "post.isClicked" 
                                        ng-repeat-end=""  
                                        ng-class = "vehicle{{post.vehicleId}}" 
                                        class = "driverPendingDetail unvisibleRowVehiclePending">
		                            	<td colspan="5">
		                            	<div class="row marginRow">
                                            <div class = "col-md-9 marginRow padding0">
                                                <div class = "row marginRow driverInfo_approval">
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vehicle Id</span><br><span>{{vehicleDetail[0].vehicleId}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vehicle Number</span><br><span>{{vehicleDetail[0].vehicleNumber}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vendor Name</span><br><span>{{vehicleDetail[0].vendorName}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vehicle Make</span><br><span>{{vehicleDetail[0].vehicleMake}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vehicle Model</span><br><span>{{vehicleDetail[0].vehicleModel}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vehicle Capacity</span><br><span>{{vehicleDetail[0].vehicleCapacity}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vehicle Engine Number</span><br><span>{{vehicleDetail[0].vehicleEngineNum}}</span></div>
                                                    
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vehicle Registration Number</span><br><span>{{vehicleDetail[0].vehicleRegistrationNum}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Insurrance Valid Date</span><br><span>{{vehicleDetail[0].insuranceValid|date : 'shortDate'}}</span></div>
                                                    
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Permit Valid Date</span><br><span>{{vehicleDetail[0].permitValid|date : 'shortDate'}}</span></div>
                                                    
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Tax Certification</span><br><span>{{vehicleDetail[0].taxCertificate|date : 'shortDate'}}</span></div>
                                                    
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Pollution Valid</span><br><span>{{vehicleDetail[0].polutionValid|date : 'shortDate'}}</span></div>
                                                    
                                                </div>
                                            </div>
                                            <div class = "col-md-3">
                                                <img src="images/portlet-remove-icon.png" 
                                                     class = "floatRight pointer" 
                                                     ng-click = "closeDriver(post)">
                                                <div class = "col-md-12">
                                                    <div ng-repeat = "doc in vehicleDetail[0].documents" class = "floatLeft imageDiv_approval" id = "div{{$index}}">
                                                       <i class = "icon-remove-sign pointer" ng-click="deleteDocument($index)"></i>
                                                            <a href="{{doc.location}}" class = "noLinkLine">
                                                                <img class="img-responsive img-rounded fileImg" 
                                                                  alt="Responsive image" 
                                                                  ng-src = "{{doc.imgSrc}}">
                                                                <div class = "docName"><span>{{doc.type}}</span></div>
                                                            </a>
                                                    </div>
		                            		    </div>
                                            </div>
		                            	</div>
		                            	 </td>                            	
		                            </tr>                                   
		                         </tbody>
                                
                            </table>
                        </div>
                    </tab>
                    <tab ng-click = "getRegisteredVehicle()">
                    <tab-heading>Registered Vehicle</tab-heading>
                        <div class = "registeredTabContent row">
                            <table class = "registeredTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Vehicle Id</th>
                                      <th>Vehicle Vendor Name</th>
                                      <th>Vehicle Number</th>
                                      <th>Action</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "vehiclesRegisterData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Vehicles for Registration</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "vehiclesRegisterData.length>0">
		                           <tr ng-repeat = "post in vehiclesRegisterData | filter:searchtextVehicle">
		                                 <td>{{post.vehicleId}}</td>
		                                 <td>{{post.name}}</td>
		                                 <td>{{post.vehicleNumber}}</td>
		                                 <td><input class = "removeButton btn btn-warning btn-xs buttonRadius0" 
                                                    type = "button" 
                                                    ng-click = "removeVehicle(post, $index)" 
                                                    value = "Remove"></td>                                    
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    <tab ng-click = "getUnRegisteredVehicle()">
                    <tab-heading>Unregistered Vehicle</tab-heading>
                    	<div class = "unregisteredTabContent row">
                            <table class = "unregisteredTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                       <th>Vehicle Id</th>
                                      <th>Vehicle Vendor Name</th>
                                      <th>Vehicle Number</th>
                                      <th>Action</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "vehiclesUnregisterData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Unregistered Vehicles</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "vehiclesUnregisterData.length>0">
		                           <tr ng-repeat = "post in vehiclesUnregisterData | filter:searchtextVehicle">
		                                 <td>{{post.vehicleId}}</td>
		                                 <td>{{post.name}}</td>
		                                 <td>{{post.vehicleNumber}}</td>
		                                 <td><input class = "addagainButton btn btn-info btn-xs buttonRadius0" 
                                                    type = "button" 
                                                    ng-click = "addagainVehicle(post, $index)" 
                                                    value = "Add Again"></td>                                    
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    <input type = 'text' class = "approvalSearchBox" ng-model = "searchtextVehicle">
                   </tabset>
                </div>
                <div class="clearfix"></div>
                <br>
             <!--   /*END OF WRAPER2 = VEHICLE*/ 
              /*START OF WRAPPER3 = VENDOER*/ -->
             <div class = "wrapper1" id = "vendorContent">
                <div class = "heading2 row">
                    <span class = "col-md-7 floatLeft" id = "vendor">Vendor</span>
                    <div class= "col-md-5 floatRight">
                    	<efmfm-button img-class = "efmfm_approvalButtons_collapse"
                                      src-url = "images/portlet-collapse-icon"
                                      selected-url = "images/portlet-expand-icon"
                                      hover-url = "images/portlet-collapse-icon"
                                      alt-text = "Collapse Window"
                                      main-div = "vendorContent"
                                      target-div = "vendorTab_Approval">
                        </efmfm-button> 
                        <efmfm-button img-class = "efmfm_approvalButtons_reload"
                                      src-url = "images/portlet-reload-icon"
                                      selected-url = "images/portlet-reload-icon"
                                      hover-url = "images/portlet-reload-icon"
                                      alt-text = "Reload Window"
                                      main-div = "vendorContent"
                                      target-div = "vendorTab_Approval"
                                      ng-click='refreshVendorApproval()'>
                        </efmfm-button>
                        <efmfm-button img-class = "efmfm_dashboarButtons_remove"
                                      src-url = "images/portlet-remove-icon"
                                      selected-url = "images/portlet-remove-icon"
                                      hover-url = "images/portlet-remove-icon"
                                      alt-text = "Remove Window"
                                      main-div = "vendorContent"
                                      target-div = "vendorTab_Approval">
                        </efmfm-button>
                    </div>
                </div>
                <tabset class="tabset vendorTab_Approval">
                <tab ng-click = "getPendingVendor()">
                    <tab-heading>Pending Vendor</tab-heading>
                        <div class = "pendingTabContent row">
                            <table class = "pendingTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Vendor Id</th>
                                      <th>Vendor Name</th>
                                      <th>Vendor Number</th>
                                      <th>Action</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "vendorPendingData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Vendors Pending</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "vendorPendingData.length>0">
		                           <tr ng-repeat-start = "post in vendorPendingData | filter:searchtextVendor"
                                       class = "rowSeen"                                       
                                       ng-class = "{vendorRowExpanded:post.isClicked}">
		                                 <td>{{post.vendorId}}</td>
		                                 <td>{{post.name}}</td>
		                                 <td>{{post.mobileNumber}}</td>
		                                 <td>
		                                 	<input ng-show = "!post.isClicked" 
                                                   class = "viewButton btn btn-primary btn-xs buttonRadius0" 
                                                   type = "button" 
                                                   ng-click = "viewVendor(post, $index)" 
                                                   value = "View Detail">
		                                 	<input ng-show = "post.isClicked" 
                                                   class = "approvalButton btn btn-success btn-xs buttonRadius0" 
                                                   type = "button" ng-class = "{{post.vendorId}}" 
                                                   ng-click = "approveVendor(post, $index)" 
                                                   value = "Approve"
                                                   ng-disabled = "supervisorRole">
			                                <input ng-show = "post.isClicked" 
                                                   class = "rejectButton btn btn-danger btn-xs buttonRadius0" 
                                                   type = "button" 
                                                   ng-click = "rejectVendor(post, $index)" 
                                                   value = "Reject"
                                                   ng-disabled = "supervisorRole">
		                                 </td>                                    
		                            </tr>   
		                            <tr ng-show = "post.isClicked" 
                                        ng-repeat-end=""  
                                        ng-class = "vendor{{post.vendorId}}" 
                                        class = "driverPendingDetail">
		                            	<td colspan="5">
		                            	<div class="row marginRow">
                                            <div class = "col-md-9 marginRow padding0">
                                                <div class = "row marginRow driverInfo_approval">
                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vendor Id</span><br><span>{{vendorDetail[$index].vendorId}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Vendor Name</span><br><span>{{vendorDetail[$index].name}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Contact Number</span><br><span>{{vendorDetail[$index].mobileNumber}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Address</span><br><span>{{vendorDetail[$index].address}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Email Address</span><br><span>{{vendorDetail[$index].emailId}}</span></div>

                                                    <div class = "col-md-3 Drivelabel">
                                                        <span>Tin Number</span><br><span>{{vendorDetail[$index].tinNum}}</span></div>                                                                                           </div>
                                            </div>
                                            <div class = "col-md-3">
                                                <img src="images/portlet-remove-icon.png" 
                                                     class = "floatRight pointer" 
                                                     ng-click = "closeDriver(post)">
                                                <div class = "col-md-12">
<!--
                                                    <div ng-repeat = "doc in vehicleDocuments" class = "floatLeft" id = "div{{$index}}">
                                                       <i class = "icon-remove-sign pointer" ng-click="deleteDocument($index)"></i>
                                                            <a href="{{doc.location}}" class = "noLinkLine">
                                                                <img class="img-responsive img-rounded fileImg" 
                                                                  alt="Responsive image" 
                                                                  ng-src = "{{doc.imgSrc}}">
                                                                <div class = "docName"><span>{{doc.name}}</span></div>
                                                            </a>
                                                    </div>
-->
		                            		    </div>
                                            </div>
		                            	</div>
		                            	</div>
		                            	 </td>                            	
		                            </tr>                                               
		                         </tbody>
                                
                            </table>
                        </div>
                    </tab>
                    <tab ng-click = "getRegisteredVendor()">
                    <tab-heading>Registered Vendor</tab-heading>
                        <div class = "registeredTabContent row">
                            <table class = "registeredTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Vendor Id</th>
                                      <th>Vendor Name</th>
                                      <th>Vendor Number</th>
                                      <th>Action</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "vendorRegisterData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Vendors for Registration</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "vendorRegisterData.length>0">
		                           <tr ng-repeat = "post in vendorRegisterData | filter:searchtextVendor">
		                                 <td>{{post.vendorId}}</td>
		                                 <td>{{post.name}}</td>
		                                 <td>{{post.mobileNumber}}</td>
		                                 <td><input class = "removeButton btn btn-warning btn-xs buttonRadius0" 
                                                    type = "button" ng-click = "removeVendor(post)" 
                                                    value = "Remove"></td>                                    
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    <tab ng-click = "getUnRegisteredVendor()">
                      <tab-heading>Unregistered Vendor</tab-heading>
                    	<div class = "unregisteredTabContent row">
                            <table class = "unregisteredTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Vendor Id</th>
                                      <th>Vendor Name</th>
                                      <th>Vendor Number</th>
                                      <th>Action</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "vendorUnregisterData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Unregistered Vendors</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "vendorUnregisterData.length>0">
		                           <tr ng-repeat = "post in vendorUnregisterData | filter:searchtextVendor">
		                                 <td>{{post.vendorId}}</td>
		                                 <td>{{post.name}}</td>
		                                 <td>{{post.mobileNumber}}</td>
		                                 <td><input class = "addagainButton btn btn-info btn-xs buttonRadius0" 
                                                    type = "button" ng-click = "addagainVendor(post)" 
                                                    value = "Add Again"></td>                                    
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                    <input type = 'text' class = "approvalSearchBox" ng-model = "searchtextVendor">
                   </tabset>
                </div>
            </div>
            
            
        </div>
        
    </div> 
   
    
</div>