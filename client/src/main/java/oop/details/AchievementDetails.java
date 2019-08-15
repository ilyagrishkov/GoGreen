package oop.details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AchievementDetails implements Comparable<AchievementDetails> {

    private int id;
    private String name;
    private String description;
    private int goal;
    private int progress;


    /**
     * Constructor for AchievementsDetails.
     * @param id - Achievement id
     * @param name - Achievement name
     * @param description - Achievement description
     */
    public AchievementDetails(int id, String name, String description, int goal) {

        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setGoal(goal);
    }

    /**
     * Parse a json containing achievements data.
     *
     * @param json the json to be parsed
     * @return an AchievementDetails containing an achievements' data
     */
    public static AchievementDetails parseAchievement(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new AchievementDetails(
                jsonObject.get("id").getAsInt(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("description").getAsString(),
                jsonObject.get("goal").getAsInt()
        );

    }

    /**
     * Parse a json containing achievements data.
     *
     * @param json the json to be parsed
     * @return an ArrayList containing all achievements
     */
    public static ArrayList<AchievementDetails> parseAchievements(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("achievements");

        ArrayList<AchievementDetails> arr = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            arr.add(new AchievementDetails(
                    jsonArray.get(i).getAsJsonObject().get("id").getAsInt(),
                    jsonArray.get(i).getAsJsonObject().get("name").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("description").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("goal").getAsInt()
            ));
        }

        return arr;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    /**
     * JSONifying the class, to faciliate sending to Server.
     *
     * @return a JSON string
     */
    public String stringify() {
        Gson gson = new Gson();

        Map jsonMap = new HashMap();

        jsonMap.put("id", getId());
        jsonMap.put("name", getName());
        jsonMap.put("description", getDescription());

        String message = gson.toJson(jsonMap);
        System.out.println(message);

        return message;
    }

    @Override
    public int compareTo(AchievementDetails achievementDetails) {
        int compare = achievementDetails.getId();
        return this.getId() - compare;
    }

}
