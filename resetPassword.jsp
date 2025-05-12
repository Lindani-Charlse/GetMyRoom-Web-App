<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password - GetMyRoom</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
    <style>
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
    </style>
</head>
<body>
    <nav class="navbar">
        <ul class="nav-menu">
            <li class="nav-item"><a href="index.html"><i class="fas fa-home"></i> Home</a></li>
            <li class="nav-item"><a href="studentLogin.html"><i class="fas fa-user-graduate"></i> Student Login</a></li>
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
        <h1>Reset Password</h1>
        <c:if test="${not empty message}">
            <p class="${messageType == 'success' ? 'success-message' : 'error-message'}">${message}</p>
        </c:if>
        <form id="resetPasswordForm" action="ResetPasswordServlet.do" method="POST">
            <div class="form-group">
                <label for="studentNo">Student Number</label>
                <input type="text" id="studentNo" name="studentNo" placeholder="000000000" required>
            </div>
            <div class="form-group">
                <label for="password">New Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirm_password">Confirm Password</label>
                <input type="password" id="confirm_password" name="confirm_password" required>
            </div>
            <div class="checkbox-group">
                <input type="checkbox" id="showPassword" name="showPassword">
                <label for="showPassword">Show Passwords</label>
            </div>
            <button type="submit" class="submit-button" style="padding: 12px 24px; background: orange; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; font-weight: 600; display: inline-flex; align-items: center; gap: 8px; transition: background 0.3s ease, color 0.3s ease, transform 0.2s ease; width: 100%; justify-content: center; font-family: 'Poppins', sans-serif;" onmouseover="this.style.background='white'; this.style.color='orange'; this.style.border='1px solid orange'; this.style.transform='translateY(-2px)';" onmouseout="this.style.background='orange'; this.style.color='white'; this.style.border='none'; this.style.transform='none';">Reset Password</button>
        </form>
    </div>

    <footer>
        <p>© 2025 GetMyRoom. All Rights Reserved.</p>
    </footer>

    <script>
        document.getElementById('showPassword').addEventListener('change', function() {
            const passwordInput = document.getElementById('password');
            const confirmPasswordInput = document.getElementById('confirm_password');
            passwordInput.type = this.checked ? 'text' : 'password';
            confirmPasswordInput.type = this.checked ? 'text' : 'password';
        });

        // Redirect to studentLogin.html after 3 seconds if success message is present
        window.onload = function() {
            const message = document.querySelector('.success-message');
            if (message) {
                setTimeout(function() {
                    window.location.href = 'studentLogin.html';
                }, 3000);
            }
        };
    </script>
</body>
</html>