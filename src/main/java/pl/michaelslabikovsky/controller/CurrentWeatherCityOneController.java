package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pl.michaelslabikovsky.WeatherManager;
import pl.michaelslabikovsky.view.ViewFactory;

import java.io.IOException;

public class CurrentWeatherCityOneController extends MainWindowController {

    @FXML
    private ImageView currentWeatherCityOneImg;

    @FXML
    private Label currentWeatherResultCityOne;

    @FXML
    private Label currentTemperatureCityOne;

    @FXML
    private Label currentPressureCityOne;

    @FXML
    private Label currentWindSpeedCityOne;

    @FXML
    private Label currentHumidityCityOne;

    public CurrentWeatherCityOneController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
    }

    public void showWeatherData() {
        try {
            getCurrentWeather("Warszawa", currentWeatherResultCityOne, currentTemperatureCityOne, currentPressureCityOne, currentWindSpeedCityOne, currentHumidityCityOne, currentWeatherCityOneImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
