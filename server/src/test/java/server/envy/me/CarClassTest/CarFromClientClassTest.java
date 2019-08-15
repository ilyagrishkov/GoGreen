package server.envy.me.CarClassTest;

import org.junit.Test;
import server.envy.me.car.CarFromClient;

import static org.junit.Assert.assertEquals;

public class CarFromClientClassTest {
    CarFromClient carToTest = new CarFromClient("Electric", 125);

    @Test
    public void getType() {
        assertEquals("Electric", carToTest.getType());
    }

    @Test
    public void setType() {
        carToTest.setType("Hybrid");
        assertEquals("Hybrid", carToTest.getType());
    }

    @Test
    public void getMpg() {
        assertEquals(125, carToTest.getMpg());
    }

    @Test
    public void setMpg() {
        carToTest.setMpg(150);
        assertEquals(150, carToTest.getMpg());
    }
}
