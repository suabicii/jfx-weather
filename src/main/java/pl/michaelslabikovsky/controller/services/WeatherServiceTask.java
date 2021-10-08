package pl.michaelslabikovsky.controller.services;

import pl.michaelslabikovsky.model.WeatherDataClient;
import pl.michaelslabikovsky.model.WeatherDataReceived;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class WeatherServiceTask implements Callable<List<WeatherDataReceived>> {

    private final String mainApiPart;
    private final String additionalApiPart;
    private final String cityName;
    private final int timeIntervalInDays;
    private final List<WeatherDataReceived> result = new ArrayList<>();

    public WeatherServiceTask(String mainApiPart, String additionalApiPart, String cityName, int timeIntervalInDays) {
        this.mainApiPart = mainApiPart;
        this.additionalApiPart = additionalApiPart;
        this.cityName = cityName;
        this.timeIntervalInDays = timeIntervalInDays;
    }

    @Override
    public List<WeatherDataReceived> call() {
        WeatherDataClient dataClient = new WeatherDataClient(mainApiPart, additionalApiPart);
        dataClient.loadWeatherData(cityName);
        String dateTimeBasedOnTimeInterval = dataClient.getDateTimeBasedOnTimeInterval(timeIntervalInDays);
        saveWeatherDataToList(dataClient, dateTimeBasedOnTimeInterval);
        return result;
    }

    private void saveWeatherDataToList(WeatherDataClient dataClient, String dateTimeBasedOnTimeInterval) {
        for (int i = 0; i < dataClient.getDataAmount(); i++) {
            String iteratedDate = dataClient.getDateTimeBasedOnArrayIndex(i);
            if (timeIntervalInDays <= 1) {
                if (iteratedDate.equals(dateTimeBasedOnTimeInterval)) {
                    result.add(new WeatherDataReceived(
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
                    result.add(new WeatherDataReceived(
                            dataClient.getDescription(i),
                            dataClient.getTemperature(i),
                            dataClient.getImageWithUrl(i)
                    ));
                }
            }
        }
    }
}
