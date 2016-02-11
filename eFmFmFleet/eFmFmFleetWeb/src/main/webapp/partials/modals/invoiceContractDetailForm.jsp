<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "addContractFormModalTemplate">
	
	<div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-pencil addNewModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Add New Contract Details</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>
    <div class="modal-body modalMainContent">
       <form class = "addUserForm" name = "addContractForm">
<!--
           <div class = "col-md-6 col-xs-12 form-group"> 
              <label>Vendor Name</label>
                   <input type="text" 
                      ng-model="contract.extraDistanceChargeRate"
                      class="form-control" 
                      placeholder = "Extra Distance Charge Rate"
                      name = 'extracharRate' 
                      ng-pattern = 'IntegerNumber'
                      ng-minlength="2"
                      ng-maxlength="15"
                      required
                      ng-class = "{error: addContractForm.extracharRate.$invalid && !addContractForm.extracharRate.$pristine}"> 
                    
           </div>     
-->
           <div class = "col-md-6 col-xs-12 form-group"> 
              <label>Extra Distance Charge Rate</label>
                   <input type="text" 
                      ng-model="contract.extraDistanceChargeRate"
                      class="form-control" 
                      placeholder = "Extra Distance Charge Rate"
                      name = 'extracharRate' 
                      ng-pattern = 'IntegerNumber'
                      ng-minlength="2"
                      ng-maxlength="15"
                      required
                      ng-class = "{error: addContractForm.extracharRate.$invalid && !addContractForm.extracharRate.$pristine}"> 
           </div>         
           
           <div class = "col-md-6 col-xs-12 form-group"> 
              <label>Fixed Distance Charge Rate</label>
                   <input type="text" 
                      ng-model="contract.fixedDistanceChargeRate"
                      class="form-control" 
                      placeholder = "Fixed Distance Charge Rate"
                      name = 'fixedcharRate' 
                      ng-pattern = 'IntegerNumber'
                      ng-minlength="2"
                      ng-maxlength="15"
                      required
                      ng-class = "{error: addContractForm.fixedcharRate.$invalid && !addContractForm.fixedcharRate.$pristine}"> 
           </div>  
           <div class = "col-md-6 col-xs-12 form-group">               
              <label>Fixed Distance Monthly</label>
                   <input type="text" 
                      ng-model="contract.fixedDistanceChargeMonthly"
                      class="form-control" 
                      placeholder = "Fixed Distance Charge Monthly"
                      name = 'fixedcharMonthly' 
                      ng-pattern = 'IntegerNumber'
                      ng-minlength="2"
                      ng-maxlength="15"
                      required
                      ng-class = "{error: addContractForm.fixedcharMonthly.$invalid && !addContractForm.fixedcharMonthly.$pristine}">  
           </div>   
           <div class = "col-md-6 col-xs-12 form-group">             
              <label>Fixed Distance per day</label>
                   <input type="text" 
                      ng-model="contract.fixedDistanceperDay"
                      class="form-control" 
                      placeholder = "Fixed Distance per day"
                      name = 'fixedcharperDay' 
                      ng-pattern = 'IntegerNumber'
                      ng-minlength="2"
                      ng-maxlength="15"
                      required
                      ng-class = "{error: addContractForm.fixedcharperDay.$invalid && !addContractForm.fixedcharperDay.$pristine}">  
           </div>     
           <div class = "col-md-6 col-xs-12 form-group">            
              <label>Minimum days</label>
                   <input type="text" 
                      ng-model="contract.minimumDays"
                      class="form-control" 
                      placeholder = "Minimum days"
                      name = 'minimumDays' 
                      ng-pattern = 'IntegerNumber'
                      ng-minlength="1"
                      ng-maxlength="15"
                      required
                      ng-class = "{error: addContractForm.minimumDays.$invalid && !addContractForm.minimumDays.$pristine}"> 
           </div>         
           <div class = "col-md-6 col-xs-12 form-group">            
              <label>Penalty</label>
                   <input type="text" 
                      ng-model="contract.penalty"
                      class="form-control" 
                      placeholder = "Penalty"
                      name = 'penalty' 
                      ng-pattern = 'IntegerNumber'
                      ng-minlength="2"
                      ng-maxlength="15"
                      required
                      ng-class = "{error: addContractForm.penalty.$invalid && !addContractForm.penalty.$pristine}"> 
           </div>
       </form>
    </div>      
<div class="modal-footer modalFooter"> 
	<button type="button" class="btn btn-success buttonRadius0" ng-click = "addContract(contract)" ng-disabled= "addContractForm.$invalid">Add New Contract</button> 
    <button type="button" class="btn btn-default buttonRadius0" ng-click = "cancel()">Cancel</button>    
</div>
     
</div>