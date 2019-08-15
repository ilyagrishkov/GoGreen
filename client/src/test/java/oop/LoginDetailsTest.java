package oop;

import oop.details.LoginDetails;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;



public class LoginDetailsTest {

    LoginDetails loginDetails;

    @Before
    public void initialize() {

        loginDetails = new LoginDetails("test", "test");
    }

    @Test
    public void constructorTestNull() {

        loginDetails = new LoginDetails();
        assertTrue(loginDetails.getPassword() == null && loginDetails.getUsername() == null);
    }

    @Test
    public void constructorTestNotNull() {

        assertTrue(loginDetails.getUsername().equals("test")
                && loginDetails.getPassword().equals("test"));
    }

    @Test
    public void getUsernameTrue() {

        assertEquals(loginDetails.getUsername(), "test");
    }

    @Test
    public void getUsernameFalse() {

        assertNotEquals(loginDetails.getUsername(),"not_test");
    }

    @Test
    public void setUsernameTest() {

        loginDetails.setUsername("something");
        assertEquals(loginDetails.getUsername(), "something");
    }

    @Test
    public void getPasswordTrue() {

        assertEquals(loginDetails.getPassword(), "test");
    }

    @Test
    public void getPasswordFalse() {

        assertNotEquals(loginDetails.getPassword(), "not_test");
    }

    @Test
    public void setPasswordTest() {

        loginDetails.setPassword("password");
        assertEquals(loginDetails.getPassword(), "password");
    }

    @Test
    public void equalsTestFalse_user() {

        assertFalse(loginDetails.equals(new LoginDetails("","test")));
    }

    @Test
    public void equalsTestFalse_pass() {

        assertFalse(loginDetails.equals(new LoginDetails("test","")));
    }

    @Test
    public void equalsTestFalse_wrong_object() {

        assertFalse(loginDetails.equals(new Object()));
    }

    @Test
    public void equalsTestTrue() {

        assertTrue(loginDetails.equals(loginDetails));
    }

    @Test
    public void test_stringify() {

        assertEquals(loginDetails.stringify(),
                "{\"password\":\"9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08\""
                        + ",\"username\":\"test\"}");
    }
}
