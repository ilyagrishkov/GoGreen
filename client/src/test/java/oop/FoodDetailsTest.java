package oop;

import oop.details.FoodDetails;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class FoodDetailsTest {

    private FoodDetails foodDetails;

    @Before
    public void initialize() {
        foodDetails = new FoodDetails.Builder()
                .setBeef(0)
                .setFish(0)
                .setOther(0)
                .setPoultry(0)
                .setGrains(40)
                .setDairy(30)
                .setFruits(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegetarian")
                .build();
    }

    @Test
    public void constructorTestValues(){
        assertTrue(foodDetails.getType().equals("Vegetarian") && foodDetails.getBeef() == 0 && foodDetails.getFish() == 0 && !foodDetails.isHomecooked());
        assertFalse(foodDetails.getType().equals("Vegan") && foodDetails.getBeef() == 10 && foodDetails.getFish() == 10 && !foodDetails.isHomecooked());

        foodDetails = new FoodDetails.Builder()
                .setBeef(10)
                .setFish(10)
                .setOther(0)
                .setPoultry(0)
                .setGrains(20)
                .setDairy(0)
                .setFruits(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegan")
                .build();

        assertFalse(foodDetails.getType().equals("Vegetarian") && foodDetails.getBeef() == 0 && foodDetails.getFish() == 0 && !foodDetails.isHomecooked());
        assertTrue(foodDetails.getType().equals("Vegan") && foodDetails.getBeef() == 10 && foodDetails.getFish() == 10 && !foodDetails.isHomecooked());
    }

    @Test
    public void setToZeroPositiveTest_Fruits(){
        foodDetails.setToZero("fruits");

        assertTrue(foodDetails.getFruits() == 0);
    }

    @Test
    public void setToZeroPositiveTest_Beef(){
        foodDetails = new FoodDetails.Builder()
                .setBeef(10)
                .setFish(10)
                .setOther(0)
                .setPoultry(0)
                .setGrains(20)
                .setDairy(0)
                .setFruits(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegan")
                .build();

        foodDetails.setToZero("beef");

        assertTrue(foodDetails.getBeef() == 0);
    }

    @Test
    public void setToZeroPositiveTest_Fish(){
        foodDetails = new FoodDetails.Builder()
                .setBeef(10)
                .setFish(10)
                .setOther(0)
                .setPoultry(0)
                .setGrains(20)
                .setDairy(0)
                .setFruits(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegan")
                .build();

        foodDetails.setToZero("fish");

        assertTrue(foodDetails.getFish() == 0);
    }

    @Test
    public void setToZeroPositiveTest_Other(){
        foodDetails = new FoodDetails.Builder()
                .setBeef(10)
                .setFish(10)
                .setOther(10)
                .setPoultry(0)
                .setGrains(20)
                .setDairy(0)
                .setFruits(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegan")
                .build();

        foodDetails.setToZero("other");

        assertTrue(foodDetails.getOther() == 0);
    }

    @Test
    public void setToZeroPositiveTest_poultry(){
        foodDetails = new FoodDetails.Builder()
                .setBeef(10)
                .setFish(10)
                .setOther(0)
                .setPoultry(10)
                .setGrains(20)
                .setDairy(0)
                .setFruits(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegan")
                .build();

        foodDetails.setToZero("poultry");

        assertTrue(foodDetails.getPoultry() == 0);
    }

    @Test
    public void setToZeroPositiveTest_Grains(){
        foodDetails = new FoodDetails.Builder()
                .setBeef(10)
                .setFish(10)
                .setOther(0)
                .setPoultry(0)
                .setGrains(20)
                .setDairy(0)
                .setFruits(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegan")
                .build();

        foodDetails.setToZero("grains");

        assertTrue(foodDetails.getGrains() == 0);
    }

    @Test
    public void setToZeroPositiveTest_Dairy(){
        foodDetails = new FoodDetails.Builder()
                .setBeef(10)
                .setFish(10)
                .setOther(0)
                .setPoultry(0)
                .setGrains(20)
                .setDairy(10)
                .setFruits(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegan")
                .build();

        foodDetails.setToZero("dairy");

        assertTrue(foodDetails.getDairy() == 0);
    }

    @Test
    public void setToZeroPositiveTest_Snacks(){
        foodDetails = new FoodDetails.Builder()
                .setBeef(10)
                .setFish(10)
                .setOther(0)
                .setPoultry(0)
                .setGrains(20)
                .setDairy(0)
                .setFruits(10)
                .setSnacks(10)
                .setLocal(false)
                .setHomecooked(false)
                .setType("Vegan")
                .build();

        foodDetails.setToZero("snacks");

        assertTrue(foodDetails.getSnacks() == 0);
    }

    @Test
    public void setToZeroNegativeTest(){
        foodDetails.setToZero("randomText");

        assertTrue(foodDetails.getFruits() == 10 && foodDetails.getGrains() == 40 && foodDetails.getBeef() == 0);
    }

    @Test
    public void correctDateTest(){
        LocalDate timeRightNow = LocalDate.now();

        assertEquals(foodDetails.getDate(), timeRightNow.toString());
    }

    @Test
    public void wrongDateTest(){
        String randomDate = "2017-09-18";

        assertNotEquals(foodDetails.getDate(), randomDate);
    }

    @Test
    public void positiveJsonifying(){
        String correctJson = "{\"date\":\"" + LocalDate.now().toString() + "\",\"eggs\":0,\"meatAlt\":0,\"cooked\":false,\"fish\":0,\"meat\":0,\"type\":\"Vegetarian\",\"veggies\":10,\"snacks\":0,\"dairy\":30,\"local\":false,\"grains\":40}";

        assertEquals(correctJson, foodDetails.stringify());
    }

    @Test
    public void negativeJsonifying(){
        //changed date
        String wrongJson = "{\"date\":\"2017-03-15\",\"eggs\":0,\"meatAlt\":0,\"cooked\":false,\"fish\":0,\"meat\":0,\"type\":\"Vegetarian\",\"veggies\":30,\"snacks\":10,\"dairy\":20,\"local\":false,\"grains\":40}";

        assertNotEquals(wrongJson, foodDetails.stringify());
    }
}
