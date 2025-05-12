<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Rooms - GetMyRoom</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
    <nav class="navbar">
        <ul class="nav-menu">
            <li class="nav-item"><a href="index.html"><i class="fas fa-home"></i> Home</a></li>
            <li class="nav-item"><a href="studentLogin.jsp"><i class="fas fa-user-graduate"></i> Student Login</a></li>
            <li class="nav-item"><a href="signup.html"><i class="fas fa-user-plus"></i> Create Profile</a></li>
            <li class="nav-item dropdown">
                <a href="#" class="dropdown-toggle"><i class="fas fa-ellipsis-h"></i> More</a>
                <ul class="dropdown-menu">
                    <li><a href="about.html"><i class="fas fa-info-circle"></i> About</a></li>
                    <li><a href="contact.html"><i class="fas fa-phone-alt"></i> Contact</a></li>
                    <li><a href="services.html"><i class="fas fa-concierge-bell"></i> Services</a></li>
                </ul>
            </li>
        </ul>
    </nav>

    <div class="container">
        <img src="logo.png" alt="University Logo" class="logo">
        <h1>View Rooms</h1>
        <c:if test="${not empty message}">
            <p class="${messageType == 'success' ? 'success-message' : 'error-message'}">${message}</p>
        </c:if>
        <table class="profile-info">
            <thead>
                <tr>
                    <th>Room No</th>
                    <th>Allocated To</th>
                    <th>Allocation Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="room" items="${roomList}">
                    <tr>
                        <td>${room.roomNo}</td>
                        <td>${room.profile != null ? room.profile.student.fullNames : 'Unallocated'}</td>
                        <td>${room.allocationDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="adminPanel.jsp" class="submit-button">Back to Admin Panel</a>
    </div>

    <footer>
        <p>© 2025 GetMyRoom. All Rights Reserved.</p>
    </footer>
</body>
</html>