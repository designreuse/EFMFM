<div class = "singleRouteMapTemplate container-fluid">
    <div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-12 col-xs-12 ">Real-Time Tracking for : <strong>{{mapData[0].driverName}}
<!--
                <span class = "drivernumbe1r_viewSingleMap ">(</span>
                <span class = "">{{singleRouteData[0].driverNumber}}</span>
                <span class = " drivernumber2_viewSingleMap">)</span>
-->
                </span>
                      
            <div class = "col-md-12 col-xs-12 mainSingleMapTabDiv_viewMap">
                <div class = "row showAllDiv">                  
                    
                    <div class = "col-md-12 col-xs-12">
                        <input type="button" 
                               class = "btn btn-success floatRight" 
                               value = "Go Back to All Routes"
                               ng-click = "showAll()">
                    </div>
                </div>
                
          <!--   /*START OF WRAPPER1 = DRIVER*/ -->
               <div class = "wrapper1" id="employeeOnMapContent">
                <div class = "heading2 row">
                    <span class = "col-md-7 floatLeft" id = "driver">Employee List for Route : {{routeId}}</span>
                    <div class= "col-md-5 floatRight">
                    	<efmfm-button img-class = "efmfm_approvalButtons_collapse"
                                      src-url = "images/portlet-collapse-icon"
                                      selected-url = "images/portlet-expand-icon"
                                      hover-url = "images/portlet-collapse-icon"
                                      alt-text = "Collapse Window"
                                      main-div = "employeeOnMapContent"
                                      target-div = "employeeOnMapTable">
                        </efmfm-button> 
                        <efmfm-button img-class = "efmfm_approvalButtons_reload"
                                      src-url = "images/portlet-reload-icon"
                                      selected-url = "images/portlet-reload-icon"
                                      hover-url = "images/portlet-reload-icon"
                                      alt-text = "Reload Window"
                                      main-div = "employeeOnMapContent"
                                      target-div = "employeeOnMapTable">
                        </efmfm-button>
                        <efmfm-button img-class = "efmfm_dashboarButtons_remove"
                                      src-url = "images/portlet-remove-icon"
                                      selected-url = "images/portlet-remove-icon"
                                      hover-url = "images/portlet-remove-icon"
                                      alt-text = "Remove Window"
                                      main-div = "employeeOnMapContent"
                                      target-div = "employeeOnMapTable">
                        </efmfm-button>
                    </div>
                </div>
                   <div class = "row">
                       <div class = "col-md-12">                      
                    <div class = "employeeOnMapTable">
                        <table class = "viewSingleRouteMapTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Employee Id</th>
                                      <th>Employee Name</th>
                                      <th>Employee Number</th>
                                      <th>Pickup/Drop Address</th>
                                      <th>Shift Time</th>
                                      <th>Planned {{mapData[0].tripType}} Time</th>
                                      <th>Actual {{mapData[0].tripType}} Time</th>
                                      <th>Status</th>
                                    </tr> 
                                </thead>
                                <tbody>
		                           <tr ng-repeat = "employee in mapData[0].empDetails" class = "pointer" ng-click = "viewThisRoute($index)">
                                      <td>{{employee.employeeId}}</td>
		                              <td>{{employee.name}}</td>
		                              <td>{{employee.employeeNum}}</td>
                                      <td>{{employee.address}}</td>
		                              <td>{{employee.tripTime| date:'short'}}</td>		                              
		                               <td>{{employee.pickUpTime}}</td>		                              
		                              <td>{{employee.actualUpTime}}</td>
		                              <td>{{employee.status}}</td>
		                            </tr>                                    
		                         </tbody>
                            </table>
                    </div>               
                </div>
                   </div>
                </div>
                <div class="clearfix"></div>
                <br>
              <!--    /*END OF WRAPPER1 = DRIVER*/
                 /*START OF WRAPPER2 = VEHICLE*/ -->
                <div class = "wrapper1" id = "mapContent">

                  <div class = "heading2 row">
                    <span class = "col-md-7 floatLeft" id = "map_viewMap">Map</span>
                    <div class= "col-md-5 floatRight">
                    	<efmfm-button img-class = "efmfm_approvalButtons_collapse"
                                      src-url = "images/portlet-collapse-icon"
                                      selected-url = "images/portlet-expand-icon"
                                      hover-url = "images/portlet-collapse-icon"
                                      alt-text = "Collapse Window"
                                      main-div = "mapContent"
                                      target-div = "mapInfoDiv">
                        </efmfm-button> 
                        <efmfm-button img-class = "efmfm_approvalButtons_reload"
                                      src-url = "images/portlet-reload-icon"
                                      selected-url = "images/portlet-reload-icon"
                                      hover-url = "images/portlet-reload-icon"
                                      alt-text = "Reload Window"
                                      main-div = "mapContent"
                                      target-div = "mapInfoDiv">
                        </efmfm-button>
                        <efmfm-button img-class = "efmfm_dashboarButtons_remove"
                                      src-url = "images/portlet-remove-icon"
                                      selected-url = "images/portlet-remove-icon"
                                      hover-url = "images/portlet-remove-icon"
                                      alt-text = "Remove Window"
                                      main-div = "mapContent"
                                      target-div = "mapInfoDiv">
                        </efmfm-button>
                    </div>
                </div>

               <div class = "col-md-12 col-xs-12 mapInfoDiv">
                   <div class = "col-md-8 col-xs-12">
                       <div class = 'map_viewMap_single' ng-if = "singleDataLoaded">
                            <efmfm-single-live-trip id = 'map-canvas1'></efmfm-single-live-trip>
                        </div>
                   </div>
                   <div class = "col-md-4 col-xs-12 infoDiv">
                       <div class = "row">
                           <div class = "col-md-4 col-sm-12 floatLeft">Route ID</div>
                           <div class = "col-md-8 col-sm-12 floatLeft"><input type = "text" ng-model = "mapData[0].routeId"></div><br>
                           <div class = "col-md-4 col-sm-12 floatLeft">Driver Name</div>
                           <div class = "col-md-8 col-sm-12 floatLeft"><input type = "text" ng-model = "mapData[0].driverName"></div><br>
                           <div class = "col-md-4 col-sm-12 floatLeft">Driver Mobile</div>
                           <div class = "col-md-8 col-sm-12 floatLeft"><input type = "text" ng-model = "mapData[0].driverNumber"></div><br>
                           <div class = "col-md-4 col-sm-12 floatLeft">Driver Speed</div>
                           <div class = "col-md-8 col-sm-12 floatLeft"><input type = "text" ng-model = "mapData[0].speed"></div><br>
                           <div class = "col-md-4 col-sm-12 floatLeft">ETA</div>
                           <div class = "col-md-8 col-sm-12 floatLeft"><input type = "text" ng-model = "mapData[0].expectedTime"></div><br>
                           <div class = "col-md-4 col-sm-12 floatLeft">Total Distance</div>
                           <div class = "col-md-8 col-sm-12 floatLeft"><input type = "text" ng-model = "mapData[0].plannedDistance"></div><br>
                           <div class = "col-md-4 col-sm-12 floatLeft">Distance Travelled</div>
                           <div class = "col-md-8 col-sm-12 floatLeft"><input type = "text" ng-model = "mapData[0].travelledDistance"></div><br>
                           <div class = "col-md-4 col-sm-12 floatLeft">Current Location</div>
                           <div class = "col-md-8 col-sm-12 floatLeft"><input type = "text" ng-model = "mapData[0].currentLocation"></div><br>
                           <div class = "col-md-4 col-sm-12 floatLeft">Trip Start Location</div>
                           <div class = "col-md-8 col-sm-12 floatLeft"><input type = "text" ng-model = "mapData[0].currentLocation"></div><br>
                       </div>
                   </div>
                 </div>

              </div>  <!--    /*END OF WRAPPER1 = DRIVER*/  -->
       </div>
   </div>  
  </div>
 </div>  