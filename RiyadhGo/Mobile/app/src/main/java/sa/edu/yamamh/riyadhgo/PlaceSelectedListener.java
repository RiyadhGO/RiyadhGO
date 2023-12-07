package sa.edu.yamamh.riyadhgo;

import sa.edu.yamamh.riyadhgo.data.LocationModel;

//This interface defines the callback method for an event indicating that a place has been selected
public interface PlaceSelectedListener {


    //This method is called when a user selects a place. It receives the selected location model object as an argument
    public void placeSelected(LocationModel model);
}
