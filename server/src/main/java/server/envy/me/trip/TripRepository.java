package server.envy.me.trip;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripRepository extends CrudRepository<TripClass, Integer> {

    TripClass findById(int id);

    List<TripClass> findByCarId(int carId);
}
