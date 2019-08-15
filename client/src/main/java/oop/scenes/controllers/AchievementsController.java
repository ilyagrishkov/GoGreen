package oop.scenes.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import oop.App;
import oop.gui.AchievementContainer;
import oop.scenes.SceneController;


public class AchievementsController {

    @FXML
    Button profileBtn;

    @FXML
    Button homeBtn;

    @FXML
    JFXDrawer drawer;

    @FXML
    VBox container;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        SceneController.sideBarControl(profileBtn, drawer);

        homeBtn.setOnAction(e -> {

            SceneController.startHome("achievements", false);
        });

        for (int i = 1; i < App.achievementDetails.size(); i++) {

            container.getChildren().add(new AchievementContainer(App.achievementDetails.get(i)));

        }
    }
}
