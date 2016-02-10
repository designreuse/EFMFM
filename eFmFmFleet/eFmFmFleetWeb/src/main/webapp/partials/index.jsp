<!doctype html>
<html>    
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
        <link rel= "stylesheet" href = "styles/index2.css">
  </head>
  <body>
	<div class="indexPageTemplate container-fluid">
	    <div class = "row topBanner">
	        <div class = "floatRight loginLink" data-toggle="modal" data-target="#loginModal">Login</div>
	    </div>
	    <div class = "row imgBG">
	        <div class = "col-md-12 imgMain">
	            <img class = "imgMain2" src = "images/slide-3a.jpg">
	        </div>
	    </div>
	<!--    MODAL-->
	    <button type="button" class="btn btn-primary btn-lg hidebutton" data-toggle="modal" data-target="#loginModal">
	    </button>
	    <div class="modal fade" id="loginModal" tabindex="-1">
	        <div class="modal-dialog">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
	                <h4 class="modal-title" id="myModalLabel">Login Page</h4>
	              </div>
	              <div class="modal-body">
	              <!--     <form class="form-horizontal formLogin" method="post">-->
	                <form class="form-horizontal formLogin" method="post" action="j_spring_security_check" name="login_form" >
	                      <div class="form-group">
	                        <div class = "col-sm-1"></div>
	                        <label for="inputEmail3" class="col-sm-2 control-label">UserName</label>
	                        <div class="col-sm-9">
	                          <input type="text" 
	                                 class="m-wrap form-control userNameInput" 
	                                 id="inputEmail3" 
	                                 placeholder="Username" 
	                                 name="j_username" 
	                                 data-rule-required="true" 
	                                 data-rule-email="true">
<!-- 	                            <input type="hidden" id="hiddenlanguage" name="productName" value="mytvs" >
 -->	                        </div>
	                      </div>
	                      <div class="form-group">
	                        <div class = "col-sm-1"></div>
	                        <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
	                        <div class="col-sm-9">
	                          <input type="password" 
	                                 class="m-wrap form-control passwordInput" 
	                                 id="inputPassword3" 
	                                 placeholder="Password" 
	                                 name="j_password" 
	                                 data-rule-required="true">
	                        </div>
	                      </div>              
	                      <div class="form-group">
	                       <div class="col-sm-offset-3 col-sm-9">
	                        <label class="checkbox"  style="display:none">
	                            <input type="checkbox" name="_spring_security_remember_me" value="true" checked> Keep Me Login         
	                        </label>                
	                          <button type="submit" class="btn blueBtn">Sign in</button>
	                        </div>
	                      </div>
	                  </form>
	              </div>
	              <div class="modal-footer">
	     <!--            <div class = "row">
	                    <div class = "col-sm-4"></div>
	                    <div class = "col-sm-4"><img src = "images/avatar1_small.jpg" class = "companyLogo" alt = "Company Logo"></div>
	                    <div class = "col-sm-4"></div>
	                </div> -->
	                <div class = "row">
	                    <div class = "col-sm-4"></div>
	                    <div class = "col-sm-4"><img src = "images/car.gif" class = "companyLogo" alt = "Company Logo"></div>
	                    <div class = "col-sm-4"></div>
	                </div>
	                </div>
	          </div>
	    </div>
	  </div>
	<!--End of the Modal   -->
	 <div class = "mainIndexContent">
	     <div class = "row description_home">
	         <div class = "col-md-4 col-xs-12"> 
	           <div class = "col-md-12">
	                <i class = "icon-location-arrow iconIndex blue floatLeft"></i>
	                <div class = "heading_home col-md-10 test"> Route Optimization</div>               
	           </div>         
	            <p>
	                Real time tracking of technician information will be shared within view map. ASP's status, speed, distance, and ETA will be viewed within view map.
	                
	            </p>        
	        </div>
	         <div class = "col-md-4 col-xs-12">
	            <div class = "col-md-12">
	                <i class = "icon-ok iconIndex red floatLeft"></i>
	                <div class = "heading_home col-md-10">Geofencing</div>               
	            </div>        
	            <p>
	                The system allows you to set up a series of geographic zones together with the time-based rules.
	            </p> 
	        </div>
	         <div class = "col-md-4 col-xs-12">
	            <div class = "col-md-12">
	                <i class = "icon-resize-small iconIndex green floatLeft"></i>
	                <div class = "heading_home col-md-10">Location/route maps</div>               
	            </div>          
	            <p>
	                The efmfm system comes with integrated mapping with google maps. Google Mapping provides real-time updates to vehicle positions overlaid on maps, and Google's satellite, hybrid and terrain views.
	            </p>
	        </div>
	     </div> 
	     <div class = "row moreDescription">
	         <div class = "col-md-3 col-xs-12 moduleIndex">
	             <h2 class = "loginH2">Modules</h2>
	             <p class = "moreDescriptionP">Mobile device with in the vehicle displays list of available employee.A vital feature "Scheduler" helps in automatic optimization and assignment of employee within vehicles.</p>
	         </div> 
	         <div class = "col-md-3 col-xs-12 empApp">
	             
	             <img src = "images/dash.png">
	             <div class="labelModuleIndexEmp">
	                 <div class = "row labelModuleIndex1">
	                     <div class = "col-md-12 col-xs-12">Employee Application</div>
	                 </div>
	                 <div class = "row labelModuleIndex2">
	                     <div class = "col-md-12 col-xs-12">Module</div>
	                 </div>
	             </div>
	         </div> 
	         <div class = "col-md-3 col-xs-12 escortApp">
	             <img src = "images/scheduler.png">
	             <div class="labelModuleIndexEscort">
	                 <div class = "row labelModuleIndex1">
	                     <div class = "col-md-12 col-xs-12">Escort Application</div>
	                 </div>
	                 <div class = "row labelModuleIndex2">
	                     <div class = "col-md-12 col-xs-12">Module</div>
	                 </div>
	             </div>
	         </div> 
	         <div class = "col-md-3 col-xs-12 adminApp">
	             <img src = "images/seller.png">
	             <div class="labelModuleIndexAdmin">
	                 <div class = "row labelModuleIndex1">
	                     <div class = "col-md-12 col-xs-12">Admin Application</div>
	                 </div>
	                 <div class = "row labelModuleIndex2">
	                     <div class = "col-md-12 col-xs-12">Module</div>
	                 </div>
	             </div>
	         </div> 
	     </div>
	 </div>
	<div class="footerIndex row">
	         <span>Copyright @ Newt|Global Consulting LLC.</span>
	     </div>
	</div> 
	<script src="scripts/controllers/index2.js"></script>
	<script src="bower_components/angular-bootstrap/ui-bootstrap-tpls2.js"></script>             
    <script src="bower_components/jquery/dist/jquery.min.js"></script>      
    <script src="bower_components/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="bower_components/bootstrap/js/jasny-bootstrap.min.js"> </script>
    <script src="scripts/controllers/index2.js"></script>
        <script type="text/javascript">
    var alreadyloggedIn ="<%=request.getSession().getAttribute("access")%>";
//    alert(alreadyloggedIn);
 //   jQuery(document).ready(function() {
    	 var flag = " ${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}";
			if(flag.length==1 ){
			 }
		 else
			 {
			 alert("Invalid Username and Password");
//			 $("#portlet-config").show();
			
			 }		
 		 	 if(alreadyloggedIn.length>0 && alreadyloggedIn!=null ){
//				 $("#portlet-config").show();
 //                 alert("hi");
				 } 
//    });
    </script>
</body>
</html>  