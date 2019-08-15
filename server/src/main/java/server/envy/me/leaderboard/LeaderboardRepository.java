package server.envy.me.leaderboard;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LeaderboardRepository extends CrudRepository<LeaderboardClass, Integer> {
    List<LeaderboardClass> findAll();

    LeaderboardClass findById(int id);
}
