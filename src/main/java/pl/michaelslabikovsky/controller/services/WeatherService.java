package pl.michaelslabikovsky.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.michaelslabikovsky.model.WeatherDataClient;
import pl.michaelslabikovsky.model.WeatherDataReceived;
import pl.michaelslabikovsky.utils.DotenvClient;

import java.util.ArrayList;
import java.util.List;

public class WeatherService extends Service<Void> {

    private final String mainApiPart;
    private final String additionalApiPart;
    private final String cityName;
    private final int timeIntervalInDays;
    private final List<WeatherDataReceived> weatherDataReceivedList = new ArrayList<>();

    public WeatherService(String cityName, int timeIntervalInDays) {
        this.cityName = cityName;
        this.timeIntervalInDays = timeIntervalInDays;
        this.mainApiPart = "https://api.openweathermap.org/data/2.5/forecast?q=";
        this.additionalApiPart = "&lang=pl&units=metric&appid=" + DotenvClient.getApiKey();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                WeatherDataClient dataClient = new WeatherDataClient(mainApiPart, additionalApiPart);
                dataClient.loadWeatherData(cityName);
                String dateTimeBasedOnTimeInterval = dataClient.getDateTimeBasedOnTimeInterval(timeIntervalInDays);
                saveWeatherDataToList(dataClient, dateTimeBasedOnTimeInterval);
                return null;
            }
        };
    }

    private void saveWeatherDataToList(WeatherDataClient dataClient, String dateTimeBasedOnTimeInterval) {
        for (int i = 0; i < dataClient.getDataAmount(); i++) {
            String iteratedDate = dataClient.getDateTimeBasedOnArrayIndex(i);
            if (timeIntervalInDays <= 1) {
                if (iteratedDate.equals(dateTimeBasedOnTimeInterval)) {
                    weatherDataReceivedList.add(new WeatherDataReceived(
                            dataClient.getDescription(i),
                            dataClient.getTemperature(i),
                            dataClient.getPressure(i),
                            dataClient.getWindSpeed(i),
                            dataClient.getHumidity(i),
                            dataClient.getImageWithUrl(i)
                    ));
                }
            } else {
                if (iteratedDate.equals(dateTimeBasedOnTimeInterval)) {
                    weatherDataReceivedList.add(new WeatherDataReceived(
                            dataClient.getDescription(i),
                            dataClient.getTemperature(i),
                            dataClient.getImageWithUrl(i)
                    ));
                }
            }
        }
    }

    public List<WeatherDataReceived> getWeatherDataReceivedList() {
        return weatherDataReceivedList;
    }
}
