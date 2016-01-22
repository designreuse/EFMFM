<div class = "viewMapTemplate container-fluid">
    <div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <div class = "col-md-6 col-xs-6 filterDiv_viewMap">Live Tracking</div> 
                     
            <div class = "col-md-6 col-xs-6 filterDiv_viewMap"><input type = 'text'
                                                                      ng-model = 'searchViewMap'
                                                                      class = 'buttonRadius0 floatRight form-control'
                                                                      placeholder = 'Filter by shift time and triptype'></div>
                      
            <div class = "col-md-12 col-xs-12 mainTabDiv_viewMap">
          <!--   /*START OF WRAPPER1 = DRIVER*/ -->
               <div class = "wrapper1" id="activeEscortContent">
                <div class = "heading2 row">
                    <span class = "col-md-7 floatLeft" id = "driver">Routes</span>
                    <div class= "col-md-5 floatRight">
                    	<efmfm-button img-class = "efmfm_approvalButtons_collapse"
                                      src-url = "images/portlet-collapse-icon"
                                      selected-url = "images/portlet-expand-icon"
                                      hover-url = "images/portlet-collapse-icon"
                                      alt-text = "Collapse Window"
                                      main-div = "activeEscortContent"
                                      target-div = "activeEscortTable">
                        </efmfm-button> 
                        <efmfm-button img-class = "efmfm_approvalButtons_reload"
                                      src-url = "images/portlet-reload-icon"
                                      selected-url = "images/portlet-reload-icon"
                                      hover-url = "images/portlet-reload-icon"
                                      alt-text = "Reload Window"
                                      main-div = "activeEscortContent"
                                      target-div = "activeEscortTable"
                                      ng-click = 'refreshViewMap()'>
                        </efmfm-button>
                        <efmfm-button img-class = "efmfm_dashboarButtons_remove"
                                      src-url = "images/portlet-remove-icon"
                                      selected-url = "images/portlet-remove-icon"
                                      hover-url = "images/portlet-remove-icon"
                                      alt-text = "Remove Window"
                                      main-div = "activeEscortContent"
                                      target-div = "activeEscortTable">
                        </efmfm-button>
                    </div>
                </div>
<!--
                   <div class = "row"><div class = "col-md-12">
                       <input type = 'button' 
                              class = "btn btn-primary btn-sm showAllRoutes_viewMap floatRight" 
                              value = "Show All Routes" 
                              ng-click = "shaowAllRoutes()"></div></div>
-->
                    <div class = "activeEscortTable">
                        <table class = "viewMapTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Route Id</th>
                                      <th>Shift Time</th>
                                      <th>Driver Number</th>
                                      <th>Driver Name</th>
                                      <th>Vehicle Number</th>
                                      <th>TripType</th>
                                      <th>Route Name</th>
                                      <th>Status</th>
                                      <th>Expected time</th>
                                      <th>Total Employees</th>
                                      <th>Speed(kph)</th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "mapData.length==0">
                                        <tr>
                                            <td colspan = '11'>
                                                <div class = "noData">There is No Active Route</div>
                                            </td>
                                        </tr>
                                </tbody>
                                <tbody ng-show = "mapData.length>0">
<!-- 		                           <tr ng-repeat = "route in mapData" class = "pointer">
 -->  
                                   <tr ng-repeat = "route in mapData | filter:searchViewMap" class = "pointer">
                                     <td>{{route.routeId}}</td>
                                      <td>{{route.shiftTime}}</td>
		                              <td>{{route.driverNumber}}</td>
		                              <td>{{route.driverName}}</td>
                                      <td>{{route.vehicleNumber}}</td>
                                      <td>{{route.tripType}}</td>
		                              <td>{{route.zoneName}}</td>
		                              <td>{{route.status}}</td>
                                      <td>{{route.ExpectedTime}}</td>
                                       <td>{{route.numberOfEmployees}}</td>
                                      <td>{{route.speed}}</td>
                                      <td>
                                          <input type="button" 
                                                 class = "btn btn-success btn-xs showDetailButton_viewMap" 
                                                 value = "Show Details"
                                                 ng-click = "thisRouteDetails($index, route)">
                                      </td>
		                            </tr>                                    
		                         </tbody>
                            </table>
                    </div>
                
                
                </div>
                <div class="clearfix"></div>
                <br>
              <!--    /*END OF WRAPPER1 = DRIVER*/
                 /*START OF WRAPPER2 = VEHICLE*/ -->
                <div class = "wrapper1" id = "mapContent">
                <div class = "heading2 row">
                    <span class = "col-md-7 floatLeft" id = "map_viewMap">All Routes</span>
                    <div class= "col-md-5 floatRight">
                    	<efmfm-button img-class = "efmfm_approvalButtons_collapse"
                                      src-url = "images/portlet-collapse-icon"
                                      selected-url = "images/portlet-expand-icon"
                                      hover-url = "images/portlet-collapse-icon"
                                      alt-text = "Collapse Window"
                                      main-div = "mapContent"
                                      target-div = "map_viewMap">
                        </efmfm-button> 
                        <efmfm-button img-class = "efmfm_approvalButtons_reload"
                                      src-url = "images/portlet-reload-icon"
                                      selected-url = "images/portlet-reload-icon"
                                      hover-url = "images/portlet-reload-icon"
                                      alt-text = "Reload Window"
                                      main-div = "mapContent"
                                      target-div = "map_viewMap"
                                      ng-click="refreshViewMap()">
                        </efmfm-button>
                        <efmfm-button img-class = "efmfm_dashboarButtons_remove"
                                      src-url = "images/portlet-remove-icon"
                                      selected-url = "images/portlet-remove-icon"
                                      hover-url = "images/portlet-remove-icon"
                                      alt-text = "Remove Window"
                                      main-div = "mapContent"
                                      target-div = "map_viewMap">
                        </efmfm-button>
                    </div>
                </div>

                <div ui-view class = "map_viewMap" ng-if = "dataLoaded">
                    
                </div>
                                
               </div>    
                
            </div>
                 
        </div>
        
    </div> 
   
    
</div>

