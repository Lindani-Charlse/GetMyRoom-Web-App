package za.ac.tut.web;

import java.io.IOException;
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

public class summaryReport extends HttpServlet {
    @EJB
    private RoomFacadeLocal roomFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String export = request.getParameter("export");

        List<Room> rooms = roomFacade.findAll();
        int totalRooms = rooms.size();
        int occupiedRooms = 0;
        for (Room room : rooms) {
            if (room.getStudent() != null) {
                occupiedRooms++;
            }
        }
        int availableRooms = totalRooms - occupiedRooms;
        double occupancyRate = totalRooms > 0 ? (occupiedRooms * 100.0 / totalRooms) : 0;

        if ("pdf".equals(export)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"summary_report.pdf\"");
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();
                document.add(new Paragraph("GetMyRoom - Summary Report"));
                document.add(new Paragraph("Total Rooms: " + totalRooms));
                document.add(new Paragraph("Occupied Rooms: " + occupiedRooms));
                document.add(new Paragraph("Available Rooms: " + availableRooms));
                document.add(new Paragraph("Occupancy Rate: " + String.format("%.2f%%", occupancyRate)));
                document.close();
            } catch (DocumentException e) {
                throw new IOException("Error generating PDF: " + e.getMessage());
            }
        } else {
            request.setAttribute("summary", new Summary(totalRooms, occupiedRooms, availableRooms, occupancyRate));
            request.getRequestDispatcher("summary_report.jsp").forward(request, response);
        }
    }

    public static class Summary {
        private final int totalRooms;
        private final int occupiedRooms;
        private final int availableRooms;
        private final double occupancyRate;

        public Summary(int totalRooms, int occupiedRooms, int availableRooms, double occupancyRate) {
            this.totalRooms = totalRooms;
            this.occupiedRooms = occupiedRooms;
            this.availableRooms = availableRooms;
            this.occupancyRate = occupancyRate;
        }

        public int getTotalRooms() { return totalRooms; }
        public int getOccupiedRooms() { return occupiedRooms; }
        public int getAvailableRooms() { return availableRooms; }
        public double getOccupancyRate() { return occupancyRate; }
    }
}