package sa.edu.yamamh.riyadhgo.ui;

import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import sa.edu.yamamh.riyadhgo.GPSLocationUtils;
import sa.edu.yamamh.riyadhgo.MarkerUtils;
import sa.edu.yamamh.riyadhgo.LocationChangedListener;
import sa.edu.yamamh.riyadhgo.MainActivity;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.data.LocationModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;

import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.animation.Animator;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {
    private ImageView LogoutButton;
    public static LocationModel pickLocation;
    public static LocationModel destLocation;
    public static Marker pickMarker;
    public static Marker destMarker;
    public static  GoogleMap mMap;
    public static Marker currentMarker;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be
         * prompted to
         * install it inside the SupportMapFragment. This method will only be triggered
         * once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // 24.7010474,46.6296859

            mMap = googleMap;
            currentMarker = getDefaultMarker();
            // googleMap.moveCamera(CameraUpdateFactory.newLatLng(riyadh));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentMarker.getPosition(), 14.0f));
            currentMarker.setDraggable(true);
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(@NonNull Marker marker) {
                    currentMarker = marker;
                }
                @Override
                public void onMarkerDrag(@NonNull Marker marker) {
                    currentMarker = marker;
                }
                @Override
                public void onMarkerDragEnd(@NonNull Marker marker) {

                    for (LocationChangedListener listener : MarkerUtils.listeners) {
                        listener.locationChanged(currentMarker.getPosition());
                    }
                }
            });
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_start, container, false);
        //Create Logout
        this.LogoutButton=root.findViewById(R.id.logoutBtn);
        this.LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogout();
            }
        });
        return root;
    }
    private void doLogout()
    {
        GPSLocationUtils.listeners.clear();
        StartFragment.resetMap();
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.menuFragment);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    public static void moveCamera(final LatLng destination, final Marker marker) {

          animateMarkerTo(marker,destination);
    }

    private static void animateMarkerTo(final Marker marker, final LatLng finalPosition) {
        final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.Linear();
        final LatLng startPosition = marker.getPosition();
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 10;

        handler.post(new Runnable() {
            long elapsed;

            float t;

            float v;

            @Override
            public void run() {
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);

                marker.setPosition(latLngInterpolator.interpolate(v, startPosition, finalPosition));

                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(finalPosition)
                                .zoom(14f)
                                .build()));
            }
        });
    }

    /**
     * For other LatLngInterpolator interpolators, see https://gist.github.com/broady/6314689
     */
    interface LatLngInterpolator {

        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class Linear implements LatLngInterpolator {

            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;

                // Take the shortest path across the 180th meridian.
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }
    public static Marker getDefaultMarker(){
        LatLng riyadh = new LatLng(24.7010474, 46.6296859);
        Marker marker = mMap.addMarker(new MarkerOptions().position(riyadh).title("King Khalid Masjid"));
        return marker;
    }

    public static Marker createMarker(LatLng position, String title){
        Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(title));
        return marker;
    }

    public static void resetMap()
    {
        if(mMap != null){
            mMap.clear();
        }
        if(pickMarker != null)
        {
            pickMarker.remove();
        }
        if(destMarker != null)
        {
            destMarker.remove();
        }
        currentMarker = getDefaultMarker();
        // googleMap.moveCamera(CameraUpdateFactory.newLatLng(riyadh));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentMarker.getPosition(), 14.0f));
        currentMarker.setDraggable(true);
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
                currentMarker = marker;
            }
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
                currentMarker = marker;
            }
            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {

                for (LocationChangedListener listener : MarkerUtils.listeners) {
                    listener.locationChanged(currentMarker.getPosition());
                }
            }
        });
    }

}

