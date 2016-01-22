<!doctype html>
<html ng-app="efmfmApp">  
    <head>
        <meta charset="utf-8">
        <title>eFmFm - Enterprise Find Me Follow Me</title> 
        <link rel="icon" type="image/ico" href="images/favicon.ico" />
        <link href = "bower_components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href = "bower_components/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" href="styles/fonts/font.css" type="text/css">
        <link rel = "stylesheet" href = "bower_components/font-awesome/css/font-awesome.css" type="text/css">
        <link rel= "stylesheet" href = "styles/eFmFmStyle.css">
        <link rel= "stylesheet" href = "styles/loading.css">
        <link rel= "stylesheet" href = "styles/index2.css">
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?client=gme-skymapglobalindia&sensor=true&v=3.14"></script>
        <script src="bower_components/angular/angular.js"></script>
        <script src="bower_components/ngProgress/ngProgress.js"></script>
        <link rel="stylesheet" href="bower_components/ngProgress/ngProgress.css">
<!--        <link rel="stylesheet" href="bower_components/ngProgress/ngProgress.css">-->
        <script src="bower_components/pagination/dirPagination.js"></script>
        <link href="bower_components/ngSpinner/angular-busy.min.css" rel="stylesheet">
        <script src = "bower_components/jsPDF/jspdf.min.js"></script>
        <script src = "bower_components/jsPDF/jspdf.debug.js"></script>
        <script src = "bower_components/excelExport/alasql.min.js"></script>
        <script src = "bower_components/excelExport/xlsx.core.min.js"></script>
<!--
      <script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js" charset="utf-8"></script>
        <script type="text/javascript" src="https://rawgit.com/chinmaymk/angular-charts/bower/dist/angular-charts.min.js"></script>
-->
        
    </head> 
  <body ng-controller = "homeCtrl">
    <div ui-view></div>  
        <script src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
        <script src="bower_components/angular-bootstrap/ui-bootstrap-tpls2.js"></script>
        <script src="bower_components/jquery/dist/jquery.min.js"></script>   
        <script src="bower_components/jquery/dist/jquery.cookie.js"></script>   
        <script src="bower_components/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="bower_components/bootstrap/js/jasny-bootstrap.min.js"> </script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>     
        <script src="bower_components/underscore/underscore-min.js"></script> 
        <script src="bower_components/angular-animate/angular-animate.min.js"></script>
        <script src="bower_components/ngSpinner/angular-busy.min.js"></script>     
        <script src = 'bower_components/angular-confirm/angular-confirm.js'></script>    
        <script src = 'bower_components/ng-auto-scroll/ng-infinite-scroll.min.js'></script>
        <!-- app.js - have all the routing information  -->
        <script src="scripts/app.js"></script>
      
        <!--Controllers-->
        <script src="scripts/controllers/index2.js"></script>
        <script src="scripts/controllers/home.js"></script>
        <script src="scripts/controllers/dashboard.js"></script> 
        <script src="scripts/controllers/viewMap.js"></script>
        <script src = "scripts/controllers/approval.js"></script>
        <script src = "scripts/controllers/serviceMapping.js"></script>
        <script src="scripts/controllers/serviceRouteMap.js"></script>
        <script src = "scripts/controllers/invoice.js"></script>
        <script src = "scripts/controllers/employeeRequestDetails.js"></script>
        <script src="scripts/controllers/alerts.js"></script>
        <script src = "scripts/controllers/vendorDashboard.js"></script>
        <script src = "scripts/controllers/routeMap.js"></script>
        <script src = "scripts/controllers/allRouteMap.js"></script>
        <script src = "scripts/controllers/employeeTravelDesk.js"></script>
        <script src = "scripts/controllers/rescheduleRequest.js"></script>
        <script src = "scripts/controllers/empDetails.js"></script>
        <script src = "scripts/controllers/myProfile.js"></script>
        <script src="scripts/controllers/reports.js"></script>    
        <script src="scripts/controllers/edsModal.js"></script>   
        <script src="scripts/controllers/sosModal.js"></script> 
        <script src="scripts/controllers/epsModal.js"></script>        
        <script src="scripts/controllers/vsModal.js"></script>       
        <script src="scripts/controllers/gdModal.js"></script>     
        <script src="scripts/controllers/gvModal.js"></script>    
        <script src="scripts/controllers/importData.js"></script>
        <script src="scripts/controllers/gsModal.js"></script>
        <script src="scripts/controllers/releasedHistory.js"></script>
      
      
      
        <!--Services-->
        <script src="scripts/services/empDetails.js"></script>
        <script src="scripts/services/alerts.js"></script>
        <script src = "scripts/services/invoiceService.js"></script>
        
<!--        <script src = "scripts/controllers/importEmpModal.js"></script>-->
      
        <!--Directives-->
        <script src="scripts/directives/efmfmbutton.js"></script>   
        <script src="scripts/directives/href.js"></script>  
        <script src="scripts/directives/efmfmShowAllLiveTrips.js"></script>    
        <script src="scripts/directives/efmfmSingleLiveTrip.js"></script>
        <script src="scripts/directives/efmfmGoogleMapSingle.js"></script>
        <script src="scripts/directives/efmfmMapLocation.js"></script> 
        <script src="scripts/directives/cleanInputField.js"></script> 
        <script src="scripts/directives/efmfmNewUserMapLocation.js"></script>
        <script src="scripts/directives/efmfmNewUserMapSearchLocation.js"></script>
        <script src="scripts/directives/checkboxGroup.js"></script>
        <script src="scripts/directives/angularjs-dropdown-multiselect.js"></script>
        <script src="scripts/directives/ngBlur.js"></script>
<!--        <script src="scripts/directives/efmfmRoutes.js"></script>     -->
<!--        <script src="scripts/directives/efmfmServiceMap.js"></script>-->
        <!--Filters-->
<!--        <script src="scripts/filters/searchBetween.js"></script>    -->
      <script src="scripts/filters/alertSearch.js"></script>  
         <script type="text/javascript">
		<%
		int timeout = session.getMaxInactiveInterval();
		response.setHeader("Refresh", timeout + "; URL = index");
		%>
		var firstName = "<%=request.getSession().getAttribute("firstName")%>";
		var lastName = "<%=request.getSession().getAttribute("lastName")%>";
		var branchId = "<%=request.getSession().getAttribute("branchId")%>";
		var profileId = "<%=request.getSession().getAttribute("profileId")%>";
		var userRole ="<%=request.getSession().getAttribute("role")%>"; 
//		alert(profileId);
//		$("#_profileName").text(firstName+" "+lastName);
//		$("#product_name").text(productName);
		/* jQuery(document).ready(function() {
		alert("firstName"+firstName);	
		alert("lastName"+lastName);	
		alert("clientId"+clientId);	
		alert("profileId"+profileId);	
		alert("userRole"+userRole);	

		}); */
		</script> 
      
      <script type = "text/javascript">function demoFromHTML() {
    var pdf = new jsPDF('p', 'pt', 'letter');
    // source can be HTML-formatted string, or a reference
    // to an actual DOM element from which the text will be scraped.
    source = $('#customers')[0];

    // we support special element handlers. Register them with jQuery-style 
    // ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
    // There is no support for any other type of selectors 
    // (class, of compound) at this time.
    specialElementHandlers = {
        // element with id of "bypass" - jQuery style selector
        '#bypassme': function (element, renderer) {
            // true = "handled elsewhere, bypass text extraction"
            return true
        }
    };
    margins = {
        top: 80,
        bottom: 60,
        left: 40,
        width: 522
    };
    // all coords and widths are in jsPDF instance's declared units
    // 'inches' in this case
    pdf.fromHTML(
    source, // HTML string or DOM elem ref.
    margins.left, // x coord
    margins.top, { // y coord
        'width': margins.width, // max width of content on PDF
        'elementHandlers': specialElementHandlers
    },

    function (dispose) {
        // dispose: object with X, Y of the last line add to the PDF 
        //          this allow the insertion of new lines after html
        pdf.save('Test.pdf');
    }, margins);
}
                               </script>
      
    </body>
</html>