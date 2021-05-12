package pl.michaelslabikovsky.controller;

import pl.michaelslabikovsky.WeatherManager;
import pl.michaelslabikovsky.view.ViewFactory;

public class MainWindowController extends BaseController {

    public MainWindowController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
    }
}
