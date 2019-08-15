package server.envy.me.LeaderboardTest;

import org.junit.Assert;
import org.junit.Test;
import server.envy.me.leaderboard.LeaderboardClass;

public class LeaderboardClassTest {

    LeaderboardClass test1 = new LeaderboardClass();
    LeaderboardClass test2 = new LeaderboardClass();


    @Test
    public void testGetPositive(){
        test1.setScore(10);
        test2.setScore(5);
        Assert.assertTrue(test1.compareTo(test2) == 1);
    }

    @Test
    public void testGetNegative(){
        test1.setScore(5);
        test2.setScore(10);
        Assert.assertTrue(test1.compareTo(test2) == 0);
    }

}
