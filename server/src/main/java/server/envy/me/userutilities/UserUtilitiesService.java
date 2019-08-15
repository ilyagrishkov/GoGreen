package server.envy.me.userutilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserUtilitiesService {
    @Autowired
    UserUtilitiesRepository userUtilitiesRepository;

    /**
     * Returns a list of UtilitiesClass objects.
     * @param userId of the user to retrieve Utilities about
     * @return
     */
    public List<UserUtilitiesClass> getUtilitiesByUserId(int userId) {
        List<UserUtilitiesClass> allUtilities = userUtilitiesRepository.findAll();
        List<UserUtilitiesClass> result = new ArrayList<>();
        for (UserUtilitiesClass next : allUtilities) {
            if (next.getUserId() == userId) {
                result.add(next);
            }
        }
        return result;
    }
}
