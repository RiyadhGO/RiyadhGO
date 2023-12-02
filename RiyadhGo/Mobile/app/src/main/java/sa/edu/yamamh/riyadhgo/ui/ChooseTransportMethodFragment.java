package sa.edu.yamamh.riyadhgo.ui;

import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import sa.edu.yamamh.riyadhgo.DataArrivedListener;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.TransportMethodTypeSelectedListener;
import sa.edu.yamamh.riyadhgo.UIUtils;
import sa.edu.yamamh.riyadhgo.data.SelectTransportMethodModel;
import sa.edu.yamamh.riyadhgo.data.TransportMethodTypes;
import sa.edu.yamamh.riyadhgo.network.TransportMethodClient;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChooseTransportMethodFragment extends Fragment implements DataArrivedListener, TransportMethodTypeSelectedListener {

    public static SelectTransportMethodModel SELECTED_METHOD;
    private FloatingActionButton backBtn;
    private RecyclerView methodsRV;
    private Button showHideMethods;
    private boolean methodsVisible = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_choose_transport_method, container, false);
        backBtn = root.findViewById(R.id.ctm_back_btn);
        methodsRV = root.findViewById(R.id.transport_method_rv);
        showHideMethods = root.findViewById(R.id.btnTransMethod_hide_show);
        this.registerListeners();
        this.initMethodsRecyclerViewLayout();
        this.getSelectTransportMethods();
        return root;
    }

    private void registerListeners() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSubAction(R.id.action_chooseTransportMethodFragment_to_pickupDestSelectionFragment);
            }
        });
        showHideMethods.setOnClickListener( l -> {
            try {
                if (methodsVisible) {
                    methodsVisible = false;
                    ((SelectTransportMethodsRVAdapter) this.methodsRV.getAdapter()).clearItems();
                } else {
                    methodsVisible = true;
                    getSelectTransportMethods();
                }
            }catch (Exception ex)
            {
                Log.e("ChooseTransMethod",ex.getMessage(),ex);
                methodsVisible = false;
            }
        });
    }

    private void initMethodsRecyclerViewLayout(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        methodsRV.setLayoutManager(layoutManager);
    }

    private void navigateToSubAction(int action){
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_start_fragment);
        navController.navigate(action);
    }

    private void getSelectTransportMethods(){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TransportMethodClient.selectTransportMethods(
                            ChooseTransportMethodFragment.this,
                            DataArrivedListener.RC_SELECT_TRANSPORT_METHODS);
                }
            });
    }


    @Override
    public void onDataArrived(Object data, int requestCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(requestCode == DataArrivedListener.RC_SELECT_TRANSPORT_METHODS) {
                    List<SelectTransportMethodModel> models = (List<SelectTransportMethodModel>) data;
                    SelectTransportMethodsRVAdapter adapter = new SelectTransportMethodsRVAdapter(models, ChooseTransportMethodFragment.this);
                    methodsRV.setAdapter(adapter);
                }

            }
        });
    }

    @Override
    public void onError(String error, int requestCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.showAlertDialog(getActivity(),"Error",error);
            }
        });
    }

    @Override
    public void methodTypeSelected(SelectTransportMethodModel model) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SELECTED_METHOD = model;
                //navigate to fragments by method types.
                switch (SELECTED_METHOD.getMethodType())
                {
                    case "BUS":

                        navigateToSubAction(R.id.action_chooseTransportMethodFragment_to_busMethodFragment);
                        break;
                    case "CAR":

                        navigateToSubAction(R.id.action_chooseTransportMethodFragment_to_carMethodFragment);
                        break;
                    case "WALK":

                        navigateToSubAction(R.id.action_chooseTransportMethodFragment_to_walkingMethodFragment);
                        break;
                    case "SCOOTER":

                        navigateToSubAction(R.id.action_chooseTransportMethodFragment_to_scooterMethodFragment);
                        break;
                }
            }
        });
    }


}