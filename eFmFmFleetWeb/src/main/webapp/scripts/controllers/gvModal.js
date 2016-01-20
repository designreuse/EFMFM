(function(){
    var gvModalCtrl = function($scope, $modal, $log){
       $scope.openGVModel = function(index, size){
       console.log("Row clicked " + index);
       switch(index){
         case 0:
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/gvPollutionDue.jsp',
            controller: 'gvModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;
                }}
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break;
       
         case 1:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/gvInsuranceDue.jsp',
            controller: 'gvModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;
                }}
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break;   
       
         case 2:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/gvTaxCert.jsp',
            controller: 'gvModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;
                }}
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break;
       
         case 3:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/gvPermitRenewal.jsp',
            controller: 'gvModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;
                }}
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break;
               
         case 4:
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/gvBreakDowns.jsp',
            controller: 'gvModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;
                }}
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break;
       
//         case 3:  
//            var modalInstance = $modal.open({
//            templateUrl: 'partials/modals/gvContractDue.jsp',
//            controller: 'gvModalInstanceCtrl',
//            size: size,
//            resolve: {
//                index : function(){
//                    return index;
//                }}
//            });
//
//            modalInstance.result.then(function (){}, function(){
//                  $log.info('Modal dismissed at: ' + new Date());
//            });
//            break;   
       
         case 5:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/gvVehicleMaintenance.jsp',
            controller: 'gvModalInstanceCtrl',
            size: size,
            resolve: {
                index : function(){
                    return index;
                }}
            });

            modalInstance.result.then(function (){}, function(){
                  $log.info('Modal dismissed at: ' + new Date());
            });
            break;  
          }   //switch ends
   }; 
 };
    angular.module('efmfmApp').controller('gvModalCtrl', gvModalCtrl);
    
     var gvModalInstanceCtrl = function($scope, $modalInstance, $state, index, $http){ 
         
         $scope.getPollutionDueData = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                       tripType:"GOVERNCEVEHICLES",
                           requestStatus:"polutionexpire"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                        //alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });             
         };
         
         $scope.getInssuranceDueData = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                     tripType:"GOVERNCEVEHICLES",
                         requestStatus:"insurancevalid"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                        //alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });    
         };
         
         $scope.getBreakdownsData = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                     tripType:"GOVERNCEVEHICLES",
                         requestStatus:"breakdownsalerts"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                        //alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });    
         };
         
         $scope.getContractData = function(){
             var data = {
					 eFmFmClientBranchPO:{branchId:branchId},
                       tripType:"ALERTS",
                           requestStatus:"roadalerts"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                        //alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });    
         };
         
         $scope.getTaxCertData = function(){  
             var data = {
                eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                tripType:"GOVERNCEVEHICLES",
                requestStatus:"taxvalid"
                };
             $http.post('services/dashboard/getDashBoardDetailList/',data).
                 success(function(data, status, headers, config) {
//                        alert(JSON.stringify(data));
                        $scope.taxCertData = data;
                     }).
                 error(function(data, status, headers, config) {
                          // log error
                     });
         };
         
         $scope.getPermitDueData = function(){
             var data = {
                eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                tripType:"GOVERNCEVEHICLES",
                requestStatus:"permitvalid"
                };
             $http.post('services/dashboard/getDashBoardDetailList/',data).
                 success(function(data, status, headers, config) {
                       // alert(JSON.stringify(data));
                        $scope.permitDueData = data;
                     }).
                 error(function(data, status, headers, config) {
                          // log error
                     });
         };
         
         $scope.getVehicleMaintenanceData = function(){
             var data = {
                eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                tripType:"GOVERNCEVEHICLES",
                requestStatus:"vehiclemaintenancevalid"
                };
             $http.post('services/dashboard/getDashBoardDetailList/',data).
                 success(function(data, status, headers, config) {
//                        alert(JSON.stringify(data));
                 console.log(data);
                        $scope.vehicleMaintenanceData = data;
                     }).
                 error(function(data, status, headers, config) {
                          // log error
                     });
         };
         
//         $scope.getVehicleMaintenanceData = function(){
//             var data = {
//					 eFmFmClientBranchPO:{branchId:branchId},
//                       tripType:"ALERTS",
//                           requestStatus:"roadalerts"
//                };
//               $http.post('services/dashboard/getDashBoardDetailList/',data).
//                     success(function(data, status, headers, config) {
//                        //alert(JSON.stringify(data));
//                        $scope.sosRoadAlertsData = data;
//                     }).
//                     error(function(data, status, headers, config) {
//                          // log error
//                     });    
//         };
         
          switch(index) {
            case 0:
                $scope.getPollutionDueData();
                break;
            case 1:
                $scope.getInssuranceDueData();
                break;
            case 2:
                $scope.getTaxCertData();
                break;
            case 3:
                $scope.getPermitDueData();
                break;
            case 4:
                $scope.getBreakdownsData();
                break;
//            case 3:
//                $scope.getContractData();
//                break;
            case 5:
                $scope.getVehicleMaintenanceData();
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
    angular.module('efmfmApp').controller('gvModalInstanceCtrl', gvModalInstanceCtrl);

}());