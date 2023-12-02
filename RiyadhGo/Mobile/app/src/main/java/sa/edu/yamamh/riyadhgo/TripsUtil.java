package sa.edu.yamamh.riyadhgo;


import java.time.LocalDateTime;

import sa.edu.yamamh.riyadhgo.data.TripModel;

public class TripsUtil {

    public static TripModel BUS_TRIP;
    public static TripModel SCOOTER_TRIP;
    public static TripModel WALKING_TRIP;

    public static void addPointToBusTrip(float lat,float lng)
    {
        if(BUS_TRIP == null)
        {
            BUS_TRIP = new TripModel();
            BUS_TRIP.setStartLat(lat);
            BUS_TRIP.setStartLng(lng);
            BUS_TRIP.setStartTime(LocalDateTime.now());
        }
        BUS_TRIP.addRoute(lat,lng);
    }
    public static void addPointToScooterTrip(float lat,float lng)
    {
        if(SCOOTER_TRIP == null)
        {
            SCOOTER_TRIP = new TripModel();
            SCOOTER_TRIP.setStartLat(lat);
            SCOOTER_TRIP.setStartLng(lng);
            SCOOTER_TRIP.setStartTime(LocalDateTime.now());
        }
        SCOOTER_TRIP.addRoute(lat,lng);
    }
    public static void addPointToWalkingTrip(float lat,float lng)
    {
        if(WALKING_TRIP == null)
        {
            WALKING_TRIP = new TripModel();
            WALKING_TRIP.setStartLat(lat);
            WALKING_TRIP.setStartLng(lng);
            WALKING_TRIP.setStartTime(LocalDateTime.now());
        }
        WALKING_TRIP.addRoute(lat,lng);
    }

    public static void endBusTrip()
    {
        BUS_TRIP.setEndTime(LocalDateTime.now());
    }

    public static void endWalkingTrip()
    {
        WALKING_TRIP.setEndTime(LocalDateTime.now());
    }

    public static void endScooterTrip()
    {
        SCOOTER_TRIP.setEndTime(LocalDateTime.now());
    }


    public static double getCarbonEmitted()
    {
        return 0;
    }

    public static double getCaloriesBurned()
    {
        return 0;
    }
}
