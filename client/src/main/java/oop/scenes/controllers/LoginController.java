package oop.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import oop.App;
import oop.connection.Connection;
import oop.details.LoginDetails;
import oop.scenes.SceneController;
import oop.validators.Validator;

import java.util.Collections;


public class LoginController {

    @FXML
    Button loginButton;

    @FXML
    Button registerButton;

    @FXML
    TextField txtLogin;

    @FXML
    PasswordField txtPass;

    @FXML
    Label wrongUserPass;

    @FXML
    BorderPane mainPane;

    /**
     * Called on scene loading.
     */
    @FXML
    private void initialize() {

        wrongUserPass.setVisible(false);

        registerButton.setOnAction(e -> {

            SceneController.startRegistration("login", true);

        });

        mainPane.setOnKeyPressed(mouseEvent -> {

            handleLogin();
        });


        loginButton.setOnAction(e -> {

            handleLogin();

        });

    }

    /**
     * Validate input and display a congrats window.
     */
    public final void handleLogin() {

        if (validateAll()) {

            wrongUserPass.setVisible(false);

            String userString = txtLogin.getText();
            String passString = txtPass.getText();

            LoginDetails loginDetails = new LoginDetails(userString,
                    passString);

            Connection connection = new Connection();

            int userId = connection.authorizeUser(loginDetails);

            if (userId > 0) {

                App.setUsername(loginDetails.getUsername());

                App.badgeDetails.addAll(App.connection.receiveBadges());

                App.achievementDetails.addAll(App.connection.receiveAchievements());
                Collections.sort(App.achievementDetails);

                for (int i = 1; i < App.achievementDetails.size(); i++) {

                    App.achievementDetails.get(i).setProgress(
                            App.connection.getAchievementProgress(i));
                }

                App.setUserId(userId);


                SceneController.startHome("login", true);
                SceneController.closeStage("login");

            } else {

                wrongUserPass.setVisible(true);
            }

        } else {

            wrongUserPass.setVisible(true);
        }
    }

    /**
     * Validates all inputs.
     *
     * @return - return true, if all fields have correct input
     */
    private boolean validateAll() {
        boolean usernameCheck;
        boolean passwordCheck;

        usernameCheck = Validator
                .validateUserName(txtLogin.getText());

        passwordCheck = Validator
                .validatePassword(txtPass.getText());


        return usernameCheck && passwordCheck;
    }
}
