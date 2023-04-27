/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pidev.Entities.Contrat;
import pidev.Entities.Invitation;
import pidev.Services.InvitationService;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class InvitationsListController implements Initializable {

    @FXML
    private AnchorPane invitationList_pane;
    @FXML
    private TableView<Invitation> invitation_table;
    @FXML
    private TableColumn<Invitation, String> description_col;
    @FXML
    private TableColumn<Invitation, String> status_col;
    @FXML
    private Button ajouter_btn;
    @FXML
    private Button supprimer_btn;
    @FXML
    private Button modifier_btn;
    @FXML
    private TableColumn<Invitation, String> dateInvitation_col;
    @FXML
    private TableColumn<Invitation, String> collector_col;
    @FXML
    private TableColumn<Invitation, String> contrat_col;

    InvitationService is = new InvitationService();
    @FXML
    private TextField search;
    @FXML
    private ComboBox<String> filter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("statut_invitation"));
        dateInvitation_col.setCellValueFactory(new PropertyValueFactory<>("date_invitation"));
        collector_col.setCellValueFactory(new PropertyValueFactory<>("collecteur_id"));
        contrat_col.setCellValueFactory(cellData -> {
            Contrat contrat = cellData.getValue().getContrat_id();
            String id = (contrat == null) ? "" : String.valueOf(contrat.getId());
            return new SimpleStringProperty(id);
        });
        invitation_table.getItems().setAll(is.afficherInvitations(1));
        modifier_btn.setVisible(false);
        supprimer_btn.setVisible(false);
        filter.getItems().setAll("All", "En attente", "Accepte", "Refuse");
        itemListner();
        searchListner();
        filterListner();
    }

    private void itemListner() {
        invitation_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (newSelection.getStatut_invitation().equals("En attente")) {
                System.out.println("hiiii from visible");
                modifier_btn.setVisible(true);
                supprimer_btn.setVisible(true);
            } else {
                modifier_btn.setVisible(false);
                supprimer_btn.setVisible(false);
            }
        });
        invitationList_pane.setOnMousePressed((event) -> {
            modifier_btn.setVisible(false);
            supprimer_btn.setVisible(false);
        });

    }

    @FXML
    private void handle_Ajouter(ActionEvent event) {
        try {
            Parent p;
            p = FXMLLoader.load(getClass().getResource("FormInvitation.fxml"));
            invitationList_pane.getChildren().removeAll();
            invitationList_pane.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handle_Supprimer(ActionEvent event) {
        Invitation c = invitation_table.getSelectionModel().getSelectedItem();
        if (c != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer la suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                is.supprimer(c.getId());
                invitation_table.getItems().clear();
                invitation_table.getItems().setAll(is.afficherInvitations(1));
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Invitation");
                tray.setMessage("Supprimer du invitation avec succès");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndWait();
            }
            modifier_btn.setVisible(false);
            supprimer_btn.setVisible(false);

        }
    }

    @FXML
    private void handle_Modifier(ActionEvent event) {
        try {
            Parent p;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormInvitation.fxml"));
            p = loader.load();
            FormInvitationController controller = loader.getController();
            controller.setInvitation(invitation_table.getSelectionModel().getSelectedItem());
            invitationList_pane.getChildren().removeAll();
            invitationList_pane.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void searchListner() {
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Invitation> temp = is.afficherInvitations(1);
            List<Invitation> filtredContrats = temp.stream().filter(invitation -> invitation.getDescription().contains(newValue) || (invitation.getCollecteur_id() != null && invitation.getCollecteur_id().getUsername().contains(newValue)))
                    .collect(Collectors.toList());
            invitation_table.getItems().setAll(filtredContrats);
        });
    }

    private void filterListner() {
        filter.setOnAction((event) -> {
            List<Invitation> temp = is.afficherInvitations(1);
            List<Invitation> filtredContrats = temp.stream().filter(contrat -> contrat.getStatut_invitation().equals(filter.getValue()))
                    .collect(Collectors.toList());

            if (filter.getValue().equals("All")) {
                invitation_table.getItems().setAll(is.afficherInvitations(1));
            } else {
                invitation_table.getItems().setAll(filtredContrats);
            }

        });
    }

}
