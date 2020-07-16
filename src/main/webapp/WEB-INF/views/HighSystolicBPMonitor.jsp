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
    <h2>High Systolic Blood Pressure Monitor</h2>
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
    <div class="col-10">
      <form class="form-inline" id="patientView">
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
    <br>
    <br>
    <table class = "row justify-content-center">
        <tbody>
        <c:forEach items="${Page.getSelectedHighBPPatients()}" var="pat" varStatus="loop">
            <c:if test="${pat.getSystolicBP()[0] >= Page.getHighSystolicBP()}">
            <tr>
                <c:set var = "values" value="" />
                <td style="text-align: left">${pat.getGivenName()} ${pat.getFamilyName()}: </td>
                
                <c:forEach items="${pat.getSystolicBP()}" var="bpValue" varStatus="count">
                <fmt:formatDate pattern="yyyy-MM-dd hh:mm" var = "dateVal" value="${pat.getBPLastUpdated()[count.index]}"/>
                <c:set var = "values"  value= "${value} ${count.first ? '' : ','} ${bpValue} (${dateVal})"/>
                <td>${values}</td>
                </c:forEach>
                
            </tr>
            </c:if>
        </c:forEach>
        </tbody>        
    </table>
    <br>
    <br>
    <br>
    <c:forEach items="${Page.getSelectedHighBPPatients()}" var="patient" varStatus="loop">
      <div id="${patient.givenName}${patient.familyName}" style="margin-bottom:30px; margin-left: 10px; width: 45%; height: 300px;display: inline-block;"></div>
    </c:forEach>
    <br>
    <br>
    <br>

    <form id="patientView" action="/monitor-patients" method="get">
    <div class="row">
        <div class="col-sm-12">
        <div class="text-left">
            <button type="submit" class="btn btn-outline-primary float-right">Add or Remove Patient(s)</button>
        </div>
        </div>
    </div>
    </form>
    <br>
    <br>
    <br>
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
          url: "update-blood-pressure",
          success: function (data) {
            if (data == true) {
              location.reload()
            }
          },
          error: function (data) {
            alert("Error: Something Went Wrong While Displaying This Webpage. Please login again");
          }
        });
      }

      window.onload = function () {
        var selectedPatient = JSON.parse('${selectedPatient}');
        for (i=0; i< selectedPatient.length;i++){
          currentPatient = selectedPatient[i]
          createChart(currentPatient,i);
        }
      }

    function createChart(currentPatient,i){
      var dps = [];
      var chart = new CanvasJS.Chart(currentPatient.givenName+currentPatient.familyName, {
            theme: "light2", //"light1", "dark1", "dark2"
            title:{
                text: currentPatient.givenName+currentPatient.familyName           
            },
            data: [              
            {
                type: "line",
                dataPoints: dps
            }],            
        });
        var xValue;
        var yValue;
        for (i=0;i<currentPatient.systolicBP.length;i++){
          current = currentPatient.systolicBP[i]
            xValue = i+1
            yValue = current;
            dps.push({
                x:xValue,
                y:yValue,
            });
           
        }
        chart.render();
    }


    </script>
</body>

</html>