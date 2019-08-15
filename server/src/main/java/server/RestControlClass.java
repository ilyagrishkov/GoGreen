package server;

import com.google.maps.errors.ApiException;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.api.connection.Connection;
import server.api.connection.FoodData;
import server.api.connection.TransportationApi;
import server.api.connection.UtilitiesData;
import server.envy.me.achievements.AchievementsClass;
import server.envy.me.achievements.AchievementsRepository;
import server.envy.me.achievements.AchievementsToClient;
import server.envy.me.badges.BadgesClass;
import server.envy.me.badges.BadgesRepository;
import server.envy.me.badges.BadgesService;
import server.envy.me.badges.BadgesToClient;
import server.envy.me.car.CarClass;
import server.envy.me.car.CarRepository;
import server.envy.me.car.CarService;
import server.envy.me.car.CarWithName;
import server.envy.me.food.FoodClass;
import server.envy.me.food.FoodRepository;
import server.envy.me.food.FoodToClient;
import server.envy.me.initsurvey.InitSurveyClass;
import server.envy.me.leaderboard.LeaderboardClass;
import server.envy.me.leaderboard.LeaderboardRepository;
import server.envy.me.leaderboard.LeaderboardService;
import server.envy.me.leaderboard.LeaderboardToClient;
import server.envy.me.login.LoginClass;
import server.envy.me.login.LoginRepository;
import server.envy.me.registration.RegistrationClass;
import server.envy.me.registration.RegistrationRepository;
import server.envy.me.registration.RegistrationService;
import server.envy.me.response.ResponseClass;
import server.envy.me.settings.SettingsClass;
import server.envy.me.settings.SettingsRepository;
import server.envy.me.trip.CO2Calculator;
import server.envy.me.trip.TripClass;
import server.envy.me.trip.TripRepository;
import server.envy.me.trip.TripToClient;
import server.envy.me.userachievements.UserAchievementsClass;
import server.envy.me.userachievements.UserAchievementsRepository;
import server.envy.me.userachievements.UserAchievementsService;
import server.envy.me.userbadges.UserBadgesClass;
import server.envy.me.userbadges.UserBadgesRepository;
import server.envy.me.userbadges.UserBadgesService;
import server.envy.me.userfood.UserFoodClass;
import server.envy.me.userfood.UserFoodRepository;
import server.envy.me.userfood.UserFoodService;
import server.envy.me.userfriends.UserFriendsClass;
import server.envy.me.userfriends.UserFriendsPostClass;
import server.envy.me.userfriends.UserFriendsRepository;
import server.envy.me.userfriends.UserFriendsService;
import server.envy.me.userfriends.UserFriendsToClient;
import server.envy.me.usertrips.UserTripClass;
import server.envy.me.usertrips.UserTripRepository;
import server.envy.me.usertrips.UserTripService;
import server.envy.me.userutilities.UserUtilitiesClass;
import server.envy.me.userutilities.UserUtilitiesRepository;
import server.envy.me.userutilities.UserUtilitiesService;
import server.envy.me.utilities.UtilitiesClass;
import server.envy.me.utilities.UtilitiesFromClient;
import server.envy.me.utilities.UtilitiesRepository;
import server.envy.me.utilities.UtilitiesService;
import server.envy.me.utilities.UtilitiesToClient;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Control Class for all the servers RESTfullness.
 */
@WebAppConfiguration
@RestController
@SuppressWarnings("unused")
public class RestControlClass {

    @Autowired
    LoginRepository loginRepo;

    @Autowired
    RegistrationRepository registrationRepo;

    @Autowired
    FoodRepository foodRepo;

    @Autowired
    UserFoodRepository userFoodRepo;

    @Autowired
    UserFriendsRepository userFriendsRepo;

    @Autowired
    UserFriendsService userFriendsService;

    @Autowired
    UserFoodService userFoodService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarService carService;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    TransportationApi transportationApi;

    @Autowired
    CO2Calculator co2Calculator;

    @Autowired
    UserTripRepository userTripRepository;

    @Autowired
    UserTripService userTripService;

    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    AchievementsRepository achievementsRepository;

    @Autowired
    UserAchievementsRepository userAchievementsRepository;

    @Autowired
    UserAchievementsService userAchievementsService;

    @Autowired
    UserBadgesRepository userBadgesRepository;

    @Autowired
    UserBadgesService userBadgesService;

    @Autowired
    BadgesRepository badgesRepository;

    @Autowired
    BadgesService badgesService;

    @Autowired
    UtilitiesRepository utilitiesRepository;

    @Autowired
    UtilitiesService utilitiesService;

    @Autowired
    UserUtilitiesRepository userUtilitiesRepository;

    @Autowired
    UserUtilitiesService userUtilitiesService;

    @Autowired
    LeaderboardRepository leaderboardRepository;

    @Autowired
    LeaderboardService leaderboardService;

    @Autowired
    Connection connection;

    //https://ourworldindata.org/co2-and-other-greenhouse-gas-emissions
    //https://www.sciencedaily.com/releases/2008/04/080428120658.htm
    //average of tCO2e/year
    private int averageTco2eperYear = 20;

    //converted average into kg per day
    private int averagekgCo2eperday = (averageTco2eperYear / 365) * 1000;

    //The following final variables will store the average food consumption calories per meal.
    /**
     * FOOD.
     */
    private final int averageVeganScore = 53;
    private final int averageVegetarianScore = 51;
    private final int averagePescatarianScore = 81;
    private final int averageLowMeatScore = 82;
    private final int averageMediumMeat = 87;
    private final int averageHighMeatScore = 101;

    /**
     * TRANSPORTATION.
     */
    private final int averageTransporationConsumptionByKm = 82;

    /**
     * UTILITIES.
     */
    private final int defaultElectricity = 100;
    private final int defaultPerCleanEnergy = 50;
    private final int defaultWater = 35;
    private final int defaultGas = 19;
    private final int defaultOther = 0;


    /**
     * This will be the default POST request from client.
     * Returns FROM SERVER + what the client.
     *
     * @param payload simple POST message body
     * @return pinging the payload with an added message
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public final String answeringPost(@RequestBody final String payload) {
        return "From Server: " + payload;
    }

    /**
     * Default GET method returning a message.
     *
     * @return returns a basic message to see if it works
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public final String answeringGet() {
        return "You should send a POST request to see something interesting";
    }

    /**
     * Method for testing JSON get message.
     *
     * @return returns a JSON message to make sure it works
     */
    @RequestMapping(value = "/getJSON", method = RequestMethod.GET)
    public final ResponseEntity<ResponseClass> testingJsonGetRequest() {
        ResponseClass ret = new ResponseClass("This is the test GET JSON page");

        return new ResponseEntity<ResponseClass>(ret, HttpStatus.OK);
    }

    ////////////////////////////////////
    //          REGISTRATION          //
    ////////////////////////////////////

    /**
     * Method to answer registration a JSON POST request.
     *
     * @param newUserInfo JSON message to receive
     * @return returns a JSON response object
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public final ResponseEntity<ResponseClass> registration(
            @RequestBody final RegistrationClass newUserInfo) {

        ResponseClass out = new ResponseClass();

        if (userNotInUse(newUserInfo.getUsername())) {

            //Create instance of Login Info
            LoginClass userLoginInfo = new LoginClass();
            userLoginInfo.setPassword(newUserInfo.getPassword());
            userLoginInfo.setUsername(newUserInfo.getUsername());

            //Save LoginInfo in the database
            loginRepo.save(userLoginInfo);

            System.out.println("Login Info user: "
                    + newUserInfo.getUsername() + " saved in the database");

            //Get the id to store in RegistrationClass
            int id = loginRepo.findByUsername(newUserInfo.getUsername()).getId();

            //In order to map as a FK
            newUserInfo.setId(id);

            registrationRepo.save(newUserInfo);

            System.out.println("Registration Info user: "
                    + newUserInfo.getUsername() + " saved in the database");
            out.setPayload("OK");

            //Save in leaderboard as well with an initial score of 0
            leaderboardRepository.save(new LeaderboardClass(id, 0));
            System.out.println(newUserInfo.getUsername() + " stored in the leaderboard.");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        }
        out.setPayload("Username already taken");
        System.out.println("Username is already used");
        return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to deal with the initial survey mapping.
     *
     * @param payload JSON String of the InitSurveyClass
     * @return : payload
     */
    @RequestMapping(value = "/initialSurvey", method = RequestMethod.POST)
    public final ResponseEntity<ResponseClass> initialSurvey(@RequestBody final String payload) {
        //System.out.println(payload);
        ResponseClass out = new ResponseClass();

        try {
            JSONObject obj = new JSONObject(payload);
            String username = obj.getString("username");
            String cartype = obj.getString("carType");
            String gasbill = obj.getString("gasbill");
            String elecbill = obj.getString("elecbill");
            String mealComp = obj.getString("mealComp");
            int nbofPeople = obj.getInt("nbofPeople");

            InitSurveyClass survey = new InitSurveyClass(username, cartype,
                    elecbill, gasbill, nbofPeople, mealComp);

            System.out.println(survey.toString());
        } catch (JSONException e) {
            System.out.println("JSON error");

            out.setPayload("JSON error");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            System.out.println("null pointer exception");

            out.setPayload("Null Pointer error");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }

        out.setPayload("OK");
        return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
    }

    /**
     * Delete user from database in registration Table.
     *
     * @param delUserInfo : info of the userto delete
     * @return : payload
     */
    @RequestMapping(value = "/deleteRegistration", method = RequestMethod.POST)
    public final ResponseEntity<ResponseClass> deleteRegistration(
            @RequestBody final RegistrationClass delUserInfo) {
        final ResponseClass out = new ResponseClass();
        int id = loginRepo.findByUsername(delUserInfo.getUsername()).getId();
        settingsRepository.deleteById(id);
        leaderboardRepository.deleteById(id);
        registrationRepo.deleteById(id);
        loginRepo.deleteById(id);

        System.out.println("Deleting Registration from Database - " + delUserInfo.getUsername());

        out.setPayload("OK");
        return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
    }

    /**
     * Method checks if username is already in use.
     *
     * @param username to search if it has been already used.
     * @return
     */
    private boolean userNotInUse(String username) {
        boolean flag = true;
        LoginClass out = loginRepo.findByUsername(username);
        if (out != null) {
            flag = false;
        }
        return flag;
    }

    /**
     * The mapping returns user information.
     * The password attribute is set to null before sending the information.
     * * @param username of the user to retrieve information
     *
     * @return RegistrationClass instance.
     */
    @RequestMapping(value = "/{username}/getRegistrationInfo", method = RequestMethod.GET)
    public final ResponseEntity<RegistrationClass> getRegistrationInfo(
            @PathVariable String username) {
        int userId = loginRepo.findByUsername(username).getId();
        RegistrationClass userInfo = registrationService.getRegistrationInfo(userId);
        userInfo.setPassword(null);
        userInfo.setUsername(username);
        return new ResponseEntity<RegistrationClass>(userInfo, HttpStatus.OK);
    }

    ////////////////////////////////////
    //              LOGIN             //
    ////////////////////////////////////

    /**
     * Method to answer authorization a JSON POST request.
     *
     * @param userToAuth JSON message to receive
     * @return returns a JSON response object
     */
    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public final ResponseEntity<ResponseClass> authorization(
            @RequestBody final LoginClass userToAuth) {
        ResponseClass out = new ResponseClass();
        //Check user exists
        if (loginRepo.findByUsername(userToAuth.getUsername()) != null) {
            if (userToAuth.getPassword().equals(loginRepo.findByUsername(
                    userToAuth.getUsername()).getPassword())) {
                out.setPayload(loginRepo.findByUsername(userToAuth.getUsername()).getId() + "");
                return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
            }
        }
        out.setPayload("User : " + userToAuth.getUsername()
                + " is NOT authenticated");
        return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
    }


    ////////////////////////////////////
    //              FOOD              //
    ////////////////////////////////////

    /**
     * Method to store food info about a user.
     * The username is passed in as a PathVariable
     *
     * @param foodToStore - foodClass object to store
     * @return message
     */
    @RequestMapping(value = "/{username}/food", method = RequestMethod.POST)
    public final ResponseEntity<ResponseClass> food(
            @RequestBody final FoodClass foodToStore, @PathVariable String username) {

        ResponseClass out = new ResponseClass();
        if (loginRepo.findByUsername(username) != null) {

            foodToStore.setDate(new Timestamp(System.currentTimeMillis()));
            System.out.println(foodToStore.getDate());

            double meatfisheggs;
            double cereals;
            double veggies;
            double otherfood;
            double dairy;

            double tempMeat = foodToStore.getFish() + foodToStore.getEggs()
                    + foodToStore.getMeat() + foodToStore.getMeatAlt();
            //System.out.println(tempMeat);
            if (tempMeat == 0) {
                meatfisheggs = 0;
            } else {
                meatfisheggs = 0.01 * tempMeat;
            }

            double tempCereal = foodToStore.getGrains();
            //System.out.println(tempCereal);
            if (tempCereal == 0) {
                cereals = 0;
            } else {
                cereals = 0.01 * tempCereal;
            }

            double tempVeggies = foodToStore.getVeggies();
            //System.out.println(tempVeggies);
            if (tempVeggies == 0) {
                veggies = 0;
            } else {
                veggies = 0.01 * tempVeggies;
            }

            double tempOtherfood = foodToStore.getSnacks();
            //System.out.println(tempOtherfood);
            if (tempOtherfood == 0) {
                otherfood = 0;
            } else {
                otherfood = 0.01 * tempOtherfood;
            }

            double tempDairy = foodToStore.getDairy();
            //System.out.println(tempDairy);
            if (tempDairy == 0) {
                dairy = 0;
            } else {
                dairy = 0.01 * tempDairy;
            }

            foodRepo.save(foodToStore);
            int foodId = foodToStore.getId();
            int userId = loginRepo.findByUsername(username).getId();
            UserFoodClass userFoodRef = new UserFoodClass(foodId, userId);
            userFoodRepo.save(userFoodRef);

            int averageCalories = 575;

            double total = +meatfisheggs + cereals + veggies + otherfood + dairy;
            System.out.println("MeatFishEggs: " + meatfisheggs + " Cereals: " + cereals
                    + " Veggies: " + veggies + " Snacks: " + otherfood + " Dairy: " + dairy
                    + " Total: " + total);

            //Create food score
            FoodData foodData = new FoodData((int) (meatfisheggs * averageCalories),
                    (int) (dairy * averageCalories), (int) (otherfood * averageCalories),
                    (int) (veggies * averageCalories), (int) (cereals * averageCalories));
            UtilitiesData utilitiesData = new UtilitiesData(0, 0, 0, 0, 0, 0, 0);
            int rawScore = connection.getTotal("result_food_total", 0, 0, utilitiesData,
                    foodData);

            System.out.println("Raw Score: " + rawScore);

            if (rawScore > 0) {
                if (foodToStore.isCooked()) {
                    rawScore -= 50;
                }

                if (foodToStore.isLocal()) {
                    rawScore -= 50;
                }

                System.out.println("Raw Score: " + rawScore);

                int score = 0;

                //In order to compute the score of the meal, we compute the difference
                //Between an average meal of that type and the meal the user had.

                switch (foodToStore.getType()) {
                    case "Vegan":
                        score = averageVeganScore - rawScore;
                        break;
                    case "Vegetarian":
                        score = averageVegetarianScore - rawScore;
                        break;
                    case "Pescetarian":
                        score = averagePescatarianScore - rawScore;
                        break;
                    case "Low meat":
                        score = averageLowMeatScore - rawScore;
                        break;
                    case "Medium meat":
                        score = averageMediumMeat - rawScore;
                        break;
                    case "High meat":
                        score = averageHighMeatScore - rawScore;
                        break;
                    default:
                        score = 0;
                }

                System.out.println("SCORE IS HERE: " + score);

                LeaderboardClass newScore = new LeaderboardClass(userId,
                        leaderboardRepository.findById(userId).getScore() + score);

                leaderboardRepository.save(newScore);

                System.out.println("Score Saved in leaderboard.");
            }

            out.setPayload("Food stored");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        }
        out.setPayload("Username does not exist");
        return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
    }

    /**
     * This mapping deletes a food based on the id specified in the GET request.
     *
     * @param id of the foodClass to be deleted
     * @return 200 if food is deleted successfully, 400 otherwise
     */
    @RequestMapping(value = "/{id}/deleteFood", method = RequestMethod.GET)
    public final ResponseEntity<ResponseClass> deleteFood(@PathVariable int id) {
        ResponseClass out = new ResponseClass();
        if (foodRepo.findById(id) != null) {
            userFoodRepo.deleteById(id);
            foodRepo.deleteById(id);
            out.setPayload("OK");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            out.setPayload("Food id is not stored in the database");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping receives a GET request and sends back a list of FoodClass.
     * Of the meals recorded by the user who is logged into the app.
     *
     * @return a list of FoodClass objects.
     */
    @RequestMapping(value = "/{username}/getAllFood", method = RequestMethod.GET)
    public final ResponseEntity<FoodToClient> getAllFood(@PathVariable String username) {
        FoodToClient allFood = new FoodToClient();
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            List<UserFoodClass> listFoodIds = userFoodService.getListFoodByUserId(userId);
            for (UserFoodClass mapFood : listFoodIds) {
                allFood.getFood().add(foodRepo.findById(mapFood.getFoodId()));
            }
            return new ResponseEntity<FoodToClient>(allFood, HttpStatus.OK);
        } else {
            return new ResponseEntity<FoodToClient>(allFood, HttpStatus.BAD_REQUEST);
        }
    }

    ////////////////////////////////////
    //              CAR               //
    ////////////////////////////////////

    /**
     * This mapping stores a new car instance in the database.
     * Checks Type is not NULL since it is required in the DB.
     *
     * @param carFromClient - car Object storing type and mpg of the car
     * @param username      - of the user storing the car
     * @return - "OK" if car is stored, BAD_REQUEST otherwise
     */
    @RequestMapping(value = "/{username}/storeCar", method = RequestMethod.POST)
    public ResponseEntity<ResponseClass> storeCar(
            @RequestBody final CarWithName carFromClient, @PathVariable String username) {
        ResponseClass out = new ResponseClass();

        if (carFromClient.getType() != null) {
            CarClass carToStore = new CarClass();

            carToStore.setName(carFromClient.getName());
            carToStore.setType(carFromClient.getType());
            carToStore.setMpg(carFromClient.getMpg());

            int userId = loginRepo.findByUsername(username).getId();
            carToStore.setUserid(userId);
            carRepository.save(carToStore);

            out.setPayload("OK");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            out.setPayload("Car Type violates NULL CONSTRAINT");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This GET request returns a List of all the cars registered to the user.
     * Since the car from the client and the Car Class used to store information
     * in the database are two different classes, the client will get a List of Cars
     * in the format that he is also storing them.
     *
     * @param username of the user you want to retrieve cars of.
     * @return List of CarClass
     */
    @RequestMapping(value = "{username}/getAllCars", method = RequestMethod.GET)
    public final ResponseEntity<List<CarClass>> getAllCars(@PathVariable String username) {
        List<CarClass> allCarClasses = new ArrayList<CarClass>();
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            allCarClasses = carService.getListCarByUserId(userId);
            return new ResponseEntity<List<CarClass>>(allCarClasses, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<CarClass>>(allCarClasses, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Mapping to deletes a car corresponding to the id passed.
     * Communicates with the client through a GET request that includes the username.
     *
     * @param id of the car to be deleted.
     * @return ResponseClass based on the outcome.
     */
    @RequestMapping(value = "{id}/deleteCar", method = RequestMethod.GET)
    public final ResponseEntity<ResponseClass> deleteCarById(@PathVariable int id) {
        ResponseClass out = new ResponseClass();
        if (carRepository.findById(id) != null) {
            //Retrieving all the trips with the car and replacing the carId with NULL
            List<TripClass> tripsWithCar = tripRepository.findByCarId(id);
            for (TripClass each: tripsWithCar) {
                each.setCarId(null);
                tripRepository.save(each);
            }
            //There should not be any reference to carId at this point.
            carRepository.deleteById(id);
            out.setPayload("OK");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            out.setPayload("Car id does not exist");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }

    }

    private TripClass transformTripClass(String payload) {

        JSONObject obj = new JSONObject(payload);

        double distance;
        int carId;
        Integer car = null;
        try {
            distance = obj.getDouble("distance");
        } catch (JSONException e) {
            //e.printStackTrace();
            System.out.println("No Distance");
            distance = 0.0;
        }

        try {
            carId = obj.getInt("carId");
            System.out.println(carId);
        } catch (JSONException e) {
            carId = -1;
            System.out.println("No Car Trip");
        }


        if (carId != -1) {
            car = new Integer(carId);
        }

        return new TripClass(obj.getString("type"),
                obj.getString("source"),
                obj.getString("destination"), distance,
                new Timestamp(System.currentTimeMillis()),
                car);
    }

    ////////////////////////////////////
    //             TRIPS              //
    ////////////////////////////////////

    /**
     * Mapping store a new trip in the database.
     * The trips are classified in:
     * - Car
     * - Plane
     * - Public Transportation
     * - Bike or Walking
     *
     * @param payload  - TripClass instance from the client to store
     * @param username - of the user storing the trip
     * @return
     */
    @RequestMapping(value = "/{username}/storeTrip", method = RequestMethod.POST)
    public ResponseEntity<ResponseClass> storeTrip(
            @RequestBody final String payload,
            @PathVariable String username) throws InterruptedException, ApiException, IOException {
        ResponseClass out = new ResponseClass();

        TripClass tripToStore = transformTripClass(payload);

        if (loginRepo.findByUsername(username) == null) {
            out.setPayload("Username does not exist");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }

        tripToStore.setDate(new Timestamp(System.currentTimeMillis()));

        String tripType = tripToStore.getType();
        int userId = loginRepo.findByUsername(username).getId();

        if (tripType.contains("Car")) {
            Integer carId = tripToStore.getCarId();

            //Check car exists and belongs to the user
            if (carId != null && carRepository.findById(carId.intValue()) != null
                    && carService.getUserId(carId.intValue()) == userId) {

                System.out.println("Reached Here");

                if (tripToStore.getDistance() == 0) {
                    tripToStore.setDistance(transportationApi.getDriveDist(
                            tripToStore.getSource(),
                            tripToStore.getDestination()));
                }
                tripRepository.save(tripToStore);
                int tripId = tripToStore.getId();
                userTripRepository.save(new UserTripClass(tripId, userId));

                int rawScore = co2Calculator.getEmission(tripToStore) * 100;
                int score = (int) Math.ceil(averageTransporationConsumptionByKm
                        * tripToStore.getDistance() * 0.1 - rawScore);
                LeaderboardClass newScore = new LeaderboardClass(userId,
                        leaderboardRepository.findById(userId).getScore() + score);
                leaderboardRepository.save(newScore);

                out.setPayload("Car trip stored");
                return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
            } else {
                out.setPayload("Car does not exist or"
                        + " does not belong to the user"
                        + "or Car ID is not specified");

                System.out.println("Car Transportation Error...");
                return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
            }
        } else {

            return handleNonCarTrip(tripToStore, out, userId);
        }
    }

    /**
     * Computes different kind of distances based on the API.
     * Only if the distance given by the client is 0 (no input)
     * The different transportation types are:
     * - Car
     * - Plane
     * - Bike or Walk, considered taking the same distance
     * @param tripToStore - trip info
     * @param out - message to client
     * @param userId 0 ID of a user
     */
    private ResponseEntity<ResponseClass> handleNonCarTrip(TripClass tripToStore,
                                                           ResponseClass out, int userId)
            throws InterruptedException, ApiException, IOException {

        if (tripToStore.getDistance() == 0) {
            if (tripToStore.getType().contains("Plane")) {
                tripToStore.setDistance(transportationApi.getFlightDist(
                        tripToStore.getSource(),
                        tripToStore.getDestination()));
            } else if (tripToStore.getType().contains("PubTrans")) {
                tripToStore.setDistance(transportationApi.getTransitDist(
                        tripToStore.getSource(),
                        tripToStore.getDestination()));
            } else if (tripToStore.getType().contains("BikeWalk")) {
                tripToStore.setDistance(transportationApi.getCycleDist(
                        tripToStore.getSource(),
                        tripToStore.getDestination()));
            }
        }

        tripRepository.save(tripToStore);
        int tripId = tripToStore.getId();
        userTripRepository.save(new UserTripClass(tripId, userId));

        int rawScore = co2Calculator.getEmission(tripToStore);

        System.out.println(rawScore);

        int score = (int) Math.ceil(averageTransporationConsumptionByKm
                                    * tripToStore.getDistance() / 1000 - rawScore);

        System.out.println(score);

        LeaderboardClass newScore = new LeaderboardClass(userId,
                leaderboardRepository.findById(userId).getScore() + score);
        leaderboardRepository.save(newScore);

        out.setPayload("Trip stored");
        return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
    }

    /**
     * This mapping deletes a trip from the database using a GET request.
     * The Client needs to specify the id of the trip to delete in
     * the URL when making the request.
     *
     * @param id of the trip to delete
     * @return
     */
    @RequestMapping(value = "/{id}/deleteTrip", method = RequestMethod.GET)
    public ResponseEntity<ResponseClass> deleteTrip(
            @PathVariable int id) {
        ResponseClass out = new ResponseClass();
        System.out.print(tripRepository.findById(id));
        if (tripRepository.findById(id) != null) {
            userTripRepository.deleteById(id);
            tripRepository.deleteById(id);
            out.setPayload("OK");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            out.setPayload("Trip id is not stored in the database");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping returns the user a list of all trips.
     *
     * @param username of the user to retrieve trips of.
     * @return
     */
    @RequestMapping(value = "/{username}/getAllTrips", method = RequestMethod.GET)
    public final ResponseEntity<TripToClient> getAllTrips(@PathVariable String username) {
        TripToClient allTrips = new TripToClient();
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            List<UserTripClass> listTripIds = userTripService.getListTripByUser(userId);
            for (UserTripClass mapTrip : listTripIds) {
                allTrips.getTrips().add(tripRepository.findById(mapTrip.getTripId()));
            }
            return new ResponseEntity<TripToClient>(allTrips, HttpStatus.OK);
        } else {
            return new ResponseEntity<TripToClient>(allTrips, HttpStatus.BAD_REQUEST);
        }
    }

    ////////////////////////////////////
    //             FRIEND             //
    ////////////////////////////////////

    /**
     * This mapping receives a POST request and stores the friendship.
     * relationship in the database.
     *
     * @param friendToStore : JSON containing a String with the friend's username
     * @param username      : of the user adding friend
     * @return : HTTP200 if friend is stored, HTTP400 if the friend username does not exist
     */
    @RequestMapping(value = "/{username}/storeFriend", method = RequestMethod.POST)
    public final ResponseEntity<ResponseClass> friend(
            @RequestBody final UserFriendsPostClass friendToStore, @PathVariable String username) {
        ResponseClass out = new ResponseClass();
        if (loginRepo.findByUsername(friendToStore.getFriend()) != null
                && loginRepo.findByUsername(username) != null) {
            int user1Id = loginRepo.findByUsername(username).getId();
            int userId2 = loginRepo.findByUsername(friendToStore.getFriend()).getId();
            UserFriendsClass userFriends = new UserFriendsClass(user1Id, userId2);
            userFriendsRepo.save(userFriends);
            out.setPayload("Friend stored");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        }
        out.setPayload("Friend does not exist");
        return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
    }

    /**
     * This mapping returns a list of Friends of the user.
     *
     * @param username of the user to retrieve friends.
     * @return
     */
    @RequestMapping(value = "/{username}/getAllFriends", method = RequestMethod.GET)
    public final ResponseEntity<UserFriendsToClient> getFriends(
            @PathVariable String username) {
        List<String> out = new ArrayList<>();
        UserFriendsToClient friends = new UserFriendsToClient(out);
        if (loginRepo.findByUsername(username) != null) {
            int id = loginRepo.findByUsername(username).getId();
            List<Integer> listIds = userFriendsService.getListFriendsId(id);
            for (Integer friendId : listIds) {
                out.add(loginRepo.findById(friendId.intValue()).getUsername());
            }
            return new ResponseEntity<UserFriendsToClient>(friends, HttpStatus.OK);
        } else {
            return new ResponseEntity<UserFriendsToClient>(friends, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Mapping to delete friendship.
     *
     * @param friendToDelete - userFriendPostClass containing String of username
     * @return
     */
    @RequestMapping(value = "{username}/deleteFriend", method = RequestMethod.POST)
    public final ResponseEntity<ResponseClass> deleteFriend(
            @RequestBody final UserFriendsPostClass friendToDelete,
            @PathVariable String username) {
        ResponseClass out = new ResponseClass();
        if (loginRepo.findByUsername(username) != null
                && loginRepo.findByUsername(friendToDelete.getFriend()) != null) {
            int userId1 = loginRepo.findByUsername(username).getId();
            int userId2 = loginRepo.findByUsername(friendToDelete.getFriend()).getId();
            UserFriendsClass friendshipToDelete = new UserFriendsClass(userId1, userId2);
            userFriendsRepo.delete(friendshipToDelete);
            out.setPayload("Friendship deleted");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            out.setPayload("Friendship does not exist");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }
    }

    ////////////////////////////////////
    //            SETTINGS            //
    ////////////////////////////////////

    /**
     * This mapping adds new Settings in the database.
     *
     * @param settingsToStore - settings Class
     * @param username        of the user to store Settings info
     * @return
     */
    @RequestMapping(value = "/{username}/storeSettings", method = RequestMethod.POST)
    public ResponseEntity<ResponseClass> storeSettings(
            @RequestBody final SettingsClass settingsToStore,
            @PathVariable String username) {
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            settingsToStore.setUserId(userId);
            settingsRepository.save(settingsToStore);
            ResponseClass out = new ResponseClass("OK");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            ResponseClass out = new ResponseClass("Username does not exist");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping provides the client with settings information of the user.
     *
     * @param username of the user to retrieve settingsInfo
     * @return
     */
    @RequestMapping(value = "/{username}/getSettings", method = RequestMethod.GET)
    public ResponseEntity<SettingsClass> getSettings(
            @PathVariable String username) {
        SettingsClass settingsToSend = new SettingsClass();
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            settingsToSend = settingsRepository.findById(userId);
            return new ResponseEntity<SettingsClass>(settingsToSend, HttpStatus.OK);
        } else {
            return new ResponseEntity<SettingsClass>(settingsToSend, HttpStatus.BAD_REQUEST);
        }
    }

    ////////////////////////////////////
    //          ACHIEVEMENTS          //
    ////////////////////////////////////

    /**
     * This mapping returns a list of all achievements.
     *
     * @return
     */
    @RequestMapping(value = "/getAllAchievements", method = RequestMethod.GET)
    public ResponseEntity<AchievementsToClient> getAllAchievements() {
        AchievementsToClient toClient = new AchievementsToClient();
        toClient.setAchievements(achievementsRepository.findAll());
        return new ResponseEntity<AchievementsToClient>(toClient, HttpStatus.OK);
    }

    /**
     * This mapping returns the client information about an achievement.
     *
     * @param id of the achievement to retrieve information about.
     * @return
     */
    @RequestMapping(value = "/{id}/getAchievement", method = RequestMethod.GET)
    public ResponseEntity<AchievementsClass> getAchievement(
            @PathVariable int id) {
        if (achievementsRepository.findById(id) != null) {
            return new ResponseEntity<AchievementsClass>(achievementsRepository.findById(id),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<AchievementsClass>(new AchievementsClass(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Mapping returns List of AchievementsID obtained by the user.
     *
     * @param username of the user to get achievements of.
     * @return
     */
    @RequestMapping(value = "/{username}/getAllAchievements", method = RequestMethod.GET)
    public ResponseEntity<AchievementsToClient> getAllAchievementsByUser(
            @PathVariable String username) {
        AchievementsToClient classToClient = new AchievementsToClient();
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            List<UserAchievementsClass> listAchId = userAchievementsService
                    .getAchievementsIdByUserId(userId);
            for (UserAchievementsClass next : listAchId) {
                classToClient.getAchievements().add(achievementsRepository
                        .findById(next.getAchievementsId()));
            }
            return new ResponseEntity<AchievementsToClient>(classToClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<AchievementsToClient>(classToClient,
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping allows the Client to store an achievement of the user.
     *
     * @param fromClient - userAchievement Object from Client
     * @return
     */
    @RequestMapping(value = "/storeAchievement", method = RequestMethod.POST)
    public ResponseEntity<ResponseClass> addAchievement(
            @RequestBody UserAchievementsClass fromClient) {
        ResponseClass out = new ResponseClass();
        int achievementId = fromClient.getAchievementsId();
        if (loginRepo.findById(fromClient.getUserId()) != null
                && achievementsRepository.findById(achievementId) != null) {
            userAchievementsRepository.save(fromClient);
            out.setPayload("OK");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            out.setPayload("Incorrect information");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping returns the progress of a user wrt a specific achievement.
     *
     * @param username of the user to check progress.
     * @param achId    of the achievement to check.
     * @return
     */
    @RequestMapping(value = "/{username}/{achId}/getProgress", method = RequestMethod.GET)
    public ResponseEntity<Integer> getProgress(
            @PathVariable String username,
            @PathVariable int achId) {
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            if (achievementsRepository.findById(achId) != null) {
                if (userAchievementsRepository.findByUserIdAndAchievementsId(userId,
                        achId) != null) {
                    int progress = userAchievementsRepository
                            .findByUserIdAndAchievementsId(userId, achId).getProgress();
                    return new ResponseEntity<>(new Integer(progress), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new Integer(0), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new Integer(0), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new Integer(0), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping stores the progress of a user in the userAchievementRepository.
     *
     * @param username of the user to store progress.
     * @param achId    of the achievement to store progress related to.
     * @return
     */
    @RequestMapping(value = "/{username}/{achId}/{progress}/setProgress",
            method = RequestMethod.GET)
    public ResponseEntity<ResponseClass> setProgress(
            @PathVariable String username,
            @PathVariable int achId,
            @PathVariable int progress) {
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            if (achievementsRepository.findById(achId) != null) {
                UserAchievementsClass userAch = new UserAchievementsClass(userId, achId, progress);
                userAchievementsRepository.save(userAch);
                return new ResponseEntity<>(new ResponseClass("Progress is stored"),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseClass("Achievement does not exist"),
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ResponseClass("User does not exist"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    ////////////////////////////////////
    //             BADGES             //
    ////////////////////////////////////

    /**
     * This mapping returns a list of all badges.
     *
     * @return
     */
    @RequestMapping(value = "/getAllBadges", method = RequestMethod.GET)
    public ResponseEntity<BadgesToClient> getAllBadges() {
        BadgesToClient toClient = new BadgesToClient();
        toClient.setBadges(badgesRepository.findAll());
        return new ResponseEntity<BadgesToClient>(toClient, HttpStatus.OK);
    }

    /**
     * This mapping retrieves a Badge class by Id.
     *
     * @param id of the badge to Retrieve
     * @return
     */
    @RequestMapping(value = "/{id}/getBadge", method = RequestMethod.GET)
    public ResponseEntity<BadgesClass> getBadge(
            @PathVariable int id) {
        if (badgesRepository.findById(id) != null) {
            return new ResponseEntity<BadgesClass>(badgesRepository.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<BadgesClass>(new BadgesClass(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Mapping returns List of BadgesID obtained by the user.
     *
     * @param username of the user to retrieve information about.
     * @return
     */
    @RequestMapping(value = "/{username}/getAllBadges", method = RequestMethod.GET)
    public ResponseEntity<BadgesToClient> getAllBadgesId(
            @PathVariable String username) {
        BadgesToClient classToClient = new BadgesToClient();
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            List<UserBadgesClass> badgesId = userBadgesService.getBadgesIdByUserId(userId);
            for (UserBadgesClass next : badgesId) {
                classToClient.getBadges().add(badgesRepository.findById(next.getBadgesId()));
            }
            return new ResponseEntity<BadgesToClient>(classToClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<BadgesToClient>(classToClient, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping allows the Client to store a Badge achieved by the user.
     *
     * @param fromClient - UserBadgeClass to store in the DB.
     *                   See the class for more info about parameters.
     * @return
     */
    @RequestMapping(value = "/storeBadges", method = RequestMethod.POST)
    public ResponseEntity<ResponseClass> addBadge(
            @RequestBody UserBadgesClass fromClient) {
        ResponseClass out = new ResponseClass();
        int badgesId = fromClient.getBadgesId();
        if (loginRepo.findById(fromClient.getUserId()) != null
                && badgesRepository.findById(badgesId) != null) {
            userBadgesRepository.save(fromClient);
            out.setPayload("OK");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            out.setPayload("Incorrect information");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }
    }


    ////////////////////////////////////
    //            UTILITIES           //
    ////////////////////////////////////

    /**
     * This mapping returns an Utility by its ID.
     *
     * @param id of the utility
     * @return
     */
    @RequestMapping(value = "/{id}/getUtilities", method = RequestMethod.GET)
    public ResponseEntity<UtilitiesClass> getUtilities(
            @PathVariable int id) {
        if (utilitiesRepository.findById(id) != null) {
            return new ResponseEntity<UtilitiesClass>(utilitiesRepository.findById(id),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<UtilitiesClass>(new UtilitiesClass(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping returns the client a list of utilities ID.
     *
     * @param username of the user to retrieve information about.
     * @return
     */
    @RequestMapping(value = "/{username}/getAllUtilitiesId", method = RequestMethod.GET)
    public ResponseEntity<UtilitiesToClient> getAllUtilitiesId(
            @PathVariable String username) {
        UtilitiesToClient classToClient = new UtilitiesToClient();
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            List<UserUtilitiesClass> listUtilitiesId = userUtilitiesService
                    .getUtilitiesByUserId(userId);
            for (UserUtilitiesClass next : listUtilitiesId) {
                classToClient.getUtilities().add(utilitiesRepository
                        .findById(next.getUtilitiesId()));
            }
            return new ResponseEntity<UtilitiesToClient>(classToClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<UtilitiesToClient>(classToClient,
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping allows the Client to store utilities of the user.
     *
     * @param utilitiesFromClient - utilities Object from Client
     * @return
     */
    @RequestMapping(value = "/{username}/storeUtilities", method = RequestMethod.POST)
    public ResponseEntity<ResponseClass> storeUtilities(
            @RequestBody UtilitiesFromClient utilitiesFromClient,
            @PathVariable String username) {
        ResponseClass out = new ResponseClass();
        if (loginRepo.findByUsername(username) != null) {
            utilitiesFromClient.setDate(new Timestamp(System.currentTimeMillis()));
            //System.out.println(utilitiesFromClient.getDate());

            UtilitiesClass fromClient = new UtilitiesClass(
                    utilitiesFromClient.getElectricityBill(),
                    utilitiesFromClient.getCleanEnergyPercentage(),
                    utilitiesFromClient.getWatersewage(),
                    utilitiesFromClient.getNatgas(),
                    utilitiesFromClient.getRoomTemp(),
                    utilitiesFromClient.getDate());

            System.out.println(fromClient.toString());

            int userId = loginRepo.findByUsername(username).getId();
            utilitiesRepository.save(fromClient);
            int utilitiesId = fromClient.getId();
            userUtilitiesRepository.save(new UserUtilitiesClass(userId,
                    utilitiesId));

            int housingSquareFeet;

            try {
                housingSquareFeet = (int) (settingsRepository
                        .findById(userId).getLivingSpace() * 10.764);

                if (housingSquareFeet < 10) {
                    housingSquareFeet = 506;
                }
            } catch (NullPointerException e) {
                System.out.println("null point in housing square feet.");
                housingSquareFeet = 506;
            }

            SettingsClass settingsOfClient = settingsRepository.findById(userId);

            //utilities score
            //Create food score

            UtilitiesData defaultUtilities = new UtilitiesData(housingSquareFeet,
                    defaultElectricity, defaultGas, defaultWater, defaultPerCleanEnergy,
                    settingsOfClient.getGasPrice(), settingsOfClient.getNatGasPrice());

            UtilitiesData utilitiesData = new UtilitiesData(housingSquareFeet,
                    fromClient.getElectricity(), fromClient.getGas(), fromClient.getWater(),
                    fromClient.getPerCleanEnergy(), settingsOfClient.getGasPrice(),
                    settingsOfClient.getNatGasPrice());

            FoodData foodData = new FoodData(0, 0, 0, 0, 0);

            int defaultScore = connection.getTotal("result_housing_total",
                    1, (int) settingsOfClient.getElectricityPrice(),
                    defaultUtilities, foodData);

            int rawScore = connection.getTotal("result_housing_total", 1,
                    (int) settingsOfClient.getElectricityPrice(),
                    utilitiesData, foodData);

            int score = defaultScore - rawScore;

            if (utilitiesFromClient.isSolarPanels()) {
                score = (3 * score) / 4;
            }

            System.out.println("SCORE IS HERE: " + score);

            LeaderboardClass newScore = new LeaderboardClass(userId,
                    leaderboardRepository.findById(userId).getScore() + score);
            leaderboardRepository.save(newScore);
            System.out.println("Score Saved in leaderboard.");

            out.setPayload("OK");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            out.setPayload("Invalid username");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }
    }

    ////////////////////////////////////
    //           LEADERBOARD          //
    ////////////////////////////////////

    /**
     * Returns the client a leaderboard sorted by users score.
     * The sorting is done on the client.
     * The length of the returned list does not exceed 100.
     *
     * @return
     */
    @RequestMapping(value = "/getLeaderboard", method = RequestMethod.GET)
    public ResponseEntity<List<LeaderboardClass>> getLeaderboard() {
        List<LeaderboardClass> leaderboardToReturn = leaderboardRepository.findAll();
        Collections.sort(leaderboardToReturn, new Comparator<LeaderboardClass>() {
            @Override
            public int compare(LeaderboardClass o1, LeaderboardClass o2) {
                if (o1.getScore() >= o2.getScore()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return new ResponseEntity<List<LeaderboardClass>>(leaderboardToReturn
                .subList(0, Math.min(leaderboardToReturn.size(),
                        100)), HttpStatus.OK);
    }

    /**
     * This mapping returns the friends Leaderboard of the user specified by username.
     * The format is the following: each object contains:
     * - Username of the friend
     * - Score of the friend
     *
     * @return
     */
    @RequestMapping(value = "{username}/getFriendsLeaderboard", method = RequestMethod.GET)
    public ResponseEntity<List<LeaderboardToClient>> getFriendsLeaderboard(
            @PathVariable String username) {
        List<LeaderboardClass> leaderboard = new ArrayList<>();
        List<LeaderboardToClient> friendsLeaderboard = new ArrayList<>();

        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            List<Integer> listFriends = userFriendsService.getListFriendsId(userId);
            leaderboard = leaderboardRepository.findAll();

            Collections.sort(leaderboard, new Comparator<LeaderboardClass>() {
                @Override
                public int compare(LeaderboardClass o1, LeaderboardClass o2) {
                    if (o1.getScore() >= o2.getScore()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });

            for (LeaderboardClass userScore : leaderboard) {
                if (listFriends.contains(userScore.getUserId())) {
                    friendsLeaderboard.add(new LeaderboardToClient(loginRepo
                            .findById(userScore.getUserId()).getUsername(),
                            userScore.getScore()));
                }
            }

            for (LeaderboardToClient l : friendsLeaderboard) {
                System.out.println(l.getUsername());
            }

            return new ResponseEntity<List<LeaderboardToClient>>(friendsLeaderboard
                    .subList(0, Math.min(friendsLeaderboard.size(),
                            100)), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<LeaderboardToClient>>(friendsLeaderboard,
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mapping allows to update a user score in the leaderboard.
     *
     * @param username of the user to update to score of.
     * @param score    to be inserted in the leaderboard.
     * @return
     */
    @RequestMapping(value = "{username}/{score}/updateLeaderboard", method = RequestMethod.GET)
    public ResponseEntity<ResponseClass> getFriendsLeaderboard(
            @PathVariable String username,
            @PathVariable int score) {
        ResponseClass out = new ResponseClass();
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            leaderboardRepository.save(new LeaderboardClass(userId, score));
            out.setPayload("OK");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.OK);
        } else {
            out.setPayload("Incorrect username");
            return new ResponseEntity<ResponseClass>(out, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This mappings returns the score of the user.
     *
     * @param username of the user to retrieve score of.
     * @return
     */
    @RequestMapping(value = "/{username}/getScore", method = RequestMethod.GET)
    public ResponseEntity<Integer> getUserScore(
            @PathVariable String username
    ) {
        if (loginRepo.findByUsername(username) != null) {
            int userId = loginRepo.findByUsername(username).getId();
            Integer score = leaderboardRepository.findById(userId).getScore();
            return new ResponseEntity<Integer>(score, HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(new Integer(0), HttpStatus.BAD_REQUEST);
        }
    }

    ////////////////////////////////////
    //              EXTRA             //
    ////////////////////////////////////

    /**
     * This mapping returns a list of usernames similar to the one given in the path.
     *
     * @param start - Input string from the user to check in the DB.
     * @return a list of String with usernames similar to the one given.
     */
    @RequestMapping(value = "{start}/getUsernames", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getUsernames(
            @PathVariable String start
    ) {
        List<String> similarUsers = new ArrayList<>();
        List<LoginClass> listAllUsers = loginRepo.findAll();
        for (LoginClass user : listAllUsers) {
            if (user.getUsername().startsWith(start)) {
                similarUsers.add(user.getUsername());
            }
            if (similarUsers.size() >= 10) {
                break;
            }
        }
        return new ResponseEntity<List<String>>(similarUsers, HttpStatus.OK);
    }

}