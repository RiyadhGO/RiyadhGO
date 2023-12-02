package sa.edu.yamamh.riyadhgo.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sa.edu.yamamh.riyadhgo.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class EndTripFragment extends Fragment {


    private TextView pickTV;
    private TextView destTV;
    private TextView calTV;
    private TextView carTV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_end_trip, container, false);
        this.pickTV = root.findViewById(R.id.end_trip_fgmt_pick_lbl);
        this.destTV = root.findViewById(R.id.end_trip_fgmt_dest_lbl);
        this.calTV = root.findViewById(R.id.end_trip_fgmt_cal_txt);
        this.carTV = root.findViewById(R.id.end_trip_fgmt_car_txt);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTextViews();
    }
    private void updateTextViews(){
        this.pickTV.setText(StartFragment.pickLocation.getName());
        this.destTV.setText(StartFragment.destLocation.getName());
        //this.calTV.setText(WalkingTripFragment.targetLocation.getCalories());
        //this.carTV.setText(WalkingTripFragment.targetLocation.getCarbon());
    }
}