package server.envy.me.achievements;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AchievementsRepository extends CrudRepository<AchievementsClass, Integer> {
    AchievementsClass findById(int id);

    List<AchievementsClass> findAll();
}
