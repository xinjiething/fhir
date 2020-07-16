<!DOCTYPE HTML>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<html>

<head>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
    integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <title>Patient's Personal Detail</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>

<body>
  <style>
    th,
    tr,
    h2,
    h3 {
      text-align: center;
    }

    button {
      margin-left: 2%;
    }
  </style>
  <div class="container">

    <br>
    <h2>Patient's Detail</h2>
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
    <br>
    <table class="table">
      <tbody>
        <tr>
          <th>Family Name</th>
          <td>${Patient.getFamilyName()}</td>
        </tr>
        <tr>
          <th>Given Name</th>
          <td>${Patient.getGivenName()}</td>
        </tr>
        <tr>
        <th>Birthdate</th>
        <fmt:formatDate type="date" var = "dob" pattern="dd-MM-yyyy" value="${Patient.getBirthDate()}"/>
        <td> ${dob}</td>
        </tr>
        <tr>
        <th>Gender</th>
        <td>${Patient.getGender()}</td>
        </tr>
        <tr>
        <th>Address</th>
        <td>${Patient.getAddrLine()}</td>
        </tr>
        <tr>
        <th>City</th>
        <td>${Patient.getCity()}</td>
        </tr>
        <tr>
        <th>State</th>
        <td>${Patient.getState()}</td>
        </tr>
        <tr>
        <th>Country</th>
        <td>${Patient.getCountry()}</td>
        </tr>
        <tr>
        <th>Cholesterol Level</th>
        <c:if test="${not empty Patient.getCholesterolLevel()}">
          <td>${Patient.getCholesterolLevel()} ${Patient.getCholesterolUnit()}</td>
          </tr>
          <th>Last Upadated (Cholesterol)</th>
          <td>${Patient.getCholLastUpdated()}</td>
          </tr>
        </c:if>
        <c:if test="${empty Patient.getCholesterolLevel()}">
          <td>-</td>
          </tr>
          <th>Last Upadated (Cholesterol)</th>
          <td>-</td>
          </tr>
        </c:if>
        <tr>
        <th>Systolic Blood Pressure</th>
        <c:if test="${not empty Patient.getSystolicBP()[0]}">
          <td>${Patient.getSystolicBP()[0]} ${Patient.getSystolicBPUnit()}</td>
          </tr>
          <tr>
          <th>Diastolic Blood Pressure</th>
          <td>${Patient.getDiastolicBP()} ${Patient.getDiastolicBPUnit()}</td>
          </tr>
          <tr>
          <th>Last Upadated (Blood Pressure)</th>
          <td>${Patient.getBPLastUpdated()[0]}</td>
          </tr>
        </c:if>
        <c:if test="${empty Patient.getSystolicBP()[0]}">
          <td>-</td>
          </tr>
          <tr>
          <th>Diastolic Blood Pressure</th>
          <td>-</td>
          </tr>
          <tr>
          <th>Last Upadated (Blood Pressure)</th>
          <td>-</td>
          </tr>
        </c:if>

      </tbody>
    </table>
    <br>
    <form id="patientView" action="/monitor-patients" method="get">
      <div class="row">
        <div class="col-sm-12">
          <div class="text-left">
            <button type="submit" class="btn btn-outline-primary float-right">View Another Patient</button>
          </div>
        </div>
      </div>
    </form>
  </div>
  <br><br><br>
</body>


</html>