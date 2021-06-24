package pl.michaelslabikovsky.controller.currentweather;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CurrentWeatherCityTwoController extends CurrentWeatherController {

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

    public CurrentWeatherCityTwoController() {
        currentWeatherCityTwoImg = new ImageView();
        currentWeatherResultCityTwo = new Label();
        currentTemperatureCityTwo = new Label();
        currentPressureCityTwo = new Label();
        currentWindSpeedCityTwo = new Label();
        currentHumidityCityTwo = new Label();
    }

    @Override
    public void showWeatherData() {
        try {
            getCurrentWeather("Londyn", currentWeatherResultCityTwo, currentTemperatureCityTwo, currentPressureCityTwo, currentWindSpeedCityTwo, currentHumidityCityTwo, currentWeatherCityTwoImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
