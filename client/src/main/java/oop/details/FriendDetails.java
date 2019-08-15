package oop.details;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class FriendDetails {

    private String username;

    private ArrayList<String> friends;

    /**
     * Constructor for FriendsDetails.
     *
     * @param username Username of the user having these friends
     * @param friends  Arraylist of usernames/friends
     */
    public FriendDetails(String username, ArrayList<String> friends) {

        this.setUsername(username);
        this.setFriends(friends);
    }

    /**
     * Parse a json containing food data.
     *
     * @param json the json to be parsed
     * @return an ArrayList containing all food data
     */
    public static ArrayList<String> parse(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("friends");

        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            arr.add(jsonArray.get(i).getAsString());
        }

        return arr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    /**
     * Method that checks if a user is in a user's friend list.
     *
     * @param user User to check
     * @return Returns true if user is in friend list, false otherwise
     */
    public boolean isFriend(String user) {

        for (String s : this.getFriends()) {

            if (s.equals(user)) {

                return true;
            }
        }

        return false;
    }

}
