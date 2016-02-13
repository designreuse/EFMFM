(function(){
   var vendorDashboardCtrl = function($scope, $modal, $state, $http, $filter, $confirm){
	   console.log("Enter :: Vendor Dashboard");
	   $('.vendorDashboard_admin').addClass('active');
	   $('.admin_home').addClass('active');
//       $scope.countofFilteredVendors;
       $scope.vendorLength;
	   $scope.currentIndex;
       $scope.checkBoxFlag = false;
	   $scope.numberofRecords = 1000000;
	  
	   $scope.$on('$viewContentLoaded', function() {
		    //call it here
		   $scope.getContractManag();
		   
		});
	   $scope.selectDrivers;
	   $scope.paginations = [{'value':10, 'text':'10 records'},
	    	 				   {'value':15, 'text':'15 record'},
	    	 				   {'value':20, 'text':'20 records'}];
	   
	   $scope.selectVendors = [{'value':'Fast Track'},
	    	 				   {'value':'BHARATHICALLTAXI'},
	    	 				   {'value':'OLACABS'},
	    	 				   {'value':'Meru Call Taxi'},
	    	 				   {'value':'Golden Call Taxi'},
	    	 				   {'value':'Yellow Call Taxi'}];

	   $scope.selectSeats = [{'value':1, 'text' : '2 Seater'},
	                         {'value':4, 'text' : '4 Seater'},
	                         {'value':7, 'text' : '7 Seater'},
	                         {'value':9, 'text' : '9 Seater'},
	                         {'value':11, 'text' : '11+ Seater'}];   
	   
	   $scope.setLimit = function(showRecords){
	    	 if(!showRecords){$scope.numberofRecords = $scope.vendorContractManagData.length;}
	    	 else $scope.numberofRecords = showRecords.value;  	 
	    };              
       
       //Convert the dates in DD-MM-YYYY format
       var convertDateUTC = function(date){
          var convert_date = new Date(date);
          var currentDay = convert_date.getDate();
              if (currentDay < 10) { 
                  currentDay = '0' + currentDay; 
              }
          var currentMonth = convert_date.getMonth()+1;
              if (currentMonth < 10) { 
                  currentMonth = '0' + currentMonth;
              }
          console.log(currentDay+'-'+currentMonth+'-'+convert_date.getFullYear());
          return currentDay+'-'+currentMonth+'-'+convert_date.getFullYear();   
        };
	    
// ******VENDOR DASHBOARD TAB FUNCTIONS - called when the user click any tab*************** 
	    
	   $scope.getContractManag = function(){
           $scope.progressbar.start();
		   console.log("You have clicked Contract Management");

		   var data = {
				   eFmFmClientBranchPO:{branchId:branchId}
			};		   
		    $http.post('services/vendor/vendorByVehicleDetails/',data).
				    success(function(data, status, headers, config) {
//				    	alert(JSON.stringify(data));
				      $scope.vendorContractManagData = data; 
                      $scope.vendorLength = $scope.vendorContractManagData.length;
				      angular.forEach($scope.vendorContractManagData, function(item) {
		  		    	  item.driver_isClicked=false;
		  		    	  item.vehicle_isClicked=false;
		  	         });
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	   /*list of Contract Type
	   
	  /* 
	   $scope.getContractManag = function(){
		   console.log("You have clicked Contract Management");

		   var data = {
				   eFmFmClientBranchPO:{branchId:branchId}
			};		   
		    $http.post('services/vehicle/listOfContractType/',data).
				    success(function(data, status, headers, config) {
				     
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };*/
	   
	   
	   
	   $scope.getVehicleCheckedIn = function(){
           $scope.progressbar.start();
		   console.log("You have clicked Vehicle Checked-In Tab");
		   var data = {
				   eFmFmClientBranchPO:{branchId:branchId}
			};
//		   $http.post('services/vehicle/vehicleonroad/',data).
		   $http.post('services/vehicle/listOfCheckedInVehicles/',data).
				    success(function(data, status, headers, config) {
//				    	alert(JSON.stringify(data));
                      console.log(data);
				      $scope.vehicleCheckInData = data;  
                      angular.forEach($scope.vehicleCheckInData, function(item) {
                            item.checkBoxFlag=false;
                      });
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	   $scope.getAvailableVehicle = function(){
           $scope.progressbar.start();
		   console.log("You have clicked Available Vehicle Tab");
		   var data = {
				   eFmFmClientBranchPO:{branchId:branchId}
			};
		  // alert("All");
		   $http.post('services/vehicle/listOfCheckedInVehicles/',data).
				    success(function(data, status, headers, config) {
				      $scope.availableVehicleData = data;   
//				      angular.forEach($scope.availableVehicleData, function(item) {
//		  		    	 });
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
       
       $scope.getEscortDetails = function(){
           $scope.progressbar.start();
           console.log("You have clicked Escort Detail Tab");
		   var data = {
				   eFmFmClientBranchPO:{branchId:branchId}
			};
		   $http.post('services/escort/listOfEscort/',data).
				    success(function(data, status, headers, config) {
//				    	alert(JSON.stringify(data));
				      $scope.escortDetailData = data;   
				      angular.forEach($scope.escortDetailData, function(item) {
                          item.isClicked = false;
		  		    	 });
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
       };
       
       $scope.getEscortCheckIn = function(){
           $scope.progressbar.start();
           var data = {
        		   branchId:branchId
			};
		   $http.post('services/escort/escortCheckInDetails/',data).
				    success(function(data, status, headers, config) {
				      $scope.escortCheckInData = data;   
                      angular.forEach($scope.availableVehicleData, function(item) {
                          item.checkBoxFlag = false;
		  		    	 });  
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
       };
       
       $scope.getEscortAvailable = function(){
           $scope.progressbar.start();
           var data = {
        		   branchId:branchId
			};
		   $http.post('services/escort/availableEscortDetails/',data).
				    success(function(data, status, headers, config) {
				      $scope.escortAvailableData = data;  
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
       };
       
       $scope.getDeviceDetail = function(){
           $scope.progressbar.start();
		   console.log("You have clicked Device Detail Tab");
		   var data = {
				   eFmFmClientBranchPO:{branchId:branchId}
			};
		   $http.post('services/device/alldevices/',data).
				    success(function(data, status, headers, config) {
				      $scope.deviceDetailData = data; 
                      console.log(data);
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
       };
       
       $scope.getDriversOnRoad = function(){
           $scope.progressbar.start();
		   console.log("You have clicked Drivers On Road Tab");
           var data = {
				   eFmFmClientBranchPO:{branchId:branchId}
			};
		   $http.post('services/vehicle/vehicleonroad/',data).
                success(function(data, status, headers, config) {
                  $scope.driversOnRoadData = data; 
                  console.log(data);
                  $scope.progressbar.complete();
                }).
                error(function(data, status, headers, config) {
                  // log error
                });
       };
       
       $scope.setLimit = function(showRecords){
	    	 if(!showRecords){$scope.numberofRecords = $scope.vendorLength;}
	    	 else $scope.numberofRecords = showRecords.value;  	 
	    };
       
       $scope.getIndex = function(){
		   return $scope.currentIndex;
	   };
       
       //************************************ADD VENDORS *******************************************
	   $scope.addVendors = function(size){
  //         alert("Add Vendor Button is Clicked");
		   var modalInstance = $modal.open({
           templateUrl: 'partials/modals/vendorDashboard/newVendorForm.jsp',
           controller: 'addVendorCtrl',
           size:size,
           resolve: {}
           }); 
		   
           //Here the VendorId will be given to the Vendor from backend
		   modalInstance.result.then(function(result){
              $scope.vendorContractManagData.push({vendorName: result.vendorName,
                                                   vendorMobileNumber: result.vendorMobileNumber,
                                                   noOfDriver:0,
                                                   noOfVehicle:0}); 
         });
	   };	    	   
	   
       //*********************************EDIT VENDORS*****************************************
       $scope.editVendor = function(post, index, size){
         var modalInstance = $modal.open({
           templateUrl: 'partials/modals/vendorDashboard/editVendorForm.jsp',
           controller: 'editVendorCtrl',
           size:size,
           resolve: {
               vendor: function () {
                        return post;}
           }
           }); 
		   
		   modalInstance.result.then(function(result){	
               var vendorIndex;       
               console.log('result.address '+result.address+' result.email'+result.email);
               var vendorIndex =  _.findIndex($scope.vendorContractManagData, {vendorId:result.vendorId}); 
               if(vendorIndex>=0){
                   $scope.vendorContractManagData[vendorIndex] ={'vendorName': result.vendorName,
                                                                 'vendorMobileNumber': result.vendorMobileNumber,
                                                                 'noOfVehicle':result.noOfVehicle,
                                                                 'noOfDriver':result.noOfDriver}; 
              }
         });           
       }
       
//*******************************************************VENDOR :: DRIVER ****************************************************************************************  
	   
       //*****************************************VIEW DRIVERS************************************************
	   $scope.viewDrivers = function(post, index){
           $scope.progressbar.start();
		   $scope.currentIndex = index;
		   $('#vehicle'+a).hide();
		   var a = post.vendorId;
		    var data = {
					   efmFmVendorMaster:{vendorId:post.vendorId}
				};
			   $http.post('services/vendor/listOfDriverByVendor/',data).
				    success(function(data, status, headers, config) {
//				    	alert(JSON.stringify(data));
                        console.log(data);				    	
				    	$scope.vendorContractManagData[$scope.getIndex()].driverData = data;
				    	angular.forEach($scope.vendorContractManagData[$scope.getIndex()].driverData, function(item) {
				    		item.editDriver_Enable = false;
			  	         });
                        $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });	
		   
		   angular.forEach($scope.vendorContractManagData[index].driverData, function(item) {
	    	   item.editDriver_Enable = false;
			   console.log(item.name + " :: " +item.editDriver_Enable);
			   
 	         });
		   
		   if(!post.driver_isClicked){
               angular.forEach($scope.vendorContractManagData, function(item){
                   item.driver_isClicked=false;
		  		   item.vehicle_isClicked=false;
               });
			   post.driver_isClicked = true;
			   $('#vehicle'+a).hide();
			   $('#driver'+a).show('slow');
		   }
		   else {
			   post.driver_isClicked = false;
			   $('#vehicle'+a).hide();
			   $('#driver'+a).hide();			   
		   }		   
	   };	
	  
      //*********************************************ADD NEW DRIVER***************************************************
      $scope.addNewDriver = function(parentIndex, size){
  //        alert("ADD NEW DRIVER BUTTON IS CLICKED for VENDOR :: " +$scope.vendorContractManagData[index].name);
          //alert("Vendor Name is  :: " +$scope.vendorContractManagData[$scope.getIndex()].vendorName);
		   $scope.currentIndex = parentIndex;
	   
		   var modalInstance = $modal.open({
           templateUrl: 'partials/modals/vendorDashboard/newDriverForm.jsp',
           controller: 'newDriverVendorCtrl',
           size:size,
           resolve: {
               masterVendor : function(){
                   return $scope.vendorContractManagData[$scope.getIndex()].vendorName;
               }
           }
           }); 
		   
		   modalInstance.result.then(function(result){//when we close the model need to append the vale from ui to here directly.
               $scope.vendorContractManagData[$scope.getIndex()].noOfDriver = $scope.vendorContractManagData[$scope.getIndex()].noOfDriver + 1;
			   $scope.vendorContractManagData[$scope.getIndex()].driverData.push({'driverId':result.driverId,
                                                                                   'driverName': result.driverName,
                                                                                   'driverAddress' : result.driverAddress,
                                                                                   'driverdob': convertDateUTC(result.driverdob),
                                                                                   'medicalCertificateValid': convertDateUTC(result.driverMedicalExpiry),
                                                                                   'licenceValid': convertDateUTC(result.licenceValid),
                                                                                   'licenceNumber': result.licenceNumber,
                                                                                   'mobileNumber':result.mobileNumber });
         });
	   };
      //***********************************************EDIT DRIVERS************************************************
	  $scope.editDriver = function(post, parentIndex, index, size){
          //alert("Driver selected :: " +post.driverId + " :: " +post.driverName + " of Vendor :: " +$scope.vendorContractManagData[parentIndex].vendorName);
           $scope.currentIndex = parentIndex;
		   var modalInstance = $modal.open({
               templateUrl: 'partials/modals/vendorDashboard/editDriverForm.jsp',
               controller: 'editDriverCtrl',
               size:size,
               resolve: {
                   driver: function () {
                        return post;}
               }
           }); 
		   
		   modalInstance.result.then(function(result){
               var driverIndex;               
               var driverIndex =  _.findIndex($scope.vendorContractManagData[$scope.getIndex()].driverData, {driverId:result.driverId}); 
               //alert("result.driverId"+result.driverId);
               if(driverIndex>=0){
                   $scope.vendorContractManagData[$scope.getIndex()].driverData[driverIndex] = {'driverId': result.driverId,
                                                                                                'driverName': result.driverName,
                                                                                                'driverAddress': result.driverAddress,
                                                                                                'driverdob': result.driverdob,
                                                                                                'licenceNumber': result.licenceNumber,
                                                                                                'medicalCertificateValid': result.medicalCertificateValid,
                                                                                                'licenceValid': result.licenceValid,
                                                                                                'mobileNumber': result.mobileNumber}; 
               }
         });
	   };
	   //***********************************************DELETE DRIVERS************************************************************
	   $scope.deleteDriver = function(post, parentIndex, index){
		   var data = {
				   driverId:post.driverId
			};
           $confirm({text: 'Are you sure you want to delete this Driver?', title: 'Delete Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/vendor/removeDriverDetails/',data).
				    success(function(data, status, headers, config) {
				      console.log(data);   
                      if(data == 'Success'){  
                          $scope.vendorContractManagData[$scope.getIndex()].driverData.splice(index, 1);
                          $scope.vendorContractManagData[$scope.getIndex()].noOfDriver = $scope.vendorContractManagData[$scope.getIndex()].noOfDriver - 1;
                          $scope.showalertMessage('Driver has been deleted successfully', '');
                      }
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
           });
		   
		   
	   };
       
       //**********************************************UPLOAD DRIVERS DOCUMENTS*****************************************************
       $scope.uploadDriver = function(post, parentIndex, index){
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/vendorDashboard/uploadDriver.jsp',
           controller: 'uploadDriverCtrl',
           resolve: {
               driver : function(){
                   return post;
               }
           }
           }); 		   
		   modalInstance.result.then(function(result){});
       };	   
	   
//*****************************************************************VENDOR :: VEHICLE ********************************************************************* 
       
       //***********************************************VIEW VEHICLES***********************************************
	   $scope.viewVehicles = function(post, index){
           $scope.progressbar.start();
           $('#driver'+a).hide();
		   var a = post.vendorId;
			   $scope.currentIndex = index;
			   console.log(post.name + " in Contract Management :: Vehicle");
			   var data = {
					   efmFmVendorMaster:{vendorId:post.vendorId}
				};
			   $http.post('services/vendor/listOfVehiclebyVendor/',data).
				    success(function(data, status, headers, config) {
				    	console.log(data);
				    	$scope.vendorContractManagData[$scope.getIndex()].vehicleData = data;  
				    	angular.forEach($scope.vendorContractManagData[$scope.getIndex()].vehicleData, function(item) {
				    		item.editVehicle_Enable = false;				    		
			  	        });
                        $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });			   
		   
		   if(!post.vehicle_isClicked){
               angular.forEach($scope.vendorContractManagData, function(item){
                   item.driver_isClicked=false;
		  		   item.vehicle_isClicked=false;
               });
			   post.vehicle_isClicked = true;
			   $('#driver'+a).hide();
			   $('#vehicle'+a).show('slow');
		   }
		   else {
			   post.vehicle_isClicked = false;
			   $('#driver'+a).hide();
			   $('#vehicle'+a).hide('slow');
		   }  
	   };   
	   
       //***********************************************ADD NEW VEHICLE***********************************************
       $scope.addNewVehicle = function(index, size){
		   $scope.currentIndex = index;
		   var modalInstance = $modal.open({
           templateUrl: 'partials/modals/vendorDashboard/newVehicleForm.jsp',
           controller: 'newVehicleVendorCtrl',
           size:size,
           resolve: {
              masterVendor : function(){
                   return $scope.vendorContractManagData[$scope.getIndex()].vendorName;
               }
           }
           }); 
		   
		   modalInstance.result.then(function(result){
               $scope.vendorContractManagData[$scope.getIndex()].noOfVehicle = $scope.vendorContractManagData[$scope.getIndex()].noOfVehicle + 1;
			   $scope.vendorContractManagData[$scope.getIndex()].vehicleData.push({'vehicleId': '',
                               'vehicleOwnerName': $scope.vendorContractManagData[$scope.getIndex()].vendorName,
                               'vehicleNumber':result.vehicleNumber,
                               'PermitValid': convertDateUTC(result.PermitValid),
//                               'capacity': result.capacity.value,
                               'InsuranceDate': convertDateUTC(result.InsuranceDate)});
               
         });
	   }; 
       
       //***********************************************EDIT VEHICLE***********************************************
	   $scope.editVehicle = function(post, parentIndex, index, size){
           $scope.currentIndex = parentIndex;
		   var modalInstance = $modal.open({
           templateUrl: 'partials/modals/vendorDashboard/editVehicleForm.jsp',
           controller: 'editVehicleCtrl',
           size:size,
           resolve: {
               vehicle: function () {
                    return post;}
           }
           }); 
		   
		   modalInstance.result.then(function(result){
               var InsuranceDate = convertDateUTC(result.InsuranceDate);
               var pollutionExpDate = convertDateUTC(result.pollutionExpDate);
               var PermitValid = convertDateUTC(result.PermitValid);
               var TaxValid = convertDateUTC(result.TaxValid);
               
               var vehicleIndex;               
               var vehicleIndex =  _.findIndex($scope.vendorContractManagData[$scope.getIndex()].vehicleData, {vehicleId:result.vehicleId}); 
               if(vehicleIndex>=0){
                   $scope.vendorContractManagData[$scope.getIndex()].vehicleData[vehicleIndex] = {'vehicleId': result.vehicleId,
                                                                                                   'vehicleOwnerName': $scope.vendorContractManagData[$scope.getIndex()].vendorName,
                                                                                                   'vehicleNumber':result.vehicleNumber,
                                                                                                   'PermitValid':PermitValid,
                                                                                                   'contactNo': result.contactNo,
                                                                                                   'contactName': result.contactName,
                                                                                                   'capacity': result.capacity.value,
                                                                                                   'regCert': result.regCert,
                                                                                                   'pollutionCert': result.pollutionCert,
                                                                                                   'polutionValid': pollutionExpDate,
                                                                                                   'modelYear': result.modelYear,
                                                                                                   'InsuranceNumber': result.InsuranceNumber,
                                                                                                   'InsuranceDate': InsuranceDate,
                                                                                                   'taxCertificateValid': TaxValid,
                                                                                                   'mobileNumber' : result.mobileNumber};  
               }
         });
	   };
	   
       //***********************************************DELETE VEHICLES***********************************************
	   $scope.deleteVehicle = function(post, parentIndex, index){
		   var data = {		
				   vehicleId:post.vehicleId		
			};
           $confirm({text: 'Are you sure you want to delete this Vehicle?', title: 'Delete Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/vehicle/removeVehicleDetails/',data).		
				    success(function(data, status, headers, config) {               
		                $scope.vendorContractManagData[$scope.getIndex()].vehicleData.splice(index, 1);
                        $scope.vendorContractManagData[$scope.getIndex()].noOfVehicle = $scope.vendorContractManagData[$scope.getIndex()].noOfVehicle - 1;
				    	$scope.showalertMessage("Vehicle deleted successfully", "");
				    }).		
				    error(function(data, status, headers, config) {		
				      // log error		
				    });		
           });
		   
	   };
       
       //***********************************************UPLOAD VEHICLE DOCUMENTS****************************************
 	   $scope.uploadVehicle = function(post, parentIndex, index){
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/vendorDashboard/uploadVehicle.jsp',
           controller: 'uploadVehicleCtrl',
           resolve: {
               vehicle : function(){
                   return post;
               }
           }
        }); 
		   
		   modalInstance.result.then(function(result){});
       };      
       
//************************************************CHECK IN / CHECKOUT VEHICLE TAB********************************************************
       $scope.select_vehicleCheckIn = function(post){
            if(!post.checkBoxFlag){
	    		 post.checkBoxFlag = true;                
                 $('.vehicle' + post.vehicleId).css('background-color','rgba(210, 230, 239, 0.5)');
	    		 console.log(post.name + " is selected");
	    	 }
	    	 else {
                 
                 $('.vehicle' + post.vehicleId).css('background-color','white');
	    		 post.checkBoxFlag = false;
	    	 }
       };
       
       $scope.submitVehicleCheckIn = function(){
           var checkedVehicle = [];
           //Following lines will Go in onSuccess Block except 'checkedVehicle.push(item.vehicleId);'
            angular.forEach($scope.vehicleCheckInData, function(item) {
 //           	alert(JSON.stringify(item));
            	alert("item.vehicleId"+item.vehicleId+":::"+item.driverId);            	
                if(item.checkBoxFlag){
                    checkedVehicle.push(item.vehicleId);
                    console.log("item.vehicleId"+item.vehicleId+":::"+item.driverId); 		   
         		   
         		/*   var data = {
         				   branchId:branchId,
         				  efmFmVehicleMaster:{vehicleId:item.vehicleId},
         				 eFmFmDeviceMaster:{deviceId:item.deviceId},         				   
         				 efmFmDriverMaster:{driverId:item.driverId}
         			};
         		   $http.post('services/vehicle/driverCheckIn/',data).
         				    success(function(data, status, headers, config) {
                            console.log(data);
//         				      $scope.escortAvailableData = data; 
         				      //alert("status"+data);
         				      if(data=="success"){                                                       
                                  item.checkBoxFlag = false;
                                  $('.vehicle'+item.vehicleId).hide('slow');
         				    	  $scope.showalertMessage("Vehicle and Device Check-In Successfully", "");
         				      }else{
         				    	 $scope.showalertMessage("Vehicle Number " + item.vehicleNumber + " and Device Number " + item.deviceNumber+" are already Allocated", "");
         				      }
         				    }).
         				    error(function(data, status, headers, config) {
         				      // log error
         				    }); */
                } 
            });
       };
	   
	   // From Vehicle Checkin Tab Change  Any Entity Vehicle,Driver,Device.....................
	   $scope.editEntity = function(post, index){
	   	 		   var modalInstance = $modal.open({
	   	            templateUrl: 'partials/modals/vendorDashboard/changeEntitesModal.jsp',
	   	            controller: 'entityCtrl',
	   	            resolve: {
                        vehicle:function(){return post;}
                    }
	   	            }); 
		   
		   modalInstance.result.then(function(result){
               console.log(result);
//               $('.vehicle'+post.vehicleId).hide('slow');
               post.vehicleNumber = result.vehicleNumber.vehicleNumber;
               post.vehicleId = result.vehicleNumber.vehicleId;
               post.vendorId = result.vehicleNumber.vendorId;
               post.DriverName = result.driverName.DriverName;
               post.MobileNumber = result.driverName.MobileNumber;
               post.driverId = result.driverName.driverId;
               post.deviceId = result.deviceId.deviceId;
               post.deviceNumber = result.deviceId.deviceNumber;
               
               //**if it is coming in the Vehicle Array for drop down then:
               //post.vehicleMake = result.vehicleNumber.vehicleMake;
 //              post.vehicleMake = 'TATA'; 
               //post.capacity = result.vehicleNumber.capacity;
  //             post.capacity = '0';               
         });
	   };
       
       $scope.cancelVehicleCheckIn = function(){
            angular.forEach($scope.vehicleCheckInData, function(item) {
                   if(item.checkBoxFlag){
                      item.checkBoxFlag = false;
                   }
            });
       }
       
       $scope.checkoutVehicle = function(vehicle){
    	   var data = {
        		   checkInId:vehicle.checkInId,
        		   branchId:branchId
                   };
               $http.post('services/vehicle/driverCheckOut/',data).
                   success(function(data, status, headers, config) {
                   	if(data.status='success'){  
                   		$('.vehicle'+vehicle.checkInId).hide();
                   		$scope.showalertMessage("Driver checkout successfully", "");                  		
                   	}
                    }).
                   error(function(data, status, headers, config) {
                       // log error
                   });
       };
//*****************************************ESCORT MANAGEMENT TAB*******************************************************	       
       $scope.addEscort = function(){
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/vendorDashboard/newEscortForm.jsp',
           controller: 'newEscortCtrl',
           resolve: {}
           }); 
		   
		   modalInstance.result.then(function(result){
               var dob = convertDateUTC(result.escortdob);
               
               $scope.escortDetailData.push({escortName: result.escortName,
                                             escortMobileNumber: result.escortMobileNumber,
                                             escortdob: dob,
                                             escortAddress: result.escortAddress});
         });       
       };
       
       $scope.viewEscortDetail = function(post, index){ 
           if(!post.isClicked){               
               angular.forEach($scope.escortDetailData, function(item){
                   item.isClicked = false;
               });
               post.isClicked = true;
               $scope.currentIndex = index;
 //              alert(post.escortId);
               var data = {
                    escortId:post.escortId,
                    };
                $http.post('services/escort/escortallDetails/',data).
                    success(function(data, status, headers, config) {
                       console.log(data);	
                       $scope.escortDetails = data;
                     }).
                    error(function(data, status, headers, config) {
                        // log error
                    });
           }
           else post.isClicked = false;
    	};
       
       $scope.closeEscortDetail = function(post){
           post.isClicked = false;
       };
       
        $scope.viewEscortDetailDoc = function(post){
           $state.go('home.escortDetail', {'escortId' : post.vehicleId});
       };             
       
       $scope. editEscortDetail = function(post, index){
           
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/vendorDashboard/editEscortForm.jsp',
           controller: 'editEscortCtrl',
           resolve: {
               escort: function () {return post;}
           }
           }); 
		   
		   modalInstance.result.then(function(result){
               var dob = convertDateUTC(result.escortdob);
               var escortIndex;               
               var escortIndex =  _.findIndex($scope.escortDetailData, {escortId:result.escortId}); 
               if(escortIndex>=0){
                   $scope.escortDetailData[escortIndex] = {'escortId': result.escortId,
                                                          'escortName':result.escortName,
                                                          'escortMobileNumber': result.escortMobileNumber,
                                                          'escortdob':dob,
                                                          'escortAddress': result.escortAddress}; 
               }
         });
       }
       //Using the same upload controller and view as in the Setting>ImportEscort
       $scope.uploadEscort = function(post, index){
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/importEscortData.jsp',
           controller: 'importEscortMasterDataCtrl',
           resolve: {
           }
           }); 
		   
		   modalInstance.result.then(function(result){});
       };
       
//*******************************************CHECK IN / CHECKOUT ESCORT TAB******************************************************	
       $scope.select_EscortCheckIn = function(post){
           if(!post.checkBoxFlag){
	    		 post.checkBoxFlag = true;
                 $('.escort'+post.escortId).css('background-color','rgba(210, 230, 239, 0.5)');
	    		 console.log(post.escortName + " is selected" + post.checkBoxFlag);
	    	 }
	    	 else {
                 $('.escort'+post.escortId).css('background-color','white');
	    		 post.checkBoxFlag = false;
	    	 }       
       };      
       
        $scope.submitEscortCheckIn = function(index){
        	
            var checkedEscort = [];
           //Following Line will go in onsuccess Block except 'checkedEscort.push(item.vehicleId);'
            angular.forEach($scope.escortCheckInData, function(item) {
                if(item.checkBoxFlag){ 
                	//alert("item.escortId"+item.escortId);
                    checkedEscort.push(item.escortId);
                    console.log("checked" +item.escortName );
            
          	var data = {escortId:item.escortId};
          	$http.post('services/escort/checkInEscort/',data).
          	    success(function(data, status, headers, config) {
                    $scope.showalertMessage("Escort Checkin Successfully", "");
                    angular.forEach($scope.escortCheckInData, function(item) {
                        if(item.checkBoxFlag){
                             item.checkBoxFlag = false;
                             $('.escort'+item.escortId).hide('slow');
                        }
                    });          				    
                }).          				   
                error(function(data, status, headers, config) {
          				      // log error
                });  
                }
            });
       };
       
       $scope.cancelEscortCheckIn = function(){
            angular.forEach($scope.escortCheckInData, function(item) {
                   if(item.checkBoxFlag){
                      item.checkBoxFlag = false;
                   }
            });
       };
       
       //Escort CheckOut Funtion
       $scope.checkoutEscort = function(escort){
           var data = {
        		   branchId:branchId,
				   escortCheckInId:escort.escortCheckId,
			};
		   $http.post('services/escort/checkOutEscort/',data).
				    success(function(data, status, headers, config) {
				    	 $('.escort'+escort.escortId).hide('slow');
				    	$scope.showalertMessage("Escort Checkout Successfully", "");
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });             
       };
       
//***********************************************************DEVICE DETAIL TAB **********************************************************
       
       $scope.enableDevice = function(device){        
           var data = {
				   eFmFmClientBranchPO:{branchId:branchId},
				   isActive:'Y',
				   imeiNumber:device.imeiNumber
			};
		   $http.post('services/device/devicestatus/',data).
				    success(function(data, status, headers, config) {
                        device.deviceStatus = 'Y';
				    	$scope.showalertMessage("Device Enable Successfully", "");
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });             
       };
       
       $scope.disableDevice = function(device){
           var data = {
				   eFmFmClientBranchPO:{branchId:branchId},
				   isActive:'N',
				   imeiNumber:device.imeiNumber
			};
		   $http.post('services/device/devicestatus/',data).
				    success(function(data, status, headers, config) {
                        device.deviceStatus = 'N';
				    	$scope.showalertMessage("Device Disable Successfully", "");
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });      
       };
       
       //****************************************INSPECTION VEHICLE**********************************************************
       $scope.inspection= {vendor:{'name':'All Vendors', 'Id':0},
                           document_rc:"NO", 
                           document_insurance:"NO", 
                           document_driverLicense:"NO", 
                           document_updateJmp:"NO", 
                           firstAidKit:"NO", 
                           fireExtingusher:"NO",
                           seatBeltsWorking:"NO",
                           placard:"NO",
                           mosquitoBat:"NO",
                           panicButton:"NO",
                           walkyTalky:"NO",
                           GPS:"NO",
                           driverUniform:"NO",
                           stephney:"NO",
                           umbrella:"NO",
                           torch:"NO",
                           toolkit:"NO",
                           seatUpholtseryCleanNotTorn:"NO",
                           vehcileRoofUpholtseryCleanNotTorn:"NO",
                           vehcileFloorUpholtseryCleanNotTorn:"NO",
                           vehcileDashboardClean:"NO",
                           vehicleGlassCleanStainFree:"NO",
                           vehicleInteriorLightBrightWorking:"NO",
                           bolsterSeperatingLastTeoSeats:"NO",
                           exterior_scratches:"NO",
                           exterior_noPatchWork:"NO",
                           brake_pedalBrakeWorking:"NO",
                           brake_handBrakeWorking:"NO",
                           tyres_noDentsTrimWheel:"NO",
                           tyres_tyresGoodCondition:"NO",
                           tyres_allTyresAndStephneyInflate:"NO",
                           tyres_jackAndTool:"NO",
                           tyres_numberofPunctruresdone:0,
                           wiperWorking:"NO",
                           ac_coolingIn5mins:"NO",
                           ac_noSmellInAC:"NO",
                           ac_acVentsClean:"NO",
                           enginOilChangeIndicatorOn:"NO",
                           coolantIndicatorOn:"NO",
                           brakeClutchIndicatorOn:"NO",
                           light_highBeamWorking:"NO",
                           light_lowBeamWorking:"NO",
                           light_rightFromtIndicatorWorking:"NO",
                           light_leftFrontIndicatorWorking:"NO",
                           light_parkingLightsWorking:"NO",
                           light_ledDayTimeRunningLightWorking:"NO",
                           rightRearIndicatorWorking:"NO",
                           light_leftRearIndicatorWorking:"NO",
                           light_brakeLightsOn:"NO",
                           light_reverseLightsOn:"NO",
                           deisel:"Nearly Empty",
                           hornWorking:"NO",
                           reflectionSignBoard:"NO",
                           driverName:"",
                           remarks:""                           
                          };
       $scope.deisels = ['Full', 'Half', 'Quater', 'Nearly Empty'];
       
       $scope.punctures = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30];
       
       $scope.getVehicleInspectionForm = function(){
           $scope.allInspectionVendors = $scope.getAllVendors();
           $scope.vendor = {};
          // $scope.vendor.Vendors = {'name':'All Vendors', 'Id':0};
       };
       
       $scope.setVendor = function(vendorSelected){
       }
       
       $scope.submitInspection = function(inspection){
           console.log(inspection);
           alert(JSON.stringify(inspection));
       }
       
       $scope.nextToPage2 = function(){
           $('.page1').slideUp('slow');
           $('.page2').show();
       };
       
       $scope.nextToPage3 = function(){
           $('.page2').slideUp('slow');
           $('.page3').show();
       };
       
       $scope.nextToPage4 = function(){
           $('.page3').slideUp('slow');
           $('.page4').show();
       };
       
       $scope.backToPage3 = function(){
           $('.page3').slideDown('slow');
           $('.page4').hide();
       };
       
       $scope.backToPage2 = function(){
           $('.page2').slideDown('slow');
           $('.page3').hide();
       };
       
       $scope.backToPage1 = function(){
           $('.page1').slideDown('slow');
           $('.page2').hide();
       };
       //******************************************************************
       
       //FUNCTION : Get aLL aCTIVE vENDORS to Populate the drop Down
       $scope.getAllVendors = function(){
    	   $scope.allVendors = [];
          var data = {
                      branchId:branchId
                   };
          $http.post('services/contract/allActiveVendor/',data).
                success(function(data, status, headers, config) {
                      angular.forEach(data, function(item){
                          $scope.allVendors.push({'name':item.name, 'Id':item.vendorId});
                      });//                             
                      $scope.allVendors.unshift({'name':'All Vendors', 'Id':0});
                }).
                error(function(data, status, headers, config) {
                      // log error
                });
           //alert($scope.allVendors);
           return $scope.allVendors;
       };
       
};    
    
    
//**********************************************ADD NEW VENDOR CONTROLLER******************************************************************
    var addVendorCtrl = function($scope, $modalInstance, $state, $http, $timeout){
        $scope.format = 'dd-MM-yyyy';
        $scope.alertMessage;
        $scope.alertHint;    
        $scope.newVendor = {'vendorId':'',
                             'vendorName':'',
                             'vendorMobileNumber':'',
                             'vendorContactNumber':'',
                             'vendorContactName':'',
                             'email':'',
                             'noOfVehicle':''};  
        
        $scope.dateOptionsFrom = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };
        $scope.dateOptionsTo = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };
        $scope.contractStartCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'contractStartDate' : true};  
             }, 50);
        };
        
        $scope.contractEndCal = function($event){              
           $event.preventDefault();
           $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'contractEndDate' : true};  
             }, 50);            
        };  
    
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
    	
    	$scope.addNewVendor = function(result){  
           $scope.isInvalid = false;
           if((new Date(result.contractStartdate)*1000)>(new Date(result.contractEndDate)*1000)){
              $scope.isInvalid = true;
           }
           else{
        	   var data = {
                       eFmFmClientBranchPO:{branchId:branchId},
                       vendorName:result.vendorName,
        				vendorMobileNo:result.vendorMobileNumber,      				
        				emailId:result.email,
        				vendorContactNumber1:result.vendorContactNumber,
        				vendorContactName4:result.vendorContactName,
        				address:result.address,
        				vendorContactNumber2:result.vendorContactNumber2,
        				vendorContactNumber2:result.vendorContactName2,     				
        				vendorContactNumber3:result.vendorContactNumber3,
                       vendorContactName3:result.vendorContactName3,                     
                       vendorContactNumber4:result.vendorContactNumber4,
                       vendorContactName4:result.vendorContactName4
        				/*noOfDays:result.numDays,	
        				contractStartDate:result.contractStartdate,		   
        				contractEndDate:result.contractEndDate*/
       			     }; 	
              console.log(data);   
		      $http.post('services/xlUtilityVendorUpload/addnewvendor/',data).
				    success(function(data, status, headers, config) {
				    	if(data.status=="exist"){
				    		$scope.showalertMessageModal("This vendorName already exist", "");
				    	}
				    	else{
                            $('.loading').show();
					    	$scope.showalertMessageModal("Vendor added successfully", "");
                            $timeout(function() {$modalInstance.close(result)}, 3000);
				    	}				    	
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
           } 		   
    	};    	
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };

//**********************************************ADD NEW DRIVER CONTROLLER******************************************************************
    var newDriverVendorCtrl = function($scope, $modalInstance, $state, $http, $timeout, masterVendor){  
        $scope.IntegerNumber = /^\d+$/;
        $scope.alertMessage;
        $scope.alertHint;    
        $scope.newDriver = {'driverId':'',
                            'driverName': '',
                           'driverAddress' : '',
                           'driverdob': '',
                           'InsuranceValid': '',
                           'licenceValid': '',
                           'licenceNumber': '',
                           'mobileNumber':'' };  
    
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
        
        
        $scope.dateOptions = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };
        $scope.format = 'dd-MM-yyyy';
        
        $scope.insurranceExpDateCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'insurranceExpDate' : true};  
             }, 50);
        };
        
        $scope.openExpDateCal = function($event){              
           $event.preventDefault();
           $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedExpDate' : true};  
             }, 50);            
        };
        
        $scope.openDobCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openeddob' : true};  
             }, 50);
        };
        
        $scope.openDDTCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedDDT' : true};  
             }, 50);
        };
        
        $scope.openBatchCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedBatch' : true};  
             }, 50);
        };
        
        $scope.openBatchNumCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedBatchNum' : true};  
             }, 50);
        };
        
        $scope.openPoliceVerificationCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedPoliceVerification' : true};  
             }, 50);
        };
        
        $scope.openMedicalExpiryCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedMedicalExpiry' : true};  
             }, 50);
        };
        
        $scope.openAntiExpiryCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedAntiExpiry' : true};  
             }, 50);
        };
        
        $scope.openJoiningCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedJoining' : true};  
             }, 50);
        };               
       
       //Convert the dates in DD-MM-YYYY format
       var convertDateUTC = function(date){
          var convert_date = new Date(date);
          var currentDay = convert_date.getDate();
              if (currentDay < 10) { 
                  currentDay = '0' + currentDay; 
              }
          var currentMonth = convert_date.getMonth()+1;
              if (currentMonth < 10) { 
                  currentMonth = '0' + currentMonth;
              }
          console.log(currentDay+'-'+currentMonth+'-'+convert_date.getFullYear());
          return currentDay+'-'+currentMonth+'-'+convert_date.getFullYear();   
        };
        
        
    	$scope.addDriver = function(result){
            $scope.isInvalid = false;
            if((new Date(result.issDate)*1000)>(new Date(result.expDate)*1000)){
                $scope.isInvalid = true;
            }
	        else{
	        	var data = {
                		//efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                		efmFmVendorMaster:{vendorName:masterVendor,eFmFmClientBranchPO:{branchId:branchId}},
                					firstName: result.driverName,
                                    address : result.driverAddress,
                                    dob: result.driverdob,
                                    licenceValid: result.licenceValid,
                                    licenceNumber: result.licenceNumber,
                                    mobileNumber:result.mobileNumber,
                                    batchNumber:result.driverBatchNum,
                                    ddtValidDate:result.driverDDT,
                                    batchDate: result.driverBatch,
                                    policeVerificationValid:result.driverPoliceVerification,
                                    medicalFitnessCertificateValid:result.driverMedicalExpiry,                                    
                                    driverAntiExpiry:result.driverAntiExpiry,
                                    dateOfJoining:result.driverJoining};
                console.log(data);
 		   $http.post('services/xlUtilityDriverUpload/addnewdriver/',data).
 				    success(function(data, status, headers, config) { 				    
 				    	if(data.status=="exist"){
 				    		$scope.showalertMessageModal("This Driver already exist with this licence number", "");
 				    	}
 				    	else{
                            $('.loading').show();
 					    	$scope.showalertMessageModal("Driver added successfully", "");
                            $timeout(function() {$modalInstance.close(result)}, 3000);
 				    	}
 				    	
 				    }).
 				    error(function(data, status, headers, config) {
 				      // log error
 				    });                
            }      
    	};    	
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    
//**********************************************ADD NEW VEHICLE CONTROLLER******************************************************************
    var newVehicleVendorCtrl = function($scope, $modalInstance, $state, $http, $timeout, masterVendor){ 
        $scope.IntegerNumber = /^\d+$/;
        $scope.alertMessage;
        $scope.alertHint;    
       
    	$scope.paginations = [{'value':1, 'text':'1 record'},
                              {'value':10, 'text':'10 records'},
                              {'value':15, 'text':'15 record'},
                              {'value':20, 'text':'20 records'}];
        
        $scope.selectVendors = [{'value':'Fast Track'},
	    	 				   {'value':'BHARATHICALLTAXI'},
	    	 				   {'value':'OLACABS'},
	    	 				   {'value':'Meru Call Taxi'},
	    	 				   {'value':'Golden Call Taxi'},
	    	 				   {'value':'Yellow Call Taxi'}];

	   $scope.selectSeats = [{'value':2, 'text' : '2 Seater'},
	                         {'value':4, 'text' : '4 Seater'},
	                         {'value':7, 'text' : '7 Seater'},
	                         {'value':9, 'text' : '9 Seater'},
	                         {'value':11, 'text' : '11+ Seater'}];
        
         $scope.newVehicle = {'vehicleId': '',
                               'vehicleOwnerName': '',
                               'vehicleNumber':'',
                               'PermitValid':'',
                               'contactNo': '',
                               'contactName': '',
                               'capacity': '',
                               'regCert': '',
                               'pollutionCert': '',
                               'pollutionExpDate': '',
                               'modelYear': '',
                               'InsuranceNumber': '',
                               'InsuranceDate': '',
                               'roadTax': '',
                               'selectDrivers': '',
                               'mobileNumber' :''};   
    
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
        
       $scope.dateOptions = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };
       $scope.format = 'dd-MM-yyyy';
        
       $scope.pollutionExpDateCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'pollutionExpDate' : true};  
             }, 50);
         };
         
      $scope.InsuranceExpiryCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'InsuranceExpiryDate' : true};  
             }, 50);
         };
        
        $scope.TaxValidCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'TaxValidDate' : true};  
             }, 50);
         };
        
        $scope.vehicleFitnessCertCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'vehicleFitnessCert' : true};  
             }, 50);
         };
        
        $scope.registrationDateCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'registrationDate' : true};  
             }, 50);
         };
         
         
         $scope.PermitValidCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'PermitValidDate' : true};  
             }, 50);
         };              
       
       //Convert the dates in DD-MM-YYYY format
       var convertDateUTC = function(date){
          var convert_date = new Date(date);
          var currentDay = convert_date.getDate();
              if (currentDay < 10) { 
                  currentDay = '0' + currentDay; 
              }
          var currentMonth = convert_date.getMonth()+1;
              if (currentMonth < 10) { 
                  currentMonth = '0' + currentMonth;
              }
          console.log(currentDay+'-'+currentMonth+'-'+convert_date.getFullYear());
          return currentDay+'-'+currentMonth+'-'+convert_date.getFullYear();   
        };
        
      $scope.addVehicle = function(result){
    	  var data = {efmFmVendorMaster:{vendorName:masterVendor,eFmFmClientBranchPO:{branchId:branchId}},
		  			eFmFmVendorContractTypeMaster:{contractTypeId:1},//need to modify
		  			 vehicleMake:result.vehicleName,		                     
                   vehicleNumber:result.vehicleNumber,
                   vehicleEngineNumber : result.contactNo,
                   vehicleOwnerName: result.contactName,
                   capacity: result.capacity.value,
                   registartionCertificateNumber:result.regCert,
                   vehicleModel:result.pollutionCert,
                   polutionValid:result.pollutionExpDate,
                   vehicleModelYear:result.modelYear,
                   taxCertificateValid:result.TaxValid,
                   insuranceValidDate:result.InsuranceDate,
                   status:'P',
                   contractDetailId:result.conTariffId,
                   //roadTax		                     
                   permitValidDate:result.PermitValid,
                   registrationDate:result.registrationDate,
                   vehicleFitnessDate:result.vehicleFitnessCert
                  
    	  };
          console.log(data);
		   $http.post('services/xlUtilityVehicleUpload/addnewvehicle/',data).
				    success(function(data, status, headers, config) {
				    	if(data.status=="exist"){
				    		$scope.showalertMessageModal("This vehicleNumber already exist", "");
				    	}
				    	else{
                            $('.loading').show();
					    	$scope.showalertMessageModal("Vehicle added successfully", "");
                            $timeout(function() {$modalInstance.close(result)}, 3000);
				    	}
				    	
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
    	};    	
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    
//**********************************************ADD NEW ESCORT CONTROLLER******************************************************************
    var newEscortCtrl = function($scope, $modalInstance, $state, $http, $timeout){
        $scope.IntegerNumber = /^\d+$/;
        $scope.alertMessage;
        $scope.alertHint;    
        
        $scope.newEscort = {'escortId': '',
                            'escortName':'',
                            'escortMobileNumber': '',
                            'escortdob': '',
                            'escortAddress': ''};  
    
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
        
        $scope.dateOptions = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };
        
       $scope.format = 'dd-MM-yyyy';
        
         $scope.dobCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'dobDate' : true};  
             }, 50);
         };              
       
       //Convert the dates in DD-MM-YYYY format
       var convertDateUTC = function(date){
          var convert_date = new Date(date);
          var currentDay = convert_date.getDate();
              if (currentDay < 10) { 
                  currentDay = '0' + currentDay; 
              }
          var currentMonth = convert_date.getMonth()+1;
              if (currentMonth < 10) { 
                  currentMonth = '0' + currentMonth;
              }
          console.log(currentDay+'-'+currentMonth+'-'+convert_date.getFullYear());
          return currentDay+'-'+currentMonth+'-'+convert_date.getFullYear();   
        };    	
    	$scope.addEscort = function(result){  
          	 var data = {
					   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
          			 	 firstName:result.escortName,
          			      dateOfBirth:result.escortdob,
          			      mobileNumber:result.escortMobileNumber,  				
        				  address:result.escortAddress,
        				  escortEmployeeId: result.escortBatchNumber,
        				  vendorName:result.escortVendorName
       			     };	
            console.log(data);
       		      $http.post('services/escort/addEscortDetails/',data).
       				    success(function(data, status, headers, config) {
 //      				    	alert(JSON.stringify(data));
       				    	if(data.status=='fail'){
       				    		$scope.showalertMessageModal("Please check VendorName", "");
       				    	}
       				    	else{
                                $('.loading').show();
       					    	$scope.showalertMessageModal("Escort added successfully", "");
                                $timeout(function() { $modalInstance.close(result)}, 3000); 
       				    	}
       				    }).
       				    error(function(data, status, headers, config) {
       				      // log error
       				    });	
    	};    	
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
  
/***********************************************EDIT VENDOR CONTROLLER******************************************************************/
    var editVendorCtrl = function($scope, $modalInstance, $state, $http, vendor, $timeout){ 
        $scope.IntegerNumber = /^\d+$/;
        $scope.alertMessage;
        $scope.alertHint; 
        var extension; 
        $scope.editVendor = {'vendorId':vendor.vendorId,
                             'vendorName':vendor.vendorName,
                             'vendorMobileNumber':vendor.vendorMobileNumber,
                             'vendorContactNumber':vendor.vendorContactNumber,
                             'vendorContactName':vendor.vendorContactName,
                             'address':vendor.vendorAddress,
                             'numDays':'',
                             'email':vendor.emailId,
                             'contractStartdate':vendor.contractStartDate,
                             'contractEndDate':vendor.contractEndDate,
                             'noOfVehicle':vendor.noOfVehicle,
                             'noOfDriver':vendor.noOfDriver,
                             'vendorContactName2':vendor.vendorContactName1,
                             'vendorContactNumber2':vendor.vendorContactNumber1,
                             'vendorContactName3':vendor.vendorContactName2,
                             'vendorContactNumber3':vendor.vendorContactNumber2,
                             'vendorContactName4':vendor.vendorContactName3,
                             'vendorContactNumber4':vendor.vendorContactNumber3};  
    
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
        
        $scope.vendorDocuments = [{name:'test1', location:'temp/test1.pdf', imgSrc:'images/docImg.png'},
                                 {name:'test2', location:'temp/test2.xls', imgSrc:'images/docImg.png'},
                                 {name:'test3', location:'temp/test3.docx', imgSrc:'images/docImg.png'}];        
        angular.forEach($scope.vendorDocuments, function(item) {
		  		    	extension = item.location.split('.').pop();  
                        switch (extension){
                            case 'pdf':
                                item.imgSrc = 'images/adobeAcrobat.png';
                                break;
                            case 'docx':
                                item.imgSrc = 'images/msWord.png';
                                break;
                            case 'xls':
                                item.imgSrc = 'images/msExcel.png';
                                break;
                            default:
                                item.imgSrc = 'images/docImg.png';                        
                        }
		  	         });
        
        
        $scope.dateOptions = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };     
        
        $scope.format = 'dd-MM-yyyy';
        
        $scope.contractStartCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'contractStartDate' : true};  
             }, 50);
        };
        
        $scope.contractEndCal = function($event){              
           $event.preventDefault();
           $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'contractEndDate' : true};  
             }, 50);            
        };
        
    	$scope.deleteDocument = function(index){
            $confirm({text: 'Are you sure you want to delete this docuument?', title: 'Delete Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
            $('#div'+index).hide('slow');
            });
        };
        
    	$scope.saveVendor = function(result){
//           	 var data = {		
//           			 	vendorId:result.vendorId,		
//         				vendorName:result.vendorName,		
//         				vendorMobileNo:result.vendorMobileNumber,		
//         				emailId:result.email,			
//         				vendorContactName:result.vendorContactName,
//         				vendorOfficeNo:result.vendorContactNumber,			
//         				address:result.address,
//                        vendorContactName2:result.vendorContactName2,
//                        vendorOfficeNo2:result.vendorContactNumber2,
//                        vendorContactName3:result.vendorContactName3,
//                        vendorOfficeNo3:result.vendorContactNumber3,
//                        vendorContactName4:result.vendorContactName4,
//                        vendorOfficeNo4:result.vendorContactNumber4};	
            var data = {		
           			 	vendorId:result.vendorId,		
         				vendorName:result.vendorName,		
         				vendorMobileNo:result.vendorMobileNumber,		
         				emailId:result.email,			
         				vendorContactName:result.vendorContactName,
         				vendorOfficeNo:result.vendorContactNumber,			
         				address:result.address};	
            console.log(data);
        		      $http.post('services/vendor/modifyVendorDetails/',data).		
        				    success(function(data, status, headers, config) {		
        				    	if(data.status=="exist"){		
        				    		$scope.showalertMessageModal("This vendorName already exist", "");		
        				    	}		
        				    	else{
                                    $('.loading').show();
        					    	$scope.showalertMessageModal("Vendor modified successfully", "");
                              //      alert(result.address + ' - '+result.email);
                                    $timeout(function() {$modalInstance.close(result)}, 3000);		
        				    	}        				    			
        				    }).		
        				    error(function(data, status, headers, config) {		
        				      // log error		
        				    });     			
    	};    	
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }; 
    
 /********************************************EDIT DRIVER CONTROLLER*******************************************************************/
    var editDriverCtrl = function($scope, $modalInstance, $state, $http, driver, $timeout){
        $scope.format = 'dd-MM-yyyy';
        $scope.IntegerNumber = /^\d+$/;
        $scope.alertMessage;
        $scope.alertHint;    
        
        var dateFormatConverter = function(x){
            var date = x.split('-');
            return date[1]+'-'+date[0]+'-'+date[2];
        };        
                       
       //Convert the dates in DD-MM-YYYY format
       var convertDateUTC = function(date){
          var convert_date = new Date(date);
          var currentDay = convert_date.getDate();
              if (currentDay < 10) { 
                  currentDay = '0' + currentDay; 
              }
          var currentMonth = convert_date.getMonth()+1;
              if (currentMonth < 10) { 
                  currentMonth = '0' + currentMonth;
              }
          console.log(currentDay+'-'+currentMonth+'-'+convert_date.getFullYear());
          return currentDay+'-'+currentMonth+'-'+convert_date.getFullYear();   
        };
        
//        $scope.editDriver = {'driverId':driver.driverId,
//                           'driverName': driver.driverName,
//                           'mobileNumber':driver.mobileNumber,
//                           'driverAddress' : driver.driverAddress,
//                           'driverdob':driver.dateOfBirth,
//                           'driverMedicalExpiry': new Date(dateFormatConverter(driver.medicalCertificateValid)),
//                           'licenceValid': new Date(dateFormatConverter(driver.licenceValid)),
//                           'licenceNumber': driver.licenceNumber};
        
//        $scope.editDriver = {'driverId':driver.driverId,
//                           'driverName': driver.driverName,
//                           'mobileNumber':driver.mobileNumber,
//                           'driverAddress' : driver.driverAddress,
//                           'driverdob':driver.dateOfBirth,
//                           'driverMedicalExpiry': new Date(dateFormatConverter(driver.medicalCertificateValid)),
//                           'licenceValid': new Date(dateFormatConverter(driver.licenceValid)),
//                           'licenceNumber': driver.licenceNumber,
//                           'batchNumber':driver.batchNumber,
//                           'driverDDT':new Date(dateFormatConverter(driver.ddtExpiryDate)),
//                           'driverBatchDate': new Date(dateFormatConverter(driver.batchDate)),
//                           'policeVerification':new Date(dateFormatConverter(driver.policeExpiryDate)),                                    
//                           'driverAntiExpiry':new Date(dateFormatConverter(driver.driverAntiExpiry)),
//                           'driverJoiningDate':new Date(dateFormatConverter(driver.driverJoining))};
        
        $scope.editDriver = {'driverId':driver.driverId,
                           'driverName': driver.driverName,
                           'mobileNumber':driver.mobileNumber,
                           'driverAddress' : driver.driverAddress,
                           'driverdob':driver.dateOfBirth,
                           'driverMedicalExpiry': new Date(dateFormatConverter(driver.medicalCertificateValid)),
                           'licenceValid': new Date(dateFormatConverter(driver.licenceValid)),
                           'licenceNumber': driver.licenceNumber,
                           'driverBatchNum':driver.driverBatchNum,
                           'driverDDT':new Date(dateFormatConverter(driver.ddtExpiryDate)),
                           'driverBatch': new Date(dateFormatConverter(driver.batchDate)),
                           'driverPoliceVerification':new Date(dateFormatConverter(driver.policeExpiryDate)),
                            'driverJoining':driver.driverJoining}; 
    
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
        
        $scope.driverDocuments = [{name:'test1', location:'temp/test1.pdf', imgSrc:'images/docImg.png'},
                                 {name:'test2', location:'temp/test2.xls', imgSrc:'images/docImg.png'},
                                 {name:'test3', location:'temp/test3.docx', imgSrc:'images/docImg.png'}];
        
        angular.forEach($scope.driverDocuments, function(item) {
		  		    	extension = item.location.split('.').pop();  
                        switch (extension){
                            case 'pdf':
                                item.imgSrc = 'images/adobeAcrobat.png';
                                break;
                            case 'docx':
                                item.imgSrc = 'images/msWord.png';
                                break;
                            case 'xls':
                                item.imgSrc = 'images/msExcel.png';
                                break;
                            default:
                                item.imgSrc = 'images/docImg.png';                        
                        }
		  	         });
        
       $scope.dateOptions = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };
        $scope.insurranceExpDateCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'insurranceExpDate' : true};  
             }, 50);
        };
        
        $scope.openExpDateCal = function($event){              
           $event.preventDefault();
           $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedExpDate' : true};  
             }, 50);            
        };
        
        $scope.openDobCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openeddob' : true};  
             }, 50);
        };
        
        $scope.openDDTCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedDDT' : true};  
             }, 50);
        };
        
        $scope.openBatchCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedBatch' : true};  
             }, 50);
        };
        
        $scope.openBatchNumCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedBatchNum' : true};  
             }, 50);
        };
        
        $scope.openPoliceVerificationCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedPoliceVerification' : true};  
             }, 50);
        };
        
        $scope.openMedicalExpiryCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedMedicalExpiry' : true};  
             }, 50);
        };
        
        $scope.openAntiExpiryCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedAntiExpiry' : true};  
             }, 50);
        };
        
        $scope.openJoiningCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openedJoining' : true};  
             }, 50);
        };
        
        $scope.deleteDocument = function(index){
            $confirm({text: 'Are you sure you want to delete this row?', title: 'Delete Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {
                $('#div'+index).hide('slow');
                         });
            
        };
    	
    	$scope.saveDriver = function(result){ 
    		alert("hi");
           	var data = {driverId:result.driverId,
                        firstName: result.driverName,
                        address : result.driverAddress,
                        dobDate: convertDateUTC(result.driverdob),                       
                        driverMedicalExpiryDate: convertDateUTC(result.driverMedicalExpiry),                       
                        licenceValidDate: convertDateUTC(result.licenceValid),
                        licenceNumber: result.licenceNumber,
                        mobileNumber:result.mobileNumber,
                        batchNumber:result.driverBatchNum,
                        ddtExpiryDate:convertDateUTC(result.driverDDT),
                        driverBatchDate: convertDateUTC(result.driverBatch),                       
                        driverPoliceVerificationDate:convertDateUTC(result.driverPoliceVerification),
                        driverMedicalExpiryDate:convertDateUTC(result.driverMedicalExpiry),                                    
                        driverJoiningDate:convertDateUTC(result.driverJoining)};	          
            console.log(data);
        	$http.post('services/vendor/modifyDriverDetails/',data).		
        		  success(function(data, status, headers, config) {		
        			if(data.status=="exist"){		
        				  $scope.showalertMessageModal("This Driver details already exist", "");		
        			}		
        			else{	
                         $('.loading').show();
        				 $scope.showalertMessageModal("Driver modified successfully", "");		
                         $timeout(function() {$modalInstance.close(result)}, 3000);		
        			}		
        		}).		
        		 error(function(data, status, headers, config) {		
        				      // log error		
        		});
    	};    	
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };   
    
/*************************************EDIT VEHICLE MODAL CONTROLLER*****************************************************************/
     var editVehicleCtrl = function($scope, $modalInstance, $state, $http, vehicle, $timeout){
        $scope.format = 'dd-MM-yyyy';
        $scope.IntegerNumber = /^\d+$/;
        $scope.alertMessage;
        $scope.alertHint;    
        
        var dateFormatConverter = function(x){
            var date = x.split('-');
            return date[1]+'-'+date[0]+'-'+date[2];
        };         
                       
       //Convert the dates in DD-MM-YYYY format
       var convertDateUTC = function(date){
          var convert_date = new Date(date);
          var currentDay = convert_date.getDate();
              if (currentDay < 10) { 
                  currentDay = '0' + currentDay; 
              }
          var currentMonth = convert_date.getMonth()+1;
              if (currentMonth < 10) { 
                  currentMonth = '0' + currentMonth;
              }
          console.log(currentDay+'-'+currentMonth+'-'+convert_date.getFullYear());
          return currentDay+'-'+currentMonth+'-'+convert_date.getFullYear();   
        };      
         
        $scope.selectVendors = [{'value':'Fast Track'},
	    	 				   {'value':'BHARATHICALLTAXI'},
	    	 				   {'value':'OLACABS'},
	    	 				   {'value':'Moorthy'},
	    	 				   {'value':'Golden Call Taxi'},
	    	 				   {'value':'Yellow Call Taxi'}];

	    $scope.selectSeats = [{'value':2, 'text' : '2 Seater'},
	                         {'value':4, 'text' : '4 Seater'},
                             {'value':5, 'text' : '5 Seater'},
                             {'value':6, 'text' : '6 Seater'},
	                         {'value':7, 'text' : '7 Seater'},
                             {'value':8, 'text' : '8 Seater'},
	                         {'value':9, 'text' : '9 Seater'},
                             {'value':10, 'text' : '10 Seater'},
                             {'value':11, 'text' : '11 Seater'},
                             {'value':12, 'text' : '12 Seater'},
	                         {'value':13, 'text' : '13 Seater'}];
        
        var vehicleOwnerIndex =  _.findIndex($scope.selectVendors, {value:vehicle.vehicleOwnerName});
        var capacityIndex =  _.findIndex($scope.selectSeats, {value:vehicle.capacity});
        var extension;        
        $scope.vehicleDocuments = [{name:'test1', location:'temp/test1.pdf', imgSrc:'images/docImg.png'},
                                 {name:'test2', location:'temp/test2.xls', imgSrc:'images/docImg.png'},
                                 {name:'test3', location:'temp/test3.docx', imgSrc:'images/docImg.png'}];        
        angular.forEach($scope.vehicleDocuments, function(item) {
		  		    	extension = item.location.split('.').pop();  
                        switch (extension){
                            case 'pdf':
                                item.imgSrc = 'images/adobeAcrobat.png';
                                break;
                            case 'docx':
                                item.imgSrc = 'images/msWord.png';
                                break;
                            case 'xls':
                                item.imgSrc = 'images/msExcel.png';
                                break;
                            default:
                                item.imgSrc = 'images/docImg.png';                        
                        }
		  	         });
         
        $scope.editVehicle = {'vehicleId': vehicle.vehicleId,
                'vehicleOwnerName': $scope.selectVendors[vehicleOwnerIndex],
                'vehicleNumber':vehicle.vehicleNumber,
                'PermitValid':new Date(dateFormatConverter(vehicle.PermitValid)),
                'contactNo': vehicle.vehicleEngineNumber,
                'contactName':vehicle.vehicleOwnerName,
                'capacity': $scope.selectSeats[capacityIndex],
                'regCert':vehicle.rcNumber,
                'pollutionCert': '',
                'TaxValid':new Date(dateFormatConverter(vehicle.taxCertificateValid)),
                'pollutionExpDate':new Date(dateFormatConverter(vehicle.polutionValid)),
                'vehicleModel': vehicle.vehicleModel,
                'modelYear':vehicle.vehicleModelYear,
                'InsuranceDate': new Date (dateFormatConverter(vehicle.InsuranceDate)),
                'vehicleName': vehicle.vehicleMake,
                'conType': vehicle.contractType,
                'conTariffId': vehicle.contractTariffId,
                'selectDrivers': '',
                'mobileNumber' : vehicle.mobileNumber,
                'registrationDate':new Date(dateFormatConverter(vehicle.registrationDate)),
                'vehicleFitnessCert':new Date(dateFormatConverter(vehicle.vehicleFitnessDate))
                             };  
    
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
         
        $scope.dateOptions = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };
         $scope.pollutionExpDateCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'pollutionExpDate' : true};  
             }, 50);
         };
         
         $scope.InsuranceExpiryCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'InsuranceExpiryDate' : true};  
             }, 50);
         };
        
        $scope.vehicleFitnessCertCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'vehicleFitnessCert' : true};  
             }, 50);
         };
        
        $scope.registrationDateCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'registrationDate' : true};  
             }, 50);
         };
         
         $scope.PermitValidCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'PermitValidDate' : true};  
             }, 50);
         };
         
         $scope.TaxValidCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'TaxValidDate' : true};  
             }, 50);
         };
         
        $scope.deleteDocument = function(index){
            $('#div'+index).hide('slow');
        };
         
    	$scope.updateVehicle = function(result){
             	 var data = {vehicleId:result.vehicleId,
			  			 vehicleMake:result.vehicleName,		                     
	                     vehicleNumber:result.vehicleNumber,
	                     vehicleEngineNumber : result.contactNo,
	                     vehicleOwnerName: result.contactName,
	                     capacity: result.capacity.value,
	                     registartionCertificateNumber:result.regCert,
	                     vehicleModel:result.modelYear,	                     
	                     polutionDate: convertDateUTC(result.pollutionExpDate),	                     
	                     vehicleModelYear:result.modelYear,
	                     taxValidDate:convertDateUTC(result.TaxValid),	                     
	                     insuranceValid:convertDateUTC(result.InsuranceDate), 	                     
	                     contractDetailId:result.conTariffId,
	                     eFmFmVendorContractTypeMaster:{contractTypeId:1},//need to modify	                     
	                     permitValid:convertDateUTC(result.PermitValid),		         
	                     registrationValid:convertDateUTC(result.registrationDate),	                     
	                     maintenanceValid:convertDateUTC(result.vehicleFitnessCert)             	 
             	 };		
            console.log(data);
          		      $http.post('services/vehicle/modifyVehicleDetails/',data).		
          				    success(function(data, status, headers, config) {		
          				    	if(data.status=="exist"){
          				    		$scope.showalertMessageModal("This Vehicle details already exist", "");		
          				    	}		
          				    	else{
                                    $('.loading').show();
          					    	$scope.showalertMessageModal("Vehicle modified successfully", "");		
                                    $timeout(function() {$modalInstance.close(result)}, 3000);		
          				    	}
          				    			
          				    }).		
          				    error(function(data, status, headers, config) {		
          				      // log error		
          				    });  
    	};    	
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    
/**************************************************EDIT ESCORT CONTROLLER**********************************************************/    
    var editEscortCtrl = function($scope, $modalInstance, $state, $http, $timeout, escort){
        console.log(escort);
        $scope.IntegerNumber = /^\d+$/; 
        $scope.alertMessage;
        $scope.alertHint;          
       
        var dateFormatConverter = function(x){
            var date = x.split('-');
            return date[1]+'-'+date[0]+'-'+date[2];
        };         
                       
       //Convert the dates in DD-MM-YYYY format
       var convertDateUTC = function(date){
          var convert_date = new Date(date);
          var currentDay = convert_date.getDate();
              if (currentDay < 10) { 
                  currentDay = '0' + currentDay; 
              }
          var currentMonth = convert_date.getMonth()+1;
              if (currentMonth < 10) { 
                  currentMonth = '0' + currentMonth;
              }
          console.log(currentDay+'-'+currentMonth+'-'+convert_date.getFullYear());
          return currentDay+'-'+currentMonth+'-'+convert_date.getFullYear();   
        };      
        
        $scope.editEscort = {'escortId': escort.escortId,
                             'escortName':escort.escortName,
                             'escortMobileNumber': escort.escortMobileNumber,
                             'escortdob':new Date(dateFormatConverter(escort.escortdob)),
                             'escortAddress': escort.escortAddress,
                             'escortVendorName':escort.escortVendorName,
                             'escortBatchNumber':escort.escortBatchNumber
//                             'escortEmployeeId': result.escortBatchNumber,
//                             'escortVendorName':result.escortVendorName
                            }; 
    
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
        
////        var extension;        
////        $scope.escortDocuments = [{name:'test1', location:'temp/test1.pdf', imgSrc:'images/docImg.png'},
////                                 {name:'test2', location:'temp/test2.xls', imgSrc:'images/docImg.png'},
////                                 {name:'test3', location:'temp/test3.docx', imgSrc:'images/docImg.png'}];        
////        
////        angular.forEach($scope.escortDocuments, function(item) {
////		  		    	extension = item.location.split('.').pop();  
////                        switch (extension){
////                            case 'pdf':
////                                item.imgSrc = 'images/adobeAcrobat.png';
////                                break;
////                            case 'docx':
////                                item.imgSrc = 'images/msWord.png';
////                                break;
////                            case 'xls':
////                                item.imgSrc = 'images/msExcel.png';
////                                break;
////                            default:
////                                item.imgSrc = 'images/docImg.png';                        
////                        }
////		  	         });
    	
    	$scope.dateOptions = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };       
          
        $scope.format = 'dd-MM-yyyy';
        
         $scope.dobCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'dobDate' : true};  
             }, 50);
         };
        
////        $scope.deleteDocument = function(index){
////            $('#div'+index).hide('slow');
////        };
////        
        $scope.saveEscort = function(result){
            var data = {
       			 	  escortId:result.escortId,
       			 	  firstName:result.escortName,
       			      mobileNumber:result.escortMobileNumber, 
       			      dateOfBirth:convertDateUTC(result.escortdob),
     				  address:result.escortAddress,
                      escortEmployeeId: result.escortBatchNumber,
                      escortVendorName:result.escortVendorName
    			     };
            console.log(data);
    		      $http.post('services/escort/modifyEscortDetails/',data).
    				    success(function(data, status, headers, config) {  
                                $('.loading').show();
    					    	$scope.showalertMessageModal("Escort modified successfully", "");
                                $timeout(function() {$modalInstance.close(result)}, 3000);
    				    	
    				    }).
    				    error(function(data, status, headers, config) {
    				      // log error
    				    });     	  
    	};    	
//        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    
/*******************************************************************************************************/
    var uploadVehicleCtrl = function($scope, $modalInstance, $state, $http, vehicle, $timeout){
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
        
        $scope.vehicleDocumentTypes = [{'value':'Insurance', 'text':'Vehicle Insurance'},
	    	 				         {'value':'Registration', 'text':'Vehicle Registration'},
	    	 				        {'value':'pollution', 'text':'Polution Certificate'},
	    	 				         {'value':'Permit', 'text':'Vehicle Tax Permit'}];
        
        var typeOfUploadDoc="";       
        $scope.setDocType = function(vehicleDocType){
            typeOfUploadDoc = vehicleDocType.value;
        };   
    	$scope.importVehicleFile = function(result){
            var fd = new FormData();
     	    fd.append( "filename", $("#filenameforactivity")[0].files[0]);
     	    var post_url=$("#addinstgroup").attr("action");
     	    var postdata=$("#addinstgroup").serialize();
     	   if(typeOfUploadDoc.length== 0){
     		   $scope.showalertMessageModal("Please select document type", "");
     		   return false;
     	   }
     	    var vehicleId=vehicle.vehicleId;
     	    var url= post_url+"?"+postdata+"&profileId="+profileId+"&branchId="+branchId+"&vehicleId="+vehicleId+"&typeOfUploadDoc="+typeOfUploadDoc;
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
                    $('.loading').show();
    		    	$scope.showalertMessageModal('Employee data imported successfully.', "");
    	            $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
    	          }
     	        
     	       });
    	};    	
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    
var uploadDriverCtrl = function($scope, $modalInstance, $state, $http, driver, $timeout){ 
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
    
        $scope.driverDocumentTypes = [{'value':'address', 'text':'Address Proof'},
	    	 				         {'value':'medical', 'text':'Medical Certificate'}];
        var typeOfUploadDoc="";
        
        $scope.setDocType = function(driverDocType){
            typeOfUploadDoc = driverDocType.value;
        };       
    	$scope.importDriverFile = function(result){
    		//alert(driver.driverId+typeOfUploadDoc);
            var fd = new FormData();
     	    fd.append( "filename", $("#filenameforactivity")[0].files[0]);
     	    var post_url=$("#addinstgroup").attr("action");
     	    var postdata=$("#addinstgroup").serialize();
     	   if(typeOfUploadDoc.length== 0){
     		   $scope.showalertMessageModal("Please select document type", "");
     		   return false;
     	   }
     	    var driverId=driver.driverId;
     	    var url= post_url+"?"+postdata+"&profileId="+profileId+"&branchId="+branchId+"&driverId="+driverId+"&typeOfUploadDoc="+typeOfUploadDoc;
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
                    $('.loading').show();
    		    	$scope.showalertMessageModal('Employee data imported successfully.', '');
    	            $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
    	          }
     	       });
    	};    	
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
//******************************************************EDIT ENTITY ON DRIVER CHECK-IN*********************************//
//EDIT CHECK IN VEHICLE ENTITIES   
var entityCtrl = function($scope, $modalInstance, $state, $http, $timeout, vehicle){ 
//    alert(vehicle.checkInId);
    console.log('Log::Edit Entity Modal is Opened');
    console.log(vehicle);
        $scope.alertMessage;
        $scope.alertHint;    
        $scope.isNotValid = true;
        $scope.vehicles = [];
        $scope.devices = [];
        $scope.drivers = [];
        $scope.edit = {};
        var deviceId;
        var driverId;
        var vehicleId;
        var checkInId;
        var isdriverChange = false;
        var isVehicleNumberChange = false;
        var isdeviceNumberChange = false;
        var currentDriverIndex;
        var currentVehicleIndex;
        var currentDeviceIndex;
    
        var data = {
				   eFmFmClientBranchPO:{branchId:branchId},
				   vendorId:vehicle.vendorId
			};
		   $http.post('services/vehicle/listOfActiveEntity/',data).
				    success(function(data, status, headers, config) {
//				      alert(JSON.stringify(data));                
                      $scope.Original_vehiclesData = data.vehicleDetails;
                      $scope.Original_driversData = data.driverDetails;
                      $scope.devicesData = data.deviceDetails;
                      $scope.vehiclesData = data.vehicleDetails;
                      $scope.driversData = data.driverDetails;
                      currentDriverIndex = _.findIndex($scope.driversData, { driverId: vehicle.driverId });
                      currentVehicleIndex = _.findIndex($scope.vehiclesData, { vehicleId: vehicle.vehicleId });
                      currentDeviceIndex = _.findIndex($scope.devicesData, { deviceId: vehicle.deviceId });
//               alert('deviceIndex' + currentDeviceIndex);
                      if(currentDriverIndex>=0){
                          $scope.edit.driverName = $scope.driversData[currentDriverIndex];
                      }
               
                      if(currentVehicleIndex>=0){
                          $scope.edit.vehicleNumber = $scope.vehiclesData[currentVehicleIndex];
                      }
               
                      if(currentDeviceIndex>=0){
                          $scope.edit.deviceId = $scope.devicesData[currentDeviceIndex];
                      }
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
    
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

       $scope.updateDriverManual = function(driver){
           if(angular.isObject(driver)){ 
               var clickedDriver_vendorId = driver.vendorId;
               var curentVehicle_vendorId = $scope.edit.vehicleNumber.vendorId;
//               alert(clickedDriver_vendorId + ' - ' + curentVehicle_vendorId);
               console.log('DRIVER Selected has VendorID : ' + driver.vendorId);
               console.log('clickedDriver_vendorId : ' + clickedDriver_vendorId + ' - ' + 'curentVehicle_vendorId : ' + curentVehicle_vendorId);
               var data = {
                        vendorId:driver.vendorId,
                           eFmFmClientBranchPO:{branchId:branchId},
               };
               $http.post('services/vehicle/listOfActiveVehicle/',data).
                     success(function(data, status, headers, config) {           
                       $scope.vehiclesData = data.vehicleDetails;
                       if($scope.vehiclesData.length>0){
                           if(clickedDriver_vendorId != curentVehicle_vendorId){
                               $scope.edit.vehicleNumber = $scope.vehiclesData[0];
                           }                            
                       }
                       else{     
                            $scope.vehiclesData = $scope.Original_vehiclesData;
                            $scope.driversData = $scope.Original_driversData;

                            $scope.edit.driverNumber=$scope.driversData[currentDriverIndex];
                            $scope.edit.driverName = $scope.driversData[currentDriverIndex];
                            $scope.edit.vehicleNumber = $scope.vehiclesData[currentVehicleIndex];

                            $scope.showalertMessageModal("There is no Vehicle Available. Please change the Driver Selection.", "");                   
                       }
                    }).
                    error(function(data, status, headers, config) {
                              // log error
				    });        
           }
       };

       $scope.updateVehicleNumberManual = function(vehicleSelected){
         if(angular.isObject(vehicleSelected)){       
            var clickedvehicler_vendorId = vehicleSelected.vendorId;
            var curentDriver_vendorId = $scope.edit.driverName.vendorId;        
            console.log('VEHICLE Selected has VendorID : ' + vehicleSelected.vendorId);             
            console.log('clickedvehicler_vendorId : ' + clickedvehicler_vendorId + ' - ' + 'curentDriver_vendorId : ' + curentDriver_vendorId);
            vendorId = vehicleSelected.vendorId;
            var data = {
        		vendorId:vehicleSelected.vendorId,
				   eFmFmClientBranchPO:{branchId:branchId},        		
			};
		   $http.post('services/vehicle/listOfActiveDriver/',data).
			 success(function(data, status, headers, config) {
                    $scope.driversData = data.driverDetails;                          
//                    alert(JSON.stringify(data.driverDetails));
                    console.log(data.driverDetails);
                    if($scope.driversData.length>0){   
                        if(clickedvehicler_vendorId != curentDriver_vendorId){                           
                           $scope.edit.driverNumber = $scope.driversData[0];
                           $scope.edit.driverName = $scope.driversData[0];
                       }
                    }
                    else{ $scope.vehiclesData = $scope.Original_vehiclesData;
                          $scope.driversData = $scope.Original_driversData;
                          $scope.edit.driverNumber=$scope.driversData[currentDriverIndex];
                          $scope.edit.driverName = $scope.driversData[currentDriverIndex];
                          $scope.edit.vehicleNumber = $scope.vehiclesData[currentVehicleIndex];
                          $scope.showalertMessageModal("There is no Driver Available. Please change the Vehicle Selection.", "");                   
                    }            
				}).
				error(function(data, status, headers, config) {
				      // log error
                });
         }
       }; 

       $scope.updateDeviceIdManual = function(device){
//           $scope.edit.deviceId = device.deviceId;
           
       }; 
        
      $scope.updateVehicleEntity = function(entity){
            console.log(entity);
            
            if($scope.edit.vehicleNumber.vehicleNumber != vehicle.vehicleNumber){
                isVehicleNumberChange = true;
            }
            else{
                isVehicleNumberChange = false;
            }
            
            if($scope.edit.driverName.driverName != vehicle.DriverName){
                isdriverChange = true;
            }
            else{
                isdriverChange = false;
            } 
            
            if($scope.edit.deviceId.deviceId != vehicle.deviceId){
                isdeviceNumberChange = true;
            }
            else{
                isdeviceNumberChange = false;
            }  
            driverId = $scope.edit.driverName.driverId;
            vehicleId = $scope.edit.vehicleNumber.vehicleId;
            deviceId = $scope.edit.deviceId.deviceId;
            console.log(driverId + ' - ' + vehicleId + ' - ' + deviceId);
            
            if(isdriverChange || isVehicleNumberChange || isdeviceNumberChange){
                console.log(isdriverChange + ' - ' + isVehicleNumberChange + ' - ' + isdeviceNumberChange);
                if($scope.edit.vehicleNumber.vehicleNumber.indexOf('DUMMY')>=0){
                    $scope.showalertMessageModal("Sorry you can not update the dummy entities", "");
                    return false;
                }
                //Here Please put one check If user changing any dummy Vehicle,Driver or device,Please block a user On UI it self
                //Sorry you can'T update dummy entries
                /*if(dummyVehicleNumberCheck.search("DUMMY") != -1) {
         		   $scope.showalertMessage("Sorry you can not update the dummy entities", "");
         		   return false;
         	   } */
                var data = {
                             efmFmVehicleMaster:{vehicleId:vehicleId},
                             eFmFmDeviceMaster:{deviceId:deviceId},         				   
                             efmFmDriverMaster:{driverId:driverId},
                             branchId:branchId,
                             checkInId:vehicle.checkInId  ///Need to put Dynamic value here from the clicked Row
                        };
                       $http.post('services/vehicle/driverCheckIn/',data).
                                success(function(data, status, headers, config) {
                                  $scope.escortAvailableData = data;
                                  if(data=="success"){
                                      $('.loading').show();
                                      $scope.showalertMessageModal("Entity changed Successfully", "");
                                      $timeout(function() {$modalInstance.close(entity)}, 3000);
                                  }else{
                                     $('.loading').show();
                                     $scope.showalertMessageModal("Vehicle and Device Check-In already Allocated", "");
                                  }                       
                                    
                                }).
                                error(function(data, status, headers, config) {
                                  // log error
                                });                
            }
            else{
               $('.loading').show();
               $scope.showalertMessageModal('None of the Entity has been Change', ''); 
               $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
            }            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    
 
/************************************************************************************************************************/
    
    angular.module('efmfmApp').controller('addVendorCtrl', addVendorCtrl);
    angular.module('efmfmApp').controller('newEscortCtrl', newEscortCtrl);
    angular.module('efmfmApp').controller('newDriverVendorCtrl', newDriverVendorCtrl);
    angular.module('efmfmApp').controller('newVehicleVendorCtrl', newVehicleVendorCtrl);
    angular.module('efmfmApp').controller('editVendorCtrl', editVendorCtrl);
    angular.module('efmfmApp').controller('editDriverCtrl', editDriverCtrl);
    angular.module('efmfmApp').controller('editVehicleCtrl', editVehicleCtrl);
    angular.module('efmfmApp').controller('editEscortCtrl', editEscortCtrl);    
    angular.module('efmfmApp').controller('uploadDriverCtrl', uploadDriverCtrl);
    angular.module('efmfmApp').controller('uploadVehicleCtrl', uploadVehicleCtrl);
    angular.module('efmfmApp').controller('vendorDashboardCtrl', vendorDashboardCtrl);
    angular.module('efmfmApp').controller('entityCtrl', entityCtrl);

}());