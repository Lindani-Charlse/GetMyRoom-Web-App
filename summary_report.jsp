<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Summary Report - GetMyRoom</title>
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
                <li><a href="ManageRoomsServlet.do"><i class="fas fa-door-open"></i> Manage Rooms</a></li>
                <li><a href="ManageProfilesServlet.do"><i class="fas fa-users"></i> Manage Profiles</a></li>
                <li><a href="SummaryReportServlet.do"><i class="fas fa-chart-bar"></i> Reports</a></li>
                <li><a href="#" onclick="confirmLogout()"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
            </ul>
        </div>
        <div class="content">
            <img src="logo.png" alt="University Logo" class="logo">
            <h1>Summary Report</h1>
            <div class="live-time" id="liveTime"></div>
            <c:if test="${not empty message}">
                <p class="${messageType == 'success' ? 'success-message' : 'error-message'}">${message}</p>
            </c:if>

            <h2>Room Occupancy Overview</h2>
            <p>Total Rooms: ${summary.totalRooms}</p>
            <p>Occupied Rooms: ${summary.occupiedRooms}</p>
            <p>Available Rooms: ${summary.availableRooms}</p>
            <p>Occupancy Rate: ${summary.occupancyRate}%</p>

            <a href="summaryReport.do?export=pdf" style="padding: 12px 24px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; font-weight: 600; display: inline-flex; align-items: center; gap: 8px; transition: background 0.3s ease, color 0.3s ease, transform 0.2s ease; justify-content: center; font-family: 'Poppins', sans-serif; text-decoration: none;" onmouseover="this.style.background='white'; this.style.color='orange'; this.style.border='1px solid orange'; this.style.transform='translateY(-2px)';" onmouseout="this.style.background='orange'; this.style.color='white'; this.style.border='none'; this.style.transform='none';"><i class="fas fa-download"></i> Export to PDF</a>

            <h3>Detailed Reports</h3>
            <ul style="list-style: none; padding: 0;">
                <li><a href="report1Servlet.do" style="padding: 12px 24px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; font-weight: 600; display: inline-flex; align-items: center; gap: 8px; transition: background 0.3s ease, color 0.3s ease, border 0.3s ease, transform 0.2s ease; justify-content: center; font-family: 'Poppins', sans-serif; text-decoration: none; margin: 5px 0; width: 300px;" onmouseover="this.style.background='white'; this.style.color='orange'; this.style.border='1px solid orange'; this.style.transform='translateY(-2px)';" onmouseout="this.style.background='orange'; this.style.color='white'; this.style.border='none'; this.style.transform='none';">Room Occupancy by Gender</a></li>
                <li><a href="report2Servlet.do" style="padding: 12px 24px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; font-weight: 600; display: inline-flex; align-items: center; gap: 8px; transition: background 0.3s ease, color 0.3s ease, border 0.3s ease, transform 0.2s ease; justify-content: center; font-family: 'Poppins', sans-serif; text-decoration: none; margin: 5px 0; width: 300px;" onmouseover="this.style.background='white'; this.style.color='orange'; this.style.border='1px solid orange'; this.style.transform='translateY(-2px)';" onmouseout="this.style.background='orange'; this.style.color='white'; this.style.border='none'; this.style.transform='none';">Room Occupancy by Course</a></li>
                <li><a href="report3Servlet.do" style="padding: 12px 24px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; font-weight: 600; display: inline-flex; align-items: center; gap: 8px; transition: background 0.3s ease, color 0.3s ease, border 0.3s ease, transform 0.2s ease; justify-content: center; font-family: 'Poppins', sans-serif; text-decoration: none; margin: 5px 0; width: 300px;" onmouseover="this.style.background='white'; this.style.color='orange'; this.style.border='1px solid orange'; this.style.transform='translateY(-2px)';" onmouseout="this.style.background='orange'; this.style.color='white'; this.style.border='none'; this.style.transform='none';">Room Occupancy by Funding Type</a></li>
                <li><a href="report4Servlet.do" style="padding: 12px 24px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; font-weight: 600; display: inline-flex; align-items: center; gap: 8px; transition: background 0.3s ease, color 0.3s ease, border 0.3s ease, transform 0.2s ease; justify-content: center; font-family: 'Poppins', sans-serif; text-decoration: none; margin: 5px 0; width: 300px;" onmouseover="this.style.background='white'; this.style.color='orange'; this.style.border='1px solid orange'; this.style.transform='translateY(-2px)';" onmouseout="this.style.background='orange'; this.style.color='white'; this.style.border='none'; this.style.transform='none';">Room Occupancy by Student Average Percentage</a></li>
                <li><a href="report5Servlet.do" style="padding: 12px 24px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; font-weight: 600; display: inline-flex; align-items: center; gap: 8px; transition: background 0.3s ease, color 0.3s ease, border 0.3s ease, transform 0.2s ease; justify-content: center; font-family: 'Poppins', sans-serif; text-decoration: none; margin: 5px 0; width: 300px;" onmouseover="this.style.background='white'; this.style.color='orange'; this.style.border='1px solid orange'; this.style.transform='translateY(-2px)';" onmouseout="this.style.background='orange'; this.style.color='white'; this.style.border='none'; this.style.transform='none';">Student Registrations by Date Range</a></li>
            </ul>
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