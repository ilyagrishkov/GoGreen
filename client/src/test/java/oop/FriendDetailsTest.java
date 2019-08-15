package oop;

import oop.details.FriendDetails;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FriendDetailsTest {

    FriendDetails friendDetails;

    @Before
    public void initialize() {

        ArrayList<String> arr = new ArrayList<>();
        arr.add("friend1");
        arr.add("friend2");

        friendDetails = new FriendDetails("user", arr);
    }

    @Test
    public void test_getuser() {
        assertEquals("user", friendDetails.getUsername());
    }

    @Test
    public void parseFailTest() {

        assertNotEquals(new FriendDetails("user", FriendDetails.parse("{\"friends\":[\"ayy\"]}")),
                friendDetails);
    }

    @Test
    public void parseValidTest() {

        assertEquals(FriendDetails.parse(
                "{\"friends\":[\"friend1\",\"friend2\"]}"), friendDetails.getFriends());
    }

    @Test
    public void isFriendFailTest() {

        assertFalse(friendDetails.isFriend("user"));
    }

    @Test
    public void isFriendValidTest() {

        assertTrue(friendDetails.isFriend("friend1"));
    }
}
