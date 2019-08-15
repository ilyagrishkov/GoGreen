package oop;

import oop.details.BadgeDetails;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BadgeDetailsTest {

    BadgeDetails badgeDetails;

    @Before
    public void initialize() {
        badgeDetails = new BadgeDetails(1, "name", "description");
    }

    @Test
    public void parseIdRightTest() {

        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);

        assertEquals(arr, BadgeDetails.parseId("{\"badges\":[{\"id\":1,\"userid\":0}]}"));
    }

    @Test
    public void parseIdWrongTest() {

        assertNotEquals(-1, BadgeDetails.parseId("{\"badgeid\":1,\"userid\":0}"));
    }

    @Test
    public void parseIdNullTest() {

        assertNotNull(BadgeDetails.parseId("{\"badgeid\":1,\"userid\":0}"));
    }

    @Test
    public void parseBadgesRightTest() {

        BadgeDetails other = BadgeDetails.parseBadges("{\"badges\":[{\"id\":1,\"name\":\"name\",\"description\":\"description\"}]}").get(0);


        assertTrue(badgeDetails.getId() == other.getId()
        && badgeDetails.getName().equals(other.getName())
        && badgeDetails.getDescription().equals(other.getDescription()));
    }

    @Test
    public void parseBadgesWrongTest() {

        BadgeDetails other = BadgeDetails.parseBadges("{\"badges\":[{\"id\":2,\"name\":\"name\",\"description\":\"description\"}]}").get(0);


        assertFalse(badgeDetails.getId() == other.getId()
                && badgeDetails.getName().equals(other.getName())
                && badgeDetails.getDescription().equals(other.getDescription()));    }

    @Test
    public void parseBadgesNullTest() {

        assertNotNull(BadgeDetails.parseBadges("{\"badges\":[]}"));
    }

    @Test
    public void stringifyRightTest() {

        assertEquals(badgeDetails.stringify(), "{\"badgeid\":1,\"userid\":0}");
    }

    @Test
    public void stringifyWrongTest() {

        assertNotEquals(badgeDetails.stringify(), "{\"badgeid\":2,\"userid\":0}");
    }

}
