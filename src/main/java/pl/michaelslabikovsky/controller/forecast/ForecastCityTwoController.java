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

    @Override
    public void showWeatherData(String cityName) {
        List<List<Label>> weatherLabels = fillLabelsList();
        List<ImageView> weatherIcons = fillImageViewList(firstDayIconCityTwo, secondDayIconCityTwo, thirdDayIconCityTwo, fourthDayIconCityTwo, fifthDayIconCityTwo);

        try {
            showWeatherForecast(weatherLabels, weatherIcons, cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<List<Label>> fillLabelsList() {
        List<List<Label>> labels = new ArrayList<>();
        labels.add(fillFirstDayControlsList(firstDayResultCityTwo, firstDayTemperatureCityTwo, pressureCityTwo, windSpeedCityTwo, humidityCityTwo));
        labels.add(fillOtherDayControlsList(secondDayResultCityTwo, secondDayTemperatureTwo));
        labels.add(fillOtherDayControlsList(thirdDayResultCityTwo, thirdDayTemperatureCityTwo));
        labels.add(fillOtherDayControlsList(fourthDayResultCityTwo, fourthDayTemperatureCityTwo));
        labels.add(fillOtherDayControlsList(fifthDayResultCityTwo, fifthDayTemperatureCityTwo));
        return labels;
    }
}
