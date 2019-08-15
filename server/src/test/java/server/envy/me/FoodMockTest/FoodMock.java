package server.envy.me.FoodMockTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.RestControlClass;
import server.envy.me.food.FoodClass;
import server.envy.me.food.FoodRepository;
import server.envy.me.food.FoodToClient;
import server.envy.me.login.LoginClass;
import server.envy.me.login.LoginRepository;
import server.envy.me.userfood.UserFoodClass;
import server.envy.me.userfood.UserFoodService;
import server.envy.me.utilities.UtilitiesToClient;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class FoodMock {
    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @InjectMocks
    RestControlClass restController;

    @Mock
    UserFoodService userFoodService;

    @Mock
    FoodRepository foodRepository;

    @Mock
    LoginRepository loginRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    @Test
    public void getAllFoodValidUser() throws Exception{
        FoodToClient toReturn = new FoodToClient();
        String validUsername = "user";
        List<UserFoodClass> listFoods = new ArrayList<>();
        listFoods.add(new UserFoodClass(1,1));
        when(loginRepository.findByUsername(validUsername)).thenReturn(new LoginClass(1,"user","pass"));
        when(userFoodService.getListFoodByUserId(1)).thenReturn(listFoods);
        when(foodRepository.findById(1)).thenReturn(new FoodClass());
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/getAllFood").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        FoodToClient fromServer = om.readValue(resultContent, FoodToClient.class);
        Assert.assertTrue(fromServer.getFood().size() == 1);

    }


}
