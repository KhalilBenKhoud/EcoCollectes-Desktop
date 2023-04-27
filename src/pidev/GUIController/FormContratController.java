/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pidev.Entities.Contrat;
import pidev.Entities.Entreprise;
import pidev.Services.ContratService;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class FormContratController implements Initializable {

    @FXML
    private TextArea description_field;
    @FXML
    private DatePicker dateDebut_field;
    @FXML
    private DatePicker dateFin_field;
    @FXML
    private ChoiceBox<String> typeContrat_field;
    @FXML
    private Button Ajouter_btn;
    @FXML
    private TextField montant_field;
    @FXML
    private Button cancel_btn;
    @FXML
    private AnchorPane formContrat_pane;

    ContratService cs = new ContratService();

    private Contrat contrat = null;

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
        Ajouter_btn.setText("Modifier");
        montant_field.setText(String.valueOf(contrat.getMontant()));
        description_field.setText(contrat.getDescription());
        dateDebut_field.setValue(contrat.getDate_debut().toLocalDate());
        dateFin_field.setValue(contrat.getDate_fin().toLocalDate());
        typeContrat_field.setValue(contrat.getType_contrat());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        typeContrat_field.getItems().setAll("CDD", "CDI", "CIVP", "CSC", "CD");

    }

    public void afficherAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public boolean testSaisie() {
        if (typeContrat_field.getValue() == null
                || dateDebut_field.getValue() == null
                || dateFin_field.getValue() == null
                || montant_field.getText().trim().isEmpty()
                || description_field.getText().trim().isEmpty()) {
            afficherAlert("Tous les champs doivent être remplis");
            return false;
        }

        if (LocalDate.now().compareTo(dateDebut_field.getValue()) > 0) {
            afficherAlert("Date debut doit être supérieur ou égal à la date courante");
            return false;
        }

        if (dateDebut_field.getValue().compareTo(dateFin_field.getValue()) > 0) {
            afficherAlert("Date fin doit être supérieur ou égal à la date de debut");
            return false;
        }
        try {
            float montant = Float.parseFloat(montant_field.getText());
        } catch (NumberFormatException e) {
            afficherAlert("Champs Montant invalide doit etre un nombre");
            return false;
        }
        return true;
    }

    @FXML
    private void handleOnClick(ActionEvent event) {
        if (testSaisie()) {
            Contrat c = new Contrat();
            Entreprise e = new Entreprise();
            e.setId(1);
            c.setDescription(description_field.getText());
            c.setDate_debut(Date.valueOf(dateDebut_field.getValue()));
            c.setDate_fin(Date.valueOf(dateFin_field.getValue()));
            c.setCollecteur_id(null);
            c.setEnterprise_id(e);
            c.setType_contrat(typeContrat_field.getValue());
            c.setMontant(Float.parseFloat(montant_field.getText()));
            if (contrat != null) {
                cs.modifier(c, contrat.getId());
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Contrat");
                tray.setMessage("Modif du contrat avec succès");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndWait();
            } else {
                cs.ajouter(c);
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Contrat");
                tray.setMessage("Ajout du contrat avec succès");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndWait();
            }
            doSwap();
        }
    }

    @FXML
    private void handleOnClickCancel(ActionEvent event) {
        doSwap();
    }

    private void doSwap() {
        try {
            Parent p;
            p = FXMLLoader.load(getClass().getResource("ContratsList.fxml"));
            formContrat_pane.getChildren().removeAll();
            formContrat_pane.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
