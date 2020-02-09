/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.ViewController;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import scheduler.Model.Appointment;
import scheduler.Model.User;
import static scheduler.Model.SqlQueries.*;

/**
 * FXML Controller class
 *
 * @author flavius8
 */
public class ModifyAppointmentController implements Initializable {
Appointment modifiedAppointment;
User currentUser;

    @FXML
    TextField custID;
    @FXML
    TextField userID;
    @FXML
    TextField title;
    @FXML
    TextField description;
    @FXML
    TextField location;
    @FXML
    TextField contact;
    @FXML
    TextField type;
    @FXML
    TextField url;
    @FXML
    DatePicker start;
    @FXML
    private ComboBox<String> pickHour;
    @FXML
    private ComboBox<String> pickMin;
    @FXML
    private ComboBox<String> pickLength;
    Integer apptLength;
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();
    ObservableList<String> length = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @FXML
    public void handleModifyAppointmentSaveButton(ActionEvent e)throws Exception{
             LocalDate startDate = start.getValue();
     String hour = pickHour.getValue();
     String minute = pickMin.getValue();
     apptLength = Integer.parseInt(pickLength.getValue());
     LocalDateTime startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), Integer.parseInt(hour), Integer.parseInt(minute));

      LocalTime currentLocalTime = startDateTime.toLocalTime();
      ZoneId dbZoneId = ZoneId.of("America/Chicago");
      ZonedDateTime currentDateZDT = ZonedDateTime.of(startDate,currentLocalTime,ZoneId.of(TimeZone.getDefault().getID()));
      Instant currentDateInstant = currentDateZDT.toInstant();
      ZonedDateTime currentDateTime = currentDateInstant.atZone(dbZoneId); 
        modifyAppointment(modifiedAppointment.getAppointmentId().get(),Integer.parseInt(custID.getText()),
             currentUser.getUserId().get(),title.getText(),description.getText(),location.getText(),
             contact.getText(),type.getText(),url.getText(),currentDateTime,apptLength,currentUser.getUsername().get());
        Stage viewAppointmentsStage; 
        Parent viewAppointmentsRoot; 
        viewAppointmentsStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Appointments.fxml"));
        viewAppointmentsRoot = loader.load();
        
        AppointmentsController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Scene viewAppointmentsScene = new Scene(viewAppointmentsRoot);
        viewAppointmentsStage.setScene(viewAppointmentsScene);           
        viewAppointmentsStage.show(); 
    }
    
    @FXML
    public void handleModifyAppointmentsCancelButton(ActionEvent e)throws Exception{
        Stage viewAppointmentsStage; 
        Parent viewAppointmentsRoot; 
        viewAppointmentsStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Appointments.fxml"));
        viewAppointmentsRoot = loader.load();
        
        AppointmentsController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Scene viewAppointmentsScene = new Scene(viewAppointmentsRoot);
        viewAppointmentsStage.setScene(viewAppointmentsScene);           
        viewAppointmentsStage.show(); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       
       
    } 
    
    
    public void setAppointment(Appointment selectedAppointment, User passCurrentUser){
        this.modifiedAppointment = selectedAppointment;
         this.currentUser = passCurrentUser;
       custID.setText(Integer.toString(modifiedAppointment.getCustomerID().get()));
       userID.setText(Integer.toString(currentUser.getUserId().get()));
       title.setText(modifiedAppointment.getAppointmentTitle().get());
       description.setText(modifiedAppointment.getAppointmentDescription().get());
       location.setText(modifiedAppointment.getAppointmentLocation().get());
       contact.setText(modifiedAppointment.getAppointmentContact().get());
       type.setText(modifiedAppointment.getAppointmentType().get());
       this.url.setText(modifiedAppointment.getAppointmentUrl().get());
       ObservableList appointmentTimes = getStartEndTimesAppointment(modifiedAppointment.getAppointmentId().get());
       LocalDateTime apptStart = LocalDateTime.parse(appointmentTimes.get(0).toString(), DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss.s"));
        hours.addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17");
        minutes.addAll("00", "15", "30", "45");
        length.addAll("15", "30", "45","60");
        pickHour.setItems(hours);
        pickMin.setItems(minutes);
        pickLength.setItems(length);
       start.setValue(apptStart.toLocalDate());
       pickMin.setValue(Integer.toString(apptStart.toLocalTime().getMinute()));
       pickHour.setValue(Integer.toString(apptStart.toLocalTime().getHour()));
       pickLength.setValue(appointmentTimes.get(1).toString());
       
       
    }
    
}
