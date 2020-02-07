/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author flavius8
 */
public class User {
    private final IntegerProperty userID;
    private final StringProperty username;

    
    public User (Integer ID,String uName){
        this.userID = new SimpleIntegerProperty(ID);
        this.username = new SimpleStringProperty(uName);
    }
    
    public IntegerProperty getUserId(){
        return userID;
    }
    public StringProperty getUsername(){
        return username;
    } 

    
    
}
