package pl.michaelslabikovsky.controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pl.michaelslabikovsky.model.WeatherDataClient;

import java.io.IOException;

public class WeatherController {

    protected void fillControlsByWeatherData(String cityName,
                                             int timeIntervalInDays,
                                             Label weatherLabel,
                                             Label temperatureLabel,
                                             Label pressureLabel,
                                             Label windSpeedLabel,
                                             Label humidityLabel,
                                             ImageView weatherIcon) throws IOException {
        WeatherDataClient dataClient = new WeatherDataClient();
        dataClient.loadWeatherData(cityName);

        String dateTimeBasedOnTimeInterval = dataClient.getDateTimeBasedOnTimeInterval(timeIntervalInDays);

        for (int i = 0; i < dataClient.getDataAmount(); i++) {
            String iteratedDate = dataClient.getDateTimeBasedOnArrayIndex(i);
            if (iteratedDate.equals(dateTimeBasedOnTimeInterval)) {
                weatherLabel.setText(dataClient.getDescription(i));
                temperatureLabel.setText(dataClient.getTemperature(i));
                pressureLabel.setText(dataClient.getPressure(i));
                windSpeedLabel.setText(dataClient.getWindSpeed(i));
                humidityLabel.setText(dataClient.getHumidity(i));
                weatherIcon.setImage(dataClient.setImageUrl(i));
            }
        }
    }

    protected void fillControlsByWeatherData(String cityName,
                                             int timeIntervalInDays,
                                             Label weatherLabel,
                                             Label temperatureLabel,
                                             ImageView weatherIcon) throws IOException {
        WeatherDataClient dataClient = new WeatherDataClient();
        dataClient.loadWeatherData(cityName);

        String dateTimeBasedOnTimeInterval = dataClient.getDateTimeBasedOnTimeInterval(timeIntervalInDays);

        for (int i = 0; i < dataClient.getDataAmount(); i++) {
            String iteratedDate = dataClient.getDateTimeBasedOnArrayIndex(i);
            if (iteratedDate.equals(dateTimeBasedOnTimeInterval)) {
                weatherLabel.setText(dataClient.getDescription(i));
                temperatureLabel.setText(dataClient.getTemperature(i));
                weatherIcon.setImage(dataClient.setImageUrl(i));
            }
        }
    }
}
