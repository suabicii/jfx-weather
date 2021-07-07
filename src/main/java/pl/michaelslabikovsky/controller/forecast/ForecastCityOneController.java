package pl.michaelslabikovsky.controller.forecast;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Icons
     */
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

    private final String CITY_NAME = "Warszawa";

    @Override
    public void showWeatherData() {
        List<List<Label>> weatherLabels = new ArrayList<>();
        weatherLabels.add(fillFirstDayControlsList(firstDayResultCityOne, firstDayTemperatureCityOne, pressureCityOne, windSpeedCityOne, humidityCityOne));
        weatherLabels.add(fillOtherDayControlList(secondDayResultCityOne, secondDayTemperatureOne));
        weatherLabels.add(fillOtherDayControlList(thirdDayResultCityOne, thirdDayTemperatureCityOne));
        weatherLabels.add(fillOtherDayControlList(fourthDayResultCityOne, fourthDayTemperatureCityOne));
        weatherLabels.add(fillOtherDayControlList(fifthDayResultCityOne, fifthDayTemperatureCityOne));
        List<ImageView> weatherIcons = fillImageViewList(firstDayIconCityOne, secondDayIconCityOne, thirdDayIconCityOne, fourthDayIconCityOne, fifthDayIconCityOne);

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
