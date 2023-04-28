/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Outils;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class LoginController implements Initializable  {

    
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private AnchorPane rootPane;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
     @FXML
    public void login(ActionEvent event) throws IOException {

        String email = tfEmail.getText() ;
        String password = tfPassword.getText() ;
        
        
        try {
        UserService s = new UserService() ;
        int valid = s.compteExiste(email, password);
        if(valid == 0)
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("invalid credentials");
                    alert.showAndWait();
                    
                       
                  
        }
        else if(s.findUserByEmail(email).isBanned()) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("you're banned");
                    alert.showAndWait();
        }
        else {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("succesful login");
                    alert.showAndWait(); 
                    Outils.start(valid);
                    System.out.println(Outils.getCurrentSession());
                       FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("profil.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) rootPane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
        }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
      public void redirectToRegister(ActionEvent event) throws IOException {
             FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("register.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) rootPane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
     }
      
         public void redirectToForgot(ActionEvent event) throws IOException {
             FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("forgot.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) rootPane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
     }
    
}
