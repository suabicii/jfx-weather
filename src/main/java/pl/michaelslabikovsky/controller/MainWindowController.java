package pl.michaelslabikovsky.controller;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import pl.michaelslabikovsky.controller.currentweather.CurrentWeatherCityOneController;
import pl.michaelslabikovsky.controller.currentweather.CurrentWeatherCityTwoController;
import pl.michaelslabikovsky.controller.forecast.ForecastCityOneController;
import pl.michaelslabikovsky.controller.forecast.ForecastCityTwoController;
import pl.michaelslabikovsky.model.LocationsDBModel;
import pl.michaelslabikovsky.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    private final Runnable getWeatherInFirstCityRunnable = MainWindowController.this::getWeatherInFirstCity;
    private final Runnable getWeatherInSecondCityRunnable = MainWindowController.this::getWeatherInSecondCity;

    @FXML
    private ChoiceBox<String> cityOneChoiceBox;

    @FXML
    private ChoiceBox<String> cityTwoChoiceBox;

    @FXML
    private CurrentWeatherCityOneController currentWeatherCityOneController;

    @FXML
    private CurrentWeatherCityTwoController currentWeatherCityTwoController;

    @FXML
    private ForecastCityOneController forecastCityOneController;

    @FXML
    private ForecastCityTwoController forecastCityTwoController;

    @FXML
    private Label choiceBoxLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    private LocationsDBModel locationsDBModel;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locationsDBModel = new LocationsDBModel();
        updateChoiceBoxes(locationsDBModel);
        addChoiceBoxesListeners();
        updateWeatherData(getWeatherInFirstCityRunnable);
        updateWeatherData(getWeatherInSecondCityRunnable);
    }

    @FXML
    public void onCloseMenuAction() {
        viewFactory.closeStage((Stage) choiceBoxLabel.getScene().getWindow());
    }

    @FXML
    public void onRefreshMenuAction() {
        getCitiesFromDB(cityOneChoiceBox);
        getCitiesFromDB(cityTwoChoiceBox);
    }

    @FXML
    public void onAddCityMenuAction() {
        viewFactory.showAddCityWindow();
    }

    @FXML
    public void onDeleteCityMenuAction() {
        viewFactory.showDeleteCityWindow();
    }

    @FXML
    public void aboutMenuAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("JFX Weather by Michael Slabikovsky | O programie");
        alert.setHeaderText("O programie");
        alert.setContentText("Wersja: 1.0\nAutor: Michał „Michael Slabikovsky” Słabik\nhttps://dev.michaelslabikovsky.pl");
        alert.showAndWait();
    }

    private void addChoiceBoxesListeners() {
        cityOneChoiceBox.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> updateWeatherData(getWeatherInFirstCityRunnable));
        cityTwoChoiceBox.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> updateWeatherData(getWeatherInSecondCityRunnable));
    }

    private void updateChoiceBoxes(LocationsDBModel locationsDBModel) {
        cityOneChoiceBox.getItems().clear();
        cityTwoChoiceBox.getItems().clear();
        cityOneChoiceBox.getItems().addAll(locationsDBModel.selectAllFromDB());
        cityTwoChoiceBox.getItems().addAll(locationsDBModel.selectAllFromDB());
        cityOneChoiceBox.getSelectionModel().selectFirst();
        cityTwoChoiceBox.getSelectionModel().select(1);
    }

    private void updateWeatherData(Runnable runnable) {
        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws InterruptedException {
                        Thread.sleep(1500);
                        Platform.runLater(runnable);
                        return null;
                    }
                };
            }
        };
        progressIndicator.visibleProperty().bind(service.runningProperty());
        service.start();
    }

    private void getWeatherInFirstCity() {
        currentWeatherCityOneController.showWeatherData(getCityName(cityOneChoiceBox));
        forecastCityOneController.showWeatherData(getCityName(cityOneChoiceBox));
    }

    private void getWeatherInSecondCity() {
        currentWeatherCityTwoController.showWeatherData(getCityName(cityTwoChoiceBox));
        forecastCityTwoController.showWeatherData(getCityName(cityTwoChoiceBox));
    }

    private String getCityName(ChoiceBox<String> choiceBox) {
        return choiceBox.getSelectionModel().getSelectedItem();
    }

    private void getCitiesFromDB(ChoiceBox<String> cityChoiceBox) {
        cityChoiceBox.getSelectionModel().clearSelection();
        locationsDBModel = new LocationsDBModel();
        updateChoiceBoxes(locationsDBModel);
    }
}
