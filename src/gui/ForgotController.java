/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class ForgotController implements Initializable {

    
    @FXML
     private AnchorPane forgotPane ;
     @FXML
     private Button validate ;
     @FXML
     private TextField tfEmail ;
      @FXML
     private TextField tfCode ;
       @FXML
     private Button next ;
     
       UserService e = new UserService();

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
        public void SendMaill(ActionEvent event) throws IOException, MessagingException {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
         try {
             e.envoyerCodeVerif(tfEmail.getText());
             
         } catch (com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException ex) {
             Logger.getLogger(ForgotController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
        
        public  boolean isNumeric(String str) {
    try {
        int i = Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}
        
        public void checkCode(ActionEvent event) throws IOException, SQLException {
             Stage stage = (Stage) forgotPane.getScene().getWindow();
            
            if(tfCode.getText().isEmpty() || !isNumeric(tfCode.getText()) )
             {
                  Alert  alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("code error");
                    alert.setContentText("please enter a code");
                    alert.showAndWait();
             }
             
            if ( e.getRandomCode() ==  Integer.parseInt(tfCode.getText() ) ){
              
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("resetPassword.fxml"));
                 
                    Parent root = loader.load();
                    
                    ResetPasswordController controller = new ResetPasswordController() ;
               
                 
                controller.setId(e.findUserByEmail(tfEmail.getText()).getId());
                  
                loader.setController(controller) ;
                 
                 tfCode.getScene().setRoot(root);


                 
             } 
           
             else {
                     Alert  alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("code error");
                    alert.setContentText("invalid code");
                    alert.showAndWait();
             }
        }
        
        
            public void redirectToLogin(ActionEvent event)throws IOException {
      FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("login.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) forgotPane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
    
    }
                   public void redirectToReset(ActionEvent event)throws IOException {
      FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("resetPassword.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) forgotPane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
    
    }

    
}
