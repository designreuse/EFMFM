(function(){
//   var index2Ctrl = function($scope, $state, $modal, $log){
       
     $(".hidebutton").trigger('click');
       
     $(".empApp").hover(function(){
        $(".labelModuleIndexEmp").css("background","#08c")},function(){ $(".labelModuleIndexEmp").css("background","#fcfcfc")});
     $(".empApp").hover(function(){
        $(".labelModuleIndexEmp>div").css("color","#fff")},function(){ $(".labelModuleIndexEmp>div").css("color","#000")});
       
     $(".escortApp").hover(function(){
        $(".labelModuleIndexEscort").css("background","#08c")},function(){ $(".labelModuleIndexEscort").css("background","#fcfcfc")});
     $(".escortApp").hover(function(){
        $(".labelModuleIndexEscort>div").css("color","#fff")},function(){ $(".labelModuleIndexEscort>div").css("color","#000")});
       
       $(".adminApp").hover(function(){
        $(".labelModuleIndexAdmin").css("background","#08c")},function(){ $(".labelModuleIndexAdmin").css("background","#fcfcfc")});
       $(".adminApp").hover(function(){
        $(".labelModuleIndexAdmin>div").css("color","#fff")},function(){ $(".labelModuleIndexAdmin>div").css("color","#000")});
       
//   $('.test').on('click',function(){
//       alert('Hi');
//   });
//    $scope.login = function(){
////            alert("User ID: " + $scope.userId + "Password : " + $scope.password);
//        
//        //if everything is ok then execute the following code
//        $('.modal-backdrop').remove();
////        $('#loginModal').modal('hide').on('hidden.bs.modal', functionThatEndsUpDestroyingTheDOM);
////        $('body').removeClass('modal-open');
//            $state.go('home');
//        }
       
//**To Remove Class ACTIVE when another button is clicked
//   $(".nav a").on("click", function(){
//       $(".nav").find(".active").removeClass("active");
//       $(this).parent().addClass("active");
//   });

////**FUNCTION TO SHOW SEARCH TEXT BOX********************  
//       $scope.showSerchTextBox = function(){
////           $scope.showTextBox = true;
//           
//           $('.search').fadeOut('slow', function(){
//               $('.searchTexBox').fadeIn('slow');
//           });
//       };
       
////**FUNCTION TO OPEN LOGIN MODAL********************      
//   $scope.openLoginModal = function(){
//
//      var modalInstance = $modal.open({
//
//        templateUrl: 'partials/login_modal.html',
//        controller: 'ModalInstanceCtrl',
//        resolve: {}
//      });
//
//     modalInstance.result.then(function (){}, function(){
//          $log.info('Modal dismissed at: ' + new Date());
//     });   
//  };
//        
//        $scope.goToFleetManagment = function(){
//            $state.go('fleetmanagement');
//        }         
//    };    
//    angular.module('efmfmApp').controller('index2Ctrl', index2Ctrl);
    
//    var ModalInstanceCtrl = function($scope, $modalInstance, $state){
//        
//        $scope.ok = function () {            
//            
//            //put the values from text box in a variable
//            //check the Login userId and Password
//            //if the Login is success then close the Modal and proceed to Home Page
//            $modalInstance.close();
//            $state.go('home.dashboard');
//        };
//
//        $scope.cancel = function () {
//            $modalInstance.dismiss('cancel');
//        };
//    }; 
//    angular.module('efmfmApp').controller('ModalInstanceCtrl', ModalInstanceCtrl);
}());