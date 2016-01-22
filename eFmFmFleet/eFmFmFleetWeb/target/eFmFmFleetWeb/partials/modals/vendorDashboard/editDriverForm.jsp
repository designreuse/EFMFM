<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "editDriverFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Edit Driver</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name="driverEditForm" class = "driverEditForm">
           <div class = "col-md-4 col-xs-12"> 
              <label for = "driverName">Driver Name</label>
              <input type="text" 
                          ng-model="editDriver.driverName" 
                          class="form-control" 
                          placeholder = "Driver Name"
                          ng-minlength="2"
                          name = "name"
                          required
                          ng-class = "{error: driverEditForm.name.$invalid && !driverEditForm.name.$pristine}">
               <span class = 'hintModal' ng-show = "driverEditForm.name.$error.minlength">* Name is too short</span>
<!--          <span ng-show="driverEditForm.name.$invalid && !driverEditForm.name.$pristine" class="help-block"></span>-->
       </div>
           
       <div class = "col-md-4 col-xs-12">  
           <label for = "mobileNum">Driver Mobile Number</label>       
           <input type="text" 
                 ng-model="editDriver.mobileNumber" 
                 class="form-control" 
                 placeholder = "Mobile Number"
                 name = "contactNo"
                 required
                 ng-minlength="0"
                 ng-maxlength="15"
                 ng-class = "{error: driverEditForm.contactNo.$invalid && !driverEditForm.contactNo.$pristine}">
          <span class = "hintModal" ng-show="driverEditForm.contactNo.$error.maxlength">* In-valid Mobile Number</span>
       </div>
           
       <div class = "col-md-4 col-xs-12">
        <label for = "driverdob">Date of Birth</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn"><button class="btn btn-default" ng-click="openDobCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="editDriver.driverdob" 
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
                 ng-class = "{error: driverEditForm.dob.$invalid && !driverEditForm.dob.$pristine}">          
           </div>
       </div>       
      
       <div class = "col-md-4 col-xs-12">
       <label>DDT Expiry Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openDDTCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="editDriver.driverDDT" 
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
                 ng-class = "{error: driverEditForm.ddt.$invalid && !driverEditForm.ddt.$pristine}">          
           </div>
       </div>
           
       <div class = "col-md-4 col-xs-12">
       <label>Batch Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openBatchCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="editDriver.driverBatch" 
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
                 ng-class = "{error: driverEditForm.batch.$invalid && !driverEditForm.batch.$pristine}">          
           </div>
       </div>
           
       <div class = "col-md-4 col-xs-12">
       <label>Batch Number</label>
        <div>
          <input type="text" 
                 ng-model="editDriver.driverBatchNum" 
                 class="form-control" 
                 placeholder = "Batch Number"
                 ng-maxlength="50"
                 maxlength="50"
                 required
                 name = 'batchNum'
                 ng-class = "{error: driverEditForm.batchNum.$invalid && !driverEditForm.batchNum.$pristine}">          
           </div>
       </div>
           
       <div class = "col-md-4 col-xs-12">
       <label>Police Verification Expiry Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openPoliceVerificationCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="editDriver.driverPoliceVerification" 
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
                 ng-class = "{error: driverEditForm.policeVerification.$invalid && !driverEditForm.policeVerification.$pristine}">          
           </div>
       </div>
           
       <div class = "col-md-4 col-xs-12">
       <label>Medical Expiry Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openMedicalExpiryCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="editDriver.driverMedicalExpiry" 
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
                 ng-class = "{error: driverEditForm.medicalExpiry.$invalid && !driverEditForm.medicalExpiry.$pristine}">          
           </div>
       </div>
           
<!--        <div class = "col-md-4 col-xs-12">
       <label>Anti Expiry Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openAntiExpiryCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="editDriver.driverAntiExpiry" 
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
                 ng-class = "{error: driverEditForm.antiExpiry.$invalid && !driverEditForm.antiExpiry.$pristine}">          
           </div>
       </div> -->
           
       <div class = "col-md-4 col-xs-12">
       <label>Joining Date</label>
        <div class = "input-group calendarInput">
            <span class="input-group-btn">
                <button class="btn btn-default" ng-click="openJoiningCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
          <input type="text" 
                 ng-model="editDriver.driverJoining" 
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
                 ng-class = "{error: driverEditForm.joining.$invalid && !driverEditForm.joining.$pristine}">          
           </div>
       </div>
           
       <div class = "col-md-4 col-xs-12">
           <label for = "licenseNum">License Number</label>
           <input type="text" 
                 ng-model="editDriver.licenceNumber" 
                 class="form-control" 
                 placeholder = "License Number"
                 required
                 name = "lisNum"
                 ng-minlength="0"
                 ng-maxlength="50"
                 ng-class = "{error: driverEditForm.lisNum.$invalid && !driverEditForm.lisNum.$pristine}">
           <span class = "hintModal" ng-show="driverEditForm.lisNum.$error.maxlength">* In-valid Liscense Number</span>
       </div>
      
       <div class = "col-md-4 col-xs-12">
           <label for = "Insurancevaliddate">License Expiry Date</label>
           <div class = "input-group calendarInput">
               <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="openExpDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span> 
               <input type="text" 
                 ng-model="editDriver.licenceValid" 
                 class="form-control" 
                 placeholder = "License Expiry Date"
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.openedExpDate"
                 show-button-bar = false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'expDate'
                 ng-class = "{error: driverEditForm.expDate.$invalid && !driverEditForm.expDate.$pristine}">                          
             </div>          
       </div>
           
       <div class = "col-md-8 col-xs-12">
           <label for = "driveraddress">Driver Address</label>
            <textarea type="text"
                       ng-model="editDriver.driverAddress" 
                       class="form-control" 
                       placeholder = "Address"
                       name = "address"
                       required
                      ng-class = "{error: driverEditForm.address.$invalid && !driverEditForm.address.$pristine}"></textarea>
           <span></span>
       </div>    
           
<!--
       <div class = "col-md-6 col-xs-12 form-group" >
           <label for = "InsuranceExpirydate">Medical Certificate Valid</label>
           <div class = "input-group calendarInput">
               <span class="input-group-btn">
                   <button class="btn btn-default" ng-click="insurranceExpDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
            <input type="text" 
                 ng-model="editDriver.InsuranceValid" 
                 class="form-control" 
                 placeholder = "Insurance Expiry Date" 
                 datepicker-popup = '{{format}}'
                 is-open="datePicker.insurranceExpDate" 
                 show-button-bar = false
                 datepicker-options = 'dateOptions'
                 required
                 readonly
                 name = 'insurDate'
                 ng-class = "{error: driverEditForm.insurDate.$invalid && !driverEditForm.insurDate.$pristine}">
            
           </div>
       </div>       
-->
       
       <input type = 'text' class = 'hidden' ng-model = "editDriver.driverId">
<!--       <div class = "clearfix"></div>-->
       <!-- <div class = "col-md-4 col-xs-12">
           <div ng-repeat = "doc in driverDocuments" class = "floatLeft" id = "div{{$index}}">
               <i class = "icon-remove-sign pointer" ng-click="deleteDocument($index)"></i>
                    <a href="{{doc.location}}" class = "noLinkLine">
                        <img class="img-responsive img-rounded fileImg" 
                          alt="Responsive image" 
                          ng-src = "{{doc.imgSrc}}">
                        <div class = "docName"><span>{{doc.name}}</span></div>
                    </a>                  
           </div>
       </div> -->
           <div class = "col-md-4 col-xs-12 hidden">
               <input type="text" ng-model="newVendor.numCabs" class="form-control">
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
       </form>   
      </div>        
    </div>      
<div class="modal-footer modalFooter">    
	<button type="button" class="btn btn-warning" ng-click = "saveDriver(editDriver)" ng-disabled="driverEditForm.$invalid">Save</button> 
    <button type="button" class="btn btn-default" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>