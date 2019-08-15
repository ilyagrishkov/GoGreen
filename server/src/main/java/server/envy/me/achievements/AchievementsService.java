package server.envy.me.achievements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementsService {

    @Autowired
    AchievementsRepository achievementsRepository;

}
