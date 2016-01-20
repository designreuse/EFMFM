<div class = "empRequestDetailsTemplate container-fluid">
    <div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-12 col-xs-12">Employee Requests</span> 
                      
            <div class = "col-md-12 col-xs-12 mainTabDiv_empRequestDetail">

            <!-- /*START OF WRAPPER1 = TODAY'S REQUEST */ -->
               <div class = "wrapper1">             
                
                <tabset class="tabset subTab_empRequestDetail">
                  <!-- FIRST TAB -->
                  <tab ng-if = "!adminRole" ng-click = "getTodayRequestDetails()">
                    <tab-heading>Today's Request</tab-heading>
                        <div class = "empRequestDetailsTabContent row">
                           <table class = "empRequestDetailsTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Request ID</th>
                                      <th>Shift Time</th>
                                      <th>Request Date</th>
                                      <th>Drop Location</th>
                                      <th>Trip Type</th>
                                      <th>Request Status</th>
                                   </tr> 
                                </thead>
                                <tbody ng-show = "empRequestDetailsData.length==0">
                                    <tr>
                                        <td colspan = '5'>
                                            <div class = "noData">There are no Re-schedule Request</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "empRequestDetailsData.length>0">
		                           <tr ng-repeat = "request in empRequestDetailsData">
                                      <td class = "col-md-1">{{request.requestId}}</td>
		                              <td class = "col-md-2" ng-show = "!request.needReshedule">{{request.tripTime |date:'HH:mm:ss'}}</td>
                                      <td class = "col-md-2 resheduleTimeDiv_empReq" ng-show = "request.needReshedule">
                                          <input type = "button" 
                                                 class = "btn btn-info btn-xs floatLeft" 
                                                 value = "Change" 
                                                 ng-click = "adHoc()" 
                                                 ng-show = "!needAdHoc">
                                          
                                          <input type = "button" 
                                                 class = "btn btn-danger btn-xs floatLeft cancelAdHocButton" 
                                                 value = "Cancel" 
                                                 ng-click = "cancelAdHoc()" 
                                                 ng-show = "needAdHoc">
                                      </td>
                                      <td class = "col-md-1">{{request.requestDate}}</td>
		                              <td class = "col-md-3">{{request.employeeAddress}}</td>
                                      <td class = "col-md-1">{{request.tripType}}</td>
                                      <td class = "col-md-2">{{request.requestStatus}}</td>
		                            </tr>                                    
		                         </tbody>
                            </table>
                            

 						</div>
 				  </tab>
                  <!--SECOND - (A) TAB-->
                  <!-- This Tab Will be Seen By the Employee Only -->
                  <tab ng-if = "!adminRole" ng-click = "initialzeNewCustomTime()">
                    <tab-heading>Create Request</tab-heading>
                        <div class = "empRequestDetailsTabContent row">
                            <div class = "col-md-8 col-xs-12 formWrapper">
                                <form name = "createNewRequestForm" class = "createNewRequestForm">
                                    <div class = "row createNewRequest">
                                        <div class = "col-md-6  form-group calenderRequest">
                                            <span>Start Request Date</span>
                                            <div class = "input-group calendarInput">
                                                <span class="input-group-btn">
                                                <button class="btn btn-default" ng-click="openFromDateCal($event)">
                                                    <i class = "icon-calendar calInputIcon"></i></button></span>                                         
                                                 <input class="form-control" 
                                                       ng-model = "newRequest.fromDate"
                                                       placeholder = "Start Request Date"
                                                       ng-change = "isFromDate()"
                                                       datepicker-popup = '{{format}}'
                                                       min-date = "setMinDate"
                                                       is-open="datePicker.fromDate" 
                                                       show-button-bar = false
                                                       show-weeks=false
                                                       datepicker-options = 'dateOptions'
                                                       name = "fromDate"
                                                       required
                                                       readonly
                                                       ng-class = "{error: createNewRequestForm.fromDate.$invalid && !createNewRequestForm.fromDate.$pristine}">                                                          </div>
                                        </div>
                                        <div class = "col-md-6  form-group calenderRequest">
                                            <span>End Request Date</span>
                                            <div class = "input-group calendarInput">
                                                <span class="input-group-btn">
                                                <button class="btn btn-default" ng-click="openEndDateCal($event)">
                                                    <i class = "icon-calendar calInputIcon"></i></button></span>                                         
                                                 <input class="form-control" 
                                                       ng-model = "newRequest.endDate"
                                                       placeholder = "End Request Date"
                                                       ng-change = "isFromDate()"
                                                       datepicker-popup = '{{format}}'
                                                       min-date = "newRequest.fromDate"
                                                       is-open="datePicker.endDate" 
                                                       show-button-bar = false
                                                       show-weeks=false
                                                       datepicker-options = 'dateOptions'
                                                       name = "endDate"
                                                       required
                                                       readonly
                                                       ng-class = "{error: createNewRequestForm.endDate.$invalid && !createNewRequestForm.endDate.$pristine}">                                                          </div>
                                        </div>
                                        <div class = "col-md-6 form-group">
                                            <span>Trip Type</span>
                                            <select ng-model="newRequest.tripType"
                                                    ng-options="tripType.text for tripType in tripTypes track by tripType.value"
                                                    ng-change = "setTripType(newRequest.createNewtripTime, newRequest.fromDate, newRequest.tripType)"
                                                    required>
                                                <option value="">-- Select Trip Type --</option>
                                            </select>
                                        </div>
                                        <div class = "col-md-6 form-group selectDiv2">
                                            <span> Shift Time</span><br>
                                            <div class = "row">
                                              <label class = "radioLabel floatLeft col-md-6 col-xs-12">                                                
                                                <input type="radio" 
                                                       class = "floatLeft"
                                                       ng-model="shiftTime"
                                                       ng-disabled = "isRadioDisable()"
                                                       value="preDefineShiftTime" 
                                                       ng-change = "selectShiftTimeRadio(shiftTime)"
                                                       required>
                                                <select ng-model="newRequest.createNewtripTime"
                                                        ng-options="tripTimes for tripTimes in tripTimes"
                                                        class = "floatLeft selectNewTripTime"                                                        
                                                        ng-disabled = "isShiftTimeDisable">
                                                    <option value="">-- Select Shift Time --</option>
                                                </select>
                                               </label>
                                            <label class = "radioLabel customNewTimePicker col-md-6  col-xs-12">
                                                <input type="radio" 
                                                       ng-model="shiftTime" 
                                                       class = "timepickerRadioButton floatLeft"
                                                       value="ShiftTimeCustom" 
                                                       ng-disabled = "isRadioDisable()"
                                                       ng-change = "selectShiftTimeRadio2(shiftTime)"
                                                       required>
                                                <timepicker ng-model="newRequest.createNewAdHocTime" 
                                                          hour-step="hstep" 
                                                          minute-step="mstep" 
                                                          show-meridian="ismeridian" 
                                                          readonly-input = 'true'
                                                          class = "timepicker2_empReq floatLeft">
                                                </timepicker>
                                            </label> </div>                                           
                                        </div>
                                        <div class = "col-md-4 col-md-offset-4">
                                            <input type = "button" 
                                                   class = "btn btn-success creatNewReqButton"
                                                   value = "Create New Request" 
                                                   ng-click = "createNewRequest(newRequest)"
                                                   ng-disabled = "createNewRequestForm.$invalid">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class = "col-md-4"> </div>
 						</div>
 				  </tab>

                  <!--THIRD TAB -->

                  <tab ng-if = "!adminRole || adminRole" ng-click = "getRescheduleRequestDetails()">
                    <tab-heading>Re-Schedule Request</tab-heading>
                        <div class = "empRequestDetailsTabContent row">
                           <table class = "empRequestDetailsTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Request ID</th>
                                      <th>Shift Time</th>
                                      <th>Request Date</th>
                                      <th>Drop Location</th>
                                      <th>Trip Type</th>
                                      <th>Request Status</th>
                                      <th></th>
                                   </tr> 
                                </thead>
                                <tbody ng-show = "empRequestDetailsData.length==0">
                                    <tr>
                                        <td colspan = '7'>
                                            <div class = "noData">There are no Re-schedule Request</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "empRequestDetailsData.length>0">
		                           <tr ng-repeat = "request in empRequestDetailsData"
                                       ng-show = 'is24hrRequest'>
                                      <td class = "col-md-1">{{request.requestId}}</td>
		                              <td class = "col-md-2" 
                                          ng-show = "!request.needReshedule">{{request.tripTime |date:'HH:mm:ss'}}
                                       </td>
                                      <td class = "col-md-2 resheduleTimeDiv_empReq" ng-show = "request.needReshedule">
                                          <select ng-model="newTripTime" 
                                                  ng-options="tripTimes for tripTimes in tripTimes"
                                                  ng-change = "setNewTripTime(newTripTime, request)"
                                                  ng-show = "!needAdHoc"
                                                  class = "floatLeft">
                                            <option value="">- Select Shift Time -</option>
                                          </select>
                                          <timepicker ng-model="adHoctime" 
                                                      ng-change="setNewTripTime(adHoctime, request)" 
                                                      hour-step="hstep" 
                                                      minute-step="mstep" 
                                                      show-meridian="ismeridian"
                                                      ng-if = "needAdHoc"
                                                      class = "floatLeft timepicker_empReq">
                                          </timepicker>
                                          <input type = "button" 
                                                 class = "btn btn-info btn-xs floatLeft" 
                                                 value = "AD HOC" 
                                                 ng-click = "adHoc()" 
                                                 ng-if = "!needAdHoc">
                                          
                                          <input type = "button" 
                                                 class = "btn btn-danger btn-xs floatLeft cancelAdHocButton" 
                                                 value = "Cancel" 
                                                 ng-click = "cancelAdHoc()" 
                                                 ng-if = "needAdHoc">
                                      </td>
                                       
                                       <td class = "col-md-2" ng-show = "!request.needReshedule">{{request.requestDate}}</td>
                                       <td class = "col-md-2"  ng-show = "request.needReshedule">                                           
                                            <div class = "input-group calendarInput">
                                                <span class="input-group-btn">
                                                <button class="btn btn-default" ng-click="openFromDateCal($event)">
                                                    <i class = "icon-calendar calInputIcon"></i></button></span>                                       
                                                 <input ng-model = "fromDate"
                                                       class="form-control" 
                                                       placeholder = "Request Date"
                                                       ng-change = "isFromDate()"
                                                       datepicker-popup = '{{format}}'
                                                       min-date = "setMinDate"
                                                       is-open="datePicker.fromDate" 
                                                       show-button-bar = false
                                                       show-weeks=false
                                                       datepicker-options = 'dateOptions'
                                                       name = "fromDate"
                                                       required
                                                       readonly>                                                                                        </div>
                                           
                                          
                                       </td>
		                              <td class = "col-md-3">{{request.address}}</td>
                                      <td class = "col-md-1">{{request.tripType}}</td>
                                      <td class = "col-md-1">{{request.requestStatus}}</td>
                                      <td class = "col-md-2 requestButtonsDiv">
                                          <input type = "button" 
                                                 class = "btn btn-warning btn-xs" 
                                                 value = "Re-Schedule"
                                                 ng-click = "reSheduleRequest(request)"
                                                 ng-show = "!request.needReshedule"
                                                 ng-disabled = "saveIsClicked">
                                          <input type = "button" 
                                                 class = "btn btn-success btn-xs" 
                                                 value = "Save"
                                                 ng-click = "saveRequest(request, fromDate, $index)"
                                                 ng-show = "request.needReshedule">
                                          <input type = "button" 
                                                 class = "btn btn-danger btn-xs" 
                                                 value = "Cancel Reschedule"
                                                 ng-click = "cancelReschedule(request, $index)"
                                                 ng-show = "request.needReshedule">
                                       </td>
		                            </tr>                                    
		                         </tbody>
                            </table>
                            

 						</div>
 					</tab>  

                  <!--SECOND - (B) TAB-->
                  <!-- This Tab Will be Seen By the ADMIN Only -->
                  <tab ng-if = "adminRole" ng-click = "initialzeNewCustomTimeByAdmin()">
                    <tab-heading>Create Request</tab-heading>
                        <div class = "empRequestDetailsTabContent row">
                            <div class = "col-md-8 col-xs-12 formWrapper">
                                <form name = "createNewRequestByAdminForm" class = "createNewRequestByAdminForm">
                                    <div class = "row createNewRequest">
                                        <div class = "col-md-6  form-group">
                                            <span>Request Type</span>
                                            <select ng-model="newRequestByAdmin.requestType" 
                                                    class="form-control"
                                                    ng-options="requestType.text for requestType in requestTypes track by requestType.value"
                                                    ng-change = "setRequestType(newRequestByAdmin.requestType)"
                                                    required>
                                                <option value="">-- Select Request Type --</option>
                                            </select>                                            
                                        </div>
                                        <div class = "col-md-6 form-group">
                                            <span>Empployee ID</span>
                                            <input ng-model="newRequestByAdmin.id"  
                                                   type = "text"
                                                   class="form-control" 
                                                   placeholder = "Employee ID"
                                                   name = "empid"
                                                   ng-maxlength="10"
                                                   ng-blur = "checkIDExist()"
                                                   ng-change = "idIsEntered()"
                                                   ng-required="requestFor == 'GUEST'"
                                                   ng-disabled = "createRequestRole_ADMIN && !createRequestRole_EMPLOYEE && !createRequestRole_GUEST">
                                        </div>
                                        <div class = "col-md-6 form-group">
                                            <span>Name</span>
                                            <input ng-model="newRequestByAdmin.name" 
                                                   type = "text" 
                                                   class="form-control" 
                                                   placeholder = "Employee Name"
                                                   ng-pattern = 'regExName'
                                                   ng-maxlength="20"
                                                   maxlength= '20'
                                                   ng-required="requestFor == 'GUEST'"
                                                   ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                        </div>
                                        <div class = "col-md-6 form-group">
                                            <span>Email</span>
                                            <input ng-model="newRequestByAdmin.email" 
                                                   type = "email" 
                                                   class="form-control" 
                                                   placeholder = "Employee Email"
                                                   ng-required="requestFor == 'GUEST'"
                                                   ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                        </div>
                                        <div class = "col-md-6 form-group">
                                            <span>Guest Contact Number</span>
                                            <div class = "">
                                                <!-- <select ng-model="newRequestByAdmin.areaCode" 
                                                        class="floatLeft form-control areaCode_createRequest"
                                                        ng-options="areaCode.text for areaCode in areaCodes track by areaCode.value"
                                                        ng-change = "setAreaCode(newRequestByAdmin.areaCode)"
                                                        ng-required="requestFor == 'GUEST'"
                                                        ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                                    <option value="">-Area Code-</option>
                                                </select>    -->              
                                                <input ng-model="newRequestByAdmin.contact" 
                                                       type = "text" 
                                                       ng-pattern = "IntegerNumber"
                                                       ng-minlength = '11'
                                                       ng-maxlength = "15"
                                                       maxlength= '15'
                                                       class="floatLeft form-control contact_createRequest" 
                                                       placeholder = "Employee Contact Number"
                                                       ng-required="requestFor == 'GUEST'"
                                                       ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                                
                                            </div>
                                        </div>   
                                        <div class = "col-md-6 form-group">
                                            <span>Host Contact Number</span>
                                            <div class = "">
                                                <!-- <select ng-model="newRequestByAdmin.areaCode2" 
                                                        class="floatLeft form-control areaCode_createRequest"
                                                        ng-options="areaCode.text for areaCode in areaCodes track by areaCode.value"
                                                        ng-change = "setAreaCode2(newRequestByAdmin.areaCode2)"
                                                        ng-required="requestFor == 'GUEST'"
                                                        ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                                    <option value="">-Area Code-</option>
                                                </select>     -->             
                                                <input ng-model="newRequestByAdmin.contact2" 
                                                       type = "text" 
                                                       ng-pattern = "IntegerNumber"
                                                       ng-minlength = '10'
                                                       ng-maxlength = "15"
                                                       maxlength= '15'
                                                       class="floatLeft form-control contact_createRequest" 
                                                       placeholder = "Host Contact Number(Mobile)"
                                                       ng-required="requestFor == 'GUEST'"
                                                       ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                                
                                            </div>
                                        </div>                                      
                                        <div class = "col-md-6 form-group">
                                            <span>Trip Type</span>
                                            <select ng-model="newRequestByAdmin.tripType" 
                                                    ng-options="tripType.text for tripType in tripTypes track by tripType.value"
                                                    ng-change = "setTripType(newRequestByAdmin.createNewtripTime, newRequestByAdmin.fromDate, newRequestByAdmin.tripType)"
                                                    required>
                                                <option value="">-- Select Trip Type --</option>
                                            </select>
                                        </div>                                        
                                        <div class = "col-md-6 form-group">
                                            <span>Gender</span>
                                            <select ng-model="newRequestByAdmin.gender" 
                                                    class="floatLeft form-control areaCode_createRequest"
                                                    ng-options="gender.text for gender in genders track by gender.value"
                                                    ng-change = "setGender(newRequestByAdmin.gender)"
                                                    ng-required="requestFor == 'GUEST'"
                                                    ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                                <option value="">-- Select Gender --</option>
                                            </select>
                                        </div>
                                        <div class = "col-md-6 form-group">
                                            <span>Pick/Drop Location</span>
                                            <div class = "input-group calendarInput"> 
                                                <span class="input-group-btn">
                                                <button class="btn btn-default" 
                                                        ng-click="openMap('lg')"                                                        
                                                        ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                                    <i class = "icon-map-marker mapMarkerIcon"></i></button></span> 
                                            <input ng-model="newRequestByAdmin.location"
                                                   id = "location"
                                                   type = "text" 
                                                   class="noPointer form-control" 
                                                   placeholder = "Location"   
                                                   ng-required="requestFor == 'GUEST'"
                                                   ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                            </div>
                                        </div>
                                        <div class = "col-md-6 form-group">
                                            <span>SMS Triggered Location</span>
                                            <div class = "input-group calendarInput"> 
                                                <span class="input-group-btn">
                                                <button class="btn btn-default" 
                                                        ng-click="openMap2('lg')"                                                        
                                                        ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                                    <i class = "icon-map-marker mapMarkerIcon"></i></button></span> 
                                            <input ng-model="newRequestByAdmin.location2"
                                                   id = "location2"
                                                   type = "text" 
                                                   class="noPointer form-control" 
                                                   placeholder = "Location"   
                                                   ng-required="requestFor == 'GUEST'"
                                                   ng-disabled = "createRequestRole_ADMIN && createRequestRole_EMPLOYEE || !createRequestRole_GUEST">
                                            </div>
                                        </div> 
                                        <div class = "col-md-6 form-group">
                                            <span>Start Request Date</span>
                                            <div class = "input-group calendarInput">
                                                <span class="input-group-btn">
                                                <button class="btn btn-default" ng-click="openFromDateCal($event)">
                                                    <i class = "icon-calendar calInputIcon"></i></button></span>                                         
                                                 <input ng-model = "newRequestByAdmin.fromDate"
                                                       class="form-control" 
                                                       placeholder = "Start Request Date"
                                                       ng-change = "isFromDate()"
                                                       datepicker-popup = '{{format}}'
                                                       min-date = "setMinDate"
                                                       is-open="datePicker.fromDate" 
                                                       show-button-bar = false
                                                       show-weeks=false
                                                       datepicker-options = 'dateOptions'
                                                       name = "fromDate"
                                                       required
                                                       readonly>                                                          </div>
                                        </div> 
                                        <div class = "col-md-6  form-group">
                                            <span>End Request Date</span>
                                            <div class = "input-group calendarInput">
                                                <span class="input-group-btn">
                                                <button class="btn btn-default" ng-click="openEndDateCal($event)">
                                                    <i class = "icon-calendar calInputIcon"></i></button></span>                                         
                                                 <input class="form-control" 
                                                       ng-model = "newRequestByAdmin.endDate"
                                                       placeholder = "End Request Date"
                                                       datepicker-popup = '{{format}}'
                                                       min-date = "newRequestByAdmin.fromDate"
                                                       is-open="datePicker.endDate" 
                                                       show-button-bar = false
                                                       show-weeks=false
                                                       datepicker-options = 'dateOptions'
                                                       name = "endDate"
                                                       required
                                                       readonly
                                                       ng-class = "{error: createNewRequestForm.endDate.$invalid && !createNewRequestForm.endDate.$pristine}">                                                          </div>
                                        </div> 
                                        <div class = "col-md-6 form-group">
                                            <span> Shift Time</span><br>
                                            <div class = "row">
                                              <label class = "radioLabel floatLeft col-md-6 col-xs-12">                                                
                                                <input type="radio" 
                                                       class = "floatLeft"
                                                       ng-model="shiftTime"
                                                       ng-disabled = "isRadioDisable()"
                                                       value="preDefineShiftTime" 
                                                       ng-change = "selectShiftTimeRadio(shiftTime)"
                                                       required>
                                                <select ng-model="newRequestByAdmin.createNewtripTime"
                                                        ng-options="tripTime for tripTime in tripTimes"
                                                        class = "floatLeft selectNewTripTime"  
                                                        ng-disabled = "isShiftTimeDisable"
                                                        ng-required = '!isShiftTimeDisable'>
                                                    <option value="">Select Shift Time</option>
                                                </select>
                                               </label>
                                            <label class = "radioLabel customNewTimePicker col-md-6  col-xs-12">
                                                <input type="radio" 
                                                       ng-model="shiftTime" 
                                                       class = "timepickerRadioButton floatLeft"
                                                       value="ShiftTimeCustom" 
                                                       ng-disabled = "isRadioDisable()"
                                                       ng-change = "selectShiftTimeRadio2(shiftTime)"
                                                       required>
                                                <timepicker ng-model="newRequestByAdmin.createNewAdHocTime" 
                                                          hour-step="hstep" 
                                                          minute-step="mstep" 
                                                          show-meridian="ismeridian" 
                                                          readonly-input = 'true'
                                                          class = "timepicker2_empReq floatLeft">
                                                </timepicker>
                                            </label> </div>                                           
                                        </div> 
                                        <div class = "col-md-4 col-md-offset-4">
                                            <input type = "button" 
                                                   class = "btn btn-success creatNewReqButton"
                                                   value = "Create New Request" 
                                                   ng-click = "createNewRequestByAdmin(newRequestByAdmin)"
                                                   ng-disabled = "createNewRequestByAdminForm.$invalid">
                                        </div> 
                                    </div>
                                </form>
                            </div>
                            <div class = "col-md-4"> </div>
 						</div>
 				  </tab> 
                  <!--FOURTH TAB -->
                  <tab ng-if = "!adminRole" ng-click = "getCancelRequestDetails()">
                    <tab-heading>Cancel Request</tab-heading>
                        <div class = "empRequestDetailsTabContent row">
                           <table class = "empRequestDetailsTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Request ID</th>
                                      <th>Shift Time</th>
                                      <th>Request Date</th>
                                      <th>Drop Location</th>
                                      <th>Trip Type</th>
                                      <th>Request Status</th>
                                      <th></th>
                                   </tr> 
                                </thead>
                                <tbody ng-show = "empRequestDetailsData.length==0">
                                    <tr>
                                        <td colspan = '6'>
                                            <div class = "noData">There are no Requests for Cancel</div>
                                        </td>
                                    </tr>
                                </tbody>
                                <tbody ng-show = "empRequestDetailsData.length>0">
		                           <tr ng-repeat = "request in empRequestDetailsData" class = "request{{request.requestId}}">
<!--		                              <td class = "col-md-1"></td>-->
                                      <td class = "col-md-1">{{request.requestId}}</td>
<!--                                      <td class = "col-md-1">employeeName</td>-->
		                              <td class = "col-md-2">{{request.tripTime |date:'HH:mm:ss'}}</td>
                                      <td class = "col-md-1">{{request.requestDate}}</td>
		                              <td class = "col-md-3">{{request.address}}</td>
                                      <td class = "col-md-1">{{request.tripType}}</td>
                                      <td class = "col-md-2">{{request.requestStatus}}</td>
                                      <td class = "col-md-3 requestButtonsDiv">
                                          <input type = "button" 
                                                 class = "btn btn-danger btn-xs" 
                                                 value = "Cancel"
                                                 ng-click = "deleteRequest(request)">
                                       </td>
		                            </tr>                                    
		                         </tbody>
                            </table>
                            

 						</div>
 					</tab>
 				</tabset>	
            </div>
        </div>
    </div>
</div>
</div>