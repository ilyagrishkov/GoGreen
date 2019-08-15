package oop.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ChangeLogLabelButton extends HBox {

    private Label details;

    private Properties prop = new Properties();

    /**
     * Constructor for ChangeLogLabelButton.
     *
     * @param text for label
     */
    public ChangeLogLabelButton(String text) {
        super();

        this.details = new Label(text);

        this.details.setMinWidth(prop.getChangelogEntryWidth());

        this.getChildren().add(this.details);
    }

}
