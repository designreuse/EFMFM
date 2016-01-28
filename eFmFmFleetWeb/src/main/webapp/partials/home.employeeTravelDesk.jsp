
    
<div class = "empTravelDeskTemplate container-fluid">
	<div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-12 col-xs-12">Employee-Roster<span class = 'spinner_travelDesk'><img ng-src='images/spiffygif_24x24.gif' ng-show = 'isProcessing'></span></span>
            <div class = "col-md-12 col-xs-12 mainTabDiv_EmployeeTravelDesk">
            <div class = 'col-md-12 col-xs-12 sceduleCab_travelDesk'>
                <button type = 'button' 
                        class = 'btn btn-success floatRight buttonRadius0 '
                        ng-click = 'assignCab()'><span>Assign Cab</span></button>
            </div>
            <div class = "wrapper1 wrapper1_travelDesk1" id="employeeList">
                <div class = "heading2 heading2_travelDesk row">
                    <span class = "col-md-7 floatLeft">Employee Details</span>
                    <div class= "col-md-5 floatRight travelDeskHeadingButtons">
                    	<efmfm-button img-class = "efmfm_approvalButtons_collapse"
                                      src-url = "images/portlet-collapse-icon"
                                      selected-url = "images/portlet-expand-icon"
                                      hover-url = "images/portlet-collapse-icon"
                                      alt-text = "Collapse Window"
                                      main-div = "employeeList"
                                      target-div = "employeeListContent">
                        </efmfm-button> 
                        <efmfm-button img-class = "efmfm_approvalButtons_reload"
                                      src-url = "images/portlet-reload-icon"
                                      selected-url = "images/portlet-reload-icon"
                                      hover-url = "images/portlet-reload-icon"
                                      alt-text = "Reload Window"
                                      main-div = "employeeList"
                                      target-div = "employeeListContent"
                                      ng-click = 'refreshEmployeeTravelDesk()'>
                        </efmfm-button>
                        <efmfm-button img-class = "efmfm_dashboarButtons_remove"
                                      src-url = "images/portlet-remove-icon"
                                      selected-url = "images/portlet-remove-icon"
                                      hover-url = "images/portlet-remove-icon"
                                      alt-text = "Remove Window"
                                      main-div = "employeeList"
                                      target-div = "employeeListContent">
                        </efmfm-button>
                    </div>
                </div>
                <div class = "employeeListContent">
                <!-- /*FIRST ROW*/-->
                <div class = "row">
                	<div class = "col-md-3 showRecordsSelect">
<!--                		<span>Show</span>-->
                		<select ng-model="showRecords" 
                                class ="form-control"
                				ng-options="pagination.text for pagination in paginations track by pagination.value"
                				ng-change = "setLimit(showRecords)">
      						<option value="">Show ALL Records</option>
    					</select>
                	</div>
                	<div class = "col-md-3 shiftTimeSelect">
<!--                		<span>Shift Time</span>-->
                		<select ng-model="selectShifts" 
                                class ="form-control"
                                ng-options="shiftTime for shiftTime in shiftsTime"
                                ng-change = "setShifts(selectShifts)">
      						<option value="">Show ALL Shift Time</option>
    					</select>
                	</div>
                	<div class = "col-md-3 searchEmployeeTravelDesk">
<!--                		<span>Search</span>-->
                		<input type = "text" 
                                class ="form-control"
                               ng-model = "searchEmployeeReported"
                               placeholder = "Filter by Name, Id, Shift....">
                	</div>
                	<div class = "col-md-3 searchEmployeeTravelDesk">
                        <div class = "input-group floatLeft calendarInput"> 
                          <input ng-model="search.text"
                                 type = "text" 
                                 class="form-control" 
                                 placeholder = "Search Employee"
                                 maxlength =  '10'
                                 ng-focus = "searchIsEmpty = false"
                                 ng-class = "{error: searchIsEmpty}">
                           <span class="input-group-btn">
                               <button class="btn btn-success" 
                                       ng-click="searchEmployee(search.text)">
                               <i class = "icon-search searchServiceMappingIcon"></i></button></span> 
                        </div>
                    </div>
                </div>
                <!--   /*SECOND ROW*/-->
                <div class = "row">
                	<!-- <div class = "wrapper1_EmpTravelDesk">
                		<div ng-click = "selectAll()" class = "selectAllCheckBox" ng-class = {disable:posts.length==0}>
                		<i ng-hide = "selectAllClicked" class = "icon-check-empty"></i>
		                <i ng-show = "selectAllClicked" class = "icon-check icon-check-custom"></i>
                		Select All</div>

                		<div ng-click = "deleteAll()" class = "deleteCheckBox" ng-class = {disable:selectAllClicked}>
						<i ng-hide = "deleteAllClicked" class = "icon-check-empty"></i>
		                <i ng-show = "deleteAllClicked" class = "icon-check"></i>
		                Delete All</div>

                	</div> -->
                	<div class = "wrapper2_EmpTravelDesk">
<!--
                        <div class = "row alert_TravelDesk">
                            <div class="col-md-12 alert alert-warning" role="alert">
                                  <span class = "floatLeft"><strong>Message</strong> : {{alertMessage}}</span>
                                       <span class = "hint floatLeft">{{hint}}</span>
                                       <span class = "floatRight pointer" ng-click = "closeAlert()">X</span>
                                  
                            </div>
                        </div>
-->
                        
                		<table class = "employeeListTable_TravelDesk table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                    <!--  <th ng-click = "selectAll()">
                                      <i ng-hide = "selectAllClicked" class = "icon-check-empty"></i>
		                           	  <i ng-show = "selectAllClicked" class = "icon-check-custom"></i>
									 </th> -->
                                      <th>Employee Id</th>
                                      <th>Employee Name</th>
                                      <th>Request Date</th>
                                      <th>Request Type</th>
                                      <th>Shift Time</th>
                                      <th>Route Name</th>                                                                  
                                      <th>Pick/Drop Time</th>
                                      <th>Pickup/Drop Location</th>
                                      <th>Area Name</th>
                                      <th>Trip Type</th>
                                      <th>Cab Status</th>
                                      <th></th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "posts.length==0">
                                    <tr>
                                        <td colspan = '13'>
                                            <div class = "noData">No Employee has requested for Pick-up or Drop Services</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "posts.length>0">
                                    <tr ng-repeat = "post in posts |limitTo: numberofRecords | filter : searchEmployeeReported" 
		                           	   class = "employee{{post.employeeId}}{{post.requestId}}">
<!--
		                           <tr ng-if = 'isLoaded' 
                                       dir-paginate = "post in posts |itemsPerPage: numberofRecords | filter : searchEmployeeReported| filter : selecdShift" 
                                       current-page="currentPage"
                                       total-items = "totalRecords"
		                           	   class = "employee{{post.employeeId}}">
-->
		                           		<!--  <td class = "col-md-1" ng-click = "select_thisEmployee(post)">
		                           		 <i ng-hide = "post.checkBoxFlag" class = "icon-check-empty"></i>
		                           		 <i ng-show = "post.checkBoxFlag" class = "icon-check icon-check-custom"></i>
		                           		 </td> -->
		                                 <td class = "col-md-1">{{post.employeeId}}</td>
		                                 <td class = "col-md-1">{{post.employeeName}}</td>		                                 
		                                 <td class = "col-md-1">{{post.tripDate}}</td>
                                         <td class = "col-md-1">{{post.requestType}}</td>
		                                 <td class = "col-md-1">{{post.tripTime}}</td>
                                         <td class = "col-md-1">{{post.employeeRouteName}}</td>                            
                                        <td class = "col-md-1">{{post.pickUpTime}}</td>   
		                                 <td class = "col-md-1">{{post.employeeAddress}}</td>	
                                         <td class = "col-md-1">{{post.employeeAreaName}}</td>		                                 
		                                 <td class = "col-md-1">{{post.tripType}}</td>
		                                  <td class = "col-md-1">{{post.cabAvailable}}</td>
                                       <td>
                                           <input type = 'button' 
                                                  class = 'btn btn-warning btn-sm form-control'
                                                  value = 'Edit'
                                                  ng-click = 'editTravelDesk(post, $index, "lg")'>
                                       </td>
                                        
<!--
		                                 <td class = "col-md-2">                                               
                                             <button class = "btn btn-info btn-sm viewMapButton_travelDesk" 
                                                     ng-click = "openSingleModal(post, 'lg')">
                                                 <span>View on Map</span>
                                                 <i class = "icon-map-marker mapMarkerIcon pointer"></i>
                                             </button>
                                             </td>	
-->
		                                 <td class = "col-md-1">
		                                 <div class = "deletebutton_empTravelDesk pointer" ng-click="deleteEmployee(post)">
		                                 <i class = "delete-employee_TravelDesk icon-remove-sign"></i></div>                                 
		                                 </td>                                         
		                            </tr>                                  
		                         </tbody>
                                
                            </table>
                         </div>
                         
                         <div class = "wrapper3_EmpTravelDesk">
	                         <div class = "row">
	                         	<div class = "col-md-6 numberofRows" ng-show = (showSearchResultCount)>
	                         		Searched {{countofFilteredRecords.length}}/{{posts.length}} records
	                         	</div>
	                         	<div class = "col-md-6 numberofRows">
<!--
                                    <dir-pagination-controls boundary-links="true" 
                                                             on-page-change="pageChangeHandler(newPageNumber)" 
                                                             template-url="bower_components/pagination/dirPagination.tpl.html">
                                    </dir-pagination-controls>
-->
	                         	</div>
	                         </div>
	                         <div class = "row">
	                         	<div class = "col-md-4 col-xs-12 numberofRows">	                         		
	                         	</div>
                                 <!--IMPORTANT *** FIRST VIEW FOR FULL SIZE SCREEN -->
	                         	<!-- <div class = "col-md-4 col-xs-12 hidden-xs numberofRows">	
	                         	<input type = "button" 
                                       class = "btn btn-primary employeeRequestButton_travelDesk pointer" 
                                       value = "Employee Reported at Service Desk" 
                                       ng-click = "employeeReported()">   
	                         	<input type = "button" class = "btn btn-default cancelButton_employeeDesk" value = "Cancel" ng-click = "cancel()">                   		
	                         	</div> -->
                                 <!--IMPORTANT *** SECOND VIEW FOR FULL SIZE SCREEN -->
	                         	<div class = "col-md-4 col-xs-12 visible-xs-* hidden-lg hidden-md hidden-sm numberofRows">	
	                         	<input type = "button" 
                                       class = "btn btn-primary employeeRequestButton_travelDesk pointer" 
                                       value = "Reported at Serv. Desk" 
                                       ng-click = "employeeReported()">   
	                         	<input type = "button" class = "btn btn-default cancelButton_employeeDesk" value = "Cancel" ng-click = "cancel()">                   		
	                         	</div>
	                         	<div class = "col-md-4 col-xs-12 numberofRows">	                         		
	                         	</div>
	                         </div>                         	
                         </div>                        
                </div>
                </div>
                </div>        
           </div>
        </div>
        
    </div>
</div>