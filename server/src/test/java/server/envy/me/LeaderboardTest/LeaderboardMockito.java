package server.envy.me.LeaderboardTest;

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
import server.envy.me.leaderboard.LeaderboardClass;
import server.envy.me.leaderboard.LeaderboardRepository;
import server.envy.me.leaderboard.LeaderboardService;
import server.envy.me.leaderboard.LeaderboardToClient;
import server.envy.me.login.LoginClass;
import server.envy.me.login.LoginRepository;
import server.envy.me.response.ResponseClass;
import server.envy.me.userachievements.UserAchievementsRepository;
import server.envy.me.userfriends.UserFriendsService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class LeaderboardMockito {

    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @InjectMocks
    RestControlClass restController;

    @Mock
    LeaderboardService leaderboardService;

    @Mock
    LeaderboardRepository leaderboardRepository;

    @Mock
    LoginRepository loginRepository;

    @Mock
    UserAchievementsRepository userAchievementsRepository;

    @Mock
    UserFriendsService userFriendsService;

    private static final String invalidUsername = "invalid";
    private static final String validUsername = "valid";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    @Test
    public void testUsernameNull() throws Exception {
        when(loginRepository.findByUsername(invalidUsername)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUsername + "/getFriendsLeaderboard").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<LeaderboardClass> fromServer = om.readValue(resultContent, new TypeReference<List<LeaderboardClass>>(){});
        Assert.assertTrue(fromServer.isEmpty());
    }

    LoginClass loginClass = new LoginClass("user", "pass");
    List<Integer> listFriends = new ArrayList<>();
    List<LeaderboardClass> leaderboard = new ArrayList<>();
    LoginClass test1 = new LoginClass("user1", "nopass");
    LoginClass test2 = new LoginClass("user2", "nopass");
    LeaderboardClass user1 = new LeaderboardClass(1,10);
    LeaderboardClass user2 = new LeaderboardClass(2,5);
    LeaderboardClass user3 = new LeaderboardClass(3,25);

    @Test
    public void testValidUsernameWithNoFriends() throws Exception {
        leaderboard.add(user1);
        test1.setId(1);
        loginClass.setId(1);
        when(leaderboardRepository.findAll()).thenReturn(leaderboard);
        when(loginRepository.findByUsername(validUsername)).thenReturn(loginClass);
        when(userFriendsService.getListFriendsId(1)).thenReturn(listFriends);
        when(loginRepository.findById(1)).thenReturn(test1);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/getFriendsLeaderboard").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<LeaderboardClass> fromServer = om.readValue(resultContent, new TypeReference<List<LeaderboardToClient>>(){});
        Assert.assertTrue(fromServer.isEmpty());
    }

    @Test
    public void testValidUsernameWithFriends() throws Exception {
        leaderboard.add(user1);
        test1.setId(1);
        loginClass.setId(1);
        listFriends.add(1);
        when(loginRepository.findById(1)).thenReturn(test1);
        when(leaderboardRepository.findAll()).thenReturn(leaderboard);
        when(loginRepository.findByUsername(validUsername)).thenReturn(loginClass);
        when(userFriendsService.getListFriendsId(1)).thenReturn(listFriends);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/getFriendsLeaderboard").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<LeaderboardClass> fromServer = om.readValue(resultContent, new TypeReference<List<LeaderboardToClient>>(){});
        Assert.assertTrue(!fromServer.isEmpty());
    }

    @Test
    public void testValidUsernameWithFriendsCheckPositive() throws Exception {
        test1.setId(1);
        test2.setId(2);
        user2.setScore(15);
        leaderboard.add(user1);
        leaderboard.add(user2);
        leaderboard.add(user3);
        loginClass.setId(1);
        listFriends.add(2);
        when(loginRepository.findById(1)).thenReturn(test1);
        when(loginRepository.findById(2)).thenReturn(test2);
        when(leaderboardRepository.findAll()).thenReturn(leaderboard);
        when(loginRepository.findByUsername(validUsername)).thenReturn(loginClass);
        when(userFriendsService.getListFriendsId(1)).thenReturn(listFriends);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/getFriendsLeaderboard").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<LeaderboardClass> fromServer = om.readValue(resultContent, new TypeReference<List<LeaderboardToClient>>(){});
        Assert.assertTrue(!fromServer.isEmpty());
    }

    @Test
    public void testGetEmptyLeaderboard() throws Exception {
        when(leaderboardRepository.findAll()).thenReturn(leaderboard);
        MvcResult result = mockMvc.perform(get("/getLeaderboard").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<LeaderboardClass> fromServer = om.readValue(resultContent, new TypeReference<List<LeaderboardClass>>(){});
        Assert.assertTrue(fromServer.isEmpty());
    }

    @Test
    public void testGetLeaderboardNegativeComparator() throws Exception {
        leaderboard.add(user1);
        leaderboard.add(user2);
        leaderboard.add(user3);
        when(leaderboardRepository.findAll()).thenReturn(leaderboard);
        MvcResult result = mockMvc.perform(get("/getLeaderboard").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<LeaderboardClass> fromServer = om.readValue(resultContent, new TypeReference<List<LeaderboardClass>>(){});
        Assert.assertTrue(!fromServer.isEmpty());
    }

    @Test
    public void testGetLeaderboardPositiveComparator() throws Exception {
        user2.setScore(15);
        leaderboard.add(user1);
        leaderboard.add(user2);
        leaderboard.add(user3);
        when(leaderboardRepository.findAll()).thenReturn(leaderboard);
        MvcResult result = mockMvc.perform(get("/getLeaderboard").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<LeaderboardClass> fromServer = om.readValue(resultContent, new TypeReference<List<LeaderboardClass>>(){});
        Assert.assertTrue(!fromServer.isEmpty());
    }

    @Test
    public void testUpdateLeaderboardInvalidUser() throws Exception {
        when(loginRepository.findByUsername(invalidUsername)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUsername + "/" + 114 + "/updateLeaderboard").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass fromServer = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(fromServer.getPayload().contains("Incorrect username"));
    }

    @Test
    public void testUpdateValidUser() throws Exception {
        when(loginRepository.findByUsername(validUsername)).thenReturn(loginClass);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/" + 114 + "/updateLeaderboard").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass fromServer = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(fromServer.getPayload().contains("OK"));
    }

    @Test
    public void testGetScoreNullUser() throws Exception {
        when(loginRepository.findByUsername(invalidUsername)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUsername + "/getScore").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Integer fromServer = om.readValue(resultContent, Integer.class);
        Assert.assertTrue(fromServer.intValue() == 0);
    }

    @Test
    public void testGetScoreUserNotNull() throws Exception {
        loginClass.setId(1);
        when(loginRepository.findByUsername(validUsername)).thenReturn(loginClass);
        when(leaderboardRepository.findById(1)).thenReturn(user1);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/getScore").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Integer fromServer = om.readValue(resultContent, Integer.class);
        Assert.assertTrue(fromServer.intValue() == 10);
    }
}
