package server.envy.me.userfriends;

import java.io.Serializable;

public class UserFriendsMapping implements Serializable {

    private int userId1;
    private int userId2;

    public UserFriendsMapping(){
    }

    public UserFriendsMapping(int userId1, int userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    public int getUserId1() {
        return userId1;
    }

    public void setUserId1(int userId1) {
        this.userId1 = userId1;
    }

    public int getUserId2() {
        return userId2;
    }

    public void setUserId2(int userId2) {
        this.userId2 = userId2;
    }
}
