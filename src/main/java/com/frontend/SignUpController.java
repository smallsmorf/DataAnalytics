package com.frontend;

import com.backend.SessionManager;
import com.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.backend.UserController;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Label toLogin;
    @FXML
    private Button submit;
    @FXML
    private Label diagnostic;

    @FXML
    protected void onSignUpButtonClick() throws IOException {

        UserController userController = new UserController();
        if (!validateInput(userController)) return;

        // If Info is valid, sign up user
        User user = new User(username.getText(), firstName.getText(), lastName.getText(), password.getText(), false);
        SessionManager.getInstance().setUser(user);
        userController.addUser(username.getText(), firstName.getText(), lastName.getText(), password.getText());
        System.out.println(username.getText() + " " + password.getText());

        Parent root = FXMLLoader.load((Driver.class.getResource("dashboard.fxml")));
        Stage stage = (Stage) submit.getScene().getWindow();
        Scene scene = new Scene(root, 350, 600);
        stage.setTitle("Data Analytics Hub - Dashboard");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void goToLogin() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("login.fxml")));
        Stage stage = (Stage) toLogin.getScene().getWindow();
        Scene scene = new Scene(root, 350, 350);
        stage.setTitle("Data Analytics Hub - Login");
        stage.setScene(scene);
        stage.show();
    }

    private boolean validateInput(UserController userController){
        if (username.getText().equals("")) {
            diagnostic.setText("Username cannot be empty!");
        }
        if (userController.isUsernameTaken(username.getText())) {
            diagnostic.setText("Username has been taken!");
            return false;
        }
        if (firstName.getText().equals("")) {
            diagnostic.setText("First Name cannot be empty!");
            return false;
        }
        if (lastName.getText().equals("")) {
            diagnostic.setText("Last Name cannot be empty!");
            return false;
        }
        if (password.getText().equals("")) {
            diagnostic.setText("Password cannot be empty!");
            return false;
        }
        if (!password.getText().equals(confirmPassword.getText())) {
            diagnostic.setText("Passwords don't match!");
            return false;
        }

        return true;
    }

}
