module pl.michaelslabikovsky {
    requires javafx.controls;
    requires javafx.fxml;
    requires dotenv.java;
    requires json.simple;
    requires jdk.jsobject;
    requires org.json;

    opens pl.michaelslabikovsky to javafx.fxml;
    opens pl.michaelslabikovsky.controller to javafx.fxml;
    exports pl.michaelslabikovsky;
}