/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.gui;

import connectionbd3a39.entities.Annonce;
import connectionbd3a39.services.ServiceAnnonce;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GAMERS
 */
public class AjoutAnnonceController implements Initializable {

    @FXML
    private TextField tfTitre;
    @FXML
    private TextArea taDesc;

    private ServiceAnnonce sa = new ServiceAnnonce();
    @FXML
    private Label errTitre;
    @FXML
    private Label errDesc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ajouterAnnonce(ActionEvent event) {
        try {
            String titre = tfTitre.getText();
            String descAnnonce = taDesc.getText();

            Boolean titreEmpty = false;
            Boolean descEmpty = false;

            if (titre.trim().isEmpty()) {
                tfTitre.setStyle("-fx-border-color: red;");
                errTitre.setText("Titre vide ou invalide.");
                titreEmpty = true;
            }
            if (descAnnonce.trim().isEmpty()) {
                taDesc.setStyle("-fx-border-color: red;");
                errDesc.setText("Description vide ou invalide.");
                descEmpty = true;
            }

            if (titreEmpty || descEmpty) {
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

                str += System.lineSeparator() + System.lineSeparator() + "Veuillez verifier vos données!";

                alert.setTitle("Information invalid");
                alert.setHeaderText(str);
                alert.showAndWait();
            } else {
                Date dateCreation = Date.valueOf(LocalDate.now());
                Date dateModification = Date.valueOf(LocalDate.now());
                Annonce a = new Annonce(titre, descAnnonce, dateCreation, dateModification, "");

                sa.ajouter(a);
                System.out.println("Annonce: " + a.toString() + " ajoutée");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void retour(ActionEvent event) {
        Scene scene = tfTitre.getScene();
        Stage stage = (Stage) scene.getWindow();

        stage.close();
    }

}
