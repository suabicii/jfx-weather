package pl.michaelslabikovsky.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.michaelslabikovsky.model.WeatherDataReceived;
import pl.michaelslabikovsky.utils.DotenvClient;

import java.util.List;

public class WeatherService extends Service<List<WeatherDataReceived>> {

    private final String mainApiPart;
    private final String additionalApiPart;
    private final String cityName;
    private final int timeIntervalInDays;

    public WeatherService(String cityName, int timeIntervalInDays) {
        this.cityName = cityName;
        this.timeIntervalInDays = timeIntervalInDays;
        this.mainApiPart = "https://api.openweathermap.org/data/2.5/forecast?q=";
        this.additionalApiPart = "&lang=pl&units=metric&appid=" + DotenvClient.getApiKey();
    }

    @Override
    protected Task<List<WeatherDataReceived>> createTask() {
        return new Task<>() {
            @Override
            protected List<WeatherDataReceived> call() {
                return new WeatherServiceTask(mainApiPart, additionalApiPart, cityName, timeIntervalInDays).call();
            }
        };
    }
}
