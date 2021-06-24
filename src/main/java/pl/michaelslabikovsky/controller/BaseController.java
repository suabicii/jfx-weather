package pl.michaelslabikovsky.controller;

import javafx.scene.image.Image;
import org.json.JSONArray;
import pl.michaelslabikovsky.WeatherManager;
import pl.michaelslabikovsky.view.ViewFactory;

public abstract class BaseController {

    protected WeatherManager weatherManager;
    protected ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        this.weatherManager = weatherManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }

    public static String getIconUrl(JSONArray jsonArray) {
        String weatherIconId = jsonArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
        return "https://openweathermap.org/img/wn/" + weatherIconId + "@2x.png";
    }

    public static Image setImageUrl(String url) {
        return new Image(url);
    }
}
