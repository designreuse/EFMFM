<div class = "serviceMappingTemplate container-fluid">
 <div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-12 col-xs-12">Service Requested Routes</span>     
<!--
            {{routeId}}
            {{waypoints}}
            {{baseLatLong}}
-->
            
            <div class = "col-md-12 col-xs-12 mainTabDiv_serviceRouteMap" ng-if="isloaded">
                <div class = 'col-md-12'>
                <input class = 'btn btn-success backtoServiceMapping floatRight' type = 'button' 
                       value = 'Back to Service Mapping'
                       ng-click = 'backToServiceMapping()'>
            </div>
                
                <efmfm-google-map-single map-data = "getSingleServiceMap()" class = "mapContainer col-md-12" id = "map-canvas2"></efmfm-google-map-single>
            </div>

              
         </div>
</div>
</div> 
