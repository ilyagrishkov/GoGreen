package server.envy.me.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

}
