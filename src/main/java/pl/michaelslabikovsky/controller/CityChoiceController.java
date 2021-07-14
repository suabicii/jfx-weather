package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.michaelslabikovsky.model.Location;
import pl.michaelslabikovsky.utils.JSONConverter;
import pl.michaelslabikovsky.view.ViewFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CityChoiceController extends BaseController implements Initializable {

    @FXML
    private TextField citySearchField;

    @FXML
    private TableColumn<Location, String> nameCol;

    @FXML
    private TableColumn<Location, String> countyCol;

    private String searchFieldValue;
    private Location location;

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
    public void searchCityAfterKeyIsReleased() {
        new Thread(() -> {
            try {
                location = new Location(searchFieldValue);
                String result = location.getResult();
                System.out.println("Miejscowość: " + JSONConverter.convertStringObjectToJSONArray(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    public void addCityAction() {

    }

    @FXML
    public void cancelAction() {
        viewFactory.closeStage((Stage) citySearchField.getScene().getWindow());
    }
}
