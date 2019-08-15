package server.envy.me.badges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BadgesService {
    @Autowired
    BadgesRepository badgesRepository;
}
