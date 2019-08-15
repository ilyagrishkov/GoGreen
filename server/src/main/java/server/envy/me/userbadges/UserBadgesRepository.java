package server.envy.me.userbadges;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserBadgesRepository extends CrudRepository<UserBadgesClass, Integer> {
    UserBadgesClass findByUserIdAndBadgesId(int userId, int badgesId);

    List<UserBadgesClass> findAll();
}
