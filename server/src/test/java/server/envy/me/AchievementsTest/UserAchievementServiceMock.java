package server.envy.me.AchievementsTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.envy.me.userachievements.UserAchievementsClass;
import server.envy.me.userachievements.UserAchievementsRepository;
import server.envy.me.userachievements.UserAchievementsService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserAchievementServiceMock {
    @InjectMocks
    UserAchievementsService userAchievementsService;

    @Mock
    UserAchievementsRepository userAchievementsRepository;

    List<UserAchievementsClass> listToReturn = new ArrayList<>();

    public static int ID = 1;

    @Test
    public void testGetAchievementsByUserIdEmpty() {
        when(userAchievementsRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(userAchievementsService.getAchievementsIdByUserId(1).isEmpty());
    }

    @Test
    public void testGetAchievementsByUserIdNotEmptyFindAll() {
        listToReturn.add(new UserAchievementsClass(2,1,10));
        when(userAchievementsRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(userAchievementsService.getAchievementsIdByUserId(1).isEmpty());
    }

    @Test
    public void testGetAchievementsByUserIdNotEmptyFindOne() {
        listToReturn.add(new UserAchievementsClass(2,1,10));
        listToReturn.add(new UserAchievementsClass(1,1,10));
        when(userAchievementsRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(!userAchievementsService.getAchievementsIdByUserId(1).isEmpty());
    }

    @Test
    public void testGetAchievementsByUserIdFindMultiple() {
        listToReturn.add(new UserAchievementsClass(1,1, 10));
        listToReturn.add(new UserAchievementsClass(1,2, 10));
        when(userAchievementsRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(userAchievementsService.getAchievementsIdByUserId(1).size() == 2);
    }
}
