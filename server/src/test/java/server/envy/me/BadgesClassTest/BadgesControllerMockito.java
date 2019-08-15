package server.envy.me.BadgesClassTest;

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
import server.envy.me.badges.BadgesClass;
import server.envy.me.badges.BadgesRepository;
import server.envy.me.badges.BadgesService;
import server.envy.me.login.LoginClass;
import server.envy.me.login.LoginRepository;
import server.envy.me.response.ResponseClass;
import server.envy.me.userbadges.UserBadgesClass;
import server.envy.me.userbadges.UserBadgesRepository;
import server.envy.me.userbadges.UserBadgesService;
import server.envy.me.badges.BadgesToClient;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class BadgesControllerMockito {

    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @InjectMocks
    RestControlClass restController;

    @Mock
    BadgesRepository badgesRepository;

    @Mock
    BadgesService badgesService;

    @Mock
    UserBadgesService userBadgesService;

    @Mock
    UserBadgesRepository userBadgesRepository;

    @Mock
    LoginRepository loginRepository;

    private static int validId = 11;

    private static int invalidId = 12;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    @Test
    public void testGetBadgeInvalidId() throws Exception {
        when(badgesRepository.findById(invalidId)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidId + "/getBadge").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        BadgesClass fromServer = om.readValue(resultContent, BadgesClass.class);
        Assert.assertNull(fromServer.getDescription());
    }

    @Test
    public void testGetBadgeValidId() throws Exception {
        BadgesClass toReturn = new BadgesClass("name","description");
        when(badgesRepository.findById(validId)).thenReturn(toReturn);
        MvcResult result = mockMvc.perform(get("/" + validId + "/getBadge").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        BadgesClass fromServer = om.readValue(resultContent, BadgesClass.class);
        Assert.assertTrue(fromServer.getDescription().contains("description"));
    }

    @Test
    public void testGetAllInvalidUsername() throws Exception {
        String invalidUser = "invalid";
        when(loginRepository.findByUsername(invalidUser)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUser + "/getAllBadges").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        BadgesToClient fromServer = om.readValue(resultContent, BadgesToClient.class);
        Assert.assertTrue(fromServer.getBadges().isEmpty());
    }

    LoginClass validUser = new LoginClass("user", "test");

    @Test
    public void testGetAllValidUsername() throws Exception {
        List<UserBadgesClass> toReturn = new ArrayList<>();
        toReturn.add(new UserBadgesClass(99,11));
        String validUsername = "valid";
        validUser.setId(99);
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(userBadgesService.getBadgesIdByUserId(99)).thenReturn(toReturn);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/getAllBadges").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        BadgesToClient fromServer = om.readValue(resultContent, BadgesToClient.class);
        System.out.print(fromServer.getBadges().get(0));
        Assert.assertTrue(!fromServer.getBadges().isEmpty());
    }

    @Test
    public void testStoreInvalidUserId() throws Exception {
        int invalidUserId = 1;
        int validBadgeId = 1;
        UserBadgesClass testClass = new UserBadgesClass(invalidUserId, validBadgeId);
        when(badgesRepository.findById(validBadgeId)).thenReturn(new BadgesClass());
        when(loginRepository.findById(invalidUserId)).thenReturn(null);
        String jsonReq = om.writeValueAsString(testClass);
        MvcResult result = mockMvc.perform(post("/storeBadges")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Incorrect information"));
    }

    @Test
    public void testStoreInvalidBadgeId() throws Exception {
        int validUserId = 1;
        int invalidBadgeId = 1;
        UserBadgesClass testClass = new UserBadgesClass(validUserId, invalidBadgeId);
        when(badgesRepository.findById(invalidBadgeId)).thenReturn(null);
        when(loginRepository.findById(validUserId)).thenReturn(new LoginClass());
        String jsonReq = om.writeValueAsString(testClass);
        MvcResult result = mockMvc.perform(post("/storeBadges")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Incorrect information"));
    }

    @Test
    public void testStoreBothNull() throws Exception {
        int invalidUserId = 1;
        int invalidBadgeId = 1;
        UserBadgesClass testClass = new UserBadgesClass(invalidUserId, invalidBadgeId);
        when(badgesRepository.findById(invalidBadgeId)).thenReturn(null);
        when(loginRepository.findById(invalidUserId)).thenReturn(null);
        String jsonReq = om.writeValueAsString(testClass);
        MvcResult result = mockMvc.perform(post("/storeBadges")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Incorrect information"));
    }

    @Test
    public void testStoreBothValid() throws Exception {
        int validUserId = 1;
        int validAchievId = 1;
        UserBadgesClass testClass = new UserBadgesClass(validUserId, validAchievId);
        BadgesClass badge = new BadgesClass("name","description");
        badge.setId(1);
        validUser.setId(1);
        when(badgesRepository.findById(validAchievId)).thenReturn(badge);
        when(loginRepository.findById(validUserId)).thenReturn(validUser);
        String jsonReq = om.writeValueAsString(testClass);
        MvcResult result = mockMvc.perform(post("/storeBadges")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

}
