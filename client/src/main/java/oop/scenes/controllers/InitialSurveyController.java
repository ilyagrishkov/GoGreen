package oop.scenes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oop.App;
import oop.connection.Connection;
import oop.details.FoodDetails;
import oop.details.SettingsDetails;
import oop.scenes.Properties;
import oop.scenes.SceneController;

import java.util.Collections;


public class InitialSurveyController {

    @FXML
    Button finishButton;

    @FXML
    CheckBox elecdefault;

    @FXML
    CheckBox emissionsdefault;

    @FXML
    CheckBox naturalgasdefault;

    @FXML
    CheckBox gasolinedefault;

    @FXML
    ComboBox<String> mealcomposition;

    @FXML
    Button addcar;

    @FXML
    TextField elecprice;

    @FXML
    TextField emissions;

    @FXML
    TextField naturalgasprice;

    @FXML
    TextField gasolineprice;

    @FXML
    TextField livingspace;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        initializeMealValues();

        elecdefault.setOnAction(e -> initializeElectricity());
        emissionsdefault.setOnAction(e -> initializeEmissons());
        naturalgasdefault.setOnAction(e -> initializeNaturalGas());
        gasolinedefault.setOnAction(e -> initializeGasoline());


        livingspace.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {
                livingspace.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        emissions.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*")) {
                emissions.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        addcar.setOnAction(e -> {

            SceneController.openDialog("addcar");
        });

        Connection connection = new Connection();

        finishButton.setOnAction(e -> {

            if (validateAll()) {
                SettingsDetails settingsDetails = new SettingsDetails(
                        Double.parseDouble(gasolineprice.getText()),
                        Double.parseDouble(elecprice.getText()),
                        Double.parseDouble(emissions.getText()),
                        Double.parseDouble(naturalgasprice.getText()),
                        Double.parseDouble(livingspace.getText())
                );
                connection.sendSettings(settingsDetails);


                if (mealcomposition.getValue().equals("Vegan")) {

                    FoodDetails foodDetails = new FoodDetails.Builder()
                            .setBeef(0)
                            .setFish(0)
                            .setOther(0)
                            .setPoultry(0)
                            .setGrains(45)
                            .setDairy(45)
                            .setFruits(10)
                            .setLocal(false)
                            .setHomecooked(false)
                            .setType(Properties.vegan)
                            .build();
                    connection.sendFood(foodDetails);

                } else if (mealcomposition.getValue().equals("Vegetarian")) {

                    FoodDetails foodDetails = new FoodDetails.Builder()
                            .setBeef(0)
                            .setFish(0)
                            .setOther(0)
                            .setPoultry(0)
                            .setGrains(40)
                            .setDairy(30)
                            .setFruits(10)
                            .setLocal(false)
                            .setHomecooked(false)
                            .setType(Properties.vegetarian)
                            .build();

                    connection.sendFood(foodDetails);

                } else if (mealcomposition.getValue().equals("Pescetarian")) {

                    FoodDetails foodDetails = new FoodDetails.Builder()
                            .setBeef(0)
                            .setFish(50)
                            .setOther(0)
                            .setPoultry(0)
                            .setGrains(20)
                            .setDairy(20)
                            .setFruits(5)
                            .setLocal(false)
                            .setHomecooked(false)
                            .setType(Properties.pescetarian)
                            .build();

                    connection.sendFood(foodDetails);

                } else {

                    FoodDetails foodDetails = new FoodDetails.Builder()
                            .setBeef(30)
                            .setFish(0)
                            .setOther(5)
                            .setPoultry(20)
                            .setGrains(10)
                            .setDairy(30)
                            .setFruits(5)
                            .setLocal(false)
                            .setHomecooked(false)
                            .setType(Properties.mediummeat)
                            .build();

                    connection.sendFood(foodDetails);

                }

                // User earns first achievement for creating account
                connection.setAchievementProgress(1, 1);

                App.achievementDetails.addAll(App.connection.receiveAchievements());
                Collections.sort(App.achievementDetails);

                App.achievementDetails.get(1).setProgress(1);

                oop.gui.Properties prop = new oop.gui.Properties();

                for (int i = 2; i <= prop.getAchievementsNumber(); i++) {

                    connection.setAchievementProgress(i, 0);
                    App.achievementDetails.get(i).setProgress(0);

                }
                App.achievementDetails.addAll(App.connection.receiveAchievements());
                App.badgeDetails.addAll(App.connection.receiveBadges());
                SceneController.startHome("initialsurvey", true);
                ((Stage) finishButton.getScene().getWindow()).close();
            }
        });
    }


    private void initializeEmissons() {

        if (emissionsdefault.isSelected()) {
            emissions.setText("590");
            emissions.setDisable(true);

        } else if (emissionsdefault.isIndeterminate()) {
            emissions.setText("");
            emissions.setDisable(false);

        } else {
            emissions.setText("");
            emissions.setDisable(false);
        }
    }

    private void initializeElectricity() {

        if (elecdefault.isSelected()) {
            elecprice.setText("10");
            elecprice.setDisable(true);

        } else if (elecdefault.isIndeterminate()) {
            elecprice.setText("");
            elecprice.setDisable(false);

        } else {
            elecprice.setText("");
            elecprice.setDisable(false);
        }
    }

    private void initializeNaturalGas() {

        if (naturalgasdefault.isSelected()) {
            naturalgasprice.setText("0.5");
            naturalgasprice.setDisable(true);

        } else if (naturalgasdefault.isIndeterminate()) {
            naturalgasprice.setText("");
            naturalgasprice.setDisable(false);

        } else {
            naturalgasprice.setText("");
            naturalgasprice.setDisable(false);
        }
    }

    private void initializeGasoline() {

        if (gasolinedefault.isSelected()) {
            gasolineprice.setText("1");
            gasolineprice.setDisable(true);

        } else if (gasolinedefault.isIndeterminate()) {
            gasolineprice.setText("");
            gasolineprice.setDisable(false);

        } else {
            gasolineprice.setText("");
            gasolineprice.setDisable(false);
        }
    }


    private void initializeMealValues() {
        ObservableList<String> meals = FXCollections.observableArrayList("Vegan",
                "Vegetarian", "Pescetarian", "Meat Based");
        mealcomposition.setItems(meals);
        mealcomposition.setValue("Meat Based");
    }

    /**
     * Sets red color to text field with wrong input
     * and removes color from field with correct input.
     *
     * @param textfield - TextField for changing color
     * @param check     - true if input was correct, false otherwise
     */
    private void setTextFieldColor(TextField textfield, boolean check) {

        if (check) {
            textfield.setStyle("-fx-border-color: #61b73a");
        } else {
            textfield.setStyle("-fx-border-color: red");
        }
    }

    private boolean validateAll() {

        boolean elecCheck;

        elecCheck = !elecprice.getText().equals("")
                && elecprice.getText().matches("^[0-9]\\d{0,9}(\\.\\d{1,3})?%?$");

        boolean emissionsCheck;

        emissionsCheck = !emissions.getText().equals("");

        boolean naturalGasCheck;

        naturalGasCheck = !naturalgasprice.getText().equals("")
                && naturalgasprice.getText().matches("^[0-9]\\d{0,9}(\\.\\d{1,3})?%?$");

        boolean gasolineCheck;

        gasolineCheck = !gasolineprice.getText().equals("")
                && gasolineprice.getText().matches("^[0-9]\\d{0,9}(\\.\\d{1,3})?%?$");

        boolean livingspaceCheck;

        livingspaceCheck = !livingspace.getText().equals("");

        setTextFieldColor(elecprice, elecCheck);
        setTextFieldColor(emissions, elecCheck);
        setTextFieldColor(naturalgasprice, naturalGasCheck);
        setTextFieldColor(gasolineprice, gasolineCheck);
        setTextFieldColor(livingspace, elecCheck);


        return elecCheck && emissionsCheck && naturalGasCheck && gasolineCheck
                && livingspaceCheck;
    }
}
