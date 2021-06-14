package pl.michaelslabikovsky.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.michaelslabikovsky.Launcher;
import pl.michaelslabikovsky.WeatherManager;
import pl.michaelslabikovsky.controller.BaseController;
import pl.michaelslabikovsky.controller.MainWindowController;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private WeatherManager weatherManager;
    private ArrayList<Stage> activeStages;

    public ViewFactory(WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
        activeStages = new ArrayList<>();
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(weatherManager, this, "MainWindow.fxml");
        initializeStage(controller);
    }

    private void initializeStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(new Launcher().getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaxWidth(830);
        stage.setMaxHeight(703);
        stage.setResizable(false);
        stage.show();
        activeStages.add(stage);
    }
}
