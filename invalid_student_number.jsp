<%-- 
    Document   : invalid_student_number
    Created on : 01 Apr 2025, 00:06:22
    Author     : linda
--%>

<%@page isErrorPage="True" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invalid Student Number Page</title>
    </head>
    <body>
        <h1>Invalid student number</h1>
        <%
            String errMsg = exception.getMessage();
        %>
        <p>
            <b>Error message --> <%=errMsg%> </b>
        </p>
        <p>
            Please click <a href="signup.html">here</a> to retry.
        </p>
    </body>
</html>
