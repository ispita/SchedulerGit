/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.ViewController;

import scheduler.Model.SqlQueries;
import scheduler.Model.User;
import utils.DBConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static scheduler.Model.SqlQueries.userCredential;


/**
 *
 * @author flavius8
 */
public class LoginController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    TextField passwordInput;
    @FXML
    TextField usernameInput;
    String username;
    String password;
    User currentUser = null;
    
    @FXML
    private void handleLoginButton(ActionEvent e)throws Exception{
        
        username = usernameInput.getText();
        password = passwordInput.getText();
        boolean credCheck = userCredential(username,password);
        if(credCheck == true){
        currentUser = SqlQueries.setCurrentUser(username);
            System.out.println(currentUser.getUsername().get());
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
        else{
            ResourceBundle rb = ResourceBundle.getBundle("language/rb");
            System.out.println(Locale.getDefault());
            System.out.println(usernameInput.getText());
            System.out.println(passwordInput.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("title"));
            alert.setHeaderText(rb.getString("header"));
            alert.setContentText(rb.getString("content"));
       
            alert.showAndWait();
        }
        
    }
    
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
