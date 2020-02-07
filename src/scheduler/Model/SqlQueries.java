/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.Model;

//import static Scheduler.DBConn;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static utils.DBConnection.getDbConn;

/**
 *
 * @author flavius8
 */
public class SqlQueries {
    
    public static Connection DBConn = utils.DBConnection.getDbConn();
    
    public static User setCurrentUser(String uname){
    User currentUser = null;
    try{
        String uName = null;
        Integer userID = null;
        String query = "SELECT userId,userName FROM user where userName = '" + uname + "'";

        ResultSet result = DBConn.createStatement().executeQuery(query);
  
       while (result.next()){
            userID = result.getInt("userId");
            uName = result.getString("userName");
       }
        currentUser = new User(userID,uName);
        }
        catch(SQLException e){
            System.out.println("Error: " + e);
        }
         return currentUser;
    }
    
        

    
    
    public static boolean userCredential(String uname, String pass){
           try{ 
      String pWord = null;

      String query = "SELECT password FROM user where userName = '" + uname + "'";

     ResultSet result = DBConn.createStatement().executeQuery(query);
  
       while (result.next()){
            if(result.getString(1) == null){
                return false;
            }
            else{

                 pWord = result.getString("password");
                     if (pWord.equals(pass)){
                       
                        return true;
                     }
                      else{
                       return false;
                       }
            }
            }


       
      }
      catch(SQLException er){
          System.out.println("Error: " + er);
      }
           
      return false;
    }
    
   
public static void addAppointment(Integer custID, String title,String description, String location, String contact, String type, String url){
    try{
        String SQL = "INSERT INTO appointment (customerId,title,description,location,contact,type,url) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement =DBConn.prepareStatement(SQL);
        statement.setInt(1,custID);
        statement.setString(2, title);
        statement.setString(3, description);
        statement.setString(4, location);
        statement.setString(5, contact);
        statement.setString(6, type);
        statement.setString(7, url);
        statement.executeUpdate();
    }
    catch(SQLException e){
        System.out.println("Error: " + e);
    }
}
    
public static ObservableList assembleCustomerData(){        
    ObservableList custData = FXCollections.observableArrayList();
    try{      
        String SQL = "Select * from customer Order By customerId";            
        ResultSet result = DBConn.createStatement().executeQuery(SQL);  
        while(result.next()){
            Customer cust = new Customer();
            cust.setCustomerID(result.getInt("customerId")); 
           // System.out.println("Customer ID: " + result.getInt("customerId"));
            cust.setCustomerName(result.getString("customerName"));
            cust.setCustomerAddressId(result.getInt("addressId"));
            cust.setCustomerActive(result.getInt("active"));
            cust.setCustomerCreateDate(result.getTimestamp("createDate"));
            cust.setCustomerCreatedBy(result.getString("createdBy"));
            cust.setCustomerLastUpdate(result.getTimestamp("lastUpdate"));
            cust.setCustomerLastUpdatedBy(result.getString("lastUpdateBy"));
            //System.out.println("Customer Data: " + cust.getCustomerName().get());
            custData.add(cust);            
        }
     
    }
    catch(SQLException e){

          System.out.println("Error: " + e);            
    }
    return custData;
}


public static ObservableList assembleAppointmentsData(){        
    ObservableList aptData = FXCollections.observableArrayList();
    try{      
        String SQL = "Select * from appointment Order By customerId";            
        ResultSet result = DBConn.createStatement().executeQuery(SQL);  
        while(result.next()){
            Appointment apt = new Appointment();
            apt.setAppointmentID(result.getInt("appointmentId")); 
           // System.out.println("Customer ID: " + result.getInt("customerId"));
            apt.setCustomerID(result.getInt("customerID"));
            apt.setUserID(result.getInt("userID"));
            apt.setAppointmentTitle(result.getString("title"));
            apt.setAppointmentDescription(result.getString("description"));
            apt.setAppointmentLocation(result.getString("location"));
            apt.setAppointmentContact(result.getString("contact"));
            apt.setAppointmentType(result.getString("type"));
            apt.setAppointmentUrl(result.getString("url"));
            apt.setAppointmentStart(result.getTimestamp("start"));
            apt.setAppointmentEnd(result.getTimestamp("end"));
            apt.setAppointmentCreateDate(result.getTimestamp("createDate"));
            apt.setAppointmentCreatedBy(result.getString("createdBy"));
            apt.setAppointmentLastUpdate(result.getTimestamp("lastUpdate"));
            apt.setAppointmentLastUpdatedBy(result.getString("lastUpdateBy"));
            //System.out.println("Customer Data: " + cust.getCustomerName().get());
            aptData.add(apt);            
        }
     
    }
    catch(SQLException e){

          System.out.println("Error: " + e);            
    }
    return aptData;
}
}
