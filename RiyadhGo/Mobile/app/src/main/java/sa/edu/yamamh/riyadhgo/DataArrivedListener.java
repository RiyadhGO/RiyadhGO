package sa.edu.yamamh.riyadhgo;

public interface DataArrivedListener {

    /////////////////////////////////////////
    // Request codes for the data arrived listener
    public static final int RC_LOGIN = 1;
    public static final int RC_REGISTER = 2;
    public static final int RC_ADD_TO_FAV = 10;
    public static final int RC_FIND_LOCATION = 11;
    public static final int RC_GET_FAV_LOCATIONS = 12;
    public static final int RC_FIND_SCOOTER_LOCATIONS = 13;
    int RC_FIND_BUS_LOCATIONS = 14;
    public static  final int RC_FIND_TRANSPORT_METHODS = 20;
    public static final int RC_SELECT_TRANSPORT_METHODS = 21;


    void onDataArrived(Object data, int requestCode);

    void onError(String error, int requestCode);

}
