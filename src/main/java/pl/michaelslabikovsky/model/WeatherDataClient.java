package pl.michaelslabikovsky.model;

import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.michaelslabikovsky.utils.JSONConverter;

import java.io.IOException;

public class WeatherDataClient extends WeatherData {
    // Nie zapomnij o DateTime!
    private JSONArray allDataJsonArray;

    public void loadWeatherData(String cityName) throws IOException {
        connectToAPI(cityName, getMainAPIPart(), getAdditionalAPIPart());
        String result = getResult();

        JSONObject jsonObject = JSONConverter.convertStringToJSONObject(result);
        allDataJsonArray = jsonObject.getJSONArray("list");
    }

    public int getDataAmount() {
        return allDataJsonArray.length();
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

    public Image setImageUrl(int arrayIndex) {
        String url = getIconUrl(arrayIndex);
        return new Image(url);
    }

    private String getIconUrl(int arrayIndex) {
        String weatherIconId = allDataJsonArray.getJSONObject(arrayIndex)
                .getJSONArray("weather")
                .getJSONObject(0)
                .getString("icon");
        return "https://openweathermap.org/img/wn/" + weatherIconId + "@2x.png";
    }
}
