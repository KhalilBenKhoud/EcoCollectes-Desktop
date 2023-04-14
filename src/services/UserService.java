/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author khalil
 */
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


public class UserService {
    Connection connexion;
    Statement stm;
    
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
                    rst.getString("username")
                   
            );
            users.add(p);
        }
        return users;

    }
    
        public void ajouter(User p) throws SQLException {

        String req = "INSERT INTO `user` (`username`, `email`, `phone`, `address`  ,  `password`,`image_filename`, `gender`) "
                + "VALUES ( ?, ?, ?,  ?, ?, ?, ?) ";
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
          

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
                    rst.getInt("id"),
                    rst.getString("username")
                   
            );
              }
            
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
               return p ;
           }
           
           

}
