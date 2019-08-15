package server.envy.me.userachievements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAchievementsService {

    @Autowired
    UserAchievementsRepository userAchievementsRepository;

    /**
     * Returns List of UserAchievementClass entities.
     * The list contains achievements of the user.
     * @param userId of the user to retrieve achievements about
     * @return
     */
    public List<UserAchievementsClass> getAchievementsIdByUserId(int userId) {
        List<UserAchievementsClass> allAchievements = userAchievementsRepository.findAll();
        List<UserAchievementsClass> result = new ArrayList<>();
        for (UserAchievementsClass next : allAchievements) {
            if (next.getUserId() == userId) {
                result.add(next);
            }
        }
        return result;
    }
}
