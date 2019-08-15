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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class UserSearchConnectionTest {

    @Mock
    HttpRequest httpRequest;

    @InjectMocks
    Connection connection;

    @Before
    public void initialize() {

        MockitoAnnotations.initMocks(this);
        try {
            when(httpRequest.sendGet())
                    .thenAnswer(new Answer<HttpData>() {
                        @Override
                        public HttpData answer(InvocationOnMock invocation) throws Throwable {
                            if(App.getUsername().equals("")){
                                return new HttpData(400, null);
                            }
                            if(App.getUsername().equals("InvalidFriend")){
                                return new HttpData(400, null);
                            }
                            if(App.getUsername().equals("user")){
                                return new HttpData(200, "smth");
                            }
                            return new HttpData(400, null);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void receiveUsersNoUsernameTest() {

        App.setUsername("");

        assertNull(connection.receiveUsers("friend"));
    }

    @Test
    public void receiveUsersWrongFriendTest() {

        App.setUsername("InvalidFriend");

        assertNull(connection.receiveUsers("friend"));
    }

    @Test
    public void receiveUsersValidFriendTest() {

        App.setUsername("");

        assertNull(connection.receiveUsers("friend"));
    }

    @Test
    public void test_receiveUsers_IOException() {
        App.setUsername("user");

        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertNull(connection.receiveUsers("friend"));

        Connection.setUrl(realURL);
    }
}
