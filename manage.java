package waste_managment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class manage {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/waste_management";
    private static final String username = "Pralabh";
    private static final String password = "pralabh@123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            admin a = new admin(conn, sc);
            worker w = new worker(conn, sc);
            citizen c = new citizen(conn, sc);
            tools1 t = new tools1(conn, sc);
            superviosor s = new superviosor(conn, sc);
            WasteReport r = new WasteReport(conn);
            validation v = new validation();

            do {
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();

                System.out.println(" HERE LIST OF ROLE ");
                System.out.println("PRESS (1) AS ADMIN");
                System.out.println("PRESS (2) AS SUPPERVISOR");
                System.out.println("PRESS (3) AS WORKER");
                System.out.println("PRESS (4) AS CITIZEN");
                System.out.println("PRESS (5)   EXIT  ");
                System.out.println();
                String op = null;
                int choice1, count = 0;
                // sc.nextLine();
                do {
                    if (count > 0) {
                        System.out.println("PLEASE ENTER VALID INPUT ");
                    }
                    System.out.print("ENTER OPTION NUMBER :: ");
                    op = sc.next();
                    count++;
                    if (op.equalsIgnoreCase("back")) {
                        return;
                    }
                } while (!op.matches("[1-5]"));

                choice1 = Integer.parseInt(op);

                switch (choice1) {
                    case 1:
                        a.admin_driver();
                        break;
                    case 2:
                        s.superviosor_driver();
                        break;
                    case 3:
                        w.worker_driver();
                        break;
                    case 4:
                        c.citizen_driver();
                        break;
                    case 5:
                        loop = false;

                        break;
                    default:
                        System.out.println("ENTER VALID CHOOSEE :: ");
                        break;
                }

            } while (loop);

        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
        }

    }
}
