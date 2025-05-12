<%-- 
    Document   : signout_outcome
    Created on : 09 Apr 2025, 1:26:03 AM
    Author     : linda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <%
            String studentNumber = (String)request.getAttribute("studentNumber");
        %>
        
        <p>Hello, your student number is : <%=studentNumber%></p>
    </body>
</html>
