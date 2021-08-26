package pl.michaelslabikovsky.controller.currentweather;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CurrentWeatherCityOneController extends CurrentWeatherController {

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

    @Override
    public void showWeatherData(String cityName) {
        try {
            showCurrentWeather(cityName, currentWeatherResultCityOne, currentTemperatureCityOne, currentPressureCityOne, currentWindSpeedCityOne, currentHumidityCityOne, currentWeatherCityOneImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
