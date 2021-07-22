package pl.michaelslabikovsky.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.michaelslabikovsky.model.Location;
import pl.michaelslabikovsky.model.LocationsDBModel;
import pl.michaelslabikovsky.utils.JSONConverter;
import pl.michaelslabikovsky.view.ViewFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class CityChoiceController extends BaseController implements Initializable {

    @FXML
    private TextField citySearchField;

    @FXML
    private TableView<Location> locationTable;

    @FXML
    private TableColumn<Location, String> nameCol;

    @FXML
    private Label messageLabel;

    private String searchFieldValue;
    private Location location;
    private LocationsDBModel locationsDBModel;

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
    public void citySearchFieldOnAction() {
        getLocationsFromAPI();
    }

    @FXML
    public void findButtonOnAction() {
        getLocationsFromAPI();
    }

    @FXML
    public void addCityAction() {
        locationsDBModel = new LocationsDBModel();
        String selectedCity = locationTable.getSelectionModel().selectedItemProperty().getValue().getCity();

        try {
            if (locationsDBModel.insertIntoTable(selectedCity)) {
                messageLabel.setStyle("-fx-text-fill: green");
                messageLabel.setText("Dodano miejscowość");
            } else {
                messageLabel.setStyle("-fx-text-fill: red");
                messageLabel.setText("Nie udało się dodać miejscowości");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        clearMessageLabel();
    }

    @FXML
    public void cancelAction() {
        viewFactory.closeStage((Stage) citySearchField.getScene().getWindow());
    }

    private void getLocationsFromAPI() {
        new Thread(() -> {
            try {
                locationTable.getItems().clear();
                location = new Location(searchFieldValue);
                String result = location.getResult();
                JSONArray resultArray = JSONConverter.convertStringObjectToJSONArray(result);
                fillTableColumn(resultArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void fillTableColumn(JSONArray resultArray) throws MalformedURLException {
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
            location = new Location(searchFieldValue);
            location.setCity(cityData);
            locationTable.getItems().add(location);
        }
    }

    private void clearMessageLabel() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    messageLabel.setText("");
                    timer.cancel();
                });
            }
        };
        timer.schedule(task, 3000);
    }
}
