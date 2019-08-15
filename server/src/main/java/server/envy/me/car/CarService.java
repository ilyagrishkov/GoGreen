package server.envy.me.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    /**
     * Get List of cars owned by user.
     * @param userId of the user to retrieve cars of
     * @return
     */
    public List<CarClass> getListCarByUserId(int userId) {
        List<CarClass> allCarUser = new ArrayList<>();
        for (CarClass s : carRepository.findAll()) {
            if (s.getUserid() == userId) {
                allCarUser.add(s);
            }
        }
        return allCarUser;
    }

    /**
     * Returns userId of the car owner.
     * @param id of the user.
     * @return
     */
    public int getUserId(int id) {
        if (carRepository.findById(id) != null) {
            return carRepository.findById(id).getUserid();
        } else {
            return -1;
        }
    }
}
