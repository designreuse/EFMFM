<div class="loadingMain"></div>
<div ng-include = "'partials/showAlertMessageTemplate.jsp'"></div>
<div class="homePage container-fluid">

<!--    NAVIGATION BAR    -->
<!-- <div class = "row navHeader_home">   -->
   <nav class = "navbar navBar_home margin0">
        <div class = "container-fluid innerNavigation">
            <!--Branding Log - Company Name-->
            <div class="navbar-header pull-left">
<!--                <div class="row">-->
                <a ui-sref="index2.home" class = "navbar-brand custome_navbarBrand_home">eFmFm - ETM</a> 

           </div>      
                
               
                <!--Navigation button will appear for small screen devices-->
              <div class = "navbar-header pull-right">
                <button type="button" 
                        class="navbar-toggle" 
                        data-toggle="collapse" 
                        data-target=".targetForButton_home">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>                
                </button>
                <div class="floatRight rightHeader_home">
                    <div class = "alert_home floatLeft">
                        <span class="icon-warning-sign alertIcon" aria-hidden="true"></span>
                    </div>
                    
                    <div class = "mail_home floatLeft">
                        <span class="icon-envelope emailIcon" aria-hidden="true"></span>
                    </div>
                    
<!--
                    <div class = "admin_home floatLeft"
                         bs-popover
                         data-content-template="partials/popover/adminHomePopover.html"
                         data-placement="bottom" data-auto-close="true" data-trigger='click'
                         data-delay="{ show: 400, hide: 200 }" >
                        <span class="icon-cog adminIcon" aria-hidden="true"><span></span></span>
                    </div>
-->
                    <div class = "admin_home floatLeft dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" role="button">
                            <span class="icon-cog adminIcon" aria-hidden="true"></span></a>
                        <ul class="dropdown-menu" role="menu">
<!--                            <li><i class = "icon-tasks"></i><span>Roster Dashboard</span></li>-->
                            <li class = "vendorDashboard_admin" ui-sref="home.vendorDashboard" ng-if = "adminRole || supervisorRole">
                                <i class = "icon-envelope"></i><span>Vendor Dashboard</span>
                            </li>
                       <!--        <li class = "rescheduleRequest_admin" ui-sref="home.rescheduleRequest" ng-if = "adminRole || supervisorRole ||managerRole">
                                <i class = "icon-refresh"></i><span>Re-Scheduled Request</span>
                            </li> --> 
                            <li class = "empDetail_admin" ui-sref="home.empDetails" ng-if = "adminRole || supervisorRole">
                                <i class = "icon-group"></i><span>Employee Details</span>
                            </li>
                            <li class = "myProfile_admin" ui-sref="home.myProfile">
                                <i class = "icon-user"></i><span>My Profile</span></li>
                                
                            <li ng-click= "openMenu()" ng-if = "adminRole || supervisorRole || managerRole"><i class = "icon-calendar" ></i>
                                <span>Import Data</span>
                                <i class = "icon-chevron-down menuSettingArrow" ng-show = "!isOpen"></i><i class = "icon-chevron-up menuSettingArrow" ng-show = "isOpen"></i></a>
                                <div class = "importSubmenu"  ng-show = "isOpen" ng-controller = "importDataCtrl">
                                    <ul>
                                        <li class = "importSubItems" ng-if = "adminRole || supervisorRole" ng-click= "openImportEmployee()">
                                            <span>Import Employee Data</span>
                                        </li>
                                        <li class = "importSubItems" ng-if = "adminRole || supervisorRole || managerRole" ng-click= "importEmployeeRequest()">
                                            <span>Import Employee Request</span>
                                        </li>                           
                                        <li class = "importSubItems" ng-if = "adminRole || supervisorRole" ng-click= "importVendorData()">
                                            <span>Import Vendor Data</span>
                                        </li>
                                        <li class = "importSubItems" ng-if = "adminRole || supervisorRole" ng-click= "importDriverData()">
                                            <span>Import Driver Data</span>
                                        </li>
                                        <li class = "importSubItems" ng-if = "adminRole || supervisorRole" ng-click= "importVehicleData()">
                                            <span>Import Vehicle Data</span>
                                        </li>  
                                         <li class = "importSubItems" ng-if = "adminRole || supervisorRole" ng-click= "importEscortData()">
                                            <span>Import Escort Data</span>
                                        </li>                           
                                         <li class = "importSubItems" ng-if = "adminRole || supervisorRole" ng-click= "importAreaData()">
                                             <span>Import Area Data</span>
                                        </li>  
                                    </ul>
                                </div>
                                
                            </li>

                           
                            <li class= "lineDivider"><hr></li>
<!--
                            <li><i class = "icon-move"></i><span>Full Screen</span></li>
                            <li><i class = "icon-lock"></i><span>Lock Screen</span></li>
-->
                             <li><a ng-href="j_spring_security_logout" id="logOut"><i
									class="icon-key"></i> Log Out</a></li>
<!--                             <li a href="j_spring_security_logout" id="logOut" ng-click = "logout()"><i class = "icon-key"></i><span>Log Out</span></li>
 -->                        </ul>
                    </div>
                </div>
<!--            <button popover="I have a title!" popover-title="The title." class="btn btn-default">title</button>-->
            </div>
            
            <!--Navigetion Links on the Right-->
            <div class="clearfix visible-sm visible-md visible-xs"></div>
            <div class = "collapse navbar-collapse targetForButton_home">                
                    <ul class="nav navbar-nav navMenu_home navbar-left">
                        <li class="dashboardMenu" ng-if = "adminRole || supervisorRole">
                            <a ui-sref="home.dashboard" ng-class = "{activeMenuTab: getActiveClassStatus('/dashboard')}">Dashboard</a></li>                         
                        <li class="" ng-if = "adminRole || supervisorRole || managerRole">
                            <a ui-sref="home.employeeTravelDesk" ng-class = "{activeMenuTab: getActiveClassStatus('/employeeTravelDesk')}">Employee Roster</a></li>  
                        <li class="empTravelDeskMenu" ng-if = "managerRole">
                            <a ui-sref="home.rescheduleRequest" ng-class = "{activeMenuTab: getActiveClassStatus('/rescheduleRequest')}">Re-Scheduled Request</a></li>
                        <li class="veiwMapMenu" ng-if = "adminRole || supervisorRole">
                            <a ui-sref="home.viewmap" ng-class = "{activeMenuTab: getActiveClassStatus('/viewmap')}">Live Tracking</a></li>                       
                        <li class="serviceMappingMenu" ng-if = "adminRole || supervisorRole">
                            <a ui-sref="home.servicemapping" ng-class = "{activeMenuTab: getActiveClassStatus('/servicemapping')}">Routing/Cab allocation</a></li>
                        <li class="reportMenu" ng-if = "adminRole || supervisorRole">
                            <a ui-sref="home.reports" ng-class = "{activeMenuTab: getActiveClassStatus('/reports')}">Reports</a></li>

                        <li ng-if = "adminRole || supervisorRole">
                            <a ui-sref="home.invoice" ng-class = "{activeMenuTab: getActiveClassStatus('/invoice')}">Invoices</a></li>                     

                        <!-- <li class ="alertsMenu" ng-if = "adminRole || supervisorRole">
                            <a ui-sref="home.alerts" ng-class = "{activeMenuTab: getActiveClassStatus('/alerts')}">Broadcast</a></li> -->
                        <li class="approvalMenu" ng-if = "adminRole || supervisorRole">
                            <a ui-sref="home.approval" ng-class = "{activeMenuTab: getActiveClassStatus('/approval')}">Approval</a></li>
                        <li class = "employeeRequest" ng-if = "supervisorRole ||webUserRole || managerRole">
                            <a ui-sref="home.requestDetails" ng-class = "{activeMenuTab: getActiveClassStatus('/requestDetails')}">Request Details</a></li>
                        <li class = "employeeRequest" ng-if = "adminRole">
                            <a ui-sref="home.requestDetails" ng-class = "{activeMenuTab: getActiveClassStatus('/requestDetails')}">AD-HOC Request</a></li>
                    </ul>            
            </div> 
        </div>
    </nav> 
<!-- END OF NAVIGATION-->
  <div class ="homeContent">
    <div ui-view></div>
  </div>  
</div>