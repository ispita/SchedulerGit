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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scheduler.Model.Customer;
import static scheduler.Model.SqlQueries.addCustomer;
import static scheduler.Model.SqlQueries.assembleCustomerData;
import static scheduler.Model.SqlQueries.deleteCustomer;
import scheduler.Model.User;

/**
 * FXML Controller class
 *
 * @author flavius8
 */
public class CustomerController implements Initializable {
    User currentUser;
    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, Integer> customerAddressID;
    @FXML
    private TableColumn<Customer, Integer> customerActive;
    @FXML
    private TableColumn<Customer, Timestamp> customerCreateDate;
    @FXML
    private TableColumn<Customer, String> customerCreatedBy;
    @FXML
    private TableColumn<Customer, Timestamp> customerLastUpdate;
    @FXML
    private TableColumn<Customer, String> customerLastUpdateBy;
    boolean addDisabled = true;
    boolean addVisible = false;
    
    @FXML
    Pane addCustomerPane;

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
    


    
    @FXML
    public void handleAddCustomerButton(ActionEvent e){
        System.out.println("You Pushed Add Customer Button");
       addDisabled = false;
       addVisible = true;
       addCustomerPane.setDisable(addDisabled);
       addCustomerPane.setVisible(addVisible);
    }
    
    @FXML 
    public void handleAddCustomerCancelButton(ActionEvent e){
       addDisabled = true;
       addVisible = false;
       addCustomerPane.setDisable(addDisabled);
       addCustomerPane.setVisible(addVisible);
        name.setText("");
        phone.setText("");
        active.setText("");
        address.setText("");
        address2.setText("");
        cityId.setText("");
        postalCode.setText("");
    }
        @FXML
    public void handleModifyCustomerButton(ActionEvent e)throws Exception{
        Stage modifyCustomerStage; 
        Parent modifyCustomerRoot; 
        modifyCustomerStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCustomer.fxml"));
        modifyCustomerRoot = loader.load();
        Scene scene = new Scene(modifyCustomerRoot);
        modifyCustomerStage.setScene(scene);
        modifyCustomerStage.show(); 
     
        ModifyCustomerController controller = loader.getController();
        Customer selectedCustomer =  customerTable.getSelectionModel().getSelectedItem();
        controller.setCustomer(selectedCustomer,currentUser);
    }
    @FXML
    public void handleAddCustomerSaveButton(ActionEvent e){
        try{
     addCustomer(name.getText(),Integer.parseInt(active.getText()),currentUser.getUsername().get(),address.getText(),address2.getText(),Integer.parseInt(cityId.getText()),
             Integer.parseInt(postalCode.getText()),phone.getText());
     customerTable.setItems(assembleCustomerData());
        name.setText("");
        phone.setText("");
        active.setText("");
        address.setText("");
        address2.setText("");
        cityId.setText("");
        postalCode.setText("");
        }
        catch(Exception ex){
            ResourceBundle rb = ResourceBundle.getBundle("language/rb");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("sqltitle"));
            alert.setHeaderText(rb.getString("header"));
            alert.setContentText(rb.getString("sqlcontent"));
            alert.showAndWait(); 
        }
    }

    @FXML
    public void handleDeleteCustomerButton(ActionEvent e){
     Customer selectedCustomer =  customerTable.getSelectionModel().getSelectedItem();
     deleteCustomer(selectedCustomer.getCustomerId().get(),selectedCustomer.getCustomerAddressId().get());
     customerTable.setItems(assembleCustomerData());
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
        customerID.setCellValueFactory(cellData -> cellData.getValue().getCustomerId().asObject());
        customerName.setCellValueFactory(cellData -> cellData.getValue().getCustomerName());      
        customerAddressID.setCellValueFactory(cellData -> cellData.getValue().getCustomerAddressId().asObject());
        customerActive.setCellValueFactory(cellData -> cellData.getValue().getCustomerActive().asObject());
        customerCreateDate.setCellValueFactory(cellData -> cellData.getValue().getCustomerCreateDate());
        customerCreatedBy.setCellValueFactory(cellData -> cellData.getValue().getCustomerCreatedBy());
        customerLastUpdate.setCellValueFactory(cellData -> cellData.getValue().getCustomerLastUpdate());
        customerLastUpdateBy.setCellValueFactory(cellData -> cellData.getValue().getCustomerLastUpdatedBy());
        customerTable.setItems(assembleCustomerData());
        System.out.println("Customer Table Contents: " + customerTable.getItems());
    }   
    
    public void setCurrentUser(User passCurrentUser){
        this.currentUser = passCurrentUser;
        System.out.println("Homepage User Set: " + currentUser.getUsername());
    }
}
