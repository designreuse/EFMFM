<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "newEscortFormModalTemplate">	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Edit Employee Detail</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name = "editEmployeeDetail">          
           <div class = "col-md-6 col-xs-12 hidden">
               <div>
               <label>Employee Id</label>
                   <input type="text" 
                      ng-model="editEmployee.employeeId" 
                      name = 'Id'>
               </div>
           </div>
           
           <div class = "col-md-6 col-xs-12">
               <div>
               <label>Employee Name</label>
                   <input type="text" 
                      ng-model="editEmployee.name" 
                      class="form-control" 
                      placeholder = "Employee Name"
                      name = 'name'
                      required
                      ng-class = "{error: editEmployeeDetail.name.$invalid && !editEmployeeDetail.name.$pristine}" >
               </div>
           </div>
                     
           <div class = "col-md-6 col-xs-12">
               <div>
               <label>Employee Designation</label> 
                   <input type="text" 
                      ng-model="editEmployee.employeeDesignation" 
                      class="form-control" 
                      placeholder = "Employee Designation"
                      name = 'employeeDesignation'
                      required
                      ng-class = "{error: editEmployeeDetail.employeeDesignation.$invalid && !editEmployeeDetail.employeeDesignation.$pristine}" >
               </div>
           </div>
             
           <div class = "col-md-6 col-xs-12">
               <div>   
               <label>Employee Mobile Number</label>
                    <input type="text" 
                           ng-model="editEmployee.number" 
                           class="form-control" 
                           placeholder = "Employee Mobile Number with contry code"
                           name = 'mobileNumber'
                           ng-pattern = "IntegerNumber"
                           required
                           ng-minlength="11"
                           ng-maxlength="15"
                           maxlength='15'
                           ng-class = "{error: editEmployeeDetail.mobileNumber.$invalid && !editEmployeeDetail.mobileNumber.$pristine}">
               </div>
               <span class = "hintModal" ng-show="editEmployeeDetail.mobileNumber.$error.maxlength">* In-valid Mobile Number</span>
           </div>
                     
           <div class = "col-md-6 col-xs-12">
               <div>
               <label>Employee Email</label> 
                   <input type="email" 
                      ng-model="editEmployee.email" 
                      class="form-control" 
                      placeholder = "Employee email"
                      name = 'email'
                      required
                      ng-class = "{error: editEmployeeDetail.email.$invalid && !editEmployeeDetail.email.$pristine}">
               </div>
           </div>
            <div class = "col-md-6 col-xs-12">
               <div>
               <label>Employee Address</label>
                   <input type="text" 
                      ng-model="editEmployee.address" 
                      class="form-control" 
                      placeholder = "Employee Address"
                      name = 'address'
                      required
                      ng-class = "{error: editEmployeeDetail.address.$invalid && !editEmployeeDetail.address.$pristine}" >
               </div>
           </div>
           
<!--
           <div class = "col-md-6 col-xs-12">
               <div>
               <label>Employee Address</label>
                   <textarea type="text" 
                             rows = "4"
                          ng-model="editEmployee.address" 
                          class="form-control textArea_editEscort" 
                          placeholder = "Employee Address"
                          name = 'address'
                          readonly></textarea>
               </div>
               <span></span>
           </div>    
-->
           
<!--
           <div class = "col-md-6 col-xs-12">
               <div>
               <label>Employee Pickup Time</label>
                   <timepicker ng-model="editEmployee.createNewAdHocTime" 
                               hour-step="hstep" 
                               minute-step="mstep" 
                               show-meridian="ismeridian"
                               class = "timepicker2_empReq">
                  </timepicker>
               </div>
               <span></span>
           </div>      
-->
       </form>   
      </div>        
    </div>      
<div class="modal-footer modalFooter">    
	<button type="button" class="btn btn-success buttonRadius0" ng-click = "updateEmployee(editEmployee)" ng-disabled="editEmployeeDetail.$invalid">Update</button> 
    <button type="button" class="btn btn-default buttonRadius0" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>