package oop.details;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class LeaderboardDetails {

    private String name;
    private int score;

    /**
     * Constructor for Leaderboard details.
     * @param name - user's name
     * @param score - user's score
     */
    public LeaderboardDetails(String name, int score) {

        this.setName(name);
        this.setScore(score);
    }

    /**
     * Parses json containing Leaderboard Details.
     * @param json - string to parse
     * @return
     */
    public static ArrayList<LeaderboardDetails> parse(String json) {

        json = "{\"leaderboard\":" + json + "}";

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("leaderboard");

        ArrayList<LeaderboardDetails> arr = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            arr.add(new LeaderboardDetails(
                    jsonArray.get(i).getAsJsonObject().get("username").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("score").getAsInt()
            ));
        }

        return arr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
