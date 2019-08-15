package server.envy.me.userFriendsTest;

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
import server.envy.me.userfriends.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class userFriendsMockitoTests {

    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @InjectMocks
    RestControlClass restController;

    @Mock
    UserFriendsRepository userFriendsRepository;

    @Mock
    UserFriendsService userFriendsService;

    @Mock
    LoginRepository loginRepository;

    private static final String username = "pierino";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    UserFriendsPostClass postClass = new UserFriendsPostClass(username);
    LoginClass loginInfo = new LoginClass("pierino", "rana");
    LoginClass loginInfoFriend = new LoginClass("giovanni", "test");

    @Test
    public void testStoreInvalidFriend() throws Exception {
        when(loginRepository.findByUsername(username)).thenReturn(null);
        when(loginRepository.findByUsername("giovanni")).thenReturn(loginInfo);
        String jsonReq = om.writeValueAsString(postClass);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + "giovanni" + "/storeFriend")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Friend does not exist"));
    }

    @Test
    public void testStoreInvalidUsername() throws Exception {
        when(loginRepository.findByUsername(username)).thenReturn(loginInfo);
        when(loginRepository.findByUsername("giovanni")).thenReturn(null);
        String jsonReq = om.writeValueAsString(postClass);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + "giovanni" + "/storeFriend")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Friend does not exist"));
    }

    @Test
    public void testStoreValidFriend() throws Exception {
        loginInfo.setId(1);
        loginInfoFriend.setId(2);
        when(loginRepository.findByUsername(username)).thenReturn(loginInfo);
        when(loginRepository.findByUsername("giovanni")).thenReturn(loginInfoFriend);
        String jsonReq = om.writeValueAsString(postClass);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + "giovanni" + "/storeFriend")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Friend stored"));
    }

    List<Integer> listFriendsId = new ArrayList<>();

    @Test
    public void testGetAllFriendsValid() throws Exception {
        loginInfo.setId(1);
        loginInfoFriend.setId(2);
        listFriendsId.add(2);
        when(loginRepository.findByUsername("pierino")).thenReturn(loginInfo);
        when(loginRepository.findByUsername("giovanni")).thenReturn(loginInfoFriend);
        when(loginRepository.findById(2)).thenReturn(loginInfoFriend);
        when(userFriendsService.getListFriendsId(1)).thenReturn(listFriendsId);
        MvcResult result = mockMvc.perform(get("http://localhost:8080/" + "pierino" + "/getAllFriends")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UserFriendsToClient response = om.readValue(resultContent, UserFriendsToClient.class);
        Assert.assertTrue(!response.getFriends().isEmpty());
    }

    @Test
    public void testGetAllFriendsInvalidUsername() throws Exception {
        when(loginRepository.findByUsername("pierino")).thenReturn(null);
        MvcResult result = mockMvc.perform(get("http://localhost:8080/" + "pierino" + "/getAllFriends")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        UserFriendsToClient response = om.readValue(resultContent, UserFriendsToClient.class);
        Assert.assertTrue(response.getFriends().isEmpty());
    }

    UserFriendsPostClass friendToDelete = new UserFriendsPostClass("giovanni");

    @Test
    public void testDeleteInvalidUsername() throws Exception{
        when(loginRepository.findByUsername("pierino")).thenReturn(null);
        String jsonReq = om.writeValueAsString(friendToDelete);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + "pierino" + "/deleteFriend")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Friendship does not exist"));
    }


    UserFriendsClass friendsToReturn = new UserFriendsClass(1,2);

    @Test
    public void testDeleteValidUsername() throws Exception{
        loginInfo.setId(1);
        loginInfoFriend.setId(2);
        when(loginRepository.findByUsername("pierino")).thenReturn(loginInfo);
        when(loginRepository.findByUsername("giovanni")).thenReturn(loginInfoFriend);
        when(userFriendsRepository.findByUserId1AndUserId2(1,2)).thenReturn(friendsToReturn);
        String jsonReq = om.writeValueAsString(friendToDelete);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + "pierino" + "/deleteFriend")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Friendship deleted"));
    }

    @Test
    public void testDeleteValidUsernameInvalidFriend() throws Exception{
        loginInfo.setId(1);
        loginInfoFriend.setId(2);
        when(loginRepository.findByUsername("pierino")).thenReturn(loginInfo);
        when(loginRepository.findByUsername("giovanni")).thenReturn(null);
        String jsonReq = om.writeValueAsString(friendToDelete);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + "pierino" + "/deleteFriend")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Friendship does not exist"));
    }

}
