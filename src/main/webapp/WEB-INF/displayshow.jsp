<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Show details</title>
</head>
<body>
	<h1><c:out value="${show.title}"/></h1>
	<p>Network: <c:out value="${show.network}"/></p>
	<h3>Users who rated this show:</h3>
	<table>
		<tr>
			<th>Name</th>
			<th>Rating</th>
		</tr>
		<c:forEach items="${users}" var="user" varStatus="theCount">
			<tr>
				<td>${user.name}</td>
				<td>${ratings[theCount.index].value}</td>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${currentUser.name.equals(show.creator)}">
		<a href="/shows/${show.id}/edit">Edit</a>
	</c:if>
	<br>
	
	<form:form action="/shows/${show.id}/rate" method="POST" modelAttribute="rating">
	<p><c:out value="${error}" /></p>
		<p>
        	<form:label path="value">Leave a Rating: </form:label>
        	<form:errors path="value"/>
        	<form:input path="value"/>
    	</p>
    	<form:input path="userid" type="hidden" value="${currentUser.id}"/>
		<button type="submit">Rate!</button>
	</form:form>
</body>
</html>