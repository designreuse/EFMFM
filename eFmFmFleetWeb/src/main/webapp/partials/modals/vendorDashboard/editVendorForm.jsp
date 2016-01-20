<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "editVendorFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Edit Vendor</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name = "editVendorForm" class = "editVendorForm">
           <div class = "col-md-4 col-xs-12 form-group">
               <label for = "name">Vendor Name</label>
               <input type="text" 
                      ng-model="editVendor.vendorName" 
                      class="form-control" 
                      placeholder = "Vendor Name"
                      ng-minlength="2"
                      name = 'name'
                      required
                      ng-class = "{error: editVendorForm.name.$invalid && !editVendorForm.name.$pristine}">
               <span class = "hintModal" ng-show = "editVendorForm.name.$error.minlength">* Name is too short</span>
           </div>
           <div class = "col-md-4 col-xs-12 form-group">
               <label for = "mobileNumber">Mobile Number</label>
               <input type="text" 
                          ng-model="editVendor.vendorMobileNumber" 
                          class="form-control" 
                          placeholder = "Mobile Number"
                          name = 'mobileNumber'
                          ng-pattern="/^[0-9]*$/"
                          ng-minlength = '10'
                          ng-maxlength="10"
                          maxlength="10"
                          required
                          ng-class = "{error: editVendorForm.mobileNumber.$invalid && !editVendorForm.mobileNumber.$pristine}">
               <span class = "hintModal" ng-show="editVendorForm.mobileNumber.$error.maxlength">* In-valid Mobile Number</span>
           </div>
           <div class = "col-md-4 col-xs-12 form-group">
               <label for = "email">Email Address</label>
                       <input type="email" 
                          ng-model="editVendor.email" 
                          class="form-control" 
                          placeholder = "Email Address"
                          name = 'email'
                          required
                          ng-class = "{error: editVendorForm.email.$invalid && !editVendorForm.email.$pristine}">
               <span class = 'hintModal'  ng-show="editVendorForm.email.$error.email">* In-valid Email Address</span>
           </div> 
           <div class = "col-md-8 col-xs-12 form-group">
               <label for = "address">Address</label>
               <input type="text" 
                          ng-model="editVendor.address" 
                          class="form-control" 
                          placeholder = "Address"
                          name = 'address'
                          required
                          ng-class = "{error: editVendorForm.address.$invalid && !editVendorForm.address.$pristine}">
               <span></span>
           </div>
           <div class = "col-md-4 col-xs-12 form-group">
               <label for = "contactName">Contact Name 1</label>
               <input type="text" 
                      ng-model="editVendor.vendorContactName" 
                      class="form-control" 
                      placeholder = "Contact Name"
                      name = 'contactName'
                      ng-minlength="2"
                      ng-maxlength="30"
                      required
                      ng-class = "{error: editVendorForm.contactName.$invalid && !editVendorForm.contactName.$pristine}">
               <span class = "hintModal" ng-show = "editVendorForm.contactName.$error.minlength">* Name is too short</span>
           </div>
           <div class = "col-md-4 col-xs-12 form-group">
               <label for = "contactNo">Contact Number 1</label>
               <input type="text" 
                          ng-model="editVendor.vendorContactNumber" 
                          class="form-control" 
                          placeholder = "Contact No."
                          name ="contactNo"
                          ng-pattern="/^[0-9]*$/"
                          ng-minlength = '10'
                          ng-maxlength="10"
                          maxlength="10"
                          required
                          ng-class = "{error: editVendorForm.contactNo.$invalid && !editVendorForm.contactNo.$pristine}">
               <span class = "hintModal" ng-show="editVendorForm.contactNo.$error.maxlength">* In-valid Contact Number</span>
           </div>
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Name 2 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="editVendor.vendorContactName2" 
                           class="form-control" 
                           placeholder = "Contact Name"
                           name = 'contactName2'
                           ng-minlength="2"
                           ng-maxlength="30"                           
                           ng-class = "{error: editVendorForm.contactName2.$invalid && !editVendorForm.contactName2.$pristine}">                   
               </div>
               <span class = "hintModal" ng-show = "editVendorForm.contactName2.$error.minlength">* Name is too short</span>
           </div> 
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Number 2 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="editVendor.vendorContactNumber2" 
                           class="form-control" 
                           placeholder = "Contact Number"
                           name = "contactNo2"
                           ng-pattern="/^[0-9]*$/"
                           ng-minlength = '10'
                           ng-maxlength="10"
                           maxlength="10"
                           ng-class = "{error: editVendorForm.contactNo2.$invalid && !editVendorForm.contactNo2.$pristine}">
               </div>
               <span class = "hintModal" ng-show="editVendorForm.contactNo2.$error.maxlength">* In-valid Contact Number</span>
           </div>           
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Name 3 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="editVendor.vendorContactName3" 
                           class="form-control" 
                           placeholder = "Contact Name"
                           name = 'contactName3'
                           ng-minlength="2"
                           ng-maxlength="30"
                           ng-class = "{error: editVendorForm.contactName3.$invalid && !editVendorForm.contactName3.$pristine}">                   
               </div>
               <span class = "hintModal" ng-show = "editVendorForm.contactName3.$error.minlength">* Name is too short</span>
           </div> 
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Number 3 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="editVendor.vendorContactNumber3" 
                           class="form-control" 
                           placeholder = "Contact Number"
                           name = "contactNo3"
                           ng-pattern="/^[0-9]*$/"
                           ng-minlength = '10'
                           ng-maxlength="10"
                           maxlength="10"
                           ng-class = "{error: editVendorForm.contactNo3.$invalid && !editVendorForm.contactNo3.$pristine}">
               </div>
               <span class = "hintModal" ng-show="editVendorForm.contactNo3.$error.maxlength">* In-valid Contact Number</span>
           </div>          
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Name 4 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="editVendor.vendorContactName4" 
                           class="form-control" 
                           placeholder = "Contact Name"
                           name = 'contactName4'
                           ng-minlength="2"
                           ng-maxlength="30"
                           ng-class = "{error: editVendorForm.contactName4.$invalid && !editVendorForm.contactName4.$pristine}">                   
               </div>
               <span class = "hintModal" ng-show = "editVendorForm.contactName4.$error.minlength">* Name is too short</span>
           </div> 
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contact Number 4 <span> (optional)</span></label>
                    <input type="text" 
                           ng-model="editVendor.vendorContactNumber4" 
                           class="form-control" 
                           placeholder = "Contact Number"
                           name = "contactNo4"
                           ng-pattern="/^[0-9]*$/"
                           ng-minlength = '10'
                           ng-maxlength="10"
                           maxlength="10"
                           ng-class = "{error: editVendorForm.contactNo4.$invalid && !editVendorForm.contactNo4.$pristine}">
               </div>
               <span class = "hintModal" ng-show="editVendorForm.contactNo4.$error.maxlength">* In-valid Contact Number</span>
           </div>
           
           <div class = "col-md-8 col-xs-12 form-group">
           <div ng-repeat = "doc in vendorDocuments" class = "floatLeft" id = "div{{$index}}">
               <i class = "icon-remove-sign pointer" ng-click="deleteDocument($index)"></i>
                    <a href="{{doc.location}}" class = "noLinkLine">
                        <img class="img-responsive img-rounded fileImg" 
                          alt="Responsive image" 
                          ng-src = "{{doc.imgSrc}}">
                        <div class = "docName"><span>{{doc.name}}</span></div>
                    </a>                     
                   
           </div>
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
	<button type="button" class="btn btn-warning" ng-click = "saveVendor(editVendor)" ng-disabled="editVendorForm.$invalid">Save</button> 
    <button type="button" class="btn btn-default" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>