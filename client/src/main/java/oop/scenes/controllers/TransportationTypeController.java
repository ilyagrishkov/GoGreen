package oop.scenes.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import oop.scenes.SceneController;

public class TransportationTypeController {

    @FXML
    Button homeBtn;

    @FXML
    Button profileBtn;

    @FXML
    Button walking;

    @FXML
    Button cycling;

    @FXML
    Button publictransport;

    @FXML
    Button driving;

    @FXML
    Button flight;

    @FXML
    JFXDrawer drawer;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        SceneController.sideBarControl(profileBtn, drawer);

        homeBtn.setOnAction(e -> {

            SceneController.startHome("transportationtype", false);
        });

        walking.setOnAction(e -> {
            SceneController.openDialog("BikeWalk");
        });

        cycling.setOnAction(e -> {
            SceneController.openDialog("BikeWalk");
        });

        publictransport.setOnAction(e -> {
            SceneController.openDialog("PubTrans");
        });

        driving.setOnAction(e -> {
            SceneController.openDialog("Car");
        });

        flight.setOnAction(e -> {
            SceneController.openDialog("Plane");
        });
    }

}
