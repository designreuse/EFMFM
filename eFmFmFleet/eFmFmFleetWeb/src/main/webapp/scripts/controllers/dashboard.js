(function(){
   var dashCtrl = function($scope, $state, $http, $rootScope, $interval){ 
       if(userRole==='webuser'){
                $state.go('home.requestDetails');
            }
       else if(userRole==='manager'){
                $state.go('home.rescheduleRequest');
            }
       $scope.sosList = [];
       $scope.employeesDropList = [];
       $scope.employeesPickupList = [];
       $scope.vehicleStatusList = [];
       $scope.govStatusList = [];
       $scope.govVehicleList = [];      
       console.log("Enter :: Dashboard");
//       $('.dashboardMenu').addClass('active');
       $( "#sosMainDiv" ).draggable();
       $( "#edsMainDiv" ).draggable();
       $( "#epsMainDiv" ).draggable();        
       $( "#vehStatMainDiv" ).draggable();
       $( "#govDriverMainDiv" ).draggable();
       $( "#govVehMainDiv" ).draggable();      
       $scope.$on('$viewContentLoaded', function() {
            $('.dashboardMenu').addClass('active');            
           $scope.getDashBoardData();		   
		});
       
       $scope.getDashBoardData = function(){ 
           console.log("Inside dashCtrl::getDashBoardData()");
//           $scope.progressbar.start();
           var dataObj = {
        		   eFmFmClientBranchPO:{branchId:branchId}
				};	
           $http.post('services/dashboard/detail/',dataObj).
				success(function(data, status, headers, config) {
					//alert(JSON.stringify(data));
                    $scope.dashboardData = data;                  
                    $scope.sosList = [{label: 'SOS Alerts',
                                       progressBar: $scope.dashboardData.sosAlerts,
                                       counter : $scope.dashboardData.sosAlerts},
                                      {label: 'Road Alerts',
                                       progressBar: $scope.dashboardData.roadAlerts,
                                       counter : $scope.dashboardData.roadAlerts},
                                      {label: 'Alerts Open',
                                       progressBar: $scope.dashboardData.openAlerts,
                                       counter : $scope.dashboardData.openAlerts},
                                      {label: 'Alerts closed',
                                       progressBar: $scope.dashboardData.closeAlerts,
                                       counter : $scope.dashboardData.closeAlerts}];
       
                     $scope.employeesDropList = [{'icon':"icon-archive",
                                    'label': 'Total Planned Drops',
                                    'counter' : $scope.dashboardData.numberOfDropRequest},
                                   {icon:"icon-check",
                                    label: 'Employee Allocated To Cab',
                                    counter : $scope.dashboardData.dropScheduled},
                                   {icon:"icon-map-marker",
                                    label: 'Employee Dropped',
                                    counter : $scope.dashboardData.dropedEmployee},
                                   {icon:"icon-spinner",
                                    label: 'Employee No Show',
                                    counter : $scope.dashboardData.noShowDrop}];
        
                    $scope.employeesPickupList = [{icon:"icon-plus-sign",
                                    label: 'Total Planned Pickups',
                                    counter : $scope.dashboardData.numberOfPickUpRequest},
                                   {icon:"icon-spinner",
                                    label: 'Employee Allocated To Cab',
                                    counter : $scope.dashboardData.pickUpScheduled},
                                   {icon:"icon-check",
                                    label: 'Employee Picked',
                                    counter : $scope.dashboardData.boardedEmployee},                                                         
                                   {icon:"icon-spinner",
                                    label: 'Employee No Show',
                                    counter : $scope.dashboardData.noShowPickUp},
                                  ];
                    
                    
                    
                    $scope.guestReportStatusList = [
                								{icon:"icon-compass",
                                                    label: 'Total Planned Guest Requests',
                								    counter : $scope.dashboardData.numberOfGuestRequest},
                                                   {icon:"icon-check",
                	                                label: 'Guest Allocated To Cab',
                                                    counter : $scope.dashboardData.tripScheduledForGuest},                      
                                                   {icon:"icon-signin",
                                                    label: 'Guest Picked',
                                                    counter : $scope.dashboardData.boardedGuest},                      
                                                   {icon:"icon-spinner",
                                                   label: 'Guest No Show',
                                                    counter : $scope.dashboardData.noShowGuests}];
                        
                    
        
                    /*$scope.vehicleStatusList = [
								{icon:"icon-compass",
								    label: 'Total Registered Vehicles',
								    counter : $scope.dashboardData.regVehicleCount},
                                   {icon:"icon-check",
                                    label: 'Vehicles Available For Trip',
                                    counter : $scope.dashboardData.totalCheckedVehicle},                      
                                   {icon:"icon-signin",
                                    label: 'Drop Vehicles On Road',
                                    counter : $scope.dashboardData.vehicleForDrop},                      
                                   {icon:"icon-spinner",
                                    label: 'Pickup Vehicles On Road',
                                    counter : $scope.dashboardData.vehicleForPickUp}];
        */
                    $scope.govStatusList = [{icon:"icon-circle",
                                    label: 'License Due For Renewal',
                                    counter : $scope.dashboardData.licenseExpire},
                                   {icon:"icon-compass",
                                    label: 'Medical Fitness Due For Renewal',
                                    counter : $scope.dashboardData.medicalFitnessCertificateValid},
                                    {icon:"icon-compass",
                                    label: 'Police Verification Due For Renewal',
                                    counter : $scope.dashboardData.policeVarificationValid},
                                    {icon:"icon-compass",
                                    label: 'DD Training Due For Renewal',
                                    counter : $scope.dashboardData.ddTrainingValid}/*,
                                    {icon:"icon-compass",
                                     label: 'Total Over Speed Alerts',
                                     counter : $scope.dashboardData.speedAlert},
                                   {icon:"icon-truck",
                                    label: 'Total Accident Alerts',
                                    counter : $scope.dashboardData.accidentAlerts}*/];
        
                    $scope.govVehicleList = [{icon:"icon-circle",
                                    label: 'Pollution Due  For Renewal',
                                    counter : $scope.dashboardData.polutionExpired},
                                    {icon:"icon-compass",
                                     label: 'Insurance Due For Renewal',
                                     counter : $scope.dashboardData.insuranceExpired},
                                     {icon:"icon-compass",
                                      label: 'Tax Certificate Due For Renewal',
                                      counter : $scope.dashboardData.taxCertificateValid},
                                      {icon:"icon-compass",
                                      label: 'Permit Due For Renewal',
                                     counter : $scope.dashboardData.permitValid},/*
                                   {icon:"icon-compass",
                                    label: 'Vehicles <5 Breakdowns',
                                    counter : $scope.dashboardData.breakDownalert},*/                                    
                                    {icon:"icon-compass",
    								    label: 'Vehicles Needed Maintenance',
    								    counter : $scope.dashboardData.vehicleMaintenance}];
//               $scope.progressbar.complete();
				}).
				error(function(data, status, headers, config) {
					      // log error
				});
       };
       
       $scope.refreshDashboard = function(){
           $scope.getDashBoardData();
       };
       
       var dashboardInterval = $interval( function(){ $scope.getDashBoardData(); }, 5000);   
       $scope.$on('$destroy', function () {$interval.cancel(dashboardInterval);});
    };   
    angular.module('efmfmApp').controller('dashCtrl', dashCtrl);
}());