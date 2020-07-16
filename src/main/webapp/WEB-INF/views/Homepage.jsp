<!DOCTYPE HTML>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>

<head>
  <title>Home Page</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
  integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <link rel="shortcut icon" href="">
  <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>

<body>
  <style>
    td,
    tr,
    h2,
    h3,
    h4,
    h6 {
      text-align: center;
    }

    .list-group-item {
      user-select: none;
    }

    .list-group input[type="checkbox"] {
      display: none;
    }

    .list-group input[type="checkbox"]+.list-group-item {
      cursor: pointer;
    }

    .list-group input[type="checkbox"]+.list-group-item:before {
      content: "\2713";
      color: transparent;
      font-weight: bold;
      margin-right: 1em;
    }

    .list-group input[type="checkbox"]:checked+.list-group-item {
      color: #0275D8;
    }

    .list-group input[type="checkbox"]:checked+.list-group-item:before {
      color: inherit;
    }

    #backbutton {
      margin-left: 2%;
    }
  </style>
  <div class="container">

    <br>
    <h2>Welcome Back</h2>
    <h4>${HP.getPrefix()} ${HP.getGivenName()} ${HP.getFamilyName()} !</h4>
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
    <h3>Your Patients</h3>
    <br>
    <form id="patientForm" modelAttribute="Page" action="monitor-patients" method="post">
    <div class="row justify-content-end">
      <div class="col">
          <label for="inputType">Monitor:  </label>
          <div class="form-check">
            <c:if test="${Page.getSelectedMonitor().getCholesterolMonitor() == true}">
              <input name="selectedView"type="checkbox" value="0" id="defaultCheck1" checked>
              <label class="form-check-label" for="defaultCheck1">
                Cholesterol
              </label>
            </c:if>
            <c:if test="${Page.getSelectedMonitor().getCholesterolMonitor() == false}">
              <input name="selectedView"type="checkbox" value="0" id="defaultCheck1">
              <label class="form-check-label" for="defaultCheck1">
                Cholesterol
              </label>
            </c:if>

          </div>
          <div class="form-check">
            <c:if test="${Page.getSelectedMonitor().getBloodPressureMonitor() == true}">
              <input name="selectedView"type="checkbox" value="1" id="defaultCheck2" checked>
              <label class="form-check-label" for="defaultCheck2">
                Blood Pressure 
              </label>
            </c:if>
            <c:if test="${Page.getSelectedMonitor().getBloodPressureMonitor() == false}">
              <input name="selectedView"type="checkbox" value="1" id="defaultCheck2">
              <label class="form-check-label" for="defaultCheck2">
                Blood Pressure 
              </label>
            </c:if>
          </div>
      </div>
    </div>
    <br>
    <h6>Please select the patients that you would like to monitor</h6>
    <br>
      <div class="container">
        <h7 style="font-style: italic">* Patients' name which is colored in grey does not have any cholesterol level AND blood pressure reading</h7>
        <br>
        <div class="list-group" id="checkbox">
          <c:forEach items="${HP.getPatientList()}" var="patient" varStatus="loop">
                <c:if test="${not empty patient.getCholesterolLevel() && selected[loop.index] ==1 || not empty patient.getSystolicBP() && selected[loop.index] ==1 || not empty patient.getDiastolicBP() && selected[loop.index] ==1}">
                  <input type="checkbox" name="inputPatient" id="${loop.index}" value="${loop.index}" checked />
                  <label class="list-group-item" for="${loop.index}">${patient.getGivenName()}
                    ${patient.getFamilyName()}</label>
                </c:if>
                <c:if test="${not empty patient.getCholesterolLevel() && selected[loop.index] == 0 || not empty patient.getSystolicBP() && selected[loop.index] == 0 || not empty patient.getDiastolicBP() && selected[loop.index] == 0}">
                  <input type="checkbox" name="inputPatient" id="${loop.index}" value="${loop.index}" />
                  <label class="list-group-item" for="${loop.index}">${patient.getGivenName()}
                    ${patient.getFamilyName()}</label>
                </c:if>
                <c:if test="${empty patient.getCholesterolLevel() && empty patient.getSystolicBP() && empty patient.getDiastolicBP()}">
                  <input type="checkbox" name="inputPatient" id="${loop.index}" value="${loop.index}" disabled />
                  <label class="list-group-item" for="${loop.index}"
                    style="color: #D3D3D3; font-style: italic">${patient.getGivenName()} ${patient.getFamilyName()}</label>
                </c:if>
          </c:forEach>
        </div>
        <div class="row">
          <div class="col-sm-12">
            <div class="text-right">
              <button type="submit" class="btn btn-outline-primary" onClick="return onCheck()" id="btnSubmit">Monitor
                Patient(s)</button>
            </div>
          </div>
        </div>
      </div>
  </div>
  <br>
  </form>


  <script>
    previous = "${Page.selectedMonitor}"
    /*
    This function is used to ensure that at least one checkbox has been checked before proceeding to the new page
    */
    function onCheck() {
      selectedPatient = 0;
      selectedView = 0
      checkSelectedPatient = false;
      checkSelectedView = false;
      $('[name=inputPatient]').each(function () {
        if ($(this).is(":checked")) {
          selectedPatient += 1;
        }
      });
      $('[name=selectedView]').each(function () {
        if ($(this).is(":checked")) {
          selectedView += 1;
        }
      });
      if (selectedPatient > 0 && selectedView > 0) {
        return true;
      }
      return false;

    }


    function changeview(select){
      var button = document.getElementsByName('myButton');
      if (previous != select){
        button.value = select
        previous = select
      }
    }

  </script>
</body>


</html>