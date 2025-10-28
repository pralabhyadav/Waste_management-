package waste_managment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class validation {

    public static boolean isValidString(String str) {
        return str != null && str.matches("^[A-Za-z\\s]{2,50}$");
    }

    public static boolean isValidInt(String a) {
        if (a.matches("^[0-9]+$")) {
            return a.matches("^[0-9]+$");
        }
        System.out.println("why");
        System.out.println("ENTER A VALID INTEGER");
        return a.matches("^[0-9]+$");
    }

    public static boolean isValidStringuser(String str) {
        if (str != null && str.matches("^[A-Za-z][A-Za-z0-9_.-@#$&]{2,50}$")) {

            return str != null && str.matches("^[A-Za-z][A-Za-z0-9_.-@#$&]{2,50}$");
        }
        System.out.println("Enter A Valid data");
        return str != null && str.matches("^[A-Za-z][A-Za-z0-9_.-@#$&]{2,50}$");
    }

    public static boolean isValidPhone(String phone) {
        if (phone != null && (phone.matches("[0-9]{10}"))) {
            return phone != null && (phone.matches("[0-9]{10}"));
        }
        System.out.println("Enter A Valid Phone");
        return phone != null && (phone.matches("[0-9]{10}"));
    }

    public static boolean isValidEmail(String email) {

        if (email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        }
        System.out.println("Enter A Valid Email");
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isUnique(Connection conn, String table, String column, String value) {
        if (value.matches("^[0-9]+$")) {
            String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = ?";
            try {
                int value1 = Integer.parseInt(value);
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, value1);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    int count = rs.getInt(1);

                    return count == 0;
                }
                System.out.println("fail");

            } catch (Exception e) {
                System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
                System.out.println(" ENTER AGAIN");
            }
        }

        else {
            System.out.println("PLEASE ENTER VALID INPUT");
        }
        System.out.println(" ENTER AGAIN");
        return false;
    }

    public static boolean isUniqueString(Connection conn, String table, String column, String value) {

        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    System.out.println(" ==> NOT PRESENTED IN DATABASE ");
                    return true;
                } else {
                    System.out.println(" ==>  PRESENTED IN DATABASE ");
                    return false;
                }

            }
        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
            System.out.println(" ENTER AGAIN");
        }
        System.out.println(" ENTER AGAIN");
        return false;
    }

    public static boolean checkavailable(Connection conn, String table, String column, String value) {
        if (value.matches("^[0-9]+$")) {

            String query = "Select " + column + " from " + table;
            try {
                int value1 = Integer.parseInt(value);
                PreparedStatement p = conn.prepareStatement(query);
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    if (r.getInt(column) >= value1) {
                        return false;
                    } // agar avaliable ke alawa kahi use karna ho to
                      // if (table.equals("overral_requests"))
                    else {
                        System.out.println("THIS TIME ONLY " + r.getInt(column) + " ARE AVALIBLE ");
                        return true;
                    }

                }
            } catch (Exception e) {
                System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
            }
        } else {
            System.out.println("PLEASE ENTER VALID INPUT");
        }

        System.out.println(" ENTER AGAIN");

        return true;
    }

    public static boolean checkward(String ward) {
        if (ward != null && (ward.matches("[1-9]|1[0-6]"))) {
            return ward != null && (ward.matches("[1-9]|1[0-6]"));
        } else {
            System.out.println("PLEASE ENTER WARD NO. B/W (1-16) ONLY");
        }
        return ward != null && (ward.matches("[1-9]|1[0-6]"));
    }

    /// date

    public static boolean checkwaste_type(String type) {
        if (type.equalsIgnoreCase("normal") || type.equalsIgnoreCase("hospital")
                || type.equalsIgnoreCase("hazardous")) {
            return true;
        } else {
            System.out.println("PLEASE ENTER ANY ONE OUT OF IT (normal,hospital,hazardous)");
        }
        return false;

    }

    public static boolean checkpriority(String priority) {
        return priority.matches("[0,1]");
    }

    public static boolean isUniqueId(Connection conn, String table, String column, int value) {

        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, value);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (Exception e) {
            System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
            System.out.println(" ENTER AGAIN");
        }

        System.out.println(" ENTER AGAIN");
        return false;
    }

}
