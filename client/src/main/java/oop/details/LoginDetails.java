package oop.details;

import com.google.gson.Gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import oop.App;
import oop.validators.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Class that stores the username and password of a user.
 */
public class LoginDetails {

    /**
     * Variable to store username.
     */
    private String username;

    /**
     * Variable to store password.
     */
    private String password;

    /**
     * Empty constructor for LoginDetails class.
     */
    public LoginDetails() {
        this.username = null;
        this.password = null;
    }

    /**
     * Constructor with parameters for LoginDetails.
     *
     * @param user the user's username
     * @param pass the user's password
     */
    public LoginDetails(final String user, final String pass) {
        this.username = user;
        this.password = pass;
    }

    /**
     * Getter for username.
     *
     * @return username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Setter for username.
     *
     * @param userName the username of this user
     */
    public final void setUsername(final String userName) {
        this.username = userName;
    }

    /**
     * Getter for password.
     *
     * @return password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Setter for password.
     *
     * @param passWord the password of this user
     */
    public final void setPassword(final String passWord) {
        this.password = passWord;
    }

    /**
     * Transform the user's login details into a JSON.
     *
     * @return the JSON
     */
    public final String stringify() {

        String hashedPass = Validator.hashWith256(password);

        Gson gson = new Gson();

        Map jsonMap = new HashMap();

        jsonMap.put("username", username);
        jsonMap.put("password", hashedPass);

        String message = gson.toJson(jsonMap);
        System.out.println(message);
        App.log(Level.INFO, LoginDetails.class.getName(), "Creating JSON - Login");

        return message;
    }

    /**
     * Retrieves the id of the user.
     * @param json JSON containing user id
     * @return Returns the user id
     */
    public int parseId(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return jsonObject.get("payload").getAsInt();
    }

}
