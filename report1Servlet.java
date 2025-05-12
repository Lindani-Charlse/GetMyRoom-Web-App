package za.ac.tut.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import za.ac.tut.bl.RoomFacadeLocal;
import za.ac.tut.entities.Room;

public class report1Servlet extends HttpServlet {
    @EJB
    private RoomFacadeLocal roomFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String gender = request.getParameter("gender");
        String export = request.getParameter("export");

        // Debug logging
        System.out.println("report1Servlet: gender=" + gender + ", export=" + export);

        List<Room> rooms = roomFacade.findAll();
        System.out.println("report1Servlet: Rooms found=" + rooms.size());

        List<ReportItem> reportData = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getStudent() != null && room.getStudent().getGender() != null) {
                Character studentGender = room.getStudent().getGender();
                if (gender == null || gender.isEmpty() || gender.equals(studentGender.toString())) {
                    reportData.add(new ReportItem(
                        room.getRoomNo(),
                        room.getStudent().getStudentNo(),
                        room.getStudent().getFullNames(),
                        studentGender.toString()
                    ));
                }
            }
        }
        System.out.println("report1Servlet: Report data size=" + reportData.size());

        if ("pdf".equals(export)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"room_occupancy_by_gender.pdf\"");
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();
                document.add(new Paragraph("GetMyRoom - Room Occupancy by Gender"));
                for (ReportItem item : reportData) {
                    document.add(new Paragraph(
                        "Room Number: " + item.roomNo + ", " +
                        "Student Number: " + item.studentNo + ", " +
                        "Full Names: " + item.fullNames + ", " +
                        "Gender: " + item.gender
                    ));
                }
                document.close();
            } catch (DocumentException e) {
                System.err.println("report1Servlet: PDF generation error - " + e.getMessage());
                throw new IOException("Error generating PDF: " + e.getMessage());
            }
        } else {
            request.setAttribute("reportData", reportData);
            request.getRequestDispatcher("report1_outcome.jsp").forward(request, response);
        }
    }

    public static class ReportItem {
        private final String roomNo;
        private final String studentNo;
        private final String fullNames;
        private final String gender;

        public ReportItem(String roomNo, String studentNo, String fullNames, String gender) {
            this.roomNo = roomNo;
            this.studentNo = studentNo;
            this.fullNames = fullNames;
            this.gender = gender;
        }

        public String getRoomNo() { return roomNo; }
        public String getStudentNo() { return studentNo; }
        public String getFullNames() { return fullNames; }
        public String getGender() { return gender; }
    }
}