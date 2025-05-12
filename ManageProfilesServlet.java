package za.ac.tut.web;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.ac.tut.bl.ProfileFacadeLocal;
import za.ac.tut.entities.Profile;

public class ManageProfilesServlet extends HttpServlet {
    @EJB
    private ProfileFacadeLocal pfl;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Profile> profileList = pfl.findAll();
        request.setAttribute("profileList", profileList);
        request.getRequestDispatcher("manage_profiles_outcome.jsp").forward(request, response);
    }
}