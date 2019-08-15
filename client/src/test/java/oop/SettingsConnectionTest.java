package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.SettingsDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

public class SettingsConnectionTest {

    SettingsDetails settingsDetails;

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
        List list = new ArrayList();
        Map json = new HashMap();

        json.put("userId", 1);
        json.put("gasPrice", 2);
        json.put("electricityPrice", 3);
        json.put("electricityEmmFactor",4);
        json.put("natGasPrice", 5);
        json.put("livingSpace", 6);

        list.add(json);

        String jstring = gson.toJson(json);


        settingsDetails = new SettingsDetails(1, 2, 3, 4, 5, 6);

        try {
            when(httpRequest.sendPost(settingsDetails.stringify()))
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
    public void test_receiveSettings_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveSettings("user"));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_receiveSettings_null() {

        App.setUsername("");

        assertNull(connection.receiveSettings(""));
    }


    @Test
    public void test_receiveSettings_valid() {
        App.setUsername("user");
        assertNotNull(connection.receiveSettings("user"));
    }

    @Test
    public void test_receiveSettings_invalid() {
        App.setUsername("NoUsername");
        assertNull(connection.receiveSettings("NoUsername"));
    }

    @Test
    public void test_send_settings_invalidUsername() {
        App.setUsername("");


        assertFalse(connection.sendSettings(settingsDetails));
    }

    @Test
    public void test_send_settings_validFalseUsername() {
        App.setUsername("ValidUsername");


        assertFalse(connection.sendSettings(settingsDetails));
    }

    @Test
    public void test_send_settings_validCorrectUsername() {
        App.setUsername("user");


        assertTrue(connection.sendSettings(settingsDetails));
    }

    @Test
    public void test_send_settings_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.sendSettings(settingsDetails));

        Connection.setUrl(realURL);
    }

}
