package server.envy.me.car;

public class CarWithName {

    private String name;
    private String type;
    private int mpg;

    /**
     * Initializing Car With Name.
     * @param name Name of the car.
     * @param type Type of the car - Gas Car, Electric Car, Diesel Car
     * @param mpg Miles per gallon
     */
    public CarWithName(String name, String type, int mpg) {
        this.name = name;
        this.type = type;
        this.mpg = mpg;
    }

    /**
     * For Deserialization.
     */
    public CarWithName(){
    }

    /**
     * Transforms Class in a beautiful String.
     * @return returns string of the Class
     */
    @Override
    public String toString() {
        return "Name: " + getName() + ". Type: " + getType()
                + ". MPG: " + getMpg();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
