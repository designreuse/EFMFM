(function(){
   var serviceRouteMapCtrl = function($scope, $stateParams, $state, $http){
       $('.serviceMappingMenu').addClass('active');
       $scope.isloaded = false;
       $scope.singleServiceData;
       $scope.routeId = $stateParams.routeId; 
       $scope.waypoints = $stateParams.waypoints;
       $scope.baseLatLong = $stateParams.baseLatLong;      
       $scope.singleServiceData = [{'waypoints':$scope.waypoints, 'baseLatLong':$scope.baseLatLong}];
        $scope.isloaded = true;
       
       $scope.getSingleServiceMap = function(){ 
           return $scope.singleServiceData;
       };   
       
       $scope.backToServiceMapping = function(){
           $state.go('home.servicemapping');
       };
       
       
    };    
    
    angular.module('efmfmApp').controller('serviceRouteMapCtrl', serviceRouteMapCtrl);
}());