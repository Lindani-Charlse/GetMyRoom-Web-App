<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Result</title>
    <style>
        .message { padding: 20px; border-radius: 5px; }
        .success { background: #d4edda; color: #155724; }
        .error { background: #f8d7da; color: #721c24; }
    </style>
</head>
<body>
    
    <div class="message <%= request.getSession().getAttribute("messageType") %>">
        <%= request.getSession().getAttribute("message") %>
    </div>
    <a href="studentPanel.jsp">Back to Profile</a>
</body>
</html>