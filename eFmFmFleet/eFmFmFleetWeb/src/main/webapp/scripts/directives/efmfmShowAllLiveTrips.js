(function(){
    
    var efmfmShowAllLiveTrips = function($http, $interval, $rootScope){
        return{
            restrict: 'E',
            template: '<div></div>',
            replace:true,           
            link:function(scope, element, attrs){
            if(scope.mapData.length!=0)
            {                
                var directionsService = new google.maps.DirectionsService();
                var num, map, data;
                var requestArray = [], renderArray = [];
                var baseLocation = [];
                var image = 'images/cabImage.png';
                var markers = [];

        // 16 Standard Colours for navigation polylines
        var colourArray = ['navy', 'fuchsia', 'black', 'white', 
                           'lime', 'maroon', 'purple', 'aqua', 
                           'red', 'green', 'silver', 'olive', 
                           'blue', 'yellow', 'teal', 
                           'grey','navy','fuchsia', 
                           'black', 'white', 'lime', 'maroon', 
                           'purple', 'aqua', 'red', 'green', 'silver', 
                           'olive', 'blue', 'yellow', 'teal', 'grey'];

          
                scope.getMapData2 = function(){
                           console.log('HITTTIINNG ViewAllGoogleMap Directive');
                    scope.cabLocation = [];
                       var data = {eFmFmClientBranchPO:{branchId:branchId}};
                       $http.post('services/view/livetrips/',data).
                            success(function(data, status, headers, config) { 
                                //scope.mapData deals with all the location of the employee on different route
                                scope.mapData = data; 
                                //scope.mapData2 deals with the location of Cab Only.
                                scope.mapData2 = data; 
            //                    alert(JSON.stringify(data));
                                angular.forEach(scope.mapData2, function(item){       
                                    scope.cabLocation.push(item.currentCabLatiLongi);
                                    console.log(item.currentCabLatiLongi);
                                });
                                scope.dataLoaded = true;                       
                             }).
                             error(function(data, status, headers, config) {
                                  // log error
                             });          
                };

              scope.watchCabsLocation = function(){
                  console.log('Cab Location Array Length:: ' + scope.cabLocation.length);
                  for (r = 0; r<scope.mapData2.length; r++){
                      cab = scope.cabLocation[r].split(',');
                      markers[r].setPosition( new google.maps.LatLng(cab[0], cab[1]) );
                  }
              }            

            var xx = $interval(scope.getMapData2, 5000); 
            var yy = $interval(scope.watchCabsLocation, 5000); 

            var dereg1 = $rootScope.$on('$locationChangeSuccess', function() {
                $interval.cancel(xx);
                $interval.cancel(yy);
                dereg1();
            });                


            // Let's make an array of requests which will become individual polylines on the map.
            function generateRequests(){
                requestArray = [];
                for (var route in scope.mapData){ 
                     var cab = scope.cabLocation[route].split(',');
                    var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(cab[0],cab[1]),
                    map: map,
                    icon:image
                    });

                    markers.push(marker);
                    // This now deals with one of the people / routes
                    console.log(route);

                    //start and finish points
                    baseLocation = scope.mapData[route].baseLatLong.split(',');
                    var originLocation = new google.maps.LatLng(baseLocation[0], baseLocation[1]);
                    var destinLocation = new google.maps.LatLng(baseLocation[0], baseLocation[1]);
                    console.log("Base Location " + originLocation+", "+destinLocation);

                    //way points:
                    var cords = scope.mapData[route].waypoints.split('|');
                    console.log(cords[0]+", " +cords[1]);
                    for(var i = 0; i < cords.length; i++){
                          if(cords[i] == null || cords[i] ==""){
                              cords.splice(i, 1);
                          }
                        else cords[i] = cords[i].replace(/^\s*/, "").replace(/\s*$/, "");
                    } 
                    var limit = cords.length;
                    console.log("Length of each cords array is : " + limit);
                    // Somewhere to store the wayoints
                    var waypts = [];
                    for (var x = 0; x < limit; x++) {
                        console.log("Waypoint HELLO " + cords[x]);
                        waypts.push({
                            location: cords[x],
                            stopover: true
                        });            
                    }
                    // Let's create the Google Maps request object
                    var request = {
                        origin: originLocation,
                        destination: destinLocation,
                        waypoints: waypts,
                        optimizeWaypoints:false,
                        travelMode: google.maps.TravelMode.DRIVING
                    };

                    // and save it in our requestArray
                    requestArray.push({"route": route, "request": request});
                }//routes loop in mapDATA ENDS******************

                processRequests();
            } //generateRequests() ENDING***************

            function processRequests(){

                // Counter to track request submission and process one at a time;
                var i = 0;

                // Used to submit the request 'i'
                function submitRequest(){
                    directionsService.route(requestArray[i].request, directionResults);
                }

                // Used as callback for the above request for current 'i'
                function directionResults(result, status) {
                    if (status == google.maps.DirectionsStatus.OK) {

                        // Create a unique DirectionsRenderer 'i'
                        renderArray[i] = new google.maps.DirectionsRenderer({suppressMarkers: true});
                        renderArray[i].setMap(map);

                        // Some unique options from the colorArray so we can see the routes
                        renderArray[i].setOptions({
                            preserveViewport: true,
        //                    suppressMarkers: true,
                            suppressInfoWindows: false,
                            polylineOptions: {
                                strokeWeight: 3,
                                strokeOpacity: 0.8,
                                strokeColor: colourArray[i]
                            },
                            markerOptions:{                        
        //                        icon:{
        //                            path: google.maps.SymbolPath.CIRCLE,
        //                            scale: 4,
        //                            strokeColor: colourArray[i]
        //                        }
                           }
                        });

                        // Use this new renderer with the result
                        renderArray[i].setDirections(result);

                        //##########################################################

                        var l = result.routes[0];
                        var x1 = l.legs.length;
                        //Drawing Custom Markers on each Route
                        for(m=1; m<x1; m++){ 
                             var leg = result.routes[ 0 ].legs[ m ];
                             var empImage = 'images/EmployeeMapIcons/emp_'+m+'.png'; //'images/emloyeeSmall.jpg';
                             var title = 'title';
                             new google.maps.Marker({
                              position: leg.start_location,
                              map: map,
                              icon: empImage,
                              title: leg.start_address
                             });
                        }                
                

                        //##############################################################
                        // and start the next request
                        nextRequest();
                    }

                }        

                function nextRequest(){
                    // Increase the counter
                    i++;
                    // Make sure we are still waiting for a request
                    if(i >= requestArray.length){
                        // No more to do
                        return;
                    }
                    // Submit another request
                    submitRequest();
                }

                // This request is just to kick start the whole process
                submitRequest();
            }  //End of processRequests()  

                // Some basic map setup (from the API docs) 
                var center = new google.maps.LatLng(12.973802,80.244119);
        //        var center = new google.maps.LatLng(12.844873,80.226535);
                var mapOptions = {
                    center: center,
                    zoom: 11,
                    mapTypeControl: false,
                    streetViewControl: false,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                map = new google.maps.Map(document.getElementById(attrs.id), mapOptions);        
                // Start the request making
                generateRequests();  
                //Traffic Layer
                var trafficLayer = new google.maps.TrafficLayer();
                trafficLayer.setMap(map);
                
            }//END of if(scope.mapData.length!=0) 
            else{
                var center = new google.maps.LatLng(12.973802,80.244119);
//                var center = new google.maps.LatLng(12.844873,80.226535);
                var mapOptions = {
                center: center,
                zoom: 11,
                mapTypeControl: false,
                streetViewControl: false,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
				icon:image
                };            
                map = new google.maps.Map(document.getElementById(attrs.id), mapOptions);  
                //Traffic Layer
                var trafficLayer = new google.maps.TrafficLayer();
                trafficLayer.setMap(map);
            }
            
            }//****END OF LINK
            
        };
        
    };

 angular.module('efmfmApp').directive('efmfmShowAllLiveTrips', efmfmShowAllLiveTrips);
}());