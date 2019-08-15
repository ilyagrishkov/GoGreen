package oop;

import oop.details.RegistrationDetails;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegistrationDetailsTest {
    private RegistrationDetails regis;

    @Before
    public void init(){
        regis = new RegistrationDetails("viktor", "Victor",
                "last","20/05/5000", "male", "securepassword");
    }

    @Test
    public void setScore() {
        regis.setScore(10);
        assertEquals(regis.getScore(), 10);
    }

    @Test
    public void test_getScore() {
        assertNotNull(regis.getScore());
    }

    @Test
    public void getUsername() {
        assertEquals("viktor", regis.getUsername());
    }

    @Test
    public void getFirstname() {
        assertEquals("Victor", regis.getFirstname());
    }

    @Test
    public void getLastname() {
        assertEquals("last", regis.getLastname());
    }

    @Test
    public void getdateofbirth() {
        assertEquals("20/05/5000", regis.getDateOfBirth());
    }
}
