/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {
    

    private Connection connection;
    static Database instance;
    
    private Database(){
        try {
      
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eco", "root", "");
            System.out.println("connexion établie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public static Database getInstance(){
        if(instance == null)
            instance = new Database();
       return instance;
    }

    public Connection getConnection() {
        return connection;
    }
    
}
