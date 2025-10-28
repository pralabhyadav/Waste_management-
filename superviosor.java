package waste_managment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;
import java.util.function.DoubleToIntFunction;

public class superviosor {

  private static Connection conn;
  private static Scanner sc;

  public superviosor(Connection conn, Scanner sc) {
    this.conn = conn;
    this.sc = sc;
  }

  public void superviosor_driver() {
    validation v = new validation();

    System.out.println(" ===>  PLEASE LOGIN FOR FURTHER PROCESS <===");
    System.out.println();
    int registration_id1 = 0;
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
    if (pasword.equalsIgnoreCase("back")) {
      return;
    }

    String query1 = "select ID,password from login where username=? AND role ='supervisor'";
    String pass = null;
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
      sc.nextLine();
      do {
        System.out.println(" HERE IS  LIST OF TASK YOU CAN PERFORM :::  ");
        System.out.println();
        System.out.println("PRESS (1) FOR PENDING REQUEST  ");
        System.out.println("PRESS (2) FOR Assign  TASK  ");
        System.out.println("PRESS (3) FOR AUTO-Assign  TASK  ");
        System.out.println("PRESS (4) FOR CHECK COMPAILN ");
        System.out.println("PRESS (5) BACK (<------) ");
        System.out.println();
        String op = null;
        int choice1;
        // sc.nextLine();
        do {

          System.out.print("ENTER OPTION NUMBER :: ");
          op = sc.nextLine();
        } while (!op.matches("[1-5]"));

        choice1 = Integer.parseInt(op);

        switch (choice1) {
          case 1:
            pending_request();
            break;
          case 2:
            Assign(registration_id1);
            break;
          case 3:
            auto_assign();
            break;
          case 4:
            check();
            break;
          case 5:

            loop = false;
            break;
          default:
            System.out.println("PLEASE ENTER VALID CHOSEE  ");
            break;
        }
      } while (loop);

    }

    else {
      System.out.println("INCORRECT PASSWORD");
      System.out.println("/ OR YOU ARE NOT A SUPERVISOR ");
      System.out.println("LOGIN FAILED !! PLEASE TRY AGAIN  ");
    }

  }

  public void Assign(int superviosor) {
    pending_request();
    validation v = new validation();
    String request_id, worker_id;
    do {
      System.out.println("==> ENTER REQUEST ID ::");
      request_id = sc.next();
      if (request_id.equalsIgnoreCase("back")) {
        return;
      }
    } while (!v.isValidInt(request_id) || v.isUnique(conn, "pickup_request", "request_id", request_id));

    String supervisorquery = "select worker_id ,name from waste_management.worker where superviosor_id=?";
    try {
      PreparedStatement p = conn.prepareStatement(supervisorquery);
      p.setInt(1, superviosor);
      ResultSet supername = p.executeQuery();
      System.out.println("HERE IS NAME AND ID OF worker");
      while (supername.next()) {
        System.out.println("    " + supername.getString("worker_id") + "       " + supername.getString("name"));
      }
      System.out.println();
    } catch (Exception e) {
      System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
    }

    do {
      System.out.println("==> ENTER WORKER ID ::");
      worker_id = sc.next();

      if (worker_id.equalsIgnoreCase("back")) {
        return;
      }
    } while (!v.isValidInt(worker_id) || v.isUnique(conn, "worker", "worker_id", worker_id));

    String query = "update waste_management.pickup_request set worker_id=? where request_id=? and worker_id is null ";
    try {
      int worker_id1 = Integer.parseInt(worker_id);
      int request_id1 = Integer.parseInt(request_id);

      PreparedStatement ps = conn.prepareStatement(query);
      ps.setInt(1, worker_id1);
      ps.setInt(2, request_id1);
      int a = ps.executeUpdate();
      if (a > 0) {
        System.out.println(" update sucessfull ");

      } else {

        System.out.println("YOU HAVE MADE SOME MISTAKE PLEASE TRY AGAIN  ");
      }

    } catch (Exception e) {
      System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
    }

  }

  public void check() {
    // request_id, citizen_id, location, ward_no, waste_type, priority,
    // request_date, worker_id, status
    String query = "Select c.request_id   , p.ward_no, p.status ,c.description from  waste_management.pickup_request p join waste_management.compain c on p.request_id=c.request_id  ";
    try {
      PreparedStatement ps = conn.prepareStatement(query);
      ResultSet r = ps.executeQuery();
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------");
      System.out.printf("%-20s %-20s %-20s %-20s %n", "request_id", "ward_no", "status", "discription");
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------");

      // Row data
      while (r.next()) {
        System.out.printf("%-20s %-20s %-20s %-20s %n", r.getInt("c.request_id"), r.getInt("p.ward_no"),
            r.getString("p.status"), r.getString("c.description"));

      }
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------");
    } catch (Exception e) {
      System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
    }

  }

  public static void pending_request() {

    String query = " select request_id, citizen_id, location, ward_no, waste_type, priority, request_date, status from pickup_request where worker_id is null and status = 'pending' order by priority desc";
    // System.out.println(" Enter worker id :: ");
    // int worker_id =sc.nextInt();
    try {
      PreparedStatement ps4 = conn.prepareStatement(query);
      // ps4.setInt(1, 100);
      ResultSet r = ps4.executeQuery();
      ResultSetMetaData cd = r.getMetaData();
      int count = cd.getColumnCount();

      System.out.println(
          "------------------------------------------------------------------------------------------------------------------------------------------------");
      System.out.println(
          "|request_id  |   citizen_id   |       location     |   ward_no   |   waste_type     |  priority |        request_date         |    status    ");
      while (r.next()) {

        System.out.println();
        System.out.println(
            "------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
        for (int i = 1; i <= count; i++) {
          System.out.print("|      " + r.getString(i) + "    ");

        }
        System.out.println();
        System.out.println(
            "------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
      }
    } catch (Exception e) {
      System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
    }

  }

  public void auto_assign() {
    pending_request();
    validation v = new validation();
    String request_id;
    int worker_id = 0, ward_no = 0;
    do {
      System.out.println("==> ENTER REQUEST ID ::");
      request_id = sc.next();
      if (request_id.equalsIgnoreCase("back")) {
        return;
      }
    } while (v.isUnique(conn, "pickup_request", "request_id", request_id));
    try {
      int request_id1 = Integer.parseInt(request_id);
      String query1 = "select ward_no from pickup_request where request_id=?";
      PreparedStatement ps4 = conn.prepareStatement(query1);
      ps4.setInt(1, request_id1);
      ResultSet rs = ps4.executeQuery();
      while (rs.next()) {
        ward_no = rs.getInt("ward_no");
      }

      String supervisorquery = "SELECT worker_id FROM waste_management.worker where ward_no=? order by RAND()  limit 1";
      PreparedStatement ps1 = conn.prepareStatement(supervisorquery);

      ps1.setInt(1, ward_no);
      ResultSet rs1 = ps1.executeQuery();
      if (rs1.next()) {
        while (rs1.next()) {
          worker_id = rs1.getInt("worker_Id");

        }
      }

      String query = "update waste_management.pickup_request set worker_id=? where request_id=? ";

      PreparedStatement ps2 = conn.prepareStatement(query);
      ps2.setInt(1, worker_id);
      ps2.setInt(2, request_id1);
      int a = ps2.executeUpdate();
      if (a > 0) {
        System.out.println();
        System.out.println("  ====>   WORKER OF WARD NO " + ward_no + " HAS AUTOMATICALLY ASSIGN THAT   TASK ");
        System.out.println();

      }

      else {
        System.out.println("YOU HAVE MADE SOME MISTAKE PLEASE TRY AGAIN  ");
      }

    } catch (Exception e) {
      System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
    }

  }

}