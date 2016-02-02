(function(){
   var reportsCtrl = function($scope, $location, $anchorScroll, $timeout, $http){      
       $('.reportMenu').addClass('active');  
       $scope.currentTab = 'tripSheet';
       $scope.setMinDate = new Date();
	   $scope.numberofRecords = 10000;
       $scope.fromDate;
       $scope.toDate;
       $scope.thisMonth;
       var selectedFrom;
       var selectedTo;
       $scope.dateRangeError = false;
       $scope.openCustomRange = false;
       $scope.dates = [{'name':'Today', 'isClicked':false},
                       {'name':'Yesturday', 'isClicked':false},
                       {'name':'Last 7 Days', 'isClicked':false},
                       {'name':'Last 30 Days', 'isClicked':false},
                       {'name':'This Month', 'isClicked':false},
                       {'name':'Custom Range Date', 'isClicked':false}];
       
       $scope.paginations = [{'value':10, 'text':'10 records'},
	    	 				   {'value':15, 'text':'15 record'},
	    	 				   {'value':20, 'text':'20 records'}];
       
       $scope.gotNoShowResult = false;
       
       $scope.reportTypes = [{'value':"vendor", 'text':'By VENDOR'},
	    	 				   {'value':"vehicle", 'text':'By VEHICLE'}];
       
       $scope.reportTypes_attendance = [ {'value':"vehicle", 'text':'By VEHICLE'},
	    	 				             {'value':"driver", 'text':'By DRIVER'},
                                         {'value':"vendor", 'text':'By VENDOR'}];       
       
       $scope.tripTypes = [{'value':'PICKUP', 'text':'PICKUP'},
	    	 	           {'value':'DROP', 'text':'DROP'}];
       
       $scope.reportKM = [];
       $scope.reportKM.reportType= {};
       $scope.reportKM.distanceShift = {};
       $scope.vendorVehicles_KMReport = [];
       $scope.reportTypeSelected;
       $scope.gotKMResult = false;
       $scope.gotTripResult = false;
       $scope.viewDetail = false;
       $scope.searchFromDateKM;
       $scope.searchToDateKM;  
       $scope.isShiftKM = false;
       $scope.searchKMType;
       $scope.distanceShiftType;
       
       $scope.reportsKMData = [];
       $scope.gotLoginResult = false;
       $scope.gotDropResult = false;
       $scope.gotSMSResult = false;
       $scope.gotVehComplianceResult = false;
       $scope.gotDriverComplianceResult = false;
       $scope.viewDetailIsClicked = false;
       $scope.allVendors = [];
       $scope.tripTimes = [];
       
       $scope.searchOT = [];
       $scope.searchOT.type = {};
       $scope.searchOT.OTShift = {};
       $scope.searchOT.OTVendors = {};
       $scope.OTShiftTimes = [];
       $scope.OTAllVendors = [];
       $scope.OTTripTypes = [];
       $scope.OTTripType;
       $scope.OTShift;
       $scope.OTVendor;
       $scope.OTresultTripType;
       
       
       $scope.OTresultDateOrShift;
       $scope.OTtotalShift;
       
       
       $scope.gotOTResult = false;
       $scope.vendorNameShow = false;
       
       $scope.noShowResult = false; 
       
       
       $scope.OT;
       
       $scope.searchSU = [];
       $scope.searchSU.type = {};
       $scope.searchSU.SUShift = {};
       $scope.searchSU.SUVendors = {};
       $scope.SUShiftTimes = [];
       $scope.SUAllVendors = [];
       $scope.SUTripTypes = [];
       $scope.SUTripType;
       $scope.SUShift;
       $scope.SUVendor;
       $scope.SUresultTripType;
       $scope.gotSUResult = false;  
       $scope.reportSUOptions = [{text:'By Shift', value:'By Shift'}];
       
       $scope.searchNS = [];
       $scope.searchNS.type = {};
       $scope.NSTripTypes = [];  
       $scope.NSShiftTimes = [];
       $scope.NSTripType;  
       $scope.NSReportTitle;
       $scope.NSShift;
       $scope.searchFromDatesNS = '';
       $scope.searchToDatesNS = '';
       $scope.vendorWiseShiftTimes = [];
       $scope.DistanceShiftTime = [];
       $scope.vehicleNumberField;
       $scope.NSresultShift;
       $scope.searchNSByEmployeeId
       
       $scope.reportTSExcel = [];
       $scope.reportOTExcel = [];
       $scope.reportSUExcel = [];
       $scope.reportNSExcel = [];
       $scope.reportKMExcel = [];
       $scope.reportSMSExcel = [];
       
       $scope.reportNoShowOptions = [{text:'By Count', value:'By Count'},
                                     {text:'By Employee Id', value:'By Employee Id'}];
       $scope.noShowCounts = [{text:'1', value:'1'},
                              {text:'2', value:'2'},                              
                              {text:'3+', value:'3+'}];
       $scope.gotNSResult = false; 
       
       
       $scope.setLimit = function(showRecords){
	    	 if(!showRecords){$scope.numberofRecords = tripDataLength;
                              console.log(tripDataLength);
                             }
             else $scope.numberofRecords = showRecords.value;  	 
	    };
       
       $scope.dateOptions = {formatYear: 'yy',
                              startingDay: 1,
                              showWeeks: false
                            }; 
       
        $scope.formats = ['yyyy','dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate','dd-mm-yyyy', 'mm-yyyy'];
        $scope.monthformat = 'MMMM' + ' - ' + 'yyyy';

        $scope.datepickerOptions = {datepickerMode:"'month'",
                                    minMode:"month",
                                    minDate:"minDate",
                                    showWeeks:"false"};
       
        $scope.selectMonthCal = function($event){
            $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'selectMonth' : true};  
             }, 50);
        };
       
       //Date Picker for the 'From Date'
       $scope.fromDateCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'fromDate' : true};  
             }, 50);
         };
       
       //Date Picker for the 'To Date'
       $scope.toDateCal = function($event){
             $event.preventDefault();
            $event.stopPropagation();
            $timeout( function(){
               $scope.datePicker = {'toDate' : true};  
             }, 50);
         };
       
       //Set the Label of the Date Selection Button
       $scope.setDates = function(tabClicked){
    	   alert(tabClicked);
           $scope.isResult = false;
           //Set the Current Tab
           $scope.currentTab  = tabClicked;    
           //alert($scope.currentTab);
           if($scope.currentTab == 'centerBase'){
               $scope.thisMonth = new Date();               
           }
           else{ 
               if($scope.currentTab == 'onTime'){
                   if($scope.OTShiftTimes.length == 0 && $scope.OTAllVendors.length == 0 && $scope.OTTripTypes.length == 0){
                       $scope.OTShiftTimes = $scope.getShiftTime('PICKUP');
                       console.log($scope.OTShiftTimes);                       
                       $scope.OTAllVendors = $scope.getAllVendors();
                       $scope.OTTripTypes = $scope.tripTypes;
                       $scope.searchOT.OTShift = {'text':'All Shifts', 'value':0};
                       $scope.searchOT.OTVendors = {'name':'All Vendors', 'Id':0};
                       $scope.searchOT.type = {'value':'PICKUP', 'text':'PICKUP'};
                   }         
               }
               if($scope.currentTab == 'seatUtil'){
                   if($scope.SUShiftTimes.length == 0 && $scope.SUAllVendors.length == 0 && $scope.SUTripTypes.length == 0){
                       $scope.SUShiftTimes = $scope.getShiftTime('PICKUP');
                       console.log($scope.SUShiftTimes);                       
                       $scope.SUAllVendors = $scope.getAllVendors();
                       $scope.SUTripTypes = $scope.tripTypes;
                       $scope.searchSU.SUShift = {'text':'All Shifts', 'value':0};
                       $scope.searchSU.SUVendors = {'name':'All Vendors', 'Id':0};
                       $scope.searchSU.type = {'value':'PICKUP', 'text':'PICKUP'};
                       $scope.searchSU.SUOption = {text:'By Shift', value:'By Shift'};
                   } 
               }
               if($scope.currentTab == 'noShow'){
                   //alert($scope.NSTripTypes.length);
 //                  if($scope.NSTripTypes.length == 0){
                       $scope.NSShiftTimes = $scope.getShiftTime('PICKUP');
                       $scope.NSTripTypes = $scope.tripTypes;
                       $scope.searchNS.NSShift = {'text':'All Shifts', 'value':0};

                       //alert(JSON.stringify($scope.NSTripTypes));
                       $scope.searchNS.type = {'value':'PICKUP', 'text':'PICKUP'};
                       $scope.searchNS.NSOption = {text:'By Count', value:'By Count'};
                       $scope.searchNS.NSCount = {text:'1', value:'1'};
//                   }        
               }
               if($scope.currentTab == 'kmReport'){
                   $scope.DistanceShiftTimes = [];
                   //$scope.DistanceShiftTimes = $scope.getAllShiftTime();
                   $scope.DistanceShiftTimes.push({'text':'All Shifts', 'value':0});
                   $scope.DistanceShiftTimes.push({'text':'By Shifts', 'value':1});
                   
                   $scope.reportKM.distanceShift = {'text':'By Shifts', 'value':1};
                   $scope.reportKM.reportType= {'value':"vendor", 'text':'By VENDOR'};
                   $scope.getVendorOrVehicle($scope.reportKM.reportType);
               }
               //Set all the items from the Dates Drop Downs to false
               angular.forEach($scope.dates, function(item){
                            item.isClicked = false;});
               //Set today's, tomorrow's date
               var todaysDate = new Date();
               var tomorrowDate = new Date();
               tomorrowDate.setDate(tomorrowDate.getDate() + 1);
               $scope.dates[0].value = todaysDate;
               $scope.fromDate = todaysDate;
               $scope.toDate = tomorrowDate;
           }
       };
       
       $scope.setDates($scope.currentTab);
       
       //Convert the dates in DD-MM-YYYY format
       var convertDateUTC = function(date){
          var convert_date = new Date(date);
          var currentMonth = convert_date.getMonth()+1;
          var currentDate=convert_date.getDate();
              if(currentDate<10){
            	  if (currentDate < 10) { 
            		  currentDate = '0' + currentDate;
                  }  
              }
              if (currentMonth < 10) { 
                  currentMonth = '0' + currentMonth; 
              }
          return currentDate+'-'+currentMonth+'-'+convert_date.getFullYear();   
        };
       
       //This Function is called on the Click of the ITEMS in the Date Popover
       $scope.updateDate = function(type){           
           switch(type) {
                case 'today':
                    $scope.openCustomRange = false;
                    $scope.getReport(new Date(), new Date());                   
                    angular.forEach($scope.dates, function(item){
                        item.isClicked = false;
                    });
                    $scope.dates[0].isClicked = true;
                    break;
                case 'yesturday':
                   $scope.openCustomRange = false;
                   var yesturdayDate = new Date();
                   yesturdayDate.setDate(yesturdayDate.getDate()-1);
                   $scope.getReport(yesturdayDate, yesturdayDate);
                   angular.forEach($scope.dates, function(item){
                        item.isClicked = false;
                    });
                    $scope.dates[1].isClicked = true;
                    break;
                case 'last7':
                   $scope.openCustomRange = false;
                   var last7 = new Date();
                   last7.setDate(last7.getDate()-7);
                   $scope.getReport(last7, new Date());                   
                   angular.forEach($scope.dates, function(item){
                        item.isClicked = false;
                    });
                    $scope.dates[2].isClicked = true;
                    break;
                case 'last30':
                    $scope.openCustomRange = false;
                   var last30 = new Date();
                   last30.setDate(last30.getDate()-30);
                   $scope.getReport(last30, new Date());                   
                   angular.forEach($scope.dates, function(item){
                        item.isClicked = false;
                    });
                    $scope.dates[3].isClicked = true;
                    break;
                case 'thisMonth':
                    $scope.openCustomRange = false;
                    var thisMonth = new Date();
                    thisMonth.setMonth(thisMonth.getMonth(), 1);
                    $scope.dates[4].value = thisMonth;
                    $scope.getReport(thisMonth, new Date());                   
                    angular.forEach($scope.dates, function(item){
                        item.isClicked = false;
                    });
                    $scope.dates[4].isClicked = true;
                    break;
                case 'customDate':
                   if($scope.openCustomRange){
                       $scope.openCustomRange = false;
                       $scope.from = '';
                       $scope.to = '';
                   }
                   else{
                       $scope.openCustomRange = true;    
                      }
                   angular.forEach($scope.dates, function(item){
                        item.isClicked = false;
                    });
                   break;
            }          
        };   
       
       //Function to check the Date Validity
       $scope.checkDateRangeValidity = function(fromDate, toDate){
           console.log('checkDateRangeValidity Function :: [fromDate - toDate] ' + fromDate + ' - ' + toDate);
           if(fromDate > toDate){
               $scope.dateRangeError = true;
               $timeout(function() {$scope.dateRangeError = false; }, 3000);
               return false;
           }
           else return true;
       };
      
      //First Tab Function - Trip Sheet 
       $scope.getReport = function(fromDate, toDate){ 
           selectedFrom = fromDate;
           selectedTo = toDate;
           $scope.efmfilter = {};
           $scope.progressbar.start();
           console.log('GetReport Function :: [fromDate - toDate] ' + fromDate + ' - ' + toDate);
           console.log('GetReport Function :: [CurrenTab] ' + $scope.currentTab);
           
           //Call the function to Check the Date range Validity before any other action
           if($scope.checkDateRangeValidity(new Date(fromDate).getTime(), new Date(toDate).getTime())) { 
             var data = {
				 eFmFmClientBranchPO:{branchId:branchId},
				 fromDate:convertDateUTC(fromDate),
				 toDate:convertDateUTC(toDate)
			 };
            console.log('Getting the Report for :: ' + convertDateUTC(fromDate) + ' - ' + convertDateUTC(toDate));
            
            //If Trip Sheet Tab is Ckicked
            if($scope.currentTab == 'tripSheet'){
                $('.popover').hide();
                $scope.efmfilter= {filterTrips: ''};
                //alert($scope.filterTrips);
                 $http.post('services/report/tripSheet/',data).
                    success(function(data, status, headers, config) {
                       console.log(data);
                       $scope.tripSheetData = data.data;
 //                    alert(JSON.stringify($scope.tripSheetData));
                       $scope.gotTripResult = true;
                       $scope.fromDate = fromDate;
                       $scope.toDate = toDate;
                       $scope.searchFromDateTS = fromDate;
                       $scope.searchToDateTS = toDate;
                    }).
                    error(function(data, status, headers, config) {
                          // log error
                    });
             }
           
           //Check if KM Report Tab has been clicked                 
           if($scope.currentTab == 'kmReport'){
               $scope.isShiftKM = false;
                $('.popover').hide(); 
               $scope.efmfilter= {filterKm:''};
               //alert($scope.reportKM.distanceShift.text + " - " + $scope.reportKM.distanceShift.value);
               if(angular.isObject($scope.reportKM.reportType) && angular.isObject($scope.reportKM.VSelection)){
                    $scope.reportsSum = [];
                    //var searchType = $scope.reportKM.reportType.value;
                    $scope.searchKMType = $scope.reportKM.reportType.value;
                    $scope.distanceShiftType = $scope.reportKM.distanceShift.text;
                    var vendor_id;
                    var vehicle_id;                    
                    //Saimaa if we will select All vendor then please send me Vendor ID as 0;
                    if($scope.searchKMType == 'vendor'){
                       vendor_id = $scope.reportKM.VSelection.Id;
                       vehicle_id = 0;
//                        alert(vendor_id);
                    }
                    else if ($scope.searchKMType == 'vehicle'){
                       vendor_id = 0;
                       vehicle_id = $scope.reportKM.VSelection.Id;               
                    }
//                 alert("vendorId"+vendor_id+"vehicleId"+vehicle_id+"searchType"+searchType)
                   if($scope.reportKM.distanceShift.text == 'By Shifts'){
                    $scope.isShiftKM = false;
                   }
                   
                   else if($scope.reportKM.distanceShift.text == 'All Shifts' && $scope.reportKM.VSelection.Id != 0){
                       $scope.isShiftKM = false;
                      }
                   else if($scope.searchKMType == 'vehicle'){
                       $scope.isShiftKM = false;
                      }
                   
                   else{
                    $scope.isShiftKM = true;
                   }

                    var dataKm = {
                     eFmFmClientBranchPO:{branchId:branchId},
                     fromDate:convertDateUTC(fromDate),
                     toDate:convertDateUTC(toDate),
                     vendorId:vendor_id,
                     vehicleId:vehicle_id,
                     time:$scope.reportKM.distanceShift.value,
                     searchType:$scope.searchKMType, //In case vendor please chenage the vehicle to vendor and add vendorId and searchType='vendor' as well
                    };
                    console.log(dataKm);
                    $http.post('services/report/kmreports/',dataKm).
                       success(function(data, status, headers, config) {
                     //alert(JSON.stringify(data));
                           $scope.reportsSum = data;
                           $scope.vehicleNumberField=vendor_id;
                           angular.forEach($scope.reportsSum, function(item){item.viewDetailIsClicked = false;})
                           console.log($scope.reportsSum);
                           $scope.gotKMResult = true;
                           $scope.viewDetail = false;                         
                           $scope.fromDate = fromDate;
                           $scope.toDate = toDate;                           
                           $scope.searchFromDateKM = fromDate;
                           $scope.searchToDateKM = toDate; 
                           $scope.headingReportTypeKM = $scope.reportKM.reportType.text;
                           $scope.reportSelectionKM = $scope.reportKM.VSelection.name;
                        }).
                        error(function(data, status, headers, config) {
                                   // log error
                        });
               }else{  
//                   $('.reportDateList').removeClass('inActive');
                   $scope.showalertMessage('Please choose valid selection from the dropdowns', "");
               } 
           }
        
            //Check if SMS Report Tab is clicked
           if($scope.currentTab == 'SMSReport'){
                $('.popover').hide();
                $scope.efmfilter= {filterSMSShow:''};
                $http.post('services/report/smsreport/',data).
                    success(function(data, status, headers, config) {
                        $scope.gotSMSResult = true;
                        console.log(data);
                        $scope.reportsSMSData = data;
                        $scope.fromDate = fromDate;
                        $scope.toDate = toDate;
                        $scope.searchFromDateSMS = fromDate;
                        $scope.searchToDateSMS = toDate;
                    }).
                    error(function(data, status, headers, config) {
                               // log error
                    });
           }  
               
          //Check if Escort Required Tab is clicked
          if($scope.currentTab == 'escortRequired'){
               
           }
           
          //Check if Vehicle And Driver attendance report Tab is clicked
          if($scope.currentTab == 'vehicleDriverAttendence'){
               
           }
           
          //Check if Over Speed report Tab is clicked
          if($scope.currentTab == 'overSpeed'){
               
          }

          //Check if Route wise travel time Report is clicked
          if($scope.currentTab == 'travelTimeByRoute'){
               
          }
               
           //Check if On Time ArrivalReport is clicked
           if($scope.currentTab == 'onTime'){               
               $('.popover').hide();
               $scope.efmfilter= {filterOTData: ''};
               if(angular.isObject($scope.searchOT.OTVendors) && angular.isObject($scope.searchOT.OTShift)){ 
                   $scope.headingVendorsOT = $scope.searchOT.OTVendors.name;
                   $scope.headingShiftOT = $scope.searchOT.OTShift.text;
                   $scope.searchFromDateOT = fromDate;
                   $scope.searchToDateOT = toDate;
                   
                   if($scope.searchOT.OTVendors.Id == 0 && $scope.searchOT.OTShift.value == 0){
                      // alert("$scope.searchOT.OTVendors.id " + $scope.searchOT.OTVendors.Id + " $scope.searchOT.OTShift.value" + $scope.searchOT.OTShift.value); //Uncoment to check the value
                   }
                   if($scope.searchOT.type.value == 'PICKUP'){
//               	   alert("vendorId"+$scope.searchOT.OTVendors.Id+"shiftTime"+$scope.searchOT.OTShift.value);
                       //alert("In Pickup Loop for OTA");
//                	   alert($scope.searchOT.OTShift.value+"Ven"+$scope.searchOT.OTVendors.Id);
                       var data = {
                         eFmFmClientBranchPO:{branchId:branchId},
                         fromDate:convertDateUTC(fromDate),
                         toDate:convertDateUTC(toDate),
                         tripType:$scope.searchOT.type.value,
                         time:$scope.searchOT.OTShift.value,
                         vendorId:$scope.searchOT.OTVendors.Id
                       };
                       $http.post('services/report/ontimearrival/',data).
                            success(function(data, status, headers, config) {
 //                           	alert(JSON.stringify(data));
                               console.log(data);
                               $scope.onTimeData = data.tripDetail;                          
                               $scope.fromDate = fromDate;
                               $scope.toDate = toDate;
                               if($scope.onTimeData.length>0){
                                   $scope.gotOTResult = true;                                   
                                   $scope.OTresultTripType = 'Pickup';
                                   
                                   if($scope.searchOT.OTVendors.Id != 0){
                                	   $scope.vendorName = true; 
                                   }
                                   else{$scope.vendorName = false; 
                                       }
                                   
                                   if($scope.searchOT.OTShift.value == 0){
                                	   $scope.OTresultDateOrShift='Date';
                                   }
                                   else{
                                	   $scope.OTresultDateOrShift='Shift Time'; 
                                   }
                                   
                                   $scope.OT = 'A';
                                   }
                               else{
                                   $scope.gotOTResult = false;
                                   $scope.showalertMessage('No Data Found. Please Change the Dates', "");                                   
                                   }
                            }).
                            error(function(data, status, headers, config) {
                                       // log error
                            });
                   }
                   else if($scope.searchOT.type.value == 'DROP'){
//                	   alert("vendorId"+$scope.searchOT.OTVendors.Id+"shiftTime"+$scope.searchOT.OTShift.value);
 //               	   alert("drop");
                      // alert("In Drop Loop for OTD");
                       var data = {
                         eFmFmClientBranchPO:{branchId:branchId},
                         fromDate:convertDateUTC(fromDate),
                         toDate:convertDateUTC(toDate),
                         tripType:$scope.searchOT.type.value,
                         time:$scope.searchOT.OTShift.value,
                         vendorId:$scope.searchOT.OTVendors.Id
                       };
                       $http.post('services/report/ontimearrival/',data).
                            success(function(data, status, headers, config) {
 //                           	alert(JSON.stringify(data));
                               console.log(data);
                               $scope.onTimeData = data.tripDetail;                           
                               $scope.fromDate = fromDate;
                               $scope.toDate = toDate;
                               if($scope.onTimeData.length>0){
                                   $scope.gotOTResult = true;                                   
                                   $scope.OTresultTripType = 'Drop';
                                   if($scope.searchOT.OTVendors.Id != 0){
                                	   $scope.vendorName = true; 
                                   }
                                   if($scope.searchOT.OTShift.value == 0){
                                	   $scope.OTresultDateOrShift='Date';
                                   }
                                   else{
                                	   $scope.OTresultDateOrShift='Shift Time'; 
                                   }
                                   $scope.OT = 'D';
                                   }
                               else{
                                   $scope.gotOTResult = false;
                                   $scope.showalertMessage('No Data Found. Please Change the Dates', "");                                   
                                   }
                            }).
                            error(function(data, status, headers, config) {
                                       // log error
                            });
                   }
               }
           }          
           //Check if Seat Utilization Report is clicked
           if($scope.currentTab == 'seatUtil'){               
               $('.popover').hide();
               $scope.efmfilter= {filterSeatUtilizationData:''};
               $scope.gotSUResult = false;
               $scope.SUTripType = '';
               $scope.SUReportTitle = '';
               $scope.searchFromDatesSU = '';
               $scope.searchToDatesSU = '';
            	   var data = {
                           eFmFmClientBranchPO:{branchId:branchId},
                           fromDate:convertDateUTC(fromDate),
                           toDate:convertDateUTC(toDate),
                           tripType:$scope.searchSU.type.value,
                           time:$scope.searchSU.SUShift.value
                         };
                         $http.post('services/report/seatutilization/',data).
                         success(function(data, status, headers, config) {
 //                       	 alert(JSON.stringify(data));
                               console.log(data);
                               $scope.seatUtilData = data.tripDetail;                          
                               $scope.fromDate = fromDate;
                               $scope.toDate = toDate;
                               if($scope.seatUtilData.length>0){
 //                                alert($scope.seatUtilData.length);
                                   $scope.gotSUResult = true;    
                                   $scope.SUresultTripType = $scope.searchSU.type.text;
                                   $scope.SUTripType = $scope.searchSU.type.text;
                                   $scope.SUReportTitle = 'Monthly Report for '+$scope.searchSU.SUShift.text;
                                   $scope.searchFromDatesSU = fromDate;
                                   $scope.searchToDatesSU = toDate;
                                   if($scope.searchSU.SUShift.value == 0){
                                	   $scope.SUresultDateOrShift='Date';
                                   }
                                   else{
                                	   $scope.SUresultDateOrShift='Shift Time'; 
                                   }
                                   }
                               else{
                                   $scope.gotSUResult = false;
                                   $scope.showalertMessage('No Data Found. Please Change the Dates', "");                                   
                                   }
                            }).
                            error(function(data, status, headers, config) {
                                       // log error
                            });
           }
           
           //If the No Show Tab is clicked
           if($scope.currentTab == 'noShow'){              
               $('.popover').hide();
               $scope.efmfilter= {filterNoShowData:''}
               $scope.gotNSResult = false;
               $scope.NSTripType = '';
               $scope.NSReportTitle = '';
               $scope.searchFromDatesNS = '';
               $scope.searchToDatesNS = '';  
             // alert($scope.searchNS.NSShift.value);
               //If the Search option is 'By Employee Id'
          /*     alert($scope.searchNS.NSCount.value);
               alert("option="+$scope.searchNS.NSOption.value+",employeeid="+$scope.searchNS.employeeId+",shiftTime="+$scope.searchSU.SUShift.value
            		   +",type="+$scope.searchNS.type.value);*/
               if(!$scope.searchNS.employeeId){
            	   employeeId='0';
                   $scope.searchNSByEmployeeId = false;
               }
               else{
            	   employeeId=$scope.searchNS.employeeId;
                   $scope.searchNSByEmployeeId = true;
                   $scope.searchNS.NSShift = {text:'All Shifts', value: 0};
               }
   /*            if($scope.searchNS.NSOption.value == 'By Employee Id' && !$scope.searchNS.employeeId){
                    $scope.showalertMessage('Please enter the valid Employee Id', "");
               }*/
 //              else if ($scope.searchNS.NSOption.value == 'By Employee Id' && $scope.searchNS.employeeId){
                   var data = {
                         eFmFmClientBranchPO:{branchId:branchId},
                         fromDate:convertDateUTC(fromDate),
                         toDate:convertDateUTC(toDate) ,  
                         tripType:$scope.searchNS.type.value,
                         time:$scope.searchNS.NSShift.value,
                         employeeId:employeeId,

                       };
 //              alert(JSON.stringify(data));
                       $http.post('services/report/noshowreport/',data).
                            success(function(data, status, headers, config) {   
 //                           	alert(JSON.stringify(data));
                               $scope.fromDate = fromDate;
                               $scope.toDate = toDate;
                                $scope.noShowReportData = data.tripDetail;
                                if($scope.noShowReportData.length>0){
                                  $scope.gotNSResult = true;
//                                    alert('By Employee Id');
//                                    alert(JSON.stringify($scope.noShowReportData));
                                  
                                   $scope.NSResultTripType = $scope.searchNS.type.text;    
                                   $scope.NSTripType = $scope.searchNS.type.text;  
                                   $scope.NSresultShift = $scope.searchNS.NSShift.text;
                                   if($scope.searchNSByEmployeeId){
                                       $scope.NSReportTitle = 'No Show Report for By Employee Id';
                                   }
                                   else{
                                        $scope.NSReportTitle = "No Show Report for " + $scope.searchNS.NSShift.text;
                                   }
                                   $scope.searchFromDatesNS = $scope.fromDate;
                                   $scope.searchToDatesNS = $scope.toDate;
                                   if($scope.searchNS.NSShift.value == 0 && employeeId ==0){
                                	   $scope.noShowResult = 0; //All Shifts with No Employee Id
                                	  // $scope.NSresultDateOrShift='Date';
                                	   $scope.NStotalShift=$scope.searchNS.type.value+' Trips';
                                	   $scope.NSTripType=$scope.searchNS.type.value +' Pax';
                                   } 
                                   else if($scope.searchNS.NSShift.value == 0 && employeeId !=0){ 
                                	   $scope.noShowResult = 1; //All Shifts with Employee Id
                                	   //$scope.NSresultDateOrShift='Date'; 
                                	   $scope.NStotalShift='Employee Id';
                                	   $scope.NSTripType='Employee Name';

                                   }
                                   else{ 
                                	   $scope.noShowResult = 1; //By Shift
                                	  // $scope.NSresultDateOrShift='Shift Time'; 
                                	   $scope.NStotalShift='Employee Id';
                                	   $scope.NSTripType='Employee Name';
                                	   
                                   }
                                }
                                else{
                                   $scope.gotNSResult = false;
                                   $scope.showalertMessage('No Data Found. Please Change the Dates', "");
                                }
                            }).
                            error(function(data, status, headers, config) {
                                       // log error
                            });                   
           }  
          //Escort Required Reports
           if($scope.currentTab == 'escortReport'){               
               $('.popover').hide();
            	   var data = {
                           eFmFmClientBranchPO:{branchId:branchId},
                           fromDate:convertDateUTC(fromDate),
                           toDate:convertDateUTC(toDate),
                         };
                         $http.post('services/report/speedReport/',data).
                         success(function(data, status, headers, config) {
                        	 alert(JSON.stringify(data));
                               console.log(data);
                               $scope.seatUtilData = data.tripDetail;                          
                               $scope.fromDate = fromDate;
                               $scope.toDate = toDate;
                               if($scope.seatUtilData.length>0){
                                   }
                               else{
                                   $scope.gotSUResult = false;
                                   $scope.showalertMessage('No Data Found. Please Change the Dates', "");                                   
                                   }
                            }).
                            error(function(data, status, headers, config) {
                                       // log error
                            });
           }
      }
    $scope.progressbar.complete();
   };          
       
//       $scope.openSearchFromDateCal = function($event){
//            $event.preventDefault();
//            $event.stopPropagation();
//            $timeout( function(){
//               $scope.datePicker = {'searchFromDate' : true};  
//             }, 50);
//        };
//        
//        $scope.openSearchToDateCal = function($event){              
//           $event.preventDefault();
//           $event.stopPropagation();
//            $timeout( function(){
//               $scope.datePicker = {'searchToDate' : true};  
//             }, 50);            
//        };
       
       var isValidDate = function(from, to){
           var fromDate = new Date(from).getTime();
           var toDate = new Date(to).getTime();
           if(fromDate<=toDate){return true;}
           else return false;
       };
       
       //Kilometer Report Click Select on Report Type
       $scope.getVendorOrVehicle = function(reportType){
           $scope.vendorVehicles_KMReport = [];
           if(angular.isObject(reportType)){
               $scope.gotKMResult = false;
               $scope.reportTypeSelected = reportType.value;
               if(reportType.value === "vendor"){
  //                 alert("ByVENDOR");
                   var data = {
                      branchId:branchId
                   };
                  $http.post('services/contract/allActiveVendor/',data).
                        success(function(data, status, headers, config) {
//                              alert(JSON.stringify(data));
                              angular.forEach(data, function(item){
                                  $scope.vendorVehicles_KMReport.push({'name':item.name, 'Id':item.vendorId});
                              });
//                              $scope.vendorVehicles_KMReport = data; 
                              $scope.vendorVehicles_KMReport.unshift({'name':'All Vendors', 'Id':0});
                              $scope.reportKM.VSelection = $scope.vendorVehicles_KMReport[0];
                        }).
                        error(function(data, status, headers, config) {
                              // log error
                        });
                   //$scope.vendorVehicles_KMReport = data; //Note: this array should be in this format [{value: "UBER", text: "UBER"}]
               }
               else {
                   $scope.vendorVehicles_KMReport.unshift({'name':'All Vehicles', 'Id':0});
                   $scope.reportKM.VSelection = $scope.vendorVehicles_KMReport[0];
//                   alert("ByVEHICLE");
//                    var data = {
//                       branchId:branchId
//                    };
//                    $http.post('services/vehicle/actualvehiclelist/',data).
//                         success(function(data, status, headers, config) {
// //                              alert(JSON.stringify(data));
//                               angular.forEach(data, function(item){
//                                   $scope.vendorVehicles_KMReport.push({'name':item.vehicleNum, 'Id':item.vehicleId});
//                                });
////                                               $scope.vendorVehicles_KMReport = data; 
////                               $scope.vendorVehicles_KMReport.unshift({'name':'All Vehicles', 'Id':'all'});
//                               $scope.reportKM.VSelection = $scope.vendorVehicles_KMReport[0];
//                         }).
//                         error(function(data, status, headers, config) {
//                               // log error
//                         });
                                    //$scope.vendorVehicles_KMReport = data; //Note: this array should be in this format [{value: "UBER", text: "UBER"}]
                                
                   //$scope.vendorVehicles_KMReport = data; //Note: this array should be in this format [{value: "UBER", text: "UBER"}]
               }
           }
//           alert(JSON.stringify(reportType));
       }; 
       
       $scope.viewKMDetails = function(reportSum){
           
           $scope.reportsKMData = [];
            if($scope.reportKM.reportType.value == 'vendor'){
                var data = {
                     eFmFmClientBranchPO:{branchId:branchId},
                     fromDate:convertDateUTC(selectedFrom),
                     toDate:convertDateUTC(selectedTo),
                     vendorId:reportSum.vendorId
                 };                
                $http.post('services/report/vendorvehiclekm/',data).
                   success(function(data, status, headers, config) {
                    if(reportSum.viewDetailIsClicked){
//                        alert("loop if reportSum.viewDetailIsClicked = " + reportSum.viewDetailIsClicked);
                        reportSum.viewDetailIsClicked = false;
                        $scope.viewDetail = false;
//                        $scope.gotKMResult = false;
                    }
                    else if(!reportSum.viewDetailIsClicked){ 
//                        alert("loop if reportSum.viewDetailIsClicked = " + reportSum.viewDetailIsClicked);                       
                       console.log(data);                        
                       $scope.reportsKMData = data;
                       reportSum.viewDetailIsClicked = true;
                       $('.reportSum').addClass("kmSum");
                       $scope.viewDetail = true;
//                       $scope.gotKMResult = true; 
//                    $('.reportTable_kmDetail').show();}
                }
            }).
            error(function(data, status, headers, config) {
                               // log error
                         });   
            }       
       };
       
       //Set the Report Type in Vehicle Driver Attendance Report
       $scope.setReportType_attendance = function(reportType){
           //alert(reportType.value);
       };
       
       $scope.setTripTypeOT = function(type){
           //alert(type.text);
           $scope.OTTripType = type.text;
           $scope.OTShiftTimes = $scope.getShiftTime(type.text);
       };
       
       $scope.setShiftOT = function(OTShift){
           $scope.OTShift = OTShift;
       };
       
       $scope.setVendorOT = function(OTVendors){
           $scope.OTVendor = OTVendors.name;
       };
       
       $scope.setTripTypeSU = function(type){
            $scope.SUShiftTimes = $scope.getShiftTime(type.text);
       };
       
       $scope.setTripTypeNS = function(type){
            $scope.NSShiftTimes = $scope.getShiftTime(type.text);
       }

//****************************************EXCEL DOWNLOADS METHODS*******************************************//
       $scope.saveInExcel = function(){
           if($scope.currentTab == 'tripSheet'){
               var blob = new Blob([document.getElementById('exportableTripSheet').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
                });
                saveAs(blob, "TripSheetReport.xls");
//               $scope.reportTSExcel = [];
//               angular.forEach($scope.tripSheetData, function(item){
//                   $scope.reportTSExcel.push({'Travelled Date':item.tripCompleteDate, 
//                                              'Employee Id':item.empId, 
//                                              'Shift Name':item.shiftTime, 
//                                              'Shift time':item.shiftTime,
//                                              'Address':item.employeeAddress,
//                                              'Trip Type':item.tripType})
//               }); 
//                alasql('SELECT * INTO XLSX("tripSheetReport.xlsx",{headers:true}) FROM ?',[$scope.reportNSExcel]);
               
           }
           
           if($scope.currentTab == 'onTime'){
               var blob = new Blob([document.getElementById('exportableOnTime').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
                });
                saveAs(blob, "onTimeReport.xls");
//               $scope.reportOTExcel = [];
//               if($scope.headingShiftOT == 'All Shifts'){
// //                  alert($scope.headingVendorsOT);
//                   if($scope.headingVendorsOT == 'All Vendors'){
//                	   if($scope.OTresultTripType=='Pickup'){
//                           angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Date':item.tripDates,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Delay Trips':item.totalDelayTrips,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//                           });
//                        
//                	   }
//                	   else{
//                       angular.forEach($scope.onTimeData, function(item){
//                           $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                      'Date':item.tripDates,
//                                                      'Actual Users':item.totalAllocatedEmployeesCount,
//                                                      'Total Fleets of the Day':item.totalUsedVehicles,
//                                                      'PAX':item.totalEmployeesPickedDropCount,
//                                                      'Trips':item.totalTrips,
//                                                      'On Time Trips':item.onTimeTrips,
//                                                      'On Time in %':item.delayTripsPercentage,
//                                                      'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                      'No Show':item.totalEmployeesNoShowCount});
//                       });
//                   }
//                   }
//                   else{
//                	   if($scope.OTresultTripType=='Pickup'){
//                           angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Date':item.tripDates,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Delay Trips':item.totalDelayTrips,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//                           });
//                        alert("HELLO")
//                        alert(JSON.stringify($scope.reportOTExcel))
//                	   }
//                	   else{
//                       angular.forEach($scope.onTimeData, function(item){
//                           $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                      'Date':item.tripDates,
//                                                      'Actual Users':item.totalAllocatedEmployeesCount,
//                                                      'Total Fleets of the Day':item.totalUsedVehicles,
//                                                      'PAX':item.totalEmployeesPickedDropCount,
//                                                      'Trips':item.totalTrips,
//                                                      'On Time Trips':item.onTimeTrips,
//                                                      'On Time in %':item.delayTripsPercentage,
//                                                      'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                      'No Show':item.totalEmployeesNoShowCount});
//                       });
//                   }
//                }
//               }
//               else if($scope.headingShiftOT == 'By Shifts'){
//                   if($scope.headingVendorsOT == 'All Vendors'){
//                	   if($scope.OTresultTripType == 'Pickup'){
//                           angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Shift Time':item.tripDates,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Delay Trips':item.totalDelayTrips,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//
//
//                           });
//                        
//                	   }
//                	   else{
//                           angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Shift Time':item.tripDates,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//
//
//                           });
//                         
//                	   }
//                   }
//                   else{
//                	   if($scope.OTresultTripType == 'Pickup'){
//                           angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Shift Time':item.tripDates,
//                                                          'Vendor Name':item.vendorName,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Delay Trips':item.totalDelayTrips,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//
//                           });
//                         
//                	   }
//                	   else{
//                          angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Shift Time':item.tripDates,
//                                                          'Vendor Name':item.vendorName,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//                           });
//                        
//                	   }
//                   }
//               }
//               else if(($scope.headingShiftOT != 'By Shifts') && ($scope.headingShiftOT != 'All Shifts')){
//                   if($scope.headingVendorsOT == 'All Vendors'){
//                	   if($scope.OTresultTripType == 'Pickup'){
//                           angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Shift Time':item.tripDates,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Delay Trips':item.totalDelayTrips,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//
//
//                           });
//                        
//                	   }
//                	   else{
//                           angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Shift Time':item.tripDates,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//
//
//                           });
//                         
//                	   }
//                   }
//                   else{
//                	   if($scope.OTresultTripType == 'Pickup'){
//                           angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Shift Time':item.tripDates,
//                                                          'Vendor Name':item.vendorName,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Delay Trips':item.totalDelayTrips,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//
//
//                           });
//                         
//                	   }
//                	   else{
//                          angular.forEach($scope.onTimeData, function(item){
//                               $scope.reportOTExcel.push({'Trip Type':$scope.OTresultTripType,
//                                                          'Shift Time':item.tripDates,
//                                                          'Vendor Name':item.vendorName,
//                                                          'Actual Users':item.totalAllocatedEmployeesCount,
//                                                          'Total Fleets of the Day':item.totalUsedVehicles,
//                                                          'PAX':item.totalEmployeesPickedDropCount,
//                                                          'Trips':item.totalTrips,
//                                                          'On Time Trips':item.onTimeTrips,
//                                                          'On Time in %':item.delayTripsPercentage,
//                                                          'Beyound Login time': item.totalDelayTripsBeyondLogin,
//                                                          'No Show':item.totalEmployeesNoShowCount});
//                           });
//                        
//                	   }
//                   }
//               
//            	   
//               }
//               
               
//               var sheetLabel = $scope.OTresultTripType+" "+$scope.headingVendorsOT;
//               var opts = [{sheetid:sheetLabel,header:true}];
//               
//               console.log(opts);
//               alasql('SELECT INTO XLSX("onTimeReport.xlsx",?) FROM ?',[opts,[$scope.onTimeData]]);
           }
           
           if($scope.currentTab == 'seatUtil'){
               $scope.reportSUExcel = [];
               if($scope.searchSU.SUShift.text == 'All Shifts'){
                   angular.forEach($scope.seatUtilData, function(item){
                        $scope.reportSUExcel.push({'Trip Type':$scope.SUresultTripType,
                                                   'Date':item.tripDates,
                                                   'Number of Vehicle Used':item.totalUsedVehicles,
                                                   'Total Oppertunity': item.totalVehicleCapacity,
                                                   'PAX':item.totalEmployeesPickedDropCount,
                                                   'SU%':item.utilizedSeatPercentage});
                   });
               }
               else{
                   angular.forEach($scope.seatUtilData, function(item){
                        $scope.reportSUExcel.push({'Trip Type':$scope.SUresultTripType,
                                                   'Shift Time':item.tripDates,
                                                   'Number of Vehicle Used':item.totalUsedVehicles,
                                                   'Total Oppertunity': item.totalVehicleCapacity,
                                                   'PAX':item.totalEmployeesPickedDropCount,
                                                   'SU%':item.utilizedSeatPercentage});
                   });
               }
               var sheetLabel = $scope.SUresultTripType+" " + $scope.SUReportTitle;
               var opts = [{sheetid:sheetLabel,header:true}];
               alasql('SELECT INTO XLSX("SeatUtilization.xlsx",?) FROM ?',[opts,[$scope.reportSUExcel]]);
               
           }
           if($scope.currentTab == 'noShow'){ 
               $scope.reportNSExcel = [];
               if($scope.searchNSByEmployeeId){
                   angular.forEach($scope.noShowReportData, function(item){
                   $scope.reportNSExcel.push({'Trip Type':$scope.NSResultTripType,
                                               'Date':item.tripDates, /*01/08/2016 Change with Dates*/
                                               'Shift Time': item.tripDates, 
                                               'Employee Id':item.totalUsedVehicles,
                                               'Employee Name':item.totalEmployeesPickedDropCount});
                    });
               }
               else{
                   if($scope.searchNS.NSShift.text == 'All Shifts'){
                       angular.forEach($scope.noShowReportData, function(item){
                           $scope.reportNSExcel.push({'Trip Type':$scope.NSResultTripType,
                                                       'Date':item.tripDates,
                                                       'Trips':item.totalUsedVehicles,
                                                       'PAX':item.totalEmployeesPickedDropCount,
                                                       'No Show':item.totalEmployeesNoShowCount});
                       });
                    }
                   else if($scope.searchNS.NSShift.text == 'By Shifts'){
                       angular.forEach($scope.noShowReportData, function(item){
                           $scope.reportNSExcel.push({'Trip Type':$scope.NSResultTripType,
                                                       'Shift Time':'7:00**',
                                                       'Employee Id':item.totalUsedVehicles,
                                                       'Employee Name':item.totalEmployeesPickedDropCount});
                       });

                   }
                   
                   else if(!($scope.searchNS.NSShift.text == 'By Shifts') && !($scope.searchNS.NSShift.text == 'All Shifts')){
                       angular.forEach($scope.noShowReportData, function(item){
                           $scope.reportNSExcel.push({'Trip Type':$scope.NSResultTripType,                                                     
                                                       'Employee Id':item.totalUsedVehicles,
                                                       'Employee Name':item.totalEmployeesPickedDropCount});
                       });

                   }   
                   
                   
               }
               var sheetLabel = $scope.NSResultTripType+" " + $scope.NSReportTitle;
               var opts = [{sheetid:sheetLabel,header:true}];
               alasql('SELECT INTO XLSX("noShow.xlsx",?) FROM ?',[opts,[$scope.reportNSExcel]]);
           }
           
           if($scope.currentTab == 'kmReport'){
               $scope.reportKMExcel = [];
               //alert($scope.searchKMType);
               if($scope.searchKMType == 'vendor'){
                   //alert($scope.reportSelectionKM);
                   if($scope.distanceShiftType == 'All Shifts' && $scope.reportSelectionKM == 'All Vendors'){
                        angular.forEach($scope.reportsSum, function(item){
                            $scope.reportKMExcel.push({'Date':item.date,
                                                       'Shift Time':item.shiftTime,
                                                       'Vendor Name':item.vendorName,
                                                       'Vehicle Number':item.vehicleNumber,
                                                       'Type of Vehicle':item.vehicleType,
                                                       'Total Opportunity':item.totalApportunity,
                                                       'Utilized KMs':item.travelledDistance});
                        });
                   }
                   else{
                        angular.forEach($scope.reportsSum, function(item){
                            $scope.reportKMExcel.push({'Date':item.date,
                                                       'Vendor Name':item.vendorName,
                                                       'Vehicle Number':item.vehicleNumber,
                                                       'Type of Vehicle':item.vehicleType,
                                                       'Total Opportunity':item.totalApportunity,
                                                       'Utilized KMs':item.travelledDistance});
                        });
                   }
               }
               else{
                    angular.forEach($scope.reportsSum, function(item){
                        $scope.reportKMExcel.push({'Date':item.date,
                                                   'Vendor Name':item.vendorName,
                                                   'Vehicle Number':item.vehicleNumber,
                                                   'Type of Vehicle':item.vehicleType,
                                                   'Total Opportunity':item.totalApportunity,
                                                   'Utilized KMs':item.travelledDistance});   
                    });
               }
               
               var sheetLabel = 'Distance Report';
               var opts = [{sheetid:sheetLabel,header:true}];
               alasql('SELECT INTO XLSX("distance_KM.xlsx",?) FROM ?',[opts,[$scope.reportKMExcel]]);   
           }
           
           if($scope.currentTab == 'SMSReport'){
               $scope.reportSMSExcel = [];
               angular.forEach($scope.reportsSMSData, function(item){
                   $scope.reportSMSExcel.push({
                                              'Travelled Date':item.travelledDate,
                                              'Employee Id':item.employeeId, 
                                              'Employee Mobile':item.employeeNumber,
                                              'Shift Time':item.shiftTime,
                                              'Route Name':item.routeName,
                                              'Trip Type':item.tripType,
                                              'Allocation Msg(Date)':item.allocationMsgDeliveryDate,
                                              'Est. 15mins Msg(Date)':item.eat15MinuteMsgDeliveryDate,
                                              'Cab Delay Msg(Date)':item.cabDelayMsgDeliveryDate,
                                              'Reached Msg(Date)':item.reachedMsgDeliveryDate});
               }); 
               var sheetLabel = 'SMS Report';
               var opts = [{sheetid:sheetLabel,header:true}];
               alasql('SELECT INTO XLSX("smsReport.xlsx",?) FROM ?',[opts,[$scope.reportSMSExcel]]);               
           }   
        };
        
//           $scope.saveInExcelKM =function(ExcelType){
//               $scope.reportKMSUMExcel = [];
//               $scope.reportKMDetailedExcel = [];
//               $scope.reportKMSUMExcel = [];
//               if(ExcelType == 'byVendor_Sum'){
////                   alert("HI");
//                   angular.forEach($scope.reportsSum, function(item){
//                       $scope.reportKMSUMExcel.push({'VENDOR NAME':item.vendorName, 
//                                                  'VENDOR Id':item.vendorId, 
//                                                  'PLANNED DISTANCE (KM)':item.totalPlannedDistance,
//                                                  'DISTANCE TRAVELLED (KM)':item.totalTravelledDistance});
//                   });
//                   if($scope.reportsKMData.length>0 && $scope.viewDetail){                       
//                       angular.forEach($scope.reportsKMData, function(item){
//                           $scope.reportKMDetailedExcel.push({'Vehicle Id':item.vehicleId, 
//                                                  'Vehicle Number':item.vehicleNum, 
//                                                  'Travelled Date':item.travelledDate,
//                                                  'Travelled Distance':item.travelledDistance,
//                                                  'Planned Distance':item.plannedDistance,
//                                                  'Shift Time':item.shiftTime,
//                                                  'Vendor Name':item.vendorName,
//                                                  'Trip Type':item.tripType});
//                       });
//                       var opts = [{sheetid:'ByVendor_Summary',header:true},{sheetid:'ByVendor_Detail',header:true}];
//                       alasql('SELECT INTO XLSX("KilometerReport_byVendor_Detail.xlsx",?) FROM ?',[opts,[$scope.reportKMSUMExcel,$scope.reportKMDetailedExcel]]);
//                   }
//                   else{                        
//                    alasql('SELECT * INTO XLSX("KilometerReport_byVendor_Summary.xlsx",{headers:true}) FROM ?',[$scope.reportKMSUMExcel]);
//                   }
//                }
//               if(ExcelType == 'byVehicle_Sum'){
//                   angular.forEach($scope.reportsSum, function(item){
//                       $scope.reportKMSUMExcel.push({'Vehicle Id':item.vehicleId, 
//                                                  'Vehicle Numbe':item.vehicleNum, 
//                                                  'Vendor Name':item.vendorName,
//                                                  'Distance Travelled (KM)':item.travelledDistance,
//                                                  'Travelled Date':item.travelledDate,
//                                                  'Planned Distance':item.plannedDistance,
//                                                  'Trip Type':item.tripType,
//                                                  'Shift Time':item.shiftTime});
//                   });
//                    alasql('SELECT * INTO XLSX("KilometerReport_byVehicler_Summary.xlsx",{headers:true}) FROM ?',[$scope.reportKMSUMExcel]);
//                   
//               }
//           };
           
//           $scope.saveInExcelOT =function(ExcelType){
//               $scope.reportOTExcel = [];
//               if(ExcelType == 'PICKUP'){
////                   alert("HI");
//                   angular.forEach($scope.onTimeData, function(item){
//                       $scope.reportOTExcel.push({'DATE':item.tripDates, 
//                           'VendorName':item.vendorName, 
//                           'Actual Users':item.totalAllocatedEmployeesCount, 
//                           'Total Fleet':item.totalUsedVehicles,                            
//                           'Pickup Pax':item.totalEmployeesPickedDropCount, 
//                           'Pickup Trips':item.totalTrips,                          
//                           'OTA Trips':item.onTimeTrips, 
//                           'OTA In %':item.delayTripsPercentage, 
//                           'Delay Trips':item.totalDelayTrips, 
//                           'Beyond Login Trips':item.totalDelayTripsBeyondLogin, 
//                           'No Show Trips':item.totalEmployeesNoShowCount});
//                        });
//                       var opts = [{sheetid:'OTA',header:true},{sheetid:'OTA',header:true}];
//                       alasql('SELECT INTO XLSX("OTA.xlsx",?) FROM ?',[opts,[$scope.reportOTExcel]]);
//                   }
////                   else{                        
////                    alasql('SELECT * INTO XLSX("KilometerReport_byVendor_Summary.xlsx",{headers:true}) FROM ?',[$scope.reportKMSUMExcel]);
////                   }
//                //}
//               else if(ExcelType == 'DROP'){
//                   angular.forEach($scope.onTimeData, function(item){
//                       $scope.reportOTExcel.push({'DATE':item.tripDates, 
//                           'VendorName':item.vendorName, 
//                           'Actual Users':item.totalAllocatedEmployeesCount, 
//                           'Total Fleet':item.totalUsedVehicles,                            
//                           'Drop Pax':item.totalEmployeesPickedDropCount, 
//                           'Drop Trips':item.totalTrips,                          
//                           'OTD Trips':item.onTimeTrips, 
//                           'OTD In %':item.delayTripsPercentage, 
//                           'Delay Trips':item.totalDelayTrips, 
//                           'Beyond Login Trips':item.totalDelayTripsBeyondLogin, 
//                           'No Show Trips':item.totalEmployeesNoShowCount});
//                        });
//                       var opts = [{sheetid:'OTD',header:true},{sheetid:'OTD',header:true}];
//                       alasql('SELECT INTO XLSX("OTD.xlsx",?) FROM ?',[opts,[$scope.reportOTExcel]]);
//               }
//               
//               else if(ExcelType == 'SEATUT'){
//                   angular.forEach($scope.seatUtilData, function(item){
//                       $scope.reportOTExcel.push({'DATE':item.tripDates, 
//                           'Total Fleet':item.totalUsedVehicles,                            
//                           'Total Fleet Capacity':item.totalVehicleCapacity, 
//                           'Total Employee':item.totalEmployeesPickedDropCount,                          
//                           'Seat Utilized In %':item.utilizedSeatPercentage});
//                        });
//                       var opts = [{sheetid:'OTD',header:true},{sheetid:'OTD',header:true}];
//                       alasql('SELECT INTO XLSX("OTD.xlsx",?) FROM ?',[opts,[$scope.reportOTExcel]]);
//               }
//               else if(ExcelType == 'NOSHOW'){
//                   angular.forEach($scope.noShowReportData, function(item){
//                       $scope.reportOTExcel.push({'DATE':item.tripDates, 
//                           'Employee Id':item.totalUsedVehicles,                            
//                           'Employee Name':item.totalEmployeesPickedDropCount,                          
//                           'No Show':item.totalEmployeesNoShowCount});
//                        });
//                       var opts = [{sheetid:'OTD',header:true},{sheetid:'OTD',header:true}];
//                       alasql('SELECT INTO XLSX("OTD.xlsx",?) FROM ?',[opts,[$scope.reportOTExcel]]);
//               }
//               
//               
//           };
       
       //FUNCTION : Get All Shifts
       $scope.getAllShiftTime = function(){ 
    	   $scope.tripTimes = [];
           $scope.tripTimeData = [];          
    	   var data = {
        		 efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{userId:profileId}}
              };	
           $http.post('services/trip/shiftime/',data).
                success(function(data, status, headers, config) {
                          $scope.tripTimeData = data.shift;  
                          angular.forEach($scope.tripTimeData, function(item){
                          //console.log(item.shiftTime);
                          $scope.tripTimes.push({'text':item.shiftTime, 'value':item.shiftTime});
                              
                      });
                           $scope.tripTimes.unshift({'text':'All Shifts', 'value':0});
                           $scope.tripTimes.unshift({'text':'By Shifts', 'value':1});
                   // alert(JSON.stringify($scope.tripTimes));
                    }).
                error(function(data, status, headers, config) {
                  // log error
                });
            return $scope.tripTimes;
       };
    
       //FUNCTION : Get Shift Time by Trip Type
       $scope.getShiftTime = function(tripType){
    	   $scope.tripTimes = [];
           $scope.tripTimeData = [];
//    	   var data = {
//        		 efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
//        		 eFmFmEmployeeRequestMaster:{efmFmUserMaster:{userId:profileId}}
//              };	
//           $http.post('services/trip/shiftime/',data).
//                success(function(data, status, headers, config) {
           
                var data = {
                       efmFmUserMaster:{eFmFmClientBranchPO:{branchId:branchId}},
                       eFmFmEmployeeRequestMaster:{efmFmUserMaster:{userId:profileId}},
                       tripType:tripType
                          }; 
                $http.post('services/trip/tripshiftime/',data).
                    success(function(data, status, headers, config) {
                          $scope.tripTimeData = data.shift;  
                          angular.forEach($scope.tripTimeData, function(item){
                          //console.log(item.shiftTime);
                          $scope.tripTimes.push({'text':item.shiftTime, 'value':item.shiftTime});
                              
                      });
                           $scope.tripTimes.unshift({'text':'All Shifts', 'value':0});
                           $scope.tripTimes.unshift({'text':'By Shifts', 'value':1});
                   // alert(JSON.stringify($scope.tripTimes));
                    }).
                error(function(data, status, headers, config) {
                  // log error
                });
            return $scope.tripTimes;
       };
       
       //FUNCTION : Get aLL aCTIVE vENDORS to Populate the drop Down
       $scope.getAllVendors = function(){
    	   $scope.allVendors = [];
          var data = {
                      branchId:branchId
                   };
          $http.post('services/contract/allActiveVendor/',data).
                success(function(data, status, headers, config) {
                      angular.forEach(data, function(item){
                          $scope.allVendors.push({'name':item.name, 'Id':item.vendorId});
                      });
//                              $scope.vendorVehicles_KMReport = data; 
                      $scope.allVendors.unshift({'name':'All Vendors', 'Id':0});
                }).
                error(function(data, status, headers, config) {
                      // log error
                });
           //alert($scope.allVendors);
           return $scope.allVendors;
       };
       
//        $scope.openPDF = function(){
//           var doc = new jsPDF();
//
//        // We'll make our own renderer to skip this editor
//        var specialElementHandlers = {
//            '#pdfKM': function(element, renderer){
//                return true;
//            }
//        };
//
//        // All units are in the set measurement for the document
//        // This can be changed to "pt" (points), "mm" (Default), "cm", "in"
//        doc.fromHTML($('#editor').get(0), 15, 15, {
//            'width': 170, 
//            'elementHandlers': specialElementHandlers
//        });
//            
//        };
 
          
    };    
    
    angular.module('efmfmApp').controller('reportsCtrl', reportsCtrl);
}());