package waste_managment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Random;
import java.util.Scanner;

public class admin {

   private static  Connection conn;
   private static  Scanner sc;
    public admin( Connection conn ,Scanner sc){
      this.conn=conn;
      this.sc=sc;
    }

public void admin_driver(){

WasteReportPDF r13 = new WasteReportPDF(conn);
validation v=new validation();
tools1 t=new tools1(conn, sc);
WasteReport r11 =new WasteReport(conn);
  //////// 1. ek int bachaa he salary wala validation ke liye
  ///      2.budget wala optional he validation ke liye 
  ///       3. budget allocation key button is remain
   boolean loop=true;


int registration_id=0;
        String pass=null;
         System.out.println("===========>   PLEASE LOGIN FOR FURTHER PROCESS   <================= ");
         System.out.println();
         String user2;
        do {
          System.out.println("==>  ENTER  USERNAME  :: ");
             user2 =sc.next();
             if (user2.equalsIgnoreCase("back")) {
              return;
             }
         } while (v.isUniqueString(conn,"login" ,"username",user2)||!v.isValidStringuser(user2));
           
            

             System.out.println("==> ENTER  PASSWORD  :: ");
             String pasword2 =sc.next();
             if (pasword2.equalsIgnoreCase("back")) {
              return;}

             String query1="select ID ,password from login where username=? and role='admin' ";
             try{
             PreparedStatement ps1=conn.prepareStatement(query1);
             ps1.setString(1,user2);
             //ps1.setString(2, pasword);
             ResultSet rs =ps1.executeQuery();
             
             
            while (rs.next()) {
              registration_id=rs.getInt("ID");
               pass=rs.getString("password");
            }
             
                
             } catch(Exception e){
                System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
             }

          
if (pasword2.equals(pass)) {
  







  do {System.out.println();System.out.println();System.out.println();System.out.println();



      System.out.println("HERE IS LIST OF TASK THAT YOU CAN PERFORM ");
  System.out.println();
  System.out.println("PRESS (1) FOR ADD SUPERVIOSOR  ");
  System.out.println("PRESS (2) FOR ADD WORKER ");
  System.out.println();
  System.out.println("PRESS (3) FOR Analyze waste collection patterns. ");
  System.out.println("PRESS (4) FOR GENRATE REPORT ");
  System.out.println("PRESS (5) FOR BUDGET ALLOCATION ");
  System.out.println();
  System.out.println("PRESS (6) FOR SHOW AVALIABLE TOOLS");
  System.out.println("PRESS (7) FOR SHOW OVERALL TOOLS REQUEST");
  System.out.println("PRESS (8) FOR SHOW PENDING TOOLS REQUEST");
  System.out.println("PRESS (9) FOR APPROVE TOOLS REQUEST");
  System.out.println();
  System.out.println("PRESS (10) FOR BACK (<------)");

 String op= null;
      int choice1;
     // sc.nextLine();
     do 
     {
       
         System.out.print("ENTER OPTION NUMBER :: ");
         
         op=sc.nextLine();
         if (op.equalsIgnoreCase("back")) {
              return;}
        } 
     while (!op.matches("[1-9]|10"));

     
     choice1 = Integer.parseInt(op);

switch (choice1) {
  case 1: add_superviosor();
    break;
case 2: 
add_worker();
    
    break;
 case 3:analyze();
 break;
 case 4:
 System.out.println();
 System.out.println("IN WHICH FORMAT YOU WANT THE REPORT");
 System.out.println();
 System.out.println("PRESS (1) FOR TEXT FORMAT");
 System.out.println("PRESS (2) FOR PDF FORMAT");
 System.out.println();
  String check = sc.next();
int ch=Integer.parseInt(check);
  if (ch==1) 
  {
     r11.report();
     break;
  } 
  else if (ch == 2) 
  {
    r13.generatePDFReport();
    break;
  }
  else 
  {
    System.out.println("ENTER A VALID OPTION");
  }

 
 
 
 

 break;
  case 5:budget();
 break;
   case 6:t.avalivale_tools();
 break;
 case 7:t.all_request();
 break;
  case 8:t.pending_request();
 break;
   case 9:t.approve();
 break;
 case 10:loop=false;
 break;
  default:System.out.println("PLEASE ENTER VALID INPUT ");
    break;
}
  } while (loop);
  















}
else {
  System.out.println();
  System.out.println("  WRONG PASSWORD");
  System.out.println("        OR         ");
  System.out.println("  YOU ARE NOT AN ADMIN ");

}





}



public static void analyze(){
    validation v=new validation();
//String query=" select r.ward_no , sum(amount_dry) as AMOUNT_DRYWASTE,sum(amount_wet) as AMOUNT_WET_WASTE, 100*sum(amount_dry)/(sum(amount_dry)+sum(amount_wet)) as percentage_of_dry , 100*sum(amount_wet)/(sum(amount_dry)+sum(amount_wet)) as percentage_of_wet from pickup_discription p join pickup_request r on p.request_id=r.request_id where ward_no =? group by ward_no";
String query1="SELECT r.ward_no,SUM(p.amount_dry) AS AMOUNT_DRYWASTE,SUM(p.amount_wet) AS AMOUNT_WET_WASTE,100.0 * SUM(p.amount_dry) / NULLIF(SUM(p.amount_dry) + SUM(p.amount_wet), 0) AS percentage_of_dry,100.0 * SUM(p.amount_wet) / NULLIF(SUM(p.amount_dry) + SUM(p.amount_wet), 0) AS percentage_of_wet FROM pickup_discription p jOIN pickup_request r  ON p.request_id = r.request_id  GROUP BY r.ward_no";
    
int ward;
try{       

/*do {System.out.println("ENTER WARD NO :: ");
    ward=sc.nextInt();
} while (!v.checkward(ward));*/
          
PreparedStatement pp=conn.prepareStatement(query1);
//pp.setInt(1,ward);
 ResultSet r=pp.executeQuery();
 System.out.println("-----------------------------------------------------------------------------------------------------------");
             System.out.printf("%-10s %-20s %-20s %-20s %-10s%n", "WARD NO","AMOUNT_DRYWASTE"  , "AMOUNT_WET_WASTE", "percentage_of_dry","percentage_of_wet");
                 System.out.println("-----------------------------------------------------------------------------------------------------------");
     
                 // Row data
                 while (r.next()) {
                     System.out.printf("%-10s %-20s %-20s %-20s %-10s%n",r.getInt("ward_no"),r.getString("AMOUNT_DRYWASTE"),r.getString("AMOUNT_WET_WASTE"),r.getString("percentage_of_dry"),r.getString("percentage_of_wet"));
                            
                 }
                System.out.println("-----------------------------------------------------------------------------------------------------------");




              }
catch(Exception e){
  System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
}
}

public static void budget(){
  
validation v=new validation();


  String supervisorquery="select supervisor_id ,name from waste_management.supervisor";
             try {
              PreparedStatement p=conn.prepareStatement(supervisorquery);
              ResultSet supername=p.executeQuery();
              System.out.println("HERE IS NAME AND ID OF SUPERVISORS");
               while (supername.next()) {
                System.out.println("    "+supername.getString("supervisor_id")+"       "+supername.getString("name"));
               }
               System.out.println();
             } catch (Exception e) {
              System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
             }



String SUPPERVISOR_ID;
do {
    System.out.println("ENTER  SUPERVISOR ID ");
        SUPPERVISOR_ID=sc.next();
} while (!SUPPERVISOR_ID.matches("^[0-9]+$"));
     
      int fixed_budget=0;
      int EXTRA_budget=0;
      float total_budget=0;
       String query="select sum(salary) as salarys from worker where superviosor_id=?";
      try {
        int SUPPERVISOR_ID1=Integer.parseInt(SUPPERVISOR_ID);
        PreparedStatement p=conn.prepareStatement(query);
        p.setInt(1, SUPPERVISOR_ID1);
        ResultSet r=p.executeQuery();
        while (r.next()) {
             fixed_budget=r.getInt("salarys") ;
        }
        System.out.println(fixed_budget+" IT IS BASIC SALARY HAVE TO GIVE TO WORKERS ");
       String percentage1;
       do {
         System.out.println("ENTER AN PERCENTAGE OF EXTRA BUDGET YOU WANT TO GIVE :: ");
       
         percentage1=sc.next();
       } while (!percentage1.matches("^[0-9]+$"));
       int percentage=Integer.parseInt(percentage1);
        EXTRA_budget=(fixed_budget/100)*percentage;
         total_budget=fixed_budget+EXTRA_budget;
        String query2="update  waste_management.supervisor set  budget = ? where supervisor_id=?";
         PreparedStatement p2=conn.prepareStatement(query2);
         p2.setFloat(1,total_budget);
         p2.setInt(2,SUPPERVISOR_ID1);
         int a=p2.executeUpdate();
         if (a>0) {
          System.out.println("BUDGET ALLOCATE SUCCESFULLY AMOUNT IS "+total_budget+" FOR SUPPERVISOR ID "+SUPPERVISOR_ID);
         }
      } catch (Exception e) {
        System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
      }
       

}


public static  void add_superviosor(){
  validation v=new validation();
System.out.println(" ======>  || HERE IS PAGE FOR ADDING SUPERVIOSOR ||  <======");
  System.out.println();
  String user;
              do {
                  System.out.println("==>  CREATE  USERNAME  :: ");
             user =sc.next();
             if (user.equalsIgnoreCase("back")) {
              return;}
              } while (!v.isValidStringuser(user)||!v.isUniqueString(conn,"login","username",user));
          
             System.out.println("==> CREATE  PASSWORD  :: ");
            String pasword =sc.next();
            if (pasword.equalsIgnoreCase("back")) {
              return;}
            Random r=new Random();
             int random_id;
            

            do {
                 random_id=100000+r.nextInt(900000);
                 
               
            } while (!v.isUniqueId(conn,"login","ID",random_id));
           

           
            try{
            String SignQuery =" insert into login(Id,username, password,role ) values (?,?,?,?) ";
            PreparedStatement ps2=conn.prepareStatement(SignQuery);
            ps2.setInt(1,random_id);
            ps2.setString(2,user);
            ps2.setString(3,pasword);
             ps2.setString(4,"supervisor");

            int ur=ps2.executeUpdate();
            if (ur>0) {
               
                //supervisor_id, name, phone, budget
                String name;String phone;
                sc.nextLine();
                do {
                   System.out.println("ENTER YOUR NAME :: ");
               name =sc.nextLine();
                } while (!v.isValidString(name));
               
               
                  do {
                    System.out.println("ENTER PHONE NO. :: ");
                 phone =sc.nextLine();
                  } while (!phone.matches("^[6-9][0-9]{9}$")||!v.isUniqueString(conn,"supervisor","phone",phone));
                
String query2 ="insert into waste_management.supervisor(supervisor_id, name, phone,budget) values (?,?,?,?)";
                PreparedStatement ps3 =conn.prepareStatement(query2);
            ps3.setInt(1,random_id);
            ps3.setString(2, name);
           
            ps3.setString(3, phone);
            ps3.setInt(4,0);
int b=ps3.executeUpdate();
if (b>0) {
     System.out.println("===========>   SIGN UP SUCESSFULL !!  PLEASE LOGIN FOR FURTHER PROCESS   <================= ");
}
else{
   System.out.println("update unsucceSfull try again ");
    String query3="DELETE FROM login WHERE ID=?";
    PreparedStatement p=conn.prepareStatement(query3);
      p.setInt(1,random_id);
      int e=p.executeUpdate();
}

            }
            }
            catch(Exception e){
                System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
            }
            
    
}



public static  void add_worker(){
  validation v=new validation();
 int random_id;
System.out.println(" ===>  || HERE IS PAGE FOR ADDING WORKER ||  <===");
       String user1;     
       do {
        System.out.println("==>  CREATE  USERNAME  :: ");
             user1 =sc.next();
             if (user1.equalsIgnoreCase("back")) {
              return;}
       } while (!v.isValidStringuser(user1)||!v.isUniqueString(conn,"login","username",user1));

             System.out.println("==> CREATE  PASSWORD  :: ");
            String pasword1 =sc.next();
            if (pasword1.equalsIgnoreCase("back")) {
              return;}
            Random r1=new Random();
             int random_id1=0;
            
            do {
                 random_id=100000+r1.nextInt(900000);
                 
                 
            } while (!v.isUniqueId(conn,"login","ID",random_id));
           

           
            try{
            String SignQuery =" insert into login(Id,username , password,role ) values (?,?,?,?) ";
            PreparedStatement ps2=conn.prepareStatement(SignQuery);
            ps2.setInt(1,random_id);
            ps2.setString(2,user1);
            ps2.setString(3,pasword1);
             ps2.setString(4,"worker");

            int ur=ps2.executeUpdate();
            if (ur>0) {
               
                //supervisor_id, name, phone, budget
                  String name;String phone, ward, SUPPERVISOR_ID, salary;
                    sc.nextLine();
                   do {
                    System.out.println("ENTER YOUR NAME :: ");
               name =sc.nextLine();
                   } while (!v.isValidString(name));
                do {
                   System.out.println("ENTER PHONE NO. :: ");
                 phone =sc.nextLine();
                } while (!phone.matches("^[6-9][0-9]{9}$")||!v.isUniqueString(conn,"worker","phone",phone));
             do {
              System.out.println("ENTER WARD NO.. :: ");
                  ward =sc.next();
             } while (!v.checkward(ward));
             String supervisorquery="select supervisor_id ,name from waste_management.supervisor";
             try {

              PreparedStatement p=conn.prepareStatement(supervisorquery);
              ResultSet supername=p.executeQuery();
              
              System.out.println("HERE IS NAME AND ID OF SUPERVISORS");
               while (supername.next()) {
                System.out.println("    "+supername.getString("supervisor_id")+"       "+supername.getString("name"));
               }
               System.out.println();
             } catch (Exception e) {
              System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
             }
               do {
                System.out.println("ENTER SUPPERVISOR ID :: ");
                      SUPPERVISOR_ID= sc.next();
               } while (!SUPPERVISOR_ID.matches("^[0-9]+$")||v.isUnique(conn,"supervisor","supervisor_id",SUPPERVISOR_ID));
                    
               do {

                       System.out.println("ENTER SALARY :: ");
                     salary=sc.next();
                    } while (!salary.matches("^[0-9]+$"));

              
                
String query2 ="insert into waste_management.worker(worker_id, name, phone, ward_no, superviosor_id,salary) values (?,?,?,?,?,?)";
               

int  ward2=Integer.parseInt(ward);
int  SUPPERVISOR_ID2=Integer.parseInt(SUPPERVISOR_ID);
int  salary2=Integer.parseInt(salary);
PreparedStatement ps3 =conn.prepareStatement(query2);
            ps3.setInt(1,random_id);
            ps3.setString(2, name);
          ps3.setString(3, phone);
          ps3.setInt(4,ward2);
          ps3.setInt(5, SUPPERVISOR_ID2);
          ps3.setInt(6,salary2);
            
int b=ps3.executeUpdate();
if (b>0) {
     System.out.println("===========>   SIGN UP SUCESSFULL !!  PLEASE LOGIN FOR FURTHER PROCESS   <================= ");
}
else{
   System.out.println("update unsuccefull try again ");
    String query3="DELETE FROM login WHERE ID=?";
    PreparedStatement p=conn.prepareStatement(query3);
      p.setInt(1,random_id);
      int e=p.executeUpdate();
}
            }
            }
            catch(Exception e){
                System.out.println(" SOMETHING WENT WRONG PLEASE TRY AGAIN");
            }
            


}

}
