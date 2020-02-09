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
import java.util.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scheduler.Model.Appointment;
import static scheduler.Model.SqlQueries.*;
import scheduler.Model.User;

/**
 * FXML Controller class
 *
 * @author flavius8
 */
public class AppointmentsController implements Initializable {
    User currentUser;
    String year;
    String month;
    String week;
    private ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentID;
    @FXML
    private TableColumn<Appointment, Integer> customerID;
    @FXML
    private TableColumn<Appointment, Integer> userID;
    @FXML
    private TableColumn<Appointment, String> appointmentTitle;
    @FXML
    private TableColumn<Appointment, String> appointmentDescription;    
    @FXML
    private TableColumn<Appointment, String> appointmentLocation;
    @FXML
    private TableColumn<Appointment, String> appointmentContact;
    @FXML
    private TableColumn<Appointment, String> appointmentType; 
    @FXML
    private TableColumn<Appointment, String> appointmentURL;
    @FXML
    private TableColumn<Appointment, Timestamp> appointmentStart;
    @FXML
    private TableColumn<Appointment, Timestamp> appointmentEnd;
    @FXML
    private TableColumn<Appointment, Timestamp> appointmentCreateDate;
    @FXML
    private TableColumn<Appointment, String> appointmentCreatedBy;
    @FXML
    private TableColumn<Appointment, Timestamp> appointmentLastUpdate;
    @FXML
    private TableColumn<Appointment, String> appointmentLastUpdateBy;
    @FXML
    Pane addAppointmentPane;
    @FXML
    TextField custID;
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
    @FXML
    private ComboBox<String> pickYear;
    @FXML
    private ComboBox<String> pickWeek;
    @FXML
    private ComboBox<String> pickMonth;    
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();
    ObservableList<String> length = FXCollections.observableArrayList();
    boolean addDisabled = true;
    boolean addVisible = false;
    Integer apptLength;
    @FXML
    public void handleAddAppointmentButton(ActionEvent e){
        System.out.println("You Pushed Add Appointment Button");
       addDisabled = false;
       addVisible = true;
       addAppointmentPane.setDisable(addDisabled);
       addAppointmentPane.setVisible(addVisible);
       custID.setText("");
       title.setText("");
       description.setText("");
       location.setText("");
       contact.setText("");
       type.setText("");
       url.setText("");
       //end.setValue(null);
       start.setValue(null);
    }
    
    @FXML 
    public void handleAddAppointmentCancelButton(ActionEvent e){
       addDisabled = true;
       addVisible = false;
       addAppointmentPane.setDisable(addDisabled);
       addAppointmentPane.setVisible(addVisible);
       custID.setText("");
       title.setText("");
       description.setText("");
       location.setText("");
       contact.setText("");
       type.setText("");
       url.setText("");
       //end.setValue(null);
       start.setValue(null);
    }
    
    @FXML
    public void handleAddApointmentSaveButton(ActionEvent e){
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
        System.out.println("The start date/time is: " + startDateTime + " and will last: " + apptLength);
     addAppointment(Integer.parseInt(custID.getText()),currentUser.getUserId().get(),title.getText(),description.getText(),location.getText(),
             contact.getText(),type.getText(),url.getText(),currentDateTime,apptLength,currentUser.getUsername().get());
     appointmentTable.setItems(assembleAppointmentsData());
    }

    @FXML
    public void handleDeleteAppointmentButton(ActionEvent e){
     Appointment selectedAppointment =  appointmentTable.getSelectionModel().getSelectedItem();
     deleteAppointment(selectedAppointment.getAppointmentId().get());
     appointmentTable.setItems(assembleAppointmentsData());
    }
    
    @FXML
    public void handleModifyAppointmentButton(ActionEvent e)throws Exception{
        Stage modifyAppointmentStage; 
        Parent modifyAppointmentRoot; 
        modifyAppointmentStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAppointment.fxml"));
        modifyAppointmentRoot = loader.load();
        Scene scene = new Scene(modifyAppointmentRoot);
        modifyAppointmentStage.setScene(scene);
        modifyAppointmentStage.show(); 
     
        ModifyAppointmentController controller = loader.getController();
        Appointment selectedAppointment =  appointmentTable.getSelectionModel().getSelectedItem();
        controller.setAppointment(selectedAppointment,currentUser);
    }
    
    @FXML
    public void handleExitButton(ActionEvent e)throws Exception{
        Stage homepageStage; 
        Parent homepageRoot; 
        homepageStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
        homepageRoot = loader.load();
        
        HomepageController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Scene homepageScene = new Scene(homepageRoot);
        homepageStage.setScene(homepageScene);           
        homepageStage.show(); 
    }
    
    @FXML
    public void handleYearSelect(ActionEvent e){
       if (pickYear.getValue() == null){
          return;
       }else{
       year = pickYear.getValue();       
       pickMonth.setItems(getMonths(year));
       }
    }
    
    @FXML void handleMonthSelect (ActionEvent e){
               if (pickYear.getValue() == null){
          return;
       }else{
        month = pickMonth.getValue();
        pickWeek.setItems(getWeeks(pickYear.getValue(),month));
               }
    }
    @FXML void handleFilterAppointments(ActionEvent e){
  
        week = pickWeek.getValue();
        System.out.println("Week value: " + week);
        appointmentTable.setItems(assembleAppointmentsFilteredData(year,month,week));
         pickYear.getSelectionModel().clearSelection();
        pickWeek.getSelectionModel().clearSelection();
        pickWeek.setValue(null);
        pickWeek.setItems(null);
        pickMonth.getSelectionModel().clearSelection();
        pickMonth.setValue(null);
        pickMonth.setItems(null);

    }
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentID.setCellValueFactory(cellData -> cellData.getValue().getAppointmentId().asObject());
        customerID.setCellValueFactory(cellData -> cellData.getValue().getCustomerID().asObject());      
        userID.setCellValueFactory(cellData -> cellData.getValue().getUserID().asObject());
        appointmentTitle.setCellValueFactory(cellData -> cellData.getValue().getAppointmentTitle());
        appointmentDescription.setCellValueFactory(cellData -> cellData.getValue().getAppointmentDescription());
        appointmentLocation.setCellValueFactory(cellData -> cellData.getValue().getAppointmentLocation());
        appointmentContact.setCellValueFactory(cellData -> cellData.getValue().getAppointmentContact());
        appointmentType.setCellValueFactory(cellData -> cellData.getValue().getAppointmentType());
        appointmentURL.setCellValueFactory(cellData -> cellData.getValue().getAppointmentUrl());
        appointmentStart.setCellValueFactory(cellData -> cellData.getValue().getAppointmentStart());
        appointmentEnd.setCellValueFactory(cellData -> cellData.getValue().getAppointmentEnd());
        appointmentCreateDate.setCellValueFactory(cellData -> cellData.getValue().getAppointmentCreateDate());
        appointmentCreatedBy.setCellValueFactory(cellData -> cellData.getValue().getAppointmentCreatedBy());
        appointmentLastUpdate.setCellValueFactory(cellData -> cellData.getValue().getAppointmentLastUpdate());
        appointmentLastUpdateBy.setCellValueFactory(cellData -> cellData.getValue().getAppointmentLastUpdatedBy());
        appointmentTable.setItems(assembleAppointmentsData());
        System.out.println("Appointment Table Contents: " + appointmentTable.getItems());
        hours.addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17");
        minutes.addAll("00", "15", "30", "45");
        length.addAll("15", "30", "45","60");
        pickHour.setItems(hours);
        pickMin.setItems(minutes);
        pickLength.setItems(length);
        pickYear.setItems(getYears());
    }   
    
    public void setCurrentUser(User passCurrentUser){
        this.currentUser = passCurrentUser;
        System.out.println("Homepage User Set: " + currentUser.getUsername());
    }
}
