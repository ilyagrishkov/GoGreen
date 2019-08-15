package oop.scenes.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import oop.App;
import oop.connection.Connection;
import oop.details.BadgeDetails;
import oop.details.FoodDetails;
import oop.scenes.Properties;
import oop.scenes.SceneController;

import java.util.ArrayList;

public class PieChartController {


    @FXML
    PieChart pieChart;

    @FXML
    HBox beefBox;

    @FXML
    HBox fishBox;

    @FXML
    HBox otherBox;

    @FXML
    HBox poultryBox;

    @FXML
    HBox grainsBox;

    @FXML
    HBox dairyBox;

    @FXML
    HBox fruitsBox;

    @FXML
    HBox snacksBox;

    @FXML
    JFXSlider beefSlider;

    @FXML
    JFXSlider fishSlider;

    @FXML
    JFXSlider otherSlider;

    @FXML
    JFXSlider poultrySlider;

    @FXML
    JFXSlider grainsSlider;

    @FXML
    JFXSlider dairySlider;

    @FXML
    JFXSlider fruitsSlider;

    @FXML
    JFXSlider snacksSlider;

    @FXML
    Button profileBtn;

    @FXML
    Button homeBtn;

    @FXML
    Button doneBtn;

    @FXML
    JFXDrawer drawer;

    @FXML
    JFXToggleButton homecooked;

    @FXML
    JFXToggleButton local;

    oop.gui.Properties prop;

    FoodDetails foodDetails;

    String mealtype;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        prop = new oop.gui.Properties();

        SceneController.sideBarControl(profileBtn, drawer);

        homeBtn.setOnAction(e -> {

            SceneController.startHome("piechart", false);
        });

        doneBtn.setOnAction(e -> {

            pushToServer(foodDetails);
            SceneController.startHome("piechart", false);
        });

        homecooked.setOnAction(e -> {
            if (homecooked.isSelected()) {

                foodDetails.setHomecooked(true);
            } else {

                foodDetails.setHomecooked(false);
            }
        });

        local.setOnAction(e -> {
            if (local.isSelected()) {

                foodDetails.setLocal(true);
            } else {

                foodDetails.setLocal(false);
            }
        });

        beefBox.setVisible(false);
        beefBox.setManaged(false);

        fishBox.setVisible(false);
        fishBox.setManaged(false);

        otherBox.setVisible(false);
        otherBox.setManaged(false);

        poultryBox.setVisible(false);
        poultryBox.setManaged(false);

        grainsBox.setVisible(false);
        grainsBox.setManaged(false);

        dairyBox.setVisible(false);
        dairyBox.setManaged(false);

        fruitsBox.setVisible(false);
        fruitsBox.setManaged(false);

        snacksBox.setVisible(false);
        snacksBox.setManaged(false);

    }

    /**
     * Set values after scene initialization.
     * @param mealtype - fisrt value to set
     * @param foodDetails - second value to set
     */
    public void setValues(String mealtype, FoodDetails foodDetails) {

        this.mealtype = mealtype;
        this.foodDetails = foodDetails;
    }

    /**
     * Called after scene initialization.
     */
    public void start() {

        pieChart.setLegendVisible(false);

        ArrayList<PieChart.Data> arrayList = new ArrayList<>(8);

        int index = 0;

        arrayList.add(index++, new PieChart.Data("Beef, pork, lamb, veal",
                foodDetails.getBeef()));

        arrayList.add(index++, new PieChart.Data("Fish & seafood", foodDetails.getFish()));

        arrayList.add(index++, new PieChart.Data("Other meat & alternatives",
                foodDetails.getOther()));

        arrayList.add(index++, new PieChart.Data("Poultry & eggs", foodDetails.getPoultry()));

        arrayList.add(index++, new PieChart.Data("Grains & baked goods", foodDetails.getGrains()));

        arrayList.add(index++, new PieChart.Data("Dairy", foodDetails.getDairy()));

        arrayList.add(index++, new PieChart.Data("Fruits and vegetables", foodDetails.getFruits()));

        arrayList.add(index++, new PieChart.Data("Snacks, drinks, etc...",
                foodDetails.getSnacks()));


        ObservableList<PieChart.Data> foodData = FXCollections.observableArrayList(arrayList);

        for (PieChart.Data data : arrayList) {
            if (data.getPieValue() != 0) {
                pieChart.getData().add(data);
            }
        }

        setUpSliders(foodData);
    }

    /**
     * Sets up sliders on the PieChart.
     * @param foodData - preset values for PieChart
     */
    public void setUpSliders(ObservableList<PieChart.Data> foodData) {
        int index = 0;

        PieChart.Data beefPorkEtc = foodData.get(index++);
        initializeSlider(beefSlider, beefPorkEtc, "beef");

        PieChart.Data fishSeafood = foodData.get(index++);
        initializeSlider(fishSlider, fishSeafood, "fish");

        PieChart.Data otherMeat = foodData.get(index++);
        initializeSlider(otherSlider, otherMeat, "other");

        PieChart.Data poultryEggs = foodData.get(index++);
        initializeSlider(poultrySlider, poultryEggs, "poultry");

        PieChart.Data grainsBakedGoods = foodData.get(index++);
        initializeSlider(grainsSlider, grainsBakedGoods, "grains");

        PieChart.Data dairy = foodData.get(index++);
        initializeSlider(dairySlider, dairy, "dairy");

        PieChart.Data fruitsVegetables = foodData.get(index++);
        initializeSlider(fruitsSlider, fruitsVegetables, "fruits");

        PieChart.Data snacksDrinks = foodData.get(index++);
        initializeSlider(snacksSlider, snacksDrinks, "snacks");


        if (!mealtype.equals(Properties.vegan)) {

            if (!mealtype.equals(Properties.vegetarian)) {

                if (!mealtype.equals(Properties.pescetarian)) {

                    beefBox.setVisible(true);
                    beefBox.setManaged(true);
                }

                fishBox.setVisible(true);
                fishBox.setManaged(true);

                if (!mealtype.equals(Properties.pescetarian)) {

                    otherBox.setVisible(true);
                    otherBox.setManaged(true);
                }

                poultryBox.setVisible(true);
                poultryBox.setManaged(true);

            }
        }

        grainsBox.setVisible(true);
        grainsBox.setManaged(true);

        if (!mealtype.equals(Properties.vegan)) {

            dairyBox.setVisible(true);
            dairyBox.setManaged(true);

        }

        fruitsBox.setVisible(true);
        fruitsBox.setManaged(true);

        snacksBox.setVisible(true);
        snacksBox.setManaged(true);
    }

    private void initializeSlider(JFXSlider slide, PieChart.Data slice, String name) {

        slide.setValue(slice.getPieValue());
        slide.setMin(0.0);
        slide.setMax(100.0);

        slide.valueProperty().addListener((obs, oldValue, newValue) -> {

            slice.setPieValue((double) newValue);


            if (newValue.intValue() == 0) {

                slice.setPieValue(0);
                foodDetails.setToZero(name);
                pieChart.getData().remove(slice);

            }

            if (newValue.intValue() > 0 && oldValue.intValue() == 0) {

                pieChart.getData().add(slice);
            }
        });
    }

    /**
     * Gets all the pie chart data, sends then to server and changes scene.
     */
    public void pushToServer(FoodDetails foodDetails) {
        int sum = 0;

        for (PieChart.Data nodes : pieChart.getData()) {
            sum += (int) nodes.getPieValue();
        }

        for (PieChart.Data nodes : pieChart.getData()) {
            int updateVal = (int) Math.round(Math.round(nodes.getPieValue()) * 100.00 / sum);

            String name = nodes.getName();

            foodDetails = setFoodDetails(foodDetails, name, updateVal);

        }


        Connection connection = new Connection();
        connection.sendFood(foodDetails);
        setAchievementProgress(foodDetails.getType());
    }

    /**
     * Chooses which property of foodDetails to update.
     * @param foodDetails foodDetails to update
     * @param name name of property
     * @param updateVal value to update with
     * @return returns the updated foodDetails
     */
    private FoodDetails setFoodDetails(FoodDetails foodDetails, String name, int updateVal) {

        switch (name) {
            case "Beef":
                foodDetails.setBeef(updateVal);
                break;

            case "Fish":
                foodDetails.setFish(updateVal);
                break;

            case "Other":
                foodDetails.setOther(updateVal);
                break;

            case "Poultry":
                foodDetails.setPoultry(updateVal);
                break;

            case "Grains":
                foodDetails.setGrains(updateVal);
                break;

            case "Dairy":
                foodDetails.setDairy(updateVal);
                break;

            case "Fruits":
                foodDetails.setFruits(updateVal);
                break;

            case "Snacks":
                foodDetails.setSnacks(updateVal);
                break;

            default:
                break;
        }

        return foodDetails;
    }

    /**
     * Update the progress of the user towards an achievement.
     * @param type Type of the meal the user ate
     */
    private void setAchievementProgress(String type) {

        switch (type) {
            case "Vegan":
                for (int i = prop.getAchveganStart(); i <= prop.getAchveganStop(); i++) {

                    App.achievementDetails.get(i).setProgress(
                            App.achievementDetails.get(i).getProgress() + 1);
                }
                if (App.achievementDetails.get(prop.getAchveganStop()).getProgress()
                        > App.achievementDetails.get(prop.getAchveganStop()).getGoal()) {
                    App.connection.sendBadge(new BadgeDetails(
                            prop.getBadgeVegan(),
                            App.badgeDetails.get(prop.getBadgeVegan()).getName(),
                            App.badgeDetails.get(prop.getBadgeVegan()).getDescription()
                    ));
                }
                break;
            case "Vegetarian":
                for (int i = prop.getAchvegStart(); i <= prop.getAchvegStop(); i++) {

                    App.achievementDetails.get(i).setProgress(
                            App.achievementDetails.get(i).getProgress() + 1);
                }
                if (App.achievementDetails.get(prop.getAchvegStop()).getProgress()
                        > App.achievementDetails.get(prop.getAchvegStop()).getGoal()) {
                    App.connection.sendBadge(new BadgeDetails(
                            prop.getBadgeVegetarian(),
                            App.badgeDetails.get(prop.getBadgeVegetarian()).getName(),
                            App.badgeDetails.get(prop.getBadgeVegetarian()).getDescription()
                    ));
                }
                break;
            case "Pescetarian":
                for (int i = prop.getAchpescStart(); i <= prop.getAchpescStop(); i++) {

                    App.achievementDetails.get(i).setProgress(
                            App.achievementDetails.get(i).getProgress() + 1);
                }
                if (App.achievementDetails.get(prop.getAchpescStop()).getProgress()
                        > App.achievementDetails.get(prop.getAchpescStop()).getGoal()) {
                    App.connection.sendBadge(new BadgeDetails(
                            prop.getBadgePescetarian(),
                            App.badgeDetails.get(prop.getBadgePescetarian()).getName(),
                            App.badgeDetails.get(prop.getBadgePescetarian()).getDescription()
                    ));
                }
                break;
            case "Low meat":
                for (int i = prop.getAchlowmeatStart(); i <= prop.getAchlowmeatStop(); i++) {

                    App.achievementDetails.get(i).setProgress(
                            App.achievementDetails.get(i).getProgress() + 1);
                }
                if (App.achievementDetails.get(prop.getAchlowmeatStop()).getProgress()
                        > App.achievementDetails.get(prop.getAchlowmeatStop()).getGoal()) {
                    App.connection.sendBadge(new BadgeDetails(
                            prop.getBadgeLowmeat(),
                            App.badgeDetails.get(prop.getBadgeLowmeat()).getName(),
                            App.badgeDetails.get(prop.getBadgeLowmeat()).getDescription()
                    ));
                }
                break;
            case "Medium meat":
                for (int i = prop.getAchmediummeatStart(); i <= prop.getAchmediummeatStop(); i++) {

                    App.achievementDetails.get(i).setProgress(
                            App.achievementDetails.get(i).getProgress() + 1);
                }
                if (App.achievementDetails.get(prop.getAchmediummeatStop()).getProgress()
                        > App.achievementDetails.get(prop.getAchmediummeatStop()).getGoal()) {
                    App.connection.sendBadge(new BadgeDetails(
                            prop.getBadgeMediummeat(),
                            App.badgeDetails.get(prop.getBadgeMediummeat()).getName(),
                            App.badgeDetails.get(prop.getBadgeMediummeat()).getDescription()
                    ));
                }
                break;
            default:
                break;
        }

    }
}
