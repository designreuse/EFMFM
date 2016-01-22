(function(){
    var sosModalCtrl = function($scope, $modal, $log, $http){
       $scope.openSOSModel = function(index, size){
       console.log("Row clicked " + index)
       switch(index){
         case 0:
            var modalInstance = $modal.open({
            templateUrl: 'partials/modals/dashboardModal/sosAlerts.jsp',
            controller: 'sosModalInstanceCtrl',
            size:size,
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
            templateUrl: 'partials/modals/dashboardModal/sosRoadAlerts.jsp',
            controller: 'sosModalInstanceCtrl',
            size:size,
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
            templateUrl: 'partials/modals/dashboardModal/sosOpenAlerts.jsp',
            controller: 'sosModalInstanceCtrl',
            size:size,
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
            templateUrl: 'partials/modals/dashboardModal/sosCloseAlerts.jsp',
            controller: 'sosModalInstanceCtrl',
            size:size,
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
    angular.module('efmfmApp').controller('sosModalCtrl', sosModalCtrl);
    
     var sosModalInstanceCtrl = function($scope, $modalInstance, $state, $http, $timeout, index){   
         $scope.sosRoadAlertsData = [];
         $scope.showActionDiv = false;
         $scope.action = {};       
         
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
         
         $scope.getSOSAlertData = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                       tripType:"ALERTS",
                           requestStatus:"sos"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                       // alert(JSON.stringify(data));
                        $scope.sosAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });             
         };
         
         $scope.getRoadAlerts = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                       tripType:"ALERTS",
                        requestStatus:"roadalerts"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                       // alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });    
         };        
         
         
         $scope.getOpenAlerts = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                       tripType:"ALERTS",
                           requestStatus:"openalerts"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
                        //alert(JSON.stringify(data));
                        $scope.sosOpenAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });    
         };
         
         $scope.getCloseAlerts = function(){
             var data = {
            		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}}},
                       tripType:"ALERTS",
                           requestStatus:"closealerts"
                };
               $http.post('services/dashboard/getDashBoardDetailList/',data).
                     success(function(data, status, headers, config) {
 //                       alert(JSON.stringify(data));
                        $scope.sosRoadAlertsData = data;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });    
         };
         
//         $scope.getOpenAlerts = function(){
//             var data = {
//            		 eFmFmClientBranchPO:{branchId:branchId},
//                       tripType:"ALERTS",
//                           requestStatus:"openalerts"
//                };
//               $http.post('services/dashboard/getDashBoardDetailList/',data).
//                     success(function(data, status, headers, config) {
// //                       alert(JSON.stringify(data));
//                        $scope.sosRoadAlertsData = data;
//                     }).
//                     error(function(data, status, headers, config) {
//                          // log error
//                     });    
//         };
         
         
         
         
          switch(index) {
            case 0:
                $scope.getSOSAlertData();
                break;
            case 1:
                $scope.getRoadAlerts();
                break;
            case 2:
                $scope.getOpenAlerts();
                break;
            case 3:
                $scope.getCloseAlerts();
                break;
        }
         
        $scope.openAction = function(openAlert){  
            $('.loading').show();
            if($scope.showActionDiv){
                $scope.showActionDiv = false;
            }
            else {
                $scope.showActionDiv = true;
                $('.loading').show();
                $scope.action.text = openAlert.description;
                $scope.action.alertId = openAlert.tripAlertId;
                $scope.action.assignRouteId = openAlert.assignRouteId;
                $scope.selectedAlert_DriverName = openAlert.driverName;
                $scope.selectedAlert_DriverNumber = openAlert.driverNumber;
                $scope.selectedAlert_AlertId = openAlert.tripAlertId;
                $scope.selectedAlert_AlertType = openAlert.alertType;
                $scope.selectedAlert_Title = openAlert.tittle;
                $scope.selectedAlert_vehicleNumber = openAlert.vehicleNumber;
            }
            //alert('Button Clicked');            
        };
         
        $scope.ok = function () {            
            
            //put the values from text box in a variable
            //check the Login userId and Password
            //if the Login is success then close the Modal and proceed to Home Page
            $modalInstance.close();
//            $state.go('home.dashboard');
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
         
        $scope.updateAlert = function(action){       	
            var data = {
            		branchId:branchId,
           		    assignRouteId:action.assignRouteId,
           		    tripAlertsId:action.alertId,
                    alertClosingDescription:action.text
               };
              $http.post('services/dashboard/updateAlertDesc/',data).
                    success(function(data, status, headers, config) {
                    	//alert("Alert Updated");
                    	   var openAlertIndex = _.findIndex($scope.sosOpenAlertsData, { tripAlertId: action.alertId });           
                           $scope.sosOpenAlertsData[openAlertIndex].description = action.text;
                           $scope.showActionDiv = false;
                           $('.loading').hide();  
                           $scope.showalertMessageModal("The Alert has been updated.", "");                  
                           
                    }).
                    error(function(data, status, headers, config) {
                         // log error
                    });    
                    
        };
         
        $scope.closeAlert = function(action){ 
            var data = {
            		branchId:branchId,
           		    assignRouteId:action.assignRouteId,
           		    tripAlertsId:action.alertId,
                    alertClosingDescription:action.text
               };
              $http.post('services/dashboard/closeOpenAlert/',data).
                    success(function(data, status, headers, config) {
                    	//alert("Alert Closed");
                       $scope.showActionDiv = false;
                       $('.loading').hide();
                       $('.row'+action.alertId).hide('slow');                   
                       $scope.showalertMessageModal("The Alert has been close.", "");
                    }).
                    error(function(data, status, headers, config) {
                         // log error
                    }); 
           
        };
         
        $scope.cancelOpenAlertModal = function(){
            $scope.showActionDiv = false;
            $('.loading').hide();
        };
    }; 
    angular.module('efmfmApp').controller('sosModalInstanceCtrl', sosModalInstanceCtrl);

}());