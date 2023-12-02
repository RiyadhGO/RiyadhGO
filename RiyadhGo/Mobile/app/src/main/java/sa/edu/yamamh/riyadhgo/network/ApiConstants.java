package sa.edu.yamamh.riyadhgo.network;

public class ApiConstants {
    public static String SUCCESS = "Success";

    public static String UN_AUTHORIZED = "Unauthorized";

    public static String BASE_URI = "http://192.168.8.125:8080";
    public static String LOGIN_URI = BASE_URI + "/auth/login";
    public static String REGISTER_URI = BASE_URI + "/auth/register";
    
    public static String LOCATIONS_URI = BASE_URI + "/locations";
    public static String LOCATIONS_CREATE_URI = LOCATIONS_URI + "/create";
    public static String LOCATIONS_EDIT_URI = LOCATIONS_URI + "/edit";
    public static String LOCATIONS_DELETE_URI = LOCATIONS_URI + "/delete";
    public static String LOCATIONS_FIND_URI = LOCATIONS_URI + "/find";
    public  static String FAV_LOCATIONS_URI = BASE_URI +  "/favlocs";
    public static String FAV_LOCATIONS_ADD_URI =  FAV_LOCATIONS_URI + "/add";
    public static String FAV_LOCATIONS_REMOVE_URI =  FAV_LOCATIONS_URI + "/remove";
    public static String FAV_LOCATIONS_ME_URI =  FAV_LOCATIONS_URI + "/me";
    public static String USERS_URI = BASE_URI + "/users";
    public static String USERS_CREATE_URI = USERS_URI + "/create";
    public static String USERS_EDIT_URI = USERS_URI + "/edit";
    public static String USERS_DELETE_URI = USERS_URI + "/delete";
    public static String TRIPS_URI = BASE_URI + "/trip";
    public static String TRIPS_CREATE_URI = TRIPS_URI + "/create";
    public static String TRIPS_EDIT_URI = TRIPS_URI + "/edit";
    public static String TRIPS_DELETE_URI = TRIPS_URI + "/delete";

    public static String ROUTES_URI = BASE_URI + "/routes";
    public static String ROUTES_CREATE_URI = ROUTES_URI + "/create";
    public static String ROUTES_EDIT_URI = ROUTES_URI + "/edit";
    public static String ROUTES_DELETE_URI = ROUTES_URI + "/delete";

    public static String TRANSPORT_METHODS_URI = BASE_URI + "/tm";
    public static String TRANSPORT_METHODS_CREATE_URI = TRANSPORT_METHODS_URI + "/create";
    public static String TRANSPORT_METHODS_EDIT_URI = TRANSPORT_METHODS_URI + "/edit";
    public static String TRANSPORT_METHODS_DELETE_URI = TRANSPORT_METHODS_URI + "/delete";
    public static String TRANSPORT_METHODS_SELECT_URI = TRANSPORT_METHODS_URI + "/select";
    public static String TRANSPORT_METHODS_FIND_URI = TRANSPORT_METHODS_URI + "/find";



}
