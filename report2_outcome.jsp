<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Room Occupancy by Course - GetMyRoom</title>
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
            <h1>Room Occupancy by Course</h1>
            <div class="live-time" id="liveTime"></div>
            <c:if test="${not empty message}">
                <p class="${messageType == 'success' ? 'success-message' : 'error-message'}">${message}</p>
            </c:if>

            <form action="report2Servlet.do" method="GET" style="margin-bottom: 20px;">
                <label for="courseName">Course:</label>
                <select name="courseName" id="courseName">
                    <option value="" ${empty param.courseName ? 'selected' : ''}>All</option>
                    <option value="Computer Science" ${param.courseName == 'Computer Science' ? 'selected' : ''}>Computer Science</option>
                    <option value="Engineering" ${param.courseName == 'Engineering' ? 'selected' : ''}>Engineering</option>
                    <option value="Business Studies" ${param.courseName == 'Business Studies' ? 'selected' : ''}>Business Studies</option>
                </select>
                <button type="submit" style="padding: 8px 16px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; margin-left: 10px;">Filter</button>
            </form>

            <c:if test="${not empty reportData}">
                <table class="profile-info">
                    <thead>
                        <tr>
                            <th>Room Number</th>
                            <th>Student Number</th>
                            <th>Full Names</th>
                            <th>Course</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${reportData}">
                            <tr>
                                <td>${item.roomNo}</td>
                                <td>${item.studentNo}</td>
                                <td>${item.fullNames}</td>
                                <td>${item.courseName}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <a href="report2Servlet.do?export=pdf&courseName=${param.courseName}" style="padding: 12px 24px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; font-weight: 600; display: inline-flex; align-items: center; gap: 8px; transition: background 0.3s ease, color 0.3s ease, transform 0.2s ease; justify-content: center; font-family: 'Poppins', sans-serif; text-decoration: none; margin-top: 20px;">Export to PDF</a>
                <a href="summaryReport.do" style="padding: 12px 24px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; font-weight: 600; display: inline-flex; align-items: center; gap: 8px; transition: background 0.3s ease, color 0.3s ease, transform 0.2s ease; justify-content: center; font-family: 'Poppins', sans-serif; text-decoration: none; margin-top: 20px; margin-left: 10px;">Back to Reports</a>
            </c:if>
            <c:if test="${empty reportData}">
                <p>No data available for the selected course.</p>
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