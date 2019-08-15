package server.envy.me.TripClassTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.envy.me.car.CarClass;
import server.envy.me.car.CarRepository;
import server.envy.me.trip.CO2Calculator;
import server.envy.me.trip.TripClass;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CO2CalculatorMockito {

    @InjectMocks
    CO2Calculator co2Calculator;

    @Mock
    CarRepository carRepository;

    private static final int airplane = 150;

    private static final int pubTrans = 90;

    private static final int carBenzine = 133;

    private static final int carDiesel = 125;

    private static final int carLpg = 113;

    private static final int carElectric = 0;

    private static final int carHybrid = 27;

    private static final int bikeWalk = 0;

    private static final int carGeneric = 100;

    private static final double distance = 22.54;

    CarClass car = new CarClass(1,"",1,"");

    TripClass tripTest = new TripClass("","","",distance,null,1);

    @Test
    public void testEmissionPlane() {
        int result = (int) Math.floor(distance * airplane / 1000);
        tripTest.setType("Plane");
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == result);
    }

    @Test
    public void testEmissionPubTrans() {
        int result = (int) Math.floor(distance * pubTrans / 1000);
        tripTest.setType("PubTrans");
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == result);
    }

    @Test
    public void testEmissionBikeWalk() {
        int result = (int) Math.floor(distance * bikeWalk / 1000);
        tripTest.setType("BikeWalk");
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == result);
    }

    @Test
    public void testEmissionCarElectric() {
        int result = (int) Math.floor(distance * carElectric / 1000);
        tripTest.setType("Car");
        car.setType("Electric");
        when(carRepository.findById(1)).thenReturn(car);
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == result);
    }

    @Test
    public void testEmissionCarBenzine() {
        int result = (int) Math.floor(distance * carBenzine / 1000);
        tripTest.setType("Car");
        car.setType("Benzine");
        when(carRepository.findById(1)).thenReturn(car);
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == result);
    }

    @Test
    public void testEmissionCarDiesel() {
        int result = (int) Math.floor(distance * carDiesel / 1000);
        tripTest.setType("Car");
        car.setType("Diesel");
        when(carRepository.findById(1)).thenReturn(car);
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == result);
    }

    @Test
    public void testEmissionCarHybrid() {
        int result = (int) Math.floor(distance * carHybrid / 1000);
        tripTest.setType("Car");
        car.setType("Hybrid");
        when(carRepository.findById(1)).thenReturn(car);
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == result);
    }

    @Test
    public void testEmissionCarLpg() {
        int result = (int) Math.floor(distance * carLpg / 1000);
        tripTest.setType("Car");
        car.setType("Lpg");
        when(carRepository.findById(1)).thenReturn(car);
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == result);
    }

    @Test
    public void testEmissionCarGeneric() {
        int result = (int) Math.floor(distance * carGeneric / 1000);
        tripTest.setType("Car");
        car.setType("");
        when(carRepository.findById(1)).thenReturn(car);
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == result);
    }

    @Test
    public void testScoreZero() {
        tripTest.setType("Not Defined");
        Assert.assertTrue(co2Calculator.getEmission(tripTest) == 0);
    }
}
