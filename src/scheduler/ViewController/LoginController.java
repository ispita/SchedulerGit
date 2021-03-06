/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler.ViewController;

import java.io.File;
import java.io.IOException;
import scheduler.Model.SqlQueries;
import scheduler.Model.User;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
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
    ResourceBundle rb = ResourceBundle.getBundle("language/rb");
    String uLabel;
    String pLabel;
    @FXML
    Label userLabel;
    @FXML
    Label passLabel;
    @FXML
    TextField passwordInput;
    @FXML
    TextField usernameInput;
    String username;
    String password;
    User currentUser = null;
    static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @FXML
    private void handleLoginButton(ActionEvent e)throws Exception{
        
        username = usernameInput.getText();
        password = passwordInput.getText();
        boolean credCheck = userCredential(username,password);
        if(credCheck == true){
        currentUser = SqlQueries.setCurrentUser(username);
            System.out.println(currentUser.getUsername().get());
        File file = new File("userLog.txt");
        file.createNewFile();
        try {
              Files.write(Paths.get("userLog.txt"), ("User: "+ username +" Login Time: " + timestamp +"").getBytes(), StandardOpenOption.APPEND);
             }catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
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
            System.out.println(Locale.getDefault());
            this.uLabel = this.rb.getString("username");
            this.pLabel = this.rb.getString("password");
            this.userLabel.setText(uLabel);
            this.passLabel.setText(pLabel);
    }    
    
}
