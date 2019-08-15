package server.envy.me.achievements;

import java.util.ArrayList;
import java.util.List;

public class AchievementsToClient {
    private List<AchievementsClass> achievements;

    public AchievementsToClient(List<AchievementsClass> achievements) {
        this.achievements = achievements;
    }

    public AchievementsToClient() {
        this.achievements = new ArrayList<>();
    }

    public List<AchievementsClass> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<AchievementsClass> achievements) {
        this.achievements = achievements;
    }
}
