package pl.michaelslabikovsky.controller;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteCityController extends BaseController implements Initializable {

    @FXML
    private TableView<SavedLocation> locationTable;

    @FXML
    private TableColumn<SavedLocation, String> nameCol;

    @FXML
    private Label messageLabel;

    private LocationsDBModel locationsDBModel;
    private SavedLocation savedLocation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locationsDBModel = new LocationsDBModel();
        List<String> locationsList = null;

        try {
            locationsList = new ArrayList<>(locationsDBModel.selectAllFromDB());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        nameCol.setCellValueFactory(new PropertyValueFactory<SavedLocation, String>("name"));
        locationTable.getColumns().add(nameCol);
        locationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (int i = 0; i < locationsList.size(); i++) {
            savedLocation = new SavedLocation(locationsList.get(i));
            locationTable.getItems().add(savedLocation);
        }
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
