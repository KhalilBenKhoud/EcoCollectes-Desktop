/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableView;

import com.itextpdf.text.BaseColor;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.scene.control.TextField;



import models.Collects;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class TableViewController implements Initializable {

    @FXML
    private TableView<Collects> CollectsTable;
    @FXML
    private TableColumn<Collects, String> idCol;
    @FXML
    private TableColumn<Collects, String> nameCol;
    @FXML
    private TableColumn<Collects, String> dateCol;
    @FXML
    private TableColumn<Collects, String> emailCol;
      @FXML
    private TableColumn<Collects, String> editCol;
      @FXML
    private TextField filterFld;
    
     String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Collects collects = null ;
    
        ObservableList<Collects>  CollectsList = FXCollections.observableArrayList();



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       loadDate();
           CollectsList = CollectsTable.getItems();


    }    
    
    
    @FXML
public void searchTable() {
    String searchText = filterFld.getText().toLowerCase();

    if (searchText.isEmpty()) {
        CollectsTable.setItems(CollectsList);
        return;
    }

    ObservableList<Collects> filteredList = FXCollections.observableArrayList();

    for (Collects data : CollectsList) {
        if (data.getName().toLowerCase().contains(searchText) || data.getEmail().toLowerCase().contains(searchText)) {
            filteredList.add(data);
        }
    }

    CollectsTable.setItems(filteredList);
}

     @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/tableView/addCollect.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @FXML
    private void refreshTable() {
        try {
            CollectsList.clear();
            
            query = "SELECT * FROM `collects`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
             
            while (resultSet.next()){
                CollectsList.add(new  Collects(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("collect_date"),
                        resultSet.getString("email")));
                                CollectsTable.setItems(CollectsList);

                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
   
    @FXML
    private void print(MouseEvent event) {
    try {
        // Step 1: Retrieve data from the database
        String url = "jdbc:mysql://localhost:3306/pidev";
        String username = "root";
        String password = "";
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM collects");

        // Step 2: Create a new PDF file using iText
                String path = Paths.get(System.getProperty("user.home"), "Documents", "output.pdf").toString();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        
         // Step 3: Define formatting options
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Font fontSubtitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.GRAY);
        Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        
         Paragraph title = new Paragraph("Collection Requests", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
             Paragraph subtitle = new Paragraph("Personal Information", fontSubtitle);
            subtitle.setSpacingBefore(20);
            document.add(subtitle);
        // Step 3: Write the retrieved data to the PDF file
        while (rs.next()) {
            String name = rs.getString("name");
            String date = rs.getString("collect_date");
            String email = rs.getString("email");
            Paragraph paragraph = new Paragraph("Name: " + name + " -  Email: "  + email, fontBody);
              paragraph.setIndentationLeft(20);
            document.add(paragraph);
        }

        document.close();
        conn.close();
        System.out.println("PDF file created successfully.");
    } catch (DocumentException | FileNotFoundException | SQLException e) {
        System.err.println(e);
    }
}

    private void loadDate() {
        
        connection = DbConnect.getConnect();
        refreshTable();
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("collectDate"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        // zid cell mtaa edit button 
        Callback<TableColumn<Collects, String>, TableCell<Collects, String>> cellFoctory = (TableColumn<Collects, String> param) -> {
            // make cell containing buttons
            final TableCell<Collects, String> cell = new TableCell<Collects, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            
                            try {
                                collects = CollectsTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `collects` WHERE id  ="+collects.getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                           

                          

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            
                            collects = CollectsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/tableView/addCollect.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            AddCollectController addCollectController = loader.getController();
                            addCollectController.setUpdate(true);
                            addCollectController.setTextField(collects.getId(), collects.getName(), 
                                    collects.getCollectDate().toLocalDate(), collects.getEmail());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                            

                           

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
         CollectsTable.setItems(CollectsList);
         
    }
    
    
}
