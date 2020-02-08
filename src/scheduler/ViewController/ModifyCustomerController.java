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
import scheduler.Model.Customer;
import scheduler.Model.User;
import static scheduler.Model.SqlQueries.*;

/**
 * FXML Controller class
 *
 * @author flavius8
 */
public class ModifyCustomerController implements Initializable {
Customer modifiedCustomer;
User currentUser;
Integer addressID;
ObservableList customerAddress;

    @FXML
    TextField name;
    @FXML
    TextField phone;
    @FXML
    TextField active;
    @FXML
    TextField address;
    @FXML
    TextField address2;
    @FXML
    TextField cityId;
    @FXML
    TextField postalCode;
    

    

    /**
     * Initializes the controller class.
     */
    @FXML
    public void handleModifyCustomerSaveButton(ActionEvent e)throws Exception{
        modifyCustomer(modifiedCustomer.getCustomerId().get(),name.getText(),addressID,Integer.parseInt(active.getText()),address.getText(),address2.getText(),
                Integer.parseInt(cityId.getText()),Integer.parseInt(postalCode.getText()),phone.getText(),currentUser.getUsername().get());
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
    public void handleModifyCustomerCancelButton(ActionEvent e)throws Exception{
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       
       
    } 
    
    
    public void setCustomer(Customer selectedCustomer, User passCurrentUser){
        this.modifiedCustomer = selectedCustomer;
        this.currentUser = passCurrentUser;

       name.setText(modifiedCustomer.getCustomerName().get());
       addressID = modifiedCustomer.getCustomerAddressId().get();
       active.setText(Integer.toString(modifiedCustomer.getCustomerActive().get()));
       customerAddress = getCustomerAddress(addressID);
       address.setText(customerAddress.get(0).toString());
       address2.setText(customerAddress.get(1).toString());
       cityId.setText(customerAddress.get(2).toString());
       postalCode.setText(customerAddress.get(3).toString());
       phone.setText(customerAddress.get(4).toString());
             
    }
    
}
