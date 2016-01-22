(function(){
    
    var eFmFm = angular.module('efmfmApp', ['ui.router', 'ui.bootstrap', 'angularjs-dropdown-multiselect', 'ngProgress', 'ngAnimate','cgBusy', 'angularUtils.directives.dirPagination', 'angular-confirm', 'infinite-scroll']);
    
    eFmFm.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
            
            $urlRouterProvider.otherwise('/dashboard');
            //now setup the states
            $stateProvider           
            .state('home', {
                abstract: true,
                url: '',
                templateUrl: 'partials/main.jsp',
                controller: 'homeCtrl'})
            .state('home.dashboard', {
                url: '/dashboard',
                templateUrl: 'partials/home.dashboard.jsp',
                controller: 'dashCtrl',
                onEnter: function(){
                        $('.dashboardMenu').addClass('active');},  
                onExit: function() {
                        console.log("Exit :: Dashboard");
                        $('.dashboardMenu').removeClass('active');
                    }
            })
            .state('home.viewmap', {
                url: '/viewmap',
                templateUrl: 'partials/home.viewmap.jsp',
                controller: 'viewMapCtrl',
                onEnter: function(){
                        $('.veiwMapMenu').addClass('active');},  
                onExit: function() {
                		console.log("Exit :: Aproval");
                        $('.veiwMapMenu').removeClass('active');
                    }
            })
            .state('home.viewmap.showRoutes', {
                url: '/showRoutes',
                templateUrl: 'partials/home.viewmap.showAll.jsp',
                controller: 'allRoutesMapCtrl',
                onEnter: function(){
                        $('.veiwMapMenu').addClass('active');},  
                onExit: function() {
                		console.log("Exit :: Aproval");
                        $('.veiwMapMenu').removeClass('active');
                    }
            })
//            .state('home.viewmap.singleRoute', {
//                url: '/singleRoutes/:routeId',
//                templateUrl: 'partials/home.viewmap.singleRoute.jsp',
//                controller: 'allRoutesMapCtrl',
//                onEnter: function(){
//                        $('.veiwMapMenu').addClass('active');},  
//                onExit: function() {
//                		console.log("Exit :: Aproval");
//                        $('.veiwMapMenu').removeClass('active');
//                    }
//            })
            .state('home.approval', {
                url: '/approval',
                templateUrl: 'partials/home.approval.jsp',
                controller: 'approvalCtrl',
                onEnter: function(){
                        $('.approvalMenu').addClass('active');},  
                onExit: function() {
                		console.log("Exit :: Aproval");
                        $('.approvalMenu').removeClass('active');
                    }
            })
            .state('home.employeeTravelDesk', {
                url: '/employeeTravelDesk',
                templateUrl: 'partials/home.employeeTravelDesk.jsp',
                controller: 'empTravelDeskCtrl',
                onExit: function() {
                    $('.empTravelDeskMenu').removeClass('active');
                }})
            .state('home.servicemapping', {
                url: '/servicemapping',
                templateUrl: 'partials/home.servicemapping.jsp',
                controller: 'serviceMappingCtrl',
                onExit: function() {
                    console.log("EXIT SERVICE MAPPING");
           		 	$('.serviceMappingMenu').removeClass('active');}
            })
            .state('home.serviceRouteMap', {
                url: '/serviceRouteMap/:routeId/:waypoints/:baseLatLong',
                templateUrl: 'partials/home.serviceSingleRouteMap.jsp',
                controller: 'serviceRouteMapCtrl',
                onExit: function() {
                    console.log("EXIT SERVICE MAPPING");
           		 	$('.serviceMappingMenu').removeClass('active');}
            })
            .state('home.reports', {
                url: '/reports',
                templateUrl: 'partials/home.reports.jsp',
                controller: 'reportsCtrl',
                onExit: function() {
                    console.log("EXIT ALERTS");
           		 	$('.reportMenu').removeClass('active');}})
//            .state('home.history', {
//                url: '/history',
//                templateUrl: 'partials/home.history.jsp',
//                controller: 'historyCtrl'})
            .state('home.alerts', {
                url: '/alerts',
                templateUrl: 'partials/home.alerts.jsp',
                controller: 'alertsCtrl',
                onExit: function() {
                    console.log("EXIT ALERTS");
           		 	$('.alertsMenu').removeClass('active');}
            })
            .state('home.invoice', {
                url: '/invoice',
                templateUrl: 'partials/home.invoice.jsp',
                controller: 'invoiceCtrl'})
            .state('home.requestDetails', {
                url: '/requestDetails',
                templateUrl: 'partials/home.employeeRequestDetails.jsp',
                controller: 'requestDetailsCtrl',
                onExit: function() {
                    console.log("EXIT EMPLOYEE REQUEST DETAILS");
                    $('.employeeRequest').removeClass('active');
                }})
             .state('home.vendorDashboard', {
                url: '/vendorDashboard',
                templateUrl: 'partials/home.vendorDashboard.jsp',
                controller: 'vendorDashboardCtrl',
                onExit: function() {
                    console.log("EXIT EMPLOYEE DETAIL");
                    $('.vendorDashboard_admin').removeClass('active');
           		 	$('.admin_home').removeClass('active');
                }}) 
            .state('home.activeRouteMap', {
                url: '/activeRoute/:routeId',
                templateUrl: 'partials/home.routeMap.jsp',
                controller: 'routeCtrl',
                onExit: function() {
                    console.log("EXIT ROUTE MAP");
                    $('.veiwMapMenu').removeClass('active');
                }}) 
            .state('home.rescheduleRequest', {
                url: '/rescheduleRequest',
                templateUrl: 'partials/home.rescheduleRequest.jsp',
                controller: 'rescheduleRequestCtrl',
                onExit: function() {
                    console.log("EXIT EMPLOYEE DETAIL");
                    $('.rescheduleRequest_admin').removeClass('active');
           		 	$('.admin_home').removeClass('active');
                }})
             .state('home.empDetails', {
                url: '/empDetails',
                templateUrl: 'partials/home.empDetails.jsp',
                controller: 'empDetailCtrl',
                onExit: function() {
                    console.log("EXIT EMPLOYEE DETAIL");
                    $('.empDetail_admin').removeClass('active');
           		 	$('.admin_home').removeClass('active');
                }})            
                .state('home.myProfile', {
                url: '/myProfile',
                templateUrl: 'partials/home.myProfile.jsp',
                controller: 'myProfileCtrl',
                onExit: function() {
                    console.log("EXIT MY PROFILE");
                    $('.myProfile_admin').removeClass('active');
           		 	$('.admin_home').removeClass('active');
                }})                            
                .state('releasedHistory', {
                url: '/releasedHistory',
                templateUrl: 'partials/releasedHistory.jsp',                
                controller: 'releasedHistoryCtrl',
                onExit: function() {
                    console.log("EXIT Release History");
                }});
        });    
}());