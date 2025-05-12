package za.ac.tut.web;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.ac.tut.bl.RoomFacadeLocal;
import za.ac.tut.bl.StudentFacadeLocal;
import za.ac.tut.entities.Room;
import za.ac.tut.entities.Student;

public class allocateRoomServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(allocateRoomServlet.class.getName());

    @EJB
    private RoomFacadeLocal rfl;

    @EJB
    private StudentFacadeLocal sfl;

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.log(Level.INFO, "Initializing AllocateRoomServlet");
        if (rfl == null || sfl == null) {
            LOGGER.log(Level.SEVERE, "EJB injection failed: RoomFacadeLocal or StudentFacadeLocal is null");
            throw new ServletException("EJB injection failed");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Processing POST request for room allocation");
        String roomNo = request.getParameter("roomNo");
        String studentNo = request.getParameter("studentNo");

        // Input validation
        String errorMessage = null;
        if (roomNo == null || roomNo.trim().isEmpty()) {
            errorMessage = "Room number is missing.";
        } else if (studentNo == null || studentNo.trim().isEmpty()) {
            errorMessage = "Student number is missing.";
        }

        if (errorMessage != null) {
            LOGGER.log(Level.WARNING, "Validation error: {0}", errorMessage);
            List<Room> roomList = rfl.findAll();
            request.setAttribute("roomList", roomList);
            request.setAttribute("message", errorMessage);
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("manage_rooms_outcome.jsp").forward(request, response);
            return;
        }

        Room room = rfl.find(roomNo);
        if (room == null) {
            LOGGER.log(Level.WARNING, "Room not found: {0}", roomNo);
            List<Room> roomList = rfl.findAll();
            request.setAttribute("roomList", roomList);
            request.setAttribute("message", "Room " + roomNo + " not found.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("manage_rooms_outcome.jsp").forward(request, response);
            return;
        }

        Student student = sfl.find(studentNo);
        if (student == null) {
            LOGGER.log(Level.WARNING, "Student not found: {0}", studentNo);
            List<Room> roomList = rfl.findAll();
            request.setAttribute("roomList", roomList);
            request.setAttribute("message", "Student with number " + studentNo + " not found.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("manage_rooms_outcome.jsp").forward(request, response);
            return;
        }

        try {
            // Allocate student to room
            room.setStudent(student);
            // Optionally update other fields if provided
            String surname = request.getParameter("surname");
            String fullnames = request.getParameter("fullnames");
            String contact = request.getParameter("contact");
            if (surname != null && !surname.trim().isEmpty()) {
                room.setSurname(surname);
            }
            if (fullnames != null && !fullnames.trim().isEmpty()) {
                room.setFullnames(fullnames);
            }
            if (contact != null && !contact.trim().isEmpty()) {
                room.setContact(contact);
            }

            rfl.edit(room);
            LOGGER.log(Level.INFO, "Room {0} allocated to student {1}", new Object[]{roomNo, studentNo});
            List<Room> roomList = rfl.findAll();
            request.setAttribute("roomList", roomList);
            request.setAttribute("message", "Room " + roomNo + " allocated successfully to student " + studentNo + ".");
            request.setAttribute("messageType", "success");
        } catch (Exception e) {
            List<Room> roomList = rfl.findAll();
            request.setAttribute("roomList", roomList);
            request.setAttribute("message", "Failed to allocate room " + roomNo + ": " + e.getMessage());
            request.setAttribute("messageType", "error");
        }

        LOGGER.log(Level.INFO, "Forwarding to manage_rooms_outcome.jsp");
        request.getRequestDispatcher("manage_rooms_outcome.jsp").forward(request, response);
    }
}