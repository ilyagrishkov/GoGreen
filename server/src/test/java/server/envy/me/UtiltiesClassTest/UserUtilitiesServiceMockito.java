package server.envy.me.UtiltiesClassTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.envy.me.userutilities.UserUtilitiesClass;
import server.envy.me.userutilities.UserUtilitiesRepository;
import server.envy.me.userutilities.UserUtilitiesService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserUtilitiesServiceMockito {
    @InjectMocks
    UserUtilitiesService userUtilitiesService;

    @Mock
    UserUtilitiesRepository userUtilitiesRepository;

    List<UserUtilitiesClass> listToReturn = new ArrayList<>();

    public static int ID = 1;

    @Test
    public void testGetUtilitiesEmpty() {
        when(userUtilitiesRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(userUtilitiesService.getUtilitiesByUserId(ID).isEmpty());
    }

    UserUtilitiesClass class1 = new UserUtilitiesClass(2,1);
    UserUtilitiesClass class2 = new UserUtilitiesClass(1,2);

    @Test
    public void testGetUtilitiesNotEmpty() {
        listToReturn.add(class1);
        when(userUtilitiesRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(userUtilitiesService.getUtilitiesByUserId(ID).isEmpty());
    }

    @Test
    public void testGetUtilitiesFindOne() {
        listToReturn.add(class1);
        listToReturn.add(class2);
        when(userUtilitiesRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(!userUtilitiesService.getUtilitiesByUserId(ID).isEmpty());
    }

    UserUtilitiesClass class3 = new UserUtilitiesClass(1,3);

    @Test
    public void testGetUtilitiesFindMultiple() {
        listToReturn.add(class1);
        listToReturn.add(class2);
        listToReturn.add(class3);
        when(userUtilitiesRepository.findAll()).thenReturn(listToReturn);
        Assert.assertTrue(userUtilitiesService.getUtilitiesByUserId(ID).size() ==  2);
    }
}
