(function(){
   var myProfileCtrl = function($scope, $modal, $http, $timeout, $confirm){
     $scope.isDisable = true;
     $('.admin_home').addClass('active');
     $('.myProfile_admin').addClass('active');  
     $scope.$on('$viewContentLoaded', function() {
         $scope.getProfileDetail();
	});
     var data = {
			   branchId:branchId,
		};
	   $http.post('services/user/branchdetail/',data).
			    success(function(data, status, headers, config) {
			    	 $scope.escortNeeded= data.escortRequired;
			    	 $scope.approvalNeeded = data.managerApproval;
				}).
			    error(function(data, status, headers, config) {
			      // log error
			    });  
     
     $scope.user = {};
     $scope.user_Type;
     $scope.isCollapsed = false;
     $scope.isEdit = false;
     $scope.isProfileEdit = false;
     $scope.changePasswordClicked = false;
     $scope.isChangeLocation = false;
     $scope.mapClick= false;
     $scope.currLocation = "Irving, Texas";
     $scope.users = [{'value':1, 'text':'ADMIN'},
	    	 		 {'value':2, 'text':'SUPERVISOR'},
	    	 		 {'value':3, 'text':'MANAGER'},
	    	 		{'value':4, 'text':'WEBUSER'}]; 
     $scope.tripTypes = [{'value':'PICKUP', 'text':'PICKUP'},
                         {'value':'DROP', 'text':'DROP'}];
     $scope.trip = {};
     $scope.trip.tripType = $scope.tripTypes[0];
     
    $scope.baseLocation = 'Office'; 
    $scope.newAddress_baseLocation;
    $scope.regExName = /^[A-Za-z]+$/;
    $scope.rolelist;
    var originalShitTime;       
    $scope.addNewShiftIsClicked = false;
    $scope.hstep = 1;
    $scope.mstep = 5;
    $scope.ismeridian = false;
    $scope.requestsData = [];       
//    $scope.searchIsEmpty = false;
//    $scope.shift = {};
//    $scope.shift.timePicker = new Date();
       
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
       
   //Convert to hh:mm
   var convertToTime = function(newdate){
       d = new Date(newdate);
//       alert(d);
       hr = d.getHours();
       min = d.getMinutes();
       if(hr<10){hr = '0'+hr;} 
       if(min<10){min = '0'+min;}           
       console.log("TIME :: " + hr+":"+min);
       return hr+":"+min;
   };  
       
   $scope.initializeTime = function(){
        var d = new Date();
        d.setHours( 00 );
        d.setMinutes( 0 );
        return d;           
   };
  
   $scope.initializeTime2 = function(hr, min){
        var d = new Date();
        d.setHours( hr );
        d.setMinutes( min );
        return d;           
   };
       
    $scope.dateOptions = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            };
    $scope.openDobCal = function($event){
        $event.preventDefault();
        $event.stopPropagation();
        $timeout( function(){
           $scope.datePicker = {'dobDate' : true};  
        }, 50);
    };
       
    $scope.getProfileDetail = function(){   
        $scope.progressbar.start();
        var data = {
			 eFmFmClientBranchPO:{branchId:branchId},
  			   userId:profileId
  		};
  	   $http.post('services/user/loginuser/',data).
				    success(function(data, status, headers, config) {
				    	console.log(data);
				        $scope.myProfileData = data; 
                        $scope.user = {userName:$scope.myProfileData[0].userName,
                                      firstName:$scope.myProfileData[0].firstName,
                                      lastName:$scope.myProfileData[0].lastName,
                                      country:$scope.myProfileData[0].country,
                                      dob:new Date($scope.myProfileData[0].birthDate),
                                      occupation:$scope.myProfileData[0].designation,
                                      email:$scope.myProfileData[0].emailId,
                                      interest:$scope.myProfileData[0].interest,
                                      website:$scope.myProfileData[0].emailId,
                                      mobileNumber:$scope.myProfileData[0].number,
                                      about:$scope.myProfileData[0].address} ;
                        $scope.progressbar.complete();
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });
//    
    }; 
       
    //All Regular master request details     
    $scope.getRequests = function(){        
           $scope.progressbar.start();
//           if($scope.requestsData.length == 0){           
               tripTypeSelected = $scope.trip.tripType.value; /// Trip Type = 'PICKUP' is a default value. Need to pass that vvalue in the service data.
  //             alert($scope.trip.tripType.value);
               var data = {
                       efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                       tripType:$scope.trip.tripType.value
                };
               $http.post('services/user/allrequestdetailtype/',data).
                        success(function(data, status, headers, config) {
 //                  alert(JSON.stringify(data));
                          $scope.requestsData = data; 
                          $scope.progressbar.complete();
                        }).
                        error(function(data, status, headers, config) {
                          // log error
                        });
//           }
//        else{
//            var data = {
//                       efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}
//                };
//               $http.post('services/user/allrequestdetail/',data).
//                        success(function(data, status, headers, config) {
//                   alert(JSON.stringify(data));
//                          $scope.requestsData_Temp = data; 
//                          $scope.progressbar.complete();
//                          angular.forEach($scope.requestsData_Temp, function(item){
//                              $scope.requestsData.push({"shiftTime": item.shiftTime,
//                                                        "pickupTime":item.pickupTime,
//                                                        "address": item.address,
//                                                        "requestType": item.requestType,
//                                                        "areaName": item.areaName,
//                                                        "tripId": item.tripId,
//                                                        "employeeId": item.employeeId,
//                                                        "userId": item.userId,
//                                                        "status": item.status,
//                                                        "routeName": item.routeName});
//                          });
//                        }).
//                        error(function(data, status, headers, config) {
//                          // log error
//                        });
//        }
            
//		   console.log("You have clicked Requests Detail Tab");
        
    };
    
    $scope.enableRequest = function(device){
        $confirm({text: "Are you sure you want to Enable this request?", title: 'Enable Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {
    	           var data = {
    					   efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
    					   tripId:device.tripId,
    					   status:'Y',
    					   readFlg:'Y'
    				};
    			   $http.post('services/user/updaterequestdetail/',data).
    					    success(function(data, status, headers, config) {
                                device.status = 'Y',
    					    	$scope.showalertMessage("Request Enable Successfully", '');
    					    }).
    					    error(function(data, status, headers, config) {
    					      // log error
    					    });  
            })   
    };
    	       
   $scope.disableRequest = function(device){
        $confirm({text: "Are you sure you want to Disable this request?", title: 'Disable Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {
       var data = {
               efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
               tripId:device.tripId,
               status:'N',
               readFlg:'N'
        };
       $http.post('services/user/updaterequestdetail/',data).
                success(function(data, status, headers, config) {
                    device.status = 'N'
                    $scope.showalertMessage("Request Disable Successfully", "");
                }).
                error(function(data, status, headers, config) {
                  // log error
                }); 
        })
   };

   $scope.getAllShifts = function(){
       $scope.addNewShiftIsClicked = false;
 	   var data = {
    		 efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}
          };	
       $http.post('services/trip/shiftime/',data).
            success(function(data, status, headers, config) {
//           	alert(JSON.stringify(data));
              $scope.shiftTimeData = data.shift;  
              angular.forEach($scope.shiftTimeData, function(item){
                        item.editShiftTimeIsClicked = false;
                        item.timePicker = $scope.initializeTime();
              });
            }).
            error(function(data, status, headers, config) {
              // log error
            });         
   
       
       
/*       var data = {
               efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}}
        };
       $http.post('services/approval/unapproveddriver/',data).
            success(function(data, status, headers, config) {
                    $scope.shiftTimeData = data;
                    alert(JSON.stringify(data));
                    angular.forEach($scope.shiftTimeData, function(item){
                        item.editShiftTimeIsClicked = false;
                        item.time = $scope.initializeTime();
                    });
                }).
                error(function(data, status, headers, config) {
                  // log error
                });	*/
       
   };
    
   $scope.getAllRouteNames = function(){
	   var data = {
	    		 eFmFmClientBranchPO:{branchId:branchId},
	          };	
       $http.post('services/trip/allzone/',data).
            success(function(data, status, headers, config) {
 //               alert(JSON.stringify(data)); 
                $scope.routeNameData = data.zones;
                angular.forEach($scope.routeNameData, function(item){
                    item.editRouteNameIsClicked = false;
                });
            }).
            error(function(data, status, headers, config) {
              // log error
            });     

	   
	   
      /* var data = {
               efmFmVendorMaster:{eFmFmClientBranchPO:{branchId:branchId}}
        };
       $http.post('services/approval/unapproveddriver/',data).
            success(function(data, status, headers, config) {
                    $scope.routeNameData = data;
                    alert(JSON.stringify(data));
                    angular.forEach($scope.routeNameData, function(item){
                        item.editRouteNameIsClicked = false;
                    });
           
                    alert(JSON.stringify($scope.routeNameData));
                }).
                error(function(data, status, headers, config) {
                  // log error
                });	*/
   };
       
    $scope.openPasswordDiv = function(){
        $('.passwordDiv').show('slow');
        $scope.changePasswordClicked = true;       
    };
    
    $scope.savePassword = function(userPassword){ 
    	if(userPassword.newPass1!=userPassword.newPass2){
    		$scope.showalertMessage("New password and confirm password does't match", "");
    		return false;
    	}
    	
        var data = {
				 eFmFmClientBranchPO:{branchId:branchId},
   			   userId:profileId,
   			newPassword:userPassword.newPass1,
   			password:userPassword.oldPass
   		};
   	   $http.post('services/user/changeuserpassword/',data).
 				    success(function(data, status, headers, config) {
 				    	if(data.status=="wrong"){
				    		$scope.showalertMessage("Your old password does not match", "");
				    	} 
 				    	else{
 				    		$scope.showalertMessage("Password change successfully", "");
				    		$('.passwordDiv').hide('slow');
				            $scope.changePasswordClicked = false;

 				    	}
 				    	}).
 				    error(function(data, status, headers, config) {
 				      // log error
 				    });        
    };
       
    $scope.cancelPassword = function(){
        $('.passwordDiv').hide('slow');
        $scope.changePasswordClicked = false;
    };
       
    $scope.editMyProfile = function(){
        $scope.isProfileEdit = true;
    };
       
    $scope.setUserType = function(user){
         if(!user){ 
             $scope.user_Type = "none";
          }
         else $scope.user_Type = user.text;
     };    
    
    $scope.saveProfile = function(user){      
//        $scope.submitted = true;
        $timeout( function(){
               $('.hintModal').hide('slow');  
             }, 50);
        
    	 var data = {
				 eFmFmClientBranchPO:{branchId:branchId},
    			   userId:profileId,
    			   userName:user.userName,
    			   firstName:user.firstName,
    			   lastName:user.lastName,
    			   mobileNumber:user.mobileNumber,
    			   emailId:user.email,
    			   birthDate:user.dob,
    			   employeeDomain:user.interest,
    			   employeeDesignation:user.occupation  			   
    		};
    	   $http.post('services/user/updateprofile/',data).
  				    success(function(data, status, headers, config) {
  				    	$scope.showalertMessage("Updated Successfull", "");
  				    	 $scope.isProfileEdit = false;
  				    }).
  				    error(function(data, status, headers, config) {
  				      // log error
  				    });
    };
    
   /* Start fuction
    * Get all the users of the particular client
    */
   $scope.getAdminSettings = function(){
       $scope.progressbar.start();
        var data = {
			 eFmFmClientBranchPO:{branchId:branchId},
		};
	   $http.post('services/user/allusers/',data).
			    success(function(data, status, headers, config) {
				    $scope.administratorData = data.users; 
                    console.log(data);
                    $scope.progressbar.complete();
				}).
			    error(function(data, status, headers, config) {
			      // log error
			    });
    
     };
     /* end fuction
      *
      */
       
    $scope.cancelProfile = function(user){
        $scope.isProfileEdit = false;
        $scope.user = {userName:$scope.myProfileData[0].userName,
                                      firstName:$scope.myProfileData[0].firstName,
                                      lastName:$scope.myProfileData[0].lastName,
                                      country:$scope.myProfileData[0].country,
                                      dob:$scope.myProfileData[0].birthDate,
                                      occupation:$scope.myProfileData[0].designation,
                                      email:$scope.myProfileData[0].emailId,
                                      interest:$scope.myProfileData[0].interest,
                                      website:$scope.myProfileData[0].emailId,
                                      mobileNumber:$scope.myProfileData[0].mobileNumber,
                                      about:$scope.myProfileData[0].address} ;
    };
       
    $scope.setEscortNeeded = function(escortNeeded){
        $scope.escortNeeded= escortNeeded;
        console.log("Form Escort Needed, you Chose :: " +$scope.escortNeeded);
    };
       
    $scope.setapprovalNeeded = function(approvalNeeded){
        $scope.approvalNeeded = approvalNeeded;
    };
       
    $scope.setLocation = function(baseLocation){
        $scope.baseLocation = baseLocation;
        console.log("Form Base Location, you Chose :: " +$scope.baseLocation);
        if($scope.baseLocation == 'Office'){
            $scope.isChangeLocation = false;
        }
        else $scope.isChangeLocation = true;
    };
       
    $scope.saveButton = function(){
        /*if($scope.baseLocation == 'Change Location'){
            $scope.newAddress_baseLocation = $scope.currLocation;
            $scope.newCords_baseLocation = $scope.currCords;
        }
        else if ($scope.baseLocation == 'Office'){
            $scope.newAddress_baseLocation = 'NEWT';            
            $scope.newCords_baseLocation = '';
        }*/
//        alert("Save Button is Clicked");
 //       alert("Escort Needed :: " + $scope.escortNeeded +" | Base Location is :: "+$scope.newAddress_baseLocation+ "Cords :: " + $scope.newCords_baseLocation + "Approval Needed : " + $scope.approvalNeeded );
//        alert("hit"+$scope.escortNeeded);
        var data = {
    			   branchId:branchId,
    			   escortRequired:$scope.escortNeeded,
    			   mangerApprovalRequired:$scope.approvalNeeded,
    			   
    		};
    	   $http.post('services/user/updatebranch/',data).
    			    success(function(data, status, headers, config) {
//    				alert(data);
    				if(data.status=="success"){
			    		$scope.showalertMessage("Saved successfully", "");
			    	} 
    				}).
    			    error(function(data, status, headers, config) {
    			      // log error
    			    });
        
    };
    
    $scope.saveLocation = function(newAddress){
        //baseLocation has office value.. so when you want to change the location pass 'newAddress' parameter to the server
//       alert("Escort Needed :: " + $scope.escortNeeded +
 //             " | Base Location is :: "+$scope.baseLocation);
        console.log("Value fron Company Address Input Box :: " + newAddress);
        $scope.isEdit = false;
        $scope.isChangeLocation = false;
    };
       
    
       
    $scope.editLocation = function(){
        $scope.isEdit = true;        
    };
    
    $scope.openMap = function(size){
        if(!$scope.mapClick){
            $scope.mapClick = true;
            $scope.isEdit = true;
        }
        else $scope.mapClick = false;
        
         var modalInstance = $modal.open({
           templateUrl: 'partials/modals/mapMyProfile.jsp',
           controller: 'mapMyProfileCtrl',
           size:size,
           resolve: {}
           }); 		   
		   modalInstance.result.then(function(result){
               $scope.currLocation = result.address;
               $scope.currCords = result.cords;
         });    
    };
    
    $scope.assignRole = function(user){
        if($scope.user_Type == undefined){
        	$scope.showalertMessage("Please change the role", "");
        	return false;
        }        
        var data = {
        		eFmFmClientBranchPO:{branchId:branchId},
        		efmFmUserMaster:{userId:user.userId},
        		efmFmRoleMaster:{role:$scope.user_Type}
 		};
 	   $http.post('services/user/changeuserrole/',data).
 			    success(function(data, status, headers, config) {
           
 			    	/*if(JSON.stringify(data).length=2){
 			    		alert("Employee does not have any project manager");
 			    		return false;
 			    	}*/
                    if(Object.keys(data).length == 0){
                        $scope.showalertMessage('This User does not have any Manager.', '');
                    }
                    else if(data.status == 'exist'){
                        $confirm({text: data.name + " is an existing Manager. Do you really want to change the role of this user to Manger", title: 'Delete Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {  
//                        if(confirm( data.name + " is an existing Manager. Do you really want to change the role of this user to Manger") == true){
                           var data = {
                           		eFmFmClientBranchPO:{branchId:branchId},
                           		efmFmUserMaster:{userId:user.userId},
                           		efmFmRoleMaster:{role:$scope.user_Type}
                    		};                          
                     $http.post('services/user/changenormaluserrole/',data).
           			    success(function(data, status, headers, config) {
//           				alert(data);
           				if(data.status=="success"){
           					$scope.showalertMessage("Manager Changed Successfully", "");
       			    	} 
           				}).
           			    error(function(data, status, headers, config) {
           			      // log error
           			    }); 
                        });
                    }
                    else if(data.status=="success"){
                        $scope.showalertMessage("Role Changed Successfully", "");
                        user.userRole = $scope.user_Type;
                    }
 				}).
 			    error(function(data, status, headers, config) {
 			      // log error
 			    });
        
        
        
    };
       
    $scope.addUser = function(size){
        var modalInstance = $modal.open({
           templateUrl: 'partials/modals/addUserModal.jsp',
           controller: 'addUserCtrl',
           size:size,
           resolve: {}
           }); 		   
		   modalInstance.result.then(function(result){
               var fullName = result.userFName+" "+result.userMName+" "+result.userLName;
               $scope.administratorData.push({'fullName':fullName, 
                                              'userName': result.userMName, 
                                              'mobileNumber': result.mobileNumber,
                                              'emailId':result.email, 
                                              'userRole': result.selectedUserRole.value});
           });  
    };
    
    $scope.setTripType = function(){
        $scope.getRequests();
    };
       
    $scope.searchRequests = function(searchText){
        if(searchText){
//            $scope.searchIsEmpty = false;
        	/*if(searchText.length==0 || searchText==""){
                $scope.showalertMessage("Please enter employeeid", '');                                         
        	}*/
             var dataObj = {
                 branchId:branchId,
                employeeId:searchText
              };	
             $http.post('services/trip/emplyeerequestsearch/',dataObj).
  			     success(function(data, status, headers, config) {
  			    	if(data.requests.length==0){
                        $scope.showalertMessage("This Request does not exist. Please check your Request Id and try again.", '');                                         
                    }
                    else{
  			    	$scope.requestsData =data.requests;
  			    	angular.forEach($scope.posts, function(item) {
                        item.checkBoxFlag=false; });
                    $scope.progressbar.complete();
//                    $scope.isLoaded = true;
                    }
  			    }).
  			    error(function(data, status, headers, config) {
  			      // log error
  			    }); 
        }
        else{
//            $scope.searchIsEmpty = true;
        }
    };
       
    $scope.editShiftTime = function(shift, index){
        var splitShiftTime = $scope.shiftTimeData[index].shiftTime.split(':');
        shift.timePicker = $scope.initializeTime2(splitShiftTime[0], splitShiftTime[1]);
//        alert(JSON.stringify(shift));
        shift.editShiftTimeIsClicked = true;
			
    };    
    
       
    $scope.updateShiftTime = function(shift, index){  
        var shiftAlreadyExist = _.findIndex($scope.shiftTimeData, { shiftTime: convertToTime(shift.timePicker)});   
//        alert(shiftAlreadyExist);
        if(shiftAlreadyExist<0){
//            alert(shift.timePicker);
            $scope.shiftTimeData[index].shiftTime = convertToTime(shift.timePicker);
            console.log(shift);
            shift.editShiftTimeIsClicked = false;
            $scope.showalertMessage('Shift Time - ' +$scope.shiftTimeData[index].shiftTim+ ' has been updated successfuly.', '');
        }
        else{
            $scope.showalertMessage('Shift Time - ' +convertToTime(shift.timePicker)+ ' for Trip Type - ' +shift.tripType+ ' already exist.', '');
        }
			
    };
    
    $scope.canceShiftTime = function(shift, index){ 
        shift.editShiftTimeIsClicked = false;        
        shift.shiftTime = $scope.shiftTimeData[index].shiftTime;
    }; 
       
    $scope.addShiftTime = function(){
        var modalInstance = $modal.open({
           templateUrl: 'partials/modals/addNewShift.jsp',
           controller: 'addNewShiftTime',
           backdrop:'static',
           resolve: {
               allExistingShifts : function(){return $scope.shiftTimeData;}
           }
           }); 		   
		   modalInstance.result.then(function(result){ 
               $scope.shiftTimeData.unshift({shiftTime:convertToTime(result.createNewAdHocTime), tripType:result.tripType});
           }); 
        
    }; 
       
    $scope.editRouteName = function(route, index){
        console.log(route);
        route.editRouteNameIsClicked = true;
        route.routeNameEdit = route.routeName;
//        route.routeName = $scope.routeNameData[index].routeName;
			
    };      
       
    $scope.updateRouteName = function(route, index){  
//        alert(route.routeId);
//        alert(route.routeName);
//        alert(route.routeNameEdit);
        routeNameAlreadyExist = _.findIndex($scope.routeNameData, { routeName: route.routeNameEdit});  
//        alert(routeNameAlreadyExist);
//            console.log(route);
        if(routeNameAlreadyExist<0){
            console.log(route);
            route.editRouteNameIsClicked = false;
            $scope.routeNameData[index].routeName = route.routeNameEdit;
            $scope.showalertMessage('Route - ' +$scope.routeNameData[index].routeName+' has been updated successfuly.', '');
        }
        else{
            $scope.showalertMessage('Route - ' +route.routeName+' already exist.', '');
        }
//        alert(route.driverId);
			
    };
    
    $scope.canceRouteName = function(route, index){        
        route.editRouteNameIsClicked = false;
        route.routeName = $scope.routeNameData[index].routeName;
    };

    $scope.addRouteName = function(){
        $scope.addNewRouteIsClicked = true;
        $('.addNewRouteButton').hide('fast');
        $('.addNewRouteDiv').show('slow');
        $('.routeFilter').addClass('marginTop15');
        
    };
    
    $scope.saveNewRoute = function(newRoute){        
        $scope.addNewRouteIsClicked = false;
        console.log(newRoute);
   	 var data = {
    		 eFmFmClientBranchPO:{branchId:branchId},
    		 routeName:newRoute.routeName,
    		 
          };	
       $http.post('services/trip/addzone/',data).
            success(function(data, status, headers, config) {  
                console.log(data);
                if(data.status == 'success'){                    
                    $('.addNewRouteDiv').hide('fast', function(){$('.addNewRouteButton').show('slow');});
                    $scope.showalertMessage('Route - ' +newRoute.routeName+' has been created successfuly.', '');
                    $scope.routeNameData.unshift({routeId:1001, routeName:newRoute.routeName});
                }
                else{
                    $scope.showalertMessage('Route - ' +newRoute.routeName+' already exist.', '');
                }
           
            }).
            error(function(data, status, headers, config) {
              // log error
            });     
    };
       
    $scope.cancelNewRoute = function(){        
        $scope.addNewRouteIsClicked = false;          
        $('.addNewRouteDiv').hide('fast', function(){
                                                $('.addNewRouteButton').show('slow');                                                
                                                $('.routeFilter').removeClass('marginTop15');
                                            });
    };      
    
//    $scope.$watch("search.text", function(query){
//        if($scope.search.text == ""){
//            $scope.searchIsEmpty = false;
//        }
//    });
       
};    
    
var mapMyProfileCtrl = function($scope, $modalInstance, $state, $http){ 
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
    
    $scope.user = {address:'', cords:''}; 
    $scope.save = function(user){
//        alert(user.address + ": " + user.cords);
    	$modalInstance.close(user);	
    };    	
        
    //CLOSE BUTTON FUNCTION
    $scope.cancel = function () {
       $modalInstance.dismiss('cancel');
    };
};
    
var addUserCtrl = function($scope, $modalInstance, $state, $http, $timeout){  
   $scope.alertMessage;
   $scope.alertHint; 
   $scope.IntegerNumber = /^\d+$/;
   $scope.regExName = /^[A-Za-z]+$/;
   $scope.NoSpecialCharacters = /^[a-zA-Z0-9]*$/;
   $scope.onMap= false;
   $scope.gender;
   $scope.phyChall;
   $scope.userRole;
   $scope.onUserInfo = true;
   $scope.genders = [{'value':'Male', 'text':'Male'},
	    	 			 {'value':'Female', 'text':'Female'}];
   $scope.physicalChalls = [{'value':'Yes', 'text':'Yes'},
	    	 			 {'value':'No', 'text':'No'}];
   $scope.userRoles = [{'value':'ADMIN', 'text':'ADMIN'},
	    	 			 {'value':'SUPERVISOR', 'text':'SUPERVISOR'},
	    	 			 {'value':'MANAGER', 'text':'MANAGER'},
	    	 			 {'value':'WEBUSER', 'text':'WEB USER'}];
    $scope.format = 'dd-MM-yyyy'
    $scope.user = {address:'', cords:''};   
    
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
    
    $scope.openDobCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'openeddob' : true};  
             }, 50);
        };
    
    $scope.setGender = function(selectedgender){       
       $scope.gender = selectedgender.value;
   };
   
   $scope.setPhysicalChall = function(selectedChall){
       $scope.phyChall = selectedChall.value;
   };
    	
   $scope.setPickDropLocation = function(){
       $scope.onMap= true;
       $scope.onUserInfo = false;
    };
    
   $scope.setUserRole = function(selectedUserRole){
       $scope.userRole = selectedUserRole.value;      
   };
    
    $scope.backtoUserInfo = function(){
       $scope.onMap= false;
        $scope.onUserInfo = true;        
    };
    
    $scope.save = function(user){
        console.log(user);
        $modalInstance.close(user);
    };
        
   //CLOSE BUTTON FUNCTION
   $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
   };
    
};
var addNewShiftTime = function($scope, $modalInstance, $state, $http, $timeout, allExistingShifts){ 
   $scope.alertMessage;
   $scope.alertHint;       
   $scope.tripTypes = [{'value':'PICKUP', 'text':'PICKUP'},
                       {'value':'DROP', 'text':'DROP'}];  
   $scope.hstep = 1;
   $scope.mstep = 5;
   $scope.ismeridian = false;
   $scope.newShift = {};
       
   $scope.initializeTime = function(){
        var d = new Date();
        d.setHours( 00 );
        d.setMinutes( 0 );
//        d.setSeconds();
        return d;           
   };
       
   //Convert to hh:mm
   var convertToTime = function(newdate){
       d = new Date(newdate);
//       alert(d);
       hr = d.getHours();
       min = d.getMinutes();
       if(hr<10){hr = '0'+hr;} 
       if(min<10){min = '0'+min;}           
       console.log("TIME :: " + hr+":"+min);
       return hr+":"+min;
   };  
    
   $scope.newShift.createNewAdHocTime = $scope.initializeTime();
    
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
    
    $scope.saveNewShift = function(newShift) {
//        var shiftAlreadyExist = _.findIndex(allExistingShifts, { shiftTime: convertToTime(newShift.createNewAdHocTime), tripType: 'Drop'});
//        if(shiftAlreadyExist<0){
             var data = {
                     eFmFmClientBranchPO:{branchId:branchId},
                     time:convertToTime(newShift.createNewAdHocTime)+':00',
                     tripType:newShift.tripType.text

                  };
            console.log(data)
            $http.post('services/trip/addshiftime/',data).
                success(function(data, status, headers, config) {                
                console.log('Inside Save New Shift:: $scope.saveNewShift()');
                console.log(data);
                if(data.status == 'success'){
                    $('.noMoreClick').addClass('disabled');
                    newShift.tripType = newShift.tripType.text;
                    $scope.showalertMessageModal('Shift has been created successfuly.', '');
                    $timeout(function() {$modalInstance.close(newShift)}, 3000);	
                }
                else{
                    $scope.showalertMessageModal('This Shift Time - ' +convertToTime(newShift.createNewAdHocTime)+ ' for Trip Type - ' +newShift.tripType.text+ ' already exist.', '');}
    //                    newShift.createNewAdHocTime = convertToTime(newShift.createNewAdHocTime);
    //                	alert(JSON.stringify(data));
                    //If status equal to fail..that means.....Give alert This shift time already exist...for this trip type
                    //Sucess....shift time added

                }).
                error(function(data, status, headers, config) {
                  // log error
                });         
//        }
//        else{
//            $scope.showalertMessageModal('This Shift Time - ' +convertToTime(newShift.createNewAdHocTime)+ ' for Trip Type - ' +newShift.tripType.text+ ' already exist.', '');
//        }
 //       $modalInstance.close(newShift);
    	
    	
        
 //       console.log(newShift);
    };     
    
        
    //CLOSE BUTTON FUNCTION
    $scope.cancel = function () {
       $modalInstance.dismiss('cancel');
    };
};
    
    angular.module('efmfmApp').controller('myProfileCtrl', myProfileCtrl);    
    angular.module('efmfmApp').controller('addUserCtrl', addUserCtrl);   
    angular.module('efmfmApp').controller('addNewShiftTime', addNewShiftTime);
    angular.module('efmfmApp').controller('mapMyProfileCtrl', mapMyProfileCtrl);
}());