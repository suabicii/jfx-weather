package pl.michaelslabikovsky.controller.forecast;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pl.michaelslabikovsky.utils.DialogUtils;

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

    @Override
    public void showWeatherData(String cityName) {
        List<List<Label>> weatherLabels = fillLabelsList();
        List<ImageView> weatherIcons = fillImageViewList(firstDayIconCityOne, secondDayIconCityOne, thirdDayIconCityOne, fourthDayIconCityOne, fifthDayIconCityOne);

        try {
            showWeatherForecast(weatherLabels, weatherIcons, cityName);
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    private List<List<Label>> fillLabelsList() {
        List<List<Label>> labels = new ArrayList<>();
        labels.add(fillFirstDayControlsList(firstDayResultCityOne, firstDayTemperatureCityOne, pressureCityOne, windSpeedCityOne, humidityCityOne));
        labels.add(fillOtherDayControlsList(secondDayResultCityOne, secondDayTemperatureOne));
        labels.add(fillOtherDayControlsList(thirdDayResultCityOne, thirdDayTemperatureCityOne));
        labels.add(fillOtherDayControlsList(fourthDayResultCityOne, fourthDayTemperatureCityOne));
        labels.add(fillOtherDayControlsList(fifthDayResultCityOne, fifthDayTemperatureCityOne));
        return labels;
    }
}
