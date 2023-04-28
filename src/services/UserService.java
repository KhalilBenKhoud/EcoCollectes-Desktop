/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author khalil
 */
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import entities.User;
import utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;
import javafx.scene.control.Alert;


public class UserService {
    Connection connexion;
    Statement stm;
    private int randomCode;

    public int getRandomCode() {
        return randomCode;
    }
    
    public UserService() {
        connexion = DataBase.getInstance().getConnection();
    }
       
    
    public List<User> afficher() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "select * from user";
         PreparedStatement stm = connexion.prepareStatement(req);
        //ensemble de resultat
        ResultSet rst = stm.executeQuery();

        while (rst.next()) {
            User p;
            p = new User(
                    rst.getInt("id"),
                    rst.getString("username"),
                    rst.getBoolean("banned")
            );
            users.add(p);
        }
        return users;

    }
    
        public void ajouter(User p) throws SQLException {

        String req = "INSERT INTO `user` (`username`, `email`, `phone`, `address`  ,  `password`,`image_filename`, `gender`,`banned`) "
                + "VALUES ( ?, ?, ?,  ?, ?, ?, ?, ?) ";
        try {
            PreparedStatement ps = connexion.prepareStatement(req);
            ps.setString(1, p.getUsername());
            ps.setString(2, p.getEmail());
            ps.setInt(3, p.getPhone());
            ps.setString(4, p.getAddress());
          //  ps.setObject(5,  p.getRoles() );
             ps.setString(5, p.getPassword());
            ps.setString(6, p.getImage_filename());
            ps.setString(7, p.getGender());
           ps.setBoolean(8, p.isBanned());

            ps.executeUpdate();
            
             
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("succesfully registered");
                    alert.showAndWait(); 
                    
        } catch (SQLException ex) {
              Alert  alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("registration error");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
        }
    }
        
            public void delete(int id) throws SQLException {
        try {
            PreparedStatement pre = connexion.prepareStatement("Delete from user where id=? ;");
            pre.setInt(1, id);
            if (pre.executeUpdate() != 0) {
                System.out.println("user Deleted");

            }

        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
           
           public User findUserById(int id) throws SQLException {
               User p = null ;
               try {
             String req="select * from user where id = ?" ;
            PreparedStatement pst = connexion.prepareStatement(req);
             pst.setInt(1, id); 
         
          
             ResultSet rst = pst.executeQuery();
              while (rst.next()) {
                p = new User(
                    rst.getInt("id") ,
                    rst.getInt("phone"),
                    rst.getString("username"),
                    rst.getString("email"),
                    rst.getString("address"),
                    rst.getString("password"),
                     rst.getString("gender"),
                     rst.getString("image_filename"),
                     rst.getBoolean("banned")
            );
              }
            
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
               return p ;
           }
                 public User findUserByEmail(String email) throws SQLException {
               User p = null ;
               try {
             String req="select * from user where email = ?" ;
            PreparedStatement pst = connexion.prepareStatement(req);
             pst.setString(1, email); 
         
          
             ResultSet rst = pst.executeQuery();
              while (rst.next()) {
                p = new User(
                    rst.getInt("id") ,
                    rst.getInt("phone"),
                    rst.getString("username"),
                    rst.getString("email"),
                    rst.getString("address"),
                    rst.getString("password"),
                     rst.getString("gender"),
                     rst.getString("image_filename"),
                      rst.getBoolean("banned")
            );
              }
            
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
               return p ;
           }
                 
            public void ban(int id) throws SQLException {
                String req ="update user set banned=? where id=?";
                PreparedStatement pst = connexion.prepareStatement(req);
                 pst.setBoolean(1, true );
                  pst.setInt(2, id);
                  pst.executeUpdate();
            }     
                 public void allow(int id) throws SQLException {
                String req ="update user set banned=? where id=?";
                PreparedStatement pst = connexion.prepareStatement(req);
                 pst.setBoolean(1, false );
                  pst.setInt(2, id);
                  pst.executeUpdate();
            } 
           
             public void modifier(int id, User newuser) {
        try {
            String req ="update user set username=?,email=?,phone=?,address=?,password=?,image_filename=?,gender=? where id=?";
            PreparedStatement pst = connexion.prepareStatement(req);
            pst.setString(1, newuser.getUsername());
            pst.setString(2, newuser.getEmail());
            pst.setInt(3, newuser.getPhone());
            pst.setString(4, newuser.getAddress());
            pst.setString(5, newuser.getPassword());
            pst.setString(6, newuser.getImage_filename());
            pst.setString(7, newuser.getGender());
             pst.setInt(8, id);
            pst.executeUpdate();
            
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("succesfully updated");
                    alert.showAndWait(); 
           
            System.out.println("Utlisateur est modifié");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                   Alert  alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("update error");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
    }}
             
             public void updateEmail(int id,String email) throws SQLException {
                 String req ="update user set email=? where id=?";
            PreparedStatement pst = connexion.prepareStatement(req);
             pst.setString(1, email);
             pst.setInt(2,id) ;
             pst.executeUpdate();
             }
              
             public void updatePass(int id,String password) throws SQLException {
             String req ="update user set password=? where id=?";
            PreparedStatement pst = connexion.prepareStatement(req);
             pst.setString(1, password);
             pst.setInt(2,id) ;
             pst.executeUpdate();
             }
             
             public int compteExiste(String email, String password) {
                 int n = 0 ;
                 String req = "select * from user where email=? and password=?" ;
        try {
            PreparedStatement pst = connexion.prepareStatement(req);
            pst.setString(1,email) ;
            pst.setString(2,password) ;
             ResultSet rst = pst.executeQuery();
             if(rst.next()) {
                 n = rst.getInt(1) ;
             }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                 
             return n ;    
         }
             
        
          public void envoyerCodeVerif(String email) throws MessagingException {
        Random rand = new Random();
         randomCode = rand.nextInt(999999);
        String subject = "Reseting Code";
        String message = "Your reset code is " + randomCode + "";

        try {
            JavaMailUtil.sendMail(email, subject, message);
        } catch (javax.mail.MessagingException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
          
                public ObservableList<User> show() throws SQLException {
          ObservableList<User> users = FXCollections.observableArrayList();
        String req = "select * from user";
        stm = connexion.createStatement();
        //ensemble de resultat
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            User p;
            p = new User(
                  rst.getInt("id") ,
                    rst.getInt("phone"),
                    rst.getString("username"),
                    rst.getString("email"),
                    rst.getString("address"),
                    rst.getString("password"),
                     rst.getString("gender"),
                     rst.getString("image_filename"),
                      rst.getBoolean("banned")
                    
            );
            users.add(p);
        }
        return users;  
    }

}
