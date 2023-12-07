package sa.edu.yamamh.riyadhgo;

import sa.edu.yamamh.riyadhgo.data.SelectTransportMethodModel;

//This interface defines the callback method for an event indicating that a transport method type has been selected
public interface TransportMethodTypeSelectedListener {

    //This method is called when a user selects a transport method type. It receives the selected transport method type model object as an argument
    public void methodTypeSelected(SelectTransportMethodModel model);
}
