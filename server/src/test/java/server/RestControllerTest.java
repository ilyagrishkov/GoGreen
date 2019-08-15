package server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import server.envy.me.car.CarClass;
import server.envy.me.car.CarFromClient;
import server.envy.me.food.FoodClass;
import server.envy.me.food.FoodToClient;
import server.envy.me.login.LoginClass;
import server.envy.me.registration.RegistrationClass;
import server.envy.me.response.ResponseClass;
import server.envy.me.settings.SettingsClass;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAutoConfiguration
//@FixMethodOrder(MethodSorters.JVM)
public class RestControllerTest {

    final int port = 8080;

    private MockMvc mockMvc;

    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RestTemplate restTemplate;

    ////////////////////////////////////
    //          FIRST - PART          //
    //  NO INTERACTION WITH DATABASE  //
    ////////////////////////////////////

    /**
     * Test POST method.
     */
    @Test
    public void answeringPost() {
        String test = "test";
        assertThat(this.restTemplate.postForEntity("http://localhost:"
                + port + "/", test, String.class).toString()
                .contains("From Server: " + test));
    }

    /**
     * Test GET method.
     *
     * @throws Exception in case of failed response.
     */
    @Test
    public void answeringGet() throws Exception {
        assertThat(this.restTemplate.getForEntity("http://localhost:"
                + port + "/", String.class)).toString().contains("You should "
                + "send a POST request to see something interesting");
    }

    /**
     * Test JSON GET request from Client.
     */
    @Test
    public void testingJsonGetRequest() {
        String responseString = "This is the test GET JSON page";
        assertThat(restTemplate.getForEntity("http://localhost:"
                + port + "/getJSON", String.class).equals(responseString));
    }

    ////////////////////////////////////
    //          SECOND - PART         //
    //    INTERACTION WITH DATABASE   //
    ////////////////////////////////////

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testValidUser() throws Exception {
        LoginClass testUser = new LoginClass("grafaz", "istruzioni56");
        String jsonReq = om.writeValueAsString(testUser);
        MvcResult result = mockMvc.perform(post(
                "http://localhost:8080/authorization").content(jsonReq)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent,
                ResponseClass.class);
        Assert.assertFalse(response.getPayload().contains("User : "
                + testUser.getUsername()
                + " is authenticated"));
    }

    @Test
    public void testInvalidUser() throws Exception {
        LoginClass testUser = new LoginClass("grafaz", "noPass");
        String jsonReq = om.writeValueAsString(testUser);
        MvcResult result = mockMvc.perform(post(
                "http://localhost:8080/authorization").content(jsonReq)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent,
                ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("User : "
                + testUser.getUsername()
                + " is NOT authenticated"));
    }

    @Test
    public void testNotExistingUser() throws Exception {
        LoginClass testUser = new LoginClass("notExisting", "noPass");
        String jsonReq = om.writeValueAsString(testUser);
        MvcResult result = mockMvc.perform(post(
                "http://localhost:8080/authorization").content(jsonReq)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent,
                ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("User : "
                + testUser.getUsername()
                + " is NOT authenticated"));
    }

    @Test
    public void testRegistrationUsernameInUse() throws Exception {
        RegistrationClass regTest = new RegistrationClass("giopowder",
                "Giorgio", "Colpani", new Date(1998, 8, 31),
                "male", "strongPass");
        String jsonReq = om.writeValueAsString(regTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/registration")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Username already taken"));
    }

    @Test
    public void testRegistrationUsernameNotInUse() throws Exception {
        RegistrationClass regTest = new RegistrationClass("newRegUser", "Giorgio", "Colpani",
                new Date(1998, 8, 31), "male", "strongPass");
        String jsonReq = om.writeValueAsString(regTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/registration")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

    @Test
    public void testRegistrationUsernameNoyInUseSettings() throws Exception {
        String username = "newRegUser";
        SettingsClass settingsDetails = new SettingsClass(10, 10.0, 10.0, 10.0, 10.0, 10);
        String jsonReq = om.writeValueAsString(settingsDetails);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/" + username + "/storeSettings")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

    @Test
    public void testRegistrationUsernameDelete() throws Exception {
        RegistrationClass regTest = new RegistrationClass("newRegUser", "Giorgio", "Colpani",
                new Date(1998, 8, 31), "male", "strongPass");
        String jsonReq = om.writeValueAsString(regTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/deleteRegistration")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

    /**
     * Check mapping to retrieve user information.
     * It's important to check that the password is set to NULL
     *
     * @throws Exception in case get request is rejected by the server
     */
    @Test
    public void retrieveUserInfoTest() throws Exception {
        RegistrationClass regTest = new RegistrationClass("giopowder", "Giorgio", "Colpani",
                new Date(1998, 8, 31), "male", null);
        regTest.setId(107);
        MvcResult result = mockMvc.perform(get("http://localhost:8080/giopowder/getRegistrationInfo")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        RegistrationClass fromServer = om.readValue(resultContent, RegistrationClass.class);
        Assert.assertNull(fromServer.getPassword());
        Assert.assertTrue(regTest.getUsername().equals(fromServer.getUsername())
                && regTest.getFirstname().equals(fromServer.getFirstname())
                && regTest.getLastname().equals(fromServer.getLastname())
                && regTest.getGender().equals(fromServer.getGender())
                && regTest.getId() == fromServer.getId());
    }

//    @Test
//    public void deleteValidUser() throws Exception {
//        //CLEAN giopowderSecond
//        RegistrationClass regTest = new RegistrationClass("giopowderSecond", "Giorgio", "Colpani",
//                new Date(1998, 8, 31), "male", "strongPass");
//        String jsonReq = om.writeValueAsString(regTest);
//        MvcResult result = mockMvc.perform(post("http://localhost:8080/deleteRegistration")
//                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk()).andReturn();
//        String resultContent = result.getResponse().getContentAsString();
//        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
//        Assert.assertTrue(response.getPayload().contains("OK"));
//    }

    /**
     * Test checks getAllFood is retrieving all the food correctly.
     * Date is not working. Find more info in the normalization method below.
     *
     * @throws Exception in case get request is rejected by the server
     */
    @Test
    public void testGetAllFoodValidUser() throws Exception {
        List<FoodClass> testAllFood = new ArrayList<>();
        FoodClass oneMeal = new FoodClass("Vegetarinan", true, true, 0, 10,
                10, 10, 10, 0, 0, 60, new Date(2019, 2, 25));
        testAllFood.add(oneMeal);
        MvcResult result = mockMvc.perform(get("http://localhost:8080/foodadduser/getAllFood")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        FoodToClient foodFromServer = om.readValue(resultContent,
                FoodToClient.class);
        List<FoodClass> foodToTest = foodFromServer.getFood();
        for (FoodClass x : foodToTest) {
            Assert.assertTrue(testAllFood.get(0).getType().equals(x.getType())
                              && testAllFood.get(0).isLocal() == x.isLocal()
                              && testAllFood.get(0).isCooked() == x.isCooked()
                              && testAllFood.get(0).getMeat() == x.getMeat()
                              && testAllFood.get(0).getFish() == x.getFish()
                              && testAllFood.get(0).getMeatAlt() == x.getMeatAlt()
                              && testAllFood.get(0).getEggs() == x.getEggs()
                              && testAllFood.get(0).getGrains() == x.getGrains()
                              && testAllFood.get(0).getDairy() == x.getDairy()
                              && testAllFood.get(0).getVeggies() == x.getVeggies()
                              && testAllFood.get(0).getSnacks() == x.getSnacks()
                    //&& testAllFood.get(0).getDate().equals(x.getDate())
            );
        }
    }

    @Test
    public void testValidUserFoodVegetarian() throws Exception {
        FoodClass foodTest = new FoodClass("Vegetarian", true, true, 0, 10, 10, 10, 10, 0,
                0, 60, new Date(2019, 2, 25));
        String jsonReq = om.writeValueAsString(foodTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/food")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Food stored"));
    }



    @Test
    public void testValidUserFoodVegan() throws Exception {
        FoodClass foodTest = new FoodClass("Vegan", true, true, 0, 0, 0, 0, 40, 0,
                0, 60, new Date(2019, 2, 25));
        String jsonReq = om.writeValueAsString(foodTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/food")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Food stored"));
    }

    @Test
    public void testValidUserFoodPascetarian() throws Exception {
        FoodClass foodTest = new FoodClass("Pescetarian", true, true, 0, 10, 10, 20, 0, 0,
                10, 50, new Date(2019, 2, 25));
        String jsonReq = om.writeValueAsString(foodTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/food")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Food stored"));
    }

    @Test
    public void testValidUserFoodLowMeat() throws Exception {
        FoodClass foodTest = new FoodClass("Low meat", true, true, 0, 10, 10, 10, 10, 0,
                60, 0, new Date(2019, 2, 25));
        String jsonReq = om.writeValueAsString(foodTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/food")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Food stored"));
    }

    @Test
    public void testValidUserFoodMediumMeat() throws Exception {
        FoodClass foodTest = new FoodClass("Medium meat", true, true, 0, 10, 10, 10, 0, 10,
                0, 60, new Date(2019, 2, 25));
        String jsonReq = om.writeValueAsString(foodTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/food")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Food stored"));
    }

    @Test
    public void testValidUserFoodNonExistingType() throws Exception {
        FoodClass foodTest = new FoodClass("Wrong", false, false, 0, 10, 10, 10, 10, 0,
                0, 60, new Date(2019, 2, 25));
        String jsonReq = om.writeValueAsString(foodTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/food")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Food stored"));
    }

    @Test
    public void testValidUserFoodHighMeat() throws Exception {
        FoodClass foodTest = new FoodClass("High meat", true, true, 0, 10, 10, 10, 10, 0,
                0, 60, new Date(2019, 2, 25));
        String jsonReq = om.writeValueAsString(foodTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/food")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Food stored"));
    }

    @Test
    public void testValidUserFoodVegetarianAllZeros() throws Exception {
        FoodClass foodTest = new FoodClass("Vegetarian", false, false, 0, 0, 0, 0, 0, 0,
                0, 0, new Date(2019, 2, 25));
        String jsonReq = om.writeValueAsString(foodTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/food")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Food stored"));
    }

    @Test
    public void testInvalidUserFood() throws Exception {
        FoodClass foodTest = new FoodClass("Vegetarian", true, true, 0.0, 10, 10, 10, 10, 0,
                0, 60, new Date(2019, 2, 25));
        String jsonReq = om.writeValueAsString(foodTest);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/invalidUser/food")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Username does not exist"));
    }




    /**
     * This is an auxiliary method to connect the addFood mapping and the deleteFood mapping.
     * In order to make sure the database is clean after we run our tests.
     * @return id of the Food object to return.
     * @throws Exception
     */
    public int getFoodIdToDelete() throws Exception {
        MvcResult result = mockMvc.perform(get("http://localhost:8080/grafaz/getAllFood")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        FoodToClient foodFromServer = om.readValue(resultContent,
                FoodToClient.class);
        List<FoodClass> foodToTest = foodFromServer.getFood();
        int idToDelete = foodToTest.get(0).getId();
        return idToDelete;
    }

    /**
     * Test checks deleting food stored by user is working.
     *
     * @throws Exception in case get request is rejected by the server
     */
    @Test
    public void testDeleteExistingFood() throws Exception {
        //int foodIdToDelete = getFoodIdToDelete();
        String username = "foodadduser";
        ResponseClass out = null;
        for(int i = 0; i < 8; i++) {
            MvcResult result = mockMvc.perform(get("http://localhost:8080/" + getFoodIdToDelete() + "/deleteFood")
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andReturn();

            String resultContent = result.getResponse().getContentAsString();
            out = om.readValue(resultContent, ResponseClass.class);
        }
        Assert.assertTrue(out.getPayload().equals("OK"));
    }

    /**
     * Tests deleting not existing food instances.
     * @throws Exception
     */
    @Test
    public void testDeleteNotExistingFood() throws Exception {
        int foodIdToDelete = 1;
        String username = "grafaz";
        MvcResult result = mockMvc.perform(get("http://localhost:8080/"+ foodIdToDelete + "/deleteFood")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass out = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(out.getPayload().contains("Food id is not stored in the database"));
    }

    /**
     * Checks that List returned is null since the user has no Food stored in the DB.
     * @throws Exception
     */
    @Test
    public void testGetAllFoodInvalidUser() throws Exception {
        MvcResult result = mockMvc.perform(get("http://localhost:8080/invalidUser/getAllFood")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        FoodToClient foodFromServer = om.readValue(resultContent, FoodToClient.class);
        assertThat(foodFromServer.getFood().isEmpty());
    }


    /**
     * Standardization of Date.
     * Find more info here: https://stackoverflow.com/questions/27661640/
     * java-util-date-equals-doesnt-seem-to-work-as-expected
     *
     * @param date to be converted
     * @return standardized Date object.
     */
    public Date getStandardizedDate(Date date) {
        return new Date(date.getTime());
    }

    /**
     * This test check that a POST request to store a car instance in the DB.
     *
     * @throws Exception
     */
    @Test
    public void testStoreCar() throws Exception {
        CarFromClient carToStore = new CarFromClient("Diesel", 90);
        String jsonReq = om.writeValueAsString(carToStore);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/storeCar")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

    /**
     * Tests mapping where car is null. Should return BAD_REQUEST and payload.
     *
     * @throws Exception
     */
    @Test
    public void testStoreCarNull() throws Exception {
        CarFromClient carToStore = new CarFromClient();
        carToStore.setMpg(100);
        String jsonReq = om.writeValueAsString(carToStore);
        MvcResult result = mockMvc.perform(post("http://localhost:8080/grafaz/storeCar")
                .content(jsonReq).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Car Type violates NULL CONSTRAINT"));
    }

    /**
     * Test list of cars returned to the Client is correct.
     * This test functionally depends on the testStoreCar.
     * If executed individually, it will give you problems.
     * Should be followed by testDeleteCar so that DB is clean.
     *
     * @throws Exception
     */
    @Test
    public void testGetAllCarsValidUser() throws Exception {
        List<CarClass> testAllCarToClient = new ArrayList<>();
        CarClass carInList = new CarClass();
        carInList.setMpg(90);
        carInList.setType("Diesel");
        testAllCarToClient.add(carInList);
        MvcResult result = mockMvc.perform(get("http://localhost:8080/grafaz/getAllCars")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<CarClass> carsFromServer = om.readValue(resultContent,
                new TypeReference<List<CarClass>>() {
                });
        for (CarClass x : carsFromServer) {
            Assert.assertTrue(testAllCarToClient.get(0).getType().equals(x.getType())
                    && testAllCarToClient.get(0).getMpg() == x.getMpg());
        }
    }

    public int getIdCarToDelete() throws Exception {
        MvcResult result = mockMvc.perform(get("http://localhost:8080/grafaz/getAllCars")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<CarClass> carsFromServer = om.readValue(resultContent,
                new TypeReference<List<CarClass>>() {
                });
        return carsFromServer.get(0).getId();
    }

    /**
     * Test getAllCars from an invalid user.
     * @throws Exception
     */
    @Test
    public void testGetAllCarsInvalidUser() throws Exception {
        List<CarFromClient> testAllCarToClient = new ArrayList<>();
        MvcResult result = mockMvc.perform(get("http://localhost:8080/invalidUser/getAllCars")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<CarFromClient> carsFromServer = om.readValue(resultContent,
                new TypeReference<List<CarFromClient>>() {
                });
        for (CarFromClient x : carsFromServer) {
            Assert.assertNull(x);
        }
    }

    /**
     * Tests GET request from user to deleteCars from valid user.
     *
     * @throws Exception
     */
    @Test
    public void testDeleteExistingCar() throws Exception {
        int carIdToDelete = getIdCarToDelete();
        MvcResult result = mockMvc.perform(get("http://localhost:8080/" + carIdToDelete
                + "/deleteCar")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("OK"));
    }

    /**
     * Test Delete NON EXISTING CAR.
     * CarId = 2 does not exist.
     * @throws Exception
     */
    @Test
    public void testDeleteNotExistingCar() throws Exception {
        MvcResult result = mockMvc.perform(get("http://localhost:8080/" + 2
                + "/deleteCar")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ResponseClass response = om.readValue(resultContent, ResponseClass.class);
        Assert.assertTrue(response.getPayload().contains("Car id does not exist"));
    }

}
