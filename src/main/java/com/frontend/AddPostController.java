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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddPostController {
    @FXML
    private Button back;
    @FXML
    private Button confirm;
    @FXML
    private TextField idField;
    @FXML
    private TextArea contentField;
    @FXML
    private TextField likesField;
    @FXML
    private TextField sharesField;
    @FXML
    private TextField datetimeField;

    @FXML
    private Label diagnostic;

    @FXML
    protected void onClickConfirm() throws IOException {
        PostController postController = new PostController();

        // to-do: data validation
        if (!validateData(postController)) return;

        boolean status = postController.addNewPost(Integer.parseInt(idField.getText()), contentField.getText(),
                Integer.parseInt(likesField.getText()), Integer.parseInt(sharesField.getText()),
                datetimeField.getText());

        if (status) {
            Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
            Stage stage = (Stage) confirm.getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
//            stage.setScene(scene);
//            stage.show();
            diagnostic.setText("Post added successfully!");
            diagnostic.setStyle("-fx-text-fill: green; -fx-font-size: 12pt;");

            Duration delay = Duration.seconds(1); // 1 second delay back to dashboard
            Timeline timeline = new Timeline(new KeyFrame(delay, event -> {
                // Change to the second scene
                stage.setScene(scene);
                stage.setTitle("Data Analytics Hub - Dashboard");
                stage.show();
            }));
            timeline.play();
        }
        else {
            diagnostic.setText("Failed to add post!");
        }
    }

    @FXML
    protected void onClickBack() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
        Stage stage = (Stage) back.getScene().getWindow();
        Scene scene = new Scene(root, 350, 600);
        stage.setScene(scene);
        stage.setTitle("Data Analytics Hub - Dashboard");
        stage.show();
    }

    private boolean validateData(PostController postController){
        // Validate ID
        try {
            int postID = Integer.parseInt(idField.getText());
            if (!postController.postIDisUnique(postID)) {
                diagnostic.setText("Post with ID " + idField.getText() + " already exists!");
                return false;
            }
        } catch (NumberFormatException e) {
            diagnostic.setText("Enter valid number for ID");
            return false;
        }

        try {
            Integer.parseInt(likesField.getText());
        } catch (NumberFormatException e) {
            diagnostic.setText("Enter valid number for Number of Likes");
            return false;
        }

        try {
            Integer.parseInt(sharesField.getText());
        } catch (NumberFormatException e) {
            diagnostic.setText("Enter valid number for Number of Shares");
            return false;
        }

        try {
            String datetime = datetimeField.getText();
            LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (DateTimeParseException e) {
            diagnostic.setText("Enter valid Date Time (DD/MM/YYYY HH:MM)");
            return false;
        }
        return true;
    }

}
