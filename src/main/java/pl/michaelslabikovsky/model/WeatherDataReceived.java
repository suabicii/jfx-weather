package pl.michaelslabikovsky.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class WeatherDataReceived {

    private final SimpleStringProperty description;
    private final SimpleStringProperty temperature;
    private SimpleStringProperty pressure;
    private SimpleStringProperty windSpeed;
    private SimpleStringProperty humidity;
    private final Image weatherIcon;

    public WeatherDataReceived(String description,
                               String temperature,
                               String pressure,
                               String windSpeed,
                               String humidity,
                               Image weatherIcon) {
        this.description = new SimpleStringProperty(description);
        this.temperature = new SimpleStringProperty(temperature);
        this.pressure = new SimpleStringProperty(pressure);
        this.windSpeed = new SimpleStringProperty(windSpeed);
        this.humidity = new SimpleStringProperty(humidity);
        this.weatherIcon = weatherIcon;
    }

    public WeatherDataReceived(String description, String temperature, Image weatherIcon) {
        this.description = new SimpleStringProperty(description);
        this.temperature = new SimpleStringProperty(temperature);
        this.weatherIcon = weatherIcon;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public SimpleStringProperty temperatureProperty() {
        return temperature;
    }

    public SimpleStringProperty pressureProperty() {
        return pressure;
    }

    public SimpleStringProperty windSpeedProperty() {
        return windSpeed;
    }

    public SimpleStringProperty humidityProperty() {
        return humidity;
    }

    public Image getWeatherIcon() {
        return weatherIcon;
    }
}
