package server.envy.me.userfriends;

import java.util.List;

public class UserFriendsToClient {

    private List<String> friends;

    public UserFriendsToClient(List<String> friends) {
        this.friends = friends;
    }

    public UserFriendsToClient() {}

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
