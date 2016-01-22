(function(){
	   var rescheduleRequestCtrl = function($scope, $http, $filter){
		 $('.rescheduleRequest_admin').addClass('active');
		 $('.admin_home').addClass('active');
		 $scope.posts;
		 var data = {
        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
//				 efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
        		 efmFmUserMaster:{userId:profileId}
			};
		 $http.post('services/trip/reshedulerequest/',data).
	    		    success(function(data, status, headers, config) {
//	    		    	alert(JSON.stringify(data));
	    		      $scope.posts = data;   
	    		      $scope.countofFilteredRecords = $scope.posts.length;
	    		      $scope.countofFilteredRecords = 0;
	    		      $scope.showSearchResultCount = false;
	    		      angular.forEach($scope.posts, function(item) {
	    		    	  item.checkBoxFlag=false;
	    	         });
//	    		      $scope.$watch("searchEmployeeReported", function(query){
//	    		          $scope.countofFilteredRecords = $filter("filter")($scope.posts, query).length;
//	    		        });
	    		    }).
	    		    error(function(data, status, headers, config) {
	    		      // log error
	    		    });
		  
		 angular.forEach($scope.posts, function(item) {
	    	  console.log("item.name :: " + item.name + ", item.checkBoxFlag :: " + item.checkBoxFlag);	                      
      });
		 
	     $scope.showSearchResultCount = false;
	     $scope.numOfRows = 0;
	     $scope.numberofRecords = 10;
	     $scope.countofFilteredRecords;
	     $scope.selectAllClicked = false;
	     $scope.deleteAllClicked = false;
	     
	     $scope.paginations = [{'value':1, 'text':'1 record'},
	    	 				   {'value':10, 'text':'10 records'},
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
	     $scope.approve = function(post){
	    	 var data = {
					 efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
					   requestId:post.requestId
					   };
			 $http.post('services/trip/approvereshedulerequest/',data).
		    		    success(function(data, status, headers, config) {
		    		    	
                            alert("Request approve successfully");
		    		    }).
		    		    error(function(data, status, headers, config) {
		    		      // log error
		    		    });
			 };
         
         $scope.reject = function(post){
        	 var data = {
					 efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
					   requestId:post.requestId
					   };
			 $http.post('services/trip/rejectreshedulerequest/',data).
		    		    success(function(data, status, headers, config) {
	                         alert("Request rejected successfully");
		    		    }).
		    		    error(function(data, status, headers, config) {
		    		      // log error
		    		    });
           
            
         };
           
//	     $scope.selectAll = function(){
//	    	 console.log($scope.selectAllClicked);
//	    	 if(!$scope.selectAllClicked){
//	    		 $scope.selectAllClicked = true;
//	    		 angular.forEach($scope.posts, function(item) {
//			    	  item.checkBoxFlag=true;
//		         });
//	    		 }
//	    	 else
//	    		 {
//	    		 $scope.selectAllClicked = false;
//	    		 angular.forEach($scope.posts, function(item) {
//			    	  item.checkBoxFlag=false;
//		         });	    		 	
//	    		 }
//	     };
//	     
//	     $scope.deleteAll = function(){
//	    	 console.log($scope.deleteAllClicked);
//	    	 if(!$scope.deleteAllClicked){
//	    		 $scope.deleteAllClicked = true;
//	    		 angular.forEach($scope.posts, function(item) {
//			    	  item.checkBoxFlag=true;
//		         });
//	    		 }
//	    	 else
//	    		 {
//	    		 $scope.deleteAllClicked = false;
//	    		 angular.forEach($scope.posts, function(item) {
//			    	  item.checkBoxFlag=false;
//		         });	    		 	
//	    		 }
//	     };
	     
//	     $scope.deleteEmployee = function(post){
//	    	 console.log("Delete row number :" + post.regId);
//	    	 $('.'+post.regId).hide('slow');
//	     };
	     
	     //THIS FUNCTION IS CALLED WHEN THE CHECKBOX IS CLICKED     
//	     $scope.select_thisEmployee = function(post) {
//	    	 if(!post.checkBoxFlag){
//	    		 post.checkBoxFlag = true;
//	    		 console.log(post.name + " is selected");
//	    	 }
//	    	 else {
//	    		 post.checkBoxFlag = false;
//	    		 $scope.selectAllClicked = false;
//	    		 $scope.deleteAllClicked = false;
//	    	 }
//	     };
	     
	     $scope.getNumOfRecordsOnPage = function(numOfRecords){
	    	 //if showRecord is not selected then show all- and the record count == total records/total records
	    	 //if showRecord is selected then check id the showRecord.val > total record =>if true then record count == total records/total records
	    	 //otherwise only selected number of records will be seen and total record== showRecod.val/Total and the next button will be visible
	    	 
	     };
	     
	     //Set the Limit of ng-repeat in <tr>
	     $scope.setLimit = function(showRecords){
	    	 if(!showRecords){$scope.numberofRecords = $scope.posts.length;}
	    	 else $scope.numberofRecords = showRecords.value;  	 
	    };
	    
	    $scope.$watch("searchEmployeeReported", function(query){
	    	if($scope.searchEmployeeReported == ''){
	    	     $scope.showSearchResultCount = false;
	    	}
	    	else
	    	     $scope.showSearchResultCount = true;
	    	
	        $scope.countofFilteredRecords = $filter("filter")($scope.posts, query).length;
	      });
	           
	  };    
	    
	    angular.module('efmfmApp').controller('rescheduleRequestCtrl', rescheduleRequestCtrl);
	}());