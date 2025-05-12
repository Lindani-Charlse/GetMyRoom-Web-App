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

public class report2Servlet extends HttpServlet {
    @EJB
    private RoomFacadeLocal roomFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseName = request.getParameter("courseName");
        String export = request.getParameter("export");

        // Debug logging
        System.out.println("report2Servlet: courseName=" + courseName + ", export=" + export);

        List<Room> rooms = roomFacade.findAll();
        System.out.println("report2Servlet: Rooms found=" + rooms.size());

        List<ReportItem> reportData = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getStudent() != null && room.getStudent().getCourseName() != null) {
                String studentCourse = room.getStudent().getCourseName();
                if (courseName == null || courseName.isEmpty() || courseName.equals(studentCourse)) {
                    reportData.add(new ReportItem(
                        room.getRoomNo(),
                        room.getStudent().getStudentNo(),
                        room.getStudent().getFullNames(),
                        studentCourse
                    ));
                }
            }
        }
        System.out.println("report2Servlet: Report data size=" + reportData.size());

        if ("pdf".equals(export)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"room_occupancy_by_course.pdf\"");
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();
                document.add(new Paragraph("GetMyRoom - Room Occupancy by Course"));
                for (ReportItem item : reportData) {
                    document.add(new Paragraph(
                        "Room Number: " + item.roomNo + ", " +
                        "Student Number: " + item.studentNo + ", " +
                        "Full Names: " + item.fullNames + ", " +
                        "Course: " + item.courseName
                    ));
                }
                document.close();
            } catch (DocumentException e) {
                System.err.println("report2Servlet: PDF generation error - " + e.getMessage());
                throw new IOException("Error generating PDF: " + e.getMessage());
            }
        } else {
            request.setAttribute("reportData", reportData);
            request.getRequestDispatcher("report2_outcome.jsp").forward(request, response);
        }
    }

    public static class ReportItem {
        private final String roomNo;
        private final String studentNo;
        private final String fullNames;
        private final String courseName;

        public ReportItem(String roomNo, String studentNo, String fullNames, String courseName) {
            this.roomNo = roomNo;
            this.studentNo = studentNo;
            this.fullNames = fullNames;
            this.courseName = courseName;
        }

        public String getRoomNo() { return roomNo; }
        public String getStudentNo() { return studentNo; }
        public String getFullNames() { return fullNames; }
        public String getCourseName() { return courseName; }
    }
}