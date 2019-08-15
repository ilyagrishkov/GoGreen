package server.envy.me.car;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Car")
public class CarClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "userid")
    private int userid;

    @Column(name = "type")
    private String type;

    @Column(name = "mpg")
    private int mpg;

    @Column(name = "name")
    private String name;

    /**
     * Constructor with parameters.
     * @param userid of the owner of the car
     * @param type of car
     * @param mpg (miles per hour)
     */
    public CarClass(int userid, String type, int mpg, String name) {
        this.userid = userid;
        this.type = type;
        this.mpg = mpg;
        this.name = name;
    }

    public CarClass() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMpg() {
        return mpg;
    }

    public void setMpg(int mpg) {
        this.mpg = mpg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
