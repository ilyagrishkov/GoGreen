package server.envy.me.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilitiesService {
    @Autowired
    UtilitiesRepository utilitiesRepository;
}
