/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javax.mail.MessagingException;
import pidev.Entities.Contrat;
import pidev.Entities.Entreprise;
import pidev.Entities.Invitation;
import pidev.Entities.User;
import pidev.Services.ContratService;
import pidev.Services.InvitationService;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class FormInvitationController implements Initializable {

    @FXML
    private AnchorPane formInvitation_pane;
    @FXML
    private TextArea description_field;
    @FXML
    private Button Ajouter_btn;
    @FXML
    private Button cancel_btn;

    private Invitation invitation = null;
    @FXML
    private ChoiceBox<Contrat> contrat_field;
    @FXML
    private ChoiceBox<User> collector_field;

    ContratService cs = new ContratService();
    InvitationService is = new InvitationService();

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
        Ajouter_btn.setText("Modifier");
        description_field.setText(invitation.getDescription());
        contrat_field.setValue(invitation.getContrat_id());
        contrat_field.setConverter(new StringConverter<Contrat>() {
            @Override
            public String toString(Contrat contrat) {
                return String.valueOf(contrat.getId());
            }

            @Override
            public Contrat fromString(String id) {
                return null;
            }
        });

        collector_field.setValue(invitation.getCollecteur_id());

    }

    public void afficherAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public boolean testSaisie() {
        if (contrat_field.getValue() == null
                || collector_field.getValue() == null
                || description_field.getText().trim().isEmpty()) {
            afficherAlert("Tous les champs doivent être remplis");
            return false;
        }

        return true;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println(cs.afficherCollectors());
        collector_field.getItems().setAll(cs.afficherCollectors());
        contrat_field.getItems().setAll(cs.afficherContrats(1));

    }

    @FXML
    private void handleOnClick(ActionEvent event) {
        if (testSaisie()) {
            Invitation c = new Invitation();

            c.setDescription(description_field.getText());
            c.setCollecteur_id(collector_field.getSelectionModel().getSelectedItem());
            c.setContrat_id(contrat_field.getSelectionModel().getSelectedItem());;

            if (invitation != null) {
                is.modifier(c, invitation.getId());
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Invitation");
                tray.setMessage("Modif du invitation avec succès");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndWait();
            } else {
                int id = is.ajouter(c);
                if (id != -1) {
                       cs.sendsms("28581523", c.getCollecteur_id().getUsername(), String.valueOf(c.getContrat_id().getId()));
                    try {
                        cs.sendmail(c.getCollecteur_id().getEmail(), "Invitation Request", id, c.getContrat_id().getId(), c.getCollecteur_id().getId());
                    } catch (MessagingException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Invitation");
                tray.setMessage("Ajout du invitation avec succès");
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
            p = FXMLLoader.load(getClass().getResource("InvitationsList.fxml"));
            formInvitation_pane.getChildren().removeAll();
            formInvitation_pane.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
