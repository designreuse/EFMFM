(function(){
   var empTravelDeskCtrl = function($scope, $http, $filter, $modal, $timeout, $confirm){
    
	 $('.empTravelDeskMenu').addClass('active');
     $scope.isLoaded = false;
     $scope.currentPage = 1;
     $scope.totalRecords = 0;
     $scope.hint;	   
     $scope.showSearchResultCount = false;
     $scope.countofFilteredRecords;
     $scope.showSelectShiftsCount = false;  
     $scope.showSelectShiftsRecords;
     $scope.numOfRows = 0;
     $scope.numberofRecords = 10000;
     $scope.selectAllClicked = false;
     $scope.deleteAllClicked = false;
     $scope.search = {};
     $scope.searchIsEmpty = false;
     $scope.paginations = [{'value':10, 'text':'10 records'},
    	 				   {'value':15, 'text':'15 record',},
    	 				   {'value':20, 'text':'20 records'}];
     
     $scope.shiftsTime = [];
     
     $scope.$on('$viewContentLoaded', function() { 
		   $scope.getTravelDesk();		   
		});
     
//     $scope.$on('LOADMODAL', function(){$scope.isProcessingModal = true;});
//     $scope.$on('UNLOADMODAL', function(){$scope.isProcessingModal = true;});
      
       
     $scope.getTravelDesk = function(){
         $scope.progressbar.start();
//         $scope.$emit('LOAD');
         var data = {
        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
        		 userRole:userRole,
        		 efmFmUserMaster:{userId:profileId}       		 
         };	
         $http.post('services/trip/employeeTravelRequest/',data).
                success(function(data, status, headers, config) {
                  $scope.OriginalPostsData = data.requests; 
                  $scope.posts = data.requests;   
                    console.log(data);
                  $scope.shiftData = data.shifts;
                  angular.forEach($scope.shiftData, function(item){
                      $scope.shiftsTime.push(item.shiftTime);
                  });
//                  $scope.countofFilteredRecords = $scope.posts.length;
//                  $scope.countofFilteredRecords = 0;
//                  $scope.showSearchResultCount = false;
                  angular.forEach($scope.posts, function(item) {
                      item.checkBoxFlag=false; });
//                $scope.$emit('UNLOAD');
                $scope.progressbar.complete();
             $scope.isLoaded = true;
                }).
                error(function(data, status, headers, config) {
                  // log error
                });         
     };
       
     //ACTION :: Called on ng-change of the Show Record Drop Down :: Set the limits of Records to show
     $scope.setLimit = function(showRecords){
    	 if(!showRecords){$scope.numberofRecords = $scope.posts.length;}
    	 else $scope.numberofRecords = showRecords.value;  	 
    };
    
    $scope.setShifts = function(shift){
//        alert(shift);
        if(shift){
             var data = {
               eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
               userRole:userRole,
               efmFmUserMaster:{userId:profileId},
               time:shift // Note: the Shift time passed does not have seconds. It is pass in hh:mm format
             }; 
             $http.post('services/trip/employeeshiftwiserequest/',data).
                 success(function(data, status, headers, config) {
//                 alert(JSON.stringify(data));
                    $scope.posts = data.requests;
                }).
                 error(function(data, status, headers, config) {});           
           }
           else{
               $scope.posts = $scope.OriginalPostsData;
           }
    };
     
    //ACTION :: SelectAll Check Box is Clicked
    $scope.selectAll = function(){
    	 if(!$scope.selectAllClicked){
    		 $scope.selectAllClicked = true;
    		 angular.forEach($scope.posts, function(item) {
		    	  item.checkBoxFlag=true;
	         });
    		 }
    	 else
    		 {
    		 $scope.selectAllClicked = false;
    		 angular.forEach($scope.posts, function(item) {
		    	  item.checkBoxFlag=false;
	         });	    		 	
    		 }
     };   
    
     //ACTION :: Single Check Box Clicked     
     $scope.select_thisEmployee = function(post) {
    	 if(!post.checkBoxFlag){
    		 post.checkBoxFlag = true;
    		 console.log(post.name + " is selected");
    	 }
    	 else {
    		 post.checkBoxFlag = false;
    		 $scope.selectAllClicked = false;
    		 $scope.deleteAllClicked = false;
    		 console.log(post.name + " is un-selected");
    	 }
     };
     
     //BUTTON ACTION :: EMPLOYEE REPORTED AT SERVICE DESK  
     $scope.employeeReported = function(){
         //insert all the checked employees in the array
         var checkedEmployee = [];
         var x = 0;
         angular.forEach($scope.posts, function(item){
                if(item.checkBoxFlag){
                    checkedEmployee.push(item.requestId); 
                    console.log('employee:: ' + item.employeeId);
                    console.log('checkedEmployee[]:: ' + checkedEmployee[x]);
                    x++;
                    //onSuccess Block;                    
                }});  
         //if no employee checked
         if(checkedEmployee.length==0){
             $scope.showalertMessage('Please Check one or more Employees.', '[Hint: Please Check one or more check boxes]');
        	 return false;
         }
         //Call HTTP Service
           var dataObj = {
        		   
          		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				   employeeId:JSON.stringify(checkedEmployee)
			};	
		   $http.post('services/trip/travelrequest/',dataObj).
				    success(function(data, status, headers, config) {
				    	if(data.status=="fail"){
                            $scope.showalertMessage('No vehicle checked in please wait..', '');
				    	}
				    	else{ 
                            angular.forEach($scope.posts, function(item){
                            if(item.checkBoxFlag){
                                 $('.employee'+item.employeeId+item.requestId).hide('slow');  
                                 item.checkBoxFlag = false;
                            }}); 
                            $scope.selectAllClicked = false;                           
//                            $scope.showalertMessage('Cab is Assigned successfully', '');
				    	}				    	
				    }).
				    error(function(data, status, headers, config) {
				      // log error
				    });        
     };

     //BUTTON ACTION :: DELETE SINGLE EMPLOYEE   
     $scope.deleteEmployee = function(post){ 
         
      $confirm({text: 'Are you sure you want to delete this row?', title: 'Delete Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {              
              var dataObj = {
                     efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                       requestId:post.requestId
                };	
               $http.post('services/trip/requestdelete/',dataObj).
                        success(function(data, status, headers, config) {
                            if(data.status=="success"){	                            	
                                $('.employee'+post.employeeId+post.requestId).hide('slow');
                                post.tripTime = '23:50:00';
                                $scope.showalertMessage(post.employeeName + ' has been changed successfully', '');
                                post.checkBoxFlag = false; 
                            }
                        }).
                        error(function(data, status, headers, config) {
                          // log error
                        });
        });
//          if(confirm("Are you sure you want to Delete this row?") == true){                
//              var dataObj = {
//                     efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
//                       requestId:post.requestId
//                };	
//               $http.post('services/trip/requestdelete/',dataObj).
//                        success(function(data, status, headers, config) {
//                            if(data.status=="success"){	                            	
// //                               $('.employee'+post.employeeId+post.requestId).hide('slow');
//                                post.tripTime = '23:50:00';
//                                $scope.showalertMessage(post.employeeName + ' has been changed successfully', '');
//                                post.checkBoxFlag = false; 
//                            }
//                        }).
//                        error(function(data, status, headers, config) {
//                          // log error
//                        });
//          }
      };
     
     //BUTTON ACTION :: CANCEL ALL ACTIONS/SELECTIONS    
     $scope.cancel = function(){
          if($scope.selectAllClicked){
              $scope.selectAllClicked = false;
          }       
            angular.forEach($scope.posts, function(item) {
              item.checkBoxFlag=false;
           });   		 	
     };
     
     //BUTTON ACTION :: CLOSE ALERT
     $scope.closeAlert = function(){
         $('.alert_TravelDesk').hide('slow');
     };    
       
    $scope.$watch("searchEmployeeReported", function(query){
    	if(!$scope.searchEmployeeReported){
    	     $scope.showSearchResultCount = false;
    	}
    	else{
    	     $scope.showSearchResultCount = true;  
             $scope.countofFilteredRecords = $filter("filter")($scope.posts, query);
        }
      });       
    $scope.$watch("selectShifts", function(query){
    	if($scope.selectShifts==""){
    	     $scope.showSelectShiftsCount = false;
    	}
    	else{
    	     $scope.showSelectShiftsCount = true;  
             $scope.showSelectShiftsRecords = $filter("filter")($scope.posts, query);
        }
      });
     
    $scope.$watch("search.text", function(query){
        if($scope.search.text == ""){
            $scope.searchIsEmpty = false;
        }
    });
       
    //BUTTON ACTION : Edit Travel Desk Employee
    $scope.editTravelDesk = function(post, index, size){
           //alert(post.tripTime);
           var tripTimeArray = post.tripTime.split(':');
           var newTripTimeFormat = tripTimeArray[0] + ':' + tripTimeArray[1];
            //alert(newTripTimeFormat);
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/editTravelDeskModal.jsp',
           controller: 'editTravelDeskCtrl',
           size:size,    
           resolve: {
               employee: function (){return post;},
               tripTimeSelected : function (){return newTripTimeFormat;}
           }
           });
		   
		   modalInstance.result.then(function(result){
               console.log(result);
               if(result.changeMasterData != 'N'){
                  post.weekOffs = result.selectedDays;
               }
               
               if(post.tripType == 'DROP'){
                   post.pickUpTime = result.sequenceNumber;
               }
               else{
                post.pickUpTime = result.timePickerSelectedTime;
               }
               
               post.tripTime = result.shiftTime.shiftTime+':00';
               post.employeeRouteName = result.zone.routeName;
               
           });      	        
    };
    
    //BUTON ACTION :: Assign Cab
    $scope.assignCab = function(){
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/assignCabModal.jsp',
           controller: 'assignCabCtrl',
           backdrop:'static',
           resolve: {
//               employee: function (){return employee;}
           }
           }); 
		   
		   modalInstance.result.then(function(result){ 
               if($scope.selectShifts){
                   console.log('Showing by Shift');
                   $scope.setShifts($scope.selectShifts);
               }
               else{  
                   console.log('Showing All Shift');
                   var data = {
                         eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                         userRole:userRole,
                         efmFmUserMaster:{userId:profileId}       		 
                   };	
                   $http.post('services/trip/employeeTravelRequest/',data)
                     .success(function(data, status, headers, config) {
                          $scope.OriginalPostsData = data.requests; 
                          $scope.posts = data.requests;   
                        })
                     .error(function(data, status, headers, config) {});
                }
               
               $timeout(function(){$scope.showalertMessage('Cab has been assigned successfully');},3000);
               //we can put the condition over here that if data = success then execute the following code
               
           }); 
    };
    
    
    //BUTTON ACTION :: Map Icon is Clicked 
    $scope.openSingleModal = function(employee, size){
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/singleMapLocation.jsp',
           controller: 'singleMapCtrl',
           size:size,
           resolve: {
               employee: function (){return employee;}
           }
           }); 
		   
		   modalInstance.result.then(function(result){
               console.log("in modal instance::"+result.employeeId);
               var index = _.findIndex($scope.posts, {employeeId:result.employeeId});                
               console.log("in modal INDEX::"+index);
               if(index>=0){                
                   $scope.posts[index].employeeWaypoints = result.cords;
                   $scope.posts[index].employeeAddress = result.employeeAddress;
               }
           });      	
    };
       
    $scope.searchEmployee = function(searchText){
        if(searchText){
            $scope.searchIsEmpty = false;
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
                        $scope.showalertMessage("Request not exist for this employeeId,Please check employeeId", '');                                         
                    }
                    else{
  			    	/*if(data==""){
  			    		alert("Request not Exist for this employeeId");
  			//    		return false;
  			    	}*/
  			    	$scope.posts =data.requests;
  			    	angular.forEach($scope.posts, function(item) {
                        item.checkBoxFlag=false; });
//                  $scope.$emit('UNLOAD');
                  $scope.progressbar.complete();
               $scope.isLoaded = true;
                    }
  			    }).
  			    error(function(data, status, headers, config) {
  			      // log error
  			    }); 
        }
        else{
            $scope.searchIsEmpty = true;
        }
    };
    
    $scope.refreshEmployeeTravelDesk = function(){
        $scope.getTravelDesk();
    };
}; 
     
//CONTROLLER FOR THE 'Edit Travel Desk Row' MODAL
var editTravelDeskCtrl = function($scope, $modalInstance, $state, employee, $http, $timeout, tripTimeSelected){ 
        console.log(employee);
        console.log(tripTimeSelected);
        $scope.delay = 0;
        $scope.minDuration = 0;
        $scope.templateUrl = 'bower_components/ngSpinner/custom-template.html';
        $scope.message = 'Please Wait...';
        $scope.backdrop = true;
        $scope.promise = null;
    
     $scope.$on('LOADMODAL', function(){$scope.isProcessingModal = true;});
     $scope.$on('UNLOADMODAL', function(){$scope.isProcessingModal = true;});
    
         $scope.update = {};
         $scope.update.shiftTime = {};
         $scope.hstep = 1;
         $scope.mstep = 5;
         $scope.ismeridian = false;
         var isShiftTimeChange = false;
         var isZoneChange = false;
         var isAreaChange = false;
         var isDaysOffChange = false;
         var isChangeToRegularShiftChange = false;
         var isPickUpTimeChange = false;
         $scope.update.changeMasterData = 'N';         
         $scope.triptype = employee.tripType;
         $scope.update.sequenceNumber = employee.pickUpTime; // Change the employeeId with the sequence parmeter
         var isSeqChange;
         var todaysDate = new Date();
         $scope.update.daysModel = [];
         var shiftTimeIndex;
         $scope.alertMessage;
         $scope.alertHint;  
    
//         $scope.pickupShiftsTime = [{'shiftTime': '05:30:00' },
//                                    {'shiftTime': '07:00:00'},
//                                    {'shiftTime': '12:00:00'},
//                                    {'shiftTime': '14:00:00'},
//                                    {'shiftTime': '16:00:00'},
//                                    {'shiftTime': '18:00:00'}
//                                    ,{'shiftTime': '19:00:00'}];
//    
//         $scope.dropShiftsTime = [{'shiftTime': '14:30:00' },
//                                    {'shiftTime': '16:00:00'},
//                                    {'shiftTime': '21:00:00'},
//                                    {'shiftTime': '23:00:00'},
//                                    {'shiftTime': '01:00:00'},
//                                    {'shiftTime': '03:00:00'}];
    
         $scope.daysData = [{'id':'Sunday', label:'Sunday'},
                           {'id':'Monday', label:'Monday'},
                           {'id':'Tuesday', label:'Tuesday'},
                           {'id':'Wednesday', label:'Wednesday'},
                           {'id':'Thursday', label:'Thursday'},
                           {'id':'Friday', label:'Friday'},
                           {'id':'Saturday', label:'Saturday'}];
    
         if(employee.weekOffs !== ''){
             var tempDaysOff = employee.weekOffs.split(',');
             angular.forEach(tempDaysOff, function(item){
                 $scope.update.daysModel.push({'id':item, 'label': item});
             });
         }
         
         $scope.daysButtonLabel = {'buttonDefaultText':'Select Days Off'};
         
         $scope.daysSettings = {
                smartButtonMaxItems: 5,
                smartButtonTextConverter: function(itemText, originalItem) {
//                if (itemText === 'Sunday') {
//                return 'Sun!';
//                }
                return itemText;
                }
         };
    
      $scope.setShifttTime = function(triptype){
          var data = {
                       efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                       eFmFmEmployeeRequestMaster:{efmFmUserMaster:{userId:profileId}},
                       tripType:triptype
                          }; 
                   $http.post('services/trip/tripshiftime/',data).
                        success(function(data, status, headers, config) {
                            $scope.shiftsTimes = data.shift; 
                            //$scope.update.shiftTime = {shiftTime:employee.tripTime, tripType:$scope.triptype};                
                       //alert(JSON.stringify($scope.shiftsTimes));

                        }).
                        error(function(data, status, headers, config) {});
      };
      $scope.setShifttTime($scope.triptype);
       
         $scope.initializeTimePickerTime = function(time){
            var pickUpTime = time.split(':');
            var d = new Date();
            d.setHours( pickUpTime[0] );
            d.setMinutes( pickUpTime[1] );
            return d;           
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
       
       //Convert to dd-mm-yyyy
         var convertToTime = function(newdate){
            d = new Date(newdate);
            hr = d.getHours();
            min = d.getMinutes();
            if(hr<10){hr = '0'+hr;} 
            if(min<10){min = '0'+min;}           
            console.log("TIME :: " + hr+":"+min);
            return hr+":"+min;
         };
         if(employee.tripType == 'PICKUP'){
         $scope.update.selectedDate = $scope.initializeTimePickerTime(employee.pickUpTime);  
         }
         
         
        /* var data = {
        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
        		 userRole:userRole,
        		 efmFmUserMaster:{userId:profileId} ,
                 time:'16:00:00'
         };	
         $http.post('services/trip/employeeshiftwiserequest/',data).*/
         
         var data = {
            efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},

        };
        $http.post('services/trip/editemployeetravelrequest/',data).
                success(function(data, status, headers, config) {
                    $scope.zonesData = data.routesData;
                    console.log($scope.zonesData);
                    var zoneIndex = _.findIndex($scope.zonesData, {routeName: employee.employeeRouteName});
                    if(employee.tripType == 'DROP'){
                        shiftTimeIndex =  _.findIndex($scope.shiftsTimes, { shiftTime: tripTimeSelected });   
                        //alert(shiftTimeIndex + ' ' + tripTimeSelected);
                        if(shiftTimeIndex>=0){
                            $scope.update.shiftTime = $scope.shiftsTimes[shiftTimeIndex];
                        }
                        else $scope.update.shiftTime = '';                    
                    }
                    else{
                        shiftTimeIndex =  _.findIndex($scope.shiftsTimes, { shiftTime: tripTimeSelected }); 
                        //alert('PICKUP' + shiftTimeIndex+ ' ' + tripTimeSelected);
                        if(shiftTimeIndex>=0){
                            $scope.update.shiftTime = $scope.shiftsTimes[shiftTimeIndex];
                        }
                        else $scope.update.shiftTime = '';
                    }
            
                    if(zoneIndex>=0){
                        $scope.update.zone = $scope.zonesData[zoneIndex];
                        console.log($scope.zonesData[zoneIndex]);
                        $scope.update.area = employee.employeeAreaName;
//                        var data = {
//                        eFmFmZoneMaster:{zoneId:$scope.zonesData[zoneIndex].routeId},
//                       };
//                       $http.post('services/trip/zonevicearea/',data).
//                            success(function(data, status, headers, config) {
//                                $scope.areasData = data.areas;
//                                var areaIndex = _.findIndex($scope.areasData, { areaName: employee.employeeAreaName });
//                                if(areaIndex>=0){
//                                    $scope.update.area = $scope.areasData[areaIndex];
//                                }
//                            }).
//                            error(function(data, status, headers, config) {
//                              // log error
//                            });
                    }
                    else{
                        $scope.update.zone = '';
//                        $scope.update.area = '';
                    }
                }).
			    error(function(data, status, headers, config) {
			      // log error
			    });
    
        $scope.setshiftTime = function(shift){
            if(angular.isObject(shift)){
                if(shift.shiftTime != employee.tripTime){
                    isShiftTimeChange = true;
                    if(employee.tripType != 'DROP'){
//                        $scope.showalertMessageModal('Please Change the Pick-up Time', '');
                        
                    }
                }
                else{
                    isShiftTimeChange = false;
                }                    
            } 
        };
    
        $scope.setZone = function(zone){
            if(angular.isObject(zone)){  
                if(zone.routeName !== employee.employeeRouteName){
                    isZoneChange = true;
                }
                else{
                    isZoneChange = false;}
//                var data = {
//                eFmFmZoneMaster:{zoneId:zone.routeId},
//               };
//               $http.post('services/trip/zonevicearea/',data).
//                    success(function(data, status, headers, config) {
//                        $scope.areasData = data.areas;
//                        $scope.update.area = '';
//                    }).
//                    error(function(data, status, headers, config) {
//                      // log error
//                    });
            }
//            else{
//                $scope.areasData = [];
////                $scope.shiftTimeNotValid = true;
//            }           
        };
        
//        $scope.setArea = function(area){
//            if(angular.isObject(area)){                
//                if(area.areaName != employee.employeeAreaName){
//                    isAreaChange = true;
//                }
//                else{
//                    isAreaChange = false;
//                }
//            }
//        };
         
        //SAVE BUTTON FUNCTION 
        $scope.save = function(result){
            result.selectedDays = result.daysModel.map( function( el ){ 
                                return el.id; 
                               }).join();
            //Watch for the Days Of String
            if(result.selectedDays != employee.weekOffs){
                isDaysOffChange = true;
            }
            else{
                isDaysOffChange = false;
            }
            
            //Watch for the CheckBox
            if($scope.update.changeMasterData!='N'){
                isChangeToRegularShiftChange = true;
            }
            else{isChangeToRegularShiftChange = false;
            }
            
            if($scope.triptype == 'DROP'){
                result.timePickerSelectedTime = $scope.update.sequenceNumber;
            }
            else{
                result.timePickerSelectedTime = convertToTime(result.selectedDate);                
                //Watch for Time From Timer 
                if(result.timePickerSelectedTime != employee.pickUpTime){
                    isPickUpTimeChange = true;
                }
                else{
                    isPickUpTimeChange = false;
                }
            }
            if(result.sequenceNumber == employee.pickUpTime){
                isSeqChange = false;
            }else isSeqChange = true;
            
            console.log('result.zone.routeId ' + result.zone.routeId);
            console.log(isShiftTimeChange + ' ' + isZoneChange + ' ' + isAreaChange + ' ' + isPickUpTimeChange +' ' +  isDaysOffChange +' ' +  isChangeToRegularShiftChange);
            if(isShiftTimeChange || isZoneChange || isAreaChange || isPickUpTimeChange || isDaysOffChange || isChangeToRegularShiftChange || isSeqChange){                            
//                     $scope.$emit('LOADMODAL'); 
                     var dataObj = {
                      		 efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                                 requestId:employee.requestId,
                                 areaId:employee.employeeAreaId,
                                 zoneId:result.zone.routeId,
                                 weekOffs:result.selectedDays,
                                 updateRegularRequest:result.changeMasterData,
                                 time:result.shiftTime.shiftTime,
                                 pickTime:result.timePickerSelectedTime
                          };	
                console.log(dataObj);
                     $scope.promise = $http.post('services/trip/updatetravelrequestdata/',dataObj).
          			     success(function(data, status, headers, config) {
          			    	if(data.status=="success"){
          			    		$scope.showalertMessageModal("Request updated successfully", "");                                
                                $('.loading').show();
                                $timeout(function() {$modalInstance.close(result)}, 3000);
          			    	}
          			    }).
          			    error(function(data, status, headers, config) {
          			      // log error
          			    });
            }  
            else{$scope.showalertMessageModal('None of the Entity has been change', '');
            $('.loading').show();
            $timeout(function() {$modalInstance.close(result)}, 3000);   
//            $scope.$emit('UNLOADMODAL');
                }
        };
         
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };      
     
//CONTROLLER FOR THE 'view on map' MODAL
var singleMapCtrl = function($scope, $modalInstance, $state, employee, $http, $timeout){
        $scope.isLoaded = false;
        $scope.employee = {cords:employee.employeeWaypoints,
                           employeeAddress: employee.employeeAddress,
                           employeeName: employee.employeeName,
                           employeeId: employee.employeeId, 
                           employeeName: employee.employeeName};
        $scope.isLoaded = true;   
        
        //SAVE BUTTON FUNCTION 
        $scope.saveNewCords = function(result){
           employee.employeeWaypoints = result.cords;
           employee.employeeAddress = result.employeeAddress;
           console.log("in savenewcords()::"+ employee.employeeWaypoints+ " :: "+ result.employeeAddress);
           var dataObj = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                       requestId:employee.requestId
                };	
           $http.post('services/trip/addressUpdate/',dataObj).
			     success(function(data, status, headers, config) {
			    	if(data.status=="success"){
                      $modalInstance.close(result);
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
    
//CONTROLLER FOR THE 'view on map' MODAL
var assignCabCtrl = function($scope, $modalInstance, $state, $http, $timeout){  
       $scope.shiftsTime = [];
       $scope.assignCab ={};
       $scope.typeOfShiftTimeSelected;
       var tripTimeSelected;
       $scope.hstep = 1;
       $scope.mstep = 5;
       $scope.ismeridian = false;
       $scope.tripTypes = [{'value':'PICKUP', 'text':'PICKUP'},
	    	 	           {'value':'DROP', 'text':'DROP'}];
       $scope.pickupShiftsTime = [{'shiftTime': '05:30:00' },
                                    {'shiftTime': '07:00:00'},
                                    {'shiftTime': '12:00:00'},
                                    {'shiftTime': '14:00:00'},
                                    {'shiftTime': '16:00:00'},
                                    {'shiftTime': '18:00:00'},
                                    {'shiftTime': '19:00:00'}];
    
       $scope.dropShiftsTime = [{'shiftTime': '14:30:00' },
                                    {'shiftTime': '16:00:00'},
                                    {'shiftTime': '21:00:00'},
                                    {'shiftTime': '23:00:00'},
                                    {'shiftTime': '01:00:00'},
                                    {'shiftTime': '03:00:00'}];
    
        //Initialize TimePicker to 00:00
        var timePickerInitialize = function(){
           var d = new Date();
           d.setHours( 00 );
           d.setMinutes( 0 );
           $scope.assignCab.createNewAdHocTime = d;
        }
       $scope.assignCab.needRoutewise = false;
       var data = {
			eFmFmClientBranchPO:{branchId:branchId}
       };
       $http.post('services/zones/createbucket/',data).
            success(function(data, status, headers, config) {
                $scope.allZonesData = data.routesData;   
                $('.btn-link').addClass('noPointer');
                $scope.shiftsTime = [{'shiftTime': 'Select Shift Time'}];
                $scope.assignCab.shiftTime = $scope.shiftsTime[0];                
                timePickerInitialize();
                console.log(data);
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
                            $scope.assignCab.shiftTime = $scope.shiftsTime[0];

                        }).
                        error(function(data, status, headers, config) {});
            } 
           else {
               $scope.shiftsTime = '';
           }
        $scope.assignCab.shiftTime = $scope.shiftsTime[0];
      };
    
    $scope.assign = function(cab){
        var selectedZone;
        $('.noMoreClick').addClass('disabled');
        if($scope.typeOfShiftTimeSelected == 'preDefineShiftTime'){
            tripTimeSelected = cab.shiftTime.shiftTime; 
        }
        else{
            var fullDate = new Date(cab.createNewAdHocTime);
            var time = fullDate.getHours()+':'+fullDate.getMinutes()+':00';
            tripTimeSelected = time;
        }
 //        alert(tripTimeSelected);
        
        if(!cab.needRoutewise){
            selectedZone = 0;
        }else{ selectedZone = cab.zone.routeId;} 
        var dataObj = {
                branchId:branchId,
                time:tripTimeSelected, // replaced the *time:cab.shiftTime.shiftTime,
                zoneId:selectedZone,
                tripType:cab.tripType.value               
              };  
        console.log(dataObj);
        //OLD CODE
//        var dataObj = {
//                branchId:branchId,
//                time:cab.shiftTime.shiftTime,
//                zoneId:selectedZone,
//                tripType:cab.tripType.value               
//              };    
               $http.post('services/request/caballocation/',dataObj).
                  success(function(data, status, headers, config) {
                	  if(data==""){                          
                          $scope.showalertMessageModal("Cab allocation is in progress....", ''); 
                          $timeout(function() {$modalInstance.close(data)}, 3000);
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
       
    angular.module('efmfmApp').controller('editTravelDeskCtrl', editTravelDeskCtrl);  
    angular.module('efmfmApp').controller('singleMapCtrl', singleMapCtrl); 
    angular.module('efmfmApp').controller('assignCabCtrl', assignCabCtrl);
    angular.module('efmfmApp').controller('empTravelDeskCtrl', empTravelDeskCtrl);
}());