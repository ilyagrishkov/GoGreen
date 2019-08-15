package server.envy.me.userLoginTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import server.envy.me.login.LoginClass;

public class LoginClassTest {

    LoginClass testClass = new LoginClass(14,"Paolino","senzaPass");

    @Test
    public void getId() {
        assertEquals(14, testClass.getId());
    }

    @Test
    public void setId() {
        testClass.setId(15);
        assertEquals(15, testClass.getId());
    }

    @Test
    public void getUsername() {
        assertEquals("Paolino", testClass.getUsername());
    }

    @Test
    public void setUsername() {
        testClass.setUsername("Paul");
        assertEquals("Paul", testClass.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("senzaPass", testClass.getPassword());
    }

    @Test
    public void setPassword() {
        testClass.setPassword("newPass");
        assertEquals("newPass", testClass.getPassword());
    }

    @Test
    public void getAll() {
        String toCheck = "AUTHORIZATION: Username: Paolino, Password: senzaPass";
        assertEquals(toCheck, testClass.getAll());
    }
}
