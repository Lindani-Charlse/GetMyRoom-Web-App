
package za.ac.tut.web;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.ac.tut.bl.RoomFacadeLocal;
import za.ac.tut.entities.Room;

public class ManageRoomsServlet extends HttpServlet {
@EJB
    private RoomFacadeLocal sfl;
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         List<Room> roomList = sfl.findAll();
        request.setAttribute("roomList", roomList);
        
        RequestDispatcher disp = request.getRequestDispatcher("manage_rooms_outcome.jsp");
        disp.forward(request, response);
    }
}
