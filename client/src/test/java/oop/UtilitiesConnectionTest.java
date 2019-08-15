package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.TripDetails;
import oop.details.UtilitiesDetails;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

public class UtilitiesConnectionTest {

    UtilitiesDetails utilitiesDetails;

    String jstring;

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

        json.put("gas", 10);
        json.put("water", 20);
        json.put("electricity", 30);
        json.put("perCleanEnergy", 40);
        json.put("datetime", LocalDate.now().toString());

        jstring = "{\"utilities\":[" + gson.toJson(json) + "]}";

        utilitiesDetails = new UtilitiesDetails(10, 20, 30, 40, 50, true);

        String jstring2 = utilitiesDetails.stringify();

        try {
            when(httpRequest.sendPost(jstring2))
                    .thenAnswer(new Answer<Integer>() {
                        @Override
                        public Integer answer(InvocationOnMock invocation) throws Throwable {
                            if(App.getUsername().equals("")){
                                return 400;
                            }
                            if(App.getUsername().equals("ValidUsername")){
                                return 400;
                            }
                            if(App.getUsername().equals("user")){
                                return 200;
                            }
                            return 400;
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
    public void test_send_utilities_invalidUsername() {
        App.setUsername("");


        assertFalse(connection.sendUtilities(utilitiesDetails));
    }

    @Test
    public void test_registerUser_wrong() {

        App.setUsername("user");

        boolean actual = connection.sendUtilities(utilitiesDetails);

        assertTrue(actual);
    }

    @Test
    public void test_send_utilities_validFalseUsername() {
        App.setUsername("ValidUsername");

        assertFalse(connection.sendUtilities(utilitiesDetails));
    }

    @Test
    public void test_send_utilities_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.sendUtilities(utilitiesDetails));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_receiveUtilities_null() {

        App.setUsername("");

        assertNull(connection.receiveUtilities(""));
    }

    @Test
    public void test_receiveUtilities_valid() {

        App.setUsername("user");

        assertNotNull(connection.receiveUtilities("user"));
    }

    @Test
    public void test_receiveUtilities_invalid() {
        App.setUsername("NoUsername");
        assertNull(connection.receiveUtilities("NoUsername"));
    }

    @Test
    public void test_receiveUtilities_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveUtilities("user"));

        Connection.setUrl(realURL);
    }
}
