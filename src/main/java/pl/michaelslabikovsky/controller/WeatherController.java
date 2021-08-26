package pl.michaelslabikovsky.controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.michaelslabikovsky.model.WeatherData;
import pl.michaelslabikovsky.utils.JSONConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static pl.michaelslabikovsky.controller.BaseController.getIconUrl;

public class WeatherController {

    protected void fillControlsByWeatherData(String cityName,
                                             int timeIntervalInDays,
                                             Label weatherLabel,
                                             Label temperatureLabel,
                                             Label pressureLabel,
                                             Label windSpeedLabel,
                                             Label humidityLabel,
                                             ImageView weatherIcon) throws IOException {
        // WeatherData data = weatherDataClient.loadWeather(cityName);
        WeatherData weatherData = new WeatherData(cityName);
        String weatherDataResult = weatherData.getResult();
        JSONObject jsonObject = JSONConverter.convertStringToJSONObject(weatherDataResult);
        JSONArray jsonArray = jsonObject.getJSONArray("list");

        String laterDateTime = getLaterDateTime(timeIntervalInDays, jsonArray);

        for (int i = 0; i < jsonArray.length(); i++) {
            String iteratedDate = jsonArray.getJSONObject(i).getString("dt_txt");
            if (iteratedDate.equals(laterDateTime)) {
                weatherLabel.setText(jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
                temperatureLabel.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp") + "°C");
                pressureLabel.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("pressure") + " hPa");
                windSpeedLabel.setText(jsonArray.getJSONObject(i).getJSONObject("wind").getDouble("speed") + " m/s");
                humidityLabel.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("humidity") + "%");
                weatherIcon.setImage(BaseController.setImageUrl(getIconUrl(jsonArray, i)));
            }
        }
    }

    protected void fillControlsByWeatherData(String cityName,
                                             int timeIntervalInDays,
                                             Label weatherLabel,
                                             Label temperatureLabel,
                                             ImageView weatherIcon) throws IOException {
        WeatherData weatherData = new WeatherData(cityName);
        String weatherDataResult = weatherData.getResult();
        JSONObject jsonObject = JSONConverter.convertStringToJSONObject(weatherDataResult);
        JSONArray jsonArray = jsonObject.getJSONArray("list");

        String laterDateTime = getLaterDateTime(timeIntervalInDays, jsonArray);

        for (int i = 0; i < jsonArray.length(); i++) {
            String iteratedDate = jsonArray.getJSONObject(i).getString("dt_txt");
            if (iteratedDate.equals(laterDateTime)) {
                weatherLabel.setText(jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
                temperatureLabel.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp") + "°C");
                weatherIcon.setImage(BaseController.setImageUrl(getIconUrl(jsonArray, i)));
            }
        }
    }

    private String getLaterDateTime(int timeIntervalInDays, JSONArray jsonArray) {
        String nearestDateTime = jsonArray.getJSONObject(0).getString("dt_txt");
        String nearestHour = getNearestHour(nearestDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime laterDate = now.plusDays(timeIntervalInDays);

        if (timeIntervalInDays == 5) { // ze względu na mniejszą liczbę rekordów z pogody za 5 dni
            nearestHour = getEarlierHour(nearestHour); // muszę cofnąć czas o 3 godziny
        }

        return formatter.format(laterDate).concat(" ").concat(nearestHour);
    }

    private String getNearestHour(String nearestDateTime) {
        String nearestHour = "";

        for (int i = 0; i < nearestDateTime.length(); i++) {
            if (nearestDateTime.charAt(i) == ' ') {
                nearestHour = nearestDateTime.substring(i + 1);
                break;
            }
        }
        return nearestHour;
    }

    private String getEarlierHour(String nearestHour) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime baseHour = LocalTime.parse(nearestHour);
        LocalTime earlierHour = baseHour.minusHours(3);
        return formatter.format(earlierHour);
    }
}
