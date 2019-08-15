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
import java.util.logging.Level;

public class TripDetails {

    private String type;

    private String source;

    private String destination;

    private Double distance;

    private String date;

    private Integer carid;

    /**
     * Constructor for creating a new Trip.
     *
     * @param type        - type of transportation
     * @param source      - from
     * @param destination - to
     * @param distance    - total distance
     */
    public TripDetails(String type, String source, String destination, Double distance,
                       String date, Integer carid) {

        this.type = type;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.carid = carid;
        this.date = date;
    }

    /**
     * Parse a json containing trip data.
     *
     * @param json JSON to parse
     * @return An ArrayList containing the data
     */
    public static ArrayList<TripDetails> parse(String json) {


        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("trips");

        ArrayList<TripDetails> tripDetails = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            Integer carid = null;

            try {

                carid = jsonArray.get(i).getAsJsonObject().get("carId").getAsInt();

            } catch (NullPointerException e) {

                carid = null;

            } catch (UnsupportedOperationException e) {

                carid = null;
            }

            TripDetails tripDetails1 = new TripDetails(
                    jsonArray.get(i).getAsJsonObject().get("type").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("source").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("destination").getAsString(),
                    jsonArray.get(i).getAsJsonObject().get("distance").getAsDouble(),
                    jsonArray.get(i).getAsJsonObject().get("date").getAsString(),
                    carid
            );

            String date = jsonArray.get(i).getAsJsonObject().get("date").getAsString();

            tripDetails1.setDate(date.substring(0, date.length() - 9));

            tripDetails.add(tripDetails1);
        }

        return tripDetails;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCarid() {
        return carid;
    }

    public void setCarid(Integer carid) {
        this.carid = carid;
    }

    /**
     * UnJSONifing the string.
     *
     * @return returns a String of the data
     */
    public String stringify() {

        Gson gson = new Gson();

        Map jsonMap = new HashMap();

        jsonMap.put("type", type);
        jsonMap.put("source", source);
        jsonMap.put("destination", destination);
        jsonMap.put("distance", distance);
        jsonMap.put("carId", carid);
        jsonMap.put("date", date);

        String message = gson.toJson(jsonMap);
        System.out.println(message);
        App.log(Level.INFO, RegistrationDetails.class.getName(), "Creating JSON - Trip");

        return message;
    }

    @Override
    public String toString() {
        return "TripDetails{"
               + "type='" + type + '\''
               + ", source='" + source + '\''
               + ", destination='" + destination + '\''
               + ", distance=" + distance
               + ", date='" + date + '\''
               + ", carId=" + carid.intValue()
               + '}';
    }
}
