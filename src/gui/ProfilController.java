/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Outils;
import entities.User;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class ProfilController implements Initializable {

    
    @FXML
    private Label id ;
    @FXML
    private Label username ;
     @FXML
    private Label email ; 
      @FXML
    private Label gender ;
       @FXML
    private Label address ;
        @FXML
    private Label phone ;
    @FXML
    private Label password ;
    @FXML
    private AnchorPane profilePane ;
     @FXML
    private ImageView profileImage ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          UserService s = new UserService() ;
        int iduser = Outils.getCurrentSession() ;
        User user = null ;
        try {
             user = s.findUserById(iduser) ;
        } catch (SQLException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        id.setText(Integer.toString(iduser)) ;
        username.setText(user.getUsername()) ;
        email.setText(user.getEmail()) ;
        address.setText(user.getAddress()) ;
        phone.setText(Integer.toString(user.getPhone())) ;
        gender.setText(user.getGender()) ;
        password.setText(user.getPassword()) ;
        System.out.println("path :"+user.getImage_filename());
        try {
            FileInputStream stream ;
           if(user.getImage_filename().length() > 40)
           {  stream = new FileInputStream(user.getImage_filename()); }
           else {
              stream = new FileInputStream("C:\\wamp64\\www\\Eco-Collectes-Web\\public\\front\\images\\"+user.getImage_filename()); 
           }
            Image image = new Image(stream);

          profileImage.setImage(image) ;
           
       
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }   
    @FXML
      public void redirectToEdit(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("edit.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) profilePane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
    }   
    
       @FXML
    public void logout(ActionEvent event) throws Exception {
        Outils.close();
          try{
            FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("login.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) profilePane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
                
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

      public void delete(ActionEvent event) throws IOException {
        UserService s = new UserService() ;
        int iduser = Outils.getCurrentSession() ;
        try {
            s.delete(iduser) ;
            
             FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("edit.fxml"));
    Parent root = loader.load();
    Scene mainScene = new Scene(root);


    Stage primaryStage = (Stage) profilePane.getScene().getWindow();
    primaryStage.setScene(mainScene);
    primaryStage.show();
    
    
            
        } catch (SQLException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
}
