package oop.details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FoodDetails {

    private int id;

    private int beef;

    private int fish;

    private int other;

    private int poultry;

    private int grains;

    private int dairy;

    private int fruits;

    private int snacks;

    private String type;

    private boolean local;

    private boolean homecooked;

    private String date;

    /**
     * Constructor for food details.
     *
     * @param builder - builder, containing all the data
     */
    public FoodDetails(final Builder builder) {

        this.id = builder.id;
        this.beef = builder.beef;
        this.fish = builder.fish;
        this.other = builder.other;
        this.poultry = builder.poultry;
        this.grains = builder.grains;
        this.dairy = builder.dairy;
        this.fruits = builder.fruits;
        this.snacks = builder.snacks;
        this.local = builder.local;
        this.homecooked = builder.homecooked;
        this.type = builder.type;

        //Timestamp ts = new Timestamp(System.currentTimeMillis());
        //
        //this.date = ts.toString();
        //System.out.println("Current Time Stamp: " + finalDate);

        LocalDate now = LocalDate.now();

        this.date = now.toString();
    }

    /**
     * Parse a json containing food data.
     *
     * @param json the json to be parsed
     * @return an ArrayList containing all food data
     */
    public static ArrayList<FoodDetails> parse(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("food");

        ArrayList<FoodDetails> foodDetails = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            FoodDetails meal = new FoodDetails.Builder()
                    .setId(jsonArray.get(i).getAsJsonObject().get("id").getAsInt())
                    .setBeef(jsonArray.get(i).getAsJsonObject().get("meat").getAsInt())
                    .setFish(jsonArray.get(i).getAsJsonObject().get("fish").getAsInt())
                    .setOther(jsonArray.get(i).getAsJsonObject().get("meatAlt").getAsInt())
                    .setPoultry(jsonArray.get(i).getAsJsonObject().get("eggs").getAsInt())
                    .setGrains(jsonArray.get(i).getAsJsonObject().get("grains").getAsInt())
                    .setDairy(jsonArray.get(i).getAsJsonObject().get("dairy").getAsInt())
                    .setFruits(jsonArray.get(i).getAsJsonObject().get("veggies").getAsInt())
                    .setSnacks(jsonArray.get(i).getAsJsonObject().get("snacks").getAsInt())
                    .setType(jsonArray.get(i).getAsJsonObject().get("type").getAsString())
                    .build();

            String date = jsonArray.get(i).getAsJsonObject().get("date").getAsString();

            meal.setDate(date.substring(0, date.length() - 9));

            foodDetails.add(meal);
        }

        return foodDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBeef() {
        return beef;
    }

    public void setBeef(int beef) {
        this.beef = beef;
    }

    public int getFish() {
        return fish;
    }

    public void setFish(int fish) {
        this.fish = fish;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public int getPoultry() {
        return poultry;
    }

    public void setPoultry(int poultry) {
        this.poultry = poultry;
    }

    public int getGrains() {
        return grains;
    }

    public void setGrains(int grains) {
        this.grains = grains;
    }

    public int getDairy() {
        return dairy;
    }

    public void setDairy(int dairy) {
        this.dairy = dairy;
    }

    public int getFruits() {
        return fruits;
    }

    public void setFruits(int fruits) {
        this.fruits = fruits;
    }

    public int getSnacks() {
        return snacks;
    }

    public void setSnacks(int snacks) {
        this.snacks = snacks;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isHomecooked() {
        return homecooked;
    }

    public void setHomecooked(boolean homecooked) {
        this.homecooked = homecooked;
    }

    /**
     * JSONifying the class, to faciliate sending to Server.
     *
     * @return a JSON string
     */
    public String stringify() {
        Gson gson = new Gson();

        Map jsonMap = new HashMap();

        jsonMap.put("type", getType());
        jsonMap.put("local", isLocal());
        jsonMap.put("cooked", isHomecooked());
        jsonMap.put("meat", getBeef());
        jsonMap.put("fish", getFish());
        jsonMap.put("meatAlt", getOther());
        jsonMap.put("eggs", getPoultry());
        jsonMap.put("grains", getGrains());
        jsonMap.put("dairy", getDairy());
        jsonMap.put("veggies", getFruits());
        jsonMap.put("snacks", getSnacks());
        jsonMap.put("date", getDate());

        String message = gson.toJson(jsonMap);
        System.out.println(message);

        return message;
    }

    /**
     * Sets the specific value to 0. Used for the pie selection.
     *
     * @param text takes in the text corresponding to a parameter of FoodDetails
     */
    public void setToZero(String text) {
        switch (text) {
            case "beef":
                this.setBeef(0);
                break;
            case "fish":
                this.setFish(0);
                break;
            case "other":
                this.setOther(0);
                break;
            case "poultry":
                this.setPoultry(0);
                break;
            case "grains":
                this.setGrains(0);
                break;
            case "dairy":
                this.setDairy(0);
                break;
            case "fruits":
                this.setFruits(0);
                break;
            case "snacks":
                this.setSnacks(0);
                break;
            default:
                break;
        }
    }

    public static class Builder {

        private int id;

        private int beef;

        private int fish;

        private int other;

        private int poultry;

        private int grains;

        private int dairy;

        private int fruits;

        private int snacks;

        private boolean local;

        private boolean homecooked;

        private String type;


        public Builder() {
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setBeef(int beef) {
            this.beef = beef;
            return this;
        }

        public Builder setFish(int fish) {
            this.fish = fish;
            return this;
        }

        public Builder setOther(int other) {
            this.other = other;
            return this;
        }

        public Builder setPoultry(int poultry) {
            this.poultry = poultry;
            return this;
        }

        public Builder setGrains(int grains) {
            this.grains = grains;
            return this;
        }

        public Builder setDairy(int dairy) {
            this.dairy = dairy;
            return this;
        }

        public Builder setFruits(int fruits) {
            this.fruits = fruits;
            return this;
        }

        public Builder setSnacks(int snacks) {
            this.snacks = snacks;
            return this;
        }

        public Builder setLocal(boolean local) {
            this.local = local;
            return this;
        }

        public Builder setHomecooked(boolean homecooked) {
            this.homecooked = homecooked;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public FoodDetails build() {
            return new FoodDetails(this);
        }

    }


}
