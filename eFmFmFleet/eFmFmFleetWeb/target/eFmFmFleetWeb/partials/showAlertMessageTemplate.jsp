<div class = "row alert_TravelDesk">
    <div class="col-md-12 alert alert-warning customAlert" role="alert">
        <div class = 'row'> 
            <div class = 'col-md-12 alertHeader'>
                <span class = "floatRight pointer" ng-click = "closeAlertMessage()">x</span>
            </div>
            <div class ='col-md-12 alertBody'>
                <span class = 'floatLeft'>{{alertMessage}}</span><br>
                <span class = "hint floatLeft">{{alertHint}}</span>
            </div>
            <div class = 'col-md-12 alertFooter'>
                <input type = 'button' class = 'btn btn-success alertButtonsOK' value = 'OK' ng-click = 'closeAlertMessage()'>
            </div>
        </div>
    </div>
</div>   