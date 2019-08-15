package server.envy.me.utilities;

import org.springframework.data.repository.CrudRepository;

public interface UtilitiesRepository extends CrudRepository<UtilitiesClass, Integer> {
    UtilitiesClass findById(int id);
}
