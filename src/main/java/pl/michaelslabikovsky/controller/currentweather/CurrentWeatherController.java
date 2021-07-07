package pl.michaelslabikovsky.controller.currentweather;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import pl.michaelslabikovsky.controller.BaseController;
import pl.michaelslabikovsky.controller.WeatherController;
import pl.michaelslabikovsky.model.WeatherData;
import pl.michaelslabikovsky.utils.JSONConverter;

import java.io.IOException;

import static pl.michaelslabikovsky.controller.BaseController.getIconUrl;

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

    public abstract void showWeatherData();
}
