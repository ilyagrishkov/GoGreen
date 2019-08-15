package server.envy.me.car;

public class CarFromClient {

    private String type;
    private int mpg;

    public CarFromClient(String type, int mpg) {
        this.type = type;
        this.mpg = mpg;
    }

    public CarFromClient() {
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
