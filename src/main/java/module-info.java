module pl.michaelslabikovsky {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.michaelslabikovsky to javafx.fxml;
    opens pl.michaelslabikovsky.controller to javafx.fxml;
    exports pl.michaelslabikovsky;
}