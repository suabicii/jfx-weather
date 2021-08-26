package pl.michaelslabikovsky.controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.michaelslabikovsky.model.WeatherData;
import pl.michaelslabikovsky.utils.JSONConverter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        String laterDateTime = null;
        try {
            laterDateTime = getLaterDateTime(timeIntervalInDays, jsonArray);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            String iteratedDate = jsonArray.getJSONObject(i).getString("dt_txt");
            if (iteratedDate.equals(laterDateTime)) {
                weatherLabel.setText(jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
                temperatureLabel.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp") + "°C");
                if (timeIntervalInDays <= 1) {
                    pressureLabel.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("pressure") + " hPa");
                    windSpeedLabel.setText(jsonArray.getJSONObject(i).getJSONObject("wind").getDouble("speed") + " m/s");
                    humidityLabel.setText(jsonArray.getJSONObject(i).getJSONObject("main").getInt("humidity") + "%");
                }
                weatherIcon.setImage(BaseController.setImageUrl(getIconUrl(jsonArray, i)));
            }
        }
    }

    private String getLaterDateTime(int timeIntervalInDays, JSONArray jsonArray) throws ParseException {
        String nearestDateTime = jsonArray.getJSONObject(0).getString("dt_txt");
        String nearestHour = getNearestHour(nearestDateTime);
        String currentDate = getCurrentDate(nearestDateTime);
        Date baseDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);
        calendar.add(Calendar.DAY_OF_MONTH, timeIntervalInDays);
        Date laterDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (timeIntervalInDays == 5) { // ze względu na mniejszą liczbę rekordów z pogody za 5 dni
            nearestHour = getEarlierHour(nearestHour); // muszę cofnąć czas o 3 godziny
        }

        return dateFormat.format(laterDate).concat(" ").concat(nearestHour);
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

    private String getCurrentDate(String nearestDateTime) {
        String date = "";

        for (int i = 0; i < nearestDateTime.length(); i++) {
            if (nearestDateTime.charAt(i) == ' ') {
                date = nearestDateTime.substring(0, i);
                break;
            }
        }
        return date;
    }

    private String getEarlierHour(String nearestHour) throws ParseException {
        Date baseHour = new SimpleDateFormat("HH:mm:ss").parse(nearestHour);
        Calendar calendarForHour = Calendar.getInstance();
        calendarForHour.setTime(baseHour);
        calendarForHour.add(Calendar.HOUR, -3);
        Date earlierHour = calendarForHour.getTime();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        return hourFormat.format(earlierHour);
    }
}
