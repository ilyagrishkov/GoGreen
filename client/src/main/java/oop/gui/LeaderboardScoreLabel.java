package oop.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class LeaderboardScoreLabel extends BorderPane {


    /**
     * Constructor for LeaderboardScoreLabel.
     *
     * @param position The place the user is in
     * @param name     The username of the user
     * @param score    The score of the user
     */
    public LeaderboardScoreLabel(int position, String name, int score) {

        super();

        Properties prop = new Properties();

        this.setMaxHeight(prop.getScoreLabelHeight());

        Label positionLabel = new Label(position + "");
        this.setLeft(positionLabel);

        Label nameLabel = new Label(name);
        this.setCenter(nameLabel);

        Label scoreLabel = new Label(score + "");
        this.setRight(scoreLabel);

    }

}
