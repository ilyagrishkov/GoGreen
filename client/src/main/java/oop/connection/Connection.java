package oop.connection;

import static java.lang.Integer.parseInt;

import oop.App;
import oop.details.AchievementDetails;
import oop.details.BadgeDetails;
import oop.details.CarDetails;
import oop.details.FoodDetails;
import oop.details.FriendDetails;
import oop.details.LeaderboardDetails;
import oop.details.LoginDetails;
import oop.details.RegistrationDetails;
import oop.details.SettingsDetails;
import oop.details.TripDetails;
import oop.details.UtilitiesDetails;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

public class Connection {

    /**
     * HTTPRequest OK code.
     */
    private static final int RESPONSE_OK = 200;

    private static final String CLASS_NAME = Connection.class.getName();

    //private static String URL = "http://env-y-me.herokuapp.com";
    private static String URL = "http://localhost:8080";
    public HttpRequest httpRequest;

    /**
     * Basic constructor for Connection class. Identifies HttpRequest
     * to standard URL.
     */
    public Connection() {
        try {
            this.httpRequest = new HttpRequest(URL);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends registration data to server.
     *
     * @param regDetails user details to check
     * @return true if success, false otherwise
     */
    public final boolean registerUser(final oop.details.RegistrationDetails regDetails) {
        App.log(Level.INFO, CLASS_NAME, "Registering User");

        try {
            httpRequest.changeUrl(new URL(URL + "/registration"));

            HttpData httpData = httpRequest.sendPost(regDetails.stringify());

            if (httpData.getResponseCode()
                    == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Registering User");

                return true;
            }

            App.log(Level.WARNING, CLASS_NAME, "Unsuccessful Posting to Server - Registering User");

            return false;

        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Registering User");

            System.out.println(e);
            return false;
        }
    }

    /**
     * Sends Utilities Details to the server.
     *
     * @param utilDetails utilities details of the client
     * @return true or false; if it was successful or not
     */
    public final boolean sendUtilities(final oop.details.UtilitiesDetails utilDetails) {
        App.log(Level.INFO, CLASS_NAME, "Sending Utilities");
        String username = App.getUsername();

        if (username.equals("")) {
            App.log(Level.WARNING, CLASS_NAME, "User Not logged In");
            return false;
        }

        try {
            HttpRequest httpRequest = new HttpRequest(URL + "/" + username + "/storeUtilities");

            HttpData httpData = httpRequest.sendPost(utilDetails.stringify());

            if (httpData.getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Utilities");

                return true;
            }
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Utilities");

            return false;
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Utilities");

            System.out.println(e);
            return false;
        }
    }

    /**
     * Retrieves a UtilitiesDetails arraylist from the server.
     * @param username User's whose utilities to retrieve
     * @return Returns an arraylist with UtilitiesDetails
     */
    public final ArrayList<UtilitiesDetails> receiveUtilities(final String username) {

        try {
            String shorturl = URL + "/" + username + "/getAllUtilitiesId";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                ArrayList<UtilitiesDetails> utilitiesDetails = UtilitiesDetails.parse(
                        httpData.getData());

                return utilitiesDetails;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving Food");
        }

        return null;
    }

    /**
     * Checks whether the username exists in the database.
     *
     * @param loginDetails user details to check
     * @return id of the user, if found
     */
    public int authorizeUser(final LoginDetails loginDetails) {

        if (loginDetails.getUsername() == null
                || loginDetails.getPassword() == null) {
            App.log(Level.WARNING, CLASS_NAME, "LoginDetails are NULL");
            return -1;
        }

        try {
            httpRequest.changeUrl(new URL(URL + "/authorization"));

            HttpData httpData = httpRequest.sendPost(loginDetails.stringify());

            if (httpData.getResponseCode()
                    == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Logging In User");
                return new LoginDetails().parseId(httpData.getData());
            }
            App.log(Level.WARNING, CLASS_NAME, "Unsuccessful Posting to  Server - Logging In User");

            return -1;
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Logging In User");

            System.out.println(e);
            return -1;
        }
    }

    /**
     * Sends all the food details to the server.
     *
     * @param foodDetails food details
     */
    public boolean sendFood(final FoodDetails foodDetails) {

        String username = App.getUsername();

        if (username.equals("")) {
            App.log(Level.WARNING, CLASS_NAME, "User Not logged In");
            return false;
        }

        try {
            String shorturl = URL + "/" + username + "/food";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendPost(foodDetails.stringify());

            if (httpData.getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Sending Food");
                return true;
            } else {
                App.log(Level.INFO, CLASS_NAME, "Unsuccessful Posting to Server - Sending Food");
                return false;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Sending Food");
            return false;
        }
    }

    /**
     * Requests a FoodDetails ArrayList for a specific user from the server.
     *
     * @param username the username whose food to retrieve
     * @return the ArrayList containing the FoodDetails
     */
    public ArrayList<FoodDetails> receiveFood(final String username) {

        try {
            String shorturl = URL + "/" + username + "/getAllFood";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                ArrayList<FoodDetails> foodDetails = FoodDetails.parse(
                        httpData.getData());

                return foodDetails;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving Food");
        }

        return null;
    }

    /**
     * Set the progress the user made towards an achievement.
     * @param achievementId The id of the achievement
     * @param progress The progress towards the achievement
     * @return Returns whether or not the update was succesful
     */
    public boolean setAchievementProgress(int achievementId, int progress) {

        try {
            String shorturl = URL + "/" + App.getUsername() + "/" + achievementId + "/"
                    + progress + "/setProgress";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                return true;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving "
                    + "achievement progress");
            return false;
        }

        return false;
    }

    /**
     * Retrieve the progress the user made towards an achievement.
     * @param id Id of the achievement
     * @return Returns the progress that has been made
     */
    public int getAchievementProgress(final int id) {

        try {
            String shorturl = URL + "/" + App.getUsername() + "/" + id + "/getProgress";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                return parseInt(httpData.getData());

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server -"
                    + "Retrieving achievement progress");
        }

        return 0;
    }

    /**
     * Sends all the badge details to the server.
     *
     * @param badgeDetails badge details
     */
    public boolean sendBadge(final BadgeDetails badgeDetails) {

        String username = App.getUsername();

        if (username.equals("")) {
            App.log(Level.WARNING, CLASS_NAME, "User Not logged In");
            return false;
        }

        try {
            String shorturl = URL + "/" + username + "/food";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendPost(badgeDetails.stringify());

            if (httpData.getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Sending Badge");
                return true;
            } else {
                App.log(Level.INFO, CLASS_NAME, "Unsuccessful Posting to Server - Sending Badge");
                return false;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Sending Badge");
            return false;
        }
    }

    /**
     * Requests all badges from the server.
     *
     * @return the ArrayList containing the BadgeDetails
     */
    public ArrayList<oop.details.BadgeDetails> receiveBadges() {

        try {
            String shorturl = URL + "/getAllBadges";

            httpRequest.changeUrl(new URL(shorturl));

            oop.connection.HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                return BadgeDetails.parseBadges(httpData.getData());
            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME,
                    "IOException - GET to Server - Retrieving all badges");
        }

        return null;
    }

    /**
     * Requests all badges from the server.
     *
     * @return the ArrayList containing the BadgeDetails
     */
    public ArrayList<Integer> receiveUserBadges() {

        try {
            String shorturl = URL + "/" + App.getUsername() + "/getAllBadges";

            httpRequest.changeUrl(new URL(shorturl));

            oop.connection.HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                return oop.details.BadgeDetails.parseId(httpData.getData());
            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME,
                    "IOException - GET to Server - Retrieving user badges");
        }

        return null;
    }

    /**
     * Requests an AchievementsDetails for a specific id from the server.
     *
     * @return the ArrayList containing the AchievementDetails
     */
    public ArrayList<AchievementDetails> receiveAchievements() {

        try {
            String shorturl = URL + "/getAllAchievements";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                return AchievementDetails.parseAchievements(httpData.getData());
            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME,
                    "IOException - GET to Server - Retrieving Achievement");
        }

        return null;
    }

    /**
     * Requests a CarDetails ArrayList for a specific user from the server.
     *
     * @param username the username whose cars to retrieve
     * @return the ArrayList containing the CarDetails
     */
    public ArrayList<CarDetails> receiveCars(final String username) {

        try {
            String shorturl = URL + "/" + username + "/getAllCars";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                ArrayList<CarDetails> carDetails = CarDetails.parse(
                        httpData.getData());

                return carDetails;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving CarList");
        }

        return null;

    }

    /**
     * Deletes registration information.
     * @param regDetails - RegistrationDetails to delete
     * @return Return whether or not the user has been succesfully deleted from database
     */
    public boolean deleteRegistration(final RegistrationDetails regDetails) {
        try {
            String shortUrl = URL + "/deleteRegistration";

            httpRequest.changeUrl(new URL(shortUrl));

            HttpData httpData = httpRequest.sendPost(regDetails.stringify());

            if (httpData.getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME,
                        "Successful Posting to Server - Deleting Registration");
                return true;
            } else {
                App.log(Level.INFO, CLASS_NAME,
                        "Unsuccessful Posting to Server - Deleting Registration");
                return false;
            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME,
                    "IOException - POST to Server - Deleting Registration");
            return false;
        }
    }

    /**
     * Deletes a car entry with a certain id.
     *
     * @param id The id of the car to be deleted
     * @return Returns true for succesful delete, false otherwise
     */
    public boolean deleteCar(final int id) {

        try {
            String shorturl = URL + "/" + id + "/deleteCar";

            httpRequest.changeUrl(new URL(shorturl));


            if (httpRequest.sendGet().getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Deleting Car");
                return true;

            } else {
                App.log(Level.INFO, CLASS_NAME, "Unsuccessful Posting to Server - Deleting Car");
                return false;


            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Deleting Car");
            return false;
        }
    }

    /**
     * Sends all the CarDetails to server.
     *
     * @param carDetails CarDetails to be stored
     * @return Returns true if posting was succesful, false otherwise
     */
    public boolean sendCar(final oop.details.CarDetails carDetails) {

        String username = App.getUsername();

        if (username.equals("")) {
            App.log(Level.WARNING, CLASS_NAME, "User Not logged In");
            return false;
        }

        try {
            String shorturl = URL + "/" + username + "/storeCar";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendPost(carDetails.stringify());

            if (httpData.getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Sending Car");
                return true;
            } else {
                App.log(Level.INFO, CLASS_NAME, "Unsuccessful Posting to Server - Sending Car");
                return false;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Sending Car");
            return false;
        }

    }

    /**
     * Sends all the TripDetails to server.
     *
     * @param tripDetails tripDetails to be stored
     * @return Returns true if posting was succesful, false otherwise
     */
    public boolean sendTrip(final TripDetails tripDetails) {

        String username = App.getUsername();

        if (username.equals("")) {
            App.log(Level.WARNING, CLASS_NAME, "User Not logged In");
            return false;
        }

        try {
            String shorturl = URL + "/" + username + "/storeTrip";

            httpRequest.changeUrl(new URL(shorturl));

            System.out.println(tripDetails.stringify());

            HttpData httpData = httpRequest.sendPost(tripDetails.stringify());

            if (httpData.getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Sending Trip");
                return true;
            } else {
                App.log(Level.INFO, CLASS_NAME, "Unsuccessful Posting to Server - Sending Trip");
                return false;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Sending Trip");
            return false;
        }

    }

    /**
     * Requests a TripDetails ArrayList for a specific user from the server.
     *
     * @param username the username whose trips to retrieve
     * @return the ArrayList containing the TripDetails
     */
    public ArrayList<oop.details.TripDetails> receiveTrips(final String username) {

        try {
            String shorturl = URL + "/" + username + "/getAllTrips";

            httpRequest.changeUrl(new URL(shorturl));

            oop.connection.HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                //System.out.println(httpData.getData());

                ArrayList<oop.details.TripDetails> tripDetails = oop.details.TripDetails.parse(
                        httpData.getData());

                return tripDetails;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving TripList");
        }

        return null;

    }

    /**
     * Requests a SettingsDetails ArrayList for a specific user from the server.
     *
     * @param username the username whose settings to retrieve
     * @return the ArrayList containing the SettingsDetails
     */
    public SettingsDetails receiveSettings(final String username) {

        try {
            String shorturl = URL + "/" + username + "/getSettings";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                SettingsDetails settingsDetails = SettingsDetails.parse(
                        httpData.getData());

                return settingsDetails;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving Settings");
        }

        return null;

    }

    /**
     * Sends all the SettingsDetails to server.
     *
     * @param settingsDetails SettingsDetails to be stored
     * @return Returns true if posting was succesful, false otherwise
     */
    public boolean sendSettings(final SettingsDetails settingsDetails) {

        String username = App.getUsername();

        if (username.equals("")) {
            App.log(Level.WARNING, CLASS_NAME, "User Not logged In");
            return false;
        }

        try {
            String shorturl = URL + "/" + username + "/storeSettings";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendPost(settingsDetails.stringify());

            if (httpData.getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Sending Settings");
                return true;
            } else {
                App.log(Level.INFO, CLASS_NAME,
                        "Unsuccessful Posting to Server - Sending Settings");
                return false;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Sending Settings");
            return false;
        }

    }

    /**
     * Sends an id to the server to remove the food details entry.
     *
     * @param id Id of the food details
     */
    public boolean removeFood(final int id) {

        if (id <= 0) {
            return false;
        }

        try {
            String shorturl = URL + "/" + id + "/deleteFood";

            httpRequest.changeUrl(new URL(shorturl));


            if (httpRequest.sendGet().getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - "
                        + "Deleting food details");
                return true;
            } else {
                App.log(Level.INFO, CLASS_NAME, "Unsuccessful Posting to Server - "
                        + "Deleting food details");
                return false;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - "
                    + "Deleting food details");
            return false;
        }
    }

    /**
     * Requests a RegistrationDetails for a specific user from the server.
     *
     * @param username the username whose details to retrieve
     * @return the RegistrationDetails containing the data
     */
    public RegistrationDetails receiveDetails(final String username) {

        try {
            String shorturl = URL + "/" + username + "/getRegistrationInfo";

            httpRequest.changeUrl(new URL(shorturl));

            oop.connection.HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                return oop.details.RegistrationDetails.parse(httpData.getData());

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving Food");
        }

        return null;
    }

    /**
     * Requests usernames from the server according to a few chars.
     *
     * @param start the username whose details to retrieve
     * @return the RegistrationDetails containing the data
     */
    public ArrayList<String> receiveUsers(final String start) {

        try {
            String shorturl = URL + "/" + start + "/getUsernames";

            httpRequest.changeUrl(new URL(shorturl));

            oop.connection.HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                return RegistrationDetails.parseUsers(httpData.getData());

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving Food");
        }

        return null;
    }

    /**
     * Requests a score for a specific user from the server.
     *
     * @param username the username whose score to retrieve
     * @return the score of the user
     */
    public int receiveScore(final String username) {

        try {
            String shorturl = URL + "/" + username + "/getScore";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                return RegistrationDetails.parseScore(httpData.getData());

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving Food");
        }

        return -1;
    }

    /**
     * Requests a friends' leaderboard from the server.
     *
     * @param username the username whose friend leaderboard to retrieve
     * @return Leaderboard of the user, sorted
     */
    public ArrayList<LeaderboardDetails> receiveFriendLeaderboard(final String username) {

        try {
            String shorturl = URL + "/" + username + "/getFriendsLeaderboard";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                return LeaderboardDetails.parse(httpData.getData());

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving Food");
        }

        return null;
    }

    /**
     * Requests a list of friends for a specific user from the server.
     *
     * @param username the username whose friends to retrieve
     * @return the friends of the user
     */
    public FriendDetails receiveFriends(final String username) {

        try {
            String shorturl = URL + "/" + username + "/getAllFriends";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {

                System.out.println(httpData.getData());

                return new FriendDetails(username, FriendDetails.parse(httpData.getData()));

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - GET to Server - Retrieving friends");
        }

        return null;
    }

    /**
     * Sends a friend to the server to add.
     *
     * @param friend friend to add
     */
    public boolean addFriend(final String friend) {

        String username = App.getUsername();

        if (username.equals("")) {
            App.log(Level.WARNING, CLASS_NAME, "User Not logged In");
            return false;
        }

        try {
            String shorturl = URL + "/" + username + "/storeFriend";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendPost("{\"friend\":\"" + friend + "\"}");

            if (httpData.getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Adding friend");
                return true;
            } else {
                App.log(Level.INFO, CLASS_NAME, "Unsuccessful Posting to Server - Adding friend");
                return false;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Adding friend");
            return false;
        }
    }

    /**
     * Sends a friend to the server to remove.
     *
     * @param friend friend to add
     */
    public boolean removeFriend(final String friend) {

        String username = App.getUsername();

        if (username.equals("")) {
            App.log(Level.WARNING, CLASS_NAME, "User Not logged In");
            return false;
        }

        try {
            String shorturl = URL + "/" + username + "/deleteFriend";

            httpRequest.changeUrl(new URL(shorturl));

            HttpData httpData = httpRequest.sendPost("{\"friend\":\"" + friend + "\"}");

            if (httpData.getResponseCode() == RESPONSE_OK) {
                App.log(Level.INFO, CLASS_NAME, "Successful Posting to Server - Deleting friend");
                return true;
            } else {
                App.log(Level.INFO, CLASS_NAME, "Unsuccessful Posting to Server - Deleting friend");
                return false;

            }
        } catch (IOException e) {
            App.log(Level.WARNING, CLASS_NAME, "IOException - POST to Server - Deleting friend");
            return false;
        }
    }

    public static String getUrl() {
        return URL;
    }

    public static void setUrl(String url) {
        Connection.URL = url;
    }
}
