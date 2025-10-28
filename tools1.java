package waste_managment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import javax.lang.model.util.SimpleAnnotationValueVisitor7;

public class tools1 {

    private static Connection conn;
    private static Scanner sc;

    public tools1(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    public static void tool_request(int worker_id) {
        // worker_id, truckes, sweeping_machine, dustbins, septic_tanker, water_tanker,
        // status;
        validation v = new validation();
        String truckes, sweeping_machine, dustbins, septic_tanker, water_tanker;
        /*
         * +do {
         * 
         * } while (v.checkavailable(conn,"overall_tools"," ",));
         */

        do {
            System.out.print("==> ENTER NUMBER OF TRUKS         ::  ");
            truckes = sc.next();
            if (truckes.equalsIgnoreCase("back")) {
                return;
            }
        } while (v.checkavailable(conn, "overall_tools", "truckes", truckes));
        do {
            System.out.print("==> ENTER NUMBER OF SWEEPING_M    ::  ");
            sweeping_machine = sc.next();
            if (sweeping_machine.equalsIgnoreCase("back")) {
                return;
            }
        } while (v.checkavailable(conn, "overall_tools", "sweeping_machine", sweeping_machine));
        do {
            System.out.print("==> ENTER NUMBER OF dustbins      ::  ");
            dustbins = sc.next();
            if (dustbins.equalsIgnoreCase("back")) {
                return;
            }
        } while (v.checkavailable(conn, "overall_tools", "dustbins", dustbins));
        do {
            System.out.print("==> ENTER NUMBER OF septic_tanker ::  ");
            septic_tanker = sc.next();
            if (septic_tanker.equalsIgnoreCase("back")) {
                return;
            }
        } while (v.checkavailable(conn, "overall_tools", "septic_tanker", septic_tanker));
        do {
            System.out.print("==> ENTER NUMBER OF water_tanker  ::  ");
            water_tanker = sc.next();
            if (water_tanker.equalsIgnoreCase("back")) {
                return;
            }
        } while (v.checkavailable(conn, "overall_tools", "water_tanker", water_tanker));

        int struckes = Integer.parseInt(truckes);
        int ssweeping_machine = Integer.parseInt(sweeping_machine);
        int sdustbins = Integer.parseInt(dustbins);
        // int s= Integer.parseInt(septic_tanker);
        int sseptic_tanker = Integer.parseInt(septic_tanker);
        int swater_tanker = Integer.parseInt(water_tanker);
        if (struckes > 0 || ssweeping_machine > 0 || sdustbins > 0 || sseptic_tanker > 0 || swater_tanker > 0) {

            String query = "insert into tools_request( worker_id, truckes, sweeping_machine, dustbins, septic_tanker, water_tanker) values (?,?,?,?,?,?)";
            try {

                PreparedStatement pp = conn.prepareStatement(query);
                pp.setInt(1, worker_id);
                pp.setInt(2, struckes);
                pp.setInt(3, ssweeping_machine);
                pp.setInt(4, sdustbins);
                pp.setInt(5, sseptic_tanker);
                pp.setInt(6, swater_tanker);
                int a = pp.executeUpdate();
                if (a > 0) {
                    System.out.println();
                    System.out.println(
                            "        YOUR REQUEST HAS BEEN SUCCESSFULLY SENT TO ADMIN  !! ADMIN WILL  APPROVE IT   ");
                    System.out.println();
                }

            }

            catch (Exception e) {
                System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
            }
        } else {
            System.out.println();
            System.out.println("             REQUEST FAILED !! AS YOU ARE TRYING TO ENTER ALL VALUE AS 0 ");
            System.out.println();
        }
    }

    public static void checkStatus(int worker_id) {
        String query = "select * from tools_request where worker_id=?";
        try {
            PreparedStatement p = conn.prepareStatement(query);
            p.setInt(1, worker_id);
            ResultSet r = p.executeQuery();
            ResultSetMetaData rsmd = r.getMetaData();
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n", rsmd.getColumnName(1),
                    rsmd.getColumnName(2), rsmd.getColumnName(3), rsmd.getColumnName(4), rsmd.getColumnName(5),
                    rsmd.getColumnName(6), rsmd.getColumnName(7), rsmd.getColumnName(8));
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------");

            // Row data
            while (r.next()) {
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n", r.getInt(rsmd.getColumnName(1)),
                        r.getInt(rsmd.getColumnName(2)), r.getInt(rsmd.getColumnName(3)),
                        r.getInt(rsmd.getColumnName(4)), r.getInt(rsmd.getColumnName(5)),
                        r.getInt(rsmd.getColumnName(6)), r.getInt(rsmd.getColumnName(7)),
                        r.getString(rsmd.getColumnName(8)));

            }
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");

        }

    }

    public static void pending_request() {
        String query = "select * from tools_request where status='pending'";
        try {
            PreparedStatement p = conn.prepareStatement(query);

            ResultSet r = p.executeQuery();
            ResultSetMetaData rsmd = r.getMetaData();

            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n", rsmd.getColumnName(1),
                    rsmd.getColumnName(2), rsmd.getColumnName(3), rsmd.getColumnName(4), rsmd.getColumnName(5),
                    rsmd.getColumnName(6), rsmd.getColumnName(7), rsmd.getColumnName(8));
            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------");

            // Row data
            while (r.next()) {
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n", r.getInt(rsmd.getColumnName(1)),
                        r.getInt(rsmd.getColumnName(2)), r.getInt(rsmd.getColumnName(3)),
                        r.getInt(rsmd.getColumnName(4)), r.getInt(rsmd.getColumnName(5)),
                        r.getInt(rsmd.getColumnName(6)), r.getInt(rsmd.getColumnName(7)),
                        r.getString(rsmd.getColumnName(8)));

            }
            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
        }

    }

    public static void all_request() {
        String query = "select * from tools_request order by status";
        try {
            PreparedStatement p = conn.prepareStatement(query);

            ResultSet r = p.executeQuery();
            ResultSetMetaData rsmd = r.getMetaData();
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n", rsmd.getColumnName(1),
                    rsmd.getColumnName(2), rsmd.getColumnName(3), rsmd.getColumnName(4), rsmd.getColumnName(5),
                    rsmd.getColumnName(6), rsmd.getColumnName(7), rsmd.getColumnName(8));
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------");

            // Row data
            while (r.next()) {
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n", r.getInt(rsmd.getColumnName(1)),
                        r.getInt(rsmd.getColumnName(2)), r.getInt(rsmd.getColumnName(3)),
                        r.getInt(rsmd.getColumnName(4)), r.getInt(rsmd.getColumnName(5)),
                        r.getInt(rsmd.getColumnName(6)), r.getInt(rsmd.getColumnName(7)),
                        r.getString(rsmd.getColumnName(8)));

            }
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
        }

    }

    public static void avalivale_tools() {
        String query = "select * from overall_tools ";
        try {
            PreparedStatement p = conn.prepareStatement(query);

            ResultSet r = p.executeQuery();
            ResultSetMetaData rsmd = r.getMetaData();
            System.out.println();
            System.out.println();
            System.out.println(
                    "                                         ||  AVAILBLE TOOLS  ||                                            ");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------");
            System.out.printf(" %-20s %-20s %-20s %-20s %-20s  %n", rsmd.getColumnName(1), rsmd.getColumnName(2),
                    rsmd.getColumnName(3), rsmd.getColumnName(4), rsmd.getColumnName(5));
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------");

            // Row data
            while (r.next()) {
                System.out.printf(" %-22s %-22s %-22s %-22s %-22s  %n", r.getInt(rsmd.getColumnName(1)),
                        r.getInt(rsmd.getColumnName(2)), r.getInt(rsmd.getColumnName(3)),
                        r.getInt(rsmd.getColumnName(4)), r.getInt(rsmd.getColumnName(5)));

            }
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
        }

    }

    public static void approve() {
        validation v = new validation();

        pending_request();
        String a;
        // SELECT tool_request_id, worker_id, truckes, sweeping_machine, dustbins,
        // septic_tanker, water_tanker, status FROM waste_management.tools_request;
        do {
            System.out.println("ENTER REQUEST_ID :: ");
            a = sc.next();
            if (a.equalsIgnoreCase("back")) {
                return;
            }
        } while (!(a.matches("^[0-9]+$")) || v.isUnique(conn, "tools_request", "tool_request_id", a));
        String query = "SELECT   truckes, sweeping_machine, dustbins, septic_tanker, water_tanker, status FROM waste_management.tools_request where tool_request_id=? and status='pending'";

        int truckes = 0, sweeping_machine = 0, dustbins = 0, septic_tanker = 0, water_tanker = 0;
        int ftruks = 0, fsweeping = 0, fdustbins = 0, fseptic = 0, fwater = 0;

        int aint1 = Integer.parseInt(a);
        try {
            PreparedStatement pa = conn.prepareStatement(query);
            pa.setInt(1, aint1);
            ResultSet r = pa.executeQuery();
            while (r.next()) {
                truckes = r.getInt("truckes");
                sweeping_machine = r.getInt("sweeping_machine");
                dustbins = r.getInt("dustbins");
                septic_tanker = r.getInt("septic_tanker");
                water_tanker = r.getInt("water_tanker");

            }

            String query1 = "select * from overall_tools ";
            PreparedStatement p = conn.prepareStatement(query1);
            ResultSet r1 = p.executeQuery();
            while (r1.next()) {
                // truckes, sweeping_machine, dustbins, septic_tanker, water_tanker
                ftruks = r1.getInt("truckes") - truckes;
                fsweeping = r1.getInt("sweeping_machine") - sweeping_machine;
                fdustbins = r1.getInt("dustbins") - dustbins;
                fseptic = r1.getInt("septic_tanker") - septic_tanker;
                fwater = r1.getInt("water_tanker") - water_tanker;
            }

            String query2 = "update  waste_management.overall_tools set truckes=?, sweeping_machine=?, dustbins=?, septic_tanker=?, water_tanker=?";
            PreparedStatement pu = conn.prepareStatement(query2);

            if (ftruks >= 0)
                pu.setInt(1, ftruks);
            if (fsweeping >= 0)
                pu.setInt(2, fsweeping);
            if (fdustbins >= 0)
                pu.setInt(3, fdustbins);
            if (fseptic >= 0)
                pu.setInt(4, fseptic);
            if (fwater >= 0)
                pu.setInt(5, fwater);
            int result = pu.executeUpdate();
            if (result > 0) {
                String queryupdate = "update tools_request set status='approve' where tool_request_id=?";
                PreparedStatement pu2 = conn.prepareStatement(queryupdate);
                pu2.setInt(1, aint1);
                int result1 = pu2.executeUpdate();
                if (result1 > 0) {
                    System.out.println("------------>> APPROVE SUCCESSFULLY ");
                }

            }

        } catch (Exception e) {
            System.out.println("NOT AVALABLE WAIT FOR OTHER'S SUBMISSION ");
        }

    }

    public static void submit(int worker_id) {
        validation v = new validation();
        all_request();
        String a;
        // SELECT tool_request_id, worker_id, truckes, sweeping_machine, dustbins,
        // septic_tanker, water_tanker, status FROM waste_management.tools_request;
        do {
            System.out.println("ENTER REQUEST_ID :: ");
            a = sc.next();
            if (a.equalsIgnoreCase("back")) {
                return;
            }
        } while (!(a.matches("^[0-9]+$")) || v.isUnique(conn, "tools_request", "tool_request_id", a));

        String query = "SELECT   truckes, sweeping_machine, dustbins, septic_tanker, water_tanker, status FROM waste_management.tools_request where tool_request_id=? and status='approve' and worker_id=?";
        int truckes = 0, sweeping_machine = 0, dustbins = 0, septic_tanker = 0, water_tanker = 0;
        int ftruks = 0, fsweeping = 0, fdustbins = 0, fseptic = 0, fwater = 0;
        try {
            int aint = Integer.parseInt(a);
            PreparedStatement pa = conn.prepareStatement(query);
            pa.setInt(1, aint);
            pa.setInt(2, worker_id);
            ResultSet r = pa.executeQuery();
            while (r.next()) {
                truckes = r.getInt("truckes");
                sweeping_machine = r.getInt("sweeping_machine");
                dustbins = r.getInt("dustbins");
                septic_tanker = r.getInt("septic_tanker");
                water_tanker = r.getInt("water_tanker");

            }
            if (truckes > 0 || sweeping_machine > 0 || dustbins > 0 || septic_tanker > 0 || water_tanker > 0) {

                String query1 = "select * from overall_tools ";
                PreparedStatement p = conn.prepareStatement(query1);
                ResultSet r1 = p.executeQuery();
                while (r1.next()) {
                    // truckes, sweeping_machine, dustbins, septic_tanker, water_tanker
                    ftruks = r1.getInt("truckes") + truckes;
                    fsweeping = r1.getInt("sweeping_machine") + sweeping_machine;
                    fdustbins = r1.getInt("dustbins") + dustbins;
                    fseptic = r1.getInt("septic_tanker") + septic_tanker;
                    fwater = r1.getInt("water_tanker") + water_tanker;
                }

                if (ftruks >= 0 && fsweeping >= 0 && fdustbins >= 0 && fseptic >= 0 && fwater >= 0) {
                    String query2 = "update  waste_management.overall_tools set truckes=?, sweeping_machine=?, dustbins=?, septic_tanker=?, water_tanker=?";
                    PreparedStatement pu = conn.prepareStatement(query2);
                    pu.setInt(1, ftruks);
                    pu.setInt(2, fsweeping);
                    pu.setInt(3, fdustbins);
                    pu.setInt(4, fseptic);
                    pu.setInt(5, fwater);
                    int result = pu.executeUpdate();
                    if (result > 0) {
                        String queryupdate = "update tools_request set status='submit' where tool_request_id=?";
                        PreparedStatement pu2 = conn.prepareStatement(queryupdate);
                        pu2.setInt(1, aint);
                        int result1 = pu2.executeUpdate();
                        if (result1 > 0) {
                            System.out.println("------------>> SUBMIT SUCCESSFULLY ");
                        }

                    }
                } else {
                    System.out.println();
                    System.out.println("======>     NOT SUBMIT ");
                    System.out.println();
                }
            } else {
                System.out.println("========> SUBIMISSION FAILED  ");
                System.out.println("IT MIGHT ALREADY SUBMITED OR VALUE PROBLEM ");
                System.out.println("             OR        ");
                System.out.println(" IT IS NOT YOUR REQEST ID ");
            }

        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");

        }

    }

    public static void main(String[] args) {

    }
}
