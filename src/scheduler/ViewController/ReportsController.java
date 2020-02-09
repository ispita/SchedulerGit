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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
public class ReportsController implements Initializable {
    User currentUser;
    String year;
    String month;
    String yearType;
    String monthType;
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
    private ComboBox<String> pickYearType;
    @FXML
    private ComboBox<String> pickMonthType;
    @FXML
    private ComboBox<String> pickYear;
    @FXML
    private ComboBox<String> pickMonth;    
    @FXML
    private ComboBox<String> customerSchedID;        
    @FXML
    private Label appointmentAmountLabel;
    @FXML
    private Label appointmentTypeLabel;

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
       start.setValue(null);

    }
    
   

    @FXML
    public void handleDeleteAppointmentButton(ActionEvent e){
     Appointment selectedAppointment =  appointmentTable.getSelectionModel().getSelectedItem();
     deleteAppointment(selectedAppointment.getAppointmentId().get());
     appointmentTable.setItems(assembleAppointmentsData());
    }
    
    @FXML
    public void handleModifyAppointmentButton(ActionEvent e)throws Exception{
        Appointment selectedAppointment =  appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null){
        Stage modifyAppointmentStage; 
        Parent modifyAppointmentRoot; 
        modifyAppointmentStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAppointment.fxml"));
        modifyAppointmentRoot = loader.load();
        ModifyAppointmentController controller = loader.getController();
        controller.setAppointment(selectedAppointment,currentUser);        
        Scene scene = new Scene(modifyAppointmentRoot);
        modifyAppointmentStage.setScene(scene);
        modifyAppointmentStage.show(); 
        }
        else{   
            ResourceBundle rb = ResourceBundle.getBundle("language/rb");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("sqltitle"));
            alert.setHeaderText(rb.getString("header"));
            alert.setContentText(rb.getString("modifyNull"));
            alert.showAndWait();
        }
            
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
    

        @FXML
    public void handleYearSelectType(ActionEvent e){
       if (pickYearType.getValue() == null){
          return;
       }else{
       yearType = pickYearType.getValue();       
       pickMonthType.setItems(getMonths(yearType));
       }
    }
    @FXML void handleFilterAppointmentsType(ActionEvent e){       
        monthType = pickMonthType.getValue();
        appointmentTypeLabel.setVisible(true);
        appointmentTypeLabel.setText(Integer.toString(assembleAppointmentsType(yearType, monthType)));
        pickYearType.getSelectionModel().clearSelection();
        pickMonthType.getSelectionModel().clearSelection();
        pickMonthType.setValue(null);
        pickMonthType.setItems(null);

    }
        @FXML void handleFilterAppointmentsAmount(ActionEvent e){
        
        month= pickMonth.getValue();
        appointmentAmountLabel.setVisible(true);        
        appointmentAmountLabel.setText(Integer.toString(assembleAppointmentsAmount(yearType, monthType)));
        pickYear.getSelectionModel().clearSelection();
        pickMonth.getSelectionModel().clearSelection();
        pickMonth.setValue(null);
        pickMonth.setItems(null);

    }
        
    
     @FXML void handleGetCustomerSchedule(ActionEvent e){
        Integer selectedCustomerID = Integer.parseInt(customerSchedID.getValue().substring(0, 1));
        appointmentTable.setItems(assembleAppointmentsPerCustomer(selectedCustomerID));
  }
    
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //These lambda expressions allow me to directly call getter functions from the class making the code more readable and more efficient.
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
        appointmentTable.setItems(assembleAppointmentsData());
        System.out.println("Appointment Table Contents: " + appointmentTable.getItems());
        customerSchedID.setItems(getCustomerName());
        pickYear.setItems(getYears());
        pickYearType.setItems(getYears());
    }   
    
    public void setCurrentUser(User passCurrentUser){
        this.currentUser = passCurrentUser;
        System.out.println("Homepage User Set: " + currentUser.getUsername());
    }
}
