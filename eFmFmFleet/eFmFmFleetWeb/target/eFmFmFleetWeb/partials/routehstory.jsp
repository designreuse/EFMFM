<%--     <style>
	#map-canvas {
        height: 500px;
        margin: 0px;
        padding: 0px
      }
      #panel {
        position: absolute;
        top: 115px;
        left: 50%;
        margin-left: -180px;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
      }
      .btn {
		filter: none;
		border: 0;
		padding: 7px 14px;
		font-family: "Segoe UI", Helvetica, Arial, sans-serif;
		font-size: 14px;
		color: #fff;
		cursor: pointer;
		outline: none;
		-webkit-box-shadow: none !important;
		-moz-box-shadow: none !important;
		box-shadow: none !important;
		-webkit-border-radius: 0 !important;
		-moz-border-radius: 0 !important;
		border-radius: 0 !important;
		-webkit-text-shadow: none;
		-moz-text-shadow: none;
		text-shadow: none;
		background: #0362fd !important;
		color: #fff!important;
		text-shadow: none!important;
		}
		
		.btn a {color:#fff!important;}
		.btn a:hover {text-decoration:none;}
    </style>


<!-- BEGIN PAGE CONTENT-->
<div class="row-fluid">
	<div class="span12">
		<div class="portlet box blue">
			<div class="portlet-title">
				<div class="caption"><i class="icon-globe"></i>Map</div>
				<div class="tools">
						<a href="javascript:;" class="collapse"></a>
						<a href="javascript:;" class="reload"></a>
						<a href="javascript:;" class="remove"></a>
				</div>
			</div>
			<div class="portlet-body">
			<div class="space5"></div>
				<div id="panel">    
    </div>
    <div id="map-canvas"></div>
    &nbsp;
    <div id="warnings_panel" style="width:100%;height:10%;text-align:center">
    <span id="optimalRoute" class="btn blue"> Optimal Route </span>
    <span id="actualRoute" class="btn blue" style="color:#fff"><a> Actual Route</a></span>
     <span id="allRoute" class="btn blue" style="color:#fff"><a> Both Route </a></span>
    
    </div>
			<div class="space5"></div>
			</div>
		</div> 
	</div>
</div>

<link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE CONTENT-->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?client=gme-newtglobalindiapvt&sensor=true&v=3.14"></script>
     <!-- <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script> -->
    <script>
    var directionsService = new google.maps.DirectionsService();
    
    var num, map, data,distlatlong,time,travalDist;
    var requestArray = [], renderArray = [];	     
    var jsonway={};
    var k=0;
    var refMarker=[];
    var currentLocationMarker;
	var colourArray =  ['blue'];
    function generateRequests(jsonArrays,pointsStatus,color,Flagoption){
        requestArray = [];     
        var jsonArray=jsonArrays;
        for (var route in jsonArray){
            var waypts = [];	           
            var start, finish
            var lastpoint
            data = jsonArray[route]            
            var str = data;
            var str_array = str.split('|');
            for(var i = 0; i < str_array.length; i++){
              str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");}             
            limit = str_array.length;
            data = str_array;
            for (var waypoint = 0; waypoint < limit; waypoint++) {
                if (data[waypoint] === lastpoint){                    
                    continue;
                }                 
                lastpoint = data[waypoint]
				if(pointsStatus)
					{
					
					waypts.push({
	                    location: data[waypoint],					
	                    stopover: true
	                });
				}
				else
					{

		               waypts.push({
		                    location: data[waypoint],					
		                    stopover: false
		                });
					
					}
        }

            // Grab the first waypoint for the 'start' location
            start = (waypts.shift()).location;
            // Grab the last waypoint for use as a 'finish' location
            finish = waypts.pop();
            if(finish === undefined){
                // Unless there was no finish location for some reason?
                finish = start;
            } else {
                finish = finish.location;
            }

			 			var request = {
			                 origin: start,
			                 destination: finish,
			                 waypoints: waypts,
			                 travelMode: google.maps.TravelMode.DRIVING
			             };
			 			//alert("route"+ route +"------lenth");
			             // and save it in our requestArray
			             requestArray.push({"route": route, "request": request});
            }

        processRequests(color,Flagoption);
    }

    function processRequests(color,suppressmarkers){

        // Counter to track request submission and process one at a time;
        var i = 0;

        // Used to submit the request 'i'
        function submitRequest(){			
            directionsService.route(requestArray[i].request, directionResults);
        }
        function directionResults(result, status) {
            if (status == google.maps.DirectionsStatus.OK) {
           	 
       	var secs=result.routes[0].legs[0].duration.value;
       			var hours = Math.floor(secs / (60 * 60));
           var divisor_for_minutes = secs % (60 * 60);
           var minutes = Math.floor(divisor_for_minutes / 60);
           var divisor_for_seconds = divisor_for_minutes % 60;
           var seconds = Math.ceil(divisor_for_seconds);
       	var timeHour="";
       	
       	if( hours !=0)
       		{	
       			//alert("hours"+hours);
       			timeHour=hours+"h";
       		}
        
              	  renderArray[i] = new google.maps.DirectionsRenderer();
                      renderArray[i].setMap(map);
                   renderArray[i].setOptions({
                    preserveViewport: true,
                    suppressMarkers :true,
                    suppressInfoWindows: false,
                    polylineOptions: {
                        strokeWeight: 5,
                        strokeOpacity: 3,
                        strokeColor: color
                    },
						/* markerOptions:{
							icon:{
								path: google.maps.SymbolPath.BACKWARD_CLOSED_ARROW,
								scale: 3,
								strokeColor: colourArray[i]
							}
						} */						
                });

                // Use this new renderer with the result
                renderArray[i].setDirections(result);
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
        
        

  
		}


		        </script>
<script>
var clientId = "<%=request.getSession().getAttribute("clientId")%>";
	 function getQueryVariable(variable)
	 {
	        var query = window.location.search.substring(1);
	        var vars = query.split("&");
	        for (var i=0;i<vars.length;i++) {
	                var pair = vars[i].split("=");
	                if(pair[0] == variable){return pair[1];}
	        }
	        return(false);
	 }

	 jQuery(document).ready(function() {
		 
	     //Optimal Route function definition & declaration
	     $("#optimalRoute").on('click', function(e){
	    	e.preventDefault();
	    	
	    	init();
	    	optimalRoute();
	     });
	     
	   //Actual Route function definition & declaration
	     $("#actualRoute").on('click', function(e){
	    	e.preventDefault();
	    	
	    	init();
	    	actualRoute();
	     });
	   
	   //Both Route function definition & declaration
	     $("#allRoute").on('click', function(e){
	    	e.preventDefault();
	    	init();
	    	optimalRoute();
	    	actualRoute();
	    	
	     });
	   
	     optimalRoute();
	    	actualRoute();
	   
	   
		
						});
			 		

			
		
   
	</script>
	<script>

	function actualRoute()	
	{
		
		var requestId = getQueryVariable("requestId");
		
		var jsonArrayss=[];
	 	 var URL = "services/history/actualtravelrout";	
		$.ajax({ 
				type: 'POST',
				contentType: "application/json; charset=UTF-8",
				url: URL,
				data: '{"requestId":"'+requestId+'"}',
				dataType: 'text',
				success: function(response, textStatus, jqXHR){
					
					var data = jQuery.parseJSON(response);
					var objLength = data.length;
			         for(var  i=0;i<objLength;i++){
			        	 jsonArrayss.push(data[i].route);
						}
			         generateRequests(jsonArrayss,true,"green",true);
			         var first  = data[0].route;
			         var second  = data[(objLength-1)].route;
			         
			         var firstPoint = first.split("|");
			         var secondPoint = second.split("|");
			         var data2 = firstPoint[0].split(",");
			         var data3 = secondPoint[secondPoint.length-1].split(",");

			         var image = 'images/wayptsicon/green_0.png';
			         var myLatlng = new google.maps.LatLng(data2[0],data2[1]);
		 			 var marker = new google.maps.Marker({
		 			      position: myLatlng,
		 			      map: map,
		 			     icon: image
		 			  });
		 			 var image2 = 'images/wayptsicon/green_1.png';
			         var myLatlng = new google.maps.LatLng(data3[0],data3[1]);
		 			 var marker = new google.maps.Marker({
		 			      position: myLatlng,
		 			      map: map,
		 				icon :image2 
		 			  });
			          
			        /*  var str_array = test.split('|');
		 			  
		 			  var icon=0;
		 			  for(var j =0; j<str_array.length;j++)
		 				  {
		 				  		icon =j;
		 				  		if(j==str_array.length-1)
		 				  			{
		 				  				icon=0;
		 				  			}
		 				  		
		 				  		var data = str_array[j].split(",");
		 				  }	 */
			          
			         

				}
					
				});
	 		
	}
	
	
	function optimalRoute()

	{
		var cabStartlati = getQueryVariable("cabstartlati");
	    var cabStartlogni = getQueryVariable("cabstartlongi");
		 var test  = cabStartlati+","+cabStartlogni+"|"+getQueryVariable("waypoints")+"|"+cabStartlati+","+cabStartlogni;
		    
		    
		    var jsonArrays2=[];
		    jsonArrays2.push(test);
	 		   generateRequests(jsonArrays2,true,"blue",false);
	 		  var str_array = test.split('|');
 			  var icon=0;
 			  for(var j =0; j<str_array.length;j++)
 				  {
 				  		icon =j;
 				  		if(j==str_array.length-1)
 				  			{
 				  				icon=0;
 				  			}
 				  		
 				  		var data = str_array[j].split(",");
 				  	
 				  	 var image = 'images/wayptsicon/'+icon+'.png';
 				  		var myLatlng = new google.maps.LatLng(data[0],data[1]);
			 			 var marker = new google.maps.Marker({
			 			      position: myLatlng,
			 			      map: map,
			 			     icon: image
			 			  });
 				  }
	}
	</script>
	<script>
	  function init() {		
		  
		  var zone = new google.maps.LatLng(13.052349, 80.251015);
		  var mapOptions = {
		    zoom: 13,
		    center: zone
		  }
	         map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);		
	     }

	     // Get the ball rolling and trigger our init() on 'load'
	     google.maps.event.addDomListener(window, 'load', init);	     
	</script>
	 --%>