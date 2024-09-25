package com.frontend;

import com.backend.PostController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class RemovePostController {
    @FXML
    private Button back;
    @FXML
    private Button delete;
    @FXML
    private TextField idField;
    @FXML
    private Label diagnostic;
    @FXML
    protected void onClickBack() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
        Stage stage = (Stage) back.getScene().getWindow();
        Scene scene = new Scene(root, 350, 600);
        stage.setTitle("Data Analytics Hub - Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onDeletePost() throws IOException {

        PostController postController = new PostController();
        try {
            Integer.parseInt(idField.getText());
        } catch (NumberFormatException e) {
            diagnostic.setText("Enter valid number for ID");
            diagnostic.setStyle("-fx-text-fill: red; -fx-font-size: 12pt;");
            return;
        }
        if (postController.retrievePost(Integer.parseInt(idField.getText())) != null) {
            if (postController.deletePost(Integer.parseInt(idField.getText()))) {
                Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
                Stage stage = (Stage) delete.getScene().getWindow();
                Scene scene = new Scene(root, 350, 600);
                diagnostic.setText("Post deleted successfully!");
                diagnostic.setStyle("-fx-text-fill: green; -fx-font-size: 12pt;");

                Duration delay = Duration.seconds(1.5);
                Timeline timeline = new Timeline(new KeyFrame(delay, event -> {
                    stage.setTitle("Data Analytics Hub - Dashboard");
                    stage.setScene(scene);
                    stage.show();
                }));
                timeline.play();
            }
            else {
                diagnostic.setText("Failed to delete post!");
            }
        }
        else {
            diagnostic.setText("Post with ID " + idField.getText() + " does not exists!");
            diagnostic.setStyle("-fx-text-fill: red; -fx-font-size: 12pt;");
        }
    }

}
