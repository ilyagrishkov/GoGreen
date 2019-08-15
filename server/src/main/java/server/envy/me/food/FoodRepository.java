package server.envy.me.food;

import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<FoodClass, Integer> {
    public FoodClass findById(int id);
}
