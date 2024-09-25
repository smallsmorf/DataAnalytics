package com.frontend;

import com.backend.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    @FXML
    private Label welcome;
    @FXML
    private Button addPost;
    @FXML
    private Button viewPost;
    @FXML
    private Button deletePost;
    @FXML
    private Button topPosts;
    @FXML
    private Button exportPost;
    @FXML
    private Button logout;
    @FXML
    private Button vip;
    @FXML
    private Button dataVisualise;
    @FXML
    private Button bulkImport;
    @FXML
    private Button settings;

    @FXML
    public void initialize(){
        welcome.setText("Welcome " + SessionManager.getInstance().getUser().getFullname() + "!");
        if (SessionManager.getInstance().getUser().isVip()) {
            vip.setVisible(false);
            dataVisualise.setDisable(false);
            bulkImport.setDisable(false);
        }

        Image img = new Image("file:assets/setting.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setPreserveRatio(true);
        settings.setGraphic(view);
    }
    @FXML
    public void onAddNewPost() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("add-post.fxml")));
        Stage stage = (Stage) addPost.getScene().getWindow();
        Scene scene = new Scene(root, 350, 700);
        stage.setTitle("Data Analytics Hub - Add Post");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onRetrievePost() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("view-post.fxml")));
        Stage stage = (Stage) viewPost.getScene().getWindow();
        Scene scene = new Scene(root, 350, 700);
        stage.setTitle("Data Analytics Hub - View Post");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onDeletePost() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("remove-post.fxml")));
        Stage stage = (Stage) deletePost.getScene().getWindow();
        Scene scene = new Scene(root, 350, 350);
        stage.setTitle("Data Analytics Hub - Remove Post");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onTopPosts() throws IOException{
        Parent root = FXMLLoader.load((Driver.class.getResource("top-posts.fxml")));
        Stage stage = (Stage) topPosts.getScene().getWindow();
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Data Analytics Hub - Top Posts");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onExportPost() throws IOException{
        Parent root = FXMLLoader.load((Driver.class.getResource("export-post.fxml")));
        Stage stage = (Stage) exportPost.getScene().getWindow();
        Scene scene = new Scene(root, 350, 350);
        stage.setTitle("Data Analytics Hub - Export Post");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onLogout() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("main-menu.fxml")));
        Stage stage = (Stage) logout.getScene().getWindow();
        Scene scene = new Scene(root, 350, 350);
        stage.setTitle("Data Analytics Hub - Main Menu");
        stage.setScene(scene);
        stage.show();
        SessionManager.getInstance().clearAuthentication();
    }

    @FXML
    public void onUpgradeVIP() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("upgrade-vip.fxml")));
        Stage stage = (Stage) logout.getScene().getWindow();
        Scene scene = new Scene(root, 350, 350);
        stage.setTitle("Data Analytics Hub - Upgrade VIP");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onDataVisualisation() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("data-visualisation.fxml")));
        Stage stage = (Stage) dataVisualise.getScene().getWindow();
        Scene scene = new Scene(root, 350, 400);
        stage.setTitle("Data Analytics Hub - Data Visualisation");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onBulkImportPost() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("bulk-import-post.fxml")));
        Stage stage = (Stage) bulkImport.getScene().getWindow();
        Scene scene = new Scene(root, 350, 350);
        stage.setTitle("Data Analytics Hub - Bulk Import Post");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onSettings() throws IOException {
        Parent root = FXMLLoader.load((Driver.class.getResource("settings.fxml")));
        Stage stage = (Stage) settings.getScene().getWindow();
        Scene scene = new Scene(root, 350, 450);
        stage.setTitle("Data Analytics Hub - Settings");
        stage.setScene(scene);
        stage.show();
    }
}
