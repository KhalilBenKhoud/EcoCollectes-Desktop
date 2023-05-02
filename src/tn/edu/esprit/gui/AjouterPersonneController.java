/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import com.sun.org.apache.xerces.internal.util.FeatureState;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.Personne;
import tn.edu.esprit.services.ServicePersonne;

/**
 * FXML Controller class
 *
 * @author abdelazizmezri
 */
public class AjouterPersonneController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ajouterPersonne(ActionEvent event) throws IOException {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Nom ou prenom invalide(s)", ButtonType.OK);
            a.showAndWait();
        } else {
            try {
                ServicePersonne sp = new ServicePersonne();
                Personne p = new Personne(tfNom.getText(), tfPrenom.getText());
                sp.ajouter(p);
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Personne added !", ButtonType.OK);
                a.showAndWait();
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherPersonne.fxml"));
                Parent root = loader.load();
                tfNom.getScene().setRoot(root);
                
                AfficherPersonneController apc = loader.getController();
                apc.setNom(tfNom.getText());
                apc.setPrenom(tfPrenom.getText());
                
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                a.showAndWait();
            }
        }

    }

}
