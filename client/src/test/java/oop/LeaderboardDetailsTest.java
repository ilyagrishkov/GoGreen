package oop;

import static org.junit.Assert.*;
import oop.details.LeaderboardDetails;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LeaderboardDetailsTest {

    private LeaderboardDetails leaderboardDetails;

    @Before
    public void initialize() {

        leaderboardDetails = new LeaderboardDetails("name", 10);
    }

    @Test
    public void parseLeaderboardValidTest() {

        ArrayList<LeaderboardDetails> ldarr = LeaderboardDetails.parse("[{\"username\":\"name\",\"score\":10}]");

        assertTrue(ldarr.get(0).getName().equals(leaderboardDetails.getName())
                && leaderboardDetails.getScore() == ldarr.get(0).getScore()
        );
    }

    @Test
    public void parseLeaderboardInvalidTest() {

        ArrayList<LeaderboardDetails> ldarr = LeaderboardDetails.parse("[{\"username\":\"Name\",\"score\":10}]");

        assertFalse(ldarr.get(0).getName().equals(leaderboardDetails.getName())
                && leaderboardDetails.getScore() == ldarr.get(0).getScore()
        );
    }

}
