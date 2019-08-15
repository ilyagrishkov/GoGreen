package server.envy.me.food;

import java.util.ArrayList;
import java.util.List;

public class FoodToClient {

    List<FoodClass> food;

    public FoodToClient(List<FoodClass> food) {
        this.food = food;
    }

    public FoodToClient() {
        this.food = new ArrayList<FoodClass>();
    }

    public List<FoodClass> getFood() {
        return food;
    }

    public void setFood(List<FoodClass> food) {
        this.food = food;
    }
}
