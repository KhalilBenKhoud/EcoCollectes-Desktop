package application;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Add Article");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Add title field
        Label titleLabel = new Label("Title:");
        GridPane.setConstraints(titleLabel, 0, 0);

        TextField titleField = new TextField();
        GridPane.setConstraints(titleField, 1, 0);

        // Add author field
        Label authorLabel = new Label("Author:");
        GridPane.setConstraints(authorLabel, 0, 1);

        TextField authorField = new TextField();
        GridPane.setConstraints(authorField, 1, 1);

        // Add content field
        Label contentLabel = new Label("Content:");
        GridPane.setConstraints(contentLabel, 0, 2);

        TextArea contentArea = new TextArea();
        contentArea.setPrefRowCount(10);
        contentArea.setWrapText(true);
        GridPane.setConstraints(contentArea, 1, 2);

        // Add submit button
        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 3);

        // Add button action
        submitButton.setOnAction(event -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String content = contentArea.getText();

            // Save article to database or file
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Content: " + content);

            // Clear fields
            titleField.clear();
            authorField.clear();
            contentArea.clear();
        });

        gridPane.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, contentLabel, contentArea, submitButton);

        Scene scene = new Scene(gridPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
