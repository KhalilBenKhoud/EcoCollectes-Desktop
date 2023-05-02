/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableView;


import javafx.scene.control.TextField;

import models.Collects;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;



/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class AddCollectController implements Initializable {

    @FXML
    private TextField nameFld;
    @FXML
    private DatePicker dateFld;
    @FXML
    private TextField emailFld;

    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Collects collects = null ;
    private boolean update; 
    int collectId;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(MouseEvent event) {
        
         connection = DbConnect.getConnect();
        String name = nameFld.getText();
        String date = String.valueOf(dateFld.getValue());
        String email = emailFld.getText();
        
        
        if (name.isEmpty() || date.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clear();

        }
    }

    @FXML
    private void clear() {
          nameFld.setText(null);
        dateFld.setValue(null);
        emailFld.setText(null);
    }

    private void getQuery() {
                if (update == false) {

        query = "INSERT INTO `collects`(`name`, `collect_date`, `email`) VALUES (?,?,?)";
        
        }else{
           query = "UPDATE `collects` SET "
                    + "`name`=?,"
                    + "`collect_date`=?,"
                    + "`email`= ? WHERE id = '"+collectId+"'";
        }
    }

    private void insert() {
         try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameFld.getText());
            preparedStatement.setString(2, String.valueOf(dateFld.getValue()));
            preparedStatement.setString(3, emailFld.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddCollectController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

  
    void setTextField(int id, String name, LocalDate toLocalDate, String email) {
         collectId = id;
        nameFld.setText(name);
        dateFld.setValue(toLocalDate);
        emailFld.setText(email);
    }
    
     void setUpdate(boolean b) {
                this.update = b;

    }
    
}
