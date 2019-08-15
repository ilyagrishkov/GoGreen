package oop;

import oop.connection.Connection;
import oop.connection.HttpData;
import oop.connection.HttpRequest;
import oop.details.LoginDetails;
import oop.details.RegistrationDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RegistrationLoginTest {

    LoginDetails correctLoginDetails;
    LoginDetails wrongLoginDetails;
    RegistrationDetails correctRegistrationDetails;
    RegistrationDetails wrongRegistrationDetails;
    RegistrationDetails profileRegistrationDetails;

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

        correctLoginDetails = new LoginDetails("user", "pass");
        wrongLoginDetails = new LoginDetails("wrong_user", "wrong_pass");
        correctRegistrationDetails = new RegistrationDetails("testUser", "Name",
                "lastName", "1999-01-12", "male", "Pass1234$");
        wrongRegistrationDetails = new RegistrationDetails("wrongUser", "Name",
                "lastName", "1999-01-12", "male", "wrongPass");
        profileRegistrationDetails = new RegistrationDetails("profile", "first",
                "last", "1999-01-12", "male");

        try {
            when(httpRequest.sendPost(correctLoginDetails.stringify())).thenReturn(new HttpData(200, "{\"payload\":1}"));
            when(httpRequest.sendPost(correctRegistrationDetails.stringify())).thenReturn(new HttpData(200, null));

            when(httpRequest.sendPost(wrongLoginDetails.stringify())).thenReturn(new HttpData(400, null));
            when(httpRequest.sendPost(wrongRegistrationDetails.stringify())).thenReturn(new HttpData(400, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_constructor() {
        Connection connection = new Connection();

        assertNotNull(connection);
    }

    @Test
    public void test_registerUser_true() {

        boolean actual = connection.registerUser(correctRegistrationDetails);

        assertTrue(actual);
    }

    @Test
    public void test_registerUser_wrong() {

        boolean actual = connection.registerUser(wrongRegistrationDetails);

        assertFalse(actual);
    }

    @Test
    public void test_registerUser_IOException() {

        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.registerUser(correctRegistrationDetails));

        Connection.setUrl(realURL);
    }

    @Test
    public void test_authorizeUser_wrong() {
        assertThat(connection.authorizeUser(wrongLoginDetails), equalTo(-1));
    }

    @Test
    public void test_authorizeUser_wrong_null_user() {

        assertThat(connection.authorizeUser(new LoginDetails(null, "pass")),
                equalTo(-1));
    }

    @Test
    public void test_authorizeUser_wrong_null_pass() throws IOException {

        assertThat(connection.authorizeUser(new LoginDetails("user", null)),
                equalTo(-1));

    }

    @Test
    public void test_authorize_user_IOException() {
        String realURL = Connection.getUrl();
        Connection.setUrl("/wrongpath");

        assertFalse(connection.authorizeUser(correctLoginDetails) > 0);

        Connection.setUrl(realURL);
    }

    @Test
    public void test_authorize_user_correct() {

        int actual = connection.authorizeUser(correctLoginDetails);

        assertTrue(actual > 0);
    }

    @Test
    public void test_parse_score_wrong() {

        assertNotEquals(RegistrationDetails.parseScore("13"), 12);
    }

    @Test
    public void test_parse_score_right() {

        assertEquals(RegistrationDetails.parseScore("12"), 12);
    }


    @Test
    public void test_parse_users_wrong() {

        ArrayList<String> users = new ArrayList<>();
        users.add("friend1");
        users.add("friend2");

        assertNotEquals(RegistrationDetails.parseUsers("[\"friend1\"]"), users);
    }

    @Test
    public void test_parse_users_right() {

        ArrayList<String> users = new ArrayList<>();
        users.add("friend1");

        assertEquals(RegistrationDetails.parseUsers("[\"friend1\"]"), users);
    }

    @Test
    public void test_parse_wrong() {

        assertNotEquals(profileRegistrationDetails.getGender(), RegistrationDetails.parse("{\"username\":\"profile\",\"firstname\":\"first\","
                + "\"lastname\":\"last\",\"dateofbirth\":\"1999-01-12\",\"gender\":\"female\"}").getGender());
    }

    @Test
    public void test_parse_right() {

        assertEquals(profileRegistrationDetails.getGender(), RegistrationDetails.parse("{\"username\":\"profile\",\"firstname\":\"first\","
                + "\"lastname\":\"last\",\"dateofbirth\":\"1999-01-12\",\"gender\":\"male\"}").getGender());
    }


}
