<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "editVehicleFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Edit Vehicle</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
    <div class = "formWrapper">
       <form name = "editVehicleForm" class = "editVehicleForm">
           
           <div class = "col-md-4 col-xs-12">
               <label for = "vehicleName">Vehicle Make</label>
               <input type="text" 
                      ng-model="editVehicle.vehicleName" 
                      class="form-control" 
                      placeholder = "Vehicle Make"
                      name = 'vehicleName'
                      required
                      ng-minlength="0"
                      ng-maxlength="15"
                      ng-class = "{error: editVehicleForm.vehicleName.$invalid && !editVehicleForm.vehicleName.$pristine}">
               
           </div>
               <div class = "col-md-4 col-xs-12">
               <label for = "insurName">Vehicle Model</label>
               <input type="text" 
                          ng-model="editVehicle.vehicleModel" 
                          class="form-control" 
                          placeholder = "Vehicle Model"
                          name = 'InsuranceComp'
                          required
                          ng-minlength="0"
                          ng-maxlength="30"
                          ng-class = "{error: editVehicleForm.InsuranceComp.$invalid && !editVehicleForm.InsuranceComp.$pristine}">
               <span></span>
           </div> 
           <div class = "col-md-4 col-xs-12">
               <label for = "InsuranceNumber">Vehicle Model Year</label>
                   <input type="text" 
                          ng-model="editVehicle.modelYear" 
                          class="form-control" 
                          placeholder = "Vehicle Model Year"
                          name = 'InsuranceNumber'
                          required
                          ng-minlength="0"
                          ng-maxlength="15"
                          ng-class = "{error: editVehicleForm.InsuranceNumber.$invalid && !editVehicleForm.InsuranceNumber.$pristine}">
               <span></span>
           </div> 
           <div class = "col-md-4 col-xs-12">
                <label for = "vehicleNum">Vehicle Number</label>
                   <input type="text" 
                          ng-model="editVehicle.vehicleNumber" 
                          class="form-control" 
                          placeholder = "Vehicle Number"
                          name = 'vehicleNumber'
                          required
                          ng-minlength="0"
                          ng-maxlength="15"
                          ng-class = "{error: editVehicleForm.vehicleNumber.$invalid && !editVehicleForm.vehicleNumber.$pristine}">
               <span class = "hintModal" ng-show="editVehicleForm.vehicleNumber.$error.maxlength">* In-valid Driver Number</span>
           </div>   
           <div class = "col-md-4 col-xs-12">
               <label for = "vehicleContact">Vehicle Engine Number</label>
               <input type="text" 
                          ng-model="editVehicle.contactNo" 
                          class="form-control" 
                          placeholder = "Vehicle Engine Number"
                          name = 'contactNumber'
                          required
                          ng-minlength="0"
                          ng-maxlength="15"
                          ng-class = "{error: editVehicleForm.contactNumber.$invalid && !editVehicleForm.contactNumber.$pristine}">
               <span class = "hintModal" ng-show="editVehicleForm.contactNumber.$error.maxlength">* In-valid Contact Number</span>
           </div>
       
           <!-- <div class = "col-md-4 col-xs-12">
               <label for = "vehicleContactName">Contact Name</label>               
               <input type="text" 
                          ng-model="editVehicle.contactName" 
                          class="form-control" 
                          placeholder = "Vehicle Contact Name"
                          name = 'contactName'
                          required
                          ng-minlength="0"
                          ng-maxlength="15"
                          ng-class = "{error: editVehicleForm.contactName.$invalid && !editVehicleForm.contactName.$pristine}">
               <span class = "hintModal" ng-show="editVehicleForm.contactName.$error.maxlength">* In-valid Contact Name</span>
           </div>       -->      
          
           <div class = "col-md-4 col-xs-12">
               <label for = "vehicleCapacity">Vehicle Capacity</label>
				<select ng-model="editVehicle.capacity" 
                	ng-options="selectSeat.text for selectSeat in selectSeats track by selectSeat.value"
                    name = "selectSeats"
                    class = 'selectSeat_editVehicleForm'
                    required
                    ng-class = "{error: editVehicleForm.selectSeats.$invalid && !editVehicleForm.selectSeats.$pristine}">                	
      				<option value="">-- Select Seats --</option>
    			</select>
           </div>
           
           <div class = "col-md-4 col-xs-12">
<!--
               <div
                tooltip="Registration Certificate"
                tooltip-placement="top"
                tooltip-trigger="mouseenter">
-->
               <label for = "vehicleCapacity">Contract Type</label>
               <input type="text" 
                          ng-model="editVehicle.conType" 
                          class="form-control" 
                          placeholder = "Contract Type"
                          name = 'conType'
                          required
                          ng-minlength="0"
                          ng-maxlength="30"
                          ng-class = "{error: editVehicleForm.conType.$invalid && !editVehicleForm.conType.$pristine}">
<!--               </div>-->
               <span class = "hintModal" ng-show="editVehicleForm.conType.$error.maxlength">* In-valid Contract Type</span>
           </div>
           
 <!--           
            <div class = "col-md-4 col-xs-12">

               <div
                tooltip="Registration Certificate"
                tooltip-placement="top"
                tooltip-trigger="mouseenter">

               <label for = "vehicleCapacity">Contract Tariff Id</label>
               <input type="text" 
                          ng-model="editVehicle.conTariffId" 
                          class="form-control" 
                          placeholder = "Contract Tariff Id"
                          name ='conTariffId'
                          required
                          ng-pattern = 'IntegerNumber'
                          ng-minlength="2"
                          ng-maxlength="2"
                          maxlength = '2'
                          ng-class = "{error: editVehicleForm.conTariffId.$invalid && !editVehicleForm.conTariffId.$pristine}">
              </div>
               <span class = "hintModal" ng-show="editVehicleForm.conTariffId.$error.maxlength">* In-valid Contract Tariff Id</span>
           </div>  -->         
           
           <div class = "col-md-4 col-xs-12">
               <label for = "vehicleCapacity">Vehicle Registration Number</label>
               <input type="text" 
                          ng-model="editVehicle.regCert" 
                          class="form-control" 
                          placeholder = "Registration Certificate"
                          name = 'regCert'
                          required
                          ng-minlength="0"
                          ng-maxlength="30"
                          ng-class = "{error: editVehicleForm.regCert.$invalid && !editVehicleForm.regCert.$pristine}">
               <span class = "hintModal" ng-show="editVehicleForm.regCert.$error.maxlength">* In-valid Registration Number</span>
           </div> 
           
           <div class = "col-md-4 col-xs-12">
           <label>Registration Date</label>
            <div class = "input-group calendarInput">
                <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="registrationDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="editVehicle.registrationDate" 
                      class="form-control" 
                      placeholder = "Registration Date"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.registrationDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      name = 'registrationDate'
                      required
                      readonly
                      ng-class = "{error: editVehicleForm.registrationDate.$invalid && !editVehicleForm.registrationDate.$pristine}">            
             </div>
           </div>
          
           <div class = "col-md-4 col-xs-12">
               <label for = "pollutionexDate">Pollution Expiry Date</label>
               <div class = "input-group calendarInput">
                   <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="pollutionExpDateCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
                <input type="text" 
                       ng-model="editVehicle.pollutionExpDate" 
                       class="form-control" 
                       placeholder="Pollution Expiry Date"
                       datepicker-popup = '{{format}}'
                       is-open="datePicker.pollutionExpDate" 
                       show-button-bar = false
                       datepicker-options = 'dateOptions'
                       required
                       name = 'pollutionExpDate'
                       ng-class = "{error: editVehicleForm.pollutionExpDate.$invalid && !editVehicleForm.pollutionExpDate.$pristine}">
                
               </div>
           </div>         
       
           <div class = "col-md-4 col-xs-12">
             <label for = "InsuranceExpiryDate">Insurance Expiry Date</label> 
             <div class = "input-group calendarInput">
                 <span class="input-group-btn">
                     <button class="btn btn-default" ng-click="InsuranceExpiryCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="editVehicle.InsuranceDate" 
                      class="form-control" 
                      placeholder = "Insurance Expiry Date"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.InsuranceExpiryDate" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      name = 'InsuranceExpiry'
                      required
                      ng-class = "{error: editVehicleForm.InsuranceExpiry.$invalid && !editVehicleForm.InsuranceExpiry.$pristine}">
                
             </div>
           </div> 
           
           <div class = "col-md-4 col-xs-12">
           <label>Vehicle Fitness Certification Date</label>
            <div class = "input-group calendarInput">
                <span class="input-group-btn">
                    <button class="btn btn-default" ng-click="vehicleFitnessCertCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
               <input type="text" 
                      ng-model="editVehicle.vehicleFitnessCert" 
                      class="form-control" 
                      placeholder = "Vehicle Fitness Certification Date"
                      datepicker-popup = '{{format}}'
                      is-open="datePicker.vehicleFitnessCert" 
                      show-button-bar = false
                      datepicker-options = 'dateOptions'
                      name = 'vehicleFitnessCert'
                      required
                      readonly
                      ng-class = "{error: editVehicleForm.vehicleFitnessCert.$invalid && !editVehicleForm.vehicleFitnessCert.$pristine}">            
             </div>
           </div>          
           
           <div class = "col-md-4 col-xs-12">
	             <label for = "PermitValid">Permit Valid Date</label> 
	             <div class = "input-group calendarInput">
	                 <span class="input-group-btn">
	                     <button class="btn btn-default" ng-click="PermitValidCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
	               <input type="text" 
	                      ng-model="editVehicle.PermitValid" 
	                      class="form-control" 
	                      placeholder = "Permit Valid"
	                      datepicker-popup = '{{format}}'
	                      is-open="datePicker.PermitValidDate" 
	                      show-button-bar = false
	                      datepicker-options = 'dateOptions'
	                      name = 'PermitValid'
	                      required
	                      ng-class = "{error: editVehicleForm.PermitValid.$invalid && !editVehicleForm.PermitValid.$pristine}">
	                
	             </div>
           </div> 
           
             <div class = "col-md-4 col-xs-12">
             		<label for = "TaxValid">Tax Valid Date</label>
		            <div class = "input-group calendarInput">
		                <span class="input-group-btn">
		                    <button class="btn btn-default" ng-click="TaxValidCal($event)"><i class = "icon-calendar calInputIcon"></i></button></span>
		               <input type="text" 
		                      ng-model="editVehicle.TaxValid" 
		                      class="form-control" 
		                      placeholder = "Tax Valid"
		                      datepicker-popup = '{{format}}'
		                      is-open="datePicker.TaxValidDate" 
		                      show-button-bar = false
		                      datepicker-options = 'dateOptions'
		                      name = 'TaxValid'
		                      required
		                      ng-class = "{error: editVehicleForm.TaxValid.$invalid && !editVehicleForm.TaxValid.$pristine}">
		            
		             </div>
           </div>
<!--           <div class = "clearfix"></div>-->
      <!--      <div class = "col-md-8 col-xs-12">
           <div ng-repeat = "doc in vehicleDocuments" class = "floatLeft" id = "div{{$index}}">
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
               <input type="text" ng-model="editVehicle.numCabs" class="form-control">
           </div> 
           <div class = "col-md-4 col-xs-12 hidden">
               <input type="text" ng-model="editVehicle.vehicleId" class="form-control">
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
	<button type="button" class="btn btn-warning" ng-click = "updateVehicle(editVehicle)" ng-disabled="editVehicleForm.$invalid">Save</button> 
    <button type="button" class="btn btn-default" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>