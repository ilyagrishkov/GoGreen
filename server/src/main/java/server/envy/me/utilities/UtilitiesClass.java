package server.envy.me.utilities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "utilities")
public class UtilitiesClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "electricity")
    private int electricity;

    @Column(name  = "percleanenergy")
    private int perCleanEnergy;

    @Column(name = "water")
    private int water;

    @Column(name = "gas")
    private int gas;

    @Column(name = "other")
    private int other;

    @Column(name = "datetime")
    private Date datetime;

    /**
     * Constructor.
     * @param electricity of the last bill
     * @param perCleanEnergy of the last bill
     * @param water of the last bill
     * @param gas of the last bill
     * @param other of the last bill
     * @param datetime of the last bill
     */
    public UtilitiesClass(int electricity, int perCleanEnergy, int water,
                          int gas, int other, Date datetime) {
        this.electricity = electricity;
        this.perCleanEnergy = perCleanEnergy;
        this.water = water;
        this.gas = gas;
        this.other = other;
        this.datetime = datetime;
    }

    public UtilitiesClass() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getElectricity() {
        return electricity;
    }

    public void setElectricity(int electricity) {
        this.electricity = electricity;
    }

    public int getPerCleanEnergy() {
        return perCleanEnergy;
    }

    public void setPerCleanEnergy(int perCleanEnergy) {
        this.perCleanEnergy = perCleanEnergy;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return this.getDatetime() + " " + this.getWater() + " " + this.getElectricity() + " "
                + this.getOther() + " " + this.getPerCleanEnergy() + " " + this.getGas();
    }
}
