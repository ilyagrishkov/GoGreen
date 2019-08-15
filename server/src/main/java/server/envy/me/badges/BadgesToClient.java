package server.envy.me.badges;

import java.util.ArrayList;
import java.util.List;

public class BadgesToClient {
    private List<BadgesClass> badges;

    public BadgesToClient(List<BadgesClass> badges) {
        this.badges = badges;
    }

    public BadgesToClient() {
        this.badges = new ArrayList<>();
    }

    public List<BadgesClass> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgesClass> badges) {
        this.badges = badges;
    }
}
