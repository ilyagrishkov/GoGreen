package server.envy.me.userFoodTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import server.envy.me.userfood.UserFoodRepository;
import server.envy.me.userfood.UserFoodService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAutoConfiguration
public class userFoodServiceTest {

    @Autowired
    UserFoodRepository userFoodRepository;

    @Autowired
    UserFoodService userFoodService;

    @Test
    public void testGetListFoodByUserId(){
        int userId = 99;
        Assert.assertNotNull(userFoodService.getListFoodByUserId(userId));
    }

}
