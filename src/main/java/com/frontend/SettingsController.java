package com.frontend;

import com.UserExistsException;
import com.backend.SessionManager;
import com.backend.UserController;
import com.models.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SettingsController {
    @FXML
    private TextField firstname, lastname, username;
    @FXML
    private PasswordField password;
    @FXML
    private Button back, confirm;
    @FXML
    private Label diagnostic;

    @FXML
    public void initialize(){
        User currentUser = SessionManager.getInstance().getUser();
        firstname.setText(currentUser.getFirstname());
        lastname.setText(currentUser.getLastname());
        username.setText(currentUser.getUsername());
        password.setText(currentUser.getPassword());
    }
    @FXML
    protected void onClickConfirm() throws IOException {

        UserController userController = new UserController();
        // to-do: data validation
        if (!validateInput()) return;

        try {
            userController.updateProfile(firstname.getText(), lastname.getText(),username.getText(),
                    password.getText());
            SessionManager.getInstance().getUser().setFirstname(firstname.getText());
            SessionManager.getInstance().getUser().setLastname(lastname.getText());
            SessionManager.getInstance().getUser().setUsername(username.getText());
            SessionManager.getInstance().getUser().setPassword(password.getText());

            Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
            Stage stage = (Stage) confirm.getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);

            diagnostic.setText("Profile Updated successfully!");
            diagnostic.setStyle("-fx-text-fill: green; -fx-font-size: 12pt;");

            Duration delay = Duration.seconds(1); // 1 second delay back to dashboard
            Timeline timeline = new Timeline(new KeyFrame(delay, event -> {
                // Change to the second scene
                stage.setTitle("Data Analytics Hub - Dashboard");
                stage.setScene(scene);
                stage.show();
            }));
            timeline.play();
        } catch (UserExistsException e) {
            diagnostic.setText("Update Failed! Username already taken!");
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

    private boolean validateInput(){
        if (firstname.getText().equals("")) {
            diagnostic.setText("First Name can't be blank!");
            return false;
        }
        if (lastname.getText().equals("")) {
            diagnostic.setText("Last Name can't be blank!");
            return false;
        }
        if (username.getText().equals("")) {
            diagnostic.setText("Username can't be blank!");
            return false;
        }
        if (password.getText().equals("")) {
            diagnostic.setText("Password can't be blank!");
            return false;
        }
        return true;
    }
}
