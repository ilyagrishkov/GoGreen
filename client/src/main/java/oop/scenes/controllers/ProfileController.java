package oop.scenes.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import oop.App;
import oop.connection.Connection;
import oop.details.FoodDetails;
import oop.details.FriendDetails;
import oop.details.RegistrationDetails;
import oop.details.TripDetails;
import oop.details.UtilitiesDetails;
import oop.gui.ChangeLogLabelButton;
import oop.gui.FriendButton;
import oop.gui.Properties;
import oop.scenes.SceneController;

import java.util.ArrayList;

public class ProfileController {

    @FXML
    Button profileBtn;

    @FXML
    Button homeBtn;

    @FXML
    JFXDrawer drawer;

    @FXML
    Label aboutName;

    @FXML
    Label aboutBirth;

    @FXML
    Label aboutScore;

    @FXML
    VBox changesContainer;

    @FXML
    Button searchBtn;

    @FXML
    TextField searchField;

    @FXML
    VBox friendsContainer;

    @FXML
    Button addBtn;

    @FXML
    Button removeBtn;

    private Properties prop;

    private String username;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        prop = new Properties();

        addBtn.setVisible(false);
        removeBtn.setVisible(false);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Called after scene initialization.
     */
    public void start() {

        SceneController.sideBarControl(profileBtn, drawer);

        Connection conn = new Connection();

        FriendDetails friendDetails = conn.receiveFriends(App.getUsername());
        if (friendDetails.isFriend(username)) {
            removeBtn.setVisible(true);
        } else if (!username.equals(App.getUsername())) {
            addBtn.setVisible(true);
        }

        homeBtn.setOnAction(e -> {

            SceneController.startHome("profile", false);
        });

        // About section

        RegistrationDetails registrationDetails = conn.receiveDetails(username);
        registrationDetails.setScore(conn.receiveScore(username));

        aboutName.setText(aboutName.getText() + registrationDetails.getFirstname() + " "
                + registrationDetails.getLastname());
        aboutBirth.setText(aboutBirth.getText() + registrationDetails.getDateOfBirth());
        aboutScore.setText(aboutScore.getText() + registrationDetails.getScore());

        FriendDetails friends = conn.receiveFriends(this.getUsername());
        for (String s : friends.getFriends()) {
            friendsContainer.getChildren().add(new FriendButton(s));
        }

        // Search for friends and display them

        searchBtn.setOnAction(mouseEvent -> searchBtnOnAction(friends));

        removeBtn.setOnAction(mouseEvent -> {

            conn.removeFriend(username);
            setFriendProgress(false);
            removeBtn.setVisible(false);
            addBtn.setVisible(true);
        });
        addBtn.setOnAction(mouseEvent -> {

            conn.addFriend(username);
            setFriendProgress(true);
            addBtn.setVisible(false);
            removeBtn.setVisible(true);
        });

        ArrayList<FoodDetails> foodDetails = conn.receiveFood(username);
        ArrayList<String> foodDet = new ArrayList<>();

        for (FoodDetails fd : foodDetails) {

            foodDet.add(fd.getDate() + " You ate a " + fd.getType() + " meal.");
        }

        ArrayList<TripDetails> tripDetails = conn.receiveTrips(username);

        ArrayList<String> tripDet = new ArrayList<>();

        for (TripDetails td : tripDetails) {

            if (td.getType().equals("PubTrans")) {
                tripDet.add(td.getDate() + " You traveled by public transport.");
            } else if (td.getType().equals("BikeWalk")) {
                tripDet.add(td.getDate() + " You traveled on foot or by bike.");
            } else {
                tripDet.add(td.getDate() + " You traveled by " + td.getType() + ".");
            }
        }

        ArrayList<UtilitiesDetails> utilitiesDetails = conn.receiveUtilities(username);

        ArrayList<String> utilitiesDet = new ArrayList<>();

        for (UtilitiesDetails ud : utilitiesDetails) {

            utilitiesDet.add(ud.getDate() + " You paid "
                    + (ud.getElectricityBill() + ud.getNaturalgas() + ud.getWatersewage())
                    + "$ for utilities.");

        }

        ArrayList<String> result = mergeDetails(foodDet, tripDet);
        result = mergeDetails(result, utilitiesDet);

        for (String s : result) {
            changesContainer.getChildren().add(new ChangeLogLabelButton(s));
        }

    }

    private ArrayList<String> mergeDetails(ArrayList<String> details1, ArrayList<String> details2) {

        ArrayList<String> result = new ArrayList<>();
        int ind;
        int jnd;

        for (ind = 0, jnd = 0; ind < details1.size() && jnd < details2.size();) {

            if (details1.get(ind).compareTo(details2.get(jnd)) < 0) {
                result.add(details2.get(jnd));
                jnd++;
            } else {
                result.add(details1.get(ind));
                ind++;
            }
        }

        for (; ind < details1.size(); ind++) {
            result.add(details1.get(ind));
        }

        for (; jnd < details2.size(); jnd++) {
            result.add(details2.get(jnd));
        }

        return result;
    }

    private void setFriendProgress(boolean addrem) {

        if (addrem) {
            for (int i = prop.getAchfriendsStart(); i <= prop.getAchfriendsStop(); i++) {
                App.achievementDetails.get(i).setProgress(
                        App.achievementDetails.get(i).getProgress() + 1);
            }
            return;
        }
        for (int i = prop.getAchfriendsStart(); i <= prop.getAchfriendsStop(); i++) {
            App.achievementDetails.get(i).setProgress(
                    App.achievementDetails.get(i).getProgress() - 1);
        }
    }

    private void searchBtnOnAction(FriendDetails friends) {

        if (searchBtn.getText().equals("Search")) {

            String searchText = searchField.getText();
            ArrayList<String> arr = App.connection.receiveUsers(searchText);

            friendsContainer.getChildren().clear();
            for (String s : arr) {

                friendsContainer.getChildren().add(new FriendButton(s));
            }
            searchBtn.setText("Clear");
        } else {

            searchField.setText("");
            friendsContainer.getChildren().clear();

            for (String s : friends.getFriends()) {
                friendsContainer.getChildren().add(new FriendButton(s));
            }

            searchBtn.setText("Search");
        }
    }
}
