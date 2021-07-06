package pl.michaelslabikovsky.controller.forecast;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ForecastCityTwoController extends ForecastController {

    @FXML
    private Label firstDayResultCityTwo;

    @FXML
    private Label firstDayTemperatureCityTwo;

    @FXML
    private Label pressureCityTwo;

    @FXML
    private Label windSpeedCityTwo;

    @FXML
    private Label humidityCityTwo;

    @FXML
    private Label secondDayResultCityTwo;

    @FXML
    private Label secondDayTemperatureTwo;

    @FXML
    private Label thirdDayResultCityTwo;

    @FXML
    private Label thirdDayTemperatureCityTwo;

    @FXML
    private Label fourthDayResultCityTwo;

    @FXML
    private Label fourthDayTemperatureCityTwo;

    @FXML
    private Label fifthDayResultCityTwo;

    @FXML
    private Label fifthDayTemperatureCityTwo;

    /** Icons */
    @FXML
    private ImageView firstDayIconCityTwo;

    @FXML
    private ImageView secondDayIconCityTwo;

    @FXML
    private ImageView thirdDayIconCityTwo;

    @FXML
    private ImageView fourthDayIconCityTwo;

    @FXML
    private ImageView fifthDayIconCityTwo;

    private String cityName = "Londyn";

    @Override
    public void showWeatherData() {
        try {
            getWeatherForecast(cityName,
                    1,
                    firstDayResultCityTwo,
                    firstDayTemperatureCityTwo,
                    pressureCityTwo,
                    windSpeedCityTwo,
                    humidityCityTwo,
                    firstDayIconCityTwo);
            getWeatherForecast(cityName,
                    2,
                    secondDayResultCityTwo,
                    secondDayTemperatureTwo,
                    null,
                    null,
                    null,
                    secondDayIconCityTwo);
            getWeatherForecast(cityName,
                    3,
                    thirdDayResultCityTwo,
                    thirdDayTemperatureCityTwo,
                    null,
                    null,
                    null,
                    thirdDayIconCityTwo);
            getWeatherForecast(cityName,
                    4,
                    fourthDayResultCityTwo,
                    fourthDayTemperatureCityTwo,
                    null,
                    null,
                    null,
                    fourthDayIconCityTwo);
            getWeatherForecast(cityName,
                    5,
                    fifthDayResultCityTwo,
                    fifthDayTemperatureCityTwo,
                    null,
                    null,
                    null,
                    fifthDayIconCityTwo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
