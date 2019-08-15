package oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import oop.details.BadgeDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BadgeContainer extends StackPane {

    /**
     * Constructor for BadgeContainer.
     * @param badgeDetails Details of this badge
     * @param owned Boolean that indicates whether the user earned this badge
     */
    public BadgeContainer(BadgeDetails badgeDetails, boolean owned) {

        super();

        Properties prop = new Properties();

        this.setMinSize(prop.getBadgeContainerWidth(), prop.getBadgeContainerHeight());

        StackPane topPane = new StackPane();
        topPane.setMaxSize(prop.getBadgeImageSize(),
                prop.getBadgeImageSize() + prop.getBadgeContainerHeight() / 6);

        this.getChildren().add(topPane);
        StackPane.setAlignment(topPane, Pos.TOP_CENTER);

        // ImageView for badge image
        ImageView imageView = new ImageView();
        imageView.setFitHeight(prop.getBadgeImageSize());
        imageView.setFitWidth(prop.getBadgeImageSize());

        StackPane imagePane = new StackPane();
        imagePane.setMinSize(prop.getBadgeImageSize(), prop.getBadgeImageSize());
        imagePane.setMaxSize(prop.getBadgeImageSize(), prop.getBadgeImageSize());

        if (owned) {
            imagePane.setBackground(new Background(
                    new BackgroundFill(Color.web("61b73a"), null, null)));
        }

        imagePane.getChildren().add(imageView);

        try {
            FileInputStream input = new FileInputStream("src/resources/greenStar.png");
            Image image = new Image(input);
            imageView.setImage(image);

        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        }

        topPane.getChildren().add(imagePane);
        StackPane.setAlignment(imagePane, Pos.CENTER);

        StackPane bottomPane = new StackPane();
        bottomPane.setMaxSize(prop.getBadgeContainerWidth(),
                prop.getBadgeContainerHeight() - prop.getBadgeImageSize() * 2);

        Label nameLabel = new Label(badgeDetails.getName());

        bottomPane.getChildren().add(nameLabel);
        StackPane.setAlignment(nameLabel, Pos.TOP_CENTER);

        Label descriptionLabel = new Label(badgeDetails.getDescription());

        bottomPane.getChildren().add(descriptionLabel);
        StackPane.setAlignment(descriptionLabel, Pos.BOTTOM_CENTER);

        this.getChildren().add(bottomPane);
        StackPane.setAlignment(bottomPane, Pos.BOTTOM_CENTER);


    }
}
