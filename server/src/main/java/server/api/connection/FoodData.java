package server.api.connection;

public class FoodData {

    int meatfisheggs;
    int dairy;
    int otherfood;
    int veggies;
    int cereals;

    /**
     * Constructor for FoodData.
     * @param meatfisheggs user calories per day meat/fish/eggs
     * @param dairy        user calories per day dairy
     * @param otherfood    user calories per day other food
     * @param veggies      user calories per day fruits & veggies
     * @param cereals      user calories per day cereals/bread
     */
    public FoodData(int meatfisheggs, int dairy, int otherfood, int veggies, int cereals) {
        this.meatfisheggs = meatfisheggs;
        this.dairy = dairy;
        this.otherfood = otherfood;
        this.veggies = veggies;
        this.cereals = cereals;
    }
}
