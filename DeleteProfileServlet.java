
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.bl.StudentFacadeLocal;
import za.ac.tut.entities.Student;

public class DeleteProfileServlet extends HttpServlet {
    @EJB
    private StudentFacadeLocal studentFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String studentNo = request.getParameter("studentNo");
        String message = null;
        String messageType = "error";

        try {
            // Validate studentNo
            if (studentNo == null || studentNo.isEmpty()) {
                message = "Invalid student number.";
            } else {
                // Find the student
                Student student = studentFacade.find(studentNo);
                if (student == null) {
                    message = "Student profile not found.";
                } else {
                    // Delete the student (cascades to Profile and Room)
                    studentFacade.remove(student);

                    // Invalidate session
                    if (session != null) {
                        session.invalidate();
                    }

                    message = "Your profile has been deleted successfully.";
                    messageType = "success";
                }
            }
        } catch (Exception e) {
            message = "Failed to delete profile. Please try again.";
            e.printStackTrace(); // Log for debugging
        }

        // Set message attributes and forward to message.jsp
        request.setAttribute("message", message);
        request.setAttribute("messageType", messageType);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/message.jsp");
        dispatcher.forward(request, response);
    }
}