package server.api.connection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransportationApi {

    private static String API_KEY = "AIzaSyAgx2-0iDurGK5yLlXlu4wDYyDW5PYrBX4";

    /**
     * Gets the driving distance between.
     * @param addrOne the first address
     * @param addrTwo the second adress.
     * @return distance
     * @throws ApiException Api Exception
     * @throws InterruptedException Interrupted Exception
     * @throws IOException I/O Exception
     * @throws NullPointerException Null pointer Exception
     */
    public double getDriveDist(String addrOne, String addrTwo)
            throws ApiException, InterruptedException, IOException, NullPointerException {

        //set up key
        GeoApiContext distCalcer = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
        DistanceMatrix result = req.origins(addrOne)
                .destinations(addrTwo)
                .mode(TravelMode.DRIVING)
                .language("en-US")
                .await();

        double distApart = result.rows[0].elements[0].distance.inMeters / 1000.0;

        return distApart;
    }

    /**
     * Gets the walking distance between.
     * @param addrOne the first address
     * @param addrTwo the second adress.
     * @return distance
     * @throws ApiException Api Exception
     * @throws InterruptedException Interrupted Exception
     * @throws IOException I/O Exception
     * @throws NullPointerException Null pointer Exception
     */
    public double getWalkDist(String addrOne, String addrTwo)
            throws ApiException, InterruptedException, IOException, NullPointerException {

        //set up key
        GeoApiContext distCalcer = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
        DistanceMatrix result = req.origins(addrOne)
                .destinations(addrTwo)
                .mode(TravelMode.WALKING)
                .language("en-US")
                .await();

        double distApart = result.rows[0].elements[0].distance.inMeters / 1000.0;

        return distApart;
    }

    /**
     * Gets the walking cycling between.
     * @param addrOne the first address
     * @param addrTwo the second adress.
     * @return distance
     * @throws ApiException Api Exception
     * @throws InterruptedException Interrupted Exception
     * @throws IOException I/O Exception
     * @throws NullPointerException Null pointer Exception
     */
    public double getCycleDist(String addrOne, String addrTwo)
            throws ApiException, InterruptedException, IOException, NullPointerException {

        //set up key
        GeoApiContext distCalcer = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
        DistanceMatrix result = req.origins(addrOne)
                .destinations(addrTwo)
                .mode(TravelMode.BICYCLING)
                .language("en-US")
                .await();

        double distApart = 0.0;

        try {
            distApart = result.rows[0].elements[0].distance.inMeters / 1000.0;

        } catch (NullPointerException e) {
            e.getMessage();
        }

        return distApart;
    }

    /**
     * Gets the transportation distance between.
     * @param addrOne the first address
     * @param addrTwo the second adress.
     * @return distance
     * @throws ApiException Api Exception
     * @throws InterruptedException Interrupted Exception
     * @throws IOException I/O Exception
     * @throws NullPointerException Null pointer Exception
     */
    public double getTransitDist(String addrOne, String addrTwo)
            throws ApiException, InterruptedException, IOException, NullPointerException {

        //set up key
        GeoApiContext distCalcer = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
        DistanceMatrix result = req.origins(addrOne)
                .destinations(addrTwo)
                .mode(TravelMode.TRANSIT)
                .language("en-US")
                .await();

        double distApart = result.rows[0].elements[0].distance.inMeters / 1000.0;

        return distApart;
    }

    /**
     * Gets the flight distance between.
     * @param origin the first address
     * @param destination the second adress.
     * @return distance
     */
    public double getFlightDist(String origin, String destination) {

        HttpResponse<JsonNode> response = null;
        try {

            origin = origin.replaceAll("\\s", "%20");
            destination = destination.replaceAll("\\s", "%20");

            response = Unirest.get("https://distanceto.p.rapidapi.com/get?car=false&foot=false&route=%5B%7B%22t%22%3A%22"
                    + origin + "%22%7D%2C%7B%22t%22%3A%22" + destination + "%22%7D%5D")
                    .header("X-RapidAPI-Key", "a645e9b330msh8a2e77a926c51dap15a8bfjsnb926780f90b9")
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }


        // Parse JSON response
        JsonElement jelement = new JsonParser().parse(response.getBody().toString());
        JsonObject jobject = jelement.getAsJsonObject();

        JsonArray stepsArray = jobject.getAsJsonArray("steps");
        JsonObject zero = stepsArray.get(0).getAsJsonObject();
        JsonObject jdistance = zero.get("distance").getAsJsonObject();
        JsonArray flights = jdistance.getAsJsonArray("flight");
        JsonObject zeroFlight = flights.get(0).getAsJsonObject();

        String time = zeroFlight.get("time").getAsString();
        String fdistance = zeroFlight.get("distance").getAsString();

        return Double.parseDouble(fdistance);
    }
}
