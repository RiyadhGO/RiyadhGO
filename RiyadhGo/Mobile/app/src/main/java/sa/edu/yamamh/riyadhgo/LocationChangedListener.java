package sa.edu.yamamh.riyadhgo;

import com.google.android.gms.maps.model.LatLng;

public interface LocationChangedListener {//This interface defines the callback method
    // for an event indicating that the user's location has changed
    public void locationChanged(LatLng location);//This method is called whenever the user's
    // location updates. It receives the updated location as a LatLng object
}
