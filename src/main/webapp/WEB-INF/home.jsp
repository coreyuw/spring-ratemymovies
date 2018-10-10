<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
</head>
<body>
	<h1>Welcome, <c:out value="${user.name}" /></h1>
	<a href="/logout">Logout</a>
	<br>
	<a href="/highToLow">Rating: High-Low</a>
	<br>
	<a href="/lowToHigh">Rating: Low-High</a>
	<table>
		<tr>
			<th>Show</th>
			<th>Network</th>
			<th>Average Rating</th>
		</tr>
		<c:forEach items="${shows.content}" var="show">
			<tr>
				<td><a href="/shows/${show.id}">${show.title}</a></td>
				<td>
					<c:out value="${show.network}"/>
				</td>
				<td>
					<c:out value="${show.aveRating}"/>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<a href="/shows/new">Add a show</a>
</body>
</html>