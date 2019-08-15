package oop.scenes.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import oop.details.FoodDetails;
import oop.scenes.Properties;
import oop.scenes.SceneController;

public class FoodTypeController {

    @FXML
    Button profileBtn;

    @FXML
    Button homeBtn;

    @FXML
    JFXDrawer drawer;

    @FXML
    Button vegan;

    @FXML
    Button vegetarian;

    @FXML
    Button pascetarian;

    @FXML
    Button lowmeat;

    @FXML
    Button mediummeat;

    @FXML
    Button highmeat;

    FoodDetails foodDetails;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        SceneController.sideBarControl(profileBtn, drawer);

        homeBtn.setOnAction(e -> {

            SceneController.startHome("foodtype", false);
        });


        vegan.setOnAction(e -> {

            foodDetails = new FoodDetails.Builder()
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

            SceneController.startPieChart("foodtype", false, foodDetails, Properties.vegan);
        });

        vegetarian.setOnAction(e -> {

            foodDetails = new FoodDetails.Builder()
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

            SceneController.startPieChart("foodtype", false, foodDetails, Properties.vegetarian);
        });

        pascetarian.setOnAction(e -> {

            foodDetails = new FoodDetails.Builder()
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

            SceneController.startPieChart("foodtype", false, foodDetails, Properties.pescetarian);
        });

        lowmeat.setOnAction(e -> {

            foodDetails = new FoodDetails.Builder()
                    .setBeef(20)
                    .setFish(0)
                    .setOther(5)
                    .setPoultry(10)
                    .setGrains(10)
                    .setDairy(50)
                    .setFruits(5)
                    .setLocal(false)
                    .setHomecooked(false)
                    .setType(Properties.lowmeat)
                    .build();

            SceneController.startPieChart("foodtype", false, foodDetails, Properties.lowmeat);
        });

        mediummeat.setOnAction(e -> {

            foodDetails = new FoodDetails.Builder()
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

            SceneController.startPieChart("foodtype", false, foodDetails, Properties.mediummeat);
        });

        highmeat.setOnAction(e -> {

            foodDetails = new FoodDetails.Builder()
                    .setBeef(40)
                    .setFish(0)
                    .setOther(10)
                    .setPoultry(30)
                    .setGrains(0)
                    .setDairy(5)
                    .setFruits(5)
                    .setLocal(false)
                    .setHomecooked(false)
                    .setType(Properties.highmeat)
                    .build();

            SceneController.startPieChart("foodtype", false, foodDetails, Properties.highmeat);
        });

    }
}
