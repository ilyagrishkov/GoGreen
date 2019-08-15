package oop;

import oop.details.UtilitiesDetails;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilitiesDetailsTest {
    UtilitiesDetails initS;

    @Before
    public void initi(){
        initS = new UtilitiesDetails(10,10,10,10,10, true);
    }

    @Test
    public void setDate() {
        initS.setDate("newdate");
        assertEquals(initS.getDate(), "newdate");
    }

    @Test
    public void setNatGas() {
        initS.setNaturalgas(20);
        assertEquals(initS.getNaturalgas(), 20);
    }

    @Test
    public void setsolarPanel(){
        initS.setSolarPanels(false);
        assertFalse(initS.isSolarPanels());
    }

    @Test
    public void setroomtemp() {
        initS.setRoomTemp(20);
        assertEquals(initS.getRoomTemp(), 20);
    }

    @Test
    public void setelec() {
        initS.setElectricityBill(20);
        assertEquals(initS.getElectricityBill(), 20);
    }

    @Test
    public void setclean() {
        initS.setCleanEnergyPercentage(20);
        assertEquals(initS.getCleanEnergyPercentage(), 20);
    }

    @Test
    public void setwater() {
        initS.setWatersewage(20);
        assertEquals(initS.getWatersewage(), 20);
    }

    @Test
    public void stringifyTest() {

        assertEquals(initS.stringify(), "{\"electricityBill\":10,\"roomTemp\":10,\"cleanEnergyPercentage\":10,\"watersewage\":10,\"solarPanels\":true,\"natgas\":10}");
    }

    @Test
    public void failedStringifyTest() {

        assertNotEquals(initS.stringify(), "{\"electricityBill\":15,\"roomTemp\":10,\"cleanEnergyPercentage\":10,\"watersewage\":10,\"solarPanels\":true,\"natgas\":10}");
    }

    @Test
    public void parseTest() {

        assertEquals(initS.getElectricityBill(), UtilitiesDetails.parse("{\"utilities\":[{\"electricity\":10,\"perCleanEnergy\":10,\"water\":10,\"gas\":10,\"datetime\":\"2000-05-12\"}]}").get(0).getElectricityBill());
    }

    @Test
    public void failedParseTest() {

        assertNotEquals(initS.getElectricityBill(), UtilitiesDetails.parse("{\"utilities\":[{\"electricity\":15,\"perCleanEnergy\":10,\"water\":10,\"gas\":10,\"datetime\":\"2000-05-12\"}]}").get(0).getElectricityBill());
    }
}
