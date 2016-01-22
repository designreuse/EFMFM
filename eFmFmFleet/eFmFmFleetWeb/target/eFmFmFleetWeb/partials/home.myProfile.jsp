<div class = "myProfileTemplate container-fluid">
    <div class = "row">
        <div class = "col-md-12 col-xs-12 heading1">            
            <span class = "col-md-12 col-xs-12">My Profile</span> 
                      
            <div class = "col-md-12 col-xs-12 mainTabDiv_viewMap">
                <tabset class="tabset myProfileTab_myProfile">
                  <tab ng-click = "getProfileInfo()">
                    <tab-heading>Profile Info</tab-heading>
                        <div class = "profileTabContent row">  
                            <!--   ONLY SEEN ON THE BIG (LG and MD Sizes) SCREEN   -->
                        	<div class = "col-md-12 col-xs-12 hidden-xs hidden-sm myProfileWrapper">
                                <form name = "myProfileForm" class = "row">
                                    <div class = "col-md-2 col-xs-12 profileFrame">
                                        <img src = "images/default_profile.png" alt = "Employee Name">
                                    </div>
                                    <div class = "col-md-7 col-xs-12 btnFrame">
                                        <input type = "button" 
                                               class = "btn btn-warning editProfileButton_myProfile" 
                                               value = "Edit My Profile"
                                               ng-click = "editMyProfile()"
                                               ng-show = "!isProfileEdit"
                                               ng-class = "{disabled: changePasswordClicked}">
                                        <input type = "button" 
                                               class = "btn btn-primary" 
                                               value = "Change Password" 
                                               ng-click = "openPasswordDiv()"
                                               ng-show = "!changePasswordClicked"
                                               ng-class = "{disabled: isProfileEdit}">
                                        <input type = "button" 
                                               class = "btn btn-success" 
                                               value = "Save Changes"
                                               ng-show = "isProfileEdit"
                                               ng-click = "saveProfile(user)"
                                               ng-disabled="myProfileForm.$invalid">
                                        <input type = "button" 
                                               class = "btn btn-default" 
                                               value = "Cancel Changes"
                                               ng-show = "isProfileEdit"
                                               ng-click = "cancelProfile(user)">
                                    </div>
                                    <div class = "col-md-10 col-xs-12 profileDataFrame">
                                        <div class = "row userInfo">
                                            <div class = "col-md-8">
                                                <div class = "passwordDiv">
                                                    <div class = "row">
                                                        <div class = "col-md-8">
                                                            <span>Enter Old Password</span><br>
                                                            <input type= "text" ng-model = "userPassword.oldPass">
                                                            <br>
                                                            <span>Enter New Password</span><br>
                                                            <input type= "text" ng-model = "userPassword.newPass1">
                                                            <br>
                                                            <span>Re-Type New Password</span><br>
                                                            <input type= "text" ng-model = "userPassword.newPass2">
                                                        </div>
                                                        <div class = "col-md-4 savePassButtons">
                                                            <input type= "button" 
                                                                   class = "btn btn-success btn-xs" 
                                                                   value = "Save"
                                                                   ng-click = "savePassword(userPassword)">
                                                            <input type= "button" 
                                                                   class = "btn btn-default btn-xs" 
                                                                   value = "Cancel"
                                                                   ng-click = "cancelPassword()">
                                                        </div>
                                                    </div>
                                                                                                 
                                                </div>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>User Name</span><br>
                                   <!--            <input type= "text" ng-model = "user.userName" 
                                                       readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">-->
                                                <input type= "text"  
                                                       class = "form-control"
                                                       ng-model = "user.userName" 
                                                       ng-pattern = 'NoSpecialCharacters'
                                                       ng-maxlength = '15'
                                                       ng-disabled = '!isProfileEdit'
                                                       required
                                                       name = "username"
                                                       ng-class = "{error: myProfileForm.username.$invalid && !myProfileForm.username.$pristine}">
                                                <span class = 'hintModal' ng-show = "myProfileForm.username.$invalid && !myProfileForm.username.$pristine">
                                                    * Please Enter a Valid User Name
                                                </span>
                                            </div>
                                                
                                            <div class = "col-md-6 form-group">
                                                <span>First Name</span>
                                                <input type= "text" 
                                                       class = "form-control"
                                                       ng-model = "user.firstName" 
                                                       name = "fname" 
                                                       ng-maxlength = '15'
                                                       ng-disabled = '!isProfileEdit'
                                                       required 
                                                       ng-pattern="regExName"
                                                       ng-class = "{error2: myProfileForm.fname.$invalid && !myProfileForm.fname.$pristine}">
                                                <span class = 'hintModal' ng-show = "myProfileForm.fname.$invalid && !myProfileForm.fname.$pristine">
                                                    * Please Enter a Valid First Name
                                                </span>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Last Name</span><br>
                                                <input type= "text" 
                                                       class = "form-control"
                                                       ng-model = "user.lastName" 
                                                       ng-maxlength = '15'
                                                       ng-disabled = '!isProfileEdit'
                                                       name = "lname"
                                                       required 
                                                       ng-pattern="regExName"
                                                       ng-class = "{error: myProfileForm.lname.$invalid && !myProfileForm.lname.$pristine}">
                                                <span class = 'hintModal' ng-show = "myProfileForm.lname.$invalid && !myProfileForm.lname.$pristine">
                                                    * Please Enter a Valid Last Name
                                                </span>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Country</span><br>
                                                <input type= "text" 
                                                       class = "form-control"
                                                       ng-model = "user.country" 
                                                       ng-maxlength = '15'
                                                       ng-disabled = '!isProfileEdit'
                                                       name = "country"
                                                       required 
                                                       ng-pattern="regExName"
                                                       ng-class = "{error: myProfileForm.country.$invalid && !myProfileForm.country.$pristine}">
                                                <span class = 'hintModal' ng-show = "myProfileForm.country.$invalid && !myProfileForm.country.$pristine">
                                                    * Please Enter a Valid Country Name
                                                </span>
                                            </div>
                            <!--                 <div class = "col-md-6 form-group">
                                               <span>Date of Birth</span>
                                               <div class = "input-group calendarInput" ng-show = "!isProfileEdit">
                                                   <span class="input-group-btn nonEditableInput_myProfile">
                                                        <button class="btn btn-default nonEditableInput_myProfile" ng-click="openDobCalFake($event)">
                                                            <i class = "icon-calendar calInputIcon"></i></button></span> 
                                                   <input type="text" 
                                                     ng-model="user.dob" 
                                                     class="nonEditableInput_myProfile" 
                                                     placeholder = "Date of Birth"
                                                     datepicker-popup = 'shortDate'
                                                     is-open="datePicker.dobDate"
                                                     show-button-bar = false
                                                     datepicker-options = 'dateOptions'
                                                     readonly
                                                     required
                                                     name = 'dobDate'
                                                     ng-class = "{error: myProfileForm.dobDate.$invalid && !myProfileForm.dobDate.$pristine}">
                                                 </div>    
                                                 <div class = "input-group calendarInput" ng-show = "isProfileEdit">
                                                   <span class="input-group-btn ">
                                                        <button class="btn btn-default" ng-click="openDobCal($event)">
                                                            <i class = "icon-calendar calInputIcon"></i></button></span> 
                                                   <input type="text" 
                                                     class = "form-control"
                                                     ng-model="user.dob"  
                                                     placeholder = "Date of Birth"
                                                     datepicker-popup = 'shortDate'
                                                     is-open="datePicker.dobDate"
                                                     show-button-bar = false
                                                     datepicker-options = 'dateOptions'
                                                     required
                                                     name = 'dobDate'
                                                     ng-class = "{error: myProfileForm.dobDate.$invalid && !myProfileForm.dobDate.$pristine}">
                                                  
                                                 </div> 
                                                
                                                

                                                <span>Date of Birth</span><br>
                                                <input type= "text" ng-model = "user.dob"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text" 
                                                       ng-model = "user.dob" 
                                                       ng-show = "isProfileEdit">
why??
                                            </div> -->
                                            <div class = "col-md-6 form-group">
                                                <span>Employee Designation</span><br>
                                                <input type= "text" 
                                                       ng-model = "user.occupation" 
                                                       ng-disabled = '!isProfileEdit'
                                                       class = "form-control">
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Email</span><br>
                                                <input type= "email" 
                                                       class = "form-control"
                                                       ng-model = "user.email" 
                                                       ng-disabled = '!isProfileEdit'
                                                       required
                                                       name = 'email'
                                                       ng-class = "{error: myProfileForm.email.$invalid && !myProfileForm.email.$pristine}">
                                                <span class = 'hintModal'  ng-show = "myProfileForm.email.$invalid && !myProfileForm.email.$pristine">
                                                    * Please Enter Valid Email Address
                                                </span>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Mobile Number</span><br>
                                                <input type= "text" 
                                                       class = "form-control"
                                                       ng-model = "user.mobileNumber" 
                                                       ng-minlength= '10'
                                                       ng-maxlength = '10'
                                                       ng-disabled = '!isProfileEdit'
                                                       name = 'mobileNumber'
                                                       ng-class = "{error: myProfileForm.mobileNumber.$invalid && !myProfileForm.mobileNumber.$pristine}">
                                                <span class = 'hintModal'  ng-show = "myProfileForm.mobileNumber.$invalid && !myProfileForm.mobileNumber.$pristine">
                                                    * Please Enter Valid Mobile Number
                                                </span>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Department</span><br>
                                                
                                                <input type= "text" 
                                                       class = "form-control"
                                                       ng-model = "user.interest" 
                                                       ng-disabled = '!isProfileEdit'
                                                       ng-maxlength = '15'>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Website URL</span><br>
                                                
                                                <input type= "text" 
                                                       class = "form-control"
                                                       ng-model = "user.website"
                                                       ng-disabled = '!isProfileEdit'>
                                            </div>                                             
                                            <div class = "col-md-12 form-group">
                                                <span>Address</span><br>
                                                
                                                <textarea type= "text" 
                                                          class = "form-control"
                                                          ng-model = "user.about"
                                                       ng-disabled = '!isProfileEdit'>
                                                </textarea>
                                            </div>
                                        </div>                                        
                                    </div>                                    
                                </form>
                          </div> 
                            
                          <!--   ONLY SEEN ON THE SMALL (SM and XS Sizes) SCREEN   -->
                          <div class = "col-md-12 col-xs-12 hidden-lg hidden-md myProfileWrapper">
                                <form name = "myProfileForm2" class = "row">
                                    <div class = "col-md-2 col-xs-12 marginRow profileFrame2">
                                        <img src = "images/default_profile.png" alt = "Employee Name">
                                    </div>
                                    <div class = "col-md-7 col-xs-12 btnFrame2">
                                        <input type = "button" 
                                               class = "btn btn-warning btn-xs editProfileButton_myProfile" 
                                               value = "Edit My Profile"
                                               ng-click = "editMyProfile()"
                                               ng-show = "!isProfileEdit"
                                               ng-class = "{disabled: changePasswordClicked}">
                                        <input type = "button" 
                                               class = "btn btn-primary btn-xs" 
                                               value = "Change Password" 
                                               ng-click = "openPasswordDiv()"
                                               ng-show = "!changePasswordClicked"
                                               ng-class = "{disabled: isProfileEdit}">
                                        <input type = "button" 
                                               class = "btn btn-success btn-xs" 
                                               value = "Save Changes"
                                               ng-show = "isProfileEdit"
                                               ng-click = "saveProfile(user)"
                                               ng-disabled="myProfileForm2.$invalid">
                                        <input type = "button" 
                                               class = "btn btn-default btn-xs" 
                                               value = "Cancel Changes"
                                               ng-show = "isProfileEdit"
                                               ng-click = "cancelProfile(user)">
                                    </div>
                                    <div class = "col-md-10 col-xs-12 profileDataFrame2">
                                        <div class = "row userInfo2">
                                            <div class = "col-md-8">
                                                <div class = "passwordDiv">
                                                    <div class = "row">
                                                        <div class = "col-md-8">
                                                            <span>Enter New Password</span><br>
                                                            <input type= "text" ng-model = "userPassword.newPass1">
                                                            <br>
                                                            <span>Re-Type New Password</span><br>
                                                            <input type= "text" ng-model = "userPassword.newPass2">
                                                        </div>
                                                        <div class = "col-md-4 savePassButtons">
                                                            <input type= "button" 
                                                                   class = "btn btn-success btn-xs" 
                                                                   value = "Save"
                                                                   ng-click = "savePassword(userPassword)">
                                                            <input type= "button" 
                                                                   class = "btn btn-default btn-xs" 
                                                                   value = "Cancel"
                                                                   ng-click = "cancelPassword()">
                                                        </div>
                                                    </div>
                                                                                                 
                                                </div>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>User Name</span><br>
                                                <input type= "text" ng-model = "user.userName" 
                                                       readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text" 
                                                       class = "form-control"
                                                       ng-model = "user.userName" 
                                                       ng-show = "isProfileEdit"
                                                       required
                                                       name = "username"
                                                       ng-class = "{error: myProfileForm2.username.$invalid && !myProfileForm2.username.$pristine}">
                                                <span class = 'hintModal' ng-show = "myProfileForm2.username.$invalid && !myProfileForm2.username.$pristine">
                                                    * Please Enter a Valid User Name
                                                </span>
                                            </div>
                                                
                                            <div class = "col-md-6 form-group">
                                                <span>First Name</span>
                                                <input type= "text" ng-model = "user.firstName"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text" 
                                                       class = "form-control"
                                                       ng-model = "user.firstName"
                                                       ng-show = "isProfileEdit"
                                                       name = "fname"
                                                       required 
                                                       ng-pattern="regExName"
                                                       ng-class = "{error2: myProfileForm2.fname.$invalid && !myProfileForm.fname.$pristine}">
                                                <span class = 'hintModal' ng-show = "myProfileForm2.fname.$invalid && !myProfileForm2.fname.$pristine">
                                                    * Please Enter a Valid First Name
                                                </span>
                                            </div>
                                            <div class = "col-md-6  form-group">
                                                <span>Last Name</span>
                                                <input type= "text" ng-model = "user.lastName"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text"  
                                                       class = "form-control"
                                                       ng-model = "user.lastName" 
                                                       ng-show = "isProfileEdit"
                                                       name = "lname"
                                                       required 
                                                       ng-pattern="regExName"
                                                       ng-class = "{error: myProfileForm2.lname.$invalid && !myProfileForm2.lname.$pristine}">
                                                <span class = 'hintModal' ng-show = "myProfileForm2.lname.$invalid && !myProfileForm2.lname.$pristine">
                                                    * Please Enter a Valid Last Name
                                                </span>
                                            </div>

                                            <div class = "col-md-6 form-group">
                                                <span>Country</span>
                                                <input type= "text" ng-model = "user.country"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text"                                                         
                                                       class = "form-control"
                                                       ng-model = "user.country" 
                                                       ng-show = "isProfileEdit"
                                                       name = "country"
                                                       required 
                                                       ng-pattern="regExName"
                                                       ng-class = "{error: myProfileForm2.country.$invalid && !myProfileForm2.country.$pristine}">
                                                <span class = 'hintModal' ng-show = "myProfileForm2.country.$invalid && !myProfileForm2.country.$pristine">
                                                    * Please Enter a Valid Country Name
                                                </span>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Date of Birth</span><br>
                                                <div class = "input-group calendarInput" ng-show = "!isProfileEdit">
                                                   <span class="input-group-btn nonEditableInput_myProfile">
                                                        <button class="btn btn-default nonEditableInput_myProfile" ng-click="openDobCalFake($event)">
                                                            <i class = "icon-calendar calInputIcon"></i></button></span> 
                                                   <input type="text" 
                                                     ng-model="user.dob" 
                                                     class="nonEditableInput_myProfile" 
                                                     placeholder = "Date of Birth"
                                                     datepicker-popup = 'shortDate'
                                                     is-open="datePicker.dobDate"
                                                     show-button-bar = false
                                                     datepicker-options = 'dateOptions'
                                                     readonly
                                                     required
                                                     name = 'dobDate'
                                                     ng-class = "{error: myProfileForm2.dobDate.$invalid && !myProfileForm2.dobDate.$pristine}">
                                                 </div>    
                                                 <div class = "input-group calendarInput" ng-show = "isProfileEdit">
                                                   <span class="input-group-btn ">
                                                        <button class="btn btn-default" ng-click="openDobCal($event)">
                                                            <i class = "icon-calendar calInputIcon"></i></button></span> 
                                                   <input type="text" 
                                                     class = "form-control"
                                                     ng-model="user.dob"  
                                                     placeholder = "Date of Birth"
                                                     datepicker-popup = 'shortDate'
                                                     is-open="datePicker.dobDate"
                                                     show-button-bar = false
                                                     datepicker-options = 'dateOptions'
                                                     required
                                                     name = 'dobDate'
                                                     ng-class = "{error: myProfileForm2.dobDate.$invalid && !myProfileForm2.dobDate.$pristine}">
                                                  
                                                 </div> 
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Designation</span>
                                                <input type= "text" ng-model = "user.occupation"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text"  
                                                       class = "form-control"
                                                       ng-model = "user.occupation" 
                                                       ng-show = "isProfileEdit"></div>
                                            <div class = "col-md-6 form-group">
                                                <span>Email</span>
                                                <input type= "email" ng-model = "user.email"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "email"                                                         
                                                       class = "form-control"
                                                       ng-model = "user.email" 
                                                       ng-show = "isProfileEdit"
                                                       required
                                                       name = 'email'
                                                       ng-class = "{error: myProfileForm2.email.$invalid && !myProfileForm2.email.$pristine}">
                                                <span class = 'hintModal'  ng-show = "myProfileForm2.email.$invalid && !myProfileForm2.email.$pristine">
                                                    * Please Enter Valid Email Address
                                                </span>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Mobile Number</span><br>
                                                 <input type= "text" ng-model = "user.mobileNumber"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text" 
                                                       class = "form-control"
                                                       ng-model = "user.mobileNumber" 
                                                       ng-show = "isProfileEdit"
                                                       required
                                                       name = 'mobileNumber'
                                                       ng-class = "{error: myProfileForm2.mobileNumber.$invalid && !myProfileForm2.mobileNumber.$pristine}">
                                                <span class = 'hintModal'  ng-show = "myProfileForm2.mobileNumber.$invalid && !myProfileForm2.mobileNumber.$pristine">
                                                    * Please Enter Valid Mobile Number
                                                </span>
                                            </div>
                                            <div class = "col-md-6 form-group">
                                                <span>Department</span><br>
                                                <input type= "text" ng-model = "user.interest"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text"  
                                                       class = "form-control"
                                                       ng-model = "user.interest" 
                                                       ng-show = "isProfileEdit"></div>
                                            <div class = "col-md-6 form-group">
                                                <span>Website URL</span><br>
                                                <input type= "text" ng-model = "user.website"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text"  
                                                       class = "form-control"
                                                       ng-model = "user.website" 
                                                       ng-show = "isProfileEdit"></div>                                             
                                            <div class = "col-md-6 form-group">
                                                <span>About</span><br>
                                                <input type= "text" ng-model = "user.about"readonly 
                                                       class = "nonEditableInput_myProfile" 
                                                       ng-show = "!isProfileEdit">
                                                <input type= "text"  
                                                       class = "form-control"
                                                       ng-model = "user.about" 
                                                       ng-show = "isProfileEdit"></div>
                                        </div>                                        
                                    </div>                                    
                                </form>
                          </div> 

                        </div>
                    </tab>
<!--***********************SECOND TAB _ ADMIN SETTING*********************** -->
                    
                    <tab ng-if = "adminRole" ng-click = "getAdminSettings()">
                    <tab-heading>Administrator Settings</tab-heading>
                        <div class = "profileTabContent row">                          
                        	<div class = "col-md-12 col-xs-12 myProfileWrapper">
                                <button class = "btn btn-success floatRight addUser_myProfiile"
                                        ng-click = "addUser('lg')"><span class = "icon icon-user"></span><span>Add User</span></button>
                                    <table class = "adminSettingsTable table table-responsive container-fluid">
                                        <thead class ="tableHeading">
                                            <tr>
                                            <th>User Id</th>
                                              <th>Name</th>
                                              <th>User Name</th>
                                              <th>Mobile Number</th>  
                                              <th>EmailId</th>
                                              <th>User Role</th>
                                              <th>Change User Role</th>
                                              <th></th>
                                            </tr> 
                                        </thead>
                                        <tbody>
                                           <tr ng-repeat = "user in administratorData">
                                              <td class = "col-md-1">{{user.userId}}</td>                                          
                                              <td class = "col-md-2">{{user.fullName}}</td>
                                              <td class = "col-md-2">{{user.userName}}</td>
                                              <td class = "col-md-2">{{user.mobileNumber}}</td>
                                              <td class = "col-md-1">{{user.emailId}}</td>
                                              <td class = "col-md-1">{{user.userRole}}</td>		                                        
                                              <td class = "col-md-2 roleAssignment">
                                                   <select ng-model="userType" 
                                                          ng-options="user.text for user in users track by user.value"
                                                          ng-change = "setUserType(userType)">
                                                    <option value="">-- Select User Role --</option>
                                                  </select>
                                               </td>   
                                               <td class = "col-md-2">
                                                  <input type = "button" 
                                                         class = "btn btn-primary btn-xs assignButton_myProfile" 
                                                         value = "Assign"
                                                         ng-click = "assignRole(user)"></td>
                                            </tr>                                    
                                        </tbody>
                                </table>
                            </div> 
                        </div>
                    </tab>
                    <!--******************THIRD TAB**************************** -->
                    <tab ng-if = "adminRole" ng-click = "getApplicationSettings()">
                    <tab-heading>Application Settings</tab-heading>
                        <div class = "profileTabContent row">                          
                        	<div class = "col-md-12 col-xs-12 applicationSetting">
                                    
                               <div class = "row">
                                    <div class = "col-md-2">
                                        Escort Needed
                                    </div>
                                    <div class = "col-md-9">

                                        <div class = "row escortNeeded">
                                            <label class = "radioLabel">
                                                <input type="radio" 
                                                       ng-model="escortNeeded" 
                                                       value="None" 
                                                       ng-disabled = "isDisable"
                                                       ng-change = "setEscortNeeded(escortNeeded)">None</label><br>
                                            <label class = "radioLabel">
                                                <input type="radio" 
                                                       ng-model="escortNeeded" 
                                                       value="Always" 
                                                       ng-disabled = "isDisable"
                                                       ng-change = "setEscortNeeded(escortNeeded)">Always</label><br>
                                            <label class = "radioLabel">
                                                <input type="radio" 
                                                       ng-model="escortNeeded" 
                                                       value="femalepresent"
                                                       ng-disabled = "isDisable" 
                                                       ng-change = "setEscortNeeded(escortNeeded)">When Female Pessenger Present</label><br>
                                            <label class = "radioLabel">
                                                <input type="radio" 
                                                       ng-model="escortNeeded" 
                                                       value="firstlastfemale" 
                                                       ng-change = "setEscortNeeded(escortNeeded)">For Last Drop and First Pickup Female Passenger</label>
                                            
                                        </div>
                                    </div>
                                </div>
                                    
                               <div class = "row">
                                    <div class = "col-md-2">
                                        Manager Approval Needed
                                    </div>
                                    <div class = "col-md-9">

                                        <div class = "row escortNeeded">
                                            <label class = "radioLabel">
                                                <input type="radio" 
                                                       ng-model="approvalNeeded" 
                                                       value="No" 
                                                       ng-change = "setapprovalNeeded(approvalNeeded)">No</label><br>
                                            <label class = "radioLabel">
                                                <input type="radio" 
                                                       ng-model="approvalNeeded" 
                                                       value="Yes" 
                                                       ng-disabled = "isDisable"
                                                       ng-change = "setapprovalNeeded(approvalNeeded)">Yes</label>                                            
                                        </div>
                                    </div>
                                </div>
                                
                         <!--        <div class = "row">
                                    <div class = "col-md-2">
                                        Base Location
                                    </div>
                                    <div class = "col-md-9">
                                        <label class = "radioLabel">
                                            <input type="radio" 
                                                   ng-model="baseLocation" 
                                                   value="Office" 
                                                   ng-change = "setLocation(baseLocation)">Office</label><br>
                                        <label class = "radioLabel">
                                            <input type="radio" 
                                                   ng-model="baseLocation" 
                                                   value="Change Location" 
                                                   ng-change = "setLocation(baseLocation)">Change Location</label>   <br><br>

                                            <input type = "button" 
                                                   class = "btn btn-success btn-sm saveButton_myProfile" 
                                                   value = "Save" 
                                                   ng-show = "!isChangeLocation"
                                                   ng-click = "saveButton()">

                                    </div>
                                </div>  -->                               
                                
                                <div class = "row" ng-show = "isChangeLocation">
                                    <div class = "col-md-2">
                                        Update Location
                                    </div>
                                    <div class = "col-md-9">
                                        <div class = "row updateLocationDiv">
                                            <div class = "col-md-2 col-xs-12">
                                                <span>Company Name</span>
                                            </div>
                                            <div class = "col-md-9 col-xs-12">
                                                <span>Newt Global</span>
                                            </div>
                                            <div class = "col-md-2 col-xs-12">
                                                <span>Company Address</span>
                                            </div>
                                            <div class = "col-md-9 col-xs-12">
                                                <div ng-show = "!isEdit" class = "editedAddress floatLeft">{{currLocation}}</div>
                                                <input  ng-show = "isEdit" 
                                                       type = "text" 
                                                       class = "editedAddress floatLeft" 
                                                       ng-model = "currLocation"> 
                                                <input  ng-show = "isEdit" 
                                                       type = "text" 
                                                       class = "editedAddress hidden" 
                                                       ng-model = "currCords"> 
                                                <span class = "floatLeft pointer"><i class ="icon-map-marker mapMarkerIcon"
                                                         tooltip="Click this to Find Location on the Map"
                                                         tooltip-placement="top"
                                                         tooltip-trigger="mouseenter"
                                                         ng-click = "openMap('lg')"></i></span>
                                            </div>
<!--
                                            <div class = "col-md-5 col-xs-12">
                                                <input type = "button" 
                                                       class = "btn btn-warning btn-sm" 
                                                       value = "Edit" 
                                                       ng-show= "!isEdit" 
                                                       ng-click = "editLocation()">
                                                <input type = "button" 
                                                       class = "btn btn-success btn-sm" 
                                                       value = "Save" ng-show= "isEdit || mapClick" 
                                                       ng-click = "saveLocation(currLocation)">
                                                <input type= "button" 
                                                       class = "btn btn-default btn-sm" 
                                                       value = "Cancel" 
                                                       ng-click = "cancelchangeLocation()"><br>
                                            </div>                                            
-->
                                        </div>                                        
                                    </div>
                                </div>  
                                
                                <div class = "row lastSaveButtonRow">
                                    <div class = "col-md-2">
                                       
                                    </div>
                                    <div class = "col-md-9">
                                            
                                <input type = "button" 
                                                   class = "btn btn-success btn-sm saveButton_myProfile" 
                                                   value = "Save" 
                                                   ng-click = "saveButton()">
                                    </div>
                                </div>
                           </div> 
                        </div>
                    </tab>
                    
                    <tab ng-click = 'getRequests()'>
                        <tab-heading>Requests Detail</tab-heading>
                    	<div class = "escortAvailableTabContent requestDetailMyProfile row">
                            <div class = "row firstRowActionDiv">
<!--
                                <div class = "col-md-2 floatLeft">
                                    <select ng-model="showRecords" 
                                            class = 'form-control requestDetailFirstRow'
                                            ng-options="pagination.text for pagination in paginations track by pagination.value"
                                            ng-change = "setLimit(showRecords)">
                                            <option value="">-- All Records --</option>
                                   </select>
                                </div>
-->
                                <div class = 'col-md-2'>
                                   <select ng-model="trip.tripType"
                                           class="form-control requestDetailFirstRow" 
                                           ng-options="tripType.text for tripType in tripTypes track by tripType.value"
                                           ng-change = "setTripType(trip.tripType)"
                                           required>
<!--                                     <option value="">-- Select Trip Type --</option>-->
                                  </select>                                    
                                </div>
                                <div class = 'col-md-4 searchEmployeeTravelDesk'>
<!--                                    <div class = "col-md-3 searchEmployeeTravelDesk">-->
                                        <div class = "input-group floatLeft calendarInput"> 
                                          <input ng-model="search.text"
                                                 type = "text" 
                                                 class="form-control" 
                                                 placeholder = "Search Pick/Drop Requests by Request Id"
                                                 maxlength =  '10'>
                                           <span class="input-group-btn">
                                               <button class="btn btn-success" 
                                                       ng-click="searchRequests(search.text)">
                                               <i class = "icon-search searchServiceMappingIcon"></i></button></span> 
                                        </div>
<!--                                    </div>                                    -->
                                </div>
                                <div class = 'col-md-2'></div>
                                <div class = "col-md-3 floatRight">
                                    <input type = "text" 
                                           class = 'form-control requestDetailFirstRow' 
                                           ng-model = "filterRequests" 
                                           placeholder = "Filter the Request Table">
                                </div>
                            </div>
                        <table class = "escortAvailableTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                    <th>Employee Id</th>
                                      <th>Pick/drop Time</th>
                                      <th>Shift Time</th>
                                      <th>Request Type</th>
                                      <th>Address</th>
                                      <th>Route Name</th>
                                      <th>Area Name</th>
                                      <th></th>
                                      <th></th>
                                    </tr> 
                                </thead>
                                <tbody>
		                           <tr ng-repeat = "requests in requestsData|filter:filterRequests">
		                                <td class = 'col-md-1'>{{requests.employeeId}}</td>
		                                <td class = 'col-md-1'>{{requests.pickupTime}}</td>
		                                 <td class = 'col-md-1'>{{requests.shiftTime}}</td>                                        
		                                 <td class = 'col-md-1'>{{requests.requestType}}</td>
		                                 <td class = 'col-md-3'>{{requests.address}}</td>                                        
		                                 <td class = 'col-md-1'>{{requests.routeName}}</td>
		                                 <td class = 'col-md-1'>{{requests.areaName}}</td>		                                 
		                                 <td class = 'col-md-1'>
                                             <input type = "button"
                                                    class = "btn btn-success btn-xs enable_vendorDeviceDetail"
                                                    value = "Enable"
                                                    ng-click = "enableRequest(requests)"
                                                    ng-class = "{disabled: requests.status=='Y'}">
                                                  
                                         </td>                                        
		                                 <td class = 'col-md-1'>
                                             <input type = "button"
                                                    class = "btn btn-danger btn-xs disable_vendorDeviceDetail"
                                                    value = "Disable"
                                                    ng-click = "disableRequest(requests)"
                                                    ng-class = "{disabled: requests.status=='N'}"></td>
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                        
                    </tab>
                    <tab>
                       <tab-heading ng-click = 'getAllShifts()'>Shift Times</tab-heading>
                    	<div class = "shiftTimeTabContent row">
                            <div class = "row firstRowActionDiv">
                                <div class = 'col-md-6' ng-show = '!addNewShiftIsClicked'>
                                    <input type = 'button' 
                                           value = 'Create New Shift Time' 
                                           class = 'btn btn-primary buttonRadius0 addShiftButton'
                                           ng-click = "addShiftTime()">
                                </div>
                                
                                <div class = 'col-md-6'>
        <!--                                    <label></label><br>-->
                                    <input type = 'text'
                                           class = 'form-control shiftFilter'
                                           placeholder = 'Filter Shift'
                                           ng-model = 'searchShift'>
                                </div>
                            </div>
<!--
                            <div class = "col-md-6" ng-show = 'addNewShiftIsClicked'>
                                <div class = 'addNewShiftMainDiv row'>
                                 <fieldset>
                                  <legend>Add New Shift</legend>
                                    </fieldset>
                                </div>
                            </div>
-->
                        <table class = "escortAvailableTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                    <th>Shift Time</th>
                                      <th>Trip Type</th>
<!--                                      <th></th>-->
                                    </tr> 
                                </thead>
                                <tbody>
		                           <tr ng-repeat = "shift in shiftTimeData | filter:searchShift">
		                                <td class = 'col-md-4' ng-show = '!shift.editShiftTimeIsClicked'>{{shift.shiftTime}}</td>                                           
                                        <td  class = 'col-md-4' ng-show = 'shift.editShiftTimeIsClicked'>    
                                             <timepicker ng-model="shift.timePicker" 
                                                      hour-step="hstep" 
                                                      minute-step="mstep" 
                                                      show-meridian="ismeridian" 
                                                      readonly-input = 'true'
                                                      class = "timepicker2_empReq floatLeft">
                                            </timepicker>
                                       </td>
		                                <td class = 'col-md-4'>{{shift.tripType}}</td>                              
<!--
		                                 <td class = 'col-md-4'>
                                             <input type = "button"
                                                    class = "btn btn-warning btn-xs buttonRadius0 editShiftTimeButton"
                                                    value = "Edit"
                                                    ng-click = "editShiftTime(shift, $index)"
                                                    ng-show = '!shift.editShiftTimeIsClicked'>                                             
                                             
                                             <input type = "button"
                                                    class = "btn btn-success btn-xs buttonRadius0 editShiftTimeButton"
                                                    value = "Save"
                                                    ng-click = "updateShiftTime(shift, $index)"
                                                    ng-show = 'shift.editShiftTimeIsClicked'>
                                             
                                             <input type = "button"
                                                    class = "btn btn-default btn-xs buttonRadius0 editShiftTimeButton"
                                                    value = "Cancel"
                                                    ng-click = "canceShiftTime(shift ,$index)"
                                                    ng-show = 'shift.editShiftTimeIsClicked'>
                                         </td>
-->
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                        
                    </tab>
                
                    <tab>
                       <tab-heading ng-click = 'getAllRouteNames()'>Route Names</tab-heading>
                    	<div class = "shiftTimeTabContent row">
                            <div class = "row firstRowActionDiv">
                                <div class = 'col-md-6 addNewRouteButton'>
                                    <input type = 'button' 
                                           value = 'Create New Route' 
                                           class = 'btn btn-primary buttonRadius0 addShiftButton'
                                           ng-click = 'addRouteName()'
                                           required>
                                </div>
                                <div class = 'col-md-6 addNewRouteDiv'>
                                    <label>Route Name</label><br>
                                    <input type = 'text' 
                                           ng-model = 'newRoute.routeName'
                                           placeholder = 'Enter Route Name'
                                           class = 'form-control floatLeft marginRight10 addShiftInput'>
                                    <input type = 'button' 
                                           value = 'Save New Route' 
                                           class = 'btn btn-success buttonRadius0 addShiftButton marginRight10 floatLeft'
                                           ng-click = 'saveNewRoute(newRoute)'
                                           ng-disabled = '!newRoute.routeName'>
                                    <input type = 'button' 
                                           value = 'Cancel' 
                                           class = 'btn btn-default buttonRadius0 addShiftButton floatLeft'
                                           ng-click = 'cancelNewRoute()'>
                                </div>
                                <div class = 'col-md-6'>
<!--                                    <label></label><br>-->
                                    <input type = 'text'
                                           class = 'form-control routeFilter'
                                           placeholder = 'Filter Route'
                                           ng-model = 'searchRouteName'>
                                </div>
                            </div>
                        <table class = "escortAvailableTable table table-responsive container-fluid">
                                <thead class ="tableHeading">
                                    <tr>
                                    <th>Route Id</th>
                                      <th>Route Name</th>
<!--                                      <th></th>-->
                                    </tr> 
                                </thead>
                                <tbody>
		                           <tr ng-repeat = "route in routeNameData | filter: searchRouteName">
		                                <td class = 'col-md-4'>{{route.routeId}}</td>    
		                                <td class = 'col-md-4' ng-show = '!route.editRouteNameIsClicked'>{{route.routeName}}</td>                                         
                                        <td  class = 'col-md-4' ng-show = 'route.editRouteNameIsClicked'>    
                                             <input type = "text"
                                                    class = "buttonRadius0"
                                                    ng-model = "route.routeNameEdit"
                                                    required>
                                       </td>                            
<!--
		                                 <td class = 'col-md-4'>
                                             <input type = "button"
                                                    class = "btn btn-warning btn-xs buttonRadius0 editShiftTimeButton"
                                                    value = "Edit"
                                                    ng-click = "editRouteName(route, $index)"
                                                    ng-show = '!route.editRouteNameIsClicked'>                                             
                                             
                                             <input type = "button"
                                                    class = "btn btn-success btn-xs buttonRadius0 editShiftTimeButton"
                                                    value = "Save"
                                                    ng-click = "updateRouteName(route, $index)"
                                                    ng-show = 'route.editRouteNameIsClicked'
                                                    ng-disabled = '!route.routeNameEdit'>
                                             
                                             <input type = "button"
                                                    class = "btn btn-default btn-xs buttonRadius0 editShiftTimeButton"
                                                    value = "Cancel"
                                                    ng-click = "canceRouteName(route, $index)"
                                                    ng-show = 'route.editRouteNameIsClicked'>
                                         </td>
-->
		                            </tr>                                   
		                         </tbody>
                            </table>
                        </div>
                    </tab>
                </tabset>
            </div>
           </div>
         </div> 
   </div>
<!--
<div class = "col-md-12 map_viewMap">
            <efmfm-new-user-map-location id = "mapDiv"></efmfm-new-user-map-location>            
        </div>-->
