/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.gui;

import connectionbd3a39.entities.Annonce;
import connectionbd3a39.entities.Commentaire;
import connectionbd3a39.services.ServiceAnnonce;
import connectionbd3a39.services.ServiceCommentaire;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GAMERS
 */
public class AnnonceController implements Initializable {

    public static final String ACCOUNT_SID = "AC796fb2a9c1b642f5141f1e40b4c90f8b";
    public static final String AUTH_TOKEN = "00655493e88c7301f62ce3af778b465b";
    
    @FXML
    private Label labelId;
    @FXML
    private Label LabelCreation;
    @FXML
    private Label labelModif;
    @FXML
    private Label labelTitre;
    @FXML
    private TextArea LabelDesc;
    @FXML
    private TextField tfCommentaire;
    @FXML
    private VBox cardBoxComm;

    private Annonce annonce;
    
    private Integer annonceId;
    
    private ServiceAnnonce sc = new ServiceAnnonce();
    
    private List<Commentaire> rs = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void setData(Annonce annonce) {
        this.annonce = annonce;
        if (annonce == null) {
            System.out.println("null");
        } else {
            annonceId = annonce.getId();
            labelTitre.setText(annonce.getTitre());
            LabelDesc.setText(annonce.getDescAnnonce());
            labelModif.setText(annonce.getDateModification().toString());
            labelId.setText(annonceId.toString());
            LabelCreation.setText(annonce.getDateCreation().toString());
        }

    }

    @FXML
    private void ajouterComm(ActionEvent event) {
        try {
            ServiceCommentaire sc = new ServiceCommentaire();
            Commentaire comm = new Commentaire();
            comm.setDateCreation(Date.valueOf(LocalDate.now()));
            comm.setDateModification(comm.getDateCreation());
            comm.setContenu(tfCommentaire.getText());
            comm.setVotes(0);
            sc.ajouter(comm, annonce.getId());
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void retour(ActionEvent event) {
        Scene scene = labelTitre.getScene();
        Stage stage = (Stage) scene.getWindow();
        
        stage.close();
    }
    
    @FXML
    private void voir(ActionEvent event) {
        try {
            rs = sc.getAllCommentaires(annonceId);
            for (Commentaire c : rs) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("CommentaireCard.fxml"));
                HBox cardBox = loader.load();
                CommentaireCardController afc = loader.getController();
                afc.setData(c);
                cardBoxComm.getChildren().add(cardBox);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void envoyerSMS(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PhonePrompt.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AnnonceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
