<div class = "gdModal container-fluid">
    <div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-circle edsModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">License Due For Renewal</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>  
    <div class = "row">
        <div class="modal-header2 col-md-12">
            <form><input class = "searchModal" 
                         ng-model = "searchRoadAlerts"
                         type="text" 
                         class="form-control" 
                         placeholder="Search..." 
                         id="formGroupInputSmall"></form>
        </div>
    </div>
    
<div class="modal-body modalMainContent">
    <table class="table table-bordered table-hover table-responsive container-fluid dashboardTable">
        <thead class ="tableHeading">
            <tr>
		      <th>Vendor Name</th>
		      <th>Vendor ContactName</th>
              <th>Vendor MobileNumber</th>
		      <th>Driver Name</th>
		      <th>Mobile Number</th>
		      <th>Licence Number</th>
		      <th>Licence Exp Date</th>
		      <!-- <th>Description</th> -->
            </tr> 
        </thead>
        <tbody ng-show = "sosRoadAlertsData.length==0">
              <tr>
                 <td colspan = '6'>
                   <div class = "noData">No Employee Dropped</div>
                 </td>
              </tr>
        </tbody>
        <tbody ng-show = "sosRoadAlertsData.length>0">
            <tr ng-repeat = "roadAlert in sosRoadAlertsData | filter: searchRoadAlerts">
            	<td class = "col-md-1">{{roadAlert.vendorName}}</td>
            	<td class = "col-md-1">{{roadAlert.vendorContactName}}</td>
                <td class = "col-md-2">{{roadAlert.vendorMobileNumber}}</td>
                <td class = "col-md-2">{{roadAlert.driverName}}</td>                
                <td class = "col-md-1">{{roadAlert.mobileNumber}}</td>                
                <td class = "col-md-1">{{roadAlert.licenceNumber}}</td>  
                <td class = "col-md-1">{{roadAlert.licenceExpDate}}</td>      
                    
                
                <!-- <td class = "col-md-4">{{roadAlert.description}}</td> -->
            </tr>
        </tbody>
        
    </table>
</div>
    
<div class="modal-footer modalFooter">     
    <button type="button" class="btn btn-success-gd" ng-click = "cancel()">Close</button>
</div>
</div>