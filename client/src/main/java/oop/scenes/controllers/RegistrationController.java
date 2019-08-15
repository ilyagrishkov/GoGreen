package oop.scenes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import oop.App;
import oop.connection.Connection;
import oop.details.RegistrationDetails;
import oop.scenes.SceneController;
import oop.validators.RegistrationValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrationController {

    /**
     * Month with 31 days in it.
     */
    private static final int DAYS_31 = 31;
    /**
     * Month with 30 days in it.
     */
    private static final int DAYS_30 = 30;
    /**
     * Month with 29 days in it.
     */
    private static final int DAYS_29 = 29;
    /**
     * Month with 28 days in it.
     */
    private static final int DAYS_28 = 28;
    /**
     * Initial year ComboBox value. Year 1970.
     */
    private static final int YEAR_1970 = 1970;
    /**
     * Initial year ComboBox value. Year 1900.
     */
    private static final int YEAR_1900 = 1900;
    /**
     * Minimum age for registration.
     */
    private static final int MINIMUM_AGE = 14;
    /**
     * Minimum registration age allowed - 14 years old.
     */
    private static final int MINIMUM_REG_AGE_YEAR =
            Calendar.getInstance().get(Calendar.YEAR) - MINIMUM_AGE;
    @FXML
    Button registerButton;
    @FXML
    ComboBox<Integer> cbDay;
    @FXML
    ComboBox<String> cbMonth;
    @FXML
    ComboBox<Integer> cbYear;
    @FXML
    ChoiceBox<String> cbGender;
    @FXML
    TextField txtUsername;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtLastname;
    @FXML
    TextField txtPassword;
    @FXML
    TextField txtRepassword;
    @FXML
    BorderPane mainPane;

    /**
     * Called on scene loading.
     */
    @FXML
    public void initialize() {

        initializeGenders();
        initializeMonths();
        initializeYears();
        setNumberOfDays(31);

        mainPane.setOnKeyPressed(mouseEvent -> {

            handleRegistration();
        });

        registerButton.setOnAction(e -> {

            handleRegistration();
        });

        cbMonth.setOnAction(event -> setNumberOfDays(
                RegistrationValidator.validateNumberOfDays(
                        cbMonth.getValue(), cbYear.getValue())));

        cbYear.setOnAction(event -> setNumberOfDays(
                RegistrationValidator.validateNumberOfDays(
                        cbMonth.getValue(), cbYear.getValue())));
    }

    private void handleRegistration() {


        if (validateAll()) {

            UserData userData = new UserData(txtUsername.getText(),
                    txtPassword.getText());

            PersonData personData = new PersonData(txtFirstname.getText(),
                    txtLastname.getText(), cbDay.getValue().toString(),
                    cbMonth.getValue(), cbYear.getValue().toString(),
                    cbGender.getValue());

            if (sendDetails(userData, personData)) {

                App.setUsername(txtUsername.getText());

                SceneController.startInitialSurvey("registration", false);
                SceneController.closeStage("login");

            }
        }

    }

    /**
     * Creates an ObservableList of months and adds them as
     * items to given ChoiceBox.
     */
    private void initializeGenders() {

        ObservableList<String> genders = FXCollections.observableArrayList(
                "Male", "Female");
        cbGender.setItems(genders);
        cbGender.setValue("Male");
    }

    /**
     * Creates an ObservableList of months and adds them as
     * items to given ComboBox.
     */
    private void initializeMonths() {

        final ArrayList<String> monthsList = new ArrayList<>(Arrays.asList(
                "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"));

        ObservableList<String> months = FXCollections
                .observableArrayList(monthsList);
        cbMonth.setItems(months);
        cbMonth.setValue("January");
    }

    /**
     * Creates an ObservableList of years and adds them as
     * items to given ComboBox.
     */
    private void initializeYears() {

        final ArrayList<Integer> yearsList =
                new ArrayList<>(MINIMUM_REG_AGE_YEAR - YEAR_1900);

        for (int i = MINIMUM_REG_AGE_YEAR; i >= YEAR_1900; i--) {
            yearsList.add(i);
        }

        ObservableList<Integer> years = FXCollections
                .observableArrayList(yearsList);
        cbYear.setItems(years);
        cbYear.setValue(YEAR_1970);
    }

    /**
     * Sets certain number of days to the items of ComboBox.
     *
     * @param days - number of days to add
     */
    public final void setNumberOfDays(final int days) {

        int tempDay;
        int numberOfDays;

        try {
            tempDay = cbDay.getValue();

        } catch (NullPointerException e) {
            tempDay = 1;
        }

        ArrayList<Integer> listOfDays = new ArrayList<>();
        switch (days) {
            case DAYS_30:

                numberOfDays = DAYS_30;

                if (tempDay > DAYS_30) {
                    tempDay = DAYS_30;
                }
                break;

            case DAYS_29:

                numberOfDays = DAYS_29;

                if (tempDay > DAYS_29) {
                    tempDay = DAYS_29;
                }
                break;

            case DAYS_28:

                numberOfDays = DAYS_28;

                if (tempDay > DAYS_28) {
                    tempDay = DAYS_28;
                }
                break;

            default:

                numberOfDays = DAYS_31;
                break;
        }

        for (int i = 1; i <= numberOfDays; i++) {
            listOfDays.add(i);
        }

        ObservableList<Integer> daysList = FXCollections
                .observableArrayList(listOfDays);
        cbDay.setItems(daysList);
        cbDay.setValue(tempDay);
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

    /**
     * Creates RegistrationDetails object and send it to server.
     *
     * @param userData   - object containing userData
     * @param personData - object containing personData
     * @return return true, if a new user ws created
     */
    private boolean sendDetails(UserData userData, PersonData personData) {

        RegistrationDetails newuser = new RegistrationDetails(userData.getUsername(),
                personData.getFirstName(), personData.getLastName(),
                personData.getDateOfBirth(), personData.getGender(),
                userData.getPassword());

        App.setGlobalRegister(newuser);

        newuser.stringify();
        Connection connection = new Connection();
        return connection.registerUser(newuser);
    }

    /**
     * Validates all inputs.
     *
     * @return - return true, if all fields have correct input
     */
    private boolean validateAll() {

        boolean usernameCheck;

        usernameCheck = RegistrationValidator
                .validateUserName(txtUsername.getText());

        boolean firstnameCheck;

        firstnameCheck = RegistrationValidator
                .validateName(txtFirstname.getText());

        boolean lastnameCheck;

        lastnameCheck = RegistrationValidator
                .validateName(txtLastname.getText());

        boolean passwordCheck;

        passwordCheck = RegistrationValidator
                .validatePassword(txtPassword.getText());

        boolean repasswordCheck;

        repasswordCheck = RegistrationValidator
                .validatePassword(txtRepassword.getText());

        boolean passwordComparison;

        passwordComparison = RegistrationValidator
                .checkRePassword(txtPassword.getText(),
                        txtRepassword.getText());

        setTextFieldColor(txtUsername, usernameCheck);
        setTextFieldColor(txtFirstname, firstnameCheck);
        setTextFieldColor(txtLastname, lastnameCheck);
        setTextFieldColor(txtPassword, passwordCheck);
        setTextFieldColor(txtRepassword, repasswordCheck);
        setTextFieldColor(txtRepassword, passwordComparison);

        return usernameCheck && firstnameCheck && lastnameCheck
                && passwordCheck && repasswordCheck
                && passwordComparison;
    }

    /**
     * Map month name to the corresponding number.
     *
     * @param month - month to map
     * @return
     */
    private String monthToNumber(String month) {

        Map<String, String> months = new HashMap<>();

        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("November", "11");
        months.put("December", "12");

        return months.get(month);

    }

    private class UserData {

        private String username;
        private String password;

        public UserData(String username, String password) {

            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private class PersonData {

        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String gender;


        public PersonData(String firstName, String lastName,
                          String day, String month, String year,
                          String gender) {

            this.firstName = firstName;
            this.lastName = lastName;
            if (Integer.parseInt(day) < 10) {
                day = "0" + day;
            }
            this.dateOfBirth = year + "-" + monthToNumber(month)
                    + "-" + day;
            this.gender = gender.toLowerCase();
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
