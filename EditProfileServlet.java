
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import za.ac.tut.bl.ProfileFacadeLocal;
import za.ac.tut.bl.StudentFacadeLocal;
import za.ac.tut.entities.Profile;
import za.ac.tut.entities.Student;

@WebServlet(name = "EditProfileServlet", urlPatterns = {"/EditProfileServlet.do"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
public class EditProfileServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @EJB
    private StudentFacadeLocal studentFacade;

    @EJB
    private ProfileFacadeLocal profileFacade;

    @Override
    public void init() throws ServletException {
        try {
            emf = Persistence.createEntityManagerFactory("StudentPU");
        } catch (Exception e) {
            System.err.println("Failed to initialize EntityManagerFactory: " + e.getMessage());
            throw new ServletException("Cannot initialize EntityManagerFactory", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get form parameters
        String studentNo = request.getParameter("studentNo");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        
        Student student = studentFacade.find(studentNo);
        
        student.setEmail(email);
        student.setContact(phone);
        
        studentFacade.edit(student);
        
        RequestDispatcher dis = request.getRequestDispatcher("result2.jsp");
        dis.forward(request, response);
        
        
        
        
        
        
        
        
        
        
        
        
    }
}
        
        
        
        
//        String phone = request.getParameter("phone");
//        String csrfToken = request.getParameter("csrfToken");
//        String sessionCsrfToken = (String) request.getSession().getAttribute("csrfToken");
//        String loggedInStudentNo = (String) request.getSession().getAttribute("loggedInStudentNo");
        
        

//        // Validate CSRF token
//        if (csrfToken == null || !csrfToken.equals(sessionCsrfToken)) {
//            System.err.println("Invalid CSRF token for studentNo: " + studentNo);
//            request.getSession().setAttribute("message", "Invalid CSRF token.");
//            request.getSession().setAttribute("messageType", "error");
//            response.sendRedirect(request.getContextPath() + "/result.jsp");
//            return;
//        }
//
//        // Validate student number and authorization
//        if (studentNo == null || studentNo.trim().isEmpty()) {
//            System.err.println("Invalid or empty student number provided");
//            request.getSession().setAttribute("message", "Invalid student number provided.");
//            request.getSession().setAttribute("messageType", "error");
//            response.sendRedirect(request.getContextPath() + "/result.jsp");
//            return;
//        }
//
//        // Prevent IDOR
//        if (!studentNo.equals(loggedInStudentNo)) {
//            System.err.println("Unauthorized attempt to update profile for studentNo: " + studentNo);
//            request.getSession().setAttribute("message", "Unauthorized action.");
//            request.getSession().setAttribute("messageType", "error");
//            response.sendRedirect(request.getContextPath() + "/result.jsp");
//            return;
//        }
//
//        // Validate input fields
//        if (email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
//            System.err.println("Invalid email provided for studentNo: " + studentNo);
//            request.getSession().setAttribute("message", "Invalid email address.");
//            request.getSession().setAttribute("messageType", "error");
//            response.sendRedirect(request.getContextPath() + "/result.jsp");
//            return;
//        }
//
//        if (phone == null || !phone.matches("\\d{10}")) {
//            System.err.println("Invalid phone number provided for studentNo: " + studentNo);
//            request.getSession().setAttribute("message", "Phone number must be 10 digits.");
//            request.getSession().setAttribute("messageType", "error");
//            response.sendRedirect(request.getContextPath() + "/result.jsp");
//            return;
//        }
//
//        EntityManager em = null;
//        try {
//            // Create EntityManager
//            em = emf.createEntityManager();
//            em.getTransaction().begin();
//
//            // Find the student
//            Student student = studentFacade.find(studentNo);
//            if (student == null) {
//                System.err.println("Student not found for studentNo: " + studentNo);
//                request.getSession().setAttribute("message", "Student not found.");
//                request.getSession().setAttribute("messageType", "error");
//                response.sendRedirect(request.getContextPath() + "/result.jsp");
//                return;
//            }
//
//            // Update student fields
//            student.setEmail(email);
//            student.setContact(phone);
//            studentFacade.edit(student);
//
//            // Handle profile picture update
//            Part filePart = request.getPart("newProfilePic");
//            if (filePart != null && filePart.getSize() > 0) {
//                Profile profile = profileFacade.findByStudentNo(studentNo);
//                if (profile == null) {
//                    System.err.println("Profile not found for studentNo: " + studentNo);
//                    request.getSession().setAttribute("message", "Profile not found.");
//                    request.getSession().setAttribute("messageType", "error");
//                    response.sendRedirect(request.getContextPath() + "/result2.jsp");
//                    return;
//                }
//
//                // Read the uploaded file into a byte array
//                try (InputStream input = filePart.getInputStream()) {
//                    byte[] profilePic = new byte[(int) filePart.getSize()];
//                    input.read(profilePic);
//                    profile.setProfilePic(profilePic);
//                    profileFacade.edit(profile);
//                }
//            }
//
//            em.getTransaction().commit();
//            System.out.println("Successfully updated profile for studentNo: " + studentNo);
//
//            // Set success message
//            request.getSession().setAttribute("message", "Profile updated successfully!");
//            request.getSession().setAttribute("messageType", "success");
//        } catch (Exception e) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//                System.err.println("Transaction rolled back for studentNo: " + studentNo);
//            }
//            System.err.println("Error updating profile for studentNo: " + studentNo + ": " + e.getMessage());
//            e.printStackTrace();
//            request.getSession().setAttribute("message", "An error occurred while updating the profile: " + e.getMessage());
//            request.getSession().setAttribute("messageType", "error");
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//            response.sendRedirect(request.getContextPath() + "/result2.jsp");
//        }
//    }
//
//    @Override
//    public void destroy() {
//        if (emf != null && emf.isOpen()) {
//            emf.close();
//            System.out.println("EntityManagerFactory closed");
//        }
//    }
//}