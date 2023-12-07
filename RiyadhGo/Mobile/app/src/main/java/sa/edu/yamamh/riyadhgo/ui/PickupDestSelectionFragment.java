package sa.edu.yamamh.riyadhgo.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;

import sa.edu.yamamh.riyadhgo.DataArrivedListener;
import sa.edu.yamamh.riyadhgo.PlaceSelectedListener;
import sa.edu.yamamh.riyadhgo.MarkerUtils;
import sa.edu.yamamh.riyadhgo.InputDoneListener;
import sa.edu.yamamh.riyadhgo.LocationChangedListener;
import sa.edu.yamamh.riyadhgo.MainActivity;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.UIUtils;
import sa.edu.yamamh.riyadhgo.data.LocationModel;
import sa.edu.yamamh.riyadhgo.network.LocationClient;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PickupDestSelectionFragment extends Fragment
        implements LocationChangedListener, DataArrivedListener,
        InputDoneListener, PlaceSelectedListener {
    public static final short PICKUP = 1;
    public static final short DESTINATION = 2;
    public static short CURRENT_SELECTION = PICKUP;
    private Button btnPickup;
    private Button btnDestination;
    private Button btnHome;
    private Button btnFavorites;
    private boolean isFavorites;
    private ImageView btnPickupFavorites;
    private ImageView btnDestinationFavorites;

    private LocationModel pickupLocation;
    private LocationModel destinationLocation;
    private RecyclerView favoritesRV;
    private Button directionStartBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pickupLocation = new LocationModel();
        this.destinationLocation = new LocationModel();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pickup_dest_selection, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        btnPickup = root.findViewById(R.id.choose_pickup_btn);
        btnDestination = root.findViewById(R.id.choose_dest_btn);
        btnHome = root.findViewById(R.id.fgmt_pickup_dest_home_btn);
        this.btnPickupFavorites = root.findViewById(R.id.fav_pickup_btn);
        this.btnDestinationFavorites = root.findViewById(R.id.fav_dest_btn);
        this.favoritesRV = root.findViewById(R.id.recent_places_rv);
        this.initFavoritePlacesRV();
        this.btnFavorites = root.findViewById(R.id.fgmt_pickup_dest_fav_btn);
        this.directionStartBtn  = root.findViewById(R.id.directions_start_btn);
        this.registerListeners();
    }

    private void initFavoritePlacesRV() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favoritesRV.setLayoutManager(layoutManager);

    }

    private void registerListeners() {
        btnPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMap();
                CURRENT_SELECTION = PICKUP;
                MarkerUtils.listeners.remove(PickupDestSelectionFragment.this);
                MarkerUtils.listeners.add(PickupDestSelectionFragment.this);
                // GpsLocationUtils.updateCurrentLocation(getActivity());
            }
        });
        btnDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              resetMap();
                CURRENT_SELECTION = DESTINATION;
                MarkerUtils.listeners.remove(PickupDestSelectionFragment.this);
                MarkerUtils.listeners.add(PickupDestSelectionFragment.this);
                // GpsLocationUtils.updateCurrentLocation(getActivity());
            }
        });
        btnHome.setOnClickListener(view -> {

            navigateToHome();
        });
        btnPickupFavorites.setOnClickListener(view -> {
            UIUtils.showInputDialog(getActivity(),
                    getResources().getString(R.string.add_to_favorites),
                    getResources().getString(R.string.add_to_favorites), this);
        });
        btnDestinationFavorites.setOnClickListener(view -> {
            UIUtils.showInputDialog(getActivity(),
                    getResources().getString(R.string.add_to_favorites),
                    getResources().getString(R.string.add_to_favorites), this);
        });
        this.btnFavorites.setOnClickListener(view -> {
            if (!isFavorites) {
                getFavorites();
                isFavorites = true;
            } else {
                isFavorites = false;
                ((LocationsRVAdapter) this.favoritesRV.getAdapter()).clearItems();

            }
        });

        this.directionStartBtn.setOnClickListener(view ->{
            if(directionStartBtn.getText().toString().equals(getResources().getString(R.string.directions)))
            {
                if(this.favoritesRV.getAdapter() != null) {
                    isFavorites = false;
                    ((LocationsRVAdapter) this.favoritesRV.getAdapter()).clearItems();
                }
                directionStartBtn.setText(getResources().getString(R.string.proceed));
                drawDirections();
            }
            else
            {
               directionStartBtn.setText(getResources().getString(R.string.directions));
               navigateToSubAction(R.id.action_pickupDestSelectionFragment_to_chooseTransportMethodFragment);
            }
        });
    }

    @Override
    public void locationChanged(LatLng location) {
        if (CURRENT_SELECTION == PICKUP) {

                updatePickupLocation(location,getActivity().getText(R.string.selected).toString());
        } else {
            updateDestinationLocation(location,getActivity().getText(R.string.selected).toString());


        }

    }

    private void updatePickupLocation(LatLng location, String name){
        this.pickupLocation.setLatitude((float) location.latitude);
        this.pickupLocation.setLongitude((float) location.longitude);
        this.pickupLocation.setName(name);
        this.btnPickup.setText(pickupLocation.getName());
    }

    private void updateDestinationLocation(LatLng location, String name){
        this.destinationLocation.setLatitude((float) location.latitude);
        this.destinationLocation.setLongitude((float) location.longitude);
        this.destinationLocation.setName(name);
        this.btnDestination.setText(destinationLocation.getName());
    }

    private void resetMap(){
        if(StartFragment.mMap == null){
            return;

        }
        StartFragment.mMap.clear();
        StartFragment.currentMarker = StartFragment.getDefaultMarker();
        StartFragment.mMap.getUiSettings().setZoomControlsEnabled(true);
        StartFragment.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(StartFragment.currentMarker.getPosition(), 14.0f));
        StartFragment.currentMarker.setDraggable(true);
    }
    private void navigateToHome() {
        this.navigateToAction(R.id.action_startFragment_to_menuFragment);
    }

    private void navigateToAction(int action) {
        NavController navController = Navigation.findNavController(this.getActivity(),
                R.id.nav_host_fragment_activity_main);
        navController.navigate(action);
    }
    private void navigateToSubAction(int action){
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_start_fragment);
        navController.navigate(action);
    }

    @Override
    public void onDataArrived(Object data, int requestCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (requestCode == DataArrivedListener.RC_ADD_TO_FAV) {
                    addedToFavorites(data);
                    return;
                } else if (requestCode == DataArrivedListener.RC_GET_FAV_LOCATIONS) {
                    showFavorites(data);

                }

            }
        });
    }

    private void addedToFavorites(Object data) {
        LocationModel location = (LocationModel) data;
        if (CURRENT_SELECTION == PICKUP) {

            btnPickup.setText(location.getName());
        } else {

            btnDestination.setText(location.getName());

        }
    }

    private void showFavorites(Object data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<LocationModel> models = (List<LocationModel>) data;
                LocationsRVAdapter adapter = new LocationsRVAdapter(models, PickupDestSelectionFragment.this,
                        pickupLocation.getLatLng());
                favoritesRV.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onError(String error, int requestCode) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.showAlertDialog(getActivity(), getResources().getString(R.string.error), error,null);
            }
        });
    }

    @Override
    public void inputDone(String text) {
        Log.e("inputDone", text);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (text != null && !text.isEmpty()) {
                    LocationModel model = CURRENT_SELECTION == PICKUP ? pickupLocation
                            : destinationLocation;
                    model.setName(text);
                    LocationClient.addToFavorites(model, PickupDestSelectionFragment.this,
                            DataArrivedListener.RC_ADD_TO_FAV);
                }
            }
        });
    }

    public void getFavorites() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LocationClient.getMyFavoriteLocations(PickupDestSelectionFragment.this,
                        DataArrivedListener.RC_GET_FAV_LOCATIONS);

            }
        });

    }
    @Override
    public void placeSelected(LocationModel model) {
        if (CURRENT_SELECTION == PICKUP) {
            this.pickupLocation = model;
            this.btnPickup.setText(model.getName());
        } else if (CURRENT_SELECTION == DESTINATION) {
            this.destinationLocation = model;
            this.btnDestination.setText(model.getName());
        }
    }

    public void drawDirections() {
        if (StartFragment.mMap == null || this.pickupLocation == null || this.destinationLocation == null) {
            return;
        }
        StartFragment.mMap.clear();
        StartFragment.pickLocation = this.pickupLocation;
        StartFragment.pickMarker =  StartFragment.mMap.addMarker(new MarkerOptions().position(StartFragment.pickLocation.getLatLng()).title("Pickup"));
        StartFragment.destLocation = this.destinationLocation;
        StartFragment.destMarker =  StartFragment.mMap.addMarker(new MarkerOptions().position( StartFragment.destLocation.getLatLng()).title("Destination"));

        // Define list to get all latlng for the route
        List<LatLng> path = new ArrayList<>();

        // Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyADIuwlpkq4f4abCILFse3q4nB_9UXQ_HI")
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, StartFragment.pickLocation.getLatitude()
                        + "," + StartFragment.pickLocation.getLongitude(),
                StartFragment.destLocation.getLatitude() + "," +  StartFragment.destLocation.getLongitude());
        try {
            DirectionsResult res = req.await();

            // Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs != null) {
                    for (int i = 0; i < route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j = 0; j < leg.steps.length; j++) {
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length > 0) {
                                    for (int k = 0; k < step.steps.length; k++) {
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            // Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        // Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("PickupDestFgmtDrawDirections", ex.getLocalizedMessage());
        }

        // Draw the polyline
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
            StartFragment.mMap.addPolyline(opts);

        }

        StartFragment.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( StartFragment.pickLocation.getLatLng(), 14));
    }

}