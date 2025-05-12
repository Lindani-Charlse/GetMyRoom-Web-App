package za.ac.tut.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.ac.tut.bl.ProfileFacadeLocal;
import za.ac.tut.entities.Profile;

@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/ResetPasswordServlet.do"})
public class ResetPasswordServlet extends HttpServlet {
    @EJB
    private ProfileFacadeLocal profileFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentNo = request.getParameter("studentNo");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String destination = "resetPassword.jsp";
        String message = null;
        String messageType = "error";

        try {
            // Validate inputs
            if (studentNo == null || studentNo.trim().isEmpty()) {
                message = "Student number is required.";
            } else if (password == null || password.trim().isEmpty() || !password.equals(confirmPassword)) {
                message = "Passwords do not match.";
            } else {
                // Find profile by studentNo
                Profile profile = profileFacade.findByStudentNo(studentNo);
                if (profile == null) {
                    message = "Student profile not found.";
                } else {
                    // Update password
                    profile.setPassword(password.trim());
                    profileFacade.edit(profile);
                    message = "Password reset successfully. Redirecting to login page...";
                    messageType = "success";
                    destination = "resetPassword.jsp"; // JSP handles redirection
                }
            }
        } catch (Exception e) {
            message = "Failed to reset password. Please try again.";
            e.printStackTrace(); // Log for debugging
        }

        // Set message attributes
        request.setAttribute("message", message);
        request.setAttribute("messageType", messageType);

        // Forward to destination
        RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }
}