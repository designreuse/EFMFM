(function(){
    var importDataCtrl = function ($scope, $modal, $http, $timeout){
               //OPEN THE MODAL WHEN IMPORT EMPLOYEE CLICKED
       $scope.openImportEmployee = function(){
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/importEmployeeForm.jsp',
            controller: 'importEmployeeCtrl',
            resolve: {}
         }); 
       };  //End Of FUNCTION
       
       //OPEN THE MODAL WHEN IMPORT EMPLOYEE REQUEST CLICKED
       $scope.importEmployeeRequest = function(){
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/importCabRequest.jsp',
            controller: 'importEmployeeRequestCtrl',
            resolve: {}
         }); 
        };  //End Of FUNCTION
        
        
       //OPEN THE MODAL WHEN IMPORT EMPLOYEE VENDOR DATA CLICKED
       $scope.importVendorData = function(){
         var modalInstance = $modal.open({
         templateUrl: 'partials/modals/importVendorData.jsp',
         controller: 'importVendorMasterDataCtrl',
         resolve: {}
         }); 
       };  //End Of FUNCTION
         
       //OPEN THE MODAL WHEN IMPORT DRIVER DATA CLICKED
       $scope.importDriverData = function(){
         var modalInstance = $modal.open({
         templateUrl: 'partials/modals/importDriverData.jsp',
         controller: 'importDriverMasterDataCtrl',
         resolve: {}
        }); 
       }; //End Of FUNCTION
          
       //OPEN THE MODAL WHEN IMPORT VEHICLE DATA CLICKED
       $scope.importVehicleData = function(){
         var modalInstance = $modal.open({
                templateUrl: 'partials/modals/importVehicleData.jsp',
                controller: 'importVehicleMasterDataCtrl',
                resolve: {}
            }); 
        };  //End Of FUNCTION 
           
       //OPEN THE MODAL WHEN IMPORT AREA MASTER DATA CLICKED
       $scope.importAreaData = function(){
         var modalInstance = $modal.open({
         templateUrl: 'partials/modals/importAreaData.jsp',
         controller: 'importAreaMasterDataCtrl',
         resolve: {}
        }); 
       };  //End Of FUNCTION      
        
       //OPEN THE MODAL WHEN IMPORT Escort MASTER DATA CLICKED
       $scope.importEscortData = function(){
         var modalInstance = $modal.open({
         templateUrl: 'partials/modals/importEscortData.jsp',
         controller: 'importEscortMasterDataCtrl',
         resolve: {}
        }); 
       };
        
    };
    
//Employee Master Data Model Controller
var importEmployeeCtrl = function($scope, $modalInstance, $state, $http, $timeout){
        $scope.alertMessage;
        $scope.alertHint;  
    
        //Show Custome Alert Messages
        $scope.showalertMessageModal = function(message, hint){
            $scope.alertMessage = message;
            $scope.alertHint = hint;
            $('.loading').show();
            $('.alert_Modal').show('slow', function(){
                $timeout(function() {$('.alert_Modal').fadeOut('slow');
                                    $('.loading').hide();
                                    }, 3000);
            });            
        };

        //BUTTON ACTION :: CLOSE ALERT
        $scope.closeAlertMessageModal = function(){
            $('.loading').hide();
            $('.alert_Modal').hide('slow');
        };
    
        //SUBMIT BUTTON FUNCTION
        $scope.importEmpFile = function () { 
        var fd = new FormData();
 	    fd.append( "filename", $("#filenameforactivity")[0].files[0]);
 	    var post_url=$("#addinstgroup").attr("action");
 	    var postdata=$("#addinstgroup").serialize();
 	    var url= post_url+"?"+postdata+"&profileId="+profileId+"&branchId="+branchId;
 	   $.ajax({
 	    url:url,
 	    type: 'POST',
 	       cache: false,
 	       data: fd,
 	       processData: false,
 	       contentType: false,
 	       success: function (data,textStatus, jqXHR) {
               console.log("Data is Imported");
 	        },
	 	    complete: function() {
		    	$scope.showalertMessageModal('Employee data imported successfully.', '');
	            $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
	          }
 	        
 	       });
            //write the code of import file over here
        };
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };//End of MODAL CTRL
    
//Employee Cab Request Model Controller
var importEmployeeRequestCtrl = function($scope, $modalInstance, $state, $http, $timeout){ 
        $scope.alertMessage;
        $scope.alertHint; 
    
        //Show Custome Alert Messages
        $scope.showalertMessageModal = function(message, hint){
            $scope.alertMessage = message;
            $scope.alertHint = hint;
            $('.loading').show();
            $('.alert_Modal').show('slow', function(){
                $timeout(function() {$('.alert_Modal').fadeOut('slow');
                                    $('.loading').hide();
                                    }, 3000);
            });            
        };

        //BUTTON ACTION :: CLOSE ALERT
        $scope.closeAlertMessageModal = function(){
            $('.loading').hide();
            $('.alert_Modal').hide('slow');
        };
    
        //SUBMIT BUTTON FUNCTION
        $scope.importEmpFile = function () { 
        var fd = new FormData();
 	    fd.append( "filename", $("#filenameforactivity")[0].files[0]);
 	    var post_url=$("#addinstgroup").attr("action");
 	    var postdata=$("#addinstgroup").serialize();
 	    var url= post_url+"?"+postdata+"&profileId="+profileId+"&branchId="+branchId+"&userRole="+userRole;
 	   $.ajax({
 	    url:url,
 	    type: 'POST',
 	       cache: false,
 	       data: fd,
 	       processData: false,
 	       contentType: false,
 	       success: function (data,textStatus, jqXHR) {
 	     },
 	    complete: function() {
	    	$scope.showalertMessageModal('Employee request data imported successfully.', '');
	        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
       } 	        
 	       });
            //write the code of import file over here
        };       
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };//End of MODAL CTRL
    
//Vendor Master Data Model Controller
var importVendorMasterDataCtrl = function($scope, $modalInstance, $state, $http, $timeout){ 
        $scope.alertMessage;
        $scope.alertHint;  
    
        //Show Custome Alert Messages
        $scope.showalertMessageModal = function(message, hint){
            $scope.alertMessage = message;
            $scope.alertHint = hint;
            $('.loading').show();
            $('.alert_Modal').show('slow', function(){
                $timeout(function() {$('.alert_Modal').fadeOut('slow');
                                    $('.loading').hide();
                                    }, 3000);
            });            
        };

        //BUTTON ACTION :: CLOSE ALERT
        $scope.closeAlertMessageModal = function(){
            $('.loading').hide();
            $('.alert_Modal').hide('slow');
        };
    
        //SUBMIT BUTTON FUNCTION
        $scope.importEmpFile = function () { 
        var fd = new FormData();
 	   fd.append( "filename", $("#filenameforactivity")[0].files[0]);
 	    var post_url=$("#addinstgroup").attr("action");
 	    var postdata=$("#addinstgroup").serialize();
 	    var url= post_url+"?"+postdata+"&profileId="+profileId+"&branchId="+branchId;
 	   $.ajax({
 	    url:url,
 	    type: 'POST',
 	       cache: false,
 	       data: fd,
 	       processData: false,
 	       contentType: false,
 	       success: function (data,textStatus, jqXHR) {
 	     },
 	    complete: function() {
	    	$scope.showalertMessageModal('Vendor master data imported successfully.', '');
	        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
       }
 	        
 	       });
            //write the code of import file over here
        };
        
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };//End of MODAL CTRL 
    
//Driver Master Data Model Controller
var importDriverMasterDataCtrl = function($scope, $modalInstance, $state, $http, $timeout){ 
        $scope.alertMessage;
        $scope.alertHint;  
    
        //Show Custome Alert Messages
        $scope.showalertMessageModal = function(message, hint){
            $scope.alertMessage = message;
            $scope.alertHint = hint;
            $('.loading').show();
            $('.alert_Modal').show('slow', function(){
                $timeout(function() {$('.alert_Modal').fadeOut('slow');
                                    $('.loading').hide();
                                    }, 3000);
            });            
        };

        //BUTTON ACTION :: CLOSE ALERT
        $scope.closeAlertMessageModal = function(){
            $('.loading').hide();
            $('.alert_Modal').hide('slow');
        };
    
        //SUBMIT BUTTON FUNCTION
       $scope.importEmpFile = function () { 
       var fd = new FormData();
 	   fd.append( "filename", $("#filenameforactivity")[0].files[0]);
 	    var post_url=$("#addinstgroup").attr("action");
 	    var postdata=$("#addinstgroup").serialize();
 	    var url= post_url+"?"+postdata+"&profileId="+profileId+"&branchId="+branchId;
 	   $.ajax({
 	    url:url,
 	    type: 'POST',
 	       cache: false,
 	       data: fd,
 	       processData: false,
 	       contentType: false,
 	       success: function (data,textStatus, jqXHR) {
 	     },
 	    complete: function() {
	    	$scope.showalertMessageModal('Driver master data imported successfully.', '');
	        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
       }
 	        
 	       });
            //write the code of import file over here
        };
    
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };//End of MODAL CTRL  
    
//Vehicle Master Data Model Controller
var importVehicleMasterDataCtrl = function($scope, $modalInstance, $state, $http, $timeout){ 
        $scope.alertMessage;
        $scope.alertHint;  
    
        //Show Custome Alert Messages
        $scope.showalertMessageModal = function(message, hint){
            $scope.alertMessage = message;
            $scope.alertHint = hint;
            $('.loading').show();
            $('.alert_Modal').show('slow', function(){
                $timeout(function() {$('.alert_Modal').fadeOut('slow');
                                    $('.loading').hide();
                                    }, 3000);
            });            
        };

        //BUTTON ACTION :: CLOSE ALERT
        $scope.closeAlertMessageModal = function(){
            $('.loading').hide();
            $('.alert_Modal').hide('slow');
        };
    
        //SUBMIT BUTTON FUNCTION
        $scope.importEmpFile = function () { 
        var fd = new FormData();
 	    fd.append( "filename", $("#filenameforactivity")[0].files[0]);
 	    var post_url=$("#addinstgroup").attr("action");
 	    var postdata=$("#addinstgroup").serialize();
 	    var url= post_url+"?"+postdata+"&profileId="+profileId+"&branchId="+branchId;
// 	    $http.post(url, fd, {'Content-Type': 'application/json'});
 	   $.ajax({
 	    url:url,
 	    type: 'POST',
 	       cache: false,
 	       data: fd,
 	       processData: false,
 	       contentType: false,
 	       success: function (data,textStatus, jqXHR) {
 	    	   alert("Data is Imported");
 	     },
 	    complete: function() {
	    	$scope.showalertMessageModal('Vehicle master data imported successfully.', '');
	        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
       }
 	        
 	       });
            //write the code of import file over here
        };
        
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };//End of MODAL CTRL  
    
//Area Master Data Model Controller
var importAreaMasterDataCtrl = function($scope, $modalInstance, $state, $http, $timeout){   
        $scope.alertMessage;
        $scope.alertHint;     
    
        //Show Custome Alert Messages
        $scope.showalertMessageModal = function(message, hint){
            $scope.alertMessage = message;
            $scope.alertHint = hint;
            $('.loading').show();
            $('.alert_Modal').show('slow', function(){
                $timeout(function() {$('.alert_Modal').fadeOut('slow');
                                    $('.loading').hide();
                                    }, 3000);
            });            
        };

        //BUTTON ACTION :: CLOSE ALERT
        $scope.closeAlertMessageModal = function(){
            $('.loading').hide();
            $('.alert_Modal').hide('slow');
        };
    
        //SUBMIT BUTTON FUNCTION
        $scope.importEmpFile = function () { 
        var fd = new FormData();
 	    fd.append( "filename", $("#filenameforactivity")[0].files[0]);
 	    var post_url=$("#addinstgroup").attr("action");
 	    var postdata=$("#addinstgroup").serialize();
 	    var url= post_url+"?"+postdata+"&profileId="+profileId+"&branchId="+branchId;
 	   $.ajax({
 	    url:url,
 	    type: 'POST',
 	       cache: false,
 	       data: fd,
 	       processData: false,
 	       contentType: false,
 	       success: function (data,textStatus, jqXHR) {
 	     },
 	    complete: function() {
	    	$scope.showalertMessageModal('Area master data imported successfully.', '');
	        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
       }
 	        
 	       });
            //write the code of import file over here
        };
        
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        
    };//End of MODAL CTRL  
    
//Escort Master Data Model Controller
var importEscortMasterDataCtrl = function($scope, $modalInstance, $state, $http, $timeout){   
        $scope.alertMessage;
        $scope.alertHint; 
    
        //Show Custome Alert Messages
        $scope.showalertMessageModal = function(message, hint){
            $scope.alertMessage = message;
            $scope.alertHint = hint;
            $('.loading').show();
            $('.alert_Modal').show('slow', function(){
                $timeout(function() {$('.alert_Modal').fadeOut('slow');
                                    $('.loading').hide();
                                    }, 3000);
            });            
        };

        //BUTTON ACTION :: CLOSE ALERT
        $scope.closeAlertMessageModal = function(){
            $('.loading').hide();
            $('.alert_Modal').hide('slow');
        };
    
        //SUBMIT BUTTON FUNCTION
        $scope.importEmpFile = function () { 
        var fd = new FormData();
 	    fd.append( "filename", $("#filenameforactivity")[0].files[0]);
 	    var post_url=$("#addinstgroup").attr("action");
 	    var postdata=$("#addinstgroup").serialize();
 	    var url= post_url+"?"+postdata+"&profileId="+profileId+"&branchId="+branchId;
 	   $.ajax({
 	    url:url,
 	    type: 'POST',
 	       cache: false,
 	       data: fd,
 	       processData: false,
 	       contentType: false,
 	       success: function (data,textStatus, jqXHR) {
 	     },
 	    complete: function() {
	    	$scope.showalertMessageModal('Escort master data imported successfully.', '');
	        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
       } 	        
 	       });
            //write the code of import file over here
        };
        
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };        
        
    };//End of MODAL CTRL  
    angular.module('efmfmApp').controller('importDataCtrl', importDataCtrl);
    angular.module('efmfmApp').controller('importEmployeeCtrl', importEmployeeCtrl);    
    angular.module('efmfmApp').controller('importEmployeeRequestCtrl', importEmployeeRequestCtrl);  
    angular.module('efmfmApp').controller('importVendorMasterDataCtrl', importVendorMasterDataCtrl);    
    angular.module('efmfmApp').controller('importDriverMasterDataCtrl', importDriverMasterDataCtrl);    
    angular.module('efmfmApp').controller('importVehicleMasterDataCtrl', importVehicleMasterDataCtrl);    
    angular.module('efmfmApp').controller('importAreaMasterDataCtrl', importAreaMasterDataCtrl);    
    angular.module('efmfmApp').controller('importEscortMasterDataCtrl', importEscortMasterDataCtrl);
}());