package za.ac.tut.web;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet.do"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String message = null;
        String messageType = "error";

        try {
            String username = request.getParameter("name");
            String password = request.getParameter("password");
            String correctUsername = getServletContext().getInitParameter("admin_name");
            String correctPassword = getServletContext().getInitParameter("admin_password");

            // Validate input
            if (username == null || username.trim().isEmpty()) {
                message = "Username is required.";
            } else if (password == null || password.trim().isEmpty()) {
                message = "Password is required.";
            } else if (username.equals(correctUsername) && password.equals(correctPassword)) {
                // Valid credentials
                session.setAttribute("adminLoggedIn", true);
                message = "Login successful. Redirecting to admin panel...";
                messageType = "success";

                // Redirect to adminLogin.html with success message
                String queryString = String.format("message=%s&messageType=%s&redirectTo=adminPanel.jsp",
                        URLEncoder.encode(message, "UTF-8"),
                        URLEncoder.encode(messageType, "UTF-8"));
                response.sendRedirect("adminLogin.html?" + queryString);
                return;
            } else {
                message = "Invalid username or password.";
            }
        } catch (Exception e) {
            message = "An error occurred during login. Please try again.";
            e.printStackTrace(); // Log for debugging
        }

        // Redirect to adminLogin.html with error message
        String queryString = String.format("message=%s&messageType=%s",
                URLEncoder.encode(message, "UTF-8"),
                URLEncoder.encode(messageType, "UTF-8"));
        response.sendRedirect("adminLogin.html?" + queryString);
    }
}