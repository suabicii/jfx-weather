package pl.michaelslabikovsky.controller.forecast;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import pl.michaelslabikovsky.controller.BaseController;
import pl.michaelslabikovsky.controller.WeatherController;
import pl.michaelslabikovsky.model.WeatherData;
import pl.michaelslabikovsky.utils.JSONConverter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static pl.michaelslabikovsky.controller.BaseController.getIconUrl;

public abstract class ForecastController extends WeatherController {

    protected void getWeatherForecast(String cityName,
                                      int timeIntervalInDays,
                                      Label weatherLabel,
                                      Label temperatureLabel,
                                      Label pressureLabel,
                                      Label windSpeedLabel,
                                      Label humidityLabel,
                                      ImageView weatherIcon) throws IOException {

        fillControlsByWeatherData(cityName,
                timeIntervalInDays,
                weatherLabel,
                temperatureLabel,
                pressureLabel,
                windSpeedLabel,
                humidityLabel,
                weatherIcon
        );
    }

    public abstract void showWeatherData();

    protected List<Label> fillFirstDayControlsList(Label resultLabel, Label temperatureLabel, Label pressureLabel, Label windSpeedLabel, Label humidityLabel) {
        List<Label> firstDayControls = new ArrayList<>();
        firstDayControls.add(resultLabel);
        firstDayControls.add(temperatureLabel);
        firstDayControls.add(pressureLabel);
        firstDayControls.add(windSpeedLabel);
        firstDayControls.add(humidityLabel);
        return firstDayControls;
    }

    protected List<Label> fillOtherDayControlList(Label resultLabel, Label temperatureLabel) {
        List<Label> otherDayControls = new ArrayList<>();
        otherDayControls.add(resultLabel);
        otherDayControls.add(temperatureLabel);
        return otherDayControls;
    }

    protected List<ImageView> fillImageViewList(ImageView firstDayIcon, ImageView secondDayIcon, ImageView thirdDayIcon, ImageView fourthDayIcon, ImageView fifthDayIcon) {
        List<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(firstDayIcon);
        imageViewList.add(secondDayIcon);
        imageViewList.add(thirdDayIcon);
        imageViewList.add(fourthDayIcon);
        imageViewList.add(fifthDayIcon);
        return imageViewList;
    }
}
