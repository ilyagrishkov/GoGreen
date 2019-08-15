package server.api.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/** Class that creates and sends Http requests.
 *
 */
public class HttpRequest {

    /** Default user agent.
     *
     */
    private static final String USER_AGENT = "Mozilla/5.0";

    /** The URL where the HTTPRequest should be sent.
     *
     */
    private URL obj;

    /** The connection that is sent.
     *
     */
    private HttpURLConnection con;

    private String appId = "eae01f78";

    private String appKey = "2a2adbb4e8b58b4d94ed8d1c93f79356";

    /** Initializes the HTTP request and writes the headers.
     *
     * @param url the server URL to connect to
     * @throws IOException Covers MalformedURLException, IOException,
     *                     ProtocolException
     */
    public HttpRequest(final String url) throws IOException {
        obj = new URL(url);
        con = (HttpURLConnection) obj.openConnection();
    }

    /** Method that sends a GET request to web server.
     *
     * @return an HttpData containing the response message
     * @throws IOException in case something goes wrong
     */
    public HttpData sendGet() throws IOException {

        // Prepare connection for GET request
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("app_id", appId);
        con.setRequestProperty("app_key", appKey);

        System.out.println("\nSending 'GET' request to URL : " + con.getURL());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        int responseCode = con.getResponseCode();

        return new HttpData(responseCode, response.toString());


    }

}
