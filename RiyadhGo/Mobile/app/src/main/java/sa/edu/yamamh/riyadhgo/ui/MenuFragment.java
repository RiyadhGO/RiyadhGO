package sa.edu.yamamh.riyadhgo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sa.edu.yamamh.riyadhgo.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    private Button acknowledgeBtn;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        this.acknowledgeBtn = root.findViewById(R.id.btn_go_acknowledgement);
        this.loginBtn = root.findViewById(R.id.btn_go_login);
        this.registerBtn = root.findViewById(R.id.btn_go_register);
        this.registerClickListeners();
        return root;
    }

    private void registerClickListeners()
    {
        this.acknowledgeBtn.setOnClickListener(view-> navigateToAction(R.id.action_menuFragment_to_navigation_acknowledgement));
        this.loginBtn.setOnClickListener(view-> navigateToAction(R.id.action_menuFragment_to_navigation_login));
        this.registerBtn.setOnClickListener(view-> navigateToAction(R.id.action_menuFragment_to_navigation_register));
    }
    private void navigateToAction(int action){
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(action);
    }
}