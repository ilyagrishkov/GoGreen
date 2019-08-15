package server.envy.me.CarClassTest;

import org.junit.Test;
import server.envy.me.car.CarClass;

import static org.junit.Assert.assertEquals;

public class CarClassTest {
    CarClass carToTest = new CarClass(99, "Electric", 125,"");

    @Test
    public void setId() {
        carToTest.setId(888);
        assertEquals(888, carToTest.getId());
    }

    @Test
    public void getId() {
        carToTest.setId(889);
        assertEquals(889, carToTest.getId());
    }

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
