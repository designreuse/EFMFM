<div class = "gvModal container-fluid">
    <div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-circle edsModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Permit Due For Renewal</div>
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
		      <th>Vehicle Number</th>
              <th>Vendor ContactNam</th>
		      <th>Vendor MobileNumber</th>	
		      <th>Permit Exp Date</th>		      
            </tr> 
        </thead>
        <tbody ng-show = "permitDueData.length==0">
              <tr>
                 <td colspan = '6'>
                   <div class = "noData">No Employee Dropped</div>
                 </td>
              </tr>
        </tbody>
        <tbody ng-show = "permitDueData.length>0">
            <tr ng-repeat = "permit in permitDueData | filter: searchRoadAlerts">
            	<td class = "col-md-2">{{permit.vendorName}}</td>
                <td class = "col-md-1">{{permit.vehicleNumber}}</td>                                
                <td class = "col-md-1">{{permit.vendorContactName}}</td>
                <td class = "col-md-1">{{permit.vendorMobileNumber}}</td>
                <td class = "col-md-1">{{permit.permitExpDate}}</td>
                
                
            </tr>
        </tbody>
        
    </table>
</div>
    
<div class="modal-footer modalFooter">     
    <button type="button" class="btn btn-success-gv" ng-click = "cancel()">Close</button>
</div>
</div>