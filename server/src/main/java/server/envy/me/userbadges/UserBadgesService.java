package server.envy.me.userbadges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBadgesService {
    @Autowired
    UserBadgesRepository userBadgesRepository;

    /**
     * Returns List of UserBadgesClass entities.
     * The list contains achievements of the user.
     * @param userId of the user to retrieve badges about
     * @return
     */
    public List<UserBadgesClass> getBadgesIdByUserId(int userId) {
        List<UserBadgesClass> allBadges = userBadgesRepository.findAll();
        List<UserBadgesClass> result = new ArrayList<>();
        for (UserBadgesClass next : allBadges) {
            if (next.getUserId() == userId) {
                result.add(next);
            }
        }
        return result;
    }
}
