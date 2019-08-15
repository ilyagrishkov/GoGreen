package oop.scenes.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oop.connection.Connection;
import oop.details.CarDetails;
import oop.scenes.SceneController;


public class AddCarDialogController {

    @FXML
    JFXButton close;

    @FXML
    JFXButton doneBtn;

    @FXML
    TextField carName;

    @FXML
    TextField carConsumption;

    @FXML
    JFXComboBox<String> carChoice;

    /**
     * Check if a string is number.
     *
     * @param strNum String to check
     * @return True if string is number, false otherwise
     */
    public static boolean isNumeric(String strNum) {
        try {
            double number = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        ObservableList<String> carTypes = FXCollections.observableArrayList("Gas Car",
                "Diesel Car", "Electric Car");

        carChoice.setItems(carTypes);
        carChoice.setValue("Gas Car");

        carChoice.setOnAction(choiceEvent -> {
            if (carChoice.getValue().equals("Electric Car")) {
                carConsumption.setDisable(true);
            } else {
                carConsumption.setDisable(false);
            }
        });


        close.setOnAction(e -> {

            ((Stage) close.getScene().getWindow()).close();
        });

        AddCarDialogController thisDialog = this;

        doneBtn.setOnAction(e -> {

            carChoice.setStyle("");
            carConsumption.setStyle("");

            if (carChoice.getValue() == null) {

                carChoice.setStyle("-fx-border-color: RED");
            } else if (!carChoice.getValue().equals("Electric Car")
                    && !isNumeric(carConsumption.getText())) {

                carConsumption.setStyle("-fx-border-color: RED");
            } else {

                CarDetails cd;
                if (carChoice.getValue().equals("Electric Car")) {

                    cd = new CarDetails(carChoice.getValue(), 0, carName.getText());
                } else {
                    System.out.println(carChoice.getValue());
                    cd = new CarDetails(carChoice.getValue(),
                            Double.parseDouble(carConsumption.getText()), carName.getText());
                }
                Connection conn = new Connection();
                conn.sendCar(cd);
                ((Stage) close.getScene().getWindow()).close();
                SceneController.startSettings();
            }
        });
    }
}
