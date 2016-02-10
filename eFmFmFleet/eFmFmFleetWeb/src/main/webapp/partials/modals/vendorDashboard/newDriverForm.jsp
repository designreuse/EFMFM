<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div>
<div class = "newDriverFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Add New Driver</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
	  <form name = "addDriverForm">  
	   <div class = "col-md-4 col-xs-12"> 
           <div>
           <label>Driver's Name</label>
            <!-- tooltip="Driver's Name"
            tooltip-placement="bottom"
            tooltip-trigger="mouseenter"> -->
                <input type="text" 
                 ng-model="newDriver.driverName" 
                 class="form-control" 
                 placeholder = "Driver Name"
                 name = "name"
                 ng-minlength="2"
                 ng-maxlength="15"
                 required
                 ng-class = "{error: addDriverForm.name.$invalid && !addDriverForm.name.$pristine}">
           </div>
           <span class = 'hintModal' ng-show = "addDriverForm.name.$error.minlength">* Name is too short</span>
       </div>       
       <div class = "col-md-4 col-xs-12">
           <div>
           <label>Driver's Mobile Number</label>
                <input type="text"
                       ng-model="newDriver.mobileNumber" 
                       class="form-control" 
                       placeholder = "Contact Number"
                       name = "contactNo"
                       required 
                       ng-pattern = 'IntegerNumber'
                       ng-minlength="10"
                       ng-maxlength="10"
                       maxlength="10"
                       ng-class = "{error: addDriverForm.contactNo.$invalid && !addDriverForm.contactNo.$pristine}">  
           </div>         
           <span class = "hintModal" ng-show="addDriverForm.contactNo.$error.maxlength">* In-valid Mobile Number</span>
       </div>
       <div class = "col-md-4 col-xs-12">
       <label>Date of Birth</label>
        <div class = "input-group calendarInput">
            <!--  tooltip="Date of Birth"
             tooltip-placement="top"
             tooltip-trigger="mouseenter"> -->
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openDobCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="newDriver.driverdob" 
                 class="form-control" 
                 placeholder = "Date of Birth" 
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.openeddob" 
                 show-button-bar = false
                 show-weeks=false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'dob'
                 ng-class = "{error: addDriverForm.dob.$invalid && !addDriverForm.dob.$pristine}">
          
           </div>
       </div>
<!--        <div class = "col-md-4 col-xs-12">
       <label>Insurance Expiry Date</label>
        <div class = "input-group calendarInput">
             tooltip="Insurance Expiry Date"
             tooltip-placement="top"
             tooltip-trigger="mouseenter">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="insurranceExpDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="newDriver.InsuranceValid" 
                 class="form-control" 
                 placeholder = "Insurance Expiry Date"
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.insurranceExpDate" 
                 show-button-bar = false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'insurDate'
                 ng-class = "{error: addDriverForm.insurDate.$invalid && !addDriverForm.insurDate.$pristine}">
            
           </div>
        
       </div> -->
       <div class = "col-md-4 col-xs-12">
       <label>License Expiry Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openExpDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span> 
          <input type="text" 
                 ng-model="newDriver.licenceValid" 
                 class="form-control" 
                 placeholder = "License Expire Date" 
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.openedExpDate"
                 show-button-bar = false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'expDate'
                 ng-class = "{error: addDriverForm.expDate.$invalid && !addDriverForm.expDate.$pristine}">
                          
             </div>          
       </div>
       <div class = "col-md-4 col-xs-12">
       <label>DDT Expiry Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openDDTCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="newDriver.driverDDT" 
                 class="form-control" 
                 placeholder = "DDT Expiry Date" 
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.openedDDT" 
                 show-button-bar = false
                 show-weeks=false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'ddt'
                 ng-class = "{error: addDriverForm.ddt.$invalid && !addDriverForm.ddt.$pristine}">          
           </div>
       </div>
       <div class = "col-md-4 col-xs-12">
       <label>Batch Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openBatchCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="newDriver.driverBatch" 
                 class="form-control" 
                 placeholder = "Batch Date" 
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.openedBatch" 
                 show-button-bar = false
                 show-weeks=false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'batch'
                 ng-class = "{error: addDriverForm.batch.$invalid && !addDriverForm.batch.$pristine}">          
           </div>
       </div>
       <div class = "col-md-4 col-xs-12">
       <label>Batch Number</label>
        <div>
          <input type="text" 
                 ng-model="newDriver.driverBatchNum" 
                 class="form-control" 
                 placeholder = "Batch Number"
                 ng-maxlength="15"
                 maxlength="15"
                 required
                 name = 'batchNum'
                 ng-class = "{error: addDriverForm.batchNum.$invalid && !addDriverForm.batchNum.$pristine}">          
           </div>
       </div>
       <div class = "col-md-4 col-xs-12">
       <label>Police Verification Expiry Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openPoliceVerificationCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="newDriver.driverPoliceVerification" 
                 class="form-control" 
                 placeholder = "Police Verification Expiry Date" 
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.openedPoliceVerification" 
                 show-button-bar = false
                 show-weeks=false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'policeVerification'
                 ng-class = "{error: addDriverForm.policeVerification.$invalid && !addDriverForm.policeVerification.$pristine}">          
           </div>
       </div>
       <div class = "col-md-4 col-xs-12">
       <label>Medical Expiry Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openMedicalExpiryCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="newDriver.driverMedicalExpiry" 
                 class="form-control" 
                 placeholder = "Medical Expiry Date" 
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.openedMedicalExpiry" 
                 show-button-bar = false
                 show-weeks=false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'medicalExpiry'
                 ng-class = "{error: addDriverForm.medicalExpiry.$invalid && !addDriverForm.medicalExpiry.$pristine}">          
           </div>
       </div>
       <div class = "col-md-4 col-xs-12">
       <label>Anti Expiry Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openAntiExpiryCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="newDriver.driverAntiExpiry" 
                 class="form-control" 
                 placeholder = "Anti Expiry Date" 
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.openedAntiExpiry" 
                 show-button-bar = false
                 show-weeks=false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'antiExpiry'
                 ng-class = "{error: addDriverForm.antiExpiry.$invalid && !addDriverForm.antiExpiry.$pristine}">          
           </div>
       </div>
       <div class = "col-md-4 col-xs-12">
       <label>Joining Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openJoiningCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="newDriver.driverJoining" 
                 class="form-control" 
                 placeholder = "Joining Date" 
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.openedJoining" 
                 show-button-bar = false
                 show-weeks=false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'joining'
                 ng-class = "{error: addDriverForm.joining.$invalid && !addDriverForm.joining.$pristine}">          
           </div>
       </div>
       <div class = "col-md-4 col-xs-12">
           <div>
           <label>License Number</label>
          <!--   tooltip="License Number"
            tooltip-placement="top"
            tooltip-trigger="mouseenter"> -->
                <input type="text" 
                       ng-model="newDriver.licenceNumber" 
                       class="form-control" 
                       placeholder = "License Number"
                       required
                       name = "lisNum"
                       ng-maxlength="15"
                       maxlength="15"
                       ng-class = "{error: addDriverForm.lisNum.$invalid && !addDriverForm.lisNum.$pristine}">
           </div>
           <span class = "hintModal" ng-show="addDriverForm.lisNum.$error.maxlength">* In-valid Liscense Number</span>
       </div>
       <div class = "col-md-8 col-xs-12">
           <div>
           <label>Address</label>
               <textarea type="text" 
                      ng-model="newDriver.driverAddress" 
                      class="form-control" 
                      placeholder = "Address"
                      name = "address"
                      required
                         ng-class = "{error: addDriverForm.address.$invalid && !addDriverForm.address.$pristine}"></textarea>
           </div>
          <span></span>
       </div>
<!--
       <div class = "col-md-12 col-xs-12">
                <div class="fileinput fileinput-new input-group vendorInputFile" data-provides="fileinput">
                    <div class="form-control  col-md-6 col-xs-6" data-trigger="fileinput">
                        <span class="fileinput-filename">Select a File</span>
                    </div>
                    <span class="input-group-addon btn btn-default btn-file">
                        <span class="fileinput-new">Select file</span>
                        <span class="fileinput-exists"></span>
                        <input type="file" id = "filenameforactivity" name="filename" class="default" placeholder= "Select a File">
                    </span>
                    <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                </div>
           </div>    
-->
          <div class = "col-md-12 col-xs-12 error" ng-show = "isInvalid">* Date period is not valid</div>
      </form> 
    </div>
      
<div class="modal-footer modalFooter">    
	<button type="button" class="btn btn-primary" ng-click = "addDriver(newDriver)" ng-disabled="addDriverForm.$invalid">Add</button> 
    <button type="button" class="btn btn-default" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>