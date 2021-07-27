package pl.michaelslabikovsky.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.michaelslabikovsky.Launcher;
import pl.michaelslabikovsky.controller.BaseController;
import pl.michaelslabikovsky.controller.AddCityController;
import pl.michaelslabikovsky.controller.DeleteCityController;
import pl.michaelslabikovsky.controller.MainWindowController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ViewFactory {

    private ArrayList<Stage> activeStages;

    public ViewFactory() {
        activeStages = new ArrayList<>();
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(this, "MainWindow.fxml");
        initializeStage(controller);
    }

    public void showAddCityWindow() {
        BaseController controller = new AddCityController(this, "AddCityWindow.fxml");
        initializeStage(controller);
    }

    public void showDeleteCityWindow() {
        BaseController controller = new DeleteCityController(this, "DeleteCityWindow.fxml");
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
        scene.getStylesheets().add(Objects.requireNonNull(Launcher.class.getResource("css/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("JFX Weather by Michael Slabikovsky");
        stage.show();
        activeStages.add(stage);
    }
}
