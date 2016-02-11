<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div>
<div class = "newEscortFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Add New Escort</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name = "addEscortForm">           
           <div class = "col-md-6 col-xs-12">
               <div>
               <label>Escort's Name</label> 
                   <input type="text" 
                      ng-model="newEscort.escortName" 
                      class="form-control" 
                      placeholder = "Escort Name"
                      name = 'name'
                      required
                      ng-minlength="2"
                      ng-maxlength="15"
                      ng-class = "{error: addEscortForm.name.$invalid && !addEscortForm.name.$pristine}">
               </div>
               <span class = 'hintModal' ng-show = "addEscortForm.name.$error.minlength">* Name is too short</span>
           </div>
             
           <div class = "col-md-6 col-xs-12">
               <div>   
               <label>Escort's Mobile Number</label>
                    <input type="text" 
                           ng-model="newEscort.escortMobileNumber" 
                           class="form-control" 
                           placeholder = "Escort Mobile Number"
                           name = 'mobileNumber'
                           ng-pattern = 'IntegerNumber'
                           required
                           ng-minlength="10"
                           ng-maxlength="10"
                           maxlength = '10'
                           ng-class = "{error: addEscortForm.mobileNumber.$invalid && !addEscortForm.mobileNumber.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addEscortForm.mobileNumber.$error.maxlength">* In-valid Mobile Number</span>
           </div>
           
           <div class = "col-md-6 col-xs-12">
               <div>   
               <label>Escort Vendor Name</label>
                    <input type="text" 
                           ng-model="newEscort.escortVendorName" 
                           class="form-control" 
                           placeholder = "Escort Vendor name"
                           name = 'escortVendorName'
                           required
                           maxlength = '20'
                           ng-class = "{error: addEscortForm.escortVendorName.$invalid && !addEscortForm.escortVendorName.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addEscortForm.escortVendorName.$error.maxlength">* In-valid Vendor Name</span>
           </div>
       
           <div class = "col-md-6 col-xs-12">
           <label>Date of Birth</label>
             <div class = "input-group calendarInput">            
                 <!--  tooltip="Date of Birth"
                  tooltip-placement="top"
                  tooltip-trigger="mouseenter"> -->
                 <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="dobCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="newEscort.escortdob" 
                      class="form-control" 
                      placeholder = "Escort Date of Birth"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.dobDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      required
                      readonly
                      name = 'dob'
                      ng-class = "{error: addEscortForm.dob.$invalid && !addEscortForm.dob.$pristine}">
             </div>
           </div> 
             
           <div class = "col-md-6 col-xs-12">
               <div>   
               <label>Escort Batch Number</label>
                    <input type="text" 
                           ng-model="newEscort.escortBatchNumber" 
                           class="form-control" 
                           placeholder = "Escort Batch Number"
                           name = 'batchNumber'
                           required
                           ng-minlength="0"
                           ng-maxlength="15"
                           maxlength = '15'
                           ng-class = "{error: addEscortForm.batchNumber.$invalid && !addEscortForm.batchNumber.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addEscortForm.batchNumber.$error.maxlength">* In-valid Mobile Number</span>
           </div>  
           
           <div class = "col-md-12 col-xs-12">
               <div>
               <label>Escort's Address</label>
               <!--  tooltip="Escort's Address"
                tooltip-placement="top"
                tooltip-trigger="mouseenter"> -->
                   <textarea type="text" 
                          ng-model="newEscort.escortAddress" 
                          class="form-control textArea_editEscort" 
                          placeholder = "Escort's Address"
                          name = 'address'
                          required
                          ng-minlength="0"
                          ng-maxlength="250"
                          ng-class = "{error: addEscortForm.address.$invalid && !addEscortForm.address.$pristine}"></textarea>
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
       </form>   
      </div>        
    </div>      
<div class="modal-footer modalFooter">    
	<button type="button" class="btn btn-success" ng-click = "addEscort(newEscort)" ng-disabled="addEscortForm.$invalid">Save</button> 
    <button type="button" class="btn btn-default" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>