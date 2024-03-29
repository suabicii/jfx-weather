package pl.michaelslabikovsky.controller.currentweather;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pl.michaelslabikovsky.utils.DialogUtils;

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

    @Override
    public void showWeatherData(String cityName) {
        try {
            showCurrentWeather(cityName,
                    currentWeatherResultCityTwo,
                    currentTemperatureCityTwo,
                    currentPressureCityTwo,
                    currentWindSpeedCityTwo,
                    currentHumidityCityTwo,
                    currentWeatherCityTwoImg);
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }
}
