package com.frontend;

import com.backend.PostController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DataVisualisationController {
    @FXML
    PieChart pieChart;
    @FXML
    Button back;
    @FXML
    Label caption;


    @FXML
    public void initialize(){
        PostController postController = new PostController();
        List<Integer> sharesArray = postController.getSharesDistribution();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        int total = sharesArray.get(0) + sharesArray.get(1) + sharesArray.get(2);
        pieChartData.add(new PieChart.Data("0-99", sharesArray.get(0)));
        pieChartData.add(new PieChart.Data("100-999", sharesArray.get(1)));
        pieChartData.add(new PieChart.Data(">=1000", sharesArray.get(2)));
        pieChart.setLabelLineLength(10);
        pieChart.setClockwise(true);
        pieChart.setLabelsVisible(false);
        pieChart.getData().addAll(pieChartData);

        // show percentage of each category in pie chart referenced from here
        // https://stackoverflow.com/questions/28943599/show-pies-percentage-of-piechart
        pieChart.getData().forEach(data -> {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    e -> {
                        caption.setTranslateX(250);
                        caption.setTranslateY(120);
                        String text = String.format("%.1f%%", 100 * data.getPieValue() /total) ;
                        caption.setText(text);
                    }
            );
            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e-> {
                caption.setText("");
            });
        });
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
