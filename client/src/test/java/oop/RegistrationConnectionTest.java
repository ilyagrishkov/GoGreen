package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.RegistrationDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

public class RegistrationConnectionTest {

    RegistrationDetails registrationDetails;

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

        json.put("username", "user");
        json.put("firstname", "first");
        json.put("lastname", "last");
        json.put("dateofbirth", "01-01-1970");
        json.put("gender", "male");

        String jstring = gson.toJson(json);

        registrationDetails = new RegistrationDetails("user","first", "last", "01-01-1970", "male", "pass");
        try {
            when(httpRequest.sendPost(registrationDetails.stringify()))
                    .thenAnswer(new Answer<HttpData>() {
                        @Override
                        public HttpData answer(InvocationOnMock invocation) throws Throwable {
                            if(App.getUsername().equals("")){
                                return new HttpData(400, "");
                            }
                            if(App.getUsername().equals("ValidUsername")){
                                return new HttpData(400, "");
                            }
                            if(App.getUsername().equals("user")){
                                return new HttpData(200, "");
                            }
                            return new HttpData(400, "");
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
    public void test_receiveRegistration_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveDetails("user"));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_receiveRegistration_null() {

        App.setUsername("");

        assertNull(connection.receiveDetails("user"));
    }


    @Test
    public void test_receiveRegistration_valid() {
        App.setUsername("user");
        assertNotNull(connection.receiveDetails("user"));
    }

    @Test
    public void test_receiveRegistration_invalid() {
        App.setUsername("NoUsername");
        assertNull(connection.receiveDetails("user"));
    }

    @Test
    public void test_deleteRegistration_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.deleteRegistration(registrationDetails));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_deleteRegistration_null() {

        App.setUsername("");

        assertFalse(connection.deleteRegistration(registrationDetails));
    }


    @Test
    public void test_deleteRegistration_valid() {
        App.setUsername("user");
        assertTrue(connection.deleteRegistration(registrationDetails));
    }

    @Test
    public void test_deleteRegistration_invalid() {
        App.setUsername("NoUsername");
        assertFalse(connection.deleteRegistration(registrationDetails));
    }
}
