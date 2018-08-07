<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sample LTI Application with Spring 4</title>
</head>
<body>
    <H1>Welcome to the sample project LTI Application!</H1>
    
    <p>
    Check configuration at some files:
        <ul>
            <li>/thachln-lti-spring/src/main/java/mks/ownbank/lti/config/LtiProviderConfig.java</li>
            <li>/thachln-lti-spring/src/main/webapp/spring/applicationContext.xml</li>
        </ul>
    </p>
    <p>
    Please connect to this LTI Application following information:
    </p>
    <ul>
        <li>URL: http://localhost:<port>/thachln-lti-spring/vote (Dependence you port of Tomcat in Eclipse, you can change <port> into your specified port)</li>
    <li>Key: key</li>
   <li>Secret: secret</li>
   <li>Check on items of Releasing Roster Information: <br/>
        <input type="checkbox" checked="checked"/>Send Names to the External Tool<br/>
        <input type="checkbox" checked="checked"/>Send Email Addresses to the External Tool<br/>
    </li>
   </ul>
       
    
</body>
</html>