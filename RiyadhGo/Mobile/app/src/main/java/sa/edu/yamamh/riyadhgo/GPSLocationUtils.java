package sa.edu.yamamh.riyadhgo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sa.edu.yamamh.riyadhgo.data.TripModel;

public class GPSLocationUtils {
    //This class provides functionality for tracking the user's location updates and notifying interested listeners

    private static ExecutorService executor = Executors.newSingleThreadExecutor();//Single-threaded executor: Utilizes a single thread for location updates to avoid resource contention
    public static List<LocationChangedListener> listeners = new ArrayList<>();
    //Allows registering and removing listeners interested in location changes
    public static void registerListener(LocationChangedListener listener){
        listeners.add(listener);//Registers a listener to receive location updates
    }
    public static void removeListener(LocationChangedListener listener){
        listeners.remove(listener);//Removes a previously registered listener
    }

    public static void startLocationUpdates(Activity ctx) {
        //Starts a background thread that periodically updates the user's location and notifies listeners
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(3000);

                            GPSLocationUtils.updateCurrentLocation(ctx);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private static void updateCurrentLocation(Activity ctx) {
        //Retrieves the current location using the FusedLocationProviderClient and broadcasts it to registered listeners
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(ctx);
        if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return new CancellationTokenSource().getToken();
                    }

                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }
                })
                .addOnSuccessListener(ctx, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            for(int i = 0; i < listeners.size(); i++)
                            {
                                listeners.get(i).locationChanged(latLng);
                            }

                        }
                    }
                });
    }
}
