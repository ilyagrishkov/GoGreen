package server.envy.me.UtiltiesClassTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.RestControlClass;
import server.api.connection.Connection;
import server.api.connection.FoodData;
import server.api.connection.UtilitiesData;
import server.envy.me.leaderboard.LeaderboardClass;
import server.envy.me.leaderboard.LeaderboardRepository;
import server.envy.me.login.LoginClass;
import server.envy.me.login.LoginRepository;
import server.envy.me.response.ResponseClass;
import server.envy.me.settings.SettingsClass;
import server.envy.me.settings.SettingsRepository;
import server.envy.me.userutilities.UserUtilitiesClass;
import server.envy.me.userutilities.UserUtilitiesRepository;
import server.envy.me.userutilities.UserUtilitiesService;
import server.envy.me.utilities.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class UtilitiesControllerMockito {

    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @InjectMocks
    RestControlClass restController;

    @Mock
    UtilitiesRepository utilitiesRepository;

    @Mock
    UtilitiesService utilitiesService;

    @Mock
    LoginRepository loginRepository;

    @Mock
    UserUtilitiesRepository userUtilitiesRepository;

    @Mock
    UserUtilitiesService userUtilitiesService;

    @Mock
    SettingsRepository settingsRepository;

    @Mock
    LeaderboardRepository leaderboardRepository;

    @Mock
    Connection connection;

    public static int invalidId = 13;

    public static int validId = 14;

    public static String invalidUsername = "invalidUser";

    public static String validUsername = "validUser";

    public static Date date1;

    private UtilitiesData utilitiesData;

    private FoodData foodData;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
        utilitiesData = new UtilitiesData(settingsClass.getLivingSpace(),
                testClass.getElectricity(),testClass.getGas(), testClass.getWater(),
                testClass.getPerCleanEnergy(), 0, 0);
        foodData = new FoodData(0,0,0,0,0);
    }

    @Test
    public void testGetUtilitiesInvalidId() throws Exception {
        when(utilitiesRepository.findById(invalidId)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidId + "/getUtilities").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UtilitiesClass fromServer = om.readValue(resultContent, UtilitiesClass.class);
        Assert.assertNull(fromServer.getDatetime());
    }

    UtilitiesClass testClass = new UtilitiesClass(1,1,1,1,1, date1);

    @Test
    public void testGetUtilitiesValidId() throws Exception {
        when(utilitiesRepository.findById(validId)).thenReturn(testClass);
        MvcResult result = mockMvc.perform(get("/" + validId + "/getUtilities").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UtilitiesClass fromServer = om.readValue(resultContent, UtilitiesClass.class);
        Assert.assertTrue(fromServer.getElectricity() == 1);
    }

    LoginClass validUser = new LoginClass(validUsername, "validPass");

    @Test
    public void testGetAllUtilitiesInvalidUsername() throws Exception {
        when(loginRepository.findByUsername(invalidUsername)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + invalidUsername + "/getAllUtilitiesId").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UtilitiesToClient fromServer = om.readValue(resultContent, UtilitiesToClient.class);
        Assert.assertTrue(fromServer.getUtilities().isEmpty());
    }

    UserUtilitiesClass userUtilities = new UserUtilitiesClass(14,1);
    List<UserUtilitiesClass> listUtilities = new ArrayList<>();

    @Test
    public void testGetAllUtilitiesValidUsername() throws Exception {
        validUser.setId(validId);
        listUtilities.add(userUtilities);
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(userUtilitiesService.getUtilitiesByUserId(validId)).thenReturn(listUtilities);
        MvcResult result = mockMvc.perform(get("/" + validUsername + "/getAllUtilitiesId").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UtilitiesToClient fromServer = om.readValue(resultContent, UtilitiesToClient.class);
        Assert.assertTrue(!fromServer.getUtilities().isEmpty());
    }

    @Test
    public void testStoreInvalidUsername() throws Exception {
        when(loginRepository.findByUsername(invalidUsername)).thenReturn(null);
        String jsonReq = om.writeValueAsString(testClass);
        MvcResult result = mockMvc.perform(post("/" + invalidUsername + "/storeUtilities")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Invalid username"));
    }

    SettingsClass settingsClass = new SettingsClass(1,1,1,1,1,140);
    SettingsClass settingsClassSmallArea = new SettingsClass(1,1,1,1,1,0);
    UtilitiesFromClient utilitiesFromClient = new UtilitiesFromClient(1,new Date(2019,1,1),1,1,1,1,true);
    UtilitiesFromClient utilitiesFromClientNoSolar = new UtilitiesFromClient(1,new Date(2019,1,1),1,
            1,1,
            1,false);

    @Test
    public void testStoreValidUsername() throws Exception {
        validUser.setId(1);
        when(settingsRepository.findById(1)).thenReturn(settingsClass);
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(connection.getTotal("result_housing_total", 1, 0,
                utilitiesData, foodData)).thenReturn(100);
        when(leaderboardRepository.findById(1)).thenReturn(new LeaderboardClass(1,14));
        String jsonReq = om.writeValueAsString(utilitiesFromClient);
        MvcResult result = mockMvc.perform(post("/" + validUsername + "/storeUtilities")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

    @Test
    public void testStoreValidUsernameSmallLivingArea() throws Exception {
        validUser.setId(1);
        when(settingsRepository.findById(1)).thenReturn(settingsClassSmallArea);
        when(loginRepository.findByUsername(validUsername)).thenReturn(validUser);
        when(connection.getTotal("result_housing_total", 1, 0,
                utilitiesData, foodData)).thenReturn(100);
        when(leaderboardRepository.findById(1)).thenReturn(new LeaderboardClass(1,14));
        String jsonReq = om.writeValueAsString(utilitiesFromClientNoSolar);
        MvcResult result = mockMvc.perform(post("/" + validUsername + "/storeUtilities")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }
}
