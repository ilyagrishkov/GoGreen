package oop.scenes.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import oop.connection.Connection;
import oop.details.UtilitiesDetails;
import oop.gui.Properties;
import oop.scenes.SceneController;

import java.util.HashMap;
import java.util.Map;

public class UtilitiesTypeController {

    @FXML
    Button homeBtn;

    @FXML
    Button profileBtn;

    @FXML
    JFXDrawer drawer;

    @FXML
    Button electricity;

    @FXML
    Button greenenergy;

    @FXML
    Button naturalgas;

    @FXML
    Button water;

    @FXML
    Button sendBtn;

    @FXML
    Label completed;

    private Properties prop;

    private Map prices = new HashMap();

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        prop = new Properties();

        completed.setText(0 + "");

        SceneController.sideBarControl(profileBtn, drawer);

        homeBtn.setOnAction(e -> {

            SceneController.startHome("transportationtype", false);
        });

        electricity.setOnAction(e -> {

            SceneController.openUtilitiesDialog("electricity", electricity, completed, prices);
        });

        greenenergy.setOnAction(e -> {

            SceneController.openUtilitiesDialog("greenenergy", greenenergy, completed, prices);
        });

        naturalgas.setOnAction(e -> {

            SceneController.openUtilitiesDialog("naturalgas", naturalgas, completed, prices);
        });

        water.setOnAction(e -> {

            SceneController.openUtilitiesDialog("water", water, completed, prices);
        });

        sendBtn.setOnAction(e -> {
            int natgas = (int) prices.get("naturalgas");
            int water = (int) prices.get("water");
            int elec = (int) prices.get("electricity");
            int greenenergy = (int) prices.get("greenenergy");
            int temp = (int) prices.get("tempslider");

            prices.putIfAbsent("solarpanel", false);

            boolean solarpanel = (boolean) prices.get("solarpanel");

            System.out.println(natgas + " " + water + " "
                    + elec + " " + greenenergy + " "
                    + temp + " " + solarpanel);

            UtilitiesDetails util = new UtilitiesDetails(natgas, water, elec,
                    greenenergy, temp, solarpanel);

            Connection connection = new Connection();
            connection.sendUtilities(util);

            SceneController.startHome("transportationtype", false);
        });

        sendBtn.setDisable(true);


        completed.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.equals("4")) {
                sendBtn.setDisable(false);
                System.out.println("4 reached");
            }
        });
    }

}
