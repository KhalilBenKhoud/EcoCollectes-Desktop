/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import connectionbd3a39.entities.Annonce;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GAMERS
 */
public class AnnonceCardController implements Initializable {

    @FXML
    private Label labelTitre;
    @FXML
    private Label labelModif;
    @FXML
    private TextArea labelDesc;
    @FXML
    private Button btnDetails;

    private Annonce annonce;
    @FXML
    private HBox box;

    private String[] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Annonce annonce) {
        this.annonce = annonce;
        if (annonce == null) {
            System.out.println("null");
        } else {
            labelTitre.setText(annonce.getTitre());
            labelDesc.setText(annonce.getDescAnnonce());
            labelModif.setText(annonce.getDateModification().toString());
            box.setStyle("-fx-background-color: " + colors[(int) (Math.random() * colors.length)]
                    + "; -fx-background-radius: 15px; -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
        }
    }

    @FXML
    private void toAnnonce(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Annonce.fxml"));
            Parent root = loader.load();
            AnnonceController ac = loader.getController();
            ac.setData(annonce);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AnnonceCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
