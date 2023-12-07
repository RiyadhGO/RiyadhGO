package sa.edu.yamamh.riyadhgo;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;
import sa.edu.yamamh.riyadhgo.ui.StartFragment;
/*
-->Defines constants for base fare, price per kilometer, price per minute, and minimum fare for each region (Riyadh, Eastern, Western, Northern).
-->Implements a getEstimatedPrice method that calculates the estimated price based on the provided region, distance in kilometers, and time in minutes.
-->Applies a minimum fare based on the region if the calculated price is lower.
 */

//This class provides methods for estimating the price of a trip based on various factors
public class PricingUtils {

    private static final double BASE_FARE = 4;
    private static final double RIYADH_PRICE_PER_KM = 1.17;
    private static final double EASTERN_PRICE_PER_KM = 1.12;
    private static final double WESTERN_PRICE_PER_KM = 1.14;
    private static final double NORTHERN_PRICE_PER_KM = 1.14;

    //5 -- 30
    public static  final double RIYADH_PRICE_PER_MIN = 0.30;
    public static  final double EASTERN_PRICE_PER_MIN = 0.27;
    public static  final double WESTERN_PRICE_PER_MIN = 0.28;
    public static  final double NORTHERN_PRICE_PER_MIN = 0.28;


    private static final double RIYADH_MINIMUM_FARE = 9;
    private static final double EASTERN_MINIMUM_FARE = 8.5;
    private static final double WESTERN_MINIMUM_FARE = 8.5;
    private static final double NORTHERN_MINIMUM_FARE = 8.5;

    public  static double getEstimatedPrice(KSARegions region, double distanceInKM, double timeInMinutes){
        double price = 0;
        switch (region){
            case RIYADH:
                price = (RIYADH_PRICE_PER_KM * distanceInKM) + (RIYADH_PRICE_PER_MIN * timeInMinutes) + BASE_FARE;
                return price < RIYADH_MINIMUM_FARE ? RIYADH_MINIMUM_FARE : price;
            case EASTERN:
                price = (EASTERN_PRICE_PER_KM * distanceInKM) + (EASTERN_PRICE_PER_MIN * timeInMinutes) + BASE_FARE;
                return price < EASTERN_MINIMUM_FARE ? EASTERN_MINIMUM_FARE : price;
            case WESTERN:
                price = (WESTERN_PRICE_PER_KM * distanceInKM) + (WESTERN_PRICE_PER_MIN * timeInMinutes) + BASE_FARE;
                return price < WESTERN_MINIMUM_FARE ? WESTERN_MINIMUM_FARE : price;
            case NORTHERN:
                price = (NORTHERN_PRICE_PER_KM * distanceInKM) + (NORTHERN_PRICE_PER_MIN * timeInMinutes) + BASE_FARE;
                return price < NORTHERN_MINIMUM_FARE ? NORTHERN_MINIMUM_FARE : price;
            default:
                return price;
        }
    }

    public static double getEstimatedPriceForPickupDest(){
        double dist = DistanceUtils.getDistanceInKM(StartFragment
                .pickLocation.getLatLng(), StartFragment.destLocation.getLatLng());
        KSARegions reg = KSARegions.IsInRegion(StartFragment.destLocation.getLongitude(),StartFragment.destLocation.getLatitude());

        double time = DistanceUtils.getEstimatedTimeInMinutes(dist, TransportMethodTypes.CAR);
        double price = getEstimatedPrice(KSARegions.RIYADH, dist, time);
        return price;
    }







}
