<div class = "viewVehicle_VendorDashBoard">
   <img src = "images/portlet-remove-icon.png" class="img-circle pointer" ng-click = "viewVehicles(post, $index)">
	<div class = "row">
	 <div class = " col-md-4">
		<input id = "addVehicleNew" 
			   class = "addVehicle_vendor btn btn-info" 
			   type = "button" 
			   ng-click = "addNewVehicle($index, 'lg');" 
			   value = "Add New Vehicle">						
	 </div>
	 <div class = " col-md-4"></div>
	 <div class = " col-md-4">
         <input type = "text" 
                ng-model = "searchVehicles" 
                class = "searchbox" 
                placeholder = "Search Vehicles"></div>
	</div>
<table class = "viewVehicleTable table table-responsive container-fluid">
	<thead class = "vehicleThead">
		<tr>
		  <th>Vehicle Id</th>
		  <th>Vehicle Number</th>
		  <th>Capacity</th> 
		  <th>vehicle Owner Name</th>
		  <th>Insurance Valid</th>
		  <th>Permit Valid</th>
		  <th></th>
		  <th></th>
		  <th><th>
		</tr>
	</thead>
    <tbody ng-show = "vendorContractManagData[$parent.$index].vehicleData.length==0">
        <tr>
            <td colspan = '9'>
                <div class = "noData">No Vehicle found for this Vendor</div>
            </td>
        </tr>
    </tbody>
	<tbody>
	
	   <tr ng-repeat = "post in vendorContractManagData[$parent.$index].vehicleData | filter : searchVehicles"
           ng-show = 'vendorContractManagData[$parent.$index].vehicleData.length>0'>
	    	    <td><div ng-show = "!post.editVehicle_Enable">{{post.vehicleId}}</div>
	    	<!--<div ng-show = "post.editVehicle_Enable">
	    		<input class = "id" ng-model = "post.regId"></div>-->
	    </td>
	    <td><div ng-show = "!post.editVehicle_Enable">{{post.vehicleNumber}}</div>
	    	<!--<div ng-show = "post.editVehicle_Enable">
	    		<input class = "name" ng-model = "post.name"></div>-->
	    </td>
	    <td><div ng-show = "!post.editVehicle_Enable">{{post.capacity}}</div>
	    	<!--<div ng-show = "post.editVehicle_Enable">
	    		<input class = "address" ng-model = "post.mobileNumber"></div>-->	
	    </td>
		<td><div ng-show = "!post.editVehicle_Enable">{{post.vehicleOwnerName}}</div>
			<!--<div ng-show = "post.editVehicle_Enable">
	    		<input class = "dob" ng-model = "post.capacity"></div>-->
		</td>
		<td><div ng-show = "!post.editVehicle_Enable">{{post.InsuranceDate}}</div>
			<!--<div ng-show = "post.editVehicle_Enable">
	    		<input class = "lisNum" ng-model = "post.vehicleName"></div>-->
		</td>
		<td><div ng-show = "!post.editVehicle_Enable">{{post.PermitValid}}</div>
			<!--<div ng-show = "post.editVehicle_Enable">
	    		<input class = "issDate" ng-model = "post.status"></div>-->
		</td>
		<td>
		   <input id = "editVehicle" 
			      class = "editVehicle_vendor btn btn-warning btn-xs" 
			      type = "button" 
			      ng-show = "!post.editVehicle_Enable"
			   	  ng-click = "editVehicle(post, $parent.$index, $index, 'lg')" 			   	  
			      value = "View/Edit">
		   <input id = "deleteVehicle" 
			      class = "deleteVehicle_vendor btn btn-danger btn-xs" 
			      type = "button" 
			      ng-show = "!post.editVehicle_Enable"
			   	  ng-click = "deleteVehicle(post, $parent.$index, $index)" 			   	  
			      value = "Delete">
            <input id = "uploadDriver" 
			      class = "uploadVehicle_vendor btn btn-success btn-xs" 
			      type = "button" 
			      ng-show = "!post.editDriver_Enable"
			   	  ng-click = "uploadVehicle(post, $parent.$index, $index)" 			   	  
			      value = "Upload">
<!--
		   <input id = "updateVehicle" 
			       class = "updateVehicle_vendor btn btn-warning btn-xs" 
			       type = "button" 
			   	   ng-click = "updateVehicle(post, $index)" 
			   	   ng-show = "post.editVehicle_Enable"
			       value = "Update">
		  <input id = "cancelEditVehicle" 
			       class = "cancelVehicle_vendor btn btn-default btn-xs" 
			       type = "button" 
			   	   ng-click = "cancelEditVehicle(post, $index)" 
			   	   ng-show = "post.editVehicle_Enable"
			       value = "Cancel">		 
-->
		</td>
	   </tr>
	</tbody>
</table>
</div>