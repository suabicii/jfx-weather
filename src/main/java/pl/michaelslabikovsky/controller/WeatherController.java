package pl.michaelslabikovsky.controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pl.michaelslabikovsky.controller.services.WeatherService;
import pl.michaelslabikovsky.model.WeatherDataReceived;

import java.util.List;

public class WeatherController {

    protected void fillControlsByWeatherData(String cityName,
                                             int timeIntervalInDays,
                                             Label weatherLabel,
                                             Label temperatureLabel,
                                             Label pressureLabel,
                                             Label windSpeedLabel,
                                             Label humidityLabel,
                                             ImageView weatherIcon) {
        WeatherService service = new WeatherService(cityName, timeIntervalInDays);
        service.start();
        service.setOnSucceeded(event -> {
            List<WeatherDataReceived> weatherDataReceivedList = service.getValue();
            for (WeatherDataReceived weatherDataReceived : weatherDataReceivedList) {
                weatherLabel.textProperty().bind(weatherDataReceived.descriptionProperty());
                temperatureLabel.textProperty().bind(weatherDataReceived.temperatureProperty());
                pressureLabel.textProperty().bind(weatherDataReceived.pressureProperty());
                windSpeedLabel.textProperty().bind(weatherDataReceived.windSpeedProperty());
                humidityLabel.textProperty().bind(weatherDataReceived.humidityProperty());
                weatherIcon.setImage(weatherDataReceived.getWeatherIcon());
            }
        });
    }

    protected void fillControlsByWeatherData(String cityName,
                                             int timeIntervalInDays,
                                             Label weatherLabel,
                                             Label temperatureLabel,
                                             ImageView weatherIcon) {
        WeatherService service = new WeatherService(cityName, timeIntervalInDays);
        service.start();
        service.setOnSucceeded(event -> {
            List<WeatherDataReceived> weatherDataReceivedList = service.getValue();
            for (WeatherDataReceived weatherDataReceived : weatherDataReceivedList) {
                weatherLabel.textProperty().bind(weatherDataReceived.descriptionProperty());
                temperatureLabel.textProperty().bind(weatherDataReceived.temperatureProperty());
                weatherIcon.setImage(weatherDataReceived.getWeatherIcon());
            }
        });
    }
}
