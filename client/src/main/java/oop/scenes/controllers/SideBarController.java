package oop.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import oop.App;
import oop.scenes.SceneController;

public class SideBarController {

    @FXML
    Button profileButton;

    @FXML
    Button achievementsButton;

    @FXML
    Button badgesButton;

    @FXML
    Button leaderboardButton;

    @FXML
    Button settingsButton;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        profileButton.setOnAction(e -> {

            SceneController.startProfile(App.getUsername());
        });

        achievementsButton.setOnAction(e -> {

            SceneController.startAchievements();
        });

        badgesButton.setOnAction(e -> {

            SceneController.startBadges();
        });

        leaderboardButton.setOnAction(e -> {

            SceneController.startLeaderboard();
        });

        settingsButton.setOnAction(e -> {

            SceneController.startSettings();
        });
    }
}
