package sa.edu.yamamh.riyadhgo;


import android.util.Log;
import java.time.LocalDateTime;

import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;
import sa.edu.yamamh.riyadhgo.data.TripModel;

public class TripsUtil {

    public static TripModel BUS_TRIP;
    public static TripModel SCOOTER_TRIP;
    public static TripModel WALKING_TRIP;

    public static void addPointToBusTrip(double lat, double lng) {
        if (BUS_TRIP == null) {
            BUS_TRIP = new TripModel();
            BUS_TRIP.setStartLat(lat);
            BUS_TRIP.setStartLng(lng);
            BUS_TRIP.setStartTime(LocalDateTime.now());
        }
        BUS_TRIP.addRoute(lat, lng);
    }

    public static void addPointToScooterTrip(double lat, double lng) {
        if (SCOOTER_TRIP == null) {
            SCOOTER_TRIP = new TripModel();
            SCOOTER_TRIP.setStartLat(lat);
            SCOOTER_TRIP.setStartLng(lng);
            SCOOTER_TRIP.setStartTime(LocalDateTime.now());
        }
        SCOOTER_TRIP.addRoute(lat, lng);
    }

    public static void addPointToWalkingTrip(double lat, double lng) {
        if (WALKING_TRIP == null) {
            WALKING_TRIP = new TripModel();
            WALKING_TRIP.setStartLat(lat);
            WALKING_TRIP.setStartLng(lng);
            WALKING_TRIP.setStartTime(LocalDateTime.now());
        }
        WALKING_TRIP.addRoute(lat, lng);
    }

    public static void endBusTrip(double lat, double lng) {
        BUS_TRIP.setDestLat(lat);
        BUS_TRIP.setDestLng(lng);
        BUS_TRIP.setEndTime(LocalDateTime.now());
    }

    public static void endWalkingTrip(double lat, double lng) {
        WALKING_TRIP.setDestLat(lat);
        WALKING_TRIP.setDestLng(lng);
        WALKING_TRIP.setEndTime(LocalDateTime.now());
    }

    public static void endScooterTrip(double lat, double lng) {
        SCOOTER_TRIP.setDestLat(lat);
        SCOOTER_TRIP.setDestLng(lng);
        SCOOTER_TRIP.setEndTime(LocalDateTime.now());
    }


    public static double getCarbonEmitted()
    {
        if(BUS_TRIP==null)
        {
            return 0;
        }

        return calculateCO2Emission(BUS_TRIP.getDistanceInKM(),"petrol");
    }

    public static double getCarbonEmissionSaved()
    {

        double saved = 0;
        if(SCOOTER_TRIP != null)
        {
            saved +=  calculateCO2Emission(SCOOTER_TRIP.getDistanceInKM(),"petrol");
        }
        if(WALKING_TRIP != null)
        {
            saved += calculateCO2Emission(WALKING_TRIP.getDistanceInKM(), "petrol");
        }
        return saved;
    }

    public static double getCaloriesBurned()
    {
        double burned = 0;
        if(SCOOTER_TRIP != null)
        {
            burned += calculateCaloriesBurned(TransportMethodTypes.SCOOTER,SCOOTER_TRIP.getDurationInMinutes());
        }
        if(WALKING_TRIP != null)
        {
            burned += calculateCaloriesBurned(TransportMethodTypes.WALK,WALKING_TRIP.getDurationInMinutes());
        }
        return burned;
    }

    /**
     * Calculates the CO2 emission based on the distance travelled and the fuel type.
     *
     * @param distance The distance travelled in kilometers
     * @param fuelType The type of fuel used (diesel or petrol)
     * @return The CO2 emission in grams
     */


    private static double calculateCO2Emission(double distance, String fuelType) {
        double emissionFactor;
        double co2Emission;

        // Set the emission factor based on the fuel type
        if (fuelType.equalsIgnoreCase("diesel")) {
            emissionFactor = 2.68; // kg CO2 per liter of diesel
        } else if (fuelType.equalsIgnoreCase("petrol")) {
            emissionFactor = 2.31; // kg CO2 per liter of petrol
        } else {
            Log.w("TripsUtils", "Invalid fuel type specified");
            return 0;
        }

        // Calculate the CO2 emission
        co2Emission = (emissionFactor * distance); // g CO2 per km

        return co2Emission;
    }

    /**
     * Calculates the calories burned based on the activity and duration.
     *
     * @param {string} activity - The activity performed.
     * @param {number} duration - The duration of the activity in minutes.
     * @returns {number} The calories burned during the activity.
     * @throws {Error} Throws an error if the activity is not supported.
     */
    private static double calculateCaloriesBurned(TransportMethodTypes activity, double duration) {
        // Define the calorie burn rates per minute for different activities
        double walkingRate = 5;
        double scooterRate = 3;
        if(activity == TransportMethodTypes.WALK)
        {
            return duration * walkingRate;
        }
        if(activity == TransportMethodTypes.SCOOTER)
        {
            return duration * scooterRate;
        }
        return 0;
    }


    public static void resetData() {
        WALKING_TRIP = null;
        BUS_TRIP = null;
        SCOOTER_TRIP = null;
    }
}
