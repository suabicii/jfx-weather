package pl.michaelslabikovsky.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class DialogUtils {

    public static final String ABOUT_TITLE = "JFX Weather by Michael Slabikovsky | O programie";
    public static final String ABOUT_HEADER = "O programie";
    public static final String ABOUT_CONTENT = "Wersja: 1.0\nAutor: Michał „Michael Slabikovsky” Słabik\nhttps://dev.michaelslabikovsky.pl";
    public static final String ERROR_TITLE = "JFX Weather by Michael Slabikovsky | Error";
    public static final String ERROR_HEADER = "Błąd";

    public static void aboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ABOUT_TITLE);
        alert.setHeaderText(ABOUT_HEADER);
        alert.setContentText(ABOUT_CONTENT);
        alert.showAndWait();
    }

    public static void errorDialog(String errorMessage) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(ERROR_TITLE);
        errorAlert.setHeaderText(ERROR_HEADER);
        TextArea textArea = new TextArea(errorMessage);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }
}
