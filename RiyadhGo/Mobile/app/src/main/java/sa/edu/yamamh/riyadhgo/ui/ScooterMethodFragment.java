package sa.edu.yamamh.riyadhgo.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import sa.edu.yamamh.riyadhgo.DataArrivedListener;
import sa.edu.yamamh.riyadhgo.PlaceSelectedListener;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.UIUtils;
import sa.edu.yamamh.riyadhgo.data.LocationModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;
import sa.edu.yamamh.riyadhgo.network.LocationClient;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ScooterMethodFragment extends Fragment implements DataArrivedListener, PlaceSelectedListener {

    private LocationModel selectedLocation;
    private RecyclerView dataRV;
    private BottomNavigationView navView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_scooter_method, container, false);
        dataRV = root.findViewById(R.id.scooter_meth_fgmt_transport_method_rv);
        navView = root.findViewById(R.id.scooter_nav_view);
        setNavViewClickListener();
        initDataRV();
        this.getTypeMethods();
        return root;
    }

    private void initDataRV(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataRV.setLayoutManager(layoutManager);
    }

    private void getTypeMethods(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LocationClient.findLocation(TransportMethodTypes.SCOOTER.toString(),
                        ScooterMethodFragment.this, DataArrivedListener.RC_FIND_SCOOTER_LOCATIONS);
            }
        });
    }

    @Override
    public void onDataArrived(Object data, int requestCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<LocationModel> models = (List<LocationModel>) data;
                ScooterBusLocationsRVAdapter adapter =
                        new ScooterBusLocationsRVAdapter( models,
                                ScooterMethodFragment.this,StartFragment.pickLocation.getLatLng());
                dataRV.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onError(String error, int requestCode) {
        getActivity().runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        UIUtils.showAlertDialog(getActivity(),"Error:", error);
                    }
                }
        );
    }



    private void navigateToSubAction(int action){
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_start_fragment);
        navController.navigate(action);
    }
    private void setNavViewClickListener() {
        navView.setSelectedItemId(R.id.navigation_scooter);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_bus) {
                    navigateToSubAction(R.id.action_scooterMethodFragment_to_busMethodFragment);
                } else if (item.getItemId()  == R.id.navigation_walking) {
                    navigateToSubAction(R.id.action_scooterMethodFragment_to_walkingMethodFragment);
                } else if (item.getItemId()  == R.id.navigation_scooter) {
                   // navigateToSubAction(R.id.action_busMethodFragment_to_scooterMethodFragment);
                } else {
                    navigateToSubAction(R.id.action_scooterMethodFragment_to_carMethodFragment);
                }
                item.setChecked(true);
                return true;
            }


        });
    }

    @Override
    public void placeSelected(LocationModel model) {
        selectedLocation = model;
        WalkingTripFragment.originalMethod = TransportMethodTypes.SCOOTER;
        WalkingTripFragment.targetLocation = model;
        navigateToSubAction(R.id.action_scooterMethodFragment_to_walkingTripFragment);
    }
}