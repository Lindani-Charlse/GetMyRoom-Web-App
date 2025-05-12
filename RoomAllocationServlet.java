package za.ac.tut.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.ac.tut.bl.StudentFacadeLocal;
import za.ac.tut.bl.RoomFacadeLocal;
import za.ac.tut.entities.Student;
import za.ac.tut.entities.Room;

public class RoomAllocationServlet extends HttpServlet {
    @EJB
    private StudentFacadeLocal studentFacade;
    
    @EJB
    private RoomFacadeLocal roomFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentNo = request.getParameter("studentNo");
        String message = null;
        String messageType = "error";

        try {
            // Find the student
            Student student = studentFacade.find(studentNo);
            if (student == null) {
                message = "Student not found.";
            } else {
                // Check eligibility (average >= 55%)
                if (student.getAveragePercentage() < 55) {
                    message = "You are not eligible to apply for a room (Average < 55%).";
                } else {
                    // Check if student already has a room
                    Room existingRoom = roomFacade.findByStudentNo(studentNo);
                    if (existingRoom != null) {
                        message = "You already have a room allocated: " + existingRoom.getRoomNo();
                    } else {
                        // Determine room prefix based on gender
                        String roomPrefix = student.getGender() == 'M' ? "A%" : "B%";
                        
                        // Find available rooms for the gender
                        Room availableRoom = roomFacade.findAvailableRoomByGender(roomPrefix);
                        
                        if (availableRoom == null) {
                            message = "No rooms available for your gender.";
                        } else {
                            // Allocate the room
                            availableRoom.setStudent(student);
                            availableRoom.setSurname(student.getSurname());
                            availableRoom.setFullnames(student.getFullNames());
                            availableRoom.setContact(student.getContact());
                            
                            // Update the room in the database
                            roomFacade.edit(availableRoom);
                            
                            message = "Room " + availableRoom.getRoomNo() + " has been allocated to you.";
                            messageType = "success";
                        }
                    }
                }

                // Re-populate studentPanel.jsp attributes
                request.setAttribute("studentNumber", student.getStudentNo());
                request.setAttribute("surname", student.getSurname());
                request.setAttribute("fullnames", student.getFullNames());
                request.setAttribute("email", student.getEmail());
                request.setAttribute("phoneNumber", student.getContact());
                request.setAttribute("averagePercentage", student.getAveragePercentage());
                
                Room updatedRoom = roomFacade.findByStudentNo(studentNo);
                String roomAllocation = updatedRoom != null ? "Allocated [" + updatedRoom.getRoomNo() + "]" : "Not allocated";
                request.setAttribute("roomAllocation", roomAllocation);
                boolean isApplyButtonActive = roomAllocation.equals("Not allocated") && student.getAveragePercentage() >= 55;
                request.setAttribute("isApplyButtonActive", isApplyButtonActive);
            }
        } catch (Exception e) {
            message = "An error occurred while processing your request. Please try again.";
            e.printStackTrace(); // Log for debugging
        }

        // Set message attributes and forward to studentPanel.jsp
        request.setAttribute("message", message);
        request.setAttribute("messageType", messageType);
        RequestDispatcher dispatcher = request.getRequestDispatcher("studentPanel.jsp");
        dispatcher.forward(request, response);
    }
}