<div class = "invoiceTemplate container-fluid">
    <div class = "row">
        <div class = "col-md-12 col-xs-12 invoiceHeading1">            
            <span class = "col-md-12 col-xs-12">Invoicing</span>                      
            <div class = "col-md-12 col-xs-12 mainTabDiv_invoice">

            <!-- /*START OF WRAPPER1 = VENDORWISE INVOICE */ -->
               <div class = "wrapper1">             
                
                <tabset class="tabset subTab_invoice">
                  <tab ng-click = "">
                    <tab-heading ng-click = 'vendorTab()'>By Vendor</tab-heading>
                        <form class = "invoiceTabContent row" name = "invoiceByVendor">
                           <table class = "invoiceByVendorTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Trip Sheet for the Vendor</th>
                                      <th>Monthly Summary</th>
                                      <th>Invoice Number</th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody>
		                           <tr>
                                      <td class = "col-md-2">
                                          <select ng-model="vendors.selectVendor" 
                                                  ng-options="vendorData.name for vendorData in vendorsData"
                                                  name = "selectVendor"
                                                  class = 'vendorSelect_invoiceByVendorForm'
                                                  ng-disabled = 'isInvoice'
                                                  ng-class = "{error: invoiceByVendor.selectVendor.$invalid && !invoiceByVendor.selectVendor.$pristine}">                	
                                                  <option value="">-- Select Vendors --</option>
                                          </select> 
                                       </td>
		                              <td class = "col-md-2">
                                          <div class = "input-group calendarInput">
                                          <span class="input-group-btn"><button class="btn btn-default" ng-click="selectMonthCal($event)"
                                                                                ng-disabled = 'isInvoice'>
                                              <i class = "icon-calendar calInputIcon"></i></button></span>
                                              <input type="text" 
                                                     class="form-control" 
                                                     ng-model="vendors.monthSelected" 
                                                     placeholder = "Select Month"
                                                     datepicker-popup = '{{monthformat}}'
                                                     datepicker-options="{{datepickerOptions}}"
                                                     is-open="datePicker.selectMonth"
                                                     readonly
                                                     show-button-bar = false>
                                          </div>
                                       
		                              <td class = "col-md-2 invoiceNumber_invoice">
                                         <div class = "floatLeft">OR</div>
                                          <input type = "text" 
                                                 ng-model = "vendors.invoiceNumber"
                                                 placeholder = "Enter Invoice Number" 
                                                 class = "form-control floatLeft"
                                                 typeahead="invoiceNumberData.invoiceNumber for invoiceNumberData in invoiceNumbersData | filter:$viewValue | limitTo:8">
                                       </td>
                                      <td class = "col-md-4">
                                          <input type = 'button' 
                                                 class = 'btn btn-success searchButton_Invoice' 
                                                 value = "Get Invoice"
                                                 ng-click = "getInvoiceByVendor(vendors)">
                                       </td>
		                            </tr>                                    
		                         </tbody>
                            </table>
                            <div ng-if = 'gotVendorData' class = 'row marginRow invoiceMainDiv'>
                              <div class = "col-md-12 col-xs12 invoiceHeader">
                              </div>
                              <div class = "col-md-12 col-xs12 mainTabDiv_invoice">
                                        <div class = "invoiceTabContent">
                                            <div class = "row marginRow">
                                                <div class= "col-md-6 col-xs-12">
                                                    <span class = "vendorName_invoice">Vendor Name - </span>
                                                    <span class = "vendorName_invoice">{{vendorName}}</span>
                                                </div>
                                                <div class= "col-md-6 col-xs-12 invoiceDateInfo">
                                                    <div><span class = "floatRight">Invoice Number :  {{invoiceNumber}}</span></div><br>
                                                    <div><span class = "floatRight">Month and Year :  {{invoiceMonthDate}}</span></div><br>
                                                    <div><span class = "floatRight">Created Date : {{invoiceCreationDate}}</span></div>
                                                </div>
                                            </div>
                                            
                                            <div ng-show = "!viewSummaryClicked" class = "row invoiceDetail">
                                                <div class = "col-md-3">Total Number of Vehicles</div>
                                                <div class = "col-md-6">{{noOfvehicle}}</div>
                                                <div class = "col-md-3">
                                                    <input type = 'text' 
                                                           class = "btn btn-success form-control"
                                                           value = 'View Complete Summary' 
                                                           ng-click = "viewSummary()">
                                                </div>

                                                <div class = "col-md-3">Total Amount</div>
                                                <div class = "col-md-9"><span class = "currency">Rs.</span>{{totalAmount}}</div>

                                                <div class = "col-md-3">Total Penalty</div>
                                                <div class = "col-md-9"><span class = "currency">Rs.</span>{{penalty}}</div>

                                                <div class = "col-md-3">Total Payable Amount</div>
                                                <div class = "col-md-9"><span class = "currency">Rs.</span>{{totalPayableAmount}}</div>

                                                <!-- <div class = "col-md-3">Service Tax Percentage</div>
                                                <div class = "col-md-9">{{serviceTax}}<span class = "currency">%</span></div> -->

                                                <div class = "col-md-3">Service Tax ({{serviceTax}}<span class = "currency">%</span>)</div>
                                                <div class = "col-md-9"><span class = "currency">Rs.</span>{{serviceTaxAmount}}</div>

                                                <div class = "col-md-3">Grand Total</div>
                                                <div class = "col-md-9"><span class = "currency">Rs.</span>{{total}}</div>
                                            </div>
                                            
                                            <div ng-show = "viewSummaryClicked" class = "row invoiceSummary">
                                                <div class = "col-md-12">
                                                    <input type = 'text' 
                                                           class = "btn btn-success backToButton floatRight"
                                                           value = 'Back To Invoice Detail' 
                                                           ng-click = "backToDetails()">
                                                </div>
                                                <div class = "col-md-12 tripTypeDiv"
                                                     ng-show = "vendorFixedDistanceBasedData.length>0">
                                                    <div>Contract Type : Fixed Distance Base</div>
                                                </div>
                                                <table class = "invoiceByVendorTable table table-responsive container-fluid"
                                                       ng-show = "vendorFixedDistanceBasedData.length>0">
                                                    <thead class ="tableHeading">
                                                        <tr>
                                                            <th><div><span>Vehicle Number</span></div></th>
                                                            <th><div><span>Total (km)</span></div></th>
                                                            <th><div><span>Extra (km)</span></div></th>
                                                            <th><div><span>Total Number of Days</span></div></th>
                                                            <th><div><span>Fixed Charges (Rs.)</span></div></th>
                                                            <th><div><span>Extra Charges (Rs.)</span></div></th>
                                                            <th><div><span>Penalty (Rs.)</span></div></th>
                                                            <th><div><span>Total (Rs.)</span></div></th>
                                                        </tr> 
                                                    </thead>
                                                    <tbody>
                                                        <tr ng-repeat = 'distanceBase in vendorFixedDistanceBasedData'>	                                       
                                                          <td class='col-md-1'>{{distanceBase.vehicleNumber}}</td>                                                          
                                                          <td class='col-md-1'>{{distanceBase.totalKm}}</td>		                                        
                                                          <td class='col-md-1'>{{distanceBase.extraKm}}</td>
                                                          <td class='col-md-1'>{{distanceBase.totalWorkingDays}}</td>
                                                          <td class='col-md-1'>{{distanceBase.fixedcharges}}</td>		                                        
                                                          <td class='col-md-1'>{{distanceBase.extraKmcharges}}</td>
                                                          <td class='col-md-1'>{{distanceBase.penalty}}</td>
                                                          <td class='col-md-1'>{{distanceBase.totalAmount}}</td>
                                                        </tr>                                    
                                                   </tbody>
                                                </table>
                                                <div class = "col-md-12 tripTypeDiv"
                                                     ng-show = "vendorTripBasedFixedDetailsData.length>0">
                                                    <div>Contract Type : Fixed Trip Base</div>
                                                </div>
                                                <table class = "invoiceByVendorTable table table-responsive container-fluid"
                                                       ng-show = "vendorTripBasedFixedDetailsData.length>0">
                                                    <thead class ="tableHeading">
                                                        <tr>
                                                            <th><div><span>Trip Id</span></div></th>
                                                            <th><div><span>Vehicle Number</span></div></th>
                                                            <th><div><span>Total Distance (km)</span></div></th>
                                                            <th><div><span>Extra Distance (km)</span></div></th>
                                                            <th><div><span>Fixed Charges (Rs.)</span></div></th>
                                                            <th><div><span>Extra Charges (Rs.)</span></div></th>
                                                            <th><div><span>Total (Rs.)</span></div></th>
                                                        </tr> 
                                                    </thead>
                                                    <tbody>
                                                        <tr ng-repeat = "tripBase in vendorTripBasedFixedDetailsData">
                                                          <td class='col-md-1'>{{tripBase.tripId}}</td>		   
                                                          <td class='col-md-1'>{{tripBase.vehicleNumber}}</td>                                     
                                                          <td class='col-md-1'>{{tripBase.totalKm}}</td>
                                                          <td class='col-md-1'>{{tripBase.extraKm}}</td>
                                                          <td class='col-md-1'>{{tripBase.fixedcharges}}</td>		                                        
                                                          <td class='col-md-1'>{{tripBase.extraKmcharges}}</td>
                                                          <td class='col-md-1'>{{tripBase.totalAmount}}</td>
                                                        </tr>                                    
                                                   </tbody>
                                                </table>
                                            </div>
                                        </div>
                                </div>
                            </div>
 						</form>
 					</tab>
                  <tab ng-click = "">
                    <tab-heading ng-click = "vehicleTab()">By Vehicle</tab-heading>
                        <form class = "invoiceTabContent row" name = "invoiceByVehicle">
                           <table class = "invoiceByVendorTable table table-hover table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                      <th>Trip Sheet for the Vendor</th>
                                      <th>Trip Sheet for the Vehicle</th>
                                      <th>Monthly Summary</th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody>
		                           <tr>
                                      <td class = "col-md-2">
                                          <select ng-model="vendors.selectVendor" 
                                                  ng-change = "getVehicles(vendors)"
                                                  ng-options="vendorData.name for vendorData in vendorsData"
                                                  name = "selectVendor"
                                                  class = 'vendorSelect_invoiceByVendorForm'
                                                  ng-class = "{error: invoiceByVendor.selectVendor.$invalid && !invoiceByVendor.selectVendor.$pristine}">                	
                                                  <option value="">-- Select Vendors --</option>
                                          </select>  
                                       </td>
                                       <td class = "col-md-2">
                                          <select ng-model="vendors.selectVehicle"
                                                  ng-options="selectVehicle.vehicleNumber for selectVehicle in selectVehicles"
                                                  ng-change = "setVehicleNumber(vendors.selectVehicle)"
                                                  name = "selectVehicle"
                                                  class = 'vendorSelect_invoiceByVendorForm'>                	
                                                  <option value="">-- Select Vehicles --</option>
                                          </select> 
                                       </td>
                                       
		                              <td class = "col-md-2">
                                          <div class = "input-group calendarInput">
                                          <span class="input-group-btn"><button class="btn btn-default" ng-click="selectMonthCal($event)">
                                              <i class = "icon-calendar calInputIcon"></i></button></span>
                                              <input type="text" 
                                                     class="form-control" 
                                                     ng-model="vendors.monthSelected" 
                                                     placeholder = "Select Month"
                                                     datepicker-popup = '{{monthformat}}'
                                                     datepicker-options="{{datepickerOptions}}"
                                                     is-open="datePicker.selectMonth"
                                                     show-button-bar = false>
                                          </div>
                                           
                                       </td>
                                      <td class = "col-md-4">
                                          <input type = 'button' 
                                                 class = 'btn btn-success searchButton_Invoice' 
                                                 value = "Get Invoice"
                                                 ng-click = "getInvoiceByVehicle(vendors)">
                                       </td>
		                            </tr>                                    
		                         </tbody>
                            </table>
                            <div ng-if = 'gotVehicleData' class = 'row marginRow invoiceMainDiv'>
                              <div class = "col-md-12 col-xs12 invoiceHeader">
                              </div>
                              <div class = "col-md-12 col-xs12 mainTabDiv_invoice">
                                        <div class = "invoiceTabContent">
                                            <div class = "row marginRow">
                                                <div class= "col-md-12 col-xs-12">
                                                    <span class = "vendorName_invoice">Vehicle Number</span>
                                                    <span class = 'vendorName_invoice'>{{vehicleNumber}}</span>
                                                </div>
                                            </div>
                                            
                                            <div class = "row invoiceSummary">
                                                <div class = "col-md-12 tripTypeDiv"
                                                     ng-show = "vehicleFixedDistanceBasedData.length>0">
                                                    <div>Contract Type : Fixed Distance Base</div>
                                                </div>
                                                <table class = "invoiceByVendorTable table table-responsive container-fluid"
                                                       ng-show = "vehicleFixedDistanceBasedData.length>0">
                                                    <thead class ="tableHeading">
                                                        <tr>
                                                            <th><div><span>Vehicle Number</span></div></th>
                                                            <th><div><span>Total (km)</span></div></th>
                                                            <th><div><span>Extra (km)</span></div></th>
                                                            <th><div><span>Total Number of Days</span></div></th>
                                                            <th><div><span>Fixed Charges (Rs.)</span></div></th>
                                                            <th><div><span>Extra Charges (Rs.)</span></div></th>
                                                            <th><div><span>Penalty (Rs.)</span></div></th>
                                                            <th><div><span>Total (Rs.)</span></div></th>
                                                        </tr> 
                                                    </thead>
                                                    <tbody>
                                                        <tr ng-repeat = 'distanceBase in vehicleFixedDistanceBasedData'>		                                        
                                                          <td class='col-md-1'>{{distanceBase.vehicleNumber}}</td>                                                            
                                                          <td class='col-md-1'>{{distanceBase.totalKm}}</td>		                                        
                                                          <td class='col-md-1'>{{distanceBase.extraKm}}</td>
                                                          <td class='col-md-1'>{{distanceBase.totalWorkingDays}}</td>
                                                          <td class='col-md-1'>{{distanceBase.fixedcharges}}</td>		                                        
                                                          <td class='col-md-1'>{{distanceBase.extraKmcharges}}</td>
                                                          <td class='col-md-1'>{{distanceBase.penalty}}</td>
                                                          <td class='col-md-1'>{{distanceBase.totalAmount}}</td>
                                                        </tr>                                    
                                                   </tbody>
                                                </table>
                                                <div class = "col-md-12 tripTypeDiv"
                                                     ng-show = 'vehicleTripBasedFixedDetailsData.length>0'>
                                                    <div>Contract Type : Fixed Trip Base</div>
                                                </div>
                                                <table class = "invoiceByVendorTable table table-responsive container-fluid"
                                                       ng-show = 'vehicleTripBasedFixedDetailsData.length>0'>
                                                    <thead class ="tableHeading">
                                                        <tr>
                                                            <th><div><span>Trip Id</span></div></th>
                                                            <th><div><span>Vehicle Number</span></div></th>
                                                            <th><div><span>Total Distance (km)</span></div></th>
                                                            <th><div><span>Extra Distance (km)</span></div></th>
                                                            <th><div><span>Fixed Charges (Rs.)</span></div></th>
                                                            <th><div><span>Extra Charges (Rs.)</span></div></th>
                                                            <th><div><span>Total (Rs.)</span></div></th>
                                                        </tr> 
                                                    </thead>
                                                    <tbody>
                                                        <tr ng-repeat = "tripBase in vehicleTripBasedFixedDetailsData">
                                                          <td class='col-md-1'>{{tripBase.tripId}}</td>		   
                                                          <td class='col-md-1'>{{tripBase.vehicleNumber}}</td>                                     
                                                          <td class='col-md-1'>{{tripBase.totalKm}}</td>
                                                          <td class='col-md-1'>{{tripBase.extraKm}}</td>
                                                          <td class='col-md-1'>{{tripBase.fixedcharges}}</td>		                                        
                                                          <td class='col-md-1'>{{tripBase.extraKmcharges}}</td>
                                                          <td class='col-md-1'>{{tripBase.totalAmount}}</td>
                                                        </tr>                                    
                                                   </tbody>
                                                </table>
                                            </div>
                                        </div>
                                </div>
                            </div>
 						</form>
                    </tab> 					
 				</tabset>
            </div>
        </div>
    </div>
   </div>
</div>