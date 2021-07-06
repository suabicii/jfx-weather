package pl.michaelslabikovsky.controller.forecast;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ForecastCityOneController extends ForecastController {

    @FXML
    private Label firstDayResultCityOne;

    @FXML
    private Label firstDayTemperatureCityOne;

    @FXML
    private Label pressureCityOne;

    @FXML
    private Label windSpeedCityOne;

    @FXML
    private Label humidityCityOne;

    @FXML
    private Label secondDayResultCityOne;

    @FXML
    private Label secondDayTemperatureOne;

    @FXML
    private Label thirdDayResultCityOne;

    @FXML
    private Label thirdDayTemperatureCityOne;

    @FXML
    private Label fourthDayResultCityOne;

    @FXML
    private Label fourthDayTemperatureCityOne;

    @FXML
    private Label fifthDayResultCityOne;

    @FXML
    private Label fifthDayTemperatureCityOne;

    /** Icons */
    @FXML
    private ImageView firstDayIconCityOne;

    @FXML
    private ImageView secondDayIconCityOne;

    @FXML
    private ImageView thirdDayIconCityOne;

    @FXML
    private ImageView fourthDayIconCityOne;

    @FXML
    private ImageView fifthDayIconCityOne;

    private String cityName = "Warszawa";

    @Override
    public void showWeatherData() {
        try {
            getWeatherForecast(cityName,
                    1,
                    firstDayResultCityOne,
                    firstDayTemperatureCityOne,
                    pressureCityOne,
                    windSpeedCityOne,
                    humidityCityOne,
                    firstDayIconCityOne);
            getWeatherForecast(cityName,
                    2,
                    secondDayResultCityOne,
                    secondDayTemperatureOne,
                    null,
                    null,
                    null,
                    secondDayIconCityOne);
            getWeatherForecast(cityName,
                    3,
                    thirdDayResultCityOne,
                    thirdDayTemperatureCityOne,
                    null,
                    null,
                    null,
                    thirdDayIconCityOne);
            getWeatherForecast(cityName,
                    4,
                    fourthDayResultCityOne,
                    fourthDayTemperatureCityOne,
                    null,
                    null,
                    null,
                    fourthDayIconCityOne);
            getWeatherForecast(cityName,
                    5,
                    fifthDayResultCityOne,
                    fifthDayTemperatureCityOne,
                    null,
                    null,
                    null,
                    fifthDayIconCityOne);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
