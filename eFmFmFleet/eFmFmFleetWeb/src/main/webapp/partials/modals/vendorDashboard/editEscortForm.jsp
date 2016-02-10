<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "editEscortFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Edit Escort</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name = "editEscortForm" class = "editEscortForm">           
           <div class = "col-md-6 col-xs-12">

               <label for = "escortName">Escort Name</label>
               <input type="text" 
                      ng-model="editEscort.escortName" 
                      class="form-control" 
                      placeholder = "Escort's Name"
                      name = 'name'
                      required
                      ng-minlength="2"
                      ng-maxlength="15"
                      ng-class = "{error: editEscortForm.name.$invalid && !editEscortForm.name.$pristine}">
               <span class = 'hintModal' ng-show = "editEscortForm.name.$error.minlength">* Name is too short</span>
           </div>
             
           <div class = "col-md-6 col-xs-12">
               <label for = "escortMobileNumber">Escort Mobile Number</label>
               <input type="text" 
                      ng-model="editEscort.escortMobileNumber" 
                      class="form-control" 
                      placeholder = "Escort Mobile Number"
                      name = 'mobileNumber'
                      ng-pattern = 'IntegerNumber'
                      required
                      ng-minlength="0"
                      ng-maxlength="15"
                      ng-class = "{error: editEscortForm.mobileNumber.$invalid && !editEscortForm.mobileNumber.$pristine}"> 
               <span class = "hintModal" ng-show="editEscortForm.mobileNumber.$error.maxlength">* In-valid Mobile Number</span>
           </div>
           
           <div class = "col-md-6 col-xs-12">
               <div>   
               <label>Escort Vendor Name</label>
                    <input type="text" 
                           ng-model="editEscort.escortVendorName" 
                           class="form-control" 
                           placeholder = "Escort Vendor name"
                           name = 'escortVendorName'
                           required
                           maxlength = '20'
                           ng-class = "{error: editEscortForm.escortVendorName.$invalid && !editEscortForm.escortVendorName.$pristine}">
               </div>
               <span class = "hintModal" ng-show="editEscortForm.escortVendorName.$error.maxlength">* In-valid Vendor Name</span>
           </div>
       
           <div class = "col-md-6 col-xs-12">
             <label for = "dob">Date of Birth</label>  
             <div class = "input-group calendarInput">
                 <span class="input-group-btn"><button class="btn btn-default" ng-click="dobCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="editEscort.escortdob" 
                      class="form-control" 
                      placeholder = "Date of Birth"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.dobDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      required
                      name = 'dob'
                      readonly
                      ng-class = "{error: editEscortForm.dob.$invalid && !editEscortForm.dob.$pristine}">
                
             </div>
           </div>            
             
           <div class = "col-md-6 col-xs-12">
               <div>   
               <label>Escort Batch Number</label>
                    <input type="text" 
                           ng-model="editEscort.escortBatchNumber" 
                           class="form-control" 
                           placeholder = "Escort Batch Number"
                           name = 'batchNumber'
                           required
                           ng-minlength="0"
                           ng-maxlength="15"
                           maxlength = '15'
                           ng-class = "{error: editEscortForm.batchNumber.$invalid && !editEscortForm.batchNumber.$pristine}">
               </div>
               <span class = "hintModal" ng-show="editEscortForm.batchNumber.$error.maxlength">* In-valid Mobile Number</span>
           </div> 
           
           <div class = "col-md-6 col-xs-12">
               <label for = "escortAddress">Escort Address</label>
               <textarea type="text" 
                          ng-model="editEscort.escortAddress" 
                          class="form-control textArea_editEscort" 
                          placeholder = "Escort's Address"
                          name = 'address'
                          required
                          ng-minlength="0"
                          ng-maxlength="250"
                          ng-class = "{error: editEscortForm.address.$invalid && !editEscortForm.address.$pristine}"></textarea>
<!--               </div>-->
               <span></span>
           </div>  
           
           <div class = "col-md-12 col-xs-12">
<!--
           <div ng-repeat = "doc in escortDocuments" class = "floatLeft" id = "div{{$index}}">
               <i class = "icon-remove-sign pointer" ng-click="deleteDocument($index)"></i>
                    <a href="{{doc.location}}" class = "noLinkLine">
                        <img class="img-responsive img-rounded fileImg" 
                          alt="Responsive image" 
                          ng-src = "{{doc.imgSrc}}">
                        <div class = "docName"><span>{{doc.name}}</span></div>
                    </a>                     
                   
           </div>
-->
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
	<button type="button" class="btn btn-warning" ng-click = "saveEscort(editEscort)" ng-disabled="editEscortForm.$invalid">Save</button> 
    <button type="button" class="btn btn-default" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>