/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.gui;

import connectionbd3a39.entities.Annonce;
import connectionbd3a39.services.ServiceAnnonce;
import javafx.geometry.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GAMERS
 */
public class CatalogueAnnonceController implements Initializable {

    @FXML
    private GridPane gridContainer;

    private ServiceAnnonce sa = new ServiceAnnonce();

    private List<Annonce> rs = new ArrayList<>();
    @FXML
    private ScrollPane scroll;
    @FXML
    private Button btn;
    @FXML
    private Label accueil;
    @FXML
    private Label linkAnnonces;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accueil.setOnMouseEntered(e -> {
            accueil.setUnderline(true);
        });
        accueil.setOnMouseExited(e -> {
            accueil.setUnderline(false);
        });
        accueil.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("HomePage.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) accueil.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        linkAnnonces.setOnMouseEntered(e -> {
            linkAnnonces.setUnderline(true);
        });
        linkAnnonces.setOnMouseExited(e -> {
            linkAnnonces.setUnderline(false);
        });
        
        btn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AjoutAnnonce.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            rs = sa.getAll();
            int c = 0;
            int r = 1;
            for (Annonce annonce : rs) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AnnonceCard.fxml"));
                HBox cardBox = loader.load();
                AnnonceCardController afc = loader.getController();
                afc.setData(annonce);
                if (c == 3) {
                    c = 0;
                    ++r;
                }
                gridContainer.add(cardBox, ++c, r);
                gridContainer.setMargin(cardBox, new Insets(15));
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void toAnnonces(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("CatalogueAnnonce.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) accueil.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
