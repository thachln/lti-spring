<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="r" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Vote</title>
</head>
<body>
    <div>
        <form:form action="vote-actions" modelAttribute="litLaunchData" method="POST">
            <button type="submit" class="btn btn-default" name="clickedBtn" value="vote">
                <i class="fa fa-search"></i>Vote
            </button>
            <button type="submit" class="btn btn-default" name="clickedBtn" value="voteHistory">
                <i class="fa fa-search"></i>History
            </button>
            <button type="submit" class="btn btn-default" name="clickedBtn" value="voteReport">
                <i class="fa fa-search"></i>Report
            </button>
        </form:form>
    </div>
</body>
</html>