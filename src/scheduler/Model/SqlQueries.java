/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.Model;

//import static Scheduler.DBConn;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author flavius8
 */
public class SqlQueries {
    static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
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
    
   
public static void addAppointment(Integer custID, Integer userID, String title,String description,
        String location, String contact, String type, String url, Timestamp start, Timestamp end,String userName){
    try{
        String SQL = "INSERT INTO appointment (customerId,userId,title,description,location,contact,type,url,start,end,createDate,createdBy,lastUpdateBy) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement =DBConn.prepareStatement(SQL);
        statement.setInt(1,custID);
        statement.setInt(2,userID);
        statement.setString(3, title);
        statement.setString(4, description);
        statement.setString(5, location);
        statement.setString(6, contact);
        statement.setString(7, type);
        statement.setString(8, url);
        statement.setTimestamp(9, start);
        statement.setTimestamp(10, end);
        statement.setTimestamp(11, timestamp);
        statement.setString(12, userName);
        statement.setString(13, userName);
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
        String SQL = "Select * from appointment Order By appointmentId";            
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
