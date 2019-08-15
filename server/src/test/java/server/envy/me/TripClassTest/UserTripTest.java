package server.envy.me.TripClassTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.envy.me.usertrips.UserTripClass;
import server.envy.me.usertrips.UserTripRepository;
import server.envy.me.usertrips.UserTripService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserTripTest {

    @InjectMocks
    UserTripService userTripService;

    @Mock
    UserTripRepository userTripRepository;

    /**
     * Test list returned is empty
     */
    @Test
    public void testGetListEmpty() {
        List<UserTripClass> listToReturn = new ArrayList<>();
        when(userTripService.getListTripByUser(1)).thenReturn(listToReturn);
        Assert.assertTrue(userTripService.getListTripByUser(1).isEmpty());
    }

    /**
     * Test list returned is NOT EMPTY
     */
    @Test
    public void testGetListNotEmpty() {
        List<UserTripClass> listToReturn = new ArrayList<>();
        listToReturn.add(new UserTripClass(1,1));
        when(userTripService.getListTripByUser(1)).thenReturn(listToReturn);
        Assert.assertTrue(!userTripService.getListTripByUser(1).isEmpty());
    }

    /**
     * Test only the correct UserTripClass is retrieved.
     */
    @Test
    public void testGetListDifferentUsers() {
        List<UserTripClass> listToReturn = new ArrayList<>();
        listToReturn.add(new UserTripClass(1,1));
        listToReturn.add(new UserTripClass(2,2));
        when(userTripService.getListTripByUser(1)).thenReturn(listToReturn);
        Assert.assertTrue(userTripService.getListTripByUser(1).size() == 1);
    }
}
