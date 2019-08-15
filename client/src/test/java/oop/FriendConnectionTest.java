package oop;

import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

public class FriendConnectionTest {

    @Mock
    HttpRequest httpRequest;

    @InjectMocks
    Connection connection;


    @Before
    public void initialize() {

        MockitoAnnotations.initMocks(this);

        try {
            when(httpRequest.sendPost("{\"friend\":\"friend\"}"))
                    .thenAnswer(new Answer<HttpData>() {
                        @Override
                        public HttpData answer(InvocationOnMock invocation) throws Throwable {
                            if(App.getUsername().equals("")){
                                return new HttpData(400, "");
                            }
                            if(App.getUsername().equals("InvalidFriend")){
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
                                return new HttpData(400, "");
                            }
                            if(App.getUsername().equals("InvalidFriend")){
                                return new HttpData(400, "");
                            }
                            if(App.getUsername().equals("user")){
                                return new HttpData(200, "");
                            }
                            return new HttpData(400, "");
                        }
                    });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addFriendNoUsernameTest() {

        App.setUsername("");

        assertFalse(connection.addFriend("friend"));
    }

    @Test
    public void addFriendWrongFriendTest() {

        App.setUsername("InvalidFriend");

        assertFalse(connection.addFriend("friend"));
    }

    @Test
    public void addFriendValidFriendTest() {

        App.setUsername("");

        assertFalse(connection.addFriend("friend"));
    }

    @Test
    public void test_addFriend_IOException() {
        App.setUsername("user");


        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.addFriend("friend"));

        Connection.setUrl(realURL);
    }

    @Test
    public void removeFriendNoUsernameTest() {

        App.setUsername("");
        assertFalse(connection.removeFriend("friend"));
    }

    @Test
    public void removeFriendValidTest() {

        App.setUsername("user");

        assertTrue(connection.removeFriend("friend"));
    }

    @Test
    public void removeFriendInvalidTest() {

        App.setUsername("InvalidFriend");

        assertFalse(connection.removeFriend("friend"));
    }

    @Test
    public void test_removeFriend_IOException() {
        App.setUsername("user");

        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.removeFriend("friend"));

        Connection.setUrl(realURL);
    }
}
