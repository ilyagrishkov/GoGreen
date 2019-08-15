package server.envy.me.usertrips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTripService {

    @Autowired
    UserTripRepository userTripRepository;

    /**
     * Method returns a list of userTrip mappings.
     * @param userId of the user to retrieve information about.
     * @return
     */
    public List<UserTripClass> getListTripByUser(int userId) {
        List<UserTripClass> allTripUser = new ArrayList<>();
        for (UserTripClass s : userTripRepository.findAll()) {
            if (s.getUserId() == userId) {
                allTripUser.add(s);
            }
        }
        return allTripUser;
    }
}
