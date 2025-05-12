<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GetMyRoom Student Portal</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Body Styling */
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.6;
            margin: 0;
            padding-top: 140px; /* Adjusted for smaller hero */
            min-height: 100vh;
        }

        /* Hero Section */
        .hero {
            background-color: rgba(0, 0, 0, 0.7);
            color: white;
            padding: 20px 30px; /* Reduced padding */
            text-align: center;
            position: fixed;
            width: 100%;
            top: 70px;
            z-index: 999;
            border-radius: 0;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
            animation: fadeIn 1s ease-in;
        }

        .hero h1 {
            margin: 0;
            font-size: 2.2em;
            font-weight: 600;
        }

        .hero p {
            margin: 10px 0 0;
            font-size: 1.1em;
            opacity: 0.9;
        }

        .hero .datetime {
            margin: 10px 0 0;
            font-size: 1em;
            font-weight: 400;
        }

        /* Navigation Menu */
        .navbar {
            background-color: rgba(0, 0, 0, 0.7);
            padding: 15px 30px;
            position: fixed;
            width: 100%;
            top: 0;
            z-index: 1000;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .nav-menu {
            list-style: none;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
        }

        .nav-item {
            position: relative;
            margin: 0 20px;
        }

        .nav-item a {
            color: white;
            text-decoration: none;
            font-size: 1.2rem;
            padding: 12px 20px;
            display: flex;
            align-items: center;
            gap: 8px;
            transition: background 0.3s ease;
        }

        .nav-item a:hover {
            background-color: #f8f9fa;
            border-radius: 5px;
            color: orange;
        }

        .dropdown-menu {
            display: none;
            position: absolute;
            background-color: rgba(0, 0, 0, 0.9);
            list-style: none;
            padding: 10px 0;
            margin: 0;
            border-radius: 5px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.2);
            min-width: 200px;
            top: 100%;
            left: 0;
        }

        .dropdown:hover .dropdown-menu {
            display: block;
        }

        .dropdown-menu li a {
            padding: 10px 20px;
            color: white;
            font-size: 1rem;
            display: block;
            transition: background-color 0.3s ease;
        }

        .dropdown-menu li a:hover {
            background-color: orange;
            color: white;
        }

        .dropdown-toggle::after {
            content: ' ▼';
            font-size: 0.7rem;
            margin-left: 5px;
        }

        .datetime {
            color: white;
            font-size: 1em;
            font-weight: 400;
            padding: 12px 20px;
        }

        /* Container Styling */
        .container {
            max-width: 1400px; /* Increased width */
            margin: 20px auto;
            padding: 0 30px;
            display: grid;
            grid-template-columns: 3fr 2fr; /* Adjusted proportions */
            gap: 30px;
        }

        /* Card Styling */
        .card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.1);
            padding: 30px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        }

        .card h2 {
            margin: 0 0 20px;
            font-size: 1.8em;
            color: #333;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        /* Profile Info Styling */
        .profile-info .profile-pic {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 20px;
            border: 3px solid orange;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .profile-info .default-pic {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            background: #e9ecef;
            border: 3px solid orange;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            display: flex;
            justify-content: center;
            align-items: center;
            margin-bottom: 20px;
        }

        .profile-info .default-pic i {
            font-size: 70px;
            color: #333;
        }

        .profile-info table {
            width: 100%;
            border-collapse: collapse;
            font-size: 1.1em; /* Improved readability */
        }

        .profile-info td {
            padding: 15px;
            border-bottom: 1px solid #e9ecef;
        }

        .profile-info td:first-child {
            font-weight: 600;
            color: orange;
            width: 40%;
        }

        /* Room Status Styling */
        .room-status.allocated {
            color: #155724;
            padding: 8px 12px;
            border-radius: 5px;
            font-weight: 500;
        }

        .room-status.not-allocated {
            color: #721c24;
            padding: 8px 12px;
            border-radius: 5px;
            font-weight: 500;
        }

        .room-status.ineligible {
            color: #721c24;
            background: #f8d7da;
            padding: 8px 12px;
            border-radius: 5px;
            font-weight: 500;
        }

        /* Room Application */
        .room-application {
            margin-top: 20px;
            text-align: center;
        }

        .room-application h3 {
            margin: 0 0 15px;
            font-size: 1.4em;
            color: #333;
        }

        /* Profile Actions */
        .profile-actions {
            margin-top: 20px;
            display: flex;
            gap: 15px;
            justify-content: center;
        }

        /* Form Styling */
        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: flex;
            align-items: center;
            gap: 5px;
            margin-bottom: 8px;
            font-weight: 600;
            color: #555;
        }

        .form-group label i {
            color: orange;
        }

        .form-group input[type="email"],
        .form-group input[type="tel"],
        .form-group input[type="file"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            font-size: 1em;
            transition: border-color 0.3s ease;
        }

        .form-group input:focus {
            border-color: orange;
            outline: none;
            box-shadow: 0 0 5px rgba(255,165,0,0.3);
        }

        input[type="file"]::file-selector-button {
            padding: 8px 15px;
            background-color: orange;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="file"]::file-selector-button:hover {
            background-color: #e69500;
        }

        /* Button Styling */
        .submit-button {
            padding: 12px 24px;
            background: orange;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            font-weight: 600;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            transition: background 0.3s ease, color 0.3s ease, transform 0.2s ease;
        }

        .submit-button:hover {
            background: white;
            color: orange;
            border: 1px solid orange;
            transform: translateY(-2px);
        }

        .submit-button:disabled {
            background: #6c757d;
            color: white;
            cursor: not-allowed;
            transform: none;
        }

        /* Message Styling */
        .success-message {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
            font-family: 'Poppins', sans-serif;
        }

        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
            font-family: 'Poppins', sans-serif;
        }

        /* Footer Styling */
        footer {
            text-align: center;
            padding: 20px;
            background: #e9ecef;
            color: orange;
            font-size: 0.9em;
            position: relative;
            width: 100%;
        }

        /* Animation */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .container {
                grid-template-columns: 1fr;
                max-width: 90%;
            }

            .hero h1 {
                font-size: 1.8em;
            }

            .profile-info .profile-pic, .profile-info .default-pic {
                width: 120px;
                height: 120px;
            }

            .profile-info .default-pic i {
                font-size: 60px;
            }

            .navbar {
                flex-direction: column;
                gap: 10px;
                padding: 10px 20px;
            }

            .nav-menu {
                flex-wrap: wrap;
                justify-content: center;
            }

            .nav-item {
                margin: 5px 10px;
            }

            .datetime {
                font-size: 0.9em;
                padding: 10px;
            }

            .profile-actions {
                flex-direction: column;
                align-items: center;
            }

            .card {
                padding: 20px;
            }
        }

        @media (max-width: 480px) {
            .hero h1 {
                font-size: 1.6em;
            }

            .card h2 {
                font-size: 1.5em;
            }

            .profile-info table {
                font-size: 1em;
            }

            .submit-button {
                padding: 10px 20px;
                font-size: 0.9em;
            }

            .form-group input {
                padding: 10px;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <ul class="nav-menu">
            <li class="nav-item"><a href="index.html"><i class="fas fa-home"></i> Home</a></li>
            <li class="nav-item"><a href="studentLogin.html"><i class="fas fa-user-graduate"></i> Student Login</a></li>
            <li class="nav-item"><a href="about.html"><i class="fas fa-info-circle"></i> About</a></li>
            <li class="nav-item"><a href="contact.html"><i class="fas fa-phone-alt"></i> Contact</a></li>
            <li class="nav-item dropdown">
                <a href="#" class="dropdown-toggle"><i class="fas fa-ellipsis-h"></i> More</a>
                <ul class="dropdown-menu">
                    <li><a href="services.html"><i class="fas fa-concierge-bell"></i> Services</a></li>
                </ul>
            </li>
        </ul>
        <div class="datetime" id="datetime"></div>
    </nav>

    <div class="hero">
        <h1>Welcome, <%= request.getAttribute("fullnames") %> <%= request.getAttribute("surname") %></h1>
    </div>

    <%
        String studentNumber = (String)request.getAttribute("studentNumber");
        String surname = (String)request.getAttribute("surname");
        String fullnames = (String)request.getAttribute("fullnames");
        String email = (String)request.getAttribute("email");
        String phoneNumber = (String)request.getAttribute("phoneNumber");
        Integer averagePercentage = (Integer)request.getAttribute("averagePercentage");
        String roomAllocation = (String)request.getAttribute("roomAllocation");
        byte[] profilePic = (byte[])request.getAttribute("profilePic");
    %>

    <div class="container">
        <div class="card profile-info">
            <h2><i class="fas fa-user-circle"></i> Profile Information</h2>
            <c:if test="${not empty message}">
                <div id="messageContainer">
                    <p class="${messageType == 'success' ? 'success-message' : 'error-message'}">${fn:escapeXml(message)}</p>
                </div>
            </c:if>
            <% if (profilePic != null) { %>
                <img src="data:image/jpeg;base64,<%= java.util.Base64.getEncoder().encodeToString(profilePic) %>" alt="Profile Picture" class="profile-pic">
            <% } else { %>
                <div class="default-pic"><i class="fas fa-user-graduate"></i></div>
            <% } %>
            <table>
                <tr><td><i class="fas fa-id-card"></i> Student Number:</td><td><%= studentNumber %></td></tr>
                <tr><td><i class="fas fa-user"></i> Name:</td><td><%= surname %> <%= fullnames %></td></tr>
                <tr><td><i class="fas fa-envelope"></i> Email:</td><td><%= email %></td></tr>
                <tr><td><i class="fas fa-phone"></i> Phone:</td><td><%= phoneNumber %></td></tr>
                <tr><td><i class="fas fa-graduation-cap"></i> Average Percentage:</td><td><%= averagePercentage %>%</td></tr>
                <tr>
                    <td><i class="fas fa-door-open"></i> Room Status:</td>
                    <td>
                        <c:choose>
                            <c:when test="${roomAllocation.startsWith('Allocated')}">
                                <span class="room-status allocated"><%= roomAllocation %></span>
                            </c:when>
                            <c:when test="${averagePercentage < 55}">
                                <span class="room-status ineligible">Ineligible (Average < 55%)</span>
                            </c:when>
                            <c:otherwise>
                                <span class="room-status not-allocated">Not Allocated</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
            <div class="room-application">
                <h3>Room Application</h3>
                <form action="RoomAllocationServlet.do" method="POST" id="roomApplicationForm">
                    <input type="hidden" name="studentNo" value="<%= studentNumber %>">
                    <input type="hidden" name="csrfToken" value="${fn:escapeXml(sessionScope.csrfToken)}">
                    <button type="submit" class="submit-button" 
                            <c:if test="${not isApplyButtonActive || averagePercentage < 55}">disabled</c:if>>
                            <i class="fas fa-door-open"></i> Apply for Room
                    </button>
                </form>
            </div>
            <div class="profile-actions">
                <button class="submit-button" onclick="confirmLogout()"><i class="fas fa-sign-out-alt"></i> Logout</button>
                <form action="DeleteProfileServlet.do" method="POST" style="display: inline;" id="deleteProfileForm">
                    <input type="hidden" name="studentNo" value="<%= studentNumber %>">
                    <input type="hidden" name="csrfToken" value="${fn:escapeXml(sessionScope.csrfToken)}">
                    <button type="button" class="submit-button" onclick="confirmDeleteProfile()"><i class="fas fa-trash-alt"></i> Delete Profile</button>
                </form>
            </div>
        </div>

        <div class="card">
            <h2><i class="fas fa-edit"></i> Edit Profile</h2>
            <form action="EditProfileServlet.do" method="POST" enctype="multipart/form-data" id="editProfileForm">
                <div class="form-group">
                    <label for="email"><i class="fas fa-envelope"></i> Email</label>
                    <input type="email" id="email" name="email" value="${fn:escapeXml(email)}" required>
                </div>
                <div class="form-group">
                    <label for="phone"><i class="fas fa-phone"></i> Phone Number</label>
                    <input type="tel" id="phone" name="phone" value="${fn:escapeXml(phoneNumber)}" required pattern="[0-9]{10}" title="Phone number must be 10 digits">
                </div>
                <div class="form-group">
                    <label for="newProfilePic"><i class="fas fa-image"></i> Update Profile Picture</label>
                    <input type="file" id="newProfilePic" name="newProfilePic" accept="image/*">
                </div>
                <input type="hidden" name="studentNo" value="${fn:escapeXml(studentNumber)}">
                <input type="hidden" name="csrfToken" value="${fn:escapeXml(sessionScope.csrfToken)}">
                <button type="submit" class="submit-button"><i class="fas fa-save"></i> Update Profile</button>
            </form>
        </div>
    </div>

    <footer>
        <p>© 2025 GetMyRoom. All Rights Reserved.</p>
    </footer>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        // Update date and time every second
        function updateDateTime() {
            const now = new Date();
            const options = {
                day: '2-digit',
                month: 'short',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit',
                hour12: false
            };
            const formattedDate = now.toLocaleString('en-US', options).replace(',', '');
            document.querySelectorAll('#datetime').forEach(el => el.textContent = formattedDate);
        }
        setInterval(updateDateTime, 1000);
        updateDateTime();

        // Logout confirmation
        function confirmLogout() {
            if (confirm('Are you sure you want to log out?')) {
                alert('You have logged out successfully!');
                window.location.href = 'index.html';
            }
        }

        // Delete profile confirmation
        function confirmDeleteProfile() {
            if (confirm('Are you sure you want to permanently delete your profile? This action cannot be undone and will remove all your data, including room allocation.')) {
                document.getElementById('deleteProfileForm').submit();
            }
        }

        // Room application confirmation
        document.getElementById('roomApplicationForm').addEventListener('submit', function(e) {
            if (!confirm('Are you sure you want to apply for a room? This action will allocate an available room based on your gender.')) {
                e.preventDefault();
            }
        });

        // Auto-refresh page after message display
        if (document.querySelector('#messageContainer p')) {
            setTimeout(function() {
                window.location.reload();
            }, 3000);
        }
    </script>
</body>
</html>