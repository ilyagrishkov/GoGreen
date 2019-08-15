package server.envy.me.userFriendsTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import server.envy.me.userfriends.UserFriendsClass;

public class userFriendsClassTest {

    UserFriendsClass testFriend = new UserFriendsClass();

    @Test
    public void testConstructor() {
        UserFriendsClass testFriend = new UserFriendsClass(112,113);
        assertNotEquals(testFriend, new UserFriendsClass(113,114));
    }

    @Test
    public void getUserId1Positive() {
        testFriend.setUserId1(112);
        assertEquals(112, testFriend.getUserId1());
    }

    @Test
    public void getUserId1Negative() {
        testFriend.setUserId1(112);
        assertNotEquals(113, testFriend.getUserId1());
    }

    @Test
    public void setUserId1Positive() {
        testFriend.setUserId1(112);
        assertEquals(112, testFriend.getUserId1());
    }

    @Test
    public void setUserId1Negative() {
        testFriend.setUserId1(112);
        assertNotEquals(113, testFriend.getUserId1());
    }

    @Test
    public void getUserId2Positive() {
        testFriend.setUserId2(112);
        assertEquals(112, testFriend.getUserId2());
    }

    @Test
    public void getUserId2Negative() {
        testFriend.setUserId2(112);
        assertNotEquals(113, testFriend.getUserId2());
    }

    @Test
    public void setUserId2Positive() {
        testFriend.setUserId2(112);
        assertEquals(112, testFriend.getUserId2());
    }

    @Test
    public void setUserId2Negative() {
        testFriend.setUserId2(112);
        assertNotEquals(113, testFriend.getUserId2());
    }
}