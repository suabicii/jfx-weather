package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import pl.michaelslabikovsky.WeatherManager;
import pl.michaelslabikovsky.model.WeatherData;
import pl.michaelslabikovsky.utils.JSONConverter;
import pl.michaelslabikovsky.view.ViewFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private ChoiceBox<String> cityOneChoiceBox;

    @FXML
    private ChoiceBox<String> cityTwoChoiceBox;

    @FXML
    private ImageView currentWeatherCityOneImg;

    @FXML
    private ImageView currentWeatherCityTwoImg;

    @FXML
    private Label currentWeatherResultCityOne;

    @FXML
    private Label currentTemperatureCityOne;

    @FXML
    private Label currentPressureCityOne;

    @FXML
    private Label currentWindSpeedCityOne;

    @FXML
    private Label currentWeatherResultCityTwo;

    @FXML
    private Label currentTemperatureCityTwo;

    @FXML
    private Label currentHumidityCityOne;

    @FXML
    private Label currentPressureCityTwo;

    @FXML
    private Label currentWindSpeedCityTwo;

    @FXML
    private Label currentHumidityCityTwo;

    private WeatherData weatherData;

    public MainWindowController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cityOneChoiceBox.getItems().addAll("Warszawa", "Dodaj miejscowość...");
        cityTwoChoiceBox.getItems().addAll("Londyn", "Dodaj miejscowość...");
        cityOneChoiceBox.getSelectionModel().selectFirst();
        cityTwoChoiceBox.getSelectionModel().selectFirst();

        cityOneChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> addCity(cityOneChoiceBox, oldValue));

        cityTwoChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> addCity(cityTwoChoiceBox, oldValue));

        showWeatherData();
    }

    public void showWeatherData() {
        try {
            getCurrentWeather("Warszawa", currentWeatherResultCityOne, currentTemperatureCityOne, currentPressureCityOne, currentWindSpeedCityOne, currentHumidityCityOne, currentWeatherCityOneImg);
            getCurrentWeather("Londyn", currentWeatherResultCityTwo, currentTemperatureCityTwo, currentPressureCityTwo, currentWindSpeedCityTwo, currentHumidityCityTwo, currentWeatherCityTwoImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCurrentWeather(String cityName, Label weatherLabel, Label temperatureLabel, Label pressureLabel, Label windSpeedLabel, Label humidityLabel, ImageView weatherIcon) throws IOException {
        weatherData = new WeatherData(cityName);
        String weatherDataResult = weatherData.getResult();
        JSONArray jsonArray = JSONConverter.convertStringObjectToJSONArray(weatherDataResult);
        weatherLabel.setText(jsonArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"));
        temperatureLabel.setText(String.valueOf(jsonArray.getJSONObject(1).getJSONObject("main").getInt("temp")) + "°C");
        pressureLabel.setText(String.valueOf(jsonArray.getJSONObject(1).getJSONObject("main").getInt("pressure")) + " hPa");
        windSpeedLabel.setText(String.valueOf(jsonArray.getJSONObject(1).getJSONObject("wind").getDouble("speed")) + " m/s");
        humidityLabel.setText(String.valueOf(jsonArray.getJSONObject(1).getJSONObject("main").getInt("humidity")) + "%");
        weatherIcon.setImage(setImageUrl(getIconUrl(jsonArray)));
    }

    private String getIconUrl(JSONArray jsonArray) {
        String weatherIconId = jsonArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
        return "https://openweathermap.org/img/wn/" + weatherIconId + "@2x.png";
    }

    private void addCity(ChoiceBox<String> cityChoiceBox, Number oldValue) {
        if (cityChoiceBox.getSelectionModel().getSelectedIndex() == cityChoiceBox.getItems().size() - 1) {
            cityChoiceBox.getSelectionModel().select(oldValue.intValue());
        }
    }

    private Image setImageUrl(String url) {
        return new Image(url);
    }
}
