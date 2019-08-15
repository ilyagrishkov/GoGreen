package server.envy.me.userfood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFoodService {

    @Autowired
    private UserFoodRepository userFoodRepository;

    /**
     * Find UserFoodClass by foodId.
     * @param foodId to find the instance
     * @return instance of the class
     */
    private UserFoodClass getByFoodId(int foodId) {
        return userFoodRepository.findByFoodId(foodId);
    }

    /**
     * Returns a list of UserFoodClass object with all the food recorded.
     * by user with id : userId
     * @param userId of the user
     * @return List of food mappings
     */
    public List<UserFoodClass> getListFoodByUserId(int userId) {
        List<UserFoodClass> allFoodUser = new ArrayList<>();
        for (UserFoodClass s : userFoodRepository.findAll()) {
            if (s.getUserId() == userId) {
                allFoodUser.add(s);
            }
        }
        return allFoodUser;
    }
}
