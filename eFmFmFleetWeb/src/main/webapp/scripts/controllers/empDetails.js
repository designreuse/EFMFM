(function(){
	   var empDetailCtrl = function($scope, $http, $filter, $modal){
		 $('.empDetail_admin').addClass('active');
		 $('.admin_home').addClass('active');
		 $scope.posts = [];	
         $scope.progressbar.start();
        
         $scope.getEmployeesDetail = function(){
             console.log('Inside :: $scope.getEmployeesDetail()');
//             alert($scope.posts.length);
//             if($scope.posts.length == 0){                 
                 var dataObj = {
                         eFmFmClientBranchPO:{branchId:branchId}
                 };		 
                 $http.post('services/employee/details/',dataObj).
                            success(function(data, status, headers, config) {
                              $scope.posts = data; 
//                                   alert(JSON.stringify(data));
//                              $scope.countofFilteredRecords = $scope.posts.length;
//                              $scope.countofFilteredRecords = 0;
//                              $scope.showSearchResultCount = false;
//                              angular.forEach($scope.posts, function(item) {
//                                  item.checkBoxFlag=false;
//                              });
                              $scope.progressbar.complete();
                            }).
                            error(function(data, status, headers, config) {
                              // log error
                            });    
//             } 
//             else{var dataObj = {
//                         eFmFmClientBranchPO:{branchId:branchId}
//                 };		 
//                 $http.post('services/employee/details/',dataObj).
//                            success(function(data, status, headers, config) {
//                              $scope.postsTemp = data; 
//                              angular.forEach($scope.postsTemp, function(item) {
//                                  $scope.posts.push({
//                                                        "employeeName": item.employeeName,
//                                                        "employeeLatiLongi": item.employeeLatiLongi,
//                                                        "mobileNumber": item.mobileNumber,
//                                                        "employeeId": item.employeeId,
//                                                        "emailId": item.emailId,
//                                                        "employeeGender": item.employeeGender,
//                                                        "userId": item.userId,
//                                                        "employeeAddress": item.employeeAddress,
//                                                        "employeeDesignation": item.employeeDesignation
//                                                    });
//                              });
//                              $scope.progressbar.complete();
//                            }).
//                            error(function(data, status, headers, config) {
//                              // log error
//                            });   
//                 
//             }
         };
		 $scope.getEmployeesDetail();
           
	     $scope.showSearchResultCount = false;
	     $scope.numOfRows = 0;
	     $scope.numberofRecords = 1000000;
	     $scope.countofFilteredRecords;
	     $scope.selectAllClicked = false;
	     $scope.deleteAllClicked = false;
	     
	     $scope.paginations = [{'value':10, 'text':'10 records'},
	    	 				   {'value':15, 'text':'15 record',},
	    	 				   {'value':20, 'text':'20 records'}];
	     //$scope.showRecords = $scope.paginations[0].text;
	     $scope.shiftsTime = ['6:00 - 8:00', 
	                         '8:00 - 10:00', 
	                         '10:00 - 12:00', 
	                         '12:00 - 14:00', 
	                         '14:00 - 16:00', 
	                         '16:00 - 18:00', 
	                         '18:00 - 20:00', 
	                         '20:00 - 22:00', 
	                         '22:00 - 24:00'];
	     
	     $scope.checkBoxModel  = {value : false};
	     
	     $scope.selectAll = function(){
	    	 console.log($scope.selectAllClicked);
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
	     
	     $scope.deleteAll = function(){
	    	 console.log($scope.deleteAllClicked);
	    	 if(!$scope.deleteAllClicked){
	    		 $scope.deleteAllClicked = true;
	    		 angular.forEach($scope.posts, function(item) {
			    	  item.checkBoxFlag=true;
		         });
	    		 }
	    	 else
	    		 {
	    		 $scope.deleteAllClicked = false;
	    		 angular.forEach($scope.posts, function(item) {
			    	  item.checkBoxFlag=false;
		         });	    		 	
	    		 }
	     };
	     
	     $scope.deleteEmployee = function(post){
             if(confirm("Are you sure you want to Delete this row?") == true){  
                 var dataObj = {
        				 eFmFmClientBranchPO:{branchId:branchId},
        				userId:post.userId,
        		 };	
        		 $http.post('services/user/deleteemployee/',dataObj).
        	    		    success(function(data, status, headers, config) {
        	    		     if(data.status='success'){
        	    		    	 $scope.showalertMessage("Employee deleted successfully", "");
        	                     $(".employee"+post.employeeId).hide('slow');
        	    		     }         	    		     
        	    		    }).
        	    		    error(function(data, status, headers, config) {
        	    		      // log error
        	    		    });   
                 
             }
	    	 
	    	 
	     };
	     
	     //THIS FUNCTION IS CALLED WHEN THE CHECKBOX IS CLICKED     
	     $scope.select_thisEmployee = function(post) {
	    	 if(!post.checkBoxFlag){
	    		 post.checkBoxFlag = true;
	    		 console.log(post.name + " is selected");
	    	 }
	    	 else {
	    		 post.checkBoxFlag = false;
	    		 $scope.selectAllClicked = false;
	    		 $scope.deleteAllClicked = false;
	    	 }
	     };
	     
	     //Set the Limit of ng-repeat in <tr>
	     $scope.setLimit = function(showRecords){
	    	 if(!showRecords){$scope.numberofRecords = $scope.posts.length;}
	    	 else $scope.numberofRecords = showRecords.value;  	 
	    };
           
        $scope.openMap = function(post, size){
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/editEmpDetailMapLocation.jsp',
           controller: 'employeeDetailsMapCtrl',
           size:size,
           resolve: {
               employee : function(){return post;},
               branchId:function(){return branchId;}
           }
           }); 
		   
		   modalInstance.result.then(function(result){
 //              alert("employeeLatiLongi : " + result.cords + " employeeAddress : " + result.address);
               post.employeeLatiLongi = result.cords;
               post.employeeAddress = result.address;
               /*if(((result.cords).length==0) || ((result.address).length==0)){
            	  alert("hit"); 
               }*/
               
               var dataObj = {
        				 eFmFmClientBranchPO:{branchId:branchId},
        				userId:post.userId,
        				latitudeLongitude:result.cords,
        				address:result.address     				
        		 };	
        		 $http.post('services/user/updateuseraddress/',dataObj).
        	    		    success(function(data, status, headers, config) {
 //       	    		    	alert(JSON.stringify(data));
        	    		     if(data.status='success'){
        	    		    	 $scope.showalertMessage("Geocodes updated successfully", "");
        	    		     } 
        	    		     else{
        	    		    	 $scope.showalertMessage("Please check Address", "");
        	    		     }
        	    		    }).
        	    		    error(function(data, status, headers, config) {
        	    		      // log error
        	    		    });
               
               
           });                
        };
           
        $scope.editEmployee = function(post){            
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/editEmployeeDetails.jsp',
           controller: 'editEmployeeCtrl',
           resolve: {
               employee : function(){return post;}
           }
           }); 
		   
		   modalInstance.result.then(function(result){
               post.employeeName = result.name;
               post.employeeAddress = result.address;
               post.mobileNumber = result.number;
               post.emailId = result.email;
           });                
            
        };
        
        $scope.searchEmployees = function(search){
            //if data comes as empty array then alert employee does not exist please chechk your employee Id
  //          alert("Inside Search Employee Function");
            var dataObj = {
                         eFmFmClientBranchPO:{branchId:branchId},
                         employeeId:search
                 };   
                 $http.post('services/employee/empiddetails/',dataObj).
                      success(function(data, status, headers, config) {
                            $scope.posts = data;
                
                        }).
                            error(function(data, status, headers, config){});
            //dump the result of search in $scope.posts
            //on success
            //$scope.posts = data;
        };
	    
	    $scope.$watch("searchEmployeeReported", function(query){
	    	if($scope.searchEmployeeReported == ''){
	    	     $scope.showSearchResultCount = false;
	    	}
	    	else
	    	     $scope.showSearchResultCount = true;
	    	
	        $scope.countofFilteredRecords = $filter("filter")($scope.posts, query);
	      });
           
        $scope.refreshEmpDetail = function(){
            $scope.getEmployeesDetail();
        };
	  };        
    
var employeeDetailsMapCtrl = function($scope, $modalInstance, $state, employee, branchId, $timeout){ 
        console.log(employee); 
        var latlong_center;
 //       alert(employee.employeeLatiLongi);
        if(employee.employeeLatiLongi==null){
        	latlong_center='12.973802,80.244119'.split(',');
        }
        else{
            latlong_center = employee.employeeLatiLongi.split(',');
	
        }
        $scope.alertMessage;$scope.alertHint;
        $scope.mapIsLoaded = true;
        $scope.user = {address:'', cords:'', search:''};
        $scope.loc = { lat: latlong_center[0], lon: latlong_center[1] }; 
        
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
                if (!this.geocoder) this.geocoder = new google.maps.Geocoder();
                this.geocoder.geocode({ 'address': $scope.user.search }, function (results, status) {
                    
                    if (status == google.maps.GeocoderStatus.OK) {
                        var loc = results[0].geometry.location;
                        $scope.search = results[0].formatted_address;
                        $scope.gotoLocation(loc.lat(), loc.lng());
                        $scope.user.cords = loc;
//                        document.getElementById("newAddress").value = $scope.search; 
//                        $scope.user.cord = $scope
                        $scope.$apply(function () {
                            $scope.user.cords = loc.lat() + ',' + loc.lng();
                            $scope.user.address = $scope.search;
                        });
                    } else {
                        $scope.$apply(function () {
                            $scope.user.cords = '';
                            $scope.user.address = '';
                        });
//                        $scope.user.cords = '';
//                        document.getElementById("newAddress").value = ''; 
                        $scope.showalertMessageModal("Sorry, this search produced no results.", "");
                    }
                });
//            }
            
        };
        
        $scope.gotoLocation = function (lat, lon) {
            if ($scope.lat != lat || $scope.lon != lon) {
                $scope.loc = { lat: lat, lon: lon };
                if (!$scope.$$phase) $scope.$apply("loc");
            }
        };
        
        $scope.setPickDropLocation = function(user){ 
            console.log('address : ' + user.address + 
                  ' latlongi : ' + user.cord + 
                  ' userID : ' + employee.userId + 
                  ' branchId ' + branchId); 
            $modalInstance.close(user);            
        }
        
        //CLOSE BUTTON FUNCTION
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };     
    
var editEmployeeCtrl = function($scope, $modalInstance, $state, $http, $timeout, employee){ 
         $scope.hstep = 1;
         $scope.mstep = 5;
         $scope.ismeridian = false;	       
        var d = new Date();
        d.setHours( 00 );
        d.setMinutes( 0 );        
       
       var convertToTime = function(newdate){
           d = new Date(newdate);
           hr = d.getHours();
           min = d.getMinutes();
           if(hr<10){hr = '0'+hr;} 
           if(min<10){min = '0'+min;}           
           console.log("TIME :: " + hr+":"+min);
           return hr+":"+min;
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
        
        $scope.IntegerNumber = /^\d+$/;
        $scope.editEmployee = {'employeeId':employee.employeeId,
                                'name':employee.employeeName, 
                               'employeeDesignation':employee.employeeDesignation, 
                               'number':employee.mobileNumber, 
                               'email':employee.emailId,
                               'address': employee.employeeAddress};
//                               'createNewAdHocTime':d};
        
        $scope.updateEmployee = function(updatedEmployee){ 
 //           alert(JSON.stringify(updatedEmployee));
  //          alert(updatedEmployee.employeeId);
 //           alert(updatedEmployee.number + " : " + updatedEmployee.email + " : "+profileId );
            if(($scope.editEmployee.address == employee.address) && ($scope.editEmployee.name == employee.employeeName) && ($scope.editEmployee.employeeDesignation == employee.employeeDesignation) && ($scope.editEmployee.number == employee.mobileNumber) && ($scope.editEmployee.email == employee.emailId)) {
                $scope.showalertMessageModal("No changes has been made", "");
//                $modalInstance.dismiss('cancel');
            }
            else{
                var dataObj = {
                     eFmFmClientBranchPO:{branchId:branchId},
                    employeeId:updatedEmployee.employeeId,
                    mobileNumber:updatedEmployee.number,
                    emailId:updatedEmployee.email,
                    firstName:updatedEmployee.name,
                    employeeDesignation:updatedEmployee.employeeDesignation,
                    address:updatedEmployee.address
                 };
                 $http.post('services/user/updateempdetails/',dataObj).
                        success(function(data, status, headers, config) {
                         if(data.status='success'){
    //   	    	            $modalInstance.close(updatedEmployee);            
                             $scope.showalertMessageModal("Employee Details updated successfully", "");
                             $timeout(function() {$modalInstance.close(updatedEmployee)}, 3000);
                         } 
                         else{
                             $scope.showalertMessageModal("Please check Employee Details", "");
                         }
                        }).
                        error(function(data, status, headers, config) {
                          // log error
                        }); 
            }          
         };
        
        $scope.cancel = function(){
            $modalInstance.dismiss('cancel');
        };
    };
	    
	angular.module('efmfmApp').controller('employeeDetailsMapCtrl', employeeDetailsMapCtrl);
    angular.module('efmfmApp').controller('editEmployeeCtrl', editEmployeeCtrl);
    angular.module('efmfmApp').controller('empDetailCtrl', empDetailCtrl);
	}());