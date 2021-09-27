package pl.michaelslabikovsky.controller;

import io.github.cdimascio.dotenv.DotenvException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.michaelslabikovsky.model.LocationClient;
import pl.michaelslabikovsky.model.LocationsDBModel;
import pl.michaelslabikovsky.model.SavedLocation;
import pl.michaelslabikovsky.utils.DialogUtils;
import pl.michaelslabikovsky.utils.DotenvClient;
import pl.michaelslabikovsky.utils.DotenvLoader;
import pl.michaelslabikovsky.view.ViewFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class AddCityController extends BaseController implements Initializable {

    private static final String MAIN_API_PART = "https://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String ADDITIONAL_API_PART = "&limit=5&appid=" + DotenvClient.getApiKey();
    private static final String SUCCESS_MESSAGE = "Dodano miejscowość";
    private static final String ERROR_MESSAGE = "Nie udało się dodać miejscowości";

    @FXML
    private TextField citySearchField;

    @FXML
    private TableView<SavedLocation> locationTable;

    @FXML
    private TableColumn<SavedLocation, String> nameCol;

    @FXML
    private Label messageLabel;

    private String searchFieldValue;
    private LocationClient locationClient;

    public AddCityController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        citySearchField.textProperty().addListener((observable, oldValue, newValue) -> searchFieldValue = newValue);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationTable.getColumns().add(nameCol);
        locationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    public void citySearchFieldOnAction() {
        getLocationsFromApi();
    }

    @FXML
    public void findButtonOnAction() {
        getLocationsFromApi();
    }

    @FXML
    public void addCityAction() {
        LocationsDBModel locationsDBModel = new LocationsDBModel();
        String selectedCity = locationTable.getSelectionModel().selectedItemProperty().getValue().getName();

        messageLabel.setStyle(null);
        if (locationsDBModel.insertIntoTable(selectedCity)) {
            messageLabel.getStyleClass().add("success");
            messageLabel.setText(SUCCESS_MESSAGE);
        } else {
            messageLabel.getStyleClass().add("danger");
            messageLabel.setText(ERROR_MESSAGE);
        }

        clearMessageLabel();
    }

    @FXML
    public void cancelAction() {
        viewFactory.closeStage((Stage) citySearchField.getScene().getWindow());
    }

    private void getLocationsFromApi() {
        new Thread(() -> {
            try {
                locationTable.getItems().clear();
                locationClient = new LocationClient(MAIN_API_PART, ADDITIONAL_API_PART);
                locationClient.loadLocationData(searchFieldValue);
                fillTableColumn();
            } catch (IOException e) {
                DialogUtils.errorDialog(e.getMessage());
            }
        }).start();
    }

    private void fillTableColumn() {
        for (int i = 0; i < locationClient.getLocationDataAmount(); i++) {
            String foundLocation = locationClient.getFoundLocation(i, searchFieldValue);
            SavedLocation savedLocation = new SavedLocation(foundLocation);
            locationTable.getItems().add(savedLocation);
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
