<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GetMyRoom - Status</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.6;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            padding: 20px;
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

        /* Message Container */
        .message-container {
            max-width: 600px;
            margin: 100px auto;
            padding: 30px;
            border-radius: 10px;
            text-align: center;
            background: white;
            box-shadow: 0 6px 20px rgba(0,0,0,0.1);
        }

        .message-container.success {
            border-left: 5px solid #28a745;
        }

        .message-container.error {
            border-left: 5px solid #dc3545;
        }

        .message-container h2 {
            margin-bottom: 20px;
            font-size: 1.8em;
            color: #333;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .message-container p {
            font-size: 1.2em;
            margin-bottom: 20px;
        }

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

        /* Footer Styling */
        footer {
            text-align: center;
            padding: 20px;
            background: #e9ecef;
            color: orange;
            font-size: 0.9em;
            width: 100%;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .message-container {
                margin: 80px auto;
                padding: 20px;
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
        }

        @media (max-width: 480px) {
            .message-container h2 {
                font-size: 1.5em;
            }

            .message-container p {
                font-size: 1em;
            }

            .submit-button {
                padding: 10px 20px;
                font-size: 0.9em;
            }
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

    <div class="message-container ${messageType}">
        <h2>
            <i class="fas ${messageType == 'success' ? 'fa-check-circle' : 'fa-exclamation-circle'}"></i>
            ${messageType == 'success' ? 'Success' : 'Error'}
        </h2>
        <p>${message}</p>
        <p>
            <button class="submit-button" onclick="window.location.href='<%= "success".equals(request.getAttribute("messageType")) ? 
                (request.getAttribute("message").toString().contains("Room") ? "studLoginServlet.do" : "studentPanel.jsp") : 
                (request.getAttribute("message").toString().contains("login") ? "studentLogin.html" : 
                request.getAttribute("message").toString().contains("Room") ? "studLoginServlet.do" : "studentPanel.jsp") %>'">
                <i class="fas fa-arrow-right"></i> <%= "success".equals(request.getAttribute("messageType")) ? "Continue" : "Try Again" %>
            </button>
        </p>
    </div>

    <footer>
        <p>© 2025 GetMyRoom. All Rights Reserved.</p>
    </footer>
</body>
</html>