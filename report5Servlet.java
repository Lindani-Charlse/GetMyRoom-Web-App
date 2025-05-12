package za.ac.tut.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import za.ac.tut.bl.StudentFacadeLocal;
import za.ac.tut.entities.Student;

public class report5Servlet extends HttpServlet {
    @EJB
    private StudentFacadeLocal studentFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String export = request.getParameter("export");

        // Debug logging
        System.out.println("report5Servlet: startDate=" + startDateStr + ", endDate=" + endDateStr + ", export=" + export);

        // Initialize variables
        List<ReportItem> reportData = new ArrayList<>();
        String message = null;
        String messageType = null;

        try {
            // Parse dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = startDateStr != null && !startDateStr.isEmpty() ? dateFormat.parse(startDateStr) : null;
            Date endDate = endDateStr != null && !endDateStr.isEmpty() ? dateFormat.parse(endDateStr) : null;
            Date currentDate = new Date();

            // Validate end date
            if (endDate != null && endDate.after(currentDate)) {
                message = "End date cannot be after the current date (May 08, 2025).";
                messageType = "error";
            } else if (startDate != null && endDate != null && startDate.after(endDate)) {
                message = "Start date cannot be after end date.";
                messageType = "error";
            } else if (startDate != null && endDate != null) {
                // Query students
                List<Student> students = studentFacade.findAll();
                System.out.println("report5Servlet: Students found=" + students.size());

                for (Student student : students) {
                    if (student.getRegisteredDate() != null) {
                        Date regDate = new Date(student.getRegisteredDate().getTime());
                        if (!regDate.before(startDate) && !regDate.after(endDate)) {
                            reportData.add(new ReportItem(
                                student.getStudentNo(),
                                student.getFullNames(),
                                dateFormat.format(regDate)
                            ));
                        }
                    }
                }
                System.out.println("report5Servlet: Report data size=" + reportData.size());
            }
        } catch (Exception e) {
            System.err.println("report5Servlet: Error parsing dates - " + e.getMessage());
            message = "Invalid date format. Please use YYYY-MM-DD.";
            messageType = "error";
        }

        if ("pdf".equals(export) && message == null && !reportData.isEmpty()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"student_registrations_by_date_range.pdf\"");
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();
                document.add(new Paragraph("GetMyRoom - Student Registrations by Date Range"));
                for (ReportItem item : reportData) {
                    document.add(new Paragraph(
                        "Student Number: " + item.studentNo + ", " +
                        "Full Names: " + item.fullNames + ", " +
                        "Registered Date: " + item.registeredDate
                    ));
                }
                document.close();
            } catch (DocumentException e) {
                System.err.println("report5Servlet: PDF generation error - " + e.getMessage());
                throw new IOException("Error generating PDF: " + e.getMessage());
            }
        } else {
            request.setAttribute("reportData", reportData);
            request.setAttribute("message", message);
            request.setAttribute("messageType", messageType);
            request.setAttribute("startDate", startDateStr);
            request.setAttribute("endDate", endDateStr);
            request.getRequestDispatcher("report5_outcome.jsp").forward(request, response);
        }
    }

    public static class ReportItem {
        private final String studentNo;
        private final String fullNames;
        private final String registeredDate;

        public ReportItem(String studentNo, String fullNames, String registeredDate) {
            this.studentNo = studentNo;
            this.fullNames = fullNames;
            this.registeredDate = registeredDate;
        }

        public String getStudentNo() { return studentNo; }
        public String getFullNames() { return fullNames; }
        public String getRegisteredDate() { return registeredDate; }
    }
}
