package server.envy.me.SettingsClassTest;

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
import server.envy.me.response.ResponseClass;
import server.envy.me.settings.SettingsClass;
import server.envy.me.settings.SettingsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class SettingsClassMockito {

    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @InjectMocks
    RestControlClass restController;

    @Mock
    SettingsRepository settingsRepository;

    @Mock
    LoginRepository loginRepository;

    private static final String username = "giovanni";

    SettingsClass testSettings = new SettingsClass(10,10,10,10,10,10);

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
        loginRepository.save(new LoginClass(username, "strongPass"));
    }

    @Test
    public void testGetSettingsValidUser() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(settingsRepository.findById(1)).thenReturn(testSettings);
        MvcResult result = mockMvc.perform(get("/" + username + "/getSettings").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        SettingsClass settingsFromServer = om.readValue(resultContent, SettingsClass.class);
        assertThat(testSettings.getGasPrice() == settingsFromServer.getGasPrice()
        && testSettings.getElectricityEmmFactor() == settingsFromServer.getElectricityEmmFactor()
        && testSettings.getElectricityPrice() == settingsFromServer.getElectricityPrice()
        && testSettings.getLivingSpace() == settingsFromServer.getLivingSpace()
        && testSettings.getNatGasPrice() == settingsFromServer.getNatGasPrice()
        && testSettings.getUserId() == settingsFromServer.getUserId());
    }

    @Test
    public void testGetSettingsInvalidUser() throws Exception {
        when(loginRepository.findByUsername(username)).thenReturn(null);
        when(settingsRepository.findById(1)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + username + "/getSettings").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        SettingsClass settingsFromServer = om.readValue(resultContent, SettingsClass.class);
        assertThat(testSettings.getGasPrice() == settingsFromServer.getGasPrice()
                && testSettings.getElectricityEmmFactor() == settingsFromServer.getElectricityEmmFactor()
                && testSettings.getElectricityPrice() == settingsFromServer.getElectricityPrice()
                && testSettings.getLivingSpace() == settingsFromServer.getLivingSpace()
                && testSettings.getNatGasPrice() == settingsFromServer.getNatGasPrice()
                && testSettings.getUserId() == settingsFromServer.getUserId());
    }

    @Test
    public void testStoreSettingsValidUser() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        String jsonReq = om.writeValueAsString(testSettings);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeSettings")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

    @Test
    public void testStoreSettingsInvalidUser() throws Exception {
        when(loginRepository.findByUsername(username)).thenReturn(null);
        String jsonReq = om.writeValueAsString(testSettings);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeSettings")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        System.out.print(response.getPayload());
        Assert.assertTrue(response.getPayload().contains("Username does not exist"));
    }

}
