package server.envy.me.BadgesClassTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.envy.me.userbadges.UserBadgesClass;
import server.envy.me.userbadges.UserBadgesRepository;
import server.envy.me.userbadges.UserBadgesService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserBadgesServiceMockito {
    @InjectMocks
    UserBadgesService userBadgesService;

    @Mock
    UserBadgesRepository userBadgesRepository;

    List<UserBadgesClass> listToReturn = new ArrayList<>();

    public static int ID = 1;

    @Test
    public void testGetAchievementsByUserIdEmpty() {
        when(userBadgesRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(userBadgesService.getBadgesIdByUserId(1).isEmpty());
    }

    @Test
    public void testGetAchievementsByUserIdNotEmptyFindAll() {
        listToReturn.add(new UserBadgesClass(2,1));
        when(userBadgesRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(userBadgesService.getBadgesIdByUserId(1).isEmpty());
    }

    @Test
    public void testGetAchievementsByUserIdNotEmptyFindOne() {
        listToReturn.add(new UserBadgesClass(2,1));
        listToReturn.add(new UserBadgesClass(1,1));
        when(userBadgesRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(!userBadgesService.getBadgesIdByUserId(1).isEmpty());
    }

    @Test
    public void testGetAchievementsByUserIdFindMultiple() {
        listToReturn.add(new UserBadgesClass(1,1));
        listToReturn.add(new UserBadgesClass(1,2));
        when(userBadgesRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(userBadgesService.getBadgesIdByUserId(1).size() == 2);
    }
}
