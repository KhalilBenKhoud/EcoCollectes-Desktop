/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import pidev.Services.ContratService;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class ContratsListController implements Initializable {

    @FXML
    private TableView<Contrat> contrat_table;
    @FXML
    private TableColumn<Contrat, String> description_col;
    @FXML
    private TableColumn<Contrat, String> dateDebut_col;
    @FXML
    private TableColumn<Contrat, String> dateFin_col;
    @FXML
    private TableColumn<Contrat, String> status_col;
    @FXML
    private TableColumn<Contrat, String> type_col;
    @FXML
    private TableColumn<Contrat, String> montant_col;
    @FXML
    private Button ajouter_btn;
    @FXML
    private Button supprimer_btn;
    @FXML
    private Button modifier_btn;

    ContratService cs = new ContratService();
    @FXML
    private AnchorPane contratList_pane;
    @FXML
    private TextField search;
    @FXML
    private ComboBox<String> filter;
    @FXML
    private Button QrCode;
    @FXML
    private Button pdf;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateDebut_col.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFin_col.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("statut_contrat"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("type_contrat"));
        montant_col.setCellValueFactory(new PropertyValueFactory<>("montant"));
        contrat_table.getItems().setAll(cs.afficherContrats(1));
        modifier_btn.setVisible(false);
        supprimer_btn.setVisible(false);
        QrCode.setVisible(false);
        filter.getItems().setAll("All", "En Attente", "Actif", "Expire");
        itemListner();
        searchListner();
        filterListner();
    }

    @FXML
    private void handle_Ajouter(ActionEvent event) {
        try {
            Parent p;
            p = FXMLLoader.load(getClass().getResource("FormContrat.fxml"));
            contratList_pane.getChildren().removeAll();
            contratList_pane.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handle_Supprimer(ActionEvent event) {
        Contrat c = contrat_table.getSelectionModel().getSelectedItem();
        if (c != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer la suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                cs.supprimer(c.getId());
                contrat_table.getItems().clear();
                contrat_table.getItems().setAll(cs.afficherContrats(1));
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Contrat");
                tray.setMessage("Supprimer du contrat avec succès");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormContrat.fxml"));
            p = loader.load();
            FormContratController controller = loader.getController();
            controller.setContrat(contrat_table.getSelectionModel().getSelectedItem());
            contratList_pane.getChildren().removeAll();
            contratList_pane.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void itemListner() {
        contrat_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                modifier_btn.setVisible(true);
                supprimer_btn.setVisible(true);
                QrCode.setVisible(true);
            } else {
                modifier_btn.setVisible(false);
                supprimer_btn.setVisible(false);
                QrCode.setVisible(false);
            }
        });
        contratList_pane.setOnMousePressed((event) -> {
            modifier_btn.setVisible(false);
            supprimer_btn.setVisible(false);
            QrCode.setVisible(false);
        });

    }

    private void searchListner() {
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Contrat> temp = cs.afficherContrats(1);
            List<Contrat> filtredContrats = temp.stream()
                    .filter(contrat -> contrat.getDescription().contains(newValue) || (contrat.getCollecteur_id() != null && contrat.getCollecteur_id().getUsername() != null && contrat.getCollecteur_id().getUsername().contains(newValue)) || String.valueOf(contrat.getMontant()).contains(newValue))
                    .collect(Collectors.toList());
            contrat_table.getItems().setAll(filtredContrats);
        });
    }

    private void filterListner() {
        filter.setOnAction((event) -> {
            List<Contrat> temp = cs.afficherContrats(1);
            List<Contrat> filtredContrats = temp.stream().filter(contrat -> contrat.getStatut_contrat().equals(filter.getValue()))
                    .collect(Collectors.toList());

            if (filter.getValue().equals("All")) {
                contrat_table.getItems().setAll(cs.afficherContrats(1));
            } else {
                contrat_table.getItems().setAll(filtredContrats);
            }

        });
    }

    @FXML
    private void handlheQrCode(ActionEvent event) {
        try {
            cs.QRcode(contrat_table.getSelectionModel().getSelectedItem());
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Export");
            tray.setMessage("Export le contrat on QrCode avec succès");
            tray.setNotificationType(NotificationType.NOTICE);
            tray.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handle_pdf(ActionEvent event) {
        try {
            cs.exportpdf(cs.afficherContrats(1), contratList_pane);
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Export");
            tray.setMessage("Export on PDF avec succès");
            tray.setNotificationType(NotificationType.NOTICE);
            tray.showAndWait();
        } catch (FileNotFoundException | DocumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
