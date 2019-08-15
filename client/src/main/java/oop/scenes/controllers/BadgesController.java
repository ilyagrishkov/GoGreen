package oop.scenes.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import oop.App;
import oop.gui.BadgeContainer;
import oop.gui.Properties;
import oop.scenes.SceneController;

import java.util.ArrayList;
import java.util.Collections;

public class BadgesController {

    @FXML
    Button profileBtn;

    @FXML
    Button homeBtn;

    @FXML
    JFXDrawer drawer;

    @FXML
    TilePane container;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        homeBtn.setOnAction(e -> SceneController.startHome("badges", false));

        SceneController.sideBarControl(profileBtn, drawer);

        ArrayList<Integer> ownedBadges = App.connection.receiveUserBadges();
        if (ownedBadges.size() != 0) {
            Collections.sort(ownedBadges);
        }

        ownedBadges.add(-1);

        int ownindex = 0;
        boolean owned = false;

        Properties properties = new Properties();

        try {
            for (int i = 1; i <= properties.getBadgesNumber(); i++) {

                if (i == ownedBadges.get(ownindex)) {

                    owned = true;
                    ownindex++;
                }
                container.getChildren().add(new BadgeContainer(App.badgeDetails.get(i), owned));
                owned = false;

            }
        } catch (Exception e) {

            System.out.println("Badges exception");
        }

    }
}
