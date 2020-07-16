<!DOCTYPE HTML>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<html>
<head>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
    integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <title>Patient Table</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src='https://kit.fontawesome.com/a076d05399.js'></script>
  <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</head>

<body>
  <style>
    th,
    td,
    tr,
    h2 {
      text-align: center;
    }
  </style>
  <div class="container">
    <br>
    <h2>Patients' Latest Cholesterol Level and Blood Pressure</h2>
    <br>
    <form id="back" action="/login" method="get">
      <div class="row">
        <div class="col-sm-12">
          <div class="text-left">
            <button id="backbutton" type="submit" class="btn btn-outline-primary"><i class='fas fa-sign-out-alt'></i>
              Log Out</button>
          </div>
        </div>
      </div>
    </form>
    <br>
    <br>
    <div class="row">
      <div class="col-md-6">
        <form class="form-inline" id="patientView" action="monitor-patients" method="get">
          <div class="form-group col-10" style="margin-top: 30px;">
              <label for="inputType">Update Interval: </label>
              <input type="text" class="form-control" id="inputTime" name="viewRefreshTime" placeholder=""
              value="${Page.getviewRefreshTime()}" aria-describedby="basic-addon2">
              <span class="form-group-text">seconds</span>
          </div>
          <div class="form-group col-2" style="margin-top: 30px;">
              <button class="btn btn-primary" type="submit">OK</button>
          </div>
        </form>
      </div>
      <div class="col-md-6">
        <form class="row" action="/bloodpressure" method="get">
          <div class="form-group col-5 margin-top:30px">
            <label for="inputSystolicBP">High Systolic Blood Pressure</label>
            <input type="text" class="form-control" id="inputSystolicBP" name="inputSystolicBP" value="${Page.getHighSystolicBP()}">
            <label class="form-group-text">mmHg</label>

          </div>
          <div class="form-group col-5">
            <label for="inputDiastolicBP">High Diastolic Blood Pressure</label>
            <input type="text" class="form-control" id="inputDiastolicBP" name="inputDiastolicBP" value="${Page.getHighDiastolicBP()}">
            <label class="form-group-text">mmHg</label>

          </div>
          <div class="form-group col-2" style="margin-top: 30px;">
            <button type="submit" class="btn btn-primary">OK</button>
          </div>
        </form>
      </div>
    </div>
    <br>
    <br>
    <div id="chartContainer" style="height: 100%; width: 80%;margin-left: 10%;">
    </div>
    <h6 style="margin-top: 500px";></h6>
    <form id="patientDetails" action="high-systolic-bp-monitor" method="post">
      <table class="table" id="currenttable">
        <colgroup>
          <col span="1" style="width: 20%;">
          <col span="1" style="width: 10%;">
          <col span="1" style="width: 15%;">
          <col span="1" style="width: 15%;">
          <col span="1" style="width: 15%;">
          <col span="1" style="width: 15%;">
          <col span="1" style="width: 5%;">
          <col span="1" style="width: 5%;">
        </colgroup>
        <thead>
          <tr valign="top" text-align="center">
            <th scope="col">NAME</th>
            <th scope="col">TOTAL CHOLESTEROL</th>
            <th scope="col">TIME</th>
            <th scope="col">SYSTOLIC BLOOD PRESSURE</th>
            <th scope="col">DIASTOLIC BLOOD PRESSURE</th>
            <th scope="col">TIME</th>
            <th scope="col">DETAILS</th>
            <th scope="col">SELECT</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${Page.getSelectedPatientList()}" var="patient" varStatus="loop">
            <tr>            
              <td>
                <c:out value="${patient.getGivenName()} ${patient.getFamilyName()}" />
              </td>
              <c:if test="${empty patient.getCholesterolLevel()}">
                <td>-</td>
                <td>-</td>
              </c:if>
              <c:if test="${not empty patient.getCholesterolLevel() && patient.getCholesterolLevel() > Page.getAverageCholesterol()}">
                <td style="color: red">${patient.getCholesterolLevel()} ${patient.getCholesterolUnit()}</td>
                <fmt:formatDate pattern="yyyy-MM-dd hh:mm" var = "dateVal" value="${patient.getCholLastUpdated()}"/>
                <td>${dateVal}</td>
              </c:if>
              <c:if test="${not empty patient.getCholesterolLevel() && patient.getCholesterolLevel() <= Page.getAverageCholesterol()}">
                <td>${patient.getCholesterolLevel()} ${patient.getCholesterolUnit()}</td>
                <fmt:formatDate pattern="yyyy-MM-dd hh:mm" var = "dateVal" value="${patient.getCholLastUpdated()}"/>
                <td>${dateVal}</td>

              </c:if>
              
              <c:if test="${empty patient.getSystolicBP() && empty patient.getDiastolicBP()}">
                <td>-</td>
                <td>-</td>
                <td>-</td>
              </c:if>
              <c:if test="${not empty patient.getSystolicBP() && patient.getSystolicBP()[0] < Page.getHighSystolicBP() && not empty patient.getDiastolicBP() && patient.getDiastolicBP() < Page.getHighDiastolicBP()}">
                <td><c:out value="${patient.getSystolicBP()[0]} ${patient.getSystolicBPUnit()}" /></td>
                <td>${patient.getDiastolicBP()} ${patient.getDiastolicBPUnit()}</td>
                <fmt:formatDate pattern="yyyy-MM-dd hh:mm" var = "dateVal" value="${patient.getBPLastUpdated()[0]}"/>
                <td>${dateVal}</td>
              </c:if>
              <c:if test="${not empty patient.getSystolicBP() && patient.getSystolicBP()[0] >= Page.getHighSystolicBP() && not empty patient.getDiastolicBP() && patient.getDiastolicBP() < Page.getHighDiastolicBP()}">
                <td style="color: blue"><c:out value="${patient.getSystolicBP()[0]} ${patient.getSystolicBPUnit()}" /></td>
                <td>${patient.getDiastolicBP()} ${patient.getDiastolicBPUnit()}</td>
                <fmt:formatDate pattern="yyyy-MM-dd hh:mm" var = "dateVal" value="${patient.getBPLastUpdated()[0]}"/>
                <td>${dateVal}</td>
              </c:if>
              
              <c:if test="${not empty patient.getSystolicBP() && patient.getSystolicBP()[0] < Page.getHighSystolicBP() && not empty patient.getDiastolicBP() && patient.getDiastolicBP() >= Page.getHighDiastolicBP()}">
                <td><c:out value="${patient.getSystolicBP()[0]} ${patient.getSystolicBPUnit()}" /></td>
                <td style="color: blue">${patient.getDiastolicBP()} ${patient.getDiastolicBPUnit()}</td>
                <fmt:formatDate pattern="yyyy-MM-dd hh:mm" var = "dateVal" value="${patient.getBPLastUpdated()[0]}"/>
                <td>${dateVal}</td>
              </c:if>
              <c:if test="${not empty patient.getSystolicBP() && patient.getSystolicBP()[0] >= Page.getHighSystolicBP() && not empty patient.getDiastolicBP() && patient.getDiastolicBP() >= Page.getHighDiastolicBP()}">
                <td style="color: blue"><c:out value="${patient.getSystolicBP()[0]} ${patient.getSystolicBPUnit()}" /></td>
                <td style="color: blue">${patient.getDiastolicBP()} ${patient.getDiastolicBPUnit()}</td>
                <fmt:formatDate pattern="yyyy-MM-dd hh:mm" var = "dateVal" value="${patient.getBPLastUpdated()[0]}"/>
                <td>${dateVal}</td>
              </c:if>
                <td>
                  <button type="submit" class="btn btn-outline-primary float-right" name= selectedButton id="${loop.index}" value="${loop.index}">View Details</button>
                </td>
              <td>
                  <c:if test="${patient.getSystolicBP()[0] >= Page.getHighSystolicBP() && chosenPatient[loop.index] == 0}">
                    <div class="custom-control custom-checkbox mr-sm-2" id=checkbox>
                      <input type="checkbox" class="custom-control-input" name="chooseHighBPPatient" id="checkbox${loop.index}"
                      value="${loop.index}">
                      <label class="custom-control-label" for="checkbox${loop.index}"></label>
                  </c:if>
                  <c:if test="${patient.getSystolicBP()[0] >= Page.getHighSystolicBP() && chosenPatient[loop.index] == 1}">
                    <div class="custom-control custom-checkbox mr-sm-2" id=checkbox>
                      <input type="checkbox" class="custom-control-input" name="chooseHighBPPatient" id="checkbox${loop.index}"
                      value="${loop.index}" checked>
                      <label class="custom-control-label" for="checkbox${loop.index}"></label>
                  </c:if>
                  <c:if test="${patient.getSystolicBP()[0] < Page.getHighSystolicBP()}">
                    <div class="custom-control custom-checkbox mr-sm-2" id=checkbox>
                      <input type="checkbox" class="custom-control-input" name="chooseHighBPPatient" id="checkbox${loop.index}"
                      value="${loop.index}" disabled>
                      <label class="custom-control-label" for="checkbox${loop.index}"></label>
                  </c:if>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <button type="submit" name= selectedButton onclick="return onCheck()" value = "-1" class="btn btn-outline-primary float-right">Monitor</button>
    </form>
    <div class="row">
      <form id="patientView" action="/Homepage" method="get">
        <div class="col">
          <div class="text-left">
            <button id="backbutton" type="submit" class="btn btn-outline-primary float-left">Add or Remove
              Patient(s)</button>
          </div>
        </div>
      </form>
      <form id="patientClear" action="clearSelected" method="post">
        <div class="col">
          <button id="button" type="submit" class="btn btn-outline-primary float-right">Stop Monitor</button>
        </div>
      </form>
    </div>

    <br/>
    <br/>
    <br/>
    <script>
     
      $(document).ready(function(){

        var inputVal = document.getElementById("inputTime").value;
  
        setInterval(checkUpdate, inputVal * 1000);
        /* 
        This function is used to ensure the controller will update the latest cholesterol level of the patient in every N seconds
         */
        function checkUpdate() {
          var choles = $.ajax({
            type: "POST",
            async: false,
            url: "/update-cholesterol"
          });

          var bp = $.ajax({
            type: "POST",
            async: false,
            url: "/update-blood-pressure"
          });

          $.when(bp,choles).done(function(bpdata,cholesdata){
            if (bpdata[0] == true || cholesdata[0] == true){
              window.location.reload()
            }
          }).fail(function(bpdata,cholesdata){
            alert("Error: Something Went Wrong While Displaying This Webpage. Please login again");
          })

        }


      })


      function onCheck() {
        selected = 0;
        $('div#checkbox input[type=checkbox]').each(function () {
          if ($(this).is(":checked")) {
            selected += 1;
          }
        });
        if (selected > 0) {
          return true;
        }
        else {
          return false;
        }
      }


      window.onload = function () {
        var dps = [];
        var chart = new CanvasJS.Chart("chartContainer", {
            theme: "light2",
            title:{
                text: "Total Cholesterol mg/dL"              
            },
            data: [              
            {
                type: "column",
                dataPoints: dps
            }
            ]
        });
        var xValue;
        var yValue;
        var selectedPatient = JSON.parse('${selectedPatient}');
        for (i=0;i<selectedPatient.length;i++){
          patient = selectedPatient[i]
          if (patient.cholesterolLevel != null){
            xValue = patient.givenName+patient.familyName;
            yValue = patient.cholesterolLevel;
            dps.push({
                label:xValue,
                y:yValue,
                color:"#6495ED"
            })

          }
           
        }
        chart.render();
    }


    </script>
</body>

</html>