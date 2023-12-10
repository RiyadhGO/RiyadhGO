package sa.edu.yamamh.riyadhgo.network;
//This class defines constant strings representing URLs and status messages for interacting with a backend API
public class ApiConstants {
    public static String SUCCESS = "Success";//Message indicating a successful operation

    public static String UN_AUTHORIZED = "Unauthorized";//Message indicating an unauthorized access attempt

    public static String BASE_URI = "http://192.168.100.59:8080";//IP Address, Base URL of the API server
    public static String LOGIN_URI = BASE_URI + "/auth/login";//URL for user login
    public static String REGISTER_URI = BASE_URI + "/auth/register";//URL for user registration
    
    public static String LOCATIONS_URI = BASE_URI + "/locations";//Base URL for location operations
    public static String LOCATIONS_CREATE_URI = LOCATIONS_URI + "/create";//URL for creating a new location
    public static String LOCATIONS_EDIT_URI = LOCATIONS_URI + "/edit";//URL for editing an existing location
    public static String LOCATIONS_DELETE_URI = LOCATIONS_URI + "/delete";//URL for deleting a location
    public static String LOCATIONS_FIND_URI = LOCATIONS_URI + "/find";//URL for searching locations
    public  static String FAV_LOCATIONS_URI = BASE_URI +  "/favlocs";//Base URL for favorite locations
    public static String FAV_LOCATIONS_ADD_URI =  FAV_LOCATIONS_URI + "/add";//URL for adding a location to favorites
    public static String FAV_LOCATIONS_REMOVE_URI =  FAV_LOCATIONS_URI + "/remove";//URL for removing a location from favorites
    public static String FAV_LOCATIONS_ME_URI =  FAV_LOCATIONS_URI + "/me";//URL for retrieving the user's favorite locations
    public static String USERS_URI = BASE_URI + "/users";//Base URL for user operations
    public static String USERS_CREATE_URI = USERS_URI + "/create";//URL for creating a new user
    public static String USERS_EDIT_URI = USERS_URI + "/edit";//URL for editing an existing user
    public static String USERS_DELETE_URI = USERS_URI + "/delete";//URL for deleting a user
    public static String TRIPS_URI = BASE_URI + "/trip";//Base URL for trip operations
    public static String TRIPS_CREATE_URI = TRIPS_URI + "/create";//URL for creating a new trip
    public static String TRIPS_EDIT_URI = TRIPS_URI + "/edit";//URL for editing an existing trip
    public static String TRIPS_DELETE_URI = TRIPS_URI + "/delete";//URL for deleting a trip

    public static String ROUTES_URI = BASE_URI + "/routes";//Base URL for route operations
    public static String ROUTES_CREATE_URI = ROUTES_URI + "/create";//URL for creating a new route
    public static String ROUTES_EDIT_URI = ROUTES_URI + "/edit";//URL for editing an existing route
    public static String ROUTES_DELETE_URI = ROUTES_URI + "/delete";//URL for deleting a route

    public static String TRANSPORT_METHODS_URI = BASE_URI + "/tm";//Base URL for transportation method operations
    public static String TRANSPORT_METHODS_CREATE_URI = TRANSPORT_METHODS_URI + "/create";//URL for creating a new transportation method
    public static String TRANSPORT_METHODS_EDIT_URI = TRANSPORT_METHODS_URI + "/edit";//URL for editing an existing transportation method
    public static String TRANSPORT_METHODS_DELETE_URI = TRANSPORT_METHODS_URI + "/delete";//URL for deleting a transportation method
    public static String TRANSPORT_METHODS_SELECT_URI = TRANSPORT_METHODS_URI + "/select";//URL for selecting a transportation method for a trip
    public static String TRANSPORT_METHODS_FIND_URI = TRANSPORT_METHODS_URI + "/find";//URL for searching for transportation methods



}
