/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.mail.MessagingException;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class ForgotController implements Initializable {

     @FXML
     private Button validate ;
     @FXML
     private TextField email ;
     
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
             e.envoyerCodeVerif(email.getText());
             
         } catch (com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException ex) {
             Logger.getLogger(ForgotController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    
}
