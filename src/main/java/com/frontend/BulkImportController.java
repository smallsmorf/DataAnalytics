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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class BulkImportController {
    @FXML
    private Button back, importButton;
    @FXML
    private Label diagnostic, selectedFile;

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
    protected void onImportPost() throws IOException {
        PostController postController = new PostController();
        File file = chooseFile((Stage) importButton.getScene().getWindow());
        if (postController.parseCSV(file)) {
            selectedFile.setText(file.getAbsolutePath());
            Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
            Stage stage = (Stage) importButton.getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            diagnostic.setText("File with name " + file.getName() + " imported successfully!");
            diagnostic.setStyle("-fx-text-fill: green; -fx-font-size: 12pt;");

            Duration delay = Duration.seconds(1.5);
            Timeline timeline = new Timeline(new KeyFrame(delay, event -> {
                stage.setScene(scene);
                stage.setTitle("Data Analytics Hub - Dashboard");
                stage.show();
            }));
            timeline.play();
        }
        else {
            diagnostic.setText("Failed to import post!");
        }
    }

    private File chooseFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile;
    }
}
