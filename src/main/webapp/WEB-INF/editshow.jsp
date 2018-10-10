<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Show</title>
</head>
<body>
	<h1>${show.title}</h1>
	<form:form action="/shows/${show.id}/updateEdit" method="post" modelAttribute="show">
		<input type="hidden" name="_method" value="put">
		<p><c:out value="${error}" /></p>
		<p><c:out value="${titleError}" /></p>
		<p>
        	<form:label path="title">Title: </form:label>
        	<form:errors path="title"/>
        	<form:input path="title"/>
    	</p>
    	<p><c:out value="${networkError}" /></p>
		<p>
        	<form:label path="network">Network: </form:label>
        	<form:errors path="network"/>
        	<form:input path="network"/>
    	</p>
		<button type="submit">Update</button>
	</form:form>
	<a href="/shows/${show.id}/delete">Delete</a>
</body>
</html>