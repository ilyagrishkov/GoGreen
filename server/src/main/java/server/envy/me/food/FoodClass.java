package server.envy.me.food;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Food")
public class FoodClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "local")
    private boolean local;

    @Column(name = "cooked")
    private boolean cooked;

    @Column(name = "meat")
    private double meat;

    @Column(name = "fish")
    private double fish;

    @Column(name = "meatalt")
    private double meatAlt;

    @Column(name = "eggs")
    private double eggs;

    @Column(name = "grains")
    private double grains;

    @Column(name = "dairy")
    private double dairy;

    @Column(name = "veggies")
    private double veggies;

    @Column(name = "snacks")
    private double snacks;

    @Column(name = "datetime")
    private Date date;

    /**
     * Constructor with parameters.
     * @param type of meal
     * @param local if bought from local producers
     * @param cooked if cooked at home
     * @param meat percentage in the meal
     * @param fish percentage in the meal
     * @param meatAlt percentage in the meal
     * @param eggs percentage in the meal
     * @param grains percentage in the meal
     * @param dairy percentage in the meal
     * @param veggies percentage in the meal
     * @param snacks percentage in the meal
     * @param date percentage in the meal
     */
    public FoodClass(String type, boolean local, boolean cooked,
                     double meat, double fish, double meatAlt, double eggs, double grains,
                     double dairy, double veggies, double snacks, Date date) {
        this.type = type;
        this.local = local;
        this.cooked = cooked;
        this.meat = meat;
        this.fish = fish;
        this.meatAlt = meatAlt;
        this.eggs = eggs;
        this.grains = grains;
        this.dairy = dairy;
        this.veggies = veggies;
        this.snacks = snacks;
        this.date = date;
    }

    /**
     * Empty constructor for deserialization.
     */
    public FoodClass(){
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isCooked() {
        return cooked;
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    public double getMeat() {
        return meat;
    }

    public void setMeat(double meat) {
        this.meat = meat;
    }

    public double getFish() {
        return fish;
    }

    public void setFish(double fish) {
        this.fish = fish;
    }

    public double getMeatAlt() {
        return meatAlt;
    }

    public void setMeatAlt(double meatAlt) {
        this.meatAlt = meatAlt;
    }

    public double getEggs() {
        return eggs;
    }

    public void setEggs(double eggs) {
        this.eggs = eggs;
    }

    public double getGrains() {
        return grains;
    }

    public void setGrains(double grains) {
        this.grains = grains;
    }

    public double getDairy() {
        return dairy;
    }

    public void setDairy(double dairy) {
        this.dairy = dairy;
    }

    public double getVeggies() {
        return veggies;
    }

    public void setVeggies(double veggies) {
        this.veggies = veggies;
    }

    public double getSnacks() {
        return snacks;
    }

    public void setSnacks(double snacks) {
        this.snacks = snacks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
