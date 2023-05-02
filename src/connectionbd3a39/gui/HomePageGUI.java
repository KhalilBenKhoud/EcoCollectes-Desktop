/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionbd3a39.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author GAMERS
 */
public class HomePageGUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
      
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("../gui/HomePage.fxml"));
            
            Scene scene = new Scene(root);
        
            primaryStage.setTitle("Projet X");
            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
