package za.ac.tut.web;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.ac.tut.bl.ProfileFacadeLocal;
import za.ac.tut.bl.StudentFacadeLocal;
import za.ac.tut.entities.Profile;
import za.ac.tut.entities.Student;

public class EditProfileServlett extends HttpServlet {
    @EJB
    private ProfileFacadeLocal pfl;

    @EJB
    private StudentFacadeLocal studentFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String profileIdStr = request.getParameter("profileId");
        Long profileId;
        try {
            if (profileIdStr == null || profileIdStr.trim().isEmpty()) {
                throw new NumberFormatException("Profile ID is missing.");
            }
            profileId = Long.parseLong(profileIdStr);
        } catch (NumberFormatException e) {
            List<Profile> profileList = pfl.findAll();
            // Convert profilePic to Base64 for display
            for (Profile profile : profileList) {
                if (profile.getProfilePic() != null) {
                    profile.setProfilePicBase64(Base64.getEncoder().encodeToString(profile.getProfilePic()));
                }
            }
            request.setAttribute("profileList", profileList);
            request.setAttribute("message", "Invalid profile ID: " + (profileIdStr != null ? profileIdStr : "none provided"));
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("manage_profiles_outcome.jsp").forward(request, response);
            return;
        }

        Profile profile = pfl.find(profileId);
        if (profile != null) {
            // Convert profilePic to Base64 for display
            if (profile.getProfilePic() != null) {
                profile.setProfilePicBase64(Base64.getEncoder().encodeToString(profile.getProfilePic()));
            }
            // Clear password to avoid exposing it in the form
            profile.setPassword(null);
            request.setAttribute("profile", profile);
            request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
        } else {
            List<Profile> profileList = pfl.findAll();
            for (Profile p : profileList) {
                if (p.getProfilePic() != null) {
                    p.setProfilePicBase64(Base64.getEncoder().encodeToString(p.getProfilePic()));
                }
            }
            request.setAttribute("profileList", profileList);
            request.setAttribute("message", "Profile with ID " + profileId + " not found.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("manage_profiles_outcome.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String profileIdStr = request.getParameter("profileId");
        String password = request.getParameter("password");
        String fundingType = request.getParameter("fundingType");
        String studentNo = request.getParameter("studentNo");

        // Input validation
        String errorMessage = null;
        Long profileId = null;
        try {
            if (profileIdStr == null || profileIdStr.trim().isEmpty()) {
                throw new NumberFormatException("Profile ID is missing.");
            }
            profileId = Long.parseLong(profileIdStr);
        } catch (NumberFormatException e) {
            errorMessage = "Invalid profile ID: " + (profileIdStr != null ? profileIdStr : "none provided");
        }

        if (errorMessage == null) {
            if (password != null && !password.trim().isEmpty() && password.length() > 15) {
                errorMessage = "Password must not exceed 15 characters.";
            } else if (fundingType != null && fundingType.length() > 50) {
                errorMessage = "Funding type must not exceed 50 characters.";
            }
        }

        if (errorMessage != null) {
            List<Profile> profileList = pfl.findAll();
            for (Profile profile : profileList) {
                if (profile.getProfilePic() != null) {
                    profile.setProfilePicBase64(Base64.getEncoder().encodeToString(profile.getProfilePic()));
                }
            }
            request.setAttribute("profileList", profileList);
            request.setAttribute("message", errorMessage);
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("manage_profiles_outcome.jsp").forward(request, response);
            return;
        }

        Profile profile = pfl.find(profileId);
        if (profile != null) {
            try {
                // Update profile details
                if (password != null && !password.trim().isEmpty()) {
                    profile.setPassword(password); // Store as plaintext due to VARCHAR(15) constraint
                } // Else, keep existing password
                profile.setFundingType(fundingType != null && !fundingType.trim().isEmpty() ? fundingType : null);

                // Handle student association
                if (studentNo != null && !studentNo.trim().isEmpty()) {
                    Student student = studentFacade.find(studentNo);
                    if (student != null) {
                        profile.setStudent(student);
                    } else {
                        List<Profile> profileList = pfl.findAll();
                        for (Profile p : profileList) {
                            if (p.getProfilePic() != null) {
                                p.setProfilePicBase64(Base64.getEncoder().encodeToString(p.getProfilePic()));
                            }
                        }
                        request.setAttribute("profileList", profileList);
                        request.setAttribute("message", "Student with number " + studentNo + " not found.");
                        request.setAttribute("messageType", "error");
                        request.getRequestDispatcher("manage_profiles_outcome.jsp").forward(request, response);
                        return;
                    }
                } else {
                    profile.setStudent(null);
                }

                pfl.edit(profile);
                List<Profile> profileList = pfl.findAll();
                for (Profile p : profileList) {
                    if (p.getProfilePic() != null) {
                        p.setProfilePicBase64(Base64.getEncoder().encodeToString(p.getProfilePic()));
                    }
                }
                request.setAttribute("profileList", profileList);
                request.setAttribute("message", "Profile with ID " + profileId + " PATCHED SUCCESSFULLY.");
                request.setAttribute("messageType", "success");
            } catch (Exception e) {
                List<Profile> profileList = pfl.findAll();
                for (Profile p : profileList) {
                    if (p.getProfilePic() != null) {
                        p.setProfilePicBase64(Base64.getEncoder().encodeToString(p.getProfilePic()));
                    }
                }
                request.setAttribute("profileList", profileList);
                request.setAttribute("message", "Failed to update profile with ID " + profileId + ": " + e.getMessage());
                request.setAttribute("messageType", "error");
            }
        } else {
            List<Profile> profileList = pfl.findAll();
            for (Profile p : profileList) {
                if (p.getProfilePic() != null) {
                    p.setProfilePicBase64(Base64.getEncoder().encodeToString(p.getProfilePic()));
                }
            }
            request.setAttribute("profileList", profileList);
            request.setAttribute("message", "Profile with ID " + profileId + " not found.");
            request.setAttribute("messageType", "error");
        }

        request.getRequestDispatcher("manage_profiles_outcome.jsp").forward(request, response);
    }
}