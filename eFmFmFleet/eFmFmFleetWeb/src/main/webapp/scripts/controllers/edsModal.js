(function(){
    var edsModalCtrl = function($scope, $modal, $log, $http){
       $scope.openEDSModel = function(index, size){
       console.log("Row clicked " + index)
       switch(index){
         case 0:
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/edstotalPlannedDrop.jsp',
            controller: 'dropStatusCtrl',
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
            templateUrl: 'partials/modals/dashboardModal/edsemployeeScheduled.jsp',
            controller: 'dropStatusCtrl',
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
            templateUrl: 'partials/modals/dashboardModal/edsDropped.jsp',
            controller: 'dropStatusCtrl',
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
            templateUrl: 'partials/modals/dashboardModal/edsNoShow.jsp',
            controller: 'dropStatusCtrl',
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
    angular.module('efmfmApp').controller('edsModalCtrl', edsModalCtrl);
    
    var dropStatusCtrl = function($scope, $modalInstance, $state, $http, index){
        $scope.getTotalPlannedDrop = function(){
		   var data = {
	        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				     tripType:"EMPLOYEESTATUS",
					 requestStatus:"droprequest"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
				  //  alert(JSON.stringify(data));
				    $scope.totalPlannedDropData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
        
        $scope.getEmployeeScheduled = function(){
		   var data = {
	        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				   tripType:"EMPLOYEESTATUS",
					   requestStatus:"dropschedule"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
//				    alert(JSON.stringify(data));
				    $scope.employeeScheduledData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
        
        $scope.getEmployeeDropped = function(){
		   var data = {
	        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				   tripType:"EMPLOYEESTATUS",
					   requestStatus:"dropboarded"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
//				    alert(JSON.stringify(data));
				    $scope.employeeDroppedData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
        
        $scope.getNoShow = function(){
		   var data = {
	        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
				   tripType:"EMPLOYEESTATUS",
					   requestStatus:"noshowdrop"
			};
		   $http.post('services/dashboard/getDashBoardDetailList/',data).
				 success(function(data, status, headers, config) {
//				    alert(JSON.stringify(data));
				    $scope.noShowData = data;
				 }).
				 error(function(data, status, headers, config) {
				      // log error
				 });
	    };
        
        switch(index) {
            case 0:
                $scope.getTotalPlannedDrop();
                break;
            case 1:
                $scope.getEmployeeScheduled();
                break;
            case 2:
                $scope.getEmployeeDropped();
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
    
     var edsModalInstanceCtrl = function($scope, $modalInstance, $state){        
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
    angular.module('efmfmApp').controller('dropStatusCtrl', dropStatusCtrl);

}());