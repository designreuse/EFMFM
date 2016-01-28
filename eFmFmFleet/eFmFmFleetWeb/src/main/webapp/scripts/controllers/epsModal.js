(function(){
    var epsModalCtrl = function($scope, $modal, $log, $http){
       $scope.openEPSModel = function(index, size){
       console.log("Row clicked " + index)
       switch(index){
         case 0:
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/epsTotalPlannedPickup.jsp',
            controller: 'epsModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;
                }
            }
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break;
       
         case 1:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/epsEmployeeScheduled.jsp',
            controller: 'epsModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;}
            }
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break; 
               
        case 2:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/epsPicked.jsp',
            controller: 'epsModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;}
            }
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break; 
               
        case 3:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/epsNoShow.jsp',
            controller: 'epsModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;}
            }
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break;  
          }   //switch ends
   }; 
 };
    angular.module('efmfmApp').controller('epsModalCtrl', epsModalCtrl);
    
     var epsModalInstanceCtrl = function($scope, $modalInstance, $state, index, $http){  
         
         $scope.getTotalPlannedPickup = function(){
		   var data = {
	        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				     tripType:"EMPLOYEESTATUS",
					   requestStatus:"pickuprequest"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
//				    alert(JSON.stringify(data));
				    $scope.totalPlannedPickupData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
         
        $scope.getEmployeeScheduled = function(){
		   var data = {
	        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				   tripType:"EMPLOYEESTATUS",
					   requestStatus:"pickupschedule"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
				   // alert(JSON.stringify(data));
				    $scope.employeeScheduledData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
         
        $scope.getEmployeePicked = function(){
		   var data = {
	        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				   tripType:"EMPLOYEESTATUS",
					   requestStatus:"pickupboarded"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
				   // alert(JSON.stringify(data));
				    $scope.employeePickedData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
        
        $scope.getNoShow = function(){
        	//alert("NoShow");
		   var data = {
	        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
	        		   tripType:"EMPLOYEESTATUS",
					   requestStatus:"noshowpickup"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
				   // alert(JSON.stringify(data));
				    $scope.noShowData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
         
        switch(index) {
            case 0:
                $scope.getTotalPlannedPickup();
                break;
            case 1:
               $scope.getEmployeeScheduled();
                break;
            case 2:
                $scope.getEmployeePicked();
                break;
            case 3:
                $scope.getNoShow();
                break;
        }
        $scope.ok = function () {            
            
            //put the values from text box in a variable
            //check the Login userId and Password
            //if the Login is success then close the Modal and proceed to Home Page
            $modalInstance.close();
            $state.go('home.dashboard');
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }; 
    angular.module('efmfmApp').controller('epsModalInstanceCtrl', epsModalInstanceCtrl);

}());