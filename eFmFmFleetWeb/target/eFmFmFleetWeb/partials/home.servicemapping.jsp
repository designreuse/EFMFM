<div class = "serviceMappingTemplate container-fluid">
 <div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-6 col-xs-12">Routing And Cab Allocation</span> 
                    <div class = "col-md-6 col-xs-12 divSpanHeading_serviceMapping"><span class = "floatRight">Total Routes:<mark class = 'mark_headingServiceMapping'><strong>{{zoneData.length}}</strong></mark></span>
            <span class = "floatRight">Total Zones:
                        <mark class = 'mark_headingServiceMapping'><strong>{{zoneData.length}}</strong></mark> | </span></div>
            <div class = "col-md-12 col-xs-12 mainTabDiv_serviceMapping">
                <div class = 'row searchArea'>
                    <div class = 'col-md-3'>  
                        <input type = 'button' 
                               class = 'btn btn-success creatNewRoute_serviceMapping' 
                               value = 'Create New Route'
                               ng-click = 'createNewRoute()'> 
                    </div>   
<!--
                    <div class = 'col-md-3'> 
                        <input type = 'button' class = 'btn btn-info' value = 'Advance Search'>
                    </div>  
-->
                    <div class = 'col-md-7 searchFirstOption'>  
                        <select ng-model="search.searchBy" 
                               class="form-control select_serviceMap floatLeft" 
                               ng-options="searchType.value for searchType in searchTypes track by searchType.value"
                                ng-show = '!advanceIsClicked'
                               >
                         <option value="">-- Search By --</option>
                         </select>
                         <div class = "input-group searchBox_serviceMap floatLeft calendarInput" ng-show = '!advanceIsClicked'> 
                              <input ng-model="search.text"
                                     type = "text" 
                                     class="form-control" 
                                     placeholder = "Search by Vehicle or Employee Id"
                                     maxLength = '10'
                                     ng-focus = "searchIsEmpty = false"
                                     ng-class = "{error: searchIsEmpty}">
                               <span class="input-group-btn">
                                   <button class="btn btn-success" 
                                           ng-click="searchBy(search.text, search.searchBy)" >
                                   <i class = "icon-search searchServiceMappingIcon"></i></button></span> 
                         </div>
                        <div class = 'row' ng-show = 'advanceIsClicked'>
                            <div class = "col-md-3 col-xs-12"> 
                               <div>
<!--                               <label>Trip Type</label>-->
                                   <select ng-model="search.tripType"
                                           class="form-control" 
                                           ng-options="tripType.text for tripType in tripTypes track by tripType.value"
                                           ng-change = "setSearchTripType(search.tripType)"
                                           >
                                     <option value="">-- Select Trip Type --</option>
                                  </select>
                               </div>
                           </div>  
                           <div class = "col-md-3 col-xs-12"> 
                               <div>
<!--                               <label>Shift Time</label>-->
                                   <select ng-model="search.shiftTime" 
                                           class="form-control" 
                                           ng-options="shiftTime.shiftTime for shiftTime in shiftsTime track by shiftTime.shiftTime"
                                           >
                                     <option value="">-- Select Shift Time --</option>
                                  </select>
                               </div>
                            </div> 
                            <div class = "col-md-3 col-xs-12 marginBottom10"> 
                            <div>
                               <select ng-model="search.routeStatus"
                                       class="form-control" 
                                       ng-options="routeStatus.text for routeStatus in routesStatus track by routeStatus.value"
                                       ng-change = "setSearchRouteStatus(search.routeStatus)">
                              </select>
                           </div>
                           </div>  
                            <div class = "col-md-3 col-xs-12">
                                <input type = 'button' 
                                       class = 'form-control btn btn-success' 
                                       value = 'Search'
                                       ng-click = 'searchByTripTypeShift(search)'>
                            </div> 
                            
                        </div>
                       </div> 
                    <div class = 'col-md-2'>  
                        <span class = 'floatLeft orSpanServiceMapping'><mark>OR</mark></span>
                             <input type = 'button' 
                                    class = 'btn btn-warning form-control floatLeft advanceSearch_serviceMapping' 
                                    value = '{{advanceLabel}}'
                                    ng-click = 'advanceSearch()'>
                    </div> 
                    <div class = 'col-md-12' ng-show = 'serachResultFound'>
                        <span class = 'floatLeft spanSearchLabel'>Search Results</span>
                        <input type = 'button' class = 'floatRight btn btn-success btn-xs buttonRadius0 backServiceMapping' 
                               value = 'Back To Main List'
                               ng-click = 'backtoMainList()'></div>
                </div>
          <!--   /*START OF WRAPPER1 = DRIVER*/ -->       
               
 <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default accordianPanel_serviceMapping" ng-repeat = "zone in zoneData">
      <a data-toggle="collapse" 
         data-parent="#accordion" 
         href="#collapse{{zone.routeId}}" 
         aria-expanded="false" 
         aria-controls="collapse{{zone.routeId}}"
         ng-click = "getRoutes(zone)"
         class = "noTextDecoration collapse{{zone.routeId}}">
    <div class="panel-heading panelHeading_serviceMapping collapseZoneDiv{{zone.routeId}}" 
         role="tab" 
         id="headingOne">
      <h4 class="panel-title heading_serviceMapping">        
          <span>{{zone.routeName}} </span>
          <span class="floatRight badge customBadge badge_serviceZone">{{zone.NumberOfRoutes}}</span>     
      </h4>
    </a>
    </div>
      
    <div id="collapse{{zone.routeId}}" 
         class="panel-collapse collapse accordionContent_serviceMapping collapse{{zone.routeId}}" 
         role="tabpanel" 
         aria-labelledby="headingOne">
        
        <div class = "col-md-3 col-md-offset-9 col-xs-6 searchDiv">
                  <input type = 'text' 
                         class = "search_serviceMapping form-control floatRight" 
                         ng-model = 'searchEmployee' 
                         placeholder = "Search Employee"> 
              </div> <br>
      <div class="panel-body accorMainContent"> 
        <div class = "routesBucket route{{zone.routeId}}{{route.routeId}}" 
             ng-repeat = "route in routesData | filter: {routeName : zone.routeName} | filter : searchEmployee"
             ng-hide = 'route.isVehicleEmpty'>
          <div class = "row overflowAuto">
              
            <div class = "col-md-3 col-xs-6 customTabs">
                <span class = "floatLeft map_serviceMappingIcon pointer" 
                      ng-click = "openMap(route, zone)">
                    <i class ="icon-map-marker mapMarkerIcon" 
                       tooltip="View on Map"
                       tooltip-placement="right"
                       tooltip-trigger="mouseenter"></i></span>
                <span><strong>Route {{route.routeId}}</strong></span><span> | {{route.tripType}}</span><span> | {{route.shiftTime}}</span>
                <span class = "floatRight pointer" 
                      ng-click = 'deleteEmptyRoute(route, zone)'
                      ng-show = 'route.empDetails.length == 0'>
                    <i class = "delete_route icon-remove-sign"></i>
<!--                    <img src = "images/portlet-remove-icon-white.png">-->
                </span>
              </div>
<!--
              <div class = "col-md-3 col-md-offset-6 col-xs-6">
                  <input type = 'text' 
                         class = "search_serviceMapping form-control floatRight" 
                         ng-model = 'searchEmployee' 
                         placeholder = "Search Employee"> 
              </div>
-->
                       <div class = "col-md-12 col-xs-12 eachRouteInfo">
                          <div class = "row margin0">
                             <div class = "col-md-12 col-xs-12 routesButtons">
                                 
                             <input type = "button" 
                                    id = "started{{zone.routeId}}{{route.routeId}}"
                                    class = "floatRight btn btn-danger1 btn-xs manuallyStartTripButton_serviceMapping" 
                                    value= "Manually Trip Started"  
                                    ng-show = "route.tripStatus !='Started'"
                                    ng-click = "manualTripStart(route, zone)">
                                 
                             <input type = "button" 
                                    id = "ended{{zone.routeId}}{{route.routeId}}"
                                    class = "floatRight btn btn-danger1 btn-xs manuallyStartTripButton_serviceMapping" 
                                    value= "Manually Trip Ended"  
                                    ng-show = "route.tripStatus =='Started'"                                
                                    ng-click = "manualTripEnd(route, zone)">
                                 
                               <input type = "button" 
                                      class = "floatRight btn btn-danger1 btn-xs closeBucketButton_serviceMapping" 
                                      value = "Close Bucket"
                                      ng-click = "closeBucket(route, zone)"
                                      ng-class = "{disabled: route.bucketStatus != 'N'}">
                               <input type = "button" 
                                       class = "floatRight btn btn-warning2 btn-xs"
                                       value = "Edit Bucket"
                                       ng-class = "{disabled: route.tripStatus == 'F'}"
                                       ng-click = "editBucket(route, zone, 'lg')" >
                                       
                                       
                                        
                                 
                               </div> 
                               <div class = "col-md-2 mainFirstRowRoute"><span>Device Number</span><br><span>{{route.deviceNumber}}</span></div>                              
                               <div class = "col-md-2 mainFirstRowRoute"><span>Driver Name</span><br><span>{{route.driverName}}</span></div>
                               <div class = "col-md-2 mainFirstRowRoute"><span>Driver Number</span><br><span>{{route.driverNumber}}</span></div>
                               <div class = "col-md-2 mainFirstRowRoute"><span>Vehicle Number</span><br><span>{{route.vehicleNumber}}</span></div>                            
                               <div class = "col-md-2 mainFirstRowRoute"><span>Escort Name</span><br><span>{{route.escortName}}</span></div>
                               
<!--
                               <div class = "col-md-2 mainFirstRowRoute">
                                 <div class = "refreshDiv_serviceMapping pointer"
                                      tooltip="Swap Cab"
                                      tooltip-placement="top"
                                      tooltip-trigger="mouseenter">
                                     <i class = "icon-refresh refreshIcon_seviceMapping"></i>
                                 </div>
                               </div>
-->
                               <div class = "col-md-2 mainFirstRowRoute">
                                   <div class = "seatsAvailable_service"><span>Available Seats</span>
                                       <span class = 'vehicleAvailableRound'><mark>{{route.vehicleAvailableCapacity}}</mark></span>
<!--                                       <span>/{{route.capacity}}</span>-->
                                    </div></div>
                              </div>
                              <table class = "serviceMappingTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Employee Id</th>
                                      <th>Employee Name</th>
                                      <th>Number</th>
                                      <th>Gender</th>
                                      <th>Address</th>
                                      <th>Area</th>
<!--                                      <th>Pick/Drop</th>-->
                                      <th ng-show = "route.tripType == 'PICKUP'">Pick-up Time</th>
<!--                                      <th>Shift Time</th>-->
                                      <th>Move To</th>
                                      <th>Move To</th>
                                      <th></th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "route.empDetails.length==0">
                                    <tr><td colspan = "11"><div class = "noData_serviceMapping">There is No Employee in this Route</div></td></tr>
                                  </tbody>
                                <tbody>                                   
		                           <tr ng-repeat = "employee in route.empDetails  | filter : searchEmployee" 
                                       ng-show = "route.empDetails.length>0"
                                       id = "row{{zone.routeId}}{{route.routeId}}{{employee.employeeId}}{{employee.requestId}}">                                       
		                             <td class = "col-md-1"><span>{{employee.employeeId}}</span>                                                          
                                     </td>
                                     <td class = "col-md-2"><span>{{employee.name}}</span>                                                            
                                     </td>
                                     <td class = "col-md-1"><span>{{employee.employeeNumber}}</span>
                                     </td>
                                     <td class = "col-md-1"><span>{{employee.gender}}</span>
                                     </td>	
                                     <td class = "col-md-2"><span>{{employee.address}}</span>
                                     </td>	
                                     <td class = "col-md-1"><span>{{employee.empArea}}</span>
                                     </td>	
                                     	                                        
<!--
		                             <td class = "col-md-1"><span>{{employee.tripType}}</span>
                                     </td>
-->
                                     <td  class = 'col-md-1' ng-show = "route.tripType == 'PICKUP'">
                                         {{employee.pickUpTime}}
                                     </td>
<!--                                     <td>{{route.shiftTime}}</td>-->
                                     <td class = "col-md-1">                                          
                                        <select ng-model="moveToZone" 
                                                class = "zoneDropDown_serviceMapping floatLeft"
                                                ng-options="zone.routeName for zone in zoneData track by zone.routeName"
                                                ng-change = "changeRoutesDropDown(employee, moveToZone)">
                                            <option value="">*Zone*</option>
                                        </select>                                                  
<!--
                                        <select ng-model="moveToZone" 
                                                class = "zoneDropDown_serviceMapping floatLeft"
                                                ng-options="zone.routeName for zone in zoneData track by zone.routeName"
                                                ng-change = "changeRoutesDropDown(employee, moveToZone)"
                                                ng-disabled = "route.tripStatus =='Started'">
                                            <option value="">*Zone*</option>
                                        </select>                                       
-->
                                      </td>
                                       
                                     <td class = "col-md-1">      
                                        <select ng-model="moveTo" 
                                                class = "routeDropDown_serviceMapping floatLeft"
                                                ng-options="routeDropDown.routeId for routeDropDown in routesDropDown track by routeDropDown.routeId"
                                                ng-change = "moveToRoute(moveTo, moveToZone, route, zone, employee, $index)"
                                                ng-disabled = "!employee.isZoneclicked">
                                            <option value="">*Routes*</option>
                                        </select>
<!--
                                        <select ng-model="moveTo" 
                                                class = "routeDropDown_serviceMapping floatLeft"
                                                ng-options="routeDropDown.routeId for routeDropDown in routesDropDown track by routeDropDown.routeId"
                                                ng-change = "moveToRoute(moveTo, moveToZone, route, zone, employee, $index)"
                                                ng-disabled = "route.tripStatus =='Started' || !employee.isZoneclicked">
                                            <option value="">*Routes*</option>
                                        </select>
-->
                                     </td>

                                     <td class = "col-md-1">
                                         <button class = "pointer btn btn-danger delete_serviceMapping" 
                                              tooltip="Delete Employee"
                                              tooltip-placement="top"
                                              tooltip-trigger="mouseenter"
                                              ng-click = "deleteEmployee(route, zone, employee, $index)">
                                             <i class = "icon-remove-sign"></i>
                                         </button>
<!--
                                         <button class = "pointer btn btn-danger delete_serviceMapping" 
                                              tooltip="Delete Employee"
                                              tooltip-placement="top"
                                              tooltip-trigger="mouseenter"
                                              ng-click = "deleteEmployee(route, zone, employee)"
                                              ng-class = "{disabled: route.tripStatus == 'Started'}">
                                             <i class = "icon-remove-sign"
                                                ng-class = "{disabled: route.tripStatus == 'Started'}"></i>
                                         </button> 
-->
                                     </td>


                                     <td class = "col-md-1">
<!--
                                         <button class = "btn upDownDiv_serviceMapping pointer floatLeft"
                                              id = "down{{zone.routeId}}{{route.routeId}}{{employee.employeeId}}"
                                              tooltip="Move Down"
                                              tooltip-placement="top"
                                              tooltip-trigger="mouseenter"
                                              ng-disabled = "route.tripStatus =='Started'"
                                              ng-click = "moveDown(route, zone, employee, $index)"><i class = "icon-arrow-down"></i></button>
                                         <button class = "btn upDownDiv_serviceMapping pointer floatLeft"
                                              id = "up{{zone.routeId}}{{route.routeId}}{{employee.employeeId}}"
                                              tooltip="Move Up"
                                              tooltip-placement="top"
                                              tooltip-trigger="mouseenter"
                                              ng-disabled = "route.tripStatus =='Started'"
                                              ng-click = "moveUp(route, zone, employee, $index)"><i class = "icon-arrow-up"></i></button>
-->
                                     </td>

		                           </tr>                                       
		                         </tbody>
                            </table>
                           
<!--
                           <div class = "col-md-12 col-xs-12 mainTabDiv_serviceMapping">
                <efmfm-google-map-single map-data = "getSingleServiceRoute()"></efmfm-google-map-single>
            </div>
-->
                          </div>                            
                        </div>
                    </div>  
          
          
          
          
      </div>
    </div>
  </div>
</div>
              <!--    /*END OF WRAPPER1 = DRIVER*/
                 /*START OF WRAPPER2 = VEHICLE*/ -->
          <!--      <div class = "wrapper1" id = "alertContent">
                <div class = "heading2 row">
                    <span class = "col-md-7 floatLeft" id = "map_viewMap">Posted Alerts List</span>
                    <div class= "col-md-5 floatRight">
                    	<efmfm-button img-class = "efmfm_approvalButtons_collapse"
                                      src-url = "images/portlet-collapse-icon"
                                      selected-url = "images/portlet-expand-icon"
                                      hover-url = "images/portlet-collapse-icon"
                                      alt-text = "Collapse Window"
                                      main-div = "alertContent"
                                      target-div = "alertTable">
                        </efmfm-button> 
                        <efmfm-button img-class = "efmfm_approvalButtons_reload"
                                      src-url = "images/portlet-reload-icon"
                                      selected-url = "images/portlet-reload-icon"
                                      hover-url = "images/portlet-reload-icon"
                                      alt-text = "Reload Window"
                                      main-div = "alertContent"
                                      target-div = "alertTable">
                        </efmfm-button>
                        <efmfm-button img-class = "efmfm_dashboarButtons_remove"
                                      src-url = "images/portlet-remove-icon"
                                      selected-url = "images/portlet-remove-icon"
                                      hover-url = "images/portlet-remove-icon"
                                      alt-text = "Remove Window"
                                      main-div = "alertContent"
                                      target-div = "alertTable">
                        </efmfm-button>
                    </div>
                </div>
                <div class = "alertTable">
                    <div class = "row">
                        <div class = "col-md-1 col-xs-12"><span>Search:</span></div>
                        <div class = "col-md-3 col-xs-12">
                          <input onfocus="(this.type='date')" 
                                 class="form-control floatLeft" 
                                 placeholder = "From Date" 
                                 ng-model = "searchFromDate"
                                 tooltip="From Date"
                                 tooltip-placement="top"
                                 tooltip-trigger="mouseenter"
                                 tooltip-enable="!fromDate"> 
                            
                        </div>

                        <div class = "col-md-3 col-xs-12">
                          <input onfocus="(this.type='date')" 
                                 class="form-control floatLeft" 
                                 placeholder = "To Date" 
                                 ng-model = "searchtoDate"
                                 tooltip="To Date"
                                 tooltip-placement="top"
                                 tooltip-trigger="mouseenter"
                                 tooltip-enable="!toDate">
                            
                        </div>
                    </div>
                        <table class = "postTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>From Date</th>
                                      <th>To Date</th>
                                      <th>Title</th>
                                      <th>Description</th>
                                      <th>Zone</th>
                                    </tr> 
                                </thead>
                                <tbody>
		                           <tr>
		                              <td>test</td>
		                              <td>test</td>
		                              <td>test</td>
		                              <td>test</td>		                                        
		                              <td>test</td>                                    
		                            </tr>                                    
		                         </tbody>
                            </table>
                    </div>
               
                </div>      -->       
            </div>
            
            
        </div>
        
    </div> 
</div>