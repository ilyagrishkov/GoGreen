package oop.connection;

/**
 * Utility class for easier handling of GET requests.
 */
public class HttpData {

    private int responseCode;

    private String data;

    /**
     * Constructor for the HttpData class.
     *
     * @param responseCode the response code of the connection
     * @param data         the actual data returned by the GET request
     */
    public HttpData(int responseCode, String data) {

        this.setResponseCode(responseCode);
        this.setData(data);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
