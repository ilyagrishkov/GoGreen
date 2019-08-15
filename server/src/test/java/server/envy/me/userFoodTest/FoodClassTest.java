package server.envy.me.userFoodTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import server.envy.me.food.FoodClass;

import java.util.Date;

public class FoodClassTest {

    FoodClass foodTest = new FoodClass("Vegan", true, true, 10,20,30,20,20,0,
            0,0,new Date(2019,3,4));

    @Test
    public void setId() {
        foodTest.setId(889);
        assertEquals(889, foodTest.getId());
    }

    @Test
    public void getId() {
        foodTest.setId(889);
        assertEquals(889, foodTest.getId());
    }

    @Test
    public void getType() {
        foodTest.setType("Vegetarian");
        assertEquals("Vegetarian", foodTest.getType());
    }

    @Test
    public void setType() {
        assertEquals("Vegan", foodTest.getType());
    }

    @Test
    public void isLocal() {
        assertEquals(true, foodTest.isLocal());
    }

    @Test
    public void setLocal() {
        foodTest.setLocal(false);
        assertEquals(false, foodTest.isLocal());
    }

    @Test
    public void isCooked() {
        assertEquals(true, foodTest.isCooked());
    }

    @Test
    public void setCooked() {
        foodTest.setCooked(false);
        assertEquals(false, foodTest.isCooked());
    }

    @Test
    public void getMeat() {
        foodTest.setMeat(15.0);
        Assert.assertEquals(15.0, foodTest.getMeat(),0);
    }

    @Test
    public void setMeat() {
        Assert.assertEquals(10, foodTest.getMeat(),0);
    }

    @Test
    public void getFish() {
        foodTest.setMeat(15);
        Assert.assertEquals(15, foodTest.getMeat(),0);
    }

    @Test
    public void setFish() {
        Assert.assertEquals(20, foodTest.getFish(),0);
    }

    @Test
    public void getMeatAlt() {
        foodTest.setMeatAlt(15);
        Assert.assertEquals(15, foodTest.getMeatAlt(),0);
    }

    @Test
    public void setMeatAlt() {
        Assert.assertEquals(30, foodTest.getMeatAlt(),0);
    }

    @Test
    public void getEggs() {
        foodTest.setEggs(15);
        Assert.assertEquals(15, foodTest.getEggs(),0);
    }

    @Test
    public void setEggs() {
        Assert.assertEquals(20, foodTest.getEggs(),0);
    }

    @Test
    public void getGrains() {
        foodTest.setGrains(15);
        Assert.assertEquals(15, foodTest.getGrains(),0);
    }

    @Test
    public void setGrains() {
        Assert.assertEquals(20, foodTest.getGrains(),0);
    }

    @Test
    public void getDairy() {
        foodTest.setDairy(15);
        Assert.assertEquals(15, foodTest.getDairy(),0);
    }

    @Test
    public void setDairy() {
        Assert.assertEquals(0, foodTest.getDairy(),0);
    }

    @Test
    public void getVeggies() {
        foodTest.setVeggies(15);
        Assert.assertEquals(15, foodTest.getVeggies(),0);
    }

    @Test
    public void setVeggies() {
        Assert.assertEquals(0, foodTest.getVeggies(),0);
    }

    @Test
    public void getSnacks() {
        foodTest.setSnacks(15);
        Assert.assertEquals(15, foodTest.getSnacks(),0);
    }

    @Test
    public void setSnacks() {
        Assert.assertEquals(0, foodTest.getSnacks(),0);
    }

    @Test
    public void getDate() {
        foodTest.setDate(new Date(2019, 1,1));
        assertTrue(foodTest.getDate().equals(new Date(2019,1,1)));
    }

    @Test
    public void setDate() {
        assertTrue(new Date(2019,3,4).equals(foodTest.getDate()));
    }
}