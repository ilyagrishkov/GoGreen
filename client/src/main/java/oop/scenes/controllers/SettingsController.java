package oop.scenes.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import oop.App;
import oop.connection.Connection;
import oop.details.CarDetails;
import oop.details.SettingsDetails;
import oop.gui.CarSettingsBox;
import oop.scenes.SceneController;

import java.util.ArrayList;


public class SettingsController {

    @FXML
    Button profileBtn;

    @FXML
    Button homeBtn;

    @FXML
    JFXDrawer drawer;

    @FXML
    TextField elField;

    @FXML
    TextField elEmm;

    @FXML
    TextField natgasField;

    @FXML
    TextField gasField;

    @FXML
    TextField livingspaceField;

    @FXML
    Button saveBtn;

    @FXML
    Button addcar;

    @FXML
    VBox carBox;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        SceneController.sideBarControl(profileBtn, drawer);

        homeBtn.setOnAction(e -> {

            SceneController.startHome("settings", false);
        });

        Connection conn = new Connection();
        SettingsDetails settingsDetails = conn.receiveSettings(App.getUsername());

        elField.setText(settingsDetails.getElectricityPrice() + "");
        elEmm.setText(settingsDetails.getElectricityemmfactor() + "");
        natgasField.setText(settingsDetails.getNatgasPrice() + "");
        gasField.setText(settingsDetails.getGasPrice() + "");
        livingspaceField.setText(settingsDetails.getLivingSpace() + "");

        saveBtn.setOnAction(mouseEvent -> {

            try {

                double gasPrice = Double.parseDouble(gasField.getText());
                double electricityPrice = Double.parseDouble(elField.getText());
                double electricityemmFactor = Double.parseDouble(elEmm.getText());
                double natgasPrice = Double.parseDouble(natgasField.getText());
                double livingSpace = Double.parseDouble(livingspaceField.getText());

                SettingsDetails settingsDetails1 = new SettingsDetails(gasPrice, electricityPrice,
                        electricityemmFactor, natgasPrice, livingSpace);

                System.out.println(settingsDetails1.stringify());

                conn.sendSettings(settingsDetails1);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        ArrayList<CarDetails> carDetails = conn.receiveCars(App.getUsername());

        for (CarDetails c : carDetails) {

            carBox.getChildren().add(new CarSettingsBox(c, carBox));

        }

        addcar.setOnAction(e -> {

            SceneController.openDialog("addcar");
        });
    }

}
