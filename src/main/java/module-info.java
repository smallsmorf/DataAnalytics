module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.models to javafx.base;
    opens com.frontend to javafx.fxml;
    exports com.frontend;
}