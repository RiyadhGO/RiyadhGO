package sa.edu.yamamh.riyadhgo.ui;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import sa.edu.yamamh.riyadhgo.DataArrivedListener;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.TransportMethodSelectedListener;
import sa.edu.yamamh.riyadhgo.UIUtils;
import sa.edu.yamamh.riyadhgo.data.SelectTransportMethodModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;
import sa.edu.yamamh.riyadhgo.network.TransportMethodClient;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CarMethodFragment extends Fragment implements DataArrivedListener, TransportMethodSelectedListener {

    private TransportMethodModel selectedMethod;
    private RecyclerView dataRV;

    private BottomNavigationView navView;
    private TextView allTaxiesTV;
    private Button bookBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_car_method, container, false);
        dataRV = root.findViewById(R.id.car_meth_fgmt_transport_method_rv);
        navView = root.findViewById(R.id.car_nav_view);
        this.allTaxiesTV = root.findViewById(R.id.car_meth_fgmt_all_taxis_lbl);

        this.bookBtn = root.findViewById(R.id.car_meth_fgmt_book_btn);
        setNavViewClickListener();
        initDataRV();
        this.getTypeMethods();
        this.setBookBtnClickListener();
        this.setAllTaxiesTVText();
        return root;
    }

    private void setAllTaxiesTVText()
    {
        String info = "All taxies: ";
        SelectTransportMethodModel model = ChooseTransportMethodFragment.SELECTED_METHOD;
        if(model != null)
        {
            info += String.format("%.2f-%.2f",model.getMaxPrice(),model.getMinPrice());
        }
        this.allTaxiesTV.setText(info);
    }
    private void setBookBtnClickListener()
    {
        this.bookBtn.setOnClickListener( view -> {
           //open uber application.
            //Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
// Or map point based on latitude/longitude
 Uri location = Uri.parse("geo:"
         + StartFragment.pickLocation.getLatitude() + ","
         + StartFragment.pickLocation.getLongitude() + "?z=14"); // z param is zoom level
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
            startActivity(mapIntent);
        });
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
                TransportMethodClient.findTransportMethodsByType(TransportMethodTypes.CAR.name(),
                        CarMethodFragment.this, DataArrivedListener.RC_FIND_TRANSPORT_METHODS);
            }
        });
    }

    @Override
    public void onDataArrived(Object data, int requestCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<TransportMethodModel> models = (List<TransportMethodModel>) data;
                TransportMethodRVAdapter adapter = new TransportMethodRVAdapter(models,CarMethodFragment.this);
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
        selectedMethod = model;
    }

    private void navigateToSubAction(int action){
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_start_fragment);
        navController.navigate(action);
    }
    private void setNavViewClickListener() {
        navView.setSelectedItemId(R.id.navigation_car);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_bus) {
                    navigateToSubAction(R.id.action_carMethodFragment_to_busMethodFragment);
                } else if (item.getItemId()  == R.id.navigation_walking) {
                    navigateToSubAction(R.id.action_carMethodFragment_to_walkingMethodFragment);
                } else if (item.getItemId()  == R.id.navigation_scooter) {
                    navigateToSubAction(R.id.action_carMethodFragment_to_scooterMethodFragment);
                } else {
                    //navigateToSubAction(R.id.action_busMethodFragment_to_carMethodFragment);
                }
                item.setChecked(true);
                return true;
            }


        });
    }
}