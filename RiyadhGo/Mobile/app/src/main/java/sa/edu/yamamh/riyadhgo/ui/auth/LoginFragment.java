package sa.edu.yamamh.riyadhgo.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sa.edu.yamamh.riyadhgo.DataArrivedListener;
import sa.edu.yamamh.riyadhgo.R;
import sa.edu.yamamh.riyadhgo.UIUtils;
import sa.edu.yamamh.riyadhgo.network.BaseApiClient;
import sa.edu.yamamh.riyadhgo.data.LoginModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements DataArrivedListener {
    private Button loginButton;
    private EditText emailText;
    private EditText passwordText;
    private TextView registerLink;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = root.findViewById(R.id.login_btn);
        emailText = root.findViewById(R.id.login_frg_email_et);
        passwordText = root.findViewById(R.id.login_frg_pass_et);
        this.registerLink = root.findViewById(R.id.register_link);
        this.registerListeners();
        return root;
    }
    private void registerListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        this.registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRegister();
            }
        });
    }
    private void doLogin() {
        LoginModel user = getUser();
        if (!validateUser(user)) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseApiClient.doLogin(user,LoginFragment.this,DataArrivedListener.RC_LOGIN);
            }
        });
    }
    private LoginModel getUser() {
        LoginModel user = new LoginModel();
        user.setEmail(emailText.getText().toString());
        user.setPassword(passwordText.getText().toString());
        return user;
    }
    private boolean validateUser(LoginModel user) {
        if (user.getEmail().isEmpty()) {
            Toast.makeText(this.getActivity(), "Email is required", Toast.LENGTH_LONG).show();
            Log.e("LoginFragment", "Email is required");
            return false;
        }
        if (user.getPassword().isEmpty()) {
            Toast.makeText(this.getActivity(), "Password is required", Toast.LENGTH_LONG).show();
            Log.e("LoginFragment", "Password is required");
            return false;
        }
        return true;
    }
    @Override
    public void onDataArrived(Object data,int requestCode) {
         this.getActivity().runOnUiThread(new Runnable() {
             @Override
             public void run() {

                 gotoStart();
             }
         });
    }
    @Override
    public void onError(String error,int requestCode) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                UIUtils.showAlertDialog(LoginFragment.this.getActivity(),getResources().getString(R.string.error),error);
            }
        });
    }

    private void gotoRegister(){
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_login_to_registerFragment);
    }

    private void gotoStart(){
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_login_to_startFragment);
    }
}