package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
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

public class CarConnectionTest {

    CarDetails carDetails;

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

        json.put("userid", 2);
        json.put("id", 1);
        json.put("type", "Diesel Car");
        json.put("mpg", 4);
        json.put("name", "name");

        String jstring = "[" + gson.toJson(json) + "]";

        carDetails = new CarDetails(1, 2, "Diesel Car", 4, "name");

        try {
            when(httpRequest.sendPost(carDetails.stringify()))
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
    public void test_receiveCar_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveCars("user"));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_receiveCar_null() {

        App.setUsername("");

        assertNull(connection.receiveCars(""));
    }


    @Test
    public void test_receiveCar_valid() {
        App.setUsername("user");
        assertNotNull(connection.receiveCars("user"));
    }

    @Test
    public void test_receiveCar_invalid() {
        App.setUsername("NoUsername");
        assertNull(connection.receiveCars("NoUsername"));
    }

    @Test
    public void test_send_car_invalidUsername() {
        App.setUsername("");


        assertFalse(connection.sendCar(carDetails));
    }

    @Test
    public void test_send_car_validFalseUsername() {
        App.setUsername("ValidUsername");


        assertFalse(connection.sendCar(carDetails));
    }

    @Test
    public void test_send_car_validCorrectUsername() {
        App.setUsername("user");


        assertTrue(connection.sendCar(carDetails));
    }

    @Test
    public void test_send_car_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.sendCar(carDetails));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_delete_car_invalidUsername() {

        App.setUsername("");


        assertFalse(connection.deleteCar(1));
    }

    @Test
    public void test_delete_car_validFalseId() {


        assertFalse(connection.deleteCar(56));
    }

    @Test
    public void test_delete_car_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.deleteCar(1));

        Connection.setUrl(realURL);
    }
}
