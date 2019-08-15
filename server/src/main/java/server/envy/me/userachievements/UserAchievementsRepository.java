package server.envy.me.userachievements;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserAchievementsRepository extends CrudRepository<UserAchievementsClass, Integer> {
    UserAchievementsClass findByUserIdAndAchievementsId(int userId, int achievementsId);

    List<UserAchievementsClass> findAll();
}
