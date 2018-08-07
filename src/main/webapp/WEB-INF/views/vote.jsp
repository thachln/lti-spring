<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="r" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>LTI Application</title>
<%@include file="_css-js.jsp" %>

</head>
<body>
    <!-- Menu bar -->
    <%@include file="_menu-bar1.jsp" %>
    <!-- Main page -->
    <jsp:include page="_vote-nav.jsp"></jsp:include>
    <jsp:include page="vote-take.jsp"></jsp:include>
 </body>
 </html>
