package sa.edu.yamamh.riyadhgo;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sa.edu.yamamh.riyadhgo.network.BaseApiClient;
import sa.edu.yamamh.riyadhgo.databinding.ActivityMainBinding;
//This class represents the main activity of the application and handles the overall navigation and initial setup
public class MainActivity extends AppCompatActivity {

    public static String currentUser = "";//Defines a currentUser static string to hold the logged-in user's username
    private FloatingActionButton fab;
    private ActivityMainBinding binding;//Initializes the layout using ActivityMainBinding and sets it as the content view

    @Override
    protected void onCreate(Bundle savedInstanceState) {//Initializes the activity and sets up navigation, listeners, and location updates
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_login,
               R.id.navigation_register)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        this.checkIsLoggedIn();
        fab = findViewById(R.id.fab_menu);
        this.registerListeners();
        GPSLocationUtils.startLocationUpdates(this);

    }
    //Verifies if a user is currently logged in and redirects to Home if so
    private void checkIsLoggedIn(){
        if(BaseApiClient.IS_LOGGED_IN){
            this.gotoHome();
        }
    }
    private void registerListeners(){
        fab.setOnClickListener(view -> gotoMenu());
    }

    //Navigates the user to the Home fragment
    private void gotoHome(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_navigation_login_to_startFragment);
    }
    //Navigates the user to the Menu fragment
    private void gotoMenu(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.menuFragment);
    }






}