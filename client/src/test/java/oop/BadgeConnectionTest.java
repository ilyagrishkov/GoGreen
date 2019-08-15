package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.BadgeDetails;
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

public class BadgeConnectionTest {

    BadgeDetails badgeDetails;

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

        json.put("id", 1);
        json.put("name", "name");
        json.put("description", "description");

        String jstring = "{\"badges\":[" + gson.toJson(json) + "]}";

        badgeDetails = new BadgeDetails(1, "name", "description");

        try {
            when(httpRequest.sendPost(badgeDetails.stringify()))
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
    public void test_receiveBadges_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveBadges());

        Connection.setUrl(realURL);
    }

    @Test
    public void test_receiveBadges_null() {

        App.setUsername("");

        assertNull(connection.receiveBadges());
    }


    @Test
    public void test_receiveBadges_valid() {
        App.setUsername("user");
        assertNotNull(connection.receiveBadges());
    }

    @Test
    public void test_receiveBadges_invalid() {
        App.setUsername("NoUsername");
        assertNull(connection.receiveBadges());
    }

    @Test
    public void test_receiveUserBadges_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveUserBadges());

        Connection.setUrl(realURL);
    }

    @Test
    public void test_receiveUserBadges_null() {

        App.setUsername("");

        assertNull(connection.receiveUserBadges());
    }


    @Test
    public void test_receiveUserBadges_valid() {
        App.setUsername("user");
        assertNotNull(connection.receiveUserBadges());
    }

    @Test
    public void test_receiveUserBadges_invalid() {
        App.setUsername("NoUsername");
        assertNull(connection.receiveUserBadges());
    }

    @Test
    public void test_sendBadge_invalidUsername() {
        App.setUsername("");


        assertFalse(connection.sendBadge(badgeDetails));
    }

    @Test
    public void test_sendBadge_validFalseUsername() {
        App.setUsername("ValidUsername");


        assertFalse(connection.sendBadge(badgeDetails));
    }

    @Test
    public void test_sendBadge_validCorrectUsername() {
        App.setUsername("user");


        assertTrue(connection.sendBadge(badgeDetails));
    }

    @Test
    public void test_sendBadge_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.sendBadge(badgeDetails));

        Connection.setUrl(realURL);
    }
}
