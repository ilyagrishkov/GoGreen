package oop.details;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class SettingsDetails {

    private int userId;
    private double gasPrice;
    private double electricityPrice;
    private double electricityemmfactor;
    private double natgasPrice;
    private double livingSpace;

    /**
     * Constructor for settings data.
     *
     * @param userId               The id of the user
     * @param gasPrice             The gas price, as set by the user
     * @param electricityPrice     The electricity price, as set by the user
     * @param electricityemmfactor The electricity emmision factor
     * @param natgasPrice          The natural gas price, as set by the user
     * @param livingSpace          The living space, as set by the user
     */
    public SettingsDetails(int userId, double gasPrice, double electricityPrice,
                           double electricityemmfactor, double natgasPrice, double livingSpace) {

        this.setUserId(userId);
        this.setGasPrice(gasPrice);
        this.setElectricityPrice(electricityPrice);
        this.setElectricityemmfactor(electricityemmfactor);
        this.setNatgasPrice(natgasPrice);
        this.setLivingSpace(livingSpace);

    }

    /**
     * Constructor for settings data.
     *
     * @param gasPrice             The gas price, as set by the user
     * @param electricityPrice     The electricity price, as set by the user
     * @param electricityemmfactor The electricity emmision factor
     * @param natgasPrice          The natural gas price, as set by the user
     * @param livingSpace          The living space, as set by the user
     */
    public SettingsDetails(double gasPrice, double electricityPrice,
                           double electricityemmfactor, double natgasPrice, double livingSpace) {

        this.setGasPrice(gasPrice);
        this.setElectricityPrice(electricityPrice);
        this.setElectricityemmfactor(electricityemmfactor);
        this.setNatgasPrice(natgasPrice);
        this.setLivingSpace(livingSpace);

    }

    /**
     * Parse a json containing settings data.
     *
     * @param json JSON to parse
     * @return An ArrayList containing the data
     */
    public static SettingsDetails parse(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        SettingsDetails settingsDetails = new SettingsDetails(
                jsonObject.get("userId").getAsInt(),
                jsonObject.get("gasPrice").getAsDouble(),
                jsonObject.get("electricityPrice").getAsDouble(),
                jsonObject.get("electricityEmmFactor").getAsDouble(),
                jsonObject.get("natGasPrice").getAsDouble(),
                jsonObject.get("livingSpace").getAsInt()
        );

        return settingsDetails;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(double gasPrice) {
        this.gasPrice = gasPrice;
    }

    public double getElectricityPrice() {
        return electricityPrice;
    }

    public void setElectricityPrice(double electricityPrice) {
        this.electricityPrice = electricityPrice;
    }

    public double getElectricityemmfactor() {
        return electricityemmfactor;
    }

    public void setElectricityemmfactor(double electricityemmfactor) {
        this.electricityemmfactor = electricityemmfactor;
    }

    public double getNatgasPrice() {
        return natgasPrice;
    }

    public void setNatgasPrice(double natgasPrice) {
        this.natgasPrice = natgasPrice;
    }

    public double getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(double livingSpace) {
        this.livingSpace = livingSpace;
    }

    /**
     * Equals method that checks whether another object is the same as this.
     *
     * @param other The object to be checked
     * @return True if other object is the same
     */
    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }

        if (other instanceof SettingsDetails) {

            SettingsDetails that = (SettingsDetails) other;

            return that.getUserId() == this.getUserId()
                    && that.getElectricityPrice() == this.getElectricityPrice()
                    && that.getElectricityemmfactor() == this.getElectricityemmfactor()
                    && that.getGasPrice() == this.getGasPrice()
                    && that.getNatgasPrice() == this.getNatgasPrice()
                    && that.getLivingSpace() == this.getLivingSpace();

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

        jsonMap.put("userId", getUserId());
        jsonMap.put("gasPrice", getGasPrice());
        jsonMap.put("electricityPrice", getElectricityPrice());
        jsonMap.put("electricityEmmFactor", getElectricityemmfactor());
        jsonMap.put("natGasPrice", getNatgasPrice());
        jsonMap.put("livingSpace", getLivingSpace());

        String message = gson.toJson(jsonMap);
        System.out.println(message);

        return message;
    }
}
