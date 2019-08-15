package server.envy.me.userbadges;

import java.io.Serializable;

public class UserBadgesCompositeKey implements Serializable {

    private int userId;
    private int badgesId;

    public UserBadgesCompositeKey(int userId, int badgesId) {
        this.userId = userId;
        this.badgesId = badgesId;
    }

    public UserBadgesCompositeKey() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBadgesId() {
        return badgesId;
    }

    public void setBadgesId(int badgesId) {
        this.badgesId = badgesId;
    }
}
