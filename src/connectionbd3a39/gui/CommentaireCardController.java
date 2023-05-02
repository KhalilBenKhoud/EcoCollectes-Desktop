/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import connectionbd3a39.entities.Commentaire;
import connectionbd3a39.services.ServiceCommentaire;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author GAMERS
 */
public class CommentaireCardController implements Initializable {

    @FXML
    private TextField tfContent;

    private Commentaire comm;
    @FXML
    private Label rating;
    @FXML
    private Label upVote;
    @FXML
    private Label downVote;
    @FXML
    private Label labelVotes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        downVote.setOnMouseEntered(e -> {
            downVote.setUnderline(true);
        });
        
        downVote.setOnMouseExited(e -> {
            downVote.setUnderline(false);
        });
        
        downVote.setOnMouseClicked(e -> {
            Commentaire c = comm;
            c.setVotes(comm.getVotes() - 1);
            ServiceCommentaire sc = new ServiceCommentaire();
            labelVotes.setText(c.getVotes().toString());
            try {
                sc.modifier(c, comm.getId());
            } catch (SQLException ex) {
                Logger.getLogger(CommentaireCardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        upVote.setOnMouseEntered(e -> {
            upVote.setUnderline(true);
        });
        
        upVote.setOnMouseExited(e -> {
            upVote.setUnderline(false);
        });        
        
        upVote.setOnMouseClicked(e -> {
            Commentaire c = comm;
            c.setVotes(comm.getVotes() + 1);
            ServiceCommentaire sc = new ServiceCommentaire();
            labelVotes.setText(c.getVotes().toString());
            try {
                sc.modifier(c, comm.getId());
            } catch (SQLException ex) {
                Logger.getLogger(CommentaireCardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setData(Commentaire comm) {
        this.comm = comm;
        if (comm == null) {
            System.out.println("null");
        } else {
            tfContent.setText(comm.getContenu());
            System.out.println("votes: " + comm.getVotes());
            labelVotes.setText(comm.getVotes().toString());
        }
    }

}
