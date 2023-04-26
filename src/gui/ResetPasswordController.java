/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class ResetPasswordController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane resetPane ;
    @FXML
    private PasswordField tfPassword ;
    @FXML
    private PasswordField tfConfirmPassword ;
    @FXML
    private CheckBox show ;
    @FXML
    private Label visiblePassword ;
    
   ForgotController f = new ForgotController();

    public int id ;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      Platform.runLater(() -> {
        System.out.println(this.id);
    });  
        
       
    }    
    public  void setId(int id) {
       this.id = id;
    }
    public  int getId() {
        return id ;
    }

  public void showPassword(ActionEvent event){
      boolean isSelected = show.isSelected();
     if(isSelected) {
         visiblePassword.setText(tfPassword.getText()) ;
         tfPassword.textProperty().addListener((observable, oldValue, newValue) -> {
        visiblePassword.setText(newValue); });
         show.setText("hide password");
     }
     else {
         visiblePassword.setText("") ;
         show.setText("show password");
     }
  }
    public void updatePassword(ActionEvent event){
        
         String password = tfPassword.getText();
        String confirmpassword = tfConfirmPassword.getText();
        UserService service = new UserService() ;
        try{
            Alert alert; 
         if(password.isEmpty() || confirmpassword.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all the fields");
                alert.showAndWait();
         }
          else if(password.length() <8){
                
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("password must exceed 8 characters");
                    alert.showAndWait();
            }
            else if(!password.equals(confirmpassword)){
                
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("password and confirm password must match");
                    alert.showAndWait();
            }
            else {
        service.updatePass(this.getId() , password);
        alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("success");
                    alert.setHeaderText(null);
                    alert.setContentText("password updated");
                    alert.showAndWait();
            }
        }
        catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
        }
    }
    
    
    
    public void redirectToLogin(ActionEvent event)throws IOException {
      FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("login.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) resetPane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
    
    }
}
