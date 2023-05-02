/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.gui;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import connectionbd3a39.entities.Annonce;
import connectionbd3a39.services.ServiceAnnonce;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GAMERS
 */
public class AnnonceFXMLController implements Initializable {
    
    @FXML
    private Button btnCreer;
    @FXML
    private TextField tfTitre;
    @FXML
    private TextField tfImg;
    @FXML
    private TextArea taDesc;
    @FXML
    private TableView<Annonce> tvAnnonce;
    @FXML
    private TableColumn<Annonce, Integer> tvId;
    @FXML
    private TableColumn<Annonce, String> tvTitre;
    @FXML
    private TableColumn<Annonce, String> tvDesc;
    @FXML
    private TableColumn<Annonce, Date> tvDC;
    @FXML
    private TableColumn<Annonce, Date> tvDM;
    @FXML
    private TableColumn<Annonce, Boolean> tvAction;
    
    ServiceAnnonce sa = new ServiceAnnonce();
    @FXML
    private Button btnSupp;
    @FXML
    private Button btnModif;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvAnnonce.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        tvId.setCellValueFactory(new PropertyValueFactory<Annonce, Integer>("id"));
        tvTitre.setCellValueFactory(new PropertyValueFactory<Annonce, String>("titre"));
        tvDesc.setCellValueFactory(new PropertyValueFactory<Annonce, String>("descAnnonce"));
        tvDC.setCellValueFactory(new PropertyValueFactory<Annonce, Date>("dateCreation"));
        tvDM.setCellValueFactory(new PropertyValueFactory<Annonce, Date>("dateModification"));
        
        try {
            
            ObservableList<Annonce> list = FXCollections.observableArrayList(sa.getAll());
            tvAnnonce.setItems(list);
            
        } catch (SQLException e) {
            Logger.getLogger(AnnonceFXMLController.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    
    @FXML
    private void ajouter(ActionEvent event) {
        try {
            String titre = tfTitre.getText();
            String descAnnonce = taDesc.getText();
            String img = tfImg.getText();
            
            Boolean titreEmpty = false;
            Boolean imgEmpty = false;
            Boolean descEmpty = false;
            
            if (titre.trim().isEmpty()) {
                tfTitre.setStyle("-fx-border-color: red;");
                titreEmpty = true;
            }
            if (descAnnonce.trim().isEmpty()) {
                taDesc.setStyle("-fx-border-color: red;");
                descEmpty = true;
            }
            if (img.trim().isEmpty()) {
                tfImg.setStyle("-fx-border-color: red;");
                imgEmpty = true;
            }
            
            if (imgEmpty || titreEmpty || descEmpty) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                
                String str = "";
                if (titreEmpty) {
                    str += "Titre manquant!";
                    System.out.println("Error: Titre manquant");
                }
                if (descEmpty) {
                    if (!str.isEmpty()) {
                        str += System.lineSeparator();
                    }
                    str += "Description manquante!";
                    System.out.println("Error: Description manquante");
                    
                }
                if (imgEmpty) {
                    if (!str.isEmpty()) {
                        str += System.lineSeparator();
                    }
                    str += "Image manquante!";
                    System.out.println("Error: Image manquante");
                    
                }
                str += System.lineSeparator() + System.lineSeparator() + "Veuillez verifier vos données!";
                
                alert.setTitle("Information invalid");
                alert.setHeaderText(str);
                alert.showAndWait();
            } else {
                Date dateCreation = Date.valueOf(LocalDate.now());
                Date dateModification = Date.valueOf(LocalDate.now());
                Annonce a = new Annonce(titre, descAnnonce, dateCreation, dateModification, img);
                
                sa.ajouter(a);
                System.out.println("Annonce: " + a.toString() + " ajoutée");
                tvAnnonce.refresh();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void supprimer(ActionEvent event) {
        ObservableList<Annonce> rs = tvAnnonce.getSelectionModel().getSelectedItems();
        
        if (rs.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information missing");
            alert.setHeaderText("No annonce selected");
            alert.showAndWait();
        }
        for (Annonce annonce : rs) {
            try {
                sa.supprimer(annonce.getId());
            } catch (SQLException ex) {
                Logger.getLogger(AnnonceFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        tvAnnonce.refresh();
    }
    
    @FXML
    private void modifier(ActionEvent event) {
        
        ObservableList<Annonce> rs = tvAnnonce.getSelectionModel().getSelectedItems();
        
        if (rs.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information missing");
            alert.setHeaderText("No annonce selected");
            alert.showAndWait();
        } else {
            try {
                Annonce a = tvAnnonce.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Modification.fxml"));
                Parent root = loader.load();
                
                ModificationController modifController = loader.getController();
                
                modifController.setId(a.getId());
                modifController.oldTitre.setText(a.getTitre());
                modifController.oldDesc.setText(a.getDescAnnonce());
                modifController.oldImg.setText(a.getImg());
                modifController.setDateCreation(a.getDateCreation());
                
                tfTitre.getScene().setRoot(root);

                System.out.println("Redirected to modification");
            } catch (IOException ex) {
                Logger.getLogger(AnnonceFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
