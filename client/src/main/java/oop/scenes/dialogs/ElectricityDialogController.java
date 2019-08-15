package oop.scenes.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ElectricityDialogController {

    @FXML
    JFXButton close;

    @FXML
    JFXButton done;

    @FXML
    JFXTextField price;

    @FXML
    JFXToggleButton solarpanel;

    @FXML
    JFXSlider tempslider;

    @FXML
    Label completed;
    Button controllButton;
    private String type;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        close.setOnAction(e -> {

            ((Stage) close.getScene().getWindow()).close();
        });


        price.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {
                price.setText(newValue.replaceAll("[^\\d]", ""));
                newValue = oldValue;
            }
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

    /**
     * Called after scene initialization.
     */
    public void start() {

        done.setOnAction(e -> {
            ((Stage) close.getScene().getWindow()).close();
            controllButton.setDisable(true);

            int temp = Integer.parseInt(completed.getText());
            temp++;
            completed.setText(temp + "");
        });
    }
}
