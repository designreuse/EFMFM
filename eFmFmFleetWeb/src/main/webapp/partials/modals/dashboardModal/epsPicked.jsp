<div class = "epsModal container-fluid">
    <div class = "row">
        <div class="modal-header modal-header1 col-md-12"> 
           <div class = "row"> 
            <div class = "icon-check edsModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Employee Picked</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div>        
          </div> 
        </div>        
    </div>  
    <div class = "row">
        <div class="modal-header2 col-md-12">
            <form><input class = "searchModal" 
                         type="text" 
                         class="form-control" 
                         placeholder="Search..." 
                         id="formGroupInputSmall"
                         ng-model = "search"></form>
        </div>
    </div>
    
<div class="modal-body modalMainContent">
   <table class="table table-bordered table-hover table-responsive container-fluid dashboardTable">
        <thead class ="tableHeading">
            <tr>
		      <th>Request ID</th>
		      <th>Request Time</th>
              <th>Employee Id</th>
		      <th>Employee Name</th>
		      <th>Gender</th>
		      <th>Address</th>
<!--              <th>Trip Type</th>-->
            </tr> 
        </thead>
        <tbody ng-show = "employeePickedData.length==0">
              <tr>
                 <td colspan = '6'>
                   <div class = "noData">No Picked up Employee</div>
                 </td>
              </tr>
        </tbody>
        <tbody ng-show = "employeePickedData.length>0">
            <tr ng-repeat = "pick in employeePickedData | filter: search">
                <td class = "col-md-1">{{pick.requestId}}</td>
                <td class = "col-md-1">{{pick.tripTime}}</td>
                <td class = "col-md-1">{{pick.employeeId}}</td>
                <td class = "col-md-3">{{pick.employeeName}}</td>
                <td class = "col-md-1">{{pick.gender}}</td>
                <td class = "col-md-4">{{pick.employeeAddress}}</td>
<!--                <td class = "col-md-1">{{dropRequest.tripType}}</td>-->
            </tr>
        </tbody>
        
    </table>
</div>
    
<div class="modal-footer modalFooter">     
    <button type="button" class="btn btn-success-eps" ng-click = "cancel()">Close</button>
</div>
</div>