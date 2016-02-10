(function(){
    var vsModalCtrl = function($scope, $modal, $log, $http){
       $scope.openVSModel = function(index, size){
       console.log("Row clicked " + index)
       switch(index){
         case 0:
           var modalInstance = $modal.open({
           templateUrl: 'partials/modals/dashboardModal/vsRegisteredVehicle.jsp',
           controller: 'vsModalInstanceCtrl',
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
            templateUrl: 'partials/modals/dashboardModal/vsVehicleAvailable.jsp',
            controller: 'vsModalInstanceCtrl',
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
       
         case 2:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/vsDropOnRoad.jsp',
            controller: 'vsModalInstanceCtrl',
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
               
        case 3:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/vsPickOnRoad.jsp',
            controller: 'vsModalInstanceCtrl',
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
        
        case 4:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/vsServices.jsp',
            controller: 'vsModalInstanceCtrl',
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
          }   //switch ends
   }; 
 };
    angular.module('efmfmApp').controller('vsModalCtrl', vsModalCtrl);
    
     var vsModalInstanceCtrl = function($scope, $modalInstance, $state, index, $http){
    	 
    	 
    	 $scope.getRegisterdVehicle = function(){
         	//alert("registeredvehicle");
 		   var data = {
 				  eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
 				   tripType:"VEHICLESTATUS",
 					   requestStatus:"registeredvehicle"
 			};
 		   $http.post('services/dashboard/getDashBoardDetailList/',data).
 				 success(function(data, status, headers, config) {
 			//	    alert(JSON.stringify(data));
 				    $scope.vehicleRegisteredData = data;
 				 }).
 				 error(function(data, status, headers, config) {
 				      // log error
 				 });
 	    };
    	 
    	 
         
        $scope.getVehicleAvailable = function(){
        	//alert("available");
		   var data = {
				   eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				   tripType:"VEHICLESTATUS",
					   requestStatus:"available"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
				   // alert(JSON.stringify(data));
				    $scope.vehicleAvailableData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
        
        $scope.getDropVehicleOnRoad = function(){
        	
		   var data = {
				   eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				   tripType:"VEHICLESTATUS",
					   requestStatus:"rolledout"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
//				    alert(JSON.stringify(data));
				    $scope.dropVehicleOnRoadData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
        
        $scope.getPickVehicleOnRoad = function(){
		   var data = {
				   eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				   tripType:"VEHICLESTATUS",
					   requestStatus:"rolledin"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
					 //alert(JSON.stringify(data));
				    $scope.pickVehicleOnRoadData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
        
        $scope.getVehicleForServices = function(){
		   var data = {
				   eFmFmClientBranchPO:{branchId:branchId},
				   tripType:"EMPLOYEESTATUS",
					   requestStatus:"rolledin"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
//				    alert(JSON.stringify(data));
				    $scope.vehicleForServicesData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
        
        switch(index) {
        	case 0:
        		$scope.getRegisterdVehicle();                
            break;
            case 1:
                $scope.getVehicleAvailable();
                break;
            case 2:
                $scope.getDropVehicleOnRoad();
                break;
            case 3:
                $scope.getPickVehicleOnRoad();
                break;
            case 4:
                $scope.getVehicleForServices();
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
    angular.module('efmfmApp').controller('vsModalInstanceCtrl', vsModalInstanceCtrl);

}());