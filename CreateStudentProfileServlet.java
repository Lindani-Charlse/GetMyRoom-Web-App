package za.ac.tut.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Random;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import za.ac.tut.bl.StudentFacadeLocal;
import za.ac.tut.bl.ProfileFacadeLocal;
import za.ac.tut.entities.Student;
import za.ac.tut.entities.Profile;

@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB max file size
public class CreateStudentProfileServlet extends HttpServlet {
    @EJB
    private StudentFacadeLocal rsfl;
    @EJB
    private ProfileFacadeLocal spfl;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String message = null;
        String messageType = "error"; // Default to error

        try {
            // Get form parameters
            String fundingType = request.getParameter("fundingType");
            String studentNumber = request.getParameter("studentNo");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm_password");
            Part profilePicPart = request.getPart("profilePic");

            // Validate inputs
            if (!validateStudentNumber(studentNumber)) {
                message = "Invalid student number. It must be exactly 9 characters.";
            } else if (!validatePassword(password, confirmPassword)) {
                message = "Passwords do not match or are invalid (max 15 characters).";
            } else if (fundingType == null || fundingType.trim().isEmpty()) {
                message = "Funding type is required.";
            } else {
                // Check if student exists
                Student student = rsfl.find(studentNumber);
                if (student == null) {
                    message = "Student number not found. Please register first.";
                } else {
                    // Process profile picture
                    byte[] profilePic = null;
                    if (profilePicPart != null && profilePicPart.getSize() > 0) {
                        try (InputStream input = profilePicPart.getInputStream()) {
                            profilePic = readInputStream(input);
                        }
                    }

                    // Create profile
                    Profile profile = createStudentProfile(student, password, fundingType, profilePic);
                    spfl.create(profile);

                    // Set session attribute
                    session.setAttribute("studentNumber", studentNumber);

                    // Set success message
                    message = "Profile created successfully! You can now log in.";
                    messageType = "success";

                    // Redirect to studentLogin.html with success message
                    String queryString = String.format("message=%s&messageType=%s",
                            URLEncoder.encode(message, "UTF-8"),
                            URLEncoder.encode(messageType, "UTF-8"));
                    response.sendRedirect("studentLogin.html?" + queryString);
                    return;
                }
            }
        } catch (Exception e) {
            message = "An error occurred while creating the profile. Please try again.";
            e.printStackTrace(); // Log the error for debugging
        }

        // Redirect to signup.html with error message
        String queryString = String.format("message=%s&messageType=%s",
                URLEncoder.encode(message, "UTF-8"),
                URLEncoder.encode(messageType, "UTF-8"));
        response.sendRedirect("signup.html?" + queryString);
    }

    private boolean validateStudentNumber(String studentNumber) {
        return studentNumber != null && studentNumber.length() == 9;
    }

    private boolean validatePassword(String password, String confirmPassword) {
        return password != null && confirmPassword != null &&
               password.equals(confirmPassword) && password.length() <= 15 && !password.trim().isEmpty();
    }

    private Profile createStudentProfile(Student student, String password, String fundingType, byte[] profilePic) {
        Profile profile = new Profile();
        profile.setPassword(password);
        profile.setStudent(student);
        profile.setFundingType(fundingType);
        profile.setProfilePic(profilePic);
        profile.setProfileId(generateProfileID());
        return profile;
    }

    private byte[] readInputStream(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
    
    public static Long generateProfileID() {
        Random random = new Random();
        return (long) (random.nextInt(999) + 2);
    }
}