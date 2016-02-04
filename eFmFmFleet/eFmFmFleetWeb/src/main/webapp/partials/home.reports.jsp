<div class = "reportsTemplate container-fluid">
    <div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-12 col-xs-12">Reports</span>            
            <div class = "col-md-12 col-xs-12 mainTabDiv_reports">
            <!-- /*START OF WRAPPER1 = DRIVER*/ -->
               <div class = "wrapper1" id="driverContent">             
                
                <tabset class="tabset tripSheet_reports">
                    
                <!-- --------------------------------------------------------------- -->   
                  <tab ng-click = "setDates('tripSheet')">
                    <tab-heading>Trip Sheet</tab-heading>
                        <div class = "tripSheetTabContent"><div class = "searchDIVREPORT row">
                          <!--   <div class = "col-md-3">
                            	<select class = "select_reports form-control"
                                        ng-model="showRecords" 
                						ng-options="pagination.text for pagination in paginations track by pagination.value"
                						ng-change = "setLimit(showRecords)">
      						  		<option value="">-- All Records --</option>
    							</select>
    						</div> -->
<!--                            <div class = "col-md-3"></div>-->
                        	<div class = "col-md-2">
                        		<input class = "form-control" ng-model = "efmfilter.filterTrips" placeholder = "Filter Trip">
                        	</div>
                            <div class = "col-md-4"></div>
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                            </div></div>
                        	
                            <div id ='exportableTripSheet' class = "col-md-12 col-xs-12 tableWrapper_report" ng-show="gotTripResult">
                            <table class = "reportTable reportTable_km table table-responsive container-fluid" ng-show = "tripSheetData.length>0">
                                <thead class ="tableHeading">
                                    <tr><th colspan = "15" class ="tableHeadding_km" style = "background-color:rgba(0, 0, 0, 0.46); color: white">Trip Sheet Report </br>Date : <span>{{searchFromDateTS | date : 'longDate'}} - {{searchToDateTS | date : 'longDate'}}</span></th></tr>
                                    <tr>
                                      <th>Trip Id</th>
                                       <th>Trip Assign Time</th>
                                      <th>Trip Start Time</th>
                                      <th>Trip Completed Time</th>
                                      <th>Shift Time</th> 
                                      <th>Route Name</th>                                   
                                      <th>Vehicle No</th>
                                      <th>Driver</th>
                                      <th>Device No</th>
                                      <th>Escort</th>
                                      <th>Type</th>
                                      <th>Emp Details</th>                                                                           
                                     <!--  <th>Start/End(km)</th> -->
<!--                                      <th>End (km)</th>-->
                                      <th>Planned Distance (KM)</th>
                                      <th>GPS Traveled Distance (KM)</th>
<!--                                      <th>Odometer Distance (KM)</th>-->
                                        <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                            <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button></th>
                                    </tr> 
                                </thead>
<!--

                                <tbody ng-show = "!isResult">
                                    <tr>
                                        <td colspan = '12'>
                                            <div class = "noData">Please Select Valid Date from the Drop Down Menu</div>
                                        </td>
                                    </tr>
                                </tbody>
-->
<!--
                                <tbody ng-show = "tripSheetData.length==0 || !isResult">
                                    <tr>
                                        <td colspan = '12'>
                                            <div class = "noData">No Trip Sheet Found for this Date Range</div>
                                        </td>
                                    </tr>
                                </tbody>
-->

<!--                                <tbody ng-show = "tripSheetData.length>0">-->
                                <tbody>
		                           <tr ng-repeat-start="date in tripSheetData" class = "visibleRow_reportTripSheet">
                                       <td colspan="15">{{date.tripAssignDate}}</td>
                                   </tr> 
		                            <tr ng-repeat-end="" 
                                        ng-repeat-start = "trip in date.tripDetail | filter: efmfilter.filterTrips"
                                        class = "secondaryloop_tripReport">
		                            	<td class = "col5">{{trip.routeId}}</td>
		                            	<td class = "col10">{{trip.tripStartDate}}</td>
		                            	<td class = "col10">{{trip.tripCompleteDate}}</td>
		                            	<td class = "col10">{{trip.tripCompleteDate}}</td> 
                                        <td class = "col10">{{trip.shitTime}}</td>
                                        <td class = "col10">{{trip.routeName}}</td>                                       
                                        <td class = "col10">{{trip.vehicleNumber}}</td>
                                        <td class = "col10">{{trip.driverName}}</td>
                                        <td class = "col10">{{trip.deviceNumber}}</td>
                                        <td class = "col5">{{trip.escortRequired}}</td>
                                        <td class = "col5">{{trip.tripType}}</td>
                                        <td class = "col10"><div ng-repeat = "employee in trip.empDetails"
                                                                 class = "pointer employeeDetailDiv"
                                                                 popover-template="partials/popovers/employeeDetails.jsp"
                                                                 popover-placement="right"
                                                                 popover-title = "Employee Detail"
                                                                 popover-trigger ="mouseenter"
                                                                 popover-append-to-body = true>
                                            <span><i class = "icon-user employeeIcon_report"
                                                     ng-class = "{noShowTrip: employee.travelStatus == 'noShow'}"></i></span>
                                            <span>Id - {{employee.empId}}</span>
                                            <span>|</span>
                                            <span  class = "marginLeft10">Name - {{employee.empName}}</span>
                                            </div>
                                        </td>                                        
<!--                                         <td class = "col10">{{trip.startKm}}/{{trip.endKm}}</td>
 --><!--                                        <td class = "col10">{{trip.endKm}}</td>-->
                                        <td class = "col5">{{trip.plannedDistance}}</td>
                                        <td class = "col5">{{trip.travelledDistance}}</td>
<!--                                        <td class = "col5">{{trip.odoMeter}}</td>-->
                                        <td></td>
		                            </tr> 
                                    <tr ng-repeat-end="" style = "display:none"></tr>
		                         </tbody>                                
                            </table> 
                            <div class = 'row noData' ng-hide = "tripSheetData.length>0" >
                                <div class = 'col-md-12'>
                                    <div>No Data Found. Please select different dates.</div>
                                </div>
                            </div>
                          </div> 
                        </div>
                    </tab><!--First Tab Ends-->                   
                                
                    
                    <!-- On Time Report Tab -->
                    <tab ng-click = "setDates('onTime')">
                    <tab-heading>On Time</tab-heading>
                       <div class = "kmTabContent">
                           <div class = "searchDIVREPORT row marginRow">                           
<!--
                                <div class = "col-md-3">
                                    <select class = "select_reports form-control"
                                            ng-model="showRecords" 
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                        <option value="">-- All Records --</option>
                                    </select>
                                </div>
-->
                               <div class = "col-md-2"> 
                                   <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterOTData'>
                               </div>     
                               <div class = "col-md-2">                                   
                                   <select ng-model="searchOT.type"
                                           class="form-control" 
                                           ng-options="OTTripType.text for OTTripType in OTTripTypes track by OTTripType.value"
                                           ng-change = "setTripTypeOT(searchOT.type)"
                                           >
<!--                                     <option value="">-- Select Trip Type --</option>-->
                                  </select>
                               </div>    
                               <div class = "col-md-2">                                   
                                   <select ng-model="searchOT.OTShift"
                                           class="form-control" 
                                           ng-options="OTShiftTime.text for OTShiftTime in OTShiftTimes track by OTShiftTime.value"
                                           ng-change = "setShiftOT(searchOT.OTShift)"
                                           >
<!--                                     <option value="">-- Select Shift --</option>-->
                                  </select>
                               </div>    
                               <div class = "col-md-3">                                   
                                   <select ng-model="searchOT.OTVendors"
                                           class="form-control" 
                                           ng-options="OTAllVendor.name for OTAllVendor in OTAllVendors track by OTAllVendor.Id"
                                           ng-change = "setVendorOT(searchOT.OTVendors)"
                                           >
<!--                                     <option value="">-- Select Vendors --</option>-->
                                  </select>
                               </div>
                                <div class = "col-md-3">
                                    <div class = "calenderMainDiv floatRight pointer" 
                                         popover-template="partials/popovers/calenderReport.jsp"
                                         popover-placement="bottom"
                                         popover-title = "Get Report"
                                         popover-trigger ="click">                           
                                            <span><i class = "icon-calendar"></i></span>
                                            <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                            <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                    </div>                                  
                               </div>   
                           </div>                     
                        <!-- Table-->                           
                           <div id="exportableOnTime" class = "col-md-12 col-xs-12 tableWrapper_report">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid" ng-show="gotOTResult">
                                    <thead class ="tableHeading">
                                        <tr>
                                            <th colspan = '12' style = "background-color:rgba(0, 0, 0, 0.46); color: white">OT{{OT}} Reoprt for {{headingVendorsOT}} | {{headingShiftOT}}</br>
                                        <span>{{searchFromDateOT | date : 'longDate'}} - {{searchToDateOT | date : 'longDate'}}</span></th>
                                        </tr>
                                        <tr>
                                          <th rowspan = '2'>{{OTresultDateOrShift}}</th>
                                          <th rowspan = '2' ng-show = 'vendorName'>Vendor Name</th>                                        
                                           <th rowspan = '2'>Actual Users</th>
                                          <th rowspan = '2'>Total Fleets of the Day</th>
                                          <th colspan = '8' class = 'monthlyDailyCol' style = "background-color:#eb9316;">Monthly and Daily OT{{OT}}</th>
                                        </tr>
                                        <tr>
                                          <th>{{OTresultTripType}} Pax</th>
                                          <th>{{OTresultTripType}} Trips</th>
                                          <th>OT{{OT}} Trips</th>
                                          <th>OT{{OT}} in %</th>
                                          <th ng-if = "OTresultTripType=='Pickup'">Delay Trips</th>
                                          <th style = 'background-color: rgba(107, 228, 12, 0.37);'>Beyond Login Time</th>
                                          <th>No Show</th>
                                            <th><button class = "btn btn-sm btn-success form-control excelExportButton" 
                                                        ng-click = "saveInExcel()">
                                                    <i class = "icon-download-alt"></i>
                                                    <span class = "marginLeft5">Excel</span>
                                                </button>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                       <tr class = "reportResultTable tabletdCenter" ng-repeat="OTA in onTimeData | filter: efmfilter.filterOTData">
                                           <td>{{OTA.tripDates}}</td>
                                            <td ng-show = 'vendorName'>{{OTA.vendorName}}</td>
                                            <td>{{OTA.totalAllocatedEmployeesCount}}</td>
                                           <td>{{OTA.totalUsedVehicles}}</td>
                                           
                                           <td>{{OTA.totalEmployeesPickedDropCount}}</td>
                                           <td>{{OTA.totalTrips}}</td>
                                           
                                           <td>{{OTA.onTimeTrips}}</td>
                                            <td>{{OTA.delayTripsPercentage}}</td>
                                           
                                           <td ng-if = "OTresultTripType=='Pickup'">{{OTA.totalDelayTrips}}</td>
                                           <td class = 'highlightedColumn_Report'>{{OTA.totalDelayTripsBeyondLogin}}</td>
                                           <td>{{OTA.totalEmployeesNoShowCount}}</td>
                                           <td></td>
                                       </tr> 
                                     </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
                    <!-- End of On Time Report Tab -->               
                    
                   <!-- Seat Utilization Report Tab -->
                    <tab ng-click = "setDates('seatUtil')">
                    <tab-heading>Seat Utilization</tab-heading>
                       <div class = "kmTabContent">
                           <div class = "searchDIVREPORT row marginRow">                           
<!--
                                <div class = "col-md-3">
                                    <select class = "select_reports form-control"
                                            ng-model="showRecords" 
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                        <option value="">-- All Records --</option>
                                    </select>
                                </div>
-->
                               <div class = "col-md-2"> 
                                   <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterSeatUtilizationData'>
                               </div>     
                               <div class = "col-md-3">                                   
                                   <select ng-model="searchSU.type"
                                           class="form-control" 
                                           ng-options="SUTripType.text for SUTripType in SUTripTypes track by SUTripType.value"
                                           ng-change = "setTripTypeSU(searchSU.type)"
                                           >
<!--                                     <option value="">-- Select Trip Type --</option>-->
                                  </select>
                               </div>   
                                 
                              <!--  <div class = "col-md-2">                                   
                                   <select ng-model="searchSU.SUOption"
                                           class="form-control" 
                                           ng-options="reportSUOption.text for reportSUOption in reportSUOptions track by reportSUOption.value"
                                           ng-change = "setSUOption(searchSU.SUOption)"
                                           >
                                  </select>
                               </div>  -->
                               <div class = "col-md-3">                                   
                                   <select ng-model="searchSU.SUShift"
                                           class="form-control" 
                                           ng-options="SUShiftTime.text for SUShiftTime in SUShiftTimes track by SUShiftTime.value"
                                           ng-change = "setShiftSU(searchSU.SUShift)"
                                           ng-disabled = 'searchSU.SUOption.text != "By Shift"'
                                           >
<!--                                     <option value="">-- Select Shift --</option>-->
                                  </select>
                               </div>    
                               <div class = "col-md-1">                                   
<!--
                                   <select ng-model="searchSU.SUVendors"
                                           class="form-control" 
                                           ng-options="SUAllVendor.name for SUAllVendor in SUAllVendors track by SUAllVendor.Id"
                                           ng-change = "setVendorSU(searchSU.SUVendors)"
                                           >
                                  </select>
-->
                               </div>
                                <div class = "col-md-3">
                                    <div class = "calenderMainDiv floatRight pointer" 
                                         popover-template="partials/popovers/calenderReport.jsp"
                                         popover-placement="bottom"
                                         popover-title = "Get Report"
                                         popover-trigger ="click">                           
                                            <span><i class = "icon-calendar"></i></span>
                                            <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                            <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                    </div>                                  
                               </div>   
                           </div>                     
                        <!-- Table-->                           
                           <div class = "col-md-12 col-xs-12 tableWrapper_report">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid" ng-show="gotSUResult">
                                    <thead class ="tableHeading">
                                        <tr>
                                            <th colspan = '11' style = "background-color:black; color: white">{{SUresultTripType}}  - {{SUReportTitle}} 
                                        </br> <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span></th>
                                        </tr>
                                        <tr>
                                          <th rowspan = '2'>{{SUresultDateOrShift}}</th>
                                          <th rowspan = '2'>Number of Vehicles Used</th>
                                          <th rowspan = '2'>Total Opportunity</th>
                                          <th colspan = '3' class = 'monthlyDailyCol' style = "background-color:#eb9316;"></th>
                                        </tr>
                                        <tr>
                                          <th>{{SUresultTripType}} Pax</th>
                                          <th style = 'background-color: rgba(107, 228, 12, 0.37);'>{{SUresultTripType}} SU %</th>
                                            <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                        <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                       <tr class = "reportResultTable tabletdCenter" ng-repeat="SU in seatUtilData | filter:efmfilter.filterSeatUtilizationData">
                                           <td>{{SU.tripDates}}</td>
                                           <td>{{SU.totalUsedVehicles}}</td>
                                           <td>{{SU.totalVehicleCapacity}}</td>
                                           <td>{{SU.totalEmployeesPickedDropCount}}</td>
                                           <td class = 'highlightedColumn_Report'>{{SU.utilizedSeatPercentage}}</td>
                                           <td></td>
                                       </tr> 
                                     </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
                    <!-- End of Seat Utilization Report Tab -->
                    
                    <!-- No Show Report Tab -->
                    <tab ng-click = "setDates('noShow')">
                    <tab-heading>No Show</tab-heading>
                       <div class = "kmTabContent">
                           <div class = "searchDIVREPORT row marginRow">                           
<!--
                                <div class = "col-md-3">
                                    <select class = "select_reports form-control"
                                            ng-model="showRecords" 
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                        <option value="">-- All Records --</option>
                                    </select>
                                </div>
-->
                               <div class = "col-md-2"> 
                                   <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterNoShowData'>
                               </div>     
                               <div class = "col-md-2">                                   
                                   <select ng-model="searchNS.type"
                                           class="form-control" 
                                           ng-options="NSTripType.text for NSTripType in NSTripTypes track by NSTripType.value"
                                           ng-change = "setTripTypeNS(searchNS.type)"
                                           >
<!--                                     <option value="">-- Select Trip Type --</option>-->
                                  </select>
                               </div>  
                               <div class = "col-md-2">                                   
                                   <select ng-model="searchNS.NSShift"
                                           class="form-control" 
                                           ng-options="NSShiftTime.text for NSShiftTime in NSShiftTimes track by NSShiftTime.value"
                                           ng-change = "setShiftSU(searchSU.SUShift)"
                                           ng-disabled = 'searchNS.employeeId'>
<!--                                     <option value="">-- Select Shift --</option>-->
                                  </select>
                               </div>       
                               <!-- <div class = "col-md-1">                                   
                                   <select ng-model="searchNS.NSOption"
                                           class="form-control" 
                                           ng-options="reportNoShowOption.text for reportNoShowOption in reportNoShowOptions track by reportNoShowOption.value"
                                           ng-change = "setNoShowOption(searchNS.NSOption)"
                                           >
                                     <option value="">-- Search By --</option>
                                  </select>
                               </div>     -->
                               <!-- <div class = "col-md-1">                                   
                                   <select ng-model="searchNS.NSCount"
                                           class="form-control" 
                                           ng-options="noShowCount.text for noShowCount in noShowCounts track by noShowCount.value"
                                           >
                                  </select>
                               </div>  -->
  
                               <div class = "col-md-2">                                   
                                   <input ng-model="searchNS.employeeId"
                                           class="form-control" 
                                           placeholder = 'Employee Id'>
                               </div>
                                <div class = "col-md-4">
                                    <div class = "calenderMainDiv floatRight pointer" 
                                         popover-template="partials/popovers/calenderReport.jsp"
                                         popover-placement="bottom"
                                         popover-title = "Get Report"
                                         popover-trigger ="click">                           
                                            <span><i class = "icon-calendar"></i></span>
                                            <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                            <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                    </div>                                  
                               </div>   
                           </div>                     
                        <!-- Table-->                           
                           <div class = "col-md-12 col-xs-12 tableWrapper_report">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid" ng-show="gotNSResult">
                                    <thead class ="tableHeading">
                                        <tr>
                                            <th colspan = '5' style = "background-color:black; color: white">
                                                {{NSResultTripType}} - {{NSReportTitle}} </br> <span>{{searchFromDatesNS | date : 'longDate'}} - {{searchToDatesNS | date : 'longDate'}}</span>
                                            </th>
                                
<!--
                                            <th ng-show = 'searchNSByEmployeeId' colspan = '5' style = "background-color:black; color: white">
                                                {{NSResultTripType}} - No Show Report for By Employee Id </br> <span>{{searchFromDatesNS | date : 'longDate'}} - {{searchToDatesNS | date : 'longDate'}}</span>
                                            </th>
-->
                                        </tr>
                                        <tr>
                                          <th ng-show = 'NSresultShift == "All Shifts" && !searchNSByEmployeeId'>Date</th>
                                          <th ng-show = 'NSresultShift == "By Shifts" && !searchNSByEmployeeId'>Shift Time</th>
                                          <th ng-show = 'searchNSByEmployeeId'>Date</th>
                                          <th ng-show = 'searchNSByEmployeeId'>Shift Time</th>
                                          <th>{{NStotalShift}}</th>
                                          <th>{{NSTripType}}</th>
                                          <th ng-hide = noShowResult == '0' style = 'background-color: rgba(107, 228, 12, 0.37);' >No-Show</th>
                                            <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                                    <i class = "icon-download-alt"></i>
                                                    <span class = "marginLeft5">Excel</span>
                                                </button>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                       <tr class = "reportResultTable tabletdCenter" ng-repeat="NS in noShowReportData | filter:efmfilter.filterNoShowData">
                                           <td ng-show = 'NSresultShift == "All Shifts" && !searchNSByEmployeeId'>{{NS.tripDates}}</td>
                                           <td ng-show = 'NSresultShift == "By Shifts" && !searchNSByEmployeeId'>{{NS.tripDates}}</td> 
                                           <td ng-show = 'searchNSByEmployeeId'>{{NS.tripDates}}</td>
                                           <td ng-show = 'searchNSByEmployeeId'>7:00**</td>
                                           
                                           <td>{{NS.totalUsedVehicles}}</td>
                                           <td>{{NS.totalEmployeesPickedDropCount}}</td>
                                           <td ng-hide = noShowResult == '0' class = 'highlightedColumn_Report' >{{NS.totalEmployeesNoShowCount}}</td>
                                           <td></td>
                                       </tr> 
                                     </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
                    <!-- End of Seat Utilization Report Tab -->
                    
                    <!--Second Tab Ends-->
<!--
                    <tab ng-click = "setDates('noShowReport')">
                    <tab-heading>No Show</tab-heading>
                       <div class = "kmTabContent"><div class = "searchDIVREPORT row marginRow">
                            <div class = "col-md-3">
                            	<select class = "select_reports form-control"
                                        ng-model="showRecords" 
                						ng-options="pagination.text for pagination in paginations track by pagination.value"
                						ng-change = "setLimit(showRecords)">
      						  		<option value="">-- All Records --</option>
    							</select>
    						</div>       
                           <div class = "col-md-3"> 
                               <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'searchNoShow'>
                           </div> 
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                           </div></div>                       
                           <div  id = "customers" class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotNoShowResult">
                            <table class = "reportTable reportTable_km table table-responsive container-fluid" ng-show = "reportsNoShowData.length>0">
                                <thead class ="tableHeading"> <tr>
                                      <th>Travelled Date</th>
                                      <th>Employee Id</th>
                                      <th>Route Name</th>
                                      <th>Shift time</th>
                                        <th>Address</th>
                                        <th>Trip Type</th>
                                        <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                            <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat="report in reportsNoShowData | filter:searchNoShow">
                                       <td class = "col-md-2">{{report.travelledDate}}</td>
                                       <td class = "col-md-2">{{report.employeeId}}</td>
                                       <td class = "col-md-2">{{report.routeName}}</td>
                                       <td class = "col-md-2">{{report.shiftTime}}</td>
                                       <td class = "col-md-2">{{report.employeeAddress}}</td>
                                       <td class = "col-md-2">{{report.tripType}}</td>
                                       <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                            <div class = 'row noData' ng-hide = "reportsNoShowData.length>0" >
                                <div class = 'col-md-12'>
                                    <div>No Data Found. Please select different dates.</div>
                                </div>
                            </div>
                          </div> 
                        </div>
                    </tab>
-->
                    <!-- End of Second Tab -->
<!--
                    <tab ng-click = "setDates('noshow')">
                    <tab-heading>No Show Report</tab-heading>
                       <div class = "tripSheetTabContent row">
                            <div class = "col-md-3">
                            	<select class = "select_reports"
                                        ng-model="showRecords" 
                						ng-options="pagination.text for pagination in paginations track by pagination.value"
                						ng-change = "setLimit(showRecords)">
      						  		<option value="">-- All Records --</option>
    							</select>
    						</div>
                        	<div class = "col-md-3">
                        		<input class = "tripsheetSearchBox" ng-model = "searchNoShows" placeholder = "Search NoShows">
                        	</div>
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                </div>                                  
                            </div>
                            
                        	
                            <div class = "col-md-12 col-xs-12 tableWrapper_report">
                                <div class = "floatRight reportButtons">
                                
                                </div>
                            <table class = "reportTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Route ID</th>
                                      <th>Route Name</th>
                                      <th>Driver Name</th>
                                      <th>Vehicle Number</th>
                                      <th>Employee Details</th>
                                      <th>Trip Type</th>
                                      <th>Trip Completed</th>
                                      <th>No Show Counter</th>
                                    </tr> 
                                </thead>
                                <tbody ng-show = "reportsTripSheetData.length==0 || !isResult">
                                    <tr>
                                        <td colspan = '8'>
                                            <div class = "noData">Result: No Vendors found. Please search again!</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "reportsTripSheetData.length>0">
		                           <tr ng-repeat-start="date in noshowData" class = "visibleRow_reportTripSheet">
                                       <td colspan="13">{{date.tripAssignDate}}</td>
                                   </tr> 
		                            <tr ng-repeat-end="" ng-repeat-start = "route in date.tripDetail" class = "secondaryloop_tripReport">
		                            	<td class = "col-md-1">{{route.routeId}}</td>
                                        <td class = "col-md-2">{{route.routeName}}</td>                                        
                                        <td class = "col-md-1">{{route.driverName}}</td>                                       
                                        <td class = "col-md-1">{{route.vehicleNumber}}</td>
                                        <td class = "col-md-4">
                                            <div ng-repeat = "employee in route.empDetails"
                                                                 class = "pointer employeeDetailDiv2"
                                                                 popover-template="partials/popovers/noShowEmployee.jsp"
                                                                 popover-placement="left"
                                                                 popover-title = "Employee Detail"
                                                                 popover-trigger ="mouseenter"
                                                                 popover-append-to-body = true>
                                                <span><i class = "icon-user employeeIcon_report"></i></span>
                                                <span>Id - {{employee.empId}}</span>
                                                <span  class = "marginLeft10">Name - {{employee.empName}}</span>                                            
                                                <span>Cab Arrival Time - {{employee.cabArrivalTime}}</span>
                                                <span>No Show Time	 - {{employee.noShowtime}}</span>
                                                <span>Schedule Time - {{employee.scheduleTime}}</span>
                                            </div>
                                        </td>                                        
                                        <td class = "col-md-1">{{route.tripType}}</td>                                          
                                        <td class = "col-md-1">{{route.tripCompleteDate}}</td>                                    
                                        <td class = "col-md-1"><div class = "noshowCounter"
                                                                 ng-class = "{warning: route.count>=10}"><span><strong>{{route.noshowCounter}}</strong></span></div></td>
		                            </tr> 
                                    <tr ng-repeat-end="" style = "display:none"></tr>
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
-->
                    <!-- End of Second Tab-->
                    
                    
                    <!-- Third Tab-->
<!--
                    <tab ng-click = "setDates('driverscore')">
                    <tab-heading>Driver Score Card</tab-heading>
                       <div class = "tripSheetTabContent row">
                            <div class = "col-md-3">
                            	<select class = "select_reports"
                                        ng-model="showRecords" 
                						ng-options="pagination.text for pagination in paginations track by pagination.value"
                						ng-change = "setLimit(showRecords)">
      						  		<option value="">-- All Records --</option>
    							</select>
    						</div>
                        	<div class = "col-md-3">
                        		<input class = "tripsheetSearchBox" ng-model = "searchDriverScore" placeholder = "Search Driver Score Card">
                        	</div>
                           
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                </div>                                  
                            </div>
                           <div class = "col-md-12 col-xs-12 tableWrapper_report">
                                <div class = "floatRight reportButtons">
                                
                                </div>
                            <table class = "reportTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Date</th>
                                      <th>Route Id</th>
                                      <th>Vehicle Number</th>
                                      <th>Speed Alert</th>
                                      <th>Accident Alert</th>
                                    </tr>
                                </thead>
                                <tbody ng-show = "driverScoreCard.length>0">
		                           <tr ng-repeat-start="driver in driverScoreCard | limitTo: numberofRecords | filter: searchDriverScore" 
                                       class = "visibleRow_reportTripSheet">
                                       <td colspan="12">
                                           <div class = "driverScoreCardMainDiv">
                                               <div class = "col-md-2 floatLeft">{{driver.driverName}}</div>
                                               <div class = "col-md-2 floatLeft"><span>Total Trips</span><span>{{driver.totalTrips}}</span></div>
                                               <div class = "col-md-2 floatLeft"><span class ="floatLeft">Total Speed Alerts</span>
                                                   <div class = "driverScoreCardMainDiv_speed floatLeft"><span>{{driver.totalSpeedAlerts}}</span></div></div>
                                               <div class = "col-md-2 floatLeft"><span class ="floatLeft">Total Accidents</span>
                                                   <div class = "driverScoreCardMainDiv_accident floatLeft"><span>{{driver.totalAccident}}</span></div></div>
                                           </div>
                                       </td>
                                   </tr> 
		                            <tr ng-repeat-end="" 
                                        ng-repeat-start = "driverDetail in driver.driverDetails | filter: searchDriverScore"
                                        class = "secondaryloop_tripReport">
		                            	<td>{{driverDetail.date}}</td>
                                                    <td>{{driverDetail.routeId}}</td>
                                                    <td>{{driverDetail.vehicleNumber}}</td>
                                                    <td>{{driverDetail.speedAlert}}</td>
                                                    <td>{{driverDetail.accidentAlert}}</td>
		                            </tr> 
                                    <tr ng-repeat-end="" style = "display:none"></tr>
		                         </tbody>                                
                            </table> 
                          </div> 

                        </div>
                    </tab>
-->
                    <!-- End of Third -->
                    
                    <!-- Fourth Tab -->
<!--
                    <tab ng-click = "setDates('vehiclescore')">
                    <tab-heading>Vehicle Score Card</tab-heading>
                       <div class = "tripSheetTabContent row">
                            <div class = "col-md-3">
                            	<select class = "select_reports"
                                        ng-model="showRecords" 
                						ng-options="pagination.text for pagination in paginations track by pagination.value"
                						ng-change = "setLimit(showRecords)">
      						  		<option value="">-- All Records --</option>
    							</select>
    						</div>
                        	<div class = "col-md-3">
                        		<input class = "tripsheetSearchBox" ng-model = "searchVehicleScoreCards" placeholder = "Search Vehicle Score Cards">
                        	</div>
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                </div>                                  
                            </div>
                           <div class = "col-md-12 col-xs-12 tableWrapper_report">
                                <div class = "floatRight reportButtons">
                                
                                </div>
                            <table class = "reportTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Date</th>
                                      <th>Route Id</th>
                                      <th>Driver Name</th>
                                      <th>Vehicle Breakdowns</th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat-start="vehicle in vehicleScoreCard | limitTo: numberofRecords | filter: searchVehicleScoreCards" 
                                       class = "visibleRow_reportTripSheet">
                                       <td colspan="12">
                                           <div class = "driverScoreCardMainDiv">
                                               <div class = "col-md-2 floatLeft"><span>Vehicle Number</span><span>{{vehicle.vehicleNumber}}</span></div>
                                               <div class = "col-md-2 floatLeft"><span>Total Trips</span><span>{{vehicle.totalTrips}}</span></div>
                                               <div class = "col-md-2 floatLeft"><span class ="floatLeft">Total Breakdowns</span>
                                                    <div class = "vehivleScoreCardMainDiv_breakdowns floatLeft"><span>{{vehicle.totalBreakdowns}}</span></div>
                                               </div>                                                         
                                           </div>
                                       </td>
                                   </tr> 
		                            <tr ng-repeat-end="" 
                                        ng-repeat-start = "vehicleDetail in vehicle.vehicleDetails | filter: searchVehicleScoreCards"
                                        class = "secondaryloop_tripReport">
		                            	            <td>{{vehicleDetail.date}}</td>
                                                    <td>{{vehicleDetail.routeId}}</td>
                                                    <td>{{vehicleDetail.driverName}}</td>
                                                    <td>{{vehicleDetail.vehicleBreakdown}}</td>
		                            </tr> 
                                    <tr ng-repeat-end="" style = "display:none"></tr>
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
-->
                    <!--END of Fourth Tab-->
                    
                    <!--  Fifth Tab -->
                    <tab ng-click = "setDates('kmReport')">
                    <tab-heading>Distance</tab-heading>
                       <div class = "kmTabContent" id = "pdfKM">
                           <div class = "searchDIVREPORT row marginRow">
                           <div class = "col-md-2">
                               <input type = 'text' class ='form-control' placeholder = 'Filter' ng-model = 'efmfilter.filterKm'></div>
<!--                           <div class = "col-md-1"></div>-->
                            <div class = "col-md-2">
                            	<select class = "marginBottom10 form-control"
                                        ng-model="reportKM.reportType" 
                						ng-options="reportType.text for reportType in reportTypes track by reportType.value"
                						ng-change = "getVendorOrVehicle(reportKM.reportType)"
                                        required>
<!--      						  		<option value="">-- Select --</option>-->
    							</select>
    						</div>
    						
     						<div class = "col-md-2">                                   
                                   <select ng-model="reportKM.distanceShift"
                                           class="form-control" 
                                           ng-options="DistanceShiftTime.text for DistanceShiftTime in DistanceShiftTimes track by DistanceShiftTime.value"
                                           ng-change = "setShiftDistance(searchDistance.distanceShift)"
                                           >
<!--                                    <option value="">-- Select Shift --</option>-->
                                  </select>
                               </div>    
    						
                        	<div class = "col-md-2">
                            	<select class = "select_reports_VSelection marginBottom10 form-control"
                                        ng-model="reportKM.VSelection" 
                						ng-options="vendorVehicle.name for vendorVehicle in vendorVehicles_KMReport track by vendorVehicle.Id"
                						ng-change = "setvendorVehicle(reportKM.VSelection)"
                                        required>
<!--      						  		<option value="">-- Select --</option>-->
    							</select>
                        	</div>                           
                            <div class = "col-md-4">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                           </div></div>
<!--
                            <div class = "col-md-2">                                
                              <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "From" 
                                     ng-model = "reportKM.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchFromDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                   <span class="input-group-btn">
                                    <button class="btn btn-default" ng-click="openSearchFromDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                                </div> 
                            </div>
                           <div class = "col-md-2">                               
                             <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "To" 
                                     ng-model = "reportKM.searchtoDate"
                                     ng-change = "searchPost(searchFromDate,searchtoDate)"
                                     min-date = "reportKM.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchToDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                  <span class="input-group-btn">
                                   <button class="btn btn-default" ng-click="openSearchToDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                              </div>
                           </div>
                           <div class = "col-md-2"> 
                               <input type = "button" class = "btn btn-success buttonRadius0" value = "Get Report" ng-click = "getReportKm(reportKM)"
                                      ng-disabled = "!reportKM.reportType || !reportKM.VSelection || !reportKM.searchFromDate || !reportKM.searchtoDate">
                               <button ng-click = "openPDF()">OPEN PDF</button>
                           </div>
-->
                           <!--ByVendor Div -->
                           <div class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotKMResult && reportTypeSelected == 'vendor'">                               
<!--                               <div class = "KmSummary row" ng-repeat = "reportSum_vendor in reportsSum">-->
                               <table class = "reportTable reportTable_km table table-responsive container-fluid" ng-show = "reportsSum.length>0">
                                    <thead class ="tableHeading">
                                        <tr><th colspan = "8" class ="tableHeadding_km" style = "background-color:rgba(0, 0, 0, 0.46); color: white">{{headingReportTypeKM}} Report for {{reportSelectionKM}} </br>Date : <span>{{searchFromDateKM | date : 'longDate'}} - {{searchToDateKM | date : 'longDate'}}</span></th></th></tr>
                                        <tr>
                                          <th>Date</th>
                                          <th ng-show = "isShiftKM">Shift Time</th>
                                          <th>Vendor Name</th>
                                          <th ng-hide = vehicleNumberField == '0' >Vehicle Number</th>
                                          <th>Type of Vehicle</th>
                                          <th>Total Opportunity</th>
                                          <th style = 'background-color: rgba(107, 228, 12, 0.37);'>Utilized KMs</th>
                                            <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                        <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                       <tr class = "reportSum tabletdCenter" ng-repeat="reportSum_vendor in reportsSum | filter:efmfilter.filterKm">
                                           <td>{{reportSum_vendor.date}}</td>
                                           <td ng-show = "isShiftKM">{{reportSum_vendor.shiftTime}}</td>
                                           <td>{{reportSum_vendor.vendorName}}</td>
                                           <td ng-hide = vehicleNumberField == '0' >{{reportSum_vendor.vehicleNumber}}</td>
                                           <td>{{reportSum_vendor.vehicleType}}</td>
                                           <td>{{reportSum_vendor.totalApportunity}}</td>
                                           <td class = 'highlightedColumn_Report'>{{reportSum_vendor.travelledDistance}}</td>
                                           <td></td>
<!--
                                           <td> <input type = "button" 
                                                       class = "btn btn-primary buttonRadius0 marginRight20"
                                                       value = "View Detail" 
                                                       ng-click = "viewKMDetails(reportSum_vendor)"
                                                       ng-show = "!reportSum_vendor.viewDetailIsClicked">
                                               <input type = "button" 
                                                       class = "btn btn-danger buttonRadius0 marginRight20"
                                                       value = "Close" 
                                                       ng-click = "viewKMDetails(reportSum_vendor)"
                                                       ng-show = "reportSum_vendor.viewDetailIsClicked">
                                           </td>
-->
                                       </tr> 
                                     </tbody>                                
                                </table> 
                                <div class = 'row noData' ng-hide = "reportsSum.length>0" >
                                    <div class = 'col-md-12'>
                                        <div>No Data Found. Please select different dates.</div>
                                    </div>
                                </div>
<!--
                                       <span>VENDOR NAME : <span><strong>{{reportSum_vendor.vendorName}}</strong></span></span>
                                   <span>VENDOR Id : <span><strong>{{reportSum_vendor.vendorId}}</strong></span></span>
                                   <span>DISTANCE TRAVELLED : <span><strong>{{reportSum_vendor.totalTravelledDistance}}km</strong></span>
                                   <span>PLANNED DISTANCE : <span><strong>{{reportSum_vendor.totalPlannedDistance}}km</strong></span></span>
-->
<!--                                       <input type = "button" class = "btn btn-primary buttonRadius0 marginRight20" value = "View Detail" ng-click = "viewKMDetails(reportSum_vendor)">-->
<!--                               </div>-->
                           </div>
                           <!--ByVehicle Div -->                           
                           <div class = "col-md-12 col-xs-12" ng-show = "gotKMResult && reportTypeSelected == 'vehicle'">                               
<!--                               <div class = "KmSummary row" ng-repeat = "reportSum_vehicle in reportsSum">-->
                                   <table class = "reportTable reportTable_km table table-responsive container-fluid" ng-show = "reportsSum.length>0">
                                        <thead class ="tableHeading">
        <!--                                    <tr col-span = "3" class ="tableHeadding_km">Kilometer Report</tr>-->
                                            <tr><th colspan = "8" class ="tableHeadding_km" style = "background-color:rgba(0, 0, 0, 0.46); color: white">{{headingReportTypeKM}} Report for {{reportSelectionKM}} </br>Date : <span>{{searchFromDateKM | date : 'longDate'}} - {{searchToDateKM | date : 'longDate'}}</span></th></th></tr>
                                            <tr>
                                                <th>Date</th>
                                                <th ng-show = "isShiftKM">Shift Time</th>
                                                <th>Vendor</th>
                                                <th>Vehicle Number</th>
                                                <th>Vehicle Type</th>
                                                <th>Total Opportunity</th>
                                                <th style = 'background-color: rgba(107, 228, 12, 0.37);'>Utilized KM's</th>
<!--
                                                <th>Vehicle Id</th>
                                                <th>Vehicle Number</th>
                                                <th>Vendor Name</th>
                                                <th>Distance Travelled (KM)</th>  
                                                <th>Travelled Date</th>                                                
                                                <th>Planned Distance</th>                                             
                                                <th>Trip Type</th>
                                                <th>Shift Time</th>
-->
                                                <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                            <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                           <tr class = 'tabletdCenter' ng-repeat="reportSum_vehicle in reportsSum | filter:efmfilter.filterKm">
                                               <td>{{reportSum_vehicle.date}}</td>
                                               <td ng-show = "isShiftKM">{{reportSum_vehicle.shiftTime}}</td>
                                               <td>{{reportSum_vehicle.vendorName}}</td>
                                               <td>{{reportSum_vehicle.vehicleNumber}}</td>
                                               <td>{{reportSum_vehicle.vehicleType}}</td>
                                               <td>{{reportSum_vehicle.totalApportunity}}</td>
                                               <td class = 'highlightedColumn_Report'>{{reportSum_vehicle.plannedDistance}}</td>
                                               <td></td>
                                           </tr> 
                                         </tbody>                                
                                    </table> 
                                    <div class = 'row noData' ng-hide = "reportsSum.length>0" >
                                        <div class = 'col-md-12'>
                                            <div>No Data Found. Please select different dates.</div>
                                        </div>
                                    </div>
<!--
                                       <span>VEHICLE NUMBER : <span><strong>{{reportSum_vehicle.vehicleNum}}</strong></span></span>
                                   <span>VENDOR NAME : <span><strong>{{reportSum_vehicle.vendorName}}</strong></span></span>
                                   <span>DISTANCE TRAVELLED : <span><strong>{{reportSum_vehicle.travelledDistance}}km</strong></span></span>
                                       <input type = "button" class = "btn btn-primary buttonRadius0" value = "View Detail" ng-click = "viewKMDetails(reportSum_vehicle)">
-->
<!--                               </div>-->
                           </div>
                           
                        <!-- Table-->                           
                           <div class = "col-md-12 col-xs-12 tableKmDetail tableWrapper_report">
                                <div class = "floatRight reportButtons">
                                
                                </div>
                            <table class = "reportTable reportTable_kmDetail table table-responsive container-fluid" ng-show = "viewDetail">
                                <thead class ="tableHeading">
                                    <tr class ="tableHeadding_km">
<!--
                                        <th class = "detailTableFirstHeader" colspan = "3">
                                            <div>Detail Report</div>
                                            <div><input type = 'text' class ='form-control' placeholder = 'Search' ng-model = 'searchKm'></div>
                                        </th>
-->
                                    </tr>
                                    <tr>
                                      <th>Vehicle Id</th>
                                      <th>Vehicle Number</th>
                                      <th>Travelled Date</th>
                                      <th>Travelled Distance</th>
                                      <th>Planned Distance</th>
                                      <th>Shift Time</th>
                                      <th>Vendor Name</th>
                                      <th>Trip Type</th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr class = 'tabletdCenter' ng-repeat="report in reportsKMData | filter:searchKm">
                                       <td>{{report.vehicleId}}</td>
                                       <td>{{report.vehicleNum}}</td>
                                       <td>{{report.travelledDate}}</td>
                                       <td>{{report.travelledDistance}}</td>
                                       <td>{{report.plannedDistance}}</td>
                                       <td>{{report.shiftTime}}</td>
                                       <td>{{report.vendorName}}</td>
                                       <td>{{report.tripType}}</td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
                    <!-- End of Fifth Tab -->
                    
                    <!--Sixth Tab-->
<!--
                    <tab ng-click = "initDate()">
                    <tab-heading>Login Report</tab-heading>
                       <div class = "kmTabContent row">
                            <div class = "col-md-2">                                
                              <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "From" 
                                     ng-model = "reportLogin.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchFromDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                   <span class="input-group-btn">
                                    <button class="btn btn-default" ng-click="openSearchFromDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                                </div> 
                            </div>
                           <div class = "col-md-2">                               
                             <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "To" 
                                     ng-model = "reportLogin.searchtoDate"
                                     ng-change = "searchPost(searchFromDate,searchtoDate)"
                                     min-date = "reportKM.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchToDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                  <span class="input-group-btn">
                                   <button class="btn btn-default" ng-click="openSearchToDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                              </div>
                           </div>
                           <div class = "col-md-2"> 
                               <input type = "button" class = "btn btn-success buttonRadius0" value = "Get Report" ng-click = "getReportLogin(reportLogin)" ng-disabled = "!reportLogin.searchFromDate || !reportLogin.searchtoDate">
                           </div>                           
-->
                        <!-- Table-->                           
<!--
                           <div class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotLoginResult">
                            <table class = "reportTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr col-span = "3" class ="tableHeadding_km">Kilometer Report</tr>
                                    <tr>
                                      <th>Date</th>
                                      <th>Employee Id</th>
                                      <th>Area</th>
                                      <th>Shift time</th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat="report in reportsLoginData">
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>            
-->
                    <!-- End of Sixth Tab -->
            
                    <!-- Seventh Tab -->
<!--
                    <tab ng-click = "initDate()">
                    <tab-heading>Drop Report</tab-heading>
                       <div class = "kmTabContent row">
                            <div class = "col-md-2">                                
                              <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "From" 
                                     ng-model = "reportDrop.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchFromDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                   <span class="input-group-btn">
                                    <button class="btn btn-default" ng-click="openSearchFromDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                                </div> 
                            </div>
                           <div class = "col-md-2">                               
                             <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "To" 
                                     ng-model = "reportDrop.searchtoDate"
                                     ng-change = "searchPost(searchFromDate,searchtoDate)"
                                     min-date = "reportKM.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchToDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                  <span class="input-group-btn">
                                   <button class="btn btn-default" ng-click="openSearchToDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                              </div>
                           </div>
                           <div class = "col-md-2"> 
                               <input type = "button" class = "btn btn-success buttonRadius0" value = "Get Report" ng-click = "getReportDrop(reportDrop)" ng-disabled = "!reportDrop.searchFromDate || !reportDrop.searchtoDate">
                           </div>                           
-->
                        <!-- Table-->                           
<!--
                           <div class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotDropResult">
                            <table class = "reportTable table table-responsive container-fluid">
                                <thead class ="tableHeading">

                                    <tr>
                                      <th>Vehicle Number</th>
                                      <th>Route Name</th>
                                      <th>No of employees dropped</th>
                                      <th>Shift time</th>
                                      <th>Last drop time</th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat="report in reportsDropData">
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
-->
                    <!-- End of Seventh Tab -->
            
                    <!-- Eight Tab -->
                    <tab ng-click = "setDates('SMSReport')">
                    <tab-heading>Daily SMS</tab-heading>
                       <div class = "kmTabContent">
                           <div class = "searchDIVREPORT row marginRow">
<!--
                            <div class = "col-md-2">                                
                              <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "From" 
                                     ng-model = "reportSMS.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchFromDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                   <span class="input-group-btn">
                                    <button class="btn btn-default" ng-click="openSearchFromDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                                </div> 
                            </div>
                           <div class = "col-md-2">                               
                             <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "To" 
                                     ng-model = "reportSMS.searchtoDate"
                                     ng-change = "searchPost(searchFromDate,searchtoDate)"
                                     min-date = "reportKM.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchToDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                  <span class="input-group-btn">
                                   <button class="btn btn-default" ng-click="openSearchToDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                              </div>
                           </div>
                           <div class = "col-md-2"> 
                               <input type = "button" class = "btn btn-success buttonRadius0" value = "Get Report" ng-click = "getReportSMS(reportSMS)" ng-disabled = "!reportSMS.searchFromDate || !reportSMS.searchtoDate">
                           </div>       
-->
                         <!--    <div class = "col-md-3">
                            	<select class = "select_reports form-control"
                                        ng-model="showRecords" 
                						ng-options="pagination.text for pagination in paginations track by pagination.value"
                						ng-change = "setLimit(showRecords)">
      						  		<option value="">-- All Records --</option>
    							</select>
    						</div> -->
                           <div class = "col-md-2"> 
                               <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterSMSShow'>
                           </div>         
                               <div class = "col-md-4"></div>
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                           </div>   </div>                     
                        <!-- Table-->                           
                           <div class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotSMSResult">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid" ng-show = "reportsSMSData.length>0">
                                <thead class ="tableHeading">
                                    <tr>
                                        <th colspan = "11" class ="tableHeadding_km" style = "background-color:rgba(0, 0, 0, 0.46); color: white">SMS Report for Date : <span>{{searchFromDateSMS | date : 'longDate'}} - {{searchToDateSMS | date : 'longDate'}}</span>
                                        </th>
                                    </tr>
                                    <tr>                                                                          
                                      <th>Travelled Date</th>
                                      <th>Employee Id</th>
                                      <th>Employee Number</th>
<!--                                      <th>Address</th>-->
                                      <th>Shift Time</th>
                                      <th>Route Name</th>
                                       <th>Trip Type</th>
                                      <th>Allocation Msg</th>                                    
                                      <th>ETA Msg</th>
                                       <th>Cab Delay Msg</th>
                                      <th>Reached Msg</th>    
                                     
                                        <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                            <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr class = 'tabletdCenter' ng-repeat="report in reportsSMSData | filter:efmfilter.filterSMSShow">                                  
                                       <td class = "col-md-1">{{report.travelledDate}}</td>           
                                       <td class = "col-md-1">{{report.employeeId}}</td>
                                       <td class = "col-md-1">{{report.employeeNumber}}</td>
<!--                                       <td  class = "col-md-1">{{report.employeeAddress}}</td>-->
                                       <td class = "col-md-1">{{report.shiftTime}}</td> 
                                       <td class = "col-md-1">{{report.routeName}}</td>
                                       <td class = "col-md-1">{{report.tripType}}</td>                                     
                                       <td class = "col-md-1">{{report.allocationMsgDeliveryDate}}</td>
                                       <td class = "col-md-1">{{report.eat15MinuteMsgDeliveryDate}}</td>
                                      <td class = "col-md-1">{{report.cabDelayMsgDeliveryDate}}</td>                                       
                                       <td class = "col-md-1">{{report.reachedMsgDeliveryDate}}</td>                              
                                       <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table>                                
                            <div class = 'row noData' ng-hide = "reportsSMSData.length>0" >
                                <div class = 'col-md-12'>
                                    <div>No Data Found. Please select different dates.</div>
                                </div>
                            </div>
                          </div> 
                        </div>
                    </tab>
                    <!-- End of Eight Tab -->
            
                    <!-- Nineth Tab -->
<!--
                    <tab ng-click = "setDates('seatUtilization')">
                    <tab-heading>Seat Utilization</tab-heading>
                       <div class = "kmTabContent"><div class = "searchDIVREPORT row marginRow">
                           
                            <div class = "col-md-3">
                            	<select class = "select_reports form-control"
                                        ng-model="showRecords" 
                						ng-options="pagination.text for pagination in paginations track by pagination.value"
                						ng-change = "setLimit(showRecords)">
      						  		<option value="">-- All Records --</option>
    							</select>
    						</div>
                           <div class = "col-md-3"> 
                               <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'searchSMSShow'>
                           </div>                              
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                           </div>   </div>                      
                           <div class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotSMSResult">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Travelled Date</th>
                                      <th>Employee Id</th>
                                      <th>Route Name</th>
                                      <th>Shift time</th>
                                      <th>Address</th>
                                      <th>Trip Type</th>
                                      <th>Seat Utilized</th>
                                      <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                        <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                      </th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat="report in reportsNoShowData | filter:searchNoShow">
                                       <td class = "col-md-2">{{report.travelledDate}}</td>
                                       <td class = "col-md-2">{{report.employeeId}}</td>
                                       <td class = "col-md-2">{{report.routeName}}</td>
                                       <td class = "col-md-2">{{report.shiftTime}}</td>
                                       <td class = "col-md-2">{{report.employeeAddress}}</td>
                                       <td class = "col-md-2">{{report.tripType}}</td>
                                       <td></td>
                                       <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
-->
                    <!-- End of Nineth Tab -->
                    
                    <!-- Tenth Tab -->
                     <tab ng-click = "setDates('escortReport')">
                    <tab-heading>Escort</tab-heading>
                       <div class = "kmTabContent"><div class = "searchDIVREPORT row marginRow">                          
                           <div class = "col-md-2"> 
                               <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterEscort'>
                           </div> 
                           <div class = "col-md-4"> </div>
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                           </div>   </div>                     
                                                   
                           <div id = "exportableEscortReport" class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotEscortResult">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Date</th>
                                      <th>Vehicle Number</th>
                                      <th>Driver Name</th>
                                      <th>Last Employee id/Name</th>
                                      <th>ShiftTime</th>
                                      <th>Escort id</th>
                                      <th>Escort Name</th>
                                      <th>Time of Drop/Pickup</th>
                                      <th>Route</th>
                                      <th>Trip Type</th>
                                        <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                            <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat="report in reportEscortData | filter:efmfilter.filterEscort">
                                       <td class = "col-md-1">{{report.tripCompleteDate}}</td>
                                       <td class = "col-md-1">{{report.vehicleNumber}}</td>
                                       <td class = "col-md-1">{{report.driverName}}</td>                                      
                                       <td class = "col-md-1">{{report.employeeName}}/{{report.employeeId}}</td>
                                       <td class = "col-md-1">{{report.shiftTime}}</td>
                                       <td class = "col-md-1">{{report.escortId}}</td>
                                       <td class = "col-md-2">{{report.escortName}}</td>
                                       <td class = "col-md-1">{{report.pickOrDropTime}}</td>
                                       <td class = "col-md-1">{{report.routeName}}</td>
                                       <td class = "col-md-1">{{report.tripType}}</td>
                                       <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
                    <!-- End of Tenth Tab -->
                    
                    <!-- Eleventh Tab -->
                     <tab ng-click = "setDates('vehicleDriverAttendence')">
                      <tab-heading>Vehicle & Driver Attendance</tab-heading>
                       <div class = "kmTabContent"><div class = "searchDIVREPORT row marginRow">
                           
                           <div class = "col-md-2"> 
                               <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterVDAttendance'>
                           </div>                              
                           <div class = "col-md-4"></div>                             
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                           </div>   </div>                     
                                                   
                           <div id = 'exportTableVDAttendance' class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotVDAttendanceResult">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                        <th colspan = '7' style = "background-color:black; color: white">
                                            Vehicle And Driver Attendance</br> 
                                            <span>{{searchFromDatesVDA | date : 'longDate'}} - {{searchToDatesVDA | date : 'longDate'}}</span>
                                        </th>
                                
                                    </tr>
                                    <tr>
                                      <th>Date</th>
                                      <th>Vendor Name</th>
                                      <th>Vehicle Number</th>
                                      <th>Driver Id</th>
                                      <th>Driver Name</th>
                                      <th>Status</th>
                                      <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                        <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                      </th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat="report in reportsVDAttendanceData | filter:efmfilter.filterVDAttendance">
                                       <td class = "col-md-2">{{report.attendanceDate}}</td>
                                       <td class = "col-md-3">{{report.vendorName}}</td>
                                       <td class = "col-md-2">{{report.vehicleNumber}}</td>
                                       <td class = "col-md-1">{{report.driverId}}</td>
                                       <td class = "col-md-3">{{report.driverName}}</td>
                                       <td class = "col-md-1">{{report.status}}</td>
                                       <td class = "col-md-1"></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab> 
                    <!-- End of Tab -->
                    
                    <!-- Driver working hours Tab -->
                     <tab ng-click = "setDates('driverWorkingHours')">
                      <tab-heading>Driver Working Hours</tab-heading>
                       <div class = "kmTabContent"><div class = "searchDIVREPORT row marginRow">
                           
                           <div class = "col-md-2"> 
                               <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterdriverWH'>
                           </div>                              
                           <div class = "col-md-4"></div>                             
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                           </div>   </div>                     
                                                   
                           <div id = 'exportTableDriverWH' class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotDriverWHResult">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                        <th colspan = '9' style = "background-color:black; color: white">
                                            Driver Working Hours</br> 
                                            <span>{{searchFromDatesDWH | date : 'longDate'}} - {{searchFromDatesDWH | date : 'longDate'}}</span>
                                        </th>
                                
                                    </tr>
                                    <tr>
                                      <th>Date</th>
                                      <th>Vendor Name</th>
                                      <th>Vehicle Number</th>
                                      <th>Driver Id</th>
                                      <th>Driver Name</th>
                                      <th>Login time</th>
                                      <th>Logout time</th>
                                      <th>Total Hours</th>
                                      <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                        <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                      </th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat="report in reportsDriverWHData | filter:efmfilter.filterdriverWH">
                                       <td class = "col-md-1">{{report.date}}</td>
                                       <td class = "col-md-2">{{report.vendorName}}</td>
                                       <td class = "col-md-2">{{report.vehicleNumber}}</td>
                                       <td class = "col-md-1">{{report.driverId}}</td>
                                       <td class = "col-md-2">{{report.driverName}}</td>
                                       <td class = "col-md-1">{{report.loginTime}}</td>
                                       <td class = "col-md-1">{{report.logOutTime}}</td>
                                       <td class = "col-md-1">{{report.totalWorkingHours}}</td>
                                       <td class = "col-md-1"></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab> 
                    <!-- End of Tab -->
                    
                    <!-- Driver Driving hours Tab -->
                     <tab ng-click = "setDates('driverDrivingHours')">
                      <tab-heading>Driver Driving Hours</tab-heading>
                       <div class = "kmTabContent"><div class = "searchDIVREPORT row marginRow">
                           
                           <div class = "col-md-2"> 
                               <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterdriverDH'>
                           </div>                              
                           <div class = "col-md-4"></div>                             
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                           </div>   </div>                     
                                                   
                           <div id = 'exportTableDriverDH' class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotDriverDHResult">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid table-bordered">
                                <thead class ="tableHeading">
                                    <tr>
                                        <th colspan = '14' style = "background-color:black; color: white">
                                            Driver Driving Hours</br> 
                                            <span>{{searchFromDatesDDH | date : 'longDate'}} - {{searchFromDatesDDH | date : 'longDate'}}</span>
                                        </th>
                                
                                    </tr>
                                    <tr>
                                      <th>Date</th>
                                      <th>Vendor Name</th>
                                      <th>Vehicle Number</th>
                                      <th>Driver Id</th>
                                      <th>Driver Name</th>
                                      <th>Trip Type</th>
                                      <th>Route Name</th>
                                      <th>Trip start time</th>
                                      <th>Trip End time</th>
                                      <th>Trip Details</th>
                                      <th>Driving hrs/trip</th>
                                      <th>Total driving hrs</th>
                                      <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                        <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                      </th>
                                    </tr>
                                </thead>
                                <tbody ng-repeat="report in reportsDriverDHData">
                                   <tr>
                                       <td class = "col-md-1">{{report.tripsDetails[0].date}}</td>
                                       <td class = "col-md-1">{{report.tripsDetails[0].vendorName}}</td>
                                       <td class = "col-md-2">{{report.tripsDetails[0].vehicleNumber}}</td>
                                       <td class = "col-md-1">{{report.tripsDetails[0].driverId}}</td>
                                       <td class = "col-md-1">{{report.tripsDetails[0].driverName}}</td>
                                       <td class = "col-md-1">{{report.tripsDetails[0].tripType}}</td>
                                       <td class = "col-md-1">{{report.tripsDetails[0].routeName}}</td>
                                       <td class = "col-md-1">{{report.tripsDetails[0].tripStartDate}}</td>
                                       <td class = "col-md-1">{{report.tripsDetails[0].tripCompleteDate}}</td>
                                       <td class = "col-md-1">**123**</td>
                                       <td class = "col-md-1">**123**</td>
                                       <td rowspan = "{{report.tripsDetails.length}}" class = "col-md-1">{{report.totalDrivingHours}}</td>
                                       <td></td>
                                   </tr> 
		                           <tr ng-repeat="dh in report.tripsDetails.slice(1,report.tripsDetails.length) | filter:efmfilter.filterdriverDH">
                                       <td class = "col-md-1">{{dh.date}}</td>
                                       <td class = "col-md-1">{{dh.vendorName}}</td>
                                       <td class = "col-md-2">{{dh.vehicleNumber}}</td>
                                       <td class = "col-md-1">{{dh.driverId}}</td>
                                       <td class = "col-md-1">{{dh.driverName}}</td>
                                       <td class = "col-md-1">{{dh.tripType}}</td>
                                       <td class = "col-md-1">{{dh.routeName}}</td>
                                       <td class = "col-md-1">{{dh.tripStartDate}}</td>
                                       <td class = "col-md-1">{{dh.tripCompleteDate}}</td>
                                       <td class = "col-md-1">**123**</td>
                                       <td class = "col-md-1">**123**</td>
                                       <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab> 
                    <!-- End of Eleventh Tab -->
                    
                    
                    <!-- Twelveth Tab -->
                     <tab ng-click = "setDates('speed')">
                    <tab-heading>Speed</tab-heading>
                       <div class = "kmTabContent"><div class = "searchDIVREPORT row marginRow">
                           
                            <div class = "col-md-2">
                               <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterSpeed'>
    						</div>
                            <div class = "col-md-2">                                
                               <select ng-model="searchSpeed.SpeedVendors"
                                           class="form-control" 
                                           ng-options="SpeedAllVendor.name for SpeedAllVendor in SpeedAllVendors track by SpeedAllVendor.Id"
                                           ng-change = "setVendorSpeed(searchSpeed.SpeedVendors)"
                                           >
<!--                                     <option value="">-- Select Vendors --</option>-->
                                  </select>
                            </div>     
                            <div class = "col-md-2">
                                <select class = "select_reports_VSelection marginBottom10 form-control"
                                        ng-model="searchSpeed.VSelection" 
                						ng-options="vendorVehicle.name for vendorVehicle in vendorVehicles_SpeedReport track by vendorVehicle.Id"
                						ng-change = "setvendorVehicle(searchSpeed.VSelection)"
                                        required>
<!--      						  		<option value="">-- Select --</option>-->
    							</select> 
                            </div>                             
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                            </div>   
                           </div>                       
                           <div id = 'exportTableSpeed' class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotSpeedResult">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid">
                                    <thead class ="tableHeading">
                                        <tr>
                                            <th colspan = '14' style = "background-color:black; color: white">
                                                Speed Report</br> 
                                                <span>{{searchFromDatesSpeed | date : 'longDate'}} - {{searchFromDatesSpeed | date : 'longDate'}}</span>
                                            </th>                                
                                        </tr>
                                        <tr>
                                          <th>Date</th>
                                          <th>Vendor Name</th>
                                          <th>Driver Id</th>
                                          <th>Driver Name</th>
                                          <th>Vehicle Number</th>
                                          <th>Area</th>
                                          <th>Speed</th>
                                            <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                        <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                       <tr class = "reportSum" ng-repeat="speed in reportsSpeedData| filter:efmfilter.filterSpeed">
                                           <td>{{speed.dateTime}}</td>
                                           <td>{{speed.vendorName}}</td>
                                           <td>{{speed.driverId}}</td>
                                           <td>{{speed.driverName}}</td>
                                           <td>{{speed.vehicleNumber}}</td>
                                           <td>{{speed.area}}</td>
                                           <td>{{speed.speed}}</td>
                                           <td></td>
                                       </tr> 
                                     </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab> 
                    <!-- End of Twelveth Tab -->
                    
                    <!-- Thirteenth Tab -->
                     <tab ng-click = "setDates('routeWiseTravel')">
                    <tab-heading>Route Wise Travel Time</tab-heading>
                       <div class = "kmTabContent"><div class = "searchDIVREPORT row marginRow">
                           <div class = "col-md-2"> 
                               <input type = 'text' placeholder="Filter" class = 'form-control floatRight' ng-model = 'efmfilter.filterRWT'>
                           </div>            
                           <div class = "col-md-2">                                
                               <select ng-model="searchRWT.type"
                                       class="form-control" 
                                       ng-options="RWTTripType.text for RWTTripType in RWTTripTypes track by RWTTripType.value"
                                       ng-change = "setTripTypeRWT(searchRWT.type)">
                              </select>
                           </div>            
                           <div class = "col-md-2"> </div>             
                            <div class = "col-md-6">
                                <div class = "calenderMainDiv floatRight pointer" 
                                     popover-template="partials/popovers/calenderReport.jsp"
                                     popover-placement="bottom"
                                     popover-title = "Get Report"
                                     popover-trigger ="click">                           
                                        <span><i class = "icon-calendar"></i></span>
                                        <span>{{fromDate | date : 'longDate'}} - {{toDate | date : 'longDate'}}</span>
                                        <span><img ng-src = 'images/spiffygif_22x22.gif' ng-show = 'isProcessing'/></span>
                                </div>                                  
                           </div>   </div>                     
                                                   
                           <div id = 'exportTableRWT' class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotRWTResult">
                            <table class = "reportTable table reportTable_km table-responsive container-fluid">
                                <thead class ="tableHeading"> 
                                    <tr>
                                        <th colspan = '10' style = "background-color:black; color: white">
                                            Route Wise Travel Time - {{RWTTripType}} </br> 
                                            <span>{{searchFromDatesRWT | date : 'longDate'}} - {{searchToDatesRWT | date : 'longDate'}}</span>
                                        </th>
                                
                                    </tr>
                                   <tr col-span = "10" class ="tableHeadding_km">Route Wise Travel Time</tr>
                                    <tr>
                                      <th>Travelled Date</th>
                                      <th>Vehicle Number</th>
                                      <th>Driver Id</th>
                                      <th>Driver name</th>
                                      <th>Vendor Name</th>
                                      <th>Route Name/Id</th>
                                      <th>Start time</th>
                                      <th>End time</th>
                                      <th>Total travel time</th>
                                        <th><button class = "btn btn-sm btn-success form-control excelExportButton" ng-click = "saveInExcel()">
                                            <i class = "icon-download-alt"></i><span class = "marginLeft5">Excel</span></button>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
		                            <tr ng-repeat="report in reportsRouteWiseTravelData | filter:efmfilter.filterRWT">
                                    <td class = "col-md-1">{{report.tripAssignDate}}</td>
                                    <td class = "col-md-1">{{report.vehicleNumber}}</td>
                                    <td class = "col-md-1">{{report.driverId}}</td>
                                    <td class = "col-md-2">{{report.driverName}}</td>
                                    <td class = "col-md-2">{{report.vendorName}}</td>
                                    <td class = "col-md-2">{{report.routeName}}</td>
                                    <td class = "col-md-1">{{report.tripStartDate}}</td>
                                    <td class = "col-md-1">{{report.tripCompleteDate}}</td>
                                    <td class = "col-md-1">{{report.totalRouteTravelledTime}}</td>
                                    <td></td>
                                    <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab> 
                    <!-- End of Thirteenth Tab -->
            
                    <!-- Nineth Tab -->
<!--
                    <tab ng-click = "initDate()">
                    <tab-heading>Vehicle Complaince report</tab-heading>
                       <div class = "kmTabContent row">
                            <div class = "col-md-2">                                
                              <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "From" 
                                     ng-model = "reportVehCompliance.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchFromDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                   <span class="input-group-btn">
                                    <button class="btn btn-default" ng-click="openSearchFromDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                                </div> 
                            </div>
                           <div class = "col-md-2">                               
                             <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "To" 
                                     ng-model = "reportVehCompliance.searchtoDate"
                                     ng-change = "searchPost(searchFromDate,searchtoDate)"
                                     min-date = "reportKM.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchToDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                  <span class="input-group-btn">
                                   <button class="btn btn-default" ng-click="openSearchToDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                              </div>
                           </div>
                           <div class = "col-md-2"> 
                               <input type = "button" class = "btn btn-success buttonRadius0" value = "Get Report" 
                                      ng-click = "getReportVehCompliance(reportVehCompliance)" 
                                      ng-disabled = "!reportVehCompliance.searchFromDate || !reportVehCompliance.searchtoDate">
                           </div>                           
-->
                        <!-- Table-->                           
<!--
                           <div class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotVehComplianceResult">
                            <table class = "reportTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
Vehicle No	Vendor Name	Insurance	Fitness	Pollution 	Tax

                                    <tr>
                                      <th>Vehicle No</th>
                                      <th>Vendor Name</th>
                                      <th>Insurance</th>
                                      <th>Fitness</th>
                                      <th>Pollution</th>
                                      <th>Tax</th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat="report in reportsVehComplianceData">
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
-->
                    <!-- End of Ninth Tab -->
            
                    <!-- Tenth Tab -->
<!--
                    <tab ng-click = "initDate()">
                    <tab-heading>Driver Complaince report</tab-heading>
                       <div class = "kmTabContent row">
                            <div class = "col-md-2">                                
                              <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "From" 
                                     ng-model = "reportDriverCompliance.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchFromDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                   <span class="input-group-btn">
                                    <button class="btn btn-default" ng-click="openSearchFromDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                                </div> 
                            </div>
                           <div class = "col-md-2">                               
                             <div class = "input-group marginBottom10 calendarInput">  
                              <input class="form-control floatLeft" 
                                     placeholder = "To" 
                                     ng-model = "reportDriverCompliance.searchtoDate"
                                     ng-change = "searchPost(searchFromDate,searchtoDate)"
                                     min-date = "reportKM.searchFromDate"
                                     datepicker-popup = '{{format}}'
                                     is-open="datePicker.searchToDate" 
                                     show-button-bar = false
                                     show-weeks=false
                                     datepicker-options = 'dateOptions'
                                     required>
                                  <span class="input-group-btn">
                                   <button class="btn btn-default" ng-click="openSearchToDateCal($event)">
                                    <i class = "icon-calendar calInputIcon"></i></button></span>
                              </div>
                           </div>
                           <div class = "col-md-2"> 
                               <input type = "button" class = "btn btn-success buttonRadius0" value = "Get Report" 
                                      ng-click = "getReportDriverCompliance(reportDriverCompliance)" 
                                      ng-disabled = "!reportDriverCompliance.searchFromDate || !reportDriverCompliance.searchtoDate">
                           </div>                           
-->
                        <!-- Table-->                           
<!--
                           <div class = "col-md-12 col-xs-12 tableWrapper_report" ng-show = "gotDriverComplianceResult">
                            <table class = "reportTable table table-responsive container-fluid">
                                <thead class ="tableHeading">

                                    <tr>
                                      <th>Driver Name</th>
                                      <th>Vendor Name</th>
                                      <th>License</th>
                                      <th>Anticedent</th>
                                      <th>Police</th>
                                      <th>Medical Verification</th>
                                      <th>DD training</th>
                                    </tr>
                                </thead>
                                <tbody>
		                           <tr ng-repeat="report in reportsDriverComplianceData">
                                       <td></td>
                                       <td></td>
                                       <td></td>
                                   </tr> 
		                         </tbody>                                
                            </table> 
                          </div> 
                        </div>
                    </tab>
-->
                    <!-- End of Tenth Tab -->
                   </tabset>
                </div>
                <div class="clearfix"></div>
                <br>
            </div>
            
            
        </div>
        
    </div>
</div>
