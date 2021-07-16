package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
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
    private TableView<Location> locationTable;

    @FXML
    private TableColumn<Location, String> nameCol;

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
        nameCol.setCellValueFactory(new PropertyValueFactory<Location, String>("city"));
        locationTable.getColumns().add(nameCol);
        locationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    public void searchCityAfterTextChange() {
        new Thread(() -> {
            try {
                locationTable.getItems().clear();
                location = new Location(searchFieldValue);
                String result = location.getResult();
                JSONArray resultArray = JSONConverter.convertStringObjectToJSONArray(result);
                fillTableColumn(resultArray, location);
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

    private void fillTableColumn(JSONArray resultArray, Location location) {
        for (int i = 0; i < resultArray.length(); i++) {
            String cityData = "";
            JSONObject resultPart = resultArray.getJSONObject(i);
            if (resultPart.has("local_names")) {
                if (resultPart.getJSONObject("local_names").has("pl")) {
                    String polishLocalName = resultPart.getJSONObject("local_names").getString("pl");
                    cityData += polishLocalName.contains("Województwo") ? searchFieldValue + ", " + polishLocalName : polishLocalName;
                } else {
                    cityData += resultPart.getJSONObject("local_names").getString("feature_name");
                }
            } else {
                cityData += resultPart.getString("name");
            }

            cityData += ", " + resultPart.getString("country");

            if (resultPart.has("state")) {
                cityData += ", " + resultPart.getString("state");
            }
            location.setCity(cityData);
            locationTable.getItems().add(location);
        }
    }
}
