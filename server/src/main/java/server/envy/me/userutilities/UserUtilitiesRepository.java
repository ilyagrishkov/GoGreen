package server.envy.me.userutilities;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserUtilitiesRepository extends CrudRepository<UserUtilitiesClass, Integer> {

    List<UserUtilitiesClass> findAll();

    UserUtilitiesClass findByUserIdAndUtilitiesId(int userId, int utilitiesId);
}
