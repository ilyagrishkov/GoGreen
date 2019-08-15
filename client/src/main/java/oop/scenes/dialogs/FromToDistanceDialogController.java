package oop.scenes.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import oop.App;
import oop.connection.Connection;
import oop.details.BadgeDetails;
import oop.details.CarDetails;
import oop.details.TripDetails;
import oop.gui.Properties;
import oop.scenes.SceneController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FromToDistanceDialogController {

    @FXML
    JFXButton close;

    @FXML
    JFXButton done;

    @FXML
    JFXTextField from;

    @FXML
    JFXTextField to;

    @FXML
    JFXTextField distance;

    @FXML
    JFXComboBox carpick;

    @FXML
    ImageView fail;

    @FXML
    ImageView success;

    Connection connection;
    Map<String, Integer> carmap;
    private String type;
    private Integer carpicked = null;
    private Properties properties;


    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        properties = new Properties();

        connection = new Connection();

        close.setOnAction(e -> ((Stage)close.getScene().getWindow()).close());

        distance.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {
                distance.setText(newValue.replaceAll("[^\\d]", ""));
                newValue = oldValue;
            }

            if (newValue.equals("")) {
                to.setDisable(false);

            } else {
                to.setDisable(true);
            }
        });


        to.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                distance.setDisable(false);
            } else {
                distance.setDisable(true);
            }
        });


        done.setOnAction(e -> doneBtnAction());

    }

    /**
     * Set type of vehicle.
     * @param type - type to assign
     */
    public void setType(String type) {
        this.type = type;

        if (type.equals("Car")) {

            distance.setPrefWidth(177);
            carpick.setVisible(true);
        }

        initializeCars();
    }

    /**
     * Get list of cars from server and add them to ComboBox.
     */
    public void initializeCars() {

        ArrayList<CarDetails> cars = connection.receiveCars(App.getUsername());

        if (!cars.isEmpty()) {

            ArrayList<String> carnames = new ArrayList<>();
            carmap = new HashMap<>();

            for (CarDetails car : cars) {

                carmap.put(car.getName(), car.getCarId());
                carnames.add(car.getName());
            }

            ObservableList<String> caritems = FXCollections.observableArrayList(carnames);

            carpick.setItems(caritems);
            carpick.setValue(carnames.get(0));

        } else {

            carpick.setDisable(true);
        }
    }

    /**
     * Deals with actions to be done on clicking the "Done" button.
     */
    private void doneBtnAction() {
        if (!from.getText().equals("") && (!to.getText().equals("")
                || !distance.getText().equals(""))) {

            fail.setVisible(false);
            success.setVisible(true);

            String from = this.from.getText().equals("") ? null : this.from.getText();
            String to = this.to.getText().equals("") ? this.from.getText() : this.to.getText();
            Double distance = this.distance.getText().equals("") ? null
                    : Double.parseDouble(this.distance.getText());

            carpicked = carmap.get(carpick.getValue());
            TripDetails tripDetails = new TripDetails(type, from, to, distance,
                    LocalDate.now().toString(), carpicked);

            int oldScore = connection.receiveScore(App.getUsername());
            boolean successfulConnection = connection.sendTrip(tripDetails);
            int newScore = connection.receiveScore(App.getUsername());

            try {
                if (type.equals("BikeWalk")) {
                    for (int i = properties.getAchwalkStart();
                         i <= properties.getAchwalkStop(); i++) {

                        App.achievementDetails.get(i).setProgress(
                                App.achievementDetails.get(i).getProgress()
                                + (int) Math.round(distance));
                    }

                    if (App.achievementDetails.get(properties.getAchwalkStop()).getProgress()
                        > App.achievementDetails.get(
                            properties.getAchwalkStop()).getGoal()) {
                        App.connection.sendBadge(new BadgeDetails(
                                properties.getBadgeWalk(),
                                App.badgeDetails.get(properties.getBadgeWalk()).getName(),
                                App.badgeDetails.get(properties.getBadgeWalk()).getDescription()
                        ));
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (!successfulConnection || oldScore == newScore) {

                success.setVisible(false);
                fail.setVisible(true);
            } else {

                SceneController.startHome("fromtodistancedialog", false);
                ((Stage) close.getScene().getWindow()).close();
            }

        } else {

            success.setVisible(false);
            fail.setVisible(true);


            System.out.println("TESTING" + connection.receiveTrips(App.getUsername()));
        }
    }
}
