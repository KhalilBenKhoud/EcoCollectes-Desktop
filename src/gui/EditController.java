/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Outils;
import entities.User;
import java.io.File;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class EditController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private AnchorPane editPane ;
    @FXML
    private Button editBtn;
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
     @FXML
    private Button imageBtn ;
    
    private String image_filename  ;
    
    UserService s = new UserService() ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         User user = null ;
        try {
             user = s.findUserById(Outils.getCurrentSession()) ;
         } catch (SQLException ex) {
             Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         tfUsername.setText(user.getUsername()) ;
         tfEmail.setText(user.getEmail()) ;
          tfAddress.setText(user.getAddress()) ;
          tfPhone.setText(Integer.toString(user.getPhone())) ;
          tfGender.setText(user.getGender()) ;
          tfPassword.setText(user.getPassword()) ;
          tfConfirmPassword.setText(user.getPassword()) ;
           imageBtn.setText("old image") ;
           image_filename = user.getImage_filename() ;
    } 
    
       public void edit(ActionEvent event)throws IOException {
        
       
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
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all the fields");
                alert.showAndWait();
            }
            else if (!email.contains("@") || !email.contains(".") || !(email.indexOf(".")  > email.indexOf("@") + 1)  ) {
                 alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("invalid email");
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
                else{
                     int tel = Integer.parseInt(phone);
                    User newuser = new User(tel,username,email,address,password,gender,image_filename);
                   
                 
                    
                  
                   
                  
                    s.modifier(Outils.getCurrentSession(),newuser);
                    
                    redirectToProfile(event) ;
                
                    }
                }
                  catch(Exception ex){
                   Alert  alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("update error");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                   }
    
       }
    
    
         public void redirectToProfile(ActionEvent event) throws IOException {
             FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("profil.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) editPane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
     }
         
         
              public void upload(ActionEvent event) {
        FileChooser fc = new FileChooser();
        String imageFile = "";
        File f = fc.showOpenDialog(null);

        if (f != null) {
            imageFile = f.getAbsolutePath();
            System.out.println(imageFile);
           
            image_filename = imageFile ;
            imageBtn.setText(f.getName()) ;
        }
    }
    
}
