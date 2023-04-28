/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import entities.User;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JFileChooser;
import services.UserService;


/**
 * FXML Controller class
 *
 * @author khalil
 */
public class UserBackOfficeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
        @FXML
    private TableColumn<User, Integer> cl_id;
    @FXML
    private TableColumn<User, String> cl_username;
    @FXML
    private TableColumn<User, String> cl_email;
    @FXML
    private TableColumn<User, String> cl_image;
    @FXML
    private TableColumn<User, String> cl_banned;

    UserService service = new UserService() ;
     @FXML
    private AnchorPane backofficePane ;
      @FXML
    private TableView tvUser ;
      @FXML
    private Button banBtn ;
          @FXML
    private ComboBox<String> triBox;
    
           @FXML
    private TextField tfSearch;
           
            ObservableList<User> obl ;

    
            
         
            
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showListUser() ;
        } catch (SQLException ex) {
            Logger.getLogger(UserBackOfficeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tvUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue obs, Object oldSelection, Object newSelection) {
                User user = (User) tvUser.getSelectionModel().getSelectedItem();
                if (user.isBanned()) {
                    // Update the button text based on the selected item in the table view
                    banBtn.setText("Allow");
                } else {
                    // Set the button text back to its original value
                    banBtn.setText("Ban");
                }       }
        });
        
        ObservableList<String> data = FXCollections.observableArrayList("id", "username", "email");
        triBox.setItems(data);
        
            try {
                obl = service.show();
            } catch (SQLException ex) {
                Logger.getLogger(UserBackOfficeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
             FilteredList<User> filteredData = new FilteredList<>(FXCollections.observableList(obl), user -> {
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();

        if (user.getUsername().toLowerCase().contains(lowerCaseFilter)) {
            return true;
        } else if  (user.getEmail().toLowerCase().contains(lowerCaseFilter)) {
            return true;
        } 

        return false; 
         });
           tvUser.setItems(filteredData);
          
});    
        
    }
                    
    
      @FXML
    public void showListUser() throws SQLException {
               ObservableList<User> List = service.show();
               
          cl_id.setCellValueFactory(new PropertyValueFactory<>("id"));

         cl_username.setCellValueFactory(new PropertyValueFactory<>("username"));
         cl_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        cl_image.setCellValueFactory(new PropertyValueFactory<>("image_filename"));
     
        cl_banned.setCellValueFactory(new PropertyValueFactory<>("banned"));      
           tvUser.setItems(List);      
    }
    
    public void ban() throws SQLException {
        
                if (tvUser.getSelectionModel().getSelectedItem() != null) {
                    
              try {
            User user = (User) tvUser.getSelectionModel().getSelectedItem();
           if(user.isBanned())
           {
              service.allow(user.getId());
           }
           else {
               service.ban(user.getId());
           }
              }
              catch(Exception ex) {
                  ex.printStackTrace();
              }
     

       } else {
       //Alert Select jeux :
       Alert selectMealAlert = new Alert(Alert.AlertType.WARNING);
       selectMealAlert.setTitle("Select a User");
       selectMealAlert.setHeaderText(null);
       selectMealAlert.setContentText("You need to select User first!");
       selectMealAlert.showAndWait();
       
       }
        showListUser();
        
    }
    
        @FXML
    public void trier(ActionEvent event) throws SQLException {
        
      Comparator<User> comparator ;
        if(triBox.getValue().equals("id")){
         comparator = Comparator.comparingInt(User::getId);
         
        }
        else if(triBox.getValue()=="username"){
         comparator = Comparator.comparing(User::getUsername);
         
        }
        else{
         comparator = Comparator.comparing(User::getEmail);
         
        }
        
        FXCollections.sort(obl, comparator);
           tvUser.setItems(obl);
           
    }
    
    
    public void toPdf(ActionEvent event) throws DocumentException {
        String path ="" ;
       JFileChooser j = new JFileChooser() ;
       j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY) ;
       int x = j.showSaveDialog(new Component() {}) ;
       if(x == JFileChooser.APPROVE_OPTION)
       {
           path = j.getSelectedFile().getPath() ;
       }
        
       Document doc = new Document() ;
            try {
                PdfWriter.getInstance(doc, new FileOutputStream(path+"list.pdf")) ;
                 doc.open();
                 PdfPTable tb1;
            tb1 = new PdfPTable(3);
             tb1.addCell("id") ;
             tb1.addCell("username") ;
             tb1.addCell("email") ;
             
          /*   for(int i=0; i < tvUser.getItems().size(); i++) {
                 int id = tvUser.getColumns()
  
}
             }*/
          doc.add(tb1) ;
            
            } catch (FileNotFoundException ex) {
                Logger.getLogger(UserBackOfficeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            doc.close() ;
    }
    
}
