package sa.edu.yamamh.riyadhgo.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import sa.edu.yamamh.riyadhgo.MainActivity;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.TripsUtil;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class EndTripFragment extends Fragment {

    private TextView userLbl;
    private TextView pickTV;
    private TextView destTV;
    private TextView calTV;
    private TextView carTV;

    private TextView svCarTV;

    private Button contBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_end_trip, container, false);
        this.userLbl = root.findViewById(R.id.end_trip_fgmt_person_lbl);
        this.pickTV = root.findViewById(R.id.end_trip_fgmt_pick_lbl);
        this.destTV = root.findViewById(R.id.end_trip_fgmt_dest_lbl);
        this.calTV = root.findViewById(R.id.end_trip_fgmt_cal_txt);
        this.carTV = root.findViewById(R.id.end_trip_fgmt_car_txt);
        this.svCarTV = root.findViewById(R.id.end_trip_fgmt_svcar_txt);
        this.contBtn = root.findViewById(R.id.end_trip_fgmt_contBtn);
        this.contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartFragment.resetMap();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_start_fragment);
                navController.navigate(R.id.action_endTripFragment_to_pickupDestSelectionFragment);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTextViews();
    }
    private void updateTextViews(){
        this.userLbl.setText(MainActivity.currentUser);
        this.pickTV.setText(StartFragment.pickLocation.getName());
        this.destTV.setText(StartFragment.destLocation.getName());
        this.calTV.setText(String.format("%.2f C",TripsUtil.getCaloriesBurned()));
        this.carTV.setText(String.format("%.2f g",TripsUtil.getCarbonEmitted()));
        this.svCarTV.setText(String.format("%.2f g",TripsUtil.getCarbonEmissionSaved()));
        TripsUtil.resetData();

    }
}