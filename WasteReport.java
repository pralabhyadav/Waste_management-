package waste_managment;

import java.io.*;
import java.sql.*;

public class WasteReport {

    private static Connection conn;

    public WasteReport(Connection conn) {
        this.conn = conn;
    }

    public static void report() {
        // JDBC connection details

        try {

            PrintWriter pw = new PrintWriter(new FileWriter("WasteReport.txt"));

            // Header

            pw.printf(
                    " ==================================> || WASTE MANAGEMENT REPORT   ||    <=================================%n");
            pw.printf("                                                %n%n");
            pw.printf("                                  NAGAR PALIKA PARISHAD KHATEGAON              %n");
            pw.printf("                              KHATEGAON DIST- DEWAS(M.P)  PINCODE-455336         %n%n");
            pw.printf("                                                %n%n");
            pw.printf("                                                %n%n");
            pw.printf("%n                           WARD-WISE GARBAGE REPORT         %n%n");

            // Ward-wise Garbage Data
            pw.printf("%-15s %-15s %-15s %-15s %-15s %n", "Ward No", "AMOUNT_DRYWASTE", "AMOUNT_WET_WASTE",
                    "percentage_of_dry", "percentage_of_wet");
            pw.printf("%n", "  ");
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(
                    "SELECT r.ward_no,SUM(p.amount_dry) AS AMOUNT_DRYWASTE,SUM(p.amount_wet) AS AMOUNT_WET_WASTE,100.0 * SUM(p.amount_dry) / NULLIF(SUM(p.amount_dry) + SUM(p.amount_wet), 0) AS percentage_of_dry,100.0 * SUM(p.amount_wet) / NULLIF(SUM(p.amount_dry) + SUM(p.amount_wet), 0) AS percentage_of_wet FROM pickup_discription p jOIN pickup_request r  ON p.request_id = r.request_id  GROUP BY r.ward_no ");
            while (rs1.next()) {
                pw.printf("%-15s %-15s %-15s %-15s %-15s %n",
                        rs1.getInt("ward_no"),
                        rs1.getInt("AMOUNT_DRYWASTE"),
                        rs1.getInt("AMOUNT_WET_WASTE"),
                        rs1.getInt("percentage_of_dry"),
                        rs1.getInt("percentage_of_wet"));

            }
            pw.printf("%n", " ");
            pw.printf("%n", " ");
            pw.printf(
                    " =====================><===========================><=========================><=========================%n");
            pw.printf("%n", " ");

            pw.printf("%n                                   CITIZEN REQUESTS        %n%n");

            // Citizen Requests
            pw.printf("%nCITIZEN REQUESTS:%n");

            pw.printf("%-13s %-13s %-13s %-13s %-13s %-13s %n", "request_id", "citizen_id", "location", "ward_no",
                    "priority", "status");
            pw.printf("%n", " ");
            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery(
                    " select request_id, citizen_id, location, ward_no, waste_type, priority, request_date, status from pickup_request ");
            while (rs2.next()) {
                // request_id, citizen_id, location, ward_no, waste_type, priority,
                // request_date, status
                pw.printf("%-13s %-13s %-13s %-13s %-13s %-13s %n",
                        rs2.getInt("request_id"),
                        rs2.getInt("citizen_id"),
                        rs2.getString("location"),
                        rs2.getInt("ward_no"),
                        rs2.getInt("priority"),
                        rs2.getString("status")

                );
            }

            pw.printf("%n", " ");
            pw.printf("%n", " ");
            pw.printf(
                    " =====================><===========================><=========================><=========================%n");
            pw.printf("%n", " ");
            pw.printf("%n                                   BUDGET DETAILS        %n%n");

            // Budget Details

            pw.printf("%-15s %-15s %-15s %-15s %n", "supervisor_id", "name", "phone_no", "budget");
            pw.printf("%n", " ");
            Statement st3 = conn.createStatement();
            ResultSet rs3 = st3.executeQuery("SELECT * FROM supervisor");
            while (rs3.next()) {
                pw.printf("%-15s %-15s %-15s %-15s %n",
                        rs3.getInt("supervisor_id"),
                        rs3.getString("name"),
                        rs3.getString("phone"),
                        rs3.getInt("budget")

                );
            }
            pw.printf("%n", " ");
            pw.printf("%n", " ");
            pw.printf(
                    " =====================><===========================><=========================><=========================%n");
            pw.printf("%n", " ");

            // Tool Requests
            pw.printf("%n                                    TOOLS REQUESTS     %n");
            pw.printf("%n", "    ");
            pw.printf("%-13s %-13s %-13s %-13s %-13s %-13s %-13s %-13s %n", "tool_request_id", "worker_id", "truckes",
                    "sweeping_machine", "dustbins", "septic_tanker", "water_tanker", "status");
            pw.printf("%n", "    ");
            Statement st4 = conn.createStatement();
            ResultSet rs4 = st4.executeQuery("SELECT * FROM waste_management.tools_request");
            while (rs4.next()) {
                pw.printf("%-13s %-13s %-13s %-13s %-13s %-13s %-13s %-13s %n", rs4.getInt("tool_request_id"),
                        rs4.getInt("worker_id"), rs4.getInt("truckes"), rs4.getInt("sweeping_machine"),
                        rs4.getInt("dustbins"), rs4.getInt("septic_tanker"), rs4.getInt("water_tanker"),
                        rs4.getString("status"));
            }
            pw.printf("%n", " ");
            pw.printf("%n", " ");
            // Footer

            pw.printf(
                    " ===================================> ||      END OF REPORT     ||    <====================================%n");

            pw.close();
            System.out.println();

            System.out.println(" WasteReport.txt generated sucessfully !");
            System.out.println();
        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
        }
    }
}
