(function(){
    var gdModalCtrl = function($scope, $modal, $log){
       $scope.openGDModel = function(index, size){
       console.log("Row clicked " + index)
       switch(index){
         case 0:
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/gdLicense.jsp',
            controller: 'gdModalInstanceCtrl',
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
            templateUrl: 'partials/modals/dashboardModal/gdMedicalDue.jsp',
            controller: 'gdModalInstanceCtrl',
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
            templateUrl: 'partials/modals/dashboardModal/gdPolicVerification.jsp',
            controller: 'gdModalInstanceCtrl',
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
            templateUrl: 'partials/modals/dashboardModal/gdDDTraining.jsp',
            controller: 'gdModalInstanceCtrl',
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
            templateUrl: 'partials/modals/dashboardModal/gdOverSpeed.jsp',
            controller: 'gdModalInstanceCtrl',
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
               
        case 5:  
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/gdAccidentAss.jsp',
            controller: 'gdModalInstanceCtrl',
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
    angular.module('efmfmApp').controller('gdModalCtrl', gdModalCtrl);
    
    var gdModalInstanceCtrl = function($scope, $modalInstance, $state, index, $http){   
         
         $scope.getLicenseDueData = function(){
        	 //alert("LicenseDueData");
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
					 tripType:"GOVERNANCEDRIVERS",
                     requestStatus:"licenseexpire"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
               //         alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });             
         };
         
         $scope.getMedicalDueData = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
					 tripType:"GOVERNANCEDRIVERS",
                     requestStatus:"medicalexpire"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                 //       alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });    
         };
         
         
        
         
         $scope.getPoliceVerification = function(){
             var data = {
                 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                 tripType:"GOVERNANCEDRIVERS",
                 requestStatus:"policeVarificationExp"
                };
             $http.post('services/dashboard/getDashBoardDetailList/',data).
                success(function(data, status, headers, config) {
                        $scope.gdPoliceVerificationData = data;
                 console.log(data);
                     }).
                 error(function(data, status, headers, config) {
                          // log error
                     });
         };
         
         $scope.getDDTraining = function(){
             var data = {
                 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                 tripType:"GOVERNANCEDRIVERS",
                 requestStatus:"ddtrainingexp"
                };
             $http.post('services/dashboard/getDashBoardDetailList/',data).
                success(function(data, status, headers, config) {
                        $scope.gdDDTrainingData = data;
                     }).
                 error(function(data, status, headers, config) {
                          // log error
                     });
         };
         
         
         $scope.getTotalSpeedAlertsData = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
					 tripType:"GOVERNANCEDRIVERS",
                     requestStatus:"speedalerts"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                   //     alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData=data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });    
         };
         
         $scope.getTotalAccidentData = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
					 tripType:"GOVERNANCEDRIVERS",
                     requestStatus:"accidentalerts"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                     //   alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });    
         };
         
  
         
          switch(index) {
            case 0:
                $scope.getLicenseDueData();
                break;
            case 1:
                $scope.getMedicalDueData();
                break;
            case 2:
                $scope.getPoliceVerification();
                break;
            case 3:
                $scope.getDDTraining();
                break;
            case 4:
                $scope.getTotalSpeedAlertsData();
                break;
            case 5:
                $scope.getTotalAccidentData();
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
    angular.module('efmfmApp').controller('gdModalInstanceCtrl', gdModalInstanceCtrl);

}());