/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import scheduler.Model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author flavius8
 */
public class DBConnection {
    //Connection params
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U05m6w";
    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    private static Connection conn;
    private static final String  mysqlJDBCDriver = "com.mysql.jdbc.Driver";
    private static final String username = "U05m6w";
    private static String password = "53688545719";
    
    
    public static Connection startConnection(){
        
        try{
            Class.forName(mysqlJDBCDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, username,password);
            System.out.println("Connection Successful");
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
            
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            
        }
        return conn;
    }
    
    public static Connection getDbConn(){
        return conn;
    }
    
    public static void closeConnection(){
        try{
        conn.close();
        System.out.println("Connection Terminated");
        }
        catch(SQLException e){
            System.out.println("Error: " + e);
        }
    }

    
    
}
