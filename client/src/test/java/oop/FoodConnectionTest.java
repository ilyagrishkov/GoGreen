package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.FoodDetails;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;


public class FoodConnectionTest {

    FoodDetails foodDetails;

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

        json.put("id", "343");
        json.put("type", "Vegetarian");
        json.put("local", false);
        json.put("cooked", false);
        json.put("meat", 0.0);
        json.put("fish", 0.0);
        json.put("meatAlt", 0.0);
        json.put("eggs", 0.0);
        json.put("grains", 67.0);
        json.put("dairy", 0.0);
        json.put("veggies", 33.0);
        json.put("snacks", 0.0);
        json.put("date", "2019-03-17T00:00:00.000+0000");

        list.add(json);

        json = new HashMap();

        json.put("food", list);

        String jstring = gson.toJson(json);


        foodDetails = new FoodDetails.Builder()
                .setBeef(0)
                .setFish(0)
                .setOther(0)
                .setPoultry(0)
                .setGrains(40)
                .setDairy(30)
                .setFruits(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegetarian")
                .build();

        try {
            when(httpRequest.sendPost(foodDetails.stringify()))
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
    public void test_receiveFood_null() {
        assertNull(connection.receiveFood(""));
    }


    @Test
    public void test_receiveFood_valid() {
        App.setUsername("user");
        assertNotNull(connection.receiveFood("user"));
    }

    @Test
    public void test_receiveFood_invalid() {
        App.setUsername("NoUsername");
        assertNull(connection.receiveFood("NoUsername"));
    }

    @Test
    public void test_receiveFood_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveFood("user"));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_send_food_invalidUsername() {
        App.setUsername("");


        assertFalse(connection.sendFood(foodDetails));
    }

    @Test
    public void test_send_food_validFalseUsername() {
        App.setUsername("ValidUsername");


        assertFalse(connection.sendFood(foodDetails));
    }

    @Test
    public void test_send_food_validCorrectUsername() {
        App.setUsername("user");


        assertTrue(connection.sendFood(foodDetails));
    }

    @Test
    public void test_send_food_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.sendFood(foodDetails));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_removeFood_null() {
        assertFalse(connection.removeFood(-1));
    }


    @Test
    public void test_removeFoodFood_valid() {
        App.setUsername("user");
        assertTrue(connection.removeFood(1));
    }

    @Test
    public void test_removeFood_invalid() {

        App.setUsername("");
        assertFalse(connection.removeFood(0));
    }

    @Test
    public void test_remove_food_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.removeFood(1));

        Connection.setUrl(realURL);
    }

}
