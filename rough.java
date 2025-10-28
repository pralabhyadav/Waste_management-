package waste_managment;

import java.util.Random;
import java.util.Scanner;
public class rough {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
      String phone;
        do {
        phone=sc.next();
       } while (!phone.matches("^[A-Za-z][A-Za-z0-9_.-@#$&]{2,50}$"));

        
System.out.println(phone);}

    
    }
    
    
    /* 
          System.out.println("                                                                                                           ");
             System.out.printf("% 10s % 20s % 20s % 20s % 10s%n", "WARD NO","AMOUNT_DRYWASTE"  , "AMOUNT_WET_WASTE", "percentage_of_dry","percentage_of_wet");
                 System.out.println("                                                                                                           ");
     
                 // Row data
                  while (r.next()) {
                     System.out.printf("% 10s % 20s % 20s % 20s % 10s%n",r.getInt("c.request_id"),r.getString("p.name"),r.getInt("p.ward_no"),r.getString("p.status"),r.getString("c.discription"));
                            
                 }
                System.out.println("                                                                                                           ");
*/

/*  public static void main(String[] args) {
    System.out.println("✅ PDF Report generated from database: ");
 }
/* 
System.out.println("                                                                                                                          ")

}citizen c =new citizen(conn, sc);
//c.citizen_driver();
tools1 t=new tools1(conn, sc);
t.avalivale_tools();
//t.all_request();
t.pending_request();
//t.checkStatus(200);
//t.tool_request(100);
//t.approve();
//t.submit(100);*/
    
   /* 
            import java.io.*;
import java.sql.*;

public class WasteReportDB {
    public static void main(String[] args) {
        // JDBC connection details
        String url = "jdbc:mysql://localhost:3306/waste_db"; // apna DB name
        String user = "root";  // apna DB user
        String pass = "root";  // apna DB password

        try (
            Connection con = DriverManager.getConnection(url, user, pass);
            PrintWriter pw = new PrintWriter(new FileWriter("WasteReport.txt"));
        ) {
            // Header
            pw.printf("%n======================================%n");
            pw.printf("        WASTE MANAGEMENT REPORT        %n");
            pw.printf("======================================%n%n");

            // Ward-wise Garbage Data
            pw.printf("%-10s %-15s %-15s%n", "Ward No", "Dry Waste(Kg)", "Wet Waste(Kg)");
            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT ward_no, dry_waste, wet_waste FROM garbage");
            while (rs1.next()) {
                pw.printf("%-10d %-15d %-15d%n",
                        rs1.getInt("ward_no"),
                        rs1.getInt("dry_waste"),
                        rs1.getInt("wet_waste"));
            }

            // Citizen Requests
            pw.printf("%nCITIZEN REQUESTS:%n");
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT location, garbage_amount FROM requests");
            while (rs2.next()) {
                pw.printf("Location: %-20s Garbage: %d Kg%n",
                        rs2.getString("location"),
                        rs2.getInt("garbage_amount"));
            }

            // Budget Details
            pw.printf("%nBUDGET DETAILS:%n");
            Statement st3 = con.createStatement();
            ResultSet rs3 = st3.executeQuery("SELECT worker_salary, supervisor_budget FROM budget");
            while (rs3.next()) {
                pw.printf("Worker Salary Budget : ₹%,d%n", rs3.getInt("worker_salary"));
                pw.printf("Supervisor Budget    : ₹%,d%n", rs3.getInt("supervisor_budget"));
            }

            // Tool Requests
            pw.printf("%nTOOL REQUESTS:%n");
            Statement st4 = con.createStatement();
            ResultSet rs4 = st4.executeQuery("SELECT tool_name, quantity FROM tools");
            while (rs4.next()) {
                pw.printf("%s : %d%n", rs4.getString("tool_name"), rs4.getInt("quantity"));
            }

            // Footer
            pw.printf("%n======================================%n");
            pw.printf("        END OF REPORT                  %n");
            pw.printf("======================================%n");

            pw.close();
            System.out.println(" WasteReport.txt generated with DB values!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}*/
//pw.printf(" ================><======================><=====================><=====================%n");


