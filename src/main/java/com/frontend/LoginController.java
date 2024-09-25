package com.frontend;

import com.backend.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private Label toSignUp;

    @FXML
    private Button submit;

    @FXML
    protected void onLoginButtonClick() throws IOException {
        UserController userController = new UserController();

        int loginStatus = userController.tryLogin(username.getText(), password.getText());
        // to-do: Validate Sign up info
        if (loginStatus == 0) {
            System.out.println("Login Successful");

            Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
            Stage stage = (Stage) submit.getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            stage.setTitle("Data Analytics Hub - Dashboard");
            stage.setScene(scene);
            stage.show();
        }
        else if (loginStatus == 1) {
            System.out.println("Invalid password");
        }
        else {
            System.out.println("No such user found");
        }
    }

    @FXML
    protected void goToSignUp() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("sign-up.fxml")));
        Stage stage = (Stage) toSignUp.getScene().getWindow();
        Scene scene = new Scene(root, 350, 350);
        stage.setTitle("Data Analytics Hub - Sign Up");
        stage.setScene(scene);
        stage.show();
    }

}
