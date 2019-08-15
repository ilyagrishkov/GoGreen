package server.envy.me.usertrips;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usertrips")
public class UserTripClass {

    @Id
    @Column(name = "tripid")
    private int tripId;

    @Column(name = "userid")
    private int userId;

    public UserTripClass(int tripId, int userId) {
        this.tripId = tripId;
        this.userId = userId;
    }

    public UserTripClass(){}

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
