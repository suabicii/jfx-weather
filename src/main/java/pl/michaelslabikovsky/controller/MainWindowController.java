package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import pl.michaelslabikovsky.Launcher;
import pl.michaelslabikovsky.WeatherManager;
import pl.michaelslabikovsky.model.WeatherData;
import pl.michaelslabikovsky.utils.JSONConverter;
import pl.michaelslabikovsky.view.ViewFactory;

import java.io.IOException;
import java.net.MalformedURLException;
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
    private Label currentWeatherResultCityTwo;

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

        currentWeatherCityOneImg.setImage(setImageUrl("icons/sun.png"));
        currentWeatherCityTwoImg.setImage(setImageUrl("icons/lightly_cloudy.png"));

        showWeatherData();
    }

    public void showWeatherData() {
        try {
            weatherData = new WeatherData("Warszawa");
            System.out.println(weatherData.getResult());
            /*JSONArray jsonArray = JSONConverter.convertStringObjectToJSONArray(weatherData.getResult());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                System.out.println("Dane pogodowe nr " + i +  ":");
                System.out.println("\nlist: " + jsonObject);
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCity(ChoiceBox<String> cityChoiceBox, Number oldValue) {
        if (cityChoiceBox.getSelectionModel().getSelectedIndex() == cityChoiceBox.getItems().size() - 1) {
            cityChoiceBox.getSelectionModel().select(oldValue.intValue());
        }
    }

    private Image setImageUrl(String url) {
        return new Image(String.valueOf(Launcher.class.getResource(url)));
    }
}
