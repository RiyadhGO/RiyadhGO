package sa.edu.yamamh.riyadhgo;

import sa.edu.yamamh.riyadhgo.data.TransportMethodModel;

//This interface defines the callback method for an event indicating that a transport method has been selected
public interface TransportMethodSelectedListener {

    //This method is called when a user selects a transport method. It receives the selected transport method model object as an argument
    public void methodSelected(TransportMethodModel model);
}
