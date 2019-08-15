package oop.details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import oop.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BadgeDetails {

    private int id;
    private String name;
    private String description;

    /**
     * Constructor for BadgeDetails.
     * @param id Id of the badge in database
     * @param name Name of the badge
     * @param description Description of the badge
     */
    public BadgeDetails(int id, String name, String description) {

        this.setId(id);
        this.setName(name);
        this.setDescription(description);

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

    /**
     * JSONifying the class, to faciliate sending to Server.
     *
     * @return a JSON string
     */
    public String stringify() {
        Gson gson = new Gson();

        Map jsonMap = new HashMap();

        jsonMap.put("badgeid", getId());
        jsonMap.put("userid", App.getUserId());

        String message = gson.toJson(jsonMap);
        System.out.println(message);

        return message;
    }

    /**
     *  Parse a json containing badge data.
     * @param json the json to be parsed
     * @return an ArrayList containing all food data
     */
    public static ArrayList<BadgeDetails> parseBadges(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("badges");

        ArrayList<BadgeDetails> arr = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            arr.add(new BadgeDetails(
                    jsonArray.get(i).getAsJsonObject().get("id").getAsInt(),
                    jsonArray.get(i).getAsJsonObject().get("name").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("description").getAsString()
            ));
        }

        return arr;
    }

    /**
     *  Parse a json containing badge data.
     * @param json the json to be parsed
     * @return an ArrayList containing all food data
     */
    public static ArrayList<Integer> parseId(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("badges");

        ArrayList<Integer> arr = new ArrayList<>();

        if (jsonArray != null) {

            for (int i = 0; i < jsonArray.size(); i++) {

                arr.add(jsonArray.get(i).getAsJsonObject().get("id").getAsInt());
            }
        }

        return arr;
    }

}
