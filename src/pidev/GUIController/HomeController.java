/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class HomeController implements Initializable {

    @FXML
    private Button btnOverview;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlOverview;
    @FXML
    private AnchorPane centerpane;
    @FXML
    private Button btnContrats;
    @FXML
    private Button btnInvitations;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void afficher(String fxml) {
        try {
            Parent p;
            p = FXMLLoader.load(getClass().getResource(fxml));
            centerpane.getChildren().removeAll();
            centerpane.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handleClicks(ActionEvent event) {
        if (event.getSource() == btnContrats) {
            afficher("ContratsList.fxml");
        }
        if (event.getSource() == btnInvitations) {
            afficher("InvitationsList.fxml");
        }
        if (event.getSource() == btnOverview) {
            centerpane.getChildren().clear();
           /* pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();*/
        }
    }

}
