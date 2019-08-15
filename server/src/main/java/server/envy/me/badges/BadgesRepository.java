package server.envy.me.badges;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgesRepository extends CrudRepository<BadgesClass, Integer> {
    List<BadgesClass> findAll();

    BadgesClass findById(int id);
}
