<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<title>Index</title>
</head>
<body>
	<div class="header">
		<nav class="navbar navbar-light bg-light">
  			<span class="navbar-brand mb-0 h1" style="color:green;"><h1>Rate My Movies</h1></span>
		</nav>
		<nav class="navbar navbar-expand-lg navbar-light bg-light" style="margin-right: 10px;">
  		<h4>Navigation:</h4>

  		<div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto" style="text-align: center;">
      <li class="nav-item active">
        <a class="nav-link" href="#" style="padding-left:30px;"><h6>Home</h6><span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="https://www.imdb.com/" style="padding-left:30px;"><h6>IMDb</h6></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/ninjagold" style="padding-left:30px;"><h6>Ninja Gold(Materialize'd mini game)</h6></a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="padding-left:30px;">
          <h6>About Us</h6>
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="https://www.google.com/maps/place/Coding+Dojo+Seattle/@47.5957085,-122.1951214,17z/data=!3m1!4b1!4m5!3m4!1s0x41087781b127379f:0x44d6854d8a4c2a7c!8m2!3d47.5957085!4d-122.1929327">Location</a>
          <a class="dropdown-item" href="https://www.codingdojo.com/">Website</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Career</a>
        </div>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>
	</div>
	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="col-md-6 border rounded" style="margin-left: -100px;padding:20px;">
						<h3>Create a new user:</h3>
		<c:out value="${registerError}"/>
    
	<form:errors path="user.*" style="color:red;"/>
    
    <form:form method="POST" action="/registration" modelAttribute="user">
    	 <p>
            <form:label path="name">Name:</form:label>
            <form:input path="name"/>
        </p>
        <p>
            <form:label path="email">Email:</form:label>
            <form:input type="email" path="email"/>
        </p>
        <p>
            <form:label path="password">Password:</form:label>
            <form:password path="password"/>
        </p>
        <p>
            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
            <form:password path="passwordConfirmation"/>
        </p>
        <button type="submit" class="btn btn-primary">Register!</button>
    </form:form>
		</div>

		<div class="col-md-6 border rounded" style="margin-left:30px;padding:20px;">
			<h1>Login</h1>
    <form method="post" action="/login">
        <p>
            <label type="email" for="email">Email</label>
            <input type="text" id="email" name="loginEmail"/>
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
        </p>
        <button type="submit" class="btn btn-primary">Login!</button>
    </form> 
    <br>
    <c:if test="${ loginError!=null }"><span class="alert alert-warning" role="alert"><c:out value="${loginError}"/></span></c:if>
			</div>
		</div>
    </div>
</body>
</html>