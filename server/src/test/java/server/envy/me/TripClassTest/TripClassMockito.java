package server.envy.me.TripClassTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.RestControlClass;
import server.api.connection.TransportationApi;
import server.envy.me.car.CarClass;
import server.envy.me.car.CarRepository;
import server.envy.me.car.CarService;
import server.envy.me.leaderboard.LeaderboardClass;
import server.envy.me.leaderboard.LeaderboardRepository;
import server.envy.me.login.LoginClass;
import server.envy.me.login.LoginRepository;
import server.envy.me.response.ResponseClass;
import server.envy.me.trip.CO2Calculator;
import server.envy.me.trip.TripClass;
import server.envy.me.trip.TripRepository;
import server.envy.me.trip.TripToClient;
import server.envy.me.usertrips.UserTripClass;
import server.envy.me.usertrips.UserTripRepository;
import server.envy.me.usertrips.UserTripService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class TripClassMockito {

    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @InjectMocks
    RestControlClass restController;

    @Mock
    TripRepository tripRepository;

    @Mock
    LoginRepository loginRepository;

    @Mock
    UserTripService userTripsService;

    @Mock
    UserTripRepository userTripRepository;

    @Mock
    LeaderboardRepository leaderboardRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    CarService carService;

    @Mock
    CO2Calculator co2Calculator;

    @Mock
    TransportationApi transportationApi;

    private static final long ID = 1;

    private static final String username = "testUser";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
        loginRepository.save(new LoginClass(username, "strongPass"));
    }

    /**
     * Tests getAllTrips to a valid user.
     * @throws Exception
     */
    @Test
    public void testGetTripsValidUser() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        //usertrips list to return
        List<UserTripClass> listTrips = new ArrayList<>();
        listTrips.add(new UserTripClass(1,1));
        when(userTripsService.getListTripByUser(1)).thenReturn(listTrips);

        //trip to return
        List<TripClass> listToReturn = new ArrayList<>();
        listToReturn.add(new TripClass("Plane", "Source", "Destination",
                100, null, null));
        OngoingStubbing stubbing = Mockito.when(tripRepository.findById(1));
        for(Object obj : listToReturn) {
            stubbing = stubbing.thenReturn(obj);
        }

        //perform GET REQUEST
        MvcResult result = mockMvc.perform(get("/" + username + "/getAllTrips").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        TripToClient tripFromServer = om.readValue(resultContent,
                TripToClient.class);
        List<TripClass> tripsToTest = tripFromServer.getTrips();
        Assert.assertTrue(tripsToTest.get(0).getType().equals(listToReturn.get(0).getType()));
    }

    /**
     * Tests GET request from invalid user.
     * @throws Exception
     */
    @Test
    public void testGetTripsInvalidUser() throws Exception{
        when(loginRepository.findByUsername(username)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + username + "/getAllTrips").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        TripToClient tripFromServer = om.readValue(resultContent,
                TripToClient.class);
        List<TripClass> tripsToTest = tripFromServer.getTrips();
        Assert.assertTrue(tripsToTest.isEmpty());
    }

    /**
     * Test delete valid Trip.
     * @throws Exception
     */
    @Test
    public void testDeleteValidTrip() throws Exception {
        TripClass tripNotNull = new TripClass();
        when(tripRepository.findById(1)).thenReturn(tripNotNull);
        MvcResult result = mockMvc.perform(get("/" + 1 + "/deleteTrip").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

    /**
     * Test delete invalid Trip.
     * @throws Exception
     */
    @Test
    public void testDeleteInvalidTrip() throws Exception {
        when(tripRepository.findById(1)).thenReturn(null);
        MvcResult result = mockMvc.perform(get("/" + 1 + "/deleteTrip").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Trip id is not stored in the database"));
    }

    //Class to POST for the tests
    TripClass tripTest = new TripClass("PubTrans", "Paris", "Milan", 200, null, null);

    /**
     * Test POST request by not-existing user.
     * @throws Exception
     */
    @Test
    public void testStoreTripInvalidUser() throws Exception {
        when(loginRepository.findByUsername(username)).thenReturn(null);
        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Username does not exist"));
    }

    /**
     * Test store Trip NOT of type Car.
     * @throws Exception
     */
    @Test
    public void testStoreTripNotTypeCar() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        LeaderboardClass fromLeaderboard = new LeaderboardClass(1,13);
        when(leaderboardRepository.findById(1)).thenReturn(fromLeaderboard);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(co2Calculator.getEmission(tripTest)).thenReturn(14);
        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Trip stored"));
    }

    /**
     * Test store Trip of Type Car where carId is not specificied.
     * @throws Exception
     */
    @Test
    public void testStoreTripTypeCarInvalid() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        LeaderboardClass fromLeaderboard = new LeaderboardClass(1,13);
        when(leaderboardRepository.findById(1)).thenReturn(fromLeaderboard);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(co2Calculator.getEmission(tripTest)).thenReturn(14);
        tripTest.setType("Car");
        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Car ID is not specified"));
    }

    /**
     * Test store trip with type Car but Car is not in the database.
     * @throws Exception
     */
    @Test
    public void testStoreTripTypeCarNotInDataBase() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        tripTest.setType("Car");
        tripTest.setCarId(1);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(carRepository.findById(tripTest.getCarId())).thenReturn(null);
        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Car does not exist"));
    }

    /**
     * Test store Car but owner does not correspond to user asking to store the Trip.
     * @throws Exception
     */
    @Test
    public void testStoreTripTypeCarInDataBaseButOwnerDoesNotCorrespond() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        CarClass carOut = new CarClass();
        tripTest.setType("Car");
        tripTest.setCarId(1);
        int carId = tripTest.getCarId();
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(carRepository.findById(carId)).thenReturn(carOut);
        when(carService.getUserId(carId)).thenReturn(2);
        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("does not belong to the user"));
    }

    /**
     * Test store valid trip with Type Car.
     * @throws Exception
     */
    @Test
    public void testStoreTripTypeCarValid() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        CarClass carOut = new CarClass();
        tripTest.setType("Car");
        tripTest.setCarId(1);
        int carId = tripTest.getCarId();
        LeaderboardClass fromLeaderboard = new LeaderboardClass(1,13);
        when(leaderboardRepository.findById(1)).thenReturn(fromLeaderboard);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(carRepository.findById(carId)).thenReturn(carOut);
        when(carService.getUserId(carId)).thenReturn(1);
        when(co2Calculator.getEmission(tripTest)).thenReturn(14);

        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Car trip stored"));
    }

    @Test
    public void testStoreTripTypeCarValidZeroDistance() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        CarClass carOut = new CarClass();
        tripTest.setType("Car");
        tripTest.setCarId(1);
        tripTest.setDistance(0);
        int carId = tripTest.getCarId();
        LeaderboardClass fromLeaderboard = new LeaderboardClass(1,13);
        when(leaderboardRepository.findById(1)).thenReturn(fromLeaderboard);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(carRepository.findById(carId)).thenReturn(carOut);
        when(carService.getUserId(carId)).thenReturn(1);
        when(co2Calculator.getEmission(tripTest)).thenReturn(14);

        String jsonReq = om.writeValueAsString(tripTest);
        System.out.println(jsonReq);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Car trip stored"));
    }


    @Test
    public void testStoreTripTypePubTransValid() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        tripTest.setType("PubTrans");
        tripTest.setDistance(0);
        tripTest.setCarId(null);
        LeaderboardClass fromLeaderboard = new LeaderboardClass(1,13);
        when(leaderboardRepository.findById(1)).thenReturn(fromLeaderboard);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(co2Calculator.getEmission(tripTest)).thenReturn(14);

        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Trip stored"));
    }

    @Test
    public void testStoreTripTypeBikeWalkValid() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        tripTest.setType("BikeWalk");
        tripTest.setDistance(0);
        tripTest.setCarId(null);
        LeaderboardClass fromLeaderboard = new LeaderboardClass(1,13);
        when(leaderboardRepository.findById(1)).thenReturn(fromLeaderboard);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(co2Calculator.getEmission(tripTest)).thenReturn(14);

        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Trip stored"));
    }

    @Test
    public void testStoreTripTypeBikeInValid() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        tripTest.setType("Bike");
        tripTest.setDistance(0);
        tripTest.setCarId(null);
        LeaderboardClass fromLeaderboard = new LeaderboardClass(1,13);
        when(leaderboardRepository.findById(1)).thenReturn(fromLeaderboard);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(co2Calculator.getEmission(tripTest)).thenReturn(14);

        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Trip stored"));
    }

    @Test
    public void testStoreTripTypePlaneValid() throws Exception {
        LoginClass out = new LoginClass(username, "strongPass");
        out.setId(1);
        tripTest.setType("Plane");
        tripTest.setDistance(0);
        tripTest.setCarId(null);
        LeaderboardClass fromLeaderboard = new LeaderboardClass(1,13);
        when(leaderboardRepository.findById(1)).thenReturn(fromLeaderboard);
        when(loginRepository.findByUsername(username)).thenReturn(out);
        when(co2Calculator.getEmission(tripTest)).thenReturn(14);

        String jsonReq = om.writeValueAsString(tripTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeTrip")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Trip stored"));
    }
}
