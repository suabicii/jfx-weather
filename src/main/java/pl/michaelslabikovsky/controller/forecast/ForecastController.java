package pl.michaelslabikovsky.controller.forecast;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pl.michaelslabikovsky.controller.WeatherController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ForecastController extends WeatherController {

    protected void startWeatherForecast(List<List<Label>> weatherLabels, List<ImageView> weatherIcons, String cityName) throws IOException {
        for (int i = 0; i < weatherLabels.size(); i++) {
            if (i == 0) {
                getWeatherForecast(cityName,
                        i + 1,
                        weatherLabels.get(i).get(0),
                        weatherLabels.get(i).get(1),
                        weatherLabels.get(i).get(2),
                        weatherLabels.get(i).get(3),
                        weatherLabels.get(i).get(4),
                        weatherIcons.get(i)
                );
            } else {
                getWeatherForecast(cityName,
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
    }

    private void getWeatherForecast(String cityName,
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

    public abstract void showWeatherData(String cityName);

    protected List<Label> fillFirstDayControlsList(Label resultLabel, Label temperatureLabel, Label pressureLabel, Label windSpeedLabel, Label humidityLabel) {
        List<Label> firstDayControls = new ArrayList<>();
        firstDayControls.add(resultLabel);
        firstDayControls.add(temperatureLabel);
        firstDayControls.add(pressureLabel);
        firstDayControls.add(windSpeedLabel);
        firstDayControls.add(humidityLabel);
        return firstDayControls;
    }

    protected List<Label> fillOtherDayControlsList(Label resultLabel, Label temperatureLabel) {
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
