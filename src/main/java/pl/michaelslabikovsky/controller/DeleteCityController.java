package pl.michaelslabikovsky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pl.michaelslabikovsky.model.Location;
import pl.michaelslabikovsky.model.LocationsDBModel;
import pl.michaelslabikovsky.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteCityController extends BaseController implements Initializable {

    @FXML
    private TableView<Location> locationTable;

    @FXML
    private TableColumn<Location, String> nameCol;

    @FXML
    private Label messageLabel;

    private LocationsDBModel locationsDBModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public DeleteCityController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    public void deleteCityAction() {

    }

    @FXML
    public void cancelAction() {
        viewFactory.closeStage((Stage) messageLabel.getScene().getWindow());
    }
}
