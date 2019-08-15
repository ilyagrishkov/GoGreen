package oop.details;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtilitiesDetails {
    private int naturalgas;
    private int watersewage;
    private int electricityBill;
    private int cleanEnergyPercentage;
    private boolean solarPanels;
    private int roomTemp;
    private String date;


    /**
     * Constructor for Utilities Details.
     * @param naturalgas - natural gas price
     * @param watersewage - water price
     * @param electricityBill - electricity price
     * @param cleanEnergyPercentage - clean energy percentage
     * @param roomTemp - room temperature
     * @param solarPanels - true if solar panels are installed
     */
    public UtilitiesDetails(int naturalgas, int watersewage,
                            int electricityBill, int cleanEnergyPercentage,
                            int roomTemp, boolean solarPanels) {
        this.naturalgas = naturalgas;
        this.watersewage = watersewage;
        this.electricityBill = electricityBill;
        this.cleanEnergyPercentage = cleanEnergyPercentage;
        this.roomTemp = roomTemp;
        this.solarPanels = solarPanels;
    }

    /**
     * Jsonifies the Initial Survey Details to be sent to server.
     *
     * @return Initial Survey in json form.
     */
    public String stringify() {
        Gson gson = new Gson();

        Map jsonMap = new HashMap();

        jsonMap.put("natgas", getNaturalgas());
        jsonMap.put("watersewage", getWatersewage());
        jsonMap.put("electricityBill", getElectricityBill());
        jsonMap.put("cleanEnergyPercentage", getCleanEnergyPercentage());
        jsonMap.put("roomTemp", getRoomTemp());
        jsonMap.put("solarPanels", isSolarPanels());

        String message = gson.toJson(jsonMap);

        System.out.println(message);

        return message;
    }

    /**
     * Parses a JSON containing a list of utilities details.
     * @param json JSON containing utilities
     * @return Returns an arraylist containing UserDetails.
     */
    public static ArrayList<UtilitiesDetails> parse(String json) {

        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("utilities");

        ArrayList<UtilitiesDetails> utilitiesDetails = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            UtilitiesDetails utilitiesDetails1 = new UtilitiesDetails(
                    jsonArray.get(i).getAsJsonObject().get("gas").getAsInt(),
                    jsonArray.get(i).getAsJsonObject().get("water").getAsInt(),
                    jsonArray.get(i).getAsJsonObject().get("electricity").getAsInt(),
                    jsonArray.get(i).getAsJsonObject().get("perCleanEnergy").getAsInt(),
                    0,
                    false
            );

            String date = jsonArray.get(i).getAsJsonObject().get("datetime").getAsString();

            utilitiesDetails1.setDate(date.substring(0, date.length() - 9));

            utilitiesDetails.add(utilitiesDetails1);
        }


        return utilitiesDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNaturalgas() {
        return naturalgas;
    }

    public void setNaturalgas(int naturalgas) {
        this.naturalgas = naturalgas;
    }

    public int getWatersewage() {
        return watersewage;
    }

    public void setWatersewage(int watersewage) {
        this.watersewage = watersewage;
    }

    public int getElectricityBill() {
        return electricityBill;
    }

    public void setElectricityBill(int elecricityBill) {
        this.electricityBill = elecricityBill;
    }

    public int getCleanEnergyPercentage() {
        return cleanEnergyPercentage;
    }

    public void setCleanEnergyPercentage(int cleanEnergyPercentage) {
        this.cleanEnergyPercentage = cleanEnergyPercentage;
    }

    public boolean isSolarPanels() {
        return solarPanels;
    }

    public void setSolarPanels(boolean solarPanels) {
        this.solarPanels = solarPanels;
    }

    public int getRoomTemp() {
        return roomTemp;
    }

    public void setRoomTemp(int roomTemp) {
        this.roomTemp = roomTemp;
    }
}
