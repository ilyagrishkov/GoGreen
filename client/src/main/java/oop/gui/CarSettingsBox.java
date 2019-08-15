package oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import oop.connection.Connection;
import oop.details.CarDetails;

public class CarSettingsBox extends StackPane {

    private CarDetails carDetails;

    /**
     * Constructor for the Box containing car details on SettingsPane.
     *
     * @param carDetails CarDetails to fill the box with
     */
    public CarSettingsBox(CarDetails carDetails, VBox parent) {

        super();

        this.setCarDetails(carDetails);

        Label carName = new Label(carDetails.getName());
        carName.setMinWidth(parent.getWidth() / 2.0);
        this.getChildren().add(carName);
        StackPane.setAlignment(carName, Pos.CENTER_LEFT);

        Properties prop = new Properties();

        TextField mpgField = new TextField(carDetails.getMpg() + "");
        this.getChildren().add(mpgField);
        StackPane.setAlignment(mpgField, Pos.CENTER);
        mpgField.setMaxWidth(prop.getSettingsTextFieldWidth() / 4.0);
        mpgField.setDisable(true);

        Button deleteButton = new Button("X");
        deleteButton.setStyle("-fx-background-color: RED");

        deleteButton.setOnAction(mouseEvent -> {

            Connection conn = new Connection();
            if (conn.deleteCar(carDetails.getCarId())) {
                parent.getChildren().remove(this);
            } else {
                this.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY, BorderWidths.FULL)));
            }
        });

        this.getChildren().add(deleteButton);
        StackPane.setAlignment(deleteButton, Pos.CENTER_RIGHT);

    }

    public CarDetails getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(CarDetails carDetails) {
        this.carDetails = carDetails;
    }
}
