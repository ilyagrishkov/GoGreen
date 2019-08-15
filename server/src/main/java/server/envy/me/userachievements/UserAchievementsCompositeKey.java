package server.envy.me.userachievements;

import java.io.Serializable;

public class UserAchievementsCompositeKey implements Serializable {

    private int userId;
    private int achievementsId;

    public UserAchievementsCompositeKey(int userId, int achievementsId) {
        this.userId = userId;
        this.achievementsId = achievementsId;
    }

    public UserAchievementsCompositeKey() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAchievementsId() {
        return achievementsId;
    }

    public void setAchievementsId(int achievementsId) {
        this.achievementsId = achievementsId;
    }
}
