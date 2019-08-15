package oop;


import oop.details.TripDetails;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class TripDetailsTest {

    TripDetails tripDetails;

    @Before
    public void initialize() {

        tripDetails = new TripDetails("Car", "Berlin", "Munich", 400.0, LocalDate.now().toString(), 1607);
    }

    @Test
    public void ConstructorTest() {

        assertEquals(tripDetails.toString(), new TripDetails("Car", "Berlin", "Munich", 400.0, LocalDate.now().toString(), 1607).toString());
    }

//    @Test
//    public void stringifyTest() {
//
//        tripDetails.setDate("2019-04-12");
//
//        assertEquals(tripDetails.stringify(), "{\"date\":\"2019-04-12\",\"distance\":400.0,\"destination\":\"Munich\",\"source\":\"Berlin\",\"type\":\"Car\",\"carid\":1607}");
//    }

    @Test
    public void stringifyFalseTest() {

        assertNotEquals(tripDetails.stringify(), "{\"date\":\"2019-03-31\",\"distance\":500.0,\"destination\":\"Munich\",\"source\":\"Berlin\",\"type\":\"Car\",\"carid\":1607}");
    }

    @Test
    public void parseTest() {

        ArrayList<TripDetails> arr = new ArrayList<>();
        TripDetails tripDetails = new TripDetails("Car", "Berlin", "Munich", 400.0, LocalDate.now().toString(), 1607);
        arr.add(tripDetails);

        assertNotEquals(TripDetails.parse("{trips:[{\"date\":\"" + LocalDate.now().toString() + "\",\"distance\":400.0,\"destination\":\"Munich\",\"source\":\"Berlin\",\"type\":\"Car\",\"carId\":1607}]}").toString(), arr.toString());
    }

    @Test
    public void parseFalseTest() {

        ArrayList<TripDetails> arr = new ArrayList<>();
        arr.add(new TripDetails("Car", "Berlin", "Munich", 400.0, LocalDate.now().toString(), 1607));

        assertNotEquals(TripDetails.parse("{trips:[{\"date\":\"2019-03-31\",\"distance\":500.0,\"destination\":\"Munich\",\"source\":\"Berlin\",\"type\":\"Car\",\"carId\":1607}]}").toString(), arr.toString());
    }
}
