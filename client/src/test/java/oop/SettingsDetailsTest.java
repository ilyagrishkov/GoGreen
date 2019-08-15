package oop;

import oop.details.SettingsDetails;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class SettingsDetailsTest {

    private SettingsDetails settingsDetails;

    @Before
    public void initialize() {

        settingsDetails = new SettingsDetails(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void constructorTest() {

        assertEquals(settingsDetails, new SettingsDetails(1, 2, 3, 4, 5, 6));
    }

    @Test
    public void constructorNoIdTest() {

        settingsDetails = new SettingsDetails(1, 2, 3, 4, 5);
        assertEquals(settingsDetails, new SettingsDetails(1, 2, 3, 4, 5));
    }

    @Test
    public void equalsNullTest() {

        assertNotEquals(settingsDetails, null);
    }

    @Test
    public void equalsOtherTest() {

        assertNotEquals(settingsDetails,"lolz");
    }

    @Test
    public void equalsNotSameIdTest() {

        assertNotEquals(settingsDetails, new SettingsDetails(2, 2, 3, 4, 5, 6));
    }

    @Test
    public void equalsNotSameGasTest() {

        assertNotEquals(settingsDetails, new SettingsDetails(1, 5, 3, 4, 5, 6));
    }

    @Test
    public void equalsNotSameElectricityTest() {

        assertNotEquals(settingsDetails, new SettingsDetails(1, 2, 6, 4, 5, 6));
    }

    @Test
    public void equalsNotSameElectricityEmmTest() {

        assertNotEquals(settingsDetails, new SettingsDetails(1, 2, 3, 6, 5, 6));
    }

    @Test
    public void equalsNotSameNatGasTest() {

        assertNotEquals(settingsDetails, new SettingsDetails(1, 2, 3, 4, 6, 6));
    }

    @Test
    public void equalsNotSameLivingTest() {

        assertNotEquals(settingsDetails, new SettingsDetails(2, 2, 3, 4, 5, 200));
    }

    @Test
    public void equalsSameLivingTest() {
        assertNotEquals(settingsDetails, new SettingsDetails(1, 4, 3, 4, 5, 6));
    }


    @Test
    public void equalsSameTest() {

        assertEquals(settingsDetails, settingsDetails);
    }

    @Test
    public void stringifyTest() {

        assertEquals(settingsDetails.stringify(), "{\"electricityEmmFactor\":4.0,\"electricityPrice\":3.0,\"natGasPrice\":5.0,\"livingSpace\":6.0,\"userId\":1,\"gasPrice\":2.0}");
    }

    @Test
    public void failedStringifyTest() {

        assertNotEquals(settingsDetails.stringify(), "{\"electricityEmmFactor\":4.0,\"electricityPrice\":3.0,\"natGasPrice\":5.0,\"livingSpace\":14.0,\"userId\":1,\"gasPrice\":2.0}");
    }

    @Test
    public void parseTest() {

        assertEquals(settingsDetails, SettingsDetails.parse("{\"electricityEmmFactor\":4.0,\"electricityPrice\":3.0,\"natGasPrice\":5.0,\"livingSpace\":6.0,\"userId\":1,\"gasPrice\":2.0}"));
    }

    @Test
    public void failedParseTest() {

        assertNotEquals(settingsDetails, SettingsDetails.parse("{\"electricityEmmFactor\":14.0,\"electricityPrice\":3.0,\"natGasPrice\":5.0,\"livingSpace\":6.0,\"userId\":1,\"gasPrice\":2.0}"));
    }

}
