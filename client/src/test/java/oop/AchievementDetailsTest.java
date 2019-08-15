package oop;

import oop.details.AchievementDetails;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AchievementDetailsTest {

    AchievementDetails achievementDetails;

    @Before
    public void initialize() {

        achievementDetails = new AchievementDetails(1, "name", "description", 100);
    }

    @Test
    public void stringifyTest() {

        assertEquals(achievementDetails.stringify(), "{\"name\":\"name\",\"description\":\"description\",\"id\":1}");
    }

    @Test
    public void parseAchievementValidTest() {

        AchievementDetails ach = AchievementDetails.parseAchievement("{\"name\":\"name\",\"description\":\"description\",\"id\":1,\"goal\":100}");

        assertTrue(achievementDetails.getId() == ach.getId()
        && achievementDetails.getDescription().equals(ach.getDescription())
        && achievementDetails.getName().equals(ach.getName()));
    }

    @Test
    public void parseAchievementInvalidTest() {

        AchievementDetails ach = AchievementDetails.parseAchievement("{\"name\":\"Name\",\"description\":\"description\",\"id\":1,\"goal\":100}");

        assertFalse(achievementDetails.getId() == ach.getId()
                && achievementDetails.getDescription().equals(ach.getDescription())
                && achievementDetails.getName().equals(ach.getName()));
    }

    @Test
    public void parseAchievementsValidTest() {

        ArrayList<AchievementDetails> ach = AchievementDetails.parseAchievements("{\"achievements\":[{\"name\":\"name\",\"description\":\"description\",\"id\":1,\"goal\":100}]}");

        assertTrue(achievementDetails.getId() == ach.get(0).getId()
                && achievementDetails.getDescription().equals(ach.get(0).getDescription())
                && achievementDetails.getName().equals(ach.get(0).getName()));
    }

    @Test
    public void parseAchievementsInvalidTest() {

        ArrayList<AchievementDetails> ach = AchievementDetails.parseAchievements("{\"achievements\":[{\"name\":\"Name\",\"description\":\"description\",\"id\":1,\"goal\":100}]}");

        assertFalse(achievementDetails.getId() == ach.get(0).getId()
                && achievementDetails.getDescription().equals(ach.get(0).getDescription())
                && achievementDetails.getName().equals(ach.get(0).getName()));
    }


}
