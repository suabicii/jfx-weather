package pl.michaelslabikovsky.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import pl.michaelslabikovsky.controller.currentweather.CurrentWeatherCityOneController;
import pl.michaelslabikovsky.controller.currentweather.CurrentWeatherCityTwoController;
import pl.michaelslabikovsky.controller.forecast.ForecastCityOneController;
import pl.michaelslabikovsky.controller.forecast.ForecastCityTwoController;
import pl.michaelslabikovsky.model.LocationsDBModel;
import pl.michaelslabikovsky.view.ViewFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindowController extends BaseController implements Initializable {

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

    private LocationsDBModel locationsDBModel;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locationsDBModel = new LocationsDBModel();
        updateChoiceBoxes(locationsDBModel);
        addChoiceBoxesListeners();
        updateWeatherDataInPartOne();
        updateWeatherDataInPartTwo();
    }

    private void addChoiceBoxesListeners() {
        cityOneChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            addCity(cityOneChoiceBox, oldValue);
            deleteCity(cityOneChoiceBox, oldValue);
            updateWeatherDataInPartOne();
            refreshCityList(cityOneChoiceBox);
        });
        cityTwoChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            addCity(cityTwoChoiceBox, oldValue);
            deleteCity(cityTwoChoiceBox, oldValue);
            updateWeatherDataInPartTwo();
            refreshCityList(cityTwoChoiceBox);
        });
    }

    private void updateChoiceBoxes(LocationsDBModel locationsDBModel) {
        cityOneChoiceBox.getItems().clear();
        cityTwoChoiceBox.getItems().clear();
        try {
            cityOneChoiceBox.getItems().addAll(locationsDBModel.selectAllFromDB());
            cityTwoChoiceBox.getItems().addAll(locationsDBModel.selectAllFromDB());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        cityOneChoiceBox.getItems().add("Odśwież listę miejscowości...");
        cityOneChoiceBox.getItems().add("Usuń miejscowość...");
        cityOneChoiceBox.getItems().add("Dodaj miejscowość...");
        cityTwoChoiceBox.getItems().add("Odśwież listę miejscowości...");
        cityTwoChoiceBox.getItems().add("Usuń miejscowość...");
        cityTwoChoiceBox.getItems().add("Dodaj miejscowość...");

        cityOneChoiceBox.getSelectionModel().selectFirst();
        cityTwoChoiceBox.getSelectionModel().select(1);
    }

    private void updateWeatherDataInPartOne() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> getWeatherInFirstCity());
                timer.cancel();
            }
        };
        timer.schedule(task, 500);
    }

    private void updateWeatherDataInPartTwo() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> getWeatherInSecondCity());
                timer.cancel();
            }
        };
        timer.schedule(task, 500);
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

    private void addCity(ChoiceBox<String> cityChoiceBox, Number oldValue) {
        int selectedIndex = cityChoiceBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex == cityChoiceBox.getItems().size() - 1 && selectedIndex != -1) {
            cityChoiceBox.getSelectionModel().select(oldValue.intValue());
            viewFactory.showAddCityWindow();
        }
    }

    private void deleteCity(ChoiceBox<String> cityChoiceBox, Number oldValue) {
        int selectedIndex = cityChoiceBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex == cityChoiceBox.getItems().size() - 2) {
            cityChoiceBox.getSelectionModel().select(oldValue.intValue());
            viewFactory.showDeleteCityWindow();
        }
    }

    private void refreshCityList(ChoiceBox<String> cityChoiceBox) {
        int selectedIndex = cityChoiceBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex == cityChoiceBox.getItems().size() - 3) {
            cityChoiceBox.getSelectionModel().clearSelection();
            locationsDBModel = new LocationsDBModel();
            updateChoiceBoxes(locationsDBModel);
        }
    }
}
