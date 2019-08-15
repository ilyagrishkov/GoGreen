package server.envy.me.LeaderboardTest;

import org.junit.Assert;
import org.junit.Test;
import server.envy.me.leaderboard.LeaderboardClass;

import java.util.Comparator;

public class ComparatorTest {
    Comparator<LeaderboardClass> comparator = new Comparator<LeaderboardClass>() {
        @Override
        public int compare(LeaderboardClass o1, LeaderboardClass o2) {
            if (o1.getScore() >= o2.getScore()) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    LeaderboardClass test1 = new LeaderboardClass();
    LeaderboardClass test2 = new LeaderboardClass();

    @Test
    public void testEqualOrGreater() {
        test1.setScore(10);
        test2.setScore(10);
        int result = comparator.compare(test1,test2);
        Assert.assertTrue(result == -1);
    }

    @Test
    public void testSmaller() {
        test1.setScore(7);
        test2.setScore(10);
        int result = comparator.compare(test1,test2);
        Assert.assertTrue(result == 1);
    }
}
