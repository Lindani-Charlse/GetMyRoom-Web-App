<%@page import="za.ac.tut.entities.Profile"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - GetMyRoom</title>
    <link rel="stylesheet" href="adminPanel.css">
</head>
<body>
    <header>
        <h1>GetMyRoom</h1>
    </header>

    <div class="container">
        <div class="sidebar">
            <h3>Admin</h3>
            <ul>
		<li><a href="ViewRoomServlet.do">View Rooms</a></li>
                <li><a href="ManageProfilesServlet.do">Manage Profiles</a></li>
		<li><a href="manageRooms.html">Manage Rooms</a></li>
                <li><a href="main.html">Logout</a></li>
            </ul>
        </div>
        
        <%
            List<Profile> list = (List<Profile>)request.getAttribute("list");
        %>

        <div class="content">
            <h2>Welcome to the Admin Panel</h2>
            
            <p>
            <form action="RemoveProfileServlet.do" method="POST">
                <table>
                    <tr>
                        <th>Student NO</th>
                        <th>Surname</th>
                        <th>Full names</th>
                        <th>Gender</th>
                        <th>Contact</th>
                        <th>Email</th>
                        <th></th>
                    </tr>
                    <%
                        for(Profile profile : list)
                        {
                            String studentNumber = profile.getStudent().getStudentNo();
                            String surname = profile.getStudent().getSurname();
                            String fullnames = profile.getStudent().getFullNames();
                            Character gender = profile.getStudent().getGender();
                            String contact = profile.getStudent().getContact();
                            String email = profile.getStudent().getEmail();
                    %>
                <tr>
                    <td><%=studentNumber%></td>
                    <td><%=surname%></td>
                    <td><%=fullnames%></td>
                    <td><%=gender%></td>
                    <td><%=contact%></td>
                    <td><%=email%></td>
                    <td>
                        <select name="studentNumber">
                            <option value="<%=studentNumber%>" selected=""></option>
                        </select>
                     </td>
                    <td><input type="submit" value="remove" /></td>
                </tr>
                <%
                    }
                %>
            </form>
            </p>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 GetMyRoom Admin Panel. All Rights Reserved.</p>
    </footer>

    </script>
</body>
</html>
