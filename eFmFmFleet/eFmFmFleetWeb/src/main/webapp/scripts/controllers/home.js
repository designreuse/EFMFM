/*
*CLIENT : SHELL
*/
(function(){
    var homeCtrl = function($scope, $modal, $log, $state, $http, $location, $timeout, ngProgressFactory){
        
       $scope.adminRole=false;
       $scope.supervisorRole = false;
       $scope.managerRole = false;
       $scope.webUserRole = false;       
       $scope.IntegerNumber = /^\d+$/;
       $scope.regExName = /^[A-Za-z]+$/;
       $scope.NoSpecialCharacters = /^[a-zA-Z0-9]*$/;
       $scope.regexDecimalNumbers = /^[0-9]+(\.[0-9]{2})?$/;
       $scope.alertMessage;
       $scope.alertHint;
       
       //Loading Spinner    
       $scope.$on('LOAD', function(){$scope.isProcessing = true;});
       $scope.$on('UNLOAD', function(){$scope.isProcessing = true;});
       //Progress Bar    
       $scope.progressbar = ngProgressFactory.createInstance();
       $scope.progressbar.setHeight('5px');
       $scope.progressbar.setColor('#ff3300');
        
       //**To Remove Class ACTIVE when another Nav Button is clicked
       $(".nav a").on("click", function(){
           $(".nav").find(".active").removeClass("active");
           $(this).parent().addClass("active");
        });
        
        $scope.getActiveClassStatus = function(viewLocation){
            return viewLocation === $location.path();
        };
        
        $scope.$on('$viewContentLoaded', function() {         
            if(userRole=='admin'){$scope.adminRole=true;}
            if(userRole=='supervisor'){$scope.supervisorRole=true;}
            if(userRole=='manager'){$scope.managerRole = true;}
            if(userRole=='webuser'){$scope.webUserRole = true;}
        });
//        alert(userRole);
       //To make the Nav Bar FIX when User Tries to to Scroll the Page down     
       $(window).bind('scroll', function () {
          if ($(window).scrollTop() > 50) {
              $('.navBar_home').addClass('navbar-fixed-top');
           }
           else{
              $('.navBar_home').removeClass('navbar-fixed-top');
            }
        }); 
        
        //Show Custome Alert Messages
        $scope.showalertMessage = function(message, hint){
            $scope.alertMessage = message;
            $scope.alertHint = hint;
            $('.loadingMain').show();
            $('.alert_TravelDesk').show('slow', function(){
                       $timeout(function() {$('.alert_TravelDesk').fadeOut('slow');
                                            $('.loadingMain').hide();}, 3000);
            });            
        };
        
        //BUTTON ACTION :: CLOSE ALERT
        $scope.closeAlertMessage = function(){
            $('.loadingMain').hide();
            $('.alert_TravelDesk').hide('slow');  
            $scope.alertMessage = '';
            $scope.alertHint = '';          
        }        
       

       //Format of the Calenders Used in all the Children Views
       $scope.format = 'dd-MM-yyyy';
       $scope.dateOptions = {formatYear: 'yy',
                             startingDay: 1,
                              showWeeks: false,
                             };

        $scope.isOpen = false;
        $scope.openMenu = function(){
            if(!$scope.isOpen){$scope.isOpen = true;}
            else $scope.isOpen = false;           
        };
                    
       $scope.logout = function(){
         alert("Log out Clicked");
       }; //End Of FUNCTION   
        
};    
    angular.module('efmfmApp').controller('homeCtrl', homeCtrl);
  
}());