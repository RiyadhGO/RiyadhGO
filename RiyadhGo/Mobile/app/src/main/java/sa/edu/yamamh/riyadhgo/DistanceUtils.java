package sa.edu.yamamh.riyadhgo;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;

public class DistanceUtils {




    private static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515; //distance in miles.
        if (unit == 'K') {
            dist = dist * 1.609344; //distance multiplied by kilometers per mile.
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public static double getDistanceInKM(LatLng loc1, LatLng loc2)
    {
        //float[] results = new float[2];
        //Location.distanceBetween(loc1.latitude, loc1.longitude, loc2.latitude, loc2.longitude, results);
        //return distance(loc1.latitude,loc1.longitude,loc2.latitude,loc2.longitude,'K');
        return SphericalUtil.computeDistanceBetween(loc1, loc2) / 1000.0;
        //return results[0] / 1000;
    }

    public static double getEstimatedTimeInMinutes(double distanceInKM, TransportMethodTypes methodType){
        switch (methodType){
            case WALK:
                return distanceInKM * 10;
            case SCOOTER:
                return distanceInKM * 5;
            case BUS:
                return distanceInKM * 7.30;
            case CAR:
                return distanceInKM * 2.30;
        }
        return distanceInKM * 2.30;
    }



}
