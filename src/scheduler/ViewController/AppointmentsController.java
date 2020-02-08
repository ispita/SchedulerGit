/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.ViewController;


import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static jdk.nashorn.internal.runtime.JSType.toInteger;
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
    TextField start;
    @FXML
    TextField end;

    
    boolean addDisabled = true;
    boolean addVisible = false;
    
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
       start.setText("");
       end.setText("");
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
       start.setText("");
       end.setText("");
    }
    
    @FXML
    public void handleAddApointmentSaveButton(ActionEvent e){
     addAppointment(Integer.parseInt(custID.getText()),currentUser.getUserId().get(),title.getText(),description.getText(),location.getText(),
             contact.getText(),type.getText(),url.getText(),Timestamp.valueOf(start.getText()),Timestamp.valueOf(end.getText()),currentUser.getUsername().get());
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
    }   
    
    public void setCurrentUser(User passCurrentUser){
        this.currentUser = passCurrentUser;
        System.out.println("Homepage User Set: " + currentUser.getUsername());
    }
}
