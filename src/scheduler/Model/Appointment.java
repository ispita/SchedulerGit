/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.Model;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
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
public class Appointment {
    
    private IntegerProperty appointmentID;
    private IntegerProperty customerID;
    private IntegerProperty userID;
    private StringProperty appointmentTitle;
    private StringProperty appointmentDescription;
    private StringProperty appointmentLocation;
    private StringProperty appointmentContact;
    private StringProperty appointmentType;
    private StringProperty appointmentUrl;
    private ObjectProperty<ZonedDateTime> appointmentStart; 
    private ObjectProperty<Timestamp> appointmentEnd; 
    private ObjectProperty<Timestamp> appointmentCreateDate; 
    private StringProperty appointmentCreatedBy;
    private ObjectProperty<Timestamp> appointmentLastUpdate;
    private StringProperty appointmentLastUpdatedBy;

    
    public Appointment (){
        this.appointmentID = new SimpleIntegerProperty();
        this.customerID = new SimpleIntegerProperty();
        this.userID = new SimpleIntegerProperty();
        this.appointmentTitle = new SimpleStringProperty();
        this.appointmentDescription = new SimpleStringProperty();
        this.appointmentLocation = new SimpleStringProperty();
        this.appointmentContact = new SimpleStringProperty();
        this.appointmentType = new SimpleStringProperty();
        this.appointmentUrl = new SimpleStringProperty();
        this.appointmentStart = new SimpleObjectProperty<>();
        this.appointmentEnd = new SimpleObjectProperty<>();
        this.appointmentCreateDate = new SimpleObjectProperty<>();
        this.appointmentCreatedBy = new SimpleStringProperty();
        this.appointmentLastUpdate = new SimpleObjectProperty<>(); 
        this.appointmentLastUpdatedBy = new SimpleStringProperty();
    }
    
    public IntegerProperty getAppointmentId(){
        return appointmentID;
    }
    public IntegerProperty getCustomerID(){
        return customerID;
    }
    public IntegerProperty getUserID(){
        return userID;
    }
    public StringProperty getAppointmentTitle(){
        return appointmentTitle;
    }
    public StringProperty getAppointmentDescription(){
        return appointmentDescription;
    }
    public StringProperty getAppointmentLocation(){
        return appointmentLocation;
    }
    public StringProperty getAppointmentContact(){
        return appointmentContact;
    }
    public StringProperty getAppointmentType(){
        return appointmentType;
    }
    public StringProperty getAppointmentUrl(){
        return appointmentUrl;
    }
    public ObjectProperty getAppointmentStart(){
        return appointmentStart;
    }
    public ObjectProperty getAppointmentEnd(){
        return appointmentEnd;
    }
    public ObjectProperty getAppointmentCreateDate(){
        return appointmentCreateDate;
    }
    public StringProperty getAppointmentCreatedBy(){
        return appointmentCreatedBy;
    }
    public ObjectProperty getAppointmentLastUpdate(){
        return appointmentLastUpdate;
    }
    public StringProperty getAppointmentLastUpdatedBy(){
        return appointmentLastUpdatedBy;
    }
    public void setAppointmentID(Integer ID){
        this.appointmentID.set(ID);
        
    }
    public void setCustomerID(Integer ID){
        this.customerID.set(ID);
    }
    public void setUserID(Integer ID){
        this.userID.set(ID);
    }
    public void setAppointmentTitle(String Title){
        this.appointmentTitle.set(Title);
    }
    public void setAppointmentDescription (String Desc){
        this.appointmentDescription.set(Desc);
    }
    public void setAppointmentLocation (String Location){
        this.appointmentLocation.set(Location);
    }
    public void setAppointmentContact (String Contact){
        this.appointmentContact.set(Contact);
    }
    public void setAppointmentType (String Type){
        this.appointmentType.set(Type);
    }
    public void setAppointmentUrl (String URL){
        this.appointmentUrl.set(URL);
    }
    public void setAppointmentStart(ZonedDateTime Start){
        this.appointmentStart.set(Start);
    }
    public void setAppointmentEnd(Timestamp End){
        this.appointmentEnd.set(End);
    }
    public void setAppointmentCreateDate(Timestamp CreateDate){
        this.appointmentCreateDate.set(CreateDate);
    }
    public void setAppointmentCreatedBy(String CreatedBy){
        this.appointmentCreatedBy.set(CreatedBy);
    }
    public void setAppointmentLastUpdate(Timestamp LastUpdate){
        this.appointmentLastUpdate.set(LastUpdate);
    }
    public void setAppointmentLastUpdatedBy(String LastUpdatedBy){
        this.appointmentLastUpdatedBy.set(LastUpdatedBy);
    }
    
}
