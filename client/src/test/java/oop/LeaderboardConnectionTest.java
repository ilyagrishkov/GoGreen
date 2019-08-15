package oop;

import com.google.gson.Gson;
import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.FriendDetails;
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
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

public class LeaderboardConnectionTest {

    FriendDetails friendDetails;

    @Mock
    HttpRequest httpRequest;

    @InjectMocks
    Connection connection;


    @Before
    public void initialize() {

        friendDetails = new FriendDetails("user", new ArrayList<>());

        Gson gson = new Gson();
        Map json = new HashMap();

        json.put("user", "user");
        json.put("friends", new ArrayList<>());

        String jstring = gson.toJson(json);


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
                                return new HttpData(200, jstring);
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
                                return new HttpData(200, "[{\"username\":\"user\",\"score\":200}]");
                            }
                            return new HttpData(400, "");
                        }
                    });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void receiveFriendLeaderboardNoUsernameTest() {

        App.setUsername("");

        assertNull(connection.receiveFriendLeaderboard("friend"));
    }

    @Test
    public void receiveFriendLeaderboardWrongFriendTest() {

        App.setUsername("InvalidFriend");

        assertNull(connection.receiveFriendLeaderboard("friend"));
    }

    @Test
    public void receiveFriendLeaderboardValidFriendTest() {

        App.setUsername("user");

        assertNotNull(connection.receiveFriendLeaderboard("friend"));
    }

    @Test
    public void receiveFriendsNoUsernameTest() {

        App.setUsername("");
        assertNull(connection.receiveFriends("friend"));
    }

    @Test
    public void receiveFriendsValidTest() {

        App.setUsername("");

        assertNull(connection.receiveFriends("friend"));
    }

    @Test
    public void receiveFriendsInvalidTest() {

        App.setUsername("");

        assertNull(connection.receiveFriends("friend"));
    }

}
