package oop.scenes.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Map;

public class GreenEnergyDialogController {

    @FXML
    JFXButton close;

    @FXML
    JFXButton done;

    @FXML
    JFXToggleButton solarpanel;

    @FXML
    JFXSlider tempslider;

    @FXML
    JFXSlider greenenergyslider;

    @FXML
    Label lblgreenenergy;

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

        lblgreenenergy.setDisable(true);
        greenenergyslider.setDisable(true);
        greenenergyslider.setValue(0.0);

        close.setOnAction(e -> {

            ((Stage) close.getScene().getWindow()).close();
        });


        solarpanel.setOnAction(e -> {

            if (solarpanel.isSelected()) {
                prices.put("solarpanel", true);
                lblgreenenergy.setDisable(false);
                greenenergyslider.setDisable(false);
            } else {
                prices.put("solarpanel", false);
                lblgreenenergy.setDisable(true);
                greenenergyslider.setDisable(true);
                greenenergyslider.setValue(0.0);
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

    public void setPrices(Map prices) {
        this.prices = prices;
    }

    /**
     * Called after scene initialization.
     */
    public void start() {

        done.setOnAction(e -> {
            prices.put("tempslider", (int) tempslider.getValue());
            prices.put("greenenergy", (int) greenenergyslider.getValue());

            ((Stage) close.getScene().getWindow()).close();
            controllButton.setDisable(true);

            int temp = Integer.parseInt(completed.getText());
            temp++;
            completed.setText(temp + "");
        });
    }
}
