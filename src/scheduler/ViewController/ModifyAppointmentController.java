/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.ViewController;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    TextField start;
    @FXML
    TextField end;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void handleModifyAppointmentSaveButton(ActionEvent e)throws Exception{
        modifyAppointment(modifiedAppointment.getAppointmentId().get(),Integer.parseInt(custID.getText()),
             currentUser.getUserId().get(),title.getText(),description.getText(),location.getText(),
             contact.getText(),type.getText(),url.getText(),Timestamp.valueOf(start.getText()),Timestamp.valueOf(end.getText()),currentUser.getUsername().get());
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
       start.setText(appointmentTimes.get(0).toString());
       end.setText(appointmentTimes.get(1).toString());
       
       
    }
    
}
