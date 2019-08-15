package server.envy.me.userfood;

import org.springframework.data.repository.CrudRepository;

public interface UserFoodRepository extends CrudRepository<UserFoodClass, Integer> {

    UserFoodClass findByFoodId(int foodId);

    UserFoodClass findByUserId(int userId);
}