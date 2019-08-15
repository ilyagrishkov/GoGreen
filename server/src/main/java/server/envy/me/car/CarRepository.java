package server.envy.me.car;

import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<CarClass, Integer> {
    CarClass findById(int id);
}
