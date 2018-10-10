<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Show</title>
</head>
<body>
	<h1>Create a new show</h1>
	<form:form action="/shows/create" method="post" modelAttribute="show">
		<p><c:out value="${error}" /></p>
		<p>
        	<form:label path="title">Title: </form:label>
        	<form:errors path="title"/>
        	<form:input path="title"/>
    	</p>
    	<form:input path="creator" type="hidden" value="${creator.name}"/>
		<p>
        	<form:label path="network">Network: </form:label>
        	<form:errors path="network"/>
        	<form:input path="network"/>
    	</p>
		<button type="submit">Create</button>
	</form:form>
</body>
</html>