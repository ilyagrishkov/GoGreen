package server.envy.me.userbadges;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "userbadges")
@IdClass(UserBadgesCompositeKey.class)
public class UserBadgesClass {

    @Id
    @Column(name = "userid")
    private int userId;

    @Id
    @Column(name = "badgesid")
    private int badgesId;

    public UserBadgesClass(int userId, int badgesId) {
        this.userId = userId;
        this.badgesId = badgesId;
    }

    public UserBadgesClass() {}

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
