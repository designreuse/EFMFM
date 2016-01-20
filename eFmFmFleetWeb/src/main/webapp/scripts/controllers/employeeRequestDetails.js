(function(){
   var requestDetailsCtrl = function($scope, $timeout, $http, $modal){
       $('.employeeRequest').addClass('active');
       $scope.tripTimes = [];
       $scope.selectedNewTripTime;
       $scope.needAdHoc = false;
       $scope.hstep = 1;
       $scope.mstep = 5;
       $scope.ismeridian = false;
       $scope.adHoctime;
       $scope.saveIsClicked = false;
       $scope.createNewRequest = {};
       $scope.tripTypes = [{'value':'PICKUP', 'text':'PICKUP'},
	    	 	           {'value':'DROP', 'text':'DROP'}];
       $scope.requestTypes = [{'value':'SELF', 'text':'SELF'},
	    	 	           {'value':'EMPLOYEE', 'text':'EMPLOYEE'},
	    	 	           {'value':'GUEST', 'text':'GUEST'}];
       $scope.areaCodes = [{'value':'91', 'text':'IND - 91'},
	    	 	           {'value':'1', 'text':'USA - 1'},
	    	 	           {'value':'44', 'text':'UK - 44'}];
       $scope.genders = [{'value':'Male', 'text':'Male'},
                         {'value':'Female', 'text':'Female'}];
       $scope.newShiftTypeSelected;
       var newShiftTimeSelected;
       $scope.setMinDate = new Date();
       $scope.isShiftTimeDisable = true;
       $scope.isFromDateEntered = true;
       $scope.isTripTypeSelected = true;
       $scope.isLocationEntered = false;
       $scope.isLocationEntered2 = false;
       $scope.validFormFlag = true;
       $scope.createRequestRole_ADMIN = true;
       $scope.createRequestRole_EMPLOYEE = false;                 
       $scope.createRequestRole_GUEST = false;
       $scope.createRequestIsClicked = false;
       $scope.IntegerNumber = /^\d+$/;
       $scope.regExName = /^[A-Za-z]+$/;
       $scope.NoSpecialCharacters = /^[a-zA-Z0-9]*$/;
       $scope.requestFor;
       $scope.is24hrRequest =  true;
       $scope.newRequest = [];
      // $scope.newRequest.tripType = {'value':'DROP', 'text':'DROP'};
       
       $scope.$on('$viewContentLoaded', function() { 
		   $scope.getTodayRequestDetails();           
           $scope.adHoctime = $scope.initializeTime();
           $scope.initialzeNewCustomTimeByAdmin();
           $scope.getShiftTime('DROP');
		}); 
       
       //FUNCTION : Get Shift Time to Populate the drop Down in Create Request
       $scope.getShiftTime = function(tripType){
           $scope.tripTimeData = [];
           $scope.tripTimes = [];
//    	   var data = {
//        		 efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
//        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{userId:profileId}}
//              };	
//           $http.post('services/trip/shiftime/',data).
//                success(function(data, status, headers, config) {           
          
              // alert(tripType);
                   var data = {
                       efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                       eFmFmEmployeeRequestMaster:{efmFmUserMaster:{userId:profileId}},
                       tripType:tripType
                          }; 
                   $http.post('services/trip/tripshiftime/',data).
                        success(function(data, status, headers, config) {
                  $scope.tripTimeData = data.shift;  
                  angular.forEach($scope.tripTimeData, function(item){
                      console.log(item.shiftTime);
                      $scope.tripTimes.push(item.shiftTime);
                  });
                }).
                error(function(data, status, headers, config) {
                  // log error
                }); 
           
       };
       
       //FUNCTION : TODAY'S REQUEST LIST
       $scope.getTodayRequestDetails = function(){
         var data = {
        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
        		 efmFmUserMaster:{userId:profileId}       		 
              };	
           $http.post('services/trip/employeetodayrequest/',data).
                success(function(data, status, headers, config) {
                  $scope.empRequestDetailsData = data.requests; 
                  angular.forEach($scope.empRequestDetails, function(item) {
                      item.needReshedule=false;
                      item.cancel = false;
                  });
                }).
                error(function(data, status, headers, config) {
                  // log error
                });         
      };
       
       //FUNCTION : RE-SCHEDULE REQUEST LIST
       $scope.getRescheduleRequestDetails = function(){
           $scope.createRequestIsClicked = false;
           $scope.saveIsClicked = false;
         var data = {
        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
        		 efmFmUserMaster:{userId:profileId}       		 
              };	
         $http.post('services/trip/requestsforreshedule/',data).
                success(function(data, status, headers, config) {
                  $scope.empRequestDetailsData = data.requests; 
                  $scope.tripTimeData = data.shifts;
                  angular.forEach($scope.empRequestDetailsData, function(item) {
                      item.needReshedule=false;
                      item.cancel = false;
                  });
                }).
                error(function(data, status, headers, config) {
                  // log error
                });         
            };
       
       //FUNCTION : CANCEL REQUEST LIST
       $scope.getCancelRequestDetails = function(){
         var data = {
        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
        		 efmFmUserMaster:{userId:profileId}       		 
              };	
         $http.post('services/trip/requestsforcancel/',data).
                success(function(data, status, headers, config) {
                  $scope.empRequestDetailsData = data.requests; 
                  $scope.tripTimeData = data.shifts;  
                  angular.forEach($scope.tripTimeData, function(item){
                      console.log(item.shiftTime);
                      $scope.tripTimes.push(item.shiftTime);
                  });              
                  angular.forEach($scope.empRequestDetailsData, function(item) {
                      item.needReshedule=false;
                      item.cancel = false;
                  });
                }).
                error(function(data, status, headers, config) {
                  // log error
                });         
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
       
       //Convert to mm-dd-yyyy
       var dateFormatConverter = function(x){
            var date = x.split('-');
            return date[1]+'-'+date[0]+'-'+date[2];
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
       
       $scope.initializeTime = function(){
            var d = new Date();
            d.setHours( 00 );
            d.setMinutes( 0 );
            return d;           
       }

       $scope.openFromDateCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'fromDate' : true};  
             }, 50);
        };

       $scope.openEndDateCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'endDate' : true};  
             }, 50);
        };
       
       $scope.reSheduleRequest = function(request){ 
           $scope.fromDate = new Date (dateFormatConverter(request.requestDate));
           
//           $scope.fromDate = new Date('08-21-2015');
           $scope.newTripTime = $scope.tripTimes[1];
           if(request.needReshedule){
                request.needReshedule=false;
           }
           else {
                $scope.saveIsClicked = true;
               
               var i =  $scope.tripTimes.indexOf(request.tripTime); 
               request.needReshedule=true;
               $scope.newTripTime = $scope.tripTimes[i];
           }
       };
       
       $scope.validateRe_requestTime = function(requestTime, fromDate, tripType){
           var RequestedScheduleTime;
           var timeSelectedArray;
           var requestedDateInMS;
           var timeDiffInMins;
           var selectedTimeInMinutes;
           var validTimeHoursStarts;
           var seledtedFromDate = new Date(fromDate);
           
           var todaysDate = new Date();
           var todayInMS = todaysDate.getTime();
           console.log('todayInMS : ' + todayInMS);
           
           var today = convertDateUTC(todaysDate); 
           var requested = convertDateUTC(seledtedFromDate);
           console.log('today : ' + today);
           console.log('requested : ' + requested);
           
           if(tripType == 'DROP'){
               console.log('todaysDate : ' + todaysDate);
               console.log('todaysDate.getHours() : ' + todaysDate.getHours());
               validTimeHoursStarts = 2 + todaysDate.getHours();
               console.log('validTimeHoursStarts : ' + validTimeHoursStarts);
           }
           if(tripType == 'PICKUP'){
               validTimeHoursStarts = 6 + todaysDate.getHours();
               console.log('validTimeHoursStarts : ' + validTimeHoursStarts);
           }
           
           if(!$scope.needAdHoc){
                timeSelectedArray = requestTime.split(':');
                selectedTimeInMinutes = parseInt(timeSelectedArray[0]*60) + parseInt(timeSelectedArray[1]);
                seledtedFromDate.setHours(timeSelectedArray[0]);
                seledtedFromDate.setMinutes(timeSelectedArray[1]);
                console.log('seledtedFromDate : ' + seledtedFromDate);              
                requestedDateInMS = seledtedFromDate.getTime();               
                console.log('requestedDateInMS : ' + requestedDateInMS);               
                console.log('todayInMS : ' + todayInMS);
           }
           else if($scope.needAdHoc){
                var timePicker = new Date(requestTime)
                var hh = timePicker.getHours();
                var min = timePicker.getMinutes();
                selectedTimeInMinutes = hh*60 + min;
                seledtedFromDate.setHours(hh);
                seledtedFromDate.setMinutes(min);
                console.log('seledtedFromDate : ' + seledtedFromDate);              
                requestedDateInMS = seledtedFromDate.getTime();               
                console.log('requestedDateInMS : ' + requestedDateInMS);
           }
           
           timeDiffInMins = Math.floor((requestedDateInMS - todayInMS)/1000/60);
           console.log('timeDiffInMins : ' + timeDiffInMins);
           
           //Check the User Role
           if($scope.adminRole){
               //Check if its Request Date is equals to Todays Date              
              if(today == requested && (requestedDateInMS < todayInMS || validTimeHoursStarts>24)){
                 $scope.showalertMessage('This is not the Valid Time. Please Change the Time or the Date', "");
                 return false;
              }
              else{
                  if(tripType == 'PICKUP' && timeDiffInMins<5){
                     $scope.showalertMessage("Please select PickUp time 6 hours from now","");
                     return false;
                  }
                  if(tripType == 'DROP' && timeDiffInMins<5){
                     $scope.showalertMessage("Please select Drop time 2 hours from now", "");
                     return false;
                  }                      
             } 
           return true;
         }
         else if(!$scope.adminRole && $scope.needAdHoc){
 //            alert(selectedTimeInMinutes); 
             if(selectedTimeInMinutes>=180 && selectedTimeInMinutes<=300){
                 if(today == requested && (requestedDateInMS < todayInMS || validTimeHoursStarts>24)){
                     $scope.showalertMessage('This is not the Valid Time. Please Change the Time or the Date', '');
                     return false;
                  }
                  else{
                      if(tripType == 'PICKUP' && timeDiffInMins<360){
                         $scope.showalertMessage("Please select PickUp time 6 hours from now", '');
                         return false;
                      }
                      if(tripType == 'DROP' && timeDiffInMins<120){
                         $scope.showalertMessage("Please select Drop time 2 hours from now", '');
                         return false;
                      }                      
                 } 
                 
             }
             else{
                 $scope.showalertMessage('Please select AD-HOC time between 3:00 am to 5:00 am', '');
                 return false;
             }
            return true; 
         } 
         else if(!$scope.adminRole && !$scope.needAdHoc){
                 if((today == requested && requestedDateInMS < todayInMS) || validTimeHoursStarts>24){
                     $scope.showalertMessage('This is not the Valid Time. Please Change the Time or the Date', '');
                     return false;
                  }
                  else{
                      if(tripType == 'PICKUP' && timeDiffInMins<360){
                         $scope.showalertMessage("Please select PickUp time 6 hours from now", '');
                         return false;
                      }
                      if(tripType == 'DROP' && timeDiffInMins<120){
                         $scope.showalertMessage("Please select Drop time 2 hours from now", "");
                         return false;
                      }                      
                 }                 
            return true;
         }
       };
       
       
        $scope.saveRequest = function(request, fromDate, index){
//            alert(fromDate);
           if(request.needReshedule){               
                if(!$scope.needAdHoc){ 
                    if(!$scope.newTripTime){
                           $scope.validRescheduleRequest = false;
                       }
                    else{
                       $scope.selectedNewTripTime = $scope.newTripTime;
                       $scope.empRequestDetailsData[index].tripTime = $scope.newTripTime;
                       $scope.empRequestDetailsData[index].requestDate = convertDateUTC(fromDate);                
                       $scope.validRescheduleRequest = $scope.validateRe_requestTime($scope.newTripTime, fromDate, request.tripType);
                    }
                       
                       
                }
                else{ 
                       $scope.selectedNewTripTime = convertToTime($scope.adHoctime);
                       $scope.empRequestDetailsData[index].tripTime = convertToTime($scope.adHoctime);
                       $scope.empRequestDetailsData[index].requestDate = convertDateUTC(fromDate);                  
                       $scope.validRescheduleRequest =$scope.validateRe_requestTime($scope.adHoctime, fromDate, request.tripType);                                    
                }
               if(request.requestType=='normal'){
            	   $scope.showalertMessage("Sorry you can not reshedule this request please contact your transport", '');
                   $scope.saveIsClicked = false;
                   request.needReshedule=false;
            	   return false;
               }
               
               var data = {           		               		   
            		   efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                       time:$scope.selectedNewTripTime,
                       requestId:request.requestId,
                       resheduleDate:convertDateUTC(fromDate)
                };	
               
               if($scope.validRescheduleRequest){                
                    $http.post('services/trip/reshedulerequestfromweb/',data).
                        success(function(data, status, headers, config) {
                            $scope.showalertMessage("Your request has been reshedule successfully.", "");
                            $scope.saveIsClicked = false;
                            request.needReshedule=false;
                        }).
                        error(function(data, status, headers, config) {
                          // log error
                        });
               }
           }
           else {
               request.needReshedule=true;
           }
          $scope.needAdHoc = false;
       };
       
       $scope.cancelReschedule = function(request, index){ 
           angular.forEach($scope.empRequestDetailsData, function(item) {
               console.log('HHH : ' + item.needReshedule);
                 item.needReshedule=false;
           });
           $scope.saveIsClicked = false;
           $scope.needAdHoc = false;          
       };
       
       $scope.adHoc = function(){
           $scope.needAdHoc = true;
       };
       
       $scope.cancelAdHoc = function(){
            $scope.needAdHoc = false;        
       }
       
       $scope.setNewTripTime = function(newTime, request){
           if(!$scope.needAdHoc){ 
               $scope.newTripTime = newTime;                
           }
           else if($scope.needAdHoc){               
               $scope.adHoctime = newTime;
           }           
       };
       
       $scope.isFromDate = function(){
           $scope.isFromDateEntered = false;           
       };
       
       //Delete Request from the 'Cancel Request Tab'
       $scope.deleteRequest = function(request){
           var requestTime = request.tripTime.split(':');           
           var requestDate = new Date(dateFormatConverter(request.requestDate));
           requestDate.setHours(requestTime[0]);
           requestDate.setMinutes(requestTime[1]);
           var requestTimeInMS = requestDate.getTime();
           console.log('requestTimeInMS : ' + requestTimeInMS);           
           
           var todaysDate = new Date();
           var todayInMS = todaysDate.getTime();
           console.log('todayInMS : ' + todayInMS);
           
           var timeDifference = (requestTimeInMS - todayInMS)/1000/60;
           console.log('timeDifference : '+timeDifference);
           
           if(timeDifference<=5){
               $scope.showalertMessage('You can not Cancel this request 5 minutes before the shift Time', '');
           }
           else{          
               var data = {
                       efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                       requestId:request.requestId
                };	
             $http.post('services/trip/employeerequestdelete/',data).
                    success(function(data, status, headers, config) {
                        $scope.showalertMessage("Your request has been cancel successfully.", "");
                        $(".request"+request.requestId).hide();
                    }).
                    error(function(data, status, headers, config) {
                      // log error
                    });  
           }         
       };
       
       $scope.selectShiftTimeRadio = function(shiftTime){
           //if the User Role is ADMIN
           if($scope.adminRole){ 
               $scope.newShiftTypeSelected = shiftTime; 
               if($scope.newRequestByAdmin.fromDate){                   
                    $scope.isShiftTimeDisable = false;
               } 
               $('.btn-link').addClass('noPointer');
           }
           else {
               $scope.newShiftTypeSelected = shiftTime; 
               if($scope.newRequest.fromDate){                   
                    $scope.isShiftTimeDisable = false;
               } 
               $('.btn-link').addClass('noPointer');
           }
       };
       
       $scope.selectShiftTimeRadio2 = function(shiftTime){
//           alert(JSON.stringify(shiftTime));
           //if the User Role is ADMIN
           if($scope.adminRole){
//               alert("S");
               $scope.newShiftTypeSelected = shiftTime; 
               if($scope.newRequestByAdmin.fromDate){                   
                    $scope.isShiftTimeDisable = true;
               } 
               $('.btn-link').removeClass('noPointer');
           }
           else{
               $scope.newShiftTypeSelected = shiftTime; 
               if($scope.newRequest.fromDate){                   
                    $scope.isShiftTimeDisable = true;
               } 
               $('.btn-link').removeClass('noPointer');
           }
       };
       
       $scope.setTripType = function(requestTime, fromDate, tripType){
           var validFromtime;
           if(angular.isObject(tripType)){               
               $scope.createNewRequest.tripType = tripType.value; 
               $scope.isTripTypeSelected = false;
               $scope.getShiftTime(tripType.value);
           }
           else $scope.createNewRequest.tripType = '';
       };
       
       $scope.isRadioDisable = function(){
           if(!$scope.isFromDateEntered && !$scope.isTripTypeSelected){  
               return false;
           }
           else return true;
       }
       
       $scope.validateRequestTime = function(requestTime, fromDate, tripType){
           var RequestedScheduleTime;
           var timeSelectedArray;
           var requestedDateInMS;
           var timeDiffInMins;
           var validTimeHoursStarts;
           var seledtedFromDate = new Date(fromDate);
           
           var todaysDate = new Date();
           var todayInMS = todaysDate.getTime();
           console.log('todayInMS : ' + todayInMS);
           
           var today = convertDateUTC(todaysDate); 
           var requested = convertDateUTC(seledtedFromDate);
           console.log('today : ' + today);
           console.log('requested : ' + requested);
           
           if(tripType.value == 'DROP'){
               console.log('todaysDate : ' + todaysDate);
               console.log('todaysDate.getHours() : ' + todaysDate.getHours());
               validTimeHoursStarts = 2 + todaysDate.getHours();
               console.log('validTimeHoursStarts : ' + validTimeHoursStarts);
           }
           if(tripType.value == 'PICKUP'){
               validTimeHoursStarts = 6 + todaysDate.getHours();
               console.log('validTimeHoursStarts : ' + validTimeHoursStarts);
           }
           
           if($scope.newShiftTypeSelected == 'preDefineShiftTime'){
               console.log('$scope.newShiftTypeSelected : ' + $scope.newShiftTypeSelected);
                timeSelectedArray = requestTime.split(':');
                selectedTimeInMinutes = parseInt(timeSelectedArray[0]*60) + parseInt(timeSelectedArray[1]);
                seledtedFromDate.setHours(timeSelectedArray[0]);
                seledtedFromDate.setMinutes(timeSelectedArray[1]);
               
                requestedDateInMS = seledtedFromDate.getTime();                
               
                console.log('seledtedFromDate : ' + seledtedFromDate);
                console.log('requestedDateInMS : ' + requestedDateInMS);               
                console.log('todayInMS : ' + todayInMS);
           }
           else if($scope.newShiftTypeSelected == 'ShiftTimeCustom'){
               console.log('$scope.newShiftTypeSelecte : ' + $scope.newShiftTypeSelecte);
                var timePicker = new Date(requestTime)
                var hh = timePicker.getHours();
                var min = timePicker.getMinutes();
                seledtedFromDate.setHours(hh);
                seledtedFromDate.setMinutes(min);
                selectedTimeInMinutes = hh*60 + min;
                requestedDateInMS = seledtedFromDate.getTime();  
               
               
                console.log('seledtedFromDate : ' + seledtedFromDate);
                console.log('requestedDateInMS : ' + requestedDateInMS);
           }
           
           timeDiffInMins = Math.floor((requestedDateInMS - todayInMS)/1000/60);
           console.log('timeDiffInMins : ' + timeDiffInMins);
           
           //Check the User Role
           if($scope.adminRole && ($scope.newShiftTypeSelected == 'preDefineShiftTime' || $scope.newShiftTypeSelected == 'ShiftTimeCustom')){
               //Check if its Request Date is equals to Todays Date              
              if(today == requested && (requestedDateInMS < todayInMS || validTimeHoursStarts>24)){
                 $scope.showalertMessage('This is not the Valid Time. Please Change the Time or the Date', '');
                 return false;
              }
              else{
                  if(tripType.value == 'PICKUP' && timeDiffInMins<5){
                     $scope.showalertMessage("Please select PickUp time 6 hours from now", "");
                     return false;
                  }
                  if(tripType.value == 'DROP' && timeDiffInMins<5){
                     $scope.showalertMessage("Please select Drop time 2 hours from now", "");
                     return false;
                  }                      
             } 
           return true;
         }
         else if(!$scope.adminRole && $scope.newShiftTypeSelected == 'ShiftTimeCustom'){
             if(selectedTimeInMinutes>=180 && selectedTimeInMinutes<=300){
                 if(today == requested && (requestedDateInMS < todayInMS || validTimeHoursStarts>24)){
                     $scope.showalertMessage('This is not the Valid Time. Please Change the Time or the Date', '');
                     return false;
                  }
                  else{
                      if(tripType.value == 'PICKUP' && timeDiffInMins<360){
                         $scope.showalertMessage("Please select PickUp time 6 hours from now", "");
                         return false;
                      }
                      if(tripType.value == 'DROP' && timeDiffInMins<120){
                         $scope.showalertMessage("Please select Drop time 2 hours from now", "");
                         return false;
                      }                      
                 }                  
             }
             else{
                 $scope.showalertMessage('Please select AD-HOC time between 3:00 am to 5:00 am', '');
                 return false;
             }
            return true; 
         } 
         else if(!$scope.adminRole && $scope.newShiftTypeSelected == 'preDefineShiftTime'){
                 if(today == requested && (requestedDateInMS < todayInMS || validTimeHoursStarts>24)){
                     $scope.showalertMessage('This is not the Valid Time. Please Change the Time or the Date', '');
                     return false;
                  }
                  else{
                      if(tripType.value == 'PICKUP' && timeDiffInMins<360){
                         $scope.showalertMessage("Please select PickUp time 6 hours from now", "");
                         return false;
                      }
                      if(tripType.value == 'DROP' && timeDiffInMins<120){
                         $scope.showalertMessage("Please select Drop time 2 hours from now", "");
                         return false;
                      }                      
                 }                 
            return true;
         }        
       };
       
       $scope.setRequestType = function(requestType){
           if(angular.isObject(requestType)){
//           alert($scope.newShiftTypeSelected + ' - ' + $scope.shiftTime);
           $scope.isFromDateEntered = true;
           $scope.newRequestByAdmin = {};
           $scope.isShiftTimeDisable = true;
           
           requestIndex = _.findIndex($scope.requestTypes, { value: requestType.value }); 
           var d = new Date();
           d.setHours( 00 );
           d.setMinutes( 0 );
           $('.btn-link').addClass('noPointer'); 
           $scope.newRequestByAdmin = {
                                       'requestType':$scope.requestTypes[requestIndex], 
                                       'id' : '',
                                       'name' : '',
                                       'email' : '',
                                       'createNewAdHocTime':d, 
                                       'createNewtripTime': $scope.tripTimes[0], 
                                       /*'areaCode':$scope.areaCodes[0],*/
                                       'areaCode2':$scope.areaCodes[0],
                                       'contact' : '',
                                       'tripType':$scope.tripTypes[1]};
//           document.getElementById('location').value = '';
           
//           $scope.shiftTime = {};
//           TripTypeSelected = false; //is inserted on Client's Request
           $scope.isTripTypeSelected = false;
           $scope.createRequestIsClicked = true;
           $scope.createRequestRole_ADMIN = true;
           $scope.createRequestRole_EMPLOYEE = false;                 
           $scope.createRequestRole_GUEST = false;
           
           $scope.requestFor = requestType.value;
           switch(requestType.value) {
            case "SELF":                 
                $scope.createRequestRole_ADMIN = true;
                $scope.createRequestRole_EMPLOYEE = false;                
                $scope.createRequestRole_GUEST = false;
//                $scope.newRequestByAdmin.id = "n/a";
                break;
            case "EMPLOYEE":                   
                $scope.createRequestRole_ADMIN = false;               
                $scope.createRequestRole_EMPLOYEE = true;                
                $scope.createRequestRole_GUEST = false;
                break;
            case "GUEST": 
                $scope.createRequestRole_ADMIN = false;                
                $scope.createRequestRole_EMPLOYEE = false;                
                $scope.createRequestRole_GUEST = true;
                break;
           }
       }
      }
       
       $scope.idIsEntered = function(){
           if($scope.newRequestByAdmin.id){               
               $(".creatNewReqButton").removeClass("disabled");
           }
           else $(".creatNewReqButton").addClass("disabled");
       };
       
       $scope.initialzeNewCustomTime = function(){
            var d = new Date();
            d.setHours( 00 );
            d.setMinutes( 0 );
            $('.btn-link').addClass('noPointer');
           $scope.getShiftTime('DROP');
           //sTripTypeSelected = false; is inserted on Client's Request
           $scope.isTripTypeSelected = false; 
           $scope.newRequest = {'createNewAdHocTime':d, 
                                'createNewtripTime': $scope.tripTimes[0],
                                'tripType':$scope.tripTypes[1]};         
       };
       
       $scope.initialzeNewCustomTimeByAdmin = function(){  
           $scope.newRequestByAdmin = {};
           var d = new Date();
           d.setHours( 00 );
           d.setMinutes( 0 );
           $('.btn-link').addClass('noPointer');
           $scope.getShiftTime('DROP');
           $scope.newRequestByAdmin = {'createNewAdHocTime':d, 
                                       'createNewtripTime': $scope.tripTimes[0], 
                                       'requestType':$scope.requestTypes[0], 
/*                                       'areaCode':$scope.areaCodes[0],
*/                                       'areaCode2':$scope.areaCodes[0],
                                       'tripType':$scope.tripTypes[1],
                                       'location':''};
           //TripTypeSelected = false; is inserted on Client's Request
           $scope.isTripTypeSelected = false;
           $scope.createRequestIsClicked = true;
           $scope.createRequestRole_ADMIN = true;
           $scope.createRequestRole_EMPLOYEE = false;                 
           $scope.createRequestRole_GUEST = false;
       }; 
       
       $scope.createNewRequest = function(request){           
           if($scope.newShiftTypeSelected == 'preDefineShiftTime'){
               newShiftTimeSelected = request.createNewtripTime;              
               $scope.validFormFlag = $scope.validateRequestTime($scope.newRequest.createNewtripTime, request.fromDate, request.tripType);
           }
           else if($scope.newShiftTypeSelected == 'ShiftTimeCustom'){
               newShiftTimeSelected = convertToTime(request.createNewAdHocTime);
               $scope.validFormFlag = $scope.validateRequestTime($scope.newRequest.createNewAdHocTime, request.fromDate, request.tripType);
           }            
           
           var data = {
            	  efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
            	  userId:profileId,
                  requestFrom:"W",
                  startDate:convertDateUTC(request.fromDate),
                  endDate:convertDateUTC(request.endDate),
                  time:newShiftTimeSelected,
                  tripType:request.tripType.value,
                  employeeId:"NO"
              };		
          console.log(data);
           if($scope.validFormFlag){  
                $http.post('services/trip/devicerequest/',data).
                       success(function(data, status, headers, config) {
//                    	   alert(JSON.stringify(data));
                           $scope.showalertMessage("Your request has been created successfully.", "");
                           $scope.initialzeNewCustomTime()
                       }).
                       error(function(data, status, headers, config) {
                         // log error
                       });   
           }  
       };
       
       $scope.createNewRequestByAdmin = function(requestByAdmin){
 //          alert(JSON.stringify(requestByAdmin));
           if($scope.newShiftTypeSelected == 'preDefineShiftTime'){
               newShiftTimeSelected = requestByAdmin.createNewtripTime;              
               $scope.validFormFlag = $scope.validateRequestTime($scope.newRequestByAdmin.createNewtripTime, requestByAdmin.fromDate, requestByAdmin.tripType);
           }
           else if($scope.newShiftTypeSelected == 'ShiftTimeCustom'){
               newShiftTimeSelected = convertToTime(requestByAdmin.createNewAdHocTime);
               $scope.validFormFlag = $scope.validateRequestTime($scope.newRequestByAdmin.createNewAdHocTime, requestByAdmin.fromDate, requestByAdmin.tripType);
           }                     
           
           switch(requestByAdmin.requestType.value) {
                case "SELF":                      
                	var data = {
                       efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                       userId:profileId,
                       requestFrom:"W",
                       startDate:convertDateUTC(requestByAdmin.fromDate),
                       endDate:convertDateUTC(requestByAdmin.endDate),
                       time:newShiftTimeSelected,
                       tripType:requestByAdmin.tripType.value,
                       employeeId:"NO"
                   };                	               	
                   console.log('Inside Self::createNewRequestByAdmin');
                   console.log(data);
                    if($scope.validFormFlag){               
                         $http.post('services/trip/devicerequest/',data).
                                success(function(data, status, headers, config) {
 //                                   alert(JSON.stringify(data));
                                    if(data.status=='alreadyRaised'){
                                        $scope.showalertMessage('You have already raised a request for this Shift Time.', '');
                                    }
                                    else {
                                        $scope.showalertMessage("Your request has been created successfully.", "");                             
                                        $scope.initialzeNewCustomTimeByAdmin();
                                    }
                                }).
                                error(function(data, status, headers, config) {
                                  // log error
                                });   
                    }
                    break;
                 case "EMPLOYEE":                       
                   if(!$scope.newRequestByAdmin.id){
                       $scope.showalertMessage("Please enter a Valid Employee ID", "");
                       $(".creatNewReqButton").addClass("disabled");
                   }
                   else{  
                	   var data = {
                        	  efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                              userId:profileId,
                              requestFrom:"W",
                              startDate:convertDateUTC(requestByAdmin.fromDate),
                              endDate:convertDateUTC(requestByAdmin.endDate),
                              time:newShiftTimeSelected,
                              tripType:requestByAdmin.tripType.value,
                              employeeId:requestByAdmin.id
                          };	                       	
                       console.log('Inside Employee::createNewRequestByAdmin');
                       console.log(data);
                        if($scope.validFormFlag){               
                            $http.post('services/trip/devicerequest/',data).
                                   success(function(data, status, headers, config) {
//                                    alert(JSON.stringify(data));
                                    if(data.status=='alreadyRaised'){
                                        $scope.showalertMessage('You have already raised a request for this Shift Time.', '');
                                    }
                                    else if(data.status=='empNotExist'){
                                    	$scope.showalertMessage("Sorry this employee id is not registered", "");                                    	
                                    }
                                    else {
                                        $scope.showalertMessage("Your request has been created successfully.", "");
                                        $scope.initialzeNewCustomTimeByAdmin();
                                    }                                
                                   }).
                                   error(function(data, status, headers, config) {
                                     // log error
                                   });   
                       }
                  }
                break;
                case "GUEST":
 //                  alert(requestByAdmin.contact2 + ' - ' + requestByAdmin.location2 + ' - ' + requestByAdmin.cords2);
                   if($scope.isLocationEntered && $scope.isLocationEntered2){  
//                       var guestContactNumber = requestByAdmin.areaCode.value+requestByAdmin.contact;  
//                       var hostContactNumber = requestByAdmin.areaCode.value+requestByAdmin.contact;  

                       var data = {
                               efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                               userId:profileId,
                               hostMobileNumber:requestByAdmin.contact2,
                               guestMiddleLoc:requestByAdmin.cords2,
                               firstName:requestByAdmin.name,
                               emailId:requestByAdmin.email,
                               mobileNumber:requestByAdmin.contact,
                               address:$scope.newRequestByAdmin.location,
                               latitudeLongitude:$scope.newRequestByAdmin.cords,
                               gender:$scope.newRequestByAdmin.gender.value,                         
                               requestFrom:"W",
                               startDate:convertDateUTC(requestByAdmin.fromDate),
                               endDate:convertDateUTC(requestByAdmin.endDate),
                               time:newShiftTimeSelected,
                               tripType:requestByAdmin.tripType.value,
                               employeeId:requestByAdmin.id
                               };	
                       console.log('Inside Guest::createNewRequestByAdmin');
                       console.log(data);
                       if($scope.validFormFlag){  
                           $http.post('services/trip/requestforguest/',data).
                                success(function(data, status, headers, config) {
 //                               	alert(data);
                                    if(data.status=='empExist'){
                                        $scope.showalertMessage('This employee id is already register with transport department.', '');
                                    }
                                    else if(data.status=='mobileExist'){
                                        $scope.showalertMessage('This mobile number already register with some other employee.', '');

                                    }
                                    else{
                                    	$scope.showalertMessage("Your request has been created successfully.", "");
                                        $scope.initialzeNewCustomTimeByAdmin();
                                    }
                               
                               }).
                               error(function(data, status, headers, config) {
                                 // log error
                               });
                       }                       
                   }
                   else{
                       if(!$scope.isLocationEntered && !$scope.isLocationEntered2){
                            $scope.showalertMessage("Please enter Pick/Drop Location", "");}
                   }
                break;
            } 
       };
       
       $scope.openMap = function(size){
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/createRequestMapLocation.jsp',
           controller: 'creatRequestMapCtrl',
           size:size,
           backdrop:'static',
           resolve: {
//               newRequestByAdmin : function(){return newRequestByAdmin;}
           }
           }); 
		   
		   modalInstance.result.then(function(result){   
               var x = document.getElementById("newAddress").value;
                    console.log("in the Modal Instance"+ x);
                    $scope.newRequestByAdmin.location = x;
                    $scope.newRequestByAdmin.cords = result.cords;
//                    $scope.newRequestByAdmin2 = {'location': result.address, 'cords': result.cords};
//                    document.getElementById("location").value = x;
                    $scope.isLocationEntered = true;
                    console.log("$scope.newRequestByAdmin.location : "+ $scope.newRequestByAdmin.location);               
           });                 
        };
       
       $scope.openMap2 = function(size){
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/createRequestMapLocation.jsp',
           controller: 'creatRequestMapCtrl',
           size:size,
           backdrop:'static',
           resolve: {
//               newRequestByAdmin : function(){return newRequestByAdmin;}
           }
           }); 
		   
		   modalInstance.result.then(function(result){   
               var x = document.getElementById("newAddress").value;
                    console.log("in the Modal Instance"+ x);
                    $scope.newRequestByAdmin.location2 = x;
                    $scope.newRequestByAdmin.cords2 = result.cords;
//                    $scope.newRequestByAdmin2 = {'location': result.address, 'cords': result.cords};
//                    document.getElementById("location").value = x;
                    $scope.isLocationEntered2 = true;
                    console.log("$scope.newRequestByAdmin.location : "+ $scope.newRequestByAdmin.location);               
           });                 
        };
       
       //ng-blur="checkIDExist()"
       $scope.checkIDExist = function(){
  //         alert("Lost Focus");
       };
    };    
    
    
    var creatRequestMapCtrl = function($scope, $modalInstance, $state, $timeout){ 
        $scope.mapIsLoaded = true;
        $scope.alertMessage;
        $scope.alertHint;    
        $scope.user = {address:'', cords:'', search:''};
        $scope.loc = { lat: 12.844873, lon: 80.226535 }; 
    
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
        
        $scope.geoCode = function(search){
//            if (search) {
                console.log($scope.user.search);
                if (!this.geocoder) this.geocoder = new google.maps.Geocoder();
                this.geocoder.geocode({ 'address': $scope.user.search }, function (results, status) {
                    
                    if (status == google.maps.GeocoderStatus.OK) {
                        var loc = results[0].geometry.location;
                        $scope.search = results[0].formatted_address;
 //                       alert(loc.lat()+ ' - ' + loc.lng());
                        $scope.gotoLocation(loc.lat(), loc.lng());
                        $scope.user.cords = loc;
//                        document.getElementById("newAddress").value = $scope.search; 
//                        $scope.user.cord = $scope
                        $scope.$apply(function () {
                            $scope.user.cords = loc.lat() + ',' + loc.lng();
                            $scope.user.address = $scope.search;
                        });
                    } else {
                        $scope.showalertMessageModal("Sorry, this search produced no results.", "");
//                        $scope.user.cords = '';
//                        document.getElementById("newAddress").value = ''; 
                        $scope.$apply(function () {
                            $scope.user.cords = '';
                            $scope.user.address = '';
                        });
                    }
                });
//            }
            
        };
        
        $scope.gotoLocation = function (lat, lon) {
            if ($scope.lat != lat || $scope.lon != lon) {
                $scope.loc = { lat: lat, lon: lon };
                if (!$scope.$$phase) $scope.$apply("loc");
            }
            console.log($scope.loc);
        };
        
        $scope.setPickDropLocation = function(user){ 
            var address = document.getElementById("newAddress").value;
            $scope.showalertMessageModal('Address has been updated successfully.', '');
            $timeout(function() {$modalInstance.close(user)}, 4000);            
        }
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    
    angular.module('efmfmApp').controller('creatRequestMapCtrl', creatRequestMapCtrl);
    angular.module('efmfmApp').controller('requestDetailsCtrl', requestDetailsCtrl);
}());