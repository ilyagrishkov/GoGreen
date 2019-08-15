package oop.scenes.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import oop.App;
import oop.connection.Connection;
import oop.details.LeaderboardDetails;
import oop.gui.LeaderboardScoreLabel;
import oop.scenes.SceneController;

import java.util.ArrayList;

public class LeaderboardController {

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

            SceneController.startHome("leaderboard", false);
        });

        Connection conn = new Connection();
        ArrayList<LeaderboardDetails> leaderboardDetails =
                conn.receiveFriendLeaderboard(App.getUsername());
        int userScore = conn.receiveScore(App.getUsername());
        System.out.println(userScore + " " + App.getUsername());

        int index = 1;

        for (LeaderboardDetails ld : leaderboardDetails) {

            if (ld.getScore() > userScore) {
                container.getChildren().add(new LeaderboardScoreLabel(index, ld.getName(),
                        ld.getScore()));
                index++;
            } else {
                container.getChildren().add(new LeaderboardScoreLabel(index, App.getUsername(),
                        userScore));
                index++;
                container.getChildren().add(new LeaderboardScoreLabel(index, ld.getName(),
                        ld.getScore()));
                index++;
                userScore = Integer.MIN_VALUE;
            }
        }

        if (userScore != Integer.MIN_VALUE) {
            container.getChildren().add(new LeaderboardScoreLabel(index, App.getUsername(),
                    userScore));
        }


    }
}
