package server.envy.me.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public FoodClass getFood(int id) {
        return foodRepository.findById(id);
    }
}
