package oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import oop.details.AchievementDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class AchievementContainer extends HBox {

    private int progress;
    private int goal;

    private String name;
    private String description;

    /**
     * Constructor for the AchievementContainer class.
     *
     * @param achievementDetails Details of the achievement
     */
    public AchievementContainer(AchievementDetails achievementDetails) {

        super();

        Properties prop = new Properties();

        this.setName(achievementDetails.getName());
        this.setDescription(achievementDetails.getDescription());
        this.setProgress(achievementDetails.getProgress());
        this.setGoal(achievementDetails.getGoal());

        this.setMinHeight(prop.getAchievementContainerHeight());

        StackPane imagePane = new StackPane();
        imagePane.setMinSize(prop.getAchievementContainerHeight(),
                prop.getAchievementContainerHeight());

        // ImageView to hold achievement picture
        ImageView imageView = new ImageView();
        imagePane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);

        imageView.setFitWidth(prop.getAchievementImageSize());
        imageView.setFitHeight(prop.getAchievementImageSize());

        try {
            FileInputStream input = new FileInputStream("src/resources/greenStar.png");
            Image image = new Image(input);
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

        this.getChildren().add(imagePane);

        StackPane descriptionPane = new StackPane();
        descriptionPane.setMinSize(prop.getWindowWidth() - prop.getAchievementContainerHeight(),
                prop.getAchievementContainerHeight());

        // Name, description and progressbar container
        VBox detailsVbox = new VBox();
        detailsVbox.setMaxHeight(prop.getAchievementDetailsHeight());

        // Name label
        Label nameLabel = new Label(this.getName());

        detailsVbox.getChildren().add(nameLabel);

        // Description label
        Label descriptionLabel = new Label(this.getDescription());

        detailsVbox.getChildren().add(descriptionLabel);

        // Progress bar

        Pane progressPane = new Pane();

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress((double) getProgress() / getGoal());
        progressBar.setDisable(true);

        progressPane.getChildren().add(progressBar);

        Label progressLabel = new Label(getProgress() + "/" + getGoal());

        progressPane.getChildren().add(progressLabel);

        detailsVbox.getChildren().add(progressPane);

        descriptionPane.getChildren().add(detailsVbox);
        StackPane.setAlignment(detailsVbox, Pos.BOTTOM_CENTER);

        this.getChildren().add(descriptionPane);

    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
