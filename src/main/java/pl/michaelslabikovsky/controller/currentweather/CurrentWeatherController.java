package pl.michaelslabikovsky.controller.currentweather;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import pl.michaelslabikovsky.controller.BaseController;
import pl.michaelslabikovsky.model.WeatherData;
import pl.michaelslabikovsky.utils.JSONConverter;

import java.io.IOException;

import static pl.michaelslabikovsky.controller.BaseController.getIconUrl;

public abstract class CurrentWeatherController {

    protected void getCurrentWeather(String cityName,
                                     Label weatherLabel,
                                     Label temperatureLabel,
                                     Label pressureLabel,
                                     Label windSpeedLabel,
                                     Label humidityLabel,
                                     ImageView weatherIcon) throws IOException {
        WeatherData weatherData = new WeatherData(cityName);
        String weatherDataResult = weatherData.getResult();
        JSONArray jsonArray = JSONConverter.convertStringObjectToJSONArray(weatherDataResult);
        weatherLabel.setText(jsonArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"));
        temperatureLabel.setText(jsonArray.getJSONObject(0).getJSONObject("main").getDouble("temp") + "°C");
        pressureLabel.setText(jsonArray.getJSONObject(0).getJSONObject("main").getInt("pressure") + " hPa");
        windSpeedLabel.setText(jsonArray.getJSONObject(0).getJSONObject("wind").getDouble("speed") + " m/s");
        humidityLabel.setText(jsonArray.getJSONObject(0).getJSONObject("main").getInt("humidity") + "%");
        weatherIcon.setImage(BaseController.setImageUrl(getIconUrl(jsonArray, 0)));
    }

    public abstract void showWeatherData();
}
