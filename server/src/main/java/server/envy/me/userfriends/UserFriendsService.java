package server.envy.me.userfriends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFriendsService {

    @Autowired
    private UserFriendsRepository userFriendsRepository;

    /**
     * Finds userfriends relationship based on userId.
     * @param userId1 of the user to find relationship
     * @param userId2 of the user to find relationship
     * @return
     */
    public UserFriendsClass getByUserId(int userId1, int userId2) {
        if (userFriendsRepository.findByUserId1AndUserId2(userId1, userId2) != null) {
            return userFriendsRepository.findByUserId1AndUserId2(userId1, userId2);
        } else {
            return null;
        }
    }

    /**
     * Return a list of IDs of the friends of the user.
     * @param userId of the user to retrieve friends of.
     * @return
     */
    public List<Integer> getListFriendsId(int userId) {
        List<Integer> friendsIds = new ArrayList<>();
        List<UserFriendsClass> friendsIterator = userFriendsRepository.findAll();
        for (UserFriendsClass next : friendsIterator) {
            if (next.getUserId1() == userId || next.getUserId2() == userId) {
                if (next.getUserId1() == userId) {
                    friendsIds.add(next.getUserId2());
                } else {
                    friendsIds.add(next.getUserId1());
                }
            }
        }
        return friendsIds;
    }
}