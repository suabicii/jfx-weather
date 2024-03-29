package pl.michaelslabikovsky.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.michaelslabikovsky.model.LocationsDBModel;
import pl.michaelslabikovsky.model.SavedLocation;
import pl.michaelslabikovsky.view.ViewFactory;

import java.net.URL;
import java.util.*;

public class DeleteCityController extends BaseController implements Initializable {

    @FXML
    private TableView<SavedLocation> locationTable;

    @FXML
    private TableColumn<SavedLocation, String> nameCol;

    @FXML
    private Label messageLabel;

    private LocationsDBModel locationsDBModel;
    List<String> locationsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locationsDBModel = new LocationsDBModel();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationTable.getColumns().add(nameCol);
        locationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateLocationTable();
    }

    public DeleteCityController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    public void deleteCityAction() {
        locationsDBModel = new LocationsDBModel();
        String selectedCity = locationTable.getSelectionModel().selectedItemProperty().getValue().getName();

        messageLabel.setStyle(null); //czyszczenie stylów (usunięcie uprzednio dodanej klasy, jeśli istnieje)
        if (locationsDBModel.deleteFromTable(selectedCity)) {
            messageLabel.getStyleClass().add("success");
            messageLabel.setText("Usunięto miejscowość");
        } else {
            messageLabel.getStyleClass().add("success");
            messageLabel.setText("Nie udało się usunąć miejscowości");
        }

        updateLocationTable();
        clearMessageLabel();
    }

    @FXML
    public void cancelAction() {
        viewFactory.closeStage((Stage) messageLabel.getScene().getWindow());
    }

    private void updateLocationTable() {
        locationTable.getItems().clear();
        locationsList = new ArrayList<>(locationsDBModel.selectAllFromDB());

        locationsList.stream().map(SavedLocation::new).forEach(savedLocation -> locationTable.getItems().add(savedLocation));
    }

    private void clearMessageLabel() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    messageLabel.setText("");
                    timer.cancel();
                });
            }
        };
        timer.schedule(task, 3000);
    }
}
