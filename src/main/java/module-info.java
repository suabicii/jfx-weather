module pl.michaelslabikovsky {
    requires javafx.controls;
    requires javafx.fxml;
    requires dotenv.java;
    requires jdk.jsobject;
    requires org.json;
    requires java.sql;
    requires sqlite.jdbc;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires org.junit.platform.commons;
    requires org.junit.platform.launcher;
    requires org.junit.platform.engine;
    requires org.hamcrest;

    opens pl.michaelslabikovsky to javafx.fxml;
    opens pl.michaelslabikovsky.controller to javafx.fxml;
    opens pl.michaelslabikovsky.controller.currentweather to javafx.fxml;
    opens pl.michaelslabikovsky.controller.forecast to javafx.fxml;
    opens pl.michaelslabikovsky.model to javafx.base, org.junit.platform.commons;
    opens pl.michaelslabikovsky.utils to org.junit.platform.commons;
    exports pl.michaelslabikovsky;
}