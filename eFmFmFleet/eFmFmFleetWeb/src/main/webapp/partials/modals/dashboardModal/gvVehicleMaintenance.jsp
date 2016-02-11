<div class = "gvModal container-fluid">
    <div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-compass edsModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Vehicles Needed Maintenance</div>
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
		      <th>Vehicle Id</th>
		      <th>Vehicle Number</th>
		      <th>Vendor Id</th>
		      <th>Vendor Name</th>
              <th>Vendor Contact Name</th>
		      <th>Vendor Contact Number</th>
		      <th>Maintenance Exp Date</th>
            </tr> 
        </thead>
        <tbody ng-show = "vehicleMaintenanceData.length==0">
              <tr>
                 <td colspan = '7'>
                   <div class = "noData">No Employee Dropped</div>
                 </td>
              </tr>
        </tbody>
        <tbody ng-show = "vehicleMaintenanceData.length>0">
            <tr ng-repeat = "vehicleMain in vehicleMaintenanceData | filter: searchRoadAlerts">
                <td class = "col-md-1">{{vehicleMain.vehicleId}}</td>
                <td class = "col-md-2">{{vehicleMain.vehicleNumber}}</td>
                <td class = "col-md-1">{{vehicleMain.vendorId}}</td>
                <td class = "col-md-2">{{vehicleMain.vendorName}}</td>
                <td class = "col-md-2">{{vehicleMain.vendorContactName}}</td>
                <td class = "col-md-2">{{vehicleMain.vendorMobileNumber}}</td>
                <td class = "col-md-2">{{vehicleMain.vehicleManintenanceExpDate}}</td>
            </tr>
        </tbody>
        
    </table>
</div>
    
<div class="modal-footer modalFooter">     
    <button type="button" class="btn btn-success-gv" ng-click = "cancel()">Close</button>
</div>
</div>