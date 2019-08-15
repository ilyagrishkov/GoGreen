package server.envy.me.AchievementsTest;

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
import server.envy.me.achievements.AchievementsClass;
import server.envy.me.achievements.AchievementsRepository;
import server.envy.me.login.LoginClass;
import server.envy.me.login.LoginRepository;
import server.envy.me.response.ResponseClass;
import server.envy.me.userachievements.UserAchievementsClass;
import server.envy.me.userachievements.UserAchievementsRepository;
import server.envy.me.userachievements.UserAchievementsService;
import server.envy.me.achievements.AchievementsToClient;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class AchievementControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @InjectMocks
    RestControlClass restController;

    @Mock
    AchievementsRepository achievementsRepository;

    @Mock
    LoginRepository loginRepository;

    @Mock
    UserAchievementsService userAchievementsService;

    @Mock
    UserAchievementsRepository userAchievementsRepository;

    private static final String invalidUsername = "invalid";
    private static final String validUsername = "valid";
    private static final int validAchId = 1;
    private static final int invalidAchId = 2;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    @Test
    public void testGetAchievementInvalidId() throws Exception {
        int invalidId = 14;
        when(achievementsRepository.findById(invalidId)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidId + "/getAchievement").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        AchievementsClass fromServer = om.readValue(resultContent, AchievementsClass.class);
        Assert.assertNull(fromServer.getDescription());
    }

    @Test
    public void testGetAchievementValidId() throws Exception {
        int validId = 11;
        AchievementsClass toReturn = new AchievementsClass("name","description",50);
        when(achievementsRepository.findById(validId)).thenReturn(toReturn);
        MvcResult result = mockMvc.perform(get("/" + validId + "/getAchievement").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        AchievementsClass fromServer = om.readValue(resultContent, AchievementsClass.class);
        Assert.assertTrue(fromServer.getDescription().contains("description"));
    }

    @Test
    public void testGetAllInvalidUsername() throws Exception {
        String invalidUser = "invalid";
        when(loginRepository.findByUsername(invalidUser)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUser + "/getAllAchievements").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        AchievementsToClient fromServer = om.readValue(resultContent, AchievementsToClient.class);
        Assert.assertTrue(fromServer.getAchievements().isEmpty());
    }

    LoginClass validUser = new LoginClass("user", "test");

    @Test
    public void testGetAllValidUsername() throws Exception {
        List<UserAchievementsClass> toReturn = new ArrayList<>();
        toReturn.add(new UserAchievementsClass(99,11,10));
        String validUsername = "valid";
        validUser.setId(99);
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(userAchievementsService.getAchievementsIdByUserId(99)).thenReturn(toReturn);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/getAllAchievements").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        AchievementsToClient fromServer = om.readValue(resultContent, AchievementsToClient.class);
        System.out.print(fromServer.getAchievements().get(0));
        Assert.assertTrue(!fromServer.getAchievements().isEmpty());
    }

    @Test
    public void testStoreInvalidUserId() throws Exception {
        int invalidUserId = 1;
        int validAchievId = 1;
        UserAchievementsClass testClass = new UserAchievementsClass(invalidUserId, validAchievId, 10);
        when(achievementsRepository.findById(validAchievId)).thenReturn(new AchievementsClass());
        when(loginRepository.findById(invalidUserId)).thenReturn(null);
        String jsonReq = om.writeValueAsString(testClass);
        MvcResult result = mockMvc.perform(post("/storeAchievement")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Incorrect information"));
    }

    @Test
    public void testStoreInvalidAchiId() throws Exception {
        int validUserId = 1;
        int invalidAchievId = 1;
        UserAchievementsClass testClass = new UserAchievementsClass(validUserId, invalidAchievId, 10);
        when(achievementsRepository.findById(invalidAchievId)).thenReturn(null);
        when(loginRepository.findById(validUserId)).thenReturn(new LoginClass());
        String jsonReq = om.writeValueAsString(testClass);
        MvcResult result = mockMvc.perform(post("/storeAchievement")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Incorrect information"));
    }

    @Test
    public void testStoreBothNull() throws Exception {
        int invalidUserId = 1;
        int invalidAchievId = 1;
        UserAchievementsClass testClass = new UserAchievementsClass(invalidUserId, invalidAchievId, 10);
        when(achievementsRepository.findById(invalidAchievId)).thenReturn(null);
        when(loginRepository.findById(invalidUserId)).thenReturn(null);
        String jsonReq = om.writeValueAsString(testClass);
        MvcResult result = mockMvc.perform(post("/storeAchievement")
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
        UserAchievementsClass testClass = new UserAchievementsClass(validUserId, validAchievId, 10);
        AchievementsClass ach = new AchievementsClass("name","description", 50);
        ach.setId(1);
        validUser.setId(1);
        when(achievementsRepository.findById(validAchievId)).thenReturn(ach);
        when(loginRepository.findById(validUserId)).thenReturn(validUser);
        String jsonReq = om.writeValueAsString(testClass);
        MvcResult result = mockMvc.perform(post("/storeAchievement")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

    @Test
    public void testGetProgressBothInvalid() throws Exception {
        when(loginRepository.findByUsername(invalidUsername)).thenReturn(null);
        when(achievementsRepository.findById(invalidAchId)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUsername + "/" +
                invalidAchId + "/getProgress").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Integer fromServer = om.readValue(resultContent, Integer.class);
        Assert.assertTrue(fromServer.intValue() == 0);
    }

    @Test
    public void testGetProgressInvalidUser() throws Exception {
        when(loginRepository.findByUsername(invalidUsername)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUsername + "/" +
                validAchId + "/getProgress").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Integer fromServer = om.readValue(resultContent, Integer.class);
        Assert.assertTrue(fromServer.intValue() == 0);
    }

    @Test
    public void testGetProgressValidUserInvalidAch() throws Exception {
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(achievementsRepository.findById(invalidAchId)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/" +
                invalidAchId + "/getProgress").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Integer fromServer = om.readValue(resultContent, Integer.class);
        Assert.assertTrue(fromServer.intValue() == 0);
    }

    AchievementsClass ach = new AchievementsClass("","",10);

    @Test
    public void testGetProgressValidUserValidAchInvalidUserAch() throws Exception {
        ach.setId(validAchId);
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(achievementsRepository.findById(validAchId)).thenReturn(ach);
        when(userAchievementsRepository.findByUserIdAndAchievementsId(validUser.getId(), validAchId)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/" +
                validAchId + "/getProgress").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Integer fromServer = om.readValue(resultContent, Integer.class);
        Assert.assertTrue(fromServer.intValue() == 0);
    }

    UserAchievementsClass userAch = new UserAchievementsClass(1,1,10);

    @Test
    public void testGetProgressValidUserValidAchValidUserAch() throws Exception {
        validUser.setId(1);
        ach.setId(validAchId);
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(achievementsRepository.findById(validAchId)).thenReturn(ach);
        when(userAchievementsRepository.findByUserIdAndAchievementsId(validUser.getId(), validAchId)).thenReturn(userAch);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/" +
                validAchId + "/getProgress").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        Integer fromServer = om.readValue(resultContent, Integer.class);
        Assert.assertTrue(fromServer.intValue() == 10);
    }

    private final int progress = 11;

    @Test
    public void testStoreProgressBothInvalid() throws Exception {
        when(loginRepository.findByUsername(invalidUsername)).thenReturn(null);
        when(achievementsRepository.findById(invalidAchId)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUsername + "/" +
                invalidAchId + "/" + progress + "/setProgress").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass fromServer = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(fromServer.getPayload().contains("User does not exist"));
    }

    @Test
    public void testStoreProgressInvalidUser() throws Exception {
        when(loginRepository.findByUsername(invalidUsername)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUsername + "/" +
                validAchId + "/" + progress + "/" + "/setProgress").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass fromServer = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(fromServer.getPayload().contains("User does not exist"));
    }

    @Test
    public void testStoreProgressValidUserInvalidAch() throws Exception {
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(achievementsRepository.findById(invalidAchId)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/" +
                invalidAchId + "/" +  progress + "/" + "/setProgress").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass fromServer = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(fromServer.getPayload().contains("Achievement does not exist"));
    }

    @Test
    public void testStoreProgressValidUserValidAchValidUserAch() throws Exception {
        validUser.setId(1);
        ach.setId(validAchId);
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(achievementsRepository.findById(validAchId)).thenReturn(ach);
        when(userAchievementsRepository.findByUserIdAndAchievementsId(validUser.getId(), validAchId)).thenReturn(userAch);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/" +
                validAchId + "/" +  progress + "/setProgress").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass fromServer = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(fromServer.getPayload().contains("Progress is stored"));
    }



}
