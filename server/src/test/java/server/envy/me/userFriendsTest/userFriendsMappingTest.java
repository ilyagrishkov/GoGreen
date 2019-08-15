package server.envy.me.userFriendsTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Assert;
import org.junit.Test;
import server.envy.me.userfriends.UserFriendsClass;
import server.envy.me.userfriends.UserFriendsMapping;

public class userFriendsMappingTest {

    UserFriendsMapping testFriend = new UserFriendsMapping();

    @Test
    public void testConstructor() {
        UserFriendsMapping testFriend = new UserFriendsMapping(112,113);
        Assert.assertNotEquals(testFriend, new UserFriendsClass(113,114));
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