package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.AchievementDetails;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

public class AchievementProgressConnectionTest {

    AchievementDetails achievementDetails;

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

        achievementDetails = new AchievementDetails(1, "name", "description", 100);

        try {
            when(httpRequest.sendPost(achievementDetails.stringify()))
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
                                return new HttpData(400, "100");
                            }
                            if(App.getUsername().equals("NoUsername")){
                                return new HttpData(400, "100");
                            }
                            if(App.getUsername().equals("user")){
                                return new HttpData(200, "100");
                            }
                            return null;
                        }
                    });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test_setAchievementProgress_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.setAchievementProgress(1, 100));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_setAchievementProgress_null() {

        App.setUsername("");

        assertFalse(connection.setAchievementProgress(1, 100));
    }


    @Test
    public void test_setAchievementProgress_valid() {
        App.setUsername("user");
        assertTrue(connection.setAchievementProgress(1, 100));
    }

    @Test
    public void test_setAchievementProgress_invalid() {
        App.setUsername("NoUsername");
        assertFalse(connection.setAchievementProgress(1, 100));
    }

    @Test
    public void test_getAchievementProgress_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl(realURL + "/wrongpath");

        assertNotEquals(0, connection.getAchievementProgress(1));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_getAchievementProgress_null() {

        App.setUsername("");

        assertNotEquals(100, connection.getAchievementProgress(1));
    }


    @Test
    public void test_getAchievementProgress_valid() {
        App.setUsername("user");
        assertEquals(100, connection.getAchievementProgress(1));
    }

    @Test
    public void test_getAchievementProgress_invalid() {
        App.setUsername("NoUsername");
        assertNotEquals(100, connection.getAchievementProgress(1));
    }

}
