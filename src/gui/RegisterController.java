/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import  entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import services.UserService ;
import com.sun.org.apache.xerces.internal.util.FeatureState;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DataBase;
/**
 * FXML Controller class
 *
 * @author khalil
 */
public class RegisterController implements Initializable {
   
    
    @FXML
    private Button registerBtn;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfGender;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfConfirmPassword;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void register(ActionEvent event)throws IOException {
        
       
        String username = tfUsername.getText();
        String email= tfEmail.getText() ;
        String address =tfAddress.getText();
        String gender=tfGender.getText();
        String password=tfPassword.getText();
        String confirmpassword=tfConfirmPassword.getText();
        String phone =tfPhone.getText();
      
        
        try{
            Alert alert; 
            if(username.isEmpty() || email.isEmpty() ||address.isEmpty() || password.isEmpty() || phone.isEmpty() || gender.isEmpty() || confirmpassword.isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all the fields");
                alert.showAndWait();
            }
            else if(password.length() <8){
                
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("password must exceed 8 characters");
                    alert.showAndWait();
            }
            else if(!password.equals(confirmpassword)){
                
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("password and confirm password must match");
                    alert.showAndWait();
            }
                else{
                     int tel = Integer.parseInt(phone);
                    User u = new User(tel,username,email,address,password,gender,"");
                    UserService s  =new UserService();
                    s.ajouter(u);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Ajout avec succés");
                    alert.showAndWait();
                
                    }
                }
                  catch(Exception e){
                       e.printStackTrace();
                    }
    }
    
}
