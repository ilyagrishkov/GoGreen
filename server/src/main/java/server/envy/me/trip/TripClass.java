package server.envy.me.trip;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Trips")
public class TripClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Column(name = "distance")
    private double distance;

    @Column(name = "datetime")
    private Date date;

    @Column(name = "carid")
    private Integer carId;

    /**
     * Constructor.
     * @param type of trip
     * @param source of trip
     * @param destination of trip
     * @param distance of trip
     * @param date of trip
     * @param carId nullable if type != Car
     */
    public TripClass(String type, String source, String destination,
                     double distance, Date date, Integer carId) {
        this.type = type;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.date = date;
        this.carId = carId;
    }

    public TripClass(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
