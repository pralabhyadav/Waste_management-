package waste_managment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Random;
import java.util.Scanner;

public class citizen {
  private static Connection conn;
  private static Scanner sc;

  public citizen(Connection conn, Scanner sc) {
    this.conn = conn;
    this.sc = sc;
  }

  public void citizen_driver() {
    validation v = new validation();
    int random_id = 0;
    System.out.println("===========> WELCOME TO THE POTRAL OF MUNCIPAL COPRATION <===========");
    System.out.println();
    boolean loop = true;
    do {
      System.out.println("PLEASE SIGN UP / LOG IN FOR FURTHER PROCESSS");
      System.out.println();
      System.out.println("PRESS (1) FOR SIGN UP ");
      System.out.println("PRESS (2) FOR LOG  IN ");
      System.out.println("PRESS (3) FOR EXIT ");
      System.out.println();
      String op = null;
      int choice1;
      do {

        System.out.print("ENTER OPTION NUMBER :: ");
        op = sc.nextLine();
        if (op.equalsIgnoreCase("back")) {
          return;
        }
      } while (!op.matches("[1-3]"));

      choice1 = Integer.parseInt(op);

      switch (choice1) {
        case 1:
          System.out.println("------------> WELCOME TO SIGN IN PAGE <------------");
          System.out.println();
          String user;
          do {
            System.out.println("==>  CREATE  USERNAME  :: ");
            user = sc.next();
            if (user.equalsIgnoreCase("back")) {
              break;
            }
          } while (!v.isUniqueString(conn, "login", "username", user) || !v.isValidStringuser(user));
          if (user.equalsIgnoreCase("back")) {
            break;
          }

          System.out.println("==> CREATE  PASSWORD  :: ");
          String pasword = sc.next();
          if (pasword.equalsIgnoreCase("back")) {
            break;
          }
          Random r = new Random();

          do {
            random_id = 100000 + r.nextInt(900000);

          } while (!v.isUniqueId(conn, "login", "ID", random_id));

          try {
            String SignQuery = " insert into login(Id,username ,password,role ) values (?,?,?,?) ";
            PreparedStatement ps2 = conn.prepareStatement(SignQuery);
            ps2.setInt(1, random_id);
            ps2.setString(2, user);
            ps2.setString(3, pasword);
            ps2.setString(4, "citizen");

            int ur = ps2.executeUpdate();
            if (ur > 0) {

              // citizen_id, name, adrress, ward_no, phone_number
              String name, addess, ward;
              do {
                System.out.println("ENTER YOUR NAME :: ");
                sc.nextLine();
                name = sc.nextLine();
                if (name.equalsIgnoreCase("back")) {
                  break;
                }
              } while (!v.isValidString(name));
              do {
                System.out.println("ENTER  ADDREESS :: ");
                addess = sc.nextLine();
              } while (!v.isValidStringuser(addess));
              do {
                System.out.println("ENTER WARD NO.. :: ");
                ward = sc.next();
              } while (!v.checkward(ward));
              String phone;
              do {
                System.out.println("ENTER PHONE NO. :: ");

                phone = sc.next();
              } while (!phone.matches("^[6-9][0-9]{9}$") || !v.isUniqueString(conn, "citien", "phone_number", phone));

              String query2 = "insert into waste_management.citien(citizen_id, name, adrress, ward_no, phone_number) values (?,?,?,?,?)";
              int ward1 = Integer.parseInt(ward);
              PreparedStatement ps3 = conn.prepareStatement(query2);
              ps3.setInt(1, random_id);
              ps3.setString(2, name);
              ps3.setString(3, addess);
              ps3.setInt(4, ward1);
              ps3.setString(5, phone);
              int b = ps3.executeUpdate();
              if (b > 0) {
                System.out.println(
                    "===========>   SIGN UP SUCESSFULL !!  PLEASE LOGIN FOR FURTHER PROCESS   <================= ");
              } else {
                System.out.println("update unsuccefull try again ");
                String query3 = "DELETE FROM login WHERE ID=?";
                PreparedStatement p = conn.prepareStatement(query3);
                p.setInt(1, random_id);
                int e = p.executeUpdate();

              }

            }

          } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
          }

          break;
        case 2:
          int registration_id = 0;
          String pass = null;
          System.out.println("===========>   PLEASE LOGIN FOR FURTHER PROCESS   <================= ");
          System.out.println();
          String user1;
          // sc.nextLine();
          do {
            System.out.println("ENTER YOUR USERNAME :: ");

            user1 = sc.nextLine();
            if (user1.equalsIgnoreCase("back")) {
              break;
            }
          } while (!v.isValidStringuser(user1) || !v.isUniqueString(conn, "login", "username", user1));
          if (user1.equalsIgnoreCase("back")) {
            break;
          }
          System.out.println("==> ENTER  PASSWORD  :: ");
          String pasword1 = sc.next();
          if (pasword1.equalsIgnoreCase("back")) {
            break;
          }
          String query1 = "select ID ,password from login where username=? and role ='citizen'";
          try {
            PreparedStatement ps1 = conn.prepareStatement(query1);
            ps1.setString(1, user1);
            // ps1.setString(2, pasword);
            ResultSet rs = ps1.executeQuery();

            while (rs.next()) {
              registration_id = rs.getInt("ID");
              pass = rs.getString("password");
            }

          } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
          }

          if (pasword1.equals(pass)) {
            System.out.println();
            System.out.println("=======================> LOGIN SUCCESSFULL <===================");
            System.out.println();
            boolean loop1 = true;
            do {
              System.out.println(" HERE SOME TASK YOU CAN PERFORM ");
              System.out.println();
              System.out.println("PRESS (1) FOR MAKING PICKUP REQUEST ");
              System.out.println("PRESS (2) FOR MAKING COMPLAGIN ");
              System.out.println("PRESS (3) FOR TRACKING REQUEST ");
              System.out.println("PRESS (4) FOR BACK  (<----) ");
              System.out.println();
              String op2 = null;
              int choice2;
              do {

                System.out.print("ENTER OPTION NUMBER :: ");
                op2 = sc.next();
                if (op2.equalsIgnoreCase("back")) {
                  return;
                }
              } while (!op2.matches("[1-4]"));

              choice2 = Integer.parseInt(op2);

              switch (choice2) {
                case 1:
                  pickup_request(registration_id);
                  break;

                case 2:
                  complaign(registration_id);
                  break;
                case 3:
                  track_request(registration_id);
                  break;
                case 4:
                  loop1 = false;
                  break;
                default:
                  System.out.println("invalid input ");
                  break;
              }
            } while (loop1);

          } else {
            System.out.println();
            System.out.println("WRONG PASSWORD ");
            System.out.println();
            System.out.println("OR MAY BE YOU ARE NOT ENTERING  CITIZEN ");
          }

          break;

        case 3:
          loop = false;
          break;
        default:
          System.out.println("invalid input ");
          break;
      }

    } while (loop);

  }

  public void pickup_request(int citizen) {

    validation v = new validation();

    String query = "insert into pickup_request ( citizen_id, location, ward_no, waste_type, priority) values (?,?,?,?,?)";
    String addess, type, ward, priority;
    sc.nextLine();
    do {
      System.out.println("ENTER  ADDREESS :: ");
      addess = sc.nextLine();
      if (addess.equalsIgnoreCase("back")) {
        return;
      }
    } while (!v.isValidStringuser(addess));
    do {
      System.out.println("ENTER WARD NO.. :: ");
      ward = sc.next();
      if (ward.equalsIgnoreCase("back")) {
        return;
      }
    } while (!v.checkward(ward));

    do {
      System.out.println("ENTER WASTE TYPE - ");
      System.out.print("(Normal , Hospital or Hazardous) :: ");
      type = sc.next();
      if (type.equalsIgnoreCase("back")) {
        return;
      }
    } while (!v.checkwaste_type(type));

    do {
      System.out.print("ENTER PRIORITY..(0/1) :: ");
      priority = sc.next();
      if (priority.equalsIgnoreCase("back")) {
        return;
      }
    } while (!v.checkpriority(priority));

    try {
      int ward2 = Integer.parseInt(ward);
      int priority2 = Integer.parseInt(priority);
      PreparedStatement pp = conn.prepareStatement(query);
      pp.setInt(1, citizen);
      pp.setString(2, addess);
      pp.setInt(3, ward2);
      pp.setString(4, type);
      pp.setInt(5, priority2);

      int r = pp.executeUpdate();

      if (r > 0) {
        System.out.println();
        System.out.println("  YOUR REQUEST HAS BEEN ACCEPTED ");
        System.out.println();

      } else {
        System.out.println(" SOMETHING WENT WRONG ");
      }
    }

    catch (Exception e) {
      System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
    }

  }

  public void complaign(int citizen_id) {
    track_request(citizen_id);
    validation v = new validation();
    String query1 = "insert into compain( request_id, description) values (?,?)";
    String request_id;
    do {
      System.out.println(" ENTER YOUR REQUEST ID :: ");
      request_id = sc.next();
      if (request_id.equalsIgnoreCase("back")) {
        return;
      }
    } while (v.isUnique(conn, "pickup_request", "request_id", request_id));
    int request_id1 = Integer.parseInt(request_id);

    String query11 = "Select citizen_id from pickup_request where request_id=?";
    try {
      PreparedStatement pp = conn.prepareStatement(query11);
      pp.setInt(1, request_id1);
      ResultSet r = pp.executeQuery();
      int check_citizen = 0;
      while (r.next()) {
        check_citizen = r.getInt("citizen_id");

      }
      if (check_citizen == citizen_id) {

        String complain;
        sc.nextLine();
        do {
          System.out.println(" DISCRIBE YOUR COMPLAGIN :: ");
          complain = sc.nextLine();
          if (complain.equalsIgnoreCase("back")) {
            return;
          }
        } while (!v.isValidStringuser(complain));

        try {
          PreparedStatement ps1 = conn.prepareStatement(query1);
          ps1.setInt(1, request_id1);
          ps1.setString(2, complain);

          int rs = ps1.executeUpdate();
          if (rs > 0) {
            System.out.println(" ------------->  YOUR COMPLAIN HAS BEEN SUCCESFULLY REGISTERED  <--------");
          } else {
            System.out.println("------------->    SOMETHING WENT WRONG PLEASE RETRY  <--------------------");
          }

        } catch (Exception e) {
          System.out.println("------------->    SOMETHING WENT WRONG PLEASE RETRY  <--------------------");
          System.out.println();
          System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
        }

      } else {
        System.out.println();
        System.out.println("       REGISTRATION FAILED !!! YOU HAVE TO ENTER YOUR OWN REQUEST ID         ");
        System.out.println();
      }
    } catch (Exception e) {
      System.out.println("YOU HAVE MADE SOME MISTAKE");
    }

  }

  public void track_request(int citizen) {
    // request_id, citizen_id, location, ward_no, request_date, status;
    String query = "select request_id, citizen_id, location, ward_no, request_date, status from waste_management.pickup_request where citizen_id=? ";

    try {

      PreparedStatement ps3 = conn.prepareStatement(query);
      ps3.setInt(1, citizen);
      ResultSet r = ps3.executeQuery();
      ResultSetMetaData rsmd = r.getMetaData();
      int count = rsmd.getColumnCount();
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------------");
      /*
       * for(int i=1;i<=count;i++){
       * System.out.print(rsmd.getColumnName(i)+"\t"+"  ");
       * }
       */
      System.out.println(
          "request_id     citizen_id       location              ward_no              request_date                 status");
      while (r.next()) {

        System.out.println();
        System.out.println(
            "-----------------------------------------------------------------------------------------------------------------");
        for (int i = 1; i <= count; i++) {
          System.out.print(r.getString(i) + "\t" + "\t");
        }
        System.out.println();
      }
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------------");

    } catch (Exception e) {
      System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
    }

  }

}
