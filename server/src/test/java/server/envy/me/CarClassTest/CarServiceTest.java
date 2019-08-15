package server.envy.me.CarClassTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.envy.me.car.CarClass;
import server.envy.me.car.CarRepository;
import server.envy.me.car.CarService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CarServiceTest {

    @InjectMocks
    CarService carService;

    @Mock
    CarRepository carRepository;

    /**
     * Test list returned is empty
     */
    @Test
    public void testGetListEmpty() {
        List<CarClass> listToReturn = new ArrayList<>();
        when(carService.getListCarByUserId(1)).thenReturn(listToReturn);
        Assert.assertTrue(carService.getListCarByUserId(1).isEmpty());
    }

    /**
     * Test list returned is NOT EMPTY
     */
    @Test
    public void testGetListNotEmpty() {
        List<CarClass> listToReturn = new ArrayList<>();
        listToReturn.add(new CarClass(1,"Electric",125, ""));
        when(carService.getListCarByUserId(1)).thenReturn(listToReturn);
        Assert.assertTrue(!carService.getListCarByUserId(1).isEmpty());
    }

    /**
     * Test only the correct UserTripClass is retrieved.
     */
    @Test
    public void testGetListDifferentUsers() {
        List<CarClass> listToReturn = new ArrayList<>();
        listToReturn.add(new CarClass(1,"Electric",125, ""));
        listToReturn.add(new CarClass(2,"Electric",125, ""));
        when(carService.getListCarByUserId(1)).thenReturn(listToReturn);
        Assert.assertTrue(carService.getListCarByUserId(1).size() == 1);
    }

    /**
     * Test not existing car ID.
     */
    @Test
    public void testNotExistingCar() {
        when(carRepository.findById(1)).thenReturn(null);
        Assert.assertTrue(carService.getUserId(1) == -1);
    }

    /**
     * Test existing car ID.
     */
    @Test
    public void testExistingCar() {
        CarClass carToReturn  = new CarClass(10, "Electric", 125, "");
        carToReturn.setId(1);
        when(carRepository.findById(1)).thenReturn(carToReturn);
        Assert.assertTrue(carService.getUserId(1) == 10);
    }
}
