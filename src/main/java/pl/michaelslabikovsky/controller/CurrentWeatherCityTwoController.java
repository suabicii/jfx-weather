package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pl.michaelslabikovsky.WeatherManager;
import pl.michaelslabikovsky.view.ViewFactory;

import java.io.IOException;

public class CurrentWeatherCityTwoController extends MainWindowController {

    @FXML
    private ImageView currentWeatherCityTwoImg;

    @FXML
    private Label currentWeatherResultCityTwo;

    @FXML
    private Label currentTemperatureCityTwo;

    @FXML
    private Label currentPressureCityTwo;

    @FXML
    private Label currentWindSpeedCityTwo;

    @FXML
    private Label currentHumidityCityTwo;

    public CurrentWeatherCityTwoController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
    }

    public void showWeatherData() {
        try {
            getCurrentWeather("Londyn", currentWeatherResultCityTwo, currentTemperatureCityTwo, currentPressureCityTwo, currentWindSpeedCityTwo, currentHumidityCityTwo, currentWeatherCityTwoImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
