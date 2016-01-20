<div class = "empDetailsTemplate container-fluid">
	<div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-12 col-xs-12">Employee Details</span>            
            <div class = "col-md-12 col-xs-12 mainTabDiv_EmployeeDetails">
            <div class = "wrapper1" id="employeeList">
                <div class = "heading2 row">
                    <span class = "col-md-7 floatLeft">All Registered Employees</span>
                    <div class= "col-md-5 floatRight">
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
                                      ng-click = 'refreshEmpDetail()'>
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
                		<select class = 'form-control'
                                ng-model="showRecords" 
                				ng-options="pagination.text for pagination in paginations track by pagination.value"
                				ng-change = "setLimit(showRecords)">
      						  <option value="">-- All Records --</option>
    					</select>
                	</div>
<!--                	<div class = "col-md-4 shiftTimeSelect">-->
                		<!-- <span>Shift Time</span>
                		<select ng-model="selectShifts" ng-options="shiftTime for shiftTime in shiftsTime">
      						<option value="">-- Select Shift Time --</option>
    					</select>-->
<!--                	</div> -->
                    <div class = 'col-md-4 searchEmployeeTravelDesk'>
<!--                                    <div class = "col-md-3 searchEmployeeTravelDesk">-->
                            <div class = "input-group floatLeft calendarInput"> 
                              <input ng-model="search.text"
                                     type = "text" 
                                     class="form-control" 
                                     placeholder = "Search Employees by Id"
                                     maxlength =  '10'
                                     ng-focus = "searchIsEmpty = false"
                                     ng-class = "{error: searchIsEmpty}">
                               <span class="input-group-btn">
                                   <button class="btn btn-success" 
                                           ng-click="searchEmployees(search.text)">
                                   <i class = "icon-search searchServiceMappingIcon"></i></button></span> 
                            </div>
<!--                                    </div>                                    -->
                    </div>
                	<div class = "col-md-5 searchEmployeeDetail">
<!--                		<span>Search</span>-->
                		<input type = "text" 
                               ng-model = "searchEmployeeReported"
                               class = 'form-control floatRight'
                               placeholder = 'Filter Empployees'>
                	</div>
                </div>
                <!--   /*SECOND ROW*/-->
                <div class = "row">
                	<!-- <div class = "wrapper1_EmpDetail">
                		<div ng-click = "selectAll()" class = "selectAllCheckBox" ng-class = {disable:deleteAllClicked}>
                		<i ng-hide = "selectAllClicked" class = "icon-check-empty"></i>
		                <i ng-show = "selectAllClicked" class = "icon-check"></i>
                		Select All</div>
                		<div ng-click = "deleteAll()" class = "deleteCheckBox" ng-class = {disable:selectAllClicked}>
						<i ng-hide = "deleteAllClicked" class = "icon-check-empty"></i>
		                <i ng-show = "deleteAllClicked" class = "icon-check"></i>
		                Delete All</div>
                	</div>--> 
                	<div class = "wrapper2_EmpDetail">
<!--                		<table class = "employeeListTable_empDetail table table-hover table-responsive container-fluid" infinite-scroll='getEmployeesDetail()' infinite-scroll-distance='2'>-->
                        <table class = "employeeListTable_empDetail table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                     <!--  <th ng-click = "selectAll()">
                                      <i ng-hide = "selectAllClicked" class = "headerIcon icon-check-empty"></i>
		                           	  <i ng-show = "selectAllClicked" class = "headerIcon icon-check"></i>
									 </th>--> 
                                      <th>Employee Id</th>
                                      <th>Employee Name</th>
                                      <th>Employee Address</th>
                                      <th>Employee Gender</th>
                                      <th>Employee Number</th>
                                      <th>Employee EmailId</th>
                                      <th></th>
                                      <th></th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody>
		                           <tr ng-repeat = "post in posts |limitTo: numberofRecords | filter : searchEmployeeReported" 
		                           	   class = "employee{{post.employeeId}}">
		                                 <td class = "col-md-1">{{post.employeeId}}</td>
		                                 <td class = "col-md-2">{{post.employeeName}}</td>
		                                 <td class = "col-md-2">{{post.employeeAddress}}</td>
		                                 <td class = "col-md-1">{{post.employeeGender}}</td>	
		                                 <td class = "col-md-1">{{post.mobileNumber}}</td>
		                                 <td class = "col-md-2">{{post.emailId}}</td>
		                                 <td class = "col-md-1">
<!--
                                             <button class = "btn btn-success btn-sm viewMapButton_travelDesk" 
                                                     ng-click = "openMap(post, 'lg')"
                                                     ng-disabled = "post.employeeLatiLongi != null">
-->
                                             <button class = "btn btn-success btn-sm viewMapButton_travelDesk" 
                                                     ng-click = "openMap(post, 'lg')">
                                                 <span>View on Map</span>
                                                 <i class = "icon-map-marker mapMarkerIcon pointer"></i>
                                             </button>
                                         </td>
                                         <td class = "col-md-1">
                                             <input type = "button"
                                                    class = "btn btn-sm btn-warning editButton_travelDesk"
                                                    value = "Edit"
                                                    ng-click = "editEmployee(post)">
                                         </td>
		                                 <td class = "col-md-1">
                                             <div class = "deletebutton_empTravelDesk pointer" ng-click="deleteEmployee(post)">
                                             <i class = "delete-employee_TravelDesk icon-remove-sign"></i></div>                                               
                                         </td>
		                            </tr>                                  
		                         </tbody>
                                
                            </table>
                         </div>
                         
                         <div class = "wrapper3_EmpDetail">
	                         <div class = "row">
	                         	<div class = "col-md-6 numberofRows" ng-show = (showSearchResultCount)>
	                         		Total {{posts.length}} records
	                         	</div>
	                         	<div class = "col-md-6 numberofRows">
                                   
	                         <!--	Total {{result.length}}/{{posts.length}} records  -->
	                         		<!-- Total {{getNumOfRecordsOnPage()}}/{{posts.length}} records -->
	                         	</div>
	                         </div>
	                      <!--  <div class = "row">
	                         	<div class = "col-md-4 col-xs-12 numberofRows">	                         		
	                         	</div>
	                         	<div class = "col-md-4 col-xs-12 hidden-xs numberofRows">	
	                         	<input type = "button" class = "btn btn-primary" value = "Employee Reported at Service Desk">   
	                         	<input type = "button" class = "btn btn-default" value = "Cancel">                   		
	                         	</div>
	                         	<div class = "col-md-4 col-xs-12 visible-xs-* hidden-lg hidden-md hidden-sm numberofRows">	
	                         	<input type = "button" class = "btn btn-primary" value = "Reported at Serv. Desk">   
	                         	<input type = "button" class = "btn btn-default" value = "Cancel">                   		
	                         	</div>
	                         	<div class = "col-md-4 col-xs-12 numberofRows">	                         		
	                         	</div>
	                         </div>      -->                   	
                         </div>                        
                </div>
                </div>
                </div>        
           </div>
        </div>
        
    </div>
</div>