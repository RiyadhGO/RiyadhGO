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
import sa.edu.yamamh.riyadhgo.data.UserModel;
import sa.edu.yamamh.riyadhgo.network.BaseApiClient;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements DataArrivedListener {

    private TextView loginLink;
    private Button registerBtn;

    private EditText emailText;
    private EditText passwordText;
    private EditText nameText;
    private EditText phoneText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        this.loginLink = root.findViewById(R.id.login_link);
        this.registerBtn = root.findViewById(R.id.register_btn);
        this.emailText = root.findViewById(R.id.register_frg_email_et);
        this.passwordText = root.findViewById(R.id.register_frg_pass_et);
        this.nameText = root.findViewById(R.id.register_frg_name_et);
        this.phoneText = root.findViewById(R.id.register_frg_phone_et);
        this.registerListeners();
        return root;
    }

    private void registerListeners() {
        this.loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });
        this.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

    }

    private void gotoLogin(){
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_registerFragment_to_navigation_login);
    }

    @Override
    public void onDataArrived(Object data,int requestCode) {

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.showAlertDialog(getActivity(), "Success!!!", "Registration Success");
            }
        });
    }
    @Override
    public void onError(String error,int requestCode) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtils.showAlertDialog(getActivity(), getResources().getString(R.string.error), error);
            }
        });
    }
    private UserModel getUser(){
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String name = nameText.getText().toString();
        String phone = phoneText.getText().toString();
        UserModel loginModel = new UserModel(email, password, name, phone);
        return loginModel;
    }
    private boolean validateUser(UserModel user){
        if(user.getEmail().isEmpty()){
            Toast.makeText(this.getActivity(), "Email is required", Toast.LENGTH_LONG).show();
            Log.e("RegisterFragment", "Email is required");
            return false;
        }
        if(user.getPassword().isEmpty()){
            Toast.makeText(this.getActivity(), "Password is required", Toast.LENGTH_LONG).show();
            Log.e("RegisterFragment", "Password is required");
            return false;
        }
        if(user.getName().isEmpty()){
            Toast.makeText(this.getActivity(), "Name is required", Toast.LENGTH_LONG).show();
            Log.e("RegisterFragment", "Name is required");
            return false;
        }
        if(user.getPhone().isEmpty()){
            Toast.makeText(this.getActivity(), "Phone is required", Toast.LENGTH_LONG).show();
            Log.e("RegisterFragment", "Phone is required");
            return false;
        }
        return true;
    }
    private void doRegister() {
        UserModel user = getUser();
        if(!validateUser(user)){
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                BaseApiClient.doRegister(user, RegisterFragment.this,DataArrivedListener.RC_REGISTER);
            }
        });


    }
}