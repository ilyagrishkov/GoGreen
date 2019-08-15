package oop;

import oop.connection.Connection;
import oop.connection.HttpRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class HttpRequestTest {
    private HttpRequest httpRequest;

    @Before
    public void init() {
        try {
            httpRequest = new HttpRequest("http://env-y-me.herokuapp.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getObj() {
        assertNotNull(httpRequest.getObj());
    }

    @Test
    public void testConnection() {

        Connection.setUrl("http://env-y-me.herokuapp.com/wrongpath");
        Connection conn = new Connection();
        Connection.setUrl("http://env-y-me.herokuapp.com");

    }
}
