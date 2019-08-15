package server.envy.me.ExtraFeaturesTest;

import com.fasterxml.jackson.core.type.TypeReference;
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
import server.envy.me.login.LoginClass;
import server.envy.me.login.LoginRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class GetListUsernamesTest {
    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @InjectMocks
    RestControlClass restController;

    @Mock
    LoginRepository loginRepository;

    private static String start = "user";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    @Test
    public void getEmptyFindAll() throws Exception {
        when(loginRepository.findAll()).thenReturn(new ArrayList<>());
        MvcResult result = mockMvc.perform(get("/" + start + "/getUsernames").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<String> fromServer = om.readValue(resultContent, new TypeReference<List<String>>(){});
        Assert.assertTrue(fromServer.isEmpty());
    }

    LoginClass user1 = new LoginClass("userTest", "na");
    LoginClass user2 = new LoginClass("userOther", "na");
    LoginClass user3 = new LoginClass("userAgain", "na");
    LoginClass otherUser = new LoginClass("otherUser", "na");



    @Test
    public void getFindAllUsers() throws Exception {
        List<LoginClass> users = new ArrayList<>();
        users.add(user1);
        users.add(otherUser);
        users.add(user2);
        users.add(user3);
        when(loginRepository.findAll()).thenReturn(users);
        MvcResult result = mockMvc.perform(get("/" + start + "/getUsernames").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<String> fromServer = om.readValue(resultContent, new TypeReference<List<String>>(){});
        Assert.assertTrue(!fromServer.isEmpty());
    }

    LoginClass user4 = new LoginClass("userAgain", "na");
    LoginClass user5 = new LoginClass("userAgain", "na");
    LoginClass user6 = new LoginClass("userAgain", "na");
    LoginClass user7 = new LoginClass("userAgain", "na");
    LoginClass user8 = new LoginClass("userAgain", "na");
    LoginClass user9 = new LoginClass("userAgain", "na");
    LoginClass user10 = new LoginClass("userAgain", "na");
    LoginClass user11 = new LoginClass("userAgain", "na");

    @Test
    public void getOnly10() throws Exception {
        List<LoginClass> users = new ArrayList<>();
        users.add(user1);
        users.add(otherUser);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        users.add(user9);
        users.add(user10);
        users.add(user11);
        when(loginRepository.findAll()).thenReturn(users);
        MvcResult result = mockMvc.perform(get("/" + start + "/getUsernames").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<String> fromServer = om.readValue(resultContent, new TypeReference<List<String>>(){});
        Assert.assertTrue(fromServer.size() == 10);
    }
}
