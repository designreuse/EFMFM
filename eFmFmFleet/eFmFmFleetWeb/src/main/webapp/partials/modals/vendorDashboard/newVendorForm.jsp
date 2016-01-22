<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div>
<div class = "addVendorFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Add New Vendor</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name = "addVendorForm">
           <div class = "col-md-4 col-xs-12"> 
               <div>
               <label>Vendor Name</label>
                <input type="text" 
                      ng-model="newVendor.vendorName"
                      class="form-control" 
                      ng-pattern="/^[a-zA-Z\s]*$/"
                      placeholder = "Vendor Name"
                      name = 'name' 
                      ng-minlength="2"
                      ng-maxlength="15"
                      maxlength="15"
                      required
                      ng-class = "{error: addVendorForm.name.$invalid && !addVendorForm.name.$pristine}">                   
               </div>
               <span class = "hintModal" ng-show = "addVendorForm.name.$error.pattern">* Enter Only Alphabets</span>           
                <span class = "hintModal" ng-show = "addVendorForm.name.$error.minlength">* Name is too short</span>
           </div>
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Mobile Number</label>             
               <input type="text" 
                           ng-model="newVendor.vendorMobileNumber" 
                           class="form-control" 
                           placeholder = "Mobile No"
                           name = 'mobileNumber'
                           ng-pattern="/^[0-9]*$/"
                           ng-minlength="10"
                           ng-maxlength="10"
                           maxlength="10"
                           required
                           ng-class = "{error: addVendorForm.mobileNumber.$invalid && !addVendorForm.mobileNumber.$pristine}">
               </div>    
                <span class = "hintModal" ng-show = "addVendorForm.mobileNumber.$error.pattern">* Enter Only Numbers</span>
               <span class = "hintModal" ng-show="addVendorForm.mobileNumber.$error.maxlength">* In-valid Mobile Number</span>
           </div>
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Email</label>
                    <input type="email" 
                           ng-model="newVendor.email" 
                           class="form-control" 
                           placeholder = "Email"
                           name = 'email'
                           required
                           ng-class = "{error: addVendorForm.email.$invalid && !addVendorForm.email.$pristine}">
               </div>
               <span class = 'hintModal'  ng-show="addVendorForm.email.$error.email">* In-valid Email Address</span>
           </div>
           <div class = "col-md-8 col-xs-12">
               <div>
               <label>Address</label>
                    <input type="text" 
                           ng-model="newVendor.address" 
                           class="form-control" 
                           placeholder = "Address"
                           name = 'address'
                           required
                              ng-class = "{error: addVendorForm.address.$invalid && !addVendorForm.address.$pristine}">
               </div>
               <span></span>
           </div>
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Name 1</label>
                    <input type="text" 
                           ng-model="newVendor.vendorContactName" 
                           class="form-control" 
                           placeholder = "Contact Name"
                           name = 'contactName'
                           ng-minlength="2"
                           ng-maxlength="30"
                           required
                           ng-class = "{error: addVendorForm.contactName.$invalid && !addVendorForm.contactName.$pristine}">                   
               </div>
               <span class = "hintModal" ng-show = "addVendorForm.contactName.$error.minlength">* Name is too short</span>
           </div> 
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Number 1</label>                 
                    <input type="text" 
                           ng-model="newVendor.vendorContactNumber" 
                           class="form-control" 
                           placeholder = "Contact Number"
                           name = "contactNo"
                           ng-pattern="/^[0-9]*$/"
                           ng-maxlength="10"
                           ng-minlength="10"
                           maxlength="10"
                           required
                           ng-class = "{error: addVendorForm.contactNo.$invalid && !addVendorForm.contactNo.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVendorForm.contactNo.$error.maxlength">* In-valid Contact Number</span>
<!--               <span class = "hint" ng-show = "addVendorForm.contactNo.$invalid && !addVendorForm.contactNo.$pristine">*Required</span>-->
           </div>           
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Name 2 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="newVendor.vendorContactName2" 
                           class="form-control" 
                           placeholder = "Contact Name"
                           name = 'contactName2'
                           ng-minlength="2"
                           ng-maxlength="30"                           
                           ng-class = "{error: addVendorForm.contactName2.$invalid && !addVendorForm.contactName2.$pristine}">                   
               </div>
               <span class = "hintModal" ng-show = "addVendorForm.contactName2.$error.minlength">* Name is too short</span>
           </div> 
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Number 2 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="newVendor.vendorContactNumber2" 
                           class="form-control" 
                           placeholder = "Contact Number"
                           name = "contactNo2"
                           ng-pattern="/^[0-9]*$/"
                           ng-minlength = '10'
                           ng-maxlength="10"
                           maxlength="10"
                           ng-class = "{error: addVendorForm.contactNo2.$invalid && !addVendorForm.contactNo2.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVendorForm.contactNo2.$error.maxlength">* In-valid Contact Number</span>
           </div>           
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Name 3 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="newVendor.vendorContactName3" 
                           class="form-control" 
                           placeholder = "Contact Name"
                           name = 'contactName3'
                           ng-minlength="2"
                           ng-maxlength="30"
                           ng-class = "{error: addVendorForm.contactName3.$invalid && !addVendorForm.contactName3.$pristine}">                   
               </div>
               <span class = "hintModal" ng-show = "addVendorForm.contactName3.$error.minlength">* Name is too short</span>
           </div> 
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Number 3 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="newVendor.vendorContactNumber3" 
                           class="form-control" 
                           placeholder = "Contact Number"
                           name = "contactNo3"
                           ng-pattern="/^[0-9]*$/"
                           ng-minlength = '10'
                           ng-maxlength="10"
                           maxlength="10"
                           ng-class = "{error: addVendorForm.contactNo3.$invalid && !addVendorForm.contactNo3.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVendorForm.contactNo3.$error.maxlength">* In-valid Contact Number</span>
           </div>          
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Name 4 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="newVendor.vendorContactName4" 
                           class="form-control" 
                           placeholder = "Contact Name"
                           name = 'contactName4'
                           ng-minlength="2"
                           ng-maxlength="30"
                           ng-class = "{error: addVendorForm.contactName4.$invalid && !addVendorForm.contactName4.$pristine}">                   
               </div>
               <span class = "hintModal" ng-show = "addVendorForm.contactName4.$error.minlength">* Name is too short</span>
           </div> 
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Number 4 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="newVendor.vendorContactNumber4" 
                           class="form-control" 
                           placeholder = "Contact Number"
                           name = "contactNo4"
                           ng-pattern="/^[0-9]*$/"
                           ng-minlength = '10'
                           ng-maxlength="10"
                           maxlength="10"
                           ng-class = "{error: addVendorForm.contactNo4.$invalid && !addVendorForm.contactNo4.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVendorForm.contactNo4.$error.maxlength">* In-valid Contact Number</span>
           </div>
 <!--           <div class = "col-md-4 col-xs-12">
               <div>
               
                tooltip="Number of Days"
                tooltip-placement="top"
                tooltip-trigger="mouseenter">
                    <input type="number" 
                           
                           ng-model="newVendor.numDays" 
                           class="form-control" 
                           placeholder = "No. od Days"
                           name = 'numDays'
                           ng-minlength="0"
                           ng-maxlength="3"
                           required
                           ng-class = "{error: addVendorForm.numDays.$invalid && !addVendorForm.numDays.$pristine}">
               </div>
               <span class = 'hintModal' ng-show = 'addVendorForm.numDays.$error.maxlength'>* In-valid Number of Days</span>
           </div> -->
         <!--   <div class = "col-md-4 col-xs-12">
            <div class = "input-group calendarInput"
                 tooltip="Contract Start Date"
                 tooltip-placement="top"
                 tooltip-trigger="mouseenter">
                <span class="input-group-btn"><button class="btn btn-default" ng-click="contractStartCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="newVendor.contractStartdate" 
                      class="form-control" 
                      placeholder = "Contract Start Date"
                      datepicker-popup = 'shortDate'
                      is-open="datePicker.contractStartDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptionsFrom'
                      required
                      name = 'contractStartDate'
                      ng-class = "{error: addVendorForm.contractStartDate.$invalid && !addVendorForm.contractStartDate.$pristine}">
                
            </div>
           </div> --> 
     <!--       <div class = "col-md-4 col-xs-12">
             <div class = "input-group calendarInput"
                  tooltip="Contract End Date"
                  tooltip-placement="top"
                  tooltip-trigger="mouseenter">
                 <span class="input-group-btn"><button class="btn btn-default" ng-click="contractEndCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="newVendor.contractEndDate" 
                      class="form-control" 
                      placeholder = "Contract End Date"
                      datepicker-popup = 'shortDate'
                      is-open="datePicker.contractEndDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptionsTo'
                      required
                      name = 'contractEndDate'
                      ng-class = "{error: addVendorForm.contractEndDate.$invalid && !addVendorForm.contractEndDate.$pristine}">
                 
             </div>
           </div>  -->
<!--
           <div class = "col-md-4 col-xs-12">
               <div
                tooltip="Number of Cabs"
                tooltip-placement="top"
                tooltip-trigger="mouseenter">
                    <input type="number" 
                           ng-model="newVendor.noOfVehicle" 
                           class="form-control" 
                           placeholder = "Number of Cabs"
                           name = "numCabs"
                           required
                           ng-minlength="0"
                           ng-class = "{error: addVendorForm.numCabs.$invalid && !addVendorForm.numCabs.$pristine}">
               </div>
               <span class = 'hintModal' ng-show = "addVendorForm.numDays.$error.minlength">* In-valid Number of Cabs</span>
           </div> 
-->
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
    </div>      
<div class="modal-footer modalFooter">    
	<button type="button" class="btn btn-success" ng-click = "addNewVendor(newVendor)" ng-disabled="addVendorForm.$invalid">Save</button> 
    <button type="button" class="btn btn-default" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>