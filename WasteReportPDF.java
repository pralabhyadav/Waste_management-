package waste_managment;

import java.io.*;
import java.sql.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
 
public class WasteReportPDF {
    private static Connection conn;

    public WasteReportPDF(Connection conn) {
        this.conn = conn;
    }

    public static void generatePDFReport() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("WasteReport.pdf"));
            document.open();

            // Header
            Font titleFont = new Font(Font.FontFamily.UNDEFINED, 14, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL);
            
            Paragraph title = new Paragraph("|| WASTE MANAGEMENT REPORT ||", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            Paragraph subtitle1 = new Paragraph("NAGAR PALIKA PARISHAD KHATEGAON", headerFont);
            subtitle1.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle1);
            
            Paragraph subtitle2 = new Paragraph("KHATEGAON DIST- DEWAS(M.P) PINCODE-455336" , headerFont);
            subtitle2.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle2);
            document.add(new Paragraph(" "));

            // Ward-wise Garbage Data
            document.add(new Paragraph("  "));
            document.add(new Paragraph("WARD-WISE GARBAGE REPORT", titleFont));
             document.add(new Paragraph("  "));
            PdfPTable table1 = new PdfPTable(5);
            table1.setWidthPercentage(100);
            table1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell("Ward No");
            table1.addCell("Dry Waste");
            table1.addCell("Wet Waste");
            table1.addCell("Dry %");
            table1.addCell("Wet %");

            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT r.ward_no,SUM(p.amount_dry) AS AMOUNT_DRYWASTE,SUM(p.amount_wet) AS AMOUNT_WET_WASTE,100.0 * SUM(p.amount_dry) / NULLIF(SUM(p.amount_dry) + SUM(p.amount_wet), 0) AS percentage_of_dry,100.0 * SUM(p.amount_wet) / NULLIF(SUM(p.amount_dry) + SUM(p.amount_wet), 0) AS percentage_of_wet FROM pickup_discription p JOIN pickup_request r ON p.request_id = r.request_id GROUP BY r.ward_no");
            
            while (rs1.next()) {
                table1.addCell(String.valueOf(rs1.getInt("ward_no")));
                table1.addCell(String.valueOf(rs1.getInt("AMOUNT_DRYWASTE")));
                table1.addCell(String.valueOf(rs1.getInt("AMOUNT_WET_WASTE")));
                table1.addCell(String.valueOf(rs1.getInt("percentage_of_dry")));
                table1.addCell(String.valueOf(rs1.getInt("percentage_of_wet")));
            }
            document.add(table1);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Citizen Requests
            document.add(new Paragraph("CITIZEN REQUESTS", titleFont));
            document.add(new Paragraph("  "));
            PdfPTable table2 = new PdfPTable(6);
            table2.setWidthPercentage(100);
            table2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.addCell("Request ID");
            table2.addCell("Citizen ID");
            table2.addCell("Location");
            table2.addCell("Ward No");
            table2.addCell("Priority");
            table2.addCell("Status");

            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT request_id, citizen_id, location, ward_no, priority, status FROM pickup_request");
            
            while (rs2.next()) {
                table2.addCell(String.valueOf(rs2.getInt("request_id")));
                table2.addCell(String.valueOf(rs2.getInt("citizen_id")));
                table2.addCell(rs2.getString("location"));
                table2.addCell(String.valueOf(rs2.getInt("ward_no")));
                table2.addCell(String.valueOf(rs2.getInt("priority")));
                table2.addCell(rs2.getString("status"));
            }
            document.add(table2);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Budget details 
            document.add(new Paragraph("BUDGET", titleFont));
            document.add(new Paragraph("  "));
            PdfPTable table3 = new PdfPTable(4);
            table3.setWidthPercentage(100);
            table3.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table3.addCell("Supervisor ID");
            table3.addCell("Name");
            table3.addCell("Phone No");
            table3.addCell("Budget");

            Statement st3 = conn.createStatement();
            ResultSet rs3 = st3.executeQuery("SELECT * FROM supervisor");

            while (rs3.next()) {
                table3.addCell(String.valueOf(rs3.getInt("supervisor_id")));
                table3.addCell(rs3.getString("name"));
                table3.addCell(rs3.getString("phone"));
                table3.addCell(String.valueOf(rs3.getInt("budget")));
            }
            document.add(table3);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Tools request
            document.add(new Paragraph("TOOLS REQUESTS", titleFont));
            document.add(new Paragraph("  "));
            PdfPTable table4 = new PdfPTable(8);
            table4.setWidthPercentage(100);
            table4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table4.addCell("Tool Request ID");
            table4.addCell("Worker ID");
            table4.addCell("Trucks");
            table4.addCell("Sweeping Machine");
            table4.addCell("Dustbins");
            table4.addCell("Septic Tanker");
            table4.addCell("Water Tanker");
            table4.addCell("Status");

            Statement st4 = conn.createStatement();
            ResultSet rs4 = st4.executeQuery("SELECT * FROM tools_request");
            while (rs4.next()) {
                table4.addCell(String.valueOf(rs4.getInt("tool_request_id")));
                table4.addCell(String.valueOf(rs4.getInt("worker_id")));
                table4.addCell(String.valueOf(rs4.getInt("truckes")));
                table4.addCell(String.valueOf(rs4.getInt("sweeping_machine")));
                table4.addCell(String.valueOf(rs4.getInt("dustbins")));
                table4.addCell(String.valueOf(rs4.getInt("septic_tanker")));
                table4.addCell(String.valueOf(rs4.getInt("water_tanker")));
                table4.addCell(rs4.getString("status"));
            }
            document.add(table4);
            document.add(new Paragraph(" "));


            document.close();
            System.out.println("WasteReport.pdf generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}