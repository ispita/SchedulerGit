/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.Model;

import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author flavius8
 */
public class Customer {
    
    private IntegerProperty customerID;
    private StringProperty customerName;
    private IntegerProperty customerAddressId;
    private IntegerProperty customerActive;
    private ObjectProperty<Timestamp> customerCreateDate; 
    private StringProperty customerCreatedBy;
    private ObjectProperty<Timestamp> customerLastUpdate;
    private StringProperty customerLastUpdatedBy;

    
    public Customer (){
        this.customerID = new SimpleIntegerProperty();
        this.customerName = new SimpleStringProperty();
        this.customerAddressId = new SimpleIntegerProperty();
        this.customerActive = new SimpleIntegerProperty();
        this.customerCreateDate = new SimpleObjectProperty<Timestamp>();
        this.customerCreatedBy = new SimpleStringProperty();
        this.customerLastUpdate = new SimpleObjectProperty<Timestamp>(); 
        this.customerLastUpdatedBy = new SimpleStringProperty();
    }
    
    public IntegerProperty getCustomerId(){
        return customerID;
    }
    public StringProperty getCustomerName(){
        return customerName;
    }
    public IntegerProperty getCustomerAddressId(){
        return customerAddressId;
    }
    public IntegerProperty getCustomerActive(){
        return customerActive;
    }
    public ObjectProperty getCustomerCreateDate(){
        return customerCreateDate;
    }
    public StringProperty getCustomerCreatedBy(){
        return customerCreatedBy;
    }
    public ObjectProperty getCustomerLastUpdate(){
        return customerLastUpdate;
    }
    public StringProperty getCustomerLastUpdatedBy(){
        return customerLastUpdatedBy;
    }
    public void setCustomerID(Integer ID){
        this.customerID.set(ID);
        
    }
    public void setCustomerName(String Name){
        this.customerName.set(Name);
    }
    public void setCustomerAddressId(Integer AddressID){
        this.customerAddressId.set(AddressID);
    }
    public void setCustomerActive (Integer Active){
        this.customerActive.set(Active);
    }
    public void setCustomerCreateDate(Timestamp CreateDate){
        this.customerCreateDate.set(CreateDate);
    }
    public void setCustomerCreatedBy(String CreatedBy){
        this.customerCreatedBy.set(CreatedBy);
    }
    public void setCustomerLastUpdate(Timestamp LastUpdate){
        this.customerLastUpdate.set(LastUpdate);
    }
    public void setCustomerLastUpdatedBy(String LastUpdatedBy){
        this.customerLastUpdatedBy.set(LastUpdatedBy);
    }
    
}
