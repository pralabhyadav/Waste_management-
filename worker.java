package waste_managment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class worker {
    private static Connection conn;
    private static Scanner sc;

    public worker(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    public void worker_driver() {
        validation v = new validation();
        tools1 t = new tools1(conn, sc);

        System.out.println(" ===>  PLEASE LOGIN FOR FURTHER PROCESS <===");
        System.out.println();

        String user;
        do {
            System.out.println("==>  ENTER  USERNAME  :: ");
            user = sc.next();
            if (user.equalsIgnoreCase("back")) {
                return;
            }
        } while (v.isUniqueString(conn, "login", "username", user) || !v.isValidStringuser(user));

        System.out.println("==> ENTER  PASSWORD  :: ");
        String pasword = sc.next();

        String query1 = "select ID,password from login where username=? and role ='worker'";
        String pass = null;
        int registration_id1 = 0;
        try {
            PreparedStatement ps1 = conn.prepareStatement(query1);
            ps1.setString(1, user);

            ResultSet rs = ps1.executeQuery();

            while (rs.next()) {
                registration_id1 = rs.getInt("ID");
                pass = rs.getString("password");
            }

        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
        }
        if (pasword.equals(pass)) {

            boolean loop = true;

            do {
                System.out.println("ENTER SPECIFIC NUMBER FOR PERFORMING TASK ");
                System.out.println();
                System.out.println("PRESS (1) FOR VIEW TASK  ");
                System.out.println("PRESS (2) FOR COMPLETING TASK");
                System.out.println("PRESS (3) FOR APPEAL TOOL ");
                System.out.println("PRESS (4) FOR CHECK STATUS ");
                System.out.println("PRESS (5) FOR SUBMIT TOOL ");
                System.out.println("PRESS (6) FOR AVAILABLE TOOL ");
                System.out.println("PRESS (7) BACK (<------) ");
                System.out.println();
                String op = null;
                int choice1;
                do {

                    System.out.print("ENTER OPTION NUMBER :: ");
                    op = sc.next();
                } while (!op.matches("[1-7]"));

                choice1 = Integer.parseInt(op);

                switch (choice1) {
                    case 1:
                        view_task(registration_id1);
                        break;
                    case 2:
                        update_request(registration_id1);
                        break;
                    case 3:
                        System.out.println(registration_id1);
                        t.tool_request(registration_id1);
                        break;
                    case 4:
                        t.checkStatus(registration_id1);
                        break;
                    case 5:
                        t.submit(registration_id1);
                        break;
                    case 6:
                        t.avalivale_tools();
                        break;
                    case 7:
                        loop = false;
                        break;
                    default:
                        System.out.println(" invalid choice ");
                        break;
                }
            } while (loop);
        } else {
            System.out.println("INCORRECT PASSWORD");
            System.out.println("/ OR YOU ARE NOT A WORKER ");
            System.out.println("LOGIN FAILED !! PLEASE TRY AGAIN  ");
        }

    }

    public void view_task(int worker_id) {

        String query = " select request_id, citizen_id, location, ward_no, waste_type, priority, request_date, status from pickup_request where  worker_id= ? AND status = 'pending'; ";
        // System.out.println(" Enter worker id :: ");
        // int worker_id =sc.nextInt();
        try {
            PreparedStatement ps4 = conn.prepareStatement(query);
            ps4.setInt(1, worker_id);
            ResultSet r = ps4.executeQuery();
            ResultSetMetaData cd = r.getMetaData();
            int count = cd.getColumnCount();

            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(
                    "|request_id  |   citizen_id   |       location         |   ward_no   |   waste_type     |  priority |        request_date         |    status ");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------");
            while (r.next()) {

                System.out.println();
                for (int i = 1; i <= count; i++) {
                    System.out.print("|      " + r.getString(i) + "    ");

                }
                System.out.println();
                System.out.println(
                        "------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
        }

    }

    public void update_request(int registration_id1) {
        view_task(registration_id1);
        validation v = new validation();
        String query = "insert into  waste_management.pickup_discription ( request_id, amount_dry, amount_wet) values (?,?,?)";
        int attempt = 5;
        String r1;
        do {
            System.out.println("ENTER REQUEST_ID :: ");
            attempt--;
            r1 = sc.next();
            if (r1.equalsIgnoreCase("back")) {
                return;
            }
            System.out.println(attempt + " ATTEMPTS ARE LEFT ");
        } while ((v.isUnique(conn, "pickup_request", "request_id", r1) && attempt >= 0)
                || (!v.isUnique(conn, "pickup_discription", "request_id", r1) && attempt >= 0));
        String query1 = "SELECT worker_id FROM waste_management.pickup_request where request_id=?";
        int dataworkerId = 0;
        int r = Integer.parseInt(r1);
        try {
            PreparedStatement ps = conn.prepareStatement(query1);
            ps.setInt(1, r);
            ResultSet r2 = ps.executeQuery();

            while (r2.next()) {
                dataworkerId = r2.getInt("worker");
            }

            if (attempt >= 0 && dataworkerId == registration_id1) {

                System.out.println("ENTER AMOUNT OF DRY WASTE COLLECTED( IN KG )");
                Float amount_dry = sc.nextFloat();

                System.out.println("ENTER AMOUNT OF WET WASTE COLLECTED( IN KG )");
                float amount_WET = sc.nextInt();

                try {
                    PreparedStatement ps1 = conn.prepareStatement(query);
                    ps1.setInt(1, r);
                    ps1.setFloat(2, amount_dry);
                    ps1.setFloat(3, amount_WET);
                    int rs = ps1.executeUpdate();
                    if (rs > 0) {
                        String query2 = " update waste_management.pickup_request Set status ='complete' where request_id=?";
                        PreparedStatement ps2 = conn.prepareStatement(query2);
                        ps2.setInt(1, r);
                        int status_check = ps2.executeUpdate();
                        if (status_check > 0) {
                            System.out.println("TASK HAS BEEN COMPLETED AND UPDATE  SUCEESFULLY ");

                        }

                    }

                } catch (Exception e) {
                    System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
                }
            } else {
                System.out.println(" RETRY YOU HAVE MADE LOTS OF ATTEMPT ");
            }

        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
        }

    }
}
