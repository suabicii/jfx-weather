package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import pl.michaelslabikovsky.WeatherManager;
import pl.michaelslabikovsky.controller.currentweather.CurrentWeatherCityOneController;
import pl.michaelslabikovsky.controller.currentweather.CurrentWeatherCityTwoController;
import pl.michaelslabikovsky.controller.currentweather.CurrentWeatherController;
import pl.michaelslabikovsky.model.WeatherData;
import pl.michaelslabikovsky.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private ChoiceBox<String> cityOneChoiceBox;

    @FXML
    private ChoiceBox<String> cityTwoChoiceBox;

    private WeatherData weatherData;

    private CurrentWeatherController currentWeatherCityOneController;

    private CurrentWeatherController currentWeatherCityTwoController;

    public MainWindowController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentWeatherCityOneController = new CurrentWeatherCityOneController();
        currentWeatherCityTwoController = new CurrentWeatherCityTwoController();

        cityOneChoiceBox.getItems().addAll("Warszawa", "Dodaj miejscowość...");
        cityTwoChoiceBox.getItems().addAll("Londyn", "Dodaj miejscowość...");
        cityOneChoiceBox.getSelectionModel().selectFirst();
        cityTwoChoiceBox.getSelectionModel().selectFirst();

        cityOneChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> addCity(cityOneChoiceBox, oldValue));

        cityTwoChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> addCity(cityTwoChoiceBox, oldValue));

        new Thread(() -> currentWeatherCityOneController.showWeatherData()).start();

        new Thread(() -> currentWeatherCityTwoController.showWeatherData()).start();
    }

    private void addCity(ChoiceBox<String> cityChoiceBox, Number oldValue) {
        if (cityChoiceBox.getSelectionModel().getSelectedIndex() == cityChoiceBox.getItems().size() - 1) {
            cityChoiceBox.getSelectionModel().select(oldValue.intValue());
        }
    }
}
