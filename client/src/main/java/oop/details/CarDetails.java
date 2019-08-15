package oop.details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CarDetails {

    private int carId;
    private int userId;
    private String type;
    private double mpg;
    private String name;

    /**
     * Simple constructor for CarDetails.
     *
     * @param type Car type
     * @param mpg  Miles per gallon
     * @param name Name of the car
     */
    public CarDetails(String type, double mpg, String name) {

        this.setType(type);
        this.setMpg(mpg);
        this.setName(name);
    }

    /**
     * Complex constructor for CarDetails.
     *
     * @param carId  The id of the car, as stored in the database
     * @param userId The id of the user owning the car
     * @param type   The type of the car
     * @param mpg    Miles per gallon
     * @param name   Name of the car
     */
    public CarDetails(int carId, int userId, String type, double mpg, String name) {

        this.setCarId(carId);
        this.setUserId(userId);
        this.setType(type);
        this.setMpg(mpg);
        this.setName(name);

    }

    /**
     * Parse a json containing car data.
     *
     * @param json JSON to parse
     * @return An ArrayList containing the data
     */
    public static ArrayList<CarDetails> parse(String json) {

        json = "{\"cars\":" + json + "}";

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("cars");

        ArrayList<CarDetails> carDetails = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            CarDetails carDetails1 = new CarDetails(
                    jsonArray.get(i).getAsJsonObject().get("id").getAsInt(),
                    jsonArray.get(i).getAsJsonObject().get("userid").getAsInt(),
                    jsonArray.get(i).getAsJsonObject().get("type").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("mpg").getAsDouble(),
                    jsonArray.get(i).getAsJsonObject().get("name").getAsString()
            );

            carDetails.add(carDetails1);
        }

        return carDetails;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMpg() {
        return mpg;
    }

    public void setMpg(double mpg) {
        this.mpg = mpg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Equals method to compare an object to this.
     *
     * @param other The object to compare
     * @return True if the other object is the same as this one, false otherwise
     */
    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }

        if (other instanceof CarDetails) {

            CarDetails that = (CarDetails) other;

            return that.getCarId() == this.getCarId()
                    && that.getMpg() == this.getMpg()
                    && that.getType().equals(this.getType())
                    && that.getUserId() == this.getUserId()
                    && that.getName().equals(this.getName());

        }

        return false;
    }

    /**
     * JSONifying the class, to faciliate sending to Server.
     *
     * @return a JSON string
     */
    public String stringify() {
        Gson gson = new Gson();

        Map jsonMap = new HashMap();

        jsonMap.put("userid", getUserId());
        jsonMap.put("type", getType());
        jsonMap.put("mpg", getMpg());
        jsonMap.put("name", getName());

        String message = gson.toJson(jsonMap);
        System.out.println(message);

        return message;
    }
}
