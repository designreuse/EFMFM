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
                      ng-model="user.employeeId" 
                      name = 'Id'>
               </div>
           </div>
           
           <div class = "col-md-6 col-xs-12">
               <div>
               <label>Employee Name</label>
                   <input type="text" 
                      ng-model="user.name" 
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
                      ng-model="user.employeeDesignation" 
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
                           ng-model="user.number" 
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
                      ng-model="user.email" 
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
                      ng-model="user.empaddress" 
                      class="form-control" 
                      placeholder = "Employee Address"
                      name = 'empaddress'
                      required
                      ng-class = "{error: editEmployeeDetail.empaddress.$invalid && !editEmployeeDetail.empaddress.$pristine}" >
               </div>
           </div>
           <div class = 'col-md-12 editEmployeeMap'>
               <fieldset class = 'fieldSetTravelDesk'>
                   <Legend class = 'legendTravelDesk'>Update Employee Geocode</Legend>
               <form class = "chooseLocationForm" name = "chooseLocationForm">         
                   <div class = "col-md-4 form-group">
                     <label>Search</label>
                     <div class = "input-group calendarInput"> 
                           <span class="input-group-btn">
                               <button class="btn btn-default" 
                                       ng-click="geoCode(search)" >
                               <i class = "icon-search mapMarkerIcon"></i></button></span> 
                          <input ng-model="user.search"
                                 id = "location"
                                 type = "text" 
                                 class="form-control" 
                                 placeholder = "Location">
                     </div>
                   </div> 
                   <div class = "col-md-6 col-xs-12 form-group"  > 
                      <label>Geocode Address</label>
                           <textarea type="text" 
                              class="form-control" 
                              ng-model="user.address"
                              id = "newAddress"
                              placeholder = "Address"
                              readonly
                              > </textarea>            
                        <span class = "hintModal"></span>  
                   </div> 
            <!--
                       <div class = "col-md-2 col-xs-12 form-group">
                           <label></label>
                           <button type="button" class="btn btn-success buttonRadius0 saveLocationbutton_empDetail" 
                                   ng-click = "setPickDropLocation(user)"
                                   ng-disabled="chooseLocationForm.$invalid">Save GeoCodes</button> 

                       </div>
            -->
                   <div class = "col-md-6 col-xs-12 form-group hidden"> 
                      <label>Cordinates</label>
                           <input type="text" 
                              ng-model="user.cords"
                              class="form-control"  
                              id = "latlangInput"
                              placeholder = "Co-ordinates"
                              name = 'cords'
                              readonly>                  
                        <span class = "hintModal"></span>  
                   </div>  
                <div class = "col-md-12 map_viewMap map_viewMapEditEmployee"  ng-if = "mapIsLoaded">
                    <efmfm-new-user-map-search-location id = "mapDiv_admin" center = 'loc' user = 'user'></efmfm-new-user-map-search-location>            
                </div>
           </div>
       </form> 
    </fieldset>
               
         
      </div>        
    </div>      
<div class="modal-footer modalFooter">    
	<button type="button" class="btn btn-success buttonRadius0" ng-click = "updateEmployee(user)" ng-disabled="editEmployeeDetail.$invalid">Update</button> 
    <button type="button" class="btn btn-default buttonRadius0" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>