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
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import sa.edu.yamamh.riyadhgo.DataArrivedListener;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.TransportMethodSelectedListener;
import sa.edu.yamamh.riyadhgo.UIUtils;
import sa.edu.yamamh.riyadhgo.data.TransportMethodModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;
import sa.edu.yamamh.riyadhgo.network.TransportMethodClient;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class WalkingMethodFragment extends Fragment  implements DataArrivedListener, TransportMethodSelectedListener {

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
        View root = inflater.inflate(R.layout.fragment_walking_method, container, false);
        dataRV = root.findViewById(R.id.walk_meth_fgmt_transport_method_rv);
        navView = root.findViewById(R.id.walking_nav_view);
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
                TransportMethodClient.findTransportMethodsByType(TransportMethodTypes.WALK.toString(),
                        WalkingMethodFragment.this, DataArrivedListener.RC_FIND_TRANSPORT_METHODS);
            }
        });
    }

    @Override
    public void onDataArrived(Object data, int requestCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<TransportMethodModel> models = (List<TransportMethodModel>) data;
                TransportMethodRVAdapter adapter = new TransportMethodRVAdapter(models,WalkingMethodFragment.this);
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

    @Override
    public void methodSelected(TransportMethodModel model) {
        WalkingTripFragment.originalMethod = TransportMethodTypes.WALK;
        WalkingTripFragment.targetLocation = StartFragment.destLocation;
        WalkingTripFragment.firstTarget = false;
        navigateToSubAction(R.id.action_walkingMethodFragment_to_walkingTripFragment);

    }

    private void navigateToSubAction(int action){
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_start_fragment);
        navController.navigate(action);
    }

    private void setNavViewClickListener() {
        navView.setSelectedItemId(R.id.navigation_walking);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_bus) {
                    navigateToSubAction(R.id.action_walkingMethodFragment_to_busMethodFragment);
                } else if (item.getItemId()  == R.id.navigation_walking) {

                } else if (item.getItemId()  == R.id.navigation_scooter) {
                    navigateToSubAction(R.id.action_walkingMethodFragment_to_scooterMethodFragment);
                } else {
                    navigateToSubAction(R.id.action_walkingMethodFragment_to_carMethodFragment);
                }
                item.setChecked(true);
                return true;
            }


        });
    }
}