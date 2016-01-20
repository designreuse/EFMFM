<div class = "gdModal container-fluid">
    <div class = "row">
        <div class="modal-header modal-header1 col-md-12">
           <div class = "row"> 
            <div class = "icon-circle edsModal-icon col-md-1 floatLeft"></div>
            <div class = "modalHeading col-md-10 floatLeft">Police Varification Due For Renewal</div>
            <div class = "col-md-1 floatRight pointer">
                <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
            </div> 
           </div> 
        </div>        
    </div>  
    <div class = "row">
        <div class="modal-header2 col-md-12">
            <form><input class = "searchModal" 
                         ng-model = "searchgdPoliceVerification"
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
		      <th>Police Varification Exp Date</th>
		      <!-- <th>Description</th> -->
            </tr> 
        </thead>
        <tbody ng-show = "gdPoliceVerificationData.length==0">
              <tr>
                 <td colspan = '7'>
                   <div class = "noData">Police Varification Due For Renewal</div>
                 </td>
              </tr>
        </tbody>
        <tbody ng-show = "gdPoliceVerificationData.length>0">
            <tr ng-repeat = "policeCert in gdPoliceVerificationData | filter: searchgdPoliceVerification">
            	<td class = "col-md-1">{{policeCert.vendorName}}</td>
            	<td class = "col-md-1">{{policeCert.vendorContactName}}</td>
                <td class = "col-md-2">{{policeCert.vendorMobileNumber}}</td>
                <td class = "col-md-2">{{policeCert.driverName}}</td>                
                <td class = "col-md-1">{{policeCert.mobileNumber}}</td>                
                <td class = "col-md-1">{{policeCert.licenceNumber}}</td>  
                <td class = "col-md-1">{{policeCert.policeExpDate}}</td>      
                    
                
                <!-- <td class = "col-md-4">{{roadAlert.description}}</td> -->
            </tr>
        </tbody>
        
    </table>
</div>
    
<div class="modal-footer modalFooter">     
    <button type="button" class="btn btn-success-gd" ng-click = "cancel()">Close</button>
</div>
</div>