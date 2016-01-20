<div ng-include = "'partials/showAlertMessageModalTemplate.jsp'"></div><div class="loading"></div>
<div class = "importEmployeeModal container-fluid">
    <div class = "row">
        <div class="modal-header modal-header1 col-md-12"> 
            <div class = "row">
                <div class = "icon-calendar edsModal-icon col-md-1 floatLeft"></div>
                <div class = "modalHeading col-md-8 floatLeft">Import Vendor Master Data</div>
                <div class = "col-md-2 floatRight pointer">
                    <img src="images/portlet-remove-icon-white.png" class = "floatRight" ng-click = "cancel()">
                </div>    
            </div>
        </div>        
    </div>  
<div class="modal-body modalMainContent">    
 <form class='importEmpForm' id="addinstgroup" action="services/xlUtilityVendorUpload/vendorRecord"
					method="post" enctype="multipart/form-data"
					class="form-horizontal form-bordered">
 <div class="fileinput fileinput-new input-group " data-provides="fileinput">
        <div class="form-control  col-md-6 col-xs-6" data-trigger="fileinput">
            <span class="fileinput-filename"></span>
        </div>
        <span class="input-group-addon btn btn-default btn-file">
            <span class="fileinput-new">Select file</span>
            <span class="fileinput-exists"></span>
            <input type="file" id = "filenameforactivity" name="filename" class="default">
        </span>
        <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
     </div>
     <div class="modal-footer modalFooter">  
           <button type="button" class="btn btn-info floatLeft" id='imp_stud' ng-click = "importEmpFile()">Submit</button>
           <button type="button" class="btn btn-close floatLeft" ng-click = "cancel()">Close</button>
     </div>
    </form>
</div>
</div>