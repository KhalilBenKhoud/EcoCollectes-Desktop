/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GAMERS
 */
public class DataBase {
    
    private final String URL = "jdbc:mysql://localhost:3306/eco";
    private final String USER = "root";
    private final String PWD = "";
    
    private Connection cnx;
    
    private static DataBase instance;
    
    /////////////////////
    
    private DataBase(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connnection success!");
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DataBase getInstance(){
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
    
    ////////////////////

    public Connection getCnx() {
        return cnx;
    }
    
    
    
}
