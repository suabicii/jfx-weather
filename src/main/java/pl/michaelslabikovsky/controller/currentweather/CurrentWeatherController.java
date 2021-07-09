package pl.michaelslabikovsky.controller.currentweather;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pl.michaelslabikovsky.controller.WeatherController;

import java.io.IOException;

public abstract class CurrentWeatherController extends WeatherController {

    protected void getCurrentWeather(String cityName,
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
}
