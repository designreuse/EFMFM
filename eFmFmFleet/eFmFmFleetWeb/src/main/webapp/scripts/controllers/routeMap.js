(function(){
   var routeCtrl = function($scope, $stateParams, $state, $http, $interval, $rootScope){    
       $('.veiwMapMenu').addClass('active');
       $scope.singleDataLoaded = false;
       $scope.routeId = $stateParams.routeId; 
       $scope.singleMapData;
       $scope.cabLocation;
       $scope.getMapData = function(){       
//           console.log('Hit');
           var data = {
        		   eFmFmClientBranchPO:{branchId:branchId},
                   assignRouteId:$scope.routeId        
           }; 
           $http.post('services/view/individualtrip/',data).
                 success(function(data, status, headers, config) {
 //               	 alert(JSON.stringify(data));
               console.log('HITTTIINNG routeMap.js');
//                   $scope.singleRouteData = data;
                   $scope.mapData = data;
//               alert(JSON.stringify($scope.mapData));
                   $scope.expectedTime = $scope.mapData[0].expectedTime;
                   $scope.speed = $scope.mapData[0].speed;
                   $scope.plannedDistance = $scope.mapData[0].plannedDistance;
                   $scope.travelledDistance = $scope.mapData[0].travelledDistance;
               
                   $scope.cabLocation = $scope.mapData[0].currentCabLatiLongi;
//                    console.log('$scope.cabLocation : ' + $scope.cabLocation);
                   $scope.singleDataLoaded = true;
                     }).
                     error(function(data, status, headers, config) {
                          // log error
                     });          
       };
       
       $scope.getMapData();              
       
       $scope.getSingleMap = function(){
           return $scope.mapData;
       };
       
       $scope.showAll = function(){
           $state.go('home.viewmap');
           $interval.cancel($scope.routeMapInterval);
       };
       
       $scope.routeMapInterval = $interval($scope.getMapData, 4000);
       
       $scope.$watch(
                    function( $scope ) {
                        console.log( "Function watched" );
                        return $scope.cabLocation;
                    },
                    function( newValue, oldvalue ) {
                        if(oldvalue!==newValue){
                        }
                    }
                );       
       
       $scope.$watch(
                    function( $scope ) {
                        console.log( "Function watched" );
                        return $scope.expectedTime;
                    },
                    function( newValue, oldvalue ) {
                        if(oldvalue!==newValue){
                             $scope.mapData[0].expectedTime = newValue;
                        }
                    }
                );       
       
       $scope.$watch(
                    function( $scope ) {
                        console.log( "Function watched" );
                        return $scope.speed;
                    },
                    function( newValue, oldvalue ) {
                        if(oldvalue!==newValue){
                             $scope.mapData[0].speed = newValue;
                        }                        
                    }
                );       
       
       $scope.$watch(
                    function( $scope ) {
                        console.log( "Function watched" );
                        return $scope.plannedDistance;
                    },
                    function( newValue, oldvalue ) {
                        if(oldvalue!==newValue){
                             $scope.mapData[0].plannedDistance = newValue;
                        }
                    }
                );       
       
       $scope.$watch(
                    function( $scope ) {
                        console.log( "Function watched" );
                        return $scope.travelledDistance;
                    },
                    function( newValue, oldvalue ) {
                        if(oldvalue!==newValue){
                             $scope.mapData[0].travelledDistance = newValue;
                        }                        
                    }
                );
       
    };    
    
    angular.module('efmfmApp').controller('routeCtrl', routeCtrl);
}());