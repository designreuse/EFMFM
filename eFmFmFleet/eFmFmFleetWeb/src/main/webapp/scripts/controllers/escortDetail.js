(function(){
   var escortDetailCtrl = function($scope, $stateParams){
      console.log( $stateParams.escortId);
         $scope.escortID = $stateParams.escortId;
    };    
    
    angular.module('efmfmApp').controller('escortDetailCtrl', escortDetailCtrl);
}());