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
 	    complete: function(data) {
 	    	var response=data.responseText;
 	    	var splitedResponce=response.split('-');
 	    	if(splitedResponce[0]=='backDateRequest'){
 	    		alert(splitedResponce[1]+'-Sorry you can not upload a back date request.Excel quite from same employee');
 	    		$modalInstance.dismiss('cancel');
 	    		// 		    	$scope.showalertMessageModal(splitedResponce[1]+ '-Sorry you can not upload a back date request.Excel quite from same employee', '');
 //		        $timeout(function() {$modalInstance.dismiss('cancel')}, 5000);
 	    	}
 	    	
 	    	/*else if(splitedResponce[0]=='emptyColumn'){
 	    		alert('Please check uploded excel for empty columns.');
 	    		$modalInstance.dismiss('cancel');
 	    	}*/
 	    	
 	    	
 	    	else if(splitedResponce[0]=='empIdNotExist'){
 	    		alert(splitedResponce[1]+'-This Employee Id Not exists in system please add it first.Excel quite from same employee');
 	    		$modalInstance.dismiss('cancel');
 	    		//		    	$scope.showalertMessageModal(splitedResponce[1]+'-This Employee Id Not exists in system please add it first.Excel quite from same employee', '');
 //		        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
 	    	}
 	    	else if(splitedResponce[0]=='routeNotExist'){
 	    		alert(splitedResponce[1]+'-This RouteName Not exists in system please confirm it from all route name tab.Excel quite from same routename');
 	    		$modalInstance.dismiss('cancel');
 	    		//	    	$scope.showalertMessageModal(splitedResponce[1]+ '-This RouteName Not exists in system please confirm it from all route name tab.Excel quite from same routename', '');
 	//	        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
 	    	}
 	    	else{
 		    	$scope.showalertMessageModal('Employee request data imported successfully.', '');
 		        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
 	    	}
       } 	        
 	       });
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
    
    var uploadRouteCtrl = function($scope, $modalInstance, $state, $http, $timeout){
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
       $scope.uploadFile = function () { 
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
	    	$scope.showalertMessageModal('Route uploaded successfully.', '');
	        $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
       }
 	        
 	       });
            //write the code of import file over here
        };
    
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        }
    };
    
    var exportRouteCtrl = function($scope, $modalInstance, $state, $http, $timeout){
       $scope.hstep = 1;
       $scope.mstep = 5;
       $scope.download = {};  
       $scope.shiftsTime = [];
       $scope.ismeridian = false;
       $scope.reportList = [];
       $scope.download.date = new Date();
//       $scope.setMinDate = new Date();
       $scope.tripTypes = [{'value':'PICKUP', 'text':'PICKUP'},
	    	 	           {'value':'DROP', 'text':'DROP'}];
        
        //Convert the dates in DD-MM-YYYY format
       var convertDateUTC = function(date){
          var convert_date = new Date(date);
          var currentMonth = date.getMonth()+1;
          var currentDate=date.getDate();
            	  if (currentDate < 10) { 
            		  currentDate = '0' + currentDate; 
                      console.log('in convert function : ' + currentDate);
              }
              if (currentMonth < 10) { 
                  currentMonth = '0' + currentMonth; 
                  console.log('in convert function : ' + currentMonth);
              }
          return currentDate+'-'+currentMonth+'-'+convert_date.getFullYear();   
        }; 
        
        //Initialize TimePicker to 00:00
        var timePickerInitialize = function(){
           var d = new Date();
           d.setHours( 00 );
           d.setMinutes( 0 );
           $scope.assignCab.createNewAdHocTime = d;
        };
        
       $scope.format = 'dd-MM-yyyy';
       $scope.dateOptions = {formatYear: 'yy',
                             startingDay: 1,
                              showWeeks: false,
                             };
        
        
        $scope.openDownloadDateCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openeddownloadDate' : true};  
            })
        };
          
    
        //Initialize TimePicker to 00:00
        var initialize = function(){
           var d = new Date();
           d.setHours( 00 );
           d.setMinutes( 0 );
           $scope.download.createNewAdHocTime = d;
           $scope.download.tripType = {'value':'PICKUP', 'text':'PICKUP'};
           
           $scope.setTripType($scope.download.tripType);
           $scope.download.shiftTime = $scope.shiftsTime[0];   
           $scope.shiftTime = 'preDefineShiftTime'; 
           $scope.download.date = new Date();
        }
    
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
    
        $scope.selectShiftTimeRadio = function(shiftTime){
           $scope.typeOfShiftTimeSelected = shiftTime; 
           $('.btn-link').addClass('noPointer');  
        };
    
        $scope.selectShiftTimeRadio2 = function(shiftTime){
           $scope.typeOfShiftTimeSelected = shiftTime;  
           $('.btn-link').removeClass('noPointer');  
        };
        
        $scope.setTripType = function(tripType){
               if(angular.isObject(tripType)){               
                       var data = {
                           efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                           eFmFmEmployeeRequestMaster:{efmFmUserMaster:{userId:profileId}},
                           tripType:tripType.value
                              }; 
                       $http.post('services/trip/tripshiftime/',data).
                            success(function(data, status, headers, config) {
                                $scope.shiftsTime = data.shift; 
                                $scope.download.shiftTime = $scope.shiftsTime[0];

                            }).
                            error(function(data, status, headers, config) {});
                 } 
               else {
                   $scope.shiftsTime = '';
               }
            $scope.download.shiftTime = $scope.shiftsTime[0];
          };
        
        initialize(); 
    
        $scope.ExportRoute = function(fileInfo){
            if($scope.shiftTime == 'preDefineShiftTime'){
                $scope.timeSelected = fileInfo.shiftTime.shiftTime
            }
            else{
                var fullDate = new Date(fileInfo.createNewAdHocTime);
                var time = fullDate.getHours()+':'+fullDate.getMinutes()+':00';
                $scope.timeSelected = time;
            }
            
            var dataObj = {
                branchId:branchId,
                executionDate:convertDateUTC(fileInfo.date),
                tripType:fileInfo.tripType.value,
                time: $scope.timeSelected
                 
              }; 
            console.log(dataObj);
            $http.post('services/xlEmployeeExport/exportRoute/',dataObj).
                success(function(data, status, headers, config) {
                       //alert(JSON.stringify(data))  
                        if(data == "SUCCEES"){
                            $scope.showalertMessageModal("Files have been exported successfully.", ""); 
                            $timeout(function() {$modalInstance.close()}, 3000); 
                        }
                        else{
                            $scope.showalertMessageModal("No file found to export. Please change your selction and try again", ""); 
                        }
                        
                }).
                error(function(data, status, headers, config) {});
        }; 
     
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
           $modalInstance.dismiss('cancel');
        };         
        
    };
    
     var downloadRouteCtrl = function($scope, $modalInstance, $state, $http, $timeout){
          $scope.getFileList = function(){
            var data = {
                           branchId:branchId
//                           eFmFmEmployeeRequestMaster:{efmFmUserMaster:{userId:profileId}}

                   };
           $http.post('services/xlEmployeeExport/listOfFileNames/',data). 
                success(function(data, status, headers, config) {
                        $scope.reportList = data;
                }).
                error(function(data, status, headers, config) {});
          }
          
          //populating the dropdown
          $scope.getFileList();
         
          $scope.downloadSelectedFile = function(fileSelected){
              var fileUrl = "downloadRoutes.do?fileName="+fileSelected.reportName;
              var atag= document.getElementById('reportName');
              $timeout(function() {atag.click();}, 500);
             // $timeout(function() {$modalInstance.close()}, 3000); 
              
          };
     
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
           $modalInstance.dismiss('cancel');
        };    
         
         
     };
    
    angular.module('efmfmApp').controller('importDataCtrl', importDataCtrl);
    angular.module('efmfmApp').controller('importEmployeeCtrl', importEmployeeCtrl);    
    angular.module('efmfmApp').controller('importEmployeeRequestCtrl', importEmployeeRequestCtrl);  
    angular.module('efmfmApp').controller('importVendorMasterDataCtrl', importVendorMasterDataCtrl);    
    angular.module('efmfmApp').controller('importDriverMasterDataCtrl', importDriverMasterDataCtrl);    
    angular.module('efmfmApp').controller('importVehicleMasterDataCtrl', importVehicleMasterDataCtrl);    
    angular.module('efmfmApp').controller('importAreaMasterDataCtrl', importAreaMasterDataCtrl);    
    angular.module('efmfmApp').controller('importEscortMasterDataCtrl', importEscortMasterDataCtrl);
    angular.module('efmfmApp').controller('uploadRouteCtrl', uploadRouteCtrl);
    angular.module('efmfmApp').controller('exportRouteCtrl', exportRouteCtrl);
    angular.module('efmfmApp').controller('downloadRouteCtrl', downloadRouteCtrl);
}());