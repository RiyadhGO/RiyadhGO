package sa.edu.yamamh.riyadhgo;

public interface DataArrivedListener {

    /////////////////////////////////////////
    // Request codes for the data arrived listener
    public static final int RC_LOGIN = 1;//Request code for login operations
    public static final int RC_REGISTER = 2;//Request code for registration operations
    public static final int RC_ADD_TO_FAV = 10;//Request code for adding a location to favorites
    public static final int RC_FIND_LOCATION = 11;//Request code for finding locations
    public static final int RC_GET_FAV_LOCATIONS = 12;//Request code for retrieving user's favorite locations
    public static final int RC_FIND_SCOOTER_LOCATIONS = 13;//Request code for finding scooter locations
    int RC_FIND_BUS_LOCATIONS = 14;
    public static  final int RC_FIND_TRANSPORT_METHODS = 20;//Request code for finding transportation methods
    public static final int RC_SELECT_TRANSPORT_METHODS = 21;//Request code for selecting a transportation method


    void onDataArrived(Object data, int requestCode);
    /*
-->This method is called when the request is successful and returns data.
-->It receives the retrieved data and the corresponding request code as arguments.
 */

    void onError(String error, int requestCode);
    /*
-->This method is called when the request encounters an error.
-->It receives the error message and the corresponding request code as arguments.
 */

}
