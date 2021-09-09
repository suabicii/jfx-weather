package pl.michaelslabikovsky.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.michaelslabikovsky.model.WeatherDataClient;
import pl.michaelslabikovsky.model.WeatherDataReceived;

import java.util.ArrayList;
import java.util.List;

public class WeatherService extends Service<Void> {

    private final String cityName;
    private final int timeIntervalInDays;
    private final List<WeatherDataReceived> weatherDataReceivedList = new ArrayList<>();

    public WeatherService(String cityName, int timeIntervalInDays) {
        this.cityName = cityName;
        this.timeIntervalInDays = timeIntervalInDays;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                WeatherDataClient dataClient = new WeatherDataClient();
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
                            dataClient.setImageUrl(i)
                    ));
                }
            } else {
                if (iteratedDate.equals(dateTimeBasedOnTimeInterval)) {
                    weatherDataReceivedList.add(new WeatherDataReceived(
                            dataClient.getDescription(i),
                            dataClient.getTemperature(i),
                            dataClient.setImageUrl(i)
                    ));
                }
            }
        }
    }

    public List<WeatherDataReceived> getWeatherDataReceivedList() {
        return weatherDataReceivedList;
    }
}
