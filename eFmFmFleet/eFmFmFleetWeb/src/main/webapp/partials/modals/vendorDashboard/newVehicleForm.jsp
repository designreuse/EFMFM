<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div>
<div class = "newVehicleFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Add New Vehicle</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name = "addVehicleForm">
       <!--     <div class = "col-md-4 col-xs-12">
               <div
                tooltip="Select Vendors"
                tooltip-placement="top"
                tooltip-trigger="mouseenter">
                    <select ng-model="newVehicle.vehicleOwnerName" 
                        ng-options="selectVendor.value for selectVendor in selectVendors"
                        class = "vendorSelect_newVehicleForm"
                        name = "selectVendor"
                        required
                        ng-class = "{error: addVehicleForm.selectVendor.$invalid && !addVehicleForm.selectVendor.$pristine}">                	
                        <option value="">-- Select Vendors --</option>
                    </select>
               </div>
              <span class = "hintModal" ng-show = "invalidVendorSelection">* In-valid Seat Selection</span>
           </div> -->
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Vehicle Make</label>
                    <input type="text" 
                      ng-model="newVehicle.vehicleName" 
                      class="form-control" 
                      placeholder = "Vehicle Make"
                      name = 'vehicleName'
                      required
                      ng-minlength="0"
                      ng-maxlength="15"
                      ng-class = "{error: addVehicleForm.vehicleName.$invalid && !addVehicleForm.vehicleName.$pristine}">
               </div>
               <span></span>
           </div>
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Vehicle Model</label>
                    <input type="text" 
                           ng-model="newVehicle.pollutionCert" 
                           class="form-control" 
                           placeholder = "Vehicle Model"
                           name = 'pollutionCert'
                           required
                           ng-minlength="0"
                           ng-maxlength="30"
                           maxlength = '30'
                           ng-class = "{error: addVehicleForm.pollutionCert.$invalid && !addVehicleForm.pollutionCert.$pristine}"> 
               </div>      
               <span class = "hintModal" ng-show="addVehicleForm.pollutionCert.$error.maxlength">* In-valid Plolution Cert. Number</span>
           </div>
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Vehicle Model Year</label>
                    <input type="text" 
                           ng-model="newVehicle.modelYear" 
                           class="form-control" 
                           placeholder = "Vehicle Model Year [YYYY]"
                           name = 'InsuranceComp'
                           required
                           ng-pattern = 'IntegerNumber'
                           ng-minlength="0"
                           ng-maxlength="4"
                           ng-class = "{error: addVehicleForm.InsuranceComp.$invalid && !addVehicleForm.InsuranceComp.$pristine}">
               </div>
<!--               <span class = "hintModal" ng-show="addVehicleForm.InsuranceComp.$error.maxlength">* In-valid Inssurance Name</span>-->
           </div> 
            
            <div class = "col-md-4 col-xs-12">
               <div>
               <label>Vehicle Number</label>
                   <input type="text" 
                          ng-model="newVehicle.vehicleNumber" 
                          class="form-control" 
                          placeholder = "Vehicle Number"
                          name = 'vehicleNumber'
                          required
                          ng-minlength="0"
                          ng-maxlength="15"
                          maxlength = '15'
                          ng-class = "{error: addVehicleForm.vehicleNumber.$invalid && !addVehicleForm.vehicleNumber.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVehicleForm.vehicleNumber.$error.maxlength"></span>
           </div> 
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Vehicle Engine Number</label>
                    <input type="text" 
                           ng-model="newVehicle.contactNo" 
                           class="form-control" 
                           placeholder = "Vehicle Engine Number"
                           name = 'contactNumber'
                           required
                           ng-minlength="0"
                           ng-maxlength="15"
                           maxlength = '15'
                           ng-class = "{error: addVehicleForm.contactNumber.$invalid && !addVehicleForm.contactNumber.$pristine}">               
               </div>
               <span class = "hintModal" ng-show="addVehicleForm.contactNumber.$error.maxlength">* In-valid Contact Number</span>
           </div>        
          
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Select Seats</label>
                    <select ng-model="newVehicle.capacity" 
                        ng-options="selectSeat.text for selectSeat in selectSeats track by selectSeat.value"
                        class = "selectSeat_newVehicleForm"
                        name = "selectSeats"
                        required
                        ng-class = "{error: addVehicleForm.selectSeats.$invalid && !addVehicleForm.selectSeats.$pristine}">                	
                        <option value="">-- Select Seats --</option>
                    </select>
               </div>
<!--               <span ng-show = "invalidSeatSelection">* In-valid Seat Selection</span>-->
			</div>
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Registration Certificate Number</label>
                    <input type="text" 
                           ng-model="newVehicle.regCert" 
                           class="form-control" 
                           placeholder = "Registration Certificate"
                           name = 'regCert'
                           required
                           ng-minlength="0"
                           ng-maxlength="30"
                           maxlength = '15'
                           ng-class = "{error: addVehicleForm.regCert.$invalid && !addVehicleForm.regCert.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVehicleForm.regCert.$error.maxlength">* In-valid Registration Number</span>
           </div>  
           
           <div class = "col-md-4 col-xs-12">
           <label>Registration Date</label>
            <div class = "input-group calendarInput">
                <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="registrationDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="newVehicle.registrationDate" 
                      class="form-control" 
                      placeholder = "Registration Date"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.registrationDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      name = 'registrationDate'
                      required
                      readonly
                      ng-class = "{error: addVehicleForm.registrationDate.$invalid && !addVehicleForm.registrationDate.$pristine}">            
             </div>
           </div>
           
           <div class = "col-md-4 col-xs-12">
           <label>Pollution Expiry Date</label>
            <div class = "input-group calendarInput">
                <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="pollutionExpDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="newVehicle.pollutionExpDate" 
                      class="form-control" 
                      placeholder="Pollution Expiry Date"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.pollutionExpDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      required
                      readonly
                      name = 'pollutionExpDate'
                      ng-class = "{error: addVehicleForm.pollutionExpDate.$invalid && !addVehicleForm.pollutionExpDate.$pristine}">               
             </div>
           </div>         
          
           
           
           
           <!-- <div class = "col-md-4 col-xs-12">
               <div>
               
                tooltip="Insurance Number"
                tooltip-placement="top"
                tooltip-trigger="mouseenter">
                   <input type="text" 
                          ng-model="newVehicle.InsuranceNumber" 
                          class="form-control" 
                          placeholder = "Insurance Number"
                          name = 'InsuranceNumber'
                          required
                          ng-minlength="0"
                          ng-maxlength="15"
                          ng-class = "{error: addVehicleForm.InsuranceNumber.$invalid && !addVehicleForm.InsuranceNumber.$pristine}">
               </div>
              <span class = "hintModal" ng-show="addVehicleForm.InsuranceNumber.$error.maxlength">* In-valid Inssurance Number</span>
           </div>  -->
           <div class = "col-md-4 col-xs-12">
           <label>Insurance Expiry Date</label>
            <div class = "input-group calendarInput">
                <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="InsuranceExpiryCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="newVehicle.InsuranceDate" 
                      class="form-control" 
                      placeholder = "Insurance Expiry Date"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.InsuranceExpiryDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      name = 'InsuranceExpiry'
                      required
                      readonly
                      ng-class = "{error: addVehicleForm.InsuranceExpiry.$invalid && !addVehicleForm.InsuranceExpiry.$pristine}">
            
             </div>
           </div> 
        <!--    <div class = "col-md-4 col-xs-12">
               <div
                tooltip="Road Tax"
                tooltip-placement="top"
                tooltip-trigger="mouseenter">
                    <input type="text" 
                           ng-model="newVehicle.roadTax" 
                           class="form-control" 
                           placeholder = "Road Tax"
                           name = 'roadTax'
                           required
                           ng-minlength="0"
                           ng-maxlength="15"
                           ng-class = "{error: addVehicleForm.roadTax.$invalid && !addVehicleForm.roadTax.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVehicleForm.roadTax.$error.maxlength">* In-valid Road Tax</span>
           </div>  -->
           <div class = "col-md-4 col-xs-12">
           <label>Tax Valid</label>
            <div class = "input-group calendarInput">
                <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="TaxValidCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="newVehicle.TaxValid" 
                      class="form-control" 
                      placeholder = "Tax Valid"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.TaxValidDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      name = 'TaxValid'
                      required
                      readonly
                      ng-class = "{error: addVehicleForm.TaxValid.$invalid && !addVehicleForm.TaxValid.$pristine}">
            
             </div>
           </div>   
           <div class = "col-md-4 col-xs-12">
           <label>Permit Valid</label>
             <div class = "input-group calendarInput">
                  <span class="input-group-btn">
                      <button class="btn btn-default" ng-click="PermitValidCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="newVehicle.PermitValid" 
                      class="form-control" 
                      placeholder = "Permit Valid"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.PermitValidDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      name = 'PermitValid'
                      required
                      readonly
                      ng-class = "{error: addVehicleForm.PermitValid.$invalid && !addVehicleForm.PermitValid.$pristine}">
               
             </div>
           </div>
           
           <div class = "col-md-4 col-xs-12">
           <label>Vehicle Fitness Certification Date</label>
            <div class = "input-group calendarInput">
                <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="vehicleFitnessCertCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="newVehicle.vehicleFitnessCert" 
                      class="form-control" 
                      placeholder = "Vehicle Fitness Certification Date"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.vehicleFitnessCert" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      name = 'vehicleFitnessCert'
                      required
                      readonly
                      ng-class = "{error: addVehicleForm.vehicleFitnessCert.$invalid && !addVehicleForm.vehicleFitnessCert.$pristine}">            
             </div>
           </div>  
       
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Vehicle Contact Person</label>
                    <input type="text" 
                           ng-model="newVehicle.contactName" 
                           class="form-control" 
                           placeholder = "Contact Person"
                           name = 'contactName'
                           required
                           ng-minlength="0"
                           ng-maxlength="15"
                           ng-class = "{error: addVehicleForm.contactName.$invalid && !addVehicleForm.contactName.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVehicleForm.contactName.$error.maxlength">* In-valid Contact Name</span>
           </div>    
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contract Type</label>
                    <input type="text" 
                           ng-model="newVehicle.conType" 
                           class="form-control" 
                           placeholder = "Contract Type"
                           name = 'conType'
                           required
                           ng-minlength="0"
                           ng-maxlength="30"
                           ng-class = "{error: addVehicleForm.conType.$invalid && !addVehicleForm.conType.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVehicleForm.regCert.$error.maxlength">* In-valid contractType</span>
           </div>
           
           <div class = "col-md-4 col-xs-12">
               <div>
               <label>Contract Tariff Id</label>
                    <input type="text" 
                           ng-model="newVehicle.conTariffId" 
                           class="form-control" 
                           placeholder = "Contract Tariff Id"
                           name = 'conTariffId'
                           required
                           ng-pattern = 'IntegerNumber'
                           ng-minlength="0"
                           ng-maxlength="2"
                           maxlength = '2'
                           ng-class = "{error: addVehicleForm.conTariffId.$invalid && !addVehicleForm.conTariffId.$pristine}">
               </div>
               <span class = "hintModal" ng-show="addVehicleForm.regCert.$error.maxlength">* In-valid Contract Tariff Id</span>
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
	<button type="button" class="btn btn-info" ng-click = "addVehicle(newVehicle)" ng-disabled="addVehicleForm.$invalid">Save</button> 
    <button type="button" class="btn btn-default" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>