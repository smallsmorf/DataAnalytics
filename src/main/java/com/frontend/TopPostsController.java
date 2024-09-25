package com.frontend;

import com.backend.PostController;
import com.models.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TopPostsController {
    @FXML
    private Button back;
    @FXML
    private TextField numField;
    @FXML
    private TextField authorField;
    @FXML
    private TableView<Post> tableView;
    @FXML
    private Label diagnostic;
    @FXML
    private TableColumn<Post,Integer> idColumn;
    @FXML
    private TableColumn<Post,String> contentColumn;
    @FXML
    private TableColumn<Post,String> authorColumn;
    @FXML
    private TableColumn<Post,Integer> likesColumn;
    @FXML
    private TableColumn<Post,Integer> sharesColumn;
    @FXML
    private TableColumn<Post,String> datetimeColumn;

    @FXML
    public void onRetrieveTopPosts(){
        PostController postController = new PostController();
        try {
            Integer.parseInt(numField.getText());
        }
        catch (NumberFormatException e) {
            diagnostic.setText("Enter valid number for ID");
            diagnostic.setStyle("-fx-text-fill: red; -fx-font-size: 12pt;");
            return;
        }
        List<Post> posts = postController.getTopNPosts(Integer.parseInt(numField.getText()),
                authorField.getText().equals("") ? null : authorField.getText());
        ObservableList<Post> data = FXCollections.observableList(posts);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        contentColumn.setCellValueFactory(new PropertyValueFactory("content"));

        // Wrap Content Column referenced here
        // https://stackoverflow.com/questions/22732013/javafx-tablecolumn-text-wrapping
        contentColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    Text text = new Text(item);
                    text.setStyle("-fx-text-alignment:justify;");
                    text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(10));
                    setGraphic(text);
                }
            }
        });

        authorColumn.setCellValueFactory(new PropertyValueFactory("author"));
        likesColumn.setCellValueFactory(new PropertyValueFactory("numLikes"));
        sharesColumn.setCellValueFactory(new PropertyValueFactory("numLikes"));
        datetimeColumn.setCellValueFactory(new PropertyValueFactory("dateTime"));
        tableView.setItems(data);
    }

    @FXML
    protected void onClickBack() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
        Stage stage = (Stage) back.getScene().getWindow();
        Scene scene = new Scene(root, 350, 600);
        stage.setTitle("Data Analytics Hub - Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}
