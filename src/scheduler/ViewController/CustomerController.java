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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import scheduler.Model.Customer;
import static scheduler.Model.SqlQueries.assembleCustomerData;
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
