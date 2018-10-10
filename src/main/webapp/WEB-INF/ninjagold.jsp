<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
     <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<style>
        .golddisplay{
            width: 100px;
            height: 30px;
            border-style: solid;
            display: inline-block;
            padding-left: 10px;
        }
        .box{
            width: 180px;
            height: 200px;
            border-style: solid;
            display: inline-block;
            margin: 40px;
            text-align: center;
            vertical-align: top;
        }
        .red{
            color: red;
        }
        .green{
            color: green;
        }
        ul{
            list-style-type: none;
        }
        .log{
            width: 800px;
            height: 100px;
            border-style: solid;
            overflow: auto;
        }
        .row{
        	margin:20px;
        }
    </style>
<title>Ninja Gold</title>
</head>
<body>
	<div class="container-fluid" style="margin:20px;">
		<h5>Your Gold:     <span class="golddisplay">${ gold }</span></h5>
	</div>
	<div class="row">
		<div class="col s2 box">
            <h3>Farm</h3>
            <p>(earns 10-20 golds)</p>
            <a href="process_money_farm" class="waves-effect waves-light btn-small"><i class="material-icons left">local_florist</i>Find gold!</a>
    	</div>
    	
    	<div class="col s2 box">
            <h3>Cave</h3>
            <p>(earns 5-10 golds)</p>
            <a href="/process_money_cave" class="waves-effect waves-light btn-small"><i class="material-icons left">whatshot</i>Find gold!</a>
    	</div>
    	
    	<div class="col s2 box">
            <h3>House</h3>
            <p>(earns 2-5 golds)</p>
            <a href="/process_money_house" class="waves-effect waves-light btn-small"><i class="material-icons left">store</i>Find gold!</a>
    	</div>
    	
    	<div class="col s2 box">
            <h3>Casino</h3>
            <p>(earns/takes 0-50 golds)</p>
            <a href="process_money_casino" class="waves-effect waves-light btn-small"><i class="material-icons left">casino</i>Find gold!</a>
    	</div>
	</div>

	<div class="row">
		<h5>Activities:</h5>
		<div class="log">
        	<p> ${ log }</p>
    	</div>
    	<br>
    	<br>
    	<a class="waves-effect waves-light btn" href="/resetgold"><i class="material-icons left">cached</i>Reset</a>
	</div>
</body>
</html>