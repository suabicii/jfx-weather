package pl.michaelslabikovsky.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.michaelslabikovsky.Launcher;
import pl.michaelslabikovsky.controller.BaseController;
import pl.michaelslabikovsky.controller.CityChoiceController;
import pl.michaelslabikovsky.controller.MainWindowController;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private ArrayList<Stage> activeStages;

    public ViewFactory() {
        activeStages = new ArrayList<>();
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(this, "MainWindow.fxml");
        initializeStage(controller);
    }

    public void showCityChoiceWindow() {
        BaseController controller = new CityChoiceController(this, "CityChoiceWindow.fxml");
        initializeStage(controller);
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    private void initializeStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource(controller.getFxmlName()));
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
        stage.setResizable(false);
        stage.setTitle("JFX Weather by Michael Slabikovsky");
        stage.show();
        activeStages.add(stage);
    }
}
