package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


// Scene switching implementation referenced from https://www.youtube.com/watch?v=qnwBZveyUtA
public class MainMenuController {
    @FXML
    Button login;
    @FXML
    Button signUp;

    @FXML
    public void switchLoginScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("login.fxml")));
        Stage stage = (Stage) login.getScene().getWindow();
        Scene scene = new Scene(root, 350, 350);
        stage.setTitle("Data Analytics Hub - Login");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchSignUpScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("sign-up.fxml")));
        Stage stage = (Stage) signUp.getScene().getWindow();
        Scene scene = new Scene(root, 350, 350);
        stage.setTitle("Data Analytics Hub - Sign Up");
        stage.setScene(scene);
        stage.show();
    }
}
