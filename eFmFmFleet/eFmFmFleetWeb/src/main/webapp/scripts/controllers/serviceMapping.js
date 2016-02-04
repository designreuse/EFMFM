(function(){
   var serviceMappingCtrl = function($scope, $http, $state, $modal, $log, $timeout, $confirm){
       $scope.advanceIsClicked = false; 
       $scope.serachResultFound = false;
       $scope.advanceLabel = 'Advance Search';
       $scope.shiftsTime = [];
       $scope.searchData = [];
       $scope.searchIsEmpty = false;
       $scope.search = {};
       $scope.hstep = 1;
       $scope.mstep = 5;
       $scope.ismeridian = false;
//       $scope.search;
       
       $scope.searchTypes = [{'value':'Search by VEHICLE', 'text':'Vehicle'},
                             {'value':'Search by EMPLOYEE ID', 'text':'EmployeeId'}];
//       $scope.search.searchBy = $scope.searchTypes[0];
       
       $scope.tripTypes = [{'value':'PICKUP', 'text':'PICKUP'},
	    	 	           {'value':'DROP', 'text':'DROP'}];
       
       $scope.routesStatus = [{'value':'All', 'text':'All Routes'},
	    	 	           {'value':'Close', 'text':'Close Routes'},
	    	 	           {'value':'Started', 'text':'Started Routes'},
	    	 	          {'value':'open', 'text':'Open Routes'}];
    
//       $scope.pickupShiftsTime = [{'shiftTime': '05:30:00' },
//                                    {'shiftTime': '07:00:00'},
//                                    {'shiftTime': '12:00:00'},
//                                    {'shiftTime': '14:00:00'},
//                                    {'shiftTime': '16:00:00'},
//                                    {'shiftTime': '18:00:00'},
//                                    {'shiftTime': '19:00:00'}];
//    
//       $scope.dropShiftsTime = [{'shiftTime': '14:30:00' },
//                                    {'shiftTime': '16:00:00'},
//                                    {'shiftTime': '21:00:00'},
//                                    {'shiftTime': '23:00:00'},
//                                    {'shiftTime': '01:00:00'},
//                                    {'shiftTime': '03:00:00'}];
       $scope.search.routeStatus = {'value':'All', 'text':'All Routes'};
       
       $('.serviceMappingMenu').addClass('active');
       $scope.editBucket = false;
       $scope.singleRouteData=[];
       $scope.isloaded = false;
       $scope.currentMapindex;
//       $scope.tripStatusStarted;
       $scope.moveToRoutes = [];
       $scope.routesData = [];
//       $scope.search.tripType = $scope.tripTypes[0];
       
       
       $scope.$on('$viewContentLoaded', function() {
    	   $scope.getZoneData();
       });
       
       $scope.$watch("search.text", function(query){
        if($scope.search.text == ""){
            $scope.searchIsEmpty = false;
        }});  
       
       $scope.initializeTime = function(time){
            var d = new Date();
            var tempTime = time.split(":");
            d.setHours( tempTime[0] );
            d.setMinutes( tempTime[1] );
            return d;           
       }
           
       $scope.getZoneData = function(){
           $scope.progressbar.start();
          // $scope.totalRoutes = 0;
           var dataObj = {
        		   eFmFmClientBranchPO:{branchId:branchId},
				};	
           $http.post('services/zones/allzones/',dataObj).
				success(function(data, status, headers, config) {
 //              alert(JSON.stringify(data.routeDetails));
                    $scope.Original_zoneData = data.routeDetails;
                    $scope.zoneData = data.routeDetails;
//                    angular.forEach($scope.zoneData, function(item){
//                        $scope.totalRoutes += item.NumberOfRoutes;
//                    });
                    $scope.searchData = [];
                    console.log($scope.zoneData);
                    $scope.progressbar.complete();
				}).
				error(function(data, status, headers, config) {
					      // log error
				});
       };  
       
       $scope.getRoutes = function(zone){
           $scope.progressbar.start();
           var dataObj = {
        		   eFmFmClientBranchPO:{branchId:branchId},
        		   eFmFmRouteAreaMapping:{eFmFmZoneMaster:{zoneId:zone.routeId}}
				};	
           $http.post('services/zones/allroutes/',dataObj).
				success(function(data, status, headers, config) {
                   $scope.routesData = data.routeDetails;
                   console.log($scope.routesData);
                   angular.forEach($scope.routesData, function(item){
                      item.isVehicleEmpty = false;
                      if(item.tripType == "DROP") {
                          angular.forEach(item.empDetails, function(item){
                                item.upArrows = false;
                                item.downArrows = false;
                                item.isZoneclicked = false;
                                item.isUpdateClicked = false;
                                item.dropSequence = item.pickUpTime;
                            }); 
                      }
                      else{ 
                          angular.forEach(item.empDetails, function(item){
                                item.upArrows = false;
                                item.downArrows = false;
                                item.isZoneclicked = false;
                                item.isUpdateClicked = false;
                                item.createNewAdHocTime = $scope.initializeTime(item.pickUpTime);
                            }); 
                      }                           
                      item.editClicked = false;
                      item.closeBucketClicked = true;
                      $scope.progressbar.complete();
                   });              
                    
				}).
				error(function(data, status, headers, config) {
					      // log error
				});
       }; 
       
       $scope.createNewRoute = function(){          
          var modalInstance = $modal.open({
            templateUrl: 'partials/modals/creatNewRouteModal.jsp',
            controller: 'creatNewRouteCtrl',
            backdrop:'static',
            resolve: {
                uiZone:function(){return $scope.zoneData;}
            }
          });

          modalInstance.result.then(function(result){ 
              console.log(result);               
              //Check if the Zone already exist
              var zoneExistIndex = _.findIndex($scope.zoneData, { routeId: result.zoneRouteId });
              if(zoneExistIndex >=0 ){
                  $scope.routesData.unshift({'routeId':result.routeId, 
                                             'tripType': result.tripType, 
                                             'shiftTime':result.shiftTime, 
                                             'tripStatus':result.tripStatus,
                                             'routeName':result.zoneName,
                                             'escortRequired':result.escortRequired,
                                             'escortName':result.escortName,
                                             'editClicked':true,
                                             'closeBucketClicked':true,
                                             'bucketStatus':result.bucketStatus,
                                             'capacity': result.capacity,
                                             'deviceNumber':result.deviceNumber,
                                             'driverId':result.driverId,
                                             'driverName':result.driverName,
                                             'driverNumber':result.driverNumber,
                                             'vehicleAvailableCapacity':result.vehicleAvailableCapacity,
                                             'vehicleId':result.vehicleId,
                                             'vehicleNumber': result.vehicleNumber,
                                             'vehicleStatus':result.vehicleStatus,
                                             'vendorId':result.vendorId,
                                             'empDetails':[]}); 
                  $scope.zoneData[zoneExistIndex].NumberOfRoutes = $scope.zoneData[zoneExistIndex].NumberOfRoutes + 1;
              }              
              else{                                   
                  $scope.zoneData.unshift({'NumberOfRoutes':1, 'routeId':result.zoneRouteId, 'routeName':result.zoneName});
              }
          }); 
       };
       
       $scope.advanceSearch = function(){
           if($scope.advanceIsClicked){
               $scope.advanceIsClicked = false;
               $scope.advanceLabel = 'Advance Search';
           }
           else {
               $scope.advanceIsClicked  = true;
               $scope.advanceLabel = 'Search Employee';
           }
       };
       
       $scope.backtoMainList = function(){           
           $scope.serachResultFound = false;
           $scope.advanceIsClicked = false; 
           $scope.search.tripType = ''; 
           $scope.search.shiftTime = ''; 
           $scope.search.employeeId = '';
           $scope.zoneData = $scope.Original_zoneData;
       };
       
       $scope.searchBy = function(searchText, searchType){
           if(searchText && angular.isObject(searchType)){             
               $scope.searchIsEmpty = false;
               var employeeId = searchText;
               var dataObj = {
                   eFmFmClientBranchPO:{branchId:branchId},
                   employeeId:employeeId, //216346 
                   searchType:searchType.text
                 }; 
                  $http.post('services/zones/employeesearchinroute/',dataObj).
                     success(function(data, status, headers, config) {
                      if(data.routeDetails.length==0){
                          $scope.showalertMessage("This search type does not exist in routes please check entered correct value", '');                                         
                      }
                      else{
                          $scope.serachResultFound = true;
                          $scope.zoneData = data.routeDetails;  
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
       
       $scope.searchByTripTypeShift = function(search){ 
           if(search.shiftTime && search.tripType){
 //              alert(search.routeStatus.value+search.tripType.value+search.shiftTime.shiftTime+search.routeStatus.value);
               console.log(search);           
               var dataObj = {
                 eFmFmClientBranchPO:{branchId:branchId},
                 tripType:search.tripType.value, 
                 time:search.shiftTime.shiftTime+':00', //'2:10:00'
                 searchType:search.routeStatus.value                
                }; 
                $http.post('services/zones/triptypesearchinroute/',dataObj).
                   success(function(data, status, headers, config) {
    //                alert(JSON.stringify(data));
                      if(data.routeDetails.length==0){
                        $scope.showalertMessage("Route not Exist for this selection", '');                                          
                    }
                    else{
                        $scope.serachResultFound = true;
                        $scope.zoneData = data.routeDetails;
                    }
                   }).
                   error(function(data, status, headers, config) {
                          // log error
                   });
           }           
       };
       
       $scope.setSearchTripType = function(tripType){
           if(angular.isObject(tripType)){                             
                   var data = {
                       efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                       eFmFmEmployeeRequestMaster:{efmFmUserMaster:{userId:profileId}},
                       tripType:tripType.value
                          }; 
                   $http.post('services/trip/tripshiftime/',data).
                        success(function(data, status, headers, config) {
                            $scope.shiftsTime = data.shift; 
                            $scope.search.shiftTime = $scope.shiftsTime[0];

                        }).
                        error(function(data, status, headers, config) {});
           }
           else{
                $scope.shiftsTime = [];
           }
       };
       
       
       $scope.changeRoutesDropDown = function(employee, moveToZone){
           
           employee.isZoneclicked = true;
                   angular.forEach($scope.routesData, function(item){ 
                            angular.forEach(item.empDetails, function(item){                                
                                item.isZoneclicked = false;                        
                            });
                   });  
           employee.isZoneclicked = true;
           
           $scope.routesDropDown = [];
           if(moveToZone){
//               alert(moveToZone.routeId);
           
               var dataObj = {
                       eFmFmClientBranchPO:{branchId:branchId},
                       eFmFmRouteAreaMapping:{eFmFmZoneMaster:{zoneId:moveToZone.routeId}}
                    };	
               $http.post('services/zones/allroutes/',dataObj).
                    success(function(data, status, headers, config) {
                            $scope.routesDropDown = data.routeDetails;
 //                           alert(JSON.stringify($scope.routesDropDown));
                    }).
                    error(function(data, status, headers, config) {
                              // log error
                    });
           }
           else {
               $scope.routesDropDown = [];
               $scope.showalertMessage('Please Select a Zone', '');
           }           
       }
       
       $scope.openMap = function(route, zone){
           console.log(route);
           console.log(zone);
           $state.go('home.serviceRouteMap', {'routeId' : route.routeId, 'waypoints': route.waypoints, 'baseLatLong':route.baseLatLong});
       };  
       

       $scope.moveToRoute = function(moveTo, moveToZone, route, zone, employee, index){
//           alert(route.bucketStatus);
           var rowId = '#row'+zone.routeId + route.routeId + employee.employeeId + employee.requestId;
           if(!moveTo){
//               alert('Please Select');
           }
           else{
//               if (confirm("Are you sure you want to move this employee from " + zone.routeName + " to " + moveTo.routeName + " ?") == true) { 
               $confirm({text: "Are you sure you want to move this employee from " + zone.routeName + " to " + moveTo.routeName + " ?", title: 'Delete Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() { 
               //Checking if Routes are moving with in the Same Zone
               if(moveToZone.routeName === route.routeName){
                   // Check if the User has selected the Same route as the Current one
                   if(moveTo.routeId != route.routeId){ 
                       var moveFromIndex = _.findIndex($scope.routesData, { routeId: route.routeId });
                       var moveToIndex = _.findIndex($scope.routesData, { routeId: moveTo.routeId });
                       
                       if($scope.routesData[moveToIndex].bucketStatus === 'Y'){
                           $scope.showalertMessage('This bucket ' + moveTo.routeId + ' is already Closed. Please first click on edit bucket.', '');
                       }
                       else{
                           if($scope.routesData[moveToIndex].vehicleAvailableCapacity>0 && route.shiftTime==moveTo.shiftTime && route.tripType==moveTo.tripType){ 
                               console.log('Swapping in Same Zone :: dataObj : ' + 
                                           branchId + ' - ' + 
                                           route.assignRouteId + ' - ' + 
                                           moveTo.assignRouteId + ' - ' + 
                                           employee.requestId);
                               var dataObj = {
                            		   eFmFmClientBranchPO:{branchId:branchId},
                    				   assignRouteId:route.assignRouteId,
                    				   selectedAssignRouteId:moveTo.assignRouteId,
                    				   requestId:employee.requestId                    				   
                    			};	
                                $http.post('services/zones/swapemployee/',dataObj).
                    			success(function(data, status, headers, config) {
                                    console.log(data);
                               		if(data.status=='failed'){
                      					$scope.showalertMessage("Sorry you can not swap this emplyee in selected zone", "");
                      				}
                      				else{                                        
                                         $scope.routesData[moveToIndex].empDetails.push({
                                            "tripType": $scope.routesData[moveFromIndex].empDetails[index].tripType,
                                            "address": $scope.routesData[moveFromIndex].empDetails[index].address,
                                            "gender": $scope.routesData[moveFromIndex].empDetails[index].gender,
                                            "name": $scope.routesData[moveFromIndex].empDetails[index].name,
                                            "employeeId": $scope.routesData[moveFromIndex].empDetails[index].employeeId,
                                            "employeeNumber": $scope.routesData[moveFromIndex].empDetails[index].employeeNumber});
                                        console.log('rowId ' + rowId);
                                        $(rowId).hide('slow');
                                        
                                        route.vehicleAvailableCapacity = data.currentRouteAvailableCapacity;
                                        $scope.routesData[moveToIndex].vehicleAvailableCapacity = data.selectedRouteAvailableCapacity;
                                        $scope.moveToZone = '';            
                                        
                                        if(route.capacity - route.vehicleAvailableCapacity <= 1){
                                             route.isVehicleEmpty = true;
                                         }
                                         route.bucketStatus = 'N';
                      			         $scope.showalertMessage("Employee swapped successfully, please close the bucket.", "");
                                         // Get the Routes and Employee Details to render the correct Oder
                                         $scope.getRoutes(zone);
                                    }
                    			}).
                    			error(function(data, status, headers, config) {
                    				      // log error
                    			});                                 
                        }
                        else $scope.showalertMessage('Sorry you can not move this employee in to the selected route', ''); 
                      }
                   }
                   else{
                       $scope.showalertMessage('Please change the route you can not move the employee in the same Route', '');                         
                   }
                }           
               else{  
                   var moveFromIndex = _.findIndex($scope.routesData, { routeId: route.routeId });
                   var routeIndex = _.findIndex($scope.routesDropDown, { routeId: moveTo.routeId });
                   if(routeIndex>=0){
                       if($scope.routesDropDown[routeIndex].bucketStatus =='Y'){

                           $scope.showalertMessage('This bucket ' + moveTo.routeName + ' is already Closed. Please first click on edit bucket.', '');
                       }
                       else{
                           if($scope.routesDropDown[routeIndex].vehicleAvailableCapacity > 0 && route.shiftTime==moveTo.shiftTime && route.tripType==moveTo.tripType){
                               //Service Call here
                               console.log('Swapping in Different Zone :: dataOnj : ' + 
                                           branchId + ' - ' + 
                                           route.assignRouteId + ' - ' + 
                                           moveTo.assignRouteId + ' - ' + 
                                           employee.requestId);
                               var dataObj = {
                            		   eFmFmClientBranchPO:{branchId:branchId},
                    				   assignRouteId:route.assignRouteId,
                    				   selectedAssignRouteId:moveTo.assignRouteId,
                    				   requestId:employee.requestId
                    				   
                    			};	
                                $http.post('services/zones/swapemployee/',dataObj).
                    			success(function(data, status, headers, config) {                                    
                                     console.log(data);
                    				if(data.status=='failed'){
                    					$scope.showalertMessage("Sorry you can not swap this emplyee in selected zone", "");
                    				}
                    				else{
                               		 $(rowId).hide('slow');
                                     $scope.routesData[moveFromIndex].vehicleAvailableCapacity = data.currentRouteAvailableCapacity;
                                        
                                     if(route.capacity - route.vehicleAvailableCapacity <= 1){
                                        route.isVehicleEmpty = true;
                                      }
                                      // Get the Routes and Employee Details to render the correct Oder
                                     $scope.getRoutes(zone);
                                     route.bucketStatus = 'N';
//                                        alert(route.bucketStatus);
                    			     $scope.showalertMessage("Employee swapped successfully, please close the bucket.", "");
//                                        alert('Second : ' + route.bucketStatus);

                    				}
                                    $scope.moveToZone = '';
                                    
                    			}).
                    			error(function(data, status, headers, config) {
                    				      // log error
                    			});                                
                           }
                           else $scope.showalertMessage('Sorry you can not move this employee in to the selected route', '');   
                       }                       
                   }                   
               }
              });
           }
       };
       
       $scope.closeBucket = function(route, zone){ 
    	   var dummyVehicleNumberCheck=route.vehicleNumber;
    	   if(dummyVehicleNumberCheck.search("DUMMY") != -1) {
    		   $scope.showalertMessage("Sorry this dummy vehicle please first update the dummy entities", "");
    		   return false;
    	   } 
           var x = _.findIndex($scope.routesData, { routeId: route.routeId });
           //PASS 'reArrangeEmployeeArray'
           var reArrangeEmployeeArray = $scope.routesData[x].empDetails;
           
           route.closeBucketClicked = true;
           route.editClicked = false;
           var dataObj = {
        		   eFmFmClientBranchPO:{branchId:branchId},
				   assignRouteId:route.routeId
			};	
            $http.post('services/zones/bucketclose/',dataObj).
			success(function(data, status, headers, config) {
                if(data.status == 'notClose' && data.type == 'DROP'){
    			    $scope.showalertMessage("Sorry you can not close this bucket because last drop is female and escort is mandatory.", "");
                }
                else if(data.status == 'notClose' && data.type == 'PICKUP'){
    			    $scope.showalertMessage("Sorry you can not close this bucket because first pickup is female and escort is mandatory.", "");
                }
                else{
                route.bucketStatus = 'Y';
                route.escortName = data.escortName;
                $scope.routesData[x].vehicleAvailableCapacity = data.availableCapacity;
			    $scope.showalertMessage("Bucket close successfully", "");
                }
			}).
			error(function(data, status, headers, config) {
				      // log error
			});        
       };
       
       $scope.manualTripStart = function(route, zone){
    	   var dummyVehicleNumberCheck=route.vehicleNumber;
    	   if(dummyVehicleNumberCheck.search("DUMMY") != -1) {
    		   $scope.showalertMessage("Sorry this dummy vehicle please first update the vehicle and driver to start the trip", "");
    		   return false;
    	   } 
           var modalInstance = $modal.open({
            templateUrl: 'partials/modals/manualTripStart.jsp',
            controller: 'manualtripstartCtrl',
            backdrop:'static',
            resolve: {
                zone: function(){return zone;},
                route: function(){return route;}
            }
          });
		   
		   modalInstance.result.then(function(result){
                route.tripStatus = result;
           });
       };      
       
       $scope.manualTripEnd = function(route, zone){
           var modalInstance = $modal.open({
            templateUrl: 'partials/modals/manualTripEnd.jsp',
            controller: 'manualtripstartCtrl', 
            backdrop:'static',
            resolve: {
                zone: function(){return zone;},
                route: function(){return route;}
            }
          });
		   
		   modalInstance.result.then(function(result){
                route.tripStatus = result;
             //  alert(result);
               if(route.tripStatus == 'Completed'){
                   $(".route"+zone.routeId+route.routeId).hide('slow');
               }
           });
       };       
       
       $scope.editBucket = function(route, zone, size){
           if(route.bucketStatus == 'Y'){
               route.bucketStatus = 'N';
           }
           else route.bucketStatus == 'Y';
           
           route.editClicked = true;
           var modalInstance = $modal.open({
            templateUrl: 'partials/modals/editBucketModal.jsp',
            controller: 'editBucketCtrl',
            backdrop:'static',
            size: size,
            resolve: {
                zone: function(){return zone;},
                route: function(){return route;}
            }
          });

          modalInstance.result.then(function(result){ 
//              alert(route.escortName);
              console.log('**In the modalInstance**');
              console.log(result);
              var index = _.findIndex($scope.routesData, { routeId: route.routeId });
              if(route.escortRequired != 'N' &&  route.escortName !== 'Escort Required But Not Available'){
//                      alert("hiii");
                      $scope.routesData[index].escortName = result.escortName.escortName;
                  
              }
              $scope.routesData[index].deviceNumber = result.deviceId.deviceId;
              $scope.routesData[index].vehicleNumber = result.vehicleNumber.vehicleNumber;
              $scope.routesData[index].driverNumber = result.driverNumber;
              $scope.routesData[index].driverName = result.driverName.driverName;
              $scope.routesData[index].vehicleAvailableCapacity = result.availableCapacity;
              $scope.routesData[index].checkInId = result.checkInId;
          });
       };
       
       $scope.deleteEmployee = function(route, zone, employee, employeeIndex){
           var routeIndex = _.findIndex($scope.routesData, { routeId: route.routeId });
//           if (confirm("Are you sure you want to delete this row?") == true) {       
           $confirm({text: 'Are you sure you want to delete this row?', title: 'Delete Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {  
               var dataObj = {
            		   branchId:branchId,
                       eFmFmEmployeeTravelRequest:{requestId:employee.requestId},
                       efmFmAssignRoute:{assignRouteId:route.routeId}
                };	
                $http.post('services/zones/cancelbucketrequest/',dataObj).
                success(function(data, status, headers, config) {                    
                    $timeout(function () {
             //           alert('#row'+zone.routeId+route.routeId+employee.employeeId+employee.requestId);
                        $('#row'+zone.routeId+route.routeId+employee.employeeId+employee.requestId).hide('slow');
                        route.bucketStatus = 'N';
                        route.escortName = data.escortName;

//                        $scope.routesData[routeIndex].empDetails.splice(employeeIndex,1);
//                        alert($scope.routesData[routeIndex].empDetails.length);
                        $scope.showalertMessage("Request deleted successfully, please close the bucket.", "");
                        $scope.routesData[routeIndex].vehicleAvailableCapacity = data.availableCapacity;
                        
                        if(route.capacity - route.vehicleAvailableCapacity <= 1){
                                 route.isVehicleEmpty = true;
                         }
                     });                    
                }).
                error(function(data, status, headers, config) {
                          // log error
                });               
            });         
       };
       
      $scope.moveDown = function(route, zone, employee, index){
          var x = _.findIndex($scope.routesData, { routeId: route.routeId });
          route.closeBucketClicked = false;
          var a = zone.routeId+route.routeId+employee.employeeId;
          var row = $('#down'+a).parents("tr:first");
          row.insertAfter(row.next());
          if(index!=$scope.routesData[x].empDetails.length-1){
              var temp = $scope.routesData[x].empDetails[index];
              $scope.routesData[x].empDetails[index] = $scope.routesData[x].empDetails[index+1];
              $scope.routesData[x].empDetails[index+1] = temp;
              for(i=0;i<$scope.routesData[x].empDetails.length;i++){
                  console.log($scope.routesData[x].empDetails[i].name);
              }
              console.log("END");
          }
          
      };
       
      $scope.moveUp = function(route, zone, employee, index){
          var x = _.findIndex($scope.routesData, { routeId: route.routeId });
          route.closeBucketClicked = false;
           var a = zone.routeId+route.routeId+employee.employeeId;
           var row = $('#up'+a).parents("tr:first");
           row.insertBefore(row.prev());   
          
          if(index!=0){
              var temp = $scope.routesData[x].empDetails[index];
              $scope.routesData[x].empDetails[index] = $scope.routesData[x].empDetails[index-1];
              $scope.routesData[x].empDetails[index-1] = temp;
              for(i=0;i<$scope.routesData[x].empDetails.length;i++){
                  console.log($scope.routesData[x].empDetails[i].name);
              }
              console.log("END");
          }
      }; 
       
      $scope.deleteEmptyRoute = function(route, zone){
//          if (confirm("Are you sure you want to delete this Route?") == true) {
          $confirm({text: 'Are you sure you want to delete this Route?', title: 'Delete Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {  
              var data = {
                    eFmFmClientBranchPO:{branchId:branchId},
                    assignRouteId:route.routeId
                 };
                 $http.post('services/zones/deleteroute/',data).
                      success(function(data, status, headers, config) {
                          if(data.status=='success'){
                              $scope.showalertMessage("Route deleted successfully");
                              $('.route'+ zone.routeId+route.routeId).hide('slow'); 
                          }
//                          else{
//                              alert("You can not delete this route");
//                          }
                 }).
                    error(function(data, status, headers, config) {
                                  // log error
                });
          });
      };
      $scope.searchEmployeesInZone = function(search){
//          $(".collapseZoneDiv88").css({"background":"#5bc0de"});
//          $("#collapse88").addClass("in");
//          $("#collapse88").attr("aria-expanded","true");
      };
     
     //Controller: importData.js
     $scope.uploadRoute = function(){         
          var modalInstance = $modal.open({
            templateUrl: 'partials/modals/uploadRoute.jsp',
            controller: 'uploadRouteCtrl',
            backdrop:'static',
            resolve: {
                
            }
          });

          modalInstance.result.then(function(result){ });
         
     };
     
     //Controller: importData.js  
     $scope.exportRoute = function(){         
          var modalInstance = $modal.open({
            templateUrl: 'partials/modals/exportRoute.jsp',
            controller: 'exportRouteCtrl',
            backdrop:'static',
            resolve: {
                
            }
          });

          modalInstance.result.then(function(result){ });
         
     };
     
     //Controller: importData.js  
     $scope.downloadRoute = function(){         
          var modalInstance = $modal.open({
            templateUrl: 'partials/modals/downloadRoute.jsp',
            controller: 'downloadRouteCtrl',
            backdrop:'static',
            resolve: {
                
            }
          });

          modalInstance.result.then(function(result){ });
         
     };
       
    $scope.updateDropSeq = function(employee, route, index, parentIndex){
        if(employee.isUpdateClicked){
            alert(employee.dropSequence);            
            $confirm({text: "Are you sure you want to change the drop sequence from " + employee.pickUpTime + " to " + employee.dropSequence + " ?", title:     'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {
            employee.pickUpTime = employee.dropSequence;
            employee.isUpdateClicked = false;
          });            
        }
        else{
            employee.isUpdateClicked = true
        }
        
    };
       
    $scope.updatePickupTime = function(employee, route, index, parentIndex){
        if(employee.isUpdateClicked){
            alert("Timer time: " + $scope.convertToTime(employee.createNewAdHocTime)+":00");
            alert("Route ID: " + route.routeId)
            alert(JSON.stringify(employee));
            $confirm({text: "Are you sure you want to change the pickup time from " + employee.pickUpTime + " to " +                                              $scope.convertToTime(employee.createNewAdHocTime)+":00 ?", title: 'Confirmation', ok: 'Yes', cancel: 'No'})
        .then(function() {
            employee.pickUpTime = $scope.convertToTime(employee.createNewAdHocTime)+":00";
            employee.isUpdateClicked = false;
            });
        }
        else{
            employee.isUpdateClicked = true
        }
    };
     
}; 
    
var creatNewRouteCtrl = function($scope, $modalInstance, $state, $http, $timeout, uiZone){  
       $scope.shiftsTime = [];
       $scope.newRoute = {};       
       var tripTimeSelected;
       $scope.typeOfShiftTimeSelected;
       $scope.hstep = 1;
       $scope.mstep = 5;
       $scope.ismeridian = false;
       $scope.tripTypes = [{'value':'PICKUP', 'text':'PICKUP'},
	    	 	           {'value':'DROP', 'text':'DROP'}];
    
//       $scope.pickupShiftsTime = [{'shiftTime': '05:30' },
//                                    {'shiftTime': '07:00'},
//                                    {'shiftTime': '12:00'},
//                                    {'shiftTime': '14:00'},
//                                    {'shiftTime': '16:00'},
//                                    {'shiftTime': '18:00'}];
//    
//       $scope.dropShiftsTime = [{'shiftTime': '14:30' },
//                                    {'shiftTime': '16:00'},
//                                    {'shiftTime': '21:00'},
//                                    {'shiftTime': '23:00'},
//                                    {'shiftTime': '01:00'},
//                                    {'shiftTime': '03:00'}];
      //Initialize TimePicker to 00:00
        var timePickerInitialize = function(){
           var d = new Date();
           d.setHours( 00 );
           d.setMinutes( 0 );
           $scope.newRoute.createNewAdHocTime = d;
       }
        
       var data = {
			eFmFmClientBranchPO:{branchId:branchId}
       };
       $http.post('services/zones/createbucket/',data).
            success(function(data, status, headers, config) {
                $scope.allZonesData = data.routesData;
                $scope.shiftsTime = [{'shiftTime': 'Select Shift Time'}];
                $scope.newRoute.shiftTime = $scope.shiftsTime[0];  
                $('.btn-link').addClass('noPointer');
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
                            $scope.newRoute.shiftTime = $scope.shiftsTime[0];

                        }).
                        error(function(data, status, headers, config) {});
            } 
           else {
               $scope.shiftsTime = '';
           }           
            $scope.newRoute.shiftTime = $scope.shiftsTime[0];
       };
    
       $scope.createNew = function(newRoute){ 
           
        if($scope.typeOfShiftTimeSelected == 'preDefineShiftTime'){
            tripTimeSelected = newRoute.shiftTime.shiftTime; 
        }
        else{
            var fullDate = new Date(newRoute.createNewAdHocTime);
            var time = fullDate.getHours()+':'+fullDate.getMinutes()+':00';
            tripTimeSelected = time;
        }
           var dataObj = {
       			eFmFmClientBranchPO:{branchId:branchId},
       			time:tripTimeSelected, //newRoute.shiftTime.shiftTime,
       			selectedAssignRouteId:newRoute.zone.routeId,  
       			tripType:newRoute.tripType.value
            };
           console.log(dataObj);
              $http.post('services/zones/savecreatebucket/',dataObj).
                   success(function(data, status, headers, config) {
                       console.log(data);
                	   if(data.status=='fail'){
   						  $scope.showalertMessageModal("No driver available please checked in driver first", "");
   			              $timeout(function() {$modalInstance.dismiss('cancel')}, 3000);
   					}
   					else{
                        $scope.resultData = data;
                        console.log($scope.resultData);
                        $scope.resultData.zoneRouteId = newRoute.zone.routeId;
                        $scope.resultData.zoneName = newRoute.zone.routeName;
           				$scope.showalertMessageModal("Empty route created successfully", "");
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
    
var manualtripstartCtrl = function($scope, $modalInstance, $state, $http, $timeout,route, zone){
    $scope.regexDecimalNumbers = /^[0-9]+(\.[0-9]{2})?$/;
    $scope.tripStatus = route.tripStatus;
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
    
    //Convert to hh:mm
    var convertToTime = function(newdate){
       d = new Date(newdate);
       hr = d.getHours();
       min = d.getMinutes();
       if(hr<10){hr = '0'+hr;} 
       if(min<10){min = '0'+min;}           
       console.log("TIME :: " + hr+":"+min);
       return hr+":"+min;
    };
    
    var currentDate = convertDateUTC(new Date()) + ' ' + convertToTime(new Date());    
	$scope.enterStartKm = function(result){
		if(route.tripStatus=="allocated"){
        var dataObj = {
     		   eFmFmClientBranchPO:{branchId:branchId},
				   assignRouteId:route.routeId,
				   odometerStartKm:result.kilometer,
                   time:currentDate
			};	
         $http.post('services/trip/tripstartkm/',dataObj).
			success(function(data, status, headers, config) {
			 $scope.showalertMessageModal("Trip Started", "");
             route.tripStatus="Started";
             $timeout(function() {$modalInstance.close(route.tripStatus)}, 3000);
			}).
			error(function(data, status, headers, config) {
				      // log error
			});  
         }
         else{
        	    var dataObj = {
        	     		   eFmFmClientBranchPO:{branchId:branchId},
        					   assignRouteId:route.routeId,
        					   odometerEndKm:result.kilometer,
                               time:currentDate
        				};	
        	         $http.post('services/trip/tripcomplete/',dataObj).
        				success(function(data, status, headers, config) {
        					if(data.status=='fail'){
        						$scope.showalertMessageModal("Please enter correct Odometer reading", "");
        					}
        					else{
                                route.tripStatus="Completed";
                				$scope.showalertMessageModal("Trip Completed ", "");
                                $timeout(function() {$modalInstance.close(route.tripStatus)}, 3000);
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

var editBucketCtrl = function($scope, $modalInstance, $state, $http, $timeout, zone, route, $rootScope){ 
 //       alert(route.checkInId);
        console.log(route);
        $scope.delay = 0;$scope.manualStartForm;
        $scope.minDuration = 0;
        $scope.templateUrl = 'bower_components/ngSpinner/custom-template.html';
        $scope.message = 'Please Wait...';
        $scope.backdrop = true;
        $scope.promise = null;
        $scope.alertMessage;
        $scope.alertHint; 
        var okIsClicked = false;
        var routeID = route.routeId;
        var isEscortNameChange = false;
        var escortCheckInId;
        var escortId;
        var deviceId;
        var driverId;
        var vehicleId;
        var vendorId;
        $scope.IntegerNumber = /^\d+$/;
        $scope.regExName = /^[A-Za-z]+$/;
        $scope.NoSpecialCharacters = /^[a-zA-Z0-9]*$/;
        $scope.routes = route;
        $scope.checkInEntitiesData = [];
        var selectedCheckInIdIndex;
        var currentCheckInId = route.checkInId; 
        var newCheckInId;
        var currentEntityIndex;
        $scope.escortRequired = route.escortRequired;
        $scope.escortNameFlag = route.escortName;
    
        var data = {
				   eFmFmClientBranchPO:{branchId:branchId},
				   assignRouteId:route.routeId
			};
		   $http.post('services/zones/checkedinentity/',data).
				    success(function(data, status, headers, config) {
//				    	alert(JSON.stringify(data));
                      console.log(data);
                      $scope.checkInEntitiesData = data.checkInList;
                      $scope.escortsData = data.escortDetails;
//               alert("currentCheckInId" + currentCheckInId);
                      currentEntityIndex = _.findIndex($scope.checkInEntitiesData, { checkInId: currentCheckInId });
//               alert(currentEntityIndex);
                      if($scope.escortRequired && route.escortName != 'Escort Required But Not Available'){                              
                          currentEscortIndex = _.findIndex($scope.escortsData, { escortName: route.escortName });                          
                          $scope.edit ={driverName:$scope.checkInEntitiesData[currentEntityIndex],
                                        driverNumber: $scope.checkInEntitiesData[currentEntityIndex].mobileNumber,
                                        vehicleNumber:$scope.checkInEntitiesData[currentEntityIndex], 
                                        deviceId: $scope.checkInEntitiesData[currentEntityIndex],
                                        escortName: $scope.escortsData[currentEscortIndex]};
                          
                      }
                      else{
                          $scope.edit ={driverName:$scope.checkInEntitiesData[currentEntityIndex],
                                        driverNumber: $scope.checkInEntitiesData[currentEntityIndex].mobileNumber,
                                      vehicleNumber:$scope.checkInEntitiesData[currentEntityIndex], 
                                      deviceId: $scope.checkInEntitiesData[currentEntityIndex]};
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
               console.log("DRIVER DATA");
               console.log(driver);
               newCheckInId = driver.checkInId;
//               alert(newCheckInId);
               selectedCheckInIdIndex = _.findIndex($scope.checkInEntitiesData, { checkInId: driver.checkInId });
               $scope.edit.driverName  = $scope.checkInEntitiesData[selectedCheckInIdIndex];
               $scope.edit.driverNumber  = $scope.checkInEntitiesData[selectedCheckInIdIndex].mobileNumber;
               $scope.edit.vehicleNumber = $scope.checkInEntitiesData[selectedCheckInIdIndex];
               $scope.edit.deviceId = $scope.checkInEntitiesData[selectedCheckInIdIndex];
           }
       }; 

       $scope.updateVehicleNumberManual = function(vehicle){
           if(angular.isObject(vehicle)){
               console.log("VEHICLE DATA");
               console.log(vehicle);
               newCheckInId = vehicle.checkInId;
     //          alert(newCheckInId);
               selectedCheckInIdIndex = _.findIndex($scope.checkInEntitiesData, { checkInId: vehicle.checkInId });
     //          selectedCheckInIdIndex = _.findIndex($scope.checkInEntitiesData, { checkInId: driver.checkInId });
               $scope.edit.driverName  = $scope.checkInEntitiesData[selectedCheckInIdIndex];
               $scope.edit.driverNumber  = $scope.checkInEntitiesData[selectedCheckInIdIndex].mobileNumber;
               $scope.edit.vehicleNumber = $scope.checkInEntitiesData[selectedCheckInIdIndex];
               $scope.edit.deviceId = $scope.checkInEntitiesData[selectedCheckInIdIndex];
           }
       };

       $scope.updateDeviceIdManual = function(device){
           if(angular.isObject(device)){
               selectedCheckInIdIndex = _.findIndex($scope.checkInEntitiesData, { checkInId: device.checkInId });
     //          selectedCheckInIdIndex = _.findIndex($scope.checkInEntitiesData, { checkInId: driver.checkInId });
               $scope.edit.driverName  = $scope.checkInEntitiesData[selectedCheckInIdIndex];
               $scope.edit.driverNumber  = $scope.checkInEntitiesData[selectedCheckInIdIndex].mobileNumber;
               $scope.edit.vehicleNumber = $scope.checkInEntitiesData[selectedCheckInIdIndex];
               $scope.edit.deviceId = $scope.checkInEntitiesData[selectedCheckInIdIndex];  
           }
       }; 

       $scope.updateEscortManual = function(escort){
           if(angular.isObject(escort)){
                escortCheckInId= escort.escortCheckInId;
           }
       }; 
    
       $scope.save = function (updatedValue) { 
           if($scope.escortRequired == 'N' || route.escortName == 'Escort Required But Not Available'){
               escortCheckInId =0;
               isEscortNameChange = false;
           }
           else{
                if($scope.edit.escortName.escortName != route.escortName){
                    isEscortNameChange = true;
                }
                else{
                    isEscortNameChange = false;
                }            
           }
           //add newCheckInId
//           alert("newCheckInId"+newCheckInId);
           if($scope.edit.driverName.checkInId !== currentCheckInId || isEscortNameChange){ 
               var data = {
                       eFmFmClientBranchPO:{branchId:branchId},
                           assignRouteId:route.routeId,
                           vehicleId:$scope.edit.vehicleNumber.vehicleId,
                           driverId:$scope.edit.driverName.driverId,
                           deviceId:$scope.edit.deviceId.deviceId,
                           escortCheckInId:escortCheckInId,
                           newCheckInId:newCheckInId
                    };	
               console.log(data);
               $scope.promise = $http.post('services/zones/saveeditbucket/',data).
                        success(function(data, status, headers, config) {
                   console.log(data);
                        	if(data.status=='lessCapacity'){
                        		$scope.showalertMessageModal("Sorry please change the vehicle heaving  capacity more than "+route.vehicleAvailableCapacity+".", ""); 
                        	}
                        	else{
                            updatedValue.availableCapacity = data.availableCapacity;
                            updatedValue.status = data.status;
                            updatedValue.checkInId = newCheckInId;
                            $scope.showalertMessageModal("Route Details Change Successfully.", ""); 
                            $timeout(function() {$modalInstance.close(updatedValue)}, 3000); 
                        	}
                        }).
                        error(function(data, status, headers, config) {
                          // log error
                        });
           }
           else {
               $scope.showalertMessageModal('None of the Entity has been Change', ''); 
               $timeout(function() {$modalInstance.dismiss('cancel')}, 3000); }  
         };   
    
       $scope.cancel = function(){
            $modalInstance.dismiss('cancel');
       }; 
 };
    
    angular.module('efmfmApp').controller('creatNewRouteCtrl', creatNewRouteCtrl);    
    angular.module('efmfmApp').controller('editBucketCtrl', editBucketCtrl);
    angular.module('efmfmApp').controller('manualtripstartCtrl', manualtripstartCtrl);

    angular.module('efmfmApp').controller('serviceMappingCtrl', serviceMappingCtrl);
}());