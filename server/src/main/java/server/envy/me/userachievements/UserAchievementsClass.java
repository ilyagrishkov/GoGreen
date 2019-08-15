package server.envy.me.userachievements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@IdClass(UserAchievementsCompositeKey.class)
@Entity
@Table(name = "userachievements")
public class UserAchievementsClass {

    @Id
    @Column(name = "userid")
    private int userId;

    @Id
    @Column(name = "achievementsid")
    private int achievementsId;

    @Column(name = "progress")
    private int progress;

    /**
     * Constructor.
     * @param userId of the user
     * @param achievementsId of the achievement
     * @param progress stores progress of the user wrt the specific achievement
     */
    public UserAchievementsClass(int userId, int achievementsId, int progress) {
        this.userId = userId;
        this.achievementsId = achievementsId;
        this.progress = progress;
    }

    public UserAchievementsClass() {}

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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
