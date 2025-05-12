////package za.ac.tut.web;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Base64;
//import java.util.List;
//import javax.annotation.Resource;
//import javax.ejb.EJB;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//import za.ac.tut.bl.ProfileFacadeLocal;
//import za.ac.tut.dal.DataAccessLayer;
//import za.ac.tut.entities.Profile;
//
//@WebServlet(name = "ManageProfilesServlet", urlPatterns = {"/ManageProfilesServlet.do"})
//public class ManageProfilesServlet extends HttpServlet {
//    @EJB
//    private ProfileFacadeLocal spfl;
//    @Resource(name = "jdbc/GetMyRoomDB")
//    private DataSource ds;
//    private DataAccessLayer dal = new DataAccessLayer();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String action = request.getParameter("action");
//        String destination = "adminPanel.jsp";
//        String message = null;
//        String messageType = "success";
//
//        try {
//            if ("edit".equals(action)) {
//                String studentNo = request.getParameter("studentNo");
//                Profile profile = dal.getProfileByStudentNo(studentNo);
//                if (profile != null) {
//                    request.setAttribute("profile", profile);
//                    destination = "edit_profile.jsp";
//                } else {
//                    message = "Profile not found.";
//                    messageType = "error";
//                }
//            } else if ("delete".equals(action)) {
//                String studentNo = request.getParameter("studentNo");
//                Profile profile = dal.getProfileByStudentNo(studentNo);
//                if (profile != null) {
//                    spfl.remove(profile);
//                    message = "Profile deleted successfully.";
//                } else {
//                    message = "Profile not found.";
//                    messageType = "error";
//                }
//            }
//
//            // Populate profile list for admin panel
//            List<Profile> profiles = dal.getAllProfiles();
//            for (Profile p : profiles) {
//                if (p.getProfilePic() != null) {
//                    p.setProfilePicBase64(Base64.getEncoder().encodeToString(p.getProfilePic()));
//                }
//            }
//            request.setAttribute("list", profiles);
//            request.setAttribute("roomList", dal.getAllRooms());
//            request.setAttribute("message", message);
//            request.setAttribute("messageType", messageType);
//        } catch (Exception e) {
//            log("Error in ManageProfilesServlet: " + e.getMessage(), e);
//            request.setAttribute("message", "An error occurred: " + e.getMessage());
//            request.setAttribute("messageType", "error");
//        }
//
//        RequestDispatcher dis = request.getRequestDispatcher(destination);
//        dis.forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String action = request.getParameter("action");
//        String destination = "adminPanel.jsp";
//        String message = null;
//        String messageType = "success";
//
//        try {
//            if ("update".equals(action)) {
//                String studentNo = request.getParameter("studentNo");
//                String password = request.getParameter("password");
//                String fundingType = request.getParameter("fundingType");
//                String email = request.getParameter("email");
//
//                Profile profile = dal.getProfileByStudentNo(studentNo);
//                if (profile == null) {
//                    message = "Profile not found.";
//                    messageType = "error";
//                } else if (password.length() > 15) {
//                    message = "Password must be 15 characters or less.";
//                    messageType = "error";
//                } else {
//                    try (Connection conn = ds.getConnection();
//                         PreparedStatement stmt = conn.prepareCall("{CALL UpdateProfileAndStudent(?, ?, ?, ?, ?)}")) {
//                        stmt.setLong(1, profile.getProfileId());
//                        stmt.setString(2, password);
//                        stmt.setString(3, fundingType);
//                        stmt.setString(4, studentNo);
//                        stmt.setString(5, email);
//                        stmt.execute();
//                        message = "Profile updated successfully.";
//                    } catch (SQLException e) {
//                        log("Database error in ManageProfilesServlet: " + e.getMessage(), e);
//                        message = "Database error: " + e.getMessage();
//                        messageType = "error";
//                    }
//                }
//            }
//
//            List<Profile> profiles = dal.getAllProfiles();
//            for (Profile p : profiles) {
//                if (p.getProfilePic() != null) {
//                    p.setProfilePicBase64(Base64.getEncoder().encodeToString(p.getProfilePic()));
//                }
//            }
//            request.setAttribute("list", profiles);
//            request.setAttribute("roomList", dal.getAllRooms());
//            request.setAttribute("message", message);
//            request.setAttribute("messageType", messageType);
//        } catch (Exception e) {
//            log("Error in ManageProfilesServlet: " + e.getMessage(), e);
//            request.setAttribute("message", "An error occurred: " + e.getMessage());
//            request.setAttribute("messageType", "error");
//        }
//
//        RequestDispatcher dis = request.getRequestDispatcher(destination);
//        dis.forward(request, response);
//    }
//}