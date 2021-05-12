package pl.michaelslabikovsky;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.michaelslabikovsky.view.ViewFactory;

import java.io.IOException;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory viewFactory = new ViewFactory(new WeatherManager());
        viewFactory.showMainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }

}