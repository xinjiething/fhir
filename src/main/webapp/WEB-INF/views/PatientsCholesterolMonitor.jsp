<!DOCTYPE HTML>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
    <h2>Patients' Latest Cholesterol Level</h2>
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
    <div class="row justify-content-end">
      <div class="col">
        <form class="form-inline" id="patientView" action="monitor-patients" method="get">
          <label for="inputType">Update Interval: </label>
          <input type="text" class="form-control" id="inputTime" name="viewRefreshTime" placeholder=""
          value="${Page.getviewRefreshTime()}" aria-describedby="basic-addon2">
          <div class="form-group">
            <span class="form-group-text">seconds</span>
          </div>
          <div class="col">
            <button class="btn btn-primary" type="submit">OK</button>
          </div>
        </form>
      </div>
    </div>
    <br>
    <br>
    <br>
    </div>
    <br>
    <br>
    <div id="chartContainer" style="height: 100%; width: 80%;margin-left: 10%;"></div>
    <h6 style="margin-top: 500px;";></h6>
    <form id="patientDetails" action="patient-details" method="post">
      <table class="table" id="currenttable" style="margin-left: 10%;margin-right: 10%;width:80%;">
        <colgroup>
          <col span="1" style="width: 25%;">
          <col span="1" style="width: 25%;">
          <col span="1" style="width: 40%;">
          <col span="1" style="width: 5%;">
        </colgroup>
        <thead>
          <tr valign="top" text-align="center">
            <th scope="col">NAME</th>
            <th scope="col">TOTAL CHOLESTEROL</th>
            <th scope="col">TIME</th>
            <th scope="col">Select</th>
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
                  <td>${patient.getCholLastUpdated()}</td>
                </c:if>
                <c:if test="${not empty patient.getCholesterolLevel() && patient.getCholesterolLevel() <= Page.getAverageCholesterol()}">
                  <td>${patient.getCholesterolLevel()} ${patient.getCholesterolUnit()}</td>
                  <td>${patient.getCholLastUpdated()}</td>
                </c:if>      
                <td>
                  <button type="submit" class="btn btn-outline-primary float-right" name= selectedButton id="${loop.index}" value="${loop.index}">View Details</button>
                </td>
              </tr>
          </c:forEach>
        </tbody>
      </table>
      <button type="submit" onClick="return onCheck()" class="btn btn-outline-primary float-right" style="margin-right: 10%;">View Patient</button>
    </form>
    <div class="row" style="margin-left: 10%;">
      <form id="patientView" action="/Homepage" method="get">
        <div class="col">
            <button id="backbutton" type="submit" class="btn btn-outline-primary float-left">Add or Remove
              Patient(s)</button>
        </div>
      </form>
      <form id="patientClear" action="clearSelected" method="post">
        <div class="col">
          <button id="button" type="submit" class="btn btn-outline-primary float-right">Stop Monitor</button>
        </div>
      </form>
    </div>
    <br><br><br>

    <script>

      var inputVal = document.getElementById("inputTime").value;

      setInterval(checkUpdate, inputVal * 1000);
      /* 
      This function is used to ensure the controller will update the latest cholesterol level of the patient in every N seconds
       */
      function checkUpdate() {
        $.ajax({
          type: "post",
          async: false,
          url: "update-cholesterol",
          success: function (data) {
            if (data == true) {
              window.location.reload();
            }
          },
          error: function (data) {
            alert("Error: Something Went Wrong While Displaying This Webpage. Please login again");
          }
        });
      }

      /* 
      This function is used to ensure there is a checkbox selected before moving to a new page
       */

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
            xValue = patient.givenName+patient.familyName;
            yValue = patient.cholesterolLevel;
            dps.push({
                label:xValue,
                y:yValue,
                color:"#6495ED"
            });
           
        }
        chart.render();
    }


    </script>
</body>

</html>