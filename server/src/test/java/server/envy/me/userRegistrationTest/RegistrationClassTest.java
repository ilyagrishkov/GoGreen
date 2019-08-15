package server.envy.me.userRegistrationTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.envy.me.registration.RegistrationClass;

import java.sql.Date;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationClassTest {

    RegistrationClass registrationTest = new RegistrationClass("giopowder", "Giorgio", "Colpani",
            new Date(1998,8,31), "male", "strongPass");

    @Test
    public void getId() {
        registrationTest.setId(27);
        assertEquals(27, registrationTest.getId());
    }

    @Test
    public void getUsername() {
        assertEquals("giopowder", registrationTest.getUsername());
    }

    @Test
    public void setUsername() {
        registrationTest.setUsername("giopowder6");
        assertEquals("giopowder6", registrationTest.getUsername());
    }

    @Test
    public void getFirstname() {
        assertEquals("Giorgio", registrationTest.getFirstname());
    }

    @Test
    public void setFirstname() {
        registrationTest.setFirstname("Alessandro");
        assertEquals("Alessandro", registrationTest.getFirstname());
    }

    @Test
    public void getLastname() {
        assertEquals("Colpani", registrationTest.getLastname());
    }

    @Test
    public void setLastname() {
        registrationTest.setLastname("Bettani");
        assertEquals("Bettani", registrationTest.getLastname());
    }

    @Test
    public void getDateofbirth() {

    }

    @Test
    public void setDateofbirth() {
        Date newDate = new Date(1998,8,24);
        registrationTest.setDateofbirth(newDate);
        assertEquals(new Date(1998, 8, 24),registrationTest.getDateofbirth());
    }

    @Test
    public void getGender() {
        assertEquals("male", registrationTest.getGender());
    }

}