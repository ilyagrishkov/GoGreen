package oop;

import oop.details.CarDetails;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CarDetailsTest {

    CarDetails carDetails;

    @Before
    public void initialize() {

        carDetails = new CarDetails(1, 2, "Diesel Car", 4, "name");
    }

    @Test
    public void Constructor4Test() {

        assertEquals(carDetails, new CarDetails(1, 2, "Diesel Car", 4, "name"));
    }

    @Test
    public void Constructor2Test() {

        carDetails = new CarDetails("Electric Car", 0, "name");
        assertEquals(carDetails, new CarDetails("Electric Car", 0, "name"));
    }

    @Test
    public void equalsNullTest() {

        assertNotEquals(carDetails, null);
    }

    @Test
    public void equalsOtherTest() {

        assertNotEquals(carDetails, "lolz");
    }

    @Test
    public void equalsNotSameCarIdTest() {

        assertNotEquals(carDetails, new CarDetails(2, 2, "Diesel Car", 4, "name"));
    }

    @Test
    public void equalsNotSameUserIdTest() {

        assertNotEquals(carDetails, new CarDetails(1, 3, "Diesel Car", 4, "name"));
    }

    @Test
    public void equalsNotSameTypeTest() {

        assertNotEquals(carDetails, new CarDetails(1, 2, "Electric Car", 4, "name"));
    }

    @Test
    public void equalsNotSameMpgTest() {

        assertNotEquals(carDetails, new CarDetails(1, 2, "Diesel Car", 5, "name"));
    }

    @Test
    public void equalsNotSameNameTest() {

        assertNotEquals(carDetails, new CarDetails(1, 2, "Diesel Car", 5, "Name"));
    }

    @Test
    public void equalsTrueTest() {

        assertEquals(carDetails, carDetails);
    }

    @Test
    public void stringifyTest() {

        assertEquals(carDetails.stringify(), "{\"mpg\":4.0,\"name\":\"name\",\"type\":\"Diesel Car\",\"userid\":2}");
    }

    @Test
    public void stringifyFalseTest() {

        assertNotEquals(carDetails.stringify(), "{\"mpg\":15,\"type\":\"Diesel Car\",\"userid\":2}");
    }

    @Test
    public void parseTest() {

        ArrayList<CarDetails> arr = new ArrayList<>();
        arr.add(new CarDetails(1, 2, "Diesel Car", 4, "name"));

        assertEquals(CarDetails.parse("[{\"mpg\":4.0,\"type\":\"Diesel Car\",\"userid\":2,\"id\":1,\"name\":\"name\"}]").get(0).getCarId(), arr.get(0).getCarId());
    }

    @Test
    public void parseFalseTest() {

        ArrayList<CarDetails> arr = new ArrayList<>();
        arr.add(new CarDetails(1, 2, "Diesel Car", 4, "name"));

        assertNotEquals(CarDetails.parse("[{\"mpg\":4.0,\"type\":\"Diesel Car\",\"userid\":2,\"id\":2,\"name\":\"name\"}]"), arr);
    }

}
