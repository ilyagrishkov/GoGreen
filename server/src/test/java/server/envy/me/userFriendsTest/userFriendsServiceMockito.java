package server.envy.me.userFriendsTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.envy.me.userfriends.UserFriendsClass;
import server.envy.me.userfriends.UserFriendsRepository;
import server.envy.me.userfriends.UserFriendsService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class userFriendsServiceMockito {

    @InjectMocks
    UserFriendsService userFriendsService;

    @Mock
    UserFriendsRepository userFriendsRepository;


    UserFriendsClass friendsToReturn = new UserFriendsClass(1,2);

    @Test
    public void testGetByInvalidInputs() {
        when(userFriendsRepository.findByUserId1AndUserId2(1,2)).thenReturn(null);
        Assert.assertNull(userFriendsService.getByUserId(1,2));
    }

    @Test
    public void testGetByValidInputs() {
        when(userFriendsRepository.findByUserId1AndUserId2(1,2)).thenReturn(friendsToReturn);
        Assert.assertNotNull(userFriendsService.getByUserId(1,2));
    }

    List<UserFriendsClass> listFriendsClass = new ArrayList<>();

    @Test
    public void testGetListFriends() {
        when(userFriendsRepository.findAll()).thenReturn(listFriendsClass);
        Assert.assertTrue(userFriendsService.getListFriendsId(1).isEmpty());
    }

    @Test
    public void testGetListOneFriend() {
        listFriendsClass.add(new UserFriendsClass(1,2));
        when(userFriendsRepository.findAll()).thenReturn(listFriendsClass);
        Assert.assertTrue(!userFriendsService.getListFriendsId(1).isEmpty());
    }

    @Test
    public void testGetListNoFriends() {
        listFriendsClass.add(new UserFriendsClass(1,2));
        when(userFriendsRepository.findAll()).thenReturn(listFriendsClass);
        Assert.assertTrue(userFriendsService.getListFriendsId(3).isEmpty());
    }

    @Test
    public void testGetListOneFriendUserId2() {
        listFriendsClass.add(new UserFriendsClass(1,2));
        when(userFriendsRepository.findAll()).thenReturn(listFriendsClass);
        Assert.assertTrue(!userFriendsService.getListFriendsId(2).isEmpty());
    }

}
