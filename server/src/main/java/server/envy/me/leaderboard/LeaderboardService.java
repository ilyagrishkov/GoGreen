package server.envy.me.leaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaderboardService {

    @Autowired
    LeaderboardRepository leaderboardRepository;
}
