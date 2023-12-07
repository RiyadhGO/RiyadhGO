package sa.edu.yamamh.riyadhgo;


import com.google.android.gms.maps.model.LatLng;

import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;

public class DistanceUtils {

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371e3; // metres
        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double Δφ = Math.toRadians(lat2 - lat1);
        double Δλ = Math.toRadians(lon2 - lon1);

        double a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2)
                + Math.cos(φ1) * Math.cos(φ2)
                        * Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }



    private static double calculateDistanceInKiloMeters(double lat1, double lon1, double lat2, double lon2) {
        return calculateDistance(lat1, lon1, lat2, lon2) / 1000;
    }
    public static double getDistanceInKM(LatLng loc1, LatLng loc2)
    {
        return calculateDistanceInKiloMeters(loc1.latitude,loc1.longitude,loc2.latitude,loc2.longitude) ;
    }

    public static double getEstimatedTimeInMinutes(double distanceInKM, TransportMethodTypes methodType){
        switch (methodType){
            case WALK:
                return distanceInKM * 15;
            case SCOOTER:
                return distanceInKM * 5;
            case BUS:
                return distanceInKM * 7.30;
            case CAR:
                return distanceInKM * 2.30;
        }
        return distanceInKM * 2.30;
    }

    //calculate average carbon emitted per distance
    public static double getCarbonEmittedInGrams(double distance, TransportMethodTypes methodType){
        double carbonEmitted = 0.0;
        if(methodType == TransportMethodTypes.BUS){
            carbonEmitted = distance * 0.30;
        }
        return  carbonEmitted;
    }

}
