package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import pl.michaelslabikovsky.controller.currentweather.CurrentWeatherCityOneController;
import pl.michaelslabikovsky.controller.currentweather.CurrentWeatherCityTwoController;
import pl.michaelslabikovsky.controller.forecast.ForecastCityOneController;
import pl.michaelslabikovsky.controller.forecast.ForecastCityTwoController;
import pl.michaelslabikovsky.model.LocationsDB;
import pl.michaelslabikovsky.view.ViewFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private ChoiceBox<String> cityOneChoiceBox;

    @FXML
    private ChoiceBox<String> cityTwoChoiceBox;

    @FXML
    private CurrentWeatherCityOneController currentWeatherCityOneController;

    @FXML
    private CurrentWeatherCityTwoController currentWeatherCityTwoController;

    @FXML
    private ForecastCityOneController forecastCityOneController;

    @FXML
    private ForecastCityTwoController forecastCityTwoController;

    private LocationsDB locationsDB;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locationsDB = new LocationsDB();

        try {
            cityOneChoiceBox.getItems().addAll(locationsDB.selectAllFromDB());
            cityTwoChoiceBox.getItems().addAll(locationsDB.selectAllFromDB());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        cityOneChoiceBox.getItems().add("Dodaj miejscowość...");
        cityTwoChoiceBox.getItems().add("Dodaj miejscowość...");

        cityOneChoiceBox.getSelectionModel().selectFirst();
        cityTwoChoiceBox.getSelectionModel().selectFirst();

        cityOneChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> addCity(cityOneChoiceBox, oldValue));
        cityTwoChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> addCity(cityTwoChoiceBox, oldValue));

        currentWeatherCityOneController.showWeatherData(getCityName(cityOneChoiceBox));
        currentWeatherCityTwoController.showWeatherData(getCityName(cityTwoChoiceBox));

        forecastCityOneController.showWeatherData(getCityName(cityOneChoiceBox));
        forecastCityTwoController.showWeatherData(getCityName(cityTwoChoiceBox));
    }

    private String getCityName(ChoiceBox<String> choiceBox) {
        return choiceBox.getSelectionModel().getSelectedItem();
    }

    private void addCity(ChoiceBox<String> cityChoiceBox, Number oldValue) {
        if (cityChoiceBox.getSelectionModel().getSelectedIndex() == cityChoiceBox.getItems().size() - 1) {
            cityChoiceBox.getSelectionModel().select(oldValue.intValue());
            viewFactory.showCityChoiceWindow();
        }
    }
}
