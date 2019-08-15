package server.envy.me.leaderboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "leaderboard")
public class LeaderboardClass {

    @Id
    @Column(name = "userid", unique = true)
    private int userId;

    @Column(name = "score")
    private int score;

    /**
     * Constructor Leaderboard Class.
     * @param userId of the user
     * @param score of the user
     */
    public LeaderboardClass(int userId, int score) {
        this.userId = userId;
        this.score = score;
    }

    public LeaderboardClass() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Function necessary to have an ordered Leaderboard.
     * @param other object to be compared to.
     * @return
     */
    public int compareTo(LeaderboardClass other) {
        if (this.score >= other.score) {
            return 1;
        } else {
            return 0;
        }
    }
}
