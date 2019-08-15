package server.envy.me.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.envy.me.car.CarClass;
import server.envy.me.car.CarRepository;

@Service
public class CO2Calculator {
    /**
     * Hardcoded values to compute the emission costs of transportation.
     */

    private static final int airplane = 150;

    private static final int pubTrans = 90;

    private static final int carBenzine = 133;

    private static final int carDiesel = 125;

    private static final int carLpg = 113;

    private static final int carElectric = 0;

    private static final int carHybrid = 27;

    private static final int bikeWalk = 0;

    private static final int carGeneric = 100;

    @Autowired
    CarRepository carRepository;

    /**
     * Method to compute the CO2 emission of a particular trip.
     * The hardcoded values are explained in the report.
     * The papers backing up the values will be referenced in there.
     * @param tripClass of the trip to compute value of.
     * @return
     */
    public int getEmission(TripClass tripClass) {
        double distance = tripClass.getDistance();
        double score = 0;
        if (tripClass.getType().contains("Car")) {
            int carId = tripClass.getCarId();
            CarClass car = carRepository.findById(carId);
            String carType = car.getType();
            if (carType.contains("Benzine")) {
                score = carBenzine * distance;
            } else if (carType.contains("Diesel")) {
                score = carDiesel * distance;
            } else if (carType.contains("Lpg")) {
                score = carLpg * distance;
            } else if (carType.contains("Electric")) {
                score = carElectric * distance;
            } else if (carType.contains("Hybrid")) {
                score = carHybrid * distance;
            } else {
                score = carGeneric * distance;
            }
        } else if (tripClass.getType().contains("Plane")) {
            score = airplane * distance;
        } else if (tripClass.getType().contains("PubTrans")) {
            score = pubTrans * distance;
        } else if (tripClass.getType().contains("BikeWalk")) {
            score = bikeWalk * distance;
        }
        return (int) Math.floor(score / 1000);
    }

}
