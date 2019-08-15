package server.api.connection;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@Service
public class Connection {
    /**
     * HTTPRequest OK code.
     */
    private static final int RESPONSE_OK = 200;
    /**
     * List from ksimms2 on github.
     * link: https://github.com/ksimms2/GreenPrint/blob/master/app/src/main/java/edu/cnm/deepdive/green_print/controller/CC_API.java.
     */
    private static String[] requiredKeys = {
        "input_size=",
        "input_footprint_household_adults=",
        "input_footprint_housing_squarefeet=",
        "input_footprint_housing_electricity_dollars=",
        "input_footprint_housing_naturalgas_dollars=",
        "input_footprint_shopping_food_meatfisheggs=", //Calories eaten daily of meat, fish and eggs
        "input_footprint_shopping_food_dairy=",                      //Calories eaten daily of dairy
        "input_footprint_shopping_food_otherfood=",                    //Calories per day other food
        "input_footprint_shopping_food_fruitvegetables=",     //Calories per day fruits and veggies
        "input_footprint_shopping_food_cereals=",
        "input_footprint_housing_watersewage=",
        "input_footprint_housing_cleanpercent=",                       //Assume no clean energy
        "input_takeaction_assumption_kwhprice=",//12
        "input_takeaction_assumption_price_of_gasoline=",//13
        "input_takeaction_assumption_ngprice=",//14

        "input_footprint_transportation_num_vehicles=0",
        "input_footprint_transportation_fuel1=0",
        "input_footprint_transportation_mpg1=0",
        "input_footprint_transportation_miles1=0",

        "internal_state_abbreviation=AK",
        "input_takeaction_switch_to_cfl=0",
        "input_takeaction_maintain_my_vehicles=0",
        "input_takeaction_more_efficient_vehicle=0",
        "input_takeaction_reduce_air_travel=0",
        "input_takeaction_telecommute_to_work=0",
        "input_takeaction_thermostat_summer=0",
        "input_takeaction_offset_housing=0",
        "input_takeaction_energy_star_fridge=0",
        "input_takeaction_thermostat_winter=0",
        "input_takeaction_offset_transportation=0",
        "input_takeaction_ride_my_bike=0",
        "input_takeaction_purchase_green_electricity=0",
        "input_takeaction_take_public_transportation=0",
        "input_takeaction_carpool_to_work=0",
        "input_takeaction_practice_eco_driving=0",
        "input_footprint_household_children=0",         // How many children occupy the user's house
        "input_footprint_housing_heatingoil_dollars=0",
        "input_footprint_housing_hdd=0",                // Days that the house is heated/year
        "input_footprint_housing_cdd=0",               // Days that the house is cooled/year
        "input_footprint_transportation_airtotal=0",  //assume no air trips
        "input_footprint_transportation_publictrans=0", //same here
        "input_changed=0",                                              // Meaningless variable
        "input_footprint_transportation_num_vehicles=0",  // Number of vehicles is assumed to be 3
        "input_footprint_transportation_fuel2=1",
        "input_footprint_transportation_fuel3=1",
        "input_footprint_transportation_miles2=0",
        "input_footprint_transportation_miles3=0",
        "input_footprint_transportation_miles4=0",
        "input_footprint_transportation_mpg2=0",
        "input_footprint_transportation_mpg3=0",
        "input_footprint_transportation_mpg4=0",
        "input_footprint_transportation_fuel4=1",
        "input_footprint_transportation_miles5=0",
        "input_footprint_transportation_mpg5=0",
        "input_footprint_transportation_fuel5=1",
        "input_footprint_transportation_miles6=0",
        "input_footprint_transportation_mpg6=0",
        "input_footprint_transportation_fuel6=1",
        "input_footprint_transportation_miles7=0",
        "input_footprint_transportation_mpg7=0",
        "input_footprint_transportation_fuel7=1",
        "input_footprint_transportation_miles8=0",
        "input_footprint_transportation_mpg8=0",
        "input_footprint_transportation_fuel8=1",
        "input_footprint_transportation_miles9=0",
        "input_footprint_transportation_mpg9=0",
        "input_footprint_transportation_fuel9=1",
        "input_footprint_transportation_miles10=0",
        "input_footprint_transportation_mpg10=0",
        "input_footprint_transportation_fuel10=1",
        "input_footprint_transportation_airtype=simple", // Total miles covered for air
        "input_footprint_transportation_groundtype=simple", // Total miles for public transportation
        "input_footprint_housing_electricity_type=0",                   // In $/year
        "input_footprint_housing_naturalgas_type=0",                    // In $/year
        "input_footprint_housing_heatingoil_type=0",                    // In $/year
        "input_footprint_housing_heatingoil_dollars_per_gallon=4", //Average is $4/gallon
        "input_footprint_shopping_food_meattype=simple",           // Meat consumed by the user
        "input_footprint_shopping_goods_default_furnitureappliances=0",
        "input_footprint_shopping_goods_default_clothing=0",            // Clothing cost/year
        "input_footprint_shopping_goods_default_other_entertainment=0", // Entertainment cost/year
        "input_footprint_shopping_goods_default_other_office=0",        // Office supplies cost/year
        "input_footprint_shopping_goods_default_other_personalcare=0",  // Personal care cost/year
        "input_footprint_shopping_goods_default_other_autoparts=0",     // Auto cost/year
        "input_footprint_shopping_goods_default_other_medical=0",
        "input_footprint_shopping_goods_type=advanced",
        "input_footprint_shopping_goods_total=0", // No input by the user, sum of the subtotals
        "input_footprint_shopping_services_type=simple",
        "input_footprint_shopping_services_total=0",
        "input_footprint_housing_gco2_per_kwh=1000"
    };
    /**
     * Corresponds to the different types of results to get.
     */
    private static String[] different_types = {
        "result_motor_vehicles_direct",
        "result_motor_vehicles_indirect",
        "result_vehicle_manufact",
        "result_air_travel_direct",
        "result_air_travel_indirect",
        "result_electricity_direct",
        "result_electricity_indirect",
        "result_publictrans_direct",
        "result_publictrans_indirect",
        "result_natgas_direct",
        "result_natgas_indirect",
        "result_heatingoil_direct",
        "result_heatingoil_indirect",
        "result_watersewage",
        "result_shelter",
        "result_food_meat",
        "result_food_dairy",
        "result_food_other",
        "result_food_fruitsveg",
        "result_food_cereals",
        "result_goods_clothing",
        "result_goods_furniture",
        "result_goods_other",
        "result_services_all",
        "result_transport_total",
        "result_housing_total",
        "result_food_total",
        "result_goods_total",
        "result_services_total",
        "result_grand_total"
    };
    /**
     * Base API URL.
     */
    private static String BASE_URL = "https://apis.berkeley.edu/coolclimate/footprint?input_location_mode=1&input_location=99500&input_income=1";

    private class Utilities {

        int squarefeet;
        int elecBill;
        int gasBill;
        int watersewage;
        int cleanpercent;
        double priceGasoline;
        double priceNatGas;

        public Utilities(int squarefeet, int elecBill, int gasBill, int watersewage,
                         int cleanpercent, double priceGasoline, double priceNatGas) {
            this.squarefeet = squarefeet;
            this.elecBill = elecBill;
            this.gasBill = gasBill;
            this.watersewage = watersewage;
            this.cleanpercent = cleanpercent;
            this.priceGasoline = priceGasoline;
            this.priceNatGas = priceNatGas;
        }
    }

    private class Food {

        int meatfisheggs;
        int dairy;
        int otherfood;
        int veggies;
        int cereals;

        public Food(int meatfisheggs, int dairy, int otherfood, int veggies, int cereals) {
            this.meatfisheggs = meatfisheggs;
            this.dairy = dairy;
            this.otherfood = otherfood;
            this.veggies = veggies;
            this.cereals = cereals;
        }
    }

    /**
     * Returns kg of CO2e per day depending on input.
     *
     * @param type         type of result to get
     * @param inputSize    number of people in household
     * @param utilities    utilities data
     * @param food         food data
     * @return Returns kg of CO2e per day
     */
    public int getTotal(String type, int inputSize, int centsperkwh, UtilitiesData utilities,
                        FoodData food) {

        int count = 0;
        for (String indivType : different_types) {
            if (type.equals(indivType)) {
                count++;
            }
        }

        if (count == 0) {
            System.out.println("Wrong Result type");
            return -5;
        }

        requiredKeys[0] += inputSize;
        requiredKeys[1] += inputSize;
        requiredKeys[2] += utilities.squarefeet;
        requiredKeys[3] += utilities.elecBill * 12; //yearly average
        requiredKeys[4] += utilities.gasBill * 12; //same
        requiredKeys[5] += food.meatfisheggs;
        requiredKeys[6] += food.dairy;
        requiredKeys[7] += food.otherfood;
        requiredKeys[8] += food.veggies;
        requiredKeys[9] += food.cereals;
        requiredKeys[10] += utilities.watersewage * 12;
        requiredKeys[11] += utilities.cleanpercent;
        requiredKeys[12] += centsperkwh;
        requiredKeys[13] += utilities.priceGasoline;
        requiredKeys[14] += utilities.priceNatGas;

        StringBuilder finalurl = new StringBuilder(BASE_URL);

        for (String parameter : requiredKeys) {
            finalurl.append("&" + parameter);
        }


        try {
            HttpRequest httpRequest = new HttpRequest(finalurl.toString());

            HttpData httpData = httpRequest.sendGet();

            if (httpData.getResponseCode() == RESPONSE_OK) {
                System.out.println(httpData.getData());

                try {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

                    //converting string into xml document
                    Document doc = docBuilder.parse(
                            new InputSource(new StringReader(httpData.getData())));

                    doc.getDocumentElement().normalize();

                    NodeList nodelist = doc.getElementsByTagName("response");

                    double tempResult = getResult(nodelist, type);

                    requiredKeys[0] = "input_size=";
                    requiredKeys[1] = "input_footprint_household_adults=";
                    requiredKeys[2] = "input_footprint_housing_squarefeet=";
                    requiredKeys[3] = "input_footprint_housing_electricity_dollars=";
                    requiredKeys[4] = "input_footprint_housing_naturalgas_dollars=";
                    requiredKeys[5] = "input_footprint_shopping_food_meatfisheggs=";
                    requiredKeys[6] = "input_footprint_shopping_food_dairy=";
                    requiredKeys[7] = "input_footprint_shopping_food_otherfood=";
                    requiredKeys[8] = "input_footprint_shopping_food_fruitvegetables=";
                    requiredKeys[9] = "input_footprint_shopping_food_cereals=";
                    requiredKeys[10] = "input_footprint_housing_watersewage=";
                    requiredKeys[11] = "input_footprint_housing_cleanpercent=";
                    requiredKeys[12] = "input_takeaction_assumption_kwhprice=";//12
                    requiredKeys[13] = "input_takeaction_assumption_price_of_gasoline=";//13
                    requiredKeys[14] = "input_takeaction_assumption_ngprice=";//14

                    if (type.equals("result_food_total")) {
                        //dividing by 3 for 1 meal
                        return (int) tempResult / 3;
                    } else {
                        //returns kg per day
                        return (int) tempResult;
                    }
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("IOException when sending to API");
        }

        requiredKeys[0] = "input_size=";
        requiredKeys[1] = "input_footprint_household_adults=";
        requiredKeys[2] = "input_footprint_housing_squarefeet=";
        requiredKeys[3] = "input_footprint_housing_electricity_dollars=";
        requiredKeys[4] = "input_footprint_housing_naturalgas_dollars=";
        requiredKeys[5] = "input_footprint_shopping_food_meatfisheggs=";
        requiredKeys[6] = "input_footprint_shopping_food_dairy=";
        requiredKeys[7] = "input_footprint_shopping_food_otherfood=";
        requiredKeys[8] = "input_footprint_shopping_food_fruitvegetables=";
        requiredKeys[9] = "input_footprint_shopping_food_cereals=";
        requiredKeys[10] = "input_footprint_housing_watersewage=";
        requiredKeys[11] = "input_footprint_housing_cleanpercent=";
        requiredKeys[12] = "input_takeaction_assumption_kwhprice=";//12
        requiredKeys[13] = "input_takeaction_assumption_price_of_gasoline=";//13
        requiredKeys[14] = "input_takeaction_assumption_ngprice=";//14

        return -5;
    }

    private static double getResult(NodeList nodelist, String type) {
        for (int temp = 0; temp < nodelist.getLength(); temp++) {

            Node node = nodelist.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                String result = element.getElementsByTagName(type)
                        .item(0).getTextContent();

                return ((Double.parseDouble(result)) / 365) * 1000 * 100;
            }
        }

        return -5.0;
    }
}
