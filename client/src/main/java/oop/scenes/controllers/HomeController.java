package oop.scenes.controllers;

import static java.lang.Integer.parseInt;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import oop.App;
import oop.gui.Properties;
import oop.scenes.SceneController;

public class HomeController {
    @FXML
    Label score;

    @FXML
    Button profileBtn;

    @FXML
    Button foodButton;

    @FXML
    Button transportationButton;

    @FXML
    Button utilitiesButton;

    @FXML
    JFXDrawer drawer;

    private Properties properties;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {
        //System.out.println(App.getUsername());

        properties = new Properties();

        SceneController.sideBarControl(profileBtn, drawer);

        foodButton.setOnAction(e -> {

            SceneController.startFoodType("home", false);
        });

        transportationButton.setOnAction(e -> {

            SceneController.startTransportationType("home", false);
        });

        utilitiesButton.setOnAction(e -> {

            SceneController.startUtilitiesType("home", false);
        });

        score.setText(String.valueOf(App.connection.receiveScore(App.getUsername())));


    }

    /**
     * Method that updates a user's score after the page has loaded.
     */
    public void start() {

        for (int i = properties.getAchscoreStart(); i <= properties.getAchscoreStop(); i++) {

            App.achievementDetails.get(i).setProgress(parseInt(score.getText()));
        }
    }
}
