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

public class ScoreConnectionTest {

    int score;

    @Mock
    HttpRequest httpRequest;

    @InjectMocks
    Connection connection;

    /**
     * Initialize loginDetail and RegistrationDetail objects.
     */
    @Before
    public void initialize() {

        score = 100;

        MockitoAnnotations.initMocks(this);

        try {

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
    public void test_receiveScore_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertEquals(-1, connection.receiveScore("user"));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_receiveScore_null() {

        App.setUsername("");

        assertEquals(-1, connection.receiveScore(""));
    }


    @Test
    public void test_receiveScore_valid() {
        App.setUsername("user");
        assertEquals(score, connection.receiveScore("user"));
    }

    @Test
    public void test_receiveScore_invalid() {
        App.setUsername("NoUsername");
        assertEquals(-1, connection.receiveScore("NoUsername"));
    }

}
