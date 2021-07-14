package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.michaelslabikovsky.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CityChoiceController extends BaseController implements Initializable {

    @FXML
    private TextField citySearchField;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> countyCol;

    private String searchFieldValue;

    public CityChoiceController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        citySearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFieldValue = newValue;
        });
    }

    @FXML
    public void searchCityAfterKeyReleased() {
        System.out.println(searchFieldValue);
    }

    @FXML
    public void addCityAction() {

    }

    @FXML
    public void cancelAction() {
        viewFactory.closeStage((Stage) citySearchField.getScene().getWindow());
    }
}
