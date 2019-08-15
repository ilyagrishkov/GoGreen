package oop.details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import oop.App;
import oop.validators.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class RegistrationDetails {

    private String username;

    private String firstname;

    private String lastname;

    private String dateOfBirth;

    private String gender;

    private String password;

    private int score;

    /**
     * Constructor for creating a new Registration.
     *
     * @param username    username in register
     * @param firstname   first name
     * @param lastname    last name
     * @param dateOfBirth verified birth
     * @param gender      sex/gender of person
     * @param password    password enterd.
     */
    public RegistrationDetails(String username,
                               String firstname, String lastname,
                               String dateOfBirth, String gender,
                               String password) {

        this.setUsername(username);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setDateOfBirth(dateOfBirth);
        this.setGender(gender);
        this.setPassword(password);
    }

    /**
     * Constructor for creating a new Registration, for profilePane purposes.
     *
     * @param username    username in register
     * @param firstname   first name
     * @param lastname    last name
     * @param dateOfBirth verified birth
     * @param gender      sex/gender of person
     */
    public RegistrationDetails(String username,
                               String firstname, String lastname,
                               String dateOfBirth, String gender) {

        this.setUsername(username);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setDateOfBirth(dateOfBirth);
        this.setGender(gender);

    }

    /**
     * Parse a json containing registration data.
     *
     * @param json the json to be parsed
     * @return an ArrayList containing all registration data
     */
    public static RegistrationDetails parse(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new RegistrationDetails(
                jsonObject.get("username").getAsString(),
                jsonObject.get("firstname").getAsString(),
                jsonObject.get("lastname").getAsString(),
                jsonObject.get("dateofbirth").getAsString(),
                jsonObject.get("gender").getAsString()
        );
    }

    /**
     * Parse a json containing an int.
     *
     * @param json Json containing score
     * @return An int representing a score
     */
    public static int parseScore(String json) {

        json = "{\"score\":\"" + json + "\"}";

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return jsonObject.get("score").getAsInt();
    }

    /**
     * Parses a json containing a list of strings.
     *
     * @param json Json containing a list of users
     * @return The list of users/strings.
     */
    public static ArrayList<String> parseUsers(String json) {

        json = "{\"users\":" + json + "}";

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("users");

        ArrayList<String> array = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            array.add(jsonArray.get(i).getAsString());
        }

        return array;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * UnJSONifing the string.
     *
     * @return returns a String of the data
     */
    public String stringify() {

        String hashedPass = Validator.hashWith256(password);

        Gson gson = new Gson();

        Map jsonMap = new HashMap();

        jsonMap.put("username", username);
        jsonMap.put("firstname", firstname);
        jsonMap.put("lastname", lastname);
        jsonMap.put("dateofbirth", dateOfBirth);
        jsonMap.put("gender", gender);
        jsonMap.put("password", hashedPass);

        String message = gson.toJson(jsonMap);
        System.out.println(message);
        App.log(Level.INFO, RegistrationDetails.class.getName(), "Creating JSON - Registration");

        return message;
    }

}
