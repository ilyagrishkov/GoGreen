package oop.connection;

import oop.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * Class that creates and sends Http requests.
 */
public class HttpRequest {

    /**
     * Default user agent.
     */
    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     * The URL where the HTTPRequest should be sent.
     */
    private URL obj;

    /**
     * The connection that is sent.
     */
    private HttpURLConnection con;

    /**
     * Initializes the HTTP request and writes the headers.
     *
     * @param url the server URL to connect to
     * @throws IOException Covers MalformedURLException, IOException,
     *                     ProtocolException
     */
    public HttpRequest(final String url) throws IOException {
        App.log(Level.INFO, HttpRequest.class.getName(), "Creating HttpRequest Object");

        obj = new URL(url);
        con = (HttpURLConnection) obj.openConnection();

    }

    /**
     * Changes URL object and establishes a new connection.
     *
     * @param obj - URL to change to
     * @throws MalformedURLException - if URL is wrong
     */
    public void changeUrl(URL obj) throws MalformedURLException {

        this.obj = new URL(obj.toString());
        try {
            this.con = (HttpURLConnection) obj.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public URL getObj() {
        return obj;
    }


    /**
     * Method that sends a POST request to web server.
     *
     * @param json the JSON data to be sent
     * @return the response code received from server
     * @throws IOException in case something goes wrong
     */
    public HttpData sendPost(final String json) throws IOException {

        // Prepare connection for POST request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type",
                "application/json; charset=UTF-8");

        App.log(Level.INFO, HttpRequest.class.getName(), "Sending POST Request method");

        // Add JSON to the HTTP request
        byte[] out = json.getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        con.setFixedLengthStreamingMode(length);
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            os.write(out);
            App.log(Level.INFO, HttpRequest.class.getName(), "Wrote to the output stream");
        }

        OutputStream os = con.getOutputStream();
        os.flush();
        os.close();

        // Return response code
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String input;
        StringBuffer response = new StringBuffer();
        while ((input = in.readLine()) != null) {
            response.append(input);
        }
        in.close();

        System.out.println("POST Response Code :: " + responseCode);
        App.log(Level.INFO, HttpRequest.class.getName(), "POST Response Code::" + responseCode);
        return new HttpData(responseCode, response.toString());
    }

    /**
     * Method that sends a GET request to web server.
     *
     * @return an HttpData containing the response message
     * @throws IOException in case something goes wrong
     */
    public HttpData sendGet() throws IOException {

        App.log(Level.INFO, HttpRequest.class.getName(), "Sending GET Request method");

        // Prepare connection for GET request
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);


        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + con.getURL());

        App.log(Level.INFO, HttpRequest.class.getName(), "GET Response Code::" + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        return new HttpData(responseCode, response.toString());


    }

}
