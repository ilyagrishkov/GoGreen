package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.TripDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

public class TripConnectionTest {

    TripDetails tripDetails;

    @Mock
    HttpRequest httpRequest;

    @InjectMocks
    Connection connection;

    /**
     * Initialize loginDetail and RegistrationDetail objects.
     */
    @Before
    public void initialize() {

        MockitoAnnotations.initMocks(this);

        Gson gson = new Gson();
        Map json = new HashMap();

        json.put("type", "Car");
        json.put("source", "Berlin");
        json.put("destination", "Munich");
        json.put("distance", 400.0);
        json.put("date", LocalDate.now().toString());
        json.put("carid", 1607);

        String jstring = "{trips:[" + gson.toJson(json) + "]}";

        tripDetails = new TripDetails("Car", "Berlin", "Munich", 400.0, LocalDate.now().toString(), 1607);

        try {
            when(httpRequest.sendPost(tripDetails.stringify()))
                    .thenAnswer(new Answer<HttpData>() {
                        @Override
                        public HttpData answer(InvocationOnMock invocation) throws Throwable {
                            if(App.getUsername().equals("")){
                                return new HttpData(400, null);
                            }
                            if(App.getUsername().equals("ValidUsername")){
                                return new HttpData(400, null);
                            }
                            if(App.getUsername().equals("user")){
                                return new HttpData(200, null);
                            }
                            return new HttpData(400, null);
                        }
                    });

            when(httpRequest.getObj()).thenCallRealMethod();
            doCallRealMethod().when(httpRequest).changeUrl(any());

            when(httpRequest.sendGet())
                    .thenAnswer(new Answer<HttpData>() {
                        @Override
                        public HttpData answer(InvocationOnMock invocation) throws Throwable {
                            if(App.getUsername().equals("")){
                                return new HttpData(400, null);
                            }
                            if(App.getUsername().equals("NoUsername")){
                                return new HttpData(400, null);
                            }
                            if(App.getUsername().equals("user")){
                                return new HttpData(200, jstring);
                            }
                            return null;
                        }
                    });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test_receiveTrip_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveTrips("user"));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_receiveTrip_null() {

        App.setUsername("");

        assertNull(connection.receiveTrips(""));
    }


    @Test
    public void test_receiveTrip_valid() {
        App.setUsername("user");
        assertNotNull(connection.receiveTrips("user"));
    }

    @Test
    public void test_receiveTrip_invalid() {
        App.setUsername("NoUsername");
        assertNull(connection.receiveTrips("NoUsername"));
    }

    @Test
    public void test_send_trip_invalidUsername() {
        App.setUsername("");


        assertFalse(connection.sendTrip(tripDetails));
    }

    @Test
    public void test_send_trip_validFalseUsername() {
        App.setUsername("ValidUsername");


        assertFalse(connection.sendTrip(tripDetails));
    }

    @Test
    public void test_send_trip_validCorrectUsername() {
        App.setUsername("user");


        assertTrue(connection.sendTrip(tripDetails));
    }

    @Test
    public void test_send_trip_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.sendTrip(tripDetails));

        Connection.setUrl(realURL);
    }

}
