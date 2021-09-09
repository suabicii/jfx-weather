module pl.michaelslabikovsky {
    requires javafx.controls;
    requires javafx.fxml;
    requires dotenv.java;
    requires jdk.jsobject;
    requires org.json;
    requires java.sql;
    requires sqlite.jdbc;

    opens pl.michaelslabikovsky to javafx.fxml;
    opens pl.michaelslabikovsky.controller to javafx.fxml;
    opens pl.michaelslabikovsky.controller.currentweather to javafx.fxml;
    opens pl.michaelslabikovsky.controller.forecast to javafx.fxml;
    exports pl.michaelslabikovsky;
}