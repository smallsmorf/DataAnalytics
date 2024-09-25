package com.frontend;

import com.backend.SessionManager;
import com.backend.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class UpgradeVIPController {
    @FXML
    private Button no;
    @FXML
    private Button yes;
    @FXML
    private Label label;

    @FXML
    protected void onClickNo() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
        Stage stage = (Stage) no.getScene().getWindow();
        Scene scene = new Scene(root, 350, 600);
        stage.setScene(scene);
        stage.setTitle("Data Analytics Hub - Dashboard");
        stage.show();
    }
    @FXML
    protected void onClickYes() throws IOException {
        UserController userController = new UserController();
        if (userController.upgradeToVip(SessionManager.getInstance().getUser().getUsername())) {
            label.setText("Please log out and log in again to access VIP functionalities.");
            Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
            Stage stage = (Stage) yes.getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);

            Duration delay = Duration.seconds(1.5); // 1 second delay back to dashboard
            Timeline timeline = new Timeline(new KeyFrame(delay, event -> {
                // Change to the second scene
                stage.setTitle("Data Analytics Hub - Dashboard");
                stage.setScene(scene);
                stage.show();
            }));
            timeline.play();
        }
    }
}
