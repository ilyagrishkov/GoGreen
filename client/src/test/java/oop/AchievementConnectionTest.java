package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.AchievementDetails;
import oop.details.CarDetails;
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

public class AchievementConnectionTest {

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

        Gson gson = new Gson();
        Map json = new HashMap();

        json.put("id", 1);
        json.put("name", "name");
        json.put("description", "description");
        json.put("goal", 100);

        String jstring = "{\"achievements\":[" + gson.toJson(json) + "]}";

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
    public void test_receiveAchievements_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveAchievements());

        Connection.setUrl(realURL);
    }

    @Test
    public void test_receiveAchievements_null() {

        App.setUsername("");

        assertNull(connection.receiveAchievements());
    }


    @Test
    public void test_receiveAchievements_valid() {
        App.setUsername("user");
        assertNotNull(connection.receiveAchievements());
    }

    @Test
    public void test_receiveAchievements_invalid() {
        App.setUsername("NoUsername");
        assertNull(connection.receiveAchievements());
    }

}
