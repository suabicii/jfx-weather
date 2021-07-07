package pl.michaelslabikovsky.controller.forecast;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private String CITY_NAME = "Londyn";

    @Override
    public void showWeatherData() {
        List<List<Label>> weatherLabels = new ArrayList<>();
        weatherLabels.add(fillFirstDayControlsList(firstDayResultCityTwo, firstDayTemperatureCityTwo, pressureCityTwo, windSpeedCityTwo, humidityCityTwo));
        weatherLabels.add(fillOtherDayControlList(secondDayResultCityTwo, secondDayTemperatureTwo));
        weatherLabels.add(fillOtherDayControlList(thirdDayResultCityTwo, thirdDayTemperatureCityTwo));
        weatherLabels.add(fillOtherDayControlList(fourthDayResultCityTwo, fourthDayTemperatureCityTwo));
        weatherLabels.add(fillOtherDayControlList(fifthDayResultCityTwo, fifthDayTemperatureCityTwo));
        List<ImageView> weatherIcons = fillImageViewList(firstDayIconCityTwo, secondDayIconCityTwo, thirdDayIconCityTwo, fourthDayIconCityTwo, fifthDayIconCityTwo);

        try {
            for (int i = 0; i < weatherLabels.size(); i++) {
                if (i == 0) {
                    getWeatherForecast(CITY_NAME,
                            i + 1,
                            weatherLabels.get(i).get(0),
                            weatherLabels.get(i).get(1),
                            weatherLabels.get(i).get(2),
                            weatherLabels.get(i).get(3),
                            weatherLabels.get(i).get(4),
                            weatherIcons.get(i)
                    );
                } else {
                    getWeatherForecast(CITY_NAME,
                            i + 1,
                            weatherLabels.get(i).get(0),
                            weatherLabels.get(i).get(1),
                            null,
                            null,
                            null,
                            weatherIcons.get(i)
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
