/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.Model;

//import static Scheduler.DBConn;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


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
            ResourceBundle rb = ResourceBundle.getBundle("language/rb");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("sqltitle"));
            alert.setHeaderText(rb.getString("header"));
            alert.setContentText(rb.getString("sqlcontent"));
            alert.showAndWait();        
    }
}
   
public static void addCustomer(String custName, Integer active,String userName,String address,String address2,Integer cityId,Integer postalCode,String phone){
    Integer latestAddressID = null;
    if (address2.isEmpty() == true){
        address2 = "";
    }
    try{
        String SQLa = "INSERT INTO address (address,address2,cityId,postalCode,phone,createDate,createdBy,lastUpdateBy) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement statementa =DBConn.prepareStatement(SQLa);
        statementa.setString(1,address);
        statementa.setString(2,address2);
        statementa.setInt(3, cityId);
        statementa.setInt(4, postalCode);
        statementa.setString(5, phone);
        statementa.setTimestamp(6, timestamp);
        statementa.setString(7, userName);
        statementa.setString(8, userName);
        statementa.executeUpdate();
        String SQLb = "SELECT max(addressId) newAddress from address";
        ResultSet result = DBConn.createStatement().executeQuery(SQLb);
        while(result.next()){
            latestAddressID = result.getInt("newAddress");
        }
        String SQL = "INSERT INTO customer (customerName,addressId,active,createDate,createdBy,lastUpdateBy) VALUES (?,?,?,?,?,?)";
        PreparedStatement statement =DBConn.prepareStatement(SQL);
        statement.setString(1,custName);
        statement.setInt(2,latestAddressID);
        statement.setInt(3, active);
        statement.setTimestamp(4, timestamp);
        statement.setString(5, userName);
        statement.setString(6, userName);
        statement.executeUpdate();
    }
    catch(SQLException e){
        System.out.println("Error: " + e);
            ResourceBundle rb = ResourceBundle.getBundle("language/rb");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("sqltitle"));
            alert.setHeaderText(rb.getString("header"));
            alert.setContentText(rb.getString("sqlcontent"));
            alert.showAndWait();
    }
}

public static ObservableList getCustomerAddress(Integer addressID){
    
    ObservableList customerAddress = FXCollections.observableArrayList();
        try{
    String SQL = "SELECT address,address2,cityId,postalCode,phone from address where addressId = ?";
    PreparedStatement statement =DBConn.prepareStatement(SQL);
    statement.setInt(1,addressID);
    ResultSet result = statement.executeQuery();
    while(result.next()){
        customerAddress.add(result.getString("address"));
        customerAddress.add(result.getString("address2"));
        customerAddress.add(result.getInt("cityId"));
        customerAddress.add(result.getInt("postalCode"));
        customerAddress.add(result.getString("phone"));
    }
    }
    catch(SQLException e){
        System.out.println("Error: " + e);
    }
        return customerAddress;
}


public static void deleteCustomer(Integer custID,Integer addressID){
    try{

    String SQL = "DELETE FROM customer where customerId = ?";
    PreparedStatement statement =DBConn.prepareStatement(SQL);
    statement.setInt(1,custID);
    statement.executeUpdate();
    
    String SQLa = "DELETE FROM address where addressId = ?";
    PreparedStatement statementa =DBConn.prepareStatement(SQLa);
    statementa.setInt(1,addressID);
    statementa.executeUpdate();
    
    }
    catch(SQLException e){
        System.out.println("Error: " + e);

    }
}


public static void deleteAppointment(Integer aptID){
    try{
    String SQL = "DELETE FROM appointment where appointmentId = ?";
    PreparedStatement statement =DBConn.prepareStatement(SQL);
    statement.setInt(1,aptID);
    statement.executeUpdate();
    }
    catch(SQLException e){
        System.out.println("Error: " + e);
    }
}

public static void modifyAppointment(Integer aptID, Integer custID, Integer userID, String title,String description,
        String location, String contact, String type, String url, Timestamp start, Timestamp end,String userName){
        try{
        String SQL = "UPDATE appointment set customerId = ?, userId = ?, title = ?, description = ?, location = ?, contact = ? , type = ?, url = ?,"
                + " start = ?, end = ?,lastUpdateBy = ? WHERE appointmentId = ?";
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
        statement.setString(11, userName);
        statement.setInt(12, aptID);
        statement.executeUpdate();
    }
    catch(SQLException e){
        System.out.println("Error: " + e);
            ResourceBundle rb = ResourceBundle.getBundle("language/rb");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("sqltitle"));
            alert.setHeaderText(rb.getString("header"));
            alert.setContentText(rb.getString("sqlcontent"));
            alert.showAndWait(); 
    }
    
}

public static void modifyCustomer(Integer custID, String custName, Integer custAddressID, Integer custActive, String address, String address2,
                                  Integer cityId, Integer postalCode, String phone, String userName){
        try{
        String SQLa = "UPDATE address set address = ?, address2 = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdateBy = ? WHERE addressId = ?";
        PreparedStatement statementa =DBConn.prepareStatement(SQLa);
        statementa.setString(1,address);
        statementa.setString(2,address2);
        statementa.setInt(3, cityId);
        statementa.setInt(4, postalCode);
        statementa.setString(5,phone);    
        statementa.setString(6,userName); 
        statementa.setInt(7, custAddressID);
        statementa.executeUpdate();    
            
        String SQL = "UPDATE customer set customerName = ?,active = ?, lastUpdateBy = ? WHERE customerId = ?";
        PreparedStatement statement =DBConn.prepareStatement(SQL);
        statement.setString(1,custName);
        statement.setInt(2, custActive);
        statement.setString(3, userName);
        statement.setInt(4, custID);
        statement.executeUpdate();
    }
    catch(SQLException e){
        System.out.println("Error: " + e);
            ResourceBundle rb = ResourceBundle.getBundle("language/rb");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("sqltitle"));
            alert.setHeaderText(rb.getString("header"));
            alert.setContentText(rb.getString("sqlcontent"));
            alert.showAndWait(); 
    }
    
}

public static ObservableList getStartEndTimesAppointment(Integer aptID){
    ObservableList appointmentTimes = FXCollections.observableArrayList();
        try{
    String SQL = "SELECT start,end from appointment where appointmentId = ?";
    PreparedStatement statement =DBConn.prepareStatement(SQL);
    statement.setInt(1,aptID);
    ResultSet result = statement.executeQuery();
    while(result.next()){
        appointmentTimes.add(result.getTimestamp("start"));
        appointmentTimes.add(result.getTimestamp("end"));
    }
    }
    catch(SQLException e){
        System.out.println("Error: " + e);
    }
        return appointmentTimes;
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
