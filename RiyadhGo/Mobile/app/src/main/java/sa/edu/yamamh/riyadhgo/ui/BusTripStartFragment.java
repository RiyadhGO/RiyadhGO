package sa.edu.yamamh.riyadhgo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sa.edu.yamamh.riyadhgo.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BusTripStartFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bus_trip_start, container, false);
        return root;
    }
}