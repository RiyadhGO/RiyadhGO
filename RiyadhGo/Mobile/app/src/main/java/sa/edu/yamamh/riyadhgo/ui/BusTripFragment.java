package sa.edu.yamamh.riyadhgo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import sa.edu.yamamh.riyadhgo.DistanceUtils;
import sa.edu.yamamh.riyadhgo.GPSLocationUtils;
import sa.edu.yamamh.riyadhgo.LocationChangedListener;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.UIUtils;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BusTripFragment extends Fragment implements LocationChangedListener {

    private LatLng currentLocation;
    private TextView distanceTV;
    private TextView timeTV;
    private Button goSosBtn;
    private boolean moving = true;
    private Marker currentMarker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bus_trip, container, false);
        distanceTV = root.findViewById(R.id.bus_trip_fgmt_distance_txt);
        timeTV = root.findViewById(R.id.bus_trip_fgmt_time_txt);
        goSosBtn = root.findViewById(R.id.bus_trip_fgmt_sos_btn);
        GPSLocationUtils.removeListener(this);
        GPSLocationUtils.registerListener(this);
        this.setGoSosBtnClickListener();
        return root;
    }

    private void setGoSosBtnClickListener() {
        this.goSosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goSosBtn.getText().toString().equals(getString(R.string.GO))) {
                    startMoving();
                    goSosBtn.setText(getString(R.string.sos));
                } else if (goSosBtn.getText().toString().equals(getString(R.string.sos))) {
                    stopMoving();
                    goSosBtn.setText(getString(R.string.GO));
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getString(R.string.sos_number)));
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void locationChanged(LatLng location) {

        if (currentMarker == null) {
            currentMarker = StartFragment.createMarker(location, "Current Location");
            currentMarker.setIcon(
                    BitmapDescriptorFactory.fromResource(R.drawable.move1));

        } else {
            currentMarker.setPosition(location);
        }
        if (currentLocation == null) {
            currentLocation = location;
        }
        StartFragment.animateMarkerNew(currentLocation, location, currentMarker);
        this.currentLocation = location;
        updateDistances();

    }

    private void updateDistances() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                double dist = DistanceUtils.getDistanceInKM(StartFragment.pickLocation.getLatLng(), currentLocation);
                double time = DistanceUtils.getEstimatedTimeInMinutes(dist, TransportMethodTypes.WALK);
                String distStr = String.format("%.2f Km", dist);
                String timeStr = String.format("%.2f Min", time);
                distanceTV.setText(distStr);
                timeTV.setText(timeStr);
                if (dist < 0.1) {
                    reachedDestination();
                }
            }


        });
    }

    private void reachedDestination() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopMoving();
                goSosBtn.setText(getString(R.string.GO));
                UIUtils.showAlertDialog(getActivity(), "Reached", "You have finished your trip!!");
                gotoEndTrip();

            }
        });
    }

    ///call GPSLocationUtil.updateLocation every 5 seconds
    public void startMoving() {
        moving = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (moving) {
                    try {
                        Thread.sleep(5000);
                        GPSLocationUtils.updateCurrentLocation(getActivity());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stopMoving() {
        this.moving = false;
    }

    public void gotoEndTrip() {
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_start_fragment);
        navController.navigate(R.id.action_scooterTripFragment_to_endTripFragment);
    }
}