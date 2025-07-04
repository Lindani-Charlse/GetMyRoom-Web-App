<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - GetMyRoom</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
    <nav class="navbar">
        <ul class="nav-menu">
            <li class="nav-item"><a href="index.html"><i class="fas fa-home"></i> Home</a></li>
            <li class="nav-item"><a href="about.html"><i class="fas fa-info-circle"></i> About</a></li>
            <li class="nav-item"><a href="contact.html"><i class="fas fa-phone-alt"></i> Contact</a></li>
            <li class="nav-item dropdown">
                <a href="#" class="dropdown-toggle"><i class="fas fa-ellipsis-h"></i> More</a>
                <ul class="dropdown-menu">
                    <li><a href="services.html"><i class="fas fa-concierge-bell"></i> Services</a></li>
                </ul>
            </li>
        </ul>
    </nav>

    <div class="container admin-container">
        <div class="sidebar">
            <h3>Admin Menu</h3>
            <ul>
                <li><a href="ViewRoomServlet.do"><i class="fas fa-eye"></i> View Rooms</a></li>
                <li><a href="ViewProfilesServlet.do"><i class="fas fa-eye"></i> View Profiles</a></li>
                <li><a href="ManageRoomsServlet.do"><i class="fas fa-door-open"></i> Manage Student Rooms</a></li>
                <li><a href="ManageProfilesServlet.do"><i class="fas fa-users"></i> Manage Student Profiles</a></li>
                <li><a href="summaryReport.do"><i class="fas fa-chart-bar"></i> Reports</a></li>
                <li><a href="#" onclick="confirmLogout()"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
            </ul>
        </div>
        <div class="content">
            <img src="logo.png" alt="Univ Logo" class="logo">
            <h1>Admin Dashboard</h1>
            <div class="live-time" id="liveTime"></div>
            <c:if test="${not empty message}">
                <p class="${messageType == 'success' ? 'success-message' : 'error-message'}">${message}</p>
            </c:if>
                
        </div>
    </div>

    <footer>
        <p>© 2025 GetMyRoom. All Rights Reserved.</p>
    </footer>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            function updateLiveTime() {
                const now = new Date();
                const options = {
                    weekday: 'long', year: 'numeric', month: 'long', day: 'numeric',
                    hour: '2-digit', minute: '2-digit', second: '2-digit'
                };
                document.getElementById('liveTime').textContent = now.toLocaleString('en-US', options);
            }
            updateLiveTime();
            setInterval(updateLiveTime, 1000);
        });

        function confirmLogout() {
            if (confirm('Are you sure you want to logout?')) {
                window.location.href = 'LogoutServlet.do';
            }
        }
    </script>
</body>
</html>