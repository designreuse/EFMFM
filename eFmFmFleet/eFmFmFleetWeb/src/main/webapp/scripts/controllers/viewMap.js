(function(){
   var viewMapCtrl = function($scope, $http, $state, $interval, $rootScope){       
       $('.veiwMapMenu').addClass('active');
       $scope.dataLoaded = false;
       $scope.selectedRoute;
       $scope.cabLocation = [];
       $scope.singleMapData = {};
       $scope.mapData = [];      
       $scope.getMapData = function(){
//           $scope.progressbar.start();
           var data = {eFmFmClientBranchPO:{branchId:branchId}};
		   $http.post('services/view/livetrips/',data).
				success(function(data, status, headers, config) {  
                    $scope.mapData = data; 
                    console.log('Function getData() in Live Tracking - is called');
//                    alert(JSON.stringify(data));
               angular.forEach($scope.mapData, function(item){
//                   alert(item.baseLatLong);
                   $scope.cabLocation.push(item.currentCabLatiLongi);
               });
               $scope.dataLoaded = true;
                 }).
				 error(function(data, status, headers, config) {
				      // log error
				 }); 
//           $scope.progressbar.complete();
       };
       
       $scope.viewAllMapInterval = $interval($scope.getMapData, 4000);    
       //$scope.$on('$destroy', function () {$interval.cancel($scope.viewAllMapInterval);});
       var dereg = $rootScope.$on('$locationChangeSuccess', function() {
            $interval.cancel($scope.viewAllMapInterval);
            dereg();
        });  
       
       $scope.getMapData();         
       $state.go('home.viewmap.showRoutes'); 
       
       
       $scope.getMap = function(){    
        return $scope.mapData;
       };
       
       $scope.thisRouteDetails = function(index, route){
           $state.go('home.activeRouteMap', {'routeId' : route.routeId});
       };
       
       $scope.refreshViewMap = function(){
//           alert('refresh');
           $scope.getMapData();
       };
    };    
    
    angular.module('efmfmApp').controller('viewMapCtrl', viewMapCtrl);
}());