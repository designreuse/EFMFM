<div class = "calenderReportPopoverTemp container-fluid">
    <div class = "row marginRow mainDiv">
<!--
      <div class = "calenderDiv">
        <div class = "calenderContainer calendarInput floatLeft">
             <datepicker ng-model="fromDate" 
                         min-date="minDate" 
                         show-weeks="false"
                         class=""
                         datepicker-options = 'dateOptions'>
             </datepicker>
        </div>
        <div class = "calenderContainer floatLeft">
             <datepicker ng-model="toDate" 
                         min-date="minDate" 
                         show-weeks="false" 
                         class=""
                         datepicker-options = 'dateOptions'>
             </datepicker>
         </div>
      </div>
-->
      <div class = "dateRangeDiv">
        <ul class = "dateRange">
          <li ng-click = "updateDate('today')" class = "pointer todayReport reportDateList" ng-class = "{inActive: dates[0].isClicked}">Today</li>
          <li ng-click = "updateDate('yesturday')" class = "pointer yesturdayReport reportDateList" ng-class = "{inActive: dates[1].isClicked}">Yesterday</li>
          <li ng-click = "updateDate('last7')" class = "pointer last7Report reportDateList" ng-class = "{inActive: dates[2].isClicked}">Last 7 Days</li>
          <li ng-click = "updateDate('last30')" class = "pointer last30Report reportDateList" ng-class = "{inActive: dates[3].isClicked}">Last 30 Days</li>
          <li ng-click = "updateDate('thisMonth')" class = "pointer thisMonth reportDateList" ng-class = "{inActive: dates[4].isClicked}">This Month</li>
          <li ng-click = "updateDate('customDate')" class = "pointer customRange reportDateList" ng-class = "{customDivIsOpen: openCustomRange}">Custom Range</li>
          <li class = 'customDateDiv' ng-if = "openCustomRange">
            <form class = 'row' name = "calender">
                <div class = 'col-md-12 col-xs-12 customInput1'><span>From</span><br>
                    <div class = "input-group calendarInput calendarInput2">
                        <input type="text" 
                             ng-model="from" 
                             id = "from"
                             class="form-control" 
                             name = "fromDate"
                             placeholder=""
                             ng-change = "checkDateRangeValidity(from, to)"
                             datepicker-popup = '{{format}}'
                             is-open="datePicker.fromDate"
                             show-button-bar = false
                             datepicker-options = 'dateOptions'
                             required
                             ng-class = "{error: calender.fromDate.$invalid && !calender.fromDate.$pristine}">
                        <span class="input-group-btn"><button class="btn btn-default" ng-click="fromDateCal($event)">
                            <i class = "icon-calendar calInputIcon"></i></button></span>
                       </div>
<!--
                   <span>From</span><br>
                   <input type = 'text' 
                          class = 'form-control' 
                          ng-model = 'fromDate' 
                          datepicker-popup="shortDate">
-->
                </div>
                <div class = 'col-md-12 col-xs-12 customInput2'><span>To</span><br>
                    <div class = "input-group calendarInput calendarInput2">
                        <input type="text" 
                             ng-model="to" 
                             class="form-control"
                             id = "toDate"
                             name='toDate'
                             placeholder=""
                             datepicker-popup = '{{format}}'
                             min-date = "from"
                             is-open="datePicker.toDate" 
                             show-button-bar = false
                             datepicker-options = 'dateOptions'
                             required
                             ng-class = "{error: calender.toDate.$invalid && !calender.toDate.$pristine}">
                        <span class="input-group-btn"><button class="btn btn-default" ng-click="toDateCal($event)">
                            <i class = "icon-calendar calInputIcon"></i></button></span>
                       </div>
<!--
                    <input type = 'text' 
                           class = 'form-control' 
                           ng-model = 'toDate' 
                           datepicker-popup="shortDate">
-->
                </div>
                <input class = 'col-md-12 col-xs-12 btn btn-success btn-xs showCustomReportButton' 
                       type='button' 
                       value = 'Show Report'
                       ng-click = "getReport(from, to)"
                       ng-show = '!dateRangeError'
                       ng-disabled="calender.$invalid">
                <input class = 'col-md-12 col-xs-12 btn btn-warning btn-xs showCustomReportButton' 
                       type='button' 
                       value = 'In-valid Date Range'
                       ng-click = "getReport(from, to)"
                       ng-show = 'dateRangeError'>
             </div>
          </li>
         </ul>      
      </div>    
</div>

                                        
                                        