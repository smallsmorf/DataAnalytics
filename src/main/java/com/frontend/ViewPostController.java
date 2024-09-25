package com.frontend;

import com.backend.PostController;
import com.models.Post;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewPostController {
    @FXML
    private Button back;
    @FXML
    private TextField idField;
    @FXML
    private Label contentField;
    @FXML
    private Label authorField;
    @FXML
    private Label likesField;
    @FXML
    private Label sharesField;
    @FXML
    private Label datetimeField;

    @FXML
    private Label diagnostic;

    @FXML
    protected void onClickSearch() {
        PostController postController = new PostController();
        try {
            Integer.parseInt(idField.getText());
        } catch (NumberFormatException e) {
            diagnostic.setText("Enter valid number for ID");
            diagnostic.setStyle("-fx-text-fill: red; -fx-font-size: 12pt;");
            return;
        }
        Post post = postController.retrievePost(Integer.parseInt(idField.getText()));
        if (post != null) {
            diagnostic.setText("");
            contentField.setText(post.getContent());
            authorField.setText(post.getAuthor());
            likesField.setText(String.valueOf(post.getNumLikes()));
            sharesField.setText(String.valueOf(post.getNumShares()));
            datetimeField.setText(post.getDateTime());
        }
        else {
            diagnostic.setText("Post with ID " + idField.getText() + " does not exists!");
            diagnostic.setStyle("-fx-text-fill: red; -fx-font-size: 12pt;");
        }
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
