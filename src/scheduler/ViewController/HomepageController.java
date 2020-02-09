/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.ViewController;

import scheduler.Model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import static scheduler.Model.SqlQueries.checkForAppointmentsLogin;

/**
 * FXML Controller class
 *
 * @author flavius8
 */
public class HomepageController implements Initializable {
User currentUser;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rba) {
        ObservableList checkAppointments = checkForAppointmentsLogin();
            if(checkAppointments.isEmpty() != true){
            ResourceBundle rb = ResourceBundle.getBundle("language/rb");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("appointmentSoonTitle"));
            alert.setHeaderText(rb.getString("appointmentSoonHeader"));
            alert.setContentText("AppointmentId: " + checkAppointments + " is scheduled to begin within the next 15 minutes!");
       
            alert.showAndWait();
        System.out.println("Appointments in 15 min or less: " + checkAppointments);
            }
    }
    
    @FXML
    public void handleViewCustomersButton(ActionEvent e)throws Exception{
        Stage viewCustomersStage; 
        Parent viewCustomersRoot; 
        viewCustomersStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        viewCustomersRoot = loader.load();
        
        CustomerController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Scene viewCustomersScene = new Scene(viewCustomersRoot);
        viewCustomersStage.setScene(viewCustomersScene);           
        viewCustomersStage.show(); 
        
    }
    
    @FXML
    public void handleViewAppointmentsButton(ActionEvent e)throws Exception{
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
    public void handleViewReportsButton(ActionEvent e)throws Exception{
        Stage viewReportsStage; 
        Parent viewReportsRoot; 
        viewReportsStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reports.fxml"));
        viewReportsRoot = loader.load();
        
        ReportsController controller = loader.getController();
        controller.setCurrentUser(currentUser);
        Scene viewReportsScene = new Scene(viewReportsRoot);
        viewReportsStage.setScene(viewReportsScene);           
        viewReportsStage.show(); 
    }
    
    public void setCurrentUser(User passCurrentUser){
        this.currentUser = passCurrentUser;
        System.out.println("Homepage User Set: " + currentUser.getUsername());
    }
}
