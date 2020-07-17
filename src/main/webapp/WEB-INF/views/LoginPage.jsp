<!DOCTYPE HTML>

<html>

<head>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
    integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <title>Login page</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="">
  <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>

<body>
  <style>
    h1 {
      text-align: center;
    }
  </style>
  <h1>Welcome!</h1>
  <br>
  <br>
  <form id="login" action="login" method="post">
    <div class="input-group mb-3" style="width: 100%;text-align:center;padding-left:40%;">
      <div class="input-group-prepend text-align:center">
        <span class="input-group-text" id="basic-addon1"><i class='far fa-user'></i></span>
        <input type="text" id="userid" class="form-control" name="identifier" placeholder="Please Enter Your User ID" value="17600"
          aria-label="Username" aria-describedby="basic-addon1" minlength=1 required>
      </div>
    </div>
    <br>
    <br>
    <div class="row">
      <div class="col-sm-12">
        <div class="text-center">
          <button type="submit" class="btn btn-outline-primary">Login</button>
        </div>
      </div>
    </div>
  </form>

</body>

</html>