(function(){
	
   var invoiceCtrl = function($scope, $state, $http, $timeout, INVOICE){  
       $scope.viewSummaryClicked = false;
       $scope.gotVendorData = false;
       $scope.gotVehicleData = false;
       $scope.isInvoice = false;
       $scope.vendors = {'invoiceNumber': ''};
       $scope.vehicleNumber;
       
        $scope.$on('$viewContentLoaded', function() { 
		   $scope.getActiveVendors();	
           $scope.getInvoiceNumbers();
		});
       
       $scope.getActiveVendors = function(){
            var data = {
                  branchId:branchId
               };
            $http.post('services/contract/allActiveVendor/',data).
                success(function(data, status, headers, config) {
//                      alert(JSON.stringify(data));
                      $scope.vendorsData = data; 
                }).
                error(function(data, status, headers, config) {
                      // log error
                });
       };  
       
       $scope.getInvoiceNumbers = function(){
           var data = {branchId:branchId};
           $http.post('services/contract/listOfInvoiceDetails/',data).
                 success(function(data, status, headers, config) {
//                    alert(JSON.stringify(data));
                    $scope.invoiceNumbersData = data;
                 }).
                 error(function(data, status, headers, config) {
                      // log error
                 });
       };
       
       var convertMonthYear = function(d){
           var month = d.getMonth()+1;
           var year = d.getFullYear()
           if(month<10){month = '0'+month;}
           return month+'-'+year;           
       };     
       
       $scope.$watch(
                    function( $scope ) {
                        console.log( "Function watched" );
                        if($scope.vendors.invoiceNumber){
                            $scope.isInvoice = true;
                        }
                        else $scope.isInvoice = false;
                        return $scope.vendors.invoiceNumber;
                        
                    },
                    function( newValue, oldvalue ) {
                        if(oldvalue!==newValue){
                            console.log('Invoice New Value is : ' + newValue);
//                            $scope.isInvoice = true;
                        }
                    }
                ); 
       
       $scope.vendorTab = function(){
           $scope.vendors = {'selectVendor' :'', 'monthSelected':'', 'invoiceNumber':''};
       };
       

        $scope.getInvoiceByVendor = function(vendor){
            $scope.progressbar.start();
            $scope.gotVendorData = false;
            
            //if invoice number is not entered
            if(!vendor.invoiceNumber || !$scope.vendors.invoiceNumber){  
                if(!$scope.vendors.selectVendor || !$scope.vendors.monthSelected){
                    $scope.showalertMessage('Please select Vendor and Date', '');
                }
                else{
                    var d = new Date(vendor.monthSelected);
                    var actionTypes="VENDORBASED";  
                    var vendorId=vendor.selectVendor.vendorId;                  
                    var monthYear=convertMonthYear(d);
                    alert(vendorId);
                    alert(monthYear);
                    var data = {invoiceDate:monthYear,
                                actionType:actionTypes,
                                efmFmVendorMaster:{vendorId:vendorId,eFmFmClientBranchPO:{branchId:branchId}}
                               };
                    console.log(data);
                    $http.post('services/contract/invoiceTripDetails/',data).
                          success(function(data, status, headers, config) {
                        	  alert(JSON.stringify(data));
                            $scope.invoiceByVendor = data;
                            if(Object.keys($scope.invoiceByVendor).length == 0){
                                $scope.showalertMessage('No Invoice found. Please try again!', '');
                                $scope.gotVendorData = false;
                                $scope.progressbar.complete();
                            }
                            else{
                                $scope.vendorFixedDistanceBasedData = data.fixedDistanceBased;
                                $scope.vendorTripBasedFixedDetailsData = data.tripBasedFixedDetails;

                                $scope.vendorName = $scope.invoiceByVendor.vendorName;
                                $scope.invoiceNumber = $scope.invoiceByVendor.invoiceNumber;
                                $scope.invoiceMonthDate = $scope.invoiceByVendor.invoiceMonthDate;
                                $scope.invoiceCreationDate = $scope.invoiceByVendor.invoiceCreationDate;
                                $scope.noOfvehicle = $scope.invoiceByVendor.noOfvehicle;
                                $scope.totalAmount = $scope.invoiceByVendor.totalAmount;
                                $scope.totalPayableAmount = $scope.invoiceByVendor.totalPayableAmount;
                                $scope.total = $scope.invoiceByVendor.total;
                                $scope.serviceTax = $scope.invoiceByVendor.serviceTax;
                                $scope.serviceTaxAmount = $scope.invoiceByVendor.serviceTaxAmount;
                                $scope.penalty = $scope.invoiceByVendor.penalty;                    

                                $scope.gotVendorData = true;
                                $scope.viewSummaryClicked = false;                                
                                $scope.vendors = {'selectVendor' :'', 'monthSelected':'', 'invoiceNumber':''};
                                $scope.progressbar.complete();
                            }
                          }).
                          error(function(data, status, headers, config) {
                            // log error
                          }); 
                }
                //
            }
            //if Invoice is entered
            else{   
//                399401908
                var actionTypes="INVOICEDETAILS";           
                var data = {actionType:actionTypes,
                            invoiceNumber:vendor.invoiceNumber,
                            efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}}
                           }; 
       
                $http.post('services/contract/invoiceTripDetails/',data).
                      success(function(data, status, headers, config) {
                            $scope.invoiceByVendor = data; 
                            $scope.progressbar.complete();
                            if(Object.keys($scope.invoiceByVendor).length == 0){
                                $scope.showalertMessage('No Invoice found. Please try again!', '');
                                $scope.gotVendorData = false;
                            }
                            else{
                                $scope.vendorFixedDistanceBasedData = data.fixedDistanceBased;
                                $scope.vendorTripBasedFixedDetailsData = data.tripBasedFixedDetails;

                                $scope.vendorName = $scope.invoiceByVendor.vendorName;
                                $scope.invoiceNumber = $scope.invoiceByVendor.invoiceNumber;
                                $scope.invoiceMonthDate = $scope.invoiceByVendor.invoiceMonthDate;
                                $scope.invoiceCreationDate = $scope.invoiceByVendor.invoiceCreationDate;
                                $scope.noOfvehicle = $scope.invoiceByVendor.noOfvehicle;
                                $scope.totalAmount = $scope.invoiceByVendor.totalAmount;
                                $scope.totalPayableAmount = $scope.invoiceByVendor.totalPayableAmount;
                                $scope.total = $scope.invoiceByVendor.total;
                                $scope.serviceTax = $scope.invoiceByVendor.serviceTax;
                                $scope.serviceTaxAmount = $scope.invoiceByVendor.serviceTaxAmount;
                                $scope.penalty = $scope.invoiceByVendor.penalty;                    

                                $scope.gotVendorData = true;
                                $scope.viewSummaryClicked = false;
                                $scope.vendors = {'selectVendor' :'', 'monthSelected':'', 'invoiceNumber':''};
                            }
                       }).
                       error(function(data, status, headers, config) {
                                 // log error
                       });
//                alert(vendor.invoiceNumber);            
            }     
          };
       
       $scope.vehicleTab = function(){
           $scope.vendors = {'selectVendor' :'', 'monthSelected':'', 'invoiceNumber':''};
       };
       
       $scope.getVehicles = function(vendor){
//           alert(vendor.selectVendor.vendorId);
           var vendorId=vendor.selectVendor.vendorId;//Need to Pass the value
           var data = {vendorId:vendorId };       
           $http.post('services/contract/allActiveVehicle/',data).
                 success(function(data, status, headers, config) {
                    $scope.selectVehicles = data;
//                    alert(JSON.stringify(data));
                 }).
                 error(function(data, status, headers, config) {
                       // log error
                 });
       };
       
       $scope.setVehicleNumber = function(vehicle){
           $scope.vehicleNumber = vehicle.vehicleNumber;
       };
       
       $scope.getInvoiceByVehicle = function(vendor){ 
            $scope.progressbar.start();
//           alert(vendor.selectVendor.vendorId);           
           if(!$scope.vendors.selectVendor || !$scope.vendors.selectVehicle || !$scope.vendors.monthSelected){
                    $scope.showalertMessage('Please select Vendor, Vehicle and Date', '');
                }
           else{
        	   var d = new Date(vendor.monthSelected);
              // alert(vendor.selectVendor.vendorId+'-' + vendor.selectVehicle.vehicleId);       
               var actionTypes="VEHICLEBASED";
               var vehicleId=vendor.selectVehicle.vehicleId;
               var vendorId=vendor.selectVendor.vendorId;                             
               var monthYear=convertMonthYear(d);
               var data = {invoiceDate:monthYear,actionType:actionTypes,vehicleId:vehicleId,
                           efmFmVendorMaster:{vendorId:vendorId,eFmFmClientBranchPO:{branchId:branchId}}
                          };      
               $http.post('services/contract/invoiceTripDetails/',data).
                     success(function(data, status, headers, config) {
                            //alert(JSON.stringify(data));
                            $scope.vehicleFixedDistanceBasedData = data.fixedDistanceBased;
                            $scope.vehicleTripBasedFixedDetailsData = data.tripBasedFixedDetails;
                            $scope.progressbar.complete();
                            if(Object.keys(data).length == 0){
                                $scope.gotVehicleData = false;
                                $scope.showalertMessage('No Invoice found. Please try again!', '');
                            }
                            else{
                                $scope.gotVehicleData = true;
                                $scope.vendors = {'selectVendor' :'', 'monthSelected':'', 'invoiceNumber':''};
                            }
                            
                            
                     }).
                     error(function(data, status, headers, config) {
                             // log error
                     });   
           }
       };
          
        $scope.formats = ['yyyy','dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate','dd-mm-yyyy', 'mm-yyyy'];
        $scope.monthformat = 'MM-yyyy';

        $scope.datepickerOptions = {datepickerMode:"'month'",
                                    minMode:"month",
                                    minDate:"minDate",
                                    showWeeks:"false"};
       
        $scope.selectMonthCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'selectMonth' : true};  
             }, 50);
        };
       
       $scope.viewSummary = function(){
            $scope.viewSummaryClicked = true;       
       };
       
       $scope.backToDetails = function(){
            $scope.viewSummaryClicked = false;
       };
       
    };    
    
    angular.module('efmfmApp').controller('invoiceCtrl', invoiceCtrl);
    
}());