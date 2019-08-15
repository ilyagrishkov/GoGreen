package server.envy.me.userfriends;

public class UserFriendsPostClass {
    private String friend;

    public UserFriendsPostClass() {}

    public UserFriendsPostClass(String friend) {
        this.friend = friend;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }
}
