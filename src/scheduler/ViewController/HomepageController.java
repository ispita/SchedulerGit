/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.ViewController;

import scheduler.Model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    public void setCurrentUser(User passCurrentUser){
        this.currentUser = passCurrentUser;
        System.out.println("Homepage User Set: " + currentUser.getUsername());
    }
}
