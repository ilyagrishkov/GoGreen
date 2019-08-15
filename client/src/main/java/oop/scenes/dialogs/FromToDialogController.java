package oop.scenes.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import oop.App;
import oop.connection.Connection;
import oop.details.TripDetails;
import oop.scenes.SceneController;

import java.time.LocalDate;

public class FromToDialogController {

    @FXML
    JFXButton close;

    @FXML
    JFXButton done;

    @FXML
    JFXTextField from;

    @FXML
    JFXTextField to;

    @FXML
    ImageView fail;

    @FXML
    ImageView success;

    Connection connection;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        connection = new Connection();

        close.setOnAction(e -> {

            ((Stage) close.getScene().getWindow()).close();
        });


        done.setOnAction(e -> {

            if (!from.getText().equals("") && !to.getText().equals("")) {

                fail.setVisible(false);
                success.setVisible(true);

                TripDetails tripDetails = new TripDetails("Plane", from.getText(),
                        to.getText(), null, LocalDate.now().toString(), null);

                int oldScore = connection.receiveScore(App.getUsername());
                boolean successfulConnection = connection.sendTrip(tripDetails);
                int newScore = connection.receiveScore(App.getUsername());

                if (!successfulConnection || oldScore == newScore) {

                    success.setVisible(false);
                    fail.setVisible(true);
                } else {

                    SceneController.startHome("fromtodialog", false);
                    ((Stage) close.getScene().getWindow()).close();
                }

            } else {

                success.setVisible(false);
                fail.setVisible(true);
            }


        });

    }
}
