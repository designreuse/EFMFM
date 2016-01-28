<!--
<!DOCTYPE html>
<html>
  <head>
    <style>
      html, body, #map-canvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }   
    </style>
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?client=gme-skymapglobalindia&sensor=true&v=3.14"></script>
    <script>
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var map;
function initialize() {
  directionsDisplay = new google.maps.DirectionsRenderer();
  var center = new google.maps.LatLng(12.849456, 80.225462);
  var mapOptions = {
    zoom:9,
    center: center
  };
  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
  directionsDisplay.setMap(map);
}
google.maps.event.addDomListener(window, 'load', initialize);
</script>
  </head>
  <body>
    <div id="map-canvas"></div>
  </body>
</html>-->
