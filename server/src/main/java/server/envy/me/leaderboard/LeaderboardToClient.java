package server.envy.me.leaderboard;

public class LeaderboardToClient {

    private String username;
    private int score;

    public LeaderboardToClient(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public LeaderboardToClient() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

