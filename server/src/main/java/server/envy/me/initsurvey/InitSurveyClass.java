package server.envy.me.initsurvey;

/**
 * Class for the initial survey.
 */
public class InitSurveyClass {
    /**
     * Username belonging to the survey.
     */
    private static String username;

    /**
     * Users type of car. Electric, Diesel, or Gas.
     */
    private static String carType;

    /**
     * User's electricity bill price.
     */
    private static String elecbill;

    /**
     * User's gas bill price.
     */
    private static String gasBill;

    /**
     * User's usual meal composition.
     */
    private static String mealComp;

    /**
     * Number of people in User's household.
     */
    private static int nbofPeople;

    /**
     * Initial Survey Class constructor.
     *
     * @param username User's username.
     * @param carType user's car type.
     * @param elecbill user's electricity bill price.
     * @param gasbill user's gas bill price.
     * @param nbofPeople number of people in user's household.
     * @param mealComp user's usual meal composition.
     */
    public InitSurveyClass(String username, String carType, String elecbill,
                           String gasbill, int nbofPeople, String mealComp) {
        this.username = username;
        this.carType = carType;
        this.elecbill = elecbill;
        this.gasBill = gasbill;
        this.nbofPeople = nbofPeople;
        this.mealComp = mealComp;
    }

    /**
     * For Deserialization purposes.
     */
    public InitSurveyClass() {
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        InitSurveyClass.username = username;
    }

    public static String getCarType() {
        return carType;
    }

    public static void setCarType(String carType) {
        InitSurveyClass.carType = carType;
    }

    public static String getElecBill() {
        return elecbill;
    }

    public static void setElecBill(String elecBill) {
        InitSurveyClass.elecbill = elecBill;
    }

    public static String getGasBill() {
        return gasBill;
    }

    public static void setGasBill(String gasBill) {
        InitSurveyClass.gasBill = gasBill;
    }

    public static String getMealComp() {
        return mealComp;
    }

    public static void setMealComp(String mealComp) {
        InitSurveyClass.mealComp = mealComp;
    }

    public static int getNbofPeople() {
        return nbofPeople;
    }

    public static void setNbofPeople(int nbofPeople) {
        InitSurveyClass.nbofPeople = nbofPeople;
    }

    /**
     * Brings this class to a readable format.
     *
     * @return toString of the InitSurveyClass
     */
    public String toString() {
        return "INIT SURVEY: " + getUsername() + " : "
                + getCarType() + " : "
                + getElecBill() + " : "
                + getGasBill() + " : "
                + getNbofPeople() + " : "
                + getMealComp();
    }
}
