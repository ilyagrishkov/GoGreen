package oop.gui;

import javafx.scene.control.Button;
import oop.scenes.SceneController;

public class FriendButton extends Button {


    /**
     * Constructor for FriendsButton.
     *
     * @param text the username of the person
     */
    public FriendButton(String text) {
        super(text);

        this.setOnAction(mouseEvent -> {
            SceneController.startProfile(text);
        });
    }
}
