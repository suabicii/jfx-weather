package pl.michaelslabikovsky.model;

import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.michaelslabikovsky.utils.DialogUtils;
import pl.michaelslabikovsky.utils.JSONConverter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WeatherDataClient extends WeatherData {

    private JSONArray allDataJsonArray;

    public WeatherDataClient(String mainApiPart, String additionalApiPart) {
        super(mainApiPart, additionalApiPart);
    }

    public void loadWeatherData(String cityName)  {
        try {
            connectToApi(cityName, getMainAPIPart(), getAdditionalAPIPart());
        } catch (MalformedURLException e) {
            DialogUtils.errorDialog(e.getMessage());
        }

        String result = "";
        try {
            result = getResult();
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
        }

        JSONObject jsonObject = JSONConverter.convertStringToJSONObject(result);
        allDataJsonArray = jsonObject.getJSONArray("list");
    }

    public String getDateTimeBasedOnTimeInterval(int timeIntervalInDays) {
        String nearestDateTime = allDataJsonArray.getJSONObject(0).getString("dt_txt");
        String nearestHour = getNearestHour(nearestDateTime);
        DateTimeFormatter mainFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter baseDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime baseDate = LocalDateTime.parse(nearestDateTime, baseDateTimeFormatter);
        LocalDateTime laterDate = baseDate.plusDays(timeIntervalInDays);

        if (timeIntervalInDays == 5) { // ze względu na mniejszą liczbę rekordów z pogody za 5 dni
            nearestHour = getEarlierHour(nearestHour); // muszę cofnąć czas o 3 godziny
        }

        return mainFormatter.format(laterDate).concat(" ").concat(nearestHour);
    }

    public int getDataAmount() {
        return allDataJsonArray.length();
    }

    public String getDateTimeBasedOnArrayIndex(int arrayIndex) {
        return allDataJsonArray.getJSONObject(arrayIndex).getString("dt_txt");
    }

    public String getDescription(int arrayIndex) {
        return allDataJsonArray.getJSONObject(arrayIndex)
                .getJSONArray("weather")
                .getJSONObject(0)
                .getString("description");
    }

    public String getTemperature(int arrayIndex) {
        return allDataJsonArray.getJSONObject(arrayIndex)
                .getJSONObject("main")
                .getInt("temp") + "°C";
    }

    public String getPressure(int arrayIndex) {
        return allDataJsonArray.getJSONObject(arrayIndex)
                .getJSONObject("main")
                .getInt("pressure") + " hPa";
    }

    public String getWindSpeed(int arrayIndex) {
        return allDataJsonArray.getJSONObject(arrayIndex)
                .getJSONObject("wind")
                .getDouble("speed") + " m/s";
    }

    public String getHumidity(int arrayIndex) {
        return allDataJsonArray.getJSONObject(arrayIndex)
                .getJSONObject("main")
                .getInt("humidity") + "%";
    }

    public Image getImageWithUrl(int arrayIndex) {
        String url = getIconUrl(arrayIndex);
        return new Image(url);
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

    private String getIconUrl(int arrayIndex) {
        String weatherIconId = allDataJsonArray.getJSONObject(arrayIndex)
                .getJSONArray("weather")
                .getJSONObject(0)
                .getString("icon");
        return "https://openweathermap.org/img/wn/" + weatherIconId + "@2x.png";
    }
}
