package server.envy.me.trip;

import java.util.ArrayList;
import java.util.List;

public class TripToClient {

    List<TripClass> trips;

    public TripToClient(List<TripClass> trips) {
        this.trips = trips;
    }

    public TripToClient() {
        this.trips = new ArrayList<TripClass>();
    }

    public List<TripClass> getTrips() {
        return trips;
    }

    public void setTrips(List<TripClass> trips) {
        this.trips = trips;
    }
}
