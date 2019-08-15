package oop.scenes.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Map;

public class MoneyDialogController {

    @FXML
    JFXButton close;

    @FXML
    JFXButton done;

    @FXML
    JFXTextField price;

    @FXML
    Label completed;
    Button controllButton;
    Map prices;
    private String type;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        close.setOnAction(e -> {

            ((Stage) close.getScene().getWindow()).close();
        });


        done.setOnAction(e -> {


        });

        price.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {
                price.setText(newValue.replaceAll("[^\\d]", ""));
                newValue = oldValue;
            }
        });

    }

    /**
     * Called after scene initialization.
     */
    public void start() {

        done.setOnAction(e -> {
            prices.put(type, Integer.parseInt(price.getText()));
            System.out.println(type + ": " + Integer.parseInt(price.getText()));

            ((Stage) close.getScene().getWindow()).close();
            controllButton.setDisable(true);

            int temp = Integer.parseInt(completed.getText());
            temp++;
            completed.setText(temp + "");

        });
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setControllButton(Button controllButton) {
        this.controllButton = controllButton;
    }

    public void setCompleted(Label completed) {
        this.completed = completed;
    }

    public void setPrices(Map prices) {
        this.prices = prices;
    }
}
