package sa.edu.yamamh.riyadhgo;

import android.app.Activity;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.CancellationToken;

import java.util.ArrayList;
import java.util.List;
/*
-->Maintains a list of LocationChangedListener objects.
-->Offers a informListeners method that broadcasts the current location to all registered listeners.
-->Simplifies location change notification without requiring direct interaction with individual listeners.
 */

//This class provides functionality for notifying interested listeners about location changes
public class MarkerUtils {
    public static List<LocationChangedListener> listeners = new ArrayList<>();
    private static void informListeners(LatLng location){
        if(listeners!=null)
        {
            for(int i = 0; i < listeners.size(); i++)
            {
                listeners.get(i).locationChanged(location);
            }
        }
    }


}
