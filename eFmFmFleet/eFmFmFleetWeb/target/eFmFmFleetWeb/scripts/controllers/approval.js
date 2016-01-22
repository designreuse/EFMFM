(function(){
   var approvalCtrl = function($scope, $state, $http, $location, $anchorScroll, $modal, $confirm){
	   
	   $scope.isClicked = false;
	   $('.approvalMenu').addClass('active');
       
       $scope.driverDocuments = [];
       $scope.vehicleDocuments = [];
       $scope.currentDriverTab = 'Pending';
       $scope.currentVehicleTab = 'Pending';
       $scope.currentVendorTab = 'Pending';
	   console.log("Enter :: Aproval");
	   $scope.$on('$viewContentLoaded', function() {
		    //call it here
		   $scope.getPendingDriver();
		   $scope.getPendingVehicle();
		   $scope.getPendingVendor();
		   
		});
	   
	   $scope.scrollTo = function(id) {
   	    $location.hash(id);
   	    console.log($location.hash());
   	    $anchorScroll();};
       
       var findImgSrc = function(extension){
           var image;
           switch (extension){
               case 'pdf':
                    image = 'images/adobeAcrobat.png';
                    break;
               case 'docx':
                    image = 'images/msWord.png';
                    break;
               case 'xls':
                     image = 'images/msExcel.png';
                     break;
               case 'jpg':
                     image = 'images/jpgIcon.png';
                     break;
               default:
                     image = 'images/docImg.png';                        
                        }                           
           return image;
       };
	   
	//*************DRIVER BUTTON FUNCTIONS********************
   	   $scope.viewDriver = function(post, index){
		   console.log(post.name + " is Viewed :: Driver");
           angular.forEach($scope.driversPendingData, function(item) {
		  		    item.isClicked=false;
		        });
		   if(!post.isClicked){
			   post.isClicked = true;
			   var dataObj = {
					   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
					   driverId:post.driverId					   
				};	
			   $http.post('services/approval/driverDetail/',dataObj).
				    success(function(data, status, headers, config) {
                        $scope.driverDetail = data;
                        angular.forEach($scope.driverDetail[0].documents, function(item) {
                            if(item.location!=null){
                                extension = item.location.split('.').pop();  
                                item.imgSrc = findImgSrc(extension);
                            }
                            else item.imgSrc = 'images/docImg.png';
		  	            });

					    }).
					    error(function(data, status, headers, config) {
					      // log error
					    });
			}          
       };	   
	  
	   $scope.approveDriver = function(post, index){
		   console.log(post.name + " is approved :: Driver");
		   post.isClicked = false;	
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
				   driverId:post.driverId					   
			};	
		   $http.post('services/approval/approvedriver/',data).
				    success(function(data, status, headers, config) {
				    	if(data=="success"){				    		             
                            $scope.showalertMessage("Driver has been approved", "");
				    	}
				    	else{
				    		$scope.showalertMessage("Please first approve the vendor","");
				    	}
                        post.isClicked = false;
                        $scope.driversPendingData.splice(index, 1);   
               
                    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	   $scope.rejectDriver = function(post, index){
		   console.log(post.name + " is rejected :: Driver");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
				   driverId:post.driverId					   
			};	
           $confirm({text: 'Are you sure you want to reject this driver?', title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/approval/removedriver/',data).
				    success(function(data, status, headers, config) {
                        post.isClicked = false;
                        $scope.driversPendingData.splice(index, 1);             
                        $scope.showalertMessage("Driver has been rejected", "");
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
           });
	   };
	   
	   $scope.closeDriver = function(post){
		   console.log(post.name + " is Close :: Vehicle");
		   post.isClicked = false;
	   };
	   
	   $scope.removeDriver = function(post, index){
		   console.log(post.name + " is removed :: Driver");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
				   driverId:post.driverId					   
			};
           $confirm({text: 'Are you sure you want to remove this registered driver?', title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/approval/rejectdriver/',data).
				    success(function(data, status, headers, config) {
//				      $scope.driversPendingData = data; 
//                        post.isClicked = false;
                        $scope.driversRegisterData.splice(index, 1);             
                        $scope.showalertMessage("Driver has been removed", "");
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });});
	   };
	   
	   $scope.addagainDriver = function(post, index){
		   console.log(post.name + " has been added :: Driver");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
				   driverId:post.driverId					   
			};
           $confirm({text: 'Are you sure you want to add this driver agaiin?', title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/approval/addagaindriver/',data).
				    success(function(data, status, headers, config) {				       
                        $scope.driversUnregisterData.splice(index, 1); 
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
           });
	   };
	   
	//*************VEHICLE BUTTON FUNCTIONS********************
	   $scope.viewVehicle = function(post){
           angular.forEach($scope.vehiclesPendingData, function(item) {
		  		    	  item.isClicked=false;});
		   if(!post.isClicked){
			   post.isClicked = true;
			   $('.vehicle'+post.vehicleId).show('slow');
			   var data = {
					   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
					   vehicleId:post.vehicleId					   
				};
			   $http.post('services/approval/vehicleDetail/',data).
				    success(function(data, status, headers, config) {
                        $scope.vehicleDetail = data;
                        angular.forEach($scope.vehicleDetail[0].documents, function(item) {
                            if(item.location!=null){
                                extension = item.location.split('.').pop();  
                                item.imgSrc = findImgSrc(extension);
                            }
                            else item.imgSrc = 'images/docImg.png';
		  	            });
					 }).
					 error(function(data, status, headers, config) {
					      // log error
					 });		   
			}
		   else post.isClicked = false;	

	   };
	   
	   $scope.approveVehicle = function(post, index){
		   console.log(post.name + " is approved :: Vehicle");
		   post.isClicked = false;
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
				   vehicleId:post.vehicleId					   
			};
		   $http.post('services/approval/approvevehicle/',data).
				    success(function(data, status, headers, config) {
				    	if(data=="success"){
				    		$scope.showalertMessage("Approved", "");
				    	}
				    	else{
				    		$scope.showalertMessage("Please first approve the vendor", "");
				    	}                        post.isClicked = false;
                        $scope.vehiclesPendingData.splice(index, 1);              
                       $scope.showalertMessage("Vehicle has been approved", "");
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   }; 
       
	   $scope.rejectVehicle = function(post, index){
		   console.log(post.name + " is rejected :: Vehicle");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
				   vehicleId:post.vehicleId					   
			};
           $confirm({text: 'Are you sure you want to reject this vehicle?', title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/approval/removevehicle/',data).
				    success(function(data, status, headers, config) {
//                    $scope.showalertMessage("Vehicle is Rejected", "");
//				      $scope.driversPendingData = data; 
                      $scope.vehiclesPendingData.splice(index, 1);                            
                      $scope.showalertMessage("Vehicle has been rejected", "");
				    }).
				    error(function(data, status, headers, config) {
				      // log error});
				    });
	   });
       }
	   
	   $scope.closeVehicle = function(post){
		   console.log(post.name + " is Close :: Vehicle");
		   post.isClicked = false;
	   };
	   
	   $scope.removeVehicle = function(post, index){
		   console.log(post.name + " is removed :: Vehicle");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}},
				   vehicleId:post.vehicleId					   
			};
           $confirm({text: 'Are you sure you want to remove this vehicle?', title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/approval/rejectvehicle/',data).
				    success(function(data, status, headers, config) {
//				      $scope.driversPendingData = data; 
                    $scope.vehiclesRegisterData.splice(index, 1);               
                    $scope.showalertMessage("Vehicle has been removed", "");
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
           });
		   //Call $http.post
		   //on success hide the row
	   };
	   
	   $scope.addagainVehicle = function(post, index){
		   console.log(post.name + " has been added :: Vehicle");
		   var data = {
				   efmFmVendorMaster:{        		   eFmFmClientBranchPO:{branchId:branchId}},
				   vehicleId:post.vehicleId					   
			};
            $confirm({text: 'Are you sure you want to add this vehicle again?', title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/approval/addagainvehicle/',data).
				    success(function(data, status, headers, config) {
//				      $scope.driversPendingData = data; 
                    $scope.vehiclesUnregisterData.splice(index, 1);
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
            });
	   };
	   
	//*************VENDOR BUTTON FUNCTIONS********************
	   $scope.viewVendor = function(post){
           angular.forEach($scope.vendorPendingData, function(item) {
		  		    	  item.isClicked=false;});
		   if(!post.isClicked){
			   post.isClicked = true;
			   $('.vendor'+post.vendorId).show('slow');	
			   var data = {
					           		   eFmFmClientBranchPO:{branchId:branchId},
					   vendorId:post.vendorId					   
				};
			   $http.post('services/approval/vendorDetail/',data).
					    success(function(data, status, headers, config) {
                            $scope.vendorDetail = data;
                   
					    }).
					    error(function(data, status, headers, config) {
					      // log error
					    });
		   }
		   else post.isClicked = false;	
	   };
	   
	   $scope.approveVendor = function(post, index){
		   console.log(post.name + " is approved :: Vendor");
		   $('.vendor'+post.vendorId).slideUp('slow');
			   var data = {
					   vendorId:post.vendorId					   
				};
			   $http.post('services/approval/approvevendor/',data).
					    success(function(data, status, headers, config) {
//					      $scope.driversPendingData = data; 
				    		$scope.showalertMessage("Vendor has been Approved", "");

                        post.isClicked = false;
                        $scope.vendorPendingData.splice(index, 1); 
					    }).
					    error(function(data, status, headers, config) {
					      // log error
					    });
		   
	   };
	   
	   $scope.rejectVendor = function(post, index){
		   console.log(post.name + " is rejected :: Vendor");
		   var data = {
				   vendorId:post.vendorId					   
			};
           $confirm({text: 'Are you sure you want to reject this vendor?', title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/approval/removevendor/',data).
				    success(function(data, status, headers, config) {
//				      $scope.driversPendingData = data; 
			    		$scope.showalertMessage("Vendor has been Removed", "");

                    post.isClicked = false;
                    $scope.vendorPendingData.splice(index, 1); 
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
           });
		   //Call $http.post
		   //on success hide the row
	   };
	   
	   $scope.closeVendor = function(post){
		   console.log(post.name + " is Close :: Vehicle");
		   post.isClicked = false;
	   };
	   
	   $scope.removeVendor = function(post, index){
		   console.log(post.name + " is removed :: Vendor");
		   var data = {
				   vendorId:post.vendorId					   
			};           
           $confirm({text: 'Are you sure you want to remove this vendor?', title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
		   $http.post('services/approval/rejectvendor/',data).
				    success(function(data, status, headers, config) {
//				      $scope.driversPendingData = data; 
			    		$scope.showalertMessage("Vendor has been Rejected", "");

                    $scope.vendorRegisterData.splice(index, 1); 
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
           });
	   };
	   
	   $scope.addagainVendor = function(post, index){
		   console.log(post.name + " has been added :: Vendor");
		   var data = {
				   vendorId:post.vendorId					   
			};
                     
           $confirm({text: 'Are you sure you want to remove this vendor?', title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {
		   $http.post('services/approval/addagainvendor/',data).
				    success(function(data, status, headers, config) {
//				      $scope.driversPendingData = data; 
                    $scope.vendorUnregisterData.splice(index, 1); 
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
           });
	   };
	   
	   
	// ******DRIVER APPROVAL FUNCTION - called when the user click any tab*************** 
	   $scope.getPendingDriver = function(){           
           $scope.currentDriverTab = 'Pending';
           $scope.progressbar.start();
		   console.log("You have clicked Pending Tab");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}}
			};
		   $http.post('services/approval/unapproveddriver/',data).
				    success(function(data, status, headers, config) {
				      $scope.driversPendingData = data; 
				      angular.forEach($scope.driversPendingData, function(item) {
		  		    	  item.isClicked=false;
		  	         });
                    $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	   $scope.getRegisteredDriver = function(){          
           $scope.currentDriverTab = 'Registered';
           $scope.progressbar.start();
		   console.log("You have clicked Driver Registered Tab");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}}
			};
		   $http.post('services/approval/approveddriver/',data).
				    success(function(data, status, headers, config) {
				      $scope.driversRegisterData = data;  
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	   $scope.getUnRegisteredDriver = function(){         
           $scope.currentDriverTab = 'UnRegistered';
           $scope.progressbar.start();
		   console.log("You have clicked Driver Un-Registered Tab");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}}
			};
		   $http.post('services/approval/inactivedriver/',data).
				    success(function(data, status, headers, config) {
				      $scope.driversUnregisterData = data;   
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	// ******VEHICLE APPROVAL FUNCTION - called when the user click any tab*************** 
	   
	   $scope.getPendingVehicle = function(){           
           $scope.currentVehicleTab = 'Pending';
           $scope.progressbar.start();
		   console.log("You have clicked Vehicle Pending Tab");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}}
			};
		   $http.post('services/approval/unapprovedvehicle/',data).
				    success(function(data, status, headers, config) {
				      $scope.vehiclesPendingData = data;   
				      angular.forEach($scope.vehiclesPendingData, function(item) {
		  		    	  item.isClicked=false;});
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	   $scope.getRegisteredVehicle = function(){          
           $scope.currentVehicleTab = 'Registered';
           $scope.progressbar.start();
		   console.log("You have clicked Vehicle Registered Tab");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}}
			};
		   $http.post('services/approval/approvedvehicle/',data).
				    success(function(data, status, headers, config) {
				      $scope.vehiclesRegisterData = data;   
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	   $scope.getUnRegisteredVehicle = function(){        
           $scope.currentVehicleTab = 'UnRegistered';
           $scope.progressbar.start();
		   console.log("You have clicked Vehicle Un-Registered Tab");
		   var data = {
				   efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}}
			};
		   $http.post('services/approval/inactivevehicle/',data).
				    success(function(data, status, headers, config) {
				      $scope.vehiclesUnregisterData = data;   
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	// ******VENDOR APPROVAL FUNCTION - called when the user click any tab*************** 	   
	   $scope.getPendingVendor = function(){
           $scope.currentVendorTab = 'Pending';
           $scope.progressbar.start();
		   console.log("You have clicked Vendor Pending Tab");
		   var data = {
				           		   eFmFmClientBranchPO:{branchId:branchId}
			};
		   $http.post('services/approval/unapprovedvendor/',data).
				    success(function(data, status, headers, config) {
				      $scope.vendorPendingData = data;  
				      angular.forEach($scope.vendorPendingData, function(item) {
		  		    	  item.isClicked=false;});
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	   $scope.getRegisteredVendor = function(){
           $scope.currentVendorTab = 'Registered';
           $scope.progressbar.start();
		   console.log("You have clicked Vendor Registered Tab");
		   var data = {
               eFmFmClientBranchPO:{branchId:branchId}
			};
		   $http.post('services/approval/approvedvendor/',data).
				    success(function(data, status, headers, config) {
				      $scope.vendorRegisterData = data;  
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
	   
	   $scope.getUnRegisteredVendor = function(){
           $scope.currentVendorTab = 'UnRegistered';
           $scope.progressbar.start();
		   console.log("You have clicked Vendor Un-Registered Tab");
		   var data = {
				           		   eFmFmClientBranchPO:{branchId:branchId}
			};
		   $http.post('services/approval/inactivevendor/',data).
				    success(function(data, status, headers, config) {
				      $scope.vendorUnregisterData = data; 
                      $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
	   };
       
       $scope.refreshDriverApproval = function(){
           console.log('Refreshed Driver');
           if($scope.currentDriverTab == 'Pending'){$scope.getPendingDriver();}
           if($scope.currentDriverTab == 'Registered'){$scope.getRegisteredDriver();}
           if($scope.currentDriverTab == 'UnRegistered'){$scope.getUnRegisteredDriver();}
           
       };
       
       $scope.refreshVehicleApproval = function(){
           console.log('Refreshed Vehicle');
           if($scope.currentVehicleTab == 'Pending'){$scope.getPendingVehicle();}
           if($scope.currentVehicleTab == 'Registered'){$scope.getRegisteredVehicle();}
           if($scope.currentVehicleTab == 'UnRegistered'){$scope.getUnRegisteredVehicle();}
       };
       
       $scope.refreshVendorApproval = function(){
           console.log('Refreshed Vendor');
           if($scope.currentVendorTab == 'Pending'){$scope.getPendingVendor();}
           if($scope.currentVendorTab == 'Registered'){$scope.getRegisteredVendor();}
           if($scope.currentVendorTab == 'UnRegistered'){$scope.getUnRegisteredVendor();}
       };
    };
    
    angular.module('efmfmApp').controller('approvalCtrl', approvalCtrl);
}());